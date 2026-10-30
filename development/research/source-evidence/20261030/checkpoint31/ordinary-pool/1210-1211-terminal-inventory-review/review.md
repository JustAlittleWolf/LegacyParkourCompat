# Independent source-only review: 1.21.0 → 1.21.1 terminal inventory

## Immutable inputs and verdict

- Inventory SHA-256: `32902f4e8bf35ba375f9e3ae3d515e62e318cdc3dc1daca2ba0d76479678cd90` (matches the assigned pin).
- Source-audit SHA-256: `0c23a664552dd5fcfdfe12d715dfd3cfc25efcaba2ccef3eb12b3e3dc2f27b1d` (matches the assigned pin).
- Canonical Mojmap source trees inspected read-only: `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/{1.21,1.21.1}/mojmap`.

The changed-path inventory is substantially accurate, and its caution that the pair is not frozen is appropriate. The report needs a correction supplement before reuse: the tick order reverses two rows, one reachable local-player velocity terminal is missing, some “open” provider families are blanket-open despite exact whole-tree Java equality, the packaged-resource gate is stale, and one cited callback-audit hash is actually the closure hash. This review does not infer pair freeze.

## Changed-path review

I inspected exact diffs for all 17 paths. They group as reported: 3 version/report/protocol metadata paths, 8 command/chat selector paths, 4 server-side `onRemove` reorderings, and 2 block-entity paths. The scope/disposition is correct at that level. Add one detail to the `BlockEntity.java` disposition: `fillCrashReportCategory` now delegates Name formatting to a new helper; it preserves the same reported string and is crash-report presentation, not movement behavior. The constructor/set-state validation and `LevelChunk` invalid-entity removal/recreation are correctly identified as the movement-relevant part.

The exact-source audit establishes equal 5,363-file Java path sets and only these 17 changed hashes. Its selected-root checks support byte identity for the unchanged tick, travel, query-entry and selected client-response roots. Thus unchanged Java shape providers, callback bodies, travel branches, packet handlers and registration code are not open version-difference candidates solely because their transitive call graph has not been enumerated. The four changed callbacks and the changed block-entity path remain bounded by the cited ordinary-valid-state closure; keep the legal/stale shulker block-entity question open.

## Corrections to ordered terminals

1. **Rows 2 and 3 are out of order.** In 1.21.0 `Player.tick` calls `updateIsUnderwater()` at line 275 before `super.tick()`. The inherited tick reaches `Entity.tick`/`baseTick`; the local input/control pass is later in `LocalPlayer.aiStep` (line 644), where `input.tick` occurs at line 664, before `super.aiStep()` at line 804 reaches the travel logic. Move the pre-travel world-sampling row ahead of local input/control, or split the sampling row if needed. The row’s `LocalPlayer.updateIsUnderwater` override is also reached through the earlier Player tick call.
2. **Add entity pushing as a post-travel movement terminal.** `LivingEntity.aiStep` calls `pushEntities()` after travel (`LivingEntity.java:2705`). Its client branch queries `Player` entities and dispatches `doPush` (`:2746–2749`); the reciprocal push path can reach `LocalPlayer.push` and change local-player delta movement. This Java path is unchanged across the endpoints, so it is a covered terminal rather than a remaining version-specific dependency. It is distinct from block collision clipping and should be named in the inventory.

## Narrow the open dependency list

Replace blanket requests to enumerate every registered collision/support provider, every callback body, every travel branch provider, and all packet consumers/producers with an explicit equivalence boundary:

- The full Java path/hash comparison plus the 17 exact diffs closes unchanged Java provider and branch implementations as version-equal. The six changed callback/entity paths have a separate ordinary-valid-state direct-response closure. Do not leave those unchanged families pending only to repeat a full caller/provider walk.
- The 1.21.0/1.21.1 default packaged movement-resource comparison is now available at `.native-source/20261030/ordinary-pool/1210-1211-resources/report.md` (SHA-256 `59b727aa2e74d6c71927cad0b634cbfac2689fda09ebcda1e0b751b149685ec0`) and its independent review at `.native-source/20261030/ordinary-pool/1210-1211-resources-review/review.md` (SHA-256 `bd1ffd84267b58121a7c1fd3b1c1a171c6b2628ff973ec4043b41b92c87595e4`). They report identical packaged movement-selection inputs under equal effective resource stacks and equal relevant world state. Remove the 1.21.0/1.21.1 packaged-resource comparison from the open gates; retain custom packs/reloads only as an equal-effective-state premise.
- Keep two conditional boundaries explicit: (a) whether a legal client load/update/lifecycle can leave a mismatched or stale `ShulkerBoxBlockEntity` attached to a shulker state, whose support/collision shape reads entity state; and (b) the four server-only callback reorderings can affect authoritative neighbor/comparator processing and later received world state. Under an equal-received-state scope, the second is an external input condition, not a changed local client movement terminal. The changed command/chat paths likewise are not local tick operations; any resulting changed world/player state belongs to that same external-input boundary.
- Key state, mode, attributes/effects/equipment, world state, and received packet values should be stated as equal-input premises where applicable. Since their Java consumers are unchanged, their general runtime variability is not an unclosed pair-specific source dependency. Keep dynamic-state assumptions distinct from the one unresolved changed-code shulker edge.

The resource report closes only the stated default-packaged-input slice; it does not establish generated-world equivalence or runtime parity. The independent source-coverage audit and any project-level pair-freeze decision remain separate.

## Prior closure citations

The inventory’s characterization of the 1.21.2/1.21.3 DDA, callback-family and phase-order closures is directionally accurate: they support bounded old-endpoint callback/traversal contracts, not blanket 1.21.0/1.21.1 acceptance. For precision, state that those closures compare the 1.21.2/1.21.3 sources with 26.2 in their named slices and do not transfer those endpoints to this pair.

The callback closure path cited in the inventory is correct: `.native-source/20261030/1210-1211-block-callbacks/closure.md`, SHA-256 `b7dbc5af7fcad4b3e8326ecbc2888c7ec8e72f593d153c1180f57394bacda5e0`. However, that SHA is the **closure** hash, not the independent audit hash. The audit at `.native-source/20261030/ordinary-pool/1210-1211-block-callbacks-review/audit.md` hashes to `ea15f281abe0c4b0412f57eb22ff94dca4caf8709b072207b752c1d97ae7d2df`. Correct the attribution in both inventory references.

## Scope of this review

No product, wiki, backlog, tests, builds, gameplay, decompilation, or downloads were used. No source or inventory was edited. This review checks the ordered inventory and scoped source evidence; it does not certify complete movement parity or authorize pair freeze.
