# Additive coverage proposal: serialized chest-pair migration and chunk-upgrade reconstruction

Propose a third, separate source-slice item in the existing `1.12.2--1.13.2` pair coverage record. This is migration-path coverage only. It adds no finding ID, changes no collision-provider verdict, and does not close pair-level coverage or audit status.

## Accepted source evidence

- **Migration audit:** `build/source-drafts/chest-pairing-serialized-migration.md`, SHA-256 `15338DAC1941F3072E7583AD38DFE4DBBE52F5CBB7EA5EB591AFD7B64DDE15E6`. Independent review by `source_12111_always_flying`: PASS bound to this exact revision.
- **Bounded state-producer context:** `build/source-drafts/chest-pairing-state-coverage.md`, SHA-256 `05DC658DC7D7D473E6D468F0E1B1CC6E8A4CB2D17BD677610AD35B5E473E96E6`. It separately records placement, neighbor-produced chest state and persistence; its previously open migration slice is the subject of the accepted supplement.
- **Exact source identity:** local 1.12.2 and 1.13.2 Ornithe Feather Gen2 build.2 endpoints. The migration audit records both ready/provenance/manifest identities and hash-checked source files.

## Proposed additive coverage item

- **Scope:** normal and trapped chests in the exact 1.12.2 → 1.13.2 pair; serialized legacy blockstate conversion, trapped chest block-entity identity repair, `UpgradeData` candidate/edge bookkeeping, and its gated chunk-upgrade pair reconstruction.
- **Bounded result:** 1.13.2 converts legacy chest IDs 54/146 and horizontal facing metadata into single chest blockstates while recording chest positions and boundary sides for `UpgradeData`. Under the observed chunk post-processing gate, a dedicated fixer reconstructs complementary pair `TYPE` for same-block horizontal neighbors where both the current and neighbor `TYPE` are `SINGLE`, with matching facing and perpendicular orientation; it handles normal and trapped chest blocks separately. A separate trapped-chest fix repairs the block-entity ID. This is conditional reconstruction from old adjacency and facing, not recovery of an old serialized pair-side value.
- **Status boundary:** source-slice coverage of the inspected conversion and reconstruction path only. It makes no migration failure, collision-equivalence, movement, implementation, or runtime-parity claim.

## Preserve sibling scopes and open limits

- Keep this as a third sibling to [state-producer/persistence coverage](build/source-drafts/chest-pairing-coverage-proposal.md), SHA-256 `970798620F3D4A18BA6F98C686818A4B5D9630ED581F1E2C7837092623E21485`, and [provider-geometry coverage](build/source-drafts/chest-pairing-geometry-coverage-proposal.md), SHA-256 `FE138321E9161DE30D97B9839598867F16943599690EFF48C0730BDFCAF3959B`. Do not merge their scopes, replace their entries, or infer geometry/provider equivalence from this migration trace.
- Keep malformed or stale state, multiply-adjacent layouts, missing or nonstandard block entities, and chunks outside the demonstrated status/eight-neighbor upgrade gate open. The source audit does not exercise real save files or runtime migration.
- Preserve the accepted provider review's valid-updated-pair precondition and the geometry item's legal-state preconditions. Keep collector behavior, player reachability/trajectory, implementation, runtime parity, complete pair dependencies and independent full-pair audit open.
- Add only after the sole integrator supplies the exact current live `1.12.2--1.13.2` coverage-row revision. Preserve all existing fields and sibling coverage; retain partial pair status.

This proposal stages only the reviewed migration evidence. It does not update the shared backlog or any product/research record.

