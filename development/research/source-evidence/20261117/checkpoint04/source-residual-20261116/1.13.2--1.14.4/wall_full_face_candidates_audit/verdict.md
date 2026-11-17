# Independent source audit verdict: wall full-face candidates

**Verdict: ACCEPT**

Reviewed report: `build/source-residual-20261116/1.13.2--1.14.4/wall_full_face_candidates/report.md`  
Reviewed report SHA-256: `1f954f1a1543a7c192deb1d84910d4ab2915d7c22e7e70667fd54f283fdbbf3e`  
Source identities: 1.13.2 Ornithe Feather Gen2 source manifest SHA-256 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`; 1.14.4 Ornithe Feather Gen2 source manifest SHA-256 `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdb5ea3c9f75bc`.

## Audit basis and result

I independently checked the pinned manifests, every source-file hash listed in the report, and the reused `walls.json` / `leaves.json` payload hashes. They match the supplied identities. The reviewed report file itself matches its bound SHA-256.

The reported predicate chain is accurate. In 1.13.2, `Block.isExceptionForPlacement` names cauldron, glass, glowstone, ice, sea lantern and conduit, and `isExceptionForAttachment` includes that chain. `WallBlock.shouldConnectTo` rejects the generic solid-face arm for those blocks. In 1.14.4, the attachment exception list no longer contains those six candidates; `Block.isFaceSolid` evaluates the directional collision-shape face and excludes leaves. The checked target classes inherit the default full-block shape where applicable; `GlassBlock` and `IceBlock` inherit `TransparentBlock`, which adds no shape override.

The per-key and state results also hold:

- `minecraft:glass`, `minecraft:glowstone`, `minecraft:ice`, and `minecraft:sea_lantern`: each is the single registered state described in the report. Old attachment rejection yields false; target full collision faces yield true for all four horizontal directions.
- `minecraft:cauldron`: `LEVEL_3` is exactly 0 through 3. The old attachment rejection dominates its old horizontal `SOLID` face shape. The target shape is state-independent and retains each full horizontal outer face, so all four levels yield true in every horizontal direction.
- `minecraft:conduit`: `WATERLOGGED` has both boolean values. The old face shape is `UNDEFINED`; the target 5/16-to-11/16 cube is state-independent and does not reach a full outer face. Both old and target results are false for both states and all horizontal directions.

The 1.13.2 registrations confirm the two old wall IDs instantiate `WallBlock`; its horizontal `MIDDLE_POLE_THICK` face is property-independent and passes the old pole arm. In the target, both IDs are registered wall blocks and present in the exact 14-entry `WALLS` payload; the 12 remaining tag IDs are not 1.13.2 registrations. The target `WALLS` arm independently makes every state of both common IDs connect in every horizontal direction. Thus the reported no-difference verdict for these common wall states is supported.

## Corrections

None. The candidate dispositions, state coverage within the assigned slice, and closure statement are supported. This audit accepts only this bounded residual report; it does not establish full-pair coverage or freeze.
