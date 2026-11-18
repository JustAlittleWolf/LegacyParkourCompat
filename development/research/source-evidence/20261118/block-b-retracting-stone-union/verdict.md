# Retracting Stone source alias and cutover audit

## Bounded question and verdict

For the frozen EAST-facing, non-source Stone retraction witness, does the normal local-player WEST collision query continue to use the current/last-progress union provider from exact 1.9.1 through 1.10.2, and what does exact 1.11 do?

**Verdict:** yes, the code present in every exact release from 1.9.1 through 1.10.2 preserves the frozen query result. At `progress=0.5F`, `lastProgress=0.0F`, retracting Stone's current/last shapes union to world X `[1.5,3]`. The ordinary swept query `[2.99,3.61]` scans the moving-block owner cell at x=1, strict overlap admits the union, and westward `dx=-0.02` clips to `-0.01`. The player's own box begins at x=3.01, strictly outside the push query's union ending at x=3, so direct piston push does not select this fixed witness.

Exact 1.11 retains the old union-returning getter, but standard `World.getCollisions` now dispatches through `MovingBlock.addCollisions` to `MovingBlockEntity.addCollisions`, which uses only `progress`. At `.5F`, the standard query sees current world X `[1.5,2.5]`, which does not intersect the swept query; owner x=1 is still scanned. The fixed query therefore no longer clips at x=3. This is the first stable checkpoint in this comparison with the current-only ordinary collision path, not a claim about unexamined versions between 1.10.2 and 1.11.

This audit addresses provider, broadphase, strict-overlap and fixed-query response only. It does not establish event/tick scheduling or a multi-tick trajectory.

## Frozen inputs and exact source identity

The prior accepted 1.9 source is `domain-b-old-stone-retraction-source/source.md`, SHA-256 `df7c99bc2c5f67396184ace0a7d8204540ab8ea6dd30e36a3e45c376653535b5`. The source-only question supplied for this audit has SHA-256 `f4d5f2051e28ab3f52f60994f9ee215cad0ef1bb7eb826e7e670cae51bf24f2a`.

I checked canonical `decompiled_minecraft/<exact-version>/ornithe-feather/` source only. I checked both legacy root sidecars and mapping-local provenance. Contrary to the earlier audit's provenance gap note, releases 1.9.1, 1.9.2, 1.9.3, 1.10 and 1.10.1 each have a mapping-local `.decompile-provenance.json`. Its `inputs` name the exact Feather metadata URL and exact `feather-gen2-<version>+build.2-mergedv2.jar`; its `sources` map matches current source bytes for inspected members. Thus all five are provenance-backed and need not be split out as unsupported aliases.

| Exact release(s) | Provenance evidence | Raw SHA-256 |
| --- | --- | --- |
| 1.9 | mapping-local `.decompile-provenance.json`, `versionId=1.9`, Feather build.2; source entries checked in frozen input | `8daacb7af8f210e716ee4b34c43b8a5d6f6b4b19831d2fd725cbe16ecf330a1a` |
| 1.9.1 | mapping-local `.decompile-provenance.json`; exact `1.9.1` metadata and build.2 mergedv2 input | `e9fb0da6511597e2de953a143f9570b82243e5b9504603686e557f06271da64a` |
| 1.9.2 | mapping-local `.decompile-provenance.json`; exact `1.9.2` metadata and build.2 mergedv2 input | `9928b467deb35c7e7f891b4fee1627a68feecdf233c2076909fd4834b0380510` |
| 1.9.3 | mapping-local `.decompile-provenance.json`; exact `1.9.3` metadata and build.2 mergedv2 input | `43325cdd53ca34b88ee9af8464782b323a948b98c8a556c4761f1e5737b84c3a` |
| 1.9.4 | root `ornithe-feather.provenance.json` (`versionId=1.9.4`, namespace `ornithe-feather`, exact Feather build.2 artifacts) plus root `ornithe-feather.sources.sha256` | provenance `5c913715bed763369ceb5fd171cab3e63c43298023078c3683f96c7ea8fed3cd`; source manifest `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19` |
| 1.10 | mapping-local `.decompile-provenance.json`; exact `1.10` metadata and build.2 mergedv2 input | `df266e2c3009dd1a873c1665c30fe388078ed1c6227668e10cde87409a18c6da` |
| 1.10.1 | mapping-local `.decompile-provenance.json`; exact `1.10.1` metadata and build.2 mergedv2 input | `a9a50497930848bf0ddec732b6d424e947f643a8fa6290e01425ac2bbfce219f` |
| 1.10.2 | root `ornithe-feather.provenance.json` (`versionId=1.10.2`, namespace `ornithe-feather`, exact Feather build.2 artifacts) plus root `ornithe-feather.sources.sha256` | provenance `13b208320bd6316d4b20eb0733f226cae151cae0ffd55ef3331a97223114adf6`; source manifest `91b0f478acb7b6f13463c35b268a30d2806f583402631a56f54ce2eb70d1ec71` |
| 1.11 | root `ornithe-feather.provenance.json` (`versionId=1.11`, `resolvedVersionId=1.11`, namespace `ornithe-feather`, exact Feather build.2 artifacts) plus root `ornithe-feather.sources.sha256` | provenance `6f775c806fd1401a4d1756e9ef88da8d73185fe1d13598235f85c424405f4c40`; source manifest `8b3e5da58494f1dba77410960753b0bf646425a1ada491d8242cc71e6c7a59ca` |

For each audited release, the selected current file hashes matched its mapping-local `sources` entry or root `ornithe-feather.sources.sha256` entry. Source-hash groups below document actual byte identity; where code is the same but surrounding classes differ, the release remains separately pinned by its provenance and source manifest.

| Release(s) | `MovingBlock.java` SHA-256 | `MovingBlockEntity.java` SHA-256 | `WorldChunk.java` SHA-256 | `Box.java` SHA-256 |
| --- | --- | --- | --- | --- |
| 1.9, 1.9.1, 1.9.2 | `a5b2584bc575dc888f1d32d9d4b683b91fc8b145ee079e66ff4722718fc8906f` | `7ad3426bb74c9e69b4f521d20e045b1dc1eafa9b725daf0e2c0b9b718bc2683c` | `b98bccf16277a5a62b09ac2e77db7d77e098a62d8029c03a44fbed62e01ff267` | `cb0f30c72b7925fa0c0b133dffd051cf8260f4321f7c4a093c64ef5a5f500e62` |
| 1.9.3, 1.9.4 | `4f84cdf7c0d88c51ca065299c8755341d01b126d62c282c31fc0fa46bde1daf6` | `50eb847d3ff4ff78441057f397c43e15fb894177f7c5a736a61c22a80f1de467` | `eb5c4b9ecefdcc17cee106fbfc4c3eee0768e88e4bb36e503fccae852cb276e6` | `055d57e1e555cc378fd1d7ea14d57036190ae335238175d284b969f439abe198` |
| 1.10, 1.10.1, 1.10.2 | `4f84cdf7c0d88c51ca065299c8755341d01b126d62c282c31fc0fa46bde1daf6` | `720e1305c494a3a316b21beef823158c435826f50383778a5bd00ad0e4ff6ac1` | `dcfcda41f28cc205f17a547c69efdf70aede8f4770a43101d20ead08e8ed86b5` | `f529ef075bd933b88e3bace9020a5e23cb18b65e816154520f10bd2f26f61155` |
| 1.11 | `35799c009f0e01655622c1939e6b35342789c5abd19016c5a09528b3fac9e620` | `0185504476d365cf05c91365e692497f618a1e536fc311d09211b7caefe8aa7a` | `b80c4962661b31c9015faa9bb3d42e390528b59473956ba20e424c76625b844f` | `f788b8146b14f299ccb58ea0854609f845c298a963295d503de2e30e15d66f3a` |

The ordinary movement dispatch classes are also source-manifest-backed at each release. `Entity.java`, `LivingEntity.java`, and `LocalClientPlayerEntity.java` hashes are byte-identical within each group: 1.9.1/1.9.2 are `74d3ac34d7e6355c8fdcdd777dcd95ba9ef4626718c8772c1b58d0e28419e42a`, `d8824f670037a88753c428c27213989fc5173b1d7c638f498f73395c145b6fd9`, `de9ad48bcfb2705ef7a9a63e9401c1742aead183e1e9c2d13f8fe40cca0139eb`; 1.9.3/1.9.4 are `bcd7fa2206bf8d7271102f6da7fe2771f96dbe22ec79dab9f2101a3e29c17ef0`, `bbb7703f18fd5da05c4e4a43a77ea644b388e63c01d34166d308ea52054be4e5`, `8aaf711948b7602c2e6c015a37e36ed06073d39727d999d80480b4910b704f5d`; and 1.10/1.10.1/1.10.2 are `05da145effa19a6ef7934cc276e89226373b67c12f4ce89a8ce2183f29039f77`, `d40dd476b6b68c6ce45b4202823475deb546ecda2284da330ff6724b33815e82`, `a9637065f21ad67464eb5c204c74ebf228c3bb0da8a96ddf4ae73c0490fed443`. At 1.11 the corresponding hashes are `3209feaacd1e798b521a193eeb081d92c96881a70a18254ef24a58e911a4fd97`, `1f2e31fa6a905ccc2a51a0338f2aaf448f2b0569f28644e8205d864e570d9b2b`, and `65c2747bd8c70def6be7f41f624d4c9493342b39ae7bed7967f9ff63608f59ed`.

## Exact old-provider continuity: 1.9.1–1.10.2

`MovingBlockEntity.getAmountExtended` is `extending ? tickDelta - 1.0F : 1.0F - tickDelta` in the audited old-provider sources. `getShape(world,pos)` unions `getShape(..., progress)` and `getShape(..., lastProgress)`; each samples the moved state's shape then translates it along `facing` by the derived amount. The independently frozen fixture fixes `extending=false`, `facing=EAST`, owner `(1,0,0)`, and progress pair `(.5F,0.0F)`. Retraction amounts are therefore `.5F` and `1.0F`. For full Stone shape `[0,1]`, the two world X boxes are `[1.5,2.5]` and `[2,3]`; their axis-aligned union is `[1.5,3]`.

The `MovingBlock` collision-shape getter supplies this moving entity shape, and inherited block collision admission translates it by the owner position and appends it only when the query box strictly intersects (`shape.intersects(box)`). The ordinary movement swept box has minX `2.99` from player minX `3.01` and `dx=-.02`; the union reaches x=3, so it overlaps by `.01`. In 1.10.2 `World.getCollisions` starts its x loop at `floor(shape.minX)-1` and loops while x is less than `ceil(shape.maxX)+1` (`World.java:903-918`); for `[2.99,3.61]` this includes the moving-block owner x=1. This preserves the same owner-cell/broadphase path through 1.10.2.

The independent direct-push query uses the same current/last union translated to world coordinates (`MovingBlockEntity.java:91-93` in 1.10.2). `WorldChunk` only returns candidates whose entity box `intersects(bounds)`; `Box.intersects` uses strict `<` / `>` checks on all axes (`1.10.2 WorldChunk.java:676`, `Box.java:203-209`). The player minX 3.01 is beyond union maxX 3, so it is excluded from direct push. The source bytes for `WorldChunk.java` and `Box.java` are hash-identical across each alias group in the table, and every exact source hash was checked against its own manifest. This is independent of the ordinary clip, which the player can receive through the normal swept collision list.

For a negative X clip, the obstacle maxX 3 minus player minX 3.01 is `-0.01`; because the requested `-0.02` crosses that edge, the normal X clip limits it to `-0.01` (1.10.2 `Entity.java:514-528`). This is a fixed-query arithmetic result, not a claim about when progress and player movement run in one tick.

## Exact 1.11 dispatch cutover

In 1.11, `MovingBlock.getCollisionShape` still returns `movingBlockEntity.getShape(world,pos)`, and that getter still returns the current/last union. The standard world collision enumeration, however, calls `BlockState.addCollisions` in `World.getCollisions` (`World.java:960-995`). `StateDefinition.addCollisions` forwards to its block implementation (`block/state/StateDefinition.java:374-375`); `MovingBlock` now overrides that hook and routes to `MovingBlockEntity.addCollisions` (`MovingBlock.java:134-140`), bypassing the getter for this standard path.

For the non-source fixture, `addCollisions` selects `movedState` and computes `facing * getAmountExtended(progress)` only (`MovingBlockEntity.java:295-316`); it does not read `lastProgress`. At `.5F` that provider box is world X `[1.5,2.5]`; it cannot intersect the swept box whose minX is 2.99. `World.getCollisions` retains the same floor/ceil expanded coordinate scan, so owner x=1 is included. Thus the change is collision-shape admission at the moving-block cell, not loss of that cell from broadphase. The old union getter remains available to other callers.

The ordinary player path uses `Entity.move`'s swept box and clips against the collision list (1.11 `Entity.java:523-539`). No piston noclip state is relevant to this standard query for `source=false`; the source-head branch is skipped. This audit asserts only the standard collision enumeration's current-only behavior.

## Source references and audit limits

Primary classes under `decompiled_minecraft/<version>/ornithe-feather/net/minecraft/`:

- 1.9.1 / 1.9.3 / 1.10.2 `block/entity/MovingBlockEntity.java`: retract amount, union getter, direct-push candidate query.
- 1.10.2 `world/World.java`, `block/Block.java`, `world/chunk/WorldChunk.java`, `util/math/Box.java`, `entity/Entity.java`: swept owner-cell scan, strict provider admission, strict direct-push eligibility and negative-X clip.
- 1.11 `block/MovingBlock.java`, `block/entity/MovingBlockEntity.java`, `world/World.java`, `util/math/Box.java`, `entity/Entity.java`: override dispatch, current-progress provider, expanded owner scan and ordinary player query.

The manifest-backed exact identities make the code claims supported for every requested alias through 1.10.2. No alternate mappings were consulted. This does not cover other piston directions, other moved states, source-piston behavior, other collision callers, subsequent releases, runtime scheduling, or end-to-end movement.
