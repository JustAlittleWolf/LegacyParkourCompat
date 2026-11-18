# Llama local-player Y placement: 1.14.2 → 1.14.3 (Yarn)

## Result

No Y-placement change was found between the exact local Yarn-mapped sources for Minecraft 1.14.2 and 1.14.3. For an ordinary adult default Llama or TraderLlama carrying the direct local player, both releases use the same Llama override, the same rider height offset, and the same callback/parent scope. This is a bounded result for the methods and dependencies below; it does not establish whole-pair movement coverage or intermediate-patch introduction boundaries.

The earlier Mojmap-availability blocker is preserved separately in `report.md`; this is the explicitly approved Yarn-mapped comparison.

## Exact Y math and types

In both `LlamaEntity.updatePassengerPosition(Entity)` bodies, the Llama first gates on `this.hasPassenger(arg)`. Inside that gate it computes yaw helper locals and calls `arg.setPosition(...)` with this Y argument:

```java
this.y + this.getMountedHeightOffset() + arg.getHeightOffset()
```

`LlamaEntity.getMountedHeightOffset()` returns `this.getHeight() * 0.67`. `Entity.getHeight()` returns `float`, so the float height is promoted to `double` by the `0.67` double literal; the method result is `double`. Java evaluates the Y expression left-to-right as `(this.y + mountedHeightOffset) + passengerHeightOffset`, with both additions in double precision. For the player, `PlayerEntity.getHeightOffset()` returns the double `-0.35`.

For the default adult entity dimensions, `EntityType.LLAMA` and `EntityType.TRADER_LLAMA` both specify height `1.87F` in each release. `LivingEntity.getScaleFactor()` returns `1.0F` when `isBaby()` is false, so the ordinary adult current height is the `float` value `1.8700000047683716`. Thus the mount-height term is `1.8700000047683716 * 0.67` = `1.252900003194809` (double). The terms sum to `0.902900003194809` when evaluated from a zero base Y, but the actual expression first adds the mount-height term to the current Llama `y`, then adds `-0.35`; the final coordinate can therefore reflect base-Y-dependent double rounding. The source expression and these terms are identical on both sides.

The same guarded method also computes `f` and `g` as `float` results from `MathHelper.cos` and `MathHelper.sin` of `field_6283 * (float)(Math.PI / 180.0)`, and places X as `this.x + 0.3F * g` and Z as `this.z - 0.3F * f`. The local `float h = 0.3F` is unused in both versions. These surrounding operations are unchanged; they do not affect Y.

## Callback and inheritance scope

On the client path, `ClientWorld.tickPassenger` reaches a player passenger through its `arg2 instanceof PlayerEntity` eligibility branch, then calls `arg2.tickRiding()` while the passenger is still attached and marked for update (`field_6016` in 1.14.2; `updateNeeded` in 1.14.3). `ClientPlayerEntity.tickRiding()` calls `super.tickRiding()` first. `PlayerEntity.tickRiding()` uses its non-server-sneak dismount guard and then calls `super.tickRiding()`; on the logical client the guard's `!world.isClient` condition is false, so it reaches the superclass path. `LivingEntity.tickRiding()` delegates to `Entity.tickRiding()`. That method zeroes rider velocity, ticks the rider, then, if `hasVehicle()`, invokes `this.getVehicle().updatePassengerPosition(this)`. With the Llama as the direct vehicle, virtual dispatch enters `LlamaEntity.updatePassengerPosition`, where `hasPassenger(arg)` is the final placement gate.

`TraderLlamaEntity` directly extends `LlamaEntity` in both releases and does not override either placement method, so it inherits the exact same formulas. No parent-class fallback replaces the Llama implementation for either entity. `Entity.updatePassengerPosition` remains the native fallback for entity types that do not override it; it is not the selected implementation for a Llama or TraderLlama direct rider.

## Paired source references

All paths below are relative to `decompiled_minecraft/<version>/yarn/`.

| 1.14.2 source and lines | 1.14.3 source and lines | Evidence |
|---|---|---|
| `net/minecraft/entity/passive/LlamaEntity.java`, `updatePassengerPosition(Entity)` 147–154; `getMountedHeightOffset()` 156–159 | Same path and members, 147–154; 156–159 | Same guard, float yaw locals, unused `h`, X/Y/Z argument expressions and double mount-height formula. |
| `net/minecraft/entity/passive/TraderLlamaEntity.java`, class declaration 20 | Same path, class declaration 20 | Both extend `LlamaEntity`; neither class adds a placement override. |
| `net/minecraft/entity/player/PlayerEntity.java`, `tickRiding()` 437–457; `getHeightOffset()` 952–954 | Same path, `tickRiding()` 438–458; `getHeightOffset()` 953–955 | Same client/server guard and superclass order; exact player offset is `-0.35` double. |
| `net/minecraft/client/network/ClientPlayerEntity.java`, `tickRiding()` 808–816 | Same path, 808–816 | Calls superclass rider tick before client-only boat handling. |
| `net/minecraft/entity/LivingEntity.java`, `tickRiding()` 2321–2326; `getScaleFactor()` 382–384; `getSize(EntityPose)` 2678–2680 | Same path, `tickRiding()` 2331–2336; `getScaleFactor()` 383–385; `getDimensions(EntityPose)` 2686–2688 | Same superclass dispatch, adult scale `1.0F`, and dimensions scaling. Names changed across this Yarn mapping pair. |
| `net/minecraft/entity/Entity.java`, `tickRiding()` 1524–1530; `updatePassengerPosition(Entity)` 1532–1536; `getHeightOffset()` 1541–1543; `getHeight()` 2582–2584; `getSize(EntityPose)` 2588–2589 | Same path, `tickRiding()` 1564–1570; `updatePassengerPosition(Entity)` 1572–1576; `getHeightOffset()` 1581–1583; `getHeight()` 2623–2625; `getDimensions(EntityPose)` 2629–2630 | Same rider-to-vehicle callback, native fallback, base zero offset, float height accessor, and type-default dimensions path. |
| `net/minecraft/entity/EntityType.java`, Llama registration 248; TraderLlama registration 360 | Same path, Llama registration 245–246; TraderLlama registration 357–358 | Both default types specify width `0.9F`, height `1.87F`; Yarn builder/name syntax differs. |
| `net/minecraft/client/world/ClientWorld.java`, `tickPassenger(Entity, Entity)` 163–184 | Same path, 164–184 | Player eligibility, attachment/update gates, rider callback and recursive passenger tick path are equivalent; update flag name/line numbers differ. |
| `net/minecraft/util/math/MathHelper.java`, `sin(float)` 24; `cos(float)` 28 | Same path, same lines | Both helpers return `float`, confirming the types of the yaw locals. |

## Source identities and SHA-256

The canonical local roots are `decompiled_minecraft/1.14.2/yarn/` and `decompiled_minecraft/1.14.3/yarn/`. Their manifests identify Yarn `1.14.2+build.7` and `1.14.3+build.13`, respectively. Manifest SHA-256 values and mapping/client input hashes are recorded here; the hashes below are from local manifests, and no network lookup was used.

- 1.14.2 manifest `.decompile-provenance.json`: `786546f99684423cc2563be45c9c3334dfcc2c7db58ddb74ba39ce403bd0869a`.
  - Yarn mapping artifact `1.14.2+build.7-v2.jar`: `3c91c928d10797ba688b1f7e61063fd4bc56a3ee0f66fa2609ef960ec08b239b`.
  - Intermediary artifact `1.14.2-v2.jar`: `f73883d007c64858ae0f26533fe47ec5d3a051cede6892c8d6531fc5122e459e`.
  - Client artifact SHA-256: `9b740c9fd4955981125ebd3cd086baa8b4392963d043a3480b5b942a96637c25`.
- 1.14.3 manifest `.decompile-provenance.json`: `81ea0021eb122d4e3340e20d47d6ecb7079365ba767a038d26a8264cddacc28a`.
  - Yarn mapping artifact `1.14.3+build.13-v2.jar`: `ad9e77f907cfc6780fb348ad4b3a948ffc8cea3bfe50abcc792428645e49e06e`.
  - Intermediary artifact `1.14.3-v2.jar`: `f2e9990c00fa87ddc7158a95afb735a947ae26d1997b52916226e11c612542a9`.
  - Client artifact SHA-256: `4063062677433988e012fba53e42e4c21adb6919ac7f061f19f3c6e75b334e07`.

Actual local hashes for every source file cited above match that relative path's `sources` entry in the corresponding provenance manifest.

| Relative source file | 1.14.2 SHA-256 | 1.14.3 SHA-256 |
|---|---|---|
| `net/minecraft/entity/passive/LlamaEntity.java` | `b9d8391860e33f3c97d75eddaa6519f1c614fba81d77a3baa9f9392fd8ecb998` | `a4d400d4358b420aee1a18da14390542fe09c23817eb53864917643570a39f2c` |
| `net/minecraft/entity/passive/TraderLlamaEntity.java` | `778c024f4751bfe6770c8c12286a86c01e1c4b9b1b86bae6c4f94f8e2c0b805c` | `82bd87f3040baa3eb08b8f19cc875121a1aeb899e1cb569d05afad4ad06615e6` |
| `net/minecraft/entity/player/PlayerEntity.java` | `333bb39db18af4cef320eb73b7abe359d438fb2a6ca7d8798ee449da6c684398` | `e3f361ac4e369d01473f3d84044ee33f9685d182c4485e2e80b7f62756950963` |
| `net/minecraft/client/network/ClientPlayerEntity.java` | `90c96701849c9212ea7714f02e7bc9011d0a7d0aaf1f7b46464497ba3ca14947` | `28b6b4031e02afd4a2e6b4a80e5a7ca06f16dac42ca0f92cab25e2c2dc9e14ae` |
| `net/minecraft/entity/LivingEntity.java` | `e8224e7df98ccf0986bb390f35e64293263d17b9ab1dbdfeb661c65d28b6ee78` | `e8aed9c63c0dfbe88e4b9e11c58881bc6e04b9cbaecbde5e3606e3afa406d72b` |
| `net/minecraft/entity/Entity.java` | `7c0e9cab0842a45e42fa9f27c9661de3fd0fd076c35ff989aa0477198eb38edc` | `9b204a5f9af6ca50c09db4b6185ff37c39980c1a8cd1b5fd004713ecffae5c14` |
| `net/minecraft/entity/EntityType.java` | `32ebed039d344551eba25ce7694a6d5ab057ab29c2864a081f8595bc3d5bcdcb` | `8ea4ffe0ac17bcbb418041e26ea29dede9b4727573c9942f7692e624112dbc92` |
| `net/minecraft/client/world/ClientWorld.java` | `86b4b5451115fde93bc1f6546dd07c93a373b09232870c0721f524216556368f` | `9c72e6f4d142b5ccbe33f4f33e6593705fd7e02755275f0f8a330561463117e2` |
| `net/minecraft/util/math/MathHelper.java` | `21ed846ac48a5a2fbcdb2ac31fd5b74a461485effd881112c903c09d9297504a` | `21ed846ac48a5a2fbcdb2ac31fd5b74a461485effd881112c903c09d9297504a` |

## Bounds

Checked the paired Llama override and mount-height provider, TraderLlama inheritance, player offset and rider-tick overrides, superclass rider dispatch and fallback, client passenger tick gate, Llama default dimensions, adult size scaling, and float trigonometric helper signatures. No candidate difference remains within this scope. Excluded as assigned: vehicle motion, non-player riding behavior, and other carriers. No tests, game launch, build, online lookup, or source generation was performed.

