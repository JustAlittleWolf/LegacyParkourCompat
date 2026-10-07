# F-SLOW-FALLING — 1.13.2 introduces an effect-dependent gravity factor

- Status: modern-only disposition pending resource/source closure; not a 1.12.2 historical mechanic
- A evidence: `ready/1.12.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, travel body lines 1425-1618; SHA-256 `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6`. No Slow Falling check occurs in the bounded A travel body.
- B evidence: corresponding body lines 1478-1688; SHA-256 `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c`. B initializes `d=0.08`; for velocityY <= 0 and Slow Falling, it uses `d=0.01` and clears fallDistance.
- Scope disposition: the effect is absent from 1.12.2; per project one-way scope, do not give 1.12.2 maps/effect states this 1.13-only behavior. Record it to explain the B branch, not as a 1.12.2 compatibility delta.
- Dependencies: exact effect registry/application source and any data entries still need inventory; fallDistance consumer audit must distinguish movement-state writes from excluded damage simulation.
- Integrity hold: mapped-artifact repair is still required before freezing the exclusion.
