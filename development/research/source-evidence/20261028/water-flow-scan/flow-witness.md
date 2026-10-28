# Water fluid-push vector integration: 1.8.9 → 1.9.4

**Finding:** The upper-bound iteration change in `World.applyLiquidDrag` changes the set of water blocks that contribute to the entity's current-flow vector at an exact integer maximum bound. This is a direct movement-velocity difference, not solely an upper-bound fluid-presence query. The same scan also affects `inWater`, which is a separate side effect.

**Version boundary:** The endpoint difference is verified in 1.8.9 and 1.9.4. Which intervening release first changes the bound is unknown; no continuous applicability interval is inferred from this pair.

## Exact source identity and integrity

Canonical source roots:

- `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.8.9\ornithe-feather`
- `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.9.4\ornithe-feather`

Both are `ornithe-feather` sources from the exact run recorded in each `ornithe-feather.provenance.json`, using Feather Gen2 build 2 mappings and Vineflower 1.12.0. Their `ornithe-feather.ready.json` source-manifest hashes match the actual manifest files:

- 1.8.9 manifest SHA-256: `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004`; raw client SHA-256: `14f0d96d1a56fb4f5c3b2233d00699525893fe5ce3dcf181e7de59120595d298`; mapping artifacts: `feather-gen2-1.8.9+build.2-mergedv2.jar` and `.tiny`.
- 1.9.4 manifest SHA-256: `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19`; raw client SHA-256: `23e90103a1ca2ac71100004c6d5846de09f85695f579843ef8da41571e60c908`; mapping artifacts: `feather-gen2-1.9.4+build.2-mergedv2.jar` and `.tiny`.

`ornithe-feather.verification.json` for each endpoint reports `verified-identical-source-and-input-artifacts`, zero source-file differences, and the matching source-manifest hash. The four files used for the path comparison also match their individual entries in `ornithe-feather.sources.sha256` (`World.java`, `LiquidBlock.java`, `Entity.java`, `LivingEntity.java`).

The requested pinned cache path, `D:\Javastuff\LegacyParkourCompat\.minecraft-decompile-cache`, contains neither a `1.8.9` nor a `1.9.4` directory. Its `1.9` pin identifies `1.9.json`, so it is not evidence for either endpoint. Source provenance therefore rests on the canonical published trees and their ready/verification manifests above.

## Reachable path and changed bounds

The ordinary client-player hierarchy reaches the fluid path: `ClientPlayerEntity` extends `PlayerEntity` in both trees; `PlayerEntity.tick` delegates to `LivingEntity.tick` (1.8.9 `PlayerEntity.java:183–228`; 1.9.4 `:170–200`); `LivingEntity.tick` delegates to `Entity.tick` (1.8.9 `LivingEntity.java:1259–1260`; 1.9.4 `:1489–1490`); `Entity.tick` calls `this.baseTick` (1.8.9 `Entity.java:245–246`; 1.9.4 `:299–304`); `LivingEntity.baseTick` delegates to `Entity.baseTick` (1.8.9 `LivingEntity.java:179–181`; 1.9.4 `:206–208`); and `Entity.baseTick` calls `checkWaterCollisions` (1.8.9 `Entity.java:300`; 1.9.4 `:361`). For an unmounted player, this reaches the water scan on each base tick. Vanilla invokes this path without a client-world gate; historical mod movement must remain client-only per project invariant. In 1.9.4, a mounted `BoatEntity` takes an early branch that skips the water scan (`Entity.java:850–852`); the stated unmounted player precondition avoids that vehicle gate. The inherited `Entity.checkWaterCollisions` calls `World.applyLiquidDrag` with `getShape().grown(0.0, -0.4F, 0.0).contract(0.001, 0.001, 0.001)` in 1.8.9 (`Entity.java:746–747`) and the equivalent isotropic `contract(0.001)` in 1.9.4 (`Entity.java:849–852`). `Box.contract(dx,dy,dz)` subtracts each amount from the corresponding maximum in 1.8.9 (`Box.java:176–183`); 1.9.4 `contract(amount)` delegates to `expand(-amount)` (`Box.java:222–224`), whose `expand` delegates equally on all axes to `grown` (`Box.java:110–112`). Thus the exact-integer case below is the maximum of the already contracted box supplied to `applyLiquidDrag`.

The scan bounds differ:

- 1.8.9 `World.java:1486–1512`: `j = floor(bounds.maxX + 1.0)`, `l = floor(bounds.maxY + 1.0)`, `n = floor(bounds.maxZ + 1.0)`; loops visit each coordinate from its floor minimum through the exclusive upper bound minus one.
- 1.9.4 `World.java:1560–1585`: the same lower bounds, but `j = ceil(bounds.maxX)`, `l = ceil(bounds.maxY)`, and `n = ceil(bounds.maxZ)`.

When a supplied maximum coordinate is exactly integer `M`, the old formula produces upper bound `M+1` and visits cell coordinate `M`; the new formula produces `M` and excludes that cell. This applies independently on X, Y, and Z. The region-loaded check receives these same bounds, so it follows the same additional-cell difference.

## Concrete eligible movement consequence

Preconditions: the ordinary player is unmounted; its water-collision box is loaded and has `bounds.maxX == M`; vertical scanning includes a level-0 water cell at `(M,p,z)` and satisfies the existing liquid-surface gate `l >= p + 1 - getHeightLoss(0)`; no other scanned water cell contributes a flow vector; and the east neighbor of `(M,p,z)` is same-material water at level 1, with no other horizontal depth gradient contributing.

In 1.8.9 the boundary cell is scanned. `LiquidBlock.getFlow` sees east depth `j=1` and current depth `i=0`, accumulates east offset times `j-i = +1`, bypasses the falling-fluid branch because current level is below 8, and normalizes to `(+1,0,0)` (`LiquidBlock.java:129–166`). `applyMaterialDrag` adds that flow to the accumulated vector. Since `Entity.hasLiquidCollision()` returns true by default (`Entity.java:1753–1755`), `World.applyLiquidDrag` sees nonzero length, normalizes, and adds `0.014` to `velocityX` (`World.java:1518–1523`).

In 1.9.4 the cell at `x=M` is excluded. With the stated sole-contributor precondition, the accumulated vector remains zero, so the `length() > 0.0` gate fails and no `0.014` impulse is applied (`World.java:1593–1598`). This is direct velocity integration in the movement-reachable method. The scan also returns different water-presence state through `bl` and `checkWaterCollisions.inWater`, but the vector impulse independently establishes movement impact.

## Flow math and neighboring gates compared

No additional flow-formula or impulse-integration change was found in this pair:

- `LiquidBlock.getFlow` visits `Direction.Plane.HORIZONTAL` in the same `NORTH, EAST, SOUTH, WEST` order. Neighbor-level deltas use the same `j - i`; the below-neighbor fallback uses the same `j - (i - 8)` after the same nonblocking gate; the level-`>=8` side-wall branch normalizes before adding `(0,-6,0)` and the result is normalized again at return. 1.8.9 uses `Vec3d.add` for each term; 1.9.4 accumulates the same terms into three doubles and constructs `Vec3d` before the same normalizations (`LiquidBlock.java:129–166` vs. `131–177`). `Direction.Plane.HORIZONTAL` order is unchanged.
- `LiquidBlock.getHeightLoss` is the same: values `>=8` are treated as 0 and the result is `(state + 1) / 9.0F` (1.8.9 `LiquidBlock.java:41–47`; 1.9.4 `:49–55`). The `l >= d` surface gate is otherwise identical in `World.applyLiquidDrag`.
- Per-cell flow addition, aggregate length gate, `entity.hasLiquidCollision()`, aggregate normalization, component order, and `0.014` impulse scale are identical. `Vec3d.normalize` keeps the same squared-component sum and `1.0E-4` cutoff; 1.9.4 returns the shared zero vector at the cutoff where 1.8.9 allocates an equivalent zero vector.
- `checkWaterCollisions` calls `applyLiquidDrag` with `Material.WATER`, so this reachable local-player push path is water-only. No `Material.LAVA` call to `applyLiquidDrag` was found in the examined entity/world callers; lava remains a presence/damage path here, with no lava current-vector impulse difference identified.

## Bounded coverage

Examined the endpoint ready/provenance/verification/artifact/source manifests and the following movement-relevant source: `ClientPlayerEntity` superclass identity, `PlayerEntity.tick`, `Entity.tick`/`baseTick`, `Entity.checkWaterCollisions`, `Entity.hasLiquidCollision`, `LivingEntity.tick`/`baseTick`/inheritance path, `World.applyLiquidDrag`, `LiquidBlock.getFlow`, `LiquidBlock.applyMaterialDrag`, `LiquidBlock.getLiquidState`/`getLiquidDepth`, `LiquidBlock.getHeightLoss`, `LiquidBlock.isFaceSolid`, inherited `Block.isFaceSolid`, `Box.contract`/`expand`, `Vec3d.add`/`length`/`normalize`, and `Direction.Plane.HORIZONTAL` order.

Not examined: source-production/update logic for water or lava; unrelated fluid-presence routines (including other upper-bound presence queries); other entities' physics; client-controller scheduling beyond the `Entity`/`LivingEntity` reachable base-tick path; later or intervening version trees; or mappings/source outside the two exact canonical trees. No server-state production behavior is proposed.


