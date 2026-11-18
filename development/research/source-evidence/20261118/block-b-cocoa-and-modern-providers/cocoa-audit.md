# Independent source audit — cocoa age-two collision shape

Verdict: COMPLETED — ACCEPT.

Reviewed immutable finding SHA-256: `a9422edf0988b22e12503f4dff492e6865f0278d1ee5035d00867538e4d1b219`.

## Evidence identity

The 1.10.2 and 1.11 Ornithe Feather ready markers identify the exact releases, mapping build.2 files, and source manifests. Their `sourceManifestSha256` values match the assigned identities: `91b0f478acb7b6f13463c35b268a30d2806f583402631a56f54ce2eb70d1ec71` and `8b3e5da58494f1dba77410960753b0bf646425a1ada491d8242cc71e6c7a59ca`. Both source manifests contain the relevant member hashes, and direct SHA-256 calculations match each entry. Exact paired member hashes:

| Member | 1.10.2 | 1.11 |
|---|---|---|
| `CocoaBlock.java` | `fc7589009c1a84c6e69408fc2ff17eec46202acd03e986555e83812204dd2234` | `97b1ac16965a9a6af7e052de9108c17cccb9579ed08283e4c9813f288bd9cd78` |
| `Block.java` | `1971dbc284d511e2ed366f77bc77fd8cd07174baad3e7732e908d3daeeb640c01` | `3fe8fdabe68da83fcfa01c0e6b8f5272fe81d71f6bf553506bdcef54757b0488` |
| `LivingEntity.java` | `d40dd476b6b68c6ce45b4202823475deb546ecda2284da330ff6724b33815e82` | `1f2e31fa6a905ccc2a51a0338f2aaf448f2b0569f28644e8205d864e570d9b2b` |
| `Entity.java` | `05da145effa19a6ef7934cc276e89226373b67c12f4ce89a8ce2183f29039f77` | `3209feaacd1e798b521a193eeb081d92c96881a70a18254ef24a58e911a4fd97` |
| `PlayerEntity.java` | `a055b84b98d98e828e177cad9bba47a1334ee9bf3ecd4331792979106235a302` | `a0d787681de5caecda03fea1536dac0c07e6ca5cfbfe3b5fbb700cbe77c86fb6` |

## Claim review

**ACCEPT — age-two CocoaBlock shape delta and vanilla collision dispatch.** `AGE` is bounded 0–2 in both sources, and `getShape` indexes the facing-specific array (CocoaBlock.java:20–40, 77–90). For EAST, age 2 duplicates age 1 in 1.10.2 (`[.5625,.3125,.3125]–[.9375,.75,.6875]`) and expands in 1.11 (`[.4375,.1875,.25]–[.9375,.75,.75]`); the other directions reflect the same extension away from the support side and downward. Cocoa support checks the jungle log at `pos.offset(FACING)` in both releases (CocoaBlock.java:60–64), so the stated EAST support at `(1,0,0)` is valid. Block collision retrieval returns the state shape, offsets it by block position, and adds it only when intersecting the query (Block.java:342–358 in 1.10.2; 353–369 in 1.11).

**ACCEPT — reachable one-tick grounded walk witness.** PlayerEntity initializes movement speed to `0.1F` and restores standing size `0.6F × 1.8F` in both releases. On stone, the ordinary on-ground path computes `u = getSpeed() * 0.16277136F / (0.91F * 0.6F)^3`, calls `updateVelocity`, then movement (LivingEntity.java:1385–1421 in 1.10.2; 1439–1475 in 1.11). From zero horizontal velocity, yaw/input aimed along +X, unobstructed grounded walking requests approximately +0.1 X. The proposed initial AABB is `[-.2,0,.2]–[.4,1.8,.8]`; it is disjoint from both cocoa boxes, touches the floor only at feet, and is clear of the support log. Its swept maxX is approximately .5. Thus the 1.11 cocoa shape is in the collision query and clips X at `.4375−.4=.0375`, moving center X to `.1375`; the 1.10.2 box starts beyond the sweep and permits the full step to center X≈.2. Entity.move enumerates the expanded directional box and clips the nonzero axes Y/X/Z in that order (Entity.java:514–537 in 1.10.2; 523–542 onward in 1.11; with zero vertical request no Y displacement occurs).

## Applicability residuals

This is a static source-derived witness, not a runtime observation. It assumes ordinary standing player dimensions and default unmodified walking speed, grounded state with stone support, zero initial horizontal velocity, no effects/equipment/modifiers that alter speed, no sneak/sprint/jump, and the stated unobstructed layout. The comparison establishes the behavior difference between exact 1.10.2 and 1.11 only; the precise intermediate patch introduction remains unknown, and no later trajectory is asserted.
