# Focused follow-up: 1.21.4 endpoint lifecycle wiring

**Verdict: REVISE** for the 1.21.4 piston callback path at product commit `2194dcfdd9b0495548aa39dd1c8b3c358572469a`. The ordinary local-player post-travel drain is wired. The current piston caller supplies a different explicit segment and no V1_21_4 adapter restores the historical endpoint origin.

## Exact drain trace

### Ordinary local-player post-travel drain: covered

In exact Mojmap 1.21.4, `LivingEntity.aiStep()` calls no-argument `applyEffectsFromBlocks()` after travel (`LivingEntity.java:2786`). `Entity.applyEffectsFromBlocks()` calls the two-position overload with `oldPosition()` and `position` (`Entity.java:747-748`). The accepted record-operation provider `MovementCallbackRecord@V1_21_4` suppresses current `Entity.move` records for active client `LocalPlayer`s; it does not capture axis segments. Therefore current 26.2 `Entity.applyEffectsFromBlocks()` sees an empty movement list, and its live caller creates the needed `(oldPosition(), position())` fallback before the list scan (`Entity.java:903-913`). The `.4` `EntityBlockContactTraversalHook` provider is a no-op with `suppressPostTravel=false`; it leaves that scan enabled. The V1_21_4 traversal/contact providers then dispatch the fallback segment through `checkInsideBlocks(Vec3, Vec3, ...)`.

The empty-list behavior is supplied by the live current caller; no helper-only fallback is relied on here.

### Piston callback drain: not covered

In exact Mojmap 1.21.4, `PistonMovingBlockEntity.moveEntityByPiston` calls `move(MoverType.PISTON, delta)` and then no-argument `applyEffectsFromBlocks()` (`PistonMovingBlockEntity.java:182-185`). That no-argument call also supplies `oldPosition() -> position()` through the same 1.21.4 `Entity.applyEffectsFromBlocks()` path.

Current 26.2 changed this call site. `PistonMovingBlockEntity.moveEntityByPiston` captures `previousPos = entity.position()`, calls `entity.move(...)`, then calls `entity.applyEffectsFromBlocks(previousPos, entity.position())` and removes the latest movement record (`PistonMovingBlockEntity.java:194-197`). For selected V1_21_4, the product suppresses the ordinary move record but does not rewrite this explicit call's `from` argument to `entity.oldPosition()`.

`EntityMixin` marks the two-argument method as an explicit scan (`:156-167`) but does not modify either argument. `EntityCallbackBatchMixin` bypasses its custom batch drain while that explicit-scan depth is active (`:41-42`); the V1_21_4 record hook also has `captureAxisSegments=false`. The V1_21_4 `NativeEntityBlockContactTraversal` is a no-op with `suppressPostTravel=false`. Thus the live current piston caller supplies only `previousPos -> position`; the required 1.21.4 `oldPosition() -> position()` segment is not supplied on this drain.

The `.4` DDA/contact hooks still process the explicit segment correctly, but that cannot recover the missing historical portion of the path.

## Minimal adapter needed

Add a narrowly scoped versioned operation for the **piston explicit-scan origin**, with an implementation at `V1_21_4` that substitutes `entity.oldPosition()` for the current `previousPos` argument at the current `PistonMovingBlockEntity.moveEntityByPiston` call to `Entity.applyEffectsFromBlocks(Vec3, Vec3)`. Preserve the supplied `position()` destination. Add native barrier providers at `V1_21` and `V1_21_2` so older profiles do not resolve forward to the `.4` adapter; profiles `V1_21_5+` should have no eligible adapter and keep the native current piston segment. Dispatch only for active local-player/client movement. The ordinary no-argument post-travel drain needs no extra synthesis.

This is a call-site adapter, not a movement replay loop. The existing explicit-scan depth and `.4` DDA/contact logic can remain the scan path once its origin is corrected.

## Provider and source evidence

Read-only annotation projection against the product worktree confirms at V1_21_4:

- `MovementCallbackRecordHook` → `MovementCallbackRecord@V1_21_4` (suppress=true; capture defaults false).
- `EntityBlockContactTraversalHook` → `NativeEntityBlockContactTraversal@V1_21_4` (no-op; suppressPostTravel=false).
- `BlockCallbackTraversalHook` → `BlockCallbackTraversal@V1_21_4`.
- `BlockCallbackContactHook` → `BlockCallbackContact@V1_21_4`.
- `BlockCallbackBudgetHook` → `BlockCallbacks@V1_21_5`; the DDA helper still retains its own legacy 17-clip-body cap.

Exact source SHA-256 identities:

- 1.21.4 Mojmap `Entity.java`: `05F18EF2EC0413FC010230407C812A11553EB5123B68D21B5D7B2C0C175698AD`.
- 1.21.4 Mojmap `LivingEntity.java`: `E62CE650AF0A5AC97E4D0A2BA7CAD4BA68A8D098525609F8B5B6976A8AB0AE30`.
- 1.21.4 Mojmap `PistonMovingBlockEntity.java`: `426442149F89A28FC5973A52B8156AEC7C18D76FAF8B62EECAE9F120B2FDBA16`.
- 26.2 unobfuscated `Entity.java`: `7AFB9C1294893FFE73E3B1ACFFCAD41C648F15DE8378BFF3DFFAFF869BB811D5`.
- 26.2 unobfuscated `PistonMovingBlockEntity.java`: `706BAB1A13BA99BBD334AAD853B99F72DF58FCE5B6B67D30D41F902B63DD8E06`.

Product source hashes at the reviewed commit:

- `EntityMixin.java`: `4D8049812E611B0701EECC835DB4238187476224DB96591B50519796643C55C6`.
- `EntityCallbackBatchMixin.java`: `51A79D77A1DAE2A0F2C2AA7E337AF217FF0E35F2E14A93B0A98A7615CB1B899A`.
- `EntityBlockCallbackSegmentMixin.java`: `4ADAAF119A7BD7ADC5D4E6E595DC013A377A3D8E443F8BF83CCF4443D4BABD83`.
- `MovementCallbackRecord.java` (`v1_21_4`): `D1A9369F736176688274FB4010EA0ECE81B6D6AFFCE8689D52CF5EF5363D4DCD`.
- `NativeEntityBlockContactTraversal.java` (`v1_21_4/entity`): `C8ECEC5133884CFBD9E35EF08CBA973BD8B216673F5F18B26D64BD76135A704D`.

This is a source and wiring review only. No build, tests, game launch, or runtime check was run.
