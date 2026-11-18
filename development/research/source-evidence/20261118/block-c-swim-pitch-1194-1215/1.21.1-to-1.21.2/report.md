# 1.21.1 → 1.21.2: Player swim pitch-derived vertical correction

## Scope and evidence readiness

Bounded slice: ordinary nonpassenger `Player.travel(Vec3)` swim correction, the `Entity` pitch source used by `getLookAngle().y`, the `Mth` trigonometric lookup, and the inherited local water-movement branch through its `move` call. These are exact Mojmap decompiled sources in `decompiled_minecraft/1.21.1/mojmap/` and `decompiled_minecraft/1.21.2/mojmap/`; both matching files were checked against their source manifests. This is an operation finding, not full-pair coverage or an intermediate-version boundary.

1.21.1 has the rooted `mojmap.ready.json` (`status: ready`, source count 5,363), with its source-manifest SHA-256 matching `mojmap.sources.sha256`; its ready marker also pins the artifact and diagnostics manifests. 1.21.2 has the embedded `.decompile-provenance.json` in the Mojmap source root, containing exact source hashes, input hashes and tool/runtime identity. No separate rooted ready marker was present for 1.21.2. The provenance manifest SHA-256 is recorded below. All cited source-byte hashes below match the corresponding recorded manifest entries.

## Difference

**The swim correction formula and its above-fluid gates do not change. `Player.travel` does not write pitch; its direct write here is the Y component of delta movement, using pitch-derived look Y. The authoritative pitch setter does change.** In 1.21.1, `Entity.setXRot(float)` stores every finite input unchanged. In 1.21.2, it stores `Math.clamp(input % 360.0F, -90.0F, 90.0F)`. Consequently, for ordinary pitch values already in `[-90, 90]`, the angle feeding swim correction is unchanged; for an out-of-range finite pitch that reaches this setter, the 1.21.2 look-vector Y and resulting swim vertical correction can differ. This setter difference is the only identified pitch-source delta in this bounded path.

`Player.travel` in 1.21.1 first gates the correction with `isSwimming() && !isPassenger()`; 1.21.2 first routes passengers to `super.travel`, then runs the correction under `isSwimming()`. For the assigned nonpassenger path, both compute `double lookY = getLookAngle().y`, choose `0.085` when `lookY < -0.2` and otherwise `0.06`, then apply the same short-circuit condition: `lookY <= 0.0 || jumping || fluid-at-(x, y + 0.9, z) is nonempty`. When admitted, both add `(lookY - currentVelocityY) * factor` to velocity Y before calling inherited travel. The block-state-fluid query in 1.21.1 was replaced by `Level.getFluidState` in 1.21.2; `Level.getFluidState` has the same out-of-build-height empty result and otherwise returns `getChunkAt(pos).getFluidState(pos)` in both releases, so this call-site spelling change does not alter the gate.

`Entity.getLookAngle()` uses `calculateViewVector(getXRot(), getYRot())`. That method converts pitch to radians in `float` (`pitch * (float)(Math.PI / 180.0)`), computes the lookup-table cosine/sine values, and returns Y as unary-negated float sine widened into `Vec3`'s double components. Both `Mth` versions use a 65,536-element float sine table initialized in ascending index order by `(float)Math.sin(index * Math.PI * 2.0 / 65536.0)`. `sin(float)` truncates `(int)(angle * 10430.378F)` then masks with `65535`; `cos(float)` adds `16384.0F` before the same cast and mask. Table width, initialization expression/order, float casts, scale, index conversion and mask are unchanged.

The direct correction precedes inherited `LivingEntity.travel`. For a locally controlled player in water, both releases pass the current velocity into `moveRelative` and then call `move(MoverType.SELF, getDeltaMovement())` before applying water drag and fluid-falling adjustment. 1.21.2 extracts this into `travelInFluid`; the water movement order and call to actual inherited movement remain the same. This description excludes the separate flight velocity handling.

## Exact source references

- **1.21.1, Mojmap:** `Player.java`, `Player.travel(Vec3)`, lines 1456–1478; `Entity.java`, `calculateViewVector`, lines 1530–1537, `getLookAngle`, lines 2099–2101, `setXRot`, lines 3363–3370; `Mth.java`, lines 28–54; `LivingEntity.java`, `travel(Vec3)`, lines 2091–2136; `Level.java`, `getFluidState`, lines 354–361.
- **1.21.2, Mojmap:** `Player.java`, `Player.travel(Vec3)`, lines 1445–1471; `Entity.java`, `calculateViewVector`, lines 1619–1626, `getLookAngle`, lines 2192–2194, `setXRot`, lines 3489–3497; `Mth.java`, lines 28–54; `LivingEntity.java`, `travel(Vec3)` and `travelInFluid(Vec3)`, lines 2178–2261; `Level.java`, `getFluidState`, lines 347–354.

## Source byte identities

SHA-256 of exact source bytes, checked against manifests:

| Source | 1.21.1 | 1.21.2 |
| --- | --- | --- |
| `net/minecraft/world/entity/player/Player.java` | `ed32b88c3c7c8418b83db41823520f2b6b0b49a98610306ef26e9681dc925c71` | `a803203e92aa4729d5f5c9b16085b6a43ce51d9907d309eb96736e9c7c1340de` |
| `net/minecraft/world/entity/Entity.java` | `b81905c7879e2cc5c5063a41d865c4164ad919f156306705be791d1017b99850` | `a93719c302a0381a972af75ea360465e2e3551708dd07c34d4d40b7e5173c2b9` |
| `net/minecraft/util/Mth.java` | `aa40272f0f980438ca7e8c874ceedc664feb77958b162d7aa3ed01f233bd2deb` | `98dfb57e239348cf71bf00695b84299232bd5e0cfe206ee8c23a69b72f1099da` |
| `net/minecraft/world/entity/LivingEntity.java` | `324a3eee8496caab57cfaf5101ef576f1ae3c60c40e3857e96f35f3af9a3a0d8` | `087390495f4fdfd14b9e12230e7aea4fdc50913bb4d1d91119e72892881cfc52` |
| `net/minecraft/world/level/Level.java` | `f698b412b7e849f5076ae268f7819247b623afc905b8d59de9eb60b414e11e96` | `ff4d57a3df0b850ba02dc798c6aa6734174790b9fd17673261f33f03d44821e1` |

Readiness/provenance manifest byte hashes: 1.21.1 `mojmap.ready.json` `988b38ac59491f9430d5bd1968db0fdaacb777a63fef745ca3e20342237a0310`; `mojmap.provenance.json` `6f84a596ea944a1d40c79b53ead9d2cdd2b3c726d710a977758d15b5578ce7a9`; `mojmap.sources.sha256` `900f956e00f6fc1300bb3d689ea49df2b1a57bcaa54617344ef456d95d47cb48`; 1.21.2 `.decompile-provenance.json` `00f399407dc447737ea83e3e46562418f8c712f66e3f51cb85d46afa2ecac575`.