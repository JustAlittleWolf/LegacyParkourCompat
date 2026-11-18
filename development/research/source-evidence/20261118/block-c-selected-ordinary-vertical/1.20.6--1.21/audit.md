# Blind source audit: ordinary local-player vertical air movement

## Assignment boundary

Comparison: exact 1.20.6 Mojmap to 1.21 Mojmap. This audit covers only a local, non-passenger Player in ordinary dry, empty-air movement: no swimming, water/lava, climbable or powder-snow handling, Levitation or Slow Falling, Elytra, abilities flight, collision/support callback behavior, jump causation, or non-player/vehicle movement. Focus is passive Y integration/drag/order and the common `fallDistance` accumulator/reset reached through player movement.

## Evidence identity and byte verification

- 1.20.6 ready marker `decompiled_minecraft/1.20.6/mojmap.ready.json`: SHA-256 `1ec1dc773ea2a8a3ef48a9fc868893bd2f83d6b13b057d75b151b711788c3d41` (version 1.20.6, mapping `mojmap`); its provenance `decompiled_minecraft/1.20.6/mojmap.provenance.json`: `ee53e67e7ab51069a0eaae5a0ce13d49a9f972bdc13e3c31e041afa77f4d26e8`; source manifest `decompiled_minecraft/1.20.6/mojmap.sources.sha256`: `56aae10684471d7abb1c366bd5dd431ab976112a87e6cc5c687a68f1eff06311`.
- 1.21 nested Mojmap provenance `decompiled_minecraft/1.21/mojmap/.decompile-provenance.json`: SHA-256 `90246f277aefd2350e42e17245faa51928a949c8db4427f3be04a732527deb7c`.
- For each compared source file below, the live SHA-256 matched its entry in the authenticated 1.20.6 source manifest or 1.21 nested provenance, respectively. The methods were read in place; no source tree was generated or changed.

| Source file, relative to version's `mojmap/` | 1.20.6 source SHA-256 | 1.21 source SHA-256 |
|---|---|---|
| `net/minecraft/client/player/LocalPlayer.java` | `6b429dfa6e0681251ec985dda1627f808652a7bbe5b70dc85c8fa0fe0ed46ffa` | `c555e68ac3c63ab9b4f9a9e31933e263b96350a2bc599d11a0de5928bc24b583` |
| `net/minecraft/world/entity/player/Player.java` | `785d93ccc94e1f912e545b2b0c355edeb352b44ee8e83a69364daec35266dbe2` | `ed32b88c3c7c8418b83db41823520f2b6b0b49a98610306ef26e9681dc925c71` |
| `net/minecraft/world/entity/LivingEntity.java` | `c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2` | `324a3eee8496caab57cfaf5101ef576f1ae3c60c40e3857e96f35f3af9a3a0d8` |
| `net/minecraft/world/entity/Entity.java` | `71cd6b9f6c002684154dce11d3745e8714d82f13c8e1b3aa56743930131c18f3` | `b81905c7879e2cc5c5063a41d865c4164ad919f156306705be791d1017b99850` |
| `net/minecraft/world/entity/ai/attributes/Attributes.java` | `7e30d87c7a58b14d0052d2f9f7319d997b49ae7d025579cd762d28e845b2e82e` | `a9a19f556bb77fc218b5b2f4831eb6b285f4398c24fa2a3519009ca8dad2db7c` |

## Reachability and enclosing gates

`LocalPlayer.tick` enters the inherited living/player tick only if its X/Z chunk is present: LocalPlayer.java:190-209 (1.20.6) and :191-210 (1.21). `LivingEntity.tick` calls virtual `aiStep` when not removed: LivingEntity.java:2336-2376 (1.20.6), :2319-2359 (1.21). LocalPlayer.aiStep performs client bookkeeping, then calls `super.aiStep`: LocalPlayer.java:648-812, call at :807 (1.20.6); :644-809, call at :804 (1.21). LocalPlayer overrides `isEffectiveAi()` to true (:488-491 and :484-487), and the inherited controlled-instance predicate identifies the local player (:2960-2962 and Entity.java:2995-2997). Thus the pre-travel non-effective-AI 0.98 vector scale is bypassed for this actor in both versions.

`LivingEntity.aiStep` applies the same `abs(y) < 0.003` cutoff (sets the Y component to `0.0`) and writes the resulting vector before its `travel` call: LivingEntity.java:2591-2629 and :2670-2684 (1.20.6); :2586-2624 and :2665-2679 (1.21). LocalPlayer has no explicit assignment to `yya`; the ordinary dry path supplies no vertical input. Jump and special movement initiation are outside this assignment. `Player.travel` adds vertical look input only while swimming; its abilities-flight branch stores prior Y velocity, calls super, scales Y by `0.6`, and resets fall distance. With swimming false, abilities flying false, and passenger false, it delegates directly to `LivingEntity.travel`: Player.java:1473-1495 (1.20.6), :1456-1478 (1.21). In `LivingEntity.travel`, `isControlledByLocalInstance()` encloses travel processing; the local actor enters it. The water, lava, and fall-flying branches precede the ordinary `else`, and are excluded by the assignment boundary. The ordinary gravity path also assumes a loaded movement-position chunk; with the ordinary local loaded-chunk case, `!clientSide || hasChunkAt($$25)` selects gravity subtraction, rather than the unloaded-client fallback.

## Paired vertical path

The paired source reaches the same operations and order:

- Gravity source: LivingEntity.getDefaultGravity returns `getAttributeValue(Attributes.GRAVITY)`: :2099-2102 (1.20.6), :2086-2089 (1.21). The attribute default is `0.08` in both: Attributes.java:42-44 and :50-52. The 1.21 addition of neutral sentiment metadata does not change this value or movement math. `Entity.getGravity` returns zero only when no-gravity is set; otherwise it returns `getDefaultGravity`: Entity.java:1093-1113 and :1132-1152. The ordinary case has no no-gravity toggle or attribute modifier.
- On ordinary dry, non-flight travel, `handleRelativeFrictionAndCalculateMovement(input, friction)` first applies relative input, invokes `move(MoverType.SELF, getDeltaMovement())`, and returns current delta movement; :2269-2279 (1.20.6), :2252-2263 (1.21). The operation and order match. `Player` is not a `FlyingAnimal`; with normal `discardFriction == false`, the post-move Y uses the ordinary `* 0.98F` friction branch.
- The ordinary `LivingEntity.travel` branch takes returned `$$28.y`, subtracts gravity when no Levitation, then stores it multiplied by `0.98F`: :2210-2228 (1.20.6), :2192-2211 (1.21). For the default unmodified gravity, the post-travel Y velocity is `0.98F * (vY - 0.08)` (multiplication is the source's double-by-float expression). Gravity and vertical drag are applied after the movement request in both releases.
- The movement request is the current Y component of delta movement passed by `handleRelativeFrictionAndCalculateMovement` into `Entity.move`. In an empty, collision-free case, the collision-resolved request has the same Y, and Entity.move writes `Y + requestY` with `setPos`: Entity.java:597-648 (1.20.6), :598-648 (1.21). Collision resolution, `onGround`/support providers and block callbacks are boundary dependencies, not audited behavior here.
- `Entity.move` calls `checkFallDamage` with collision-resolved `$$2.y` before `LivingEntity.travel` subtracts gravity: Entity.java:633-648 (1.20.6), :634-648 (1.21). The base accumulator is identical: when grounded it resets after fall handling; otherwise for negative displacement it performs `fallDistance -= (float) displacementY`: Entity.java:1124-1140 and :1163-1179. LivingEntity delegates to this base method in both versions (LivingEntity.java:308-342; :326-359); its additional particle logic is server-only and does not change client fall state.

For collision-free downward movement, therefore, each tick first advances position by the prior vertical velocity, then increments the float fall-distance state by the negative of the float-cast resolved displacement, then computes next velocity from prior velocity minus gravity and multiplies by `0.98F`. The `abs(vY) < 0.003` zeroing occurs at the next `aiStep` before that tick's travel. These numeric operations, casts, cutoff, gates and order match across the pair.

`Entity.move` calls `setOnGroundWithKnownMovement` in 1.20.6 and the renamed `setOnGroundWithMovement` in 1.21; corresponding declarations only assign `onGround` and call `checkSupportingBlock` with the same arguments (Entity.java:562-565 vs :563-566). There is no change to this path’s vertical request or fall-distance behavior; support evaluation is outside scope.

## Verdict and coverage

No source-confirmed change found in the assigned ordinary local dry-air vertical-integration / common fall-accumulator slice for 1.20.6→1.21. The shared path has paired evidence through LocalPlayer dispatch, Player.travel, LivingEntity.aiStep/travel and Entity.move/checkFallDamage. No claim is made about the excluded fluid/effect/flight/jump/collision/support branches, vertical callbacks, blocks, networking, or other movement axes.

## Phase 2 independent verdict

Author draft reviewed: `source-1.20.6--1.21/source.md`, whole-file SHA-256 `5db610c249c81890e04392df39e2832a9ba33d4595aa23ee77dce238f458f5ee`.

Verdict: **Agree; no disagreement.** I independently confirm the draft’s ordinary dry, local-player branch gates, Y request/position order, gravity subtraction followed by `0.98F` vertical drag, `0.003` deadzone, and the shared float-cast fall-distance accumulation/ground reset. Its default `0.08` gravity witness is consistent with the authenticated attribute and source expression.

I also verified the draft’s explicit movement-length clip-reset gate: 1.20.6 `Entity.move` lines 620–626 and 1.21 lines 621–627 require nonzero `fallDistance` and resolved movement length squared `>= 1.0`, then reset only when the `FALLDAMAGE_RESETTING`/water clip is not a miss. The paired gate and order are identical. Its providers remain outside this assignment. The author source did not alter my frozen finding or reveal any qualifying delta.

Signed: `audit_1206_121`, independent Phase 2 source verdict, 2026-11-18. Blind-prefix SHA-256: `45bccddefc21dfd6012b5ade085a6011a428838737f652b379bd4205a740d743`.