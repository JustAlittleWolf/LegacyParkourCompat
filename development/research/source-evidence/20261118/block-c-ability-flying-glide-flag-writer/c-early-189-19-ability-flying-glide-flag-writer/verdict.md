# Independent source audit: creative flight while fall-gliding (1.8.9 → 1.9)

## Verdict

**Accepted, with the local synchronization path explicitly closed.** The frozen claim is supported: on 1.9, ordinary creative-flight double-tap can set `abilities.flying` while the local player still has synced flag 7 set. If the ordinary server acceptance and metadata update arrive before the next eligible jump edge, `LivingEntity.moveRelative` takes its fall-flying branch and calls `Entity.move`; then `PlayerEntity.moveRelative` resumes and clears flag 7. This is source-established reachability, not a latency guarantee. The adjacent `fallDistance = 0.0F` write is excluded from this claim. There is no 1.8.9 Elytra/fall-flying counterpart.

## Frozen input and exact source identity

Audited frozen input `c-early_189_19_ability_flying_glide_flag_writer/report.md`, SHA-256 `7e254aa6b797c34109e25610c87e5f7b9f4077e1c7e0fc77963bcf44ac12b8d0`.

Canonical local sources are exact Minecraft 1.8.9 and 1.9, both Ornithe Feather. The 1.8.9 preparation manifest `decompiled_minecraft/1.8.9/ornithe-feather.provenance.json` has SHA-256 `ba066fc8f1c7ceb9819d2106338d9e9f2c2b96eb5a8329eb40e171b1f9889185`; its canonical source-hash index `decompiled_minecraft/1.8.9/ornithe-feather.sources.sha256` has SHA-256 `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004`. The manifest records `versionId: 1.8.9`, `namespace: ornithe-feather`, and Feather Gen2 `1.8.9+build.2`; its original preparation batch also included 1.9.4, which does not change this tree's explicit 1.8.9 identity.

The 1.9 canonical `.decompile-provenance.json` has SHA-256 `8daacb7af8f210e716ee4b34c43b8a5d6f6b4b19831d2fd725cbe16ecf330a1a`. Its source map records the 1.9 Feather-generated source bytes. Current file hashes were recomputed and match the frozen report and, for 1.9, the manifest entries. The source set is read-only; no generation or artifact refresh was performed.

| Version | Relative source | SHA-256 |
|---|---|---|
| 1.8.9 | `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `1762b116e6b06d682b7daaa0fc8cce39b0ff455b3db8cf79dab03ac74f6c4053` |
| 1.8.9 | `net/minecraft/entity/living/player/PlayerEntity.java` | `e66cb294fc93118148a444bbafdf4dd57cbf66a23d69b1e8892cefccc690ab88` |
| 1.9 | `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `04d6196e7e17595c0aed59b28bc1730f8be5ea3eccdb1f971e9e2492dfadfe06` |
| 1.9 | `net/minecraft/entity/living/player/PlayerEntity.java` | `edbb46236d1e42630e600e4afcb434eb6e9adecbfc4ac7e15710a5dea50010b1` |
| 1.9 | `net/minecraft/entity/living/LivingEntity.java` | `5b5936656aacc64a4d75db7b370985f9456ac96076331da047bd246f76a705fb` |
| 1.9 | `net/minecraft/entity/Entity.java` | `74d3ac34d7e6355c8fdcdd777dcd95ba9ef4626718c8772c1b58d0e28419e42a` |
| 1.9 | `net/minecraft/world/WorldSettings.java` | `31f980844878ca5d20f36c23736484622de44b43f92754e7aaca5f4122ddd0d6` |
| 1.9 | `net/minecraft/client/ClientPlayerInteractionManager.java` | `7f64e714377fba1e09d47b1a89a55e5bee961040b6707ecf9c1065df740fea53` |
| 1.9 | `net/minecraft/server/network/handler/ServerPlayNetworkHandler.java` | `89609533320e02ed781c4aa87caf9eb55417756bba8a6b0ea53bb5f2e1836634` |
| 1.9 | `net/minecraft/server/entity/living/player/ServerPlayerEntity.java` | `dc6b4d3dfff336aed5efa03c9f97a3ae09531e0ae8cd80f8cb6de949333fbe10` |
| 1.9 | `net/minecraft/server/TrackedEntity.java` | `82e4772fb6da96cbc92548211f7e0f5161b68e1b71726f26a6e2b7bb08ef09f0` |
| 1.9 | `net/minecraft/client/network/handler/ClientPlayNetworkHandler.java` | `7f67bd2daa792cfc4aac1744d30f9cfdb28306ec55583d474ddc1a251561b1d6` |
| 1.9 | `net/minecraft/network/packet/s2c/play/EntityDataS2CPacket.java` | `e431eca560c9aab47b934991dd546fa80634284373dfcac47158836bc6bf28d8` |

## Independent trace

- **Normal creative setup and toggle:** `ClientPlayerInteractionManager.setGameMode` applies the game mode to local abilities (`ClientPlayerInteractionManager.java:74–77`); `WorldSettings.GameMode.apply` gives CREATIVE `canFly` (`WorldSettings.java:104–108`). In `LocalClientPlayerEntity.mobTick`, a rising jump stores `pressedJumpTwiceTimer = 7`; a subsequent rising edge with the timer active toggles `abilities.flying` and calls `syncAbilities`, without testing ground state or `isFallFlying` (`LocalClientPlayerEntity.java:641–645, 686–700`). The inherited player tick decrements the timer (`PlayerEntity.java:401–403`), leaving the normal later-edge window.
- **Ordinary glide request and server acceptance:** a rising jump while airborne and descending, not already flying/gliding, and wearing a usable Elytra sends `START_FALL_FLYING` (`LocalClientPlayerEntity.java:703–707`). The vanilla server handler checks airborne/downward motion, not already gliding, not in water, and usable Elytra, then invokes `ServerPlayerEntity.setFlying`; this writes synced flag 7 true (`ServerPlayNetworkHandler.java:846–854`; `ServerPlayerEntity.java:1040–1042`).
- **Server-to-own-client route:** `TrackedEntity.sendPacketToAll` sends to tracking clients and also directly sends the same packet through this `ServerPlayerEntity`'s own network handler (`TrackedEntity.java:286–295`). Dirty synced data is emitted as `EntityDataS2CPacket` (`TrackedEntity.java:269–273`); the explicit `updatePlayer` self-exclusion for initial tracking is at `314–323`, but it does not suppress the direct self send in `sendPacketToAll`. On receipt, the local client updates the entity's synced data (`ClientPlayNetworkHandler.handleEntityData`, `ClientPlayNetworkHandler.java:443–449`). Flag 7 is a bit in that shared synced-data byte (`Entity.java:1652–1663`), and `isFallFlying()` reads the flag (`LivingEntity.java:2038–2040`). Therefore the server acceptance can establish flag 7 on the local player through the ordinary synchronized path. The local creative abilities toggle is a separate client-side state update. A second jump edge after both states are present creates the conjunction; network latency/order determines whether that timing occurs, and source alone cannot guarantee it.
- **Movement and clear order:** after `LivingEntity` calls `flyingTick()` it dispatches `moveRelative` (`LivingEntity.java:1697–1698`). On the client, `flyingTick` deliberately does not write flag 7; its write is gated to server worlds (`LivingEntity.java:1705–1723`), so the synced true flag survives up to movement. `PlayerEntity.moveRelative` enters its ability-flying block, invokes `super.moveRelative` while flag 7 remains true, then restores vertical velocity and air speed, writes `fallDistance = 0.0F`, and directly clears flag 7 (`PlayerEntity.java:1366–1380`). `LivingEntity.moveRelative` tests `isFallFlying()` and calls the ordinary `this.move(velocityX, velocityY, velocityZ)` in that branch (`LivingEntity.java:1289–1329`), which dispatches the inherited `Entity.move` implementation. This substantiates the ordering in the frozen report.
- **Excluded adjacent write:** `fallDistance = 0.0F` at `PlayerEntity.java:1377` is immediately before the clear at line 1378. It is a separate movement-state write and was not treated as the finding.
- **Older endpoint:** a source-wide scan of the 1.8.9 Feather `net/minecraft` tree for Elytra, `isFallFlying`, `START_FALL_FLYING`, and `setFlag(7` found no match. Its `LocalClientPlayerEntity.mobTick` does contain the ordinary creative double-jump toggle (`LocalClientPlayerEntity.java:579–594`), but 1.8.9 has no corresponding flag-7 glide state to overlap.

No runtime or packet manipulation was used. This verdict is source-only and establishes reachable source path and exact ordering, not frequency, trajectory, or guaranteed network timing.
