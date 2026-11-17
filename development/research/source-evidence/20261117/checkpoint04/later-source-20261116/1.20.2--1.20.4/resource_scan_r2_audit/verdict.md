# Independent audit — 1.20.2–1.20.4 resource/tag scan r2

Verdict: **ACCEPT for the bounded partial source inventory.** This is not a pair-completion verdict and does not establish effective resource or tag membership.

## Bound input and source identity

- Report: `build/later-source-20261116/1.20.2--1.20.4/resource_scan_revision_r2/report-r2.md`
- Report SHA-256: `07A428AA62E5E65A9BE755616E2CCC9BAE853D51C2D402EA4148F734BE39D310`
- Exact canonical inputs: `decompiled_minecraft/1.20.2/mojmap/` and `decompiled_minecraft/1.20.4/mojmap/`; both declared ready for the exact version and `mojmap` mapping.
- Provenance sidecars: `decompiled_minecraft/1.20.2/mojmap.provenance.json` SHA-256 `1DAD9F725F6CB25B6568700FA0294F83B66EA9633B0D62ED354D7BD8B45D4F05`; `decompiled_minecraft/1.20.4/mojmap.provenance.json` SHA-256 `10914D9B7B3FC29DE066CA24F7D905E4D4F0A451A6C81729A2D1981ADDFC1385`.
- Ready metadata source-manifest declarations match the local manifest bytes: 1.20.2 `e439ff7e1d3f1d31e01be1edd141f94d917a9dc7fa0de5f69904544cbbe62887` (4,904 files); 1.20.4 `fd3c8668483e5ff208c847474f6cbd3952a909c4aa602ed98b24e3d11a9602a1` (5,048 files). This audit reused the prior r1 inventory checks for unchanged declarations, source inventory, artifact-ledger references and package-membership limits; it did not rehash either complete source tree.

## Corrected attribution

The r1 audit's requested correction is present and supported. `Entity.isStateClimbable(BlockState)` is at 1.20.2 `Entity.java:726–728` and 1.20.4 `Entity.java:727–729`. Its callers in the inspected file are movement-emission/step bookkeeping and `vibrationAndSoundEffectsFromBlock`, not `LivingEntity.onClimbable()`'s physical climbing predicate. The physical predicate begins at 1.20.2 `LivingEntity.java:1511` and 1.20.4 `LivingEntity.java:1508`; it reads `BlockTags.CLIMBABLE` at 1.20.2 line 1517 and 1.20.4 line 1514, with the reported trapdoor-ladder handling following it.

For the changed and directly cited paths, current file bytes match their source-manifest rows:

| Exact source file | 1.20.2 SHA-256 | 1.20.4 SHA-256 |
| --- | --- | --- |
| `net/minecraft/world/entity/Entity.java` | `d7ee49aaea5e862b92508e767562cabc8f10515605e8fb67565c8c8d5c23b01b` | `07383522bff169938136638ef8c3244ca511b56ca4266913524f99f9821331b9` |
| `net/minecraft/world/entity/LivingEntity.java` | `5c481da1ffc8684c4b30171c92e61fad751e6a3a708d5bc6486ac9f96ff69828` | `f7bc53db24c1798f19f9bd6f6356c86d5e560e9c8e8aac60decaf15cc785e07d` |

## Limits retained

The report accurately limits its conclusion to Java registry/tag declarations and selected source consumers. It leaves data-pack/default tag membership, effective synchronized membership, artifact bytes, broader changed registry entries, full consumer/resource closure, and pair coverage open. Its claim that the selected registry declarations are unchanged or that the listed keys were added is retained from the unchanged r1 inventory audit. No movement finding or pair freeze follows from this report.

No artifact extraction, build, test, runtime launch, product review, or Git-history inspection was performed.
