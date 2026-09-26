# F-003: Stable sprinting uses a one-ULP lower air-speed value in B

- Older version A: Minecraft 1.18.2
- Newer version B: Minecraft 1.19.4
- Mechanic / coverage slice IDs: stage 3 airborne movement acceleration; stage 1 sprint state
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: historical player behavior (sprinting player in ordinary airborne travel)
- First changed release: unknown within (1.18.2, 1.19.4]
- Runtime validation: not performed

## Paired evidence

- Artifact identity: exact release-specific Mojmap sources A and B, verified in `run.md`.
- A: `decompiled_minecraft/1.18.2/mojmap/net/minecraft/world/entity/player/Player.java`, `aiStep()`, lines 508–513; SHA-256 `BF639C1962FF90D69E4569B2B18F6FCF57AC46EF80B19686F0FBC1687FCA744A`. After `super.aiStep()`, it sets `flyingSpeed = 0.02F` and, when sprinting, adds `0.006F`.
- A: `.../world/entity/LivingEntity.java`, `getFrictionInfluencedSpeed(float)`, lines 2206–2207; SHA-256 `DB4168D531CAF18F22E3FEFD073365E776DA4075CE01452BB9F7671D9B458782`. In air the returned speed is the `flyingSpeed` field.
- B: `decompiled_minecraft/1.19.4/mojmap/net/minecraft/world/entity/player/Player.java`, `getFlyingSpeed()`, lines 2112–2117; SHA-256 `5E4436AFCCB361156F8184E7A5CFD91D5B12DCFE8F5937B4AC23DD3EDDA737A2`. For a non-creative-flying player it returns `0.025999999F` when sprinting and `0.02F` otherwise.
- B: `.../world/entity/LivingEntity.java`, `getFrictionInfluencedSpeed(float)`, lines 2197–2198; SHA-256 `C8D91AF61F87AAA1666DE79696D7CD9D4A8212D05F873BDAF28D7BB2926BF165`. In air it calls `getFlyingSpeed()`.

## Source-level difference

While sprinting with stable sprint state in ordinary airborne movement, A forms its speed through two binary32 operations: assign `0.02F`, then add `0.006F`. B returns the literal `0.025999999F`. Under Java float rounding, A's sum is binary32 `0x3cd4fdf4` (approximately `0.026000000536441803`); B's literal is `0x3cd4fdf3` (approximately `0.025999998673796654`). B is one binary32 ULP lower. When not sprinting both return/use `0.02F`. The creative-flight branch is separately coded and is outside this finding.

## Reachability and dependencies

`LocalPlayer.aiStep()` sets the sprint state before delegating to `Player.aiStep()` and `LivingEntity.aiStep()`. The standard travel path calls `handleRelativeFrictionAndCalculateMovement()`, whose air branch supplies this value to `moveRelative()`. Water, lava, fall-flying and grounded branches use other speed expressions and are excluded here.

## Consequence and uncertainty

For the same normalized movement vector and surrounding state, B supplies a one-ULP lower acceleration scale to airborne sprinting. This is a source-level numeric difference; no trajectory was computed. Exact introduction release is unknown within `(1.18.2, 1.19.4]`.

## Handoff

Related to F-004, which records the change from a post-travel cached field to a live sprint-state lookup. Keep the numeric-value and update-timing claims distinct. Runtime validation is deferred.
