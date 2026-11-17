# Independent source audit verdict: climbing travel slice

Bound report: `build/later-source-20261116/26.1.2--26.2/climbing_travel/report.md`, SHA-256 `492EC581D2F2174F66A01580704E3EADC3847F9BB34A877E363F6B6FCE690A72`.

## Verdict

Accepted for the bounded claim: no movement-semantic change was found between 26.1.2 and 26.2 in the listed climb travel clamps/ordinary-air caller gate, `LivingEntity` climbing predicate and trapdoor ladder predicate, or `Player.onClimbable` override. Pair coverage remains partial. This verdict does not infer an introduction boundary or establish implementation/runtime parity.

## Identity and evidence checks

The report's SHA-256 matches its bound digest. Both ready markers identify `unobfuscated` exact-version source sets and their byte hashes match the report: 26.1.2 ready marker `f24af0f4d38543c2bd9c19b50f347d4e89d6027050e4b21ae070734843946bb7`; 26.2 ready marker `f9406adb6bf7cb4c1ab0792a798ab2cb90e082ad4c2eee032c9f674d0ea8070f`. Provenance file hashes also match: 26.1.2 `8d727f880cc91f4a297e148c5b75976303d051457334bc689268fbdf631a012a`; 26.2 `a328f5333a1b27aadde4422b23159a323ae219ccd26f11207c1a5461366f27d1`. Each marker-declared `unobfuscated.sources.sha256` and `artifacts.sha256` file matches its declared digest. The LivingEntity.java and Player.java payload hashes match their source-manifest entries and the report on both endpoints.

The 26.2 provenance records a summary-excerpt-only success log and no mappingArtifacts object; this does not contradict the exact ready/source identities or the cited source hashes. No source identity mismatch was found.

## Retrace

Compared `net/minecraft/world/entity/LivingEntity.java` and `net/minecraft/world/entity/player/Player.java` in both exact source sets. I retraced the report's cited members and checked surrounding ordinary `travel` branch dispatch:

- `travel` in both releases selects fluid travel first, fall-flying travel second, and `travelInAir` otherwise. The ordinary-air dispatch order and helper call position match. `travelInAir` has other changes (including friction handling in this pair); the report's bounded verdict must not be read as covering all ordinary-air travel behavior.
- `handleRelativeFrictionAndCalculateMovement` calls `moveRelative`, writes `handleOnClimbable(getDeltaMovement())` to delta movement, performs self movement, then checks `(horizontalCollision || jumping) && (onClimbable() || wasInPowderSnow && canEntityWalkOnPowderSnow(this))` before replacing movement Y with `0.2`. This order, gate and value match.
- `handleOnClimbable` calls `onClimbable`, resets fall distance, clamps X and Z with `Mth.clamp(..., -0.15F, 0.15F)`, applies only a lower Y bound with `Math.max(delta.y, -0.15F)`, then may set negative Y to zero under the same scaffolding/shift/player conditions. There is no upper Y clamp in either version. The returned `Vec3` and operation order match. The local `float max = 0.15F` is unused in both versions.
- `LivingEntity.onClimbable` has the same spectator early return, `blockPosition` then `getInBlockState` reads, fall-flying/glide-through rejection, climbable-tag check and `lastClimbablePos` write, trapdoor fallback and false fallback. `trapdoorUsableAsLadder` has the same OPEN check followed by below-position ladder and FACING comparison.
- `isSuppressingSlidingDownLadder` delegates to `isShiftKeyDown` in both versions. `Player.onClimbable` returns false for flying abilities and otherwise delegates to `super.onClimbable` in both.

The declaration of `CLIMBING_VERTICAL_SPEED` in 26.2 is not used by the covered methods; both still use literal `0.2` for the post-move Y replacement.

## Preserved gates and limits

This audit did not inspect or close `getInBlockState`/position semantics, tag and resource contents, OPEN/FACING or neighbor ladder state, scaffolding semantics, powder-snow eligibility, shift-key input provenance, or collision/movement dependencies. These remain open as stated in the report. This is a bounded independent audit only, not full-pair inventory closure.
