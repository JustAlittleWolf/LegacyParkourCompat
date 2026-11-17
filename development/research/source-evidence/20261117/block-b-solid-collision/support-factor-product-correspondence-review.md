# Supporting-block position: static product correspondence review

## Verdict

The 1.19.4 to 1.20.1 supporting-position difference corresponds to the existing `SupportingBlockPositionHook` operation and its existing resolver registration. The product restores the pre-1.20 entity-column sample through the 1.19.4 profile, while profiles at 1.20 and later fall through to native support tracking. No additional hook or separately resolved product dependency is indicated by this source correspondence. This is a static product review only; world-wide behavior and runtime parity remain unknown.

## Exact source evidence

The evidence was read from the primary `decompiled_minecraft/` sources. SHA-256 values identify the exact decompiled Java files; manifest hashes identify their full source sets.

- **1.19.4 Mojmap** — `decompiled_minecraft/1.19.4/mojmap/net/minecraft/world/entity/Entity.java`, lines 744–762. `getBlockPosBelowThatAffectsMyMovement()` returns `BlockPos.containing(position.x, boundingBox.minY - 0.5000001, position.z)` (lines 760–762). `getBlockJumpFactor()` and `getBlockSpeedFactor()` call that position (lines 744–756). File SHA-256: `3667FEE610CBC5F58012E3A8FB8D6C4849F649FB7FE5300595158112D8B4B58B`. Source-manifest SHA-256: `6286e325371e085dfee1ab7987e66d4b6d1a984a49b6ec8ea730045cd24cecb3`.
- **1.20.1 Mojmap** — `decompiled_minecraft/1.20.1/mojmap/net/minecraft/world/entity/Entity.java`, lines 567–587 and 790–814. Grounded support tracking searches a thin AABB from `boundingBox.minY - 1.0E-6` to `minY`, retries against the prior horizontal movement when needed, and clears the stored support when not grounded (lines 567–587). `getBlockPosBelowThatAffectsMyMovement()` delegates to `getOnPos(0.500001F)` (lines 790–792). With a stored support, `getOnPos` retains its X/Z and normally floors Y from entity position minus the offset; walls and fence gates retain the stored position. With no stored support, it floors entity X/Y/Z (lines 798–814). File SHA-256: `94B9C3656715DE2D61FA9A02CCEF164261E50EB13D6090A6F07E58FE9C5B0759`. Source-manifest SHA-256: `858b56764113e591c60d09bc63e4d90f9c1d67a705645f0c793e8b28f2378465`.
- **Supporting-block selection in 1.20.1** — `decompiled_minecraft/1.20.1/mojmap/net/minecraft/world/level/CollisionGetter.java`, `findSupportingBlock` lines 95–109. It enumerates colliding block positions and selects the nearest to entity position, using `compareTo` as the tie-breaker. File SHA-256: `8E4863AFE40705497B36D0A58D8EE11E5103F4E72CB642AA63CC57F57649A130`.

The finding also cites Mojang release 1.20, but that exact release source was not independently inspected for this product correspondence verdict. The pair-specific evidence used here is 1.19.4 and 1.20.1; the patch introduction within that interval remains unknown.

## Existing product correspondence and profile reach

- `src/client/java/me/wolfii/legacyparkourcompat/mechanic/hook/entity/SupportingBlockPositionHook.java`, lines 9–12, defines the position operation.
- `src/client/java/me/wolfii/legacyparkourcompat/change/v1_15_2/GroundFrictionSamplePoint.java`, lines 41–53, is annotated `@MovementChange(emulates = ParkourVersion.V1_19_4)` and returns the 1.19.4 formula from entity X, bounding-box minY minus `0.5000001`, and entity Z. File SHA-256: `D21C16DBE3898CF94D340240DF91F0D5CAF520B20ABC683ABEC9C025C37E0425`.
- `src/main/java/me/wolfii/legacyparkourcompat/impl/ChangeResolver.java`, lines 23–36, resolves each hook independently. It admits changes whose `emulates` version is greater than or equal to the selected profile and chooses the nearest such version. Therefore the V1_19_4 implementation is active for V1_15 through V1_19_4; it is not active for V1_20 or later. For profiles earlier than V1_15, the V1_14_2 implementation wins when eligible.
- `src/client/java/me/wolfii/legacyparkourcompat/mixin/EntitySupportingBlockPositionMixin.java`, lines 15–24, wraps `getBlockPosBelowThatAffectsMyMovement()`: profiles older than V1_15 take native fallback here; otherwise the resolved supporting-position hook runs or falls back to native. `LivingEntityMixin.java`, lines 761–771, routes the older ground-friction sample directly through the same hook for profiles older than V1_15. Thus the V1_14_2 registration (`GroundFrictionSupportCellChange.java`, lines 30–39; sample Y is `boundingBox.minY - 1.0`) covers that earlier call path.
- `MovementRuntime.find` (`src/client/java/me/wolfii/legacyparkourcompat/mechanic/MovementRuntime.java`, lines 379–388) first checks entity applicability, then reads the hook from its active movement profile. `MovementControllerImpl.profileFor` delegates per-entity targets to profile construction (lines 124–131); `ChangeResolver` supplies the per-hook version selection. Native fallback remains in the wrapper when there is no applicable change.

This maps the described neighboring lowered-shape case to the existing operation: older profiles query the entity’s own X/Z column, while 1.20.1 native behavior may query the tracked neighboring support. The evidence does not establish how all blocks, shape variants, worlds, or trajectories behave.

## Product source identities

- `ChangeResolver.java`: `F9DD060F232BB3D34A58A55B792283A3AA01875B44E80FC454B9298075AF8587`
- `ActiveMovementProfile.java`: `80E3D33EFCD5C2473E87D5E5EC7FCE960D58A78C0BFA52083C13C8D373E3D07F`
- `MovementControllerImpl.java`: `0409675535DAA60F660441D2167833A44C027F2E276F2E45CE9567C98CA57B4E`
- `MovementRuntime.java`: `E52433CAFEB85BDBEA5033E9C73386E87DBC6EA8253057F1AE6780036C204A4D`
- `EntitySupportingBlockPositionMixin.java`: `A01451DB6D67813BD1ED3C842EA53B726329D937F879A4618C727A0A09EA1452`
- `LivingEntityMixin.java`: `1E818EAC358DC5F9C6B9B055AD13961C69B15FF792B3C95825D68C9CF4E792ED`

No product source, Git state, build, test, game runtime, or world was changed or exercised.