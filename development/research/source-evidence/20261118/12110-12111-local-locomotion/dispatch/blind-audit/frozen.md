# Blind source audit: local locomotion dispatch, 1.21.10 → 1.21.11

## Frozen verdict

For a loaded, living ordinary local player on the logical client, with movement simulation locally authoritative, not riding, not fall-flying, not swimming, not flying, and with no water/lava movement-fluid dispatch, the ordered direct tick-to-travel scheduling and dry-air branch are equivalent through the first movement-calculation call. No change in this bounded slice changes which movement call runs or its order. This is a source-level equivalence for the stated path, not a claim about all player movement or runtime parity.

Reachable witness: an ordinary local player in a loaded client world, on dry ground or in air, with ordinary walking input and no vehicle, swim, flight, or glide state. `LocalPlayer.tick` reaches `LivingEntity.tick`; after `Entity.tick`/`baseTick`, a nonremoved player reaches `LocalPlayer.aiStep`, then its inherited `Player.aiStep` and `LivingEntity.aiStep`. The local instance-authority gates pass. In the travel section, the controlling-passenger branch is false and `canSimulateMovement() && isEffectiveAi()` is true. `Player.travel` takes its non-passenger, nonswimming, nonflying `super.travel` branch. `LivingEntity.travel` takes its no-fluid, no-fall-flying `travelInAir` branch. The endpoint is `handleRelativeFrictionAndCalculateMovement(input, friction)`.

## Ordered path and evidence

All paths below are relative to each version's `mojmap/` root. Line ranges are one-based and inclusive.

1. `net/minecraft/client/player/LocalPlayer.java`: `tick()` 1.21.10:195–220; 1.21.11:211–235. Both call `super.tick()` inside the client-loaded condition before position/network reporting. 1.21.10 first invokes `tickClientLoadTimeout()` and checks `hasClientLoaded()`; 1.21.11 checks `connection.hasClientLoaded()` directly. For the witness where the loaded gate passes, the direct movement scheduling remains the same.
2. `net/minecraft/world/entity/LivingEntity.java`: `tick()` 1.21.10:2542–2544 calls `super.tick()` (Entity tick/base tick); if not removed, calls `aiStep()` at 2581–2582. 1.21.11:2610–2612 and 2649–2650 do the same. The surrounding movement-trigger order is unchanged in this slice. The 1.21.11 Entity base tick adds `computeSpeed()` before clearing `inBlockState`; this records positional delta (`Entity.java` 1.21.11:540–547) and does not alter the travel dispatch or its input in this witness.
3. `net/minecraft/client/player/LocalPlayer.java`: `aiStep()` starts at 687 (1.21.10) / 728 (1.21.11), and calls `super.aiStep()` at 834 / 875. Local input, sprint/sneak/timer and ability branches are excluded from this audit. The call onward is the same ordering for the selected ordinary state.
4. `net/minecraft/world/entity/player/Player.java`: `aiStep()` 1.21.10:455–466; 1.21.11:452–463 delegates to `super.aiStep()` before its subsequent work. The movement invocation occurs inside `LivingEntity.aiStep()` after jump processing. At 1.21.10:2827–2848 and 1.21.11:2953–2974, both versions build the input vector from `xxa/yya/zza`, skip the controlling-passenger route for this witness, then call `travel(input)` under `canSimulateMovement() && isEffectiveAi()`. Both methods return `isLocalInstanceAuthoritative()` (`Entity.java` 3420–3425 and 3438–3443 respectively).
5. `net/minecraft/world/entity/player/Player.java`: `travel(Vec3)` 1.21.10:1295–1318; 1.21.11:1360–1383. With passenger, swimming, and flying gates false, both reach `super.travel(input)`.
6. `net/minecraft/world/entity/LivingEntity.java`: `travel(Vec3)` 1.21.10:2275–2283; 1.21.11:2309–2317. The first gate is equivalent: fluid travel only for water/lava plus affected-by-fluids and not standing on that fluid; next is fall-flying; otherwise `travelInAir(input)`. In 1.21.11 the fluid predicate is factored into `shouldTravelInFluid(FluidState)` at 2319–2321; its returned condition is textually the former inline condition. The dry witness takes the same false result either way. `travelInAir` starts at 2306 / 2343; both read the block below for movement, choose block friction iff on ground (otherwise `1.0F`), compute `friction * 0.91F`, then call `handleRelativeFrictionAndCalculateMovement(input, friction)` at 2310 / 2347. This is the audit boundary; later air math is not assessed here.

## Exact source provenance

Both canonical source manifests were checked against `mojmap.ready.json` and their byte hashes matched the supplied immutable identities. Both `mojmap.provenance.json` records identify Mojmap exact decompilation in one Vineflower batch using Minecraft version IDs 1.21.10 and 1.21.11, respectively, with Java 25; mapping family is `mojmap`.

- 1.21.10 source manifest SHA-256: `4be26049350c1b314a0b198022cb1e7ab1e745e104047de7a5c7d099a9d7b7e1`
- 1.21.11 source manifest SHA-256: `0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555`

Inspected source file SHA-256 values, matching each version's manifest:

| Source file | 1.21.10 | 1.21.11 |
|---|---|---|
| `net/minecraft/client/player/LocalPlayer.java` | `9c1df00e2f8379b2c19577a3691fee28071d8925210be3d3df928b5352e367e1` | `948e94f8e874b2f72e9a689e6e3ba1933f5728ec381d64f3bb5e2388718bd477` |
| `net/minecraft/world/entity/player/Player.java` | `af857617b66a5776e63830771360b96f75e21d47d20db08f164a2dbeee801d82` | `8e97167350a91741d0aa10d3b0d92a33150ed6dccdc94cd5b37d9c7ca22bcc81` |
| `net/minecraft/world/entity/LivingEntity.java` | `b8b49d60769203f7bd5afe4a1bffcdcdbec30be28960324cdc43a2df85a6eb66` | `19ed2d565858c401c69a06750b054a633a20ea864cab3a753b5c767f0bdd60e8` |
| `net/minecraft/world/entity/Entity.java` | `8361dbb86fe6c975d21f69d008377b6f191669be751150c842517e9d0346fa18` | `32314478c6036fa9f3f3cc409c61c622eefc5a282d60e1011a1a33cc29cf18a3` |

Excluded by assignment: swim lookup-table changes, rotation, Elytra, abilities, climbing, riding, sprint/sneak/timers, external player responses and other physics. This freeze does not claim coverage beyond local locomotion dispatch through the first dry-air movement-calculation call.