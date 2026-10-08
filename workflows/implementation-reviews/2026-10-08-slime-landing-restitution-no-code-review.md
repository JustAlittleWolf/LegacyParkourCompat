# Independent no-code coverage review — F-26.2-SLIME-LANDING-RESTITUTION

**Decision: ACCEPT — the bounded slime-restitution finding is already covered; no behavior change is needed.** This accepts implementation correspondence for the finding only. It does not claim runtime parity, pair completion, bed coverage, or modern attribute/producer coverage.

## Review identities and source binding

- Reconciliation commit: `4bb2bd611711a184f474017d90ce8e5ac857026d`, branch `fix/slime-landing-restitution-26-2`. `git diff-tree` confirms it adds only `workflows/fix-implementation/runs/26.1.2-slime-landing-restitution-reconciliation.md` (blob `d7431e9fd0ebe404bea8058347a8b1c343631932`); it contains documentation only.
- Accepted source finding: `8eb345d5973158e7e1bc26a2044d63fb77b45e19:workflows/source-campaign-2026-10-07/26.1.2--26.2/findings/F-26.2-SLIME-LANDING-RESTITUTION.md`; Git blob `7e1487f380b34b900408d7ad33be3c410a0b2bf9`; raw SHA-256 `c14302ffa5b0a773d1e27adbb7f8fbe12188066c80763bdac4a8a02ef0446a05`. `git rev-parse` independently returns the cited blob.
- Independent blind source review: `d6664c08d015b6d301b5498302d9f64f32ebec92:workflows/source-campaign-2026-10-07/independent-review-F-26.2-SLIME-LANDING-RESTITUTION-2026-10-08.md`; finding-only decision **ACCEPT**. The pair remains partial.
- Exact ready source roots are 26.1.2 and 26.2 `unobfuscated`. Source-manifest SHA-256 values are `54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0` and `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894`. Recomputed cited source hashes match: 26.1.2 `Entity.java` `8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf` and `SlimeBlock.java` `84d22cf526d6bf1b4ec4b0b642a76fc2c90380a71f05ab92a7de9935f0d1c38e`; 26.2 `Entity.java` `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5`, `Blocks.java` `f3f2faeed23e9697407069a1d523107491590b8710175523ea05294d5bd00435`, and `LivingEntity.java` `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a`.

## Bounded source witness

The accepted witness is a standing local player with no Slow Falling, walking off a 1/16-high carpet onto adjacent existing slime one carpet-height lower, with bounce suppression off. In 26.1.2, `Entity.move` resolves the post-collision support block and, after fall processing, calls that block’s `updateEntityMovementAfterFallOn` when Y movement was clipped (`Entity.java:767-790`). `SlimeBlock` reads `entity.getDeltaMovement()` and, for negative Y, writes `-movement.y * factor`; a `LivingEntity` uses factor `1.0` (`SlimeBlock.java:33-46`). For the witness, the current velocity is approximately `-0.0784`, and the nonzero downward clip is `-0.0625`; the callback reads the former, not the clipped vector, and writes positive Y.

In 26.2, `Entity.move` first resolves `effectPos` through `getOnPosLegacy()` and obtains `effectState` from that position (`Entity.java:775-778`); on the witness’s landed position, this is the slime block. `checkFallDamage` runs before generic restitution. The restitution call then runs for vertical clipping or horizontal collision (`Entity.java:784-786`). The generic downward restitution gate compares `-currentMovement.y` to effective gravity and chooses zero when it is smaller (`Entity.java:802-819`). With `0.0784 < 0.08`, the later Y formula multiplies by zero and clears Y even though the clipped movement is `-0.0625`. This confirms the witness’s native-versus-historical contrast without relying on a zero clipped distance or claiming a runtime trajectory.

## Existing implementation coverage

The current implementation reviewed is on main `61f278e268c3ed2bcc4d95e8f7bb6e9c543cfa82`; reconciliation base is `a5612be5107c9e4e3d604e43e7a481c5d868c56c`. The intervening main commit is source-readiness documentation and does not alter the movement paths below.

- `src/main/java/me/wolfii/legacyparkourcompat/mixin/EntityMixin.java:50-77` redirects the exact `Entity.restituteMovementAfterCollisions(BlockState, boolean, boolean, Vec3)` call from `Entity.move` (`require = 1`). The wrapper receives the resolved landing `effectState`, collided axes, clipped movement, and original requested movement. It resolves `BlockLandingBehavior` by the landing block and derives vertical clipping from requested Y versus clipped Y. If no historical restitution resolves, it invokes the original callback unchanged.
- `change/v26_1/MovementChanges.java:16-30` registers `CollisionRestitution` and a keyed `BlockLanding` for registered slime and bed block types. The slime landing is keyed by its registry ID (`BlockLandingBehavior.variant()` defaults to `blockId()`), so the witness’s `minecraft:slime_block` resolves to the slime implementation.
- `change/v26_1/CollisionRestitution.java:14-40` is registered at `V26_1`. It preserves current Y through horizontal-axis response, zeroes only clipped X/Z axes, and dispatches block landing only when Y clipped. Thus the landing callback still sees the negative pre-restitution Y velocity. The supplied vanilla fallback zeros Y for ordinary blocks and for suppressed bounce, matching the prior generic landing operation.
- `change/v26_1/BlockLanding.java:27-47` retains the suppress-bounce branch, default slime coefficient `1.0F`, and the negative-current-Y predicate. For slime it writes the negated current Y with the living-entity factor `1.0`, preserving X/Z. The separate `BlockBounceBehavior` registration is `BedBounce` keyed only to `minecraft:red_bed`; it does not override slime restitution.
- `MovementRuntime.find` returns no historical behavior for non-players or when emulation is disabled. `ChangeResolver` chooses the nearest registration at or after the selected version and explicitly returns no changes for `CURRENT`. `V26_1` covers 26.1, 26.1.1, and 26.1.2; the accepted source report confirms matching relevant 26.1/26.1.2 movement sources. For the historical profile, `CollisionRestitution` and keyed slime `BlockLanding` are selected; for `CURRENT` (26.2) the redirect runs its vanilla callback, retaining the native low-speed branch. Non-player movement likewise falls through to vanilla.

### Sneak and horizontal interaction limits

The witness is not shift-suppressing and has no horizontal clipping, so neither branch changes the result. `BlockLanding` checks `entity.isSuppressingBounce()` and calls the vanilla zero-Y path when true; the source slime callback likewise delegates to its superclass when bounce is suppressed. Horizontal collision axes are handled by the separate X/Z portion of `CollisionRestitution`; the slime landing callback preserves those components. This review does not accept any horizontal slime restitution delta. No separate slime-specific sneak or horizontal mechanic is introduced. Fall-damage handling remains vanilla and is outside the finding.

## Six review angles

1. **Requirement/scope — PASS.** The accepted vertical slime witness is covered; no bed or modern-only scope is inferred.
2. **Correctness/edge case — PASS.** Callback order, nonzero clipping, current negative Y, and the low-speed zero-restition branch were checked.
3. **Coverage — PASS for this finding.** Slime registration, player-only lookup, closest resolver selection, suppression, and `CURRENT`/non-player fallback are traced.
4. **Architecture — PASS.** The shared collision seam delegates to separate collision and block-landing mechanics; no duplicated movement loop was added.
5. **Permissions/user-visible behavior — PASS.** No access, configuration, protocol, or UI behavior changes.
6. **Security — PASS.** No new input, trust boundary, persistence, or external resource access.

No tests, builds, Gradle, client, server, TAS, Gym, Docker, or runtime checks were run. The separate bed finding, other restitution source slices, full-pair status, and modern attribute/producer questions remain open.

Reviewer: independent static no-code coverage review. Date: 2026-10-08.
