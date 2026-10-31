# Candidate review — bed wake position search

## Verdict

**Accept the bounded source implementation for the 1.16 bed-wake search and its 26.2 call site, with one integration blocker for full movement parity:** the algorithm currently queries current collision shapes. It cannot claim historical stand-up results for providers whose 1.16 shapes differ from the current provider. The candidate itself acknowledges the shape-view gap; no such context is implemented in this commit.

This verdict is bound to candidate commit `b7ff4451f9c604673066f5fc2e9300dcada96640`, parent `732fbd7efaca399f8271dd0e1173e1b0b216c7f8`. It does not assert runtime parity.

## Source identity

Read-only canonical source root: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft`.

- 1.16.1 Mojmap ready manifest SHA-256: `76fdd121070c2012f48cbdf5cf0c78490116afc94117c30b0858eedcd6ab9754`.
- 1.16.2 Mojmap ready manifest SHA-256: `10d312ca29ee48e25f4727adafb9fcfbd741dcebe81b343d3dcffa071952a7a7`.
- 26.2 unobfuscated ready manifest SHA-256: `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894`.
- 1.16.1 `BedBlock.java` SHA-256: `47fff9ab0073a8970ffd0a0db3120903b43e4246bf0f932687bdee9e6e6de063`.
- 1.16.2 `BedBlock.java` SHA-256: `32cff55596d10fb86bb8bd3fb9769df0df9826b8bd449fd4d70fb4d6f3868ced`.
- 26.2 `BedBlock.java` SHA-256: `22f515c272d52eebd75456e4a78eb7f38708d682f8d2cb5fdc170e7c3105af2b`.
- 26.2 `LivingEntity.java` SHA-256: `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a`.

The supplied alias report `.native-source/20261031/bed-wake-alias56/report.md` supports the complete 1.16/1.16.1 alias claim (including file identity). Its SHA-256 is `9caf34c33200f7dad8b2782f22677d6e31a6405faad81c78b2f5b30e1b324a49`. The supplied 1.16.1→1.16.2 boundary review is `.native-source/20261030/1152-1165-finish43/bed-wake-witness-independent-review/renewal-boundary-508f3510.md`, reviewed finding SHA-256 `508f35100e17eadb6a0d94b688d899b9012c01f08bf20816af680f0fc885d4bc`.

## Technical findings

- The 1.16.1 `BedBlock.findStandUpPosition(EntityType<?>, LevelReader, BlockPos, int)` performs `m = 0..1`, then x outer and z inner across inclusive 3×3 ranges. For `i = 0`, it returns the first accepted result. Candidate `BedWakePosition.findStandUpPosition` reproduces that ordering and this first-result behavior. The historical `i > 0` ranking behavior is not part of the `stopSleeping` call being ported.
- `getStandingLocationAtOrBelow` preserves the initial `max(Y) > 0.4375` rejection, the exact down-search gates (`y >= 0`, original-to-current delta `<= 2`, empty shape), empty-support rejection, support Y expression with `2.0E-7`, original candidate drop limit `> 2.0`, center construction, collision-before-danger short-circuit, and downward-expanded `AABB` dangerous-block test. The supplied predicate matches the 1.16.1 local-player dangerous set; for current-state disagreement the candidate abandons its whole historical search and invokes native search. Its `unsupported` result propagates immediately, so a partial old result is not combined with current yaw ordering.
- The current provider query is the material gap: each initial/support shape and `noCollision` check uses current `CollisionGetter`/`BlockState` collision data. To claim 1.16 parity, integration must supply a position- and level-aware historical collision-shape view for initial-height rejection, downward support search, support height, and full player-box collision. It must preserve the current `BlockState` identity and vanilla world data while replacing only the historical shape result where that block's 1.16 geometry differs. `getBlockStates(expandedBox)` and the danger predicate must remain evaluated over the same candidate AABB and in the historical order; disagreement must continue to restart the complete native query before returning any earlier candidate.
- The bounds construction matches for this operation: 1.16.1 `EntityType.getAABB(x,y,z)` uses half-width on X/Z and height above Y; current `EntityDimensions.makeBoundingBox(position)` uses the same expression. The 1.16.1 player type is sized `0.6 × 1.8`; 26.2 `EntityType.Builder` defaults to the same dimensions. Thus the candidate’s dimensions form and current player dimensions match the inspected sources, with no separate dimensions blocker. Shape-provider parity remains unproven.
- At 26.2, `LivingEntity.stopSleeping` directly calls `BedBlock.findStandUpPosition(this.getType(), this.level(), bedPosition, facing, this.getYRot())`. The candidate redirect descriptor matches the actual static helper descriptor `(EntityType, CollisionGetter, BlockPos, Direction, float) -> Optional`. It intercepts only `LocalPlayer` that is exactly `Minecraft.getInstance().player`; all other entities call native search. This matches the client local-player scope and keeps server/remote-entity movement native.
- Old fallback remains the caller's `above + (0.5, 0.1, 0.5)` when the search returns empty. The candidate does not alter that fallback. The new 1.16.2 yaw-ordered helper path is not reproduced by the 1.16 implementation; the explicit 1.15.2 identity boundary preserves native behavior below the proved old group. No upper barrier was added.

## Limits and verification

This review checks source and commit contents only. No product checkout changes, staging, build, test, decompile, or game launch were performed. Historical shape providers and historical dimension differences remain unverified and block a whole-world parity claim until their contracts are integrated.
