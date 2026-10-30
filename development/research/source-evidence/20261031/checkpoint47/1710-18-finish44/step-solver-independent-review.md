# Independent review: 1.7.10–1.8 step solver witness

**ACCEPT** the bounded swept-clearance step-solver claim in `canonical-findings/1.7.10--1.8/step-solver-swept-clearance-candidate.md`.

I retraced the paired `Entity.move` step implementations, collision math, snow-layer provider, and ordinary local-player dimensions. In the witness, the grounded downward request clips at the floor and horizontal travel clips to 0.01 at the snow layer, so both shared step gates pass. The old 0.5F single candidate has its horizontal movement clipped by the overhead block to the same 0.01 and restores ordinary movement on the tie. In 1.8, candidate A's horizontally expanded clearance box reaches the roof and limits the rise to 0.2; after rising, its top touches the roof face, which the strict non-overlap comparisons permit, so it traverses 0.10 and descends onto the snow. Candidate B has a 0.6F rise, collides with the roof during horizontal movement, and moves only 0.01; candidate A therefore wins by squared horizontal distance. The witness distinguishes the candidate solver from the separate step-height change: candidate A requires only 0.2 rise.

The paired `Box.expanded`, `intersectY`, and `intersectX` comparisons support the stated intervals. The two-layer snow shapes agree at Y=1..1.125. The local player uses the standard 0.6F width, 1.8F height, positive step-height and `Entity.move` path; the old `immovable` guard remains true for this instance, so it does not reset the clipping results. This is a source-derived witness, not runtime parity. The stair direction branch check in `stair/stair-solver-witness.md` is bounded and separate from this acceptance.

No pair-wide inventory freeze or intermediate patch boundary is claimed.
