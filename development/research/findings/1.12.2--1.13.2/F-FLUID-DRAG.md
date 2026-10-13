# F-FLUID-DRAG — 1.13.2 replaces material drag sampling with fluid-state flow sampling

1.12.2, ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny: `1.12.2/ornithe-feather/net/minecraft/entity/Entity.java`, `public boolean checkWaterCollisions() {`, lines 948-964.
1.12.2, ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny: `1.12.2/ornithe-feather/net/minecraft/world/World.java`, `public boolean applyLiquidDrag(Box bounds, Material material, Entity entity) {`, lines 1579-1621.
1.13.2, ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny: `1.13.2/ornithe-feather/net/minecraft/entity/Entity.java`, `private void m_03231680() {`, lines 947-951.
1.13.2, ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny: `1.13.2/ornithe-feather/net/minecraft/entity/Entity.java`, `public boolean m_69693160(Tag<Fluid> tag) {`, lines 2509-2571.

Difference: 1.12.2 delegates water contact/drag to the World material callback. 1.13.2 samples fluid states intersecting the contracted player box, accumulates fluid flow vectors and adds `0.014`-scaled flow components to velocity; it stores maximum fluid depth for other movement gates.

Movement meaning: - Reachability and dependencies: Entity fluid check is inherited by the player base tick and feeds water contact plus F-WATER-STATE.

This is not a block-state historical emulation claim.


## Current evidence and remaining work

Source decisions, implementation coverage and runtime parity are separate. Source paths are relative to `decompiled_minecraft/`; immutable Git evidence can be read with `git show <revision>:<path>`.

```json
{
  "implementation": {
    "status": "open",
    "changes": [],
    "hooks": [],
    "limits": [
      "Complete current Change/catalog inventory inspected; no dedicated operation implements F-FLUID-DRAG — 1.13.2 replaces material drag sampling with fluid-state flow sampling; later broadly related hooks do not establish this claimed delta."
    ]
  },
  "applicability": [],
  "sources": [
    {
      "version": "1.12.2",
      "mapping": "ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny",
      "path": "1.12.2/ornithe-feather/net/minecraft/entity/Entity.java",
      "member": "public boolean checkWaterCollisions() {",
      "lines": "948-964",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 3e7ee90879d783b73ccca1552c5170f3d19b675894f8b7a835a6b4c6e8ee2e64."
    },
    {
      "version": "1.12.2",
      "mapping": "ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny",
      "path": "1.12.2/ornithe-feather/net/minecraft/entity/Entity.java",
      "member": "Entity#m_03231680",
      "lines": null,
      "status": "different",
      "detail": "Checked complete canonical file SHA-256 80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a (matches an archived source-file digest); named declaration absent. Named declaration is absent in this checked canonical file; paired existing declaration/range is retained separately, so this is an explicit addition/removal or mapping correspondence limit, not an invented line."
    },
    {
      "version": "1.12.2",
      "mapping": "ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny",
      "path": "1.12.2/ornithe-feather/net/minecraft/entity/Entity.java",
      "member": "Entity#m_69693160",
      "lines": null,
      "status": "different",
      "detail": "Checked complete canonical file SHA-256 80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a (matches an archived source-file digest); named declaration absent. Named declaration is absent in this checked canonical file; paired existing declaration/range is retained separately, so this is an explicit addition/removal or mapping correspondence limit, not an invented line."
    },
    {
      "version": "1.12.2",
      "mapping": "ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny",
      "path": "1.12.2/ornithe-feather/net/minecraft/world/World.java",
      "member": "public boolean applyLiquidDrag(Box bounds, Material material, Entity entity) {",
      "lines": "1579-1621",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 e9fa9b8d6d31ad57a5b876f5f63a5e3c554a23437decf1845a493daf48233594; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 cd88e40697c0a23fa1e7ce9f0da2bb8dc88ded478b2092df23fdadef9f0b4f1e."
    },
    {
      "version": "1.13.2",
      "mapping": "ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny",
      "path": "1.13.2/ornithe-feather/net/minecraft/entity/Entity.java",
      "member": "Entity#checkWaterCollisions",
      "lines": null,
      "status": "different",
      "detail": "Checked complete canonical file SHA-256 1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269 (matches an archived source-file digest); named declaration absent. Named declaration is absent in this checked canonical file; paired existing declaration/range is retained separately, so this is an explicit addition/removal or mapping correspondence limit, not an invented line."
    },
    {
      "version": "1.13.2",
      "mapping": "ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny",
      "path": "1.13.2/ornithe-feather/net/minecraft/entity/Entity.java",
      "member": "private void m_03231680() {",
      "lines": "947-951",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 62eb055a426f7c53078564e48b79204d59c9b9bd082fde6246cb4c5e6ff37b31."
    },
    {
      "version": "1.13.2",
      "mapping": "ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny",
      "path": "1.13.2/ornithe-feather/net/minecraft/entity/Entity.java",
      "member": "public boolean m_69693160(Tag<Fluid> tag) {",
      "lines": "2509-2571",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 c354fc9dfd269c48bf3aedc76773d4a756608f639b2753f762b2613d9703c3ef."
    },
    {
      "version": "1.13.2",
      "mapping": "ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny",
      "path": "1.13.2/ornithe-feather/net/minecraft/world/World.java",
      "member": "World#applyLiquidDrag",
      "lines": null,
      "status": "different",
      "detail": "Checked complete canonical file SHA-256 0023de30da608db73c6c6188334d2bb19039d86fe4d6f5e4ad79d732a5d2cd1a (not bound to a source-file digest in this record); named declaration absent. Named declaration is absent in this checked canonical file; paired existing declaration/range is retained separately, so this is an explicit addition/removal or mapping correspondence limit, not an invented line."
    }
  ],
  "sourceReviews": [],
  "openIssues": [
    {
      "missing": "Later applicability gate: exact introduction of campaign-F-FLUID-DRAG is unknown within (1.12.2, 1.13.2]",
      "next_action": "Preserve any exact historical verdict for its bounded assertion; verify intervening releases separately before assigning a new implementation range. Formatting alone requires no renewed acceptance."
    },
    {
      "missing": "Current implementation of campaign-F-FLUID-DRAG",
      "next_action": "After expert scope review and independent source acceptance, assign this specific operation to the separate implementation phase."
    },
    {
      "missing": "Exact independent source verdict/snapshot identity for F-FLUID-DRAG",
      "next_action": "Retain original source-confirmed status only; source-acceptance owner must resolve a preserved independent verdict or review this exact bounded assertion in the later campaign."
    }
  ],
  "runtime": "unverified"
}
```
