# TICK-05 independent ordering review — 2026-10-08

## Snapshot identity

- Owner source slice: commit `1dbafbb7899c57ddf1718e421aa2c32e283fc7c7`, `workflows/source-campaign-2026-10-07/1.8.9--1.9.4/run.md`, SHA-256 `410733b99e194916c399cd0a113f433a4fc8323afaea1c879d68b22aa791222e`. TICK-05 is marked in-progress there; no immutable finding file or TICK-05 snapshot event exists at that revision.
- Source manifests: A `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004`; B `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19`. Relevant source hashes: A `PlayerEntity.java` `e66cb294fc93118148a444bbafdf4dd57cbf66a23d69b1e8892cefccc690ab88`, `LocalClientPlayerEntity.java` `1762b116e6b06d682b7daaa0fc8cce39b0ff455b3db8cf79dab03ac74f6c4053`, `LivingEntity.java` `082831c6578e3a70fa6cea5b90bc3eefc26678259b66334470de22b90b5b0e4e`; B `PlayerEntity.java` `d658a0d95452d12bb7e347bfd802240eeecaf7f938e10dcd43e2640434387f85`, `LocalClientPlayerEntity.java` `8aaf711948b7602c2e6c015a37e36ed06073d39727d999d80480b4910b704f5d`, `LivingEntity.java` `bbb7703f18fd5da05c4e4a43a77ea644b388e63c01d34166d308ea52054be4e5`.
- Artifact identity is limited to immutable `feather-r1-2026-10-07` snapshots: A `5c4cff3e4ac10ea1e1da166279133801ad304b4557d5ee4a77a2430977afb6a5`; B `fbcf50795566e12b8eab0e733b136ed562c4009d707ef4a7a4994936491816a3`. Original derived mapped-JAR identity/equivalence remains unproven.

## Decision: reject a B-only update-before-input ordering difference

The paired source does not support the stated ordering contrast. In A, `PlayerEntity.tick` updates or clears the active item use before calling `super.tick`; the superclass tick reaches `LivingEntity.tick`, which invokes the local player's overridden `mobTick`. In B, `LivingEntity.tick` calls `tickUsingItem` before its `mobTick` call. On both sides, the local player's `mobTick` then ticks input and applies the active-use attenuation before superclass movement: A checks `hasItemInUse() && !isRiding()`; B checks `isUsingItem() && !isRiding()`.

The update method moved between classes, but its position relative to local input sampling is before that sampling in both endpoints. This evidence rejects that specific timing difference as a movement finding.

## Limits

- This is a bounded no-difference decision for active-item-use update order relative to local input attenuation. It does not close every item-use writer, cancellation, or finish semantic.
- The owner TICK-05 slice remains in-progress until its remaining producer/consumer inventory is closed; no pair-completeness claim is made.
- No runtime validation or trajectory claim was made.
