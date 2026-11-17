# 1.9.2 → 1.9.3 ordinary local-player border ingress follow-up

## Bound inputs

- Exact pair and namespace: Minecraft `1.9.2` → `1.9.3`, `ornithe-feather`.
- Mapping-local source manifest identities: 1.9.2 `9928b467deb35c7e7f891b4fee1627a68feecdf233c2076909fd4834b0380510`; 1.9.3 `43325cdd53ca34b88ee9af8464782b323a948b98c8a556c4761f1e5737b84c3a`.
- Accepted conditional query/reachability packet: `world-query-player-reachability.md`, SHA-256 `B4D0AB9A6247B0431E3F919A0B8085BDD016E9A25B2D4C468F153DC165BFB656`, with its bounded audit under `world-query-player-reachability-audit/`.
- Accepted streaming/cache-population packet: `world-query-reachability-followup.md`, SHA-256 `76a2c86aa12365aeb2754367ea340b83b67a2b95c993feba05b24ccc6b785046`; audit SHA-256 `d7df8600104961258e10cdfadf29ff1bdfe5ce1b3d88a4f3ef34795e9da7fe36`.
- Accepted full-pair partial audit: `audit-r4/final-verdict.md`, SHA-256 `ed503abead5da07741a8bb164de907703d68b8d8232a23157d3623b246bc18cf`.
- Reused source bindings: `Entity.java` hashes 1.9.2 `74D3AC34D7E6355C8FDCDD777DCD95BA9EF4626718C8772C1B58D0E28419E42A`, 1.9.3 `BCD7FA2206BF8D7271102F6DA7FE2771F96DBE22EC79DAB9F2101A3E29C17EF0`; `World.java` hashes 1.9.2 `CDDA70C97F0E4D6F6C454C8F6D3C0D47F0E0AE612E75062D8FE93DDD3963E6B0`, 1.9.3 `2FE063E0EC224EED9FC7D01B8F788C32035EED5FEE5A9D98EA5E82296236E05A`. Recomputed paired entity/world file SHA-256 values match.

## Ordinary collision-resolved movement ingress

In both versions, `Entity.move` takes the `noClip` direct-position branch only when `noClip` is true. In the ordinary collision-resolved branch, it obtains `World.getCollisions(this, this.getShape().expanded(dx, dy, dz))` before changing the position, then clips Y, X, and Z movement against the returned boxes. The step-up alternative performs another collision query and clips the candidate displacement against that query's boxes as well. This query-before-position-update order prevents one ordinary move request from skipping the boundary merely because its requested displacement is large.

For an entity not already marked outside the world border, `World.getCollisions` computes `bl2` from `isWithinBorder`. That predicate tests the entity's x/z center against a one-block-expanded world-border interval when the outside flag is false. For scan cells outside the actual border, the query uses `Blocks.STONE.defaultState()` while `bl2` is true; it reads the actual block state only when the cell is inside the border or the entity is not within the border interval. The returned stone provider boxes therefore participate in the same X/Z clipping loop as other collision boxes. The same gate and movement ordering are present in both exact source versions; this is not a 1.9.3 ingress change.

The world-border maximum is `29,999,984`, while the hard-coordinate exterior starts at `30,000,000` (and symmetrically on the negative side). A normal player approaching the border from inside therefore encounters the synthetic stone boundary first; ordinary collision-resolved motion does not source a path to an entity already outside the border and using the changed 1.9.3 cached-state branch. The accepted chunk-streaming report establishes that adjacent beyond-hard-limit chunks may be cached, but cache presence does not remove this earlier collision barrier. The boundary cells are within the coordinate-valid range, and the accepted streaming evidence records a minimum view distance of three chunks, so this disposition is not based on an assumed missing boundary chunk.

This conclusion is limited to ordinary collision-resolved local-player movement from an in-border state. A position supplied externally (for example by a server correction/teleport) can start outside the border, after which the accepted conditional query finding applies. The `noClip` direct-position branch also bypasses this collision barrier, but it is an alternate movement state and does not establish ordinary collision-resolved ingress. Neither route is used here to infer ordinary movement behavior.

## Separate open gates

- **Position ingress:** ordinary collision-resolved movement from inside the border does not reach the outside-border predicate needed to read the cached hard-limit-exterior state. The externally supplied initial position remains a separate conditional route.
- **Cached block content:** even with an outside-border position, no accepted evidence establishes a non-air hard-limit-exterior state at the scanned coordinate.
- **Shape/intersection:** no accepted evidence establishes that such a state yields a nonempty collision box intersecting the player query.
- **Tick initial state:** the local-player tick's initial chunk-loaded check and later player-coordinate clamp describe processing after a position exists; neither is evidence of ordinary movement ingress.
- **Movement consequence:** no trajectory/response difference or implementation significance is established.

This packet reuses accepted streaming, query, and exact-pair coverage evidence and binds the ordinary movement/query gate narrowly. Pair status remains PARTIAL; this is not a movement finding or pair freeze. No product, wiki, backlog, runtime, test, build, or game input was used.
