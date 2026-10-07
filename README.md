# Legacy Parkour Compat

Legacy Parkour Compat is a Fabric client and server mod for playing parkour maps with the player movement of older Minecraft versions. A modern client can select a historical movement profile; a server with the mod can require one profile for everyone. The goal is tick-level parity with historical Minecraft movement, including quirks that matter to parkour.

This is one-way compatibility for old maps on newer clients. It does not rewrite block states, invent historical behavior for blocks added later, or change non-player entities.

## Getting started

Use a JDK supported by the current Minecraft target and the Gradle wrapper. Run commands from the repository root. On Windows, use `gradlew.bat` (or `.\gradlew.bat` in PowerShell).

```powershell
.\gradlew.bat build
.\gradlew.bat runClient
```

The client can choose a historical version in the mod UI. When connected to a server running this mod, the server's version takes precedence. A dedicated server accepts modded clients and vanilla or Via-translated clients whose Minecraft version already matches its configured parkour version; it disconnects vanilla clients on a different version. The server reads `config/legacyparkourcompat.properties`. Closing the client discards its local selection.

`gradlew test` runs unit tests. Movement and physics loops are verified through the TAS lab rather than unit or mock tests.

## Repository guide

- `src/main/` holds the shared Fabric API, generic mechanic/version resolution, network handshake, and server/client-independent configuration. `src/client/` has the version UI and client handshake; `src/server/` has dedicated server configuration.
- [`buildSrc/`](buildSrc/README.md) implements `decompileMinecraft` and produces historical source and mapping output.
- [`parkourgym-server/`](parkourgym-server/README.md) runs the local Paper gym, isolated test worlds, and Gym control API.
- [`tas-client/`](tas-client/README.md) launches exact-version Minecraft clients for recording and playback.
- [`testing/`](testing/README.md) coordinates persistent clients, automated runs, and comparisons.
- [`workflows/`](workflows/README.md) documents source-based movement difference discovery before implementation and runtime validation.

## Historical movement implementation model

The retained framework supports `@MovementChange(emulates = ...)` classes registered through the Fabric `legacyparkourcompat:movement-change` entrypoint. In a populated implementation, selecting version *V* applies changes that emulate *V* or a later version; where a mechanic changed several times, the closest applicable delta wins. General mixins can dispatch those changes while preserving the vanilla path when emulation is inactive.

Each mechanic hook represents one technical operation, such as a boat acceleration coefficient, a jump gate, or a fall-distance reset at a particular point in the tick. Singleton hooks are functional interfaces; block hooks additionally expose their block identifier as registration metadata. Mixins supply context and dispatch the operation, while historical applicability and arithmetic belong to the change implementation. Do not use the presence of one hook to control a different operation.

Changes live in the package matching their `emulates` annotation, for example `change.v1_8.BoatRiderInput`, with no version suffix in the class name. Each version package has a `MovementChanges` provider listed in `fabric.mod.json`. A change can implement several narrow interfaces and register once; all of its hooks then share the same object, including any state, and resolve independently. Keep unrelated deltas in separate classes. Remove unused hooks and methods instead of keeping placeholders for operations that have no mixin dispatch.

The fresh major-version campaign integrates fifteen source-based handoffs on the clean baseline, with shared movement mixins and independently selected historical deltas. Historical profiles remain partial: compilation does not prove runtime movement parity, and minor-version discovery and pending coverage slices remain open. See [the final integration record](workflows/fix-implementation/final-integration.md) for retained findings, exclusions and validation limits. Previous implementation coverage claims remain invalidated.

The primary reference for exact operation order and floating-point behavior is decompiled source from the target Minecraft release. See [buildSrc](buildSrc/README.md) for the decompilation task and output layout. The [MCPK version differences](https://www.mcpk.wiki/wiki/Version_Differences) and [Minecraft Java Edition history](https://minecraft.wiki/w/Java_Edition_version_history) are secondary references.

## Testing movement across versions

For manual recording, launch an exact historical client with `gradlew runTasClient -PclientVersion=1.16.5`; see [tas-client](tas-client/README.md) for supported versions and in-game commands. For automated reference capture and comparison, see [testing](testing/README.md). The lab starts the [Parkour Gym](parkourgym-server/README.md), reuses up to five clients, and stores `.lprc` results under `tas-results/`.
