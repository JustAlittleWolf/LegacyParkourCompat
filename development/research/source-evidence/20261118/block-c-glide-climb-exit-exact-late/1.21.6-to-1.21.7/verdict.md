# Independent source audit: Elytra climb exit, 1.21.6–1.21.7

## Bound input and verdict

Audited the exact bytes of `c-glide-1216-1217-actual-climb-exit/report.md`; SHA-256 from `Get-FileHash -Algorithm SHA256` on the requested report path: `FDA2FA518B4D52D43B54E93269B3DE864824E2C18CE3A75D26FB40242CCEF43D`.

**Verdict: no source difference in the bounded already-active, dry, on-foot chest-Elytra climb-exit tick.** The compared source claims are supported. One phrase in the report needs precision: the climbable branch performs the `travelInAir` velocity update before the stop writes, but that `travelInAir` method contains no position `move` call. Thus the source proves velocity-update-before-stop order, not a first position displacement before the stop. The same source bytes on both sides preserve this exact order.

## Side identities

- **A — 1.21.6, Mojmap:** `decompiled_minecraft/1.21.6/mojmap/`. `.decompile-provenance.json` SHA-256 `56b036c61094d1d936882bdf5b2b59044512189cb33adbb075880c727da9b19e`; input client JAR SHA-256 `9592df8587d6d3ec0334c873ad432dc76006d6129afdce9a7eac52638339a24a`; Mojang `client.txt` SHA-256 `2ff82b34ce5f849aaafdff51e23b9448c4263881238b780a441fe1061a6fc261`.
- **B — 1.21.7, Mojmap:** `decompiled_minecraft/1.21.7/mojmap/`. `.decompile-provenance.json` SHA-256 `45bf296d921b2239fd0aedb2684e2c1cb9b7d7532b4106b8a2fd9b09309ca82d`; input client JAR SHA-256 `6d0aa64c6a0aee3270253317f51e9e816f94cd3b16b44f5d605615c39fc62fb9`; Mojang `client.txt` SHA-256 `328f2c5849c7c5fc4f2b1eae4f74a7cfe6bb337c7938242ac5c4f9eac89b1e50`.

For both sides, I recomputed hashes for `LivingEntity.java` (`609f0197a0b4551ab42279e452c11cdd256135b1d467c2a95950b0e9fd7ddef8`), `Entity.java` (`c403e6176d27b5bfd6aaa0dea3735ffa4fcf80dbae58766661dd84453b7e9704`), `Player.java` (`8dc5514fe44311f39268692f5188840b9f65cb71143228fa8b84242585ea7987`), and `LocalPlayer.java` (`53f2a71a886b9c71853afe36f2df857f80a9bf1cd6ec4ca8604ccd7e238d89ee`). Each equals its side’s provenance `sources` entry; A and B each match byte-for-byte for these files. The checked target method bodies therefore have the same mapping family, line locations, and contents.

## Bounded path and source checks

The start bound is an already-active local player (`isFallFlying()` true) with the ordinary chest Elytra, on foot, dry, and at a ladder/vine position for which `onClimbable()` is true when `travelFallFlying` is entered. The final bound is the end of that same travel call after `stopFallFlying()`; entry into flight, later ticks, and other Elytra routes are outside this verdict.

- `LocalPlayer.aiStep` calls `super.aiStep` (A/B `LocalPlayer.java:830`); `Player.aiStep` calls `super.aiStep` (A/B `Player.java:576`). `LivingEntity.aiStep` calls `updateFallFlying()` when already fall-flying before its travel dispatch (A/B `LivingEntity.java:2805–2807`), then invokes the ordinary player travel route when `canSimulateMovement() && isEffectiveAi()` (A/B `LivingEntity.java:2815–2818`). The local-authority gate at `2821` guards block effects after travel, not this travel dispatch.
- `updateFallFlying()` first invokes `checkFallDistanceAccumulation()` (A/B `LivingEntity.java:2871–2873`). `Entity.java:2674–2677` caps `fallDistance` to `1.0` only if vertical delta is greater than `-0.5` and `fallDistance > 1.0`. The subsequent failed-`canGlide()` cancellation is inside `if (!level().isClientSide)` (A/B `LivingEntity.java:2873–2877`), so it does not cancel an already-active local client glide here. The capped fall-distance write itself precedes that side gate.
- Dry `LivingEntity.travel` selects `travelFallFlying` for the active glide (A/B `LivingEntity.java:2245–2253`). The climbable branch calls `travelInAir($$0)` and then `stopFallFlying()` (A/B `LivingEntity.java:2353–2356`). In this branch, `travelInAir` computes air friction/gravity and writes delta movement (`2276–2299`); it does not invoke `move`. Consequently the accurately bounded ordering is air travel velocity write, then stop writes, rather than a demonstrated positional move before stop.
- `stopFallFlying()` writes shared flag 7 true and then false (A/B `LivingEntity.java:2369–2372`). The ordinary-feet glide branch instead updates fall-flying velocity and calls `move` at `2360–2361`, without this climb-exit stop. The branch contrast and all orderings are identical on A and B.
- `Player.travel` for the ordinary non-passenger, non-abilities-flying player delegates to `super.travel` (A/B `Player.java:1438–1459`), closing the player override dependency for this bound.

This verdict accepts only the bounded no-difference claim for the selected in-tick route. It does not audit flight start/finalization, applicability of the climbable predicate, ladder/vine shape behavior, custom Elytra equipment, or intermediate releases.