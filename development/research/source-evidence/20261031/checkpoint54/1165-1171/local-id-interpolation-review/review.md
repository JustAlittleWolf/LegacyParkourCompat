# Independent review: local-ID packet rotation interpolation

## Verdict

**ACCEPT as a conditional client-receiver movement candidate under an identical accepted inbound relative-move packet and equal LocalPlayer state/input.** The client receiver can resolve the packet's entity ID to its on-foot LocalPlayer, and its state gates select the inherited `LivingEntity.lerpTo` interpolation. The first yaw interpolation value differs by one float ULP at the 1.16.5→1.17 boundary; the ordinary local travel path can consume that yaw in `Entity.getInputVector`/`Mth.sin` and add a different horizontal movement component. The 1.17 implementation remains in 1.17.1.

This is a conditional receiver result, not a claim of ordinary vanilla self-entity traffic: the examined vanilla `ChunkMap.TrackedEntity.updatePlayer` method expressly excludes its own `ServerPlayer` (`serverPlayer != this.entity`) before sending tracked entity movement. That production gate means the reviewed tracking path does not normally deliver those packets to the tracked player's own client. Server production is outside the accepted receiver claim; no ordinary-server reachability or runtime trajectory is asserted.

## Frozen scope and exact source identity

The source roster was frozen before consulting the proposing packet report. The owned `roster.md` gives the complete focused route. `source-hashes.tsv` records 27 version/path source hashes and matched every present hash against 1.16.5/1.17.1 publication source sidecars or the 1.17 embedded provenance table. The file/manifest comparisons were mechanically recomputed from the exact sources. `publication-identities.tsv` records the parent sidecar/provenance digests used. Both TSVs have adjacent SHA-256 sidecars. No raw JAR or mapping artifact was reopened.

## Receiver, dynamic dispatch, and eligibility

- `ClientPacketListener.handleMoveEntity` (`ClientPacketListener.java:628–648` in 1.16.5; `:510–530` in 1.17 and 1.17.1) obtains an entity by packet ID and enters the update when `entity != null && !entity.isControlledByLocalInstance()`. For a no-passenger client-side LocalPlayer, `getControllingPassenger()` is null and `Entity.isControlledByLocalInstance()` returns `!level.isClientSide`, hence false; the handler's negated gate passes. `handleTeleportEntity` has the same receiver condition (`:602–617` / `:484–498` / `:484–498`).
- `LocalPlayer` inherits `LivingEntity.lerpTo(double,double,double,float,float,int,boolean)`; it does not override that method. Dynamic dispatch therefore selects `LivingEntity.lerpTo`, which stores the target position/angles and `lerpSteps` rather than calling `Entity.lerpTo` immediately (`LivingEntity.java:2585–2592` / `:2693–2700` / `:2697–2704`). A report that resolves this call directly to the base `Entity.lerpTo` body would miss the actual local-player route.
- In `LivingEntity.aiStep`, the local-instance branch clears `lerpSteps` only when `isControlledByLocalInstance()` is true. For an on-foot LocalPlayer that predicate is false. The queued target is consequently interpolated in the next player AI step. `LocalPlayer.aiStep()` calls `super.aiStep()` (`LocalPlayer.java:788` / `:812` / `:810`), and the inherited living path does interpolation before the travel dispatch. Identical zero turning input preserves the interpolated yaw through that movement call; this assumes equal logical input and does not cover input-device sampling.

## Paired numeric operation and movement consumer

The exact old and new interpolation expressions are at `LivingEntity.java:2378–2385` (1.16.5), `:2461–2467` (1.17), and `:2465–2471` (1.17.1). For yaw, 1.16.5 keeps the wrapped difference as `double`, divides by `lerpSteps` in double and casts after adding to the current float. 1.17/1.17.1 cast the wrapped difference to `float` before float division/addition. The position interpolation remains double and is not part of this claim.

For a fixed relative rotation packet byte `110`, the handler computes target yaw `110 * 360 / 256.0F = 154.6875F`. From the same current yaw `32.60743F` and three interpolation steps, source-order arithmetic gives:

| Result | 1.16.5 | 1.17 / 1.17.1 |
| --- | ---: | ---: |
| Wrapped yaw difference (double) | `122.08007049560547` | `122.08007049560547` |
| First interpolated yaw | `73.30079F`, bits `0x42929A01` | `73.30078F`, bits `0x42929A00` |
| `Mth.sin` lookup index from this yaw | `13344` | `13343` |

The byte-to-yaw operation is present in `ClientPacketListener` and in the exact packet's rotation representation. `Mth.sin(float)` uses `(int)(radians * 10430.378F) & 65535` to index its sine table; `Entity.getInputVector` calculates radians from `yRot` and returns `x = vecZ*cos - vecX*sin`, `z = vecZ*cos + vecX*sin` in the source order. The indexed sine entries differ for the adjacent indices at this angle. With identical forward input `(0,0,1)`, positive movement speed and zero prior horizontal velocity, the `moveRelative` addition therefore has different X/Z components. The ordinary player travel path calls `handleRelativeFrictionAndCalculateMovement` and `Entity.moveRelative`; on-ground movement with positive movement speed and a clear horizontal path satisfies this consumer. A reproduced arithmetic component is an operation-level witness, not a completed tick trajectory.

## Vanilla sender boundary and limits

`ChunkMap.TrackedEntity.updatePlayer` guards tracked replication with `serverPlayer != this.entity` in all three versions (1.16.5 `ChunkMap.java:1158–1160`; 1.17 `:1139–1142`; 1.17.1 `:1143–1146`). Thus the examined normal tracking mechanism does not send this entity move/teleport traffic to the local player's own connection. I retain the finding only for the client receiver's conditional behavior under the same accepted inbound packet; it is not evidence that vanilla server tracking generates the condition. The equality condition also fixes the packet ID, byte rotation, current orientation, `lerpSteps`, logical movement input, movement speed and surrounding movement state across versions.

The review did not audit every alternate server packet producer, network misuse/malformed input, or other response handlers. No packet traffic was injected, and no build, test, game/server launch, source generation, product source/history, wiki or backlog was used.
