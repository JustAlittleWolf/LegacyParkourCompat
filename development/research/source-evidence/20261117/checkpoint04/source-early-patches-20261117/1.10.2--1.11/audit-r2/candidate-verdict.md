# Bounded independent audit: 1.10.2 → 1.11 candidate corrections

**Verdict: ACCEPT for the corrected candidate gates and `NOCLIP_DIR` qualification only. The exact pair remains partial and unfrozen.**

## Bound inputs

- Correction note: `candidate-corrections-r2.md`, SHA-256 `7C315067FAED2D16047D8E20A3995E11E510F4611B54E04F6F2C52D1D36A8FAB`.
- Sneak-edge candidate r2: `findings/sneak-edge-piston-mover-type-r2.md`, SHA-256 `BC274350BE1022F359B5D28915BA5A012913F50A15F73A501F3C5EC24CCB627A`.
- Moving-piston candidate r2: `findings/moving-piston-client-response-r2.md`, SHA-256 `49CCAEA0CC48E8BD8314D63D647C4C976FEBF4448E27DD8DE9CB424FC556A18F`.
- Original coverage: `coverage.md`, SHA-256 `B6789F01DE090321F9F05E79A99B35E9EE6739A141A26DED0B510D643FB24069`.
- Prior full audit: `audit/full-pair-verdict.md`, SHA-256 `4A7404E4DFE1DA4012378EE00428B55B9F129036540DFCFE37D39115BB20246C`.

The original coverage, both r1 candidates and the prior audit verdict match the stated immutable hashes. This review checked only the corrected gates and collision qualification; it does not repeat the full inventory or manifest comparison.

## Verified corrections

The `noClip == false` precondition is supported: both `Entity.move` implementations take the `noClip` branch before their collision-clipped movement. In 1.11, the sneak-edge loop follows that branch and is further restricted to `MoverType.SELF` or `MoverType.PLAYER`.

The non-spectator selection gate is supported. Both moving-piston callers use `World.getEntities(null, bounds)`, whose overload applies `EntityFilter.NOT_SPECTATOR`; the predicate excludes spectator players.

The 1.11 moving-piston caller sets `NOCLIP_DIR` around `Entity.move(MoverType.PISTON, ...)`. `MovingBlockEntity.addCollisions` omits the moved-state moving shape from its gated collision-list section only while the current progress is below 1.0 and the thread-local is true. Other block collision boxes still participate in the movement clipping. For retracting source pistons, the extended piston-base state is added before that conditional; the correction is accepted only as a statement about the gated moving shape, not every piston-related collision shape.

The corrected candidate language preserves the conditional nature of both observations and adds the requested player gates. The r2 claims are supported by the cited exact source methods and their unchanged identities from the prior full audit.

## Remaining limits

This verdict accepts only the corrected gates and `NOCLIP_DIR` qualification. It does not close moved-state/piston-head geometry or the complete collision dependency, establish an ordinary map precondition or net trajectory, or accept full-pair coverage. The pair remains partial and unfrozen.
