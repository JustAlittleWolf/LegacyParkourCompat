# Physics testing server

`./gradlew runPhysicsServer` (or `./gradlew :physics-server:runServer`) starts a Paper 26.2 server and leaves it running.

The server is offline-mode and bound to `127.0.0.1`. Joining players are op, so `/gamemode` works. `/save` writes the loaded world as Polar.

The testing world is Polar (`polarpaper:physics_test`), height `[0, 256)`, with a 16×16 stone spawn platform at `y=64`. Players spawn there; the Nether is disabled (`config/paper-global.yml` `misc.enable-nether: false`). Paper still boots a dummy overworld because it requires one, then the helper plugin unloads it when possible. Polar custom gamerules: `blockPhysics` on; `blockGravity`, `liquidPhysics`, and `blockFade` off. Mob spawning, weather, and the daylight cycle are off, and time is noon (`6000`).

The standardized Polar world is `physics-server/worlds/physics_test.polar` (Git LFS). First launch copies it into `physics-server/run/` if that copy is missing. After changing the test world, `/save` and copy the Polar file back into `physics-server/worlds/` to update the tracked world.

If you already have an old `physics-server/run/` from before the height change, delete that folder so the datapack and Polar world can be copied fresh.

Plugins installed automatically: ViaVersion, ViaBackwards, ViaRewind, PolarPaper, Axiom Paper, and this helper plugin.
