"""Export neutral movement infrastructure into a fresh, history-free repository.

Run from any directory: py -3 development/workflows/implementation/export_implementation_base.py
    --destination <new-directory> --branch fix/<finding>
    --finding development/research/findings/<pair>/<finding.md>
Only the integrator runs this in the product checkout. Workers receive the export.
"""

import argparse
import hashlib
import json
from pathlib import Path
import re
import shutil
import subprocess


ROOT = Path(__file__).resolve().parents[3]
JAVA = Path("src/main/java/me/wolfii/legacyparkourcompat")
CLIENT_JAVA = Path("src/client/java/me/wolfii/legacyparkourcompat")
NEUTRAL_MECHANICS = (
    "MechanicKey", "MechanicType", "MovementChange", "MovementChangeRegistry",
    "MovementRuntime", "VanillaCall", "VanillaFn", "VersionedMechanic",
)

# MovementRuntime is a product dispatch facade as well as the generic lookup
# entry point used by changes. Keep the exported surface deliberately small:
# these methods contain only profile/client gating, entity context, and typed
# registry lookup. Product hook dispatchers are excluded by construction.
RUNTIME_LOOKUP_METHODS = (
    "appliesTo", "isPlayer", "epoch", "enter", "exit", "currentEntity",
    "profile", "find", "playerFrom", "blockId",
)

RUNTIME_IMPORTS = '''package me.wolfii.legacyparkourcompat.mechanic;

import me.wolfii.legacyparkourcompat.api.ActiveMovementProfile;
import me.wolfii.legacyparkourcompat.api.MovementController;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import org.jspecify.annotations.Nullable;

import java.util.Optional;

/**
 * Generic client-side profile and mechanic lookup for independent changes.
 */
public final class MovementRuntime {
    private static final ThreadLocal<Entity> CURRENT_ENTITY = new ThreadLocal<>();

    private MovementRuntime() {
    }

'''


def method_body_end(source, opening):
    """Find a Java method's closing brace while ignoring comments and literals."""
    depth = 0
    state = "code"
    index = opening
    while index < len(source):
        char = source[index]
        next_char = source[index + 1] if index + 1 < len(source) else ""
        if state == "line-comment":
            if char == "\n":
                state = "code"
        elif state == "block-comment":
            if char == "*" and next_char == "/":
                state = "code"
                index += 1
        elif state in ("string", "character"):
            if char == "\\":
                index += 1
            elif (state == "string" and char == '"') or (state == "character" and char == "'"):
                state = "code"
        elif char == "/" and next_char == "/":
            state = "line-comment"
            index += 1
        elif char == "/" and next_char == "*":
            state = "block-comment"
            index += 1
        elif char == '"':
            state = "string"
        elif char == "'":
            state = "character"
        elif char == "{":
            depth += 1
        elif char == "}":
            depth -= 1
            if depth == 0:
                return index + 1
        index += 1
    return None


def extract_runtime_lookup(source):
    """Copy only curated generic lookup methods from the product runtime."""
    methods = []
    for name in RUNTIME_LOOKUP_METHODS:
        declaration = re.compile(
            rf"(?m)^[ \t]*(?:public|private)\s+static\b[^\n]*\b{re.escape(name)}\s*\("
        )
        matches = list(declaration.finditer(source))
        if not matches:
            raise ValueError(f"MovementRuntime is missing generic method: {name}")
        for match in matches:
            opening = source.find("{", match.end())
            if opening < 0:
                raise ValueError(f"MovementRuntime method has no body: {name}")
            closing = method_body_end(source, opening)
            if closing is None:
                raise ValueError(f"MovementRuntime method body is unbalanced: {name}")
            methods.append("    " + source[match.start():closing].strip())
    exported = RUNTIME_IMPORTS + "\n\n".join(methods) + "\n}\n"
    if "mechanic.hook." in exported or "Hook.class" in exported:
        raise ValueError("Generic MovementRuntime export contains a concrete hook reference")
    return exported

BUILD = '''plugins {
    id 'net.fabricmc.fabric-loom' version "${loom_version}"
}
version = project.mod_version
group = project.maven_group
repositories { mavenCentral(); maven { url = 'https://maven.fabricmc.net/' } }
loom {
    splitEnvironmentSourceSets()
    mods { legacyparkourcompat { sourceSet sourceSets.main; sourceSet sourceSets.client } }
}
dependencies {
    minecraft "com.mojang:minecraft:${project.minecraft_version}"
    implementation "net.fabricmc:fabric-loader:${project.loader_version}"
    implementation "net.fabricmc.fabric-api:fabric-api:${project.fabric_api_version}"
}
java { toolchain { languageVersion = JavaLanguageVersion.of(25) } }
tasks.withType(JavaCompile).configureEach { options.release = 25 }
processResources {
    inputs.property 'version', project.version
    filesMatching('fabric.mod.json') { expand version: project.version }
}
'''

SETTINGS = '''pluginManagement {
    repositories { maven { url = 'https://maven.fabricmc.net/' }; gradlePluginPortal(); mavenCentral() }
}
rootProject.name = 'movement-candidate'
'''

ENTRY = '''package me.wolfii.legacyparkourcompat;
import me.wolfii.legacyparkourcompat.impl.MovementControllerImpl;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public final class LegacyParkourCompat implements ModInitializer {
    public static final String MOD_ID = "legacyparkourcompat";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    @Override public void onInitialize() { MovementControllerImpl.get().initialize(); }
}
'''

CLIENT_ENTRY = '''package me.wolfii.legacyparkourcompat;
import me.wolfii.legacyparkourcompat.change.MovementChangeDiscovery;
import me.wolfii.legacyparkourcompat.impl.MovementControllerImpl;
import net.fabricmc.api.ClientModInitializer;
public final class LegacyParkourCompatClient implements ClientModInitializer {
    @Override public void onInitializeClient() {
        MovementChangeDiscovery.register(MovementControllerImpl.get().changeRegistry());
    }
}
'''

GUIDANCE = '''# Independent movement candidate

Implement only the assigned finding using the supplied exact vanilla sources.
This repository contains neutral infrastructure and no product implementation history.
Do not inspect the product checkout, its Git history, old reports, or other candidates.
Do not create further agents unless the assignment explicitly permits delegation.

Use a narrow interface per independently resolved operation, under mechanic/hook/
in a topic package and named *Hook. Implement historical arithmetic in change/v*/
classes annotated @MovementChange(emulates = ...). Client discovery automatically
registers every implemented @MechanicType interface, using the annotation's version.
Supply a public no-argument constructor or a class-local @MovementChange.Factory
method returning a nonempty List of keyed instances. Do not add a manual catalog.
Mixins remain thin and use MovementRuntime; historical injections belong in the
client list of legacyparkourcompat.mixins.json. Logical server state must remain native.
Preserve exact casts, gates, ordering and floating-point quirks. Do not implement
modern-only features historically or replace vanilla block states. Source comments
must cite both exact versions, mappings, classes and members. Omit hashes and
provenance boilerplate from Java comments; source line ranges are optional.

No movement-loop unit/mock tests. Builds only with all Gradle Test tasks disabled
and -x test. Tests or game/server launches require separate explicit authorization.
Return the candidate commit and source evidence to the integrator. Do not merge
the product repository into this independent repository; the integrator reconciles
overlapping hooks, registrations and existing changes in the product repository.
This is context isolation, not an operating-system access sandbox.
'''


def write(base, relative, text):
    target = base / relative
    target.parent.mkdir(parents=True, exist_ok=True)
    target.write_text(text, encoding="utf-8", newline="\n")


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--destination", type=Path, required=True)
    parser.add_argument("--branch", required=True)
    parser.add_argument("--finding", action="append", type=Path, required=True)
    args = parser.parse_args()
    destination = args.destination.resolve()
    if destination.exists():
        parser.error("Destination must not exist; exports never overwrite a checkout.")
    if not re.fullmatch(r"(?:fix|feat)/[a-zA-Z0-9][a-zA-Z0-9._/-]*", args.branch):
        parser.error("Use a descriptive fix/ or feat/ branch.")
    subprocess.run(["git", "check-ref-format", "--branch", args.branch], check=True, capture_output=True)
    findings = [path.resolve(strict=True) for path in args.finding]
    if any(path.suffix.lower() != ".md" for path in findings):
        parser.error("Findings must be explicit Markdown files.")
    if len({path.name for path in findings}) != len(findings):
        parser.error("Finding filenames must be unique.")
    source_files = [ROOT / name for name in ("gradlew", "gradlew.bat", "gradle.properties", "LICENSE")]
    source_files += sorted((ROOT / "gradle/wrapper").glob("*"))
    source_files.append(ROOT / CLIENT_JAVA / "change/MovementChangeDiscovery.java")
    for package in ("api", "impl"):
        source_files += sorted((ROOT / JAVA / package).glob("*.java"))
    for name in NEUTRAL_MECHANICS:
        relative = Path("mechanic") / (name + ".java")
        source_files.append(ROOT / (CLIENT_JAVA if name in ("MovementRuntime", "VanillaCall", "VanillaFn") else JAVA) / relative)
    for path in source_files:
        if not path.is_file() or path.is_symlink():
            raise ValueError(f"Missing or linked infrastructure file: {path}")
    destination.mkdir(parents=True)
    for path in source_files:
        relative = path.relative_to(ROOT)
        target = destination / relative
        target.parent.mkdir(parents=True, exist_ok=True)
        if path.suffix == ".java":
            # The version enum's old Javadoc records previous concrete findings.
            text = path.read_text(encoding="utf-8")
            if path.name == "MovementRuntime.java":
                text = extract_runtime_lookup(text)
            if path.name in ("ParkourVersion.java", "MechanicKey.java"):
                text = re.sub(r"/\*\*.*?\*/", "", text, flags=re.S)
            target.write_text(text, encoding="utf-8", newline="\n")
        else:
            shutil.copy2(path, target)
    write(destination, "build.gradle", BUILD)
    write(destination, "settings.gradle", SETTINGS)
    write(destination, ".gitignore", ".gradle/\nbuild/\nrun/\n")
    write(destination, JAVA / "LegacyParkourCompat.java", ENTRY)
    write(destination, CLIENT_JAVA / "LegacyParkourCompatClient.java", CLIENT_ENTRY)
    write(destination, "AGENTS.md", GUIDANCE)
    write(destination, "disable-tests.gradle", (ROOT / "development/disable-tests.gradle").read_text(encoding="utf-8"))
    resources = Path("src/main/resources")
    write(destination, resources / "fabric.mod.json", json.dumps({
        "schemaVersion": 1, "id": "legacyparkourcompat", "version": "${version}",
        "name": "Movement candidate", "environment": "*", "license": "MIT",
        "entrypoints": {"main": ["me.wolfii.legacyparkourcompat.LegacyParkourCompat"],
                        "client": ["me.wolfii.legacyparkourcompat.LegacyParkourCompatClient"]},
        "mixins": ["legacyparkourcompat.mixins.json"],
        "depends": {"fabricloader": ">=0.19.0", "fabric-api": "*", "minecraft": "*"},
    }, indent=2) + "\n")
    write(destination, resources / "legacyparkourcompat.mixins.json", json.dumps({
        "required": True, "package": "me.wolfii.legacyparkourcompat.mixin",
        "compatibilityLevel": "JAVA_25", "client": [], "injectors": {"defaultRequire": 1},
    }, indent=2) + "\n")
    for path in findings:
        write(destination, Path("findings") / path.name, path.read_text(encoding="utf-8"))
    base_revision = subprocess.check_output(
        ["git", "-C", str(ROOT), "rev-parse", "HEAD"], text=True
    ).strip()
    write(destination, "infrastructure.json", json.dumps({
        "productBaseRevision": base_revision,
        "note": "Exported file hashes identify the actual snapshot, including uncommitted infrastructure edits.",
        "files": {path.relative_to(destination).as_posix(): hashlib.sha256(path.read_bytes()).hexdigest()
                  for path in sorted(destination.rglob("*")) if path.is_file()},
    }, indent=2) + "\n")
    # A fresh object database ensures deleted historical patches cannot be recovered here.
    subprocess.run(["git", "init", "--initial-branch", args.branch, str(destination)], check=True)
    subprocess.run(["git", "-C", str(destination), "add", "."], check=True)
    subprocess.run(["git", "-C", str(destination), "commit", "-m", "Prepare independent movement infrastructure"], check=True)
    print(destination)


if __name__ == "__main__":
    main()
