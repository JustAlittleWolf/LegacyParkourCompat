# 1.17 to 1.17.1: player swim-pitch Y correction

## Verdict

No source difference was found in the assigned direct local-player swim-pitch correction path. The 1.17 `Player.travel` correction is retained verbatim in 1.17.1; the inherited view-vector lookup, locally controlled water travel, and ordinary collision move used by this path preserve the same math and ordering. This is a checked slice with no finding.

## Exact source identity

Both sides use the `mojmap` namespace and exact client release sources.

- **1.17:** `decompiled_minecraft/1.17/mojmap/`. The hidden `.decompile-provenance.json` records the version metadata input `https://piston-meta.mojang.com/v1/packages/0d9ace8a2ecfd1f4c782786f4b985a499240ff12/1.17.json` (SHA-256 `537c6fa0e6ab13caa76ce2e84e4748882d36fcd5a076fa6903f894880e1df0f6`), client JAR `https://piston-data.mojang.com/v1/objects/1cf89c77ed5e72401b869f66410934804f3d6f52/client.jar` (SHA-256 `14122287d4e4256c2d65ec8c0ccfa3b56448495fa3ccabc87d0f0eeb4fa1c30f`), and official client mappings `https://piston-data.mojang.com/v1/objects/227d16f520848747a59bef6f490ae19dc290a804/client.txt` (SHA-256 `191b957aaa40b294402feef29651ae7a9f1263ec22c5382f3b8fa0a4918b0471`). The manifest's source hashes match the local relied files.
- **1.17.1:** `decompiled_minecraft/1.17.1/mojmap/`, published with `mojmap.ready.json`, `mojmap.provenance.json`, `mojmap.sources.sha256`, and `artifacts.sha256`. Ready marker identifies version `1.17.1`, mapping `mojmap`, client SHA-256 `a49b4a56c5bbe15c9ed9fe53efa9a591f265a1f5ba7d6aa9739a58ea7a92b79d`, official client-mappings artifact SHA-256 `2b28ded68f8602aaf2f35ef92dd10ee41ba2cf9723e29570155d60e1723848e9`, and source-manifest SHA-256 `93270d229acfb751bf56daf1e7be26ce3dcff26b29e94aa157de621405a3463b`. The manifest binds the source files below.

SHA-256 of the relied Java sources, checked against the source manifests where published:

| Source | 1.17 | 1.17.1 |
| --- | --- | --- |
| `net/minecraft/world/entity/player/Player.java` | `9ad4c439814caf5f51cf6da9a86c5d22906b0a28b5b606bdeb1b770617d90516` | `724bb298499dabe489dffd5ce7ec81c9773619a8d2c70044911eae4c9e8ff481` |
| `net/minecraft/world/entity/LivingEntity.java` | `8e81e5f1317d154fa41b676721ff581badd46296f77bcebcadb3c63448e528ed` | `33fd081aadb2b6fdc9ebf487db6da5b38c54f4b8676572790ee2203690d15e6f` |
| `net/minecraft/world/entity/Entity.java` | `81231ad8ac1b9a7a9f7ee186352c7e73c0c5211a3034c3a35c04badc6914c8ef` | `ab28e1fba924771ec048140dfd293ee5a46a7dfe81f71a1a0b1aecc1927232de` |
| `net/minecraft/util/Mth.java` | `24515c4549e01e985017227dccf7159166a675b232be9135d5f896022a9cb113` | `24515c4549e01e985017227dccf7159166a675b232be9135d5f896022a9cb113` |

## Compared path

- **1.17 `Player.travel(Vec3)`, `Player.java:1418–1448`; 1.17.1, `Player.java:1409–1439`.** Both first snapshot position, then enter the correction only for `isSwimming() && !isPassenger()`. They read `g = getLookAngle().y`, choose `h = g < -0.2 ? 0.085 : 0.06`, then—only when `g <= 0.0`, `jumping`, or the short-circuited upper-position fluid query is nonempty—add `(g - deltaMovement.y) * h` to Y. The comparison operators, conditional order, position expression `getY() + 1.0 - 0.1`, `BlockPos` construction, `Vec3.add`, and subsequent `super.travel(vec3)` dispatch are unchanged. There is no added pitch clamp or changed threshold.
- **Look vector:** 1.17 `Entity.java:1325–1344, 1829–1831`; 1.17.1 `Entity.java:1327–1346, 1831–1833`. `getLookAngle()` calls `calculateViewVector(getXRot(), getYRot())`; the float radians and `Mth.cos/sin` products producing Y (`-sin(pitchRadians)`) are unchanged. Both `Mth.java` files are byte-identical (same SHA-256). The lookup table is filled with 65,536 `(float)Math.sin(ix * Math.PI * 2.0 / 65536.0)` values; `sin` indexes via `(int)(f * 10430.378F) & 65535`, and `cos` uses `(int)(f * 10430.378F + 16384.0F) & 65535`. Thus the float multiplication/addition, truncating cast and mask order used by this path have no patch change.
- **Actual inherited local movement:** 1.17 `LivingEntity.travel(Vec3)`, `LivingEntity.java:1999–2032`, and `handleRelativeFrictionAndCalculateMovement`, `2151–2161`; 1.17.1 `LivingEntity.java:2003–2036` and `2155–2165`. When the local instance controls movement, the water/affected-by-fluids/non-standable-fluid gate reaches `moveRelative` and then `move(MoverType.SELF, getDeltaMovement())`, followed by the same water slowdown and fluid-falling updates. The correction precedes this inherited travel in both versions.
- **Collision/position write:** 1.17 and 1.17.1 `Entity.move(MoverType, Vec3)`, `Entity.java:534–642` (same relevant body). For this `SELF` request, the routine applies edge backoff and collision, writes the resulting position when nontrivial, derives collision/on-ground flags, adjusts blocked velocity components and performs post-move effects in the same order. This is the real inherited movement implementation reached through the swim water branch; it is not replaced by a special swim move in either release.

## Coverage boundary

Examined the named correction, its `getLookAngle`/view-vector and trig-LUT dependencies, the `LivingEntity.travel` water branch and helper that invokes `move`, and the inherited `Entity.move` collision/position writer. No difference found in these slices. This result does not claim coverage of other swim mechanics, swimming-state acquisition, fluid/block shape providers, or the rest of the player tick graph.

