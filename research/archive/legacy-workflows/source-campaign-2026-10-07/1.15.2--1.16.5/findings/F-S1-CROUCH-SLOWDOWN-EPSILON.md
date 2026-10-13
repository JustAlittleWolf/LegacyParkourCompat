# F-S1-CROUCH-SLOWDOWN-EPSILON: pose-clearance epsilon changes keyboard slowdown

- Older version A: 1.15.2, Mojmap
- Newer version B: 1.16.5, Mojmap
- Mechanic / coverage slice IDs: local input sampling and crouch slowdown; S1-LOCAL-AISTEP, S2-LOCAL-CROUCHING-STATE, S2-POSE, INV-TICK, INV-STATE, INV-COLLISION
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: historical player behavior
- First changed release: unknown within (1.15.2, 1.16.5]
- Runtime validation: not performed

## Paired evidence

### A artifact identity

- Evidence artifact record ID: run.md Artifact Manifest A — Java Edition 1.15.2 Mojmap
- Publication status: original-verified
- Immutable evidence path: `../../../build/movement-campaign-2026-10-07/ready/1.15.2/mojmap`
- Evidence artifact SHA-256: source files below; original client jar SHA-256 `4a73008a73f3824b7c711750a5a37556df8614f193c0a531e292dad159a73a7c`
- Evidence manifest path / SHA-256: `../../../build/movement-campaign-2026-10-07/ready/1.15.2/artifacts.sha256` / `208ab867640097a0c188e452de4876934deb217d75ac726d358cfa6730260406`
- Original artifact-manifest path / SHA-256: same ready artifact manifest and SHA-256 above
- Original derived-artifact availability and SHA-256 or expected hash: exact Mojmap source tree is ready and source-hash checked; no revised artifact used
- Source/raw-input hash relation and verification reference: source file hashes are listed in the A manifest in run.md and were rechecked against the ready tree
- Revised-to-original derived-artifact equivalence and evidence reference: not applicable
- Provenance limitations: runtime movement and a specific collision layout were not validated
- Relative source path; fully qualified class; member signature/descriptor; original line range; source hash:
  - `net/minecraft/client/player/LocalPlayer.java`, `LocalPlayer#isCrouching()`, lines 591-604; `LocalPlayer#aiStep()`, lines 625-641; SHA-256 `3a9019bd7b860e251c23fd8d0cd70b7f5b38566d34470c4e29b1014ef689ccbd`
  - `net/minecraft/client/player/KeyboardInput.java`, `KeyboardInput#tick(boolean)`, lines 13-28; SHA-256 `746ea654cf4f46a5f4b94a237c4307652252a44807a488b606dc993e088396f7`
  - `net/minecraft/world/entity/Entity.java`, `Entity#canEnterPose(Pose)`, lines 1605-1607; SHA-256 `191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e`

### B artifact identity

- Evidence artifact record ID: run.md Artifact Manifest B — Java Edition 1.16.5 Mojmap
- Publication status: original-verified
- Immutable evidence path: `../../../build/movement-campaign-2026-10-07/ready/1.16.5/mojmap`
- Evidence artifact SHA-256: source files below; original client jar SHA-256 `00b5ebbc33e95ea88c1ab80601599c9827e9aa861d93ccc2cfcbbfd996e86263`
- Evidence manifest path / SHA-256: `../../../build/movement-campaign-2026-10-07/ready/1.16.5/artifacts.sha256` / `f9b9812d6995012cefcc1201a63855e5931e8551b508753be6c46d442d8b370c`
- Original artifact-manifest path / SHA-256: same ready artifact manifest and SHA-256 above
- Original derived-artifact availability and SHA-256 or expected hash: exact Mojmap source tree is ready and source-hash checked; no revised artifact used
- Source/raw-input hash relation and verification reference: source file hashes are listed in the B manifest in run.md and were rechecked against the ready tree
- Revised-to-original derived-artifact equivalence and evidence reference: not applicable
- Provenance limitations: runtime movement and a specific collision layout were not validated
- Relative source path; fully qualified class; member signature/descriptor; original line range; source hash:
  - `net/minecraft/client/player/LocalPlayer.java`, `LocalPlayer#aiStep()`, lines 627-641; `LocalPlayer#isCrouching()`, lines 595-605; SHA-256 `6011569e766bb1568609147be9aa14e9c08c51948e3d3a60fd066e848f6a8c2b`
  - `net/minecraft/client/player/KeyboardInput.java`, `KeyboardInput#tick(boolean)`, lines 13-28; SHA-256 `746ea654cf4f46a5f4b94a237c4307652252a44807a488b606dc993e088396f7`
  - `net/minecraft/world/entity/Entity.java`, `Entity#canEnterPose(Pose)`, lines 1640-1642; SHA-256 `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666`

## Source-level difference

A `LocalPlayer#isCrouching()` computes the crouch predicate on demand and passes it through `isMovingSlowly()` to the same-tick `input.tick(boolean)`. B writes the same predicate to `crouching` immediately before that call, and its `isCrouching()` returns the cached field. The adjacent writer/consumer order means the cache itself introduces no intervening tick or input-sampling delay.

The predicate's `canEnterPose(Pose.CROUCHING)` dependency differs: A submits the full candidate box to `Level.noCollision`; B first deflates the candidate box by `1.0E-7`. With the shift key already down, and with a block collision shape intersecting only the boundary shell removed by that deflation, A's clearance test is blocked while B's is clear. Assuming the player is not flying or swimming and is not visually crawling, A therefore passes `false` to `KeyboardInput#tick`, while B passes `true`. Both versions' keyboard input bodies then multiply the forward and lateral impulses by `0.3` when that flag is true.

## Reachability and dependencies

The client path is `LocalPlayer#aiStep -> isMovingSlowly -> isCrouching/crouching snapshot -> Input#tick -> KeyboardInput#tick -> forwardImpulse/leftImpulse`. The resulting input vector is consumed by the ordinary player travel path. `LocalPlayer#isShiftKeyDown()` reads the already sampled `input.shiftKeyDown` in both versions. The source proves the conditional input-slowdown difference; it does not prove a final displacement or a specific block placement. The full collision-shape/provider and registration inventory remains open, and the pre-existing `F-S2-POSE-EPSILON` covers the related clearance predicate in the separate pose-selection route.

## Consequence and uncertainty

For the stated narrow collision-boundary condition, B can scale both movement input axes by `0.3` for this input tick when A does not. Which concrete fixed-state block layout realizes the condition remains subject to the open provider inventory. First changed release in the interval is unknown; no trajectory was tested.

## Handoff

Independent source delta: pose-clearance deflation can make the local crouch-slowdown input gate pass in B where A rejects the crouching candidate box. This finding is limited to the same-tick input boolean and the stated geometry; it makes no claim about pose selection, final displacement, or the full provider inventory. Related finding: `F-S2-POSE-EPSILON`.
