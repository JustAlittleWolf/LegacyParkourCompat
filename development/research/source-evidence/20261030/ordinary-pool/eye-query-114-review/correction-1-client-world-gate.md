# Correction 1: client loaded-chunk gate is inert

This supplement corrects the loaded-chunk interpretation in `.native-source/20261030/eye-query-114-closure/source-review.md` (SHA-256 `7784336b9268bb4a156ebee41fd0394affc9c66711f5650d86d3f900cd2e6db4d`) and narrows the conclusion in the independent audit (SHA-256 `f81c0f9f04e9d6c669b28c7ed35632f237b56594fc1a7229f5888afbc50338df`). The point-query branch and its `checkLoaded` argument exist in 1.14.x, but they do not perform a client chunk-residency test: the entity's client world overrides the tested method to return `true` unconditionally.

## Exact client overrides

`Entity.isSubmergedIn(tag, true)` in 1.14.x calls `this.world.isChunkLoaded(blockX >> 4, blockZ >> 4)` before reading the fluid state. `Entity.world` dispatches to the runtime client world for the local-player refresh path. The exact client-world overrides are:

| Release / mapping | Exact source and method | SHA-256 |
|---|---|---|
| 1.14 Yarn | `net/minecraft/client/world/ClientWorld.java:212–214`, `isChunkLoaded(int,int) { return true; }` | `86b4b5451115fde93bc1f6546dd07c93a373b09232870c0721f524216556368f` |
| 1.14.1 Yarn | same path/member/lines | `86b4b5451115fde93bc1f6546dd07c93a373b09232870c0721f524216556368f` |
| 1.14.2 Yarn | same path/member/lines | `86b4b5451115fde93bc1f6546dd07c93a373b09232870c0721f524216556368f` |
| 1.14.3 Yarn | `net/minecraft/client/world/ClientWorld.java:213–215`, `isChunkLoaded(int,int) { return true; }` | `9c72e6f4d142b5ccbe33f4f33e6593705fd7e02755275f0f8a330561463117e2` |
| 1.14.4 Ornithe Feather | `net/minecraft/client/world/ClientWorld.java:222–224`, `isChunkLoaded(int,int) { return true; }` | `c4674cc7c6b322bef6a5a951aa73be2b405de4bc64804fb9b6bb96c1832b1768` |

Each 1.14–1.14.3 file hash matches the corresponding source row in its mapping-root `.decompile-provenance.json`; those provenance SHA-256 values are 1.14 `ea70a955a12f377cd3b9e8d5f346f4625b044d55da726b6bc472405ae91f4341`, 1.14.1 `308537c5adec3104c5ca243eb5010554f00978635ed74f0d7801c804dd4d4907`, 1.14.2 `786546f99684423cc2563be45c9c3334dfcc2c7db58ddb74ba39ce403bd0869a`, and 1.14.3 `81ea0021eb122d4e3340e20d47d6ecb7079365ba767a038d26a8264cddacc28a`. The 1.14.4 hash matches `ornithe-feather.sources.sha256` at the version root. These are exact source-hash checks; missing separate 1.14–1.14.3 ready markers do not block this operation-level verification.

The corresponding 26.2 client override is `net/minecraft/client/multiplayer/ClientLevel.java:517–520`, `hasChunk(int,int) { return true; }`, SHA-256 `f0255138128f19fb354e1a208f457e7fdb2df986f3103ec91f52bcabfa076631`, matching the 26.2 unobfuscated source manifest. This is likewise a stub-style affirmative answer; it is not a real-cache residency result. The modern fluid-interaction route's separate full-chunk/section preflight remains a distinct mechanism and does not convert the old dead check into a real-cache gate.

## Corrected applicability conclusion

For the ordinary local-player query on the logical client, the 1.14.x loaded check always passes through `ClientWorld.isChunkLoaded`. The previously reported absent-adjacent-chunk witness is doubly invalid: its sampled x/z chunk equals the player's chunk, and the selected client override returns `true` regardless. Keep the loaded-check source branch as a historical code detail, but do not claim a chunk-admission or movement difference from it, and do not substitute a real-cache `FULL,false` query for that gate. The contextual-fluid-height difference is independently supported and remains unaffected.

The earlier “public query unguarded, player refresh guarded” description is still accurate about call arguments. Replace “guarded” as an applicability claim with: “the refresh passes `true` to the helper, but ClientWorld makes its `isChunkLoaded` branch unconditionally true.” This is an immutable source correction only; it does not edit the prior report/audit or authorize runtime verification.
