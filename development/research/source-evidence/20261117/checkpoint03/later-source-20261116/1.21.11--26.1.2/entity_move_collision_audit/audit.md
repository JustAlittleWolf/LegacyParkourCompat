# Independent audit: 1.21.11 → 26.1.2 Entity.move collision kernel

**Verdict: ACCEPT — bounded NO_DIFF only.** This verdict is bound to the exact candidate bytes at SHA-256 `3F44A596DD8F9D8885C565CB2269355B5B6503D0B4EB3898FACD6EBCD77F73B`. It accepts the report's bounded conclusion for ordinary local-player `Entity.move` collision resolution and immediate position/flag writes. It does not close the release pair or establish runtime parity.

## Identity and candidate binding

Independently checked both ready markers, their source/artifact-manifest pins, and the corresponding provenance records. 1.21.11 is the ready Mojmap/Vineflower source (`mojmap.sources.sha256` `0C4D83FEF84C101D9DB88F331ACEC7960F3C8346228C28995E6CF57BBFCAE555`); 26.1.2 is the ready unobfuscated/Vineflower source (`unobfuscated.sources.sha256` `54AE320660CA911A6D20514C965C33EB414FBEDD1AD6727BBAA95FE906AA23C0`). The endpoint marker/provenance digests and artifact/client pins agree with the candidate's immutable identity table. Recomputed the candidate SHA-256 and confirmed it is the assigned digest above.

Independently recomputed the bytes and checked manifest membership for all twelve paired source paths listed in the report. Each of the 24 endpoint source hashes matches both its source-manifest entry and file bytes. The path/hash table in the candidate is accurate.

## Source audit

Compared the full relevant `Entity.move` branch and ordering at the cited anchors, plus `Entity.collide` and step-up helper. The no-physics write and flag clearing, piston limit/zero early return, stuck multiplier, edge-backoff call, collision resolution, movement-length write threshold, fall-reset clipping, movement record/position update, collision/ground/support flags, fall callbacks, blocked-velocity zeroing, movement emission and block-speed factor agree in order and conditions. Collision collection and candidate step-up retain the same entity-collider query, border/block shape sequence, float conversions, limits, unstable sort, horizontal-distance choice and base-height correction.

Compared the relevant player edge-backoff/support functions and LocalPlayer movement/minor-collision wrapper. Their player/mover/flying/surface gates, 0.05 increments, support check, X/Z snapshot and `super.move` sequence, float conversions, auto-jump call, walked-distance multiplier, angle calculation and thresholds match.

Compared `CollisionGetter` iteration/support plumbing and the complete `BlockCollisions` traversal/filter/intersection path. The padded bounds, Cursor3D iteration, face exclusion, chunk cache, collision predicates, special-shape gates, collision context, full-block AABB fast path and translated voxel-shape intersection agree. I separately inspected `Cursor3D` iteration arithmetic and the AABB expansion/intersection operations used by this slice; those also agree. `ChunkPos` packing and `LevelReader`/`Level.getChunkForCollisions` lookup paths used by the traversal agree.

The collision clipping path agrees through `Direction.axisStepOrder`, axis sequencing, `Shapes.collide`, `VoxelShape.collideX`, and `Shapes.joinIsNotEmpty` used for non-full-shape intersection. Scan directions, iteration bounds, `1.0E-7` offsets and comparisons, shape-membership tests, and min/max clipping operations have the same order and meaning. World-border distance/close-inclusion conditions also agree.

## Limits retained

The candidate correctly marks this as bounded source coverage. Block-specific and state-dependent collision shapes and their direct player-visible state predicates were not exhaustively traced. Full input/tick/travel routing and fluid, packet, vehicle, auto-jump continuation, and callbacks outside the enumerated postlude remain open. Entity-entity collision/push and unrelated movement routes remain excluded. No pair closure, implementation recommendation, or runtime claim is made by this audit.
