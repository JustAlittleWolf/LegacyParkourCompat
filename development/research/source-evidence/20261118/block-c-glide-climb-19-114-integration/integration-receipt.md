# Early climbable Elytra fall-distance integration receipt

## Product and review identities

- Product branch: `codex/c-glide-climb-early-range`
- Clean product base: earlier reviewed glide receipt `12e5b3ce083d84e5a83fc1bde31b4b6e2aeb0dbb`, with current-main ancestor `aae9c86464638cb5ed7faa535c92569b89484906`.
- Integrated code commit: `350cd66b2d0e485ef9af899e065716e6cdabcd7f`
- Integrated code tree: `00a71cec7089ef3912e258373f95da915b4dbf87`
- Reviewed receipt commit: this document's commit; compiled tree is `47e8fa3b16832a54ba6a712d5553de18767735bc`.
- Functional independent integration review: `c_glide_climb_integration_bound_review` PASS for exact code commit/tree above.
- Citation-only follow-up review: signed PASS for exact descendant `2825447c5d73ac00bde576398cc5a1ac91490de5` / tree `47e8fa3b16832a54ba6a712d5553de18767735bc`; it confirms the Feather, Yarn, Mojmap member references and native-cap schedule citations without changing behavior.

## Integrated operation and bounds

The integration changes only the selected-profile bounds in the existing `GlideFallDistance` assignment and `GlideFallDistanceLimiterStage` scheduler. The accepted climbable interval in both operations now covers selected profile groups V1_9 through V1_19. It reuses the existing scheduler and travel assignment; no new hook, mixin, scheduler, loop, or movement operation was added.

The accepted witness is an ordinary dry on-foot local player at ladder/vine feet with a usable default chest Elytra and fall-flying already active. The historical travel branch has no climbability check before the first glide move. For strict incoming Y greater than `-0.5`, the existing travel assignment writes `fallDistance = 1.0F` once before glide movement. The two profile-first checks avoid the modern `onClimbable()` query and its cache side effect for these source-confirmed profiles. All prior client/local-player, active-flight, airborne, not-flying, not-passenger, chest-Elytra and non-chest-equipment gates remain. Other profile behavior, the native callback fallback, startup gates, glide math, final contact and collision behavior are unchanged and outside this claim.

The native-cap schedule remains the existing callback operation: in 1.21.1 the cap runs within the dry fall-flying arm of `LivingEntity.travel(Vec3)`; in 1.21.2 `LivingEntity.updateFallFlying()` invokes it before travel. The stage provider dispatches that existing callback at the accepted historical travel stage for eligible witnesses. The exact 1.21.1–1.21.2 source report/verdict are `development/research/source-evidence/20261118/block-c-glide-climb-route/1.21.1-to-1.21.2-active-glide-cap/report.md` and `verdict.md`.

## Bound source evidence

- Early exact comparisons, 1.9–1.14: source commit `e4f0ae79d37596a33a338afd500082486a803e23`, bundle SHA-256 `6483f5bf13309790fe4232547023db1e48bfe019bf4a609002c300c259e870ea`. The 17 report/verdict pairs were rehashed against their manifest with no mismatch. Manifest `block-c-glide-climb-19-114/README.md` SHA-256 `b1193ae24ad99717f97618f2889f41b82ed20eb1025700ed1c3316e2043d1c1d`.
- Exact 1.14–1.15 comparisons and active-scope correction: source commit `621143a19ca06a6bf2384d6c4d2c79a7888072ea`, bundle SHA-256 `67ae8b7e2d77fa8b7827b38040fb80be0697a961c7aa580b9d6128fd1f527774`. All five report/verdict pairs, the input-identity correction and the already-active-scope addendum were rehashed with no mismatch. Manifest `block-c-glide-climb-114-exact-aliases/README.md` SHA-256 `53e43a775133f98ed6f030a5057a5db6ad6ad193895b694c3b1ee8fa23d06909`.
- The earlier reviewed integration receipt `12e5b3ce083d84e5a83fc1bde31b4b6e2aeb0dbb` binds the accepted 1.15–1.19 sources, including exact aliases, the 1.18.2/1.19.2 climbable scope, and the 1.21.1–1.21.2 cap scheduling route. Its source commits `1422aa70dc505104e95156562052880f3a8d334c`, `9799e43bd1dbf97031f89bc45cdd43e73258ead4`, `5499504691579996c7d9edddb1079b64b536937b`, `ae77924340bef9a41f76903f61ce533d7602eefd`, `cf5d89aefd1078e6e71425e1f5b702409c46000a`, and `8f161c8c0e153b9228ad1e293df28e9c83452e3f` are ancestors of this batch.

Change comments name the source members and mapping-specific paths: Feather `LivingEntity.moveRelative(float, float)` / `flyingTick()`, Yarn `LivingEntity.travel(Vec3d)` / `initAi()`, and Mojmap `LivingEntity.travel(Vec3)` / `updateFallFlying()`. The 1.21.1–1.21.2 cap schedule comparison is separately identified above. This is bounded source acceptance plus static implementation review and compilation, not runtime parity.

## Test-disabled build

- Exact command: `gradlew.bat build -x test --init-script development/disable-tests.gradle --console=plain`
- Result on exact compiled tree `47e8fa3b16832a54ba6a712d5553de18767735bc`: exit code `0`, `BUILD SUCCESSFUL`; 19 actionable tasks, 3 executed and 16 up-to-date. `compileClientJava` ran. The init script disabled Gradle `Test` tasks and `-x test` was supplied.
- Compiled class SHA-256 values:
  - `v1_19/GlideFallDistance.class`: `734aeef8995ed26251643ce654f6ae892b9968b5eca0065644cba3d711e0592f`.
  - `v1_21/GlideFallDistanceLimiterStage.class`: `d306224d1349f011a9e04015f2c5f47f3c64265e33653b3976ecb341f74abed9`.
- Intended central finding association: `development/research/findings/1.18.2--1.19.4/glide-fall-distance.md`.

No tests or runtime launches were performed. Compilation is not a movement-parity claim.
