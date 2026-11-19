# Independent source verdict: piston stair cutover

**Verdict: ACCEPT**, bound to claim commit `f27a45b0e9daf6398bcef4cb3de9ba1b5b484200`, path `stair-cutover.md`, payload SHA-256 `768bf5efcc6b8b95712e45b76be31d522a5b29807cb79c09455b3db07ad8062c` (3,956 bytes).

## Scope and conclusion

The bounded bottom-north straight cobblestone-stair fixture is supported and does not interpenetrate the ordinary stair collision at the relevant position. For the second eastward half-step, 1.10.2's inherited full-cube outline reaches the player through the unioned moving shape and issues the direct `+0.36 X` request. In 1.11, 1.11.1, and 1.11.2, the mover samples the actual stair component boxes. The base-box movement area only face-touches the player's feet at `Y=0.5`; the north tread misses the player's `Z` interval beginning at `0.65`. Strict `Box.intersects` therefore returns false for both components, the accumulated amount remains zero, and the positive-amount guard skips `Entity.move`.

The broad candidate query still includes the player in every named release. 1.11 uses `moveBox(new Box(BlockPos.ORIGIN))`; 1.11.1/.2 use `getBounds(list)`, whose maxima initialize to `1.0` on all axes. Thus the partial-depth stair boxes do not shrink the unit-cube query bound. This verdict establishes the source-level response for this fixture only; it does not assert a measured runtime trajectory or a cutover for other states/layouts.

## Exact source evidence

All paths below are relative to `decompiled_minecraft/<release>/ornithe-feather/` in the primary local checkout. The source trees are the named canonical Feather outputs. Their legacy trees have no `.decompile-provenance.json`; the hashes below bind the reviewed source bytes and do not claim artifact provenance.

- **1.10.2** — `Block.java:733,868` registers `cobblestone` as `block` and constructs `stone_stairs` from `block.defaultState()`. `StairsBlock.java:56` defaults to north/bottom/straight; `addCollisions` at `67-72` supplies its component collision boxes. `Block.java:277-279` supplies inherited full-cube `getShape`. `MovingBlockEntity.java:80-88,91-147,166-183` unions current/previous outline shapes, advances by `0.5F`, computes the positive-X amount from `box.maxX - player.minX`, adds `0.01`, and invokes ordinary `Entity.move`. `Entity.java:165-170` gives the ordinary `0.6F` by `1.8F` dimensions.
- **1.11** — `Block.java:738,873` registers the same block relationship; `StairsBlock.java:55,66-87,90-103` gives the same default and north straight boxes. `MovingBlockEntity.java:116-169,183-208` uses `d=progress-this.progress`, broad-queries the unit outline, translates each actual component box by the old extension amount, tests it with `intersects`, and calls `move` only behind `if (!(e <= 0.0))`. `Box.java:245-250` uses strict inequalities on all three axes. The tick caller computes `f = progress + 0.5F`, calls `moveEntities(f)`, and then stores `progress = f` (`MovingBlockEntity.java:262-267`).
- **1.11.1 / 1.11.2** — `Block.java:738,873`, `StairsBlock.java:55,66-88,90-103`, and `Box.java:245-250` retain the same registration, component boxes, and strict overlap test. In each `MovingBlockEntity.java:116-169,171-188,262-267`, the broad bounds start with maxima `1.0` and remain the unit cube; the same per-box check and positive-amount guard reject the stair witness. Both versions pass `forceShape=true` when obtaining the boxes (`MovingBlockEntity.java:120`), so the stored straight shape is sampled directly.

Reviewed source SHA-256 identities:

| Release | Block.java | StairsBlock.java | MovingBlockEntity.java | Box.java |
|---|---|---|---|---|
| 1.10.2 | `1971dbc284d511e2ed366f77bc77fd8cd07174baad3e7732e908d3daeb640c01` | `ee143aced2a6902563cddbae77e78d1d1a512244f278987684ebe5bcacba37cb` | `720e1305c494a3a316b21beef823158c435826f50383778a5bd00ad0e4ff6ac1` | `f529ef075bd933b88e3bace9020a5e23cb18b65e816154520f10bd2f26f61155` |
| 1.11 | `3fe8fdabe68da83fcfa01c0e6b8f5272fe81d71f6bf553506bdcef54757b0488` | `f1ec8567bac6140dd43770d4adcd9b589c25807845c8bac3b01a4b5b61289391` | `0185504476d365cf05c91365e692497f618a1e536fc311d09211b7caefe8aa7a` | `f788b8146b14f299ccb58ea0854609f845c298a963295d503de2e30e15d66f3a` |
| 1.11.1 and 1.11.2 | `ab873436b24487ab0ee8055bf7a478349c7f7978398aea8e387796a6c820996a` | `5df1782da6eb7acdb9935b3910a0484d6417661bfd6230ecf81df0de6d782627` | `a55ee14227fe6ac0932283cba0414e946ba8e2637f543b3a3fe13d4e45b7b16e` | `f788b8146b14f299ccb58ea0854609f845c298a963295d503de2e30e15d66f3a` |

This finding is not a full-pair coverage audit.

## Provenance correction (follow-up audit)

The preceding caveat that these trees lack provenance manifests was incorrect: each release has sibling `ornithe-feather.ready.json`, `ornithe-feather.provenance.json`, and `ornithe-feather.sources.sha256` files in `decompiled_minecraft/<release>/`. I verified every ready marker reports `status=ready` and the matching exact release and `ornithe-feather` mapping; each ready marker's `sourceManifestSha256` matches its manifest bytes. The five cited member files (`Block.java`, `StairsBlock.java`, `MovingBlockEntity.java`, `Box.java`, and `Entity.java`) match their entries in each release's source manifest. For 1.11 and 1.11.1, the ready marker's provenance hash also matches; 1.10.2 and 1.11.2 ready markers omit that field, but their sibling provenance files are present and identify the matching release and mapping.

| Release | Ready marker SHA-256 | Provenance SHA-256 | Source manifest SHA-256 |
|---|---|---|---|
| 1.10.2 | `4c6d236e1d06ffc365452c48e73dfa2bf2414bbf4ea67a24a7010c5583906a2d` | `13b208320bd6316d4b20eb0733f226cae151cae0ffd55ef3331a97223114adf6` | `91b0f478acb7b6f13463c35b268a30d2806f583402631a56f54ce2eb70d1ec71` |
| 1.11 | `3c4102230dba0f9d2d07f752bbd2a19d2717375f6e31fb618c0450b7fd82bd45` | `6f775c806fd1401a4d1756e9ef88da8d73185fe1d13598235f85c424405f4c40` | `8b3e5da58494f1dba77410960753b0bf646425a1ada491d8242cc71e6c7a59ca` |
| 1.11.1 | `454036a978074d3a34359dfebf03d6ef3e3335d1318b88d6b17e4f1f5ab566cf` | `0f6c92b8fbf4b1053a26a4cb61dc8f20c695e74a1963b49d911b8c7fbf01a055` | `0d5f4697d4e4ce53b36d0dbfec89d44830eb76753a6643b49e42f0857753066d` |
| 1.11.2 | `ceabfb266993567aab8c867525365e18aa0a0d7ba5a2cb7b6cfcc83d21318c40` | `dcdb65e1361297a584c5b93f581b1b539911911c6cec1a203740baa5379732c6` | `d908c2af76598ad26999563bfef0089f1d3c8c32483eee6b108f0defa2773df0` |
