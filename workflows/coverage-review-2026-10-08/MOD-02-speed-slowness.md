# MOD-02 Speed/Slowness modifier review — 2026-10-08

## Snapshot identity

- Owner source slice: commit `1dbafbb7899c57ddf1718e421aa2c32e283fc7c7`, `workflows/source-campaign-2026-10-07/1.8.9--1.9.4/run.md`, SHA-256 `410733b99e194916c399cd0a113f433a4fc8323afaea1c879d68b22aa791222e`. MOD-02 is marked in-progress; no immutable finding or snapshot event exists for this sub-slice at that revision.
- Source manifests: A `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004`; B `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19`. Cited source hashes: A `StatusEffect.java` `f9bb4d1839337229cb6f6d8dc9ae17a49cfad757a42388e00abbaa68be2b21a3`; B `StatusEffect.java` `cd56502d70b9c742dcffc61fbac6337f268838e35f268ffead4610ff964bea89`.
- Artifact identity is limited to immutable `feather-r1-2026-10-07` snapshots: A `5c4cff3e4ac10ea1e1da166279133801ad304b4557d5ee4a77a2430977afb6a5`; B `fbcf50795566e12b8eab0e733b136ed562c4009d707ef4a7a4994936491816a3`. Original derived mapped-JAR identity/equivalence remains unproven.

## Decision: no difference in Speed/Slowness modifier definitions and amplifier scaling

At both endpoints, Speed registers movement-speed modifier UUID `91AEAA56-376B-4498-935B-2F7F68070635`, amount `+0.2F`, operation 2; Slowness registers UUID `7107DE5E-7CE8-4030-940E-514C1F160890`, amount `-0.15F`, operation 2. Both `StatusEffect.getModifier` implementations scale the base modifier by `(amplifier + 1)`, and both `addModifiers` implementations replace the prior modifier while preserving its operation. `PlayerEntity.getSpeed()` reads `EntityAttributes.MOVEMENT_SPEED` in both versions; grounded `LivingEntity` travel uses `getSpeed()` to form movement input.

## Limits

- This is a bounded no-difference result for the two effect modifier registrations and their amplifier scaling.
- Attribute aggregation, status-effect transition timing, sprint, jump, and equipment modifiers remain outside this decision; MOD-02 remains open.
- No trajectory or runtime validation was made.
