# Independent audit verdict: local input modifiers r2

Reviewed candidate: `build/later-source-20261116/26.1.2--26.2/local_input_modifiers_revision_r2/report-r2.md`

Candidate SHA-256: `AA9F88113F03E44A39695B07635EFB2D2A1E6C70CC6254D1FE0E60633E2E212C`

## Verdict

**Pass for the bounded endpoint no-difference claim and its stated sequencing.** The report is bound to the exact candidate revision above. This verdict does not accept a finding, establish an intermediate onset, or close the pair.

## Independent evidence checks

- Both release sidecar sets identify the exact endpoints and `unobfuscated` namespace. Ready markers and provenance agree on version/mapping; both declare Vineflower and Java 25.
- The actual `unobfuscated.sources.sha256` digests match the ready markers: `26.1.2` `54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0`; `26.2` `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894`. The ready-declared `artifacts.sha256` digests also match: `89f648229de83b0109460695c9f1f3bed7ef36efb84ede9224e80cce87118f92` and `ba9dc53a41bca9744ec1c8ab2ce9363764a22333c39ef64d5105f4e3bf5c98ba` respectively.
- Independently recomputed all six cited Java file hashes and matched each to its source-manifest entry.
- The compared `LocalPlayer` methods preserve the same active-use slowdown predicate, item speed multiplier source, passenger gate, slow-movement predicate, `0.98F` scaling, optional item-use scaling, float-cast `SNEAKING_SPEED` scaling, square-movement call, and sprint gates/order at both endpoints. `modifyInput` reads `SNEAKING_SPEED` before travel.
- In both endpoints `Player.aiStep` calls `super.aiStep()` before reading `MOVEMENT_SPEED` and calling `setSpeed`. The corresponding `LivingEntity.aiStep` calls `applyInput()` and then performs its travel section before returning, confirming the report's stated input/travel-to-speed-set sequence. Player's attribute builder and saved-ability base assignment also match. Attribute declarations preserve the reported defaults, bounds, and syncability; their line positions shift in 26.2 as reported.

## Gates retained

Effective item/equipment state, `USE_EFFECTS` component values/defaults and synchronization into `useItem`, and sources/synchronization for effective `SNEAKING_SPEED` remain open dependencies, as the candidate states. Other movement slices are outside this bounded assignment. No pair freeze, implementation acceptance, or runtime-parity conclusion follows.
