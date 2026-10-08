# Review candidate: V26_1 BlockLanding catalog accessibility

## Trigger

The accepted central catalog instantiates `me.wolfii.legacyparkourcompat.change.v26_1.BlockLanding` from the `change` package. The existing implementation class and constructor were package-private, so the single accepted-subset integration build stopped at `:compileJava` with an accessibility error at `MovementChangeCatalog.java:334`.

Full failed build log: `D:\Javastuff\LegacyParkourCompat\build\movement-campaign-2026-10-07\integration-artifacts\architecture-2026-10-08\build.full.log`.

## Candidate delta

This candidate changes only the visibility of `BlockLanding` and its constructor to `public`, allowing the statically typed catalog in another package to instantiate the existing implementation. Its state, block IDs, per-bed/slime registration sequence, and movement logic are untouched.

Candidate branch: `fix/architecture-blocklanding-visibility-candidate-2026-10-08`, based on accepted integration branch commit `ae112de6f59a628d9e5a68b2302a526f39e1d3bb`.

This is an unreviewed compile-fix candidate. It has not been merged into the accepted integration branch or local `main`. No build or tests were run after this change; independent review is required before either merge or another build.
