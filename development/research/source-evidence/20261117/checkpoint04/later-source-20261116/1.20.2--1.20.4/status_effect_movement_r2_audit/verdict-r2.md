# Independent audit: movement status-effect paths, r2

Verdict: **ACCEPT**

Reviewed report: `build/later-source-20261116/1.20.2--1.20.4/status_effect_movement_revision_r2/coverage-r2.md`  
Reviewed report SHA-256: `7528412AE87243293DE7C785C4A76E6424A3FD1FDC891D72B1587D880F529446`

## Corrected evidence check

The prior r1 audit (`08D25D0F5BA3010687A059F22BD1B854E82FC282E63DB3A7F10F8E947DCAAD61`) verified the endpoint identities, cited file hashes, and paired operation comparisons; it requested only that the report cite the direct amplifier formula implementation. The r2 diff changes only that evidence paragraph.

The added source entry is `net/minecraft/world/effect/MobEffect.java`, SHA-256 `FE3D18C51352A1E84CF96BCF6386FD19B4FA6BF6D7BD667B813699FAB57EFAE5` at both 1.20.2 and 1.20.4. Local bytes match the respective `mojmap.sources.sha256` entries. At each endpoint, `MobEffectAttributeModifierTemplate.create(int)` is at lines 140–142; line 141 constructs the `AttributeModifier` using `this.amount * ($$0 + 1)` and the declared operation. This directly supports the corrected formula anchor.

## Reused scope verdict

No other report content changed. The unchanged endpoint identity bindings and the other six Java file checks, plus the previously compared method bodies and bounded no-difference conclusion, are reused from the r1 audit. The corrected claim remains limited to the examined effects and direct movement consumers. Potion/effect production, fluid movement/Dolphin's Grace, Elytra eligibility, broader attributes, and pair-wide closure remain open.
