# Independent WorldBorder collision review

## Verdict

**ACCEPT, conditionally, as a direct player-response source witness for 1.16.5 → 1.17; unchanged in 1.17.1.** This accepts a difference under equal raw client-side border/player/request inputs. It does not claim that a server or any producer normally supplies this border state, nor does it establish runtime trajectory parity.

## Equal-input witness

Use `absoluteMaxSize = 29,999,984` (the default); center `(59,999,984, 0)`; and size `59,999,968` (the built-in default diameter). Set these same raw values on all three client `WorldBorder`s. `WorldBorder.setCenter` stores its arguments directly and calls `onCenterChange`; `setSize` constructs a static extent from its argument. The compared client border packet paths also carry center/size doubles and call those setters. Border production and server policy are outside this equal-input comparison.

In 1.16.5, the un-clamped candidate X limits before the absolute bounds are `30,000,000` and `89,999,968`; `updateBox` applies `max(rawMin, -absoluteMaxSize)` and `min(rawMax, absoluteMaxSize)`, yielding minX `30,000,000`, maxX `29,999,984`. The Z limits are `-29,999,984` and `29,999,984`. `Shapes.box` routes through the six-coordinate `AABB` constructor, which sorts endpoint pairs. Consequently, the interior subtracted from `Shapes.INFINITY` is a nonempty rectangle with X `[29,999,984, 30,000,000]` and Z `[-29,999,984, 29,999,984]`.

In 1.17 and 1.17.1, `updateBox` clamps both X endpoints to `29,999,984` and leaves the same Z interval. `Shapes.create` returns empty when any dimension has extent below `1.0E-7`; the resulting border collision shape (`INFINITY ONLY_FIRST empty`) is therefore `INFINITY`. The corresponding 1.17 and 1.17.1 `WorldBorder.java` and `Shapes.java` files are identical by the mechanical ledger.

For a standing LocalPlayer with anchor X `29,999,999.69`, Z `0`, ordinary bounding-box width `0.6F`, `noPhysics=false`, and a requested SELF displacement `(0.02, 0, 0)`, the X box is approximately `[29,999,999.38999999, 29,999,999.990000013]`. It lies inside the old sorted interior; after the `1.0E-7` deflation it still does not overlap the old outside-border collision shape, so `Entity.collide` retains that shape in the resolution stream. Eastward movement is clipped at the old X edge `30,000,000`: available X displacement is `30,000,000 - 29,999,999.990000013 = 0.009999986737966537` (subject to the exact AABB/vector operations in the body). In 1.17+, the border collision shape is infinite and intersects the current AABB, so the same overlap gate filters the border shape out and the requested X displacement remains `0.02`, assuming no other colliders. The displacement therefore differs by about `0.010000013262033463`.

This is a valid coordinate-scale witness: player anchor and AABB remain below `30,000,000`, and the queried area is well within signed integer block/chunk coordinate capacity. The centered border value is unusual but representable as the same raw client state through the source setters/received fields. No claim is made that producer validation, server border enforcement, or world generation creates the state. The comparison is conditional on the supplied equal raw logical inputs, as assigned.

## Consumer path and limits

In each release `Entity.collide` obtains `level.getWorldBorder().getCollisionShape()`, tests it against `Shapes.create(aABB.deflate(1.0E-7))` with Boolean AND, and either excludes or appends the border shape before calling the bounding-box collision resolver. The gate is the key polarity: an interior player box does not overlap the old outside-border shape, so that shape participates in axis clipping; the 1.17+ full-space shape overlaps the box and is excluded. This member is source-identical in 1.16.5 and 1.17 for the inspected body; the changed response follows from the changed border shape construction, not a changed Entity gate.

The witness is for one constructed legal-scale AABB and open eastward requested movement. It is not an exhaustive border/player-state proof, does not cover moving borders, collision with other shapes, servers, or input-device/state production, and is not runtime-validated. It can be accepted as a concrete conditional direct response while broader collision-query-shape inventory remains separately open.

## Source identity

`source-hashes.tsv` contains mechanical SHA-256 rows for the exact Mojmap `WorldBorder.java`, `Entity.java`, `AABB.java`, `Shapes.java`, `LocalPlayer.java`, `ClientPacketListener.java`, and `ClientLevel.java` files for 1.16.5, 1.17, and 1.17.1. It was checked against the task's three-way source inventory. The adjacent `source-hashes.tsv.sha256` binds that ledger. Source files were read-only; no source generation, build, test, or game launch was used.
