# Independent source audit: leaf wall connection projection

Audited packet: `wall_leaf_exception_projection/report.md`, SHA-256 `2be6c8a767c44c90cd37314344ae0af802c03f0a312b9bb7c357a81cb9843e0c`.

## Checks

- The ready markers bind 1.13.2 and 1.14.4 to the reported source-manifest SHA-256 values. The corresponding source sidecars hash to those same values. I independently hashed every Java source named in the packet; each matches its reported SHA-256.
- In 1.13.2, `Block.java` registers all six reported leaf keys directly as `LeavesBlock`. `LeavesBlock.buildStateDefinition` adds `DISTANCE` and `PERSISTENT`; the corresponding `Properties` declarations define distance 1–7 and persistent boolean, giving 14 states per key. The old wall predicate suppresses the solid-face branch for `LeavesBlock` through `isExceptionForConnection` → `Block.isExceptionForAttachment` → `isExceptionForPlacement`; its remaining middle-pole branch is false for these inherited `FaceShape.SOLID` leaves because they are not fence gates.
- In 1.14.4, `Blocks.java` registers the same six keys as `LeavesBlock`; the state properties and 14-state domain are unchanged. `WallBlock` returns `(!isExceptionForAttachment(block) && faceSolid) || (block in WALLS || gateCondition)`. The attachment exception includes `LeavesBlock`; the exact pinned `walls.json` does not list any of the six leaf keys; none is a fence gate. Thus all three branches are false for each key/state. The packet's `leaves.json` also lists exactly the six keys, and `Block.isFaceSolid` explicitly excludes `BlockTags.LEAVES`.
- The extracted tag payload hashes match the packet, and the referenced independent resource audit is bound to the cited extraction record. I checked the payload values directly: leaves contains the six keys; walls contains fourteen wall keys and none of the leaves.

## Verdict

**ACCEPT.** The bounded no-difference claim is supported for the six common leaf keys across their complete registered state domains and four horizontal wall directions: their wall connection bit is false at both endpoints. Scope remains limited to this predicate/provider family; this is not a player-trajectory result, full provider inventory, or pair freeze. No correction addendum is needed.
