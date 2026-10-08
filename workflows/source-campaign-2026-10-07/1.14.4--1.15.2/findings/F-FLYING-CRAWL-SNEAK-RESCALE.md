# F-FLYING-CRAWL-SNEAK-RESCALE: flight cancels crawl slowdown only in 1.14.4

- Older version A: Minecraft 1.14.4
- Newer version B: Minecraft 1.15.2
- Mechanic / coverage slice IDs: INV-TICK, INV-STATE; S-INPUT-KEYS, S-LOCAL-PRETRAVEL
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: local-player flight through a dry crawl pose
- First changed release: within (1.14.4, 1.15.2]
- Boundary resolution: the endpoint delta is confirmed; the earliest changed release inside the interval has not been established, and no intermediate-release source check is claimed.
- Runtime validation: not performed

## Paired evidence

- A `net/minecraft/client/player/LocalPlayer.java::LocalPlayer#aiStep()`, lines 625-630, passes `isVisuallySneaking() || isVisuallyCrawling()` plus `isSpectator()` to `KeyboardInput.tick`; its later controlled-flight block, lines 727-737, divides both horizontal impulses by `0.3` whenever the sneak key is down. SHA-256 `0795c1223198ce5acf5d2ed9e5db8435bbec4cd52b96f96b1ddf2865baaae85f`.
- B `LocalPlayer#aiStep()`, lines 632-636, passes `isMovingSlowly()` to `KeyboardInput.tick`; `isMovingSlowly()` lines 602-604 includes `isVisuallyCrawling()`. Its controlled-flight block, lines 735-744, uses shift only for downward flight and does not divide horizontal impulses. SHA-256 `3a9019bd7b860e251c23fd8d0cd70b7f5b38566d34470c4e29b1014ef689ccbd`.
- A `net/minecraft/client/player/KeyboardInput.java::KeyboardInput#tick(boolean,boolean)`, lines 13-25, multiplies horizontal impulses by 0.3 when the caller marks slow movement (provided the player is not a spectator); SHA-256 `33daa0833a95e09e728c1b8f509020dd13b70e38aba922e2b1e10ec5c9b7739a`.
- B `KeyboardInput#tick(boolean)`, lines 13-25, applies the same 0.3 multiplier whenever the caller marks slow movement; SHA-256 `746ea654cf4f46a5f4b94a237c4307652252a44807a488b606dc993e088396f7`.
- A `LocalPlayer#isVisuallySneaking()` is disabled while ability-flying (lines 593-597). B `LocalPlayer#isCrouching()` is disabled while ability-flying and `isMovingSlowly()` still includes the visual-crawl predicate (lines 596-604). Thus for the stated flying crawl state both callers mark slow movement through `isVisuallyCrawling`, independent of the crouching branch.
- A/B `Entity#isVisuallyCrawling()` checks the current visual swimming pose and that the player is outside water (A lines 1840-1842; B lines 1823-1825). `Entity#canEnterPose(Pose)` checks collision for the candidate pose (A line 1641; B line 1605). The paired `Player#updatePlayerPose()` methods fall back to `Pose.SWIMMING` when swimming clearance fits but standing and sneaking/crouching do not (A lines 344-370; B lines 357-383); the mapped dimensions are 0.6 high for swimming, 1.5 for sneaking/crouching and 1.8 for standing (A `Player.java` lines 101-118; B lines 102-119). Hashes: A `Player.java` `e9ce5eb18c5581ffe2b610b273ffd85786587a00ba853e5dba0ac96593622b12`, B `1ba2724c22163862b8f7fdfdea5a04a66e4db26a119d7e5360ba024724a34793`; A `Entity.java` `31c6be42d165102d3e6dc4295fdfef6e8971fc3aea13f938a5b3a33b91d524a7`, B `191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e`.
- Both `LocalPlayer#serverAiStep()` methods copy the final input impulses to `xxa` and `zza` when the local player is the camera, and both `LocalPlayer#isEffectiveAi()` return true (A `LocalPlayer.java` lines 483-485 and 600-605; B lines 490-492 and 607-612). `LivingEntity#aiStep()` passes those movement fields to `travel()` after `serverAiStep()` (A line 2223; B line 2287). `Player#travel()` delegates to `LivingEntity.travel()` when flying (A lines 1380-1405; B lines 1448-1475), and `Entity#moveRelative` / `getInputVector` retain vectors with squared length at or below 1.0 rather than normalizing them (A lines 1080-1094; B lines 1063-1077). A cardinal input therefore reaches travel as 1.0 after A's compensation and 0.3 in B.

## Source-level difference

Reachable precondition: a non-spectator local player is ability-flying, is the controlled camera, is outside water in a dry crawl pose, and holds a directional key plus sneak/shift. The crawl pose can persist in a low passage with enough space for the 0.6-high swimming pose but not the 1.5-high crouching/sneaking pose; while flying, both pose selectors choose standing and then fall back to swimming in that passage.

Both keyboard handlers first scale horizontal input by 0.3 because the caller identifies the dry crawl pose as slow movement. A then divides the horizontal impulses by 0.3 when sneak is held during controlled flight; B has no corresponding compensation. The final movement vector is consequently full-scale in A and 0.3-scale in B for a cardinal direction. In clear flight without the crawl pose, A's key-triggered 0.3 reduction is canceled by division and B applies neither operation, so the ordinary clear-space case remains equivalent.

## Reachability and dependencies

`LocalPlayer#aiStep()` samples input before its controlled-flight adjustment and then calls `super.aiStep()`. The `LivingEntity.aiStep()` server-AI dispatch reaches the LocalPlayer override, which copies the adjusted values before it calls `travel()`. The Player flying branch forwards that vector to `LivingEntity.travel()`, whose movement-relative path consumes it. The comparison is bounded to the horizontal movement input; the separate flight jump toggle, Elytra request, and auto-jump guards remain tracked under `S-LOCAL-PRETRAVEL` and their own dependencies.

## Consequence and uncertainty

The source proves a 1.0 versus 0.3 horizontal input magnitude for the stated cardinal-input precondition. It does not establish measured velocity or trajectory. The first release with the changed compensation inside the endpoint interval is unknown.

## Handoff

Preserve the 1.14.4 sneak compensation only when the selected historical version requires it. This finding is limited to controlled flight with a dry crawl pose; the wider tick, pose and movement-source inventories remain open in the run ledger.