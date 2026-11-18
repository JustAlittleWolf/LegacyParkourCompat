# Source comparison: 1.17 → 1.17.1 ordinary vertical air integration

## Bounded coverage and verdict

**No difference found** in the assigned ordinary local-player vertical air path: gravity, vertical drag and their order, movement request/position handoff, and common fall-distance accumulation/reset. This is a source comparison, not a trajectory/runtime test.

The local-player tick route is unchanged: `LocalPlayer.aiStep` calls `Player.aiStep` (`LocalPlayer.java`: 1.17 lines 651–817, including `super.aiStep()` at 812; 1.17.1 lines 649–815, call at 810). `Player.aiStep` calls `LivingEntity.aiStep` at line 506 in both. `LivingEntity.aiStep` calls virtual `travel(...)` at 1.17 line 2542 and 1.17.1 line 2546. `Player.travel` takes its non-flying branch and delegates to `super.travel(vec3)` at 1.17 line 1444 and 1.17.1 line 1435 under this assignment’s dry, non-flying, non-passenger conditions. `LocalPlayer.move` delegates the requested vector to `super.move` in both releases (1.17 lines 895–900; 1.17.1 lines 893–898).

In `LivingEntity.travel`, the shared normal branch is reached after the water, lava and fall-flying branches. `handleRelativeFrictionAndCalculateMovement` consumes current delta movement and calls `move` before the gravity/drag write (1.17 `LivingEntity.java` lines 2107–2130 and 2151–2162; 1.17.1 lines 2111–2134 and 2155–2166). With ordinary assumptions (no Slow Falling or Levitation, gravity enabled, loaded movement chunk, no friction discard), the same operation is: preserve the post-move Y component `v`; subtract `d = 0.08` when gravity applies; then write `v * 0.98F`. Horizontal air friction is `u = 0.91F` and does not affect this Y result. The assignment excludes block/support behavior; for an unobstructed request `dy`, the call’s Y position handoff is `dy` before the next velocity write.

Causal numeric witness, identical on each side: with starting `vy = -0.1` and no vertical collision, `Entity.move` receives `dy = -0.1`; ordinary travel then writes `(-0.1 - 0.08) * 0.98F = -0.17640000343322754` for next-tick `vy`. The movement call contributes `0.1F` to `fallDistance` before that new velocity is written.

`Entity.move` passes the resolved Y movement and `onGround` state to `checkFallDamage` (both `Entity.java` lines 534–651). The common update is identical: on ground, call the block fall hook only when `fallDistance > 0.0F`, then reset it to `0.0F`; otherwise, only when `d < 0.0`, set it to `(float)(fallDistance - d)` (`Entity.java` lines 998–1011). The `LivingEntity.checkFallDamage` override is also byte-identical (lines 286–307) and then delegates to this common update. Its server-only fall-particle threshold is outside local client movement and this assignment. Damage calculation and block behavior are excluded.

## Evidence identity and file authentication

Both sides are Mojmap exact-release publications. For 1.17, `decompiled_minecraft/1.17/mojmap/.decompile-provenance.json` SHA-256 is `afc9dc30cd8f6406d8ea306e64cc0b3601cc8d7c7bcd314f737573d830a79357`; its pinned inputs identify the 1.17 client (`client.jar` SHA-256 `14122287d4e4256c2d65ec8c0ccfa3b56448495fa3ccabc87d0f0eeb4fa1c30f`) and official Mojang mappings (`client.txt` SHA-256 `191b957aaa40b294402feef29651ae7a9f1263ec22c5382f3b8fa0a4918b0471`). The nested `mojmap` tree and mapping input establish the Mojmap namespace.

For 1.17.1, the release-root `mojmap.ready.json` SHA-256 is `c7180dce94d7b57b53a964445b0c788c71f23ba3ba40eea593c11cc939185e9b`; it records `versionId=1.17.1`, `mapping=mojmap`, the exact metadata ID and client SHA-256 `a49b4a56c5bbe15c9ed9fe53efa9a591f265a1f5ba7d6aa9739a58ea7a92b79d`. Its companion `mojmap.provenance.json` SHA-256 is `de197f77473955635e8c5730e113f227af2b8af9306b74e8a54a765eafc5a333`. The source manifest SHA-256 is `93270d229acfb751bf56daf1e7be26ce3dcff26b29e94aa157de621405a3463b`, matching the ready marker. The publication’s success excerpt reports `BUILD SUCCESSFUL`; its movement diagnostic confirms exact metadata and presence of Entity, LivingEntity, Player and LocalPlayer sources. All inspected method bodies are complete and source hashes match their manifests.

Each file below has an actual SHA-256 equal to its publication manifest entry:

| Relative Mojmap source | 1.17 SHA-256 | 1.17.1 SHA-256 |
|---|---|---|
| `net/minecraft/client/player/LocalPlayer.java` | `08dbb8615cc6c7776f35ce269223699d39f758d15786babfe11e497421c5f4d4` | `c9a91cb6cb57806bc8d22e5bfe2d97daaf21d5d2a48f34a6e2c53164c61c5812` |
| `net/minecraft/world/entity/Entity.java` | `81231ad8ac1b9a7a9f7ee186352c7e73c0c5211a3034c3a35c04badc6914c8ef` | `ab28e1fba924771ec048140dfd293ee5a46a7dfe81f71a1a0b1aecc1927232de` |
| `net/minecraft/world/entity/LivingEntity.java` | `8e81e5f1317d154fa41b676721ff581badd46296f77bcebcadb3c63448e528ed` | `33fd081aadb2b6fdc9ebf487db6da5b38c54f4b8676572790ee2203690d15e6f` |
| `net/minecraft/world/entity/player/Player.java` | `9ad4c439814caf5f51cf6da9a86c5d22906b0a28b5b606bdeb1b770617d90516` | `724bb298499dabe489dffd5ce7ec81c9773619a8d2c70044911eae4c9e8ff481` |

The paired bodies compared equal for `LocalPlayer.aiStep`, `LocalPlayer.move`, `Player.aiStep`, `Player.travel`, `LivingEntity.aiStep`, `LivingEntity.travel`, `LivingEntity.handleRelativeFrictionAndCalculateMovement`, `LivingEntity.checkFallDamage`, `Entity.move`, and `Entity.checkFallDamage`; line ranges above reflect each release’s source line numbers.

## Dependencies and exclusions

Closed caller/data-flow slice: local `aiStep` → player/living `aiStep` → `Player.travel` → `LivingEntity.travel` → `handleRelativeFrictionAndCalculateMovement` → local/entity `move` → common fall update → post-move gravity and drag. The comparison holds for the stated ordinary inputs and unobstructed movement handoff.

Excluded by assignment: jump causation, statuses and equipment, angle/horizontal calculations, fluids, climbables, blocks/support/collision providers and their behavior, vehicles, gliding, flying, visual/network state, and damage calculation. Collision results are treated as a boundary input; this comparison does not claim shape or support parity.
