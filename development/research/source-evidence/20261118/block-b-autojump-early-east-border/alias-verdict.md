# Independent audit: early AutoJump east-border source alias

## Verdict

**ACCEPTED for the supplied conditional fixture; no source-alias or manifest-pin defect found.** In the exact locally published Feather Gen2 build.2 sources for 1.10, 1.10.1, 1.10.2, 1.11, 1.11.1, 1.11.2 and 1.12.2, the prior-state player-center predicate admits a loaded exterior east-face cell as synthetic stone on the non-enforcing entity-collision query. Its full-cube collision AABB can enter the AutoJump sweep, intersect a lateral segment, and produce the fixture's rise of 1.0 block, which passes the strict `> 0.5F` / inclusive `<= 1.2F` scheduling test.

This verdict is conditional on the supplied preconditions, especially the accepted movement delta and input state. It does not establish an ordinary movement producer for `dx = 0.1F`, nor runtime tick parity. That producer/reachability check remains open if the result is to support an unconditioned ordinary-player claim.

## Immutable input and source identities

- Audited only `early-border-alias-source/evidence.md`; its current SHA-256 is `a8143fc88eedd05955dd9b64a7803d77d4a83749800b4a9f133d2d138ac547b2`, matching the assigned immutable identity.
- Independently recomputed each version's AutoJump/player source and `World.java` SHA-256. They match the source report's identities: the AutoJump method digest aliases exactly across 1.10/1.10.1/1.10.2 (`a9637065…fed443`) and across 1.11/1.11.1/1.11.2 (`65c2747b…8f59ed`); 1.12.2 is `01a58e94…9823cc`. All `World.java` digests also match the report's table.
- Recomputed all six relied-on Java source hashes from the embedded `.decompile-provenance.json` `sources` maps for 1.10 and 1.10.1; all match. For 1.10.2 through 1.12.2, the actual source-manifest hashes match each ready marker's `sourceManifestSha256` and the report's table; current `LocalClientPlayerEntity.java` and `World.java` byte hashes match the table. Relevant Feather Gen2 build.2 mapping JAR/Tiny pins match `artifacts.sha256` (1.10.2–1.12.2) or embedded provenance (1.10/1.10.1). The checked manifest digests are recorded in the source report; no source tree was generated or modified.

## Independent source trace

### Cached center predicate and state

At 1.12.2 `World.getCollisions(Entity, Box)` calls the private collision query with `enforceWorldBorder == false` (World.java:1017–1020). The query caches `entity.isOutsideWorldBorder()` and `isWithinBorder(entity)` once before scanning (964–974). With the fixture's prior outside flag false, `isWithinBorder` expands each border edge by one block and compares the entity center strictly (`1042–1059`). A center strictly inside the eroded rectangle therefore passes this expanded-center check. There is no full entity-AABB or swept-path containment test. Since cached `bl` and `bl2` are false and true respectively, the conditional state update at 989–991 does not alter this state.

The same relevant logic is present in 1.10.2 and the 1.11 line (for example 1.10.2 World.java:912–945 and 965–987). The sampled chunk is tested at `(x, 64, z)` before its Y loop. The two scan-corner columns are pruned (`q != 2`); side columns omit bottom and top Y caps, while interior columns retain those caps. The foot-level face cell is not a corner or scan cap in the supplied swept box and remains eligible. In 1.12.2 the side-cell condition omits only the top cap; the same fixture cell remains eligible.

### Exterior east cell to stone AABB

The cached predicate gates the per-cell fallback: 1.12.2 uses `Blocks.STONE.defaultState()` when the query is non-enforcing, the cell is not contained by `WorldBorder`, and `bl2` is true (World.java:993–1001). The earlier checked lines initialize the same stone fallback and select it for an exterior cell under the same successful center predicate (1.10.2 World.java:912–940; 1.11.2 World.java:968–997). `WorldBorder.contains(BlockPos)` treats a cell at the strict east maximum as outside; its overlap test includes `pos.getX() < getMaxX()` (1.12.2 WorldBorder.java:24–26). With the supplied wall-cell placement at X=10, the exterior cell therefore takes the synthetic fallback.

The fallback goes through ordinary `BlockState.addCollisions`. `Block.getShape` returns the full unit cube (1.12.2 Block.java:307–308); `addCollisions` translates the shape to the position and appends it only when it intersects the AutoJump query box (372–380). Thus the candidate is an ordinary unit AABB for the remainder of the AutoJump collision list.

### AutoJump consumer and fixture math

`LocalClientPlayerEntity.move` records prior X/Z, delegates movement, then calls AutoJump with actual position deltas (1.12.2:846–851). Its gates require enabled AutoJump, expired cooldown, grounded state, not sneaking/riding, nonzero movement input, and a forward-facing dot product not below `-0.15F` (857–886). It checks two headroom positions before building the query. For the supplied `dx=0.1`, `dz=0`, width `0.6`, height `1.8`, Speed II `f=0.14`, and no jump boost, the support-length term is `max(f*7, 1/l)=max(0.98, 0.1)=0.98`; the endpoint is X=10.07 before the width growth, so the swept box reaches X=10.67. The swept box is grown by width, and the two side segments are raised by `0.51F` (887–910). The loaded `x=10, y=64` candidate cell is in the scan and its unit box intersects the query and a lateral segment. The fixture's stone supports at X=8 and X=9 support the stated grounded starting state; they do not replace the synthetic wall-cell candidate.

For a unit candidate at `(10,64,0)`, its center maps to `BlockPos(10,64,0)`. AutoJump's loop visits only `t=1` because `1 < 1.2F`; it reads the block at Y=65, whose stated air collision shape leaves the candidate top `s=65`. With feet at Y=64, the rise is `u=1.0F`. The final guard rejects `u <= 0.5F` and `u > 1.2F`, so this value schedules `ticksToNextAutojump = 1` (916–948). This establishes candidate survival and the consumer's conditional scheduling behavior, not the subsequent jump execution.

## Limits retained

- The absence of an AABB/swept-path border guard is a generalization limit, not a blocker for a center strictly inside the eroded rectangle.
- The provided `dx`, active movement input, grounded state and ordinary floor support are accepted fixture inputs. This audit did not trace their ordinary production from input through all movement gates.
- No other border face, moving border, non-player entity mode, aggregate-border implementation, runtime launch or parity claim was reviewed.

## Procedure and write boundary

Read the project README, buildSrc README, orchestration WORKFLOW and OVERNIGHT, shared discovery WORKFLOW, and assigned source-lane WORKFLOW. This audit used only the assigned immutable source report, allowed exact decompiled source/manifests and the specified project/workflow guidance. No product code, Git state, backlog/wiki records, tests, builds or launches were accessed or changed. The only write is this audit file in the exclusive assigned audit directory.