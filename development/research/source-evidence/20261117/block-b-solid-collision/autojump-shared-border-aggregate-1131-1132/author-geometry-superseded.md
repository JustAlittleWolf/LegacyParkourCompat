# 1.13.2 aggregate border AutoJump disposition — r2

## Corrected disposition

The earlier `aggregate-timer-disposition.md` (SHA-256 `29fef119e5e6fe88f889d3999998623abab76643efabfcedd1f827ffbe81baac`) used an incorrect clipped y=66 AABB. Preserve that receipt as written; this r2 note supersedes its aggregate-box, timer, and height-cap conclusions.

For the exact integer-height east fixture, the first qualifying 1.13.2 aggregate border AABB is **[10,11] × [63,67] × [−1,1]**, as independently derived in `review-1122-1132-114-autojump-border-cutchains/east-fixture-final-audit.md` (SHA-256 `dc5d6d7de8e3dcb265655fe66ac9f297f7422b90c003f0dd6db58e72c442f1bf`). Its maximum is y=67, giving a rise of 3 from integer feet y=64. Jump Boost II's cap 2.7 **cannot** arm the timer.

A separate, conditional vanilla map-command witness **can** arm it: Jump Boost III (amplifier 2) raises the source cap to 3.45. With physical AIR at every clearance sample below, the first box leaves s=67, u=3 passes the cap, and the source writes the one-tick timer. This applies to maps that lawfully supply the vanilla effect through the vanilla command system; it is not a survival-potion-only claim.

## Exact evidence identity

1.13.2 Ornithe Feather Gen2 build.2; ready manifest SHA-256 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`; client SHA-256 `3410887ba652f25792c7675bfaf9140e73b60e93cfbf113a803f8a98cb05c0f9`. The source files below were checked against that manifest.

- `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` (SHA-256 `2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29b29d8d9bf42bf`), `move`/AutoJump, lines 871–973: immediate post-move call; gates; 1.2F cap plus 0.75F per amplifier level; ray query; sample loop; timer write.
- `net/minecraft/world/WorldView.java` (SHA-256 `9577360b1a1bdde9280f0b72593e5e6dde45d9a54719cd7ff933f3ecd31b971f`), lines 112–143 and 170–198: finite scan, loaded-chunk/corner predicate, synthetic cells, and stable outside-flag selection.
- `net/minecraft/block/shape/VoxelSet.java` (SHA-256 `fe39ea60ac27e595280627a4d15ea93a69292da5870b5ef34a1d7d7283a267c6`), lines 200–255; `VoxelShape.java` (SHA-256 `d95b0607647da11595e24e1e26e9615cb7da7705ea7885908544362df62275c1`), lines 94–112; `BitSetVoxelSet.java` (SHA-256 `2048daa3a95ddd567a70476862e9b2dde135b91f49b61d22a4ae3aeed98322aa`), lines 61–75: greedy emission, conversion to AABBs, and stored-cell bounds.
- `net/minecraft/entity/Entity.java` (SHA-256 `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269`), lines 190–191 and 473–679: default dimensions, movement collision resolution, and `onGround = collidingVertically && requestedY < 0`.
- `net/minecraft/entity/living/player/PlayerEntity.java` (SHA-256 `4ed22f6c5a3c55d67eed782070ac722201df4d624adbc90779f1fd29c2876633`), lines 159 and 1488–1489: base movement speed 0.1 and AutoJump's queried speed attribute.
- `net/minecraft/world/border/WorldBorder.java` (SHA-256 `7593d7a8df153f2a039a5b38cca684e1e15529b80ffde743151419e125590718`), lines 22–24: x=10 is outside when the border's maxX is 10.
- `net/minecraft/server/command/EffectCommand.java` (SHA-256 `4e425cecbb0b9873c5b229beb6b6835c043d17b1709b7881aeeab21b950dc2ab`), lines 67–130; `StatusEffectInstance.java` (SHA-256 `4e32f77b5e656de2f07b8ec2851dde4c3ee107f773984d2c03774f0c24a33e93`), lines 28–40, 91–121: command amplifier range, seconds-to-ticks conversion, instance storage, and once-per-tick duration decrement.
- `net/minecraft/entity/living/LivingEntity.java` (SHA-256 `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c`), lines 335 and 546–571: status-effect ticking from base tick.

The exact same-geometry independent reconstruction is preserved in the audit cited above. Its finite cells are x=8..11, y=63..66, z=−2..1. The admission predicate is `(!xEdge || !yEdge) && (!yEdge || !zEdge) && (!zEdge || !xEdge)`. At x=10 and interior z cells −1/0, both y edge rows 63 and 66 pass. The contiguous run at x=10, y=63, z=−1..0 is the first set run. The greedy emitter cannot extend it in x because x=11/y=63 is excluded at the simultaneous x/y edge, but extends it through y=63..66. Offsetting the emitted voxel box yields world AABB [10,11]×[63,67]×[−1,1]. Its center floors to (10,65,0), and both rays at z=±0.3 intersect it. Earlier/later cells cannot displace it because AutoJump breaks on the first qualifying AABB.

## Reachable post-move state and effect lifetime

Use a stationary border centered at (0,0), size 20, with east edge x=10. Put one full support block at (9,63,0). Keep physical AIR in the query region and at all sample coordinates below. The player width is 0.6F; position the grounded contact center at x=10−(0.6F/2), approximately 9.7, z=0. Its AABB touches the synthetic border at x=10. With the player outside-border flag false, the expanded-border test is true and collision substitution is active.

A normal downward collision establishes the grounded post-move state without assuming `onGround` beforehand: start at feet y=64.1 over the support, then make an ordinary non-sneaking move into the wall while falling with requested vertical displacement −0.2. The floor clips that displacement to −0.1; the wall clips eastward displacement to zero. Entity.move then sets `collidingVertically` true and `onGround` true because the requested vertical displacement is negative. The resulting state is x≈9.7, feet y=64, z=0; LocalClientPlayerEntity.move immediately calls AutoJump with actual dx=dz=0. Preserve nonzero eastward input and a yaw/look direction passing the source dot gate. The documented zero-displacement fallback projects 0.7 blocks at base speed 0.1. Set the AutoJump timer to 0 before this call; do not sneak or ride.

To supply the required effect without an expiry race, a vanilla command-enabled map may apply Jump Boost with duration 2 seconds and amplifier 2 (the 1.13.2 `EffectCommand` accepts amplifier 0–255 and converts seconds to 20-tick duration). Its new instance has duration 40. `StatusEffectInstance.tick` decrements duration once per LivingEntity base tick; AutoJump reads the effect later in that same player tick, and no effect tick runs inside the synchronous `move` → `autoJump` call. Thus a synchronized instance that begins the tick at 40 is active at duration 39 when AutoJump reads it; even if the instance has already consumed another tick before this move, it remains active. A command map is the explicitly bounded applicability: ordinary potion-only availability does not establish this amplifier.

Before the call, timer=0 and the player is airborne; after the preceding collision-resolved move, timer is still 0 and onGround is true. AutoJump computes `o=1.2+(2+1)×0.75=3.45`. It tests t=1,2,3. The first AABB center floors to (10,65,0), which is used to calculate the t samples; the code does not read that center block as a clearance sample. The t samples are (10,66,0), (10,67,0), and (10,68,0). The additional head-clearance `blockPos` starts at (9,65,0), then is advanced once at t=2 and again at t=3, sampling (9,66,0) and (9,67,0). Together with the initial head reads, the exact distinct physical-AIR set is:

- (9,65,0), (9,66,0), and (9,67,0);
- (10,66,0), (10,67,0), and (10,68,0).

The x=9,y=65 sample is the initial head read; x=9,y=66 is both the second initial head read and the extra t=2 sample; x=9,y=67 is required by t=3. The support at (9,63,0) remains solid. All query cells x=8..11 use chunk x=0; z=−2..1 use chunks z=−1 and 0. Load those chunks. Synthetic collision substitution is for collision collection only: direct `getBlockState` samples read the physical AIR listed above.

With those reads empty, s remains the first AABB maxY=67 and u=3. Since 0.5<u≤3.45, AutoJump writes `ticksToNextAutojump=1`. Position remains x≈9.7, y=64, z=0. On a subsequent client tick the ordinary timer countdown applies; this is an immediate one-tick arm, not a persistent timer claim.

## Corrected height bound and border flag

For feet y=y₀+f (integer y₀, 0≤f<1), the first x=10/interior-z run covers scan rows from y₀−1 through `ceil(y+1.8)`. Thus its top is `ceil(y+1.8)+1`, not the top of a corner-clipped full-z row. Its rise is:

- u=3−f when f≤0.2;
- u=4−f when f>0.2.

The minimum possible rise approaches 2.8, so Jump Boost II cap 2.7 never suffices for this aggregate first box. Jump Boost III cap 3.45 suffices for f≤0.2 and for f≥0.55; the latter threshold is subject to the source's float rounding exactly at the equality boundary. Integer feet (f=0) gives the witness above.

At x≈9.7 with outside flag false, the border is tested expanded by one block, so the collision query is active and the flag remains false. With outside flag true, the border is inset by one block, x≈9.7 fails the containment test, the flag remains true, and synthetic border collision checking is disabled. A true-flag entity inside the inset would instead transition to false; it is not a stable true-flag witness.

## Scope

This resolves only whether the 1.13.2 aggregate synthetic border can itself arm AutoJump. It is a source result, not gameplay verification or a general pair-coverage verdict. The Jump Boost III witness is conditional on a vanilla command-enabled map supplying that effect; under a no-command or potion-only restriction, this source analysis establishes no aggregate-border timer witness.

