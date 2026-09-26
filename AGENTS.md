# Agent guidance

Read [README.md](README.md) for project behavior and commands. The module guides are in [buildSrc](buildSrc/README.md), [parkourgym-server](parkourgym-server/README.md), [tas-client](tas-client/README.md), and [testing](testing/README.md).

## Scope and invariants

- This is a one-way compatibility layer for modern players on old parkour maps. Emulate player movement from the selected historical version. Leave modern-only blocks, block states, and other entities alone.
- Do not preserve compatibility with old releases of this mod. Historical Minecraft bugs that affect movement are part of the target behavior.
- Each historical mechanic change belongs in a small, independent delta. Do not copy whole movement loops or create redundant version classes.
- Match decompiled historical Minecraft math exactly, including operation order, casts, and floating-point behavior. Use `gradlew decompileMinecraft` for source and mappings; do not look up mappings online.
- Keep mixins thin and general. Delegate decisions to `MovementRuntime` and mechanic hooks. Log expected failures with `LegacyParkourCompat.LOGGER`; fail on broken invariants.
- Verify changes are active only for their historical version and modern movement remains unchanged when disabled. Run `gradlew build` after code changes. Do not write unit or mock tests for Minecraft movement loops; use unit tests for versioning and other non-physics logic.
- Ask before adding a heavy Java library or external physics engine, or refactoring the core version-resolution pipeline.

## Code structure

Fabric Loom separates `src/main/` (shared), `src/client/` (client only), and `src/server/` (dedicated server). Runtime packages are under `me.wolfii.legacyparkourcompat`.

- `src/main/api/`: public version and movement controller API.
- `src/main/mechanic/` and `mechanic/hook/`: hook interfaces and runtime lookup.
- `src/main/change/`: granular historical deltas, registered through the `legacyparkourcompat:movement-change` entrypoint.
- `src/main/impl/`: registry and version resolution; for each mechanic, the closest applicable historical change wins.
- `src/main/mixin/`: injections that dispatch to hooks. `src/main/network/`: join handshake and optional ViaVersion lookup.
- `src/client/`: version UI and client handshake. `src/server/`: dedicated server config.
- `buildSrc/`: historical source decompilation task. `parkourgym-server/`: Paper test server. `tas-client/`: historical recording and playback clients. `testing/`: persistent lab and run coordinator.
