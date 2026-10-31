# Erratum 4 — final census reconciliation

This erratum supersedes the Shulker closure status in `report.erratum-3.md` while preserving that immutable file and its source observations. The independently reviewed Shulker formula appears equivalent for equal inputs and the vanilla progress route uses coarse 0.1F increments, but this exact dynamic block-entity provider remains the one eligible callsite not independently closed in this census. Keep `ShulkerBoxBlock.getShape` → `ShulkerBoxBlockEntity.getBoundingBox` → `Shapes.create` **OPEN** pending an independent member/provider consequence audit. Do not treat the formula-only reconstruction here as pair acceptance.

The current-box border query callsites are **closed for the AABB encoding transition** by the separately source-bound, hash-verified addendum `../collision-query-review/20261031-current-border-query-addendum.md`. Its scoped verdict covers both `Entity.collide`'s deflated current player box and `CollisionSpliterator.worldBorderCheck`'s inflated/deflated current player box; it keeps the independent WorldBorder shape geometry delta separate. Its adjacent SHA sidecar matches the report on disk.

Other final dispositions remain:

- `CollisionSpliterator.<init>` displacement-expanded block query: independently accepted conditional 1.16.5→1.17 LocalPlayer axis-response witness, source-level and equal-input bounded, no runtime trajectory claim.
- `EntityGetter` mapped entity-provider AABBs: paired no-difference for equal raw provider AABBs, with the bounded boat/Shulker entity-kind census and clip equivalence in `entity-shape-query-witness/report.md`.
- `Entity.isColliding` current player AABB from block-break-ack rollback: paired no-difference for the AABB encoding transition; all Player X/Z dimensions force continuous fallback in both versions. The review's 1.17 hashes bind to embedded `.decompile-provenance.json` via its correction erratum.
- `ShulkerBoxBlock` dynamic provider AABB: one remaining eligible census callsite open as stated above.

The callsite ledger inventories all one-argument `Shapes.create(AABB)` occurrences in the exact three source trees and every callsite file matches the declared source manifest. That is a complete API-use census only; it is not a full changed-member/tick/provider pair audit and does not freeze the pair.
