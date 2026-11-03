# Fluid motion-predicate/provider census — 1.19.4 to 1.20.4

## Bounded result

The old-to-new predicate replacement is present in the shared fluid implementation: 1.19.4 reads `BlockState.getMaterial().blocksMotion()`; 1.20, 1.20.1, 1.20.2, 1.20.3 and 1.20.4 read `BlockState.blocksMotion()`. This census found no confirmed equal-state predicate mismatch in the checked registration/provider cohorts. It does not establish full pair coverage or a universal negative across every registered state.

There are three changed-predicate consumers in the fluid package: `FlowingFluid.getFlow`, `FlowingFluid.canHoldFluid`, and the lava tick's neighboring-block gate in `LavaFluid.tick`. `getFlow` can change the current fluid push vector directly; the other two affect fluid propagation or fire placement, which can change later player fluid contact or nearby world state. `canHoldFluid` has additional exclusions and a `LiquidBlockContainer` dispatch that must be considered separately (see `caller-comparison.tsv`).

## Alias/helper cohort closure

The source-derived all-six registration intersection is 997 IDs. The previously unclassified set of 240 registrations with no direct `Material.*` token in the 1.19.4 registration expression was resolved through exact property copies and helper bodies. I re-read the resulting lineage table and counted its rows and outcomes:

| Cohort/result | Count |
|---|---:|
| All-six common resource IDs (source roster) | 997 |
| Alias/helper lineage rows | 240 |
| Old `blocksMotion=true`; each of the five modern providers also true | 229 |
| Old `blocksMotion=false`; each of the five modern providers also false | 11 |
| Alias/helper rows with a checked predicate mismatch | 0 |

The eleven false rows are the nine wooden buttons, stone button, and polished blackstone button. Their old `DECORATION` material is non-motion; modern helper properties set `noCollission()`, making the default cached collision shape empty and the state predicate false. The other 229 rows resolve to old motion-true materials and modern force-on or cached-shape outcomes that are also true. This includes the copied amethyst buds: modern legacy-copy lineage carries `forceSolidOn`, so their result is true. The counts and each release's basis are in `../1194-1204-pool54/fluid-material-lineage/material-lineage.tsv`; lineage/provenance analysis is in its adjacent `audit.md`.

## Remaining provider decision paths

The modern `BlockStateBase.blocksMotion()` in all five 1.20 sources is `owner != COBWEB && owner != BAMBOO_SAPLING && isSolid()`. `calculateSolid()` checks `forceSolidOn`, then `forceSolidOff`, then a missing cache or empty collision shape, then the collision bounds rule (`getSize() >= 0.7291666666666666 || getYsize() >= 1.0`). Therefore a dynamic shape callback does not by itself supply a motion-predicate value: the force flags and null-cache branch determine the value before dynamic geometry is sampled.

The remaining named dynamic-property cases that could have been confused with provider behavior were reconciled by their property/material flags for each 1.20 release:

| IDs/family | 1.19.4 material bit | 1.20–1.20.4 modern gate | Predicate result | Census disposition |
|---|---:|---|---|---|
| 17 shulker boxes | true | dynamic plus `forceSolidOn` | true | Equal; `.forceSolidOn` precedes null cache. Their dynamic collision geometry is separately equal at the checked endpoints. |
| bamboo | true | `forceSolidOn` | true | Equal; callback geometry is not the motion predicate input. |
| moving piston | true | `forceSolidOn` | true | Equal; callback geometry is not the motion predicate input. |
| pointed dripstone | true | `forceSolidOn` | true | Equal; callback geometry is not the motion predicate input. |
| powder snow | false | dynamic, no force override, null cache | false | Equal; modern null-cache branch returns false. |
| scaffolding | false (`DECORATION`) | dynamic/no collision property, null cache | false | Equal for `blocksMotion`; its custom `getCollisionShape` override remains relevant to collision-provider parity and does not override `BlockStateBase.blocksMotion()`. |

The six rows/categories above are present in `dynamic-shape-ids.csv` for every modern release (20 force-on IDs, one dynamic-cache-null ID, and one no-collision ID per release). The still-open bamboo, moving-piston, pointed-dripstone and powder-snow *collision geometry* domains do not leave a motion-predicate value open; they are intentionally not claimed closed as collision providers by this audit.

## Reconciled candidate dispositions

These previous proposals are not valid witnesses and must stay withdrawn:

| Candidate | Correct source result |
|---|---|
| Amethyst cluster/buds | Old `Material.AMETHYST` is true; every 1.20 registration/copy inherits `forceSolidOn`, so modern state predicate is true. |
| Panes/bars | Every state retains a full-height center component; the modern `Ysize >= 1` clause makes it true, matching old true. |
| Cobweb/bamboo sapling | Old material is false because `Material.Builder.noCollider()` clears the bit; modern `blocksMotion()` explicitly returns false for both IDs. |
| Trapdoor average-size claim | The full provider comparison found the equality case at the actual modern threshold; no mismatch is established. |
| Scaffolding collision shape | Its custom collision override bypasses the default `hasCollision` check for collision queries. That does not change the old-vs-modern `blocksMotion` result: old `DECORATION=false`, modern dynamic null-cache result false. |

See `../1194-1204-pool54/orchestrator-audit/fluid-review-status-erratum.md`, `fluid-provider-witness-correction.md`, `fluid-census-provenance-location-correction.md`, and `fluid-blocks-motion-audit/truth-table-supplement.md` for the historical correction chain. In particular, do not revive the original amethyst, cobweb, bamboo-sapling, or pane witnesses.

## Exact-source identity and scope

Canonical read-only root: `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft`, Mojmap. Exact manifest/provenance identities and source entries are recorded in the pool's `fluid-predicate-registration-census/source-roster.md` and the corrected `fluid-census-provenance-location-correction.md`. I rehashed the compared `FlowingFluid.java` and `BlockBehaviour.java` files:

| Release | `FlowingFluid.java` SHA-256 | `BlockBehaviour.java` SHA-256 |
|---|---|---|
| 1.19.4 | `D84CEB3C0B83DA25FB68599E16CC2298106E745A9EF0C9F32F616D097A8EC56C` | `517AA4BD9875E9F7B3F5FBA472A84E82AFD1E11E35D3C83DC2677D8BBEB4997D` |
| 1.20 | `BA1DFF6CCB28B62760C74D66832D8FDBE7A1CDC37B9C5A0FF476BD200215C904` | `5F37987191165379C222C412949A0C80A904BF78A453035B1F9D86C6454510FA` |
| 1.20.1 | `BA1DFF6CCB28B62760C74D66832D8FDBE7A1CDC37B9C5A0FF476BD200215C904` | `5F37987191165379C222C412949A0C80A904BF78A453035B1F9D86C6454510FA` |
| 1.20.2 | `0F44B1AF25533DD8A1FBFEC13B279739341DB0C563568337A2825D3E46A1A5BB` | `A6D88726CE4642FC0A61F46A01347BB0B74887B61EF55AF7E7C76723789A0B57` |
| 1.20.3 | `0F44B1AF25533DD8A1FBFEC13B279739341DB0C563568337A2825D3E46A1A5BB` | `6284A7D08A6D0D475CD4FE8AEA62B35153AE8FBC0BCE5F51788389F679233B54` |
| 1.20.4 | `0F44B1AF25533DD8A1FBFEC13B279739341DB0C563568337A2825D3E46A1A5BB` | `6284A7D08A6D0D475CD4FE8AEA62B35153AE8FBC0BCE5F51788389F679233B54` |

The exact static slice is the shared fluid-package motion-predicate calls, their old material/modern property inputs, alias/helper lineage cohort and listed exceptional provider gates. The source sets still have legacy provenance roots for some releases as described by the corrected roster. This report adds no new accepted movement finding: the historical reports proposing witnesses were superseded or rejected. No collision-geometry closure, full pair freeze, intermediate patch-boundary proof, implementation, runtime parity, or global negative is claimed.
