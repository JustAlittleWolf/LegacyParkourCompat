# Fluid scan preflight changes from XYZ to inflated XZ bounds

1.16.5, mojmap: `1.16.5/mojmap/net/minecraft/world/entity/Entity.java`, `updateFluidHeightAndDoFluidPushing`, lines 2641–2705.
1.17.1, mojmap: `1.17.1/mojmap/net/minecraft/world/entity/Entity.java`, `updateFluidHeightAndDoFluidPushing`, lines 2773–2846.

Exact 1.17, Mojmap, `Entity.updateFluidHeightAndDoFluidPushing` and `touchingUnloadedChunk`, lines 2771–2783 and 2838–2845, already use the later preflight.

The 1.16.5 sampler passes the deflated box's floor-minimum and ceil-maximum bounds to the six-integer `LevelReader.hasChunksAt`. That overload requires `maxY >= 0 && minY < 256` before checking X/Z. Exact 1.17 and 1.17.1 call the four-integer overload on the box inflated by one block and check X/Z only.

In both versions, an ordinary client's `ClientLevel.hasChunk` returns true, including for the wider X/Z range. When the old vertical guard rejects, the scan has no in-height cells. The ordinary refresh wrapper clears `fluidHeight` before sampling. A rejected old scan leaves depth at zero, and the new scan reads empty states outside build height and records zero. The rewrite establishes no distinct ordinary local-player movement result through that refresh. [Source comparison](../../source-evidence/20261117/block-a-fluid-height-preflight/fluid-sampler-build-height-guard.corrected-r1.md), [independent verdict](../../source-evidence/20261117/block-a-fluid-height-preflight/verdict.md).

## Loaded body column at a padded chunk edge

On a 26.2 client, the padded `EntityFluidInteraction.hasFluidAndLoaded` check can reject a loaded water column when only the padding enters a missing neighbor chunk. Exact 1.17 and 1.17.1 admit the old sampler through `ClientLevel.hasChunk` and use the actual box deflated by `0.001`. For an ordinary local standing player with a single loaded water column, the body result uses inclusive surface overlap and contextual water height, with the same-fluid-above cell in that column. This corresponds to the rejected-preflight case for those two profiles. Eye sampling and current remain on their respective paths.

Exact 1.14 through 1.15.2 use the same inset and contextual surface rule, with a virtual client area check that also admits empty chunks. Centering this loaded-column specialization at `x=15.7, z=8.5` gives the single horizontal cell `(15,8)` inside the historical body box. Its same-fluid-above lookup remains in the loaded column. The explicit 1.16–1.16.5 scan-admission change takes precedence for the normal-scale in-height case. [Exact source evidence](../../source-evidence/20261118/block-a-114-115-body-water-admission/evidence.md), [independent admission audit](../../source-evidence/20261118/block-a-114-115-body-water-admission/verdict.md), [independent height audit](../../source-evidence/20261118/block-a-114-115-body-water-admission/contextual-height-verdict.md).

The same ordinary unmounted body query and contextual height apply in exact 1.18, 1.18.1, 1.18.2, 1.19, 1.19.1, 1.19.2, 1.19.3 and 1.19.4. The compatibility fallback covers the same loaded column within the existing height limits. [Exact source evidence](../../source-evidence/20261119/block-a-118-119-body-water-admission/evidence.md), [independent audit](../../source-evidence/20261119/block-a-118-119-body-water-admission/verdict.md).

The loaded single-column body query also applies in exact 1.20 through 1.20.6 and 1.21 through 1.21.10. The unstacked source-water witness uses feet at `Y=64` (inset minimum `64.001`) with a readable cell above. The separate 1.21.11 scan-admission change retains its own path. [1.20 evidence](../../source-evidence/20261119/block-a-120-body-water-admission/evidence.md), [1.20 audit](../../source-evidence/20261119/block-a-120-body-water-admission/verdict.md), [1.21 evidence](../../source-evidence/20261119/block-a-121-body-water-admission/evidence.md), [1.21 audit](../../source-evidence/20261119/block-a-121-body-water-admission/verdict.md).
