# TAS client

`./gradlew runTasClient -PclientVersion=1.16.5` launches that exact Minecraft version with the recording mod. Versions are not remapped to a later patch; `1.21.9` stays `1.21.9`. Unsupported versions fail the Gradle task.

- Versions before 1.14 use Forge when a loader is pinned (for example `1.8.9` or `1.8.9-forge`). `1.8` is rejected.
- 1.14 through 1.21.x use Fabric through Unimined (for example `1.16.5` or `1.21.9-fabric`).
- `current` uses this repository's Fabric Loom client (`minecraft_version` in `gradle.properties`). There is no `latest` alias.

In-game (simulation keys and facing, not the camera):

- `.recording start [name]`
- `.recording stop`
- `.playback <name>`
- `.playback stop`

Right-click is stored as both hold and press: holding use (bow draw) is not the same as a click this tick (pearl / place).

Each Unimined version uses its own game directory, `tas-client/run/<minecraft version>` (for example `tas-client/run/1.8.9`). Worlds and `options.txt` are not shared: a save written by a newer client makes 1.8 crash while reading chunk NBT. `current` still uses the repository Loom run directory.

Recordings are `.lprc` files in `.legacyparkourrecordings` under that game directory. The on-disk layout is the same on every version. Playback teleports to the start pose, then applies recorded keys and facing each tick; stored positions are for later comparison and are not replayed.

TAS clients are not signed in. From 1.16 onward vanilla greys out Multiplayer for that reason; this mod keeps the button enabled so you can still join the offline parkour gym.

Shared recording types live in the `core` subproject (`./gradlew -p tas-client :core:test`). Mixins are selected per era (`forge`, Fabric 1.14–1.16, Fabric 1.17–1.20.4, Fabric 1.20.5+ / current) rather than one config for every class name.

## Automated reference workflow

For a deterministic comparison, pass one recording file and a comma-separated
version list to the root Gradle task:

```text
gradlew runTasWorkflow -PtasRecording=recordings\jump.lprc -PtasVersions=1.8.9,1.12.2,1.16.5,current
```

The task starts the localhost gym on `127.0.0.1:25565` when it is not already
running, launches one isolated client per version, joins the gym, replays the
recording, and waits for the client to exit after the final tick. Use
`-PtasStartGym=false` when the gym is already managed separately, and
`-PtasServer=host:port` for another local endpoint. Results are published below
`tas-results/` as `<recording-name>-<version>.lprc`; a
`<recording-name>-manifest.tsv` lists every result. Each result uses the normal
`.lprc` format: source buttons/yaw/pitch plus the observed post-movement
position for every tick, so it can be replayed or compared by the existing
tools. The client writes a temporary `.lprc.tmp` first and the workflow moves it
into place only after a successful run.

The same automation can be enabled for one client without the workflow by
passing JVM properties (Gradle `-PtasRecording`/`-PtasOutput` are translated for
you):

```text
gradlew runTasClient -PclientVersion=1.12.2 -PtasRecording=C:\\runs\\input.lprc -PtasOutput=C:\\runs\\1.12.2.lprc -PtasServer=localhost:25565
```

Automated clients mute audio, connect to the gym, and show chat feedback for
recording/playback start and completion. Manual commands remain
`.recording start [name]`, `.recording stop`, `.playback <name>`, and
`.playback stop`.

To compare a reference against the Legacy Parkour movement emulation in the
repository's current client, use an expected `.lprc` from the gym and choose a
parkour version explicitly:

```text
gradlew runTasCompare -PtasExpected=tas-results\jump-1.12.lprc -PtasVersion=1.12 -PtasCompare=true -PtasOutput=tas-results\current-1.12.lprc
```

`tasExpected` must name the version-specific reference file produced or chosen
for the selected movement profile; it is deliberately separate from the
cross-version source input accepted by `runTasWorkflow`. This starts the newest
repository client, selects the requested movement profile, and compares each
post-movement position with that expected recording.
At the first tick outside `-PtasTolerance` (default `0.0001` on each
coordinate), it sends `!lpcf <runId> <version> <tick>` as normal chat while
the player is still connected. The gym writes a `[LPC_FAILURE_SNAPSHOT]` JSON
line containing the server-side world, position, velocity, bounding box, and
nearby blocks; the message itself is canceled and never broadcast. A
cross-version `runTasWorkflow` capture does not compare against source XYZ,
because movement is expected to differ across versions.

The Forge 1.12.2 MCP client uses `Minecraft.gameDir` for its run directory;
the TAS reflection layer includes that name (alongside modern
`gameDirectory`). This prevents the historical `.playback` crash before a
recording file is opened. If an automated run observes its first positional
deviation, it reports a reserved normal-chat `!lpcf <run> <version> <tick>`
signal (maximum 64 ASCII characters) to
the connected gym (the server records the surrounding-world snapshot).
