# Independent source verdict: marginal water admission

**Verdict: ACCEPT** for the bounded claim that an ordinary non-flying local player can be admitted to 1.13/1.13.1/1.13.2 water travel from water in an adjacent body-scan cell while the feet cell is a dry, open, north-facing trapdoor over a same-facing ladder. The fixture's concrete geometry and ordering are source-reachable in all three releases. No blocking source issue remains for this admission dependency.

This verdict does not establish optional clearance correction success, sustained water propagation, a multi-tick approach/trajectory, or the already-reviewed water-travel climb-impulse behavior.

## Immutable evidence inputs

- Frozen fixture: commit `bec48f6c1d8371645699061895c2f753cc795087`, `legacy-fluid/marginal-climb-water-admission/admission.md`, raw SHA-256 `3837bc51f392464b9d1824f3f23f306efc05f07535fdb3c91266db0b8479f325`.
- Allowed prior finding: `development/research/findings/1.13.2--1.14.4/water-travel-climb-impulse.md`, raw SHA-256 `5432c7e3271a7ffe5005ad08cbed8126855b15b33736aadda4d218580ff7f1f2`.
- Allowed prior verdict: `development/research/source-evidence/20261117/block-a-water-climb/verdict.md`, raw SHA-256 `b686587f91a610d954c2d7896fc41b72e937bd1131e5ba146d8708bcce6ace9c`.
- Blind source roster was frozen before opening the fixture at `legacy-fluid/marginal-climb-admission-audit/roster.md`, raw SHA-256 `7839ad9a3ba24a7e9fc609d5a6c9ae236b46b00437fcf3cd03e5a1a73468a677`.

## Exact source identities

Canonical read-only source root: `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/<release>/ornithe-feather/`. Each ready marker names the matching exact release and `ornithe-feather` mapping; its source-manifest hash equals the actual manifest file hash. Every cited source file hash checked against its per-release manifest.

| Release | Feather mapping artifact | Source-manifest SHA-256 | Ready-marker SHA-256 | Provenance SHA-256 |
|---|---|---|---|---|
| 1.13 | `feather-gen2-1.13+build.2` | `0509c2614b0bb0e616e2ebc6382f8eb5d031b5d86b834a4a6ed7fd96b6d68f1a` | `82a4a8f6a03fbd99a7b8577b1ae5980d95af07c7ded0da284106d16eb1e01a99` | `34652aeef6914b5cbff6619ae60a855aa676b2ae1f607eccdaa623ad09dc5e8d` |
| 1.13.1 | `feather-gen2-1.13.1+build.2` | `21770b01ff3bf443db2e9e1b6df7db25d2fd6b7a72a86228259269c932d3abba` | `f098b3a08b37276cc1a465a8a122df8de76d68786c854b92bac1ca51a79670de` | `c28ed59f2c507b6133f7677742d316d38e9993eaf1fbb6c2054bd68d1085e904` |
| 1.13.2 | `feather-gen2-1.13.2+build.2` | `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211` | `1d1c644c1deb05c02d11dc575ad834f4f72489124701d9b2b90357f43dc37b38` | `e25950b7855fc4f2aee1a662f0be6b0af9f28788d9e27293075cfc5a3aa5d383` |

The 1.13.2 source tree additionally has `verification.json` status `verified-identical-source-and-input-artifacts`; it records 2,711/2,711 source files verified, zero source differences, and zero missing published sources. Its one derived remap archive difference is explicitly distinguished from the unchanged published source tree.

## Paired source audit

- `Entity.tick` reaches `baseTick`; `baseTick` refreshes water state before living/player travel (`Entity.java:332–394, 946–975`; 1.13.1/1.13.2 corresponding lines shift by one). The refresh sets `inWater` from the fluid-tag scan. `LivingEntity.moveRelative` routes into its water branch when `isInWater()` is true unless a player is flying (`LivingEntity.java:1467` in 1.13, `:1486` in 1.13.1/1.13.2). The ordinary player delegates to `super.moveRelative` outside the flying branch (`PlayerEntity.java:1451–1464` in 1.13; `:1454–1464` in later releases).
- The body scan contracts the entity AABB by `0.001`, floors minima, ceils maxima, gates on loaded area, and loops each integer cell in those bounds (`Entity.java:2507–2535`; 1.13.1/1.13.2 `:2509–2537`). It accepts a tagged fluid when `cellY + fluidState.getHeight() >= contractedBox.minY`; the cell need not equal the feet cell. `FluidState.getHeight` delegates to `Fluid.getHeight`; `FlowingFluid.getHeight` returns `state.getLevel() / 9.0F`, and source water level is 8.
- Numeric check: the standing player width is `0.6F`; `Entity.setPosition` first computes `float halfWidth = width / 2.0F`, then widens it in the double AABB arithmetic (`Entity.java:309–315`; `PlayerEntity.java:448–451`). At `(0.9,64.0,0.32)`, the uncontracted Z minimum is `0.01999998807907105`; max Z is `0.620000011920929`. The open north trapdoor collision slab begins at Z `13/16 = 0.8125` (`TrapdoorBlock.java:30–60`), leaving a positive gap. The source water cell `(1,64,0)` is included by contracted scan X bounds `[floor(.600999988...), ceil(1.199000011...)) = [0,2)` and Z bounds `[0,1)`. Its source height is the float `8 / 9.0F`; in `double e = q + fluidState.getHeight()`, `q` is promoted to float, so the sum at `q=64` rounds to `64.88888549804688F`, then widens to double. This exceeds contracted `minY=64.001`.
- The climb predicate samples `floor(x)`, `floor(box.minY)`, `floor(z)`, then accepts an open trapdoor when the ladder directly below has matching facing (`LivingEntity.java:1041–1063` in 1.13; `:1046–1068` in 1.13.1/1.13.2). The witness samples `(0,64,0)`. The north ladder shape is a 3/16 slab; it touches the box at Y=64 without volumetric overlap (`LadderBlock.java:22–45`). Dry states are permitted; `WATERLOGGED` exists but the fixture does not require either trapdoor or ladder to contain water.
- A full block at `(0,65,-1)` has positive X/Y box overlap but ends at Z=0, leaving the same positive uncontracted gap before the movement. A `-0.03` Z displacement crosses the gap and clips to the wall boundary. `Entity.move` obtains collision shapes for the expanded movement box, clips Z against those shapes, then records `collidingHorizontally` when requested X/Z differs from clipped X/Z (`Entity.java:612–628, 674–680`). The clipped player center remains about `z=0.3000000119`, so the feet predicate still floors to Z=0. This establishes a possible collision input in the admitted same-tick state without using contracted scan bounds as movement geometry.
- Water travel's optional later `0.3F` clearance attempt occurs after `move` and damping (`LivingEntity.java:1583–1596` in 1.13; corresponding `:1652–1654` in later releases). `canMove` checks block collision and `!containsFluid` (`Entity.java:465–471`); `WorldView.containsFluid` returns true for any nonempty fluid state in its cell scan. The candidate can therefore fail clearance while the water branch was already entered. This verdict needs no clearance success.

The source bodies and relevant block/fluid geometry were checked across all three releases. No code, build, tests, runtime, or history inspection was performed.


