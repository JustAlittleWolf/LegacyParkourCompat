# 1.20.3 → 1.20.4: local player swim pitch Y writer

## Exact source identity and integrity

Both sides are Mojmap decompilations. The 1.20.3 mapping-root `decompiled_minecraft/1.20.3/mojmap/.decompile-provenance.json` embeds its per-source SHA-256 map and input pins. Its cache metadata identifies version `1.20.3`; local `client.jar`, `client_mappings.txt`, and `version.json` all match their adjacent `.pin.json` hashes: `bef21938224902dd925e2d89dcfe1d2540656d4cf7a5abd2b93e0402d135c655`, `ad03c803d866062909dd378ed5f1611687dc0ed0a76cc7fd57e968a99d641e5f`, and `05e727aac55594b6014e9714ad38fa16af09f075daf5efa0f5743f2453fa8fc2` respectively.

The 1.20.4 version-root ready marker names `versionId: 1.20.4`, `mapping: mojmap`, source manifest `mojmap.sources.sha256` (SHA-256 `fd3c8668483e5ff208c847474f6cbd3952a909c4aa602ed98b24e3d11a9602a1`), and artifact manifest `artifacts.sha256` (SHA-256 `ee3efc771d264c0bc49d5472abb9763a646f8d221841d0a61201ecd991cb3948`). Both manifest hashes match the ready marker. The artifact manifest pins `1.20.4/client.jar` to `9221ab461a491bf9661cd8e773a5e662aaa43d600fa7970b8c12bbfb0431b838` and `1.20.4/client_mappings.txt` to the same Mojmap mapping bytes used on the 1.20.3 side (`ad03c803d866062909dd378ed5f1611687dc0ed0a76cc7fd57e968a99d641e5f`). Its provenance names exact version `1.20.4`, namespace `mojmap`, and mapping option `mojmap`.

For every source file in the table, the current 1.20.3 bytes match the embedded provenance entry, the current 1.20.4 bytes match its source-manifest entry, and the two full-file hashes are equal:

| Source | SHA-256 (both versions) |
| --- | --- |
| `net/minecraft/client/player/LocalPlayer.java` | `bb5cbfb03656a1866bb77c00431618befe792081223b35db4bd121ecbb151fd5` |
| `net/minecraft/client/player/AbstractClientPlayer.java` | `759f8f03b89dab1f6b65c73e1e25a68ae6a6083cb49b5d2719136edda7edf696` |
| `net/minecraft/world/entity/player/Player.java` | `218da60bc4f8c9279f56eb87e2cf2c0af79562fbe3d818a408429c656efb95dd` |
| `net/minecraft/world/entity/LivingEntity.java` | `f7bc53db24c1798f19f9bd6f6356c86d5e560e9c8e8aac60decaf15cc785e07d` |
| `net/minecraft/world/entity/Entity.java` | `07383522bff169938136638ef8c3244ca511b56ca4266913524f99f9821331b9` |
| `net/minecraft/util/Mth.java` | `f211066a32f5fd2ff3605d0db66f79d7811d7c90ea205caf6b5ce957c0652075` |
| `net/minecraft/core/BlockPos.java` | `a91e96745a49cdb2ec18be2ac7986071d25ed2de2a04c8f779ca41cfcccc29fc` |

## Call path and pitch-derived Y

`LocalPlayer` extends `AbstractClientPlayer`, which extends `Player`; neither local-player class declares `travel`. `LivingEntity.aiStep` calls virtual `this.travel($$9)` at `LivingEntity.java:2600–2604` when there is no controlling passenger. Thus an active local nonpassenger runs the inherited `Player.travel` override.

In both releases, `Player.travel(Vec3)` at `Player.java:1451–1472` first checks `isSwimming() && !isPassenger()`. It obtains `double lookY = this.getLookAngle().y`, then chooses double gain `0.085` if `lookY < -0.2`, otherwise `0.06`. Its OR condition is evaluated left to right: `lookY <= 0.0` first; `this.jumping` second only if the first term is false; only if both are false does it evaluate `level().getBlockState(BlockPos.containing(getX(), getY() + 1.0 - 0.1, getZ())).getFluidState().isEmpty()`. The negation means the condition succeeds when that sampled cell contains fluid. Therefore the above-fluid lookup is skipped for level/downward look and for jumping; it is reached only for upward look while not jumping. If the OR succeeds, the code reads the current `Vec3` and writes `current.add(0.0, (lookY - current.y) * gain, 0.0)` through `setDeltaMovement`. The Y update is a double calculation and occurs before either `super.travel($$0)` branch.

`Entity.getLookAngle()` (`Entity.java:2005–2006`) calls `calculateViewVector(getXRot(), getYRot())` directly, without partial-tick interpolation. That method (`Entity.java:1459–1466`) converts the authoritative current pitch float to radians using `pitch * (float)(Math.PI / 180.0)` and returns Y as the negated pitch sine; yaw affects X/Z but not Y. The float result from `Mth.sin` is promoted to the `Vec3` double Y component.

The relevant trig procedure is unchanged: `Mth` creates `SIN` as a 65,536-element `float[]`, initializing each slot to `(float)Math.sin(index * Math.PI * 2.0 / 65536.0)` (`Mth.java:31–35`). `sin(float)` returns `SIN[(int)(angle * 10430.378F) & 65535]` (`Mth.java:47–49`): float multiplication, cast to int (truncation toward zero), then low-16-bit mask. The swimming writer's look-Y path uses this sine lookup; the adjacent yaw/pitch components of `calculateViewVector` also use the same table through `Mth.sin`/`Mth.cos`.

For the conditional cell, `BlockPos.containing(double,double,double)` passes its x, y, and z arguments in that order to `Mth.floor(double)` (`BlockPos.java:81–83`). The vertical argument remains the source expression `getY() + 1.0 - 0.1`; it is not rewritten as a literal `+ 0.9`. `Mth.floor(double)` first casts to `int`, then subtracts one if the original double is below that cast (`Mth.java:64–67`), so negative coordinates use floor rather than truncation.

After the writer, the flying/nonpassenger branch or ordinary branch dispatches to `super.travel($$0)` (`Player.java:1463–1472`). For an active local player in water, `LivingEntity.travel` passes its `isControlledByLocalInstance()` gate, enters the water branch when `isInWater() && isAffectedByFluids() && !canStandOnFluid(initialFluidState)`, and performs `moveRelative` followed by the inherited `move(MoverType.SELF, getDeltaMovement())` (`LivingEntity.java:2035–2068`). The pitch writer is therefore pre-move velocity shaping, not a pitch assignment or a replacement for the inherited move.

## Pair result

No source change is present in this operation across 1.20.3→1.20.4. `LocalPlayer`, `AbstractClientPlayer`, `Player`, `Entity`, `LivingEntity`, `Mth`, and `BlockPos` are byte-identical across the pair, with both sides' published source hashes verified against their respective provenance/source manifests. The same local nonpassenger swim gates, current-pitch lookup, lookup-table index/cast, above-fluid short-circuit order, vertical velocity expression, and superclass movement order apply in both releases. This bounded comparison yields no independent difference finding.
