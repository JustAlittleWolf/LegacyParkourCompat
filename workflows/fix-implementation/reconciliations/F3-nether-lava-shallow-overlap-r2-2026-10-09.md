# F-3 R2 shallow Nether lava current reconciliation

**Status:** Implemented for the accepted bounded F-3 R2 witness; candidate awaits independent technical review and serial integration/build.

## Accepted source evidence

The corrected source snapshot is immutable at commit `b2b493a2521b036bca2fa590feeda3194c49bd4c`, path `workflows/source-campaign-2026-10-07/1.21.11--26.1.2/findings/F-3-nether-lava-shallow-overlap-r2-2026-10-09.md`, Git blob `73a244f66a6501b1d48f11fc4bcbde95d25c8518`, raw SHA-256 `69146a6130e72d7fffdf4bfa71954c41c62dc57aebfa0246f040e079c5f28907`. The independent source review is commit `a76e28419f6b9767eab2e78147883c5459ed60c4`, path `workflows/source-boundary-reviews/2026-10-09-lava-shallow-overlap-r2-review.md`, Git blob `10d56f3e76eca339a95f6ea3f47ec099decb3467`; it accepts those exact corrected bytes. The pair report's correction-binding event is commit `bd244527013e8eb7a5ae912c4fe6ce42aba5dbcc`. The prior original-snapshot `REQUEST CHANGES` review remains preserved and does not apply to the corrected R2 bytes.

The acceptance is limited to the corrected, standing, unmounted, non-flying Player Nether LAVA shallow-overlap witness. A (1.21.11) scales the one-cell overlap by `0.007`, then its weak-current branch raises the resulting `1.4023730099168574E-5` vector to the minimum impulse. B (26.1.2) accumulates `(-0.0030000000000001137, 0, 0)` and returns because its squared magnitude `9.000000000000683E-6` is below the strict `1.0E-5F` cutoff. The corrected coordinates use the same double-precision player position on both sides while retaining A's float-rounded fluid top.

The exact source-ready identities recorded in the accepted review are:

| Release | Mapping | Ready marker SHA-256 | Source manifest SHA-256 | Artifact manifest SHA-256 | Relevant source SHA-256 |
|---|---|---|---|---|---|
| A: 1.21.11 | Mojmap | `0ad98d0ebd654650492c97eb58bc324d33864787f0ce10d99faec3ae2f5b804b` | `0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555` | `c8210b15012dcc4e109c2f73015b4117abec1f8ab04fdd375dbc11419e9e246c` | `Entity.java` `32314478c6036fa9f3f3cc409c61c622eefc5a282d60e1011a1a33cc29cf18a3`; `Player.java` `8e97167350a91741d0aa10d3b0d92a33150ed6dccdc94cd5b37d9c7ca22bcc81` |
| B: 26.1.2 | Unobfuscated | `f24af0f4d38543c2bd9c19b50f347d4e89d6027050e4b21ae070734843946bb7` | `54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0` | `89f648229de83b0109460695c9f1f3bed7ef36efb84ede9224e80cce87118f92` | `Entity.java` `8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf`; `EntityFluidInteraction.java` `5264ff4f1fddebc3fa9d63ad2edbe2eaf617392ff946a867a6817a78b478ee62` |

The exact ready roots and source/client artifact identities are verified in the accepted review. The source report and review both leave the pair partial; the finding does not establish the first changed release within `(1.21.11, 26.1.2]`, close the broader S4.6 inventory, or claim runtime parity.

## Current target operation

The build target is Minecraft 26.2 (`gradle.properties`), unobfuscated official-name source. The canonical read-only target source root is `ready/26.2/unobfuscated`. Its ready marker SHA-256 is `f9406adb6bf7cb4c1ab0792a798ab2cb90e082ad4c2eee032c9f674d0ea8070f`, source manifest SHA-256 `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894`, and artifact manifest SHA-256 `ba9dc53a41bca9744ec1c8ab2ce9363764a22333c39ef64d5105f4e3bf5c98ba`. The current target `Entity.java` hash is `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5`; `EntityFluidInteraction.java` is `5264ff4f1fddebc3fa9d63ad2edbe2eaf617392ff946a867a6817a78b478ee62` and matches B's cited tracker source.

In 26.2, `Entity.updateFluidInteraction()Z` calls `EntityFluidInteraction.applyCurrentTo` first for WATER (ordinal 0), then LAVA (ordinal 1). The lava call uses the FAST_LAVA dependent scale. `EntityFluidInteraction.Tracker.applyCurrentTo(Entity,double)` first checks `currentCount != 0` and returns for `accumulatedCurrent.lengthSqr() < 1.0E-5F`; averaging, scale, Player minimum impulse and `addDeltaMovement` follow that guard. This target operation and call order support a narrow redirect at the existing LAVA call, without replacing the tracker loop.

## Existing coverage and disposition

Existing coverage is related but does not include this lava cutoff case:

- `ShallowWaterCurrentCutoffBehavior` is registered at V1_21_11 and only wraps the WATER call (ordinal 0).
- `LavaCurrentBehavior` at V1_15_2 disables lava current for its applicable older profiles. It is a separate all-lava gate and remains unchanged.
- `FluidCurrentMinimumBehavior` is also registered at V1_15_2 and does not provide the missing shallow LAVA cutoff bypass at V1_21_11.
- `EntityFluidCurrentMixin` already exposes the tracker cutoff through one `lengthSqr()` redirect. The scoped context makes that cutoff bypass available only during the specifically resolved shallow-fluid call.

The missing operation is the shallow LAVA call's bypass of the accumulated-current cutoff. The new `ShallowLavaCurrentCutoffBehavior` and `change.v1_21_11.ShallowLavaCurrentCutoff` select a positive LAVA height strictly below `0.4`, matching the bounded A witness's shallow-overlap branch. `EntityMixin` intercepts only the 26.2 LAVA invocation at ordinal 1, requires the LAVA tag and a Player, resolves the hook through `MovementRuntime`, and invokes the existing target operation inside the scoped cutoff context. If the hook is absent or the height is outside the accepted shallow range, it calls `applyCurrentTo` unchanged. The context restores its previous thread-local value in `finally`.

The `ParkourVersion` groups 1.21.11 at `V1_21_11` and 26.1.2 at `V26_1`. `ChangeResolver` selects the closest registration whose emulated version is the selected version or later, and resolves no historical changes for `CURRENT`. Registering this old-side delta at `V1_21_11` therefore makes it available to profiles through V1_21_11; selecting V26_1 excludes it, and CURRENT leaves the 26.2 native cutoff in place. This represents the exact A-to-B endpoint evidence without claiming an unreviewed first changed release inside the interval.

Native and toggle fallback is preserved: `MovementRuntime.find` returns no behavior for `CURRENT`, disabled emulation, or non-player entities; all those paths execute the vanilla lava call. WATER remains on its existing hook and branch. No lava state, block state, general fluid loop, or other fluid-current operation was changed.

## Implementation and handoff

- Code candidate commit: `a5e43adb6251a867f4ac2b9f05ecdf73ca65564f` on `fix/f3-nether-lava-shallow-overlap-r2-2026-10-09`, based on main `cb11257e593987c68be98a7dbda8bbea7d5e77fd`.
- Files: `src/main/java/me/wolfii/legacyparkourcompat/mechanic/hook/ShallowLavaCurrentCutoffBehavior.java`; `src/main/java/me/wolfii/legacyparkourcompat/change/v1_21_11/ShallowLavaCurrentCutoff.java`; `src/main/java/me/wolfii/legacyparkourcompat/change/MovementChangeCatalog.java`; `src/main/java/me/wolfii/legacyparkourcompat/mixin/EntityMixin.java`; `src/main/java/me/wolfii/legacyparkourcompat/mixin/EntityFluidCurrentMixin.java`; and the shared neutral context rename to `FluidCurrentCutoffContext.java`.
- Integration conflicts: none observed against main at branch creation; this candidate uses the existing fluid tracker redirect and adds one target-call redirect for LAVA.
- Static checks: `git diff --check` passed; the hook key is unique; the static catalog registration and version direction were inspected against `ChangeResolver`, `ParkourVersion`, and the exact 26.2 call order.
- Build/tests/runtime: not run. No tests, clients, TAS, Gym, server, or Docker were launched.
- Remaining: independent technical review of the exact code candidate, then serial integration and the campaign build owner’s required build with all Gradle Test tasks disabled plus `-x test`. Runtime validation remains separate and unauthorized here.
- Feedback isolation: no implementation findings were sent to source-only owners; the pair remains partial.
