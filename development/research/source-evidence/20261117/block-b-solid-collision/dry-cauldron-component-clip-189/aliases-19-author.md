# Exact 1.9 alias audit: cauldron component clipping

## Verdict

**The old-side full-move result is source-consistent across 1.9, 1.9.1, 1.9.2, 1.9.3 and 1.9.4 for the bounded command-created setup.** With the corrected 0.6F player width, feet at y=0.5, a dry cauldron at (0,0,0), a bottom-slab support at (1,0,0), a top-slab canopy at (1,2,0), and immediate move request `(0,0,+0.2)`, each alias has the same ordered component boxes, strict old Z clip and step fallback. The resulting full move remains `(0,0,+0.075)` on the old algorithm. This is an alias audit; it does not establish runtime parity or natural locomotion reachability.

The independent 1.8.9 comparison fixture used as the allowed anchor was read and its SHA-256 matched the supplied identity: `51456db253a63c257acfb32bf1156d4f1ddb9721dde5164a30533193c5aa05c8`. Its canopy correction and narrow applicability conditions are preserved here.

## Evidence identity

The canonical roots are `decompiled_minecraft/<version>/ornithe-feather/`; I inspected the actual roots and hidden/root sidecars with `-Force`. 1.9 through 1.9.3 each contain `.decompile-provenance.json` inside the Feather source root. The manifests identify exact version-specific Feather inputs (1.9, 1.9.1, 1.9.2, 1.9.3) and the same `lpc-decompile-v1` source pipeline; their sidecar hashes respectively are `8daacb7af8f210e716ee4b34c43b8a5d6f6b4b19831d2fd725cbe16ecf330a1a`, `e9fb0da6511597e2de953a143f9570b82243e5b9504603686e557f06271da64a`, `9928b467de35c7e7f891b4fee1627a68feecdf233c2076909fd4834b0380510`, and `43325cdd53ca34b88ee9af8464782b323a948b98c8a556c4761f1e5737b84c3a`.

The 1.9.4 version-root sidecars identify `versionId: 1.9.4`, `namespace: ornithe-feather`, `feather-gen2-1.9.4+build.2`, and a ready source tree. `ornithe-feather.sources.sha256` matches the ready marker's digest `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19`; its verification says 1,819 source files verified, zero source differences, and no raw input artifact differences. The one reported artifact difference is a regenerated intermediate remapped jar, with an explicit verification explanation that the published source tree and raw inputs match. `artifacts.sha256` is `9527dca544694daa3b4a7741be1a5b4802d8b6665c8a152a408a37a27a1b4d77`.

Relevant source-file SHA-256s form two alias groups, without a behavior split in the audited operations:

| Source file | 1.9–1.9.2 | 1.9.3–1.9.4 |
|---|---|---|
| `net/minecraft/block/CauldronBlock.java` | `b11344eff4f1fac51066eafedd9eaaba047784f5bc8034780d24230c9d879c61` | `84b346d09f7badf7821a43ef8a6a16fbcd161972ad67a8030c7574780ca6b205` |
| `net/minecraft/util/math/Box.java` | audited method has strict same predicates | audited method has strict same predicates |
| `net/minecraft/entity/Entity.java` | `74d3ac34d7e6355c8fdcdd777dcd95ba9ef4626718c8772c1b58d0e28419e42a` | `bcd7fa2206bf8d7271102f6da7fe2771f96dbe22ec79dab9f2101a3e29c17ef0` |
| `net/minecraft/entity/living/LivingEntity.java` | `1.9: 5b5936656aacc64a4d75db7b370985f9456ac96076331da047bd246f76a705fb`; `1.9.1: d8824f670037a88753c428c27213989fc5173b1d7c638f498f73395c145b6fd9` (1.9.2 matches 1.9.1) | `bbb7703f18fd5da05c4e4a43a77ea644b388e63c01d34166d308ea52054be4e5` |
| `net/minecraft/server/command/SetBlockCommand.java` | `6ebe9e1cdd85971d55f46855623811c0c1a6f774f9cefcae0639837018b7ef74` | `04ad74bbb6d532d2a750a1d2763b73becfe8bb0274ba34f4569461289bd235e` |

The changed class/file hashes and line offsets do not alter the bounded methods below. The 1.9.3/1.9.4 cauldron signature adds a nullable annotation; the five component values and their append order are unchanged.

## Bounded source trace

- All five `CauldronBlock.java` files declare `BASE_SHAPE = (0,0,0)-(1,0.3125,1)`, north wall `z=0..0.125`, south wall `z=0.875..1`, east wall `x=0.875..1`, west wall `x=0..0.125` (1.9 lines 32–36; 1.9.3/1.9.4 lines 33–37). `addCollisions` appends **base, west, north, east, south**, in that order (1.9 lines 45–49; 1.9.3/1.9.4 lines 46–50). Default state sets cauldron level to `0`, so this is dry.
- `Box.intersectZ` retains the strict orthogonal overlap gates `!(box.maxX <= this.minX)`, `!(box.minX >= this.maxX)`, and equivalent Y conditions, then clips positive Z when `box.maxZ <= this.minZ`; its comparisons and arithmetic are the same in every alias (1.9 line 177 onward; 1.9.3/1.9.4 line 179 onward). Entity move still applies Y, X, then Z component clipping in list order, and enters its step alternative only when `stepHeight > 0.0F`, grounded, and X or Z was clipped (`Entity.java` 1.9 lines 438, 519–530; 1.9.3/1.9.4 lines 441, 522–533). Candidate horizontal squared gains still select the first route only on `al > am`; ties choose the other candidate (1.9 line 593; 1.9.3/1.9.4 line 596), which preserves the fixture's normal clipped route on the tie.
- `LivingEntity` assigns `stepHeight = 0.6F` in every alias (1.9 lines 162; 1.9.3/1.9.4 line 163). The entity constructor still sets `width = 0.6F`, `height = 1.8F`; `setPosition` halves width in float (`float f = this.width / 2.0F`) before constructing the body box (1.9 `Entity.java` lines 165–166, 274 onward; 1.9.3/1.9.4 lines 167–168, 277 onward). Use the fixture's corrected center x `1.299999961920929`, which gives minX about `0.99999995` and maxX about `1.5999999738418579`; do not substitute an idealized 0.3 double half-width.
- `SlabBlock` retains bottom collision y `0..0.5` and top collision y `0.5..1` (lines 19–20 and 38 in 1.9, 1.9.3 and 1.9.4). `StoneSlabBlock` maps metadata 0 to bottom and metadata 8 to top (1.9 line 78; later aliases line 80). Thus the support slab's top at y=0.5 supports the stipulated feet; the top slab at `(1,2,0)` begins collision at y=2.5. The body top at 2.3 has 0.2 clearance, less than the 0.5 rise needed to clear the cauldron rim from feet at 0.5. It leaves the starting body clear while blocking the upward step route.
- `SetBlockCommand.getRequiredPermissionLevel()` is 2 in every alias (1.9 line 28; later aliases line 29). Its execute path obtains state from metadata and directly calls `world.setBlockState(blockPos, blockState, 2)` (1.9 line 91; later aliases line 92); no player collision rejection appears on that path. Metadata 0 leaves the default dry cauldron. The trace is command-authorized placement followed immediately by the audited move; it claims no intervening tick or command-free placement reachability.

For the stipulated box, feet y=0.5 leaves the cauldron floor below the player. During positive Z movement the east wall's Z min is 0 and cannot clip; the entity has a strict 5e-8 X overlap with that wall but maxZ 0.8 is not `<= 0`. The south wall overlaps in X/Y and has minZ 0.875, so it clips requested `+0.2` to `0.875 - 0.8 = +0.075`. The canopy prevents the 0.6F step alternative from lifting enough to clear the 1.0-high wall. The unchanged tie fallback keeps this clipped horizontal result for the full move.

## Gates

This conclusion depends on the exact corrected float-width endpoints, the dry five-box cauldron, support at y=0.5, canopy underside at y=2.5, grounded state, no no-clip, and no intervening player-motion tick. Remove the canopy and the step alternative can make final Z equal while changing Y, as the accepted 1.8.9 anchor warns. The alias audit does not extend the anchor's independent modern-side check or establish game/runtime behavior.
