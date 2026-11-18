# Independent source audit: active Elytra climb exit, 1.21.5 → 1.21.6

## Verdict

**Substantive no-difference conclusion accepted for the bounded slice, with corrections.** The report's route selection, pre-travel cap predicate/assignment, client-versus-server cancellation gate, climb-branch order, flag writes, and local-player travel authority conclusion are supported by the exact sources. The description of actual travel is incomplete: `travelInAir` reaches `move` through `handleRelativeFrictionAndCalculateMovement` in both releases. On a climbable, that helper also runs `handleOnClimbable`, which resets fall distance before the movement call. These are not 1.21.5→1.21.6 differences, but they matter to the actual tick sequence and should be stated accurately.

## Frozen input and source identity

The audited input is the frozen `report.md`, SHA-256 `DD4C4F6D1905729AD9EEE81FCFE458ED3FFBDC21CFEF7B16A176BD4B481F4FBF`.

Both exact Mojmap source trees are locally present and read-only. For 1.21.5, `decompiled_minecraft/1.21.5/mojmap.ready.json` identifies status `ready`, `versionId=1.21.5`, mapping `mojmap`, and pins `mojmap.sources.sha256` to `365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9`; the current manifest hash matches. `mojmap.provenance.json` likewise names exact version 1.21.5 and Mojmap. Its artifact manifest contains client JAR SHA-256 `522672ad20b460c02c2e39b6c5035ef6a849af28eb11ab2ac7eb293d395a8c11` and client mappings SHA-256 `3907657ade3e61bc8cffb4ca0a1bcba15f57986be4e613900947119364a206e5`.

For 1.21.6, the source tree carries `.decompile-provenance.json` (SHA-256 `56b036c61094d1d936882bdf5b2b59044512189cb33adbb075880c727da9b19e`). Its exact pinned input URLs identify the `1.21.6.json` metadata (SHA-256 `ea1c91ccb9760c9f7e697c29ed679427dd20cdac9ce35937125e5690973df6dd`), client JAR (`9592df8587d6d3ec0334c873ad432dc76006d6129afdce9a7eac52638339a24a`), and official client mappings (`2ff82b34ce5f849aaafdff51e23b9448c4263881238b780a441fe1061a6fc261`). The provenance source entries match the four actual files below. This is provenance-backed source evidence; the 1.21.6 tree does not use the visible 1.21.5 ready-marker format.

| Source file | 1.21.5 SHA-256 | 1.21.6 SHA-256 |
|---|---|---|
| `net/minecraft/world/entity/Entity.java` | `fc177733e9cc4b2d5cf2562e5529d0f4e0b9c3690f081fbd58aa9119eb173dc4` | `c403e6176d27b5bfd6aaa0dea3735ffa4fcf80dbae58766661dd84453b7e9704` |
| `net/minecraft/world/entity/LivingEntity.java` | `a8aed863d4fdc515c751dd2878a8bb9c13179228cb8dbb50edf1d19cd5404271` | `609f0197a0b4551ab42279e452c11cdd256135b1d467c2a95950b0e9fd7ddef8` |
| `net/minecraft/world/entity/player/Player.java` | `8fc187f33999db9dfc49251e95e92a17645a50adab16ca0f93f4948209f62036` | `8dc5514fe44311f39268692f5188840b9f65cb71143228fa8b84242585ea7987` |
| `net/minecraft/client/player/LocalPlayer.java` | `f1fcfed4a938732361e7ad951f93e9b73b02320ee56f0b219e2b3c7acdbfa2ef` | `53f2a71a886b9c71853afe36f2df857f80a9bf1cd6ec4ca8604ccd7e238d89ee` |

## Independent trace and correction

1. **Pre-travel update.** `LivingEntity` checks `isFallFlying()` and calls `updateFallFlying()` before the travel call: 1.21.5 `LivingEntity.java:2753–2767`; 1.21.6 `:2805–2819`. `Entity.checkSlowFallDistance()` at 1.21.5 `Entity.java:2506–2510` and renamed `checkFallDistanceAccumulation()` at 1.21.6 `:2674–2678` both use `deltaMovement.y() > -0.5 && fallDistance > 1.0`, then assign `fallDistance = 1.0`. `updateFallFlying()` invokes that cap before its `if (!level().isClientSide)` branch; the `canGlide()` cancellation and `setSharedFlag(7, false)` are server-only in both versions (old `LivingEntity.java:2815–2821`, new `:2871–2877`). The report is right that this updater does not cancel the active client glide.

2. **Reachable local-player route.** With the stated ordinary on-foot, non-swimming, non-flying, non-passenger preconditions, `Player.travel` delegates to `super.travel`: 1.21.5 `Player.java:1426–1448`; 1.21.6 `:1438–1460`. `LivingEntity.travel` gives fluid travel precedence, then selects `travelFallFlying` when the glide flag is still active: old `LivingEntity.java:2213–2221`, new `:2245–2254`. The stated dry precondition therefore reaches the glide branch.

3. **Climb exit and actual movement.** `travelFallFlying` tests `onClimbable()`, calls `travelInAir(input)`, then calls `stopFallFlying()`: old `LivingEntity.java:2301–2320`, new `:2353–2372`. The branch skips the *glide-specific* `updateFallFlyingMovement` and its direct `move` at old `:2306–2313` / new `:2358–2365`. It does **not** skip movement: `travelInAir` calls `handleRelativeFrictionAndCalculateMovement` (old `LivingEntity.java:2224–2228`, new `:2276–2280`), and that helper calls `move(MoverType.SELF, getDeltaMovement())` (old `:2394–2404`, new `:2446–2456`). Because this path is on a climbable, the helper first calls `handleOnClimbable`; that method resets fall distance and applies its climb velocity handling (old `LivingEntity.java:2421–2431`, new `:2473–2487`). Thus, if the pre-travel cap assigned 1.0, the climbable air-movement step resets fall distance before moving. After `travelInAir` completes, `stopFallFlying` writes shared flag 7 true and then false, in that order, on both releases.

4. **Authority gate.** 1.21.6 adds `&& isEffectiveAi()` to the non-ridden travel gate (`LivingEntity.java:2815–2819`) where 1.21.5 checks `canSimulateMovement()` alone (`:2763–2767`). `Player` implements both predicates identically on both sides as `!level().isClientSide || isLocalPlayer()` (old `Player.java:1321–1328`; new `:1333–1340`); `LocalPlayer.isLocalPlayer()` returns true (old `LocalPlayer.java:348–350`; new `:342–344`). The additional conjunct remains true for this ordinary local player and does not change reachability.

## Scope boundary

This verdict covers only the requested already-active glide, dry, ordinary local-player climb-exit route and the adjacent pre-travel cap and authority gates. It does not audit climbable block-state production, A/B collision geometry/contact, glide activation, the final glide tick, custom equipment variants, or runtime trajectory parity. No game, test, build, generation, Git, wiki, or network action was performed.
