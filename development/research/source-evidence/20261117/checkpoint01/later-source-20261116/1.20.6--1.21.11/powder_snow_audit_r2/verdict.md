# Independent source audit verdict: powder-snow transition supplement

**Verdict: ACCEPT**

## Immutable inputs

- Correction supplement `build/later-source-20261116/1.20.6--1.21.11/powder_snow/slice-r2.md`, SHA-256 `CA4A25818B94D87A1B6FE333D42C13F6BEF514A28E6E5D94D8F1C2B746BBFD37` (verified).
- Prior bounded note `build/later-source-20261116/1.20.6--1.21.11/powder_snow/slice.md`, SHA-256 `CC0304E51C90B526AEBEEAF20C913EB4B240A89F04093165151F099DECB7585A` (verified; unchanged).
- 1.20.6 Mojmap provenance JSON SHA-256 `EE53E67E7AB51069A0EAAE5A0CE13D49A9F972BDC13E3C31E041AFA77F4D26E8`; ready marker confirms `1.20.6` / `mojmap`, and source ledger SHA-256 `56AAE10684471D7ABB1C366BD5DD431AB976112A87E6CC5C687A68F1EFF06311` matches the ready marker.
- 1.21.11 Mojmap provenance JSON SHA-256 `99C9FB741EB5E4AC8FB5780E2158DA324D7C8B6AFC179D494E5986F78FE29F72`; ready marker confirms `1.21.11` / `mojmap`, and source ledger SHA-256 `0C4D83FEF84C101D9DB88F331ACEC7960F3C8346228C28995E6CF57BBFCAE555` matches the ready marker.
- The correction supplement’s relevant `LivingEntity`, `Entity`, `PowderSnowBlock`, and `InsideBlockEffectType` source digests match the exact files and entries in the corresponding Mojmap source ledgers.

## Review result

The supplement closes the specific reachability question accurately. In 1.20.6, the movement helper resolves `move` first and then reads the current `getInBlockState()`; a changed floored block position clears the cached state, so an entry into powder snow can be observed by that post-move predicate. In 1.21.11, the helper instead reads `wasInPowderSnow` during travel, before the tick’s collected inside-block effects are applied; that flag is the prior-tick latch. Under the shared `(horizontalCollision || jumping)` condition and a passing `canEntityWalkOnPowderSnow(this)` check, the supplement correctly states that entry can produce returned movement Y `0.2` in the old branch and not in the new branch, with the stale inverse response on exit.

The note describes the response as a movement-branch difference and explicitly disclaims full trajectory, pair coverage, intermediate cutoff, finding ownership, product review, and runtime parity. The reachable entry/exit witness is supported by the cited method order and cache invalidation. No correction is required for this bounded slice.

This acceptance applies only to the immutable r2 supplement and exact source identities above. It does not accept a complete pair inventory or establish runtime movement parity.
