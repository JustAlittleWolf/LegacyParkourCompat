# Powder-snow stuck multiplier: exact stable piston boundary

## Finding

Within the prepared exact stable-source sequence, the first source with the piston-specific stuck-multiplier exclusion is **26.1**. The immediately preceding prepared stable source, **1.21.11**, still multiplies piston movement like every other mover. The boundary is therefore **1.21.11 → 26.1**. The exclusion remains in 26.1.1, 26.1.2, and 26.2. At 26.2 the ready source manifest SHA-256 is `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894` (matches the supplied post-introduction audit), and its `Entity.java` SHA-256 is `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dfaff869bb811d5`; the same exclusion is at `Entity.java:728–735`. The endpoint draft's unresolved-introduction statement can be narrowed to this stable-source boundary; this does not infer an intermediate patch or snapshot cutover.

I scanned each available stable `Entity.java` source from 1.17 through 1.21.11 and 26.1 through 26.2 for the stuck gate. All 1.17–1.21.11 source files had an unconditional `multiply(stuckSpeedMultiplier)` in the gate; 26.1 was the first with a nested mover-type exclusion. This scan covers the prepared stable release folders, not snapshots or an external release catalog.

## Input and source identity

The supplied immutable inputs were rehashed:

- Prior audit `powder-snow-postintro-audit/audit.md`: `a9c68903a2d451c34ed81954801451e05bdcbfe03798a5419db9e293b39ecdcd`.
- Candidate recheck `powder-snow-postintro-audit/powder-snow-stuck-piston-recheck.md`: `392c0dd4204de8f15176a7399af8d50a581bd57ac2e3e0e8cddb91e842f24a3d`.
- Revised candidate `powder-snow-postintro/drafts/powder-snow-stuck-piston-response.md`: `1baacb3c01ad34b9146137e1df6c3afcc7fb65e65b0aee3be22693fc957004d6`.

Boundary source identities:

- **1.21.11 Mojmap**: ready marker status `ready`; version id `1.21.11`; Vineflower / Java 25; source-manifest SHA-256 `0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555`, rehashed from `decompiled_minecraft/1.21.11/mojmap.sources.sha256` and matching `mojmap.ready.json`; client artifact SHA-256 `1473c9489ac50fda3c435049a76a70d61a10b8610db27f5ba9d8756b686cd3bd`.
- **26.1 published unobfuscated**: ready marker status `ready`; version id `26.1`; Vineflower / Java 25; source-manifest SHA-256 `b77289c135ac6e12aa35e75177cea52d968f49d49e1ec745e510b1a786c9aeba`, rehashed from `decompiled_minecraft/26.1/unobfuscated.sources.sha256` and matching `unobfuscated.ready.json`; client artifact SHA-256 `bc6194c61566b587f196090d730698f2191170f7580adb143e4ff839939d3840`.

The rehashed relevant source files match their source-manifest entries (full SHA-256):

| File relative to each mapped source root | 1.21.11 Mojmap | 26.1 unobfuscated |
|---|---|---|
| `net/minecraft/world/entity/Entity.java` | `32314478c6036fa9f3f3cc409c61c622eefc5a282d60e1011a1a33cc29cf18a3` | `8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf` |
| `net/minecraft/world/entity/LivingEntity.java` | `19ed2d565858c401c69a06750b054a633a20ea864cab3a753b5c767f0bdd60e8` | `c3b64de8dbaba8ad8a7ccd4f33255346d8206e66f12ca91260971bf5e5ac93bd` |
| `net/minecraft/world/entity/player/Player.java` | `8e97167350a91741d0aa10d3b0d92a33150ed6dccdc94cd5b37d9c7ca22bcc81` | `44cf28e0c64e78d39fd13368e9991381dbebab67029070cb9ddc43f09d45d14d` |
| `net/minecraft/client/player/LocalPlayer.java` | `948e94f8e874b2f72e9a689e6e3ba1933f5728ec381d64f3bb5e2388718bd477` | `433fd995ad317af0f6ef0e50c1e8e3483cb8f00e0e327d4edf27a4dd99666ebe` |
| `net/minecraft/client/Minecraft.java` | `e41d192579972ea043cf39ad7f754e1279dca63aa6a576edceb4c9928d685d59` | `8b072df57a6efb47bcb4f5b2ac1bb750ed8bfc7b9441b1894167a7b8c046b4b1` |
| `net/minecraft/world/level/block/PowderSnowBlock.java` | `84e3f4c9063c6be560b400686afa2bcd6761585c9c56566d023ad8fe47048940` | `5733056253c3f9c7d843e8e7c29deaee67d235d830c63c0641f3a7b70604970f` |
| `net/minecraft/world/level/block/piston/MovingPistonBlock.java` | `b129cc22900f2025b876edb4ff6a9a099bb3adc43576ef8be34d3d6f1bcdf5c7` | `87e8c6c1374b73d086b41a40adce0194f2b30f0ee195ec331b51989be7b92c73` |
| `net/minecraft/world/level/block/piston/PistonMovingBlockEntity.java` | `c5dc0cb682442ced62a73bfe27bcbcb77a069d8e3057a80b7c62ae1f6867c845` | `67bceb946c172c5c022663fda32d123a6bbb85ca4171705ca9dc2c54105ab6e6` |

## Exact operation and call path

- 1.21.11 `Entity.move(MoverType, Vec3)`, `Entity.java:685–709`: piston movement is limited first; an exactly zero limited vector returns before the stuck gate. At `:702–708`, `stuckSpeedMultiplier.lengthSqr() > 1.0E-7` multiplies the requested vector unconditionally, then clears the stored multiplier and sets delta movement to zero.
- 26.1 `Entity.move(MoverType, Vec3)`, `Entity.java:704–728`: the same order and threshold/early return remain. At `:721–727`, the nested `moverType != MoverType.PISTON` now guards only multiplication; the gate still clears `stuckSpeedMultiplier` and zeros delta movement for piston moves.
- Provider is unchanged across the boundary: 1.21.11 `PowderSnowBlock.entityInside`, `PowderSnowBlock.java:62–65`, and 26.1 `:62–71` call `Entity.makeStuckInBlock(..., new Vec3(0.9F, 1.5, 0.9))`. The consumer `Entity.makeStuckInBlock` stores that vector (1.21.11 `Entity.java:2810–2813`; 26.1 `:2876–2879`).
- During player movement, `LivingEntity` runs travel then, on a client only for its locally authoritative instance, `applyEffectsFromBlocks` (1.21.11 `LivingEntity.java:2971–2979`; 26.1 `:3070–3078`). Entity's block-effect scan dispatches `BlockState.entityInside` for intersected block cells (1.21.11 `Entity.java:1214–1219`; 26.1 `:1264–1269`), allowing the powder-snow provider to establish the pending multiplier; because the player is a LivingEntity, the provider requires `getInBlockState().is(powderSnow)`.
- The vanilla local-player path is reachable on both endpoints: `LocalPlayer.tick` calls `super.tick()` (1.21.11 `LocalPlayer.java:211–215`; 26.1 `:227–231`); LocalPlayer overrides `isLocalPlayer()` to return true (1.21.11 `:359–361`; 26.1 `:377–379`). Its `move` override calls `super.move` (1.21.11 `LocalPlayer.java:946–951`; 26.1 `:985–990`), entering the changed inherited consumer.
- Client timing closes the witness: `Minecraft.tick` calls `level.tickEntities()` before `level.tickBlockEntities()` (1.21.11 `Minecraft.java:1809–1813`; 26.1 `:1855–1859`). The moving-piston block ticker is registered without a side guard (`MovingPistonBlock.getTicker`, 1.21.11 `MovingPistonBlock.java:62–64`; 26.1 `:69–71`). `PistonMovingBlockEntity.tick` calls collided/stuck-entity movement (1.21.11 `PistonMovingBlockEntity.java:320–328`; 26.1 `:334–342`). For a normal non-slime piston, its swept-volume query includes intersecting entities without a `ServerPlayer` exclusion (`moveCollidedEntities`, 1.21.11 `PistonMovingBlockEntity.java:113–180`; 26.1 `:120–187`); `moveEntityByPiston` invokes `entity.move(MoverType.PISTON, ...)` then applies block effects (1.21.11 `:185–191`; 26.1 `:192–199`). Thus a local player whose just-completed entity tick touched powder snow can reach the piston gate later in that same client tick, provided the player intersects the swept volume, has the pending multiplier above threshold, and piston limiting returns a nonzero vector.

## Scope limits / open gates

This closes only the introduction boundary for the bounded stuck-multiplier consumer in the prepared stable source sequence. The exact snapshot/development introduction was not inspected. Pair-wide source coverage, independent pair audit, and runtime tick parity remain unestablished; the candidate's already-reviewed shared gate conditions (threshold and zero limited-vector early return) remain prerequisites on the witness.


