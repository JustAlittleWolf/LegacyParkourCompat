# F-SOUL-SAND-SPEED implementation binding

## Disposition

**Covered by existing implementation; no code delta identified for this accepted finding.** This is a finding-specific endpoint binding, not pair completion or runtime validation. The accepted first-changed-release boundary remains unknown inside `(1.14.4, 1.15.2]`; this review does not assign it to 1.15 or 1.15.1. Other block-speed-factor consumers and the wider pair inventories remain open.

## Immutable finding and acceptance

- Finding: `1.14.4-1.15.2/F-SOUL-SAND-SPEED`, snapshot commit `4a0c35f2008d785867c00360a7b72726e3506435`, path `workflows/source-campaign-2026-10-07/1.14.4--1.15.2/findings/F-SOUL-SAND-SPEED.md`, SHA-256 `a43043bed85d233478d323621ed97ef7ac27e7fc9a286f439dc2cd5280604f7e`, Git blob `57a16764abd6aaa3b5bdd1864c92170a74899bb9`.
- Independent acceptance: commit `960ce5644ceacd8e27bd3cb9145bd4eaf221379c`, path `workflows/coverage-review-2026-10-07/early.md`. Its exact snapshot decision accepts the Soul Sand player-speed delta, bounds the first changed release to `(1.14.4, 1.15.2]`, and leaves runtime and full pair inventories open.
- Read-only source publication: `build/movement-campaign-2026-10-07/ready/<version>/mojmap`. Both readiness records state `ready`, exact `versionId` and `versionMetadataId`, and `mojmap` mappings. A (`1.14.4`) readiness JSON SHA-256 is `ec7b6a6d9ba72f8a19908527977c970a8e2b3007b0e0b4323a725fb5943d3043`; its source/artifact/diagnostics manifest hashes are `af98406f3d4ed31494fbdce1078d1f3df3f712501878f9a0e9ad5879bdf23c3b`, `308cc33ef6dffc047432ade6affc88eccc9de94a736ee9a98571fd92915c2970`, and `d404e46059270c9283f6767edb6050959548253d2725443c95b6ab9cb49b1a61`. B (`1.15.2`) readiness JSON SHA-256 is `64a0e40b784d525ce50937c63e465fc4f564efb1f7af17f2bac3c77a36907be0`; its source/artifact/diagnostics manifest hashes are `cb7fd93f8730d8b2813857744f8572060535c7de44fcee86222acd1ceb6f43e7`, `208ab867640097a0c188e452de4876934deb217d75ac726d358cfa6730260406`, and `1ab6c473dbbdf2c590a7a8870befbd404e634fe7c1b0ae6a796b7b5f9c63d4d5`.
- The cited A/B `Entity.java`, `Blocks.java`, `Block.java`, `SoulSandBlock.java`, `LivingEntity.java`, and `Player.java` files were rehashed against their respective source manifests; all matched. The cited hashes for the principal pair evidence (`Entity`, `Blocks`, `Block`, `LivingEntity`) match the finding and acceptance record. No source generation or cache write was performed.

## Historical operation and code binding

In 1.14.4, `Entity.move` runs `checkInsideBlocks()` and then has no block-speed-factor multiplication. The exact A-side `SoulSandBlock.entityInside` callback scales the entity's stored movement as `multiply(0.4, 1.0, 0.4)`. In 1.15.2, `Blocks.SOUL_SAND` registers `speedFactor(0.4F)`; `Entity.move` applies the block factor to X/Z after `checkInsideBlocks()`. Its block lookup checks the current block and movement-support block, with water/bubble handling, and the 1.15.2 `Player` override returns the superclass factor only when not ability-flying or fall-flying. The ordinary ground player travel route reaches `Entity.move`; the source finding's direct player preconditions are therefore satisfied for grounded, non-flying travel on or supported by Soul Sand.

`change/v1_14/SoulSandOverlapChange.java` supplies both sides needed to reproduce the accepted old behavior on the modern engine:

- `afterCollision` repeats the source callback's box bounds (`floor(min + 0.001)`, `floor(max - 0.001)`), loaded-chunk gate, X/Y/Z scan order, Soul Sand selection and literal `0.4, 1.0, 0.4` velocity multiplication. The `EntityMixin` callback is injected immediately before the modern `getBlockSpeedFactor()` call, after `Entity.move` has run `checkInsideBlocks()`, so it runs at the equivalent stage before the modern factor multiplication. One multiply is applied for each intersecting Soul Sand block, matching the old callback loop's behavior.
- `movementSpeedFactor` returns `1.0F` when the current or support block is Soul Sand, suppressing the newly registered 0.4 property so the old callback is not applied twice. It otherwise returns the native value and leaves water/bubble and other non-default block-factor cases on that path; those other factors are outside this finding's accepted closure.

The bridge keeps the mixin general. `EntityMixin` invokes `AfterCollisionBehavior` before the factor call and redirects the factor call through `EntityInvoker`, passing the vanilla value to `BlockSpeedFactorBehavior`. `MovementRuntime.find` returns no behavior when emulation is disabled or the entity is not a player, so both hooks preserve the native path in those cases. The historical class does not inherit another release's implementation.

## Resolver and applicability

`MovementChangeCatalog.registerV1_14` registers the same `SoulSandOverlapChange` instance independently under `BlockSpeedFactorBehavior` and `AfterCollisionBehavior`, matching its two interfaces. The class annotation is `@MovementChange(emulates = ParkourVersion.V1_14)`. `ParkourVersion.V1_14` contains 1.14 through 1.14.4; the next selectable group is `V1_15` (1.15 and 1.15.1), followed by `V1_15_2`.

`ChangeResolver` keeps changes whose `emulates()` is newer than or equal to the selected profile and chooses the closest such key. Consequently, selection of V1_14 resolves this old-side behavior; selection of V1_15 or later does not resolve this V1_14 entry, so the native newer behavior remains. For older selections, this is the closest registered change for these hooks. That describes the repository's resolver behavior; it does not establish which 1.15.x release first changed vanilla. The accepted evidence compares endpoints only, and this report makes no claim about exact 1.15/1.15.1 source behavior.

## Checks and remaining limits

- Reviewed exact source snapshot and acceptance identities, readiness JSONs, manifests and cited source-file hashes.
- Compared source arithmetic, callback order, box/chunk gates, player applicability, hooks, catalog registrations, resolver direction and vanilla fallback against the existing class and mixin.
- No implementation code was changed. No tests, build, runtime launch or game validation was run.
- Runtime parity is unverified. Full pair completion, the first changed release within the accepted interval and non-Soul-Sand block-factor closure are not claimed.