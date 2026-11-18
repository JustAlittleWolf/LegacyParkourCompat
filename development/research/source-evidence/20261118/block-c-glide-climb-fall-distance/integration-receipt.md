# Corrected climbable glide fall-distance integration receipt

## Product, review, and build identities

- Product branch: `codex/c-glide-climb-fall-distance-corrected`
- Current primary `main` ancestry: `aae9c86464638cb5ed7faa535c92569b89484906`
- Source evidence already in main ancestry: `8438e45a9ecc96f62764137a8ceeb10dcec6fdf1`
- Imported exact source evidence commits: aliases `cf5d89aefd1078e6e71425e1f5b702409c46000a`; route `8f161c8c0e153b9228ad1e293df28e9c83452e3f` with docs parent `9799e43bd1dbf97031f89bc45cdd43e73258ead4`.
- Corrected code commit: `a03cdbabbcd4e7d97158790a98f8cc16b5b9dd2e`
- Compiled code tree: `977b5f89494a4c168645e3651f274472a8d74122`
- Independent exact-tree static review: PASS for `a03cdbabbcd4e7d97158790a98f8cc16b5b9dd2e` / `977b5f89494a4c168645e3651f274472a8d74122`.

The held code commit `8dad6ae18d6706e5c964aaed885e26ea59f66969` is not an ancestor. The original ready pin remains held and unchanged at `refs/campaign-ready/c-glide-climb-fall-distance` → `20186143e63e527923e990f90e2388687b0a5f17`.

## Integrated operation and exact scope

The change reuses the existing `GlideFallDistanceLimiterStageHook` stage scheduler and `GlideFallDistanceHook` travel assignment. It adds no scheduler, mixin, hook, or native movement loop.

For the selected `V1_18_2` profile and `V1_19` profile group (1.19–1.19.2), the existing stage provider now accepts the already-active ordinary local dry glide witness with climbable feet, provided every pre-existing eligibility condition still passes: local client player, fall-flying flag, airborne, not creative-flying, not a passenger, default chest Elytra, and no non-chest glider. It selects the existing travel callback, so the pre-travel native limiter callback does not run for this target. At travel, the existing historical assignment executes once: when current vertical delta is strictly greater than `-0.5`, write `fallDistance = 1.0F` before the glide move.

Both target paths short-circuit profile eligibility before calling `player.onClimbable()`. This preserves the historical branch's lack of a climbability read and avoids updating the modern `lastClimbablePos` cache for the target witness. Other selected profiles retain the previous climbability query, stage decision, assignment gate, and native fallback. Non-climbable targets retain the existing behavior. The change does not claim glide-start eligibility, later collision/landing effects, a final tick value, other states, or runtime parity.

## Bound source evidence

- Mojmap 1.18.2/1.19.2 active climbable-feet scope comparison: SHA-256 `7d5f95456c9f1f7d2373b9a08aae27e99d1059efe0c06a679126f4da89aeac21`; independent verdict: `128b50a9f2227f2e568a76e654f34267cc5d15860bfbcc72f4b89afd9934b0d8`. Source paths are exact `decompiled_minecraft/<version>/mojmap/net/minecraft/world/entity/LivingEntity.java` and `Entity.java`; the accepted report binds the dry active travel path, feet-block climbable witness, and strict `y > -0.5` / `1.0F` assignment.
- Mojmap 1.19/1.19.1 alias: report `62eeb88e913f8d4aec58eb747128df4e0231d06aacf61b7425aa385ddfe1afd9`, verdict `aadb32534227e2c8f4b075862231b1722134c6a2bb909684ccf5063dd4adcbff`.
- Mojmap 1.19.1/1.19.2 alias: report `fe17c39e0c8847e3b872645e81fa79afa0505dc0c99346d1b0d13e8b1047f1f8`, verdict `35f3a2ac4a7c6dbc1a9a8ceb2e2a6c57ad1e94de7bdfa05a82ccd3677ea6ea98`.
- Mojmap 1.19.2/1.19.3 active-state and travel gate: report `46beb961fb7f98cb9a6889d4a5b16865188021ab7ea122d6596383d9668b6fa8`, verdict `6c33ca487f745aa067113b82373e47d53fd3aae0b40e8a33ce9030e04257e57f`; the later 1.19.3 operation is a distinct `checkSlowFallDistance` cap.
- Mojmap 1.19.2/26.2 endpoint route: report `ac6c82cf3d7bb4d0e65eda567b9d5f8b9f1fa1e9e67f962da15409b9e2a9273f`, verdict `137928ec30e14f18e1662eb371671dc1545674036633c2689a15c8046ab599f1`.
- Mojmap 1.21.1/1.21.2 active dry glide cap stage comparison: report `c3148cfc94158e786ae6ddf85268df8a995c4ef5c268290633642c2a2664bdd6`, verdict `53e9730787825417192ec341f74fb7ade48c6e5a12687d9adf4b8330ad2a7aa0`.

The Change comments cite Mojmap `LivingEntity.travel(Vec3)`, `LivingEntity.updateFallFlying()`, and `Entity` fall-distance limiter members. The accepted reports bound the source witnesses; compilation and static integration review do not establish movement parity.

## Test-disabled build

- Exact command: `gradlew.bat build -x test --init-script development/disable-tests.gradle --console=plain`
- Result: `BUILD SUCCESSFUL`; 19 actionable tasks, 3 executed and 16 up-to-date. The init script disables every Gradle `Test` task and `-x test` was supplied.
- Build ran on exact compiled code commit/tree above.
- Compiled class SHA-256 values:
  - `v1_19/GlideFallDistance.class`: `67BA0003FD89146763B1B5C5D088AA7FE50CB5963545747D17719C409B52B8B7`.
  - `v1_21/GlideFallDistanceLimiterStage.class`: `D94D114EF60E798323A01DBBCD3E1272DE53080F916276A876AD02766E1045B6`.

Intended central finding association: `development/research/findings/1.18.2--1.19.4/glide-fall-distance.md`.
