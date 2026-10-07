# F-GROUND-ACCEL — 1.13.2 changes the ground-acceleration float literal

- Status: provisional source candidate; artifact-integrity hold and block-friction closure pending
- A evidence: `ready/1.12.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `moveRelative(FFF)V`, lines 1479-1496; SHA-256 `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6`. On ground the factor is `0.16277136F / (s * s * s)`; `s` is block slipperiness times `0.91F`.
- B evidence: corresponding member, lines 1538-1558; SHA-256 `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c`. On ground the factor is `0.16277137F / (t * t * t)`; `t` is block slipperiness times `0.91F`.
- Preconditions/reachability: local player reaches this branch while grounded and outside the water/lava/glide branches; movement speed attribute and input are then applied through `updateVelocity`.
- Difference: the source literals differ by one binary32 step. Default block slipperiness and each historical block's exact float calculation remain part of collision/world inventory; no general per-surface quotient claim is made here.
- Limits: source confirms literal difference only. The prior source audit's default-ground binary32 quotient is a navigation hint and has not been revalidated against the repaired artifact manifest; no trajectory was measured.
- Integrity hold: do not accept/freeze until source owner/ops repair and freshly verify derived mapped artifacts.
