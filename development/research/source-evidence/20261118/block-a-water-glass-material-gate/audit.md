# Independent audit: dry full-glass water-current gate

Verdict: **ACCEPT** for the bounded current-support dependency.

## Input identities and scope

- Audited report: `legacy-fluid/water-material-glass-gate/evidence.md`, raw SHA-256 `1c115cad2d61299f8300591e3a4207c55960781ef7fd52d3f10ec1c24937e65b`.
- Accepted companion: `development/research/source-evidence/20261117/block-a-early-fluid-bounds/water-upper-bound-liquid-admission.md`, raw SHA-256 `a82ce04764f3d0c5f2155c0fda86b0cf29aa460d39be2ac3bd296baef32cd330`.
- Source roster was frozen before either report was opened: `roster.md`, commit `eae85a4d7085dc10e8fcf257115ec991a29125d9`. The registration initializer is `Block.java`; the post-report correction and raw hashes for this required path are recorded separately in `roster-amendment.md`.
- The bounded operation is ordinary, nonfalling `LiquidBlock.getFlow` horizontal-neighbor dry-side admission and its one-below material contribution, projected only onto `minecraft:glass` and the 16 `minecraft:stained_glass` color states. No generalized material behavior is accepted.

## Source checks

All 24 stable releases from 1.8 through 1.12.2 are present as exact `ornithe-feather` sources, with each corresponding Feather Gen2 `build.2` mapping identity. The 17 `.decompile-provenance.json` source trees match their recorded hashes for `LiquidBlock.java`, `Block.java`, and `Material.java`. The other seven releases have ready sidecars with `status: ready`; source-manifest, artifact-manifest, and provenance hashes match their sidecars; the exact version and `ornithe-feather` namespace match; and the three relevant local sources match their source-manifest hashes. Both the merged mapping jar and Tiny artifact identify `feather-gen2-<release>+build.2`. No missing, changed, or mismatched source artifact was found.

Meaningful raw-class-hash deduplication across all 24 exact releases produced 7 `LiquidBlock` groups, 9 `Block` groups, 5 `Material` groups, 2 `GlassBlock` groups, and 5 `StainedGlassBlock` groups. The gate was checked in each distinct `LiquidBlock` group and then checked across every release for the same ordered operation: dry side (`depth < 0`), `!blocksMovement()`, sample `side.down()`, admit a nonnegative below depth, weight `below - (center - 8)`. Variable and API differences across release groups do not alter that sequence. The 1.8.1 local name difference (`var9` versus `k`) is only a decompiler local-name difference; the expression remains `j - (i - 8)`.

Across all 24 releases, `Block.java` registers both identifiers using `Material.GLASS`: `GlassBlock(Material.GLASS, ...)` for `glass` and `StainedGlassBlock(Material.GLASS)` for `stained_glass`. `Material.GLASS` is constructed as an ordinary `Material`, and the base `Material.blocksMovement()` returns true. `StainedGlassBlock` uses the `DyeColor` color state and `DyeColor.values()`, preserving the 16 full stained-glass variants. `GlassBlock` and stained glass inherit material behavior; transparency, opacity, and cube shape are not used as a proxy for the flow gate.

The path is continuous from 1.8 to 1.12.2: all dry horizontal neighbors use the same `blocksMovement()` rejection before the below read. There is no side-only contribution change and no interval boundary indicated by the checked stable patches.

## Caller and coordinate dependency

The source geometry in the supplied companion was retraced in both 1.8.9 and 1.9. For a standing player at `(0.5,64,0.5)`, the player source sets size to `(0.6F,1.8F)`. `checkWaterCollisions` passes `shape.grown(0,-0.4F,0).contract(...)` to `World.applyLiquidDrag`. With feet at Y=64, this bounds Y to `[64.401,65.399]` and X/Z to `[0.201,0.799]`.

The 1.8.9 world loop computes exclusive maximum Y as `floor(maxY + 1.0) = 66`; the 1.9 loop uses `ceil(maxY) = 66`. Both visit only Y=64 and 65. The accepted level-1 water at `(0,64,0)` passes its liquid-top check (`66 >= 65 + 1 - (1+1)/9`), while its dry upper cell is not water. The lower source at `(1,63,0)` is outside the drag scan; it can only contribute through the distinct side-neighbor branch in `getFlow`. This valid geometry isolates the gate. The player-specific `hasLiquidCollision()` is `!abilities.flying` in both releases, and the world adds normalized flow times `0.014` only when that predicate is true.

With dry full glass on the east side, the material returns true and suppresses the one-below read. Substituting air admits that read; for center level 1 and lower source level 0, its weight is `0 - (1 - 8) = 7`, giving positive X as the only nonzero direction. This consequence is supported by the source path; no runtime claim is made.

## Limits and disposition

This acceptance covers only the specified ordinary side and one-below gate for dry full `glass` and `stained_glass`. Panes, falling-liquid side-face `isSolid()` handling, waterlogging, modern-only blocks, generalized material rules, and other water-current mechanics remain outside scope. No tests, builds, decompilation, implementation, or runtime execution occurred.

The report's bounded claim is source-confirmed and its applicability dependency is closed: **ACCEPT**. This does not constitute full-pair discovery coverage or runtime movement-parity acceptance.
