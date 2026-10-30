# Local auto-jump finding correction supplement

Applies to immutable source-only draft `194-1102-ordinary31/local-auto-jump.md`, SHA-256 `65ca1b9a69e8823f870ef6523e9f3d8f3df12ecf76406ca98d80db8f2c573f88`, and report `194-1102-ordinary31/report.md`, SHA-256 `16bd0e586560d6ded6d76c5b73a8565a4aefcf7263e7a2c53311d02ab5518b58`.

Replace “obstacle rise ... no greater than 1.2F” / “at most 1.2F” with: “the accepted rise is greater than `0.5F` and no greater than the current ceiling, initialized to `1.2F` and increased by `(jump-boost amplifier + 1) * 0.75F` while jump boost is active.” Exact source: 1.10.2 `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java:856–860, 888–895, 911–915`.

The detector's option field is copied in `sendMovementToServer` under its `isCamera()` gate (`LocalClientPlayerEntity.java:168–229`), so “refreshes/synchronizes the option” should retain that enclosing-call qualification if described precisely.
