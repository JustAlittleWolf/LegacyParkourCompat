# Independent audit: 1.8 heading applicability

## Verdict

**Confirmed for the bounded operation.** The ordinary local, dry, nonflying player path uses the same `Entity.updateVelocity(float sideways, float forwards, float scale)` behavior in every stable 1.8 release from 1.8 through 1.8.9. No earlier 1.8 exception to the accepted 1.8.9-to-1.9 yaw-expression boundary was found. This closes only that known applicability question; it is not complete tick coverage, a freeze of either pair, a patch-introduction proof, or runtime-parity evidence.

## Evidence and scope

I independently inspected the complete `updateVelocity`, `moveRelative`, local-control override, local input-copy, and relevant sine/cosine helper bodies in all ten exact Ornithe Feather source trees under `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/<version>/ornithe-feather/`. For 1.8 through 1.8.8, the per-version `.decompile-provenance.json` source table binds the source bytes; for 1.8.9, `ornithe-feather.sources.sha256` does so. Every checked source hash matched its canonical record. Mapping identity is Feather Gen2 `build.2` / mergedv2 for each exact release. I also checked the supplied immutable comparison file SHA-256: `4304d71613cac37168dacf3287ee245659a9e03805fc96afc6ed84b4803e9772`.

Relevant locations:

- `net/minecraft/entity/Entity.java`, `updateVelocity`: 1.8 line 843, 1.8.1 line 848, 1.8.2–1.8.9 line 844.
- `net/minecraft/entity/living/LivingEntity.java`, `moveRelative`: 1.8 line 1115, 1.8.1 line 1120, 1.8.2–1.8.9 line 1119; `mobTick` attenuates movement inputs and calls it at lines 1443–1446 (1.8.9; equivalent route in each checked version).
- `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`, `isLocallyControlled` returns `true`: line 389 in 1.8/1.8.1 and line 381 in 1.8.2–1.8.9. Its local AI copies the input sideways/forward values into the movement fields consumed by `mobTick`.
- `net/minecraft/util/math/MathHelper.java`, `sin(float)` and `cos(float)`: both use the same 65,536-entry sine table lookup in all ten releases. The table uses `(float)Math.sin(i * Math.PI * 2.0 / 65536.0)`.
- Boundary anchor: 1.8.9 `Entity.java:844–858`; 1.9 `Entity.java:943–957`. These bodies retain the same magnitude gate, normalization and X-then-Z additions; the yaw-to-radians expression changes as described below. The 1.9 helper table lookup remains the same.

## Operation and caller findings

Across the ten 1.8 releases, the helper computes `sideways * sideways + forwards * forwards` in `float`, returns when that value is below `1.0E-4F`, takes `MathHelper.sqrt(float)`, clamps the magnitude to at least `1.0F`, computes `scale / magnitude`, and scales both input floats. It then computes both trigonometric arguments from `yaw * (float) Math.PI / 180.0F`, looks up sine and cosine, and adds `sideways*cos - forwards*sin` to X before `forwards*cos + sideways*sin` to Z. No changes in operation order, primitive types, casts, threshold, lookup, or velocity write order appeared within 1.8.

`LivingEntity.moveRelative` admits its ordinary branch under `isLocallyControlled()` and dry conditions (water/lava branches have their own behavior; flying players are an exception to those fluid exclusions). The local player override makes the local path eligible; its local AI supplies the two input fields, which `mobTick` attenuates before invoking `moveRelative`. The admission conditions and route relevant to this ordinary local player path are the same across 1.8–1.8.9. Server-controlled entities do not enter that ordinary branch through the local-control path. The source confirms applicability, not every other `moveRelative` branch.

In 1.9, the helper keeps the same two-axis normalization and velocity additions, but converts yaw using `yaw * (float) (Math.PI / 180.0)`. The 1.8 expression first multiplies by rounded `(float)Math.PI` and then divides by `180.0F`; 1.9 rounds the radian-per-degree constant first and performs one float multiplication. The two sequences are not generally bit-identical for arbitrary float yaw, although many yaw values produce the same float result. Any differing argument can affect the lookup-table index and thus horizontal acceleration; this is a numeric input-path difference, not evidence that every tick produces different velocity. The older 1.8 caller gate does not alter that local player applicability. In 1.9 the caller adds a logical-side movement allowance, which does not establish server-side historical movement in 1.8 nor change the ordinary local player path under review.

## Recomputed SHA-256 bindings

The following hashes were recomputed from source bytes. Each source digest matched the per-version provenance table, or the 1.8.9 source manifest. Grouped rows have identical complete file bytes for the listed releases.

| Release(s) | `Entity.java` | `LivingEntity.java` | `MathHelper.java` | `LocalClientPlayerEntity.java` | provenance SHA-256 |
|---|---|---|---|---|---|
| 1.8 | `2be081abe1720f89d7ab90f782b8411e71bc5b8a6b53966b49a2380e3c47ffd9` | `645624913e265cad50293f659812a35c3c4303d1e8bdafdcca4eaf2e8ed5c69d` | `d70dc21aef6575374804429a7d1c3792641877c4a2d5529077796d6937484f09` | `42a92a74e5c64992572760196a2052273d96bc061cd9d1120045ba03532f1e2c` | `52c8312c34a1a491d4a200c84e90f5d8d95c7e5caa9571f864d8e70f9737db05` |
| 1.8.1 | `aa359621cf9348e5ce4bcb9e28b67f8a5c75d7e5a3d554c8d93471c79540a55c` | `fbfb0f0821a2d6b9d1d665387b2c5423fc0c1d8f5f36f7b5e1258b26d90c4c4f` | `724bcc7d2b986b7f400d36af9a5b5a855c69a24c0d1e4454285d0fc2406d6627` | `42a92a74e5c64992572760196a2052273d96bc061cd9d1120045ba03532f1e2c` | `f3a3baa039f0e0ade6e656dfa11a4529909a4b709ac489703c4250262cefb1ca` |
| 1.8.2–1.8.8 | `d4c10932cb5bb1067a5a58be4e1bb1b42bdde3b6fc893d88178cc07435a1696b` | `082831c6578e3a70fa6cea5b90bc3eefc26678259b66334470de22b90b5b0e4e` | `cb51612cdd1af73b909566813892db5c074cc453f7f7c166fbb950ab6d331ba4` | `1762b116e6b06d682b7daaa0fc8cce39b0ff455b3db8cf79dab03ac74f6c4053` | per-version values below |
| 1.8.9 | `d4c10932cb5bb1067a5a58be4e1bb1b42bdde3b6fc893d88178cc07435a1696b` | `082831c6578e3a70fa6cea5b90bc3eefc26678259b66334470de22b90b5b0e4e` | `de0aa811e3ef80cfce9ec3cf8752627b8d2de5261c7e567b34ac1d611d167366` | `1762b116e6b06d682b7daaa0fc8cce39b0ff455b3db8cf79dab03ac74f6c4053` | `ba066fc8f1c7ceb9819d2106338d9e9f2c2b96eb5a8329eb40e171b1f9889185` |

| Release | provenance SHA-256 |
|---|---|
| 1.8.2 | `8ad11ea36f2dda39ae30d57daa53316a8c0889a2f3c0c84ee5d79b9b21c25472` |
| 1.8.3 | `7da5f772d7ed1433cb3da23861723ebaec7cb714ce49c4d1a5c30499fec3afed` |
| 1.8.4 | `b2207bf9ab2a7561bf73ac92a63cc67760ccc6fd9e2378ff1e10710ecbb03a00` |
| 1.8.5 | `986d25dfd11927abed51c92643e5beda48237da875fbcdf1a48a930456ea99ce` |
| 1.8.6 | `c96f635864596f6cbd6e34ba7ba184c22a8139b25f099cc8fbbff46a85286ea9` |
| 1.8.7 | `6c6a5b64243ae7931ea1fa72a6c33440d5eec4070461cba80cdf51bf95dd93ef` |
| 1.8.8 | `588f83dc64ce0bbd0f11880d2da8e59d4d097d43225025a2a74a2b26cf909ac1` |

For 1.8.9, the recomputed SHA-256 of `ornithe-feather.sources.sha256` is `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004`, matching the supplied comparison. The 1.8 row's provenance input records the exact client SHA `ccccd0a92f76fbb24279691daad9b67f85d80471a37e59e0da76d7754982dfe8` and Feather Gen2 1.8 build.2 mergedv2 artifact SHA `3c020d1588b4aab403f5df077ca91b6e3776da2823d3b55202f4616f0d5ae5fe`.

## Limitations

This audit checks only the known ordinary heading helper and the caller path needed to establish its applicability across stable 1.8 releases. It does not re-audit the full 1.8–1.9 movement inventory, establish an exact intermediate introduction date, evaluate unrelated caller branches, or establish runtime parity. The accepted 1.8.9-to-1.9 source anchor remains the basis for that pair's yaw claim.

