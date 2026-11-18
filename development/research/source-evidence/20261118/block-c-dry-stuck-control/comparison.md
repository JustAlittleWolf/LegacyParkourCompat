# Local-player stuck-probe call/control comparison

## Frozen source inputs

The source bodies below are compared as exact decompiled files. SHA-256 binds each raw source file; the associated ready-marker digest binds the recorded version and mapping metadata.

| Version / mapping | Raw source | Source SHA-256 | Ready marker SHA-256 | Marker provenance |
|---|---|---|---|---|
| 1.13.2 / Ornithe Feather | `decompiled_minecraft/1.13.2/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `2583495F3A02B4791AA036E6A8D354D7596C4984761969A0F29B29D8D9BF42BF` | `1D1C644C1DEB05C02D11DC575AD834F4F72489124701D9B2B90357F43DC37B38` | Ready marker names version `1.13.2`, mapping `ornithe-feather`, mapping inputs `feather-gen2-1.13.2+build.2-mergedv2.jar` and `feather-gen2-1.13.2+build.2.tiny`; records `clientSha256` `3410887ba652f25792c7675bfaf9140e73b60e93cfbf113a803f8a98cb05c0f9`. |
| 1.14.4 / Ornithe Feather | `decompiled_minecraft/1.14.4/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `708AF6A3880FB58B67BF4604A5509B351719A9C2A0C06A8EC261586435BF00CE` | `BF8C9399E3A36DDD7EDC78D98413319000895F49E88DB224335F62B2BAF21E77` | Ready marker names version `1.14.4`, mapping `ornithe-feather`, mapping inputs `feather-gen2-1.14.4+build.2-mergedv2.jar` and `feather-gen2-1.14.4+build.2.tiny`; records `clientSha256` `B3B2A798E2D67B566008FE4A03767AE2C7FF3F8C7BA6751E7B71FC7299672D0A`. |
| 26.2 / unobfuscated | `decompiled_minecraft/26.2/unobfuscated/net/minecraft/client/player/LocalPlayer.java` | `8D089AA09217E3607B38590F7C1623385562800943AC6DFD3D17804E041DA6D6` | `F9406ADB6BF7CB4C1AB0792A798AB2CB90E082AD4C2EEE032C9F674D0EA8070F` | Ready marker names version `26.2`, mapping `unobfuscated`; source-manifest SHA-256 `A7AD74FC712567EB87136AFC1EAFCFD602B4088E9869F3596331FD34FAEFE894`. |

The original derived 1.13.2 Feather client identified by the prior evidence as `d118ff5b9eb93fed06680466cb26c41a30647cdb2fc8744638cf9b35cf7864e5` is unavailable. The revised 1.13.2 ready source metadata above does not establish byte identity or equivalence to that original client artifact.

## Ordinary call schedule and gates

In 1.13.2 `LocalClientPlayerEntity#mobTick` calls `pushAwayFrom` four times in this exact order, using one captured `Box box = this.getShape()` and the current width:

1. west/south corner: `(x - width * 0.35, box.minY + 0.5, z + width * 0.35)`;
2. west/north corner: `(x - width * 0.35, box.minY + 0.5, z - width * 0.35)`;
3. east/north corner: `(x + width * 0.35, box.minY + 0.5, z - width * 0.35)`;
4. east/south corner: `(x + width * 0.35, box.minY + 0.5, z + width * 0.35)`.

These calls occur before the following food-level and sprint conditions. `mobTick` itself has no no-clip enclosing guard in this source. Each `pushAwayFrom` call checks `this.noClip` at entry and returns before probing when true.

In 1.14.4 `LocalClientPlayerEntity#mobTick` wraps the same four calls, same corner arguments, and same order in `if (!this.noClip)`. They remain before the subsequent hunger/sprint logic. The called `pushAwayFrom` helper does not add a no-clip check.

In 26.2 `LocalPlayer#aiStep` wraps four `moveTowardsClosestSpace` calls in `if (!this.noPhysics)`, before the following sprint-reset/start checks. In order, its arguments are `(getX() - getBbWidth()*0.35, getZ() + getBbWidth()*0.35)`, `(x-, z-)`, `(x+, z-)`, and `(x+, z+)`, where `x±` and `z±` denote the same current center coordinate plus/minus `getBbWidth()*0.35`. Thus all three caller bodies preserve the same W/E/N/S corner visitation sequence. On the ordinary admitted path, the differing outer gate expressions do not reorder or skip probes.

## Candidate order and direct response writes

The 1.13.2 helper tests candidate directions west, east, north, then south, retaining a candidate only when its edge distance is strictly smaller. It writes only the selected horizontal component directly: `velocityX = -0.1F`, `velocityX = 0.1F`, `velocityZ = -0.1F`, or `velocityZ = 0.1F` for west/east/north/south respectively. The four caller invocations remain ordered independently of this per-probe candidate order.

The 1.14.4 helper tests west, east, north, south in the same order and strict-distance pattern; it writes only the selected horizontal component through `m_32166403`, passing `-0.1`, `0.1`, `-0.1`, or `0.1` as a double while preserving the other two components. 26.2 constructs the direction array in west/east/north/south order and uses the same strict closest-distance rule, then calls `setDeltaMovement` with the chosen horizontal component `0.1 * step` and the previous other components.

Therefore the call/control evidence shows no change in four-probe order or ordinary admitted control schedule across these checked sources. The 1.13.2 response literal is `0.1F`; the later direct response uses `0.1D`. That precision/magnitude delta is separate from call order and control.

Exact source locations: 1.13.2 `LocalClientPlayerEntity#mobTick` lines 653–723 and `#pushAwayFrom` lines 401–449; 1.14.4 `#mobTick` lines 619–653 and `#pushAwayFrom` lines 389–433; 26.2 `LocalPlayer#aiStep` lines 795–800 and `#moveTowardsClosestSpace` lines 451–478. Source files and ready markers are the inputs hashed above.
