# F-004: Air sprint speed follows the current sprint flag in B

- Older version A: Minecraft 1.18.2
- Newer version B: Minecraft 1.19.4
- Mechanic / coverage slice IDs: stage 1 sprint transition ordering; stage 3 airborne movement acceleration
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: historical player behavior (sprint flag changes while the player is airborne)
- First changed release: unknown within (1.18.2, 1.19.4]
- Runtime validation: not performed

## Paired evidence

- Artifact identity: exact release-specific Mojmap sources A and B, verified in `run.md`.
- A: `decompiled_minecraft/1.18.2/mojmap/net/minecraft/world/entity/player/Player.java`, `aiStep()`, lines 508–513; SHA-256 `BF639C1962FF90D69E4569B2B18F6FCF57AC46EF80B19686F0FBC1687FCA744A`. It calls `super.aiStep()` before recalculating and storing `flyingSpeed` from the sprint flag.
- A: `.../world/entity/LivingEntity.java`, `aiStep()` reaches `travel(Vec3)` before returning to `Player.aiStep()`; `getFrictionInfluencedSpeed(float)` lines 2206–2207 reads the stored field in air. SHA-256 `DB4168D531CAF18F22E3FEFD073365E776DA4075CE01452BB9F7671D9B458782`.
- B: `decompiled_minecraft/1.19.4/mojmap/net/minecraft/world/entity/player/Player.java`, `getFlyingSpeed()`, lines 2112–2117; SHA-256 `5E4436AFCCB361156F8184E7A5CFD91D5B12DCFE8F5937B4AC23DD3EDDA737A2`. The override reads `isSprinting()` when called.
- B: `.../world/entity/LivingEntity.java`, `getFrictionInfluencedSpeed(float)`, lines 2197–2198; SHA-256 `C8D91AF61F87AAA1666DE79696D7CD9D4A8212D05F873BDAF28D7BB2926BF165`. Its air branch calls the player override.
- A/B: `decompiled_minecraft/<version>/mojmap/net/minecraft/client/player/LocalPlayer.java`, `aiStep()` (A lines 642–716; B lines 645–708), hashes A `99C2D18BCD23243AFB8F95C5BAFB21FB0BE7EA04AACBB14FCF7BE7CED2C9C095`, B `8E7DA18F42D09FBB994F522C2B0E65FCB2BB83CABB21024360299D44D9674C58`. Sprint start/stop calls occur before the final `super.aiStep()` dispatch.

## Source-level difference

In A, inherited travel runs inside `Player.aiStep()` before that method updates `flyingSpeed`. If the sprint flag changed earlier in the same local-player tick, the current airborne travel still reads the value stored after the preceding tick's travel; the new value is stored only after the current travel returns. In B, the air travel accessor calls `Player.getFlyingSpeed()` and reads the current sprint flag directly. Thus a sprint start or stop while already airborne affects the B air-speed lookup in that travel call, while A uses its previous cached sprint state for one such call. This finding concerns lookup timing; the one-ULP stable-state value difference is separate in F-003.

## Reachability and dependencies

Local client path: `LocalPlayer.aiStep()` sprint-state transition -> `Player.aiStep()` -> `LivingEntity.aiStep()` -> `travel(Vec3)` -> `handleRelativeFrictionAndCalculateMovement()` -> air-speed lookup -> `moveRelative()`. Preconditions exclude grounded movement, fluids, fall-flying and creative flight. A sprint transition can come from the local sprint key or automatic sprint rules; the individual gates remain under stage 1 coverage.

## Consequence and uncertainty

B applies the current sprint value to the airborne input acceleration one movement call sooner on a transition. The direction and value are source-proven; no position trajectory was measured. Exact first changed release remains unknown within `(1.18.2, 1.19.4]`.

## Handoff

Related to F-003. Sprint eligibility, start/stop triggers and how they interact with any vehicle remain in stage 1 dependencies. Runtime validation is deferred.
