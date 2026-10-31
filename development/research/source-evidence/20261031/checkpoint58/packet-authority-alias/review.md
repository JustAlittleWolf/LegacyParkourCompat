# Blind source applicability alias review

## Scope and evidence identity

This review covers exact Mojmap sources from the read-only tree `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft` for 1.16.2, 1.16.3, 1.16.4, 1.16.5, 1.17, 1.17.1, 1.18, 1.18.1, 1.18.2, 1.19, 1.19.1, 1.19.2, and 1.19.3. I also inspected 1.19.4 only as the contrast release for the accepted control-gate change.

`source-hashes.tsv` records 75 exact-file hashes for `Entity`, `LivingEntity`, `Player`, `LocalPlayer`, `ClientPacketListener`, and `VecDeltaCodec` where present. Each selected source hash matched that release's `mojmap.sources.sha256` entry or, for 1.16.3, 1.16.4, and 1.17, the corresponding `sources` entry in its embedded `.decompile-provenance.json`. The per-release source-manifest/provenance identity is recorded on every row. `operation-hashes.tsv` stores normalized method/block fingerprints and source line anchors for the control predicate, dynamic player overrides, `LivingEntity.aiStep`, packet handlers, and `VecDeltaCodec` operations. The tables include 1.19.4 as the boundary comparator.

## Control predicate result through 1.19.3

Across all thirteen requested older releases, `Entity.getControllingPassenger()` returns `null`, and it has no `Player` or `LocalPlayer` override. `Entity.isControlledByLocalInstance()` is the only declaration; its semantics are the same in every inspected release:

```java
Entity controller = this.getControllingPassenger();
return controller instanceof Player
    ? ((Player) controller).isLocalPlayer()
    : !this.level.isClientSide;
```

The source changes local-variable names at 1.18, but the normalized predicate fingerprint is stable within each syntactic naming family: 1.16.2–1.17.1 and 1.18–1.19.3. The values of the relevant dynamic methods are stable throughout the interval: `Player.isLocalPlayer()` returns `false`; `LocalPlayer.isLocalPlayer()` returns `true`; and `LocalPlayer.isEffectiveAi()` returns `true`. Neither `Player` nor `LocalPlayer` overrides `getControllingPassenger()` or `isControlledByLocalInstance()`.

Therefore the client-side on-foot LocalPlayer's own predicate value is **false** throughout 1.16.2–1.19.3: its inherited controller is null, and the fallback is `!level.isClientSide`. `LocalPlayer.isEffectiveAi() == true` does not affect this result because this predicate does not call `isEffectiveAi()` in these releases. For a different entity whose controlling passenger is the LocalPlayer, the Player branch dynamically calls `LocalPlayer.isLocalPlayer()` and returns true throughout the same interval. The alias does not imply that all entities controlled by the LocalPlayer are excluded.

In 1.19.4 only, the fallback changes to `this.isEffectiveAi()`. The LocalPlayer override then makes its own client-side predicate true. The base getter's declared return type also narrows from `Entity` to `LivingEntity`, while its base implementation still returns null; this does not change the on-foot LocalPlayer path. This agrees with the separately accepted immutable finding `local-player-control-gate.md` (SHA-256 `ddfa7b209c53d9b125c9e66b2605147d4a48d18b83145e2f31726686f8d0f5ef`); this review does not revise or rebind that claim.

## Callers and state consequences

- `ClientPacketListener.handleMoveEntity` and `handleTeleportEntity` use the same negated gate, `!entity.isControlledByLocalInstance()`, in every older release. When packet ID lookup resolves to the client LocalPlayer, both handlers enter their position/rotation/on-ground response branch through 1.19.3. In 1.19.4, the unchanged negated gates skip those response branches for LocalPlayer. This is a receiver-side source result only; no packet producer or ordinary-server traffic claim is made.
- The response arithmetic and state writes are separate from this predicate alias. Through 1.18.2, the relative-move handler calls `ClientboundMoveEntityPacket.updateEntityPosition` using packet coordinates; from 1.19 it decodes through `VecDeltaCodec` and updates its base. Teleport handling writes packet coordinates before the negated gate through 1.18.2; from 1.19 it calls `syncPacketPositionCodec` before the gate. Thus that teleport base update still happens for LocalPlayer in 1.19–1.19.3 even while the gated lerp and on-ground write are taken. Relative decode quantization is floor-based and aliased across 1.19, 1.19.1, and 1.19.2; 1.19.3 changes the quantizer to `Math.round`, as captured by the separately accepted `vec-delta-position-rounding.md` (SHA-256 `95f07ebe0373988fbe62560bdbc0b8b914e4a2c352309b23a9cb3e4eebff3efa`). This review does not revise that claim.
- In `LivingEntity.aiStep`, the `isControlledByLocalInstance()` branch is present in every inspected release. It clears `lerpSteps` and synchronizes the packet-position base. That branch uses `setPacketCoordinates(getX(), getY(), getZ())` through 1.18.2, then `syncPacketPositionCodec(getX(), getY(), getZ())` from 1.19. The branch body is a distinct state-synchronization boundary, not a predicate difference. For on-foot LocalPlayer the branch is skipped through 1.19.3, so queued interpolation continues; it is entered in 1.19.4, clearing the queued interpolation and synchronizing the codec base. The lerp arithmetic block itself is unchanged from 1.17 through 1.19.4, apart from the older 1.17 yaw-expression boundary below.
- `LivingEntity.travel` is another caller worth keeping separate. Through 1.19.3 it accepts `isEffectiveAi() || isControlledByLocalInstance()`; 1.19.4 uses only `isControlledByLocalInstance()`. For the LocalPlayer the movement gate remains true on both sides: the older `isEffectiveAi()` override already returns true, and the 1.19.4 control predicate now returns true. The changed predicate therefore alters packet-response and `aiStep` gates without changing this LocalPlayer travel gate's truth value.

## Separate interpolation arithmetic boundary

The `LivingEntity.aiStep` lerp branch is reachable for on-foot LocalPlayer in the older interval because its control predicate is false and packet interpolation steps are not cleared. Its body-yaw update has a separate 1.16.5→1.17 arithmetic change: 1.16.2–1.16.5 retain the wrapped yaw difference as `double`, divide by `lerpSteps`, then cast after addition to the float yaw; 1.17 onward casts the wrapped difference to `float` before division/addition. This is not part of the control-predicate claim. The immutable accepted local-ID interpolation review documents the 1.16.5→1.17 movement consequence (review SHA-256 `12ef5845ca4389c7acc578a6b7bcbcdfb358216b20f0a17faf48b05062a6cf0a`).

## Decoder alias

In `VecDeltaCodec`, 1.19, 1.19.1, and 1.19.2 share the floor encoder `Mth.lfloor(value * 4096.0)` and the decoder `encoded / 4096.0`. The vector decoder returns the base for an all-zero delta; per axis it preserves the unquantized base when that axis delta is zero, and otherwise computes `decode(encode(baseAxis) + delta)`. The exact decoder-vector block fingerprint is identical across 1.19–1.19.3; the effective base quantization changes only when the helper encoder changes in 1.19.3 from floor to `Math.round`. This confirms the floor decoder alias through 1.19.2 before the accepted rounding boundary.

## Distinct source boundaries retained

1. The control-predicate result for LocalPlayer is stable false across the complete requested older interval; it becomes true at 1.19.4 through the fallback substitution. The `Player`/`LocalPlayer` dynamic override values do not change at that boundary.
2. `LivingEntity.aiStep` packet-base synchronization changes from packet coordinates to the packet-position codec at 1.19, independently of the control predicate.
3. `ClientPacketListener` switches its relative position response to `VecDeltaCodec` in 1.19; its decode quantization changes at 1.19.3, independently of the gate.
4. The eligible LocalPlayer interpolation path has the separate 1.16.5→1.17 body-yaw arithmetic change.

No exact intermediate patch cutover between the listed releases is inferred. The review establishes receiver and player-source applicability only and does not inspect packet producers.
