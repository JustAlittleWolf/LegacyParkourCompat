# Pane collision product review r3

## Verdict

**ACCEPT** commit `a2c01d420b6328811210f7bba668a5bb2ca6fc5d` (`Implement bounded 1.12 pane collision components`) for the bounded 1.12 provider/collector operation. The reviewed commit has parent `f462c4db35cba2cb7eb201ec5080d2a2a51c8ddb`.

## Source bindings

- Selector audit: `pane-full-alias-audit-r2/audit.md`, SHA-256 `CDA0060136C96785F3009A7918F5E82040A99814631EC82EAA633AC806BA32FF`.
- FaceShape supplement: `pane-262-faceshape-bridge/inventory-supplement.md`, SHA-256 `C14E1E7D7109BEE7F8AB2CCC72AA44E3673485B765FB458AC802478CB08D2E97`.
- Corrected 1.12 ID crosswalk: `pane-faceshape-overrides/legacy-block-id-crosswalk.md`, SHA-256 `9C2CDC0CFA4A1A6CE37E1B4AC22AB19183E3006968ADC3EA4C7F896D2FF6E486`.
- 1.11.2 component/source audit: `pane-neighbor-1112-audit/audit.md`, SHA-256 `A8C1A9EAA21B109F96DB4C1A2D89BCC9233182EB5F30107A4EC9BB0EFE62259C`.

All four digests were recomputed against the current files during this review.

## Review findings

- `PaneNeighborFaceShape` uses the corrected finite 454-ID 1.12 crosswalk and its audited 185-member base-`SOLID` set. The explicit pane, slab, and stair selectors correspond to the audited historical rules. In particular, the old snow layer resolves to current `snow` and returns false; old full snow resolves to `snow_block` and is in the base-solid set. Unlisted/current-only IDs return empty, allowing the native pane shape to remain authoritative.
- The pane provider recomputes the four neighboring states and emits the center post followed by north, south, east, and west arms. The movement collector checks strict per-component intersection with the query and returns a `ComponentCollisionShape`; its clip hook delegates to the existing strict single-box clipper. This preserves component order and does not derive movement components from the union shape.
- The shared dispatch change returns the native shape when a resolved shape hook explicitly returns empty. That is needed for unknown-neighbor pane fallback, where invoking the pane collector on the native union would change provider semantics. The current other collector/shape-hook pairings do not depend on an empty provider result to perform additional collection: the other movement collectors are brewing-stand providers, whose applicable shape hook is present on the bounded old and 1.12 ranges or whose collector returns the native shape. This review found no conflicting current consumer.
- The V1_11_2 and V1_13 provider/collector fences return native behavior. V1_11_2 does not claim the audited default-state cube selector as complete arbitrary-state pane geometry; V1_13 does not claim the grouped pane operation outside the accepted 1.12 source contract. For selected V1_12, resolver ordering chooses the V1_12 pane provider and component collector. The changes use the existing hook interfaces and class-local variant factories; no duplicate axis-clipping hook or exact-profile gate was added.

## Limits and checks

This accepts the static integration contract for the accepted source range only. It does not establish input/tick reachability, arbitrary neighboring-state parity beyond the audited selector, or runtime movement parity. `git diff --check` on the commit range passed. No build, tests, game launch, or runtime check was run.
