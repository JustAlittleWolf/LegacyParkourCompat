# C: direct local pitch setter and Elytra pitch consumer, 1.9.2→1.9.3

## Result

No source difference was found in the assigned path. The ordinary local mouse-turn call, pitch field arithmetic and clamp order, and the first stored-pitch read in the active dry local Elytra travel branch are identical in 1.9.2 and 1.9.3. This is a bounded operation result, not full-pair coverage or a patch-boundary claim.

## Exact source provenance

The nested mapping roots inspected were:

- `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.9.2\ornithe-feather`
- `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.9.3\ornithe-feather`

These roots are the literal release/mapping directories under the canonical local `decompiled_minecraft` source root. Their nested `.decompile-provenance.json` SHA-256 values are `9928b467deb35c7e7f891b4fee1627a68feecdf233c2076909fd4834b0380510` (1.9.2) and `43325cdd53ca34b88ee9af8464782b323a948b98c8a556c4761f1e5737b84c3a` (1.9.3).

Pinned input identities in those manifests:

- Feather 1.9.2 build.2 mergedv2: `https://maven.ornithemc.net/releases/net/ornithemc/feather-gen2/1.9.2+build.2/feather-gen2-1.9.2+build.2-mergedv2.jar`, SHA-256 `e2b98ececbff8fa0bd0c89283bbb32b7f9f9dcd4b89fa3f137666fa3ac0b6859`.
- Feather 1.9.3 build.2 mergedv2: `https://maven.ornithemc.net/releases/net/ornithemc/feather-gen2/1.9.3+build.2/feather-gen2-1.9.3+build.2-mergedv2.jar`, SHA-256 `fc5defa1546cf87888a11783e2b5d9b48b47c6c022f48de4e55add978ab35da3`.
- Exact Mojang client inputs: 1.9.2 `https://piston-data.mojang.com/v1/objects/19106fd5e222dca0f2dde9f66db8384c9a7db957/client.jar`, SHA-256 `ef87eb71250df1b871deab8d3ef10aac3290b9e78667ad548fde4d0e14af0d65`; 1.9.3 `https://piston-data.mojang.com/v1/objects/b6985b0d3a0520dfb6f17eeb1e8ba58ce9577061/client.jar`, SHA-256 `ab8fdde789c5b4614ffc98249e04fdcce7dfce320b55215bfe5418608af6a63b`.
- Feather version metadata is pinned separately in each manifest at `https://meta.ornithemc.net/v3/versions/feather/1.9.2` (SHA-256 `5bad5e67d0870fec69bbab8fcae8d0525a274668bb472eb09d1552359d08edd1`) and `https://meta.ornithemc.net/v3/versions/feather/1.9.3` (SHA-256 `353c13519171cbba1a96872aebcb8b38248d5d5963b4c4613c23337adf028985`). The Mojang version JSON pins are also present in the manifests.

For each source file below, the current file SHA-256 was checked against the corresponding `sources` entry in its nested provenance manifest; all match.

| Source file relative to its nested mapping root | 1.9.2 file SHA-256 | 1.9.3 file SHA-256 |
|---|---|---|
| `net/minecraft/entity/Entity.java` | `74d3ac34d7e6355c8fdcdd777dcd95ba9ef4626718c8772c1b58d0e28419e42a` | `bcd7fa2206bf8d7271102f6da7fe2771f96dbe22ec79dab9f2101a3e29c17ef0` |
| `net/minecraft/entity/living/LivingEntity.java` | `d8824f670037a88753c428c27213989fc5173b1d7c638f498f73395c145b6fd9` | `bbb7703f18fd5da05c4e4a43a77ea644b388e63c01d34166d308ea52054be4e5` |
| `net/minecraft/client/render/GameRenderer.java` | `d03fb41524c7b0ecf92696ae6aa8144fdce7a449f5e670a59b210d8f6b06a709` | `e08c2034c7fd4621a76ff5a4ba26dd80e065c895bc5c0a4120d4d36141203d6b` |

## Compared path and source facts

- `GameRenderer.java`: the ordinary and smooth-camera paths both call `player.updateLocalPlayerCamera(h, i * j)` (1.9.2 lines 849/853; 1.9.3 lines 850/854). The call expression and branch behavior are unchanged.
- `Entity.updateLocalPlayerCamera(float yaw, float pitch)`: 1.9.2 lines 283–294; 1.9.3 lines 286–297. Both first save `float f = this.pitch`, then update yaw, then perform the first pitch-field write as `this.pitch = (float)(this.pitch - pitch * 0.15)`, then clamp that stored value to `[-90.0F, 90.0F]`, then update `lastPitch` from the clamped field minus saved `f`. Since `0.15` is a double literal, the multiplication/subtraction expression is double precision until its explicit float cast. The saved previous pitch precedes both field writes and is used only after clamp. The complete extracted method body SHA-256 is identical on both sides: `3970cf44a7c1bb64be023db7a3b83f1d0c3f8d8d0d152df48c330e058d8293ac`.
- `LivingEntity.moveRelative`: 1.9.2 lines 1293 onward; 1.9.3 lines 1302 onward. The local-player override returns true from `isLocallyControlled()` (1.9.2 `LocalClientPlayerEntity.java` lines 444–446; 1.9.3 lines 447–449). `moveRelative` reaches the Elytra branch after the water/lava gates when `isFallFlying()` is true. `flyingTick()` validates the active flag against airborne, nonriding, fly-capable Elytra conditions (1.9.2 `LivingEntity.java` lines 1709–1737; 1.9.3 lines 1718–1736); client-side flag writes are separately gated by `!world.isClient`, so this citation describes the validity logic, not a client flag write. The extracted `moveRelative` body SHA-256 matches: `8935df8b81194575e97189f46e7e59422988118ba73b1eca880c8340699359dd`.
- Within that branch, `this.getLookVector()` is the first stored-pitch read. The `LivingEntity` override returns `getRotationVector(1.0F)` (1.9.2 lines 1808–1810; 1.9.3 lines 1817–1819); the `tickDelta == 1.0F` case supplies `this.pitch` to `Entity.getRotationVector(float pitch, float yaw)` (1.9.2 `LivingEntity.java` lines 1813–1816; 1.9.3 corresponding method shifted by nine lines). Only afterward does `moveRelative` explicitly read `this.pitch` into radians (`float i = this.pitch * (float)(Math.PI / 180.0)`). In the eligible dry local branch these methods are identical across the pair. The extracted `getLookVector` method body SHA-256 matches: `73297ebeaab31abac9f803dfb0b1899d2c0762013655975eaea087ebb3ccfff2c`.

The cited file SHA-256 values identify complete decompiled files; the method SHA-256 values above identify the exact extracted method bodies used for the equality check.

