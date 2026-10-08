# F-BLOCK-CONTACT-CLIENT-GATE: local client cobweb callback is skipped in 1.21.3

- Older version A: 1.21.1, Mojmap.
- Newer version B: 1.21.3, Mojmap.
- Mechanic / coverage slice IDs: client player movement; S-BLOCK-CONTACT, S-ENTITY-MOVE, S-TRAVEL.
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: historical player behavior
- First changed release: unknown within (1.21.1, 1.21.3]
- Runtime validation: not performed

## Paired evidence

Source roots are the manifest artifacts `../../../build/movement-campaign-2026-10-07/ready/1.21.1/mojmap` (A, source manifest `900f956e00f6fc1300bb3d689ea49df2b1a57bcaa54617344ef456d95d47cb48`) and `../../../build/movement-campaign-2026-10-07/ready/1.21.3/mojmap` (B, source manifest `d673bb5464853e3a2a92ed9b1ffe1789d4e62c097bb884f67c336fcbb3b178ce`). Their artifact manifests are `09ced418cbc7530a1d6d8802ee10c05cd576b217a2129655a71f30a2ae38f486` and `0587668e5c70bb06dacf3496a4442f66dc9cdcab23f2350b844f14259cc40dcf`, respectively.

- A `Entity.move(MoverType, Vec3)`, `net/minecraft/world/entity/Entity.java`, lines 598-727, SHA-256 `b81905c7879e2cc5c5063a41d865c4164ad919f156306705be791d1017b99850`: after position/collision processing, lines 705-706 call `tryCheckInsideBlocks()`. Its current-box callback path is at lines 758-766 and `checkInsideBlocks` at lines 1001-1031; the latter visits the entity's current bounding-box cells and invokes `entityInside` for eligible states.
- A player reachability: `LocalPlayer.tick`, `net/minecraft/client/player/LocalPlayer.java`, lines 191-210, SHA-256 `c555e68ac3c63ab9b4f9a9e31933e263b96350a2bc599d11a0de5928bc24b583`; `Player.aiStep`, `net/minecraft/world/entity/player/Player.java`, lines 513-535, SHA-256 `ed32b88c3c7c8418b83db41823520f2b6b0b49a98610306ef26e9681dc925c71`; and `LivingEntity.aiStep`/`travel`, `net/minecraft/world/entity/LivingEntity.java`, lines 2586-2681 and 2091-2217, SHA-256 `324a3eee8496caab57cfaf5101ef576f1ae3c60c40e3857e96f35f3af9a3a0d8`. The ordinary land/air path reaches `Entity.move` through `handleRelativeFrictionAndCalculateMovement`.
- B `Entity.move(MoverType, Vec3)`, `net/minecraft/world/entity/Entity.java`, lines 619-701, SHA-256 `a93719c302a0381a972af75ea360465e2e3551708dd07c34d4d40b7e5173c2b9`: this body has no inside-block callback. `LivingEntity.aiStep`, `net/minecraft/world/entity/LivingEntity.java`, lines 2690-2779, SHA-256 `087390495f4fdfd14b9e12230e7aea4fdc50913bb4d1d91119e72892881cfc52`, calls `applyEffectsFromBlocks()` after travel only under `!level().isClientSide() || isControlledByLocalInstance()`.
- B client guard reachability: `Entity.getControllingPassenger`, `Entity.java`, lines 3065-3067, returns null for the local player itself; `Entity.isControlledByLocalInstance`, lines 3149-3151, checks whether a controlling passenger is a local `Player`, otherwise delegates to `isEffectiveAi`; `Entity.isEffectiveAi`, lines 3158-3159, is false client-side. These members are in the B `Entity.java` source cited above. `LocalPlayer.isLocalPlayer`, `net/minecraft/client/player/LocalPlayer.java`, lines 347-349, SHA-256 `fbd40f1f47adfa66dda9b15188e5dce82af3e8e8d7c3dd0543e602a354ad3fe0`, does not make the local player its own controlling passenger. The normal client player path therefore does not enter B's post-travel callback.
- B player reachability: `LocalPlayer.tick`, `LocalPlayer.java`, lines 189-212, hash above; `Player.aiStep`, `Player.java`, lines 543-556, SHA-256 `a803203e92aa4729d5f5c9b16085b6a43ce51d9907d30996736e9c7c1340de`; and the B `LivingEntity.aiStep`/`travel` path above reach `Entity.move` as in A.
- Both endpoints: `WebBlock.entityInside`, `net/minecraft/world/level/block/WebBlock.java`, lines 26-33, SHA-256 `ece9f8840481748d2d1542990b442cc9a97d670255d693b85f528fa618f48414`, is identical and calls `makeStuckInBlock` with the standard cobweb multipliers (horizontal `0.25`, vertical `0.05`; weaving-effect values are a separate unchanged branch). COBWEB is registered in A `Blocks.java` lines 765-770, hash `538ac50c164484ca4c2367a2a6c07cf2faedcac0c4d5117d8817d36c2d1bde6d`, and B `Blocks.java` lines 701-706, hash `28a66da36ce98800226ec6d8e5fb4904fc7b137322a31f6d7ec822450374e62a`. A `Entity.move` reads and resets `stuckSpeedMultiplier` at lines 611-615; B does so at lines 633-637.

## Source-level difference

For a normal local player on the client, A invokes inside-block callbacks from each eligible `Entity.move` after movement and scans the resulting bounding box. B removed that callback from `Entity.move`; its replacement is after travel and is guarded out for the ordinary local player on the client. With the same cobweb overlap and no weaving effect, A's callback writes the cobweb movement multiplier for the next move, while B's local-client path does not write it. This finding concerns the callback execution gate; it does not claim that B's recorded-path scan runs for the local client.

## Reachability and dependencies

On both sides, `LocalPlayer.tick` reaches `Player.aiStep`, then `LivingEntity.aiStep` → travel → the ordinary land/air movement helper → `Entity.move`. In A, that move reaches the current-box callback if the entity is not removed or `noPhysics`, and the block query's chunk-availability guard passes. In B, the movement method has no callback, and the only base-entity post-travel call is skipped for a client-side local player: the local player has no controlling passenger, while `isEffectiveAi()` is false on the client. `WebBlock.entityInside` and the COBWEB registration are unchanged; the changed callback reachability affects the `stuckSpeedMultiplier` writer, whose consumer is the next `Entity.move` on both sides. The claim is limited to client-side local-player movement and does not infer server movement behavior.

## Consequence and uncertainty

Source-proven: under the stated guards and cobweb callback, A writes the multiplier after the current move and the next move consumes it; the corresponding B local-client callback is not reached. It is a source-level prediction that the next client-predicted movement vector differs when the multiplier would otherwise affect a nonzero movement component. No trajectory or reproduction was run. Other block callbacks, world data, and pair-wide travel dependencies remain outside this finding and keep the pair audit open.

## Handoff

Independent delta: 1.21.3 suppresses the ordinary local-client player's post-travel block-effect callback, while 1.21.1 invokes the current-box callback from `Entity.move`. Related slices: S-BLOCK-CONTACT, S-ENTITY-MOVE and S-TRAVEL. Applicability is limited to client local-player movement and the guards above. The first changed release is unknown within (1.21.1, 1.21.3]. Implementation and testing decisions are deferred.
