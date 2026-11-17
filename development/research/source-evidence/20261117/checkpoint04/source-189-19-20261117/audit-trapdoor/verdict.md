# Independent source audit: trapdoor ladder climbing

**Verdict: ACCEPT** for candidate SHA-256 `C2986394FAAFE72C02684CADA9987767E0301F1C849B96B2C1E8414C47A7170C`.

## Evidence identity

- 1.8.9 Ornithe Feather source manifest SHA-256: `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004`; provenance SHA-256: `ba066fc8f1c7ceb9819d2106338d9e9f2c2b96eb5a8329eb40e171b1f9889185`. The manifest row for `net/minecraft/entity/living/LivingEntity.java` matches the checked file SHA-256 `082831c6578e3a70fa6cea5b90bc3eefc26678259b66334470de22b90b5b0e4e`.
- 1.9 Ornithe Feather provenance SHA-256: `8daacb7af8f210e716ee4b34c43b8a5d6f6b4b19831d2fd725cbe16ecf330a1a`. Its `net/minecraft/entity/living/LivingEntity.java` entry matches the checked file SHA-256 `5b5936656aacc64a4d75db7b370985f9456ac96076331da047bd246f76a705fb`.
- Both `LivingEntity.java` hashes and the candidate hash were recomputed from the checked files. The 1.9 provenance inputs identify Feather 1.9 build.2.

## Audit result

In 1.8.9 `LivingEntity.isClimbing()` (lines 794–800), the sampled position is `(floor(x), floor(getShape().minY), floor(z))`. It returns true only when that block is `Blocks.LADDER` or `Blocks.VINE` and the entity is not a spectator `PlayerEntity`. In 1.9 (lines 896–908), the same sample and spectator exclusion remain; the block result additionally accepts a `TrapdoorBlock` when `canClimbTrapdoor` succeeds. That helper (lines 910–919) requires `OPEN == true`, then queries the block below and requires `Blocks.LADDER` and exact equality between `LadderBlock.FACING` and `TrapdoorBlock.FACING`. Thus the candidate's described open trapdoor over a same-facing ladder is sufficient for a non-spectator player to see the predicate change from false to true, provided that trapdoor occupies the sampled position.

The predicate is inherited by players: `PlayerEntity extends LivingEntity` on both sides, `ClientPlayerEntity extends PlayerEntity`, and `LocalClientPlayerEntity extends ClientPlayerEntity`. The local-player override of `isLocallyControlled()` returns true in both releases. The paired travel trace's movement claim is confirmed in source: the ordinary `moveRelative` path calls `isClimbing()` before movement to clamp X/Z velocity to `[-0.15F, 0.15F]`, clear fall distance, cap downward Y velocity at `-0.15`, and suppress negative Y for a sneaking player. After `move`, horizontal collision plus another true `isClimbing()` sets Y velocity to `0.2` (1.8.9 lines 1150–1168; 1.9 lines 1363–1381). The matching-open-trapdoor predicate can therefore change player movement state and, for the post-move collision gate, subsequent vertical movement.

The 1.9 ordinary path is gated by locally controlled or logical-side movement updating and excludes water, lava and the fall-flying branch; the first climbing clamp additionally depends on entering that ordinary path. The post-move `0.2` response also requires horizontal collision. The candidate says only that the new predicate “can activate” climb response, so this conditional wording is accurate. The added predicate's state test, inherited player reachability, and direct movement consequence are source-supported; no unresolved dependency remains for this bounded finding.

This acceptance is for the immutable candidate and supplied source identities only. It does not establish the exact intermediate patch boundary, full-pair coverage/freeze, or runtime parity.
