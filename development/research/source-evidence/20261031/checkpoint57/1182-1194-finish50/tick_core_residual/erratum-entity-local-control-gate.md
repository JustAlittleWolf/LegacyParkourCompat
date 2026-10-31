# Erratum — LocalPlayer control-gate disposition

Status: immutable addendum to `tick_core_residual/closure.md` and `tick_core_residual/member-dispositions.tsv`. This note preserves both source reports and their original hashes below; it supersedes only the overall disposition of `Entity.isControlledByLocalInstance` for 1.19.3→1.19.4.

## Binding

The predicate operation is accepted by the existing source-only review `protocol_nonclientbound/local-player-control-gate.md`, SHA-256 `ddfa7b209c53d9b125c9e66b2605147d4a48d18b83145e2f31726686f8d0f5ef`. It is not terminally unchanged. The previous ledger row terminalized it only against `LivingEntity.travel`; that scope was incomplete.

The changed predicate also controls LocalPlayer's `LivingEntity.aiStep` lerp reset and packet-position codec base sync, and the LocalPlayer guards in `ClientPacketListener.handleMoveEntity` and `handleTeleportEntity`. The accepted review closes those paths: LocalPlayer is not controlled by the 1.19.3 client-side fallback and passes the packet-handler negated guards; in 1.19.4 the fallback calls `LocalPlayer.isEffectiveAi()` (true), so the packet handlers filter LocalPlayer. `LivingEntity.aiStep` correspondingly switches its controlled-instance lerp/base-sync branch. The accepted review does not assert a vanilla server producer or runtime behavior.

Only the bounded `LivingEntity.travel` branch comparison in this worker's closure is locally no-difference for ordinary LocalPlayer travel: its old disjunction and new controlled-instance condition both evaluate true. That local branch result does not terminalize the predicate operation or its other callers.

This erratum does not create a new candidate or overlapping draft. It binds the existing accepted operation and withdraws the broader terminal wording for the predicate row only. The separate `LocalPlayer.isEffectiveAi` row remains an unchanged override; that fact is part of the accepted gate's dynamic call chain.

## Preserved report identity

- `tick_core_residual/closure.md`: SHA-256 `b4546200dc7b7397a4d0a155745699c6ad6c65ff817b27d5aaad4f111db02d99`.
- `tick_core_residual/member-dispositions.tsv`: SHA-256 `15cba3e99e3b3da15fcd0e4d28bceb904baf9ba9f6165b159b589fbde7d90acf`.
- The affected immutable ledger row is 1.19.3→1.19.4, `net/minecraft/world/entity/Entity.java`, `isControlledByLocalInstance`, anchors `2742`→`2795`; its paired exact source file hashes remain recorded in that row.
- Accepted operation source anchors and its exact source review identity are in `protocol_nonclientbound/local-player-control-gate.md` at the SHA above.
