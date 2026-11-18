# 1.12.2 → 1.13 Llama rider Y position (source-only)

**Result: no difference found in the assigned operation.** The adult-default Llama direct rider-position callback writes the local player's Y using the same float/double inputs, expression order and guards in both exact releases. This closes only this bounded operation, not the release pair.

## Exact source identity and byte checks

Both sides use the canonical local `ornithe-feather` sources; the exact 1.13 folder and `versionId` were used (not 1.13.2). The 1.12.2 provenance names `feather-gen2-1.12.2+build.2` artifacts; 1.13 names `net.ornithemc:feather-gen2:1.13+build.2`. Both are Vineflower output. The ready marker's source-manifest SHA-256 matched the local manifest, and each listed source below matched its manifest entry byte-for-byte via SHA-256.

| Release | Provenance / ready marker SHA-256 | Source manifest SHA-256 |
|---|---|---|
| 1.12.2 | `ornithe-feather.provenance.json` `747425a26da363d1abc8a2cecd83a74504aeb1f58be93aa53f48c690e35318ce`; `ornithe-feather.ready.json` `b0aeec721e5c0af02b33d9e217cb8272889fa139ded1ecc2a31e42b930138f7a` | `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da` |
| 1.13 | `ornithe-feather.provenance.json` `34652aeef6914b5cbff6619ae60a855aa676b2ae1f607eccdaa623ad09dc5e8d`; `ornithe-feather.ready.json` `82a4a8f6a03fbd99a7b8577b1ae5980d95af07c7ded0da284106d16eb1e01a99` | `0509c2614b0bb0e616e2ebc6382f8eb5d031b5d86b834a4a6ed7fd96b6d68f1a` |

Hashes below identify the exact cited source bytes under `decompiled_minecraft/<release>/ornithe-feather/`:

| Relative source | 1.12.2 SHA-256 | 1.13 SHA-256 |
|---|---|---|
| `net/minecraft/world/World.java` | `e9fa9b8d6d31ad57a5b876f5f63a5e3c554a23437decf1845a493daf48233594` | `6e3d94e6cdcfbb15472c4ee981a1c12500eaf7cd5d5dc20d75bcde91174c2eb5` |
| `net/minecraft/entity/Entity.java` | `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a` | `09514488d9055449c47e2e4bcd23b5773203ca634b7ecba2323a99d0ae016642` |
| `net/minecraft/entity/living/LivingEntity.java` | `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6` | `c38e95c2c13e088b03d77fc2c6b98d4edd7e6ca5aef12b187c8e3f3ea782e864` |
| `net/minecraft/entity/living/player/PlayerEntity.java` | `e4e0fdbe07a7d0a0ae4a70cbb6739a287c9d045a4a12b409895c220b5d91fe1e` | `4a57ce65405436aeb211d7a04a9a9f6ae1a661d033fd3ba2424909bfb42b8acd` |
| `net/minecraft/client/entity/living/player/ClientPlayerEntity.java` | `207a0ff4ee996916cd1fc73f47bfaae64f92719916cafed8395b1f2a8f797809` | `99b57acea68323125d859313ef5dd2c55d0703c08c5cce475c0227da0d12d1ee` |
| `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `01a58e94d8c6ff98a8e3794227cdc76a5fcbdbad795c70c9cf28854aff9823cc` | `a188e1bc87831dd9da1ec1366a49acc4104c18e6979129097d02d5d3617e6a08` |
| `net/minecraft/entity/living/mob/passive/animal/LlamaEntity.java` | `487c7209a69391f1a19dce5fbdf34d609d2e9bc53c47b04a225e9c0d0c93cf7e` | `464c8e8056e5ece43f496f022aaf1474a2c3a95ac49817812ef914495a639015` |

## Compared operation

- `world/World.java`: entity tick enters `rideTick()` only beneath `requireLoaded && entity.inChunk`, and selects that path when `entity.isRiding()` (1.12.2 lines 1427–1434; 1.13 lines 1143–1149). The predicate and branch order match.
- `entity/Entity.java`: `rideTick()` stops riding if the current mount is removed; otherwise it zeroes the rider's three velocity components, ticks it, then—only if still riding—calls the mount's virtual `updateRiderPositon(this)` (1.12.2 lines 1582–1595; 1.13 lines 1574–1587). This dispatch and guards match.
- Local player inheritance is `LocalClientPlayerEntity` → `ClientPlayerEntity` → `PlayerEntity`. `LocalClientPlayerEntity.rideTick()` delegates to `super.rideTick()` before boat-only input/rowing handling (1.12.2 lines 820–828; 1.13 lines 841–853). `PlayerEntity.rideTick()` delegates through `LivingEntity.rideTick()` to `Entity.rideTick()`; the logical-server sneaking dismount guard in `PlayerEntity` is false for a local client. The client class's `startRiding` override first delegates to `super`; it adds sound/boat yaw work and no alternate Y placement (1.12.2 lines 135–149; 1.13 lines 144–158).
- `LlamaEntity` constructor gives the ordinary default dimensions with `setSize(0.9F, 1.87F)` (1.12.2 line 62; 1.13 line 64). Its override guards with `this.hasPassenger(rider)` and then performs the sole `setPosition` in that class (1.12.2 lines 144–151; 1.13 lines 146–153):

  `rider.setPosition(this.x + 0.3F * g, this.y + this.getMountHeight() + rider.getRideHeight(), this.z - 0.3F * f);`

  The expression is unchanged. In particular, player Y is the left-associative double sum `mount.y + llama.getMountHeight() + player.getRideHeight()`; there is no float cast or intermediate float rounding. Llama `getMountHeight()` returns `this.height * 0.67` (1.12.2 lines 153–156; 1.13 lines 155–158). `Entity.height` and the constructor dimensions are `float`; `0.67` is a double literal, so `height` widens to double before multiplication. `PlayerEntity.getRideHeight()` returns double `-0.35` (1.12.2 lines 943–945; 1.13 lines 974–976). With default adult height `1.87F`, the added offset is `(double)1.87F * 0.67D + (-0.35D)`, identically both sides.

## Scope boundary

This result covers the direct Y write for an ordinary adult default Llama carrying the local player and the generic/local rider dispatch needed to reach it. Baby/adult resizing transitions, other mounts/passengers, vehicle-specific movement/positioning, other player position writers, and the rest of the player tick are not audited here. Llama's own passenger callback has one `setPosition` occurrence per exact source, the guarded direct rider write above. No implementation candidate or behavior difference is established.
