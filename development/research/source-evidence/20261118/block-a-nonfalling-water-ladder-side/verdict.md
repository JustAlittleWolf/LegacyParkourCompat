# Independent verdict: occupied dry ladder in the nonfalling water-current side gate

## Bound claim and verdict

Author artifact: `C:/Users/Wolfi/.codex/visualizations/2026/11/18/01a1f01a-5c56-7280-8791-914a15288686/portal-farmland/known-nonfalling-materials-occupied-ladder/evidence.md`

Author SHA-256: `4649542bb20cfad534eb4204ad710a617da20a552585a5194fe9503bd4437dde` (verified before reading).

Independent source verdict: **ACCEPTED, scoped to the stated source behavior and static player probe.** The exact 24 stable versions from 1.8 through 1.12.2 support the ordinary, nonfalling water `LiquidBlock.getFlow` material-gate claim for an attached vanilla ladder occupying one dry horizontal side cell. This does not establish runtime parity or full-pair source coverage.

The independent input roster was frozen first in commit `a833a41c76054e3d08a19de4c37ddbba3a05f186`. No product implementation/history, wiki, backlog, or other lane was read.

## Identity and coverage audit

The roster enumerates all 24 stable IDs, mapping-local provenance SHA-256 values, exact Feather Gen2 build.2 mapping artifact digests, and the bounded source inventory. It excludes the retained `.previous-*` directories at 1.12 and 1.12.1. In the 17 versions with `.decompile-provenance.json`, I matched the six bounded source members to that record's per-file `sources` SHA-256 entries. In the remaining seven, I matched those six source files against `ornithe-feather.sources.sha256` and independently checked its digest and `artifacts.sha256` digest against the exact-version `ornithe-feather.ready.json`. All 24 release records identify the exact version and Feather mapping; no moving alias or path substitution was used. All 144 bounded source-file hash checks matched.

The six source members were `LiquidBlock.java`, `FlowingLiquidBlock.java`, `LadderBlock.java`, `Block.java`, `material/Material.java`, and `material/PlantMaterial.java`. Their distinct raw-file hash groups and release membership are recorded in the immutable author evidence; I recomputed the groupings from the source directories. The three 1.12 bodies were specifically present and hash-checked separately against their exact identities; their relevant `LiquidBlock`, `FlowingLiquidBlock`, and `LadderBlock` bodies are byte-identical across 1.12, 1.12.1 and 1.12.2.

A 24/24 static source predicate pass independently checked: ordinary ladder registration at historical ID 65; `LadderBlock` constructor uses `Material.DECORATION`; `Material.DECORATION` is a `PlantMaterial` whose `blocksMovement()` returns false; `LiquidBlock.getFlow` contains the dry-side material check, one-below liquid-depth read and normalized result; and `FlowingLiquidBlock` explicitly treats `Blocks.LADDER` as liquid-blocking. The exact `getFlow` body was retraced across its seven raw hash groups; the ladder-specific spread obstruction was retraced across all six raw `FlowingLiquidBlock` groups.

## Source facts independently confirmed

- The registry entry creates the ordinary ladder (`Block.java`, register ID 65). `LadderBlock` uses `Material.DECORATION`. That material instance is a `PlantMaterial`; the actual `blocksMovement()` implementation returns false. The flow sampler queries that movement-blocking predicate; it does not query ladder collision shape, solid rendering, climbing, or a body predicate.
- For each horizontal side, a negative liquid depth enters the branch guarded by `!sideMaterial.blocksMovement()`. The branch samples the block one below the side. For lower depth `j >= 0`, it accumulates `j - (centerDepth - 8)` times the side offset. At a center depth of 0 and lower source depth of 0, the east-side weight is 8. The later falling-wall-face branch is gated on raw level `>= 8`, so level 0 bypasses it. Final normalization turns the isolated east vector into `(+1, 0, 0)`.
- `FlowingLiquidBlock.isLiquidBlocking` has an independent ladder identity check. Spread admission consults it for destinations, so liquid propagation does not replace the ladder even though its material admits the current sampler's below read.
- A WEST-facing ladder at `(1,64,0)` uses support at `(2,64,0)`: in the pre-1.12 code the support is looked up at `pos.offset(facing.getOpposite())` and required solid; 1.12/1.12.1/1.12.2 use the same opposite-facing position and require a solid face (also rejecting signal sources). Stone satisfies those checks.

Representative exact lines: 1.8 `LiquidBlock.java:129–160`, `FlowingLiquidBlock.java:183–189`, `LadderBlock.java:82–110`, `Block.java:885`; 1.8.9 `LiquidBlock.java:129–160`, `FlowingLiquidBlock.java:184–190`; 1.9.3 `LiquidBlock.java:131–165`, `FlowingLiquidBlock.java:186–192`; 1.11.2 `LiquidBlock.java:131–176`, `LadderBlock.java:65–93`; 1.12.2 `LiquidBlock.java:139–179`, `FlowingLiquidBlock.java:186–214`, `LadderBlock.java:54–91`, `Block.java:890`, `Material.java:22`, `PlantMaterial.java:20–22`.

## Static local-player probe trace

The author fixture is source-consistent and can be probed by an ordinary local player without including the lower side source in the player's fluid scan. Use a standing, unmounted, non-flying local player centered at `(0.5,64,0.5)` with the ordinary `0.6 × 1.8` entity size. The `checkWaterCollisions` box is grown downward by `0.4` and contracted slightly; its horizontal extent remains strictly within block column `(0,*,0)`. It samples the center source at `(0,64,0)` but not the east-side ladder or its lower source at `(1,63,0)`.

`LocalClientPlayerEntity.tick` reaches `Entity.tick`; `Entity.tick` calls `checkWaterCollisions`. For an unmounted player, that calls `World.applyLiquidDrag` over the stated bounds. The world reads the center water state and calls its block's `applyMaterialDrag`, which adds `getFlow`; `Entity.hasLiquidCollision()` is true. With the ladder fixture, the accumulated flow is `(+1,0,0)` and the world path adds `0.014` to the player's X velocity. With the east ladder replaced by full stone, the below sample is rejected, the flow sum is zero, and no current velocity is added. In 1.12.0, 1.12.1 and 1.12.2 the unmounted case passes the boat-mount early gate in `checkWaterCollisions`; the current-flow path itself has no flying predicate. This describes source execution only, not a launched or runtime-verified world.

Player-path evidence: 1.8 `LocalClientPlayerEntity.java:111–114`, `Entity.java:299`, `Entity.java:745–758`, `World.java:1462–1490`, `Entity.java:1726–1728`; 1.12/1.12.1/1.12.2 `Entity.java:382`, `Entity.java:948–963`, `World.java:1579–1618`, `Entity.java:2141–2143`. A 24/24 static path check confirmed that each local-player `tick` reaches `super.tick`, each `Entity.tick` calls `checkWaterCollisions`, each `Entity` reports `hasLiquidCollision() == true`, and `World.applyLiquidDrag` dispatches `applyMaterialDrag` before the collision predicate. Endpoint traces and the 1.12 patch trio were read directly for the full bounds and vector application. No movement-loop test or game launch was performed.

## Scope and exclusions

Accepted scope is only the nonfalling ordinary water-current side-sampler gate and the attached vanilla ladder fixture described above. Do not generalize to all decorations or nonblocking materials. Falling wall-face behavior, ladder climbing, body-water predicates, portal/piston behavior, runtime parity, or exact mod implementation cutover remain outside this verdict.

