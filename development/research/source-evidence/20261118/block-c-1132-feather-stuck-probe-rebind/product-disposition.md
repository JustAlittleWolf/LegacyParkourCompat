# C PushAway velocity: actual-product disposition

## Identity

- Isolated checkout: `C:/Users/Wolfi/.codex/visualizations/2026/11/18/01a1f01a-5064-75c0-a594-b2e7fa3b99e2/domain-c`
- Checkout HEAD / immutable source rebind: `1e61919c8b39348a384dfa4ab2707e1cc197b18d`; clean before this report.
- Product tested commit supplied for this verification: `27591b8550873071878abf41d362f17f8e7fced0` (commit subject: “Apply historical lava glide cap timing through earlier profiles”). The inspected checkout is the separate source-rebind commit above; this report does not claim that its packaged JAR was built from the tested commit.
- Exact frozen source receipt: `C:/Users/Wolfi/.codex/visualizations/2026/11/18/01a1f01a-5064-75c0-a594-b2e7fa3b99e2/c-feather-1132-rebind/receipts/frozen-stuck-probe/sources.sha256`. Frozen 1.13.2 `LocalClientPlayerEntity.java` SHA-256: `2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29b29d8d9bf42bf`; frozen 1.14.4 counterpart SHA-256: `708af6a3880fb58b67bf4604a5509b351719a9c2a0c06a8ec261586435bf00ce`; frozen 26.2 `LocalPlayer.java` SHA-256: `8d089aa09217e3607b38590f7c1623385562800943ac6dfd3d17804e041da6d6`.
- Product readiness finding: `development/research/findings/1.13.2--1.14/local-block-escape-velocity-precision.md`; acceptance is bounded to this precision operation and does not assert runtime parity.

## Source and implementation match

The frozen 1.13.2 Feather source (`LocalClientPlayerEntity.pushAwayFrom`, lines 401–449) compares the four open-side distances and, after a side is selected, assigns only `velocityX` or only `velocityZ` using `-0.1F` / `0.1F` (lines 432–448). Assignment to the double velocity field widens the float value (`0.10000000149011612` magnitude); the transverse horizontal component and Y remain untouched. If no direction is selected, no velocity assignment occurs. The adjacent frozen 1.14.4 source (`pushAwayFrom`, lines 389–432) instead passes double literals `-0.1` / `0.1` to the velocity setter while copying the other two components. This confirms the 1.14 precision boundary.

Existing `src/client/java/me/wolfii/legacyparkourcompat/change/v1_9/PushAwayVelocity.java` is annotated `@MovementChange(emulates = ParkourVersion.V1_13_1)`. It creates `float g = 0.1F` and returns signed `g`, reproducing widening for the chosen component. The separate `PushAwayVelocityHook` is one independently resolved operation. The existing `LocalPlayerMixin` dispatches through `MovementRuntime.find(PushAwayVelocityHook.class, player)` and preserves `vanilla` when no change resolves. Its two `@ModifyArg`s target `LocalPlayer.moveTowardsClosestSpace(DD)V` and `LocalPlayer.setDeltaMovement(DDD)V`: ordinal 0/index 0 for the X argument, ordinal 1/index 2 for Z. The frozen 26.2 source at `LocalPlayer.moveTowardsClosestSpace`, lines 451–477, confirms two setter call sites: the X branch supplies the old Y/Z components; the Z branch supplies old X/Y. Thus the hook substitutes only the selected axis value and keeps transverse/Y values unchanged. The 1.13.2 selected direction and sign are already chosen by the native method; this hook does not alter occupancy, range, or side selection.

`ChangeResolver` filters for `emulates >= selected` and picks the earliest eligible provider for that hook. The `V1_13_1` annotation therefore reaches 1.13.1/1.13.2 profiles, and does not reach 1.14 or later. Discovery scans annotated classes under the version packages and registers implemented `@MechanicType` hooks; there is no manual provider list for this operation.

## Native descriptor, packaging, and client boundary

The mixin source and packaged `LocalPlayerMixin.class` contain the exact targets `moveTowardsClosestSpace(DD)V` and `setDeltaMovement(DDD)V`; `javap -v` on the available packaged class also shows the `PushAwayVelocityHook.pushAwayVelocity(Player, ParkourVersion, double): double` call. The available JAR `build/libs/LegacyParkourCompat+26.2-1.0.0.jar` contains `PushAwayVelocity.class`, its hook, and `legacyparkourcompat.client.mixins.json`. Its class annotation was inspected with `javap -v` and records `MovementChange.emulates = ParkourVersion.V1_13_1`. Reading the packaged client mixin config confirmed `LocalPlayerMixin` is listed. JAR timestamp: 2026-11-18 23:21:48 +01:00; it predates the supplied tested commit timestamp (23:23:16 +01:00), so it verifies packaging shape but is not attributed to that commit.

`LocalPlayerMixin` is in the `src/client` source set and the client-only mixin config; `fabric.mod.json` scopes that config to `environment: client`. `MovementChangeDiscovery.register` is called from `LegacyParkourCompatClient.onInitializeClient`. This correction adds no server-side movement or server hook effect.

## Disposition

**COMPLETED — existing code covers the accepted bounded operation. No product gap found.** Old float-literal widening, signed direction, one-axis-only write, transverse/Y preservation, resolver reach, native fallback, target descriptors, JAR class annotation, packaged client mixin membership, and client-only scope were verified. Occupancy/range, raw identity, broader source coverage, and runtime parity were not assessed. No source generation, product changes, tests, runtime launch, build, or push was performed.
