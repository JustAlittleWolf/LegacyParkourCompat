# Wall neighbor mask product wiring review

Verdict: **ACCEPT** for product wiring at commit `4adc85cbaced9115e8c55a8de03eff4b9576eb3c`, based on static inspection only. No source edits, builds, tests, game launches, or runtime checks were performed.

## Bound identity

- Product worktree: `.native-integrations/wall-neighbor-mask-20261029`, branch `fix/wall-neighbor-mask-112-20261029`; `HEAD` resolves to `4adc85cbaced9115e8c55a8de03eff4b9576eb3c`.
- Product commit parent: `962f98d4ece9e97b9b9581535f601858980f551d` (`docs: preserve reviewed movement source chains and readiness gap`), the requested immediately prior docs-only merge.
- Product commit changes six Java files: adds the 1.11.1 native resolver fence, 1.11.2 mask provider and hook, extends the existing collision-shape hook and V1_12 implementation, and wires the operation in `WallBlockMixin`.
- `git diff --check 962f98d4..4adc85cb` is clean. The product worktree also has one unrelated unstaged edit at `development/research/source-evidence/20261029/frontier-reviewed-chains/1194-1200-respawn-sprint-cache-closure/respawn-sprint-cache-and-player-info-game-mode.md`; it is not part of the reviewed commit.

## Evidence bindings

- Candidate follow-up review: `.native-source/20261029/wall-mask-candidate-review/follow-up-review.md`, SHA-256 `d4c0badff64b78d8ac331f6e332f1a8895d70ae935e83e9feac27e576a209a27` (verified).
- Accepted R2 supplement: `.native-source/20261029/wall-state-selector-closure/supplement-r2.md`, SHA-256 `3fc036aafd62b848e4f4cc3e60d85c26ad1142fff5b7296a5706eafefcdb9406` (verified); accepted R2 audit SHA-256 `d4e47725337f694672deff4baa57d85778580255175046027a1b3c1766092d0b` (verified).
- R3 source supplement: `.native-source/20261029/wall-state-selector-closure/supplement-r3.md`, SHA-256 `7bad1170df6e31864064e7f5e3b3d60d001cee31a973b48e19feee5302dd418d` (verified); independent accepted audit SHA-256 `193c141acd17742b3c5c7a4bf62b9f7addeeb9bda1f9d2f25242894dec421b35` (verified).
- Product seam plan identity supplied for this review: `.native-source/20261029/wall-mask-product-seam/plan.md`, SHA-256 `4b1f48ee0bd88d622d66ac88a57ba5033ed016d9e98f2e137c33c34cfd3b1607`. That path is absent from both the primary checkout and the target product worktree, so I could not independently read or recompute the plan file hash.

## Review findings

- **Resolver boundaries:** `ChangeResolver` selects the nearest change whose emulated version is not older than the selected profile; `CURRENT` resolves to none. For profiles through 1.11.1, `NativeBeforeWallNeighborMask` at V1_11_1 is the selected mask-hook fence and returns `suppliesHistoricalMask() == false`, preserving the existing native/shape path. For V1_11_2, `WallNeighborMask` is selected and supplies a mask only for the source-closed tuple. V1_12 and later do not resolve either mask provider, so the newly added overload receives an empty mask and delegates to the prior overload behavior.
- **Selector gates:** the V1_11_2 provider checks exactly the two mapped target wall keys and `WATERLOGGED=false`; it admits exact air or one of the fourteen R3-mapped stair keys with `WATERLOGGED=false`. The list includes `cobblestone_stairs` and `purpur_stairs`, and excludes the old alias `stone_stairs`. `isFalseWaterlogged` first checks property presence, then tests the false value; all fourteen stair keys and both wall keys have that property in the bound current-state contract. Current wall UP/side fields and stair directional/shape fields are not read, as the contract requires.
- **Whole-shape fallback:** any unsupported target or any unsupported cardinal neighbor makes `selectMask` return empty. `WallBlockMixin` immediately returns the original `vanillaShape` before height adaptation, legacy restored-connection handling, or the collision-shape hook. Thus it does not mix a partial historical mask with native sides and does not accidentally apply historical height on an unsupported tuple. The four neighbors are checked in north/east/south/west order; only a fully admitted tuple yields the complete all-false historical mask (`OptionalInt.of(0)`).
- **Historical shape use:** for a complete mask, the V1_12 collision provider bypasses `WallCollisionGeometry.mask(state)` and the restored legacy bits, selects the old table entry from the complete mask, and preserves the resolved historical Y extent. If the complete mask is absent, it delegates to the original behavior. This keeps the change bounded to the accepted 1.11.2 selector domain.
- **Hook/API wiring:** the new `WallNeighborMaskHook` is one independently resolved operation with `@MechanicType`, and each concrete implementation has `@MovementChange`, a public no-arg constructor, and no unrelated hooks. Client discovery recursively registers annotated hook interfaces implemented by annotated changes. The `WallCollisionShapeHook` signature extension is source-compatible through its default overload; the only production call site is `WallBlockMixin`, and all existing implementations retain their old behavior when the complete mask is empty. The lambda captures the single-assignment `completeHistoricalMask`, so it is effectively final.
- **Profile preservation:** the V1_11_1 fence preserves prior behavior for that boundary; V1_11_2 changes only the exact closed tuple; profiles V1_12+ have no active mask change and route the new overload to the pre-existing restored-bit/shape path. `CURRENT` remains unmodified by `ChangeResolver` and the mixin also requires a player collision context.

No product-wiring defect was found in the requested review scope. This review does not claim compilation or runtime parity; those checks were explicitly excluded from this assignment.

