# F-SWIM-LOOK — 1.13.2 swimming adds pitch-directed vertical velocity control

- Status: provisional source candidate; artifact-integrity hold and dependency closure pending
- A evidence: `ready/1.12.2/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java`, `moveRelative(FFF)V`, lines 1386-1410; SHA-256 `e4e0fdbe07a7d0a0ae4a70cbb6739a287c9d045a4a12b409895c220b5d91fe1e`. No swimming-look velocity adjustment precedes the superclass movement call.
- B evidence: corresponding member, lines 1442-1487; SHA-256 `4ed22f6c5a3c55d67eed782070ac722201df4d624adbc90779f1fd29c2876633`. When swimming and not riding, B reads look-vector Y, selects 0.085 below -0.2 else 0.06, then conditionally adds `(lookY - velocityY) * factor` if lookY <= 0, jumping, or fluid exists at the sampled head position.
- Preconditions/reachability: virtual call from LivingEntity.mobTick executes on local player; B must be swimming and not riding. F-WATER-STATE establishes the new state producer, subject to artifact repair.
- Difference: B applies a look-directed vertical adjustment absent from A's player override. It is separate from the local sneak-water descent impulse and water-height jump selection.
- Limits: source proves conditional velocity write; no trajectory was measured. Exact fluid query, pose, and collision effects remain separate dependencies.
- Integrity hold: do not accept/freeze until source owner/ops repair and freshly verify derived mapped artifacts.
