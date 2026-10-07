# F-ELYTRA-GLIDE-MATH — 1.13.2 regroups vertical glide acceleration

- Status: provisional source candidate; artifact-integrity hold and numeric-branch closure pending
- A evidence: `ready/1.12.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, fall-flying branch in `moveRelative(FFF)V`, lines 1425-1534; SHA-256 `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6`. The vertical increment is written `-0.08 + m * 0.06`.
- B evidence: corresponding member lines 1478-1537; SHA-256 `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c`. The increment is written `d * (-1.0 + n * 0.75)` with `d` initialized to `0.08`, except for the Slow Falling branch.
- Difference: B regroups the arithmetic and introduces a shared gravity factor that can be affected by Slow Falling. Preserve these source expressions and intermediate types/order; don't simplify them to a real-number identity.
- Reachability: inherited LivingEntity movement dispatch is called by PlayerEntity through the local player's travel tick while fall-flying. Elytra state/equipment eligibility and camera pitch are separate dependencies.
- Limits: candidate arithmetic delta; accumulated float/double consequence and endpoint-specific effect reachability remain open. No trajectory was measured. Slow Falling itself is a modern-only effect for this historical one-way scope.
- Integrity hold: do not accept/freeze until source owner/ops repair and freshly verify derived mapped artifacts.
