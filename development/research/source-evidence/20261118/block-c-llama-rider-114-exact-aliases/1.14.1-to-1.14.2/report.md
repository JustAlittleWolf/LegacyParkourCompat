# 1.14.1 → 1.14.2 llama rider Y-position comparison

## Scope and verdict

For a direct local-player passenger on an ordinary adult vanilla Llama or Trader Llama, the rider Y-placement operation and its relevant guards/call path are unchanged between these releases. No 1.14.1→1.14.2 Y-position delta is supported. This covers only the mount-to-player Y assignment; it does not compare vehicle/player physics outside that response.

The canonical local source directories are `decompiled_minecraft/1.14.1/yarn/` and `decompiled_minecraft/1.14.2/yarn/`. These exact 1.14.x trees are Yarn-mapped, not Mojmap. Their pinned provenance confirms the exact client, version metadata, and Yarn mapping inputs; no alternate namespace or source generation was used.

## Position expression and dispatch

- In both releases, `Entity.tickRiding` zeros the rider velocity, calls `tick()`, then calls the current vehicle's virtual `updatePassengerPosition(this)` only if the rider still `hasVehicle()`: `entity/Entity.java:1522–1528` (1.14.1) and `:1524–1530` (1.14.2). `PlayerEntity.tickRiding` has a server-only sneaking dismount guard (`!world.isClient && isSneaking() && hasVehicle()`); on the client it reaches `super.tickRiding()`: `entity/player/PlayerEntity.java:437–450` in both.
- `LlamaEntity` directly overrides `updatePassengerPosition(Entity)` and does not call `super`. The override first requires `this.hasPassenger(arg)`; then its Y argument to `setPosition` is evaluated left-to-right as `this.y + this.getMountedHeightOffset() + arg.getHeightOffset()`: `entity/passive/LlamaEntity.java:147–153` in both. `setPosition` accepts three doubles. `Entity.hasPassenger` tests equality against the direct passenger list (`entity/Entity.java:2355–2363` in 1.14.1; `:2366–2374` in 1.14.2), so this is a direct-passenger guard, not a deep-passenger check.
- The llama override of `getMountedHeightOffset()` returns `this.getHeight() * 0.67` (`LlamaEntity.java:157–159` in both). `Entity.getHeight()` returns `float` (`entity/Entity.java:2571–2575` in 1.14.1; `:2582–2586` in 1.14.2); multiplying by the unsuffixed `0.67` promotes that float to double and yields a double mount offset. `PlayerEntity.getHeightOffset()` is double `-0.35` (`entity/player/PlayerEntity.java:952–954` in both). Thus the exact rider Y operation is `double y + double((float) llamaHeight * 0.67) + double(-0.35)`, with no float intermediate in the addition. The llama's x/z terms use `0.3F` and float sin/cos; they do not affect this Y-only finding.
- The class ancestry is unchanged: `TraderLlamaEntity extends LlamaEntity`, `LlamaEntity extends AbstractDonkeyEntity`, and `AbstractDonkeyEntity extends HorseBaseEntity`. Llama's override wins over the inherited HorseBase passenger-placement method and AbstractDonkey mounted-height override, and adds no super placement. `canBeControlledByRider()` remains false; it does not gate this position callback.
- The reachable adult mount route is `AbstractDonkeyEntity.interactMob`: spawn eggs and babies fall through to superclass behavior; a tamed sneaking player opens inventory; an existing passenger diverts to superclass; after item handling, an adult without those diversions calls virtual `putPlayerOnBack` (`:139–196` in both). `HorseBaseEntity.putPlayerOnBack` calls `startRiding(this)` only server-side (`:475–483` in both). `TraderLlamaEntity.putPlayerOnBack` delegates to that method only when `getHoldingEntity()` is not a `WanderingTraderEntity` (`:63–67` in 1.14.1; `:59–63` in 1.14.2). The client-side placement path is the rider's tick callback after its riding relationship is established.

The compared `LlamaEntity.java` files are byte-identical. The relevant `Entity.tickRiding`, `PlayerEntity.tickRiding`, `Entity.hasPassenger`, player offset, Llama override and mount-height methods above also retain the same bodies. Other file-level differences do not alter this path: for example, TraderLlama initialization changes unrelated despawn-delay initialization in 1.14.2.

## Exact evidence identity

Source hashes below were recomputed from the canonical files and match the `sources` entries in each adjacent `.decompile-provenance.json`. Provenance hashes are SHA-256 of the complete manifest files.

| Relative file under `<version>/yarn/` | 1.14.1 SHA-256 | 1.14.2 SHA-256 |
|---|---|---|
| `net/minecraft/entity/passive/LlamaEntity.java` | `b9d8391860e33f3c97d75eddaa6519f1c614fba81d77a3baa9f9392fd8ecb998` | `b9d8391860e33f3c97d75eddaa6519f1c614fba81d77a3baa9f9392fd8ecb998` |
| `net/minecraft/entity/passive/TraderLlamaEntity.java` | `998fb0e745c3656115fbaa46f9febb59eb651ec6947d66b3bfb95872163308a5` | `778c024f4751bfe6770c8c12286a86c01e1c4b9b1b86bae6c4f94f8e2c0b805c` |
| `net/minecraft/entity/passive/AbstractDonkeyEntity.java` | `885ab12cd50193a5cb878c41559445c995027f5f9155b150f358aff4c2391b3c` | `71db7009e25e905e720904820e7a9873e352884335085912f52968571c65a2b2` |
| `net/minecraft/entity/passive/HorseBaseEntity.java` | `317ec0256a38c0f35b3dd20e8a03b17a7819bb903ad41c77f95cde6e78e47387` | `317ec0256a38c0f35b3dd20e8a03b17a7819bb903ad41c77f95cde6e78e47387` |
| `net/minecraft/entity/Entity.java` | `ce429699f2d8f0a54f4be516783a474c6c7808572950164508ceed0baf68ed5b` | `7c0e9cab0842a45e42fa9f27c9661de3fd0fd076c35ff989aa0477198eb38edc` |
| `net/minecraft/entity/player/PlayerEntity.java` | `ebda4ad25a265f8c85e88345efcce753b78e248a29a9b910c5cfd311a4a69319` | `333bb39db18af4cef320eb73b7abe359d438fb2a6ca7d8798ee449da6c684398` |

- 1.14.1 provenance: `decompiled_minecraft/1.14.1/yarn/.decompile-provenance.json`, SHA-256 `308537c5adec3104c5ca243eb5010554f00978635ed74f0d7801c804dd4d4907`. Pinned Mojang client SHA-256 `7194d1326cf796f62ac55f3cb56f851c232895639ae27510f3207d5a73f91814`; version metadata input `1.14.1.json` SHA-256 `71c39840d012fcbe8fedb7be876bd008365b62ec7b1c9c5c7bd34d9a29c8ca22`; Yarn `1.14.1+build.10` v2 mapping artifact SHA-256 `f9462af25135abeb22cbdec829cc8618b1a8dd902f17259abfbe7399a222a630`.
- 1.14.2 provenance: `decompiled_minecraft/1.14.2/yarn/.decompile-provenance.json`, SHA-256 `786546f99684423cc2563be45c9c3334dfcc2c7db58ddb74ba39ce403bd0869a`. Pinned Mojang client SHA-256 `9b740c9fd4955981125ebd3cd086baa8b4392963d043a3480b5b942a96637c25`; version metadata input `1.14.2.json` SHA-256 `237aa8d499b71bb17122cbee40b8748efcd5ac9d8362c68c89e99ab0703e66b8`; Yarn `1.14.2+build.7` v2 mapping artifact SHA-256 `3c91c928d10797ba688b1f7e61063fd4bc56a3ee0f66fa2609ef960ec08b239b`.
