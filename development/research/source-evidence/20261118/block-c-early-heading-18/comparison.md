# Domain C — 1.8 ordinary input helper comparison

## Verdict

**No exception found in the ten stable 1.8 releases (1.8 through 1.8.9).** For the bounded earlier ordinary dry, local, nonflying on-foot path, `Entity.updateVelocity(float sideways, float forwards, float scale)` uses the same two-axis float magnitude gate, normalization sequence, old yaw expression, lookup-table trig, and X-then-Z velocity additions throughout. Its ordinary caller reaches the helper under the same relevant local/dry/nonflying conditions throughout. This is a prerequisite applicability check for the already accepted 1.8.9 boundary claim; it does not establish an intermediate release change relative to 1.9.

## Exact source comparison

All ten source files are canonical local Ornithe Feather decompilations. For 1.8–1.8.8, each `.decompile-provenance.json` contains the client-jar input SHA, Feather Gen2 mapping-artifact SHA, and a path-to-source SHA table; the file SHA-256 values below bind those records. The checked `Entity.java`, `LivingEntity.java`, `MathHelper.java`, and `LocalClientPlayerEntity.java` bytes matched their provenance source entries. 1.8.9 uses the root provenance record and `ornithe-feather.sources.sha256`; those checked source bytes match the manifest. Its canonical publication/mapping identity and provenance digest are recorded in the 1.8.9 row.

The matching helper has these release-relative locations: `net/minecraft/entity/Entity.java`, `updateVelocity`, 1.8 line 843; 1.8.1 line 848; 1.8.2–1.8.9 line 844. Each body:

1. computes `sideways * sideways + forwards * forwards` as `float`;
2. proceeds iff `!(magnitudeSquared < 1.0E-4F)`;
3. applies `MathHelper.sqrt(float)`, clamps magnitude to at least `1.0F`, computes `scale / magnitude`, and scales both float axes;
4. evaluates yaw as `yaw * (float) Math.PI / 180.0F` for the `sin` argument, then repeats that same multiply-before-divide expression for `cos`;
5. adds `sideways*cos - forwards*sin` to X, then `forwards*cos + sideways*sin` to Z.

For each release, `MathHelper.sin(float)` indexes `SINE_TABLE[(int)(x * 10430.378F) & 65535]`; `cos(float)` uses `SINE_TABLE[(int)(x * 10430.378F + 16384.0F) & 65535]`. The table is populated as `(float)Math.sin(i * Math.PI * 2.0 / 65536.0)`. The 1.8 and 1.8.1 helper-file hashes differ from later files because `MathHelper` evolved elsewhere; the relevant lookup methods and table generation remain the same. No 1.8.9-specific table or helper exception exists.

`net/minecraft/entity/living/LivingEntity.java`, `moveRelative` (1.8 line 1115; 1.8.1 line 1120; 1.8.2–1.8.9 line 1119), admits the ordinary path only when locally controlled, dry, and not flying to use the fluid exceptions. The common branch selects ground friction-derived scale or `speedInAir`, then calls `updateVelocity(sideways, forwards, scale)`. `mobTick` attenuates the two movement-speed fields by `0.98F` and calls `moveRelative`; `LocalClientPlayerEntity.isLocallyControlled()` returns `true` (1.8/1.8.1 line 389; 1.8.2–1.8.9 line 381). Local `serverTickAi` copies the input's sideways/forward values into those movement-speed fields. These relevant caller bodies/source hashes are stable in groups 1.8, 1.8.1, and 1.8.2–1.8.9; the route has no release-specific admission exception. Server-side entities fail the local-control gate.

### Exact source SHA-256 groups

| Releases | Entity.java | LivingEntity.java | MathHelper.java | LocalClientPlayerEntity.java |
|---|---|---|---|---|
| 1.8 | `2be081abe1720f89d7ab90f782b8411e71bc5b8a6b53966b49a2380e3c47ffd9` | `645624913e265cad50293f659812a35c3c4303d1e8bdafdcca4eaf2e8ed5c69d` | `d70dc21aef6575374804429a7d1c3792641877c4a2d5529077796d6937484f09` | `42a92a74e5c64992572760196a2052273d96bc061cd9d1120045ba03532f1e2c` |
| 1.8.1 | `aa359621cf9348e5ce4bcb9e28b67f8a5c75d7e5a3d554c8d93471c79540a55c` | `fbfb0f0821a2d6b9d1d665387b2c5423fc0c1d8f5f36f7b5e1258b26d90c4c4f` | `724bcc7d2b986b7f400d36af9a5b5a855c69a24c0d1e4454285d0fc2406d6627` | `42a92a74e5c64992572760196a2052273d96bc061cd9d1120045ba03532f1e2c` |
| 1.8.2–1.8.8 | `d4c10932cb5bb1067a5a58be4e1bb1b42bdde3b6fc893d88178cc07435a1696b` | `082831c6578e3a70fa6cea5b90bc3eefc26678259b66334470de22b90b5b0e4e` | `cb51612cdd1af73b909566813892db5c074cc453f7f7c166fbb950ab6d331ba4` | `1762b116e6b06d682b7daaa0fc8cce39b0ff455b3db8cf79dab03ac74f6c4053` |
| 1.8.9 | `d4c10932cb5bb1067a5a58be4e1bb1b42bdde3b6fc893d88178cc07435a1696b` | `082831c6578e3a70fa6cea5b90bc3eefc26678259b66334470de22b90b5b0e4e` | `de0aa811e3ef80cfce9ec3cf8752627b8d2de5261c7e567b34ac1d611d167366` | `1762b116e6b06d682b7daaa0fc8cce39b0ff455b3db8cf79dab03ac74f6c4053` |

### Canonical input/provenance bindings

The Feather mapping identity is per release (`feather-gen2-<version>+build.2`, mergedv2); the mapping-artifact and client-jar hashes for 1.8–1.8.8 are embedded in the immutable provenance file named by each row. Each provenance SHA below was computed from the canonical file. 1.8.9's provenance and source-manifest digests also match the previously frozen r2 audit inputs.

| Release | Canonical provenance file SHA-256 | Client input SHA-256 |
|---|---|---|
| 1.8 | `.decompile-provenance.json`: `52c8312c34a1a491d4a200c84e90f5d8d95c7e5caa9571f864d8e70f9737db05` | `ccccd0a92f76fbb24279691daad9b67f85d80471a37e59e0da76d7754982dfe8` |
| 1.8.1 | `.decompile-provenance.json`: `f3a3baa039f0e0ade6e656dfa11a4529909a4b709ac489703c4250262cefb1ca` | `66a749ad95adcb929439155c0341309e89e437444867e108dcb36fd16493b0bd` |
| 1.8.2 | `.decompile-provenance.json`: `8ad11ea36f2dda39ae30d57daa53316a8c0889a2f3c0c84ee5d79b9b21c25472` | `5cc5b24312f3d182bf06e7f1a4da3a05ce94be6c861d5c9995c86e6d8811fbe9` |
| 1.8.3 | `.decompile-provenance.json`: `7da5f772d7ed1433cb3da23861723ebaec7cb714ce49c4d1a5c30499fec3afed` | `4da43d06c8c1605a9a3ba1bf22893245cf1b108eb5c8fed859012252370c2c06` |
| 1.8.4 | `.decompile-provenance.json`: `b2207bf9ab2a7561bf73ac92a63cc67760ccc6fd9e2378ff1e10710ecbb03a00` | `d68a3aa5271abece4ce2b7048f6caf4f58071476344d9a52fe9ab1410c5d458b` |
| 1.8.5 | `.decompile-provenance.json`: `986d25dfd11927abed51c92643e5beda48237da875fbcdf1a48a930456ea99ce` | `2d825073de0da4383c0525e81d181ec81f92d17daa2f0b81faff3850492e461d` |
| 1.8.6 | `.decompile-provenance.json`: `c96f635864596f6cbd6e34ba7ba184c22a8139b25f099cc8fbbff46a85286ea9` | `e597384cf964950b02650a39f73c2cbf5ce9e51d934cdfa1291530896e7cf100` |
| 1.8.7 | `.decompile-provenance.json`: `6c6a5b64243ae7931ea1fa72a6c33440d5eec4070461cba80cdf51bf95dd93ef` | `2e45066ec194c6daec5f7dbb27822ef0b5855d5829f62392906620145689a3e0` |
| 1.8.8 | `.decompile-provenance.json`: `588f83dc64ce0bbd0f11880d2da8e59d4d097d43225025a2a74a2b26cf909ac1` | `9481ed51d7fc4be54ec38c509f84124fbac5d9fea238bbde5b2e6c4f753b1ac8` |
| 1.8.9 | `ornithe-feather.provenance.json`: `ba066fc8f1c7ceb9819d2106338d9e9f2c2b96eb5a8329eb40e171b1f9889185`; source manifest `ornithe-feather.sources.sha256`: `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004` | manifest-bound `Entity.java` and accepted r2 exact-source identity; mapping is Feather Gen2 `1.8.9+build.2` |

## Boundary relationship

The frozen 1.8.9–1.9 audit accepts the 1.9 yaw-angle expression change: 1.8.9 uses `yaw * (float) Math.PI / 180.0F`, while 1.9 uses `yaw * (float) (Math.PI / 180.0)`. Since all 1.8 releases checked here retain the 1.8.9 expression and helper path, this comparison confirms the accepted expression's applicability over the prior stable 1.8 release sequence. No earlier exception or narrower 1.8 cutover was found.
