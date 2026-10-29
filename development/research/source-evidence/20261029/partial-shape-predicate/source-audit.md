# Independent audit: 1.21.2–1.21.4 partial-shape sweep alias closure

## Binding and verdict

Audited input: `.native-source/20261029/partial-sweep-1212-alias-closure/report.md`, SHA-256 `554fca52d724e8133ec090f8d1b28f3daea443c926682df21b62e133d0816ab4`. Its `report.sha256` sidecar contains the same value, and I independently recomputed the report hash.

**Verdict: ACCEPT for the bounded source closure.** I independently checked the claimed source identities and selected complete files, retraced the sweep calculation and candidate traversal endpoints, and checked the stated local-player, End Portal, Tripwire, and piston gates. I found no material error or claim that exceeds the declared slice. This accepts only the 1.21.2–1.21.4 partial entity-inside-shape sweep, candidate enumeration/input lifecycle, and stated reachability paths; it is not a full-pair freeze, implementation decision, trajectory result, or runtime-parity verdict.

Related previously audited input: `.native-source/20261028/frontier-c/1213-1214-block-segment-callback-closure/report.md`, SHA-256 `11c1e42f76911b4f527e3dc360e24834d81ea15374a12f6e41fc90a25a5b0c50` (matches its sidecar). Its independent audit is `.native-source/20261028/frontier-c/1213-1214-block-segment-callback-audit/audit.md`, SHA-256 `66772dbc839f21fa0243b5f3e87f40be2726bd35916a53fadda62c64fcd0651f`; that audit passes the bounded 1.21.3–1.21.4 callbacks, traversal, local-player, piston, and recorder closure. The added 1.21.2 alias checks are consistent with that verdict.

## Canonical source identity and hashes

The checked source roots are the canonical read-only `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\<version>\mojmap` trees.

- **1.21.2:** `.decompile-provenance.json` SHA-256 `00f399407dc447737ea83e3e46562418f8c712f66e3f51cb85d46afa2ecac575`. It has 5,654 embedded source records; the recorded `Entity.java` digest matches the file. This legacy publication has no ready marker, so the per-file provenance records are the applicable source-integrity evidence.
- **1.21.3:** `mojmap.ready.json` SHA-256 `72385aa99d249a56d742b9c07823c1155c74c39dda1fbc6eacc12c980cd12e02`; it identifies `versionId=1.21.3`, `mapping=mojmap`, `status=ready`. Provenance `mojmap.provenance.json` SHA-256 `24093437c0cd41d807ab0829068ef97828b65bbb95b38dfdc4f706883a8483d7`. The source manifest is SHA-256 `d673bb5464853e3a2a92ed9b1ffe1789d4e62c097bb884f67c336fcbb3b178ce`, with 5,655 entries and 26,544,115 source bytes; all agree with the ready marker.
- **1.21.4:** `mojmap.ready.json` SHA-256 `1c6617b7acf8bb357f77c6c6c276881c73d882bda1f93f6dd217bb54eb8aa4be`; it identifies `versionId=1.21.4`, `mapping=mojmap`, `status=ready`. Provenance `mojmap.provenance.json` SHA-256 `ab0439864c89f4e0dab09daae7b57ab973368eeb0320626822e06f5a9bce9fb9`. The source manifest is SHA-256 `f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0`, with 5,744 entries and 26,784,732 source bytes; all agree with the ready marker.

I recomputed complete-file SHA-256 values and checked them against the 1.21.2 embedded records and the 1.21.3/1.21.4 source manifests. Every row below matches its recorded digest. Each listed 1.21.2 and 1.21.3 file is byte-identical; the 1.21.4 hashes are included to bind the compared endpoint evidence.

| Relative source file under `net/minecraft/` | 1.21.2 SHA-256 | 1.21.3 SHA-256 | 1.21.4 SHA-256 |
| --- | --- | --- | --- |
| `world/entity/Entity.java` | `a93719c302a0381a972af75ea360465e2e3551708dd07c34d4d40b7e5173c2b9` | same | `05f18ef2ec0413fc010230407c812a11553eb5123b68d21b5d7b2c0c175698ad` |
| `world/entity/LivingEntity.java` | `087390495f4fdfd14b9e12230e7aea4fdc50913bb4d1d91119e72892881cfc52` | same | `e62ce650af0a5ac97e4d0a2ba7cad4ba68a8d098525609f8b5b6976a8ab0ae30` |
| `client/player/LocalPlayer.java` | `fbd40f1f47adfa66dda9b15188e5dce82af3e8e8d7c3dd0543e602a354ad3fe0` | same | `145686ebdc7f0d12a64070309073665eb8695b7e09911a723e86113a77d04611` |
| `world/level/BlockGetter.java` | `5d6fde6fef7928603ff97f4a4015efbb48442f9e583f1d0cfe368c5168e01c39` | same | `8bd936e3ecac5720a36b675f4910801e9d201e899b34c316c6d0be9a12d4da20` |
| `world/level/block/state/BlockBehaviour.java` | `4d92417129eb6def084a67d249d2ff8c7a30a4767fa46e63a045667ea70d5133` | same | `a549c5dcf7768f89f18519bdd2f985ef498d1bd4f6b329a30e0088e437819ddf` |
| `world/level/block/EndPortalBlock.java` | `725bbf4f36daa1f57f343d3f3123a6c527f13d7bf3c51fba1b27515b65c8c863` | same | `9f364e00f466e7cb10da6e5e3ab4719f32940529f18f5a5b9b30fcd1543cbfb9` |
| `world/level/block/TripWireBlock.java` | `8fc4647ed98d48434dc3ac10590507156b62fce924d2d7fd28ea40cf9f466012` | same | same |
| `world/level/block/piston/PistonMovingBlockEntity.java` | `426442149f89a28fc5973a52b8156aec7c18d76faf8b62eecae9f120b2fdba16` | same | same |
| `world/phys/AABB.java` | `1990e0e1058e9ac5201c32e51c6a5f9b671e7ed00ba863aacf2c5b7b0adff217` | same | same |
| `world/phys/shapes/VoxelShape.java` | `440519ae483df99d05f1d2be8c3e245055b30bb5b676230b70b7be01dc9dea96` | same | same |
| `world/entity/EntityDimensions.java` | `9fb9575ec615d4aefe6316c443e87a902f44e955393778a4b93623e8b680adc4` | same | same |
| `world/phys/shapes/Shapes.java` | `ab6c69c27c07c3cfa82e85906888036c34a46188e1bde5663e980dea3f6c2a75` | same | same |
| `world/level/block/Block.java` | `4ba7c916ae7c4ee9174c468e9a1f56218d2bcac16f74dd6080c561f945ea4d3a` | same | `857a7f02c86cbaff6b889032145677a0cc2255814dd983c0fae393cab0cb8a68` |
| `world/phys/Vec3.java` | `62581d3005cc32401545f9229a9c293dfd61c52e7d30f88456c49d2c2867e68d` | same | same |
| `core/BlockPos.java` | `aa1017d0cd97ddf59c45efa21e3c8970284fb237d6335e7e34f5627ed37a69b3` | same | `a01661d6880dab86f2adb8c8bc203484f63bc0e10eaa14e36525e7123a2d4d15` |

The additional whole-file hashes for `Block.java` and `BlockPos.java` differ in 1.21.4, but the used `Block.box(...)` method (line 143) and `BlockPos.betweenClosed(AABB)` method (1.21.2 line 357; 1.21.4 line 358) were directly compared and retain the cited behavior. The `Shapes.block()/box()` and relevant `Vec3` arithmetic methods also match across the three versions. Thus the report does not rely on full-file aliasing for those two 1.21.4 files.

## Geometry, helper ordering, and traversal

- In 1.21.2/1.21.3 `Entity.applyEffectsFromBlocks()` passes `oldPosition()` as `from` and current `position` as `to`. The vector overload appends that `(from,to)` pair after the grounded `stepOn` path, checks movement entries in list order, then clears the movement list on the ordinary completion path. Those complete `Entity.java` files are byte-identical.
- For a non-sentinel entity-inside shape, 1.21.2/1.21.3 `collidedWithShapeMovingFrom(from,to,...)` builds the current final `getBoundingBox()`, recenters it by subtracting its own center, moves it to `to`, then computes `from - recenteredMovedBox.getBottomCenter()`. It invokes `collidedAlongVector` on the current final bounding box. `AABB.collidedAlongVector` tests its center and center-plus-vector against the shape boxes inflated by half the entity-box extents, then clips that segment. This is the reverse sweep from the final-position box described in the report.
- In 1.21.4 the helper instead builds `makeBoundingBox(from)` and passes `to.subtract(from)` to `collidedAlongVector`: a forward sweep from the segment-start box. `makeBoundingBox(Vec3)` uses the entity dimensions at the given position. The report correctly separates the predicate geometry change from the candidate-bound/list-snapshot changes.
- `Entity.checkInsideBlocks` in 1.21.2/1.21.3 calculates one deflated current bounding box before iterating movements; 1.21.4 makes a deflated `makeBoundingBox(to)` inside each movement iteration. In every version it passes the same `from`, `to` order to `BlockGetter.boxTraverseBlocks` and the shape predicate.
- For displacements with squared length below `Mth.square(0.99999F)`, `boxTraverseBlocks(from,to,box)` returns the ordered AABB cell iterable directly. For longer displacements, 1.21.2/1.21.3 define `delta=to-from`, `epsilon=normalize(delta)*1.0E-7`, and pass `(box.min-delta-epsilon, box.min+epsilon)` to `addCollisionsAlongTravel`. 1.21.4 passes `(box.min-delta, box.min)` with no epsilon. It then adds the AABB cells to the ordered linked set. `BlockGetter.java` and its helper are byte-identical at the .2/.3 alias boundary; the report states endpoint construction without claiming coordinate-independent candidate outcomes.
- For 1.21.4 only, `applyEffectsFromBlocks` snapshots `movementThisTick` and clears the live list before calling `checkInsideBlocks`; this is a distinct lifecycle change and is not folded into the sweep-direction claim.

## Reachability and gates

- The ordinary local-player route is supported: `LocalPlayer.tick()` calls `super.tick()`; the `LivingEntity.aiStep()` callback runs under `!level.isClientSide() || isControlledByLocalInstance()`. `LocalPlayer.isLocalPlayer()` and `isEffectiveAi()` return true, so the controlled-local-instance gate can admit the local client. The report limits this to local-player reachability.
- The End Portal is a valid partial-shape example: its `SHAPE` is `Block.box(0,6,0,16,12,16)`, and `getEntityInsideCollisionShape` returns the block state's shape. Its callback calls `setAsInsidePortal` only when `canUsePortal(false)` passes; the End credits branch is restricted to a server-side End `ServerPlayer` who has not seen credits. This is a conditional reachable player response, not a claim that all portal overlaps teleport or that bed-entry eligibility is in scope.
- Tripwire also returns its state shape, but `TripWireBlock.entityInside` encloses its behavior in `!level.isClientSide()`. It therefore supplies no Tripwire callback effect on the local client through this operation. The report does not count it as a client-side movement response.
- During the piston tick's `progressO < 1.0F` branch, `moveCollidedEntities` is called without a logical-side condition. Its push helper sets the piston noclip state, calls `entity.move(MoverType.PISTON, delta)`, calls the no-argument `entity.applyEffectsFromBlocks()`, then clears noclip. This supports the stated conditional local-player entry path and the report's careful refusal to infer repeated callback outcomes or a concrete trajectory.
- The full-block sentinel path is sourced in `Entity.checkInsideBlocks`: only a shape other than `Shapes.block()` is subjected to `collidedWithShapeMovingFrom`; both sentinel and admitted partial shapes reach `entityInside` and `onInsideBlock`. The base provider returns `Shapes.block()`. The two observed overrides are End Portal and Tripwire; the latter's callback gate is respected by the closure.

## Scope limits

The accepted evidence establishes byte aliasing for the selected complete .2/.3 source files, the stated .4 source changes and unchanged helper behavior, and the conditional caller paths. It does not prove every block/state/cell candidate outcome, every caller of the general entity operation, intermediate patch introduction, any realized trajectory delta, complete 1.21.2–1.21.4 movement coverage, implementation applicability, or runtime parity. No product files, source artifacts, findings, or history were changed; no build, test, or runtime command was run.
