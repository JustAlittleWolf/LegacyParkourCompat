# Relative entity position codec precision review

## 1.18.2 packet helper vs 1.19 codec

Exact sources, Mojmap:

- 1.18.2 `net/minecraft/network/protocol/game/ClientboundMoveEntityPacket.java:23-35`: `entityToPacket(v) = Mth.lfloor(v * 4096.0)`; `packetToEntity(n) = n / 4096.0`; for each axis, `xa == 0 ? base.x : packetToEntity(entityToPacket(base.x) + xa)` (same y/z).
- 1.19 `net/minecraft/network/protocol/game/VecDeltaCodec.java:10-26`: `encode(v) = Mth.lfloor(v * 4096.0)`; `decode(n) = n / 4096.0`; for each axis, `delta == 0 ? base.x : decode(encode(base.x) + delta)` (same y/z), with an all-zero fast return of `base`.

These are identical per-axis operations, including the zero-delta coordinate preservation, floor, addition order, integer width, division and output Vec3 order. The all-zero fast return produces the same base Vec3 as the three old zero-delta branches. Therefore introducing `VecDeltaCodec` and moving the old helper math into it causes no movement difference on the direct LocalPlayer receive route.

## Base initialization/update order

The replaced packet-coordinate storage begins at `Vec3.ZERO` in 1.18.2 `Entity.java:239`; the codec begins at `Vec3.ZERO` in 1.19 `VecDeltaCodec.java:8`. Across corresponding client/source callers, the old setter is replaced by `syncPacketPositionCodec`/`setBase` at the same point in the sequence:

- Spawned entities: 1.18.2 `Entity.recreateFromPacket` sets packet coordinates before `moveTo` (`Entity.java:2985-2995`); 1.19 syncs the codec base before the same `moveTo` (`Entity.java:3009-3019`). `LivingEntity.recreateFromPacket` likewise sets/syncs the base before the remaining entity initialization (`LivingEntity.java:3321-3332` in 1.18.2; `3364-3375` in 1.19).
- Client-added experience orbs and remote players: `ClientPacketListener.handleAddExperienceOrb` and `handleAddPlayer` replace the coordinate setter with codec synchronization at the corresponding pre-rotation/pre-position initialization point (`ClientPacketListener.java:410-421,459-473` in 1.18.2; `410-?` and `461-478` in 1.19; see source hashes and exact methods).
- Teleport receive: old `setPacketCoordinates(x,y,z)` and new `syncPacketPositionCodec(x,y,z)` both execute before the same control gate, lerp and on-ground write (`ClientPacketListener.java:476-491` in 1.18.2; `483-498` in 1.19).
- Relative movement receive: old decode, setter, rotation selection, then `lerpTo` (`ClientPacketListener.java:502-520` in 1.18.2); new codec decode, `setBase`, the same rotation selection, then `lerpTo` (`ClientPacketListener.java:509-528` in 1.19).
- Ongoing local/control synchronization: the same `isControlledByLocalInstance()` gate, lerp-step reset and current-position base update occur in `LivingEntity.aiStep` (`LivingEntity.java:2461-2469` in 1.18.2; `2494-2502` in 1.19) and `Boat.tickLerp` (`Boat.java:407-411` in 1.18.2; `411-415` in 1.19).

For LocalPlayer, the control result is false on the client in both 1.18.2 and 1.19, as detailed in `route-audit.tsv`; the receiver gate therefore has the same truth value across this refactor. Server-side codec references were found by symbol search only; no producer behavior or runtime claim is made.

**Disposition:** the 1.18.2→1.19 `VecDeltaCodec.java` added row is a movement-equivalent storage/helper refactor, not a movement difference. The LocalPlayer sink is reachable, but this row contributes no change in its math, base order or receiver gate.

## Later changes remain eligible

The 1.19.2 decoder is still floor-based. The 1.19.3 decoder changes from `floor(base * 4096) + delta` to `round(base * 4096) + delta`, retaining the existing per-axis decode; the encoder changes from `floor(position - base)` to `round(position) - round(base)` at 1/4096 scale. With the 1.19.2–1.19.3 LocalPlayer receiver gate still false on the client, that changed quantization reaches the LocalPlayer `handleMoveEntity`/`lerpTo` response path. Keep the 1.19.2→1.19.3 codec row eligible.

## Separate 1.19.3→1.19.4 receiver-gate change

The handler source keeps `if (!entity.isControlledByLocalInstance())` in both versions, but its LocalPlayer result changes:

- 1.19.3 `Entity.java:2742-2745` falls back to `!level.isClientSide`, so a client LocalPlayer with null controlling passenger is not controlled; `LocalPlayer.java:493-495` returns true from `isEffectiveAi()`, but this predicate does not call it. `ClientPacketListener.handleMoveEntity` applies delta/base/lerp (`:552-573`); `handleTeleportEntity` applies lerp and on-ground after syncing the codec base (`:526-541`). `LivingEntity.aiStep` does not take its controlled-instance branch for LocalPlayer (`LivingEntity.java:2515-2521`).
- 1.19.4 `Entity.java:2795-2801` calls `this.isEffectiveAi()` when no controlling passenger; `LocalPlayer.java:486-488` returns true. The negated receiver branches in `ClientPacketListener.handleMoveEntity` (`:565-586`) and `handleTeleportEntity` (`:539-554`) are therefore filtered for LocalPlayer. Teleport still syncs the packet codec base before the gate, but the branch no longer lerps position/rotation or writes on-ground. `LivingEntity.aiStep` now enters the controlled-instance branch and resets lerp steps/syncs the codec base (`LivingEntity.java:2459-2467`).

Bound source hashes (also in `source-hashes.tsv`):

| Version | `Entity.java` | `LocalPlayer.java` | `ClientPacketListener.java` |
|---|---|---|---|
| 1.19.3 | `8c35bdef3bf2de7b70ac76ad767ba2c194305e0dda2e03fdf6544983667adc32` | `64b670ee323d195b3928fb8ea629c26d2560e75a17379a12c376a7bc686d5479` | `c01c3c362e5eb7de780f4ca0f1b8964a49e40cfc166ae63cbe392cd71b6b1b9e` |
| 1.19.4 | `3667fee610cbc5f58012e3a8fb8d6c4849f649fb7fe5300595158112d8b4b58b` | `8e7da18f42d09fbb994f522c2b0e65fcb2bb83cabb21024360299d44d9674c58` | `bcc74e52a32d20a3e993ebe4e08fffe8caca7481fad8f326dacdc93796b2b715` |

This receiver-gate change is surfaced separately from codec arithmetic so the coordinator can assign its technical-operation ownership. It is a source-path finding only; no producer proof is claimed. The exact `Entity.java`, `LocalPlayer.java`, and `ClientPacketListener.java` hashes bind the contrasted source files.
