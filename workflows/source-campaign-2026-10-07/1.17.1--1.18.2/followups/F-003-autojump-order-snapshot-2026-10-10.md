# F-003: Auto-jump uses a different first collision candidate order

- Older version A: 1.17.1
- Newer version B: 1.18.2
- Mechanic / coverage slice IDs: T-AUTOJUMP-ORDER
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: client-controlled local player meeting `canAutoJump` guards; candidate order can change the next-tick jump input and direct player jump response
- First changed stable release: 1.18
- Runtime validation: not performed
- Snapshot revision: additive dependency-closure supplement; preserves the previously accepted snapshot bytes

## Paired evidence

Exact Mojmap source from the ready publications:

- A `LocalPlayer#move` / `updateAutoJump`: lines 884–1008, `LocalPlayer.java` SHA-256 `c9a91cb6cb57806bc8d22e5bfe2d97daaf21d5d2a48f34a6e2c53164c61c5812`.
- B `LocalPlayer#move` / `updateAutoJump`: lines 880–1014, `LocalPlayer.java` SHA-256 `99c2d18bcd23243afb8f95c5bafb21fb0be7ea04aacbb14fcf7be7ced2c9c095`.
- A `CollisionGetter#getCollisions`: lines 57–59, block shapes then entity shapes; SHA-256 `eb707479e75cf200731df4546a546fb984be33cf3e4a8e17d3065a916497f747`.
- B `CollisionGetter#getCollisions`: lines 67–75, entity shapes then block shapes; SHA-256 `ed62e6b800ec057c1269d3cc4e2fb7c0cc2037370223fac99f41371e000c6b5c`.
- A/B entity query filters, concrete collision geometry, readiness identities and source hashes: [additive closure memo](F-003-autojump-order-closure-2026-10-10.md).

The first intersecting candidate AABB sets the candidate height from `maxY`, runs the candidate clearance checks, and terminates the scan. The timer is set only when the resulting rise is `> 0.5` and `<= q` (normally `q = 1.2`).

## Reachability and dependencies

`LocalPlayer#move` calls `updateAutoJump` after movement. Its guards require auto-jump enabled, timer `<= 0`, on ground, not staying on a ground surface, not a passenger, moving input, and block jump factor `>= 1`. Both entity paths use the player's collision predicate and the candidate bounding box; B adds a spectator filter that does not reject the ordinary non-player Boat witness. Bounded dependency closure is recorded in the linked memo.

## Source-level witness and consequence

With the guards satisfied, no Jump effect (so `q = 1.2F`), a vanilla Boat AABB (registered dimensions `1.375F × 0.5625F`, collidable with the player) and a FenceBlock collision AABB (maximum height `24/16 = 1.5`) can each intersect a different one of the two horizontal probe segments inside the swept query. Keep the checked space above both candidates clear. In A, the first qualifying block candidate is the fence: its `1.5` rise exceeds `q`, so no auto-jump timer write occurs before the loop breaks. In B, the entity candidate is visited first: its `0.5625` rise is above `0.5` and within `q`, so `autoJumpTime = 1` is written before the fence is reached.

On the next local-player `aiStep`, a positive timer forces `input.jumping = true`. The LocalPlayer server-AI step copies that input to the jumping state; `LocalPlayer#isEffectiveAi` returns true, so the grounded `LivingEntity#aiStep` branch calls `jumpFromGround` when its jump-delay guard allows. Thus the candidate order can change direct player movement under the stated world and state preconditions. This is an exact-source witness, not runtime validation or a claim about a specific map.

## Version boundary

The paired 1.17.1 source is block-first. Exact 1.18 source is entity-first; the relevant `LocalPlayer.java`, `CollisionGetter.java` and `EntityGetter.java` bytes are identical in ready 1.18.1 sources, and 1.18.2 retains the entity-first order. The first changed stable release is 1.18.

## Handoff

This additive snapshot is submitted for blind source re-review. It does not alter the prior accepted finding snapshot or the accepted full-pair audit. Implementation status is not assessed; runtime validation remains not performed.
