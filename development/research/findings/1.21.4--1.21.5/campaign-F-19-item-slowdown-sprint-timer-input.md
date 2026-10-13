# 1.21.4--1.21.5/F-19

On dry ground, old LocalPlayer attenuates and retains forwardImpulse during non-passenger item use; newer movement modification leaves stored moveVector raw. After item-use release while forward remains held, old prior-input threshold arms sprintTriggerTime=7 and new positive-vector predicate does not. Release forward one tick, then press again: old starts sprint with positive timer; new only arms the timer.

Conditions: controlled local keyboard Player on clear dry ground, forward-only input; non-passenger, not shifting/crouching, not already sprinting, not fall-flying, no blindness; food above sprint threshold; sprint key released; initial sprintTriggerTime=0; one forward-held item-use tick, item-use release with forward still held, one forward-release tick, forward re-press.

Existing run reports ACCEPT for the exact d41bbfe8f6cf48e05fc210c297201095f83500f1 snapshot; standalone independent review artifact identity and exact applicability remain open. No implementation or runtime verification.

Source locators:

- `1.21.4/mojmap/net/minecraft/client/player/KeyboardInput.java`, calculateImpulse/tick, lines ['13-34'].
- `1.21.5/mojmap/net/minecraft/client/player/KeyboardInput.java`, tick, lines ['21-36'].
- `1.21.4/mojmap/net/minecraft/client/player/LocalPlayer.java`, aiStep/hasEnoughImpulseToStartSprinting, lines ['653-677', '698-708', '1068-1071'].
- `1.21.5/mojmap/net/minecraft/client/player/LocalPlayer.java`, applyInput/modifyInput/aiStep, lines ['608-638', '693-703', '718-733'].
- `1.21.4/mojmap/net/minecraft/client/player/ClientInput.java`, stored movement impulses, lines ['5-13'].
- `1.21.5/mojmap/net/minecraft/client/player/ClientInput.java`, hasForwardImpulse, lines ['17-19'].
- `1.21.4/mojmap/net/minecraft/world/entity/LivingEntity.java`, setSprinting, lines ['2077-2084'].
- `1.21.5/mojmap/net/minecraft/world/entity/LivingEntity.java`, setSprinting, lines ['2101-2108'].

Exact evidence: [incoming snapshot](../../archive/branch-integration-2026-10-13/7e18e61fcdd1d20e6e7d21f17bcce711410f756d37285162ca72e94d15573230.md); [full revision, source and verdict record](../../reconciliation/supplemental-branch-records.json); [incoming review](../../reconciliation/branch-doc-review-late.md).
