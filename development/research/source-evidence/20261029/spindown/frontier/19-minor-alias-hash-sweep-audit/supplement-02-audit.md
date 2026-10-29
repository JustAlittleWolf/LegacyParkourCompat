# Independent audit: Supplement 02

## Review identity and verdict

Reviewed `.native-source/20261028/frontier-c/19-minor-alias-hash-sweep/supplement-02.md` at SHA-256 `421fad6d7c0d9a210954c0e060da4ab2038f40895c335940a5db761931c78e63`. Scope is the exact `1.9.2→1.9.3` pair in the canonical `ornithe-feather` mapping roots.

**Verdict:** The two mapping-root manifest SHA-256 values in the supplement match the files in the canonical roots. Both manifests declare 1,819 source entries. The listed selected sources match their in-tree source hashes, except for two transcription errors in the supplement's evidence table. StateDefinition, FireBlock, the player pose call, and the key world collision gates were retraced. The pose query is a source-confirmed player dimension/collision-state candidate at an extreme-coordinate precondition, not an ordinary reachable movement finding. Full-pair coverage and parity remain open.

The version parents contain only the `ornithe-feather` directory; the hidden `.decompile-provenance.json` sits at that mapping-root level. The records bind the 1.9.2 and 1.9.3 source roots to their release-specific client, Feather Gen2 build 2 mapping and metadata inputs. As the supplement states, these locally embedded identities and source hashes do not authenticate the underlying artifact bytes; none were fetched or independently rehashed here.

## Selected-hash corrections

The source files themselves match their corresponding manifest entries. Six table pairs are correct as written. Two 1.9.3 table hashes are wrong:

- `net/minecraft/entity/ai/pathing/WalkNodeEvaluator.java`: the supplement's 1.9.3 value has 65 hex characters (`893108191844cee1eceb32fbef9c245a70cad969c02c8895b17e0475436b4c85c`). The canonical source and manifest both give the 64-character hash `893108191844cee1eceb32fbe9c245a70cad969c02c8895b17e0475436b4c85c`.
- `net/minecraft/block/FireBlock.java`: the supplement gives `48b734bf3665b9c743c05c176bb1208992e357f376fecfb1b6d337dade5a2c29`; canonical source and manifest give `48b734bf3665b9c743c05c176bb1208992e357f376fefcb1b6d337dade5a2c29`.

All other paired hashes in the table match, including the equal `WorldBorder.java` hash.

## Retraced source claims and exact gates

### StateDefinition

The `get(Property)` and `set(Property,value)` changes are described accurately. In `.2`, `get` uses `containsKey` then casts the stored value; `.3` treats `values.get(property) == null` as absent and then casts the local comparable. In `.2`, `set` checks property membership and `property.values().contains(value)` before the identity-based same-value return / neighbor-table lookup. In `.3`, it checks the current value for null, returns early on reference identity, then looks up the neighbor and throws the same not-allowed `IllegalArgumentException` if the lookup is null. For normally constructed vanilla states and valid property-domain values, the described valid reads/transitions remain equivalent. Malformed null-valued maps and absent neighbor entries have differing failure behavior; no changed valid movement state was established.

### Entity-less world queries and callers

`World.isChunkLoaded(pos, allowEmpty)` uses `contains(pos)` before the chunk-provider call in `.2`; `.3` calls `isChunkLoadedAt(pos.x >> 4, pos.z >> 4, allowEmpty)` directly. Collision probes use `y=64`, so the changed gate is the horizontal world-coordinate predicate.

`World.getCollisions(Box)` otherwise retains the scan and immediately returns true for an out-of-range scanned x/z block after a loaded-chunk probe. In `.2`, `contains` prevents that column from being scanned. In `.3`, it can reach the hard-coded outside-range return if the provider reports that chunk loaded. The supplement's substantive classification is correct, with a narrower exact edge gate than its phrase “candidate box crosses” suggests: the scan pads one block around the box (`floor(min)-1` through `ceil(max)` inclusive). For high X/Z, an out-of-range column may be visited when `ceil(max) >= 30,000,000`; for low X/Z, when `floor(min)-1 < -30,000,000`. A scanned column must be loaded and must not be skipped as a two-axis corner (`q == 2`). The pose box need not itself extend fully beyond the coordinate bound for the padded scan to encounter such a column.

`PlayerEntity.tick()` calls `updatePlayerPose()` in both versions. The pose method only makes a candidate when target width or height differs; it chooses dimensions for fall-flying, sleeping, sneaking, or the standing pose, builds a box from current minimum coordinates with the target width/height, and applies `setSize` only when `world.getCollisions(box)` is false. Thus a loaded, out-of-range padded scan can reject a resize in `.3` that `.2` would allow. The direct source consequence is a player size/collision-state difference; no ordinary client path to that extreme-coordinate and loaded-chunk precondition is proven here.

`WorldBorder.java` is identical in both versions. Its initialized `maxSize` is `29,999,984`, and the coordinate getters clamp to `±maxSize`; the world horizontal range is `[-30,000,000, 30,000,000)`. This leaves 16 blocks between the default extreme border coordinate and the out-of-world range. The source also has a `setMaxSize` mutator, so “maxSize” is a default field value rather than an immutable invariant; no additional source caller was established. The entity-aware `World.getCollisions(Entity, Box)` uses its separate world-border logic and stone fallback. The entity-less pose check does not use that gate.

All `getCollisions(Box)` call sites were searched in both exact trees. `PlayerEntity.updatePlayerPose` is direct. The remaining calls are `WalkNodeEvaluator` and `LivingEntity.dismountRider`; `LivingEntity.stopRiding()` invokes the latter only after `super.stopRiding()` and only when the world is not client-side. `WalkNodeEvaluator` is mob pathing. The supplement's server-only and non-player classifications for these callers are supported.

`World.getBlockCollisions(Box)` shares the changed loaded-chunk gate and uses `BEDROCK` for scanned out-of-range positions. Its only direct world caller is the base `Entity.pushAwayFrom()` implementation. That base method is called by `ExperienceOrbEntity` and `ItemEntity`, so those are non-player physics. `LocalClientPlayerEntity` does define and call its own `pushAwayFrom` override; that implementation does not delegate to the base method and does not call `getBlockCollisions`. The supplement's conclusion that this query does not add a player path is correct when method dispatch is included.

### FireBlock

The complete `.2→.3` diff for `FireBlock.java` adds nullability metadata and reorders the `neighborChanged` signature to put `BlockState` first. Its empty collision shape and support/flammable-neighbor test and removal behavior are unchanged. The `.3` state dispatch forwards the same state, world, position and neighbor. No shape or direct player movement change was found in this provider.

## Remaining boundaries

The corrected pose-query path remains a narrow extreme-coordinate candidate. It does not establish ordinary movement reachability, an accepted movement finding, or parity. The complete source inventory, other providers and independent pair freeze remain open. The earlier review and supplement audits are unchanged.
