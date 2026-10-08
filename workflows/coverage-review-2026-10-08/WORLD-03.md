# WORLD-03 independent review — 2026-10-08

## Snapshot identity

- Owner finding: commit `1dbafbb7899c57ddf1718e421aa2c32e283fc7c7`, path `workflows/source-campaign-2026-10-07/1.8.9--1.9.4/findings/WORLD-03-piston-player-block-ejection.md`, SHA-256 `a3125e53e15bbe8f4a7692bd3494716f9845ff69c13db551b379654852c5d07d`.
- No WORLD-03 immutable finding-snapshot event appears in that owner run. The pair remains partial; this is not a pair-completeness decision.
- Source manifests: A `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004`; B `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19`. Independently checked hashes: A `LocalClientPlayerEntity.java` `1762b116e6b06d682b7daaa0fc8cce39b0ff455b3db8cf79dab03ac74f6c4053`, `Block.java` `ea10f05106a3cf7189aec85236a7ecf9c7106a717f37bed583b5ea4adeffa528`, `PistonBaseBlock.java` `3c96698a674714446f9fd0d6cc1c4d5eb72937d9a2f396516ddb2461c3ea4929`; B `LocalClientPlayerEntity.java` `8aaf711948b7602c2e6c015a37e36ed06073d39727d999d80480b4910b704f5d`, `Block.java` `e62ece80c6a7e7121346f65f8fdfd9b148c29441a27de9f568bba9afe1d84fe6`, `PistonBaseBlock.java` `4ef15129e660397ba3a531c8ff4a4810393973ea56417d8830f4445d14035e36`, `Material.java` `f198b08007c0acbe4e2737f7e484a9c85ec183220daef03b954cc90d0af95d19`, `StateDefinition.java` `10ba661985c87801e1bb7e941399498e89fb67e1bd9ead2869c00d39ffff6493`.
- Artifact identity is limited to immutable `feather-r1-2026-10-07` snapshots, A `5c4cff3e4ac10ea1e1da166279133801ad304b4557d5ee4a77a2430977afb6a5`, B `fbcf50795566e12b8eab0e733b136ed562c4009d707ef4a7a4994936491816a3`; original mapped-JAR identity/equivalence is unproven.

## Decision: reject the proposed piston-state solidity delta

The four local-player probes and their conditional horizontal velocity write exist. The decisive B-side premise does not: 1.9.4 `PistonBaseBlock` overrides `isCube(BlockState)` and returns `false` (lines 215-218). `StateDefinition.isCube()` delegates to that override, and `Block.isSolid(state)` requires `state.isCube()` along with solid-blocking material and non-signal-source state. A likewise has `PistonBaseBlock.isCube() == false`, used by its block-level `isSolid()` test.

Thus `Material.PISTON` and the extended state's partial shape do not make this piston-base state solid for `LocalClientPlayerEntity.canSurvive`. On the proposed piston-only condition, the ejection gate is not shown to differ from A. Other blocks or neighbor states could independently make the probe fail, but that does not establish this piston-specific claim.

## Limits

- Rejection covers only the proposed piston-base solidity/ejection delta; it makes no trajectory or runtime claim.
- Pair completeness and original mapped-JAR identity/equivalence remain unresolved.
