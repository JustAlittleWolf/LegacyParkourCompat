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

- `src/main/` holds the shared Fabric API, mechanic hooks, historical deltas, version resolution, network handshake, and mixins. `src/client/` has the version UI and client handshake; `src/server/` has dedicated server configuration.
- [`buildSrc/`](buildSrc/README.md) implements `decompileMinecraft` and produces historical source and mapping output.
- [`parkourgym-server/`](parkourgym-server/README.md) runs the local Paper gym, isolated test worlds, and Gym control API.
- [`tas-client/`](tas-client/README.md) launches exact-version Minecraft clients for recording and playback.
- [`testing/`](testing/README.md) coordinates persistent clients, automated runs, and comparisons.

## How historical mechanics are implemented

Each `@MovementChange(emulates = ...)` class implements one mechanic hook and is registered through the Fabric `legacyparkourcompat:movement-change` entrypoint. Selecting version *V* applies changes that emulate *V* or a later version; where a mechanic changed several times, the closest applicable delta wins. Mixins intercept general Minecraft behavior and call `MovementRuntime`, leaving current vanilla movement intact when no historical change applies.

The primary reference for exact operation order and floating-point behavior is decompiled source from the target Minecraft release. See [buildSrc](buildSrc/README.md) for the decompilation task and output layout. The [MCPK version differences](https://www.mcpk.wiki/wiki/Version_Differences) and [Minecraft Java Edition history](https://minecraft.wiki/w/Java_Edition_version_history) are secondary references.

## Testing movement across versions

For manual recording, launch an exact historical client with `gradlew runTasClient -PclientVersion=1.16.5`; see [tas-client](tas-client/README.md) for supported versions and in-game commands. For automated reference capture and comparison, see [testing](testing/README.md). The lab starts the [Parkour Gym](parkourgym-server/README.md), reuses up to five clients, and stores `.lprc` results under `tas-results/`.
