# F-S2-POSE-EPSILON: 1.16.5 shrinks the candidate box for pose clearance

- Older version A: 1.15.2, Mojmap
- Newer version B: 1.16.5, Mojmap
- Mechanic / coverage slice IDs: player pose selection and collision clearance; S2-POSE, INV-STATE, INV-COLLISION
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: player pose-entry checks when `Player#updatePlayerPose` tests whether a target pose can be entered
- First changed release: unknown within (1.15.2, 1.16.5]
- Runtime validation: not performed

## Paired evidence

- A manifest artifact reference: `../../../build/movement-campaign-2026-10-07/ready/1.15.2/mojmap`; `net/minecraft/world/entity/player/Player.java`, `Player#updatePlayerPose`, lines 357-384; SHA-256 `1ba2724c22163862b8f7fdfdea5a04a66e4db26a119d7e5360ba024724a34793`. `net/minecraft/world/entity/Entity.java`, `Entity#canEnterPose`, lines 1605-1607, and `Entity#getBoundingBoxForPose`, lines 2320-2325; SHA-256 `191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e`.
- B manifest artifact reference: `../../../build/movement-campaign-2026-10-07/ready/1.16.5/mojmap`; `net/minecraft/world/entity/player/Player.java`, `Player#updatePlayerPose`, lines 350-377; SHA-256 `d2e26589bdb6a20dc914266db06aa48f50811efc792d6e63b3008c9199914960`. `net/minecraft/world/entity/Entity.java`, `Entity#canEnterPose`, lines 1640-1642, and `Entity#getBoundingBoxForPose`, lines 2360-2365; SHA-256 `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666`.
- Shared geometry evidence: A `net/minecraft/world/phys/AABB.java`, `inflate(double)` lines 157-159 and `deflate(double)` lines 246-248; SHA-256 `82324c0e6a3d69e80424656e6098b39ce41f0f17a5e6efbe6542fe8eaca24a18`. B same methods, lines 161-163 and 250-252; SHA-256 `514558cf4827679d84a4debd4f400d7d65bd4b8982b7ae8124a0d83a916ba878`. `deflate(d)` delegates to `inflate(-d)`, and scalar inflate applies the value on each axis.
- Paired unchanged pose/dimension path: the player pose maps, `updatePlayerPose` decision order, player eye-height switch, `Entity#onSyncedDataUpdated` pose listener, and `Entity#refreshDimensions` are equal in behavior. A/B `Pose.java` hashes both equal `3e3d6c8f1d28686e5bee8b2e8a043832fd8af03e4eb8df35551a5db3d2bacebd`. Player and Entity hashes are above.

## Source-level difference

A `Entity#canEnterPose(pose)` submits `getBoundingBoxForPose(pose)` directly to `level.noCollision`. B submits that candidate box after `.deflate(1.0E-7)`. AABB deflation shrinks the candidate on all axes by the supplied epsilon. Player pose selection calls this predicate for swimming eligibility, its selected pose, and crouching fallback; the method body and branch order are otherwise the same between endpoints.

When a collision shape overlaps only the thin boundary removed by B's deflation, the two calls give the collision query different candidate boxes and may select different poses. No displacement or full collision result is inferred here. Once a pose is written to synced entity data, the unchanged listener refreshes dimensions, eye height and the bounding box.

## Reachability and dependencies

The finding is restricted to the player pose-entry predicate and candidate AABB. It does not claim a difference in pose dimensions: A and B have the same player pose map, swimming/fall-flying/spin-attack eye height `0.4F`, crouching eye height `1.27F`, and default eye height `1.62F`. The full shape-provider and block-registration inventory is not yet closed; those sources determine which real block shapes can present the boundary condition and remain a separate collision inventory dependency.

## Consequence and uncertainty

The source proves a `1.0E-7` per-axis inset in B's pose-clearance argument. Whether a specific world position changes pose depends on the block shapes and exact box/query geometry. No runtime collision or player trajectory was tested. The first changed release within the endpoint interval is unknown.

## Handoff

Independent source delta: B tests an inset candidate box for player pose entry; A tests the full candidate box. The pose map, player pose decision order, dimensions, eye heights and pose-triggered dimension refresh match. Shape-level reachability remains open under the required collision/shape inventory.
