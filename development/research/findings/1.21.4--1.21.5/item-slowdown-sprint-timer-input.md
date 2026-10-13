# Item slowdown sprint timer input

Difference: On dry ground, old LocalPlayer attenuates and retains forwardImpulse during non-passenger item use; newer movement modification leaves stored moveVector raw. After item-use release while forward remains held, old prior-input threshold arms sprintTriggerTime=7 and new positive-vector predicate does not. Release forward one tick, then press again: old starts sprint with positive timer; new only arms the timer.

Conditions: controlled local keyboard Player on clear dry ground, forward-only input; non-passenger, not shifting/crouching, not already sprinting, not fall-flying, no blindness; food above sprint threshold; sprint key released; initial sprintTriggerTime=0; one forward-held item-use tick, item-use release with forward still held, one forward-release tick, forward re-press.

Source locators:

- `1.21.4/mojmap/net/minecraft/client/player/KeyboardInput.java`, calculateImpulse/tick, lines ['13-34'].
- `1.21.5/mojmap/net/minecraft/client/player/KeyboardInput.java`, tick, lines ['21-36'].
- `1.21.4/mojmap/net/minecraft/client/player/LocalPlayer.java`, aiStep/hasEnoughImpulseToStartSprinting, lines ['653-677', '698-708', '1068-1071'].
- `1.21.5/mojmap/net/minecraft/client/player/LocalPlayer.java`, applyInput/modifyInput/aiStep, lines ['608-638', '693-703', '718-733'].
- `1.21.4/mojmap/net/minecraft/client/player/ClientInput.java`, stored movement impulses, lines ['5-13'].
- `1.21.5/mojmap/net/minecraft/client/player/ClientInput.java`, hasForwardImpulse, lines ['17-19'].
- `1.21.4/mojmap/net/minecraft/world/entity/LivingEntity.java`, setSprinting, lines ['2077-2084'].
- `1.21.5/mojmap/net/minecraft/world/entity/LivingEntity.java`, setSprinting, lines ['2101-2108'].

Movement meaning: The differing retained input and sprint timer can cause sprint to start on different ticks after item-use release and forward re-press.
