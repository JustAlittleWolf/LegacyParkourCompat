# Independent product review: lava-presence height correction

## Verdict

**ACCEPT** for commit `3e02c4d4c4cebb204a71b29dc9aecf9919a5107f` on `fix/lava-presence-height-admission-20261029`, compared with base `29ddf7be`. No functional findings remain within the bounded height-admission correction.

## Evidence

The supplied source report, original audit, height supplement, height audit, and prior product review were rebound from the primary workspace. Their SHA-256 values match the assigned identities:

- `REPORT.md`: `d3bbd6c106a0051396aca17aa0269e17fc217542a44f07656ada428a99454b05`
- Original audit: `02e6604f2c3115247747c43eb06c0fe041fd017a6f4cbc88b10442b4b1466b6e`
- Height supplement: `27c29dec0ffd6425419f7d083786e2d7e220279f1f9f6d3e9f3b933847aab545`
- Height audit: `f5f5afad29984679c2fee51eeab9127dbd1b9ef318f7f005979fd10e11151cf6`
- Prior product review: `482be22eb923d03d162b728f8af48e17ff6ba7f4bd9744b58f0183ba6fa55854`

## Review findings

- The old `hasChunksAt` height admission is represented by the correct overlap domain `[0,256)`: reject only when the inclusive maximum Y is below 0 or the minimum Y is at least 256.
- The original box inset, floored inclusive min/max positions, and x/y/z traversal order remain. Partially out-of-range scans preserve those bounds and skip only Y outside `[0,256)`, where the old `Level.getBlockState` path returns `VOID_AIR` and cannot produce lava.
- A wholly out-of-range box returns after the latch was cleared, matching the old admission failure result. No current client readiness rejection remains; the source correction establishes `ClientLevel.hasChunk=true`. For a cache miss in an in-range cell, the client empty-chunk path returns `VOID_AIR`.
- Lava type checks, `float` surface calculation, and the predicate against the original unshrunk entity box remain unchanged.
- The local-client and latch reset/sticky lifecycle guards remain unchanged. `@MovementChange(emulates = ParkourVersion.V1_16)` and the provider boundary are unchanged.
- The final commit and the base-to-tip diff modify only `src/client/java/me/wolfii/legacyparkourcompat/change/v1_16/LavaPresenceLatch.java`. The additional final-tip change is explanatory source commentary.

No build, tests, game launch, or runtime check was run, per assignment.
