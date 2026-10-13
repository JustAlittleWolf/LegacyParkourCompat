# Chest mutable collision bounds

1.8.9, ornithe-feather; feather-gen2-1.8.9+build.2-mergedv2.jar, feather-gen2-1.8.9+build.2.tiny: `1.8.9/ornithe-feather/net/minecraft/block/ChestBlock.java`, `public void updateShape(WorldView world, BlockPos pos) {`, lines 56-68.
1.9.4, ornithe-feather; feather-gen2-1.9.4+build.2-mergedv2.jar, feather-gen2-1.9.4+build.2.tiny: `1.9.4/ornithe-feather/net/minecraft/block/ChestBlock.java`, `public Box getShape(BlockState state, WorldView world, BlockPos pos) {`, lines 63-73.

Difference: 1.8.9 ChestBlock.updateShape writes shared singleton bounds from north/south/west/east neighbor precedence. Generic collision dispatch consumes those bounds. 1.9.4 resolves adjacency at the queried chest position.

Movement meaning: Conditional on a latest north-neighbor writer and a later isolated chest query, the preserved isolated airborne witness clips dz=0.4 to 0.2 versus 0.2625. Chest and anvil writers are independent providers.


## Current evidence and remaining work

Source decisions, implementation coverage and runtime parity are separate. Source paths are relative to `decompiled_minecraft/`; immutable Git evidence can be read with `git show <revision>:<path>`.

```json
{
  "implementation": {
    "status": "open",
    "changes": [],
    "hooks": [],
    "limits": [
      "Complete current Change/catalog inventory inspected; no dedicated operation implements WORLD-25: mutable-bound writers and Anvil/Chest collision consumers; later broadly related hooks do not establish this claimed delta."
    ]
  },
  "applicability": [],
  "sources": [
    {
      "version": "1.8.9",
      "mapping": "ornithe-feather; feather-gen2-1.8.9+build.2-mergedv2.jar, feather-gen2-1.8.9+build.2.tiny",
      "path": "1.8.9/ornithe-feather/net/minecraft/block/AnvilBlock.java",
      "member": "public void updateShape(WorldView world, BlockPos pos) {",
      "lines": "67-74",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 7e52c4a86c29548d8dc491fe7d4ff17a79c1dc9f426f538a6c19e161f99f013e; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 8fdb28e7e4f6114af3baf2520b4b765c5a7f93017202c2f614c03615f54f3a11."
    },
    {
      "version": "1.8.9",
      "mapping": "ornithe-feather; feather-gen2-1.8.9+build.2-mergedv2.jar, feather-gen2-1.8.9+build.2.tiny",
      "path": "1.8.9/ornithe-feather/net/minecraft/block/AnvilBlock.java",
      "member": "AnvilBlock#getShape",
      "lines": null,
      "status": "different",
      "detail": "Checked complete canonical file SHA-256 7e52c4a86c29548d8dc491fe7d4ff17a79c1dc9f426f538a6c19e161f99f013e (matches an archived source-file digest); named declaration absent. Named declaration is absent in this checked canonical file; paired existing declaration/range is retained separately, so this is an explicit addition/removal or mapping correspondence limit, not an invented line."
    },
    {
      "version": "1.8.9",
      "mapping": "ornithe-feather; feather-gen2-1.8.9+build.2-mergedv2.jar, feather-gen2-1.8.9+build.2.tiny",
      "path": "1.8.9/ornithe-feather/net/minecraft/block/ChestBlock.java",
      "member": "public void updateShape(WorldView world, BlockPos pos) {",
      "lines": "56-68",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 8072bc317398297d1327a3be4edd8a66381a1a1021a6c4a8f3fbeebca2891d51; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 94c903b8e80d5cf6d785fdee87aceb2076d91811af361556c15fe34c66a54dc1."
    },
    {
      "version": "1.8.9",
      "mapping": "ornithe-feather; feather-gen2-1.8.9+build.2-mergedv2.jar, feather-gen2-1.8.9+build.2.tiny",
      "path": "1.8.9/ornithe-feather/net/minecraft/block/ChestBlock.java",
      "member": "ChestBlock#getShape",
      "lines": null,
      "status": "different",
      "detail": "Checked complete canonical file SHA-256 8072bc317398297d1327a3be4edd8a66381a1a1021a6c4a8f3fbeebca2891d51 (matches an archived source-file digest); named declaration absent. Named declaration is absent in this checked canonical file; paired existing declaration/range is retained separately, so this is an explicit addition/removal or mapping correspondence limit, not an invented line."
    },
    {
      "version": "1.8.9",
      "mapping": "ornithe-feather; feather-gen2-1.8.9+build.2-mergedv2.jar, feather-gen2-1.8.9+build.2.tiny",
      "path": "1.8.9/ornithe-feather/net/minecraft/block/Block.java",
      "member": "public Box getCollisionShape(World world, BlockPos pos, BlockState state) {",
      "lines": "346-350",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 ea10f05106a3cf7189aec85236a7ecf9c7106a717f37bed583b5ea4adeffa528; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 f71ce97f89d471ebaa8b0ab8d3fe1f42c1cfc4893972d27a16963b2ca05fa694."
    },
    {
      "version": "1.9.4",
      "mapping": "ornithe-feather; feather-gen2-1.9.4+build.2-mergedv2.jar, feather-gen2-1.9.4+build.2.tiny",
      "path": "1.9.4/ornithe-feather/net/minecraft/block/AnvilBlock.java",
      "member": "AnvilBlock#updateShape",
      "lines": null,
      "status": "different",
      "detail": "Checked complete canonical file SHA-256 ec6435b45c9a091b969ae2870839f2f7b1f3924658989226f8fdd23fe4320e0a (matches an archived source-file digest); named declaration absent. Named declaration is absent in this checked canonical file; paired existing declaration/range is retained separately, so this is an explicit addition/removal or mapping correspondence limit, not an invented line."
    },
    {
      "version": "1.9.4",
      "mapping": "ornithe-feather; feather-gen2-1.9.4+build.2-mergedv2.jar, feather-gen2-1.9.4+build.2.tiny",
      "path": "1.9.4/ornithe-feather/net/minecraft/block/AnvilBlock.java",
      "member": "public Box getShape(BlockState state, WorldView world, BlockPos pos) {",
      "lines": "98-101",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 ec6435b45c9a091b969ae2870839f2f7b1f3924658989226f8fdd23fe4320e0a; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 0c39364e0f20314e29c6a33012f4d649aa6c5fce150fb7189da4bf7ab288b58e."
    },
    {
      "version": "1.9.4",
      "mapping": "ornithe-feather; feather-gen2-1.9.4+build.2-mergedv2.jar, feather-gen2-1.9.4+build.2.tiny",
      "path": "1.9.4/ornithe-feather/net/minecraft/block/ChestBlock.java",
      "member": "ChestBlock#updateShape",
      "lines": null,
      "status": "different",
      "detail": "Checked complete canonical file SHA-256 0fc74197f3f4710268944bb802a1621dc8ee359c29289ec8ce878ad0b2b834ce (matches an archived source-file digest); named declaration absent. Named declaration is absent in this checked canonical file; paired existing declaration/range is retained separately, so this is an explicit addition/removal or mapping correspondence limit, not an invented line."
    },
    {
      "version": "1.9.4",
      "mapping": "ornithe-feather; feather-gen2-1.9.4+build.2-mergedv2.jar, feather-gen2-1.9.4+build.2.tiny",
      "path": "1.9.4/ornithe-feather/net/minecraft/block/ChestBlock.java",
      "member": "public Box getShape(BlockState state, WorldView world, BlockPos pos) {",
      "lines": "63-73",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 0fc74197f3f4710268944bb802a1621dc8ee359c29289ec8ce878ad0b2b834ce; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 06fe8f76b41f8392368068a03155312958c819de855da3b160262a856e5733e1."
    },
    {
      "version": "1.9.4",
      "mapping": "ornithe-feather; feather-gen2-1.9.4+build.2-mergedv2.jar, feather-gen2-1.9.4+build.2.tiny",
      "path": "1.9.4/ornithe-feather/net/minecraft/block/Block.java",
      "member": "public Box getCollisionShape(BlockState state, World world, BlockPos pos) {",
      "lines": "352-354",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 e62ece80c6a7e7121346f65f8fdfd9b148c29441a27de9f568bba9afe1d84fe6; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 ba2470b733895ec6989b2972ae2c0bdbd9a6b0bf0d88eaba8dd9ea4fc9d3920f."
    }
  ],
  "sourceReviews": [
    {
      "verdict": "[{'section': 'Independent source review: WORLD-25 Chest clipped-displacement witness', 'verdict': ['- **Decision:** ACCEPT — the conditional Chest clipped-displacement witness only.'], 'snapshot_hash_matches': True}]",
      "assertion_preserved": true,
      "detail": "Archived independent report contains the exact original digest. Section-specific verdict remains bound to that snapshot; later corrections in this report supersede its earlier sections.",
      "identity": "development/research/archive/legacy-workflows/source-campaign-2026-10-07/1.8.9--1.9.4/reviews/WORLD-25-chest-clip-independent-2026-10-09.md"
    }
  ],
  "openIssues": [
    {
      "missing": "Later applicability gate: exact introduction of campaign-WORLD-25-mutable-bound-writers-anvil-chest is unknown within (1.8.9, 1.9.4]",
      "next_action": "Preserve any exact historical verdict for its bounded assertion; verify intervening releases separately before assigning a new implementation range. Formatting alone requires no renewed acceptance."
    },
    {
      "missing": "Current implementation of campaign-WORLD-25-mutable-bound-writers-anvil-chest",
      "next_action": "After expert scope review and independent source acceptance, assign this specific operation to the separate implementation phase."
    },
    {
      "missing": "Exact independent source verdict/snapshot identity for WORLD-23-inherited-state-shape-providers",
      "next_action": "Retain original source-confirmed status only; source-acceptance owner must resolve a preserved independent verdict or review this exact bounded assertion in the later campaign."
    }
  ],
  "runtime": "unverified"
}
```
