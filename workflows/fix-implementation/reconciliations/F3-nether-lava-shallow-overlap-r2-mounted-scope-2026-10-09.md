# F-3 R2 mounted-player applicability correction

**Status:** Corrected code candidate and post-merge integration independently accepted; ready for serial integration/build ownership. Runtime parity remains unverified.

## Immutable source and code inputs

- Accepted source finding: commit `b2b493a2521b036bca2fa590feeda3194c49bd4c`, path `workflows/source-campaign-2026-10-07/1.21.11--26.1.2/findings/F-3-nether-lava-shallow-overlap-r2-2026-10-09.md`, blob `73a244f66a6501b1d48f11fc4bcbde95d25c8518`, raw SHA-256 `69146a6130e72d7fffdf4bfa71954c41c62dc57aebfa0246f040e079c5f28907`.
- Independent source acceptance: commit `a76e28419f6b9767eab2e78147883c5459ed60c4`, path `workflows/source-boundary-reviews/2026-10-09-lava-shallow-overlap-r2-review.md`, blob `10d56f3e76eca339a95f6ea3f47ec099decb3467`, raw SHA-256 `C589664E82974CC07B7010A9C4BE9C42EF4E334221D1D73B11FE1E4261F7352E`. It accepts the corrected standing, unmounted, non-flying Player Nether LAVA witness only. The pair remains partial and the first changed release remains unknown within `(1.21.11, 26.1.2]`.
- Preserved original code candidate: commit `a5e43adb6251a867f4ac2b9f05ecdf73ca65564f`. Its original reconciliation is commit `93b4839c4a9d0234a436c1f2072de6052ae5e4a9`. The independent technical review at commit `dafa594318874592f821da95393e0860a6ee4e0c`, file `workflows/implementation-reviews/2026-10-09-F3-r2-technical-review.md`, blob `3672e070dd382bd1ab0d1ac1ab37c874d6d283b1`, raw SHA-256 `E0872EEAB806B9EF1DB20813B274C32A0C9E6778E62A8D99F94B647CDFD030DA`, requests the mounted-player correction. The old candidate and review remain unchanged in history.
- Corrected code commit: `b2eac92a9adf1d773aafc2d7013ea41fb977319f`. The corrected `EntityMixin.java` blob is `6e15d7262855d95e7df722f72a61b72ebe1e9939`, raw SHA-256 `0030874F368528DB2B3A52BB8B138AC077E332A3BCC0861604296D0D1F496C6B`.

## Current operation and correction

The build target is Minecraft 26.2 (`gradle.properties`), using the canonical read-only source root `D:/Javastuff/LegacyParkourCompat/build/movement-campaign-2026-10-07/ready/26.2/unobfuscated`. Its ready marker SHA-256 is `f9406adb6bf7cb4c1ab0792a798ab2cb90e082ad4c2eee032c9f674d0ea8070f`, source-manifest SHA-256 is `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894`, and artifact-manifest SHA-256 is `ba9dc53a41bca9744ec1c8ab2ce9363764a22333c39ef64d5105f4e3bf5c98ba`. Direct source-file hashes match the manifest: `Entity.java` `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5`, `EntityFluidInteraction.java` `5264ff4f1fddebc3fa9d63ad2edbe2eaf617392ff946a867a6817a78b478ee62`, and `Player.java` `8decc71b9c780664578ddb14591db2a2f207c72c05b676edded6f8e964576531`.

In 26.2, `Entity.updateFluidInteraction()` dispatches WATER then LAVA to the existing tracker. The LAVA call is the second `applyCurrentTo` invocation, and its tracker returns on `currentCount == 0` or when `accumulatedCurrent.lengthSqr() < 1.0E-5F`, before Player averaging and velocity application. `Entity.getFluidInteractionBox()` passes a passenger's box through `vehicle.modifyPassengerFluidInteractionBox(...)`; a mounted Player can therefore reach the same LAVA cutoff through a vehicle-adjusted box. The accepted F-3 witness covers an unmounted Player, and the technical review correctly left mounted boxes unresolved.

The correction adds `!player.isPassenger()` to the existing LAVA-only mixin dispatch before resolving `ShallowLavaCurrentCutoffBehavior`. A mounted Player now invokes `applyCurrentTo` unchanged, retaining the native weak-current cutoff for this unreviewed case. The accepted unmounted witness still reaches the same `0 < lavaHeight < 0.4` hook and scoped tracker bypass. The WATER path and shared tracker redirect are unchanged.

No profile check was added. `MovementRuntime.find` still uses the ordinary resolver: the `V1_21_11` old-side registration applies to selected versions through that group; `V26_1` excludes it, and `CURRENT` resolves no historical change. The first changed release inside the accepted endpoint interval remains unclaimed.

## Verification and remaining status

- `git diff --check` passed for the one-line correction.
- The corrected candidate was independently accepted at commit `f89440c372c9f795e75d7c6e0e1bfef9253001a3`, file `workflows/implementation-reviews/2026-10-09-F3-r2-mounted-scope-correction-review.md`, blob `520357aef12404e06a4f0704139e09678778c2ed`, raw SHA-256 `74BF4EC60210E6E3CAC5C8081943C0FA8B5C23A0DFE809455E8322C436FC3FF4`.
- Default branch `main` at `3467cc398a89bb86bfafa6eedb315e0a29e040de` was merged at `5c27fe49d651898f108b34de223c132ae1dd3fb2`. The merge retained the F-3 hook and passenger guard alongside F-005's distinct `Entity.move` reset hook; no F3/F005 semantic conflict was found.
- The merged tree was independently accepted at commit `f467c6df7101f26b542d941bd2fc05536520fc69`, file `workflows/implementation-reviews/2026-10-09-F3-r2-mounted-scope-integration-review.md`, blob `fda61ce1f6a36d24c1ecbce8489e46ff0e93f102`, raw SHA-256 `06765E7BD3EE0C5B904EEC45E3742E2663EA3EEC9BCDD2F824410E17EF901949`. It reviewed merged HEAD `5c27fe49d651898f108b34de223c132ae1dd3fb2` and the listed code blobs/raw hashes.
- `git diff --check` passed after the main merge. The final integration review confirms the water and lava paths share the scoped context, F005's redirect targets a separate method, and resolver/native fallbacks are unchanged.
- No tests, build, runtime, client, TAS, Gym, server, or Docker activity was performed.
- No implementation result was sent to source-only owners. The 1.21.11–26.1.2 source pair remains partial.
- Runtime validation of the accepted unmounted witness and broader mounted-fluid-box behavior remain open for separately authorized work.
