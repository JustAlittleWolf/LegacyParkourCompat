# Alternate step-query evidence — 1.13 → 1.13.1 slab witness

Immutable source-only candidate for independent audit. Scope is only the alternate step-up query/caller for the existing witness in `development/research/findings/1.13--1.13.1/movement-collision-shape-aggregation.md`; the ordinary primary query finding is already accepted and is not repeated here.

## Exact source identity

Both canonical markers identify Ornithe Feather Gen2 `1.13+build.2` / `1.13.1+build.2`, with ready exact-version source sets. Marker source-manifest SHA-256 values: 1.13 `0509c2614b0bb0e616e2ebc6382f8eb5d031b5d86b834a4a6ed7fd96b6d68f1a`; 1.13.1 `21770b01ff3bf443db2e9e1b6df7db25d2fd6b7a72a86228259269c932d3abba`.

Relevant references:

- 1.13 `net/minecraft/entity/Entity.java:580-620, 650-679`: primary query, step gate, restored-box alternate query and candidate selection.
- 1.13.1 same `Entity.java:580-620, 650-679`: same callers/gates/order, with `calculateMaxOffset` over the collected stream.
- Both `net/minecraft/entity/living/LivingEntity.java:185`: constructor assigns `this.stepHeight = 0.6F`.
- 1.13 `net/minecraft/world/WorldView.java:113-196`: old block aggregation and swept query construction.
- 1.13.1 `net/minecraft/world/WorldView.java:112-163`: stream collector and swept query construction; partial slab candidates are emitted in X-fast scan order.
- 1.13.1 `net/minecraft/block/shape/VoxelShapes.java:194-205`: stream clipping tests strict `abs(maxDist) < 1.0E-7` before each next shape.

## Caller reachability

In both releases the primary `Entity.move` query receives the witness `(box, 2, 0, 0)`. After clipping, the caller computes `bl = this.onGround || o != y && o < 0.0`; the alternate is entered only for `this.stepHeight > 0.0F && bl && (n != x || p != z)`. The existing witness's primary clip has `n != x` on both sides: 1.13 retains `4.9999999918171056e-8`, while 1.13.1 clips X to zero. Therefore the alternate call is concretely reachable if the same actor is grounded. A living player receives `stepHeight = 0.6F` from its `LivingEntity` constructor on both sides. Without grounded state, this zero-Y witness has no downward-clipped Y term to make `bl` true, so it does not reach this alternate route. This is a conditional caller path, not an unconditional consequence of the box and vector alone.

## Alternate query and candidate result

After primary clipping, the method saves `r=x`, `s=y`, `t=z` and the post-primary box, then restores the original `box`; it sets the alternate vector to `(n, this.stepHeight, p)`. For the grounded living-player witness this is the restored original box and `(2, 0.6F, 0)`. The float value promoted to double is `0.6000000238418579`.

The slabs at `(2,64,0)` and `(3,64,0)` remain outside the start-shape exclusion and inside the swept query. The added positive-Y sweep extent contributes no additional nonempty collision shape under the witness's no-other-shapes setup. Candidate identity/order is the same in both versions: these two partial slabs are encountered with X=2 before X=3 (old X-outer scan; new X-fast scan). The 1.13 aggregate and 1.13.1 ordered partial stream differ structurally, but that does not change this alternate candidate result.

With the restored box's `minY=64.1`, the upward candidate rises by the full float step height: its lower face becomes `64.70000002384186`, above both bottom slabs' top `64.5`, so the alternate candidate can move X by the full `2`. At the endpoint, the actor overlaps the slab at X=3. The selected candidate's downward settle is therefore `64.5 - 64.70000002384186 = -0.2000000238418579` (approximately; double arithmetic from the fixture gives `-0.200000023841852`), ending at `minY=64.5`.

Crucial distinction: that negative value is the settle leg stored in the method's local `y`; it is not the net position displacement from the restored starting box. The box was first moved up by `+0.6000000238418579`, then down by approximately `-0.200000023841852`. The net Y position delta is approximately `+0.400000000000006`, and the final lower face is exactly the slab top `64.5` under the fixture arithmetic. The alternate's selected overall displacement is `(2, approximately +0.4, 0)` in both releases. It beats the primary unstepped candidate in both. Thus the primary query's horizontal result difference is masked when this grounded positive-step branch is taken; the alternate query/candidates/order and resulting selected movement have no 1.13-to-1.13.1 difference for this fixture.

## Bounded conclusion

Settled no-difference for the named alternate caller/query under the named fixture plus the explicit grounded LivingEntity state: same restored box, same alternate movement operands, same two partial candidates in the same order, and same selected physical endpoint. This does not claim parity for unrelated step-up requests, other nearby collision shapes, or a general modern collector adapter.
