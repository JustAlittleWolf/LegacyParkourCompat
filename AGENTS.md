# AGENTS.md

## Project Overview
A Minecraft Fabric mod (Client & Server) targeting the latest Minecraft release. It enables players to emulate historical movement mechanics and physics from previous Minecraft versions (e.g., 1.8, 1.9, 1.12) to accurately play parkour maps designed for those eras.

## Tech Stack & Tooling
- **Language:** Java (Modern JDK matching the targeted modern Minecraft version)
- **Mod Loader:** Fabric (Fabric Loader, Fabric API, SpongePowered Mixin)
- **Build Tool:** Gradle (wrapped via npm runner)

## Build & Run Commands
Always execute commands from the project root:
- **Build project:** `npm run build`
- **Unit Tests:** *Do not write standard unit tests.* Testing will be handled via headless input-simulation runs.

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
---

## Agent Boundaries & Guardrails

- **ALWAYS:**
    - Verify that new mechanic changes apply *only* when the corresponding historical version/toggle is active, leaving default modern behavior intact when disabled.
    - Check that the project builds cleanly using `npm run build` after any code modifications.

- **ASK FIRST:**
    - Introducing heavy third-party Java libraries or external physics engines.
    - Refactoring the core version-resolution pipeline.

- **NEVER:**
    - Create conventional unit tests or mock tests for game physics loops (headless integration test framework is planned).
    - Create duplicate version files containing redundant vanilla code.
    - "Fix" or smooth out historical Minecraft bugs/quirks that affect movement (they are considered intentional parkour features in older versions).
