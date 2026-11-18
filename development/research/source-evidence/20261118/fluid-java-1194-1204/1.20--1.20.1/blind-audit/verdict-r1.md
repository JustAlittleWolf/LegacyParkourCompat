# Independent review: corrected LocalPlayer eye-fluid order

## Bound inputs

- Corrected author artifact: `source/comparison-r1.md`; supplied and observed raw SHA-256 `8de7634ad761856243c90f1935d91872080a25034b885f9f20231e3d3e14ad8d` match (8,622 bytes, UTF-8 without BOM, LF).
- Frozen roster SHA-256 remains `9a7d7b9afd9b8107664a21c2004fc8d60f610692c26ea3f6aa63ccb822dca082`; prior verdict SHA-256 remains `c9b1c0b25292ac5877023137768f0c0bf9f46c98788783a9ea708dc252c53a52`. Both were observed unchanged. This review adds only a verdict; prior files remain untouched.
- Checked the corresponding LocalPlayer, Player and Entity source files in both authorized Mojmap roots. Their respective SHA-256 digests are identical across 1.20 and 1.20.1 and match the frozen source identities.

## Verdict

**ACCEPT — corrected LocalPlayer producer/reader order.** The revised paragraph now follows the actual dispatch. `Player.tick` invokes virtual `updateIsUnderwater()` before `super.tick()` (`Player.java:254-255`). On the local player, the override captures the prior `wasUnderwater`, invokes `Player.updateIsUnderwater()` which assigns the field from `isEyeInFluid(WATER)`, then handles the spectator and sound cases (`LocalPlayer.java:1064-1082; Player.java:313-316`). `LocalPlayer.isUnderWater()` reads `wasUnderwater` directly (`LocalPlayer.java:1059-1062`), so the paragraph correctly identifies this as the local-player predicate and distinguishes it from the generic `Entity.isUnderWater()` implementation.

The revised order is also accurate after `super.tick()` reaches `Entity.baseTick`: body-fluid contact/pushing runs before `updateFluidOnEyes`, then swimming updates (`Entity.java:434-436`). The eye writer copies the existing eye-water membership into `wasEyeInWater` before clearing `fluidOnEyes`, resamples at `eyeY - 0.11111111F`, and adds fluid tags only when sampled fluid top is strictly above the sample (`Entity.java:1200-1212`). Thus `Player.updateIsUnderwater()` reads the prior eye-tag cache before the entity eye writer refreshes that cache for the next tick. This fixes the generic-predicate error identified in the prior verdict; the earlier rejection is superseded for this corrected paragraph.

## Residual dependencies

Acceptance is limited to the corrected source-order claim and its paired code equivalence. It assumes identical eye-fluid tags, sampled fluid state/height and tick inputs; those runtime/data inputs were not compared. `LocalPlayer.isUnderWater()` consumes the cached eye result without the generic body-water test, as the corrected paragraph states. This does not establish runtime parity or an intermediate patch boundary.