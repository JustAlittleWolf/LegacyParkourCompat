# Fix implementation from a movement finding

## Contract

Implement **one independently scoped, source-confirmed finding** from [movement discovery](../movement-discovery/README.md) at a time. The output is a compiling change to the main Fabric mod under `src/main/`: a reusable Minecraft injection where needed, a mechanic hook, and a versioned Java implementation. Do not treat a source difference as a demonstrated trajectory. Runtime comparison belongs to the later validation workflow.

Read the root [README](../../README.md), [AGENTS.md](../../AGENTS.md), [buildSrc guide](../../buildSrc/README.md), the finding, and its discovery run manifest before editing. The manifest supplies exact releases, artifact hashes, source locations, dependency limits, and unresolved questions. A `candidate` finding or a finding whose required dependency remains blocked must be researched to source-confirmed status before implementation; do not fill gaps from memory. A discovery run can be partial if this finding's own evidence and dependencies are complete.

Required inputs: discovery run and finding ID, the intended older behavior, the repository revision, and the current build target. If the finding names only endpoints A and B, they prove a boundary somewhere in `(A, B]`; they do **not** prove the first changed release. Check intervening exact releases where needed before assigning a version boundary or claiming that the behavior applies to a whole `ParkourVersion` group. If the boundary is still uncertain, record it and stop short of registering a misleading change.

## 1. Work in isolation

Use a dedicated task branch and isolated worktree for this finding when other fix agents may work concurrently. Give each agent one finding and its own checkout; do not have two agents edit the shared `src/main/` tree. This clean baseline has no historical changes or movement mixins: treat every in-scope, source-confirmed finding as unimplemented, regardless of prior campaign dispositions. Inspect the generic version registry/resolver, mechanic types, and API in the checkout. Add the smallest hook and mixin bridge needed for this finding; do not assume a historical injection or implementation exists. An overlap between independently developed injection points is resolved during integration, after both patches can be reviewed.

Keep a short implementation record using [the template](templates/fix.md) in the task handoff or PR description. Include the source evidence, target hook, version boundary, build result, and unresolved runtime questions. Do not copy generated Minecraft sources, jars, or raw diffs into Git.

## 2. Find the injection in the **current** Minecraft release

For assignments in the active major-version campaign, follow [the campaign source handoff](major-campaign.md) first: workers must read the exact shared version/mapping directory recorded in `major-campaign.json` and verify its matching readiness marker. Do not run `decompileMinecraft`, create a worktree-local fallback, or add a junction as a worker. The task defaults resolve relative to the invoking project checkout, so a worker worktree would write a separate `decompiled_minecraft/` and `build/minecraft-decompile-cache/`. If a required shared source or marker is unavailable, stop source-dependent implementation and coordinate with the preparation owner. The command below is for the designated preparation owner or work outside that campaign.

Read the current target from `gradle.properties` (`minecraft_version`) and confirm it against the build configuration; never hardcode a release from this document. Use the repository's `decompileMinecraft` task to obtain its source, with the mapping mode appropriate to that exact release. For example, after resolving `$currentVersion` from the build configuration:

```powershell
.\gradlew.bat decompileMinecraft --versions=$currentVersion --mappings=<appropriate-mode>
```

Output is under `decompiled_minecraft/<resolved-version>/<mapping>/` and caches under `build/minecraft-decompile-cache/`. Follow [buildSrc/README.md](../../buildSrc/README.md) and the discovery workflow's source-provenance rules. Confirm that the resolved release is exactly the build target and that the source body decompiled correctly. Do not look up mappings online. Existing generated output is usable only after checking its provenance; rerun the task if it cannot be established.

Start from the finding's old and new call chains, then trace the **current** player path through callers, inherited methods, relevant block/fluid callbacks, and side-specific code. Identify the smallest stable operation, return value, field access, or method boundary that exposes the changed behavior. Check its actual method descriptor and execution order in current source. A name match alone is not enough. Prefer a shared hook that can serve later versions, but never move the injection away from the required timing just to make it look generic. Keep client-only injections in `src/client/` and shared injections in `src/main/` as appropriate.

## 3. Separate the mixin bridge from the historical patch

The mixin belongs in `mixin/` and is named for its **Minecraft location or operation** (for example `PhysicsTickMixin`), not for a historical version or one finding. Its job is to expose a narrow extension point: capture vanilla inputs/result or an original call, identify the player, ask `MovementRuntime` for the active mechanic, and dispatch. Define or extend an interface in `mechanic/hook/` for the operation and its vanilla fallback. If an existing general mixin already reaches the correct point, extend it instead of making a parallel injection. Keep version checks, block selection, and historical formulas out of the mixin.

Put the actual historical implementation in a plain Java class under `src/main/java/me/wolfii/legacyparkourcompat/change/v<emulated-version>/`, with a name describing the behavior. It implements the hook and carries `@MovementChange(emulates = ParkourVersion.<version>)`. It may use Minecraft types and code, but it must not be a mixin, contain injection annotations, or depend on a mixin class as its behavior. Accessor/invoker bridges may expose otherwise inaccessible vanilla members, but keep them mechanical and let the Java change own the behavior. Register the change through that version's `MovementChanges` provider and the `legacyparkourcompat:movement-change` entrypoint if a new provider is needed. Register a new mixin in the correct mixin config.

`emulates` names the **behavior being reproduced**, not automatically the newer endpoint of the finding. Check `ParkourVersion`, `ChangeResolver`, existing changes for the same mechanic key, and any variant selection: the closest applicable historical change must win. For example, a difference between 1.20.6 and 1.21.11 may require a change emulating `V1_20_5`, but only after verifying the boundary and other intervening versions. Add the smallest delta; do not duplicate a whole movement loop or physics class to alter one expression. Preserve vanilla block states and limit the effect to player movement and historically available content.

## 4. Match the source, tick for tick

Transcribe the relevant old-version operations exactly: literal types, casts, evaluation order, intermediate float or double rounding, `Math`/`Mth` choice, comparisons, branch and callback order, state writes, and persistence across ticks. Check helpers, attributes, tags, defaults, and server-supplied inputs used by the expression. Historical quirks are part of the behavior. If the modern hook cannot reproduce that order, move or refine the hook before shipping the patch. Keep interactions with other mechanics explicit; do not silently replace their behavior.

The mixin must invoke vanilla unchanged when emulation is disabled, the entity is not a player, or no matching hook is resolved. When the change is active, check the intended historical versions and relevant preconditions, including neighboring versions that should use a different change. Do not swallow broken movement invariants. Unit tests are suitable for version resolution or configuration, not Minecraft physics loops.

## 5. Build, hand off, then integrate

Run `./gradlew build` (`.\gradlew.bat build` on Windows) after code changes and fix compilation or test failures. Inspect the final diff for mixin scope, descriptor accuracy, registration, fallback behavior, and source fidelity. Record what the build verifies and which runtime cases still need the TAS lab. A successful build does not establish tick-level parity.

Commit the isolated finding on its branch. During integration, compare every incoming mixin and mechanic key against the others. If two changes target the same Minecraft location, merge their bridges into one general injection and dispatch through separate mechanic hooks or variants as appropriate; keep the versioned Java implementations independent. Recheck injection order and original-call behavior after this merge. Resolve duplicate providers/config entries and semantic conflicts, merge the default branch according to [AGENTS.md](../../AGENTS.md), rerun the build, and commit the integrated result. Hand the implemented finding and remaining runtime questions to the validation workflow.
