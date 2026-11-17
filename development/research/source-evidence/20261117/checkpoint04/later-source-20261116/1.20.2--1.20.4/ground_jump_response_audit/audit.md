# Independent audit: ground jump response (1.20.2 → 1.20.4)

**Verdict: ACCEPT — bounded NO_DIFF slice.** The report's paired source claims and source identity are supported. This verdict accepts only the named jump response calculation and the explicitly bounded caller/input observations. It does not establish the complete ordinary dry-ground client prediction lifecycle, packaged values, gameplay behavior, or full-pair coverage.

## Bound input

- Report: `build/later-source-20261116/1.20.2--1.20.4/ground_jump_response/coverage.md`
- Report SHA-256: `87F65406A89A983997F36BF2893BC097C8B6E1D70E6998D0482790C699812D97`
- Exact sources: `decompiled_minecraft/1.20.2` and `decompiled_minecraft/1.20.4`, Mojmap.
- Root-sidecar provenance hashes: 1.20.2 `1DAD9F725F6CB25B6568700FA0294F83B66EA9633B0D62ED354D7BD8B45D4F05`; 1.20.4 `10914D9B7B3FC29DE066CA24F7D905E4D4F0A451A6C81729A2D1981ADDFC1385`.
- Root-sidecar ready records identify exact version IDs, `status=ready`, and `mapping=mojmap`. Actual `mojmap.sources.sha256` file hashes match the ready-record declarations: 1.20.2 `e439ff7e1d3f1d31e01be1edd141f94d917a9dc7fa0de5f69904544cbbe62887`; 1.20.4 `fd3c8668483e5ff208c847474f6cbd3952a909c4aa602ed98b24e3d11a9602a1`.

## Audit findings

I checked the cited Java file hashes against current source bytes and their source-manifest entries. All reported hashes match. `Block.java` has no report-listed whole-file hash, but the cited `getJumpFactor()` declarations and source-manifest rows were checked in both versions.

The paired implementations support the report's NO_DIFF conclusion for this slice:

- `LivingEntity.getJumpPower()` uses `0.42F * getBlockJumpFactor() + getJumpBoostPower()` in each version. The JUMP effect term is `0.1F * (amplifier + 1.0F)` when present, otherwise zero.
- `Entity.getBlockJumpFactor()` checks the in-body block factor and the block below affecting movement; it uses the lower factor only when the first factor equals `1.0`. `Block.getJumpFactor()` returns the stored factor.
- `LivingEntity.jumpFromGround()` replaces Y velocity with jump power while preserving X/Z, applies the same sprint yaw-relative `0.2F` horizontal impulse, then sets `hasImpulse`.
- `Player.jumpFromGround()` calls the superclass implementation and awards the jump stat; the additional exhaustion action is outside the movement-vector claim.
- The inspected keyboard/input propagation and server rising-movement packet gate agree with the cited bounds. The server packet condition is explicitly presented as server packet processing, not historical server-side movement.

An independent call-site search found the fluid-affected `LivingEntity.aiStep` caller, the server movement packet caller, and non-player callers. The report accurately limits the `aiStep` gate to the fluid-affected branch and does not claim that it demonstrates the ordinary dry-ground client caller. Its remaining dependencies explicitly include dry-ground local-player prediction, effect/resource values, and concrete block factors.

## Acceptance boundary

Accepted: unchanged paired source formula, source gates/ordering, Player override, and bounded input/server observations as described above.

Not accepted or established: full local-player dry-ground lifecycle, packaged/configured effect or block values, gameplay parity, any excluded domain, or complete pair coverage. This report is one source slice only.
