# Independent source audit verdict: swim pitch vertical adjustment

## Verdict

**Accepted: no independent source delta for the `Player.travel(Vec3)` swim-pitch Y adjustment from 1.20.6 to 1.21.0.** The reviewed source report is bound to SHA-256 `b4b9d3cf0678e7624c97625739dbb3043f050121b18db9b86c83f6fed39547ff` (6,501 bytes) at the assigned absolute path. The report bytes were rehashed directly before review.

I independently traced the player tick dispatch and compared the operation, its direct writer, look-angle math, trig table implementation, and inherited water movement. The pitch-adjustment claim and exact operation order match. The report correctly keeps the separate `WATER_MOVEMENT_EFFICIENCY` change in inherited water travel outside this no-delta claim.

## Bounded coverage and evidence

- The ordinary local player is `LocalPlayer extends AbstractClientPlayer` (`LocalPlayer.java`, 1.20.6 line 90; 1.21 line 92), and `AbstractClientPlayer extends Player` in both releases. The active nonpassenger tick reaches `LivingEntity.tick`'s ordinary branch `this.travel(input)` (1.20.6 `LivingEntity.java:2680–2684`; 1.21 `:2675–2679`). When a controlling player passenger is present, that caller instead uses `travelRidden`; that case is outside this assigned ordinary nonpassenger path.
- `Player.travel(Vec3)` performs the same pre-super block in both versions: gate `isSwimming() && !isPassenger()`; read `double lookY = getLookAngle().y`; choose `double coefficient = lookY < -0.2 ? 0.085 : 0.06`; then test `lookY <= 0.0 || jumping || !fluidStateAt(BlockPos.containing(getX(), getY() + 1.0 - 0.1, getZ())).isEmpty()`. Short-circuit order is preserved, including skipping the block-state sample for nonpositive look Y or jumping. If the condition passes, it snapshots delta movement and adds `(lookY - velocityY) * coefficient` to Y through `Vec3.add(0.0, ..., 0.0)`. Exact ranges: 1.20.6 `Player.java:1473–1483`; 1.21 `Player.java:1456–1466`.
- Both methods next call `super.travel(input)` on the ordinary nonflying path. A locally controlled entity enters `LivingEntity.travel`'s water branch when `isInWater() && isAffectedByFluids() && !canStandOnFluid(fluidAtBlockPosition)`. It calls `moveRelative(speed, input)`, then `move(MoverType.SELF, deltaMovement)` before the later velocity updates. Exact ranges: 1.20.6 `LivingEntity.java:2104–2147`; 1.21 `LivingEntity.java:2091–2129`.
- The inherited water branch is a separate changed operation: 1.20.6 reads/caps Depth Strider and divides both interpolation terms by 3 (`LivingEntity.java:2117–2129`); 1.21 reads `(float)getAttributeValue(Attributes.WATER_MOVEMENT_EFFICIENCY)` and uses the value directly in those terms (`:2104–2112`). Both retain the airborne half multiplier. This confirms the report's separation; it is not acceptance of a separate finding in this verdict.
- `Entity.getLookAngle()` calls `calculateViewVector(getXRot(), getYRot())` in both releases, using current rotations rather than interpolated view rotations (`Entity.java:2027–2028` and `:2099–2100`). `calculateViewVector` has identical float radians conversions, `pitch * (float)(Math.PI / 180.0)` followed by `-yaw * (float)(Math.PI / 180.0)`, and returns `(sinYaw * cosPitch, -sinPitch, cosYaw * cosPitch)` (`1.20.6 Entity.java:1479–1486`; `1.21 :1530–1537`).
- `Mth` has the same 65,536-entry float `SIN` table initialized by `(float)Math.sin(index * Math.PI * 2.0 / 65536.0)` and the same indexing: `sin` uses `(int)(radians * 10430.378F) & 65535`; `cos` adds `16384.0F` before the cast/index mask (`Mth.java:32–35, 48–53` on both sides).
- No second `getLookAngle()`-based Y correction appears in `Player.java`; the other `setDeltaMovement` calls found there are unrelated player movement operations, including the subsequent flight branch. The bounded operation has no difference in gates, sample position, types, casts, rounding, or write order.

## Canonical source identity and byte checks

All paths below are relative to `D:\Javastuff\LegacyParkourCompat`. Each relied Java file's actual SHA-256 matched its canonical source-manifest/provenance entry.

| Version | Canonical readiness/provenance identity | SHA-256 |
|---|---|---|
| 1.20.6 Mojmap | `decompiled_minecraft/1.20.6/mojmap.ready.json` (`status=ready`, exact ID and mapping) | `1ec1dc773ea2a8a3ef48a9fc868893bd2f83d6b13b057d75b151b711788c3d41` |
| 1.20.6 Mojmap | `decompiled_minecraft/1.20.6/mojmap.provenance.json` | `ee53e67e7ab51069a0eaae5a0ce13d49a9f972bdc13e3c31e041afa77f4d26e8` |
| 1.20.6 Mojmap | `decompiled_minecraft/1.20.6/mojmap.sources.sha256` | `56aae10684471d7abb1c366bd5dd431ab976112a87e6cc5c687a68f1eff06311` |
| 1.20.6 Mojmap | `decompiled_minecraft/1.20.6/artifacts.sha256` | `e5882622bcf22b3e3c2c96c73843a1308e11e3096f4feefd945ea5808933ce31` |
| 1.21 Mojmap | `decompiled_minecraft/1.21/mojmap/.decompile-provenance.json` (embedded source hashes; no sibling ready marker exists) | `90246f277aefd2350e42e17245faa51928a949c8db4427f3be04a732527deb7c` |

| Version | Source file | SHA-256 |
|---|---|---|
| 1.20.6 | `net/minecraft/world/entity/player/Player.java` | `785d93ccc94e1f912e545b2b0c355edeb352b44ee8e83a69364daec35266dbe2` |
| 1.20.6 | `net/minecraft/world/entity/Entity.java` | `71cd6b9f6c002684154dce11d3745e8714d82f13c8e1b3aa56743930131c18f3` |
| 1.20.6 | `net/minecraft/world/entity/LivingEntity.java` | `c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2` |
| 1.20.6 | `net/minecraft/util/Mth.java` | `f1b2d62f18b4b20ffad5843f40d34d8b5f9c40eee3e106dfe275859a24cfb18d` |
| 1.20.6 | `net/minecraft/client/player/LocalPlayer.java` | `6b429dfa6e0681251ec985dda1627f808652a7bbe5b70dc85c8fa0fe0ed46ffa` |
| 1.20.6 | `net/minecraft/client/player/AbstractClientPlayer.java` | `759f8f03b89dab1f6b65c73e1e25a68ae6a6083cb49b5d2719136edda7edf696` |
| 1.21 | `net/minecraft/world/entity/player/Player.java` | `ed32b88c3c7c8418b83db41823520f2b6b0b49a98610306ef26e9681dc925c71` |
| 1.21 | `net/minecraft/world/entity/Entity.java` | `b81905c7879e2cc5c5063a41d865c4164ad919f156306705be791d1017b99850` |
| 1.21 | `net/minecraft/world/entity/LivingEntity.java` | `324a3eee8496caab57cfaf5101ef576f1ae3c60c40e3857e96f35f3af9a3a0d8` |
| 1.21 | `net/minecraft/util/Mth.java` | `aa40272f0f980438ca7e8c874ceedc664feb77958b162d7aa3ed01f233bd2deb` |
| 1.21 | `net/minecraft/client/player/LocalPlayer.java` | `c555e68ac3c63ab9b4f9a9e31933e263b96350a2bc599d11a0de5928bc24b583` |
| 1.21 | `net/minecraft/client/player/AbstractClientPlayer.java` | `759f8f03b89dab1f6b65c73e1e25a68ae6a6083cb49b5d2719136edda7edf696` |

The 1.20.6 ready marker declares the exact source-manifest and artifact-manifest hashes recorded above; both match their current bytes. The 1.21 embedded provenance's exact client mapping input is `https://piston-data.mojang.com/v1/objects/0530a206839eb1e9b35ec86acbbe394b07a2d9fb/client.txt`, SHA-256 `127485d79248d4ae2955a7d061c3626d38853ecb054c4d008707732264b0aac1`; its 1.21 release metadata input is pinned as `cdd1a44db7b5e8787723c1f6c7c7dc3a4638d96a31ddc84aeb27d3f30efa802a`. The provenance records Vineflower 1.12.0, Gradle 9.7.1, Java 25.0.3, and the pinned remapper/tool class hashes.

## Review boundary

This verdict accepts only the report's no-delta conclusion for the direct `Player.travel` swim-pitch Y adjustment and closes its compared math/source dependencies for this exact pair. It does not establish intermediate patch boundaries, runtime parity, or acceptance of the distinct water-movement-efficiency change.