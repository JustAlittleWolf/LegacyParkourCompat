# Moving world-border clock and collision evidence

## Scope and verdict

This source-only dossier closes the bounded world-border movement operation across exact Mojmap sources 1.21.4, 1.21.5, 1.21.6, 1.21.7, 1.21.8, 1.21.9, and 1.21.10, with 1.21.11 inspected as the requested later boundary reference. It does not claim complete movement-difference discovery for these releases, runtime parity, or authorization to alter product code. Existing independent acceptance for adjacent operations remains owned by the coordinator.

Result: border admission, collision-shape rounding, border clock placement, and client packet tick anchor are source-confirmed. In 1.21.9, the new `Entity.collectAllColliders` surface is only called by `ServerPlayerGameMode`'s bed eligibility calculation; this is explicitly out of movement scope. The 1.21.9 change does not alter the movement `Entity.collide`/`collideBoundingBox`/`collectColliders` path.

## Exact source identity and integrity

All evidence is under `decompiled_minecraft/<release>/mojmap/` and uses Mojang mappings. SHA-256 of each `Entity.java` matched that release's source manifest (`.decompile-provenance.json` inside the mapping directory for 1.21.6/.7/.9, or root-level `mojmap.sources.sha256` for 1.21.4/.5/.8/.10/.11). Root sidecars identify exact `versionId` and namespace for 1.21.4/.5/.8/.10/.11. Source-tree manifests and exact-run sidecars were consumed read-only.

| Release | Entity.java SHA-256 | Integrity record |
| --- | --- | --- |
| 1.21.4 | `05f18ef2ec0413fc010230407c812a11553eb5123b68d21b5d7b2c0c175698ad` | root `mojmap.sources.sha256`, match |
| 1.21.5 | `fc177733e9cc4b2d5cf2562e5529d0f4e0b9c3690f081fbd58aa9119eb173dc4` | root `mojmap.sources.sha256`, match |
| 1.21.6 | `c403e6176d27b5bfd6aaa0dea3735ffa4fcf80dbae58766661dd84453b7e9704` | hidden mapping `.decompile-provenance.json`, match |
| 1.21.7 | `c403e6176d27b5bfd6aaa0dea3735ffa4fcf80dbae58766661dd84453b7e9704` | hidden mapping `.decompile-provenance.json`, match |
| 1.21.8 | `c403e6176d27b5bfd6aaa0dea3735ffa4fcf80dbae58766661dd84453b7e9704` | root `mojmap.sources.sha256`, match |
| 1.21.9 | `bd6074b108212bde667877700af1a52649472630c906c57c2a36c96a6fcee74c` | hidden mapping `.decompile-provenance.json`, match |
| 1.21.10 | `8361dbb86fe6c975d21f69d008377b6f191669be751150c842517e9d0346fa18` | root `mojmap.sources.sha256`, match |
| 1.21.11 boundary | `32314478c6036fa9f3f3cc409c61c622eefc5a282d60e1011a1a33cc29cf18a3` | root `mojmap.sources.sha256`, match |

For 1.21.7 and .9, `Entity.java` SHA values above were cross-checked against the `sources` entry inside the hidden per-mapping manifests. Those manifests are present despite the absence of root-level `mojmap.provenance.json` sidecars for those versions. 1.21.6 likewise has its complete hidden mapping manifest. No online mapping lookup or decompilation was used.

## Paired behavior evidence

### Admission and collision insertion

- `1.21.7/mojmap/net/minecraft/world/entity/Entity.java:1002-1030,1054-1073`: `Entity.collide(Vec3)` obtains the player's current box and entity collision shapes, calls `collideBoundingBox`, and, for eligible step-up attempts, passes collected shapes to the step-up collision path. `collideBoundingBox` expands the box toward the requested motion, then `collectColliders` builds the collision list. That collector preserves supplied entity collisions, then checks `entity != null && border.isInsideCloseToBorder(entity, queryAabb)`, adds `border.getCollisionShape()` when admitted, and finally appends block collisions.
- `1.21.9/mojmap/net/minecraft/world/entity/Entity.java:1022-1050,1074-1098`: the same movement collector and its gates/order remain; the movement path still uses `getEntityCollisions`, `collideBoundingBox`, then `collectColliders`. The new public `collectAllColliders` is separate and not called by `collide`.
- `1.21.7/mojmap/net/minecraft/world/level/border/WorldBorder.java:111-114` and `1.21.9/.../WorldBorder.java:104-107`: admission computes `extent = max(absMax(box width, box depth), 1.0)` and requires both `distanceToBorder(entity) < extent * 2.0` and `isWithinBounds(entity x, entity z, extent)`. Thus the border shape is conditionally supplied near the border; it is not unconditionally collidable.
- Exact `WorldBorder.java` SHA-256 is `94efa5133c7a4344c980b107f937fe397996596ba7e1608dee5fa5e500c55925` for 1.21.5 through .8, and `1b20dcab5a5fa945235709f868348f9abd73a93c4a85aac6e361d5e55cd9df3c` for 1.21.9 and .10. The .4 WorldBorder file is separately identified as `f4d4fb13548c6728550c47c250d5db3b899811759ea0fe485a8499daea641781`; all seven scoped versions contain the checked admission and collision-shape methods. These identities tie the intervening .7/.8 and .9 source results to the already checked endpoint intervals without assuming every source file is identical.

### Dynamic border shape rounding

`WorldBorder.MovingBorderExtent.getCollisionShape()` in 1.21.7 (`WorldBorder.java:382-395`) and 1.21.9 (`:380-393`) constructs the outside shape from a box whose min X/Z use `Math.floor(getMinX/getMinZ)` and max X/Z use `Math.ceil(getMaxX/getMaxZ)`, with infinite Y bounds, then takes `Shapes.INFINITY ONLY_FIRST` against that box. The operation order and floor/ceil rounding are unchanged across the inspected .7-to-.9 interval. The moving extent recomputes this shape on access. The static extent instead returns its cached shape, updated when its bounds change (`1.21.7:550-590`; `1.21.9:515-539`). The `WorldBorder` implementation is identical by SHA for .5-.8 and .9-.10; .4 contains the equivalent checked mechanics under its earlier source shape.

### Border clock and client packet tick anchor

- `1.21.7/mojmap/net/minecraft/client/multiplayer/ClientLevel.java:241` and `1.21.9/.../ClientLevel.java:268`: client level tick calls `getWorldBorder().tick()`.
- `1.21.7/mojmap/net/minecraft/server/level/ServerLevel.java:321` and `1.21.9/.../ServerLevel.java:325`: server level also ticks its border. This establishes vanilla clock placement only; the project invariant keeps historical movement off logical servers.
- `1.21.7/mojmap/net/minecraft/network/TickablePacketListener.java:3-4` and same file in 1.21.9: `TickablePacketListener extends PacketListener` and exposes `void tick()`.
- `1.21.7/mojmap/net/minecraft/network/Connection.java:368-372` and 1.21.9 `:367-371`: `Connection.tick()` flushes then invokes the installed listener iff it implements `TickablePacketListener`.
- `1.21.7/mojmap/net/minecraft/client/multiplayer/ClientPacketListener.java:346,2571-2596` and 1.21.9 `:329,2616-2644`: the client play listener implements `TickablePacketListener`; its `tick()` performs client connection/key/deferred-packet/telemetry/load bookkeeping. This method is not the world-border clock. The client level tick is the border-clock anchor. For both versions the client packet tick is driven through the same `Connection.tick()` interface gate; class line movement reflects intervening source changes and must not be treated as a stable absolute injection offset.

### 1.21.9 API addition and exclusion boundary

`Entity.java:1079-1082` in 1.21.9 adds `collectAllColliders(entity, level, box)`, which obtains entity collisions and delegates to the established private collector. Whole-source call-site search finds its only caller at `server/level/ServerPlayerGameMode.java:89`, in the bed eligibility predicate (`getAvailableSpaceBelow(1.0) < 1.0`). Bed-entry eligibility is expressly excluded. The helper addition is therefore recorded as an API/eligibility boundary, not a movement delta. Although the shared collector conditionally inserts the rounded world-border shape when used there, that call does not expand the accepted movement claim.

## Review summary

Independent re-read of the bounded claims against exact sources confirms: (1) the collision insertion predicate and ordering; (2) moving-border floor/ceil rounding and static-shape cache behavior; (3) border ticking at the client level boundary; (4) the client packet-listener tick's distinct interface and caller path; and (5) the sole 1.21.9 `collectAllColliders` call-site and its excluded bed-eligibility purpose. Release identity and source hashes were checked for each scoped release, including the hidden manifests. No product files, Git history, backlog, wiki inventory, tests, game launches, or decompiler outputs were accessed or changed.

This review closes only the assigned technical evidence gate. It does not independently audit the complete movement tick graph or resolve other operations in 1.21.4–1.21.10. The source comparison establishes mechanics and applicable gates, not a measured player trajectory or runtime parity.