# Independent source verdicts — corrected revision 1

## Immutable input checks

All three corrected payloads matched the supplied SHA-256 before inspection:

- `comparison.corrected-r1.md`: `91e13a3f615831be64a314a018322cc172f82924966f59ea96e1f634ba4b4a80`
- `water-gravity-damping.corrected-r1.md`: `73826e1a8a460ca36027f85fc187a9105147cefd9ed3a6e34f0b2312dda0240d`
- `water-sprint-horizontal-drag.corrected-r1.md`: `25b7d39a9e78da24777d640fdedf14d08b17f0dd750aff81f4dd9553c64478f8`

The frozen source roster and evidence identities remain those recorded in `verdict.md`; no source inputs changed. I checked only the amended claims: player-wrapper gates, the 1.14.4 clamp snapshot, and the exact float outcomes. Relevant source hashes still match the frozen roster.

## Terminal dispositions

### Water vertical gravity, damping, and clamp — ACCEPT

The corrected finding accurately states the 1.12.2 and 1.13 endpoint behavior and operation order. It now scopes the reachable clamp witness to a non-swimming, unmounted local player. In 1.13 `PlayerEntity.moveRelative` adjusts Y only under `isSwimming() && !isRiding()` and then delegates to `LivingEntity.moveRelative` (lines 1435–1458); the specified witness therefore bypasses that pre-adjustment. Its source-evaluated values are correct: pre-move Y `-0.002`, unchanged through collision-free movement, becomes `-0.0016` after `0.8F` damping; with Slow Falling, `d/16.0=0.000625`, satisfying the clamp predicates and assigning `-0.003`.

The corrected 1.14.4 control distinction is also accurate: `LivingEntity.travel` snapshots `bl = getDeltaMovement().y <= 0.0` at entry (line 1785), then uses `bl` in the later clamp (line 1911); 1.13 tests its then-current post-damping `velocityY <= 0.0` (line 1626). The 1.14.4 control supports the shared damping and `d/16.0` arithmetic, not predicate identity. The 1.14 `Player.travel` swim-only Y wrapper is correctly bounded to the wrapper gate and does not invalidate the non-swimming 1.13 witness.

Evidence: 1.12.2 Feather `LivingEntity.java` lines 1555–1584; 1.13 Feather `LivingEntity.java` lines 1461–1465 and 1598–1635; identical 1.13.1/1.13.2 `LivingEntity.java` body at lines 1617–1654; 1.14.4 Mojmap `LivingEntity.java` lines 1782–1789 and 1878–1923; wrapper code as cited above. The `0.8F` multiply widens to double against double Y; gravity literals and clamp comparisons use doubles. First observed changed endpoint is 1.13; no patch-level introduction boundary is claimed within the 1.12.2–1.13 interval.

### Sprint-dependent horizontal drag and dosage — ACCEPT

The corrected finding accurately covers the drag/input formulas, branch gates, and operation order. In 1.12.2, water `f` begins at `0.8F` regardless of sprint; in 1.13 it begins at `0.9F` while sprinting and `0.8F` otherwise, then applies capped/air-halved Depth Strider interpolation, followed by the Dolphin’s Grace override. The separate input scale `g` receives the Depth Strider interpolation with direct `getSpeed()` input. Movement uses `g`; after movement, double X/Z components are multiplied by float `f`. The corrected Feather wrapper note is appropriately limited: its swim adjustment changes Y before delegation and leaves these horizontal formulas unaffected.

The exact values in corrected revision 1 match binary32 evaluation of the source expression in Java operation order:

| Initial `f` | Depth Strider `h` | Result |
|---|---:|---:|
| `0.8F` nonsprinting | `3.0F` ground | `0.54600006F` |
| `0.9F` sprinting | `3.0F` ground | `0.546F` |
| `0.8F` nonsprinting | `1.5F` airborne | `0.67300004F` |
| `0.9F` sprinting | `1.5F` airborne | `0.723F` |

For a post-move double X of `0.1`, widening the float factor gives `0.0800000011920929` with `0.8F` and `0.08999999761581422` with `0.9F`. The corrected distinction between direct inputs and their producers is accurate. The 1.13.1/1.13.2 code is supported by their identical `LivingEntity.java` hashes; 1.14.4 retains the horizontal expressions at Mojmap `LivingEntity.travel` lines 1880–1907. First observed changed endpoint is 1.13; no intermediate patch boundary is asserted.

## Remaining unsupported claims

None in the corrected R1 claims reviewed. The already disclosed 1.14.4 legacy provenance `mappingArtifacts` inconsistency remains an evidence-metadata caveat; R1 describes it as inconsistent and relies on the Mojmap ready/artifact sidecars, so it does not affect these operation verdicts.
