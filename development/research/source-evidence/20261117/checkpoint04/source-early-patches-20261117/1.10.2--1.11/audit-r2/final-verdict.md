# Full-audit continuation: 1.10.2 → 1.11

**ACCEPT the corrected candidate revisions and the existing coverage report as PARTIAL source coverage. The exact pair remains partial and is not frozen.** This continuation resolves only the correction gates from the prior full audit; it does not repeat the full inventory or source comparison.

## Bound evidence

- Prior full-audit REQUEST CHANGES verdict: `audit/full-pair-verdict.md`, SHA-256 `4a7404e4dfe1da4012378ee00428b55b9f129036540dfcfe37d39115bb20246c`.
- Coverage report: `coverage.md`, SHA-256 `b6789f01de090321f9f05e79a99b35e9ee6739a141a26ded0b510d643fb24069`.
- Correction note: `candidate-corrections-r2.md`, SHA-256 `7c315067faed2d16047d8e20a3995e11e510f4611b54e04f6f2c52d1d36a8fab`.
- Sneak-edge candidate r2: `findings/sneak-edge-piston-mover-type-r2.md`, SHA-256 `bc274350be1022f359b5d28915ba5a012913f50a15f73a501f3c5ec24ccb627a`.
- Moving-piston candidate r2: `findings/moving-piston-client-response-r2.md`, SHA-256 `49ccaea0cc48e8bd8314d63d647c4c976febf4448e27dd8de9cb424fc556a18f`.
- Independent bounded correction ACCEPT: `audit-r2/candidate-verdict.md`, SHA-256 `7938ae7dca987305d1b8ca0243dee4613909ae55fd98b854e5676620a4564bf7`.

## Correction disposition

The bounded correction verdict verifies the `noClip == false` precondition, non-spectator selection through the default `EntityFilter.NOT_SPECTATOR` query, and the 1.11 `NOCLIP_DIR` qualification: while progress is below 1.0, the gated moving shape is omitted from that collision-list section, while other collision boxes still participate. The corrected candidates state those conditions and preserve conditional movement meaning. This continuation reuses the prior full audit's unchanged source and inventory findings.

## Remaining coverage gates

The accepted coverage report remains partial. Full input and tick order, changed entity responses, collision-provider registry/state/callback closure, fluids and climbing, effects/attributes/equipment, and other client responses remain partial or unexamined. Moved-state/piston-head geometry, the complete collision dependency, ordinary map reachability, and net trajectory are also unresolved. No implementation readiness or runtime parity is established; the frozen `1.10.2→1.11.2` endpoint verdict does not transfer to this edge.
