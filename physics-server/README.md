# Physics testing server

`./gradlew runPhysicsServer` (or `./gradlew :physics-server:runServer`) starts a Paper 26.2 server and leaves it running.

The server is offline-mode and bound to `127.0.0.1`. Joining players are op, so `/gamemode` works. `/save` writes the loaded world as Polar.

The overworld is generated with the vanilla void superflat preset (empty world plus the stone spawn platform), then loaded through PolarPaper. Polar custom gamerules: `blockPhysics` on; `blockGravity`, `liquidPhysics`, and `blockFade` off. Mob spawning and the daylight cycle are off, and time is noon (`6000`).

Plugins installed automatically: ViaVersion, ViaBackwards, ViaRewind, PolarPaper, Axiom Paper, and this helper plugin.
