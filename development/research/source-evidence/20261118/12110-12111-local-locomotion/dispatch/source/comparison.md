# 1.21.10 to 1.21.11 local locomotion dispatch comparison

## Evidence identity

Both source roots report exact version IDs and `mojmap` mappings. `mojmap.ready.json` records 6,386 source files and source manifest SHA-256 `4be26049350c1b314a0b198022cb1e7ab1e745e104047de7a5c7d099a9d7b7e1` for 1.21.10, and 6,622 files and `0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555` for 1.21.11. Recomputed manifest hashes match those values. Both provenance files identify the exact batch decompile as `mojmap`, Vineflower 1.12.0, Java 25.0.3+9-LTS; version metadata IDs match the roots.

Consumed source SHA-256 values (recomputed and matching their source-manifest entries):

- 1.21.10 `net/minecraft/client/player/LocalPlayer.java`: `9c1df00e2f8379b2c19577a3691fee28071d8925210be3d3df928b5352e367e1`
- 1.21.10 `net/minecraft/world/entity/player/Player.java`: `af857617b66a5776e63830771360b96f75e21d47d20db08f164a2dbeee801d82`
- 1.21.10 `net/minecraft/world/entity/LivingEntity.java`: `b8b49d60769203f7bd5afe4a1bffcdcdbec30be28960324cdc43a2df85a6eb66`
- 1.21.11 `net/minecraft/client/player/LocalPlayer.java`: `948e94f8e874b2f72e9a689e6e3ba1933f5728ec381d64f3bb5e2388718bd477`
- 1.21.11 `net/minecraft/world/entity/player/Player.java`: `8e97167350a91741d0aa10d3b0d92a33150ed6dccdc94cd5b37d9c7ca22bcc81`
- 1.21.11 `net/minecraft/world/entity/LivingEntity.java`: `19ed2d565858c401c69a06750b054a633a20ea864cab3a753b5c767f0bdd60e8`
- 1.21.11 `net/minecraft/client/multiplayer/ClientPacketListener.java` (load-gate dependency): `f1ebdb54d717266c894c87381c98bad101980d252de5bae6ca55c37aece1732e`

## Ordered witness and comparison

1. **Ordinary local tick admission differs.** In 1.21.10, `LocalPlayer.tick()` first calls `tickClientLoadTimeout()` and then gates its `super.tick()` path on `hasClientLoaded()` (`net/minecraft/client/player/LocalPlayer.java:195-200`). `Player.hasClientLoaded()` returns `clientLoaded || clientLoadedTimeoutTimer <= 0`; `tickClientLoadTimeout()` decrements the timer while not loaded (`net/minecraft/world/entity/player/Player.java:1875-1883`). In 1.21.11, `LocalPlayer.tick()` gates `super.tick()` on `connection.hasClientLoaded()` and has no timeout decrement (`net/minecraft/client/player/LocalPlayer.java:211-215`). `ClientPacketListener.hasClientLoaded()` returns only `clientLoaded` (`net/minecraft/client/multiplayer/ClientPacketListener.java:2740-2746`). Thus, if load acknowledgment remains absent past the old 60-tick fallback, the old client begins the ordinary superclass/player tick while the new client continues to suppress it until connection load state becomes true. Since the gated call is `super.tick()`, this suppresses local player tick/physics progression under that reachable precondition.

2. **Admitted tick-to-travel order is retained in the inspected slices.** `Player.tick()` reaches `super.tick()` in both versions (`Player.java:267` / `266`), and `Player.aiStep()` reaches `super.aiStep()` before speed assignment and later interaction work (`Player.java:466-469` / `463-466`). The ordinary local path reaches the player superclass from `LocalPlayer.aiStep()` (`LocalPlayer.java:834` / `875`). `LivingEntity.aiStep()` performs input application, jump handling, constructs `Vec3(xxa, yya, zza)`, then invokes `travel` when movement is simulatable and effective AI is enabled (`LivingEntity.java:2793-2849` / `2918-2975`). In the ordinary non-riding, dry, non-fall-flying path, `Player.travel()` delegates to `super.travel()` (`Player.java:1295-1316` / `1360-1381`); `LivingEntity.travel()` chooses its air branch (`LivingEntity.java:2275-2284` / `2309-2317`). The dry-air arithmetic has the same order and expressions in the inspected body: supporting-block friction and `0.91F`, relative-friction movement, levitation or gravity/unknown-chunk Y adjustment, then friction retention (`LivingEntity.java:2306-2329` / `2343-2366`). No difference was found in this terminal arithmetic slice.

## Boundary

This comparison covers local tick admission, the ordinary tick/`aiStep` dispatch into travel, and the principal travel selection through dry-air arithmetic. It does not close the full movement inventory or the pair. Swimming/sprinting and other excluded branches, rotation, Elytra, abilities/flying, climbing, riding, timers, external direct responses, and all downstream movement/collision/shape dependencies were not reviewed here. No runtime trajectory was inferred beyond the direct scheduling consequence above.