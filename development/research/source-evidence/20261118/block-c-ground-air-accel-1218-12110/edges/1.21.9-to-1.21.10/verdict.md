# Independent source audit: 1.21.9 → 1.21.10 local dry ground/air horizontal movement

Verdict: **NO DIFFERENCE** in the bounded ordinary local-player operation. The reachable input, acceleration, ground-friction, yaw transform, and pre-collision move request have the same source behavior in both exact Mojmap outputs. No finding is supported for this slice.

## Boundary

Audited the local-player path from the movement vector already supplied to `LocalPlayer` through input speed modification, `LivingEntity` travel selection, dry ground/air acceleration, block-friction lookup, yaw rotation, velocity accumulation, and the `MoverType.SELF` move request. Reachability assumes a loaded/local-controlled, non-passenger player who can simulate movement, is not in fluid or fall-flying, is not using the abilities-flying branch, and is not sprinting where sprint changes speed. The `shouldDiscardFriction` gate is unchanged. Keyboard/controller sampling, sprint policy, vehicle/rider movement, rotation-device plumbing, vertical gravity, and collision response are outside this verdict; the move request itself is included.

## Exact source identity and hash authentication

Both trees are `decompiled_minecraft/<version>/mojmap/` and contain 6,386 source files.

- **1.21.9:** `.decompile-provenance.json` uses the older unversioned five-key record (`pipeline`, `runtime`, `tools`, `inputs`, `sources`); its literal SHA-256 is `37d3049478585a43ffcace14fc8f2ee814b35ceedd8c9bad8b60adaed9b72c4f`. Its input records pin the 1.21.9 metadata JSON (`3cca801f41f83059092c88f7de3ea74247da4c394f3e672b7a41451151c293d5`), Mojang `client.txt` (`f223e9775abb66b6712c505cb1a73b48d615efebaf43a9f81dd58df9a9b1c7b3`) and client JAR (`40c0a32c46601f71f6a61dae9f126202ded5cc453cfb8da6e42ae73f2a3f5c1b`). The provenance source map’s entries for every cited Java file below match that file’s computed SHA-256.
- **1.21.10:** `mojmap.provenance.json` has `schema=1`, `versionId=1.21.10`, `namespace=mojmap`; literal SHA-256 `9a1db4c050c3caa28a1313f89dae9da3b28feaa5f8afba42cab798b47d7ad77c`. `mojmap.ready.json` has `schema=1`, `status=ready`, `versionId=1.21.10`, `mapping=mojmap`, and SHA-256 `2374d901074b72cd69f0229381d6a11edea7983613c538a2e2e18d9fc73dbeb0`. The ready marker’s source-manifest SHA (`4be26049350c1b314a0b198022cb1e7ab1e745e104047de7a5c7d099a9d7b7e1`), artifact-manifest SHA (`c8210b15012dcc4e109c2f73015b4117abec1f8ab04fdd375dbc11419e9e246c`), and diagnostics SHA (`5642b893edbb01bc1dd57386e013c61572c20f75fd25542877bace1f1f457eb0`) all match their literal files. Its recorded client JAR SHA (`e65ca028bc58da12bf8413066c90ecd4d48c94fb351d9bbd22ecdff5c87f9482`) is present in the artifact manifest. Each cited Java file’s entry in `mojmap.sources.sha256` matches its computed file SHA-256.

| Cited source | 1.21.9 SHA-256 | 1.21.10 SHA-256 |
|---|---|---|
| `net/minecraft/client/player/LocalPlayer.java` | `9c1df00e2f8379b2c19577a3691fee28071d8925210be3d3df928b5352e367e1` | same |
| `net/minecraft/client/player/ClientInput.java` | `597a44339a99f1bce1b081614c7c2984ca03e255b40e9d247675a31b6f810d78` | same |
| `net/minecraft/client/player/KeyboardInput.java` | `d5cb0e93df7f66755172d74e028225012ee33c6e4f0d510a1d8e25ba5497c0a3` | same |
| `net/minecraft/world/entity/player/Player.java` | `af857617b66a5776e63830771360b96f75e21d47d20db08f164a2dbeee801d82` | same |
| `net/minecraft/world/entity/LivingEntity.java` | `b8b49d60769203f7bd5afe4a1bffcdcdbec30be28960324cdc43a2df85a6eb66` | same |
| `net/minecraft/world/entity/Entity.java` | `bd6074b108212bde667877700af1a52649472630c906c57c2a36c96a6fcee74c` | `8361dbb86fe6c975d21f69d008377b6f191669be751150c842517e9d0346fa18` |
| `net/minecraft/world/level/block/Block.java` | `4563cbfb224c213cac1e24baa01a79c91eb4ab79ffc327acff280bd55142db90` | same |
| `net/minecraft/world/level/block/state/BlockBehaviour.java` | `195711b3651969f0202296bc4522b1dbc67d190ad21fa73b00e97b5040528620` | `d1bacd23db38cfdc437f3571d297bef8bd2785beceb0ec632e0ad791d5011350` |
| `net/minecraft/world/level/block/Blocks.java` | `f94e222b2d37a3d9fae6f6d1f83dfa449b05ff847ea366edc5db89b2fe89b139` | same |

The two whole-class hash differences are not themselves movement differences: the exact cited `Entity` slices and the relevant `BlockBehaviour` friction slices were compared and are identical.

## Reachable operation and comparison

- `LocalPlayer.tick()` enters `super.tick()` at `LocalPlayer.java:195`. On the local path, `LocalPlayer.aiStep()` samples the current `ClientInput` vector with `input.tick()` at `:687–706`, then reaches `super.aiStep()` at `:834`. `LocalPlayer.applyInput()` at `:612–614` applies `modifyInput()` to `getMoveVector()` and writes the result to `xxa`/`zza`. `modifyInput()` at `:627–646` retains the same `0.98F` scale, optional `0.2F` item-use scale, sneaking-speed attribute scale, and unit-square speed adjustment in both releases. These entire `LocalPlayer.java` and `ClientInput.java` files are byte-identical. `KeyboardInput.tick()` at `KeyboardInput.java:23–35` also matches (device sampling itself is outside scope).
- `Player.aiStep()` calls `super.aiStep()` at `Player.java:455–466`. `LivingEntity.aiStep()` at `LivingEntity.java:2751–2848` calls dynamic `applyInput()`, builds `Vec3(xxa, yya, zza)`, and reaches `travel()` for the ordinary effective-AI simulation branch. These files are byte-identical. `Player.travel()` at `Player.java:1295–1316` delegates to `super.travel()` for the non-passenger, non-flying dry branch; its relevant source is byte-identical.
- `LivingEntity.travel()` at `LivingEntity.java:2275–2284` chooses `travelInAir()` for dry, non-fall-flying movement. At `:2306–2328`, ground friction is the supporting block’s `getFriction()` when on ground and `1.0F` otherwise; `f * 0.91F` is retained for the post-move horizontal multiplier. `handleRelativeFrictionAndCalculateMovement()` at `:2476–2486` calls `moveRelative(getFrictionInfluencedSpeed(f), input)`, then issues `move(MoverType.SELF, getDeltaMovement())`. After that request, `travelInAir()` keeps the same `shouldDiscardFriction()` branch and applies `x/z * f * 0.91F` when friction is not discarded. `getFrictionInfluencedSpeed()` at `:2520` keeps the ground expression `getSpeed() * (0.21600002F / (f * f * f))` and chooses `getFlyingSpeed()` in air. `Player.getSpeed()` at `Player.java:1339–1342` remains the movement-speed attribute; normal non-flying, non-sprinting `Player.getFlyingSpeed()` at `:1867–1874` remains `0.02F`. All relevant classes are byte-identical.
- `Entity.moveRelative()`/`getInputVector()` at `Entity.java:1585–1601` (1.21.9) and `:1590–1606` (1.21.10) are identical: inputs below squared length `1.0E-7` produce zero; inputs above squared length `1.0` are normalized before acceleration scaling; yaw converts degrees to radians, computes sine then cosine, and transforms horizontal coordinates in the same order (`x*cos - z*sin`, `z*cos + x*sin`). `moveRelative()` adds the transformed vector to existing delta movement before the unchanged `MoverType.SELF` request.
- Supporting-block selection is unchanged: `Entity.getBlockPosBelowThatAffectsMyMovement()` at `:935–957` / `:938–960` delegates to `getOnPos(0.500001F)` with the same supporting-position, fence/wall/gate, and floor fallback logic. `Block.getFriction()` at `Block.java:457–459` remains a direct field read. `BlockBehaviour`’s friction field transfer (`:111`), default `0.6F` and property copy (`:965–1034`), and `.friction(float)` setter (`:1076–1078`) compare equal. `Blocks.java` is byte-identical; its friction registrations at `:2002, :3148, :3329, :4419, :5239` therefore retain the same values and gates.

## Conclusion

For this exact endpoint pair and bounded ordinary local dry ground/air path, no operation order, gate, type, constant, cast, friction provider, input pre-scaling, yaw transform, velocity addition, or actual move request changed. This is no-difference coverage only for the stated slice; it does not establish intermediate-patch boundaries or cover the exclusions above.

## Phase-two comparison against the frozen source report

Comparison input was `c-ground-air-accel-1219-12110-source/report.md`, whose SHA-256 was rechecked as `ceba78d5565e0fd2cfc5145a23c862ac88489f663890d95c449e669674dd36a3` before reading. The report’s conclusion and the source facts it presents for the overlapping path agree with this audit: no release difference in ordinary dry nonvehicle local horizontal input pre-scaling, ground/air speed selection, friction, `Entity.getInputVector` expression, velocity addition, or `MoverType.SELF` move request. Its 1.21.9/1.21.10 source-manifest identities and the source hashes I checked do not conflict.

The author report also cites equal `Vec2`, `Vec3`, and `Mth` source hashes and describes `Vec2.normalized()` plus the sine/cosine helper dependency. Those helper file hashes/bodies were not part of my pre-author blind evidence. `Entity.getInputVector`’s call sites and transform expression were independently compared, but this worker therefore does **not** close the independent audit gate for helper implementation details of normalization and trig. Do not treat the author’s additional evidence as independently audited here; my bounded no-difference conclusion remains partial at that dependency boundary.

The exact frozen verdict prefix SHA-256 before append was `03657c9e62e23347a0304e5fa250a7cf173b8ea98d1803e02a80ea50aea9c8bf`. The initial bytes were verified unchanged after appending this comparison.
Author report SHA-256 after comparison (unchanged): `ceba78d5565e0fd2cfc5145a23c862ac88489f663890d95c449e669674dd36a3`.
