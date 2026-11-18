# 1.21.8 → 1.21.9 ordinary ground and air acceleration

## Compared source identity

Both sides are Mojmap decompilations. For 1.21.8, `decompiled_minecraft/1.21.8/mojmap.ready.json` identifies version `1.21.8`, mapping `mojmap`, and the source-manifest SHA-256 `8ffb76cea647a2ba4fe58e000678f751bea2c40ea6358bae492e962ed1d9d008`; the file `mojmap.sources.sha256` matches that digest. Its ready marker records client SHA-256 `ea74e9c5e92d01f95f3d39196ddb734e19a077a58b4c433c34109487d600276f`.

The 1.21.9 tree uses the legacy source provenance form: `decompiled_minecraft/1.21.9/mojmap/.decompile-provenance.json` has SHA-256 `37d3049478585a43ffcace14fc8f2ee814b35ceedd8c9bad8b60adaed9b72c4f`. Its pinned inputs include the exact `1.21.9.json` version metadata (SHA-256 `3cca801f41f83059092c88f7de3ea74247da4c394f3e672b7a41451151c293d5`) and client JAR (SHA-256 `40c0a32c46601f71f6a61dae9f126202ded5cc453cfb8da6e42ae73f2a3f5c1b`). The manifest's source entries match the local source-file hashes listed below. The provenance file itself has no standalone version field; the exact metadata/client input identities, Mojmap output location, and source entries jointly identify this tree.

Relevant source hashes (SHA-256; local bytes checked against the 1.21.8 source manifest or 1.21.9 provenance `sources` entries):

| File | 1.21.8 | 1.21.9 |
|---|---|---|
| `net/minecraft/world/entity/Entity.java` | `c403e6176d27b5bfd6aaa0dea3735ffa4fcf80dbae58766661dd84453b7e9704` | `bd6074b108212bde667877700af1a52649472630c906c57c2a36c96a6fcee74c` |
| `net/minecraft/world/entity/LivingEntity.java` | `609f0197a0b4551ab42279e452c11cdd256135b1d467c2a95950b0e9fd7ddef8` | `b8b49d60769203f7bd5afe4a1bffcdcdbec30be28960324cdc43a2df85a6eb66` |
| `net/minecraft/world/entity/player/Player.java` | `8dc5514fe44311f39268692f5188840b9f65cb71143228fa8b84242585ea7987` | `af857617b66a5776e63830771360b96f75e21d47d20db08f164a2dbeee801d82` |
| `net/minecraft/client/player/LocalPlayer.java` | `53f2a71a886b9c71853afe36f2df857f80a9bf1cd6ec4ca8604ccd7e238d89ee` | `9c1df00e2f8379b2c19577a3691fee28071d8925210be3d3df928b5352e367e1` |
| `net/minecraft/client/player/AbstractClientPlayer.java` | `b10c8a80de875262ffc8c95614f8e6eeff187ded7c042261ffde993230479d2e` | `052e8387ace3ee65ea7456af23b8e9197931e5d5faeda54589166dce60269296` |
| `net/minecraft/world/entity/Avatar.java` | — | `6cfd006480fa708ec420a3d20a6207a49c515a6cfce2fe2fbf19b4b1ec633f17` |

## Ordinary local-player path checked

In both versions, `LocalPlayer` extends `AbstractClientPlayer`; the latter extends `Player`. On the ordinary dry, non-passenger, non-flying, non-swimming path, `Player.travel` delegates to `super.travel`, and `LivingEntity.travel` selects `travelInAir` when neither fluid travel nor fall-flying applies. In 1.21.9, `Player` now extends the new `Avatar` intermediate class, which extends `LivingEntity`; `Avatar` does not declare `travel`, so the inherited travel path remains the same. The checked `LivingEntity.aiStep` path constructs the request from the movement fields and dispatches `travel` under the same `canSimulateMovement() && isEffectiveAi()` gate in both releases (1.21.8 lines 2810–2818; 1.21.9 lines 2840–2848). This traces the existing request only; it does not assess input-device handling or the upstream production of those fields.

## Paired movement slice and result

- Dry travel dispatch: `LivingEntity.java`, 1.21.8 lines 2245–2253 and 1.21.9 lines 2275–2283. `Player.java`, 1.21.8 lines 1438–1460 and 1.21.9 lines 1295–1317. The non-ridden branch and selection of the base travel path are unchanged. The 1.21.9 superclass insertion (`Player → Avatar → LivingEntity`) adds no `Avatar.travel` override.
- Ground/air friction input: `LivingEntity.java`, 1.21.8 lines 2276–2280, 2446–2456, 2490–2498; 1.21.9 lines 2306–2310, 2476–2486, 2520–2528. Both versions select block friction while on ground and `1.0F` in air; derive horizontal drag as friction times `0.91F`; call `moveRelative(getFrictionInfluencedSpeed(friction), input)` before the move; and apply the same ground speed expression, `getSpeed() * (0.21600002F / (friction * friction * friction))`, or the airborne `getFlyingSpeed()`. `Player.getSpeed()` returns the same movement-speed attribute cast to `float` on both sides (1.21.8 lines 1482–1484; 1.21.9 lines 1339–1341). For an ordinary local player, inherited `getFlyingSpeed()` resolves to `0.02F` because the player is not a controlling passenger.
- Input normalization, yaw rotation and affected velocity write: `Entity.java`, 1.21.8 lines 1530–1547; 1.21.9 lines 1585–1602. `moveRelative` adds the transformed vector to current delta movement. The helper returns zero below squared length `1.0E-7`, normalizes only above length-squared `1.0`, scales by the supplied speed, and rotates horizontal components using float sine/cosine of current `getYRot()` in radians. These expressions and their order are unchanged.
- The subsequent horizontal decay remains `x,z *= friction * 0.91F` unless `shouldDiscardFriction()` is true; for air this is `0.91F`, and on ground it depends on the block friction. This is the same in `travelInAir` on both sides. `handleRelativeFrictionAndCalculateMovement` performs the same relative-acceleration call, existing climbable velocity handling, and `MoverType.SELF` move request before returning the movement vector.

**Coverage verdict:** no ground/air acceleration, friction, speed-input, normalization, yaw-to-travel, velocity-addition, or movement-request difference was found in this paired slice. The visible source-level changes here are line relocation and `Player`'s intermediate `Avatar` superclass. The changed `isClientSide` accessor spelling in `travelInAir` affects only the vertical gravity/chunk fallback expression and is outside this assigned horizontal acceleration slice. No trajectory prediction or runtime parity claim is made.
