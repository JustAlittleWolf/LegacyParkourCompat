# Independent source verdict: corrected revision r1

## Input identity

Both authorized immutable r1 inputs matched their supplied SHA-256 values and are UTF-8/LF:

- `C:\Users\Wolfi\.codex\visualizations\2026\11\17\01a1eb7b-ac27-7712-9259-3041950bc340\lane-contact-path-order\comparison.corrected-r1.md` — `1d7a7942dae79ecad38b6043880025c4aee865f39d04abb3746e4821d47f1203`
- `C:\Users\Wolfi\.codex\visualizations\2026\11\17\01a1eb7b-ac27-7712-9259-3041950bc340\lane-contact-path-order\findings\1.21.1--1.21.2\inside-block-path-order.corrected-r1.md` — `1c41a4ef0adc38e313b8b5eee40f09adc7b849b2022e79fbf82c88b508285a0d`

The prior source roster and verdict were left unchanged. The added `Player.java` witness file hash also matches the 1.21.2 in-tree provenance source map: `a803203e92aa4729d5f5c9b16085b6a43ce51d9907d309eb96736e9c7c1340de`.

## Amendment verdicts

### Exact float cutoff and DDA cap — ACCEPT

The revised cutoff is exact. `Mth.square(float)` returns a float product, and `Vec3.lengthSqr()` returns the double sum of coordinate squares. For the call `Mth.square(0.99999F)`, the binary32 result is `0x3F7FFEB0`, exactly `0.99997997283935546875`. Since `boxTraverseBlocks` returns only final-box cells for `lengthSqr() < threshold`, equality and larger values enter swept traversal. The corresponding displacement magnitude is approximately `0.99998998637`.

The revised 17-cell limit is also exact: `addCollisionsAlongTravel` evaluates `if (counter++ > 16) break` before clipping the current cube. Counter values 0 through 16 reach the clip; the next iteration breaks first. Thus at most 17 cells reach the clip and entity-extent enumeration. The described three-block COBWEB segment stays well within this limit. Candidate insertion order, extent loops, set de-duplication, and appending the final AABB cells match the exact 1.21.2 source.

### Vertical COBWEB witness — ACCEPT

The revised recurrence and coordinates are correct for the stated ordinary free-fall assumptions. `Player.createAttributes()` extends `LivingEntity.createLivingAttributes()`, which includes `Attributes.GRAVITY`; the default is 0.08. In `travelInAir`, with no status override, no collision, no flying, and ordinary friction, the requested movement uses current vertical velocity, then updates it using the double gravity subtraction followed by multiplication with `0.98F` widened to double.

Iterating that exact recurrence from zero for 72 updates gives `v72 = -3.004701526459729` and cumulative displacement `-132.00505506000846`. Starting at height `133.10505506000845` leaves the player at feet `y=1.1` with that velocity. A following move ends at feet `-1.904701526459729` and head `-0.104701526459729`; both endpoint boxes are outside COBWEB cell `(0,0,0)`, while the swept standing body crosses it. The swept candidate traversal admits the cell, and COBWEB’s entity-inside shape is exactly `Shapes.block()`, which skips the additional shape test. The `noCollission()` property permits the fall. The prior final-box-only callback does not include that cell at either endpoint. This establishes source-level reachability; it does not establish runtime parity.

### Conditional timing and next-move consumption — ACCEPT

The corrected finding distinguishes general same-tick possibility from the ordinary LocalPlayer route. Old in-move callback timing can feed another `move` in that tick only if the calling path invokes `move` again. The reviewed steady LocalPlayer path does not establish such a second move: `moveTowardsClosestSpace` only changes velocity, and travel has one ordinary movement call before the post-travel callback. The callback’s multiplier is therefore consumed by the next travel move on the following tick. This is accurate and avoids extending the LocalPlayer witness to unproven same-tick consumption.

The cycle-tail description also matches the source: 1.21.2 traverses the live list and clears it afterward; 1.21.4 snapshots then clears before callbacks. Ordinary client LocalPlayer has no vanilla caller recording extra movement segments.

### Provenance qualification — ACCEPT

The revised wording is accurate. 1.21 and 1.21.2 have in-tree `.decompile-provenance.json`; the other three do not. For 1.21.1, 1.21.3, and 1.21.4 the exact root-level ready, provenance, and source-hash files are present, their hashes match the revised table, ready markers identify the exact version and bind the source-manifest digest, and cited source hashes match the indexes. Calling only the hidden file absent avoids implying that no provenance records exist. The caveat that root sidecars are not the complete hidden input/source manifest remains appropriate.

## Remaining bounds

The finding is accepted for its bounded 1.21.1 → 1.21.2 source claim. Keep the 1.21.4 boundary-sensitive and multi-segment/pose caveats; do not claim universal candidate-set equality across the endpoint-offset refactor. The exact introduction remains bounded by the named release pair. This is not full-pair coverage, a runtime result, or a finer implementation cutoff.

No tests, builds, runtime, network, decompilation, or product/Git writes were performed.