# Collision provider and step-operation audit: 1.7.10 → 1.8

## Evidence identity

Read-only sources are the exact Ornithe Feather trees at `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.7.10\ornithe-feather` and `...\1.8\ornithe-feather`. The permitted source-only pool records provenance manifest SHA-256 values `11bada441ebe07661402d1c40e1c490b78b279ecaa084f9a4dc3ae2dd54caf6f` and `52c8312c34a1a491d4a200c84e90f5d8d95c7e5caa9571f864d8e70f9737db05`; Feather build 2 on both sides. I independently recomputed the hashes listed in the accepted drafts below. The root pair has no runtime parity claim.

## Review verdicts

| Candidate | Verdict | Basis |
|---|---|---|
| Carpet collision bounds | Accept | Carpet is registered in both versions. 1.7.10's provider returns a zero-height box; the 1.8 `CarpetBlock` declares no collision provider and inherits the base 1/16-high bounds. The strict intersection predicate rejects the old box. |
| Cake collision bounds | Accept | The paired state/metadata bite count preserves X inset. The top changes from 7/16 to 1/2. |
| Ordinary living-player step height | Accept | `LivingEntity` constructor changes `stepHeight` from `0.5F` to `0.6F`; a grounded ordinary player can clear a bed obstacle only with the newer lift under the documented witness. |
| Step eligibility on airborne sneaking landing | Accept | 1.7.10 has the extra `(var20 || eyeHeightSneakOffset < 0.05F)` gate, absent in 1.8. Local sneaking sets 0.2F before the movement call; `Entity.move` multiplies it by 0.4F before evaluating the old gate, leaving 0.08F. On a descending airborne landing tick, `var20` is false and the old gate rejects the otherwise shared step conditions. |
| Successful sleep response position/box | Accept as a direct player-state response; separate from entry eligibility | Both `BedBlock` interaction paths call `PlayerEntity.trySleep`; after eligibility checks, the client-executable mutation sets a 0.2×0.2 size and a different position. The old eye-anchored AABB bottom is bedY+0.7375 plus `eyeHeightSneakOffset` after eye height is set to 0.2F; the new base-anchored AABB bottom is bedY+0.6875. This is an immediate direct position/box write, not a claim about bed-entry eligibility. Ordinary travel while asleep and downstream collision effects were not established here. |
| Stairs neighbor-dependent collision decomposition | Reject as a delta; paired behavior is equivalent for old states | Metadata maps `0/1/2/3` to `EAST/WEST/SOUTH/NORTH` via 1.8 `Direction.byId(5 - (metadata & 3))`. Pairwise, the half-box, inner mask, outer-step mask and base/inner/step add order match; block-state `SHAPE` is derived and is not part of old metadata. The old/new `addCollisions` call sequence is identical. |
| Other registered block providers | No further candidate accepted in this bounded audit | Fresh case-sensitive declaration scan found 44 collision-provider classes per side. Membership changes only from old `CarpetBlock` override to 1.8 `BannerBlock`; banner is absent from 1.7.10. The allowed provider inventory records paired dispositions for the remaining common providers. This worker directly retraced the candidate definitions, stair branches and player query/dispatch path; it did not independently re-derive every same-geometry formula in all 42 other common classes. |

## Provider registration and dispatch

1.7.10 registers cake, stairs and carpet in `block/Block.java` at lines 271/313/401/456/457/473/541/564-566/630/650-651/662 (depending on concrete stair and block). 1.8 registers the matching old-era blocks at lines 868/887/944/962/965-966/980/1019/1031-1033/1066/1077/1079/1089. The carpet registry key/id is 171 on both sides; cake is id 92 on both sides. The player collision route remains `Entity.move` → `World.getCollisions` → virtual `Block.addCollisions` → virtual shape provider, with the base gate adding a shape only if non-null and strictly intersecting. Source references: old `entity/Entity.java:383-558`, `world/World.java:972-1014`, `block/Block.java:836-847`; new `entity/Entity.java:370-608`, `world/World.java:873-946`, `block/Block.java:292-303`.

The complete member enumeration from the permitted provider inventory was cross-checked against a fresh signature scan of both block source trees: 44 declaration-bearing classes on each side, 43 common, with Carpet absent as an override on the new side and Banner newly present. Banner has no older block-state counterpart and is not an emulated historical provider.

## Stairs comparison detail

Old `StairsBlock.isInnerStair` and `setStepShapeForCollisions` (`:88-164`, `:167-249`) were compared branch-by-branch with the 1.8 state-based equivalents (`:218-296`, `:299-383`). The metadata/state mapping preserves all four cardinal facings and top/bottom half. The same neighbor coordinates, same-half check, direction tests, mask changes, shape coordinates, boolean return and conditional shape mutation occur in each corresponding branch. Both collision providers perform base box, base box again after inner-shape selection, optional step box, then restore full block bounds (`:252-262` / `:386-396`). Shape-state virtual properties in 1.8 are derived from neighboring blocks; no delta to the collision decomposition was established.

## Residuals

- Full per-provider behavioral proof for every common provider was not repeated here; see the allowed `provider_registration/provider-inventory.tsv` and `collision_shapes/slice-report.md` for the prior source-only enumeration and detailed paired dispositions.
- Moving-block box aliasing remains a source-level side-effect candidate from the allowed slice report; no player movement effect is established.
- Entity-list collision behavior, downstream standing/displacement readers, complete tick consumers of ground/collision flags and the other callback lanes remain outside this bounded audit.
- No trajectory was run. Step witnesses establish reachability from source gates and shapes only.
- These accepted drafts are not pair freeze, full movement coverage, implementation acceptance or runtime parity.
