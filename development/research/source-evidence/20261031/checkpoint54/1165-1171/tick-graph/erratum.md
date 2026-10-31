# Erratum: Jump Boost precision boundary

This erratum supersedes only the Jump Boost boundary statements in `inventory.md`. The original inventory remains unchanged at SHA-256 `004508c02730a54c4a3e7bc55f4e430799daa4fe854c00713601edb13311226b`.

## Exact three-version evidence

- 1.16.5 Mojmap `net/minecraft/world/entity/LivingEntity.java`, `getJumpPower()` and `jumpFromGround()`, lines 1878–1889: `getJumpPower()` returns `float`; `jumpFromGround()` assigns it to `float f`, conditionally adds `0.1F * (amplifier + 1)` into that float, then writes `f` as Y delta movement.
- 1.17 Mojmap `net/minecraft/world/entity/LivingEntity.java`, the same members, lines 1963–1974: the float return, float accumulator, conditional float addition, and float velocity write are retained. The sprint-angle field access changes to `getYRot()` but does not alter the jump-boost sum.
- 1.17.1 Mojmap `net/minecraft/world/entity/LivingEntity.java`, `getJumpPower()`, new `getJumpBoostPower()`, and `jumpFromGround()`, lines 1967–1978: `getJumpPower()` remains float, `getJumpBoostPower()` returns the float-derived product as double, and `jumpFromGround()` adds the two in `double d` before writing Y delta movement as double.

## Correct boundary and cause

The Jump Boost addition-precision change is bounded to 1.17 → 1.17.1 among these inspected releases. There is no such change from 1.16.5 to 1.17: both perform float accumulation and write the float result. A grounded player jump with Jump Boost reaches this inherited operation through `Player.jumpFromGround()` and the existing `LivingEntity.aiStep()` jump gate; the reachable effect is therefore at the 1.17.1 endpoint, not 1.17.

The original inventory misclassified an endpoint difference as an earlier adjacent-release change. The prior endpoint report established 1.16.5-versus-1.17.1 arithmetic but did not establish the 1.17 intermediate body. I incorrectly carried the endpoint delta across the first interval instead of checking the 1.17 method before assigning its boundary. The exact intermediate check resolves that classification error.

This correction is limited to the Jump Boost claim and its release boundary. It does not renew or broaden any other inventory conclusion, pair coverage, source acceptance, or runtime-parity claim.
