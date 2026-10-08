# F-BLOCK-CONTACT-TRAVERSE — swept player block contact

- Older version A: 1.21.1, Mojmap.
- Newer version B: 1.21.3, Mojmap.
- Mechanic / coverage slice IDs: client player movement; S-BLOCK-CONTACT, S-ENTITY-MOVE, S-TRAVEL.
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: historical player behavior
- First changed release: unknown within (1.21.1, 1.21.3]
- Runtime validation: not performed

## Paired evidence

Manifest artifact roots are `../../../build/movement-campaign-2026-10-07/ready/1.21.1/mojmap` (A, source manifest `900f956e00f6fc1300bb3d689ea49df2b1a57bcaa54617344ef456d95d47cb48`, artifact manifest `09ced418cbc7530a1d6d8802ee10c05cd576b217a2129655a71f30a2ae38f486`) and `../../../build/movement-campaign-2026-10-07/ready/1.21.3/mojmap` (B, source manifest `d673bb5464853e3a2a92ed9b1ffe1789d4e62c097bb884f67c336fcbb3b178ce`, artifact manifest `0587668e5c70bb06dacf3496a4442f66dc9cdcab23f2350b844f14259cc40dcf`).

- A `Entity.move(MoverType, Vec3)`, `net/minecraft/world/entity/Entity.java`, lines 598-727, SHA-256 `b81905c7879e2cc5c0633a41d865c4164ad919f156306705be791d1017b99850`: after movement processing it calls `tryCheckInsideBlocks()` at lines 705-706. `checkInsideBlocks`, lines 1001-1031, enumerates only the current bounding-box cells (after the chunk guard) and invokes `entityInside` for their states.
- A player reachability: `LocalPlayer.tick`, `net/minecraft/client/player/LocalPlayer.java`, lines 191-210, SHA-256 `c555e68ac3c63ab9b4f9a9e31933e263b96350a2bc599d11a0de5928bc24b583`; `Player.aiStep`, `net/minecraft/world/entity/player/Player.java`, lines 513-535, SHA-256 `ed32b88c3c7c8418b83db41823520f2b6b0b49a98610306ef26e9681dc925c71`; and `LivingEntity.aiStep`/`travel`, `net/minecraft/world/entity/LivingEntity.java`, lines 2586-2681 and 2091-2217, SHA-256 `324a3eee8496caab57cfaf5101ef576f1ae3c60c40e3857e96f35f3af9a3a0d8`. The ordinary land/air path reaches `Entity.move` through `handleRelativeFrictionAndCalculateMovement`.
- B `LivingEntity.aiStep`, `net/minecraft/world/entity/LivingEntity.java`, lines 2690-2779, SHA-256 `087390495f4fdfd14b9e12230e7aea4fdc50913bb4d1d91119e72892881cfc52`, calls `applyEffectsFromBlocks()` after travel under `!level().isClientSide() || isControlledByLocalInstance()`. The latter is true for the normal local player: `Entity.isControlledByLocalInstance`, `net/minecraft/world/entity/Entity.java`, lines 3149-3151, falls back to `isEffectiveAi()` when there is no controlling passenger; B `LocalPlayer.isEffectiveAi`, `net/minecraft/client/player/LocalPlayer.java`, lines 478-480, SHA-256 `fbd40f1f47adfa66dda9b15188e5dce82af3e8e8d7c3dd0543e602a354ad3fe0`, returns true.
- B `Entity.applyEffectsFromBlocks()`, `Entity.java`, lines 736-771, SHA-256 `a93719c302a0381a972af75ea360465e2e3551708dd07c34d4d40b7e5173c2b9`, passes `oldPosition()` and `position` to the overload, which records that movement and calls the path checker at lines 740-752. B `Entity.checkInsideBlocks`, lines 1038-1078, traverses the cells along each movement using `BlockGetter.boxTraverseBlocks`, applies the inside-collision-shape gate, then invokes `entityInside` for qualifying cells. The default B provider `BlockBehaviour.getEntityInsideCollisionShape`, `net/minecraft/world/level/block/state/BlockBehaviour.java`, lines 353-355, returns `Shapes.block()`; the scanner accepts that full-block shape for a traversed cell without the moving-shape intersection test.
- Both endpoints: `WebBlock.entityInside`, `net/minecraft/world/level/block/WebBlock.java`, lines 26-33, identical SHA-256 `ece9f8840481748d2d1542990b442cc9a97d670255d693b85f528fa618f48414`, calls `makeStuckInBlock` with horizontal multiplier `0.25` and vertical multiplier `0.05` (the weaving-effect branch is unchanged). COBWEB is registered in A `Blocks.java` lines 765-770, SHA-256 `538ac50c164484ca4c2367a2a6c07cf2faedcac0c4d5117d8817d36c2d1bde6d`, and B `Blocks.java` lines 701-706, SHA-256 `28a66da36ce98800226ec6d8e5fb4904fc7b137322a31f6d7ec822450374e62a`. `Entity.move` reads, applies and resets `stuckSpeedMultiplier` at A lines 611-615 and B lines 633-637.

## Source-level difference

For a movement whose player bounding box traverses a cobweb block's inside shape but ends with no overlap, A's post-move scan visits only the final bounding-box cells and does not call the callback for that cobweb. B's post-travel scan traverses cells along the old-position-to-current-position movement and can call it. The ordinary local player reaches B's scan because `LocalPlayer.isEffectiveAi()` returns true. The unchanged cobweb callback then writes a movement multiplier which is consumed by the next `Entity.move`; this is conditional on the path geometry, callback guards and a movement component affected by that multiplier.

## Reachability and dependencies

On both sides the local player tick reaches `Player.aiStep`, `LivingEntity.aiStep`, travel and `Entity.move`. A invokes the inside-block callback from each eligible move and scans the post-move box when the current box's chunks are available. B replaces that per-move callback with a post-travel pass from `oldPosition()` to `position`; the server-or-locally-controlled guard passes for `LocalPlayer` because its `isEffectiveAi()` override returns true. B's path checker uses the current entity box, path-traversed block positions and each state's inside-collision shape. For COBWEB, the unchanged `WebBlock` callback writes `stuckSpeedMultiplier`, which both versions consume on the next move. This finding is limited to client local-player movement; it makes no claim about server movement equivalence.

## Consequence and uncertainty

Source-proven: with the stated path and shape preconditions, A's final-box scan omits an intermediate-only cobweb callback while B's path traversal can invoke it, and the callback's multiplier is read by the next move. It is a source-level prediction that this changes the next client-predicted movement vector when the multiplier affects a nonzero component. No trajectory or reproduction was run. Other block providers/resources and pair-wide travel dependencies remain open.

## Handoff

Independent delta: B adds a post-travel path traversal for inside-block callbacks, replacing A's per-move current-box callback; under intermediate-only cobweb contact this changes callback reachability. Related slices: S-BLOCK-CONTACT, S-ENTITY-MOVE and S-TRAVEL. Applicability is restricted to ordinary local-client player movement and the guards above. First changed release unknown within (1.21.1, 1.21.3]. Implementation and testing decisions are deferred.
