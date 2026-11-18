# Independent audit: 1.18 → 1.18.1 Entity pitch setter and Elytra read

## Verdict

**No source difference in the assigned slice.** In both exact Mojmap source sets, `Entity.turn(double, double)`, `Entity.setXRot(float)`, the local player's tick dispatch, `Player.travel`, and the active Elytra branch are byte-identical. There is no 1.18.1 pitch-setter or Elytra-read delta to implement from this evidence. This is a bounded operation audit, not a full-pair movement-coverage freeze or runtime-parity claim.

## Exact source identity and integrity

The local read-only source sets are `decompiled_minecraft/1.18/mojmap/` and `decompiled_minecraft/1.18.1/mojmap/`. Both ready records identify exact release IDs and `mojmap`; both say `status: ready`. They were prepared in run `prep-1.18-1.18.1-mojmap-2026-10-09-r1` with Mojang official client mappings and Vineflower 1.12.0 / Java 25.0.3+9-LTS. Provenance records exact metadata IDs and client/mapping input pins.

| Release | Client SHA-256 | Official client mappings SHA-256 | Ready record SHA-256 | Provenance SHA-256 | Source manifest SHA-256 | Artifact manifest SHA-256 |
|---|---|---|---|---|---|---|
| 1.18 | `381e090ef22584526113bcccf26ae38a310a22094d14c2d0dec72b71fdf65aaa` | `b4c7d514328050fa09ac04818d21e4be0b58e75c6b0847e56ce09f6637b8877b` | `4537edf0e88c88631215beb1c14f1d67f5a5d6d021c982c0af549b7b27856e5f` | `e642f075b25d1b6e74bad05edacd740421c70f5d8e0c2a38767523cf864124c5` | `a43c61223ddedbdd8ede4d2daf6a750a58cbba324b30c025429ee1030cfc0d78` | `7ca2b4da88c215e52924d277b257c4631f9664f5def2fb2b7ef25479ec330234` |
| 1.18.1 | `737c28a56c8980fcd8574465c61538ebde8f511bdd035c69d0cbe27953f892aa` | `a7a4109255dfb74b9552b339482f0714bb0e7e7143d1059b0bf62a526d2c2a84` | `7a6f3d9d86776e5a95e4722fc2c36f41165c34177f2993978dfaab410641fc81` | `4b181e586201aa2695835e31155c96d8ae3d05b00c9bbb83e99eaa5b2b9db139` | `52aa98450b5cfa55ac2bd2054f108e16df7fa939a3f061991b6a9adb434d8d2d` | `b8237a01cdebe7d784caae113886f42565677ce6897ea960a49223cfec4c00ec` |

I recomputed SHA-256 for both ready, provenance, source-manifest, artifact-manifest, diagnostic, and success-log files. Each ready record's manifest/provenance/diagnostic hashes agree with the corresponding sidecars. For each release, the selected source bodies also match their entries in `mojmap.sources.sha256`; hashes are the same across the pair:

- `net/minecraft/world/entity/Entity.java`: `69417675ae8e0a5bd88b7372baea1e73b1f5217154f39446c8ad5c92731437f1`
- `net/minecraft/world/entity/LivingEntity.java`: `68fc9b15eb31690377afe92acfc783c86945fedec5739d28faacd96da4f9e0e7`
- `net/minecraft/world/entity/player/Player.java`: `1fb9dfb9f700323d77f4196dbc3618d01a65e56438b9cce3ee38fa0b258bd7e2`
- `net/minecraft/client/player/LocalPlayer.java`: `e38d0bbb6e5b9de2a698609b8491d0007406b3becd42ba9b95289f533788c996`

The pair's diagnostic summaries say decompilation succeeded and exact metadata IDs were verified; they explicitly disclaim method-body review. This audit performed that method review. No source generation or mutation was performed.

## Compared behavior

Both `Entity.java` files have identical bytes. In `Entity.turn` (lines 383–395), parameters are `double`; the second argument (pitch delta) is cast to `float` before multiplication by `0.15F`, yielding a `float` delta. It calls `setXRot(getXRot() + delta)` (float addition), updates yaw similarly, then clamps the resulting current pitch to `[-90.0F, 90.0F]`. It next adds the same float delta to `xRotO` and clamps that old pitch too. Thus this method cannot return with a current pitch over 360 degrees.

In the same identical source, `setXRot(float)` (lines 3014–3020) checks only `Float.isFinite`; for finite inputs it assigns the float directly to `xRot`. It does not clamp, wrap, or normalize. Therefore the lawful, exact-width witness `entity.setXRot((float)361.0)` supplies exactly `361.0F`, which is finite and greater than 360, and the vanilla setter retains it. This witness uses the public vanilla setter directly; it is not a claim that `turn` leaves such a value after its own clamp.

The witness can reach a real next active Elytra movement read under ordinary valid player state: the local player is in a loaded chunk; has fall-flying flag set, is airborne and not a passenger, has enabled Elytra equipped, has no levitation, is not swimming or using the separate abilities-flying branch, and no intervening action changes pitch. `LocalPlayer.tick` calls `super.tick`; `LivingEntity.tick` calls `aiStep`; `aiStep` calls `updateFallFlying()` and then virtual `travel(...)`. `Player.travel` dispatches to `super.travel(...)` when abilities-flying does not apply. `LivingEntity.travel` then reaches its `isFallFlying()` branch (lines 2070–2100 in each release).

At that live read, the Elytra branch first calls `getLookAngle()` and separately computes `float radians = getXRot() * (float)(Math.PI / 180.0)`. `getLookAngle()` passes current pitch to `calculateViewVector`; there the radian intermediates and sine/cosine values are `float`, while `Vec3` components are `double`. The branch uses the direct pitch radians for `Mth.cos`, the negative-pitch test, and `Mth.sin`, then updates velocity and moves the player. The actual downstream pitch read is therefore present and reachable, not merely a setter-only or rendering observation. These operations and their order match byte-for-byte in 1.18 and 1.18.1.

## Exact references

Line numbers are one-based in the generated files:

- Both releases, `net/minecraft/world/entity/Entity.java`: `turn` 383–395; `setXRot` 3010–3020; `calculateViewVector` 1311–1319; `getLookAngle` 1813–1815.
- Both releases, `net/minecraft/world/entity/LivingEntity.java`: `travel` Elytra branch 2070–2100; tick-to-movement call in `aiStep` 2543–2549; fall-flight state validation 2586–2611.
- Both releases, `net/minecraft/world/entity/player/Player.java`: `travel` dispatch 1409–1438.
- Both releases, `net/minecraft/client/player/LocalPlayer.java`: `tick` and `super.tick()` dispatch 188–195.

The result is **no difference for the audited pitch setter/cast/order and active Elytra movement-read path**. Acceptance, whole-pair source coverage, integration, and runtime parity remain separate.


## Phase-one supplement: ordinary mouse path witness

This appended trace addresses the ordinary `MouseHandler` → `Entity.turn` path. It does not rewrite the earlier frozen audit. The earlier direct `Entity.setXRot(361.0F)` witness is **not adopted as the ordinary-input witness**; use this mouse-path trace for that claim.

Both releases have identical `MouseHandler.java` bytes (`7ab775d1565a883cbfb37dbef44da9412ce3f071475cb23e8f1247785634f819`) and `Option.java` bytes (`3c026480166c989da7ea0c5bdd1f0bea297aee6e993ad89331fb7d470397e182`), each matching its source manifest. `MouseHandler.onMove` (171–204) accumulates the finite event-coordinate difference into `accumulatedDY` when the window is active and mouse grabbed, then invokes `turnPlayer`. `turnPlayer` (208–245) computes sensitivity scale in `double` arithmetic, chooses the ordinary unsmoothed/non-scoped scale, and calls `player.turn($$7, $$8 * $$13)`. In both releases, `Option.SENSITIVITY` is a `ProgressOption` with min `0.0`, max `1.0`, and no step quantization (Option.java 219–228); `ProgressOption.toValue` clamps the slider fraction and value to that range (ProgressOption.java 61–75). `Options.sensitivity` is `double` in both versions (Options.java 1.18 line 66; 1.18.1 line 67). The complete Options files differ elsewhere, so this claim is limited to the cited field and option definitions.

Use an ordinary finite mouse event with `accumulatedDY = 600.0`, sensitivity `1.0`, `smoothCamera == false`, non-scoped first-person (or another non-scoped view), and `invertYMouse == false`. This is a source-feasible finite callback delta; it is not a claim about a particular device or a runtime-observed event. Java numeric widths, exactly following the source expressions:

1. The double sensitivity value `1.0` multiplies the binary32 literal `0.6F` promoted to double, then adds binary32 `0.2F` promoted to double: base `0.8000000268220901` (`0x3FE99999A8000000`).
2. `$$3 = $$2 * $$2 * $$2` and `$$4 = $$3 * 8.0` remain `double`; `$$4 = 4.09600041198732` (`0x4010624DEE978D5F`). `$$8 = accumulatedDY * $$4` is double, `2457.60024719239`.
3. MouseHandler passes `$$8 * 1` as a double pitch argument to `Entity.turn`. The turn method casts that argument to float first: `2457.60034179688F` (binary32 bits `0x4519999B`). It then multiplies by `0.15F`, producing the binary32 finite pitch delta `368.640075683594F` (bits `0x43B851EE`), strictly greater than 360.
4. Starting at pitch `0.0F`, the first `setXRot(getXRot() + delta)` therefore calls the ordinary finite setter with `368.640075683594F`, which passes `Float.isFinite` and stores it. The later `setXRot(Mth.clamp(getXRot(), -90.0F, 90.0F))` in the same `turn` call stores `90.0F` before the method returns. The post-`turn` live state is clamped; the >360 witness is specifically the finite post-cast value seen by the first setter.

This route is compatible with the real active Elytra movement path already described above: on the next eligible local-player tick, the `LivingEntity.travel` Elytra branch reads current pitch after the clamp, so it reads `90.0F`. The source call order, widths, setter invocation and clamp match between 1.18 and 1.18.1. This supplements the no-difference verdict; it does not establish runtime parity.

## Phase-two independent exact comparison

**Reviewed after the phase-one source witness was frozen.** Author artifact: `c-raw-pitch-setter-118-1181-source/report.md`, SHA-256 `7c5ddca016665cdf72132e3767a8c431c1bc1e0f8a1b31b80afac4c474825c8f` (verified before reading). Phase-one frozen verdict identity before this addendum: SHA-256 `2443c595fafca16ffac5a8dfe7515a9918d2947e0fd786e64acf46342730c169`.

**Comparison result:** the author's direct-source verdict agrees with my independently frozen conclusion: no 1.18→1.18.1 change in `Entity.turn` pitch cast/setter/clamp or active Elytra pitch consumption. The author independently reports matching pairwise bytes for `Entity.java`, `LivingEntity.java`, `Player.java`, and `MouseHandler.java`; these match the source hashes in my phase-one record. Their bounded report intentionally stops short of ordinary input scaling and tick scheduling.

My pre-open ordinary-input supplement closes those specifically identified gaps. Its source-feasible MouseHandler witness uses max lawful slider sensitivity and a finite `600.0` accumulated vertical delta: the double turn argument is `2457.60024719239`; the `Entity.turn` cast yields `2457.60034179688F`; the exact float multiplication yields `368.640075683594F` (finite and `>360`) at the first `setXRot` call; the same `turn` call then clamps current pitch to `90.0F`. The author's independent `1000.0` direct pitch-delta example yielding `150.0F` is correct but is not a counterexample to this higher ordinary mouse-path witness. On the next eligible live local-player tick, the active Elytra movement read observes the clamped `90.0F` state. My earlier direct global `setXRot(361.0F)` witness remains unadopted for ordinary input.

**Signed audit disposition:** bounded source agreement, with the ordinary input and next active movement-call chain covered by my pre-open evidence. No product edit, runtime execution, acceptance transfer, or full-pair coverage claim is made.
