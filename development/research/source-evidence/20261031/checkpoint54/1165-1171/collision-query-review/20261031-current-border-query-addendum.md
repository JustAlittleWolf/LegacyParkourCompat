# Addendum — current-player-box world-border queries (2026-10-31)

## Verdict

**ACCEPT bounded no-difference for `Shapes.create` encoding at both current-box world-border callsites.** This closes the query-shape subquestion only; it does not disposition any independent change to `WorldBorder` geometry or other border response.

## Callsites and box provenance

In `Entity.collide`, both 1.16.5 and 1.17/1.17.1 create the border-gate query from `Shapes.create(aABB.deflate(1.0E-7))`, where `aABB` is assigned directly from `this.getBoundingBox()` at entry to the current movement collision method. This is the player's current box, not a movement-expanded query.

In `CollisionSpliterator.worldBorderCheck`, each version assigns `aABB = this.source.getBoundingBox()` and passes it to `isCloseToBorder` (`inflate(1.0E-7)`) and `isOutsideBorder` (`deflate(1.0E-7)`). The spliterator's separate `this.box` retains the collision-query argument, which may be expanded, but these world-border helpers do not read it. These two checks therefore use the current LocalPlayer box as requested.

## All Player poses remain continuous in X and Z

The exact `Player.POSES` maps are paired across 1.16.5, 1.17 and 1.17.1. Their dimensions are:

- Standing, crouching, fall-flying, swimming and spin-attack: width `0.6F`.
- Sleeping: inherited `LivingEntity.SLEEPING_DIMENSIONS`, fixed width `0.2F`.
- Dying: fixed width `0.2F`.
- Unlisted poses: `Player.getDimensions` falls back to standing dimensions.

`EntityDimensions.makeBoundingBox` applies width symmetrically on X/Z using `width / 2.0F`; the position does not change that box width. The two collision-border transforms change each horizontal width only by `+2.0E-7` for inflation or `-2.0E-7` for deflation. Thus every transformed Player box has horizontal width either approximately `0.60000002384 ± 0.0000002` or `0.20000000298 ± 0.0000002`.

For `findBits` to accept both endpoints of an axis at scale `j ∈ {1,2,4,8}`, the width must be within `2.0E-7` of a multiple of `1/j` under the new nearest-rounding tolerance; the old floor test is at least as restrictive. A `0.6F` width is about `0.0249998` away from the nearest eighth-grid width (`0.625`), and a `0.2F` width is about `0.0499998` away (`0.25`). Even after either border transform, these distances are many orders larger than the endpoint tolerance. Consequently X and Z always fail `findBits`, independent of absolute coordinates. `Shapes.create(AABB)` therefore preserves the exact transformed box coordinates through the continuous fallback in both implementations.

The 1.17 six-coordinate overload's new `< 1.0E-7` extent-empty gate is unreachable here. After the largest relevant shrink (`2.0E-7` in width/height), the minimum Player dimension remains above `0.1999998`, well above that gate. The other axes can neither change the fact that X/Z fail nor cause a different fallback shape.

## Consequence and boundary

For equal current Player boxes, same world-border shape and equal surrounding inputs, these current-box query operands are exactly equal across 1.16.5 and 1.17/1.17.1. This removes the `Shapes.create` rounding difference as a cause of a different result at `Entity.collide`'s deflated-box test or `CollisionSpliterator`'s inflated/deflated-box tests. It does not prove the border-shape operands themselves are equal; the separate `WorldBorder` source change/witness remains independent.

## Source identity

`border-query-source-hashes.tsv` mechanically binds `Entity.java`, `CollisionSpliterator.java`, `Player.java`, `LivingEntity.java`, `EntityDimensions.java` and `Shapes.java` for all three releases to their declared exact-source hashes. Every row matches its release manifest/provenance. For 1.16.5 and 1.17.1 the publication identities are the parent-sidecar artifacts in `publication-identities.tsv`; 1.17 is bound by its embedded `.decompile-provenance.json`. The new source-hash ledger has adjacent `.sha256` sidecar.

No product code, wiki, backlog, runtime, build, test, server/TAS/Gym launch, or source regeneration was consulted or changed.
