# Final author review: 1.21.8–1.21.9 direct contact comparison

## Bound artifacts and pins

Reviewed current author revision `source/comparison-r1.md`, raw SHA-256 `3869ad7451fa1a9d350afbbda6884dd26f9b5e1b17cbe7e25aa68c5fd0320137`, against frozen blind audit `blind-audit/frozen.md`, raw SHA-256 `0de29689dc36cee2c2fbddcbe7a2819a5c894716fa150eb5011256c0e72095b2`. The earlier `source/comparison.md`, SHA-256 `9cb2a578cbf9c1457ce0b78762ffa0522b3a9963a8f1b6143f3787ca2a662cea`, is superseded by r1; its missing 1.21.9 client/versioned-input identity gate is resolved in r1.

All six source classes listed in r1 were independently checked on both sides against the 1.21.8 source manifest and the 1.21.9 provenance `sources` entries, and each local raw SHA-256 matched: `HoneyBlock.java`, `WebBlock.java`, `SweetBerryBushBlock.java`, `Entity.java`, `LivingEntity.java`, and `LocalPlayer.java`. The 1.21.9 embedded provenance raw SHA-256 is `37d3049478585a43ffcace14fc8f2ee814b35ceedd8c9bad8b60adaed9b72c4f`; its inputs pin the exact official `1.21.9.json` metadata, client jar, and client mapping text to the hashes recorded in r1. The provenance lacks a top-level `versionId` and exact-run record, so r1 correctly records exact versioned-input pins while retaining that narrower run-metadata limitation.

## Direct-effect review

The blind verdict agrees with r1 on the three named local effects, with the assumption of equal callback inputs, block state/position, and entity movement state:

- Honey slide gates, reconstructed Y velocity math, strict horizontal-scaling threshold and factor, replacement Y velocity, and fall-distance reset match. Its separate landing callback's damage calculation is excluded.
- `WebBlock` default and WEAVING multipliers, berry bush living/non-FOX/non-BEE gate and multiplier, and `makeStuckInBlock` reset-then-store ordering match.
- For the first ordinary SELF move, the stored multiplier's `lengthSqr() > 1.0E-7` gate, componentwise request multiplication, multiplier clearing, and delta-velocity zeroing occur in the same order before edge adjustment and collision. The SELF path skips the piston limiter. This does not establish equality of the remaining movement pipeline or final trajectory.

The bounded reachability argument is sufficient for these operations: ordinary movement is recorded, `applyEffectsFromBlocks` traverses its recorded path and invokes `entityInside` on an intersected block inside-shape, and the living-entity dry non-flying movement routine supplies an ordinary SELF move. A single ordinary block contact is a concrete reachable witness in each release. The 1.21.9 scan is bounded; this verdict makes no claim about long or complex traversal equivalence, differing upstream trajectories, or complete tick/pair coverage. Excluded areas and the need for broader source/runtime parity remain as stated in r1 and the frozen audit.

## Verdict

**Accept the scoped no-difference result.** The frozen blind audit and current author revision agree on the requested direct effects, and the six source-class hashes and exact 1.21.9 official versioned-input pins check out. Preserve the explicit limits: this verdict does not accept whole-pair coverage, end-of-tick trajectory parity, or runtime parity.