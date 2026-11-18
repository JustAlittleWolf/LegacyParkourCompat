# Independent source-claim audit: wall neighbor connection mask

**Verdict: COMPLETED**  
**Finding verdict: ACCEPT**  
**Immutable claim SHA-256:** `f74d0ffeab51dc49965e03d10e34eba56bbb68b14d88fdd2be880cec879f846c`

## Evidence identity and integrity

- Exact 1.11.2 Feather gen2 build.2 source manifest: `decompiled_minecraft/1.11.2/ornithe-feather.sources.sha256`, SHA-256 `d908c2af76598ad26999563bfef0089f1d3c8c32483eee6b108f0defa2773df0`. The matching ready marker identifies `1.11.2`, `ornithe-feather`, and `feather-gen2-1.11.2+build.2` merged/tiny mapping inputs. The assigned manifest hash is verified.
- Exact 1.12 `ornithe-feather` provenance manifest SHA-256 `27515e5f4c71e7db2f035a81716507128d77466c75380ad1ef7d140c06401467`; provenance identifies the 1.12 Feather build.2 mapping input. The assigned hash is verified. SHA-256 of all cited 1.12 source files matches the provenance `sources` map; all cited 1.11.2 source hashes match the assigned source manifest. Cited method bodies are present and intact.

## Claim review

**ACCEPT — non-stair horizontal connection predicate and mask transition.** At 1.11.2 `WallBlock.shouldConnectTo` reads the neighbor's vanilla `BlockState`; barrier is rejected, the same wall and `FenceGateBlock` are accepted, otherwise the state must be both material-solid-blocking and a cube, with pumpkin material rejected (`1.11.2 WallBlock.java:148–157`). In 1.12 the neighbor state is queried for the face directed toward the wall: north uses `SOUTH`, east `WEST`, south `NORTH`, west `EAST` (`WallBlock.java:190–196`). It connects for non-exception `SOLID` face shape, a `MIDDLE_POLE_THICK`, or a `MIDDLE_POLE` belonging to a fence gate (`146–160`). The exception predicate includes `Block.isExceptionForAttachment`, barrier, melon, pumpkin and lit pumpkin. Operator precedence retains the pole alternatives after the exception/solid-face clause.

Both versions derive the north/east/south/west virtual bits in the same neighbor-coordinate order and use the same straight-through `UP` condition (`1.11.2:188–194`; `1.12:190–196`). `addCollisions` resolves those virtual properties on the normal `forceShape == false` path and chooses `COLLISION_SHAPES[getShapeIndex(state)]` (`1.11.2:92–98`; `1.12:90–96`). The 16-entry collision table is present in both sources (`1.11.2:48–64`; `1.12:46–62`).

**ACCEPT — TNT adjacency witness and player collision reachability.** `TntBlock` constructs with `Material.TNT` in both versions (`TntBlock.java:26–28`); `Material.TNT` is translucent (`Material.java:26`), and `isSolidBlocking()` returns false for translucent material (`103–105`). Thus an adjacent TNT state does not satisfy the 1.11.2 material gate. In 1.12 TNT is not among wall connection exceptions, and its inherited `Block.getFaceShape` returns `SOLID` (`Block.java:362–364`), so this fixed neighbor state connects. With otherwise identical vanilla states, that changes the directional mask and can select a wall collision box with the added arm.

This is reachable in ordinary entity movement: `Entity.move` obtains world collisions for the player's swept shape (`1.11.2 Entity.java:564–565`; `1.12:567–568`); `World.getCollisions` reads each vanilla block state and calls its `addCollisions` with `forceShape=false` (`1.11.2 World.java:989–997`; `1.12:993–1001`). The wall then resolves its neighbor states as above. The claimed consequence is correctly conditional on a player path intersecting the added arm; source evidence establishes changed collision input, not a specific trajectory outcome.

**ACCEPT — fixed-state gates and bounded applicability.** The witness uses vanilla TNT and wall states without changing block state or consulting rendering. The comparison establishes the old predicate at exact 1.11.2 and the new predicate at exact 1.12 only. It does not establish the exact introduction patch inside that interval, behavior of versions before 1.11.2, or carry-forward behavior after 1.12; the finding correctly records the intermediate boundary as unknown. Earlier-profile applicability remains unestablished by this bounded pair.

## Residual gates

- Exact intermediate patch boundary is unknown within 1.11.2–1.12; no additional release was inspected.
- No claim is made for profiles earlier than 1.11.2 or later than 1.12 from this review.
- Stairs and adjacent patch continuity were excluded as assigned.
- Acceptance is source-level only; runtime parity was not tested.
