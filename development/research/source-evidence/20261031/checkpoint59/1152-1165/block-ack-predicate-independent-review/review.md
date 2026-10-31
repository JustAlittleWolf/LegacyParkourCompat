# Independent review — block-break acknowledgment predicate

Verdict: **ACCEPT**.

Reviewed immutable report `.native-source/20261030/1152-1165-finish43/block-ack-predicate-closure/report.md`, SHA-256 `60b587aaf613805e8f2966d0f6f4f16bd2607913a410b2b4c104e14f3156e925`.

## Evidence checked

The read-only exact Mojmap source roots are `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.15.2\mojmap` and `...\1.16.5\mojmap`. Their ready markers identify versions 1.15.2 and 1.16.5, Mojmap, and `ready`; recomputed source-manifest SHA-256 values match the markers and report. I recomputed each listed relevant source-file hash and they match the report.

- `ClientPacketListener.handleBlockBreakAck` in both releases calls `PacketUtils.ensureRunningOnSameThread` before forwarding `this.level`, packet position/state/action/`allGood` to `minecraft.gameMode.handleBlockBreakAck` (1.15.2 lines 2354–2365; 1.16.5 lines 2380–2391). `Minecraft.player` is declared `LocalPlayer` in both releases; `LocalPlayer` declares no `absMoveTo` override.
- Both `sendBlockAction` methods store under `Pair.of(blockPos, action)` and both callbacks remove under the same pair key. 1.15.2 stores `PosAndRot(localPlayer.position(), localPlayer.xRot, localPlayer.yRot)`; the record fields are pitch then yaw, and callback arguments correctly pass yaw then pitch. 1.16.5 stores only `localPlayer.position()`.
- In 1.15.2, `handleBlockBreakAck` lines 424–434 enters reconciliation for `!allGood`; with a present saved entry it calls `setKnownState` and then five-argument `player.absMoveTo`. Equal current and acknowledged state does not suppress that branch.
- In 1.16.5, lines 434–444 the correction gate is conjoined with `blockState2 != blockState`. Equal state therefore skips reconciliation for any `allGood`/action values, before the `isColliding` guard or any player write.
- The old inherited `Entity.absMoveTo(x,y,z,yaw,pitch)` clamps X/Z, updates previous coordinates, calls `setPos`, then writes current and previous rotations. The new callback invokes the inherited three-coordinate overload, which clamps X/Z, updates previous coordinates and calls `setPos`, with no rotation write. `setPos` updates position and bounding box in both versions. No delta movement write is in either overload.
- The cited `Entity.isColliding(pos,state)` is a direct current-player-box shape intersection. It is not `CollisionGetter.getBlockCollisions` / `CollisionSpliterator`; additionally, the equal-state witness exits the outer 1.16.5 gate before reaching it. The report correctly excludes the separate collision-collector witness.

## Precision notes

These do not change the verdict or the accepted predicate difference:

1. For a witness that changes rotation, choose saved yaw/pitch whose normalized outputs differ from the current rotation. Merely choosing numerically unequal inputs is not sufficient in every case because the old overload applies yaw modulo 360 and clamps pitch before modulo. A distinct saved position alone already witnesses the position/bounding-box difference.
2. The `size() >= 50` cleanup loop runs at the end of `handleBlockBreakAck`, after that callback's exact-key removal; it is not run by `sendBlockAction` when an entry is added. This does not affect the witness conditioned on the matching saved entry being present.

No acknowledgment-frequency, packet-producer or timing proof is needed for this conditional callback-level source witness. This review accepts only the stated bounded callback finding; it does not accept full-pair coverage, an intermediate patch boundary, implementation or runtime parity.
