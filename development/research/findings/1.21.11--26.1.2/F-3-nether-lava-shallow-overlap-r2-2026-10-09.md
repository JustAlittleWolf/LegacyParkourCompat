# F-3 correction snapshot: Shallow-overlap Nether lava current crosses 1.21.11's Player minimum impulse but 26.1.2's tracker cutoff returns

1.21.11, mojmap: `1.21.11/mojmap/net/minecraft/world/entity/Entity.java`, baseTick 501-507; updateFluidHeightAndDoFluidPushing 3509-3582.
26.1.2, unobfuscated: `26.1.2/unobfuscated/net/minecraft/world/entity/Entity.java`, baseTick 522-528; updateFluidInteraction 1558-1583; getFluidInteractionBox 4015-4025.
26.1.2, unobfuscated: `26.1.2/unobfuscated/net/minecraft/world/entity/EntityFluidInteraction.java`, update 32-129; applyCurrentTo 171-189.
1.21.11, mojmap: `1.21.11/mojmap/net/minecraft/world/entity/player/Player.java`, isPushedByFluid 1679-1681.
26.1.2, unobfuscated: `26.1.2/unobfuscated/net/minecraft/world/entity/player/Player.java`, isPushedByFluid 1673-1675.
1.21.11, mojmap: `1.21.11/mojmap/net/minecraft/world/level/material/LavaFluid.java`, getDropOff 164-166.
26.1.2, unobfuscated: `26.1.2/unobfuscated/net/minecraft/world/level/material/LavaFluid.java`, getDropOff 165-167.
1.21.11, mojmap: `1.21.11/mojmap/net/minecraft/data/worldgen/DimensionTypes.java`, bootstrap 81.
1.21.11, mojmap: `1.21.11/mojmap/net/minecraft/world/level/material/FlowingFluid.java`, getNewLiquid 160-195; getFlow 55-100.
26.1.2, unobfuscated: `26.1.2/unobfuscated/net/minecraft/world/level/material/FlowingFluid.java`, getNewLiquid 161-195; getFlow 55-100.

Difference: Define float h=1.0F/9.0F, then double top=64.0D+(double)h and playerY=top-0.003D, with a standing unmounted non-flying Player at X/Z (100.5,100.5) and zero horizontal velocity. In a loaded Nether side-walled source-fed lava row, level 1 at (100,64,100) and east level 2 generate (-1,0,0). 1.21.11 uses float blockY+height before widening and overlap from the 0.001-deflated box; its scaled current magnitude is 1.4023730099168574E-5, above Vec3's floor. 26.1.2 measures 0.0030000000000001137 from the raw minimum and rejects squared accumulated current 9.000000000000683E-6 < 1.0E-5F before averaging/scaling/minimum impulse.

Movement meaning: The same exact double coordinate is used on both sides. 1.21.11's Player branch adds the minimum westward impulse 0.0045000000000000005; 26.1.2 adds no LAVA current. This preserves the reviewed R2 arithmetic correction. Other lava heights/vectors, mounted boxes and first changed release remain outside the bounded assertion.


## Current evidence and remaining work

Source decisions, implementation coverage and runtime parity are separate. Source paths are relative to `decompiled_minecraft/`; immutable Git evidence can be read with `git show <revision>:<path>`.

```json
{
  "implementation": {
    "status": "partial",
    "changes": [
      "src/main/java/me/wolfii/legacyparkourcompat/change/v1_21_11/ShallowLavaCurrentCutoff.java"
    ],
    "hooks": [
      "ShallowLavaCurrentCutoffHook"
    ],
    "limits": [
      "ShallowLavaCurrentCutoffHook exists; corrected mounted-scope review is retained. Exact old float-top/deflated overlap arithmetic and endpoint applicability need independent fidelity review. Registration establishes static correspondence only; no runtime parity or renewed applicability acceptance."
    ]
  },
  "applicability": [],
  "sources": [
    {
      "version": "1.21.11",
      "mapping": "mojmap",
      "path": "1.21.11/mojmap/net/minecraft/world/entity/Entity.java",
      "member": "baseTick",
      "lines": "501-507",
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
      "lines": "32-129",
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
      "path": "1.21.11/mojmap/net/minecraft/world/level/material/LavaFluid.java",
      "member": "getDropOff",
      "lines": "164-166",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 8fabbb9a75d7620963f7a44141280bb08e0363ece7a425fcd37334e422af5abb; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 517799a8485e107e932dc1bd27c002b2d0b9207eb2396b685bcfe6c3321a9fbd *1.21.11/client_mappings.txt. Canonical source-manifest SHA-256 0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "26.1.2",
      "mapping": "unobfuscated",
      "path": "26.1.2/unobfuscated/net/minecraft/world/level/material/LavaFluid.java",
      "member": "getDropOff",
      "lines": "165-167",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 423ff44a57c316f58c21a66903a4ef2c1b9c3d26316bdc4d301727575f93703f; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Canonical source-manifest SHA-256 54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.11",
      "mapping": "mojmap",
      "path": "1.21.11/mojmap/net/minecraft/data/worldgen/DimensionTypes.java",
      "member": "bootstrap",
      "lines": "81",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 532319fc7694da6007767e3ce521e5e7ea3a7926c1e782eba132caf03cd202d3; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 517799a8485e107e932dc1bd27c002b2d0b9207eb2396b685bcfe6c3321a9fbd *1.21.11/client_mappings.txt. Canonical source-manifest SHA-256 0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555 matches archived publication; cited file hash is present in that manifest."
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
      "lines": "161-195",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 f8c0c4430e81efdf39b221968272ed8e3a4d030fa5192e04e04d34c9fbd37b28; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Canonical source-manifest SHA-256 54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.11",
      "mapping": "mojmap",
      "path": "1.21.11/mojmap/net/minecraft/world/level/material/FluidState.java",
      "member": "getHeight",
      "lines": "53-55",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 146a5e6a19a93da631a6f4ed57020791bde31087fcedc6d52c68b1c582b71b0f; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 517799a8485e107e932dc1bd27c002b2d0b9207eb2396b685bcfe6c3321a9fbd *1.21.11/client_mappings.txt. Canonical source-manifest SHA-256 0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "26.1.2",
      "mapping": "unobfuscated",
      "path": "26.1.2/unobfuscated/net/minecraft/world/level/material/FluidState.java",
      "member": "getHeight",
      "lines": "49-51",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 e35608d5775013c67653ea350309b5150a97f36aa65c27f1c6247ae2aa840f18; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Canonical source-manifest SHA-256 54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0 matches archived publication; cited file hash is present in that manifest."
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
      "version": "26.1.2",
      "mapping": "unobfuscated",
      "path": "26.1.2/unobfuscated/net/minecraft/world/level/material/FlowingFluid.java",
      "member": "getFlow",
      "lines": "55-100",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 f8c0c4430e81efdf39b221968272ed8e3a4d030fa5192e04e04d34c9fbd37b28; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Canonical source-manifest SHA-256 54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.11",
      "mapping": "mojmap",
      "path": "1.21.11/mojmap/net/minecraft/world/phys/Vec3.java",
      "member": "normalize",
      "lines": "80-83",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 a4050765738a0cfdb1100b83f2ba4effd40155de99bb703a8381dbe775148ec1; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 517799a8485e107e932dc1bd27c002b2d0b9207eb2396b685bcfe6c3321a9fbd *1.21.11/client_mappings.txt. Canonical source-manifest SHA-256 0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "26.1.2",
      "mapping": "unobfuscated",
      "path": "26.1.2/unobfuscated/net/minecraft/world/phys/Vec3.java",
      "member": "normalize",
      "lines": "83-86",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 57b08ae818868a4fffdc9b5dadba2b127138c8945c2194f6f4b01e6150cd3bcb; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Canonical source-manifest SHA-256 54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0 matches archived publication; cited file hash is present in that manifest."
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
      "verdict": "ACCEPT corrected exact-coordinate unmounted Nether LAVA witness",
      "assertion_preserved": true,
      "detail": "Decision preserved only for named revision, bounded witness and exclusions.",
      "snapshot": {
        "commit": "b2b493a2521b036bca2fa590feeda3194c49bd4c",
        "path": "4a5eae32b5819418dbd59eb4a763ee24ea1f2c52:development/research/archive/legacy-workflows/source-campaign-2026-10-07/1.21.11--26.1.2/findings/F-3-nether-lava-shallow-overlap-r2-2026-10-09.md",
        "sha256": "69146a6130e72d7fffdf4bfa71954c41c62dc57aebfa0246f040e079c5f28907"
      },
      "review": {
        "path": "4a5eae32b5819418dbd59eb4a763ee24ea1f2c52:development/research/archive/legacy-workflows/source-boundary-reviews/2026-10-09-lava-shallow-overlap-r2-review.md",
        "commit": "a76e28419f6b9767eab2e78147883c5459ed60c4",
        "sha256": "c589664e82974cc07b7010a9c4be9c42ef4e334221d1d73b11fe1e4261f7352e"
      }
    }
  ],
  "openIssues": [],
  "runtime": "unverified"
}
```
