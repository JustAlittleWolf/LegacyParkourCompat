# F-001: Minor horizontal collisions no longer always stop local sprinting

- Older version A: 1.17.1
- Newer version B: 1.18.2
- Mechanic / coverage slice IDs: direct player sprint state after collision; T-SPRINT,T-INPUT,T-ENTITY-COLLISION
- Classification: changed player movement behavior
- Confidence: source-confirmed for the bounded collision and next-tick sprint gate
- Applicability: historical player behavior
- First changed release: unknown within (1.17.1, 1.18.2]
- Runtime validation: not performed

## Exact source and artifact identity

A: version metadata 1.17.1, Mojmap; source manifest SHA-256 93270d229acfb751bf56daf1e7be26ce3dcff26b29e94aa157de621405a3463b; 41-entry artifact manifest SHA-256 e52c5dbae7d9663190ccc55a4f9b44a8e0615fb1fbbd8280811df43f70ec8aaa; mapped jar SHA-256 2a2be036174902e447865498741b8c59fa2e090d352d786a8507dccb7c23008c; source root build/movement-campaign-2026-10-07/ready/1.17.1/mojmap.

B: version metadata 1.18.2, Mojmap; source manifest SHA-256 aea0cb9c6fc8f7a46f0eb82b0388ad58a4659f513be6c0a2be06c0df0c1eb07a; 41-entry artifact manifest SHA-256 a1507e4875faee892aca4c59686bbd68933db21a2274559e81eea8a32b021036; mapped jar SHA-256 60a2017dd217b23df8a5ddbebac96fccb58ff747e961974696e0018f6ef12dba; source root build/movement-campaign-2026-10-07/ready/1.18.2/mojmap.

## Paired source evidence and call order

A LocalPlayer#aiStep lines 715-724 stops a sprinting, non-swimming player when the forward/food-or-flight gate fails, any horizontal collision is present, or the player is in water but not underwater. LocalPlayer.java SHA-256 c9a91cb6cb57806bc8d22e5bfe2d97daaf21d5d2a48f34a6e2c53164c61c5812.

B LocalPlayer#aiStep lines 708-718 changes the collision term to horizontalCollision && !minorHorizontalCollision. LocalPlayer.java SHA-256 99c2d18bcd23243afb8f95c5bafb21fb0be7ea04aacbb14fcf7be7ced2c9c095.

The B-only minorHorizontalCollision field is written after Entity#move resolves movement: horizontal collision sets it from LocalPlayer#isHorizontalCollisionMinor(resolvedDelta), otherwise the field is reset to false. B classifier lines 999-1014 rotates xxa/zza by yaw, rejects squared input or resolved horizontal magnitudes below 1.0E-5, computes the angle between the world-space input and resolved displacement, and returns true below 0.13962634F. B Entity#move lines 543-674 and classifier base lines 676-678; Entity.java SHA-256 2228fdaca5793171cbd94038306d571a6ada78ca96f5734efb4cada5b744c10a. A Entity#move lines 534-651 writes horizontalCollision from requested/resolved X/Z deltas and has no minor flag; Entity.java SHA-256 ab28e1fba924771ec048140dfd293ee5a46a7dfe81f71a1a0b1aecc1927232de.

Input path on A: KeyboardInput#tick lines 14-27 maps key pairs to -1, 0 or 1 and applies the slow factor; SHA-256 ea41065c909e53f1a2cc29ecdb6a9a8f9265d2801cd8b95b996e182c318ecd69. Input.java fields, getMoveVector and hasForwardImpulse lines 5-24; SHA-256 367c3a9b0b21d8f106a21fd2c73a3018685dbf07d9c8a9340e2d4c9d73359201.

Input path on B: KeyboardInput#tick lines 22-36 calculates the same discrete impulses and applies 0.3F when slowed; SHA-256 281622f8481654035196a7bc1554d5251c1040518375e3ac6f6439e5ec894a75. Input.java corresponding fields/getters lines 5-24; SHA-256 eb50a4e268ec5ff8423d2805499ca3c7bae33765cb44cfecef808e38fa6de3c3.

In both versions LocalPlayer#aiStep ticks input before the sprint gate. LivingEntity#aiStep calls LocalPlayer#serverAiStep, which copies current input.leftImpulse/forwardImpulse to xxa/zza, then calls travel; Entity#move writes collision flags during that movement. LocalPlayer#aiStep reads the prior movement's collision flag before entering super.aiStep on the next tick. A LivingEntity.java SHA-256 33fd081aadb2b6fdc9ebf487db6da5b38c54f4b8676572790ee2203690d15e6f; B SHA-256 db4168d531caf18f22e3fefd073365e776da4075ce01452bb9f7671d9b458782.

## Concrete reachability

Use forward keyboard input with no lateral input, on ground, with negligible prior horizontal velocity, outside water and away from the world border and other entities. A wall made from a stone block exists in both versions: Blocks.STONE is registered as a plain Block at lines 39-41 in both Blocks.java files, and BlockBehaviour's default shape is Shapes.block() while its collision shape delegates to that shape. A BlockBehaviour.java SHA-256 920896e6bc9d7f8794aba3c5f325d9dffd9c2c9422a0be2e5dd0b7474e980515; B SHA-256 3d82b89f13ed3108e09b64226d98fd673e5fedd9a933afe888ae580db486dd89. A Blocks.java SHA-256 87d72a113a3f8937a6a585ef917a4fd29cc5b335c00a858f6800e38f3c1bf7a8; B SHA-256 cc6b87d2c5897e71e5244b889444ac040e3fa0a139e392523e87fec99805a0f2.

Set yaw so the world-space requested horizontal movement has blocked X magnitude equal to 0.1 times its unblocked Z magnitude. A one-block-high stone wall face clips the X displacement while Z remains. Both LivingEntity constructors set maxUpStep to 0.6F (A line 239; B line 243), so from the support-floor level this one-block wall cannot be cleared by a step candidate. A reports horizontalCollision for a clipped X component; B's horizontalCollision is also true and its classifier compares the rotated input vector with that resolved displacement. The angle is atan(0.1), about 0.0997 radians, below B's 0.13962634F cutoff.

For this concrete two-axis, horizontal-only path, A's Entity#collideBoundingBoxHeuristically selects collideBoundingBoxLegacy; B uses collideWithShapes. Both apply Y first, then the smaller horizontal component, X, and the remaining horizontal component. A's stream Shapes#collide overload and B's iterable overload both visit the one stone wall shape and apply VoxelShape#collide with the same 1.0E-7 cutoff. A Entity.java solver lines 750-873 and Shapes.java lines 225-237, Shapes.java SHA-256 8249d3e406ba400bd812aa19be1fad4f78b451b2add85eea4f4b2209649a9e; B Entity.java lines 777-854 and Shapes.java lines 204-214, Shapes.java SHA-256 ec207e73b8e9de4be5244b889444ac040e3fa0a139e392523e87fec99805a0f2. The scenario excludes border and entity candidates, so those open whole-pair dependencies do not affect this finding-specific path.

Preconditions for the observed sprint-state difference: the player is already sprinting, is not swimming, has forward impulse and sprint permission so the other sprint-stop terms are false, and the prior move produced a nonzero horizontal collision with the angle below B's cutoff. A then clears sprint; B marks the collision minor and keeps sprinting. Health/food production remains vanilla; the direct food/ability value is only a sprint-gate input. Later speed or trajectory effects are inferred and were not simulated.

## Dependency closure and release boundary

The relevant keyboard-to-xxa/zza assignment, tick order, sprint gate, flag writer, B classifier and one existing full-cube wall path are checked on both versions. The complete input inventory and other collision-shape providers remain open for the pair, but are not required for this bounded reachable case. The first changed release is not established; the proven boundary is 1.17.1 versus 1.18.2 only.

## Handoff

Snapshot eligible for blind source review. Pair discovery remains partial; no pair freeze or runtime parity claim.
