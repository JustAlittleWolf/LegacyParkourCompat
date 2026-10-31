# Independent boundary binding review — bed-wake finding

Reviewed finding SHA-256: `d82a6f2d766718dab654dc3911bd2268ffbc68e0521418fd510d32df89d51078`
Boundary report SHA-256: `1bfa29de90e99fbb1a82370035d8f7edad2d5f85162458f0d5a6607de137376`
Accepted transferred witness SHA-256: `d0fdc5ea63ecf1b2cb9f637255280c7873a4971501104b2096302175927e998e`

## Verdict

**REVISE** before accepting this boundary-specific finding. The method boundary, source groups, client consumer and coordinates are correct, and the accepted concrete world-state does transfer across 1.16.1 → 1.16.2. However, the 1.16.1 old algorithm has an additional dangerous-block gate after `noCollision`, and the finding does not account for it when claiming the old candidate is accepted. The concrete witness passes that gate, so the conclusion stands after the short finding states it.

## Independent checks

- Both supplied immutable report hashes match the current files.
- Rehashed the 1.16.1 and 1.16.2 `mojmap.sources.sha256` files; they match their ready records and exact Mojmap releases. Source-file hashes match the boundary report's grouped identities: 1.16.1 BedBlock/LivingEntity are in the 1.16/.1 group; 1.16.2 BedBlock/LivingEntity/BlockGetter/DismountHelper are in the .2–.5 group. The .2 files also match the accepted .5 witness source hashes for those members.
- 1.16.1 `BedBlock.findStandUpPosition` (227–254) retains the integer-indexed x-outer/z-inner grid search, and `getStandingLocationAtOrBelow` (257–288) retains the `.4375` shape rejection, downward support search, `2.0E-7` epsilon, vertical limit and `noCollision`. `LivingEntity.stopSleeping` (3016–3032) passes index zero and commits via `setPos`.
- 1.16.2 `BedBlock.findStandUpPosition` (233–244) derives the first side offset from bed direction and player yaw, then uses `findStandUpPositionAtOffset` (276–290). `LivingEntity.stopSleeping` (3032–3052) passes yaw and commits the selected point. That helper invokes `DismountHelper.findSafeDismountLocation` (82–98), which obtains floor height from `BlockGetter.getBlockFloorHeight(VoxelShape,Supplier)` and accepts the candidate only if the player box has no block collisions. Method/source form is byte-identical through .5 per the boundary report and the accepted witness.
- The concrete non-bunk witness transfers: for the old scan the first supported center is `(-0.5,64.0000002,-0.5)`; the new yaw-first side candidate is west `(-0.5,64,0.5)` or east `(1.5,64,0.5)`. The bed and stone shapes do not intersect either entity AABB; .1's `noCollision` succeeds, and .2's block-collision walk finds no overlap. In .1, the added dangerous-block check examines `aABB.expandTowards(0.0,-0.2F,0.0)`; this includes only air and stone for the stated box/world, and `EntityType.isBlockDangerous` returns false for those blocks. In .2, the danger gates likewise accept air and stone. Thus the omitted gate does not invalidate the witness, but it is part of the exact old branch and should be recorded.
- The local consumer route is present in both releases: `ClientPacketListener.handleAnimate` action 2 calls `Player.stopSleepInBed(false,false)`; that method calls `super.stopSleeping()`. In .1/.2 the exact `handleAnimate` source shows those calls (action 2 at lines 929–931 and 902–904 respectively), and the accepted endpoint route review confirms the same bed-wake route at 1.15.2/1.16.5. This finding is correctly restricted to bed exit, with bed entry excluded.

## Required correction

In the 1.16.1 helper summary, say that after `noCollision`, it also rejects candidates whose expanded AABB intersects blocks for which `EntityType.isBlockDangerous` is true; then note that this witness's air/stone blocks pass. This keeps the precise boundary-specific gates attached to the old method while preserving the accepted witness and conclusion.

No product, wiki, backlog, build, test, runtime, or decompile action was performed.
