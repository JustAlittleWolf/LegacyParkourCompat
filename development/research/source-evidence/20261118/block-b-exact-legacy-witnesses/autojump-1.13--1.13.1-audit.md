# Independent audit: AutoJump collision enumeration witness

Status: COMPLETED

## Bound claim and evidence identity

Reviewed the corrected legal source witness at `b-source-autojump-partial-113-legal-witness/1.13--1.13.1-autojump-collision-enumeration.md`, SHA-256 `62b424ae55069feecd84f6119ba3c1c18832ee0865a2fb1b2dd4c298667588eb`.

Read-only canonical evidence roots were exactly `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/{1.13,1.13.1,1.13.2}/ornithe-feather`. All three source-manifest hashes match the supplied identities: `0509c2614b0bb0e616e2ebc6382f8eb5d031b5d86b834a4a6ed7fd96b6d68f1a`, `21770b01ff3bf443db2e9e1b6df7db25d2fd6b7a72a86228259269c932d3abba`, and `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`. Ready markers bind each source tree to its exact release and Ornithe Feather gen2 build.2 artifacts. The primary `LocalClientPlayerEntity.java` and `WorldView.java` hashes match the witness for 1.13 and 1.13.1; the cited SlabBlock, EndPortalFrameBlock and BlockPos hashes match across all three. Relevant method bodies are present and intact.

## Recomputed source result

The proposed feet pose is y=64, directly on the y=63 support floor. At x=.59 before and x=.69 after the move, a .6-wide player spans respectively [.29,.89] and [.39,.99], so it remains clear of the slab beginning at x=1. The post-move headroom test floors `(x, box.maxY, z)` with `box.maxY=65.8` to `(0,65,0)`, then checks `(0,66,0)`; both are empty. The x=2 frame is not part of that gate. `move` calls AutoJump after `super.move` with the actual post-minus-pre x/z displacement. The supplied +X displacement and nonzero forward input satisfy the enable, cooldown, ground, sneak/riding, input and facing gates.

With `dx=.1F`, no Jump Boost, `f=.1F`, `g=(float)(dx²)`, and normalized +X, the source's `p=max(f*7,1/l)` is about .7. From post-position x=.69, the extended endpoint is about 1.49. Growing the search box by width .6 in X/Z gives approximately x=[.09,2.09], y=[64,65.8], z=[-.1,1.1]. Thus both slab shapes overlap the query. The no-eye frame at x=2,y=65 also overlaps its upper edge region (its body reaches y=65.8125), but it is above the ray height.

The rays are at y=64.51 and z=.2/.8, run from x=.69 to about 1.49, and intersect the x=1 top-slab box `[1,2]×[64.5,65]×[0,1]`. They stop before x=2, so they do not intersect the second slab or frame. In 1.13, WorldView accumulates partial shapes with OR across its column/volume loops, optimizes the resulting union, then joins the full-cube aggregate; the adjacent slab region is one ray-hit component with x extent [1,3]. Its center `(2,64.75,.5)` floors to `(2,64,0)`. At t=1 the no-eye frame's 13/16 body sets the candidate top to 65.8125, exceeding the 1.2 limit, so AutoJump returns without writing the cooldown.

In 1.13.1, WorldView returns streamed per-block shapes plus its cube aggregate. `BlockPos.iterateRegionMutable` advances X first, so at the same y/z the x=1 slab is encountered before x=2. The first intersected component is therefore x=1 slab; its center floors to `(1,64,0)`. The t=1 lookup at `(1,65,0)` is empty, leaving candidate top 65 and height difference 1.0. The strict timer checks accept that value (`> .5F` and `<= 1.2F`) and set `ticksToNextAutojump=1`.

The same scoped claim is verified in 1.13.2: its AutoJump player and WorldView source hashes are identical to 1.13.1, and the relevant slab/frame/BlockPos source hashes also match. This verifies only this bounded behavior across the stated source inputs.

## Verdict and limits

**COMPLETED — claim supported.** The revised witness's initial legal pose, dimensions, query bounds, rays, enumeration order, center-derived lookup and timer writes are consistent with the exact sources. The fixture does not embed the player and does not require the frame to block a ray. No remaining flaw in this bounded witness was found.

This is static source reasoning, not runtime evidence. It does not establish a downstream jump or trajectory result, other AutoJump cases, full-pair tick coverage, or any patch cutover before/after the exact releases checked. It binds the corrected witness revision and supplied manifest identities only.
