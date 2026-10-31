# Independent review: Soul Sand and Grass Path LocalPlayer escape

## Verdict: ACCEPT

Bound to canonical draft SHA-256 `4cebd421b1627cbb8007589bb89292229a4d82a15f03715462c8f6cd681fa87d`. The source-confirmed registration/consumer change and stated same-input response are correct for both named blocks, conditional on the exact pre-tick box and surrounding-air assumptions. This review does not claim producer or ordinary-walking reachability.

## Sources and exact boundary

The 1.16.1 `Blocks.SOUL_SAND` and `Blocks.GRASS_PATH` registrations have no suffocation override. The registrations first add `.isSuffocating(Blocks::always)` in 1.16.2 and retain it in 1.16.3–1.16.5. The four `LocalPlayer.aiStep` calls switch from `checkInBlock` to `moveTowardsClosestSpace` at the same 1.16.1→1.16.2 boundary. This exact cutover is confirmed from each release's source; the canonical wording does not claim the change began at 1.16.0.

In 1.16.1 the default predicate is the `BlockBehaviour.Properties` suffocation predicate (`BlockBehaviour.java:786–787`), dispatched by `BlockBehaviour.BlockStateBase.isSuffocating` (`:633–635`): motion-blocking material **and** full collision shape. Both shapes are partial, so the old suffocation result is false. In 1.16.2, the registered always-true predicate makes the new consumer treat the nonempty collision shape as suffocating.

- Soul Sand `SHAPE` is `Block.box(0,0,0,16,14,16)`; `getCollisionShape` returns it (`SoulSandBlock.java:18, 25–26`) in both endpoints. On the specified query its vertical overlap is `64.875 - 64.8 = 0.075`; after `1.0E-7` deflation it remains positive.
- Grass Path assigns `SHAPE = FarmBlock.SHAPE` (`GrassPathBlock.java:18`); `FarmBlock.SHAPE` is `Block.box(0,0,0,16,15,16)` (`FarmBlock.java:29`) in both endpoints. Its vertical overlap is `64.9375 - 64.8 = 0.1375`, also positive after deflation.

The box dimensions agree with a 0.6-wide, 1.8-high player at `(0.5,64.8,0.5)`, so the four source probe coordinates floor to `(0,64,0)`. Current-cell queries intersect the chosen block. Deflated adjacent-cell queries are disjoint from that block and the stipulated neighboring cells are air, so each new candidate direction is clear. Old `blocked` examines Y=64,65,66; the partial state returns false and the other sampled cells are air.

## Probe result

The new method checks WEST, EAST, NORTH, SOUTH, with strict `<`. For probe coordinates `(.29,.71)`, `(.29,.29)`, `(.71,.29)`, `(.71,.71)`, the distance tuples `(W,E,N,S)` are respectively `(.29,.71,.71,.29)`, `(.29,.71,.29,.71)`, `(.71,.29,.29,.71)`, `(.71,.29,.71,.29)`. The choices are therefore WEST, WEST, EAST, EAST; the first of each tied pair wins. Sequential X writes are `-0.1,-0.1,+0.1,+0.1`, leaving final `(x,y,z)=(+0.1,0,0)`. The paired old probes leave the stated zero vector unchanged.

## Manifest checks

Every cited source hash in the draft matched its own release's `mojmap.sources.sha256`; manifest hashes checked: 1.15.2 `cb7fd93f8730d8b2813857744f8572060535c7de44fcee86222acd1ceb6f43e7`, 1.16.1 `76fdd121070c2012f48cbdf5cf0c78490116afc94117c30b0858eedcd6ab9754`, and 1.16.2 `10d312ca29ee48e25f4727adafb9fcfbd741dcebe81b343d3dcffa071952a7a7`. Cited 1.16.3, .4 and .5 `Blocks.java` registrations also match each release's own source publication: .3/.4 embedded `.decompile-provenance.json` source maps, and .5 parent manifest `9499f2611d0e6dde37cb635f1a28a591416382d6bc188b37d5f99d2b2a20823b`.

I additionally rehashed the 1.16.1 and 1.16.2 `SoulSandBlock`, `GrassPathBlock`, and `FarmBlock` dependencies against each endpoint manifest; every entry matched. This verifies the shape heights and unchanged-shape premise used above.

No canonical source, product, wiki, backlog, build, test, decompilation or runtime files were changed or used.
