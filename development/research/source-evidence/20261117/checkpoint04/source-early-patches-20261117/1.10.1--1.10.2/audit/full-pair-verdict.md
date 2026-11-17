# Independent full-pair source audit: 1.10.1 → 1.10.2

**Verdict: REQUEST CHANGES** for pair freeze. The changed-file inventory and the report's conclusion that no in-scope local-player movement delta was found are supported, but one changed dependency route is described incompletely and must be recorded before the coverage report is complete.

## Bound inputs

- Coverage report: `build/source-early-patches-20261117/1.10.1--1.10.2/coverage.md`, SHA-256 `A25A3B142686C31702CDED51F9AB192C06CF8F30BFAC54B890EC0F0D5DFEF71A`.
- Exact source identities: 1.10.1 mapping-local provenance SHA-256 `A9A50497930848BF0DDEC732B6D424E947F643A8FA6290E01425AC2BBFCE219F`; 1.10.2 readiness marker SHA-256 `4C6D236E1D06FFC365452C48E7DFA2BF2414BBF4EA67A24A7010C5583906A2D`; 1.10.2 source manifest SHA-256 `91B0F478ACB7B6F13463C35B268A30D2806F583402631A56F54CE2EB70D1EC71`.
- Neutral source contract: `build/source-inputs-110-112-20261117/queue.md`, SHA-256 `358D084A0CBEFC450B70224608845580B3854C64EDA90D15FACD8AB46682EC72`.

## Verified coverage

Both exact source manifests were checked against every local source file: 1,845 entries per release, with no missing files or mismatched hashes. The path sets match; the independently recomputed delta is 21 changed shared paths, with no additions or removals. The coverage report enumerates the full changed set.

The FlowerPot geometry claim is supported: its `getShape` returns the same constant `SHAPE`, and the block-entity virtual-property change does not alter collision geometry. `World.addBlockEntity` adds a client-side same-state notification; its `WorldRenderer` listener marks nearby chunks dirty. The DataVersion/schema-number changes add no movement transformation, the saved player version literal does not alter a player tick, and the remaining UI, render, release-text and save-version changes do not change local-player movement.

## Required coverage correction

The report says the `WorldRegion` branch in `FlowerPotBlock.resolveVirtualProperties` is the render-region access path. `WorldRegion` is also constructed by `PathNavigation` (`net/minecraft/entity/ai/pathing/PathNavigation.java`, lines 90 and 112) for pathfinding. `PathNavigation` is used by `MobEntity`, and is therefore a non-player pathing route; it is excluded from local-player movement under the project invariant. This does not establish an in-scope movement difference: FlowerPot `getShape` remains constant, and the relevant `WorldRegion` code is unchanged between these releases. However, the report should name this additional caller and classify it as non-player pathfinding rather than describing the branch as render-only.

Update the immutable coverage revision or add a bound correction supplement that documents this caller/exclusion. A fresh independent audit of that correction is required before pair freeze. This verdict does not infer acceptance or completion for either neighboring edge, implementation, or runtime parity.
