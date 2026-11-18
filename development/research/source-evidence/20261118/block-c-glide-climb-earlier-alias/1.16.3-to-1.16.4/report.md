# 1.16.3–1.16.4 glide fall distance at climbables

## Bounded result

For an ordinary dry, on-foot local player whose flight flag is already true, whose chest Elytra remains enabled, and whose feet block is an ordinary ladder or vine in `BlockTags.CLIMBABLE`, both exact releases take the same Elytra travel branch. The branch has no climbable predicate. Before calculating the new glide velocity or moving, it assigns `fallDistance = 1.0F` only when the pre-move vertical delta is strictly greater than `-0.5`. The climbable fall-distance reset is confined to the separate non-flight friction path. The compared releases therefore show no difference for this bounded operation.

This does not establish how flight became active, guarantee a ladder trajectory, establish end-of-tick fall distance, or close the full 1.16.3–1.16.4 movement pair.

## Exact source identity and hashes

Canonical read-only source root: `decompiled_minecraft/<version>/mojmap/`. Each ignored `.decompile-provenance.json` manifest is present and its recorded source hashes match the actual source-file SHA-256 values below. The manifests record the same pipeline/toolchain (`lpc-decompile-v1`, Vineflower 1.12.0, single-threaded) and Java 25 runtime on Windows 11 x64.

| Release | Provenance manifest SHA-256 | Pinned client and Mojmap input | Release metadata pin |
| --- | --- | --- | --- |
| 1.16.3 | `513271804ead6757345d305717e7e0bfc1641434c35a3b254f287bd8fd281498` | `client.jar` `da85197babd989a7de318774cf5d97bcef95e89a70c9fa9a4c8180463e9589fa`; Mojang `client.txt` `c41c58faaf3a3b651d8883b9b1df355cb6ad59c4d6296b2e1ffc3dfe2bf9be3e` | `1.16.3.json` `193c5226095ce9e8a317f1e35ddbe4d976f176f6df7ac649ecfa669e17160ead` |
| 1.16.4 | `1e04555c915527a398a2c7150679fb24d056f54bd680261e3c1dd55da821147c` | `client.jar` `d6740464ee2bdf37d285c5d368d2e16497492427fb5783e2db3482bbf0079ed0`; Mojang `client.txt` `93f188a0038fec8c2402d78e7acca983680978d6a9237c1fab80f22caddb00fe` | `1.16.4.json` `667fff4ca7fd0f56ab78b757224515e8e64375868e8d1ce6040091d7b63a0cb5` |

The corresponding pinned input URLs are `https://piston-data.mojang.com/v1/objects/1321521b2caf934f7fc9665aab7e059a7b2bfcdf/client.jar`, `https://piston-data.mojang.com/v1/objects/faac5028fbca3859db970cc4ca041aeec55f6d9d/client.txt`, and `https://piston-meta.mojang.com/v1/packages/6485dd131ef68c968041a9f6fd73094b027e42e1/1.16.3.json` for 1.16.3; and `https://piston-data.mojang.com/v1/objects/1952d94a0784e7abda230aae6a1e8fc0522dba99/client.jar`, `https://piston-data.mojang.com/v1/objects/0837de813d1a6b67e23da3c520a84e872c8d2f0e/client.txt`, and `https://piston-meta.mojang.com/v1/packages/596ad61fda7612d9edf8881cf81869276bdb7f82/1.16.4.json` for 1.16.4. URLs identify the locally pinned manifest inputs; no online mapping lookup was used.

| Relative source file | SHA-256 in 1.16.3 | SHA-256 in 1.16.4 | Manifest match |
| --- | --- | --- | --- |
| `net/minecraft/world/entity/LivingEntity.java` | `b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88` | `b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88` | both |
| `net/minecraft/world/entity/player/Player.java` | `d2e26589bdb6a20dc914266db06aa48f50811efc792d6e63b3008c9199914960` | `d2e26589bdb6a20dc914266db06aa48f50811efc792d6e63b3008c9199914960` | both |
| `net/minecraft/client/player/LocalPlayer.java` | `6011569e766bb1568609147be9aa14e9c08c51948e3d3a60fd066e848f6a8c2b` | `6011569e766bb1568609147be9aa14e9c08c51948e3d3a60fd066e848f6a8c2b` | both |
| `net/minecraft/world/entity/Entity.java` | `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666` | `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666` | both |

## Route and operation order

All references below are to the matching Mojmap source in both releases; the four relevant files have identical bytes across the pair.

- `LocalPlayer.aiStep` line 725 prevents the *start* input path from starting flight while `onClimbable()` is true. This is not a stop rule for an already active flag. It records `wasFallFlying` at line 732 and calls `super.aiStep()` at line 788.
- `LivingEntity.aiStep` lines 2454–2459 calls `updateFallFlying()` and then dynamically dispatches `travel(...)`. `updateFallFlying` lines 2478–2497 retains the true client shared flag when the player is airborne, not a passenger, lacks Levitation, and has an enabled chest Elytra. The climbable predicate is absent; the method only writes the flag back on the logical server.
- `Player.travel` lines 1387–1416 only diverts to its creative-flight path when `abilities.flying && !isPassenger()`. Under the stated ordinary on-foot assumptions that is false, so it calls `LivingEntity.travel`.
- `LivingEntity.travel` lines 1923–1979 first bypasses the water and lava branches for the stated dry witness, then tests `isFallFlying()`. The Elytra branch at lines 1979–2021 snapshots current movement as `vec36`; lines 1981–1983 perform `if (vec36.y > -0.5) this.fallDistance = 1.0F;`. It then computes glide movement, writes velocity, and calls `move` at line 2008. There is no `onClimbable()` test or further direct `fallDistance` assignment in this branch. Equality at `vec36.y == -0.5` does not satisfy the gate; values below it also skip the assignment.
- Climbable handling is in the `else` non-flight path: `LivingEntity.travel` lines 2022–2042 calls `handleRelativeFrictionAndCalculateMovement`; lines 2062–2071 invoke `handleOnClimbable` before movement. `handleOnClimbable` lines 2089–2103 resets `fallDistance` to `0.0F` when `onClimbable()` is true. `onClimbable` lines 1363–1384 reads the feet state and accepts `BlockTags.CLIMBABLE` (as well as a separately gated ladder-trapdoor case). That cleanup cannot execute on the already-active Elytra branch.
- The `1.0F` write is pre-move, not a final tick result. `Entity.move` lines 513–518 records collision/ground state and calls `checkFallDamage` with the realized vertical displacement. `LivingEntity.checkFallDamage` lines 256–278 delegates to `Entity.checkFallDamage`; lines 894–903 reset distance on ground or add the realized downward displacement when negative. Thus a landing can immediately reset the pre-move value, and a downward realized move can increase it.

## Pair boundary

The strict gate, assignment type/value, branch precedence, local active-flag update, and climbable cleanup route are byte-identical in the inspected 1.16.3 and 1.16.4 source files. This is a bounded endpoint no-difference result for an already-active dry Elytra-climbable witness; it does not prove an intermediate patch boundary or general end-of-tick parity.


