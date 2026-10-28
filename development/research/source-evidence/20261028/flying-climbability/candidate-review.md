# Independent source-candidate review: 1.21.3–1.21.4 flying climbability gate

## Inputs and identity

- Accepted source report: `.native-source/20261028/frontier-c/1213-1214/report.md`, SHA-256 `F576C21E81913E4509823000EB4A9FFB9B3D69614E7955DA7BB455152638C03B`.
- Independent source audit: `.native-source/20261028/frontier-c/1213-1214-audit/audit.md`, SHA-256 `6383BD90811E8694B3C830FD393DACB6A073FBA518ECDAA72AB56BFE1F0DD8C6`.
- Neutral candidate: commit `7575a78e6161932c55b1c0c2db2efa77c1c41af8`, export `.native-candidates/1213-flying-climbability-candidate-20261028-a1`; `infrastructure.json` SHA-256 `BAC6DBC30847925297E10AD95F35055B3A4926015B8AE0C7AF0A77FCBF521AE6`.
- Candidate-local `findings/report.md` and `findings/audit.md` contain the same text as the canonical evidence with normalized checkout line endings; their export hashes are recorded in `infrastructure.json`. The canonical input hashes above were independently verified against the repository files.

## Verdict

**ACCEPT, bounded to the added Player-level flying veto.** The candidate represents the accepted 1.21.3 → 1.21.4 source difference without attempting to reproduce the inherited climb predicate itself.

- `PlayerClimbabilityFlyingGateHook.shouldApplyFlyingVeto` is a narrow gate contract. Its documentation correctly requires the caller to substitute only the newly added flying test and continue evaluating `LivingEntity.onClimbable` when the gate is disabled. An unresolved hook retains native behavior.
- `InheritedPlayerClimbability@V1_21_2` disables that gate for the existing alias covering 1.21.2 and 1.21.3. Its contract matches the source finding: 1.21.3 inherits the living-entity predicate, while 1.21.4 returns false for flying before delegating.
- `NativePlayerClimbabilityFlyingGate@V1_21` returns the passed native flying value. The sanitized `ParkourVersion` aliases show `V1_21` covers 1.21/1.21.1 and `V1_21_2` covers 1.21.2/1.21.3; `ChangeResolver` picks the earliest provider at or after the selected profile. Thus the native barrier keeps unexamined earlier profiles native, the accepted historical provider applies to the 1.21.2 alias, and selected 1.21.4 has no candidate provider and remains on native fallback.
- No interface or provider reproduces climb math, movement impulses, attack-critical behavior, or the shared `Player` override globally.

## Product caller requirements

This candidate has no mixin or call site by design. Integration must dispatch only for the local logical-client player, leave remote players and all logical-server players native, alter only the added `abilities.flying` early-false branch, and preserve the inherited `LivingEntity.onClimbable` evaluation. The source audit does not establish pair-wide coverage or runtime parity.

No candidate files were changed. No build, tests, or runtime checks were run.
