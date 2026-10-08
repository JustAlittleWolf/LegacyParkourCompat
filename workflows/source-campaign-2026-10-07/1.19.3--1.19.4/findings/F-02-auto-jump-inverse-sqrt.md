# F-02: auto-jump probe uses a different inverse-square-root approximation

- Older version A: 1.19.3
- Newer version B: 1.19.4
- Mechanic / coverage slice IDs: INV-TICK / I-TICK-AUTO-JUMP; INV-COLLISION / I-COLLISION-AUTO-JUMP-PROBE
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: historical player behavior
- First changed release: unknown within (1.19.3, 1.19.4]
- Runtime validation: not performed

## Paired evidence

All paths are repository-relative; hashes match the verified per-version source manifests.

- A `build/movement-campaign-2026-10-07/ready/1.19.3/mojmap/net/minecraft/client/player/LocalPlayer.java`, `LocalPlayer#updateAutoJump(float,float)Z`, lines 911–1007, SHA-256 `64b670ee323d195b3928fb8ea629c26d2560e75a17379a12c376a7bc686d5479`: computes the probe factor with `Mth.fastInvSqrt($$6)` and uses it in the forward vector and probe distance.
- B `.../1.19.4/mojmap/net/minecraft/client/player/LocalPlayer.java`, corresponding method, lines 891–987, SHA-256 `8e7da18f42d09fbb994f522c2b0e65fcb2bb83cabb21024360299d44d9674c58`: calls `Mth.invSqrt($$6)` at line 911.
- A `.../1.19.3/mojmap/net/minecraft/util/Mth.java`, `Mth#fastInvSqrt(F)F`, lines 495–501, SHA-256 `f5b2738a7145303f8598b9876c0f6950e853110f594fc4cffa633ab7bab98d3f`: bit-hack estimate followed by a Newton step.
- B `.../1.19.4/mojmap/net/minecraft/util/Mth.java`, `Mth#invSqrt(F)F`, lines 387–389, SHA-256 `201d17ea024637036761c2703e407d4ab1dc7651a1f4b00a857c09803754c797`: delegates to `org.joml.Math.invsqrt(float)`.
- Dependency artifact: `build/movement-campaign-2026-10-07/artifacts/1.19.4/libraries/org/joml/joml/1.10.5/joml-1.10.5.jar`, SHA-256 `cac9f22f83a7aa33eebda73c16ff5261e3cb4911b6bafc4f4c79ea486099d0c9a`; class bytecode for `org.joml.Math.invsqrt(float)` computes `1.0F / (float)Math.sqrt(input)`.
- A/B `LocalPlayer#move(MoverType,Vec3)` calls `updateAutoJump` after superclass movement using actual horizontal displacement; A lines 900–905 and B lines 880–885, same respective LocalPlayer hashes. `canAutoJump()` gates are unchanged: auto-jump enabled, timer elapsed, on ground, not staying on the ground surface, not passenger, moving input, and block jump factor at least 1 (A lines 1027–1035; B lines 1007–1015).

## Source-level difference

The methods normalize a horizontal direction and derive a forward probe distance before querying collision boxes and shapes. A uses the approximate `Mth.fastInvSqrt(float)` bit-hack plus a Newton step. B delegates to JOML's reciprocal square root implementation, which takes `Math.sqrt` and rounds the result to `float` before float division. The factor is used in probe endpoints and its reciprocal in search distance. The A `BlockPos(double,double,double)` conversion was replaced by B `BlockPos.containing(double,double,double)`; both call `Mth.floor` for each coordinate, so that replacement is checked as equivalent for this slice.

## Reachability and dependencies

`LocalPlayer#move` -> `updateAutoJump` -> inverse-square-root factor -> forward probe endpoints/distance -> world collision-box and block-shape queries. When the probe accepts a gap, it writes `autoJumpTime = 1`; local tick consumes that timer to schedule a jump. The gates above bound reachability. Collision providers and shape dependencies remain open in the broader collision inventory.

## Consequence and uncertainty

Source proves the normalization algorithm changed in a reachable auto-jump collision probe. This can change probe endpoints or distance and therefore a threshold/query result. It does not prove a particular gap changes outcome or that a player follows a different trajectory; collision-provider inventory remains incomplete.

## Handoff

Separate from sprint air speed in F-01. Release introduction is unknown within the compared interval. Collision-query dependency closure remains pending in the run ledger.
