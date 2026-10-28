# Independent review: lava-presence neutral candidate

## Verdict

**ACCEPT for product integration within the bounded 1.16/1.16.1 local-client lava-presence operation.** This accepts the candidate contract only. It does not review product wiring, establish runtime parity, or freeze the full 1.16.1→1.16.2 pair.

## Bound inputs

- Candidate `.native-candidates/AF-lava-presence-20261028/candidate.md`, SHA-256 `6c9f2803458d19385097007a42e0abe2562fbeac2f8cd68f11058a113db307a4`.
- Source report `.native-source/20261028/lava-presence-client-consumers/REPORT.md`, SHA-256 `d3bbd6c106a0051396aca17aa0269e17fc217542a44f07656ada428a99454b05`.
- Independent source audit `.native-source/20261028/lava-presence-client-consumers-audit/audit.md`, SHA-256 `02e6604f2c3115247747c43eb06c0fe041fd017a6f4cbc88b10442b4b1466b6e`.

All three files were rehashed from the workspace and match the supplied identities.

## Review findings

The candidate accurately represents the old operation as a per-player boolean latch. It resets after collision and fall-damage handling at `Entity.move`, before `checkInsideBlocks`; the scan uses the old `0.001` inset and only calls block callbacks after `hasChunksAt` admits the inclusive block-position range. A rejected scan leaves the just-reset result false. The callback sets true only for a lava-tagged fluid satisfying the source surface predicate. The candidate correctly preserves the exact disjunction (or its valid ordered-AABB reduction), does not clear the latch on later non-lava callbacks, and retains the result until the next movement reset.

The contract keeps the new 1.16.2+ `!firstTick && fluidHeight[LAVA] > 0` native path active, and separates it from the independent water-first current/depth behavior, eye cache, damage/fire, and server behavior. It also retains the source report's binary-provenance and alias-identity caveats without broadening the release or runtime claims.

Product integration must still establish the actual reset/observe/read call sites and preserve the exact callback phase and bounds. This review does not establish that those mixin seams are available or correctly wired.
