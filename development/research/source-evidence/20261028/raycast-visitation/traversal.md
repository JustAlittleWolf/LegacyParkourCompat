# Integer-origin traversal order follow-up

## Scope and relation to earlier notes

This is a narrow source comparison for a ray whose X origin is exactly an integer and whose X motion is positive. It supplements `REPORT.md` and `ray-cache-correction.md`; it does not edit or supersede either. The initial `REPORT.md` statement that ray DDA was categorically outside movement scope remains withdrawn as documented in `ray-cache-correction.md`. The accepted 1.8.9 camera-pick path can still write mutable anvil/chest bounds for cells it actually visits before later player movement reads those bounds.

This follow-up tests only whether the modern epsilon-shifted traversal adds the cell immediately behind that origin, and what its canonical Anvil/Chest providers do there. It uses only the exact 1.8.9 and 26.2 source exports named below and the already accepted camera-tick-raytrace source finding.

## Exact ray and visited-cell comparison

Use the same endpoints in both versions:

```text
from = (10.0, 64.25, 32.5)
to   = (12.5, 64.25, 32.5)
```

Y and Z are constant and interior to the candidate cells; there are no axis ties. Let `N = 10`. To isolate order, take the state in `(9,64,32)` to be an Anvil or Chest and the states in X cells 10 through 12 to miss (air suffices, with no pickable fluid). The listed sequences are these pre-stop candidate orders; any later non-null result truncates the sequence at that cell.

| Version/path | Initial X cell | X-cell visitation for these endpoints | Relevant stop rule |
|---|---:|---|---|
| 1.8.9 `World.rayTrace` | `floor(from.x) = 10` | `10, 11, 12` | Starting cell is dispatched before the loop; later cells are dispatched in DDA order. First non-null `Block.rayTrace` result returns. |
| 26.2 `BlockGetter.traverseBlocks` | `floor(lerp(-1e-7, 10.0, 12.5)) = floor(9.99999975) = 9` | `9, 10, 11, 12` | The initial consumer runs before stepping; each later consumer returns immediately on a non-null result. |

The 26.2 endpoint is also shifted: `toX = lerp(-1e-7, 12.5, 10.0) = 12.50000025`, whose floor remains 12. The initial `fromX` has fractional part `0.99999975`; with positive X motion, its first boundary fraction is positive and small, so the next visited X cell is 10. The remaining X crossings yield 11 and 12. In 1.8.9, the initial cell is directly `floor(from.x) = 10`; positive-X stepping then reaches 11 and 12. The extra 26.2 cell is therefore exactly `(9,64,32)` for these endpoints.

Source: 1.8.9 `World.rayTrace` lines 639-653, 658-664, 673-754; 26.2 `BlockGetter.traverseBlocks` lines 114-172; 26.2 `Mth.lerp(double,double,double)` lines 558-560. In the old camera-pick call shape recorded in the accepted source finding, the start cell is eligible (`allowHitInStartPosition=true`) and the anvil/chest ray provider is reached without a collision-shape prefilter. The supplied ray condition is enough to compare candidate order; this is not a claim about a full historical gameplay trajectory.

## Provider calls and early-stop consequence

For the ordinary 26.2 player block-pick fallback, `Entity.pick` constructs `ClipContext.Block.OUTLINE` and `ClipContext.Fluid.NONE` (accepted source finding, `Entity.pick` lines 2006-2010). `BlockGetter.clip` processes each visited position in this order: `getBlockState(pos)`, `getFluidState(pos)`, `context.getBlockShape(...)`, block-shape clipping/conditional interaction-shape clipping, then fluid-shape selection and clipping (`BlockGetter.java` lines 65-81, 84-95; `ClipContext.java` lines 48-59). Thus the additional `(9,64,32)` callback proves an extra block-state read, fluid-state read, and outline-shape provider query. With `Fluid.NONE`, the fluid shape is empty. The interaction shape is called only if the outline shape already produced a hit.

Anvil and chest are admissible existing states for this cell. In 26.2, `AnvilBlock.getShape` selects static geometry from the queried state's `FACING` axis (`AnvilBlock.java` lines 35-37, 76-79); `ChestBlock.getShape` selects static geometry from queried state `TYPE` and direction (`ChestBlock.java` lines 76-77, 192-202). These outlines stay within their block cell: the Anvil shape is built from local coordinates 0 through 16, and the Chest shapes use local 14/16 and 15/16 dimensions. Importantly, the lerped endpoints in `traverseBlocks` determine visited cell indices only; `BlockGetter.clip` retrieves the original `context.getFrom()` and `context.getTo()` and passes those to the shape clip (`BlockGetter.java` lines 65-74). For cell X=9, the ray starts at its east face, x=10; `VoxelShape.clip` probes at `from + 0.001*(to-from)`, x=10.0025, then falls through to AABB clipping for this non-full-wide outline (`VoxelShape.java` lines 163-182). Since the Anvil/Chest outline AABBs have `maxX <= 10`, the positive-X entry-plane intersection has scale `s <= 0`; `AABB.clipPoint` accepts only `0 < s < scaleReference` (`AABB.java` lines 331-395). Consequently these specific behind-origin Anvil/Chest outlines return no block hit for the stated ray. The traversal then proceeds to cell 10; the extra callback does not early-stop this ray.

The general traversal stop effect is source-proven but conditional: any visited provider whose callback returns a non-null result ends traversal before later cells. The exact behind-origin Anvil/Chest state above does not satisfy that condition for this ray. The canonical 26.2 Anvil/Chest shape path is state-resolved and contains no write to the old shared `Block.min/max` bounds. This non-null-hit rejection is separate from a possible side effect on provider visitation.

**Conditional compatibility-observer scenario (not a claim about product code):** suppose a compatibility observer reproduces the old `Block.rayTrace -> updateShape` write on each selected modern provider query before clipping. The extra modern Anvil query at `(9,64,32)` would then read that state's `FACING` and mutate the shared Anvil bounds even though its outline clip misses. For a concrete state contrast, let a prior Anvil visit at another position have left the cache in the axis-Z form, and let the extra candidate at `(9,64,32)` have an axis-X `FACING`. Old `AnvilBlock.updateShape` writes axis-X bounds `(0,0,0.125)..(1,1,0.875)` or axis-Z bounds `(0.125,0,0)..(0.875,1,1)` from the queried state (`AnvilBlock.java` lines 67-74). The old DDA starts at cell 10 and skips cell 9, so with cells 10-12 missing it performs no later Anvil write and leaves the prior axis-Z cache intact. Under the stated observer, modern traversal visits cell 9 first and changes the last writer to axis-X; if there is no later Anvil provider visit before movement, `Block.getCollisionShape` reads the shared `min/max` fields to construct collision geometry at the movement query position (`Block.java` lines 346-349). That changes the emulated cache state supplied to movement collision despite the pick miss. The exact movement response still depends on where movement queries that geometry and the player's path. The observer, its cache identity, and its before-movement call ordering are conditional implementation premises, not established by canonical 26.2 sources; without such an observer, native Anvil/Chest outline lookup is state-resolved and does not perform this old write.

The broader old-path movement dependency remains source-supported: old ray visits elsewhere can write shared bounds, and the accepted tick ordering places camera pick before entity movement. This follow-up does not establish any adapter-specific visitation or cache behavior.

## Source identity and hashes

Canonical read-only roots:

- 1.8.9: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.8.9\ornithe-feather\`
- 26.2: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\26.2\unobfuscated\`

| Version | Ready marker SHA-256 | Provenance SHA-256 | Source-manifest SHA-256 | Artifact-manifest SHA-256 |
|---|---|---|---|---|
| 1.8.9 `ornithe-feather` | `FE0EC792349AE43C91D0A30F23660C94B2F5C8D31A29643F73ED0DE367110B95` | `BA066FC8F1C7CEB9819D2106338D9E9F2C2B96EB5A8329EB40E171B1F9889185` | `9E75F46DC0ED43B6A355BD65DB8A92C93A4DFEAECFA92284187C6FE9410D8004` | `DA003358256D1C4402EBB20614651E5410310E871EE913DE2B9C1295A64E1446` |
| 26.2 `unobfuscated` | `F9406ADB6BF7CB4C1AB0792A798AB2CB90E082AD4C2EEE032C9F674D0EA8070F` | `A328F5333A1B27AADDE4422B23159A323AE219CCD26F11207C1A5461366F27D1` | `A7AD74FC712567EB87136AFC1EAFCFD602B4088E9869F3596331FD34FAEFE894` | `BA9DC53A41BCA9744EC1C8AB2CE9363764A22333C39EF64D5105F4E3BF5C98BA` |

The ready markers identify versions, namespaces, and the source/artifact manifest digests above; the provenance manifests identify exact decompile runs and mapping identity (`ornithe-feather` / Feather build.2 for 1.8.9; `unobfuscated` with null mapping artifacts for 26.2). Selected source fingerprints:

| Version | Exact source file | SHA-256 |
|---|---|---|
| 1.8.9 | `net/minecraft/world/World.java` | `3C04F5B874FBB6692039164C882A3C47FE28C7ECA864E91C9AC57798153888EE` |
| 1.8.9 | `net/minecraft/block/Block.java` | `EA10F05106A3CF7189AEC85236A7ECF9C7106A717F37BED583B5EA4ADEFFA528` |
| 1.8.9 | `net/minecraft/block/AnvilBlock.java` | `7E52C4A86C29548D8DC491FE7D4FF17A79C1DC9F426F538A6C19E161F99F013E` |
| 1.8.9 | `net/minecraft/block/ChestBlock.java` | `8072BC317398297D1327A3BE4EDD8A66381A1A1021A6C4A8F3FBEEBCA2891D51` |
| 26.2 | `net/minecraft/world/level/BlockGetter.java` | `623A44206DB93D8FB2EEFC9353345689CC35E0C1742B5D61789EAB2B098B687D` |
| 26.2 | `net/minecraft/util/Mth.java` | `30455C684F2401C79290847822BCA82E77162C4A2BCF8618E85CAC9898A2DC17` |
| 26.2 | `net/minecraft/world/phys/shapes/VoxelShape.java` | `419A46BBCC8B48E1C075127954EB243DCC60D10D4087BF256D895CF91A2FF854` |
| 26.2 | `net/minecraft/world/phys/AABB.java` | `1FFDD0F6D26A4DD1E26407EA9A386EAB7903E43462E60C9AE5FDC50A0468F10B` |
| 26.2 | `net/minecraft/world/level/ClipContext.java` | `67F5569E00DAC4169F54C42D2C237B1488EA3C7C8E00889603FC748E3C24ECBC` |
| 26.2 | `net/minecraft/world/level/block/AnvilBlock.java` | `A15B5EF5067F098A2E6B83D3B934730F376A3420A4CE47C798A6A80D800B9391` |
| 26.2 | `net/minecraft/world/level/block/ChestBlock.java` | `8BE5C44FE3E8B5C9F529F58E0E3694A7CE1590A1957C31B72CBEC7B97E061D22` |

## Boundaries and open gates

- Proven: with the specified positive-X integer-origin ray, 26.2 visits the immediately-behind cell `(N-1, floor(y), floor(z))` before `(N, floor(y), floor(z))`; 1.8.9 starts at N and does not visit N-1.
- Proven: the added callback asks the modern state for its outline shape; the ordinary traversal returns on the first non-null consumer result.
- Proven for the stated ray and canonical Anvil/Chest outlines: the behind-cell shape query misses and does not stop traversal; canonical modern providers are state-resolved, not old mutable-bound writes.
- Conditional: if a compatibility observer applies old `updateShape` semantics on every modern provider query before clipping, the extra behind-origin Anvil query can become the last mutable-bound writer on a no-later-Anvil-writer path, changing bounds subsequently read by movement collision. This is a source-grounded conditional, not product evidence.
- Open: whether any compatibility implementation actually has that observer, uses the modern traversal, preserves shared cache identity, or meets the stated call-order/no-later-writer conditions. That requires implementation evidence and is not inferred from these sources.
- This exact boundary case does not establish provider coverage for all blocks, all endpoint configurations, or all historical movement outcomes.

No product or integration source, backlog/wiki material, tests, builds, runtime/game launch, or source generation was used.
