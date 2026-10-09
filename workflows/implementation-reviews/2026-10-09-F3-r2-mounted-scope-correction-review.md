# Independent implementation review: F-3 mounted-player scope correction

- **Decision:** ACCEPT the corrected code candidate identified below.
- **Review date:** 2026-10-09.
- **Scope:** independent review of the passenger exclusion added after the request-changes review. This acceptance is limited to the accepted standing, unmounted, non-flying Player Nether LAVA shallow-overlap witness and preservation of native behavior for mounted Players.
- **Source pair status:** partial. No first-changed-release boundary is claimed.
- **Runtime validation:** not performed.

## Immutable reviewed identities

- Corrected implementation commit: `b2eac92a9adf1d773aafc2d7013ea41fb977319f`.
- Parent: `682c7903a00302bbd934bd4da3e41d79f1751d54`.
- Changed path: `src/main/java/me/wolfii/legacyparkourcompat/mixin/EntityMixin.java`.
- Git blob: `6e15d7262855d95e7df722f72a61b72ebe1e9939`.
- Raw SHA-256: `0030874F368528DB2B3A52BB8B138AC077E332A3BCC0861604296D0D1F496C6B`.
- Prior request-changes review: commit `dafa594318874592f821da95393e0860a6ee4e0c`, path `workflows/implementation-reviews/2026-10-09-F3-r2-technical-review.md`, blob `3672e070dd382bd1ab0d1ac1ab37c874d6d283b1`, raw SHA-256 `E0872EEAB806B9EF1DB20813B274C32A0C9E6778E62A8D99F94B647CDFD030DA`.
- Accepted source finding: commit `b2b493a2521b036bca2fa590feeda3194c49bd4c`, path `workflows/source-campaign-2026-10-07/1.21.11--26.1.2/findings/F-3-nether-lava-shallow-overlap-r2-2026-10-09.md`, blob `73a244f66a6501b1d48f11fc4bcbde95d25c8518`, raw SHA-256 `69146a6130e72d7fffdf4bfa71954c41c62dc57aebfa0246f040e079c5f28907`.
- Independent source acceptance: commit `a76e28419f6b9767eab2e78147883c5459ed60c4`, path `workflows/source-boundary-reviews/2026-10-09-lava-shallow-overlap-r2-review.md`, blob `10d56f3e76eca339a95f6ea3f47ec099decb3467`. It accepts only the corrected standing, unmounted, non-flying Player witness.

The corrected commit adds one condition, `&& !player.isPassenger()`, to the existing LAVA-only redirect. The original candidate and request-changes review remain unchanged. The condition excludes mounted Players from the historical bypass, so they continue through the vanilla tracker cutoff. The accepted unmounted witness still reaches the existing strict `0 < lavaHeight < 0.4` behavior. The WATER redirect and shared tracker redirect are unchanged.

## Canonical target publication and direct verification

I inspected the exact ready root directly at:

`D:\Javastuff\LegacyParkourCompat\build\movement-campaign-2026-10-07\ready\26.2\unobfuscated`

The ready marker identifies version `26.2`, mapping `unobfuscated`, and ready status. Recomputed publication identities:

- `unobfuscated.ready.json`: `f9406adb6bf7cb4c1ab0792a798ab2cb90e082ad4c2eee032c9f674d0ea8070f`.
- `unobfuscated.sources.sha256`: `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894`.
- `artifacts.sha256`: `ba9dc53a41bca9744ec1c8ab2ce9363764a22333c39ef64d5105f4e3bf5c98ba`.

Each direct source file hash matched its source-manifest entry:

- `net/minecraft/world/entity/Entity.java`: `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5`.
- `net/minecraft/world/entity/EntityFluidInteraction.java`: `5264ff4f1fddebc3fa9d63ad2edbe2eaf617392ff946a867a6817a78b478ee62`.
- `net/minecraft/world/entity/player/Player.java`: `8decc71b9c780664578ddb14591db2a2f207c72c05b676edded6f8e964576531`.

The verified 26.2 source dispatches WATER at ordinal 0 and LAVA at ordinal 1 in `Entity.updateFluidInteraction()`. The LAVA scale is selected from `FAST_LAVA`. `EntityFluidInteraction.Tracker.applyCurrentTo()` first checks `currentCount != 0`, then returns when `accumulatedCurrent.lengthSqr() < 1.0E-5F`, before Player averaging, scale, minimum impulse, or velocity addition. `Entity.getFluidInteractionBox()` calls `vehicle.modifyPassengerFluidInteractionBox(box)` when mounted. `Player.isPushedByFluid()` returns `!abilities.flying`; the accepted witness is non-flying and unmounted.

## Applicability, dispatch, and fallback review

- **Requirement and edge cases:** The added passenger exclusion closes the unsupported mounted-player activation identified by the request-changes review. It preserves the accepted unmounted witness and leaves the current tracker untouched for mounted Players.
- **LAVA target and cutoff:** The redirect still targets the LAVA `applyCurrentTo` invocation at ordinal 1. It queries the exact measured LAVA height, while the nested tracker redirect changes only the cutoff comparison for the matching active entity.
- **Nested context:** `FluidCurrentCutoffContext.run` restores its prior ThreadLocal entity in `finally`, including nested calls. The correction does not change that helper.
- **Registration and resolver direction:** `ShallowLavaCurrentCutoffBehavior` remains registered once in `MovementChangeCatalog` at `V1_21_11`. `ChangeResolver` rejects CURRENT and selects the closest eligible registration at or after the selected profile. No profile gate or resolver refactor was added; `V26_1` and CURRENT do not resolve this historical hook.
- **Native and toggle fallback:** `MovementRuntime.find` returns empty when emulation is disabled/current or when the entity is not a Player. The redirect then calls `interaction.applyCurrentTo(...)` unchanged. Passenger Players also fall through to that native call.
- **Conventions, visibility, and security:** No unrelated hooks, access changes, or externally reachable security paths were introduced by the one-line correction.

## Limits

This review accepts the corrected implementation scope only. It does not establish the earliest changed release within `(1.21.11, 26.1.2]`, close other lava heights or vectors, resolve broader mounted fluid-box behavior, or establish runtime parity. No build, tests, client/server launch, TAS, Gym, or Docker activity was performed. Compilation and runtime validation remain separate statuses.
