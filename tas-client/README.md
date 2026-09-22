# TAS client

`./gradlew runTasClient --project-prop "clientVersion=1.16.5"` launches that exact Minecraft version with the recording mod. Versions are not remapped to a later patch; `1.21.9` stays `1.21.9`. Unsupported versions fail the Gradle task.

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
gradlew runTasWorkflow --project-prop "tasRecording=recordings\jump.lprc" --project-prop "tasVersions=1.8.9,1.12.2,1.16.5,current"
```

The task starts the localhost gym on `127.0.0.1:25565` when it is not already
running, launches one isolated client per version, joins the gym, replays the
recording, and waits for the client to exit after the final tick. Use
`--project-prop "tasStartGym=false"` when the gym is already managed separately,
and `--project-prop "tasServer=host:port"` for another local endpoint. Results are published below
`tas-results/` as `<recording-name>-<version>.lprc`; a
`<recording-name>-manifest.tsv` lists every result. Each result uses the normal
`.lprc` format: source buttons/yaw/pitch plus the observed post-movement
position for every tick, so it can be replayed or compared by the existing
tools. The client writes a temporary `.lprc.tmp` first and the workflow moves it
into place only after a successful run.

The same automation can be enabled for one client without the workflow by
passing Gradle properties, which are translated into JVM properties for the
client:

```text
gradlew runTasClient --project-prop "clientVersion=1.12.2" --project-prop "tasRecording=C:\runs\input.lprc" --project-prop "tasOutput=C:\runs\1.12.2.lprc" --project-prop "tasServer=localhost:25565"
```

Automated clients mute audio, connect to the gym, and show chat feedback for
recording/playback start and completion. Manual commands remain
`.recording start [name]`, `.recording stop`, `.playback <name>`, and
`.playback stop`.

To compare a reference against the Legacy Parkour movement emulation in the
repository's current client, use an expected `.lprc` from the gym and choose a
parkour version explicitly:

```text
gradlew runTasCompare --project-prop "tasExpected=tas-results\jump-1.12.lprc" --project-prop "tasVersion=1.12" --project-prop "tasCompare=true"
```

`tasExpected` must name the version-specific reference file produced or chosen
for the selected movement profile; it is deliberately separate from the
cross-version source input accepted by `runTasWorkflow`. This starts the newest
repository client, selects the requested movement profile, and compares each
post-movement position with that expected recording. `--project-prop
"tasOutput=<file.lprc>"` is optional; without it, the task creates a unique actual recording beside the
expected one. A supplied output path must not already exist. After the client exits, Gradle checks every tick and the tick
count, then exits successfully only when the comparison passes. A failure
reports the first tick and expected/actual XYZ, run ID, and snapshot log path.

By default the comparison is exact (`Double.compare` on each coordinate), so
even the smallest representable difference fails. `--project-prop
"tasTolerance=<number>"` can explicitly allow a finite per-coordinate
tolerance; it applies to both
the live first-mismatch signal and final Gradle result. At the first failing
tick, the connected player sends `!lpcf <runId> <version> <tick>` as normal
chat. The gym records the server-side world, position, velocity, bounding box,
and nearby blocks in `parkourgym-server/run/logs/latest.log` with the marker
`[LPC_FAILURE_SNAPSHOT]`; the task includes a matching log entry when present,
or the exact path, marker, and run ID to search for. The signal is canceled and
never broadcast. A cross-version `runTasWorkflow` capture does not compare
against source XYZ, because movement is expected to differ across versions.

For an explicit representable-double threshold, pass `--project-prop
"tasMaxUlps=10"` to `runTasCompare`. Every X, Y, and Z coordinate on every
tick must be finite and within 10 ULPs; the final result also requires the
exact tick count and reports the largest observed ULP distance. A failing tick
reports all three coordinate distances. Negative values and subnormals are
ordered by their IEEE 754 bit patterns; `-0.0` and `+0.0` have distance zero.
NaN and either infinity always fail. `tasMaxUlps` and the absolute
`tasTolerance` cannot be combined. With neither option, exact comparison
remains the default, including distinct signed zeros.

The Forge 1.12.2 MCP client uses `Minecraft.gameDir` for its run directory;
the TAS reflection layer includes that name (alongside modern
`gameDirectory`). This prevents the historical `.playback` crash before a
recording file is opened.
