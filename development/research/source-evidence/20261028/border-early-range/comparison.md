# Early wall-clock range comparison: 1.21.6–1.21.8

## Result

The literal source trees labeled `mojmap` for 1.21.6, 1.21.7, and 1.21.8 contain byte-identical `WorldBorder.java` and `Entity.java` inputs (their SHA-256 values match across all three releases; see [audit.md](audit.md)). The moving-border extent in each therefore uses the same wall-clock interpolation, and the player collider collection in each admits and appends the border collision shape through the same predicate and ordering. Within the inspected releases, 1.21.6 is the earliest directly evidenced old endpoint. This comparison does not establish behavior before 1.21.6.

## Moving-border clock

In all three source files, `WorldBorder.MovingBorderExtent` is declared at line 307. Its constructor records `lerpBegin = Util.getMillis()` at line 318 and derives `lerpEnd` from that value and the requested duration at line 319. `getSize()` samples `Util.getMillis()` again on each call, divides elapsed milliseconds by `lerpDuration`, then uses `Mth.lerp` while the fraction is below `1.0`, otherwise returning `to` (lines 343–346). The moving min/max accessors call `getSize()` (lines 323–339), so their values are read-time samples of wall time.

The moving extent's `getCollisionShape()` computes the border geometry when called, flooring the current minimum X/Z and ceiling the current maximum X/Z (lines 382–392). Thus both border admission bounds and the collision shape are derived from the moving wall-clock size at their respective reads.

## Player collision consumer

In all three `Entity.java` files, `collectColliders` begins at line 1059. It starts with the supplied colliders, gets the level's `WorldBorder`, and evaluates `isInsideCloseToBorder(entity, box)` only when the entity is non-null (lines 1065–1066). If admitted, it appends `getCollisionShape()` (lines 1067–1069), then appends block collisions (line 1071). The border is therefore part of the collider list used by entity movement; the source path preserves the order “existing colliders, border shape if close, block collisions.”

The six release-specific hashes for these two files are all identical. Direct checks also found identical hashes for `CollisionGetter.java`, `ClientLevel.java`, and `ClientPacketListener.java` across .6/.7/.8; see the source hash table in [audit.md](audit.md). The two additional audited files, `BlockCollisions.java` and `Minecraft.java`, are likewise identical across these three selected release trees.

## Adjacent recorded boundary

The existing source record [border-range/evidence.md](../border-range/evidence.md) already compares the later transition: 1.21.9 and 1.21.10 retain wall-clock interpolation; 1.21.11 removes the `Util.getMillis()` moving-extent clock and interpolates from previous/current tick snapshots, with border progression inside `tickRateManager().runsNormally()`. The 1.21.11 default border geometry getters and moving collision shape use the previous-tick snapshot (`0.0F`). This artifact adds direct .6/.7 comparison with .8; it does not revise or reclassify that later boundary.

## Source identity limits

The source trees are read from `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\<version>\mojmap`. The .6/.7 provenance files record the decompile pipeline, tools, runtime and input hashes, but do not explicitly record a namespace field or the exact decompiler invocation. The tree directory is labeled `mojmap`, and the official Mojang `client.txt` mappings inputs are pinned and hash-verified for .6/.7; that evidence does not fill the provenance-record omissions. The .8 provenance record explicitly names namespace `mojmap` and includes the exact Gradle invocation. See [audit.md](audit.md) for the local cache, manifest, artifact and provenance hashes and URLs.

This is a source-evidence artifact only. It records no acceptance, pair-completion, full-coverage, or runtime status.
