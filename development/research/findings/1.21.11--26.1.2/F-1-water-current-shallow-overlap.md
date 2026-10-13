# F-1: Shallow-overlap water current cutoff can suppress a reachable player impulse

1.21.11, mojmap: `1.21.11/mojmap/net/minecraft/world/entity/Entity.java`, updateInWaterStateAndDoWaterCurrentPushing 1509-1520; updateFluidHeightAndDoFluidPushing 3509-3582.
26.1.2, unobfuscated: `26.1.2/unobfuscated/net/minecraft/world/entity/Entity.java`, baseTick 522-528; updateFluidInteraction 1558-1583; getFluidInteractionBox 4015-4025.
26.1.2, unobfuscated: `26.1.2/unobfuscated/net/minecraft/world/entity/EntityFluidInteraction.java`, update 32-89; applyCurrentTo 171-189.
1.21.11, mojmap: `1.21.11/mojmap/net/minecraft/world/entity/player/Player.java`, isPushedByFluid 1679-1681.
26.1.2, unobfuscated: `26.1.2/unobfuscated/net/minecraft/world/entity/player/Player.java`, isPushedByFluid 1673-1675.
1.21.11, mojmap: `1.21.11/mojmap/net/minecraft/world/level/material/FlowingFluid.java`, getFlow 55-100; getFlow 55-103; getNewLiquid 160-195.
1.21.11, mojmap: `1.21.11/mojmap/net/minecraft/world/level/material/FluidState.java`, getFlow 95-97.
1.21.11, mojmap: `1.21.11/mojmap/net/minecraft/world/level/material/WaterFluid.java`, getDropOff 107.
26.1.2, unobfuscated: `26.1.2/unobfuscated/net/minecraft/world/level/material/FlowingFluid.java`, getNewLiquid 160-195.
1.21.11, mojmap: `1.21.11/mojmap/net/minecraft/world/phys/Vec3.java`, normalize 80-86.

Difference: For an exact current whose scanned overlap is shallow, 1.21.11 scales the one current by overlap measured from the deflated box minimum, averages it, and reaches the Player minimum-impulse branch. 26.1.2 scales the current by tracker height measured from the entity's un-deflated minimum, then rejects the accumulated vector by squared length before averaging or applying the minimum impulse. The conditions are reachable with one flowing-water cell at `(100,64,100)` in LEVEL 1, its east neighbor at `(101,64,100)` in LEVEL 2, and a supported, side-walled horizontal channel extending east through LEVEL 7 to a water source at `(107,64,100)`. The water update path produces each flowing level by subtracting the water drop-off of 1 from the higher neighbor amount. The target's north, south and west neighbors are solid channel walls, so their empty fluid states contribute no fallback flow; the east LEVEL 2 neighbor contributes `1/9F - 2/9F = -1/9F` in X. The final `getFlow()` is therefore `(-1, 0, 0)`. This uses actual vanilla fluid states and the paired vanilla flow calculation, rather than an arbitrary input vector.

Movement meaning: The per-tick path is `Player.tick()` / inherited `Entity.tick()` -> 1.21.11 `updateInWaterStateAndDoFluidPushing()` -> water helper -> `updateFluidHeightAndDoFluidPushing(WATER, 0.014)`, versus 26.1.2 `Entity.tick()` -> `updateFluidInteraction()` -> `EntityFluidInteraction.update()` -> `applyCurrentTo(WATER, this, 0.014)` -> tracker cutoff. Both exact Player overrides permit the path when not flying. Fluid LEVEL states are constructed through the same `FlowingFluid.getFlowing(amount, false)` / state-definition mechanism; the horizontal source-to-level chain is rooted in the exact WaterFluid amount and drop-off methods. The verified player dimensions and box scans give one counted state, while the adjacent level-2 state supplies its nonzero current. The caller and relevant state writes are closed for this case: 26.1.2 reports in-water from positive tracker height, both paths write only the player's delta movement for the current response, 1.21.11 also updates its per-tag fluid-height map, and 26.1.2 updates per-tag height/eye trackers. Player horizontal velocity cutoff reads X/Z only; the vanilla movement producer may supply zero X/Z while the player crosses the fluid surface. No health/food/combat or non-player producer is needed. Exact loaded-chunk/section guards and the water resource tag are included above. Source proves a reachable conditional player delta-velocity difference: 1.21.11 adds a nonzero minimum horizontal impulse in the stated shallow-overlap state, while 26.1.2 does not add the water current. The 1.21.11/26.1.2 pair does not establish the first release that introduced the cutoff.


## Current evidence and remaining work

Source decisions, implementation coverage and runtime parity are separate. Source paths are relative to `decompiled_minecraft/`; immutable Git evidence can be read with `git show <revision>:<path>`.

```json
{
  "implementation": {
    "status": "partial",
    "changes": [
      "src/main/java/me/wolfii/legacyparkourcompat/change/v1_21_11/ShallowWaterCurrentCutoff.java"
    ],
    "hooks": [
      "ShallowWaterCurrentCutoffHook"
    ],
    "limits": [
      "ShallowWaterCurrentCutoffHook bypasses weak-current cutoff for 0<height<0.4. Old overlap scaling/top arithmetic and broader height behavior are not proved by cutoff bypass alone. Registration establishes static correspondence only; no runtime parity or renewed applicability acceptance."
    ]
  },
  "applicability": [],
  "sources": [
    {
      "version": "1.21.11",
      "mapping": "mojmap",
      "path": "1.21.11/mojmap/net/minecraft/world/entity/Entity.java",
      "member": "updateInWaterStateAndDoWaterCurrentPushing",
      "lines": "1509-1520",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 32314478c6036fa9f3f3cc409c61c622eefc5a282d60e1011a1a33cc29cf18a3; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 517799a8485e107e932dc1bd27c002b2d0b9207eb2396b685bcfe6c3321a9fbd *1.21.11/client_mappings.txt. Canonical source-manifest SHA-256 0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "26.1.2",
      "mapping": "unobfuscated",
      "path": "26.1.2/unobfuscated/net/minecraft/world/entity/Entity.java",
      "member": "baseTick",
      "lines": "522-528",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Canonical source-manifest SHA-256 54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "26.1.2",
      "mapping": "unobfuscated",
      "path": "26.1.2/unobfuscated/net/minecraft/world/entity/EntityFluidInteraction.java",
      "member": "update",
      "lines": "32-89",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 5264ff4f1fddebc3fa9d63ad2edbe2eaf617392ff946a867a6817a78b478ee62; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Canonical source-manifest SHA-256 54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.11",
      "mapping": "mojmap",
      "path": "1.21.11/mojmap/net/minecraft/world/entity/player/Player.java",
      "member": "isPushedByFluid",
      "lines": "1679-1681",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 8e97167350a91741d0aa10d3b0d92a33150ed6dccdc94cd5b37d9c7ca22bcc81; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 517799a8485e107e932dc1bd27c002b2d0b9207eb2396b685bcfe6c3321a9fbd *1.21.11/client_mappings.txt. Canonical source-manifest SHA-256 0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.11",
      "mapping": "mojmap",
      "path": "1.21.11/mojmap/net/minecraft/world/level/material/FlowingFluid.java",
      "member": "getFlow",
      "lines": "55-100",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 905e5a02ab10b23b5f6e64a0f732ee552ae8895801e1944d9850a41cc8bce91a; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 517799a8485e107e932dc1bd27c002b2d0b9207eb2396b685bcfe6c3321a9fbd *1.21.11/client_mappings.txt. Canonical source-manifest SHA-256 0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.11",
      "mapping": "mojmap",
      "path": "1.21.11/mojmap/net/minecraft/world/level/material/FlowingFluid.java",
      "member": "getFlow",
      "lines": "55-103",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 905e5a02ab10b23b5f6e64a0f732ee552ae8895801e1944d9850a41cc8bce91a; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 517799a8485e107e932dc1bd27c002b2d0b9207eb2396b685bcfe6c3321a9fbd *1.21.11/client_mappings.txt. Canonical source-manifest SHA-256 0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.11",
      "mapping": "mojmap",
      "path": "1.21.11/mojmap/net/minecraft/world/level/material/FluidState.java",
      "member": "getFlow",
      "lines": "95-97",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 146a5e6a19a93da631a6f4ed57020791bde31087fcedc6d52c68b1c582b71b0f; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 517799a8485e107e932dc1bd27c002b2d0b9207eb2396b685bcfe6c3321a9fbd *1.21.11/client_mappings.txt. Canonical source-manifest SHA-256 0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.11",
      "mapping": "mojmap",
      "path": "1.21.11/mojmap/net/minecraft/world/level/material/WaterFluid.java",
      "member": "getDropOff",
      "lines": "107",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 bcc55994c0a150a975bacd7920c161b09a6d00e3a78af209c924ad03a4b949d6; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 517799a8485e107e932dc1bd27c002b2d0b9207eb2396b685bcfe6c3321a9fbd *1.21.11/client_mappings.txt. Canonical source-manifest SHA-256 0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.11",
      "mapping": "mojmap",
      "path": "1.21.11/mojmap/net/minecraft/world/level/material/FlowingFluid.java",
      "member": "getNewLiquid",
      "lines": "160-195",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 905e5a02ab10b23b5f6e64a0f732ee552ae8895801e1944d9850a41cc8bce91a; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 517799a8485e107e932dc1bd27c002b2d0b9207eb2396b685bcfe6c3321a9fbd *1.21.11/client_mappings.txt. Canonical source-manifest SHA-256 0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "26.1.2",
      "mapping": "unobfuscated",
      "path": "26.1.2/unobfuscated/net/minecraft/world/level/material/FlowingFluid.java",
      "member": "getNewLiquid",
      "lines": "160-195",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 f8c0c4430e81efdf39b221968272ed8e3a4d030fa5192e04e04d34c9fbd37b28; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Canonical source-manifest SHA-256 54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.11",
      "mapping": "mojmap",
      "path": "1.21.11/mojmap/net/minecraft/world/phys/Vec3.java",
      "member": "normalize",
      "lines": "80-86",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 a4050765738a0cfdb1100b83f2ba4effd40155de99bb703a8381dbe775148ec1; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 517799a8485e107e932dc1bd27c002b2d0b9207eb2396b685bcfe6c3321a9fbd *1.21.11/client_mappings.txt. Canonical source-manifest SHA-256 0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.11",
      "mapping": "mojmap",
      "path": "1.21.11/mojmap/net/minecraft/world/entity/EntityType.java",
      "member": "class declaration / field initialization",
      "lines": "1196",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 fc2abb3e905b9a63a27f0b027fad089d9d40e5d3d45608406b5da2a5ec47a73e; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 517799a8485e107e932dc1bd27c002b2d0b9207eb2396b685bcfe6c3321a9fbd *1.21.11/client_mappings.txt. Canonical source-manifest SHA-256 0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "26.1.2",
      "mapping": "unobfuscated",
      "path": "26.1.2/unobfuscated/net/minecraft/world/entity/EntityType.java",
      "member": "class declaration / field initialization",
      "lines": "1196",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 e5f37ee2dd639b7271197e58f7a6f3bdbedc0f0f1d618039d159a0e7de315229; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Canonical source-manifest SHA-256 54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.11",
      "mapping": "mojmap",
      "path": "1.21.11/mojmap/net/minecraft/world/entity/Entity.java",
      "member": "updateFluidHeightAndDoFluidPushing",
      "lines": "3509-3582",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 32314478c6036fa9f3f3cc409c61c622eefc5a282d60e1011a1a33cc29cf18a3; matches an explicit archived source hash. Restored the actual paired fluid operation omitted from migrated call-site-only locations. Official exact client-mapping manifest binding: 517799a8485e107e932dc1bd27c002b2d0b9207eb2396b685bcfe6c3321a9fbd *1.21.11/client_mappings.txt. Canonical source-manifest SHA-256 0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "26.1.2",
      "mapping": "unobfuscated",
      "path": "26.1.2/unobfuscated/net/minecraft/world/entity/Entity.java",
      "member": "updateFluidInteraction",
      "lines": "1558-1583",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf; matches an explicit archived source hash. Restored the actual paired fluid operation omitted from migrated call-site-only locations. Canonical source-manifest SHA-256 54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "26.1.2",
      "mapping": "unobfuscated",
      "path": "26.1.2/unobfuscated/net/minecraft/world/entity/Entity.java",
      "member": "getFluidInteractionBox",
      "lines": "4015-4025",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf; matches an explicit archived source hash. Restored the actual paired fluid operation omitted from migrated call-site-only locations. Canonical source-manifest SHA-256 54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "26.1.2",
      "mapping": "unobfuscated",
      "path": "26.1.2/unobfuscated/net/minecraft/world/entity/EntityFluidInteraction.java",
      "member": "applyCurrentTo",
      "lines": "171-189",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 5264ff4f1fddebc3fa9d63ad2edbe2eaf617392ff946a867a6817a78b478ee62; matches an explicit archived source hash. Restored the actual paired fluid operation omitted from migrated call-site-only locations. Canonical source-manifest SHA-256 54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "26.1.2",
      "mapping": "unobfuscated",
      "path": "26.1.2/unobfuscated/net/minecraft/world/entity/player/Player.java",
      "member": "isPushedByFluid",
      "lines": "1673-1675",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 44cf28e0c64e78d39fd13368e9991381dbebab67029070cb9ddc43f09d45d14d; matches an explicit archived source hash. Restored the actual paired fluid operation omitted from migrated call-site-only locations. Canonical source-manifest SHA-256 54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0 matches archived publication; cited file hash is present in that manifest."
    }
  ],
  "sourceReviews": [
    {
      "verdict": "ACCEPT, finding snapshot only",
      "assertion_preserved": true,
      "detail": "Historical acceptance belongs only to the immutable snapshot and its limits; canonical reformatting is not renewed source acceptance.",
      "snapshot": {
        "commit": "6374ff904018e102afd8353864d3cbcbb1b99ddd",
        "path": "workflows/source-campaign-2026-10-07/1.21.11--26.1.2/findings/F-1-water-current-shallow-overlap.md",
        "gitBlob": "90c8a48282c036e6082c85f0be7a69e041ac0cb7",
        "sha256": "9d2c273a1c3b358eac7ebaa1e4cbe9e91a126648a90e89d8d6bdeb1953159cdd",
        "resolved_commit": "6374ff904018e102afd8353864d3cbcbb1b99ddd",
        "git_blob_sha256": "9d2c273a1c3b358eac7ebaa1e4cbe9e91a126648a90e89d8d6bdeb1953159cdd"
      },
      "review": {
        "commit": "f222615162499441f959899600744813b1276614",
        "path": "workflows/source-campaign-2026-10-07/independent-review-water-current-12111-2612-2026-10-08.md",
        "gitBlob": "bfb5d5477b27238e8079a807de8ba5c21115ba0e",
        "sha256": "3e1b1d42a8de917ae57d3b3496866dec3828cc6378a2dece6f49fc3840b7c538",
        "decision": "ACCEPT, finding snapshot only",
        "git_blob_available": true,
        "git_blob_sha256": "3e1b1d42a8de917ae57d3b3496866dec3828cc6378a2dece6f49fc3840b7c538"
      }
    }
  ],
  "openIssues": [],
  "runtime": "unverified"
}
```
