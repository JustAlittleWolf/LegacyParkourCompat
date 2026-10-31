# Erratum 3 — Shulker shape and current-box border query

This erratum updates the open-callsite wording in the original census; all earlier artifacts remain immutable.

## Shulker box provider

The paired `ShulkerBoxBlock.getShape` callsites both pass the block entity's local progress-dependent AABB to `Shapes.create`. The geometry formulas are equivalent for equal raw progress: 1.16.5 expands the unit block bounds toward `0.5F * getProgress(1.0F) * direction.step`; 1.17 calls `Shulker.getProgressAabb(direction, 0.5F * getProgress(1.0F))`, which starts from a unit `AABB` and applies the same one-sided expansion. Both use the same float progress input and block facing; `getProgress(1.0F)` is the current progress.

In the vanilla block-entity state producer, progress begins at zero and changes in 0.1F increments/decrements, clamped at zero/one. The serialized Shulker box payload saves items/loot only; loading does not inject an arbitrary tiny progress. At progress zero both sides return the exact unit box. At every positive vanilla progress the extension is at least roughly 0.05 block, so the extending bound falls beyond `findBits`'s `[-1.0E-7,1.0000001]` coordinate guard on the extended axis. Both versions therefore retain the same exact AABB coordinates in their continuous fallback. No changed provider VoxelShape follows from this encoder transition for equal vanilla progress/facing inputs. This is a source-domain closure, not a runtime tick test; arbitrary reflective mutation of the private progress field is not an input domain claimed here.

## CollisionSpliterator current-box border tests

The `worldBorderCheck` callsites `Shapes.create(aABB.inflate(1.0E-7))` and `Shapes.create(aABB.deflate(1.0E-7))` use the source entity's current AABB, not the displacement-expanded block query. The separate `Entity.collide` border inclusion call uses the same current AABB deflated by `1.0E-7`. For all LocalPlayer poses, horizontal width is 0.6F or 0.2F; adding/subtracting `2.0E-7` leaves both X and Z extents far from every supported dyadic grid width. `findBits` therefore fails on X/Z in both releases, so these AABB conversions preserve identical exact endpoints in both releases at every position. There is no `Shapes.create(AABB)` encoding difference in these current-box border predicates.

This does not independently close every predicate outcome of the full border callback. `WorldBorder.getCollisionShape()` itself changed at 1.17, and its contribution to `worldBorderCheck`'s `isOutsideBorder`/`isCloseToBorder` Boolean ANDs is a separate provider-shape dependency. Keep that border-filter stream consequence open unless bound to the existing border response evidence by a separate exact caller audit. The previously accepted `Entity.collide` border witness is a distinct direct-border caller and is not silently substituted for this callback path.

## Updated local-player dispositions

- **Paired no-difference for `Shapes.create(AABB)` encoding:** EntityGetter entity-provider AABBs (see Erratum 2); `Entity.isColliding` block-ack AABB (see Erratum 1); ShulkerBoxBlock's vanilla progress-domain provider AABB (this erratum); current player AABB conversions in `Entity.collide` and `CollisionSpliterator` (this erratum).
- **Conditionally different, independently reviewed:** displacement-expanded block-provider query AABB through `CollisionSpliterator.<init>`, with the moving-piston collision-axis witness in `../collision-query-review/20261031-short-pose-addendum.md`.
- **Open provider consequence:** `CollisionSpliterator.worldBorderCheck` interaction with the version-changed WorldBorder shape; the query AABB encoding is equal, but the border-shape Boolean outcome needs its own direct caller disposition.

The exact `Shulker.java` and `EntityDimensions.java` source hashes have been added to `semantic-source-hashes.tsv`; all 57 source rows match their publication manifests, including the 1.17 embedded provenance `sources` map. No product code, wiki/backlog, builds/tests, gameplay, source generation, or runtime comparison was used.
