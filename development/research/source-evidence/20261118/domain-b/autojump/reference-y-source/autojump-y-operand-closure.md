# AutoJump Y operand closure

Outcome: the bounded dependency is closed. In ordinary vanilla local-player movement, `AABB.minY` and the player position Y are equal at the `LocalPlayer.updateAutoJump` callback in both releases. The 1.14.4-to-1.15 source operand substitution therefore has no reachable distinct operand under the callback's grounded gate or its downstream timer scheduling. This does not establish any other AutoJump gate or geometry equivalence.

## Exact source identities

Read-only source root: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft`.

- 1.14.4 Mojmap provenance: `decompiled_minecraft/1.14.4/mojmap.provenance.json`, SHA-256 `9d1ad405e1e787d2fca5ed1897e6797b1f3cd79527da50d713232f46e2f30d97`; `mojmap.ready.json`, SHA-256 `ec7b6a6d9ba72f8a19908527977c970a8e2b3007b0e0b4323a725fb5943d3043`; `mojmap.verification.json`, SHA-256 `34f9ddace437422a36ea148767a1b72a6ff97a8166e404c6698154db730415d9`. Ready marker identifies exact version 1.14.4, Mojmap, 3,250 sources and raw client SHA-256 `b3b2a798e2d67b566008fe4a03767ae2c7ff3f8c7ba6751e7b71fc7299672d0a`. Verification reports all 3,250 published source files identical and no raw input artifact differences.
- 1.15 Mojmap provenance: `decompiled_minecraft/1.15/mojmap.provenance.json`, SHA-256 `de4a538ebbaf82d1615683b08ecae3cd76553345f3079436764eb095be343949`; provenance identifies exact version 1.15, official client mappings, raw client SHA-256 `41eee66b4c8e76c018745a63a46a15097cc1c6482c94b38801054938a010b21b`, and run `prep-1.15-1.15.1-mojmap-2026-10-09-r1`.
- Source manifest hashes: 1.14.4 `mojmap.sources.sha256` = `af98406f3d4ed31494fbdce1078d1f3df3f712501878f9a0e9ad5879bdf23c3b`; 1.15 `mojmap.sources.sha256` = `251a3019ccc3fd067f66365f3545b69a983383a28b19a1038debf1791be29340`. The per-file hashes below match each manifest and were recomputed from the published source files.

| Release | Source file | SHA-256 |
|---|---|---|
| 1.14.4 | `net/minecraft/client/player/LocalPlayer.java` | `0795c1223198ce5acf5d2ed9e5db8435bbec4cd52b96f96b1ddf2865baaae85f` |
| 1.14.4 | `net/minecraft/world/entity/Entity.java` | `31c6be42d165102d3e6dc4295fdfef6e8971fc3aea13f938a5b3a33b91d524a7` |
| 1.14.4 | `net/minecraft/client/multiplayer/ClientPacketListener.java` | `6dddf37b79830d5413d91675e0bed8b4a3f0a179ac4535616dc57b510b778b45` |
| 1.15 | `net/minecraft/client/player/LocalPlayer.java` | `7227da5233e3c72ced008187b92a6a86842f84778eaa9f40fa1eb117286af823` |
| 1.15 | `net/minecraft/world/entity/Entity.java` | `191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e` |
| 1.15 | `net/minecraft/client/multiplayer/ClientPacketListener.java` | `d806d286af4b93d7c61c3c884f344a5870ce1871fb0a811506031adf78fafc30` |

## Bounded dataflow and callback proof

- In 1.14.4, `LocalPlayer.move` calls `super.move` and then `updateAutoJump` (`LocalPlayer.java:845-850`). The grounded candidate path checks `onGround` at `856-862`; the initial block-top query deliberately uses box `maxY` at `884`. Rise limit and scheduled rise use box `minY` at `929` and `947-949`.
- In 1.15, `LocalPlayer.move` likewise calls `super.move` and then `updateAutoJump` (`LocalPlayer.java:851-856`). The gate is `canAutoJump` (`862-863`), the candidate starts at `position()` (`864-866`), the initial block-top query still uses box `maxY` (`888`), and rise limit/scheduling use position Y at `933` and `951-953`.
- The positional invariant is established and maintained by the entity writers. In 1.14.4, `Entity.setPos` stores `y=e` and constructs a box whose minY is `e` (`Entity.java:310-316`); `setLocationFromBoundingbox` copies `bb.minY` into `y` (`791-796`). In 1.15, `setPos` delegates to `setPosRaw` and constructs a box whose minY is `e` (`315-320`); `setLocationFromBoundingbox` copies `bb.minY` through `setPosRaw` (`774-777`). In both versions `Entity.move` moves the box and immediately synchronizes position after nonzero movement (`1.14.4 Entity.java:446-470`; `1.15 Entity.java:450-474`); if displacement is skipped, both values retain their previous invariant. Pose/dimension refresh keeps the lower bound anchored to position Y in each version (`1.14.4 Entity.java:2265-2285`; `1.15 Entity.java:2258-2280`).
- The 1.15 correction path briefly calls `setPosRaw` at `ClientPacketListener.java:712`, but in the same packet handler immediately calls `absMoveTo` at `727`; `Entity.absMoveTo` calls `setPos` (`Entity.java:1094-1105`) before the handler returns. Thus this temporary raw-position write is not an inter-tick or movement-callback state with a stale box. The 1.14.4 correction path calls `absMoveTo`, which calls `setPos` before return (`ClientPacketListener.java:704`; `Entity.java:1116-1138`).
- Grounding is settled before the AutoJump callback: `Entity.move` computes `onGround` from vertical collision before returning (`1.14.4 Entity.java:474-477`; `1.15 Entity.java:478-481`), and the local-player override invokes AutoJump afterward. This permits the grounded gate, but does not break `y == bb.minY`.
- The resulting arithmetic substitutions are equal at each eligible callback: `u - bb.minY` equals `u - y`, and the later float conversion yields the same `w`. Either path therefore reaches the same threshold decision and timer write (`autoJumpTime = 1`) or declines both. The subsequent `aiStep` decrements a positive timer and sets the jump input in both versions (`1.14.4 LocalPlayer.java:638-642`; `1.15 LocalPlayer.java:643-648`), so this operand does not alter downstream scheduling.

No concrete legal vanilla grounded witness with distinct operands exists in the examined dataflow. Remaining bounds are the assignment's explicitly excluded AutoJump differences (including other gates, normalization and collision geometry); they were not reopened.