# Stair side-face ray threshold: exact source witness

## Scope and identity

This closes the bounded open gate in `stair_placement_threshold/report.md` (SHA-256 `7855256f0ef52b603a9030192c7fd7a6e99f36f593af47522f93e3d3ace71809`). Sources are exclusively the canonical Ornithe Feather Gen2 sets: 1.13.2 source-manifest SHA-256 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`; 1.14.4 source-manifest SHA-256 `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdbb5ea3c9f75bc`. Each ready marker binds that manifest digest, and every consulted source-file SHA-256 below was checked against its endpoint manifest. Paths in the tables are relative to `decompiled_minecraft/<version>/ornithe-feather/`.

## Exact witness and verdict

**Verdict: yes, source-level reachable in both endpoint client ray paths.** Choose an ordinary player camera, standing pose, `tickDelta = 1.0F`, yaw `0.0F`, pitch `0.0F`, player X `0.5`, Z `-2.0`, and player Y `-1.1199999749660492` (binary64 bits `BFF1EB8518000000`). Target an otherwise empty-world full-cube block at block position `(0, 0, 0)` with the ray reaching its north side; the normal interaction-manager reach is `4.5F` in survival.

The player eye height is the binary32 value `1.62F` (bits `3FCF5C29`), widened exactly to binary64 `1.62000000476837158203125` (shortest round-trip decimal `1.6200000047683716`). At the chosen position, the source expression `player.y + player.getEyeHeight()` evaluates to exact binary64 endpoint `0.5000000298023223876953125` (bits `3FE0000010000000`; shortest round-trip decimal `0.5000000298023224`), the inclusive upper bound in the prior gate. The default, standing-pose eye-height branches return `1.62F` at both endpoints.

For these exact zero yaw/pitch inputs, the source angle math produces look vector `(-0.0, -0.0, 1.0)`: `sin(0)` indexes the initialized sine-table entry zero and `cos(0)` indexes the entry equal to one. The ray endpoint therefore has the same Y as its eye origin. In each endpoint's box clipper, the north-face candidate has `e = to.y - from.y = 0`; its side-coordinate expression is `from.y + o * e`, and the returned hit Y adds `g * e` to `from.y`. Both remain exactly the eye-origin double. The Y coordinate is strictly inside the unit cube, so the horizontal (north) face is hit at local Y `0.5000000298023224`; there is no Y quantization in the ray clip.

The ordinary reach is enough: the ray starts two blocks before the north plane and the manager reports `4.5F`. The 1.13.2 client picks with `Entity.rayTrace(..., RayTraceFluidContext.NEVER)`, whose world clip reaches the block's ordinary shape. The 1.14.4 client picks with `Entity.rayTrace(..., FluidHandling.NONE)` and `ShapeType.OUTLINE`; the default block outline shape is the unit cube. Thus this is an actual source-generated block hit from player camera/pose/rotation/position inputs, not an injected `HitResult`.

At the already-audited placement conversion, 1.13.2 subtracts clicked block Y `0` and casts this hit Y to float; the exact midpoint rounds to `0.5F` by ties-to-even. 1.14.4 retains the hit Y as double, so its strict `> 0.5` stair gate takes the opposite branch. This establishes only the candidate placement-state consequence (BOTTOM vs TOP for the same horizontal-face ray); it does not establish a movement trajectory or runtime run.

## Exact consulted source files

| 1.13.2 source path | SHA-256 |
|---|---|
| `net/minecraft/client/render/GameRenderer.java` (`pick`, line 283) | `587be722d7600bcf0ebc363770d1b954411739330c81c555b09a90a452f14f6d` |
| `net/minecraft/client/ClientPlayerInteractionManager.java` (`getReach`, line 260) | `892bdb99e894b538daeeb7fd9ae6f9a0970b854f38f51b4b39c446e905692359` |
| `net/minecraft/entity/Entity.java` (rotation, eye position, ray, lines 1239–1277) | `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269` |
| `net/minecraft/entity/living/player/PlayerEntity.java` (`getEyeHeight`, line 1859) | `4ed22f6c5a3c55d67eed782070ac722201df4d624adbc90779f1fd29c2876633` |
| `net/minecraft/util/math/MathHelper.java` (`sin`/`cos`, lines 24–28) | `2211b0cb3da7d8789a4a4df96509b95e6b1be8b222be2f530e7bd0695573bd69` |
| `net/minecraft/util/math/Vec3d.java` (`add`, line 50) | `6941a43536e8aefaa435a4e5f1731b0fa26a9d96503d1f4d07de8950c297f3c5` |
| `net/minecraft/util/math/Box.java` (clip at line 252; helper at 295) | `0f573d00cc10386f94cf51671c62815979973053c9e67a806e9fee98e55a4d8d` |
| `net/minecraft/world/World.java` (`rayTrace`, line 553) | `0023de30da608db73c6c6188334d2bb19039d86fe4d6f5e4ad79d732a5d2cd1a` |
| `net/minecraft/block/Block.java` (default unit shape line 388; ray trace line 542) | `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4` |

| 1.14.4 source path | SHA-256 |
|---|---|
| `net/minecraft/client/render/GameRenderer.java` (`pick`, line 277) | `4877f2d1ab62acb9c31a008eb7997691c412183e01cc17cd8308d5ff8a8a4254` |
| `net/minecraft/client/ClientPlayerInteractionManager.java` (`getReach`, line 227) | `86707c46294dae65c878e43da56b9330ab46e7069d8119e179e485baafb2d0de` |
| `net/minecraft/entity/Entity.java` (rotation, eye position, ray, lines 1221–1272) | `7315a496c195da767de9d4936d3adb6efc3c419dc0f0e95d6f32781b0da1ba55` |
| `net/minecraft/entity/living/player/PlayerEntity.java` (default-pose eye height, line 1802) | `2614deb3b50d11f6cdf54d9e308703bf49cb53b72646b05253857e9557d657df` |
| `net/minecraft/util/math/MathHelper.java` (`sin`/`cos`, lines 24–28) | `e39d5dfc69c17a9032be086d7d4d14cc63525962a0ff83615b1befdcca599843` |
| `net/minecraft/util/math/Vec3d.java` (`add`, line 50) | `b69b990b768e52d6fac6b3ed82f60e7526e78d0837c868a61a4171ebca21040b` |
| `net/minecraft/util/math/Box.java` (clip at line 263; helper at 306) | `b68667118ba729fc18fefa3b9de9060184f9d71f53a4bf10a8b7215863ff8b6e` |
| `net/minecraft/world/BlockView.java` (context and shape ray clip, lines 35–59) | `817fba3b8ae05ad4901f836b250e5bf44f45787fe10d00d89dc6dc5a950ef518` |
| `net/minecraft/world/RayCastContext.java` (`OUTLINE`/`NONE`, lines 51–70) | `f60acaf56972f50af1be35bd1d7f3239b798a9b123a12257befe981977b7b315` |
| `net/minecraft/block/Block.java` (default unit shape, line 371) | `1f8ec3628f08e196c2793e91ca0b94feacea6bc3939e7fb770f0682a6e66f2c5` |
| `net/minecraft/block/state/BlockState.java` (shape delegation, line 193) | `6c6703c7c2f7203a7507d8a621bc83e1dad613b32089394f5af4fbd8636b165f` |


