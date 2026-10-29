# Independent review: trapdoor collision provider closure

**Verdict: ACCEPT, within the report's stated provider-family scope.** I independently checked the report and its hashes against the canonical read-only 1.8.9 and 26.2 source trees. The closure supports default collision-shape equivalence for old block IDs 96 and 167 over metadata 0–15. It provides no evidence about DDA visitation order, ray hit ordering, or runtime parity, and makes no such claim.

## Source and hash checks

- All 17 entries in the supplied `sources.sha256` match the referenced bytes, including `report.md` when resolved against the closure directory.
- All 10 listed Java source files match their canonical source-manifest entries. The source-manifest digests match the ready-marker values: 1.8.9 `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004`; 26.2 `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894`.
- The reviewed report hash is `691c23ecd2342d08aa43b8fb59ce187b14804ddcc1497c9e37de56b04b8533a0` and its supplied manifest hash is `7609d8e9c8dc664a3f6aa211ea32b9c5d047fc1999061a04942c008c4816d1ad0`.

## Findings

- Old `Block.java` registers ID 96 as `trapdoor` and ID 167 as `iron_trapdoor`, both using `TrapdoorBlock` with wood and iron materials respectively.
- Old `TrapdoorBlock` has FACING, OPEN, and HALF, defaulting to north/closed/bottom. Metadata decodes the low two bits as north, south, west, east; bit 2 as open; and bit 3 as top. All 16 metadata values are valid combinations. For open states the shape code ignores HALF.
- `getCollisionShape` refreshes mutable instance bounds from the current state at the queried position before `Block.getCollisionShape` copies the bounds into the world-space box. The closed bottom/top and four open-panel boxes in the report match the source assignments. `rayTrace` refreshes before delegating, and base `Block.rayTrace` refreshes again. The reset strip therefore does not establish stale bounds for these collision or ray-trace calls.
- Current `BlockStateData` has 16 old `minecraft:trapdoor` aliases to matching `minecraft:oak_trapdoor` states (IDs 1536–1551), and 16 iron state rows (IDs 2672–2687) retaining `minecraft:iron_trapdoor`. The old facing/open/half tuples match. This is state-alias evidence, not a claim about every conversion path.
- Current `TrapDoorBlock.getShape` chooses the open facing shape, or DOWN/UP for closed top/bottom. `Block.boxZ(16,13,16)` yields the north-seed coordinates; `Shapes.rotateAll` maps EAST/SOUTH/WEST by Y rotations and UP/DOWN by X rotations. The coordinate transforms agree with the old four open panels and closed top/bottom boxes. Current extra POWERED and WATERLOGGED properties are not read by this shape method.
- Current oak and iron registrations omit `noCollision()`. `BlockBehaviour.Properties.hasCollision` defaults to true and the collision method returns the state shape when enabled. This closes the normal collision-shape gate for these registrations.

## Scope boundary

Accept this as a bounded provider-family input for a future operation. It does not prove which blocks an old or current ray traversal visits, their order, first-hit behavior, or mutable-bound observer parity. It also does not establish outline, placement, fluid, redstone, or other modern behavior equivalence.
