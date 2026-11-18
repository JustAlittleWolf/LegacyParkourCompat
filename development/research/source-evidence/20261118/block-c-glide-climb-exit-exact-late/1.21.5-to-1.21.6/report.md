# 1.21.5 → 1.21.6: active Elytra climb exit (bounded source slice)

## Source identity and integrity

Compared the adjacent exact releases under Mojmap in the primary checkout's `decompiled_minecraft/` tree, read-only:

- **1.21.5**: `decompiled_minecraft/1.21.5/mojmap/`; exact metadata says `versionId=1.21.5`, `namespace=mojmap` in `decompiled_minecraft/1.21.5/mojmap.provenance.json`. Its ready marker binds the source manifest SHA-256 to `365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9`, matching the actual `mojmap.sources.sha256` hash. Artifact manifest entries include client JAR `522672ad20b460c02c2e39b6c5035ef6a849af28eb11ab2ac7eb293d395a8c11` and Mojmap client mappings `3907657ade3e61bc8cffb4ca0a1bcba15f57986be4e613900947119364a206e5`.
- **1.21.6**: adjacent `decompiled_minecraft/1.21.6/mojmap/`, using the hidden `.decompile-provenance.json`. Its pinned inputs include the exact `1.21.6.json` metadata (`ea1c91ccb9760c9f7e697c29ed679427dd20cdac9ce35937125e5690973df6dd`), client JAR (`9592df8587d6d3ec0334c873ad432dc76006d6129afdce9a7eac52638339a24a`) and official client mappings (`2ff82b34ce5f849aaafdff51e23b9448c4263881238b780a441fe1061a6fc261`). The manifest’s source hashes match the actual files listed below. Hidden provenance file SHA-256: `56b036c61094d1d936882bdf5b2b59044512189cb33adbb075880c727da9b19e`.

Relevant source SHA-256 (both sides verified against the old source manifest or new hidden provenance source entries):

| File | 1.21.5 | 1.21.6 |
|---|---|---|
| `net/minecraft/world/entity/Entity.java` | `fc177733e9cc4b2d5cf2562e5529d0f4e0b9c3690f081fbd58aa9119eb173dc4` | `c403e6176d27b5bfd6aaa0dea3735ffa4fcf80dbae58766661dd84453b7e9704` |
| `net/minecraft/world/entity/LivingEntity.java` | `a8aed863d4fdc515c751dd2878a8bb9c13179228cb8dbb50edf1d19cd5404271` | `609f0197a0b4551ab42279e452c11cdd256135b1d467c2a95950b0e9fd7ddef8` |
| `net/minecraft/world/entity/player/Player.java` | `8fc187f33999db9dfc49251e95e92a17645a50adab16ca0f93f4948209f62036` | `8dc5514fe44311f39268692f5188840b9f65cb71143228fa8b84242585ea7987` |
| `net/minecraft/client/player/LocalPlayer.java` | `f1fcfed4a938732361e7ad951f93e9b73b02320ee56f0b219e2b3c7acdbfa2ef` | `53f2a71a886b9c71853afe36f2df857f80a9bf1cd6ec4ca8604ccd7e238d89ee` |

## Ordered behavior and predicates

**Ordinary local player, on foot, default chest-slot Elytra, glide flag already active, dry, and on a ladder/vine** (block-state producer and shape are outside this slice):

1. `LivingEntity.tick` checks `isFallFlying()` and invokes `updateFallFlying()` before building the travel input and dispatching `travel` (1.21.5 `LivingEntity.java:2753–2767`; 1.21.6 `:2804–2819`). The update first caps accumulated fall distance. Old `Entity.checkSlowFallDistance()` and new `Entity.checkFallDistanceAccumulation()` use the identical strict predicate `deltaMovement.y() > -0.5 && fallDistance > 1.0`, then write exactly `fallDistance = 1.0` (1.21.5 `Entity.java:2506–2510`; 1.21.6 `:2674–2678`). This is a relocation/rename, not a numeric or gate change.
2. `updateFallFlying()` then has a logical-side gate. On the logical **client**, it does not run `canGlide()` and does not write the fall-flying shared flag. Only on the logical server does it test `!canGlide()`, write flag 7 false with `setSharedFlag(7, false)`, and return (1.21.5 `LivingEntity.java:2815–2821`; 1.21.6 `:2871–2877`). Thus this updater does not cancel the already-active local client's glide at the ladder/vine in this scenario. Ordinary default Elytra eligibility is otherwise retained in `canGlide()` (old `:2837–2849`, new `:2893–2905`); no custom gear variant is considered here.
3. For the local player, `Player.travel` reaches `super.travel(input)` in the ordinary on-foot, non-swimming, non-flying, non-passenger case (1.21.5 `Player.java:1426–1448`; 1.21.6 `:1438–1460`). `LivingEntity.travel` tries fluids first; with the dry precondition removed, `isFallFlying()` selects `travelFallFlying` (old `LivingEntity.java:2213–2221`; new `:2245–2254`).
4. Within `travelFallFlying`, `onClimbable()` selects `travelInAir(input)` first and then `stopFallFlying()` (old `LivingEntity.java:2301–2320`; new `:2353–2372`). The other branch computes `updateFallFlyingMovement`, sets velocity and calls `move(MoverType.SELF, getDeltaMovement())` (old `:2306–2313`; new `:2358–2365`). The climb branch does **not** enter that explicit glide-update/`move` sequence. It runs `travelInAir`, which updates velocity, and then stops gliding; `travelInAir` itself has no `move` call. This is the same order in both releases.
5. `stopFallFlying()` writes shared flag 7 true and immediately writes it false (`setSharedFlag(7, true)` then `setSharedFlag(7, false)`) in both releases (old `LivingEntity.java:2317–2320`; new `:2369–2372`). On subsequent travel dispatch, with the flag false, the `LivingEntity.travel` ordinary-air branch selects `travelInAir` directly. The ordinary-air branch and the climb diversion therefore use the same air velocity updater; the climb diversion additionally performs the ordered two flag writes after that updater.

## Local authority reachability

1.21.6 adds `&& this.isEffectiveAi()` to the non-ridden travel call gate: old `LivingEntity.java:2763–2767` uses `canSimulateMovement()` alone, new `:2815–2819` requires both `canSimulateMovement()` and `isEffectiveAi()`. `Player` overrides both methods with the same expression on both sides—`!level().isClientSide || isLocalPlayer()` (old `Player.java:1321–1328`; new `:1333–1340`). `LocalPlayer.isLocalPlayer()` is true (`LocalPlayer.java:348` old, `:342` new). Consequently the added conjunct is true for the target local client player and does not change this route’s reachability; it also remains false for a remote client-side `Player` under both relevant predicates. No extra local-authority condition changes the selected active-glide/climb exit here.

## Bounded conclusion

No behavioral difference is established for the requested active local-player climb-exit slice across 1.21.5→1.21.6. The updater’s fall-distance cap is moved/renamed with identical predicate and assignment; updater cancellation remains server-only; the explicit glide route, climb-to-air-then-stop order, flag writes, and default local-player travel reachability are unchanged. This report does not cover climbable state production, fluid precedence beyond the stated dry gate, collision geometry, glide start or final tick, custom equipment, landing, or runtime trajectory parity.
