# Blind source audit: player pitch setters and Elytra pitch read

## Verdict

For the bounded `1.10.2` → `1.11` comparison, the ordinary camera-turn arithmetic, the current/previous pitch clamp order, and the first stored-pitch reads in the dry active Elytra movement path are unchanged. I found no release-boundary movement difference in this slice. This is a bounded no-difference verdict, not a full-pair coverage audit.

The exact 1.11 input is canonical ID `1.11` as requested; it was not substituted with `1.11.2`.

## Exact inputs and readiness

Both version roots publish `status: ready`, `mapping: ornithe-feather`, and matching `versionId` fields in their ready and provenance markers.

| Root | Feather build | Source files verified against `*.sources.sha256` | Source-manifest SHA-256 | Artifact-manifest SHA-256 |
|---|---|---:|---|---|
| `decompiled_minecraft/1.10.2/ornithe-feather` | `feather-gen2-1.10.2+build.2` | 1,845 / 1,845 | `91b0f478acb7b6f13463c35b268a30d2806f583402631a56f54ce2eb70d1ec71` | `6b402f3e6d6cf2f7b3647806364ff44214c47348fefd6949fad03e2011379116` |
| `decompiled_minecraft/1.11/ornithe-feather` | `feather-gen2-1.11+build.2` | 1,918 / 1,918 | `8b3e5da58494f1dba77410960753b0bf646425a1ada491d8242cc71e6c7a59ca` | `ae874615f961d67e402c978ff01cd3f26f61145e6b3c21ef318c1ab71f7794fd` |

For each root, the ready marker's source-manifest and artifact-manifest digests match the files on disk. The ready marker's source file count matches the manifest line count. Every listed source file exists and matches its SHA-256. Target method bodies inspected below are within those verified files.

The artifact manifests pin the exact mapping archives and Tiny files. Their entries are:

- 1.10.2 merged mapping JAR: `18cffc56c2d8de89b50cb0328b174566236553c4aafb62afdc58a1e0ff0cadb0`; Tiny: `7c4055aa9becb027462fe4f4a9af8822de4df9b70ecb80a84e38fd9e81e33af1`.
- 1.11 merged mapping JAR: `658ad8c5a5d661eb5c2f97c00a47f6e02c02d5eee1eb1005f8e2926eb95dec44`; Tiny: `46bd6702a0983ad667109aadad90b2e6390ed5cd5999561a8a904956f4baf9c0`.

The historical artifact-cache paths named by provenance are absent from this checkout, so I could not recompute those four raw mapping-artifact hashes from the original files. The on-root artifact manifests, their hashes, the mapping names, and the exact release metadata pins are present and consistent with ready/provenance. 1.11 provenance records version metadata SHA-256 `a77e2568ef2f6733064c394eb40a7f1d8ca8ed48c56ee17cb6c720aeaf0118f4` and client SHA-256 `f0d11987ea743f55a2c03bf77216e6dc9baea7201cc7bf498ceb18043e984634`; 1.10.2's ready marker records version metadata ID `1.10.2` and client SHA-256 `7cdf7fcdc1c92584a233bf3c42bd7f0df1bdad3007d306831fe50410692be1e9`.

## Compared behavior

### Ordinary direct camera turn and finite overrange

In both versions, `GameRenderer.render` scales mouse Y by the sensitivity cubic and applies the invert-Y multiplier before calling `player.updateLocalPlayerCamera(h, i * j)`. Smoothing changes the mouse deltas before the same call. The relevant sequence is identical: 1.10.2 `GameRenderer.java:832–855`; 1.11 `GameRenderer.java:844–867`.

Both versions' `Entity.updateLocalPlayerCamera` (`1.10.2 Entity.java:291–301`; `1.11 Entity.java:300–310`) do this in the same order:

1. Save the old current pitch in `f`.
2. Compute `this.pitch = (float)(this.pitch - pitch * 0.15)`.
3. Clamp the new current pitch to `[-90.0F, 90.0F]`.
4. Adjust previous pitch with `this.lastPitch = this.lastPitch + (this.pitch - f)`.

Thus a finite out-of-range result of the direct turn is clamped before the previous-pitch delta is computed. The previous field is shifted by the clamped current-pitch delta; this code does not separately clamp or assign `lastPitch`. `MathHelper.clamp(float, float, float)` is identical in both roots (`MathHelper.java:80–86`): values below/above the bounds return the bound. The target files are hash-pinned in both source manifests.

The other pitch setters also match across the pair. `Entity.updatePositionAndAngles` clamps its `pitch` parameter first, then assigns both current and previous pitch from that clamped value (`1.10.2 Entity.java:1002–1025`; `1.11 Entity.java:1024–1047`). `Entity.setPositionAndAngles`, by contrast, assigns its pitch argument directly to current pitch and does not touch previous pitch (`1.10.2 Entity.java:1031–1044`; `1.11 Entity.java:1053–1066`). `setRotation` applies `% 360.0F` in both (`1.10.2 Entity.java:277–280`; `1.11 Entity.java:286–289`). These distinct setter semantics are not a 1.10.2→1.11 change.

### First stored-pitch read in dry active Elytra movement

The player override calls `super.moveRelative` in the ordinary non-creative-flying path in both versions (`1.10.2 PlayerEntity.java:1391–1409`; `1.11 PlayerEntity.java:1385–1403`). In `LivingEntity.moveRelative`, the branch is gated by the same movement-authority check, then by not being in water and not being in lava for a normally flying player, then by `isFallFlying()` (`1.10.2 LivingEntity.java:1332–1343`; `1.11 LivingEntity.java:1386–1397`).

Within that branch, the first stored-pitch access is indirect: `getLookVector()` calls `getRotationVector(1.0F)`, whose `tickDelta == 1.0F` arm passes `this.pitch` and `this.headYaw` directly to the rotation-vector helper (`1.10.2 LivingEntity.java:1853–1865`; `1.11 LivingEntity.java:1914–1926`). The next explicit read is `float i = this.pitch * (float)(Math.PI / 180.0)` (`1.10.2 LivingEntity.java:1341–1348`; `1.11 LivingEntity.java:1395–1402`). The later trigonometric uses and ordering match. The only relevant movement-call difference in the displayed branch is the general API change from `move(dx, dy, dz)` to `move(MoverType.SELF, dx, dy, dz)`; it does not change which stored pitch is read or its math here.

The active-flight validation is also in the same order: read flag 7; require the flag, airborne state, and `!isRiding()`; then check the chest Elytra and `ElytraItem.canFly`; finally retain/update the flag under the existing client/server gate. See `1.10.2 LivingEntity.java:1754–1772` and `1.11 LivingEntity.java:1808–1826`. The client-side path can enter `moveRelative` with that active flag; the pitch read itself is not newly gated or transformed in 1.11.

## Reproduction identities

The exact ready/provenance/source-pin files are under the two roots named above. Focused verified source hashes:

| Source | 1.10.2 SHA-256 | 1.11 SHA-256 |
|---|---|---|
| `net/minecraft/entity/Entity.java` | `05da145effa19a6ef7934cc276e89226373b67c12f4ce89a8ce2183f29039f77` | `3209feaacd1e798b521a193eeb081d92c96881a70a18254ef24a58e911a4fd97` |
| `net/minecraft/entity/living/LivingEntity.java` | `d40dd476b6b68c6ce45b4202823475deb546ecda2284da330ff6724b33815e82` | `1f2e31fa6a905ccc2a51a0338f2aaf448f2b0569f28644e8205d864e570d9b2b` |
| `net/minecraft/entity/living/player/PlayerEntity.java` | `a055b84b98d98e828e177cad9bba47a1334ee9bf3ecd4331792979106235a302` | `a0d787681de5caecda03fea1536dac0c07e6ca5cfbfe3b5fbb700cbe77c86fb6` |
| `net/minecraft/util/math/MathHelper.java` | `43b2a4e3931733dbc3538024b7182b024d874a5f22a269a6199c6b4ca8c59151` | `97aa2499681fdd8cee6564f777310ee396bc447fef78c2273a990e9202d9641b` |
| `net/minecraft/client/render/GameRenderer.java` | `35467c3723b9b50bb7e25ee439b8262d091cee12a1b94881f25fe90d35a83c52` | `7b46d9bb3a0e78f3921dfbf75c0563befb2cfb521745b3235274f73cdc817d09` |

No tests, game/runtime launches, source generation, network lookups, product sources, Git history, wiki material, or research/progress artifacts were used or edited.

## Phase 2 independent review

### Inputs and overall verdict

I rehashed the authorized author inputs before review: `report.md` SHA-256 `ac35e7276f6b76a715556dae840c48c677bad9a9664538274ff6a238d996518b`; `identity.md` SHA-256 `f03e783f426847aff39fa3129ddf69312025da04607cfb2552100bc18a59b2dd`. Both matched the dispatch pins. I also independently recomputed the named version-root ready/provenance/source-manifest/artifact-manifest hashes; they match the identity sidecar. All listed source manifests and source-file hashes checked in phase one still agree.

The identity sidecar corrects the report's provenance caveat. Both roots have their version-root ready, provenance, source-hash and artifact-hash files; the exact IDs and mapping build.2 inputs are pinned. The report's statement that the local source trees lack any usable provenance/input identity is rejected. For 1.10.2, the provenance file hash is bound by the sidecar because its ready marker has no provenance-digest field; the artifact manifest and ready marker pin release metadata, client and mapping artifacts. The raw mapping archives are absent from their historical cache paths, so this audit still cannot recompute those archives directly.

The report's direct pitch-setter comparison is accepted: its ordinary camera-turn expression, finite-overrange saturation, and current-before-previous clamp/update order match in both versions. Its finite witness is arithmetically valid for a direct finite method argument: the `double` multiplication by `0.15`, cast to float, current clamp to 90, then float previous-pitch update yields 170 from the stated initial values.

The report identifies the first pitch-dependent call in the dry active Elytra branch correctly as `getLookVector()`, but its call-chain account is incomplete on the 1.10.2 side. That omission does not change the bounded movement verdict because the local-player tick sets `headYaw = yaw` before `flyingTick()` and the following `moveRelative()` in both versions. The distinct helper implementations and the reachability condition are detailed below.

### Independent trace of the first stored-pitch read

In 1.10.2, `LivingEntity.getLookVector()` calls `this.getRotationVector(1.0F)` (`LivingEntity.java:1853–1855`). No `getRotationVector(float)` override exists in `LocalClientPlayerEntity`. Dynamic dispatch therefore reaches `LivingEntity.getRotationVector(float)`; its `tickDelta == 1.0F` arm passes current `this.pitch` and `this.headYaw` to the vector helper (`LivingEntity.java:1858–1865`). That is the first stored-pitch read in the Elytra branch, before its explicit `float i = this.pitch * (float)(Math.PI / 180.0)`.

In 1.11, inherited `LivingEntity.getLookVector()` calls `this.getRotationVector(1.0F)` (`LivingEntity.java:1914–1916`), which dynamically dispatches to the new `LocalClientPlayerEntity.getRotationVector(float)` override. That override ignores `tickDelta` and passes current `this.pitch` and `this.yaw` to the vector helper (`LocalClientPlayerEntity.java:154–155`). The Elytra branch's first stored-pitch access is there; the explicit `this.pitch` angle conversion follows at `LivingEntity.java:1395–1396`.

The helper input names therefore differ at method level (`headYaw` in the inherited 1.10.2 path; `yaw` in the 1.11 local-player override). For the actual local player movement path, both inputs are equal before the call: `LocalClientPlayerEntity.isLocallyControlled()` returns true in both versions (`LocalClientPlayerEntity.java:458–460` / `468–470`); `LivingEntity.mobTick()` invokes `serverTickAi()` under that gate before the travel section (`1.10.2 LivingEntity.java:1720–1747`; `1.11 LivingEntity.java:1774–1801`); and `PlayerEntity.serverTickAi()` assigns `this.headYaw = this.yaw` (`1.10.2 PlayerEntity.java:405–410`; `1.11 PlayerEntity.java:409–414`). The same tick then runs `flyingTick()` and `moveRelative()`. Thus this getter refactor does not change the vector input or the first stored-pitch value on the bounded dry active local-Elytra path, even though the override differs for other states/callers.

The required Elytra activation gates remain as stated in the report: active flag, airborne, and non-riding in `flyingTick()`, then normal local-player `moveRelative()` reaches the branch only under the same dry fluid gates and the active flag. The movement trace is source-backed; no runtime trajectory or universal method-equivalence claim is made.

### Verdict by claim

- Direct local-camera pitch arithmetic and statement order: **accepted; no endpoint difference**.
- Finite overrange witness and effect on current/previous pitch: **accepted** for the direct method argument and supplied finite values.
- First stored-pitch read in dry active local Elytra movement: **accepted with call-chain correction**. Both paths first read current pitch in `getLookVector()`; older uses `headYaw` as the accompanying horizontal angle and newer uses `yaw`, equalized by the local tick before this movement call.
- Source identity/provenance qualification in the report: **rejected and superseded by the verified identity sidecar**. Exact roots, release IDs, Feather mapping build.2, local source hashes, and pinned input metadata are established from the local manifests. Only raw artifact rehash remains unavailable.
- Full-pair source freeze, intermediate-release boundary, implementation, and runtime parity: **not established** by this bounded audit.