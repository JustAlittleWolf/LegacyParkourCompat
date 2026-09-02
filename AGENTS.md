# AGENTS.md

## Project Overview
A Minecraft Fabric mod (Client & Server) targeting the latest Minecraft release. It enables players to emulate historical movement mechanics and physics from previous Minecraft versions (e.g., 1.8, 1.9, 1.12) to accurately play parkour maps designed for those eras.

This is a **one-way** compatibility layer: newer clients playing older parkour. Do not invent historical behaviour for features that did not exist in the emulated version (for example, unique collision boxes on blocks added after that version). Parkour maps from that era simply do not contain those blocks.

Do **not** keep compatibility with older versions of this mod (handshake ids, config keys, packet formats, aliases, and similar may change freely).

**Out of scope — block state:** Do not emulate or rewrite block states. Defaults such as glass panes extending on all sides, fence/wall connections, and similar world data stay vanilla. Historical collision *shapes* for blocks that existed and later changed (e.g. ladder hitboxes) are movement mechanics and *are* in scope. Movement mechanics for entities other than the player are also out of scope.

### Client vs server
- **Client:** The player may choose which historical version to emulate, unless the connected server has this mod enabled. When the server has the mod, it forces its parkour version on the client.
- **Server:** Only two kinds of clients may join: clients that have this mod (always accepted, then told the server version), or clients whose Minecraft version already matches the server's parkour version (native or Via-translated). Vanilla clients on the wrong version are disconnected.

## Tech Stack & Tooling
- **Language:** Java (Modern JDK matching the targeted modern Minecraft version)
- **Mod Loader:** Fabric (Fabric Loader, Fabric API, SpongePowered Mixin)
- **Build Tool:** Gradle

## Build & Run Commands
Always execute commands from the project root:
- **Build project:** `gradlew build`
- **Unit Tests:** `gradlew test`. Unit tests are welcome for mod logic such as version resolution, `ParkourVersion` parsing, and config handling. Do **not** write unit or mock tests for Minecraft movement/physics loops; those will be covered by a headless input-simulation framework.
- **TAS client:** `gradlew runTasClient -PclientVersion=1.16.5`. Pass an exact Minecraft version (no patch remapping; `1.21.9` stays `1.21.9`). Forge before 1.14 when a loader is pinned, Fabric from 1.14, `current` for this project's Fabric run. There is no `latest` alias. `/recording start`, `/recording stop`, and `/playback <name>` write and replay `.legacyparkourrecordings` files. Right-click hold and press are recorded separately.
- **Decompile Minecraft:** `gradlew decompileMinecraft` or `gradlew decompileMinecraft --versions=1.XX.X`. Decompiles the specified minecraft versions into `decompiled_minecraft/`. Decompiles 1.8.9, 1.12.2, 1.14.4 and latest by default.
- **Parkour gym server:** `gradlew runParkourGymServer` (or `gradlew :parkourgym-server:runServer`). Downloads latest Paper 26.2 with ViaVersion, ViaBackwards, ViaRewind, PolarPaper, and Axiom, then keeps the server running. Localhost-only, offline mode, players are op. The Polar world is height `[0, 256)` with a stone spawn platform at `y=64`. Nether is disabled; players spawn in the Polar world. `/gamemode` is available; `/save` writes the Polar file.

---

## Code Structure

Fabric Loom splits environments. Common code lives in `src/main/`; client-only and dedicated-server-only code live in `src/client/` and `src/server/`. Runtime Java is under `me.wolfii.legacyparkourcompat`.

- **`src/main/`** — shared by the integrated client and dedicated server.
  - `api/` — public API: `ParkourVersion`, `MovementController`. Other mods and the UI select versions and register deltas here.
  - `mechanic/` and `mechanic/hook/` — mixin-free hook interfaces (`@MechanicType`) plus `MovementRuntime` lookup. Mixins dispatch into these hooks; change authors implement the interfaces instead of writing mixins.
  - `change/` — historical deltas. Each class implements one hook and is annotated `@MovementChange(emulates = ...)`. Register via the `legacyparkourcompat:movement-change` Fabric entrypoint. No mixins in this package.
  - `impl/` — version resolution (`ChangeResolver`) and the controller/registry. Selecting version *V* applies every change whose `emulates` is *V* or later, keeping the closest when the same mechanic changed more than once.
  - `mixin/` — thin injections into Minecraft. Keep them general and delegate to `MovementRuntime`.
  - `network/` — join handshake and optional ViaVersion lookup.
- **`src/client/`** — Mod Menu / version screen, client handshake receivers. The client keeps the typed version in memory only; closing the game returns to vanilla movement.
- **`src/server/`** — dedicated-server config loaded from `config/legacyparkourcompat.properties`.
- **`buildSrc/`** — Gradle task that decompiles historical Minecraft clients into `decompiled_minecraft/` (gitignored). Not game logic.

---

## Core Architecture & Guidelines

### 1. Delta-Based Mechanic Layering (No Duplication)
- **Strictly No Duplication:** Never copy-paste entire movement loops or physics classes between versions, unless there is a change in vanilla behavior that requires it.
- **Granular Mechanic Deltas:** Every individual historical change to a mechanic must be isolated as an independent, minimal patch/delta (e.g., `mechanic.step_height`, `mechanic.slime_bounce`, `mechanic.ladder_climb`).
- **Version Resolution / Override Pattern:**
    - Version mechanics resolve hierarchically.
    - *Example:* If mechanic `A.A` changed in 1.9 (Change A) and 1.11 (Change C), and mechanic `A.B` changed in 1.10 (Change B):
        - When a player selects version 1.8: `A.A` applies Change A (overriding Change C), while `A.B` applies Change B.
    - Implement mechanics as composable feature flags / strategies that the active version profile configures dynamically.

### 2. Math & Physics Parity (Zero Drift)
- **Exact Historical Parity:** The goal is 100% deterministic tick-level parity with historical versions.
- **Floating-Point Precision:** Respect exact order of operations, casting, and floating-point logic from historical decompiled code. Do not optimize, simplify, or refactor mathematical expressions if it risks altering floating-point outcomes.
- **Source of Truth:**
    1. **Primary Ground Truth:** Decompiled historical Minecraft source code for the respective version.
    2. **Secondary References:** [MCPK Wiki (Version Differences)](https://www.mcpk.wiki/wiki/Version_Differences) and [Minecraft Wiki Java Edition Version History](https://minecraft.wiki/w/Java_Edition_version_history).

### 3. Mixin Strategy: Generalized Injections
- **Inject High, Filter in Mod Logic:** Keep Mixins as broad/general as possible at the injection point.
    - *Bad:* Creating dedicated mixins targeting `SlimeBlock`, `HoneyBlock`, `SoulSandBlock`, etc.
    - *Good:* Mix into the base class/hook (e.g., `Block#stepOn` or `Entity#move`), intercept the call, and delegate execution to the mod's active version handler to decide the behavior for that specific block.
    - *Ok:* Mix into `AbstractBoat#getPassengerAttachmentPoint` if a change is relevant for that specific part of the code, and targeting a more general hook would result in lots of code duplication.

### 4. Logging
- Never ignore errors (no empty `catch`, no swallowed failures).
- Conditions that must not happen (broken invariants, unexpected nulls on movement paths) should crash the game.
- Expected failures (unreadable config, unknown version id, join rejected for mismatch) should be logged with `LegacyParkourCompat.LOGGER` and handled.

---

## Agent Boundaries & Guardrails

- **ALWAYS:**
    - Verify that new mechanic changes apply *only* when the corresponding historical version/toggle is active, leaving default modern behavior intact when disabled.
    - Check that the project builds cleanly using `gradlew build build` after any code modifications.

- **ASK FIRST:**
    - Introducing heavy third-party Java libraries or external physics engines.
    - Refactoring the core version-resolution pipeline.

- **NEVER:**
    - Write unit or mock tests for Minecraft movement/physics loops (a headless input-simulation framework is planned). Unit tests for versioning logic and other mod code are fine.
    - Create duplicate version files containing redundant vanilla code.
    - "Fix" or smooth out historical Minecraft bugs/quirks that affect movement (they are considered intentional parkour features in older versions).
    - Add historical behaviour for modern-only features (new blocks with unique collision, and similar additions that old parkour maps do not use).
    - Preserve compatibility with older versions of this mod.
    - Look up Minecraft mappings online. Use the decompileMinecraft task instead.
