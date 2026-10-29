# 1.8.9 ray provider and per-cell hit contract

## Scope and decision

This is a source-only closure of the per-cell provider/hit operation consumed by the accepted `BlockRayTraceTraversalHook.CellVisitor`. It uses the exact 1.8.9 Feather Gen2 build.2 export and 26.2 unobfuscated export, plus the accepted 1.8.9 caller/visitation inventory. It does not repeat the cache-writer/Anvil/Chest movement-consumer proof.

**Decision: not sufficiently closed for a candidate that supplies historical provider lookup and hit construction.** The old call/stop contract and every virtual `canRayTrace` / `rayTrace` override family are enumerable, and the old axis-box clip can be specified. The unresolved dependency is translating each current state to the exact old provider identity and old state/metadata, then applying the correct old shape writer before clipping. The accepted inventory deliberately does not contain an exhaustive 1.8.9-to-26.2 ID/state crosswalk or the source identity of every fixed constructor bound. A modern outline/collision `VoxelShape` cannot safely stand in for this adapter.

A sanitized candidate could implement an isolated old box-ray primitive/API contract from this note, but without the unresolved legacy-state/provider adapter that code would not supply a usable source-faithful `CellVisitor`; do not treat such a primitive as closing or repairing the camera/cache path. The generic DDA traversal candidate remains useful only when a caller supplies the missing old provider semantics.

## Exact provider dispatch

In canonical `1.8.9/ornithe-feather`, `World.rayTrace` (`net/minecraft/world/World.java:633-761`) visits the start cell, then its old DDA cells. With the camera's `Entity.rayTrace` call (`net/minecraft/entity/Entity.java:1029-1034`), flags are `allowLiquids=false`, `ignoreBlocksWithoutCollision=false`, `allowHitInStartPosition=true`. Because the second flag is false, Java short-circuit evaluation skips `getCollisionShape` as a ray eligibility test. The state still goes through virtual `Block.canRayTrace(state, false)`. A true result dispatches virtual `Block.rayTrace`; a non-null hit immediately ends the world walk. A later false result replaces the retained result with a MISS at the current segment start, entry face, and cell; a false start-cell result is not retained. If no hit occurs, only that last later-cell MISS may be returned under the camera's `allowHitInStartPosition=true` flag.

Exact 1.8.9 `Block.java` provider methods:

- `canRayTrace(BlockState, boolean)` and its default `canRayTrace()` at lines 356-362 default to true.
- `AirBlock.canRayTrace` at `AirBlock.java:30-32` returns false; `FireBlock.canRayTrace` at `FireBlock.java:297-299` returns false; `LiquidBlock.canRayTrace` at `LiquidBlock.java:69-71` rejects the camera's `allowLiquids=false` call. `StairsBlock.canRayTrace` at `StairsBlock.java:442-449` delegates both overloads to its wrapped base block.
- `rayTrace` overrides are the default `Block.rayTrace` (`Block.java:456-516`) plus `AbstractRailBlock.java:49-52`, `DoorBlock.java:207-210`, `TrapdoorBlock.java:139-142`, `TorchBlock.java:138-155`, `StairsBlock.java:502-535`, and `MovingBlock.java:106-108`.

The overrides have these required provider semantics:

| Family | Source behavior that a provider adapter must preserve |
|---|---|
| Default `Block` | Calls virtual world-aware `updateShape` once before clipping the current shared six bounds. A miss still performs the update. A hit is the nearest accepted plane intersection to the segment start. |
| Rail | Explicit `updateShape`, then `super.rayTrace`; the default path dispatches `updateShape` again. Preserve the duplicate write/call. |
| Door | Same explicit update followed by default ray path and second update. Shape is computed from combined upper/lower metadata and open/hinge/facing state. |
| Trapdoor | Same explicit update followed by default ray path and second update. Shape depends on facing/open/half state. |
| Torch | Chooses facing-dependent bounds in its ray override, then calls the default path. The default world-aware update is inherited and empty, so the chosen bounds are the clipped bounds. |
| Stairs | Performs its own ordered 8-sub-box pass. For each non-excluded sub-box it calls `super.rayTrace`, which invokes the virtual stairs `updateShape`; it nulls excluded sub-boxes and selects the hit with the greatest squared distance to the end, with strict `>` retaining the first equal-distance result. A single modern outline shape call does not express this internal order or result selection. |
| Moving block | Returns null without delegating, so the camera provider does not call its `updateShape` method. This is a deliberate no-hit provider, regardless of the geometry returned by separate collision methods. |

## Exact default ray-box math

`Block.rayTrace` translates endpoints to block-local coordinates, asks `Vec3d.intermediateWithX/Y/Z` for each min/max plane (`Vec3d.java:81-110`), and tests each candidate with inclusive bounds (`Block.java:497-516`). The `Vec3d` helpers reject an axis when squared delta is below `1.0E-7F`, and reject a plane fraction outside `[0,1]`. Candidate face test order is minX, maxX, minY, maxY, minZ, maxZ; squared distance from the segment start replaces the current candidate only on strict `<`, so exact ties retain the earlier plane. Face direction is then assigned by identity against the selected candidate. Do not substitute current `AABB.clip` without a proven equivalence: current `AABB.java:292-341` uses `getDirection` / `clipPoint` with signed `1.0E-7` delta cutoffs and a shared segment-scale result, which is a different algorithm and tie mechanism.

For the camera's fixed flags, a sufficient per-cell contract for the DDA callback is:

1. The caller resolves the exact old provider identity and old state for the visited current cell.
2. Apply old `canRayTrace(oldState, false)` before provider dispatch. For a non-start false cell, emit the old MISS (`HitResult.Type.MISS`, current segment start, DDA entry face, cell); for the start cell, emit no remembered result.
3. For a true gate, run the old provider semantics including ordered state/bounds writes and any internal sub-box iteration. Return `CellResult.hit` only for the first non-null old provider hit; on null, continue and preserve any previous remembered MISS.
4. Let the accepted traversal own cell stepping and global first-hit short circuit. Its caller must not use modern `BlockGetter.traverseBlocks` to decide which legacy cells get queried.

For a generic (non-camera) caller with `ignoreBlocksWithoutCollision=true`, reproduce the old collision-shape non-null prefilter before `canRayTrace`; that branch is not exercised by the accepted camera call.

## Modern endpoint and adapter limits

In 26.2 unobfuscated source, `BlockGetter.clip` (`BlockGetter.java:66-81`) calls modern `traverseBlocks` (`BlockGetter.java:114-181`), gets a ClipContext-selected voxel shape per block, calls `clipWithInteractionOverride`, and separately clips the fluid shape. `BlockGetter.traverseBlocks` shifts both endpoints by `1.0E-7` before selecting its initial cell. `BlockBehaviour.BlockStateBase.getShape` (`BlockBehaviour.java:678-684`) calls modern state-based `Block.getShape`; there is no corresponding old virtual `canRayTrace` / `Block.rayTrace` provider contract in this path. It does not execute old mutable `Block.min/max` writes or the old provider's repeated-call/sub-box ordering. The accepted DDA finding already establishes that traversal order differs at exact integer starts; this note does not reopen that finding.

Current outlines, collision shapes, and interaction shapes are different APIs with different selection and clipping purposes. Even where a current block has a direction/state-specific `getShape`, the old provider may write shared mutable bounds, use a state pair such as a door's combined metadata, emit a null result by provider override, or iterate multiple boxes with old hit-selection semantics. The current source can provide possible geometry, but it does not provide a mapping from all 1.8.9 numeric/provider identities plus metadata to current IDs/properties. The accepted 1.12 crosswalk is a different registry endpoint and is not evidence for 1.8.9 alias migration.

Modern classes with corresponding roles are present: old `AbstractRailBlock` maps by role to current `BaseRailBlock` (`getShape`, line 54); old `DoorBlock` to current `DoorBlock` (`getShape`, line 79); old `TrapdoorBlock` to current `TrapDoorBlock` (`getShape`, line 71); old `TorchBlock` to current `TorchBlock`/`WallTorchBlock`; old `StairsBlock` to current `StairBlock` (`getShape`, line 74); old `MovingBlock` to current piston-package `MovingPistonBlock` (separate outline and block-entity collision shape at lines 102-109). Current `AnvilBlock` and `ChestBlock` expose state-based `getShape` providers. These are class-role correspondences only, not verified per-ID/per-state migration or old provider equivalence. Current `MovingPistonBlock` dynamic geometry does not change that old `MovingBlock.rayTrace` is an unconditional null provider.

The already accepted provider inventory lists all 35 world-aware shared-bound writer overrides and its source fingerprints, but it explicitly leaves the rest of movement-consumer composition open. Its writer list also is not a registry mapping or a fixed-constructor-bound inventory. Therefore no complete set of historical first-hit occluders is closed by the present evidence. This is the precise gate before creating an integrating candidate: an independently audited 1.8.9 registry/state crosswalk plus old-provider/fixed-bound shape mapping for every legacy provider that can be first hit, with explicit native fallback for unknown/current-only IDs. Keep `V1_8` native: 1.8 and 1.8.1 sources are unavailable; this note establishes no earlier profile coverage.

## Evidence identities

| Evidence | SHA-256 |
|---|---|
| Accepted DDA traversal candidate, commit | `1ca8b8308cf20cb7636306cf366d86aed8cd1967` |
| Current accepted 1.8.9 provider inventory | `5B970E9685C73E044F51771CA5FE79F00E4D97984F44E17F82978CB6EF05914E` |
| Writer-table row-format correction | `A717C149BE0F8301839552B2CEAFCFA6F381B9E7E370FBF230207BAB7703D0C4` |
| 1.8.9 `Block.java` | `EA10F05106A3CF7189AEC85236A7ECF9C7106A717F37BED583B5EA4ADEFFA528` |
| 1.8.9 `StairsBlock.java` | `FADC964480227F6FFD5756471212B18D0172BFFBCD0866EE0C278454AB079409` |
| 1.8.9 `TorchBlock.java` | `20AC458631CDF68B24E739AC100616BB024EC2200262A4DE66AD5C59E7967A5B` |
| 1.8.9 `MovingBlock.java` | `700010B071738738F04E0508050590D8D625F0115FC4886FEF6E262F85C6E85` |
| 1.8.9 `World.java` | `3C04F5B874FBB6692039164C882A3C47FE28C7ECA864E91C9AC57798153888EE` |
| 1.8.9 `Vec3d.java` | `40CA6E908D709CB16F325A0D3FF2D4131D12FB96FF74446FE382A693ED30A831` |
| 26.2 `BlockGetter.java` | `623A44206DB93D8FB2EEFC9353345689CC35E0C1742B5D61789EAB2B098B687D` |
| 26.2 `AABB.java` | `1FFDD0F6D26A4DD1E26407EA9A386EAB7903E43462E60C9AE5FDC50A0468F10B` |
| 26.2 `BlockBehaviour.java` | `9C7A103492D0714C90397DA88EB696912FF6A9CA1C005D984C4746D52637FD1E` |
| 26.2 `Block.java` | `CEC6A05E644E4A7FEB8253CC4CA772A98F0E116FB098A1B7EE7302984AC7ECAB` |
| 26.2 `BaseRailBlock.java` | `0A76FF60D5D21999FA03C7354F2DA4643FF3FB7EAB432D0ED6F84183E980C3A3` |
| 26.2 `DoorBlock.java` | `CBBFB567E366D043ECC8742188F7DE36799B54D5EA46E0BB944688EEBA7760C5` |
| 26.2 `TrapDoorBlock.java` | `3FE7FF071A7BF9D03B072DD453FE18D546F5D7F780839A39E5EA2D08BB48F3F8` |
| 26.2 `WallTorchBlock.java` | `3165F2003545CDD4D9FB39B6B0B14D7956D536250BD631C663CD677F0D19D42B` |
| 26.2 `StairBlock.java` | `2A8AD6EEDC5A151AF7A739CCD476F18CAB4FE78E9DF7C1EC601CB9687A77630B` |
| 26.2 `piston/MovingPistonBlock.java` | `9A8D9B03BE6866B1E5A2D237F8CB635B89C08E63A241DB2D32338448240102D1` |
| 26.2 `AnvilBlock.java` | `A15B5EF5067F098A2E6B83D3B934730F376A3420A4CE47C798A6A80D800B9391` |
| 26.2 `ChestBlock.java` | `8BE5C44FE3E8B5C9F529F58E0E3694A7CE1590A1957C31B72CBEC7B97E061D22` |

Canonical roots are `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/1.8.9/ornithe-feather/` (Feather Gen2 build.2) and `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/26.2/unobfuscated/`. No product, backlog, tests, build, game, runtime or source generation was read or run.
