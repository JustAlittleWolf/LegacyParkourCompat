# Source lane: 1.21.8 → 1.21.9 vertical air integration

## Exact source identity

Both sides are Mojmap decompilations from `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/` and were read-only.

- **1.21.8:** release/mapping ready marker `decompiled_minecraft/1.21.8/mojmap.ready.json` SHA-256 `2e3bf302503eb127061cc605797b390f5f4a4b508f09350a59d78ced857ba7d7`; version `1.21.8`, mapping `mojmap`, version metadata ID `1.21.8`, client SHA-256 `ea74e9c5e92d01f95f3d39196ddb734e19a077a58b4c433c34109487d600276f`. Release-level provenance `mojmap.provenance.json` SHA-256 `90fcb3707371f2cfc5414e0089cfa231f505821d7aca45211c7c816332bb4cfa`; source-manifest `mojmap.sources.sha256` SHA-256 `8ffb76cea647a2ba4fe58e000678f751bea2c40ea6358bae492e962ed1d9d008` (also equals the ready marker's `sourceManifestSha256`).
- **1.21.9:** Mojmap tree `decompiled_minecraft/1.21.9/mojmap`; `.decompile-provenance.json` SHA-256 `37d3049478585a43ffcace14fc8f2ee814b35ceedd8c9bad8b60adaed9b72c4f`. Pinned inputs identify exact `1.21.9.json` metadata (SHA-256 `3cca801f41f83059092c88f7de3ea74247da4c394f3e672b7a41451151c293d5`), official `client.txt` mappings (SHA-256 `f223e9775abb66b6712c505cb1a73b48d615efebaf43a9f81dd58df9a9b1c7b3`), and client JAR (SHA-256 `40c0a32c46601f71f6a61dae9f126202ded5cc453cfb8da6e42ae73f2a3f5c1b`).

Selected Java files were individually hashed against the exact manifest entry on each side; every match passed:

| Mojmap source file | 1.21.8 SHA-256 | 1.21.9 SHA-256 |
|---|---|---|
| `net/minecraft/client/player/LocalPlayer.java` | `53f2a71a886b9c71853afe36f2df857f80a9bf1cd6ec4ca8604ccd7e238d89ee` | `9c1df00e2f8379b2c19577a3691fee28071d8925210be3d3df928b5352e367e1` |
| `net/minecraft/world/entity/player/Player.java` | `8dc5514fe44311f39268692f5188840b9f65cb71143228fa8b84242585ea7987` | `af857617b66a5776e63830771360b96f75e21d47d20db08f164a2dbeee801d82` |
| `net/minecraft/world/entity/LivingEntity.java` | `609f0197a0b4551ab42279e452c11cdd256135b1d467c2a95950b0e9fd7ddef8` | `b8b49d60769203f7bd5afe4a1bffcdcdbec30be28960324cdc43a2df85a6eb66` |
| `net/minecraft/world/entity/Entity.java` | `c403e6176d27b5bfd6aaa0dea3735ffa4fcf80dbae58766661dd84453b7e9704` | `bd6074b108212bde667877700af1a52649472630c906c57c2a36c96a6fcee74c` |
| `net/minecraft/world/entity/ai/attributes/Attributes.java` | `b941c0556d9ed3c3ce1ffd4c993c9902287a690230e1510696ff13f9efdf2020` | `b941c0556d9ed3c3ce1ffd4c993c9902287a690230e1510696ff13f9efdf2020` |

## Bounded coverage and comparison

This compares only ordinary local-player movement after its pre-travel inputs: dry, no fluid travel, not riding, not gliding, not ability-flying, no Levitation/Slow Falling, no climbable/powder-snow branch, and no block collision/support/provider investigation. Horizontal acceleration and Y-velocity causation are not evaluated.

The local call path remains the same: `LocalPlayer.aiStep` calls `super.aiStep` (1.21.8 `LocalPlayer.java:830`, 1.21.9 `:834`); `Player.aiStep` calls `super.aiStep` (1.21.8 `Player.java:576`, 1.21.9 `:466`). `LivingEntity.aiStep` reaches `travel` for the non-ridden case when `canSimulateMovement() && isEffectiveAi()` (1.21.8 `LivingEntity.java:2815–2819`; 1.21.9 `:2845–2849`). `LocalPlayer.isLocalPlayer()` remains `true` (1.21.8 `LocalPlayer.java:342–344`; 1.21.9 `:346–348`), making the common `Entity.move` local-authority fall-state path reachable.

- `Player.travel` has the same passenger/swimming/abilities-flying gates and delegates the ordinary case to `super.travel` (1.21.8 `Player.java:1438–1461`; 1.21.9 `:1295–1318`).
- `LivingEntity.travel` has the same routing: fluid branch, then fall-flying branch, else `travelInAir` (1.21.8 `LivingEntity.java:2245–2254`; 1.21.9 `:2275–2284`). The assigned dry, non-gliding case reaches `travelInAir` on both sides.
- `travelInAir` is unchanged in order and operations (1.21.8 `LivingEntity.java:2276–2298`; 1.21.9 `:2306–2328`): it calls `handleRelativeFrictionAndCalculateMovement` first; absent Levitation it subtracts `getEffectiveGravity()` on a client with the below-movement chunk loaded; then, absent discarded friction, multiplies Y by `0.98F` for a player. An unloaded-client-chunk fallback (`-0.1` above min Y, else `0.0`) is also textually unchanged. `getEffectiveGravity` is unchanged (1.21.8 `LivingEntity.java:2240–2243`; 1.21.9 `:2270–2273`). The ordinary base gravity is `0.08` in both `Attributes.java:44–46`; `Entity.getGravity` keeps the same no-gravity gate (1.21.8 `Entity.java:1308–1310`; 1.21.9 `:1359–1361`).
- `handleRelativeFrictionAndCalculateMovement` remains the same sequence: `moveRelative`, `handleOnClimbable`, `move(MoverType.SELF, currentDeltaMovement)`, then read the resulting delta (1.21.8 `LivingEntity.java:2446–2455`; 1.21.9 `:2476–2485`). With the assigned no-climbable/no-collision boundary, the move uses the current Y velocity before this tick's gravity update. The next Y velocity is `(vy - 0.08) * 0.98F`, with Java's float `0.98F` promoted for the double multiplication. Starting at `vy=0`, the post-travel value is `-0.0784000015258789`; the next tick's Y request is that value. Its passive recurrence approaches `-4.0000038147009`, below the 1.21.9 reset-ray cap of 8 blocks.
- Common fall state remains the same. `Entity.move` calls `checkFallDamage(actualDelta.y, onGround, ...)` only under `isLocalInstanceAuthoritative()` (1.21.8 `Entity.java:717–721`; 1.21.9 `:730–734`). The base accumulator adds `-(float)actualDeltaY` only when not in water and actual Y is negative, then resets `fallDistance` when grounded (1.21.8 `Entity.java:1336–1354`; 1.21.9 `:1387–1405`). These expressions/order are unchanged. LivingEntity's override still delegates to `super.checkFallDamage` (1.21.8 `LivingEntity.java:342–369`; 1.21.9 `:349–376`); server damage calculation is outside this assignment.

### Adjacent source delta and disposition

`Entity.move` has unrelated changes for the excluded `noPhysics` branch and movement-record argument. In the ordinary branch, its fall-reset test remains `fallDistance != 0.0 && actualDelta.lengthSqr() >= 1.0` (1.21.8 `Entity.java:682–692`; 1.21.9 `:693–705`). The subsequent fall-resetting-block clip now caps the inspected actual-displacement segment at 8 blocks in 1.21.9 (`Entity.java:697–704`), whereas 1.21.8 clips to the full actual displacement (`:686–691`). This is a block-provider boundary and cannot change the assigned ordinary vertical recurrence from rest: with horizontal movement excluded and passive gravity/drag, `|vy|` approaches 4.000004, so the capped and full segments have the same endpoint. The `>=1.0` threshold itself and common accumulator/ground reset are unchanged. I did not promote this boundary-only conditional to an independent finding.

## Result

No in-scope source difference found for ordinary local dry non-ridden, non-gliding, non-flying vertical air integration, passive gravity/drag order, or common fall accumulation/ground reset. **Canonical relative findings:** none for this bounded slice. Source coverage is limited to the call path and methods listed above; collision/support providers, status/equipment branches, jump causation, horizontal and ground acceleration, and flight/swimming remain excluded or owned by their respective lanes.
