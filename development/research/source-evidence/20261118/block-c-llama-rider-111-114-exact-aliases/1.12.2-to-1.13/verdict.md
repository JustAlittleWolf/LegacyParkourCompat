# Independent source audit — Llama rider Y position

**Verdict: accept the bounded no-difference claim.** For an ordinary adult Llama with a local player as its passenger, the direct rider-position callback and the Y arithmetic match in exact 1.12.2 and exact 1.13. The audit covers only this operation and its dispatch/gates; it does not close either release pair.

## Bound claim and immutable input

Reviewed report: `C:\Users\Wolfi\.codex\visualizations\2026\11\17\01a1eb7b-b9ff-7a20-8686-0295dde20f26\c-llama-1122-113-position-intermediate\report.md`

Report SHA-256: `7e4cf7268484b1ab281a131d250fa84864060ceebb303216041d6d20117e1280`

The report digest above is the SHA-256 computed from the report’s exact bytes. Its cited seven source files on each release match the current canonical files and each source manifest entry.

## Exact source identities

Both source trees are canonical `ornithe-feather` Vineflower output. The release metadata says `versionId=1.12.2` and `versionId=1.13`; the latter is exact 1.13, not 1.13.2. The 1.13 provenance resolves `net.ornithemc:feather-gen2:1.13+build.2`.

| Release | Provenance SHA-256 | Ready marker SHA-256 | Source manifest SHA-256 |
|---|---|---|---|
| 1.12.2 | `747425a26da363d1abc8a2cecd83a74504aeb1f58be93aa53f48c690e35318ce` | `b0aeec721e5c0af02b33d9e217cb8272889fa139ded1ecc2a31e42b930138f7a` | `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da` |
| 1.13 | `34652aeef6914b5cbff6619ae60a855aa676b2ae1f607eccdaa623ad09dc5e8d` | `82a4a8f6a03fbd99a7b8577b1ae5980d95af07c7ded0da284106d16eb1e01a99` | `0509c2614b0bb0e616e2ebc6382f8eb5d031b5d86b834a4a6ed7fd96b6d68f1a` |

Full SHA-256 values for the reviewed source files (`decompiled_minecraft/<release>/ornithe-feather/<path>`):

| Source path | 1.12.2 | 1.13 |
|---|---|---|
| `net/minecraft/world/World.java` | `e9fa9b8d6d31ad57a5b876f5f63a5e3c554a23437decf1845a493daf48233594` | `6e3d94e6cdcfbb15472c4ee981a1c12500eaf7cd5d5dc20d75bcde91174c2eb5` |
| `net/minecraft/entity/Entity.java` | `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a` | `09514488d9055449c47e2e4bcd23b5773203ca634b7ecba2323a99d0ae016642` |
| `net/minecraft/entity/living/LivingEntity.java` | `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6` | `c38e95c2c13e088b03d77fc2c6b98d4edd7e6ca5aef12b187c8e3f3ea782e864` |
| `net/minecraft/entity/living/player/PlayerEntity.java` | `e4e0fdbe07a7d0a0ae4a70cbb6739a287c9d045a4a12b409895c220b5d91fe1e` | `4a57ce65405436aeb211d7a04a9a9f6ae1a661d033fd3ba2424909bfb42b8acd` |
| `net/minecraft/client/entity/living/player/ClientPlayerEntity.java` | `207a0ff4ee996916cd1fc73f47bfaae64f92719916cafed8395b1f2a8f797809` | `99b57acea68323125d859313ef5dd2c55d0703c08c5cce475c0227da0d12d1ee` |
| `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `01a58e94d8c6ff98a8e3794227cdc76a5fcbdbad795c70c9cf28854aff9823cc` | `a188e1bc87831dd9da1ec1366a49acc4104c18e6979129097d02d5d3617e6a08` |
| `net/minecraft/entity/living/mob/passive/animal/LlamaEntity.java` | `487c7209a69391f1a19dce5fbdf34d609d2e9bc53c47b04a225e9c0d0c93cf7e` | `464c8e8056e5ece43f496f022aaf1474a2c3a95ac49817812ef914495a639015` |
| `net/minecraft/client/Minecraft.java` (tick entry checked in this audit) | `8af833a5d03a6f6516a7eff0ab463a96bfec64aac31fc80656f37d193b1c1310` | `cac2f11502e58c76d7a0319756a6caecf632c8166536bd4a5b634052bcb73d05` |

## Source review

- The client main tick calls `world.tickEntities()` (1.12.2 `Minecraft.java:1468`; 1.13 `Minecraft.java:1142`). In `World.tickEntities`, ordinary entities other than `ServerPlayerEntity` are passed to `tickEntity` (1.12.2 `World.java:1290–1292`; 1.13 `World.java:1006–1008`). The local client player is not the excluded server-player type.
- The concrete mounted-player route is recursive: after ticking a mount, `World.tickEntity` iterates its passengers and calls `tickEntity(passenger)` when the passenger is not removed and still names that mount (1.12.2 `World.java:1473–1477`; 1.13 `World.java:1191–1195`). With `requireLoaded && entity.inChunk`, a riding passenger enters `rideTick()` (1.12.2 `World.java:1427–1433`; 1.13 `World.java:1143–1151`). This recursive passenger leg makes the callback path explicit; the draft’s tick-gate description was accurate but did not spell out this leg.
- `PlayerEntity.rideTick()` has a server-only sneaking dismount branch (`!world.isClient`); otherwise it delegates to `LivingEntity.rideTick()`, which delegates to `Entity.rideTick()`. `LocalClientPlayerEntity.rideTick()` delegates to `super` first (1.12.2: `PlayerEntity.java:375–385`, `LivingEntity.java:1917–1921`, `LocalClientPlayerEntity.java:820–827`; 1.13: `PlayerEntity.java:425–435`, `LivingEntity.java:2020–2024`, `LocalClientPlayerEntity.java:841–848`).
- Generic `Entity.rideTick()` stops if its mount is removed; otherwise it zeroes the rider’s velocity, ticks the rider, then invokes the saved mount’s virtual `updateRiderPositon(this)` only if the rider is still riding (1.12.2 `Entity.java:1582–1593`; 1.13 `Entity.java:1574–1585`). The Llama override adds `hasPassenger(rider)` as its callback gate (1.12.2 `LlamaEntity.java:143–150`; 1.13 `LlamaEntity.java:145–152`).
- The Llama constructor sets default size to `0.9F, 1.87F` (1.12.2 `LlamaEntity.java:60–63`; 1.13 `LlamaEntity.java:62–65`). Its callback contains the class’s one `setPosition` occurrence in each exact source. Both Y arguments are exactly `this.y + this.getMountHeight() + rider.getRideHeight()`. The player override returns `-0.35` as a double (1.12.2 `PlayerEntity.java:943–945`; 1.13 `PlayerEntity.java:974–976`). Llama mount height is `this.height * 0.67` (1.12.2 `LlamaEntity.java:153–156`; 1.13 `LlamaEntity.java:155–158`); `height` is float and `0.67` is an unsuffixed double literal. Thus multiplication widens the float height to double, and the Y expression adds mount base Y first, then the double height product, then `-0.35D`. No intermediate float rounding is introduced in Y.
- Compared callback, guards, callback order, default adult size and Y numeric types/order are identical. The report’s no-difference conclusion is supported for its bounded operation.

## Scope

This verdict does not cover baby/adult size transitions, other mounts or riders, other player position writers, vehicle physics, or the remainder of the player tick. It establishes no implementation candidate and no release-wide coverage.

