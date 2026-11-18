# Independent verdict: 1.12.1→1.12.2 chunk overlap audit

## Bound identities

- Author comparison: `chunk-overlap-1121-1122/source/comparison.md`, raw SHA-256 `00a207e95131627223381f833c3a6f6cd4340d8a3fa9f579698eed104e9c9007` (verified against bytes).
- Frozen independent audit: `chunk-overlap-1121-1122/blind-audit/frozen.md`, raw SHA-256 `be3d20d7cd0c3f1a6e42efd9bad5bd8c57aae26d0ed9503da351f0a10605253e` (verified unchanged).

## Verdict

**Accept the scoped no-difference result with the timing qualification below.** The author’s packet-to-cache trace is accurate: a handled forget packet removes the client cache entry; absent coordinates resolve to `EmptyChunk`/air; the client `isChunkLoaded` overload used by collision queries admits empty chunks; and the movement collision scan then reads the fallback state. The local-player tick’s apparent `isChunkLoaded` precondition also does not stop ticking in this case, because the client override delegates with `allowEmpty=true`. The inspected bounded behavior is unchanged across these releases, and no eligible pair-local change is identified.

The server-window geometry is valid at the server’s authoritative tracked center: with the minimum configured radius of three chunks, ordinary range reconciliation removes chunks outside that square, beginning at the fourth chunk column. That establishes distance from that center only. It does not itself bound the client’s predicted position at packet application. The report correctly leaves a continuous-walking overlap witness open; packet receipt is not proof that a movement query ran while the cache entry was absent. Its delayed-unload scenario is a possible timing condition, not a concrete source-proven delivery/tick schedule. The independent audit additionally found that a server position correction can put the local player at a previously forgotten coordinate without a cache-presence check, but the next movement query’s ordering against replacement chunk data is still unresolved. This does not close the ordinary-walking denominator.

## Separate tickable-block-entity lifecycle

One important distinction: forgetting a chunk removes the cache mapping and changes later block-state reads to the empty fallback, but the chunk’s existing tickable block entities are not necessarily removed from the world tick list synchronously. In both releases, `WorldChunk.unload` calls `World.unloadBlockEntity` for its block entities (`WorldChunk.java:651-657`); that method enqueues them in `removedBlockEntities` (`World.java:1783-1785`). In the block-entity tick phase, `World` iterates `tickingBlockEntities` and tests `isChunkLoaded(pos)` before invoking `Tickable.tick`; it removes the queued unloads from the ticking list only after that iteration (`World.java:1325-1352`). On the client, the default availability test returns true for an absent chunk, so an already attached `MovingBlockEntity` (`MovingBlockEntity.java:25-27`) can receive a tick in the next block-entity phase if the forget handler ran before it. The examined `WorldChunk.java` and `MovingBlockEntity.java` are byte-identical across the pair; the corresponding lifecycle code is unchanged.

That stale tickable object is a separate, short-lived world-tick path. It is not a block state supplied by `EmptyChunk`, does not make the cache entry present, and is not evidence that the player’s contact query saw the old chunk’s geometry. Its exact ordering relative to a particular packet and local player movement tick remains a timing question. The author report does not conflate these mechanisms, but the distinction should accompany any claim that a missing cache chunk means all effects from its former contents have stopped.

## Remaining gate

No exact ordinary-walking source schedule was established for an overlapping movement tick, and no packet/tick ordering witness establishes that the conditional correction or delayed-unload path is observed before replacement chunk data. Keep that reachability/timing denominator open. No runtime, shape, or broader movement claims are made.