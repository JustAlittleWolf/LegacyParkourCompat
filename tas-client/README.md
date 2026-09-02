# TAS client

`./gradlew runTasClient -PclientVersion=1.16.5` launches that exact Minecraft version with the recording mod. Versions are not remapped to a later patch; `1.21.9` stays `1.21.9`. Unsupported versions fail the Gradle task.

- Versions before 1.14 use Forge when a loader is pinned (for example `1.8.9` or `1.8.9-forge`). `1.8` is rejected.
- 1.14 through 1.21.x use Fabric through Unimined (for example `1.16.5` or `1.21.9-fabric`).
- `current` uses this repository's Fabric Loom client (`minecraft_version` in `gradle.properties`). There is no `latest` alias.

In-game (simulation keys and facing, not the camera):

- `/recording start [name]`
- `/recording stop`
- `/playback <name>`

Right-click is stored as both hold and press: holding use (bow draw) is not the same as a click this tick (pearl / place).

Recordings are `.lprc` files in `.legacyparkourrecordings` under the Minecraft run directory. The on-disk layout is the same on every version. Playback teleports to the start pose, then applies recorded keys and facing each tick; stored positions are for later comparison and are not replayed.

Shared recording types live in the `core` subproject (`./gradlew -p tas-client :core:test`). Mixins are selected per era (`forge`, Fabric 1.14–1.16, Fabric 1.17+ / current) rather than one config for every class name.
