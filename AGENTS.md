# Agent guidance

Read [README.md](README.md) for project behavior and commands. The module guides are in [buildSrc](buildSrc/README.md), [parkourgym-server](parkourgym-server/README.md), [tas-client](tas-client/README.md), and [testing](testing/README.md).

## Project guidelines

- This is a one-way compatibility layer: newer clients emulate old player movement to play maps from that era. Do not invent historical behavior for features those maps could not contain.
- Block states stay vanilla. Historical collision *shapes* of blocks that existed in the emulated version are movement mechanics and are in scope. Movement of non-player entities is out of scope.
- Implement each historical mechanic change as an independent, minimal delta. For a selected version, `ChangeResolver` applies changes from that version or later and chooses the closest change for each mechanic. Do not duplicate whole movement loops or physics classes between versions.
- Keep each mechanic hook tied to one independently resolved technical operation. A change may implement several narrow hook interfaces when one historical behavior affects those operations, but each interface remains separately registered and resolved. Implement version changes independently; do not inherit one release's behavior from another release's change class. Share only neutral helpers for operations that are truly identical, and prefer vanilla accessors/invokers when they preserve the historical operation's exact math and gates. Never call modern behavior that differs from the historical source.
- Match historical math tick for tick. Preserve floating-point operation order, casts, and quirks even when a simpler expression looks equivalent. Decompiled source from the exact Minecraft version is the primary reference; the MCPK and Minecraft wikis are secondary references.
- Keep mixins thin and general: inject at a shared Minecraft hook where practical, then let `MovementRuntime` and the mechanic hook decide the block or situation. Put historical changes in `change/`, not in dedicated block mixins.
- Never swallow errors. Crash on broken invariants or unexpected nulls on movement paths. Log expected failures such as unreadable config, unknown version IDs, and rejected joins with `LegacyParkourCompat.LOGGER` and handle them.

## Movement discovery and implementation campaigns

- For the 1.8.9 through native 26.2 discovery campaign, follow `workflows/movement-discovery/README.md`. Discover from exact-version vanilla sources without consulting old mod implementation until the blind discovery record is frozen. Prior source-discovery reports may be used as navigation, not evidence of current coverage. Normal source workers must not browse the wikis or read isolated wiki-audit findings before freeze; the release-taxonomy owner uses wiki material only to choose boundaries.
- Discovery is complete only when every exact source slice in the per-tick call graph and required producer/consumer inventories has evidence-backed disposition, all dependencies are closed, and an independent reviewer has audited the inventory and routed any missed slices. A completed worker branch, populated catalog, successful build, or narrow unchanged travel comparison is not proof of discovery coverage.
- Track checked method/body ranges and producer-to-consumer dependencies. Split large methods into bounded named behaviors. Explicitly inventory input/tick ordering; pre-travel and post-travel; pose, dimensions and eye-height state writers; collision shapes including registrations and neighboring-block providers; movement attributes, effects, enchantments and equipment; and external movement influences. Pending, in-progress, or blocked slices prevent discovery `complete`.
- Keep discovery coverage, implementation coverage, and runtime validation as separate statuses. After blind discovery is frozen, reconcile it against existing code/catalogs and account for every discovered finding as implemented, intentionally excluded, or still open. Do not modify mechanics from a discovery-only assignment.
- The campaign excludes health, regeneration, hunger, food, saturation, exhaustion, and damage/combat simulations. Preserve vanilla health/food systems. A movement predicate may read vanilla state, but do not emulate the non-movement system that produces it. Player-only movement remains in scope; non-player movement does not.
- Do not run tests, game clients, TAS runs, servers, or Docker for campaign documentation/source discovery or implementation unless the campaign coordinator explicitly authorizes it. Static checks and builds that explicitly exclude tests remain allowed.
- Keep worker assignments and shared decompilation/build preparation disjoint. The source owner controls shared artifact preparation and publishes exact version/mapping paths plus readiness; version workers consume those artifacts read-only. Do not run a competing decompile/build in a worker checkout or overlap source-tree writers.

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
- `src/main/change/`: granular historical deltas, independently implementing their mechanic hooks and statically registered by explicit per-version methods in the single `MovementChangeCatalog`. Register a multi-hook change under each interface it implements; do not add per-version providers or movement entrypoints.
- `src/main/impl/`: registry and version resolution; for each mechanic, the closest applicable historical change wins.
- `src/main/mixin/`: injections that dispatch to hooks. `src/main/network/`: join handshake and optional ViaVersion lookup.
- `src/client/`: version UI and client handshake. `src/server/`: dedicated server config.
- `buildSrc/`: historical source decompilation task. `parkourgym-server/`: Paper test server. `tas-client/`: historical recording and playback clients. `testing/`: persistent lab and run coordinator.
