# WORLD-06: liquid-drag scan excludes exact upper-bound cells in 1.9.4

1.8.9, ornithe-feather; feather-gen2-1.8.9+build.2-mergedv2.jar, feather-gen2-1.8.9+build.2.tiny: `1.8.9/ornithe-feather/net/minecraft/world/World.java`, `public boolean applyLiquidDrag(Box bounds, Material material, Entity entity) {`, lines 1486-1527.
1.9.4, ornithe-feather; feather-gen2-1.9.4+build.2-mergedv2.jar, feather-gen2-1.9.4+build.2.tiny: `1.9.4/ornithe-feather/net/minecraft/world/World.java`, `public boolean applyLiquidDrag(Box bounds, Material material, Entity entity) {`, lines 1560-1602.

Difference: 1.8.9 derives each exclusive loop end as `MathHelper.floor(bounds.maxAxis + 1.0)` for X/Y/Z. 1.9.4 derives it as `MathHelper.ceil(bounds.maxAxis)`.

For non-integer maxima these values match; at an exact integer maximum 1.8.9 visits the cell at that coordinate while 1.9.4 excludes it. 1.8.9 and 1.9.4 use the same lower bound `floor(bounds.minAxis)`.

Both then filter candidate blocks by material and the liquid-height test before setting `bl` and invoking `Block.applyMaterialDrag()`; both normalize and add the resulting vector at scale `0.014` when `entity.hasLiquidCollision()`.

The bounding box comes from the player/entity shape grown by `(0.0, -0.4F, 0.0)` and contracted by `0.001` on each axis. 1.8.9 `Box.contract(double,double,double)` adds each amount to minima and subtracts it from maxima; 1.9.4 `Box.contract(double)` delegates to symmetric expansion with the negative amount, so the input bounds otherwise match.

Movement meaning: Local player tick -> inherited `Entity.tick()` -> `checkWaterCollisions()` -> `World.applyLiquidDrag()` -> liquid block's `applyMaterialDrag()`/`getFlow()` -> possible player velocity write.

The normal, non-boat route is present in both endpoints; 1.9.4's boat early branch is documented separately in `WORLD-05`. 1.8.9 player not flying passes `PlayerEntity.hasLiquidCollision()` in both versions.

Concrete boundary precondition: at least one contracted box maximum is an exact integer and the world cell at that coordinate is water with its liquid surface meeting the scan's vertical bound. 1.8.9 scans that cell; 1.9.4 does not.

Source proves the differing upper-bound calculation and the one-cell inclusion rule at exact integer maxima.

If the included cell passes the liquid material/height filter, 1.8.9 can set the water result and invoke its liquid drag callback for a cell 1.9.4 omits.


## Current evidence and remaining work

Source decisions, implementation coverage and runtime parity are separate. Source paths are relative to `decompiled_minecraft/`; immutable Git evidence can be read with `git show <revision>:<path>`.

```json
{
  "implementation": {
    "status": "open",
    "changes": [],
    "hooks": [],
    "limits": [
      "Complete current Change/catalog inventory inspected; no dedicated operation implements WORLD-06: liquid-drag scan excludes exact upper-bound cells in 1.9.4; later broadly related hooks do not establish this claimed delta."
    ]
  },
  "applicability": [],
  "sources": [
    {
      "version": "1.8.9",
      "mapping": "ornithe-feather; feather-gen2-1.8.9+build.2-mergedv2.jar, feather-gen2-1.8.9+build.2.tiny",
      "path": "1.8.9/ornithe-feather/net/minecraft/world/World.java",
      "member": "public boolean applyLiquidDrag(Box bounds, Material material, Entity entity) {",
      "lines": "1486-1527",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 3c04f5b874fbb6692039164c882a3c47fe28c7eca864e91c9ac57798153888ee; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 ee5eb7bd6d99da77cff7ab567899bcf9858f3b63d1b3ac51648c8fdf6db8c7f2."
    },
    {
      "version": "1.9.4",
      "mapping": "ornithe-feather; feather-gen2-1.9.4+build.2-mergedv2.jar, feather-gen2-1.9.4+build.2.tiny",
      "path": "1.9.4/ornithe-feather/net/minecraft/world/World.java",
      "member": "public boolean applyLiquidDrag(Box bounds, Material material, Entity entity) {",
      "lines": "1560-1602",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 2fe063e0ec224eed9fc7d01b8f788c32035eed5fee5a9d98ea5e82296236e05a; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 f6a185036e816fcd73e75cef8e0439bc738869468b93830e19b2e0cbef829f1d."
    }
  ],
  "sourceReviews": [],
  "openIssues": [
    {
      "missing": "Later applicability gate: exact introduction of campaign-WORLD-06-liquid-scan-upper-bound is unknown within (1.8.9, 1.9.4]",
      "next_action": "Preserve any exact historical verdict for its bounded assertion; verify intervening releases separately before assigning a new implementation range. Formatting alone requires no renewed acceptance."
    },
    {
      "missing": "Current implementation of campaign-WORLD-06-liquid-scan-upper-bound",
      "next_action": "After expert scope review and independent source acceptance, assign this specific operation to the separate implementation phase."
    },
    {
      "missing": "Exact independent source verdict/snapshot identity for WORLD-06-liquid-scan-upper-bound",
      "next_action": "Retain original source-confirmed status only; source-acceptance owner must resolve a preserved independent verdict or review this exact bounded assertion in the later campaign."
    }
  ],
  "runtime": "unverified"
}
```
