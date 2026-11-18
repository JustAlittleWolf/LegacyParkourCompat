# Water body-contact scan aliases: 1.10 through 1.11.2

**Assignment:** Source-only extension of the previously accepted 1.8.9–1.9.4 liquid upper-bound finding. Exact releases checked: 1.10, 1.10.1, 1.10.2, 1.11, 1.11.1, 1.11.2. This closes only the named body-contact scan/provider aliases; it does not check other providers or current behavior.

## Exact sources and publisher identity

All six trees are `ornithe-feather` (Feather Gen2, build 2). Hidden 1.10 and 1.10.1 `.decompile-provenance.json` records contain per-file source hashes; the selected `World.java` and `Entity.java` disk hashes match those map entries. For 1.10.2 and 1.11.x, the root ready markers identify the exact version and mapping, and their source-manifest SHA matches the manifest file; each selected source hash matches its manifest entry. These are local pins, not a fresh decompilation.

| Release | Publisher record | `World.java` SHA-256 | `Entity.java` SHA-256 |
|---|---|---|---|
| 1.10 | `ornithe-feather/.decompile-provenance.json`, SHA-256 `df266e2c3009dd1a873c1665c30fe388078ed1c6227668e10cde87409a18c6da` | `827fea19d95ddc0a2847859f0c288bf0b6d6dc3692feb30b52b84c60fa19d877` | `05da145effa19a6ef7934cc276e89226373b67c12f4ce89a8ce2183f29039f77` |
| 1.10.1 | `ornithe-feather/.decompile-provenance.json`, SHA-256 `a9a50497930848bf0ddec732b6d424e947f643a8fa6290e01425ac2bbfce219f` | `41fd1edc24b39a5b9a979d15d54fbd3ebf9818651628109f232fcc24c7e28d0a` | `05da145effa19a6ef7934cc276e89226373b67c12f4ce89a8ce2183f29039f77` |
| 1.10.2 | `ornithe-feather.provenance.json` SHA-256 `13b208320bd6316d4b20eb0733f226cae151cae0ffd55ef3331a97223114adf6`; ready record SHA-256 `4c6d236e1d06ffc365452c48e73dfa2bf2414bbf4ea67a24a7010c5583906a2d`; source manifest SHA-256 `91b0f478acb7b6f13463c35b268a30d2806f583402631a56f54ce2eb70d1ec71` | `888ed0e9de765def87b05c4126ecdf0b10e9dd448b4543dd1cb98211e9951646` | `05da145effa19a6ef7934cc276e89226373b67c12f4ce89a8ce2183f29039f77` |
| 1.11 | `ornithe-feather.provenance.json` SHA-256 `6f775c806fd1401a4d1756e9ef88da8d73185fe1d13598235f85c424405f4c40`; ready record SHA-256 `3c4102230dba0f9d2d07f752bbd2a19d2717375f6e31fb618c0450b7fd82bd45`; source manifest SHA-256 `8b3e5da58494f1dba77410960753b0bf646425a1ada491d8242cc71e6c7a59ca` | `f64905888809d5666a63c8e403f171c13907502b8d982c5b3b8fce192360ff47` | `3209feaacd1e798b521a193eeb081d92c96881a70a18254ef24a58e911a4fd97` |
| 1.11.1 | `ornithe-feather.provenance.json` SHA-256 `0f6c92b8fbf4b1053a26a4cb61dc8f20c695e74a1963b49d911b8c7fbf01a055`; ready record SHA-256 `454036a978074d3a34359dfebf03d6ef3e3335d1318b88d6b17e4f1f5ab566cf`; source manifest SHA-256 `0d5f4697d4e4ce53b36d0dbfec89d44830eb76753a6643b49e42f0857753066d` | `27cfaa5ff45c2d88c492fc5dae3ea4a2bb4536fd64bff8498a9a7d0cb82efb58` | `5a906de1d40a4a5ba8aedc9a6a9dc704e0ed862df177cf967fb1a9454765fa96` |
| 1.11.2 | `ornithe-feather.provenance.json` SHA-256 `dcdb65e1361297a584c5b93f581b1b539911911c6cec1a203740baa5379732c6`; ready record SHA-256 `ceabfb266993567aab8c867525365e18aa0a0d7ba5a2cb7b6cfcc83d21318c40`; source manifest SHA-256 `d908c2af76598ad26999563bfef0089f1d3c8c32483eee6b108f0defa2773df0` | `27cfaa5ff45c2d88c492fc5dae3ea4a2bb4536fd64bff8498a9a7d0cb82efb58` | `ce8104a17ce783df639cf9726e7b1cd0936563eaa7ba308603335bbe05d49440` |

The hidden 1.10/.1 source records identify the versioned Feather Gen2 `+build.2` merged mapping inputs (their exact mapping artifact SHA-256 values are respectively `ad7cf6744d81d0260c9d97ddfe8250b2fdbd54fe332d4d00266b3545d8ddf8dd` and `6e8ba44832b7b77d913962462bde55084005d5de6389232453afc0bc0339ea37`). Root provenance for 1.10.2 and 1.11.x likewise identifies versioned Feather Gen2 `+build.2` merged JAR and Tiny mappings. All selected paths were read from `decompiled_minecraft/<release>/ornithe-feather/`; no source tree was modified.

## Body-contact caller and scan

`PlayerEntity extends LivingEntity extends Entity` in all six releases (`PlayerEntity.java:96–97`, `LivingEntity.java:76` for 1.10.x; `PlayerEntity.java:96`, `LivingEntity.java:82` for 1.11.x). `Entity.baseTick` invokes `checkWaterCollisions` (`Entity.java:366` for 1.10.x; `:375` for 1.11 and `:379` for 1.11.1/.2), so the ordinary player inherits this body-contact predicate.

| Release | `Entity.checkWaterCollisions()` | `World.applyLiquidDrag(Box, Material, Entity)` |
|---|---:|---:|
| 1.10 | `Entity.java:862–878` | `World.java:1564–1606` |
| 1.10.1 | `Entity.java:862–878` | `World.java:1564–1606` |
| 1.10.2 | `Entity.java:862–878` | `World.java:1570–1612` |
| 1.11 | `Entity.java:884–900` | `World.java:1592–1634` |
| 1.11.1 | `Entity.java:934–950` | `World.java:1571–1613` |
| 1.11.2 | `Entity.java:927–943` | `World.java:1571–1613` |

Across all six caller methods, a boat mount sets `inWater=false` and skips the scan. Otherwise the inherited body box is `getShape().grown(0.0, -0.4F, 0.0).contract(0.001)`, passed for `Material.WATER`. A positive result resets fall distance, sets `inWater`, and clears fire; false clears `inWater`. The caller source changes in 1.11 from assigning `onFireTimer = 0` to calling `extinguish()`, which does not change the scan inputs or contact predicate. There is no player-flight gate in `checkWaterCollisions`. The player-flying exception is separately present in `LivingEntity.moveRelative` around lines 1334–1335 (1.10.x) and 1388–1389 (1.11.x); this is a movement branch gate, not a body-scan gate.

The six `World.applyLiquidDrag` method texts are identical. Their method-text SHA-256, UTF-8 from declaration through the closing brace, is `32a6bd91971a8da63a1fa91710c87be272fdaece8226b64b6395e2818e72e3ec`. Each method takes minima with `floor(bounds.min*)`, exclusive maxima with `ceil(bounds.max*)`, then calls `isAreaLoaded(i,k,m,j,l,n,true)` and returns false when the area is not loaded. It iterates half-open ranges (`o < j`, `p < l`, `q < n`), selects block states whose material is the requested material, and applies the liquid surface check `l >= p + 1 - LiquidBlock.getHeightLoss(level)`. It releases the pooled mutable position, and only a positive accumulated drag vector plus `entity.hasLiquidCollision()` changes velocity. The bounds, loaded-area gate, loop exclusivity, surface gate and return/drag behavior match the 1.9/1.9.4 scan already recorded in `development/research/source-evidence/20261028/water-flow-scan/comparison.md` and `alias-comparison.md`.

`Entity.checkWaterCollisions` method-text SHA-256 is `bb008e529fb656f40bc3d62647da8bed0a02acc8a124463eb4ed9baa7217517f` for 1.10, 1.10.1 and 1.10.2. It is `8f4a0fdf17238649603c274af68c1c846d08f7331e9146a64f3d3ea793111b50` for 1.11, 1.11.1 and 1.11.2; the difference is the fire-clearing operation noted above. Its boat gate and body-box input are unchanged throughout.

## Concrete upper-edge witness

For the specified standing player position `y=62.60100005364418`, the transformed body box has `maxY=64.0` after the grow and contract calls. `ceil(64.0)` makes the scan's exclusive vertical endpoint `l=64`; the loop visits `p < 64` and never tests the water block at Y=64. Thus an otherwise loaded level-1 water block occupying only Y=64 does not make `applyLiquidDrag` return true for this body box, so this path does not set body `inWater`. This is solely the body-presence result: it does not establish flow-vector behavior or say whether an independent fluid-flow path affects the player.

## Terminal scope and audit gate

This documents source coverage for the six requested 1.10–1.11 aliases of the known body-scan cutoff. It does not re-prove 1.8.9/1.9/1.9.4, establish a new version boundary, inspect other fluid/current providers, or establish runtime parity. This is a source-author draft; the independent source audit and any separate acceptance/coverage record remain open gates.
