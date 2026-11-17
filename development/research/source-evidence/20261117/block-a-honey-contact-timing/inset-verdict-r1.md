# Independent source-review verdict — corrected r1

## Immutable handoff verification

Both offered revisions match the stated SHA-256 and are UTF-8 without BOM, with LF line endings:

- `comparison.corrected-r1.md` — `9c646534f4536d83f22ae4d845a9eb1fb1d747b020a267db8469acae0e12cd94`
- `honey-side-slide-contact-scan-inset.corrected-r1.md` — `afaa1316347cc45d7298e37b39ca215c6dfa431df357077f9feceb569c022f2b`

Review remains bound to frozen roster `roster.json` SHA-256 `731f9af818fd6aafec0ee9f5acd0da5fc3e313eef9bd05f8a627f0aca7e92532`, plus dependency addendum `roster-addendum.json` SHA-256 `9581f402950dd4714f842e417045a0adf0988ea56549609e54214630af601f7f`.

## Verdict

**ACCEPTED.** The corrected revisions accurately state the 1.19.2 → 1.19.3 contact-scan inset change and its Honey side-slide consequence within the bounded source claim. The call path now places `tryCheckInsideBlocks` inside `Entity.move`, after position update, and Honey's callback before the remaining `LivingEntity.travel` gravity/drag work. The geometry and Honey gates are correct. Ordinary input reachability is explicitly labeled a source-level inference and is separated from coordinate placement; no exact input trajectory is claimed.

## Verified movement and numeric details

For Honey at `(0,0,0)`, player center `x=1.2995`, standing width `0.6F`, and half-width `0.30000001192092896F`, `minX` is approximately `0.9994999880790711`. The old bound floors `minX+0.001` to 1; the new bound floors `minX+1.0E-7` to 0. The old `BlockPos(double)` path and 1.19.4 `BlockPos.containing` path both floor coordinates. Honey's `0.9375` collision-face maximum leaves this AABB edge overlap outside its shape.

At the callback, feet y `0.9`, `onGround=false`, and pre-move `vy=-0.2` pass the unchanged Honey gates. The threshold is the double sum of `0.4375` and the float result `0.6F/2.0F`, namely `0.737500011920929`; x-center distance `0.7995` passes. Honey writes `vy=-0.05`, scales x/z by 0.25, and resets fall distance. It does so after the current displacement, so the current position is not retroactively changed.

The stated end-of-tick outputs are correct to the shown decimal precision. With the ordinary airborne branch, `shouldDiscardFriction()==false`, no collision/auto-jump follow-up, no slow falling/levitation/fluid/no-gravity/unloaded-chunk branch, and no fall-flying, the Java float drag literal `0.98F` gives approximately `-0.12740000247955321` after the Honey write and `-0.2744000053405762` without it. The drafts show these rounded as `-0.1274` and `-0.2744`; consider adding `≈` or the full values if preserving strict float-level reporting. The response is a falling/travel velocity and fall-distance change, with position divergence on a later move, not a jump witness.

## Reachability boundary

The ordinary LocalPlayer tick/input/travel/move path and collision-free side gap support the stated source-level inference. The sample coordinates and velocity independently prove only scan admission and Honey-gate behavior. The revised drafts correctly say no exact key sequence, initial state, or displacement trace was verified, so they do not overstate the sample as an input replay.

No product or Git files, wiki inventories, builds, tests, runtime launches, source writes, or network were used. The prior `verdict.md` remains untouched; this verdict is a separate r1 file.