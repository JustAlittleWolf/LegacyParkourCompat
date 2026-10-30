# Follow-up supplement: Shulker reachability gate closed

This immutable supplement binds the dedicated Shulker reachability result to the prior terminal-inventory review. It preserves both prior review artifacts and the original inventory.

## Evidence identity

- Shulker reachability report: `.native-source/20261030/ordinary-pool/1210-1211-shulker-reachability/report.md`, SHA-256 `6e602b6af56f2d2ff78cee28f065e780d96f0ae9abf7be2dd65bdd8971ac3a6e`.
- Independent source review: `.native-source/20261030/ordinary-pool/1210-1211-shulker-reachability-review/review.md`, SHA-256 `be540921f5ca269d4d2e6361c61b9c2462172bd7ff1f0e8a5d37fc8e1bce83ec`.
- Prior terminal inventory review: `.native-source/20261030/ordinary-pool/1210-1211-terminal-inventory-review/review.md`, SHA-256 `dc9482ec21671704914d01045f37938d2c67c56dc324e173815a3640a19737e9`.
- Prior correction supplement: `.native-source/20261030/ordinary-pool/1210-1211-terminal-inventory-review/correction-supplement.md`, SHA-256 `1da3f3595d4dfb79e05c3805e751fef884ff90bb27259cba93b5bdaff71d83a18`.

The assigned Shulker pins match the files. The independent review accepts the bounded vanilla client reachability trace: chunk-load tags are applied only to an entity constructed from the received block state and only when their declared entity type matches; incremental block-identity changes remove/recreate block entities; same-block property changes preserve type validity; block-entity updates do not create or replace entities; unload/reload reconstructs from the incoming states. The 1.21.1 validation addition therefore has no legal vanilla client Shulker transition that changes support/collision geometry compared with 1.21.0. Malformed/custom packet contents and external client mutation remain out of scope.

This closes the specific stale/mismatched Shulker block-entity gate retained in the prior review and correction supplement. Movement-relevant received state equality should include relevant block-entity data (for example, Shulker animation progress), not only the block-state identifier. Different received animation data is a differing runtime input consumed by identical geometry code, not a remaining version-specific source delta.

## Remaining changed-path boundary

Reconciled with the pinned full Java source audit, the 17 changed paths leave no identified client movement-operation difference under equal movement-relevant received state and equal effective resources:

- The 3 version/report/protocol metadata paths and 8 command/chat selector paths contain no local-player movement operation. Commands may produce different server-side actions or resulting state; that is outside an equal-received-state comparison.
- The 4 changed block `onRemove` methods are invoked only on the logical server in `LevelChunk.setBlockState`. Their reordered neighbor/comparator notifications can affect authoritative server state and later client-delivered block state. Under equal received state, they add no direct local-client callback, collision, or movement difference. Keep this explicit as an external-state boundary if the comparison includes unequal server-produced inputs.
- The changed `BlockEntity`/`LevelChunk` validation paths affect invalid entity/state combinations. The legal vanilla client lifecycle described above prevents such a Shulker mismatch, and the same state-derived construction, typed update, and block-identity replacement paths are the generic guard for ordinary block-entity lifecycle. No other concrete movement-source dependency caused by these two changes is identified under legal vanilla state delivery.
- The exact 1.21.0/1.21.1 resource report and independent review bind equality of packaged movement-selection resources under equal effective resource stacks. Custom data packs/reloads remain an equal-input premise, not a pair-specific source gap.

Thus, for ordinary client movement under equal movement-relevant received state (including relevant block-entity values) and equal effective resources, this review identifies no remaining concrete version-difference source dependency. This is a bounded source-difference assessment, not proof of runtime parity or a pair-freeze verdict.

## Recommended next action

The pinned source audit supplies complete Java path/hash accounting and exact dispositions for all 17 changed paths; the default packaged resource comparison and Shulker reachability edge now have independent reviews; and the prior terminal review supplies corrections for the tick ordering and post-travel entity-push terminal. Recommend a fresh blind whole-pair movement-coverage audit against both exact source trees, incorporating these immutable supplements and checking terminal completeness/order. That audit should decide whether coverage is complete. Do not infer pair freeze from this inventory review or its component closures.
