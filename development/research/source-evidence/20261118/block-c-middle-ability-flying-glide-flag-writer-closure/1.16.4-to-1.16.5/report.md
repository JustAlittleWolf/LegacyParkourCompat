# 1.16.4 → 1.16.5 source trace: local Elytra and ability flight

## Evidence identity

- Compared canonical local Mojmap trees: `decompiled_minecraft/1.16.4/mojmap/` and `decompiled_minecraft/1.16.5/mojmap/` (read-only).
- 1.16.4 has `decompiled_minecraft/1.16.4/mojmap/.decompile-provenance.json`, SHA-256 `1e04555c915527a398a2c7150679fb24d056f54bd680261e3c1dd55da821147c`. It records pipeline `lpc-decompile-v1` with one thread, generic signatures, ASCII output, and Mojmap input identities: client mapping `https://piston-data.mojang.com/v1/objects/0837de813d1a6b67e23da3c520a84e872c8d2f0e/client.txt`, SHA-256 `93f188a0038fec8c2402d78e7acca983680978d6a9237c1fab80f22caddb00fe`; client jar `https://piston-data.mojang.com/v1/objects/1952d94a0784e7abda230aae6a1e8fc0522dba99/client.jar`, SHA-256 `d6740464ee2bdf37d285c5d368d2e16497492427fb5783e2db3482bbf0079ed0`; version metadata `https://piston-meta.mojang.com/v1/packages/596ad61fda7612d9edf8881cf81869276bdb7f82/1.16.4.json`, SHA-256 `667fff4ca7fd0f56ab78b757224515e8e64375868e8d1ce6040091d7b63a0cb5`. The manifest's source hashes match the four checked 1.16.4 files below.
- 1.16.5 has no mapping-root `.decompile-provenance.json`, no `.minecraft-decompile-cache/1.16.5` directory, and no manifest-bound source hashes. Its resident files are byte-identical to the checked 1.16.4 files, but their exact 1.16.5 source/mapping provenance is not established here. Thus this is a comparison of resident source bytes, not a fully pinned exact-release pair verdict.
- Resident source SHA-256 on both sides (each pair of files hashes identically):
  - `net/minecraft/client/player/LocalPlayer.java`: `6011569e766bb1568609147be9aa14e9c08c51948e3d3a60fd066e848f6a8c2b`
  - `net/minecraft/world/entity/player/Player.java`: `d2e26589bdb6a20dc914266db06aa48f50811efc792d6e63b3008c9199914960`
  - `net/minecraft/world/entity/LivingEntity.java`: `b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88`
  - `net/minecraft/world/entity/Entity.java`: `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666`

## Bounded trace

The checked LocalPlayer input section is `LocalPlayer.java:705–733` in both trees. With `abilities.mayfly`, normal flying is toggled by the existing jump-trigger path (or maintained by `isAlwaysFlying()`); `bl8` prevents the same jump from also starting Elytra flight. The Elytra-start condition requires jumping, not `bl8`, not `bl`, not already flying, not a passenger, and not climbable; it then reads the chest slot and requires `Items.ELYTRA`, `ElytraItem.isFlyEnabled`, and `tryToStartFallFlying()`. A successful start sends the start-fall-flying command. The following statement snapshots `wasFallFlying`.

For the requested dry, airborne, nonpassenger ability-flight path, `Player.travel(Vec3)` is identical at `Player.java:1387–1417`. It enters the ability branch on `abilities.flying && !isPassenger()`, saves the current vertical delta and flying speed, sets flying speed from ability speed (doubled while sprinting), then calls `super.travel(vec3)`. That reaches `LivingEntity.travel`, whose active Elytra branch (`LivingEntity.java:1979–2021`) performs its glide math and calls `this.move(MoverType.SELF, this.getDeltaMovement())` at line 2008. Dynamic dispatch reaches `LocalPlayer.move` (`LocalPlayer.java:862–868`), which calls `super.move` before its auto-jump update; `Entity.move` begins at `Entity.java:485` and applies the actual bounding-box movement.

After `super.travel` and that movement return, `Player.travel` restores vertical velocity to the saved `i * 0.6`, restores the prior flying speed, sets `fallDistance = 0.0F`, and then calls `setSharedFlag(7, false)` (`Player.java:1402–1412`). These are distinct writes, in that order: the fall-distance reset does not itself clear the Elytra shared flag. The earlier Elytra branch can set `fallDistance = 1.0F` when vertical velocity is greater than `-0.5`; its own landing flag clear is gated by `onGround && !level.isClientSide`, so it does not perform that clear for an airborne local client. The later Player ability-flight clear is unconditional within the nonpassenger ability-flight branch and runs after the actual movement.

## Comparison result and limit

All four relevant resident source files are byte-identical across the two directories, so the traced operations, gates, call order, movement dispatch, casts, and state writes have no difference in the bytes examined. Do not publish this as an accepted exact 1.16.4→1.16.5 no-difference finding until the 1.16.5 source tree has bound provenance; its missing manifest/cache evidence is the remaining source-identity gate. No broader tick-graph or pair coverage is claimed.
