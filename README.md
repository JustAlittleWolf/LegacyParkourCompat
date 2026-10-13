# Legacy Parkour Compat

A Fabric mod for playing old parkour maps on a modern Minecraft client. A fixed historical profile must reproduce the original client player's position and movement state after every tick, including historical bugs, casts, floating-point rounding and operation order.

## Scope and client/server roles

Historical movement runs only on the logical client. This includes historical collision shapes for blocks that already existed, block effects, player responses to pistons and other events, and movement rules that read vanilla hunger, effects or equipment. Block states remain vanilla. Features introduced after the selected profile retain modern behavior; introduction and later changes to an existing feature are different boundaries.

Rendering, input-device plumbing, non-player/vehicle physics, hunger production, healing, damage calculation and bed-entry eligibility are outside scope. A direct player movement response can remain in scope even when the event producing it is not. Profile switching is undefined behavior; parity targets fixed profiles.

The client selects a profile in its UI. A dedicated server can require a profile through `config/legacyparkourcompat.properties` and the mod handshake. Matching native/Via-translated clients remain admissible under the existing policy. Historical physics never runs on the server, including an integrated server. For a connection that acknowledged the required historical profile, the server relaxes player speed, collision correction and floating checks that would reject that movement. Other connections and vehicle checks retain vanilla behavior.

## Repository and commands

- `src/`: product mod, client UI, neutral version resolution and server handshake/check policy.
- [development/](development/README.md): TAS recorder, Parkour Gym, test coordinator and authoring utilities.
- [buildSrc/](buildSrc/README.md): Gradle build logic and the exact-version decompiler. Gradle requires this directory at the root.
- [development/workflows/](development/workflows/README.md): the four current working procedures, without campaign results.
- [development/research/](development/research/README.md): findings, central progress and preserved historical reports.

Use the Gradle wrapper and Java 25. Build without executing tests:

```powershell
.\gradlew.bat build -x test --init-script development/disable-tests.gradle
```

On Linux/macOS use `./gradlew` with the same arguments. `build` compiles the mod and development modules; it does not establish movement parity. Tests and any game, TAS, Gym or server launch require separate authorization. Module guides document the launch commands for that later phase.

## Movement architecture

Keep each independent delta in a `change/v<version>/` class annotated `@MovementChange(emulates = ...)`. Changes may implement several independently resolved hook interfaces. Register each interface in the single static `MovementChangeCatalog`; no per-version providers, dynamic Fabric metadata registry, cross-release inheritance or duplicated movement loops.

`ChangeResolver` selects the closest `emulates` version greater than or equal to the profile separately for each operation. If an operation has changes for 1.8 and 1.10, the 1.9 profile uses the 1.10 change. A modern operation needs no historical change.

Thin client mixins dispatch through `MovementRuntime`; hook interfaces live in topic packages under `mechanic/hook/` and use the `Hook` suffix. Native movement, non-player entities and logical servers retain vanilla behavior. Share neutral helpers only when their historical behavior is identical. Existing Changes are retained during independent discovery and reconciled by the integrator against source-confirmed findings.

Exact-version decompiled source is primary evidence. The [decompiler guide](buildSrc/README.md) explains the shared source directory in the primary local checkout, pinned inputs and deliberate regeneration. Researchers consume those sources read-only. Source comments on new or revised changes cite both compared releases and mappings. Wiki entries are leads to verify in source, not sufficient implementation evidence.

## Research and verification

Read the [orchestration workflow](development/workflows/orchestration/README.md) before coordinating work and the relevant discovery, implementation or testing procedure before that phase. Initial research targets the latest bugfix release of each older release line, the relevant 1.21 sub-lines and the latest bugfix of each 26.x line through the current client target. All stable releases remain the long-term goal; endpoint comparisons do not establish every intermediate patch boundary.

Discovery acceptance, integration/build success and runtime parity are separate statuses in the central research index. Historical reports survive in the archive; format conversion alone does not renew their acceptance or prove implementation coverage. Automated recording/playback verification will be elaborated after manual expert assessment: first reproduce recordings exactly in the same native version, then compare native historical references with the modern client profile tick by tick.
