# Immutable erratum 1 — bed wake resolver annotation

This erratum binds to `reconciliation.md`, SHA-256 `727b102390b1a0cf8a5583db2a7ac75cb78ad3f2e3a2625ce799df1641ca2a90`. It corrects the resolver annotation recommendation in the bed wake section. The original report remains unchanged.

## Correction

The original report says an old-algorithm change annotated `emulates = V1_16_2` can serve selected profiles through 1.16.1 while leaving 1.16.2 native. That is incorrect: `ChangeResolver` includes equality, so the change would also be selected for the 1.16.2 profile.

The old wake algorithm should be annotated `emulates = V1_16`, covering the product's 1.16 / 1.16.1 profile range. The 1.16.2 profile must retain native behavior. The source boundary remains unchanged: old grid search through 1.16.1; yaw-ordered search from 1.16.2 through 1.16.5.

No product source was changed.
