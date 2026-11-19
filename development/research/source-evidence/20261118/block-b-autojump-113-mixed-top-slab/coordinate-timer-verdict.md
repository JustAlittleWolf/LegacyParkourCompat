# Independent audit: 1.13.1/.2 coordinate AutoJump timer addendum

## Verdict

**The bounded coordinate witness is source-supported conditionally.** Under the addendum's stated fixture and callback preconditions, the old 1.13.1 and 1.13.2 source path resolves the requested move to `(x=.69,y=8,z=1.1)`, emits the dry TOP slab collision box as the first intersecting candidate, leaves the candidate's above-cell query empty, computes `u=1.0F`, and writes `ticksToNextAutojump = 1`.

This verdict is for the supplied single movement request and fixture. It does not show that player input or a complete ordinary tick necessarily produces this movement in general. Missing fixture conditions remain explicit below. The earlier raw-ID/state alias verdict is unchanged and not reopened here.

## Immutable evidence identity

The assigned addendum `coordinate-timer-addendum.md` SHA-256 is `4844699b22b41f1dd7989692a04f220000fd873aa8e6d5c70a4efaaccf79b550`, matching the requested identity.

Both exact roots are 1.13.1 and 1.13.2 Ornithe Feather Gen2 build.2. Their ready-marker source-manifest digests match the adjacent manifest files. I checked 34 cited full source-file hashes across the two manifests; all matched. I also recomputed the addendum's normalized member hashes for the move/AutoJump body, entity collision movement, block-collision stream, world entity append, entity-collision stream, block-position iteration, inverse-square-root helper and slab shape; all matched on both versions.

## Independent source trace

- `PlayerEntity.java:450` sets ordinary standing dimensions to `0.6F × 1.8F`; `PlayerEntity.java:159,1488-1489` establishes the base movement-speed attribute as `0.1F` and `getSpeed()` as that attribute. The numeric reach calculation assumes no modifier that lowers the far endpoint below the slab; this is a fixture precondition, not a conclusion from the requested displacement alone.
- `Entity.java:581-598` clips Y before X before Z. With feet at y=8.1, a full support top at y=8, and request `(+.10,-.20,0)`, Y resolves to `-.10`, then X remains `+.10` because the post-move maxX is about `.99`, still short of the x=1 blocks. `Entity.java:676-680` sets `onGround` when the downward request clips. The ordinary step retry condition compares original and resolved horizontal motion, so with no horizontal clipping that retry is skipped. The callback therefore sees post-move `(x=.69,y=8,z=1.1)` and `(dx=.1F,dz=0)`.
- The player's post-move shape maxY is `8 + 1.8F`, so AutoJump's direct column probes floor to `(0,9,1)` and `(0,10,1)`. They must be actual AIR. The obstacle's `(1,9,1)` is a separate later query and must also be AIR. The block-coordinate floor for the slab's shape-box center `(1.5,8.75,1.5)` is `(1,8,1)`.
- In `LocalClientPlayerEntity.java:882-980`, `g` is above the `0.001F` fallback threshold for the nonzero resolved `dx`, so raw input is not used to synthesize direction, although the outer nonzero-input gate still applies. With base `f=.1F`, the source's float cast gives `g=.010000000707805157`; the cast inverse-square-root value is about `9.982504844665527`; `p=max(f*7F,1F/l)` is `.699999988079071F`. The yaw `-90°` at pitch 0 produces a +X rotation vector via `Vec3d.fromRotation`; its horizontal dot with +X passes the `m < -0.15F` rejection gate.
- The callback's broad query covers the slab. Its two probe segments are at y about `8.5099999905`, with z about `.8005248383` and `1.3994751617`. The latter intersects the TOP slab box `[1,8.5,1]`–`[2,9,2]`; the former intersects the full stone at `(1,8,0)`.
- `SlabBlock.java:24-59` returns a half-height `TOP_SHAPE` for TOP. `WorldView.java:123-151` emits non-full collision shapes in its first stream and accumulates only shapes identical to the full-cube singleton into the later `IntVoxelShape`. The slab shape is not the full-cube singleton, even though the slab's separate `isFullBlock` predicate treats TOP as full. Thus the slab appears before the full stone/cap aggregate. `World.java:1755-1757` appends entity collision shapes after block shapes. Given the fixture's stated absence of other non-full shapes and other entities, the slab is the first yielded box that intersects either probe, and AutoJump breaks after it.
- That box has `maxY=9.0F`; `blockPos2.up(1)` is `(1,9,1)`. With no Jump Boost, `o=1.2F`, so the loop visits only `t=1`; the AIR state leaves `s=9`. The `t>1` standing-clearance branch is not reached. Since post-move `getShape().minY=8`, `u=(float)(9-8)=1.0F`, which passes `u>0.5F && u<=1.2F`; the source writes the timer to 1.

## Required fixture/callback preconditions and scope

The source conclusion depends on: y=7 full support covering some part of the pre-move footprint; the exact requested move and starting position/dimensions; the relevant chunk cells being loaded; actual AIR at `(0,9,1)`, `(0,10,1)`, and `(1,9,1)`; no additional partial collision shape preceding the slab; no nearby other entity; default world border; no Jump Boost; a speed attribute that keeps the AutoJump endpoint reaching x=1 (the stated unmodified `0.1F` baseline does); AutoJump enabled; timer `<=0`; grounded after resolution; non-sneaking, unmounted, nonzero raw input; and the +X-facing yaw gate.

Some of these are ordinary fresh-client defaults in source (`autoJumpEnabled=true`, timer's Java-default zero, baseline player dimensions/speed), but a later callback's current state can differ. The addendum's explicit player-column AIR and callback assumptions are therefore accepted as fixture conditions, not inferred from the shorter scene description. The witness establishes one conditional callback result, not a universal ordinary-tick movement outcome or runtime parity.

No remaining source-level blocker exists under those stated fixture preconditions. If the intended claim is unconditional for an actual map/tick, the source-only audit cannot supply the unprovided world states or player input/attribute/cooldown state; those must be fixed as part of that claim.
