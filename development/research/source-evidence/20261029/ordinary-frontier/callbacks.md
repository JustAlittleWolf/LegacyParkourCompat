# Local-player movement callback and phase closure: 1.17.1 → 1.18.2

## Disposition

This is a bounded source-only closure of local-player movement callback phases and direct position/velocity response calls. It is not a whole-pair freeze. The 1.18.2 `Entity.move` path adds an earlier fall-distance reset predicate before committing the clipped position. The compared local-player auto-jump wrapper, landing/step callback order, and inside-block callback loop otherwise retain their reviewed control flow. Collision/ray shape providers and state-specific callback implementations remain dependencies.

## Immutable inputs and source identity

This closure reuses, and does not regenerate, the complete path/hash ledger from `.native-source/20261028/frontier-c/1171-1182-full-inventory/java-path-classification.tsv` (SHA-256 `11f7bc1d88b5947b0de13962dee93be8339028165f2d083b1448078f1bbae3ff`). Its independent full-inventory audit is `.native-source/20261028/frontier-c/1171-1182-full-audit/audit.md` (SHA-256 `8379db16e7e252f6c140d2dce33bb8560493e4279be98abdcbcb284c302f5e1a`). The audit binds the exact Mojmap endpoint manifests, ready markers and 4,142/4,236 Java-source inventories. The selected canonical sources were rehashed for this closure; the hashes and exact paths are in `source-hashes.tsv`.

Canonical sources are read-only under `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\{1.17.1,1.18.2}\mojmap`. No product, history, wiki, build, test, or runtime materials were used.

## Ordered local-player path and findings

### Ordinary travel and block callbacks

`LocalPlayer.move` is an override in both endpoints (`LocalPlayer.java`, 1.17.1 lines 893–897; 1.18.2 lines 889–893). It saves X/Z, calls `super.move(moverType, requested)`, then invokes `updateAutoJump` with the actual post-move X/Z delta. Thus piston displacement and ordinary travel both enter the same wrapper when the moved entity is the local player; auto-jump runs only after `Entity.move` returns.

Within `Entity.move` (1.17.1 lines 534–651; 1.18.2 lines 543–679), the reviewed common order is: collision result; position write (subject to the small-length guard); collision flags and on-ground state; support block lookup; `checkFallDamage`; horizontal clipped-velocity zeroing; `updateEntityAfterFallOn` if requested Y differs from clipped Y; `stepOn` if on ground and not stepping carefully; movement bookkeeping/emissions; `tryCheckInsideBlocks`; then block-speed-factor horizontal velocity multiplication. The landing and step callback gates and their relative order are unchanged.

The changed pre-position phase in 1.18.2 is precise: after `collide` returns, it computes clipped movement length squared. If that exceeds `1.0E-7`, and `fallDistance != 0.0F`, and clipped length squared is at least `1.0`, it clips a ray from the current position to current position plus clipped movement with `ClipContext.Block.FALLDAMAGE_RESETTING` and `ClipContext.Fluid.WATER`; any non-MISS hit calls `resetFallDistance()`. Then it writes position. 1.17.1 has no corresponding ray/reset in `Entity.move`. This is a new direct movement-state write that occurs before `checkFallDamage`, `updateEntityAfterFallOn`, `stepOn`, inside-block callbacks, and velocity scaling. Its concrete effect is conditional on the queried block/fluid state and the exact ray/shape implementation; those providers are outside this closure, so no witness or trajectory is claimed.

The post-move inside-block dispatch remains in the same phase in both versions. `tryCheckInsideBlocks()` wraps `checkInsideBlocks()` and rethrows failures as a collision `ReportedException`. The latter first requires `level.hasChunksAt(min+0.001, max-0.001)`, then visits covered integer positions in nested X/Y/Z ascending loops; for each state it calls `BlockState.entityInside(level,pos,this)` and then `this.onInsideBlock(state)` in a try/catch. The dispatch remains after landing/step and movement emission, and before `getBlockSpeedFactor` and horizontal velocity damping. Block-specific `entityInside` effects are not audited here.

### Piston displacement phase

`ClientLevel.tickEntities` runs tracked non-passenger entities, then calls `tickBlockEntities` in both endpoints (1.17.1 lines 161–170; 1.18.2 lines 195–204). In both versions `PistonMovingBlockEntity.tick` advances an active piston by `progress + 0.5F`, calls `moveCollidedEntities`, then `moveStuckEntities`, and only then stores the new progress (1.17.1 lines 279–313; 1.18.2 lines 271–307). The shared `moveEntityByPiston` sets its NOCLIP direction, calls virtual `entity.move(MoverType.PISTON, displacement)`, then clears the thread-local. The compared client path has no local-player exclusion in these selectors; when the entity is the local player, virtual dispatch reaches the `LocalPlayer.move` wrapper and its post-move auto-jump call.

The piston callback sequence, movement amount adjustment, sticky-honey gate, and displacement call order are unchanged in the compared methods. `Level.tickBlockEntities` gains a `shouldTickBlocksAt` condition in 1.18.2, but base `Level.shouldTickBlocksAt` returns true; the client level uses that base implementation. Therefore this new check does not suppress client-side piston block-entity ticks in these sources. Piston shape queries, entity-list selection implementations, and collision/no-clip behavior remain provider dependencies and were not cleared.

### Auto-jump body

The `canAutoJump` gates match: enabled, no active auto-jump timer, on ground, not staying on a ground surface, not passenger, moving input, and block jump factor at least `1.0`. The subsequent threshold, direction, jump-effect, height, and `autoJumpTime = 1` logic match in the compared method bodies. 1.17.1 obtains the collision iterable through `getCollisions(this, aabb, entity -> true).flatMap(...)`; 1.18.2 uses `getCollisions(this, aabb)` and `StreamSupport.stream(...)`. The changed collision-query API and its ordering/provider semantics are owned by the collision closure; this report does not assert provider equivalence. Auto-jump still evaluates the collision/shape candidates after the movement callbacks have completed.

## Scope boundaries and open dependencies

- The new pre-position `FALLDAMAGE_RESETTING` ray is a confirmed source delta, but collision ray traversal, block/fluid shapes and predicate registration are open dependencies.
- `checkFallDamage`, `updateEntityAfterFallOn`, `stepOn`, `entityInside` and `onInsideBlock` call sites and gates were traced, but changed state-specific block implementations and their movement response are not closed here.
- The local-player auto-jump collision provider and shape enumeration are not closed.
- Direct packet/network writers are covered by the sibling closure, not this report. Fluid current/bubble, effects/attributes/equipment, and collision provider/shape traversal are also assigned elsewhere.
- This closure does not establish a full changed-file semantic inventory, patch-version introduction boundary, equivalent-world trajectory, runtime parity, or pair freeze.

