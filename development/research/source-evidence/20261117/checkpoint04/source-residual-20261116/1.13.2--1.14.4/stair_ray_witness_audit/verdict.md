# Independent source audit: stair ray witness

**Verdict: ACCEPT, with one immaterial signed-zero correction.** This verdict binds to `build/source-residual-20261116/1.13.2--1.14.4/stair_ray_witness/report.md`, SHA-256 `d6e3b33981df6558a1f60d74e3958f60c09a26a64c6b17b43aa4b32e230dd977`. The bounded parent report cited by it still hashes to `7855256f0ef52b603a9030192c7fd7a6e99f36f593af47522f93e3d3ace71809`.

## Evidence identity and integrity

The canonical source manifests hash to the report's assigned identities: 1.13.2 Ornithe Feather `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`; 1.14.4 Ornithe Feather `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdbb5ea3c9f75bc`. I independently recomputed each of the 21 cited Java source-file SHA-256 values (9 for 1.13.2, 12 for 1.14.4); all match both the report and the corresponding manifest rows. The named ready markers repeat those manifest digests.

## Reachability check

For both endpoints, the reported player position parses to binary64 `-1.1199999749660492` (bits `BFF1EB8518000000`). Standing eye height is `1.62F` (binary32 bits `3FCF5C29`), exactly widened to `1.62000000476837158203125`; adding it to player Y gives `0.5000000298023223876953125` (binary64 bits `3FE0000010000000`). This is strictly above 0.5 and is exactly the upper midpoint of the float-rounding interval described in the parent report.

With `tickDelta = 1.0F`, `getEyePosition` uses current coordinates and adds eye height. `getRotationVector` uses current pitch/yaw; at zero pitch/yaw its Z component is `1.0` and its Y component is `-0.0`. Small correction to the report: X is `+0.0`, not `-0.0`, because `sin(-0.0F)` indexes sine-table element zero, whose value is positive zero. This sign has no effect on the coordinates or witness: adding either signed zero leaves X at `0.5`.

The client pick order is `GameRenderer.pick` -> interaction-manager `getReach()` -> `Entity.rayTrace` -> eye position and rotation vector -> endpoint via `Vec3d.add` -> world/block ray clip. At ordinary survival reach `4.5F`, the ray starts at `(0.5, 0.5000000298023224, -2.0)` and ends at the same X/Y with Z `2.5`. The full-cube block at `(0,0,0)` is in reach. In each `Box` clipper, the Z delta is positive and the north-face candidate uses the box's `minZ = 0`; its parameter is `2/4.5`, while the Y delta is exactly zero. The side-coordinate test and returned hit therefore retain Y `0.5000000298023224`, strictly within the cube. It is a NORTH-face hit (the face at Z=0), with no Y quantization in the clip.

The endpoint paths support the same witness: 1.13.2 calls `rayTrace(..., RayTraceFluidContext.NEVER)` and `World.rayTrace` reaches the block's default unit shape; 1.14.4 calls `rayTrace(..., false)`, which constructs an `OUTLINE`/`NONE` context, and the block state's default outline is the unit cube. Thus an ordinary source-generated player ray reaches the required stair threshold interval in both endpoint clients.

The immaterial X signed-zero correction does not change the witness, face, tick, or raycast order, so no correction to the bounded attainability conclusion is required. This audit makes no runtime or movement-trajectory claim.

**Terminal status: SUCCESS.**