# Feather 1.9.1 to 1.9.2: local pitch setter and Elytra use

## Result

No movement difference was found in the bounded pitch-setter and Elytra-use slices. The camera setter preserves the same arithmetic and write order in both releases. The same active-glide `LivingEntity.moveRelative` code consumes current pitch on the local, dry path. This is a no-difference result for these slices, not a full-pair inventory or freeze.

## Exact local source identity

Read-only source roots:

- `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.9.1\ornithe-feather\`
- `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.9.2\ornithe-feather\`

Each mapping root contains `.decompile-provenance.json`. This is the nested mapping-root manifest scheme: the sibling files `decompiled_minecraft/<version>/ornithe-feather.ready.json` and `...ornithe-feather.provenance.json` are absent for both versions. Do not substitute the later sibling ready/provenance sidecars found for some other releases. The exact manifests hashed here were `e9fb0da6511597e2de953a143f9570b82243e5b9504603686e557f06271da64a` (1.9.1) and `9928b467deb35c7e7f891b4fee1627a68feecdf233c2076909fd4834b0380510` (1.9.2).

Both manifests name the Feather mapping artifact `feather-gen2/<exact version>+build.2` and the exact Minecraft client and release metadata URLs. Their recorded SHA-256 values are:

| Input | 1.9.1 | 1.9.2 |
|---|---|---|
| Feather mergedv2 mapping jar | `6d5768353bf2ff2756d38537312548a8ac91d570f130d23c23e9412d2edd97b3` | `e2b98ececbff8fa0bd0c89283bbb32b7f9f9dcd4b89fa3f137666fa3ac0b6859` |
| Feather version metadata | `da56c9e7efe5c929b2ec8d99a8e2bbc6825847f2852a0e717fcd6c674db73dc4` | `5bad5e67d0870fec69bbab8fcae8d0525a274668bb472eb09d1552359d08edd1` |
| Mojang client jar | `56ad7e53f6f4ad393844e274aa2190692b61d2ad9f6b3d12b33a8b7332fd51f9` | `ef87eb71250df1b871deab8d3ef10aac3290b9e78667ad548fde4d0e14af0d65` |
| Mojang release metadata | `b423c2ee9dc43f9204fea8bf8cbad1780c52fbb62b107dc948cc72b56491fd56` | `a0b96ff1dabf71650c40957b615188067e6ee338e3a30522b4c3fd8613a25c0f` |

The manifest also records matching tool hashes for both outputs (including buildSrc, mapping-io 0.9.1, tiny-remapper 0.14.1 and Vineflower 1.12.0) and the same `lpc-decompile-v1` pipeline. I checked the actual source-file SHA-256 values against each manifest's `sources` entries:

| Source file | 1.9.1 | 1.9.2 | Comparison |
|---|---|---|---|
| `net/minecraft/entity/Entity.java` | `74d3ac34d7e6355c8fdcdd777dcd95ba9ef4626718c8772c1b58d0e28419e42a` | same | identical; both match manifest |
| `net/minecraft/entity/living/LivingEntity.java` | `d8824f670037a88753c428c27213989fc5173b1d7c638f498f73395c145b6fd9` | same | identical; both match manifest |
| `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `de9ad48bcfb2705ef7a9a63e9401c1742aead183e1e9c2d13f8fe40cca0139eb` | same | identical; both match manifest |
| `net/minecraft/item/ElytraItem.java` | `880009469dda5e3d81716c45a0508fd0d0c2260f141e572fe9e6a080bcccd95a` | same | identical; both match manifest |
| `net/minecraft/entity/living/player/PlayerEntity.java` | `d7035f08f03b01823dff70ca504f7244f9b0753542cc0f6dd7301e66f8866b71` | `1fc5107b9b7f5711105d521a28829f2466fe9f297e832f4d209e6ec422111aac` | whole file differs; checked `moveRelative` lines 1366–1384 are unchanged |

These are local provenance and byte-integrity checks, not a detached signature of the downloaded artifacts.

## Setter behavior (both releases)

`net/minecraft/entity/Entity.java`:

- `setRotation(float yaw, float pitch)`, lines 269–272, stores both values with `% 360.0F`; it does not itself clamp pitch to ±90.
- The ordinary camera-input setter `updateLocalPlayerCamera(float yaw, float pitch)`, lines 283–290, snapshots old pitch/yaw as `float`s; adds `yaw * 0.15` and subtracts `pitch * 0.15` using the double literal (the float parameter is promoted, then the result is cast to float); clamps the new current pitch to `[-90.0F, 90.0F]`; then adds the post-clamp `(currentPitch - oldPitch)` delta to `lastPitch`. The subtraction and addition in that previous-pitch update are float operations. The previous pitch write therefore follows the current-pitch clamp.
- A finite overrange input witness is `updateLocalPlayerCamera(0.0F, 1200.0F)` from current pitch `0.0F`: the double-width product is `180.0`, the new current pitch clamps to `-90.0F`, and `lastPitch` is then adjusted by `-90.0F`. Both releases perform this exact sequence.
- Do not conflate that path with `setPositionAndAngles`, lines 1009–1015: it writes `pitch` directly without clamping and does not update `lastPitch`. `updatePositionAndAngles`, lines 985–1002, clamps pitch and first assigns `lastPitch = pitch`, then calls `% 360.0F` setter.

## First active dry local Elytra pitch use (both releases)

`LocalClientPlayerEntity.isLocallyControlled()` returns `true` (`LocalClientPlayerEntity.java`, lines 444–446). Its tick path can request `START_FALL_FLYING` when jump is pressed while airborne, descending, not already gliding and not in abilities flight; it checks chest-slot Elytra and `ElytraItem.canFly` (`LocalClientPlayerEntity.java`, lines 704–709; `ElytraItem.java`, lines 28–30). The `canFly` predicate is `damage < maxDamage - 1`.

`LivingEntity.moveRelative`, lines 1293–1303, enters its movement implementation for locally controlled entities; water and lava each bypass the ordinary path only for a player with abilities flight. On the ordinary dry local path, an active `isFallFlying()` flag selects the Elytra branch. The first current-pitch-dependent expression there is `getLookVector()` (line 1302): `LivingEntity.getLookVector()` calls `getRotationVector(1.0F)`, whose `tickDelta == 1.0F` branch passes current `this.pitch` directly to the vector calculation (`LivingEntity.java`, lines 1807–1819). The next explicit current-pitch read is `float i = this.pitch * (float) (Math.PI / 180.0)` (line 1303), which feeds the lift and dive calculations through lines 1307–1326 before velocity damping and movement.

The active-flight maintenance gate in `LivingEntity.flyingTick`, lines 1709–1727, requires the flag, airborne state and `!isRiding()`, then checks a usable chest Elytra; on the server it writes the flag. On the client, `isFallFlying()` reads flag 7 (lines 2042–2044), so the local active-flight branch uses the synchronized state. The identified setter, local override, glide predicate and current-pitch movement slice are unchanged between 1.9.1 and 1.9.2.

## Scope limit

Only the direct pitch setters and the local dry active Elytra current-pitch path named above were checked. This report does not claim complete movement inventory, source-pair freeze, independent review, implementation acceptance or runtime parity.
