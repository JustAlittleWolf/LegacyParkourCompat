# 1.12.0–1.12.1 ordinary local pitch setter slice

## Bound and verdict

Exact pair: Minecraft `1.12` (1.12.0) → `1.12.1`, Ornithe Feather mappings, `build.2` on both sides. This report is limited to the ordinary local camera pitch writer, its current/previous pitch ordering, and the earliest current-pitch read in local dry Elytra movement. The compared sources show **no difference** in this slice; do not create a movement finding for it. This does not close the full release-pair inventory.

## Canonical provenance and integrity

Both canonical `decompiled_minecraft/<version>/ornithe-feather/` output directories have `.decompile-provenance.json` manifests. These record exact client and mapping origins, pipeline/tool/runtime data, and source hashes. The four relevant source files below match their per-file manifest hashes on both sides and are byte-identical across the pair.

| Version | Manifest SHA-256 | Exact client origin SHA-256 | Feather mapping origin SHA-256 | Feather metadata SHA-256 |
|---|---|---|---|---|
| 1.12 | `27515e5f4c71e7db2f035a81716507128d77466c75380ad1ef7d140c06401467` | `https://piston-data.mojang.com/v1/objects/909823f9c467f9934687f136bc95a667a0d19d7f/client.jar` — `59e84cf6acc3912e1705ab05c722485afb3ec33343eef4756d8c16edfe6411ca` | `https://maven.ornithemc.net/releases/net/ornithemc/feather-gen2/1.12+build.2/feather-gen2-1.12+build.2-mergedv2.jar` — `73ce7d6d0a281db15fa030ae09e836387dae1172fdffcaf3e2d1a4840830b719` | `https://meta.ornithemc.net/v3/versions/feather/1.12` — `5b4555cb0473f14b4bde13b7f60f7f979dc4f8e149187f5696ae8283e437fac4` |
| 1.12.1 | `0ff5246e8d29c04554cc9ec93ae9b4abef539652325b6c0ac822851d7186a9c8` | `https://piston-data.mojang.com/v1/objects/83385d346cf7f97d1e447b888750fb88e9928b93/client.jar` — `19331fb29bf8b8d5ed764677c94cca9d66481e10104f660eb555894a4300cf22` | `https://maven.ornithemc.net/releases/net/ornithemc/feather-gen2/1.12.1+build.2/feather-gen2-1.12.1+build.2-mergedv2.jar` — `f94febd2167d6091b4c157aef0a85c8791e6f8fb27b883e27a82a68c7b02b9c7` | `https://meta.ornithemc.net/v3/versions/feather/1.12.1` — `1893deb3a1a74c6f2054876e2b8512817f744188aa8a9d8aafdba8525c0e896b` |

The canonical mapping directories contain the decompiler provenance manifest but no separate pair-ready marker. Exact source provenance is therefore verified; independent campaign readiness is not inferred from that manifest. No substitute release, mapping, or source was used.

| Source path under `ornithe-feather/` | 1.12 and 1.12.1 SHA-256; manifest match on both | Pair content |
|---|---|---|
| `net/minecraft/entity/Entity.java` | `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a` | Identical |
| `net/minecraft/entity/living/LivingEntity.java` | `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6` | Identical |
| `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `d7237b8b9a68525bbfec6af1c234d2aafb1e4aa7d52059331d1d243313d6d7d1` | Identical |
| `net/minecraft/client/render/GameRenderer.java` | `c6634f86b93421a4f1d05ab3df5bfdf8edef3a295190a3a06eba48a4cac874bb` | Identical |

## Compared source behavior

- `Entity.java:108,110` declares `pitch` and `lastPitch` as `float`. The ordinary direct rotation setter at `:293–296` accepts `float yaw, float pitch` and assigns each modulo `360.0F`; it does not clamp pitch or update `lastPitch`. The same text occurs in both releases.
- The local camera method `Entity.updateLocalPlayerCamera(float yaw, float pitch)` at `:307–314` snapshots old current pitch into float `f`, updates current pitch as `(float)(this.pitch - pitch * 0.15)`, clamps that current value to `[-90.0F, 90.0F]`, then updates previous pitch with `this.lastPitch + (this.pitch - f)`. The clamp precedes the previous-pitch adjustment in both releases.
- Finite overrange witness for this method: starting with `pitch = 0.0F` and `lastPitch = 0.0F`, a finite camera pitch delta of `-1000.0F` yields an unclamped current pitch of `150.0F`; the clamp stores `90.0F`, after which `lastPitch` becomes `90.0F`. `GameRenderer.java:863,867` supplies the camera method from its local mouse-input path in both versions. This witness describes the source arithmetic; it is not a runtime launch or test.
- In an active, dry, locally controlled Elytra branch, `LivingEntity.moveRelative` begins at `:1425`; its local-control and liquid gates lead to `isFallFlying()` at `:1429`. The first actual stored-pitch read in that branch is `getLookVector()` at `:1434`. `Entity.getLookVector()` at `:1690–1692` passes the current `this.pitch` directly to the rotation-vector math. The branch then separately converts current pitch to radians at `LivingEntity.java:1435` for glide terms. This is current pitch, not `lastPitch` or an interpolated pitch. Both releases have identical gate and operation order in the manifest-verified files.

## Slice status

Setter width and modulo semantics, local current/previous clamp order, the finite overrange witness, mouse call sites, and first actual stored-pitch use in active dry local Elytra were examined in both exact outputs. No difference was found in this bounded slice. No broader tick or dependency inventory was attempted.
