# Independent review: farmland suffocation LocalPlayer witness

## Verdict: REVISE

The conditional Farmland witness and all-four probe calculation are source-supported. The earlier witness wording error has been corrected in the reviewed current bytes; one canonical-draft provider-class attribution remains inaccurate. Correct that attribution before binding ACCEPT.

Reviewed current immutable inputs:

- Witness `.native-source/20261030/1152-1165-finish43/block-suffocation-player-witness/witness.md`, SHA-256 `c8277296919e394e4861f2d6d5264e09f7effe54f79db412d6e0968047e8df97`. This current revision correctly states the final result as `+0.1`.
- Canonical draft `.native-source/20261030/1152-1165-finish43/canonical-findings/1.16.1--1.16.2/farmland-suffocation-localplayer-escape.md`, SHA-256 `d091d88c826ea08e886ef82e9d47555d573132b751b219fbe524b3629fe7268b`.

## Four-probe recomputation

For each call, `d/e` are the sampled X/Z fractions. The method tests `WEST, EAST, NORTH, SOUTH`; negative-axis distance is the fraction and positive-axis distance is one minus the fraction. With every horizontal neighbor clear, strict `<` retains the first equal-distance candidate:

| Probe `(d,e)` | Distances `(W,E,N,S)` | Chosen direction | X write |
|---|---|---|---|
| `(.29,.71)` | `(.29,.71,.71,.29)` | WEST (wins tie with SOUTH) | `-0.1` |
| `(.29,.29)` | `(.29,.71,.29,.71)` | WEST (wins tie with NORTH) | `-0.1` |
| `(.71,.29)` | `(.71,.29,.29,.71)` | EAST (wins tie with NORTH) | `+0.1` |
| `(.71,.71)` | `(.71,.29,.71,.29)` | EAST (wins tie with SOUTH) | `+0.1` |

All four writes are X-axis writes, preserving Y and Z. Starting from `(0,0,0)`, the final new value is **`(+0.1,0,0)`**. The current witness and canonical draft both correctly state the new final result as `+0.1`; an earlier witness revision had said `-0.1` in its verdict sentence, and that typo is resolved in the current bound hash.

The geometry and old result check out: `[0.2,64.8,0.2]–[0.8,66.6,0.8]` intersects the 15/16-high Farmland shape in a vertical interval of `0.1375`; its current-cell query intersects the shape. Neighbor-cell queries are deflated away from the Farmland cell and see only air, so all new probes select a clear neighbor. Old `blocked` checks Y cells 64, 65 and 66 and `Player.freeAt` returns true for all: Farmland's old predicate is false and the other cells are air; the old response writes no escape impulse.

## Exact registration boundary

The three registrations are absent from the 1.16 and 1.16.1 `Blocks.java` bodies and first appear with `.isSuffocating(Blocks::always)` in **1.16.2**, persisting in 1.16.3, 1.16.4 and 1.16.5:

- Farmland: 1.16 / 1.16.1 registration lines 495–498, no suffocation setter; 1.16.2+ lines 494–503, setter at 502.
- Soul Sand: 1.16 / 1.16.1 lines 724–735, no setter; 1.16.2+ lines 730–742, setter at 740.
- Grass Path: 1.16 / 1.16.1 lines 1951–1953, no setter; 1.16.2+ lines 1965–1970, setter at 1968.

The new four-call `moveTowardsClosestSpace` LocalPlayer route also begins in 1.16.2: 1.16 and 1.16.1 retain `checkInBlock` at `LocalPlayer.java:682–685`; 1.16.2–1.16.5 call `moveTowardsClosestSpace` at `:657–660`. The canonical 1.16.1→1.16.2 boundary is therefore correct. The older 1.15.2→1.16.5 witness's suffocation provider difference is real but should not be used to claim a 1.16 introduction.

## Needed wording correction

The canonical draft says that in 1.16.1 the inherited `Block.isSuffocating` requires a motion-blocking material and full collision shape. In 1.16.1, the call dispatches through `BlockBehaviour.BlockStateBase.isSuffocating` to the default predicate held by `BlockBehaviour.Properties` (`BlockBehaviour.java:633–635, 786`), which is the same formula: `material.blocksMotion() && blockState.isCollisionShapeFullBlock(...)`. In 1.15.2 the implementation is indeed `Block.isSuffocating` (`Block.java:285–286`), but that is not the old endpoint of this canonical claim. Change the canonical wording to identify the 1.16.1 `BlockBehaviour.Properties` default and its `BlockStateBase` dispatch; retain the 1.15.2 citation only as earlier supporting evidence if desired.

## Manifest verification and source identities

Every relevant source inspected below rehashed to its own release's source manifest. 1.16, 1.16.1, 1.16.2 and 1.16.5 use their parent `mojmap.sources.sha256`; 1.16.3 and 1.16.4 use the exact mapping-root `.decompile-provenance.json` embedded `sources` map. The manifest SHA / provenance-file SHA values checked were:

- 1.16: `e3c23e94ac985c25dec0c768aed17b443ba4823662cf1e152235c99756ae5d4a`
- 1.16.1: `76fdd121070c2012f48cbdf5cf0c78490116afc94117c30b0858eedcd6ab9754`
- 1.16.2: `10d312ca29ee48e25f4727adafb9fcfbd741dcebe81b343d3dcffa071952a7a7`
- 1.16.3 embedded provenance: `513271804ead6757345d305717e7e0bfc1641434c35a3b254f287bd8fd281498`
- 1.16.4 embedded provenance: `1e04555c915527a398a2c7150679fb24d056f54bd680261e3c1dd55da821147c`
- 1.16.5: `9499f2611d0e6dde37cb635f1a28a591416382d6bc188b37d5f99d2b2a20823b`

For 1.16–1.16.5, the registered-block, `BlockBehaviour`, `FarmBlock`, `SoulSandBlock`, `GrassPathBlock`, `LocalPlayer`, `CollisionGetter`, and `Player` files each matched their own release manifest/map. The 1.15.2 `Blocks`, `Block`, `FarmBlock`, `LocalPlayer`, `Player`, and `CollisionGetter` files also matched the previously pinned 1.15.2 source manifest SHA `cb7fd93f8730d8b2813857744f8572060535c7de44fcee86222acd1ceb6f43e7`.

The important fingerprints include:

- 1.16.1 `Blocks.java`: `61f18f903f7d96731bbc3cac544a8614f55fddac872dc0b0fee5a53511c313a9`; 1.16.2–1.16.5 `Blocks.java`: `3b39d5cc4cd22f146ed3195aa30cbb9fdfca49f63783fabf9924a6ce7795fa12`.
- 1.16.1 `LocalPlayer.java`: `ee20523adfdb82f3753fa43312af170f34c4f4aa78570631c61bc8c0820dfb8b`; 1.16.2–1.16.5: `6011569e766bb1568609147be9aa14e9c08c51948e3d3a60fd066e848f6a8c2b`.
- 1.16.1 `BlockBehaviour.java`: `1b524b5c45e3be4397dd680b9bd12c937497b9088e623428cf6387406018ef7f`; 1.16.2–1.16.5: `c62e2f07094e5a7bcad00cbae7d24497bf636cc740232a1472f4128a472def8b`.
- 1.16.2–1.16.5 `FarmBlock.java`: `1298367814491ef92fe8c0f43f1c14e173e54182859c3a68fa9422b60d23c810`.

No canonical source, product, wiki, backlog, build, test, decompilation or runtime files were changed or used.
