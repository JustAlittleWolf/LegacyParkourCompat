# Recording client

`./gradlew runRecordingClient -PclientVersion=1.16.5` launches the latest patch of a parkour version with the recording mod.

- Versions before 1.14 use Forge (for example `1.8.9` or `1.8.9-forge`).
- 1.14 through 1.21.11 use Fabric through Unimined (for example `1.16.5` or `1.16.5-fabric`).
- `26.2` / `current` uses this repository's Fabric Loom client.
- Only the latest patch of each `ParkourVersion` is wired (so `1.8` runs 1.8.9, `1.16.2` runs 1.16.5).

In-game (simulation keys and facing, not the camera):

- `/recording start [name]`
- `/recording stop`
- `/playback <name>`

Recordings are `.lprc` files in `.legacyparkourrecordings` under the Minecraft run directory. The format is the same on every version. Playback teleports to the start pose, then applies recorded keys and facing each tick; stored positions are for later comparison and are not replayed.
