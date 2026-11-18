# Ordinary LocalPlayer block-contact record lifecycle: 1.21.3 to 26.2

Status: completed endpoint source comparison; this does not freeze the release pair, establish intermediate cutovers, or prove runtime parity.

## Source identity and integrity

The exact source roots are `decompiled_minecraft/1.21.3/mojmap/` and `decompiled_minecraft/26.2/unobfuscated/`. Their version-root ready markers identify exact release/mapping pairs as `ready`: 1.21.3/mojmap (5,655 Java sources, 26,544,115 bytes) and 26.2/unobfuscated (7,055 Java sources, 33,813,717 bytes). Each ready marker's source-manifest and artifact-manifest SHA-256 equals the corresponding current manifest hash. Full source-manifest verification found 5,655/5,655 and 7,055/7,055 listed Java files present with matching hashes, zero missing files, and zero mismatches. Provenance markers identify matching version and namespace. The source file roster below records hashes for all inspected class sources and both roots' ready/provenance/manifest files.

## Findings

### 1. Record origin/span and callback timing

**1.21.3 Mojmap.** `LocalPlayer.tick` calls `super.tick` (LocalPlayer.java:189-192). `LivingEntity.tick` runs `travel`/`travelRidden`, then calls `applyEffectsFromBlocks()` on the client only when the entity is locally controlled (LivingEntity.java:2768-2776). `Entity.move` (Entity.java:619-701) does not append a movement record. The ordinary callback instead reaches `Entity.applyEffectsFromBlocks()` (Entity.java:736-752), which invokes the span overload with `oldPosition()` to current `position`, creates exactly one two-field `Movement(from,to)`, checks it, and clears `movementThisTick` and `blocksInside`. The ordinary LocalPlayer route has no source call to `recordMovementThroughBlocks`; the only external calls in this source tree are server packet processing.

**26.2 unobfuscated.** `LocalPlayer.tick` calls `super.tick` when the client connection is loaded (LocalPlayer.java:227-245); `LivingEntity.tick` runs travel and then invokes block effects under the same logical-client/local-authority gate (LivingEntity.java:3137-3145). Each accepted normal `Entity.move` (Entity.java:711-755) captures `pos = position()`, computes `newPosition = pos.add(resolved movement)`, appends `Movement(pos,newPosition,delta)`, then sets position. Here `delta` is the already edge-adjusted request used for axis provenance; the span endpoint uses collision-resolved movement. The no-physics branch returns without recording; if that leaves the per-tick deque empty, the callback consumer uses oldPosition-to-position fallback.

`applyEffectsFromBlocks()` (Entity.java:903-914) snapshots all queued movements into `finalMovementsThisTick`, clears the per-tick deque, and, if the snapshot is empty, inserts oldPosition-to-position. Otherwise it appends an unannotated bridge from the last record's `to` to current position only if squared distance exceeds `9.9999994E-11F`. It then consumes this snapshot. `addMovementThisTick` (Entity.java:920-929) appends one record per move; at size 100 it merges the oldest two into a single `(first.from, second.to)` record before appending. The merged and bridge/fallback records have no axis metadata. `Movement` is `(from,to,Optional<axisDependentOriginalMovement>)`; the 3-argument constructor marks metadata present, the 2-argument constructor absent (Entity.java:4120-4127).

### 2. Ordinary callback consumer and repeated moves

For normal client LocalPlayer travel, the selected fluid, fall-flying, or air route performs one `move(SELF, deltaMovement)` in that travel call (LivingEntity.java:2430-2437, 2452-2465, 2529-2535, 2581-2594); ridden travel is not the LocalPlayer's ordinary unmounted path. So the ordinary one-move tick creates one provenance-bearing record when the resolved movement passes Entity.move's acceptance gate. Multiple move calls before the post-travel callback create multiple ordered records. The callback iterates records in order, maintaining one per-callback `visitedBlocks` set, then clears that set after the list (Entity.java:1278-1305). A later already-visited block is not called again in the same callback. The 100-record cap merges the earliest path and removes its axis provenance, as described above.

In 1.21.3, the ordinary callback's one record is always an unannotated start-to-end span regardless of how many moves happened before it; intermediate paths are unavailable. The separate public `recordMovementThroughBlocks` can add spans, but the source caller is server packet movement and is outside this ordinary LocalPlayer path (ServerGamePacketListenerImpl.java:452, 930).

### 3. Provenance-dependent ordinary callback witness

A source-reachable one-move witness exists for a non-immune, non-removed, physics-enabled LocalPlayer on the client, with fire at block `(0,64,1)`, no other collision obstacles, and an unobstructed SELF displacement from `(1.296,64,0.699)` by `(0.004,0,0.004)`. This is a valid normal move vector; exact source establishes the route and gates, not how a particular prior input/impulse established that velocity.

Player dimensions are 0.6 by 1.8 on both sides (1.21.3 EntityType.java:1053-1062; 26.2 EntityTypes.java:1163-1173). Its box starts with its east edge below `z=1` and west edge below `x=1`; it ends with its west edge at `x=1` and moves diagonally through a small interval where it overlaps fire at `(0,64,1)`. Fire is explicitly no-collision on both sides (Blocks.java:1171-1181 in 1.21.3 and 1233-1244 in 26.2); its entity-inside shape is the default full block on both sides (BlockBehaviour.java:353-355 in 1.21.3, 331-333 in 26.2). Thus this block does not clip or alter the chosen movement.

In 1.21.3, the ordinary fallback span is the diagonal `(1.296,64,0.699)` to `(1.300,64,0.703)`, and `boxTraverseBlocks` plus `collidedWithShapeMovingFrom` checks the diagonal box sweep (Entity.java:1038-1061, 1080-1084). It reaches `BaseFireBlock.entityInside`, whose non-immune branch advances/starts fire ticks (BaseFireBlock.java:129-145).

In 26.2, the actual normal move records the same endpoints plus original delta metadata. The consumer follows `axisStepOrder(delta)`; equal absolute X/Z chooses `Y,X,Z` (Direction.java:48-49, 378-380), so the zero Y axis is skipped, then X is checked while `z=0.699` (box edge below `z=1`), then Z is checked at `x=1.300` (deflated box begins east of `x=1`). Neither orthogonal segment intersects the fire cell. Consequently `BaseFireBlock.entityInside` is not queued for this cell and `FIRE_IGNITE` is not applied (Entity.java:1282-1301, 1315-1352; BaseFireBlock.java:131-157; InsideBlockEffectApplier.java:32-64, 67-85).

The record provenance is directly operative: with the same 26.2 endpoints but an unannotated two-field Movement, checkInsideBlocks takes its straight-span branch; with the actual three-field move record it takes axis splitting. The actual pair also changes the traversal implementation, so this static comparison does not isolate provenance as the sole cross-version code change or prove all callback differences are due to provenance. The witness closes the narrower question that an ordinary movement record's provenance can change a concrete block-inside callback observation at fixed endpoints in the native consumer.

## Exact source roster

See `source-roster.tsv` for programmatic SHA-256 values. Manifest file entries use repository-relative paths under the named source root. All inspected source hashes matched the manifest entry for that file.
