# 1.10.2→1.11 Elytra near-vertical look alias

## Result

No source-confirmed change in the pre-move Elytra steering math between exact Minecraft 1.10.2 and 1.11 (Ornithe Feather mappings). The negative-pitch branch does divide by the horizontal look magnitude `j` without a local zero check in both releases. However, the nearby sine-table behavior matters: at vertical pitch the lookup's cosine is the tiny nonzero float `(float)Math.sin(Math.PI)`, not zero. The yaw lookup supplies a nonzero horizontal direction, so `j` remains positive in this vertical lookup bucket; the suspected `0/0` and NaN path is not supported by these exact sources for an ordinary legal view. This is a shared source pattern, not a pair delta.

## Exact evidence

- **1.10.2:** `decompiled_minecraft/1.10.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `moveRelative`, lines 1332–1371. Local-control and dry checks enclose `if (this.isFallFlying())` (1333–1336). The selected look vector is read at 1341; `i` is float pitch radians, `j` is the double square root of the look vector's X/Z squares, and `k` is the double horizontal speed magnitude (1342–1345). Downward-pitch compensation is guarded by `j > 0.0` at 1349, but the separate negative-pitch branch at 1356–1361 has no local guard: it computes `o` and subtracts `vec3d.x * o / j` and `vec3d.z * o / j`. The later steering block also checks `j > 0.0` (1364). Damping and the movement call occur later (1368–1371).
- **1.11:** `decompiled_minecraft/1.11/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `moveRelative`, lines 1386–1425. Corresponding gates and expressions are unchanged: `getLookVector` 1395; float `i`, double `j`/`k` 1396–1399; guarded downward compensation 1403; negative-pitch branch and the two divisions by `j` 1410–1415; guarded steering 1418; damping then `move` 1422–1425.
- **Both releases' view path:** `LivingEntity.getLookVector` delegates to `getRotationVector(1.0F)` (1.10.2 lines 1853–1859; 1.11 lines 1914–1920). At `tickDelta == 1.0F`, `LivingEntity.getRotationVector` uses current `pitch` and `headYaw`; otherwise it interpolates. `PlayerEntity.serverTickAi` assigns `headYaw = yaw` (1.10.2 line 409; 1.11 line 413). `Entity.getRotationVector(float,float)` has float intermediates and constructs the look vector from `MathHelper` LUT trig (1.10.2 lines 1148–1154; 1.11 lines 1170–1176). Both `MathHelper` files use the same lookup at lines 15–20 and initialize entries with `(float)Math.sin(i * Math.PI * 2.0 / 65536.0)` at line 424 (1.10.2) / 428 (1.11).
- **Near-vertical LUT value:** At pitch `-90.0F`, the rotation-vector cosine lookup indexes slot 32768: the index expression is `(int)(x * 10430.378F + 16384.0F) & 65535`, with `x` produced from the float pitch-to-radians multiplication. Slot 32768 is initialized from `sin(π)`, which is approximately `1.2246469E-16` as a float, not zero. The X/Z components are that tiny float multiplied by the yaw sine/cosine pair and then promoted to doubles in `Vec3d`; the pair cannot both be zero at once. Therefore their squared sum and `j` are positive in this bucket. Neighboring pitch values that alias to the same LUT slot retain the same nonzero result. This preserves the exact float-to-double ordering relevant to the division.
- **Reachability:** `Entity.updateLocalPlayerCamera` clamps pitch to `[-90.0F, 90.0F]` in both releases (1.10.2 lines 291–297; 1.11 lines 300–306), making the vertical endpoint ordinarily reachable.
- **Move call difference:** the 1.10.2 branch calls `move(dx,dy,dz)` at 1371; the 1.11 branch calls `move(MoverType.SELF, x,y,z)` at 1425. This is after the compared look-vector and steering math. The report does not infer equivalence of the broader `Entity.move` implementations.

For `j > 0.0`, both guarded terms execute according to their other conditions, and the negative-pitch divisions are finite for finite components. A literal `j == 0.0` would make those unguarded divisions suspect, but the exact view LUT's vertical entry is nonzero; no zero-horizontal-look result was established for the ordinary legal near-vertical path. The active branch remains enclosed by local movement and dry-medium gates and `isFallFlying()` (flag 7 in both releases: 1.10.2 `LivingEntity.java:2088–2090`; 1.11 `:2143–2145`). No malformed input or runtime behavior was considered.

## Source identity and hash checks

Both source trees use the exact `ornithe-feather` namespace. The 1.10.2 provenance records the exact 1.10.2 client, Feather Gen2 1.10.2 build.2 artifacts, Vineflower 1.12.0 and Java 25; its ready marker identifies client SHA-256 `7cdf7fcdc1c92584a233bf3c42bd7f0df1bdad3007d306831fe50410692be1e9`. The 1.11 provenance records exact version ID `1.11`, Feather Gen2 1.11 build.2 artifacts, Vineflower 1.12.0 and Java 25; client SHA-256 is `f0d11987ea743f55a2c03bf77216e6dc9baea7201cc7bf498ceb18043e984634`.

The complete source-manifest hashes match their ready markers and the manifests' listed hashes for every cited Java file match local SHA-256 recomputation:

| Version | Source-manifest SHA-256 | Relevant source-file SHA-256 |
|---|---|---|
| 1.10.2 | `91b0f478acb7b6f13463c35b268a30d2806f583402631a56f54ce2eb70d1ec71` | `LivingEntity.java` `d40dd476b6b68c6ce45b4202823475deb546ecda2284da330ff6724b33815e82`; `Entity.java` `05da145effa19a6ef7934cc276e89226373b67c12f4ce89a8ce2183f29039f77`; `MathHelper.java` `43b2a4e3931733dbc3538024b7182b024d874a5f22a269a6199c6b4ca8c59151` |
| 1.11 | `8b3e5da58494f1dba77410960753b0bf646425a1ada491d8242cc71e6c7a59ca` | `LivingEntity.java` `1f2e31fa6a905ccc2a51a0338f2aaf448f2b0569f28644e8205d864e570d9b2b`; `Entity.java` `3209feaacd1e798b521a193eeb081d92c96881a70a18254ef24a58e911a4fd97`; `MathHelper.java` `97aa2499681fdd8cee6564f777310ee396bc447fef78c2273a990e9202d9641b` |

1.10.2 ready-marker source-manifest SHA-256: `91b0f478acb7b6f13463c35b268a30d2806f583402631a56f54ce2eb70d1ec71`. 1.11 ready-marker source-manifest SHA-256: `8b3e5da58494f1dba77410960753b0bf646425a1ada491d8242cc71e6c7a59ca`. 1.11 provenance JSON SHA-256: `6f775c806fd1401a4d1756e9ef88da8d73185fe1d13598235f85c424405f4c40`. The 1.10.2 local verification record reports all 1,845 published source files identical to its verification output; its note about one regenerated intermediate remapped JAR does not apply to the verified source tree.

## Scope boundary

This is a bounded source comparison for the assigned near-vertical Elytra steering slice only. It is not a full-pair coverage or acceptance verdict, implementation handoff, or runtime-parity claim.
