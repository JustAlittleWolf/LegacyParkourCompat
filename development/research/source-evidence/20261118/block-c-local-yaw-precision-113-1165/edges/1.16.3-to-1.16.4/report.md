# 1.16.3–1.16.4 `Entity.turn` yaw precision

## Exact source identities

The read-only local Mojmap trees are `decompiled_minecraft/1.16.3/mojmap/` and `decompiled_minecraft/1.16.4/mojmap/`. Their `.decompile-provenance.json` manifests identify exact releases and pin distinct Mojang client artifacts and official client mappings:

| Release | Client artifact (manifest SHA-256) | Official client mappings (manifest SHA-256) | Mojang version metadata package (manifest SHA-256) |
| --- | --- | --- | --- |
| 1.16.3 | `https://piston-data.mojang.com/v1/objects/1321521b2caf934f7fc9665aab7e059a7b2bfcdf/client.jar` (`da85197babd989a7de318774cf5d97bcef95e89a70c9fa9a4c8180463e9589fa`) | `https://piston-data.mojang.com/v1/objects/faac5028fbca3859db970cc4ca041aeec55f6d9d/client.txt` (`c41c58faaf3a3b651d8883b9b1df355cb6ad59c4d6296b2e1ffc3dfe2bf9be3e`) | package `6485dd131ef68c968041a9f6fd73094b027e42e1/1.16.3.json` (`193c5226095ce9e8a317f1e35ddbe4d976f176f6df7ac649ecfa669e17160ead`) |
| 1.16.4 | `https://piston-data.mojang.com/v1/objects/1952d94a0784e7abda230aae6a1e8fc0522dba99/client.jar` (`d6740464ee2bdf37d285c5d368d2e16497492427fb5783e2db3482bbf0079ed0`) | `https://piston-data.mojang.com/v1/objects/0837de813d1a6b67e23da3c520a84e872c8d2f0e/client.txt` (`93f188a0038fec8c2402d78e7acca983680978d6a9237c1fab80f22caddb00fe`) | package `596ad61fda7612d9edf8881cf81869276bdb7f82/1.16.4.json` (`667fff4ca7fd0f56ab78b757224515e8e64375868e8d1ce6040091d7b63a0cb5`) |

Both manifests record pipeline `lpc-decompile-v1` with the same pinned local remapper/decompiler tool hashes and Java 25 runtime (`Eclipse Adoptium:25.0.3+9-LTS:Windows 11:amd64`). For each inspected class, the manifest `sources` hash matches the on-disk SHA-256:

| Relative Mojmap source | 1.16.3 SHA-256 | 1.16.4 SHA-256 |
| --- | --- | --- |
| `net/minecraft/world/entity/Entity.java` | `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666` | `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666` |
| `net/minecraft/world/entity/LivingEntity.java` | `b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88` | `b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88` |
| `net/minecraft/world/entity/player/Player.java` | `d2e26589bdb6a20dc914266db06aa48f50811efc792d6e63b3008c9199914960` | `d2e26589bdb6a20dc914266db06aa48f50811efc792d6e63b3008c9199914960` |

## Bounded comparison

`Entity.turn(double d, double e)`, `Entity.java:340–352`, is byte-identical across these exact releases. For yaw only, it computes `g = d * 0.15` as a `double`, then writes current yaw as `(float)(this.yRot + g)` and previous yaw as `(float)(this.yRotO + g)`, in that order. There is no yaw clamp, normalization or wrap here. The pitch-specific writes/clamps in the enclosing method are outside this slice. The vehicle callback at lines 349–351 is not needed for this player-ground/air path and is excluded.

The paired source bodies have identical operation order, widths, casts, and state writes; there is no finite yaw witness that produces a 1.16.3-versus-1.16.4 result difference in this operation. A finite ordinary input illustrates the shared arithmetic: with `d = 10.0`, `yRot = 0.25F`, and `yRotO = 0.125F`, both releases compute `g = 1.5` and store `yRot = 1.75F`, `yRotO = 1.625F` (with finite `e`, e.g. `0.0`).

## First dry ground/air movement consumer

The player path reaches `Player.travel(Vec3)`, `Player.java:1387–1417`, which delegates to `LivingEntity.travel`. In the non-fluid, non-fall-flying branch, `LivingEntity.travel`, `LivingEntity.java:2022–2042`, calls `handleRelativeFrictionAndCalculateMovement`; that method at lines 2062–2065 invokes `moveRelative`. `Entity.moveRelative`, `Entity.java:1075–1078`, passes current `this.yRot` to `getInputVector`. The first grounded/airborne yaw-based movement consumer is therefore this shared `moveRelative` operation. Its input-vector logic at `Entity.java:1080–1090` is identical between the releases, as is the relevant `LivingEntity.travel` and `Player.travel` source. The downstream acceleration/friction and trig are cited only to establish reachability; no change in those computations is claimed.

**Verdict for the assigned slice:** no 1.16.3 → 1.16.4 `Entity.turn` yaw-precision delta found. This closes only this bounded direct-turn/yaw-consumer slice; it does not assert full-pair coverage or a precise intermediate patch boundary.
