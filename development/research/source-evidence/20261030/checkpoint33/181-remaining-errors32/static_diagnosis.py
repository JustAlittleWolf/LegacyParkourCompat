from __future__ import annotations

import hashlib
import pathlib
import re
import subprocess
import zipfile

ROOT = pathlib.Path(__file__).resolve().parents[3]
EVIDENCE = ROOT / ".native-source/20261030/181-enum-helper-allowlist/evidence.md"
LOG = ROOT / ".native-artifacts/decompile-181-proof33.log"
JAR = pathlib.Path(r"D:\Javastuff\LegacyParkourCompat\.minecraft-decompile-cache\1.8.1\client-ornithe-feather.jar")
VF_SOURCES = pathlib.Path(r"C:\Users\Wolfi\.gradle\caches\modules-2\files-2.1\org.vineflower\vineflower\1.12.0\f58b7657cf95301c390b21ccec3a43d824f15409\vineflower-1.12.0-sources.jar")


def sha256(path: pathlib.Path) -> str:
    h = hashlib.sha256()
    with path.open("rb") as stream:
        for block in iter(lambda: stream.read(1024 * 1024), b""):
            h.update(block)
    return h.hexdigest()


def main() -> None:
    helpers = []
    for line in EVIDENCE.read_text(encoding="utf-8").splitlines():
        if line.startswith("| `") and line.count("|") >= 5:
            helpers.append(line.split("`", 2)[1].replace("/", "."))

    errors = [line for line in LOG.read_text(encoding="utf-8", errors="replace").splitlines()
              if "[Vineflower] Unable to simplify switch on enum:" in line]
    empty_maps = [line for line in errors if "available: {}" in line]
    print(f"helper_count={len(helpers)}")
    print(f"proof33_switch_errors={len(errors)} empty_mapping_errors={len(empty_maps)}")
    print(f"mapped_jar_sha256={sha256(JAR)}")
    print(f"proof33_log_sha256={sha256(LOG)}")
    print(f"vineflower_sources_sha256={sha256(VF_SOURCES)}")

    failures = []
    for name in helpers:
        output = subprocess.run(["javap", "-p", "-v", "-classpath", str(JAR), name],
                                check=True, capture_output=True, text=True).stdout
        class_flags = re.search(r"^  flags: \(0x([0-9a-fA-F]+)\)", output, re.M)
        header = re.search(r"^  interfaces: (\d+), fields: (\d+), methods: (\d+), attributes: (\d+)$", output, re.M)
        if class_flags is None or header is None:
            failures.append((name, "class-header-parse"))
            continue
        field_count = int(header.group(2))
        method_count = int(header.group(3))
        field_area = output.split("\n{", 1)[1].split("\n  static {};", 1)[0]
        fields = re.findall(r"^    descriptor: ([^\n]+)\n    flags: \(0x([0-9a-fA-F]+)\)", field_area, re.M)
        synthetic_class = bool(int(class_flags.group(1), 16) & 0x1000)
        valid_fields = len(fields) == field_count and all(
            descriptor in ("[I", "Z") and (int(flags, 16) & 0x1018) == 0x1018
            for descriptor, flags in fields
        )
        has_single_clinit = "\n  static {};" in output and method_count == 1
        if not (synthetic_class and valid_fields and has_single_clinit):
            failures.append((name, f"synthetic={synthetic_class}, fields={fields}, methods={method_count}"))
    print(f"shouldInitEarly_candidates={len(helpers) - len(failures)}/{len(helpers)}")
    for item in failures:
        print("PREDICATE_FAIL", *item)

    source_name = "org/jetbrains/java/decompiler/modules/decompiler/SwitchHelper.java"
    with zipfile.ZipFile(VF_SOURCES) as sources:
        switch_helper = sources.read(source_name).decode("utf-8")
        class_processor = sources.read("org/jetbrains/java/decompiler/main/ClassesProcessor.java").decode("utf-8")
    for label, source, term in [
        ("SwitchHelper.isEnumArray", switch_helper, "private static boolean isEnumArray"),
        ("SwitchHelper.mapping", switch_helper, "Map<Exprent, Exprent> mapping"),
        ("ClassesProcessor.shouldInitEarly", class_processor, "private static boolean shouldInitEarly"),
    ]:
        line = source[:source.index(term)].count("\n") + 1
        print(f"{label}_source_line={line}")


if __name__ == "__main__":
    main()
