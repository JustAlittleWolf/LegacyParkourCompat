# 26.1.1–26.1.2 Player swim-pitch vertical adjustment

## Scope and result

Compared the bounded operation `Player.travel(Vec3)` swim-pitch contribution to player Y velocity for an ordinary nonpassenger player, including its pitch-derived input and inherited travel/move path. **No source difference is present in this slice**: `Player.java`, `Entity.java`, `LivingEntity.java`, and `Mth.java` have the same SHA-256 in both releases. This is a no-difference coverage result, not a finding of a changed mechanic.

## Exact source readiness and provenance

Canonical readable source roots: `decompiled_minecraft/26.1.1/unobfuscated/` and `decompiled_minecraft/26.1.2/unobfuscated/`. Both adjacent `unobfuscated.ready.json` markers report `status=ready`, the exact version ID, mapping `unobfuscated`, and 6,882 source files. Their source-manifest hashes match the corresponding `unobfuscated.sources.sha256` files.

- 26.1.1 marker SHA-256 `29540fe56da79d19a9b125a091cf89cdb45e4493e4c5898c677658dca1474802`; provenance SHA-256 `9df9595443d56779d505511f5441bb360cca71aafcc570bc16ea8e61a1d847bf`; manifest SHA-256 `88afdfb8264842713f8cce86c0bf88e95c2c991ee706349d6ed8b6c22d52fcb3`; client SHA-256 `8c109be6177f40d10d0afd820b06542f9609597874242b96e16c5b0dc9e1fef3`.
- 26.1.2 marker SHA-256 `f24af0f4d38543c2bd9c19b50f347d4e89d6027050e4b21ae070734843946bb7`; provenance SHA-256 `8d727f880cc91f4a297e148c5b75976303d051457334bc689268fbdf631a012a`; manifest SHA-256 `54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0`; client SHA-256 `b1b3158572666445eff01e82fad8c7de2e4953db6d354f311730d77a8359d0b0`.

The 26.1.1 provenance records its version metadata and exact run; the 26.1.2 provenance records its exact ID and run metadata. Both identify published unobfuscated sources (no mapping file). Provenance source roots refer to the preparation output under the ignored `build/movement-campaign-2026-10-07/ready/` tree; the evidence was consumed read-only from the canonical published version roots above. The ready markers and current source-file bytes were checked against their manifests.

## Paired source trace

- `26.1.1`, unobfuscated, `net/minecraft/world/entity/player/Player.java`, `Player.travel(Vec3)`, lines 1381–1401; `26.1.2`, same file/member/range. The bodies are byte-identical (SHA-256 `44cf28e0c64e78d39fd13368e9991381dbebab67029070cb9ddc43f09d45d14d`). A passenger immediately takes `super.travel(input)` and bypasses this player-owned adjustment. A nonpassenger enters the adjustment only when `isSwimming()` is true. It reads `double lookAngleY = getLookAngle().y`; selects multiplier `0.085` when `lookAngleY < -0.2`, otherwise `0.06`; and adds Y velocity `(lookAngleY - movement.y) * multiplier` only if `lookAngleY <= 0.0`, `jumping`, or the fluid state at `BlockPos.containing(x, y + 1.0 - 0.1, z)` is nonempty. The add is made before `super.travel(input)`. Afterward, flying players restore Y to the pre-super Y times `0.6`; ordinary swimming does not take that flying restoration path.
- `Entity.java`, `Entity.getLookAngle()`, lines 2512–2514, is identical in both releases (SHA-256 `8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf`). It passes the current `getXRot()` and `getYRot()` to `calculateViewVector`, so the swim input uses current pitch, not an interpolated partial-tick pitch. `calculateViewVector`, lines 1879–1887, computes `realXRot = xRot * (float)(Math.PI / 180.0)`, `realYRot = -yRot * (float)(Math.PI / 180.0)`, then `ySin`, `xCos`, `xSin` through `Mth`; the returned Y is `-xSin` (therefore pitch sine with this exact float conversion and LUT path).
- `Mth.java`, static SIN initialization and `sin`/`cos`, lines 31–54, is identical in both releases (SHA-256 `19d00e9745368c34692d0dfd7eddec07797790ac7f6e0bc579208b7a4b682d33`). Its table is 65,536 floats, initialized for indices 0 through 65,535 as `(float)Math.sin(i / 10430.378350470453)`. `sin(i)` indexes `SIN[(int)((long)(i * 10430.378350470453) & 65535L)]`; `cos(i)` adds `16384.0` before the long conversion and same 16-bit mask. Thus both conversion/truncation order and table quantization are unchanged.
- `LivingEntity.java`, `travel(Vec3)` and `travelInFluid`/`travelInWater`, lines 2392–2399 and 2455–2491, is identical in both releases (SHA-256 `c3b64de8dbaba8ad8a7ccd4f33255346d8206e66f12ca91260971bf5e5ac93bd`). The `super.travel(input)` dispatches to fluid travel when `shouldTravelInFluid` holds; its water path calls `travelInWater`, which eventually invokes `move(MoverType.SELF, getDeltaMovement())` after `moveRelative`. `Entity.move(MoverType, Vec3)`, line 704 onward, is inherited by the player and performs actual entity movement/collision. The preceding swim-pitch Y addition therefore feeds the inherited movement path when the fluid branch is selected.

## Source byte evidence

For each release, all four current Java-file hashes exactly matched entries in that version's source manifest. The per-file hashes are the same on both sides:

| Source file | SHA-256 |
|---|---|
| `net/minecraft/world/entity/player/Player.java` | `44cf28e0c64e78d39fd13368e9991381dbebab67029070cb9ddc43f09d45d14d` |
| `net/minecraft/world/entity/Entity.java` | `8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf` |
| `net/minecraft/world/entity/LivingEntity.java` | `c3b64de8dbaba8ad8a7ccd4f33255346d8206e66f12ca91260971bf5e5ac93bd` |
| `net/minecraft/util/Mth.java` | `19d00e9745368c34692d0dfd7eddec07790ac7f6e0bc579208b7a4b682d33` |

## Coverage boundary

Covered the player override gates and operation order, current pitch-to-look-Y conversion, Mth lookup-table procedure, inherited LivingEntity travel dispatch, and the actual inherited Entity movement call. This establishes no source change for this exact operation between 26.1.1 and 26.1.2; it does not establish behavior for other movement slices or the exact introduction boundary in intermediate releases.
