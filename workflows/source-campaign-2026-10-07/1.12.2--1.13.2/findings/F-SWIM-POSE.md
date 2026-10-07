# F-SWIM-POSE — 1.13.2 adds swimming pose and dimensions

- Status: provisional source candidate; artifact-integrity hold and collision/dimension closure pending
- A source: `net/minecraft/entity/living/player/PlayerEntity.java`, updatePlayerPose lines 291-315; SHA-256 `e4e0fdbe07a7d0a0ae4a70cbb6739a287c9d045a4a12b409895c220b5d91fe1e`.
- B source: same class, updatePlayerPose starts at line 334; SHA-256 `4ed22f6c5a3c55d67eed782070ac722201df4d624adbc90779f1fd29c2876633`.
- Difference: B has swimming pose handling and assigns compact dimensions (width 0.6, height 0.6) after a world collision-fit check; A has no swimming pose branch. PlayerEntity.updateSwimming also suppresses swimming while flying in B.
- Reachability/dependencies: swimming transition is sourced in Entity base tick and state is consulted by PlayerEntity pose update. Dimension changes affect getShape, eye-height and collision consumers.
- Direct consequence: B can move through a different collision volume while swimming, subject to the fit query.
- Limits: exact end line of B pose method, all dimension writers, eye-height behavior, and world collision query semantics remain to be inventoried. Do not treat this file as a closed collision finding.


- Integrity hold: source owner/ops reported derived mapped-JAR cache replacement during reproducibility; source tree hashes remain unchanged. Do not accept/freeze this finding until canonical artifact repair and fresh verification.
