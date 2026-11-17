# Source cutover review: support-position factors, 1.19.4–1.20.1

## Bounded verdict

**Accepted source claim:** the on-foot supporting-block position mechanism used by friction and jump-factor lookups changes at the 1.19.4 → 1.20 boundary. In 1.19.4, the affected position is derived directly from entity position and bounding-box minimum Y. In 1.20 and 1.20.1, it derives from the main supporting block found by collision search, then applies the historical `getOnPos(float)` Y rules. The relevant 1.20 and 1.20.1 source files are byte-identical, including the selection/tie logic and friction lookup call site.

Acceptance is limited to this source-level position-selection/factor-input claim and the referenced exact sources and provenance listed below. It does not accept a runtime movement-parity claim, nor a change to normalized velocity or fluid behavior. No runtime was launched and no product changes were made.

## Decompiled source findings

All references are to the canonical Mojmap decompilation in `decompiled_minecraft/<version>/mojmap/`.

### 1.19.4

- `net/minecraft/world/entity/Entity.java:720-742`: `getOnPosLegacy()` calls offset `0.2F`; public `getOnPos()` calls `1.0E-5F`. `getOnPos(float)` floors `(position.x, position.y - offset, position.z)`. Only if that block is air does it inspect the block below and return that block for fences, walls, or fence gates.
- `Entity.java:744-758`: jump factor checks the entity block first and uses the below-movement block's jump factor only when the entity block factor equals `1.0`. Speed factor uses the same fallback ordering, except it returns the entity block's factor directly for water or bubble columns.
- `Entity.java:760-762`: `getBlockPosBelowThatAffectsMyMovement()` returns `BlockPos.containing(position.x, boundingBox.minY - 0.5000001, position.z)`.
- `net/minecraft/world/entity/LivingEntity.java:2089-2092`: the friction branch obtains this helper position, reads that block's friction, derives the on-ground multiplier, and passes the friction value to `handleRelativeFrictionAndCalculateMovement`.

### 1.20

- `net/minecraft/world/entity/Entity.java:233`: `mainSupportingBlockPos` is introduced as `Optional<BlockPos>`.
- `Entity.java:554-587`: setting ground state invokes `checkSupportingBlock`. When grounded, it searches an AABB with feet footprint and vertical extent `1.0E-6` immediately below `boundingBox.minY`. A found support (or `onGroundNoBlocks`) stores that result. If direct search is empty and known movement exists, it moves the support slice by `(-movement.x, 0, -movement.z)` and retries. Grounding false clears the tracked support.
- `net/minecraft/world/level/CollisionGetter.java:95-109`: `findSupportingBlock` enumerates `BlockCollisions`, scores each candidate with squared distance from block center to `entity.position()`, and retains the nearest. Equal-distance candidates replace the current result when `current.compareTo(candidate) < 0`; thus the tie favors the greater `BlockPos.compareTo` order among candidates encountered.
- `net/minecraft/world/level/BlockCollisions.java:32-39,64-89`: collision context is made from the entity; candidate block states provide collision shapes; a full-block shape must intersect the query box, while other nonempty shapes must intersect it under `Shapes.joinIsNotEmpty(..., BooleanOp.AND)` before being yielded.
- `Entity.java:785-820`: public `getOnPos()` calls `getOnPos(1.0E-5F)`. With a tracked support, the exact threshold test returns the support position directly for this offset. Otherwise the helper floors entity X, `position.y - offset`, and Z. The movement-factor helper calls `getOnPos(0.500001F)`: for ordinary supports it retains support X/Z and sets Y to `floor(position.y - 0.500001F)`; walls and fence gates retain the support position. The fence special case retains support Y only for offsets `<= 0.5`, so it does not apply to `0.500001F`.
- `Entity.java:817-830`: jump factor uses the movement-factor helper as the below-block candidate, preserving the entity-block-first / factor-equals-`1.0` fallback rule. Speed factor keeps the water and bubble-column guard.
- `net/minecraft/world/entity/LivingEntity.java:2109-2112`: friction still selects the helper position, reads that block's friction, and passes the friction value to the movement calculation.

### 1.20.1 and controls

The four scoped source files (`Entity.java`, `CollisionGetter.java`, `BlockCollisions.java`, `LivingEntity.java`) have exactly the same SHA-256 in 1.20 and 1.20.1, and their bytes match. The 1.20.1 source therefore confirms the 1.20 support-search selection, tie rule, `getOnPos` behavior, and friction lookup behavior without a cutover inside that interval.

## Source and provenance digests

SHA-256 values below are full digests. The four source file paths are the same in each version:

- `net/minecraft/world/entity/Entity.java`
- `net/minecraft/world/level/CollisionGetter.java`
- `net/minecraft/world/level/BlockCollisions.java`
- `net/minecraft/world/entity/LivingEntity.java`

| Version | Entity.java | CollisionGetter.java | BlockCollisions.java | LivingEntity.java |
| --- | --- | --- | --- | --- |
| 1.19.4 | `3667fee610cbc5f58012e3a8fb8d6c4849f649fb7fe5300595158112d8b4b58b` | `ed62e6b800ec057c1269d3cc4e2fb7c0cc2037370223fac99f41371e000c6b5c` | `11fecadc012d0a544097bf0febfa1a83da5a534e6182ee7df7b2334b6ee80127` | `c8d91af61f87aaa1666de79696d7cd9d4a8212d05f873bdaf28d7bb2926bf165` |
| 1.20 | `94b9c3656715de2d61fa9a02ccef164261e50eb13d6090a6f07e58fe9c5b0759` | `8e4863afe40705497b36d0a58d8ee11e5103f4e72cb642aa63cc57f57649a130` | `3313a38f9ebbd389f62e95dcbedab6a4f7f3cd3920b9ffa0ecf1a77dd869725b` | `decf8cd70d194098ad51d0c82e5a0087e0687881e0a2dc66456f2ba6ea76676f` |
| 1.20.1 | `94b9c3656715de2d61fa9a02ccef164261e50eb13d6090a6f07e58fe9c5b0759` | `8e4863afe40705497b36d0a58d8ee11e5103f4e72cb642aa63cc57f57649a130` | `3313a38f9ebbd389f62e95dcbedab6a4f7f3cd3920b9ffa0ecf1a77dd869725b` | `decf8cd70d194098ad51d0c82e5a0087e0687881e0a2dc66456f2ba6ea76676f` |

### Version-root and mapping provenance

| Version | Provenance / source index | Other root controls |
| --- | --- | --- |
| 1.19.4 | `mojmap.provenance.json`: `0efcfc6075852711cff5d62c3177258cba273c206c8a2d2642bdf3487c676b34`; `mojmap.sources.sha256`: `6286e325371e085dfee1ab7987e66d4b6d1a984a49b6ec8ea730045cd24cecb3` | `mojmap.ready.json`: `59f59ab516d41eb97e09d63256ee26a68c15102052bf7acafdbd5440b72547d0`; artifact manifest: `e84b8441615fd386ccdd3bfde71b7b1d658061e9383daffe41842e9acb7afb72` |
| 1.20 | Hidden mapping provenance `mojmap/.decompile-provenance.json`: `573f0bb3e18e39ae882769a007a1c97d711da123034f9a1c894eb407ef58030f` | No version-root manifests or ready marker present. The hidden mapping provenance records hashes for every source; the four audited values above match those records. It records Mojang client jar input `https://piston-data.mojang.com/v1/objects/e575a48efda46cf88111ba05b624ef90c520eef1/client.jar` SHA-256 `5ac9ead135746f5f27048f2e01d8d5bdc2dabc4d91422f73ef2435f4c3d24aff`, and mapping text input `https://piston-data.mojang.com/v1/objects/a4cd9a97400f7ecfe4dba23e427549ebc5815d66/client.txt` SHA-256 `fdd921d6bcc7657850f78514d36bb57af0b8a340786d12c46ec18d9c46c039de`. |
| 1.20.1 | `mojmap.provenance.json`: `5aeb1df09671480aeddb921c919e92098e33d0a0bce47e22f82a865cb32400aa`; `mojmap.sources.sha256`: `858b56764113e591c60d09bc63e4d90f9c1d67a705645f0c793e8b28f2378465` | `mojmap.ready.json`: `f8dc81374dc42521ee9d17a04ea0dc77f1c5384548cfc01c9621b3d84b670fed`; artifact manifest: `5f1fc7dcd4ee82ddb7ea0c3be6fd21ceb1910cc691594bda163bfa19b01c7db2` |

The 1.19.4 and 1.20.1 source hashes match their version-root source manifests. Version 1.20 has no version-root controls, but does have the hidden mapping-local provenance file whose full digest and relevant per-file digests are recorded above. The 1.20.1 ready metadata's decompile batch starts at 1.20.1; the separate 1.20 mapping-local provenance was used for the 1.20 source check.

## Runtime boundary

This is an independent, read-only source audit. No implementation, gameplay launch, TAS/Gym/server run, build, unit test, or parity simulation was performed. Source evidence establishes the code-level cutover and its boundaries only.