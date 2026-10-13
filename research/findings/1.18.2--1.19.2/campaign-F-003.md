# F-003: player step events use different movement guards and context

- **1.18.2**, mojmap: `1.18.2/mojmap/net/minecraft/client/player/LocalPlayer.java`; lines 889-894.
- **1.19.2**, mojmap: `1.19.2/mojmap/net/minecraft/client/player/LocalPlayer.java`; lines 979-984.

## Difference

Both versions enter this branch after movement distance passes `nextStep`, when the moved-over state is non-air and the entity is not in water. 1.18.2 requires an event-emitting support state and rejects a support state tagged `OCCLUDES_VIBRATION_SIGNALS`. 1.19.2 replaces that second condition with movement/support predicates: the entity is grounded, the requested vertical delta is exactly zero, it is in powder snow, or the support state is climbable or powder snow. 1.19.2 also supplies the current entity and `getBlockStateOn()` in the event context.

The `GameEvent.STEP` dispatch condition and source context therefore differ within the core movement path.

## Movement meaning

Local player movement -> `LocalPlayer.move()` -> `Entity.move()` -> collision-resolved displacement and step-distance update -> non-water step-event branch -> level game-event listeners.

This comparison establishes the changed event dispatch, not whether a particular listener accepts it or causes a later block-state change.

Relevant listener configuration, vibration tags and resource-backed world outcomes remain in C03/W04; 1.19.2's `getBlockStateOn()` source is covered by the C02 support-lookup comparison.

Source-proven: under the respective guards, 1.19.2 can dispatch a step event in cases where 1.18.2's support-block occlusion gate rejects it, and the event context can include an explicit affected block state.

The changed branch contains no direct player position or velocity write.

Predicted consequence: a reachable event listener may respond differently and change surrounding world state.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.18.2--1.19.2/findings/F-003.md).
