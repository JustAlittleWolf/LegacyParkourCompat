# Final author review: 1.21.10 → 1.21.11

## Binding check

The supplied author comparison SHA-256 matches `c61fe6e6b511f740e5582ca315b367b07cf5f0842e3c27086aeccc47e78c184f`; the unchanged blind freeze matches `a8e0615da46f246a9e8ebcf51a29a6069e61501ad8f2d5ad6ba01b680d3d2ef0`. Both bind the exact 1.21.10 and 1.21.11 releases under Mojmap. The author comparison’s HoneyBlock, WebBlock, SweetBerryBushBlock, and Entity file hashes match the corresponding entries in the frozen evidence.

## Verdict within frozen scope

The author’s bounded no-difference conclusion is supported for Honey slide Y/horizontal-velocity/fall-reset behavior and the Web/Berry stuck-multiplier writers followed by the first ordinary non-PISTON SELF move. The source order described by the author agrees with the frozen trace: `LivingEntity.aiStep` moves before applying block effects; the direct block callbacks write afterward; the next ordinary SELF move consumes the stored multiplier at `Entity.move` entry. No wider player-movement equivalence is accepted.

## Residuals outside the freeze

The author comparison adds claims not covered by the frozen audit: exact provenance-run/toolchain and client.jar-entry verification; Web’s Weaving branch; movement/axis traversal and `onInsideBlock` ordering beyond the direct callback point; and `Player.makeStuckInBlock` flying-guard/impulse-context behavior, including its `Player.java` hashes. These claims remain unreviewed here and are not needed for the bounded verdict. This review does not infer them from other source files or hashes.