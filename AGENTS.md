# Agent guidance

Read [README.md](README.md) for project behavior and commands. The module guides are in [buildSrc](buildSrc/README.md), [parkourgym-server](parkourgym-server/README.md), [tas-client](tas-client/README.md), and [testing](testing/README.md).

## Project guidelines

- This is a one-way compatibility layer: newer clients emulate old player movement to play maps from that era. Do not invent historical behavior for features those maps could not contain.
- Block states stay vanilla. Historical collision *shapes* of blocks that existed in the emulated version are movement mechanics and are in scope. Movement of non-player entities is out of scope.
- Implement each historical mechanic change as an independent, minimal delta. For a selected version, `ChangeResolver` applies changes from that version or later and chooses the closest change for each mechanic. Do not duplicate whole movement loops or physics classes between versions.
- Match historical math tick for tick. Preserve floating-point operation order, casts, and quirks even when a simpler expression looks equivalent. Decompiled source from the exact Minecraft version is the primary reference; the MCPK and Minecraft wikis are secondary references.
- Keep mixins thin and general: inject at a shared Minecraft hook where practical, then let `MovementRuntime` and the mechanic hook decide the block or situation. Put historical changes in `change/`, not in dedicated block mixins.
- Never swallow errors. Crash on broken invariants or unexpected nulls on movement paths. Log expected failures such as unreadable config, unknown version IDs, and rejected joins with `LegacyParkourCompat.LOGGER` and handle them.

## Do

- Verify a new mechanic applies only when its historical version or toggle is active; default modern behavior must remain intact when disabled.
- Run `gradlew build` after code changes. Unit tests are appropriate for version resolution, parsing, and config handling.
- Use `gradlew decompileMinecraft` for historical source and mappings. See [buildSrc/README.md](buildSrc/README.md) for options.

## Ask first

- Before adding heavy third-party Java libraries or external physics engines.
- Before refactoring the core version-resolution pipeline.

## Don't

- Write unit or mock tests for Minecraft movement and physics loops; those belong in the headless input-simulation framework.
- Create duplicate version files containing redundant vanilla code.
- Fix or smooth out historical movement bugs; they are intentional parkour behavior for the emulated version.
- Add historical behavior for modern-only features, such as blocks that did not exist in the target era.
- Preserve compatibility with older releases of this mod, including handshake IDs, config keys, packet formats, or aliases.
- Look up Minecraft mappings online; use the decompilation task.

## Code structure

Fabric Loom separates `src/main/` (shared), `src/client/` (client only), and `src/server/` (dedicated server). Runtime packages are under `me.wolfii.legacyparkourcompat`.

- `src/main/api/`: public version and movement controller API.
- `src/main/mechanic/` and `mechanic/hook/`: hook interfaces and runtime lookup.
- `src/main/change/`: granular historical deltas, registered through the `legacyparkourcompat:movement-change` entrypoint.
- `src/main/impl/`: registry and version resolution; for each mechanic, the closest applicable historical change wins.
- `src/main/mixin/`: injections that dispatch to hooks. `src/main/network/`: join handshake and optional ViaVersion lookup.
- `src/client/`: version UI and client handshake. `src/server/`: dedicated server config.
- `buildSrc/`: historical source decompilation task. `parkourgym-server/`: Paper test server. `tas-client/`: historical recording and playback clients. `testing/`: persistent lab and run coordinator.
