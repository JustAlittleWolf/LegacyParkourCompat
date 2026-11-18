# Independent source verdict r2: combined collision mask and effect witness

## Verdict

**Corrected: accept the bounded 1.13.2 timer-arm witness with the physical Stone floor included in the aggregate mask.** The first ray-hit AABB is `[10,12] × [64,66] × [0,1]`, so `s=66`, rise `u=2`; Jump Boost II (amplifier 1), cap `2.7F`, suffices when the direct AIR checks pass. Jump Boost III also passes but is unnecessary. The previous r1 conclusion (`s=67`, `u=3`, JBIII required) is superseded for this exact two-Stone support setup: it omitted physical canonical full cubes that join the same voxel mask. This is exact-source reasoning, not runtime verification.

The r1 target remains preserved. This r2 re-audit incorporates the exact bounded setup (Speed II, east step x `8.89→8.99`, z `.5`, feet y `64`, full Stone support at `(8,63,0)` and `(9,63,0)`, stationary world-border east edge x=10, loaded chunks for the full scan, physical AIR in every other scanned cell and every sampled clearance cell). If the floor is not exactly these Stone cells or required chunks are unloaded, the matrix and boxes must be recomputed.

## Evidence identity

I independently reconstructed the matrix and greedy output from 1.13.2 source before comparing with the integrator's read-only scratch input. The independent matrix, nine emitted boxes, hit selection and coordinates match `aggregate-autojump-stone-mask-recheck/combined-mask-repro.md` SHA-256 `2eb8a055a56248b39e6cd2f80a3a6d0e0c0eff95dad51390db04af1b8ffca398`.

Canonical source: 1.13.2 Ornithe Feather Gen2 build.2, exact client SHA-256 `3410887ba652f25792c7675bfaf9140e73b60e93cfbf113a803f8a98cb05c0f9`, source manifest SHA-256 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`, provenance SHA-256 `e25950b7855fc4f2aee1a662f0be6b0af9f28788d9e27293075cfc5a3aa5d383`. The ready-marker digest matches the source manifest; all following hashes match their manifest entries:

- `WorldView.java`: `9577360b1a1bdde9280f0b72593e5e6dde45d9a54719cd7ff933f3ecd31b971f`
- `World.java`: `0023de30da608db73c6c6188334d2bb19039d86fe4d6f5e4ad79d732a5d2cd1a`
- `LocalClientPlayerEntity.java`: `2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29b29d8d9bf42bf`
- `VoxelSet.java`: `fe39ea60ac27e595280627a4d15ea93a69292da5870b5ef34a1d7d7283a267c6`
- `VoxelShape.java`: `d95b0607647da11595e24e1e26e9615cb7da7705ea7885908544362df62275c1`
- `IntVoxelShape.java`: `e563f5530907a5cd0ecc07bd79473d378c8c1aeeaba1dba4ac6f703de7d7b3ca`
- `BitSetVoxelSet.java`: `2048daa3a95ddd567a70476862e9b2dde135b91f49b61d22a4ae3aeed98322aa`
- `Block.java`: `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4`
- `StoneBlock.java`: `4a85d515f1195600042d7e67de00767c77c91c2e74aa00ac7796ceb7ea78c906`
- `WorldBorder.java`: `7593d7a8df153f2a039a5b38cca684e1e15529b80ffde743151419e125590718`
- `PlayerEntity.java`: `4ed22f6c5a3c55d67eed782070ac722201df4d624adbc90779f1fd29c2876633`
- `StatusEffect.java`: `60cc53e7233d28413d3c93292fd216babe8ea9edeb70774141b68101c515c639`
- `EffectCommand.java`: `4e425cecbb0b9873c5b229beb6b6835c043d17b1709b7881aeeab21b950dc2ab`
- `StatusEffectInstance.java`: `4e32f77b5e656de2f07b8ec2851dde4c3ee107f773984d2c03774f0c24a33e93`
- `LivingEntity.java`: `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c`

The 1.13.1 `LocalClientPlayerEntity.java` and `WorldView.java` are byte-identical to 1.13.2. 1.13 itself differs in AutoJump's collision-query shape/iteration call, so this remains 1.13.1/1.13.2 bounded and does not establish an unchanged 1.13→1.13.1 boundary.

## Exact occupancy matrix and greedy boxes

For the AutoJump `getCollisions(this, box)` call, the query is approximately x `[8.39,10.67]`, y `[64,65.8]`, z `[-0.1,1.1]`. `WorldView.getBlockCollisions` scans x `[7,12)`, y `[63,67)`, z `[-2,3)`. Its admission rule rejects cells on two or more scan edges. The border collector puts both physical full cubes and outside-border full cubes into one `BitSetVoxelSet`; it does not test positive overlap with the query before setting those cells. The aggregate `IntVoxelShape` is appended and overlap-filtered only after the mask has been built. `World.getCollisions` forwards this stream before appending entity collisions. Thus the support cells at y=63, although their shapes only touch the query bottom, affect the aggregate's greedy decomposition.

Complete admitted occupancy (z entries are block-cell coordinates; every unlisted cell in scan volume is empty):

| Scan y | x=8 | x=9 | x=10 | x=11 |
|---:|---|---|---|---|
| 63 | {0} physical Stone | {0} physical Stone | {-1, 0, 1} synthetic | {} |
| 64 | {} | {} | {-2, -1, 0, 1, 2} synthetic | {-1, 0, 1} synthetic |
| 65 | {} | {} | {-2, -1, 0, 1, 2} synthetic | {-1, 0, 1} synthetic |
| 66 | {} | {} | {-1, 0, 1} synthetic | {} |

Reason for the shape: scan-edge exclusions remove x=11 at y=63/66 and remove the z-edge cells at those y edges. At y=64/65, x=10 retains the z edges, while x=11 retains only interior z cells. All admitted x≥10 cells are outside the border at east edge 10. The player has a valid support overlap: center x≈8.99, width 0.6, z=0.5; the footprint overlaps both Stone cells `(8,63,0)` and `(9,63,0)`.

Running `VoxelSet.forEachBox` in source order (x outer, then y, then z; take first contiguous z run, extend x, then extend y) yields these nine boxes, in order. The first floor-connected box consumes `(8,63,0)`, `(9,63,0)` and the adjoining `(10,63,0)` cell, then stops at y=64. It touches query.minY but misses the two rays raised to y≈64.51. The first later boxes at z `[-1,0]` and `[1,2]` extend vertically through y=67 but miss ray z≈0.2/0.8. The first ray hit is item 5:

1. `[8,11] × [63,64] × [0,1]` — no ray hit (top only touches query floor).
2. `[10,11] × [63,67] × [-1,0]` — no ray hit.
3. `[10,11] × [63,67] × [1,2]` — no ray hit.
4. `[10,11] × [64,66] × [-2,-1]` — no ray hit.
5. **`[10,12] × [64,66] × [0,1]` — first ray hit.**
6. `[10,11] × [64,66] × [2,3]` — no ray hit.
7. `[10,11] × [66,67] × [0,1]` — rays are below it.
8. `[11,12] × [64,66] × [-1,0]` — no ray hit.
9. `[11,12] × [64,66] × [1,2]` — no ray hit.

Item 5's center `(11,65,0.5)` floors to candidate center block `(11,65,0)`. AutoJump uses it only to derive the `t` sample positions; it does not read that center block for clearance.

## Actual movement, cap and exact AIR reads

The move wrapper calls AutoJump immediately after resolved movement and passes the actual new-minus-old x/z as floats. For the stated actual x `8.89→8.99`, dx≈0.10 and dz=0; squared delta≈0.01 exceeds the input fallback threshold 0.001. Speed II adds a 0.4 multiplier to base player movement speed 0.1, so queried speed is `f=0.14`. The lookahead is `p=max(f*7,1/l)≈max(0.98,0.10)=0.98`; candidate endpoint is approximately `8.99+0.10+0.98=10.07`. The raised side rays are at z≈0.2 and z≈0.8, both inside item 5's z range. These calculations use actual displacement as the witness premise; they do not claim every input tick yields exactly 0.10.

For Jump Boost II (amplifier 1), `o=1.2F+(1+1)*0.75F=2.7F`, so the source loop evaluates t=1 and t=2. At item 5, `s=66`; feet y=64 gives `u=2`. Since `0.5<u≤2.7`, the source writes `ticksToNextAutojump=1`. Jump Boost I gives cap 1.95 and fails; no Jump Boost gives 1.2 and fails. Jump Boost III (amplifier 2) cap 3.45 also passes and adds t=3 reads, but it is not needed for the timer arm.

The exact physical-AIR requirements for the minimal JBII arm are:

- direct initial head checks: `(8,65,0)`, `(8,66,0)`;
- cap samples: `(11,66,0)`, `(11,67,0)`;
- additional t>1 head-clearance check: `(8,67,0)`.

With JBIII, also require `(11,68,0)` and `(8,68,0)`. Synthetic collision substitution does not change these direct `getBlockState(...).getCollisionShape(...).isEmpty()` reads; the coordinates must hold physical AIR.

Speed II can affect horizontal movement/lookahead. Jump Boost changes AutoJump's height cap and changes `jump()` velocity if a jump occurs; it does not alter this current tick's vertical physics because the witness move is supported and nonjumping. The current query's y bounds remain `[64,65.8]`. The timer is set after the local tick's countdown; at the next local tick a positive timer is decremented to zero while that tick's `input.jumping` event is set. A two-second command effect gives 40 ticks at amplifier 1 or 2; status-effect ticking decrements it once per living tick, and it stays positive through the synchronous AutoJump read with this duration.

## Border flag and limitations

For border east edge x=10, x=8.89/8.99 and z=0.5 lie strictly inside both expanded bounds (incoming outside flag false) and eroded bounds (incoming flag true). `WorldView` computes the collision boolean before conditionally updating the stored flag. In either incoming state this call has `collidesWithBorder=true`; the false state remains false, and the true state transitions to false. The computed true boolean still reaches collision collection. Thus the incoming flag does not distinguish this witness; gating directly on its historical incoming value would differ from source.

Acceptance is limited to the stated scan contents/chunk availability and 1.13.2 collector. The output is a source derivation, not a tested game trajectory, a pair coverage audit, or proof that a specific ordinary input always yields the stated displacement. The 1.13→1.13.1 query-call/collection change remains a separate patch boundary.
