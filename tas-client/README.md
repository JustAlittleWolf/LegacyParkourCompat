# TAS recording client

This included Gradle build runs exact historical Minecraft clients with a recording and playback mod. Launch one manually from the repository root:

```powershell
.\gradlew.bat runTasClient -PclientVersion=1.16.5
```

An exact version stays exact: `1.21.9` does not become another patch release. Pinned versions before 1.14 use Forge (for example `1.8.9` or `1.8.9-forge`); 1.14 through 1.21.x use Fabric through Unimined. `current` runs this repository's Loom client with both the recorder and Legacy Parkour Compat. `gradlew runClient` does the same for the current client. Select the movement profile in the mod UI before playback on `current`. `latest` is not a client alias, and unsupported releases fail the task.

Each historical client has a separate `tas-client/run/<version>/` game directory, including its worlds and `options.txt`. This prevents newer save data from crashing older clients. The current Loom client uses the root run directory. Clients are unsigned in; the mod keeps Multiplayer available so they can join the offline [Parkour Gym](../parkourgym-server/README.md). The launch task sets a 3 GB maximum heap and writes low graphics settings, including two-chunk view distance and 50 FPS, before startup.

## Recording and playback

Enter commands in game chat (the leading `.` is part of the command):

```text
.recording start [name]
.recording stop [name]
.playback <name>
.playback stop
```

Recordings are `.lprc` files in `.legacyparkourrecordings` below that client's game directory. They store the starting pose, per-tick input and facing, and observed positions. Playback teleports to the start pose, applies input and facing each tick, and does not force the stored positions. Right-click hold and press are separate inputs, so drawing a bow and a single-use action can be replayed distinctly.

To make a recording usable on selected test surfaces or at named starts:

```text
.recording start reusable block <name>
.recording start reusable positions <name>
.recording stop
.recording position <recording> <positionName>
```

The two `start` forms are alternatives. Reusable captures trim leading and trailing ticks without position change; ordinary captures preserve their exact timeline. A positions recording saves its original start as `origin`. After stopping, stand at another permitted start and use `.recording position` to add it. The recording keeps the same input ticks and facing for each placement. The [testing lab](../testing/README.md) explains how a caller chooses a block pad, named start, and offsets.

The recorder automatically saves Survival or Creative mode and Creative flying state. If you are wearing gear or have an effect while recording, annotate that setup so a test run can reconstruct it:

```text
.recording equipment swiftSneak 2
.recording equipment soulSpeed 3
.recording equipment depthStrider 1
.recording effect minecraft:speed 1 1200
.recording effects clear
```

The effect amplifier is zero-based and duration is in ticks. A run may override saved equipment and effects, but cannot override the recorded game mode or flying state. The per-tick input stream remains portable between supported versions; a run rejects setup a target client cannot represent.

## Other input and code layout

`.playback` also accepts Legacy Parkour/Combat TAS JSON format version 1 by filename or absolute path. JSON row yaw adds to the previous yaw; row pitch is absolute. Missing angles retain their previous value. Playback uses starting position and velocity, keys, and angles, while ignoring editor metadata such as `yawLocked` and `angleSolver`. Unsupported teleports, potion amplifiers, nonzero hotbar slots, and unknown keys fail explicitly. JSON input is read only; captured output is `.lprc`. Replay in the world for which the input was recorded.

Shared recording types, file parsing, and position comparison live in `core/` (`.\gradlew.bat -p tas-client :core:test`). `src/common/` contains the controller and worker connection. `src/forge/`, `src/forge113/`, and `src/fabric/` contain loader entry points; `src/mixins/` is split by Minecraft era because class names and hooks differ. Automated runs and cross-version comparison belong to the [testing lab](../testing/README.md).
