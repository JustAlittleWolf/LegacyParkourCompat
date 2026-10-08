# F-01: sprint air speed reads current sprint state and changes by one float ULP

- Older version A: 1.19.3
- Newer version B: 1.19.4
- Mechanic / coverage slice IDs: INV-TICK / I-TICK-AIR-SPEED; INV-STATE / I-STATE-SPRINT-SPEED
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: historical player behavior
- First changed release: unknown within (1.19.3, 1.19.4]
- Runtime validation: not performed

## Paired evidence

All paths are repository-relative; hashes match the verified per-version source manifests.

- A `build/movement-campaign-2026-10-07/ready/1.19.3/mojmap/net/minecraft/world/entity/player/Player.java`, `Player#aiStep()V`, lines 496–517, SHA-256 `02e64e197aec3f4f2a8abc9c5ce4545121ac5bcbbc71592ce478601a0db17203`: invokes `super.aiStep()` before assigning `flyingSpeed = 0.02F` and conditionally adding `0.006F` when sprinting.
- B `build/movement-campaign-2026-10-07/ready/1.19.4/mojmap/net/minecraft/world/entity/player/Player.java`, `Player#aiStep()V`, lines 499–510, SHA-256 `5e4436afccb361156f8184e7a5cfd91d5b12dcfe8f5937b4ac23dd3edda737a2`: no corresponding stored-air-speed assignment; `Player#getFlyingSpeed()F`, lines 2112–2118, returns `0.025999999F` while sprinting and `0.02F` otherwise.
- A `.../1.19.3/mojmap/net/minecraft/world/entity/LivingEntity.java`, `LivingEntity#handleRelativeFrictionAndCalculateMovement(Vec3)` and `getFrictionInfluencedSpeed()F`, lines 2201–2248, SHA-256 `2b8befdc406176e01672175451dd480b14da0b52bfe11a982d368909c05e73db`: airborne movement reads `flyingSpeed` and passes it to `moveRelative`.
- B `.../1.19.4/mojmap/net/minecraft/world/entity/LivingEntity.java`, corresponding methods, lines 2152–2203, SHA-256 `c8d91af61f87aaa1666de79696d7cd9d4a8212d05f873bdaf28d7bb2926bf165`: airborne movement calls virtual `getFlyingSpeed()` and passes that value to `moveRelative`.
- A/B `LocalPlayer#aiStep()V`, respectively A source lines 652–819 and B lines 645–800; hashes `64b670ee323d195b3928fb8ea629c26d2560e75a17379a12c376a7bc686d5479` and `8e7da18f42d09fbb994f522c2b0e65fcb2bb83cabb21024360299d44d9674c58`. Both set/update sprint state before calling the superclass tick that reaches travel.

## Source-level difference

For ordinary non-passenger, non-fall-flying player travel outside abilities flight, A's airborne movement consumes stored `flyingSpeed` during the superclass travel call. `Player#aiStep` writes that field only after its superclass returns, so a sprint transition made by `LocalPlayer#aiStep` is reflected in the air-speed field on the following player tick. B obtains the speed through `Player#getFlyingSpeed()` during travel, so the current sprint flag controls the current travel call.

The constants also differ at single precision. A evaluates `0.02F` then adds `0.006F`; as Java `float`, this is `0.026000000536441803` (bits `0x3cd4fdf4`). B's `0.025999999F` is `0.025999998673796654` (bits `0x3cd4fdf3`). They differ by one representable float step. B's ability-flight branch is separate and outside this finding.

## Reachability and dependencies

`LocalPlayer#aiStep` sprint state -> `Player#aiStep` / `LivingEntity#aiStep` -> `LivingEntity#travel` -> relative-friction helper -> airborne speed consumer. A's consumer reads the field written after superclass travel; B's consumer calls the player override during travel. Grounded friction uses block friction instead and is not covered here.

## Consequence and uncertainty

Source proves a one-tick state-read timing difference and a one-ULP coefficient difference in the ordinary airborne relative movement input. It does not establish an observed displacement or runtime trajectory. Other movement branches and modifiers remain under audit.

## Handoff

Separate from the auto-jump math delta in F-02. Apply only to the stated ordinary airborne player path. Release introduction is unknown within the compared interval.
