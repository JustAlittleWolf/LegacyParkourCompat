# F14: 1.15.2 resumes hidden movement-affecting effects

- **1.14.4**, mojmap: [source locations in preserved report](../../archive/legacy-workflows/movement-discovery/runs/1.14.4--1.15.2/findings/F14-hidden-movement-effects-resume.md).
- **1.15.2**, mojmap: [source locations in preserved report](../../archive/legacy-workflows/movement-discovery/runs/1.14.4--1.15.2/findings/F14-hidden-movement-effects-resume.md).

## Difference

If the stronger instance expires while the weaker instance still has duration, 1.15.2 restores the weaker amplifier and makes that movement effect active again. 1.14.4 discarded the weaker instance when the stronger one replaced it, so it removes the effect at expiry.

The resumed state can change jump height, fall gravity, levitation velocity, water drag, or whether the local sprint-start gate accepts a sprint attempt, depending on which effect is stacked.

## Movement meaning

Effect insertion or renewal -> `MobEffectInstance.update` -> active effect tick/expiry -> 1.15.2 hidden-effect restoration -> the existing player movement reader.

F08 separately records Speed/Slowness restoration and movement-speed attribute refresh.

The generic effect stack and the movement readers are source-confirmed in both endpoint releases.

[Archived report](../../archive/legacy-workflows/movement-discovery/runs/1.14.4--1.15.2/findings/F14-hidden-movement-effects-resume.md).
