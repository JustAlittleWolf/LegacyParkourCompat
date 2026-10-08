# TICK-01 independent review — 2026-10-08

## Snapshot identity

- Owner finding snapshot: commit `1dbafbb7899c57ddf1718e421aa2c32e283fc7c7`, path `workflows/source-campaign-2026-10-07/1.8.9--1.9.4/findings/TICK-01-sprint-timeout.md`, SHA-256 `f7fb9e8aaf8685c65a2af1ce23c43f9d52e44393280c4047a5fd4d6cfc0788d5`.
- Owner event: no immutable finding snapshot event exists for TICK-01 at the reviewed owner tip. The owner run remains partial; this review does not infer pair completeness.
- Source publication: immutable Feather-r1-2026-10-07 pair; manifest and artifact identities were checked in the campaign. Source files: A `LocalClientPlayerEntity.java` SHA-256 `1762b116e6b06d682b7daaa0fc8cce39b0ff455b3db8cf79dab03ac74f6c4053`; B SHA-256 `8aaf711948b7602c2e6c015a37e36ed06073d39727d999d80480b4910b704f5d`.

## Decision: accept, bounded to the timer difference

The paired source supports the stated timer change. In 1.8.9, `setSprinting(true)` sets `sprintTimer` to 600; `mobTick` decrements a positive timer and calls `setSprinting(false)` at zero. In 1.9.4, `setSprinting` writes zero and `mobTick` only increments the field. The full LocalClientPlayerEntity sources contain no B-side read/timeout consumer of the incremented timer.

The 1.8.9 and 1.9.4 local sprint gates both separately stop sprinting when forward input is below `0.8F`, horizontal collision is present, or food level is not above 6 and flight is unavailable. Other entry gates include item-use and blindness checks. Thus, conditional on sprint remaining otherwise eligible (including no separate player/combat action calling `setSprinting(false)`), B permits the sprint flag to persist beyond A's 600-tick timeout; A ends it when its timer expires. This is not a claim that sprint always persists or that a measured trajectory differs.

`LivingEntity.setSprinting` installs/removes the sprint movement-speed modifier in both versions. The player `moveRelative` flight path also multiplies flight speed by two while `isSprinting()` in both versions. Sprint-jump adds a horizontal velocity impulse while sprinting in both versions. These establish direct movement consumers of the flag; they do not expand this finding to other sprint mechanics.

## Limits

- Accept only the timer lifetime delta and its conditional flag-persistence consequence.
- First changed release remains unknown within `(1.8.9, 1.9.4]`.
- No runtime validation, displacement measurement, mod integration, or pair-completeness conclusion is made.
- Health/food production and non-player motion remain outside scope.
