# 1.20.1–1.20.2 S10 roster closure r2

## Scope and evidence identity

This consolidates only the frozen 135-path block-shape roster for exact Minecraft 1.20.1 → 1.20.2 Mojmap source. It closes roster coverage for direct player solid collision and support-shape selection; it does not claim full movement-graph closure, acceptance beyond the cited independent verdict, implementation, or runtime parity.

Both release-specific source-manifest SHA-256 values are consistent across the source reports and independent verdict, and independently match the actual `mojmap.sources.sha256` file and ready marker:

- 1.20.1: `858b56764113e591c60d09bc63e4d90f9c1d67a705645f0c793e8b28f2378465`; client `56b71336d2b4fdffd197f56595b0da93e32a946f78f382a299b8f4b92758bb0f`.
- 1.20.2: `e439ff7e1d3f1d31e01be1edd141f94d917a9dc7fa0de5f69904544cbbe62887`; client `fa1a19be56a506426308abbc1cad85f299a7fc6dae4335559351ba0246713fda`.

The source-manifest hashes are release-specific, not equal to one another. The 19 initial files were rechecked against both source manifests (38 path/version checks); the four slice reports record source-manifest checks for the remaining 116. Each roster path appears exactly once across the initial set and four ordered slices. The roster source is `S10-roster-audit.md` (SHA-256 `1f7e1dad38c90ac7d6ba5afea73f57a5a47c7013cb923b97f17a42791f34b77c`).

## Coverage binding

The initial roster contains 19 unique paths: 18 audited collision-provider/framework paths and `PinkPetalsBlock.java`, reviewed separately as a changed outline excluded by its no-collision registration. Its remaining 116 unique paths partition exactly into four disjoint slices:

| Artifact | Ordinals in frozen remainder | Paths | Actual report SHA-256 |
|---|---:|---:|---|
| Group 1 `audit.md` | 1–29 | 29 | `56b27005c9173c3a790bf920d1e6377117fe61259c90deb74047909092e61b58` |
| Group 2 author `audit-author-r2.md` | 30–58 | 29 | `76541a08c88a3c3b3fe8e098d20124b93ca793d7ee18d3ffca34e4f5f8bac66a` |
| Group 3 `audit.md` | 59–87 | 29 | `4fbd240d50a84873092996550acbd0b8bf8aed4f356ddf214fc8409d5a3dd545` |
| Group 4 `coverage.md` | 88–116 | 29 | `c6fe087ed26f8f397a710f607e95ce79898444ae0f85a479fef2e0da49da3b61` |
| Independent diode verdict `verdict.md` | Review of group 2 diode claim | — | `99ca9dd1fb5b8fda6d13e43605c539e3872538a6c0104f059b84f8a8a680b522` |

I parsed the explicit path rows from those exact artifacts, using group 2's author recovery file only (not the overwritten reviewer `audit.md`). The four slices contain 116 entries, 116 unique paths, zero duplicates, and exactly match the 116-path initial remainder: zero missing and zero extra. Combined with the initial 19, the roster has 135 unique entries with zero overlap. Group 4 places `entity/BeehiveBlockEntity.java` last under its recorded ordinal path order.

## Direct solid collision and support result

Across this roster, the reviewed same-state collision-shape formulas and direct directional/support selectors have no confirmed player-movement difference. The initial 18-file subset reported no collision-provider difference; the Pink Petals shape change does not reach solid collision. Groups 1, 3, and 4 reported no direct solid-shape or support-selector difference in their slices. Group 2's diode operations preserve the same direct rigid-support predicate and plate geometry; its separate state-update gate is an effective-state caveat below, not a changed shape for the same state.

The relevant shared dispatch stays `hasCollision ? state.getShape(...) : Shapes.empty()` in both versions. Confirmed changed outlines on no-collision registrations (including Pink Petals, Bamboo Sapling, Big Dripleaf Stem, Bubble Column, Pitcher Crop, and Redstone Wire where identified in the slices) cannot contribute to player solid collision through that dispatch. Skull `POWERED` state in 1.20.2 affects the animation counter; skull and wall-skull shape selectors still ignore it.

## Effective-state caveats; not direct same-state shape changes

- **Diodes (group 2, independently reviewed):** 1.20.2 factors `DiodeBlock.canSurvive` through `canSurviveOn`, retaining the same rigid UP-face support predicate and position. `RepeaterBlock.updateShape` and `ComparatorBlock.updateShape` add a DOWN-neighbor guard that can return AIR when support is not rigid; neither guard exists in 1.20.1. The independent verdict finds that ordinary server support removal already removes these blocks synchronously in 1.20.1. The added route matters for a narrower write with shape propagation but without the direct-neighbor update bit (for example flag 2); client packet state updates use flag 19, suppressing that shape propagation. No vanilla player-reachable event with the needed server flags was established. Treat this as a narrow server/world effective-state route, not a client historical-physics or same-state collision finding.
- **Shulker boxes (group 4):** the block's direct `getBlockSupportShape`, dynamic `getShape`, moving-entity displacement, and progress math remain the same. Neighbor-shape notification moved from `triggerEvent` on open-count transitions to the animation tick's first transition and endpoint, and 1.20.2 also calls `Level.updateNeighborsAt`. Secondary adjacent-state/terrain reactions may therefore be timed differently; that effective-state scope remains unclosed and is not a direct same-state collision change.
- **Other state updates in the roster:** the group reports identify changes to crop persistence/update behavior, Redstone Wire support updates, Hopper update flags, and Skull inherited `POWERED` state. They do not establish changed solid collision for a same state; no-collision registration or unchanged shape selectors account for the collision dispositions. Their broader world-state lifecycle is outside this roster closure.

## Limits

This closure binds the exact 135-path S10 roster and listed reports. It does not resolve Shulker secondary neighbor-state effects, establish a vanilla reachable trigger for the narrow diode server route, inspect intermediate patch releases, or establish runtime parity.

