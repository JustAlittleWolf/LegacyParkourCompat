# Independent source verdict r2: 1.8 cauldron fixture alias

Verdict: **The bounded command-created, canopy-constrained source witness carries across exact 1.8.0–1.8.9.** The earlier r1 report at `review-18-aliases-cauldron-component-clip/verdict.md` correctly identified the strict X gate and 1.8.0 descent difference, but lacked the fixture coordinates and consequently left the required decimal unresolved. This r2 traces the supplied fixture and corrects that gap.

## Exact source identity and sidecars

I checked the exact `ornithe-feather` trees and their sidecars using `Get-ChildItem -Force` at both specified locations for each patch:

- For 1.8.0–1.8.8, `.decompile-provenance.json` is present inside each `decompiled_minecraft/<version>/ornithe-feather/` directory. It names that release's exact `feather-gen2-<version>+build.2-mergedv2.jar`. Recomputed SHA-256s matched all seven files listed in `source-18-aliases-cauldron-component-clip/report.md` for each patch: `CauldronBlock.java`, `Box.java`, `Entity.java`, `LivingEntity.java`, `PlayerEntity.java`, `World.java`, and `SetBlockCommand.java` (70/70).
- The version-root forms `decompiled_minecraft/<version>/ornithe-feather.provenance.json`, `ornithe-feather.ready.json`, `ornithe-feather.sources.sha256`, and `artifacts.sha256` are absent for 1.8.0–1.8.8. They were not used as evidence.
- For 1.8.9, `decompiled_minecraft/1.8.9/ornithe-feather/.decompile-provenance.json` is absent. The version-root forms `decompiled_minecraft/1.8.9/ornithe-feather.provenance.json`, `ornithe-feather.ready.json`, `ornithe-feather.sources.sha256`, and `artifacts.sha256` are present and identify Feather Gen2 build 2. The source-manifest digest is `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004`; its seven fixture source hashes match (7/7).

This corrects the r1 report's mistaken claim that the 1.8.0–1.8.8 roots have no provenance: the manifests are hidden one directory down, in the mapping roots.

## Float geometry and exact clip

The source player width is `0.6F`, whose stored float is `0.6000000238418579`; division by `2.0F` gives `0.30000001192092896`. `Entity.setPosition` subtracts/adds that float half-width to its double position. With the fixture's x center `1.299999961920929`, the box is approximately x `[0.99999995, 1.5999999738418579]`, producing a positive `5e-8` overlap with the east cauldron wall ending at x=1. `Box.intersectZ` rejects only when the orthogonal X or Y intervals are non-overlapping or merely touching, so this strict overlap passes.

The exact source clip value depends on the Z center. With center z=`0.500000011920929` (the approved fixture's approximately 0.5 center), `maxZ=0.800000023841858`; the south-wall gap is `0.875 - 0.800000023841858 = 0.074999976158142` (normally printed as `0.074999976`). With center z exactly `0.5`, `maxZ=0.800000011920929` and the source returns `0.07499998807907104` instead. Thus the earlier shorthand “z=.5 ± (.6F/2)” does not yield `+0.074999976`; retain the approved fixture's tiny positive Z-center offset when binding that decimal.

At feet y=0.5, the cauldron floor ends at y=0.3125 and fails the strict vertical-overlap guard. Its south wall starts at z=0.875, has X/Y overlap with the player, and caps the requested positive 0.2 Z displacement by the gap above. The dry cauldron's `addCollisions` supplies five boxes in fixed order (floor, west, north, east, south); state level does not change that geometry. `Block.addCollisions` only appends a provider box after strict shape intersection, and `World.getCollisions` supplies these boxes to `Entity.move`.

## Canopy, full step solver, and alias result

The bottom slab under the player has top y=0.5. The player's top is y=2.3. A top slab at block `(1,2,0)` starts at y=2.5, so it leaves the initial body clear and clips an upward step request to +0.2. That is less than the +0.5 rise needed to clear the cauldron wall from feet y=0.5.

All aliases use the same ordinary Y→X→Z component clipping order and the same strict Z-gap expression. The ordinary +0.2 Z request therefore clips to the south-wall gap. The grounded player enters the step alternative because horizontal motion was clipped. For the canopy-constrained alternative, both horizontal candidate routes clip to the same Z gap; their squared horizontal gains tie. The final comparison uses `>=`, so it restores the saved ordinary clipped move.

The descent expression does change across the alias line. In 1.8.0 `Entity.move` uses `dy = -stepHeight` for the selected candidate; in 1.8.1 it uses the selected candidate's actual clipped ascent (`-var33` or `-var81`); 1.8.2–1.8.9 express the same candidate-specific logic with readable locals (`-n` or `-q`). On this witness, the 1.8.0 full descent is clipped by the bottom slab to -0.2 after a +0.2 rise, and the newer expression also descends by -0.2. Both candidates return to the support height; then the equal-horizontal-gain fallback restores the ordinary clip. So the 1.8.0 versus 1.8.1+ source change does not change this fixture's accepted full move: zero net Y and the same south-wall Z clip. This conclusion is limited to the stated canopy and immediate move; without the canopy, the step can clear the wall and the same full-move Z claim does not follow.

The setblock command and world write provide the lawful stationary setup: the command's write path does not reject the existing player-box intersection. The reviewed witness goes from the block write to the immediate move request, with no intervening tick. This does not establish natural locomotion reachability, runtime parity, other push behavior, or unrelated movement.

## Bound conclusion

For the approved fixture using Z center `0.500000011920929`, the exact old-source SELF-Z component limit is `+0.074999976158142` and the roof-constrained full move resolves to that same Z delta in every exact alias from 1.8.0 through 1.8.9. If the fixture is instead specified with exactly z=`0.5`, change the expected clip to `+0.07499998807907104`. The sidecar-backed source check and this scope do not make claims beyond this stationary command-created witness.
