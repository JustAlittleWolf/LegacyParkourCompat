# Independent reconciled pair audit v4 — 1.15.2 → 1.16.5

## Verdict

**Independent coverage verdict: ACCEPT; the 1.15.2 → 1.16.5 source pair is closed at the movement-route/member level.** The corrected exact-case inventory and v4 route crosswalk reconcile without missing, extra, duplicate, or hash-mismatched rows. Its 188 movement-reachable rows are now all closed in scope: the final `LocalPlayer.updateAutoJump` full-cube collector dependency was accepted after v4 by an independent review bound to the current common-pose proof and low-pose supplement. The v4 residual TSV predates this review and still labels that now-closed route partial; preserve it as historical input and use this report as the post-review closure overlay. This verdict covers source inventory, route/member dependencies and independent audit; it does not claim runtime parity or a movement trajectory.

This is source coverage only. It does not claim runtime parity, a movement trajectory, or producer timing. It does not reopen later exact member closures because stale source-row annotation text says `OPEN`.

## Independent mechanical reconciliation

- Exact source roots: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.15.2\mojmap` and `...\1.16.5\mojmap`; release-parent `mojmap.sources.sha256` hashes rechecked as `cb7fd93f8730d8b2813857744f8572060535c7de44fcee86222acd1ceb6f43e7` and `9499f2611d0e6dde37cb635f1a28a591416382d6bc188b37d5f99e2b2a20823b`. Both parent folders include the source, provenance and ready manifests. The corrected manifest verification reports 3,332 and 3,523 Java files with zero missing, unlisted, or hash-mismatched paths.
- Corrected source inventory `path-status-ordinal-v2.tsv` rehashed to `835BA455D3DD4825F01A74F282EEE21C42254CEE3870A400EE7F96D9824FBCD5`; it contains 3,799 exact-case rows: 3,056 shared (1,182 identical, 1,874 modified), 467 added, 276 removed. This correctly keeps `SoulsandBlock.java` (removed) and `SoulSandBlock.java` (added) distinct.
- Using ordinal path keys, I independently joined every non-identical inventory row to v4 `reconciled-route-ledger-v4.tsv`: expected 2,617; ledger 2,617; missing 0; extra 0; duplicate 0; delta or endpoint-hash mismatch 0. This avoids case-insensitive path grouping, which would incorrectly collapse the two Soul Sand names.
- Route totals reconcile to 188 movement-reachable, 584 no-movement-dependency, and 1,845 excluded (=2,617). The v4 artifact predates the final AutoJump review and records 187 `CLOSED_IN_SCOPE` plus one `PARTIAL`; applying the later bound ACCEPT review closes that one row, leaving 188/188 movement rows closed and zero remaining eligible open rows.
- V4 ledger SHA-256 `c2f5d791c72869e5996011afaa1350932e38ba1b84bbad87e8824dee8c849dd2`. Reconciliation report SHA-256 `a7e80d55286666e0be7d1b3fe2c625b6d2178e9d7af928590e0d23e8916ef24f`; residual TSV SHA-256 `8670169f4a3881305c6ba5b0ffddaaca7892cdd7d8bd11d80ea07d4841764f21`.
- All 16 files listed in `supplemental-evidence-hashes-v4.tsv` rehashed to their recorded digests. The corrected comparison, status, manifest-verification, source-identity and tree-verification artifacts rehash to their recorded inventory identities.

## Route closure and scope checks

The v4 overlay is consistent with the later exact member reports inspected here: LocalPlayer, Player and LivingEntity inventories; Level/LevelAccessor/LevelReader and EntityGetter consumer closures; respawn sprint receiver; block-break acknowledgment; bed wake direct response; fixed-state fluid, Leaves, piston and equal-state Wall/IronBars providers; and the previously reviewed collision, border, tag and direct-response slices. The report pins these artifacts through `supplemental-evidence-hashes-v4.tsv` and its preceding input-hash list. Coverage is member and caller bounded; unrelated methods on a changed class are not required.

The FishHook added/removed source rows are correctly classified as movement-reachable and closed for the direct client `handleEntityEvent` event-31 local-player impulse, under equal synchronized owner/hook inputs. The separate hook-projectile motion and server production are not part of that client response. Vehicle-produced placement and server-produced teleport are producer routes and do not remain open absent a changed direct client consumer.

Wall/IronBars generated `getStateForPlacement` and `updateShape` connection outputs are vanilla block-state production and are excluded by the final scope erratum. Equal supplied-state collision providers remain closed. No old neighbor-state generation should be implemented or counted as a movement gap.

## Final route close

The sole partial row in v4 was `net/minecraft/client/player/LocalPlayer.java`. Its paired body and empty-set-to-always-true entity-filter migration are equivalent for this LocalPlayer caller. Its only outstanding dependency was the applicability of the 1.15.2 `Shapes.create(AABB)` grid join versus the 1.16.5 raw-AABB full-cube collision fast path to the two side segments consumed by `LocalPlayer.updateAutoJump`; the separate `Entity.move` witness does not transfer.

Current common-pose proof: `.native-source/20261030/1152-1165-finish43/autojump-residual-audit/fastpath-exclusion-proof.md`, SHA-256 `9d9a396c672fb2dc8a3102fc57f8e2ba7268b851c1cc7795ded769306bd8d87b`. It bounds standing, crouching, fall-flying, swimming and spin-attack poses against the `1.0E-7` collector fringe using a conservative normalization margin. The earlier independent review remains bound to stale proof SHA `98cdda70fe2d755f0d0caf3f7806283e78675e50817b669cd62054baf4345938` and is superseded for this route by `.native-source/20261030/1152-1165-finish43/autojump-residual-audit/fastpath-exclusion-combined-review.md`, SHA-256 `9acfc37c8a280dc5886171946043027370da9608d7bcc7e9c691d42ddfa11085`. The combined review binds the current proof and the low-pose report below and returns **ACCEPT** for all vanilla player poses. Its cited LocalPlayer, Mth, Player, LivingEntity, Shapes, AABB, CollisionGetter and CollisionSpliterator identities are supported by the exact root manifests; selected main proof inputs were rehashed against those roots in this audit.

The separate low-pose report `.native-source/20261030/1152-1165-finish43/autojump-pose-scope/report.md`, SHA-256 `f146e2ae8cdd496d455aa0c5d637cfd6df7a150e312314ce3c2906ab2de64357`, establishes that sleeping/dying 0.2-height queries cannot pass any old `Shapes.findBits` grid resolution; the old exact-shape route therefore preserves the same positive full-cube overlap. The combined independent review accepts both geometry bounds and closes this final route. The post-v4 eligible residual list is empty. No whole-class, producer, raw-packet timing, or runtime trajectory evidence is needed for this source freeze.

No product, wiki, backlog, canonical source, build, test, decompilation or runtime files were changed or used.

## V5 reconciliation audit erratum — 2026-10-31

I independently checked final route reconciliation v5 against the corrected exact-case path inventory and the accepted AutoJump closure. **V5 passes; pair coverage remains ACCEPTED and no eligible movement residual remains.**

- Reconciliation report SHA-256: `c25d81dff7436622784e49858ccb7c6010f41f173a65db491d55f3c5f253a473`.
- Full route ledger SHA-256: `b50a9c004b9d2fd197446df09f3199a411dd685013df308a79571750dcb60a5d`.
- Header-only remaining movement TSV SHA-256: `62a9a26184ec47a69447cc5540f1dd9ca3377289381bbaeea897d71586ffbea4`.
- Exact-case join against corrected ordinal inventory: 2,617 expected non-identical rows; 2,617 ledger rows; 0 missing, 0 extra, 0 duplicates, 0 delta/hash mismatches.
- Route totals: 188 movement-reachable, 584 no-movement-dependency, 1,845 excluded. All 188 movement rows are `CLOSED_IN_SCOPE`; the residual TSV has zero data rows.
- Dispositions: 354 closed in scope, 425 open non-movement route rows, 1,838 unchanged from the corrected ledger. The 425 open annotations do not name movement-reachable routes and are outside the freeze criterion.
- All 19 supplemental evidence paths rehashed to their listed SHA-256 values. The newly closed `LocalPlayer.updateAutoJump` route binds the combined ACCEPT review SHA `9acfc37c8a280dc5886171946043027370da9608d7bcc7e9c691d42ddfa11085` to the current common-pose proof, low-pose supplement, and collision collector report; no open movement row remains.

This erratum supersedes the prior audit's note that the v4 residual TSV was stale. V4 is retained as historical evidence; v5 is the final reconciled ledger. No earlier closed row was reopened: the v5 changes are limited to the exact AutoJump row closed by its bound independent review and mechanical reconciliation of route status.
