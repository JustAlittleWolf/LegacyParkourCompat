# F-13: Forward double-tap sprint activation loses its ground gate in 1.21.5

1.21.4, mojmap: `1.21.4/mojmap/net/minecraft/client/player/LocalPlayer.java`, aiStep 653-662.
1.21.5, mojmap: `1.21.5/mojmap/net/minecraft/client/player/LocalPlayer.java`, aiStep 693-703.
1.21.5, mojmap: `1.21.5/mojmap/net/minecraft/client/player/ClientInput.java`, hasForwardImpulse 17-19.

Difference: For a non-passenger local player whose current forward input and sprint eligibility gates pass, 1.21.5's forward double-tap path can arm and activate its seven-tick sprint trigger while airborne. 1.21.4 requires the player to be on the ground or underwater to enter that double-tap path. The separate held-sprint-key branch is not this finding: 1.21.4 has that branch outside the ground/underwater double-tap guard.

Movement meaning: `LocalPlayer#aiStep()` samples the prior forward state, ticks local input, evaluates sprint eligibility and writes sprint state before reaching `super.aiStep()`. On the ordinary keyboard path, forward input supplies the positive forward impulse used by both versions' eligibility checks. For the stated non-passenger airborne case, 1.21.4's ground/underwater condition rejects the double-tap path while 1.21.5's eligibility predicate contains no airborne restriction. Food, blindness, item-use, fall-flying, movement-slowdown and fluid values are direct eligibility inputs; excluded producer systems remain vanilla. The paired source proves that the local sprint state can transition through a forward double-tap while airborne in 1.21.5 under the stated eligibility gates, while 1.21.4's corresponding double-tap branch rejects that state.

Original evidence: [archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.21.4--1.21.5/findings/F-13-airborne-double-tap-sprint.md).
