# 1.18.1–1.18.2 `Entity.turn` pitch-setter slice

## Verdict

No source difference in the bounded pitch-setter path. In both releases the first pitch setter receives a `float` sum, and the later setter receives the float result of the same `[-90.0F, 90.0F]` clamp in the same order. A finite, large post-cast pitch delta is accepted by the first setter and then clamped by the second. This slice does not support a pitch-setter change between these endpoints.

## Exact source identity and cited bytes

Both trees are Mojmap exact-release source sets. The ready markers report `status: ready`; their source-manifest digests match the current manifest bytes, and each cited Java file's SHA-256 matches its entry in that manifest. The ready markers' recorded `outputPath` points to an absent historical campaign build directory; the inspected `decompiled_minecraft/<version>/mojmap` files still match the marker's complete source-manifest digest and the cited per-file hashes below.

| Release | Ready marker SHA-256 | Provenance SHA-256 | Source manifest SHA-256 | `Entity.java` SHA-256 |
|---|---|---|---|---|
| 1.18.1 Mojmap | `7a6f3d9d86776e5a95e4722fc2c36f41165c34177f2993978dfaab410641fc81` | `4b181e586201aa2695835e31155c96d8ae3d05b00c9bbb83e99eaa5b2b9db139` | `52aa98450b5cfa55ac2bd2054f108e16df7fa939a3f061991b6a9adb434d8d2d` | `69417675ae8e0a5bd88b7372baea1e73b1f5217154f39446c8ad5c92731437f1` |
| 1.18.2 Mojmap | `d8057d47468c37880de38d658e95070ec3b750305b7856189997604822480946` | `ce14d5dfc2d51cffa7b1512878d908e37c930d414147be24e69439a7f1061ca9` | `aea0cb9c6fc8f7a46f0eb82b0388ad58a4659f513be6c0a2be06c0df0c1eb07a` | `2228fdaca5793171cbd94038306d571a6ada78ca96f5734efb4cada5b744c10a` |

The exact source inputs, artifact hashes, mappings, and tool versions are recorded in each `mojmap.provenance.json`. Support-file hashes for the cited call/read context are: `MouseHandler.java` 1.18.1 `7ab775d1565a883cbfb37dbef44da9412ce3f071475cb23e8f1247785634f819`, 1.18.2 `fa651c8c0e0f75b5ffa816b57e734159b34465b37e288116e9fd4f08da831ca1`; `LivingEntity.java` 1.18.1 `68fc9b15eb31690377afe92acfc783c86945fedec5739d28faacd96da4f9e0e7`, 1.18.2 `db4168d531caf18f22e3fefd073365e776da4075ce01452bb9f7671d9b458782`; `LocalPlayer.java` 1.18.1 `e38d0bbb6e5b9de2a698609b8491d0007406b3becd42ba9b95289f533788c996`, 1.18.2 `99c2d18bcd23243afb8f95c5bafb21fb0be7ea04aacbb14fcf7be7ced2c9c095`; `Player.java` 1.18.1 `1fb9dfb9f700323d77f4196dbc3618d01a65e56438b9cce3ee38fa0b258bd7e2`, 1.18.2 `bf639c1962ff90d69e4569b2b18f6fcf57ac46ef80b19686f0fbc1687fca744a`.

## Compared behavior

- `net/minecraft/world/entity/Entity.java`, `turn(double, double)`: 1.18.1 lines 383–395; 1.18.2 lines 385–397. Both first compute `float pitchDelta = (float) pitchInput * 0.15F`. The first `setXRot` receives `getXRot() + pitchDelta` (float addition). Then yaw is updated. The second `setXRot` receives `Mth.clamp(getXRot(), -90.0F, 90.0F)`. Only after these writes are the old pitch/yaw fields updated and old pitch clamped.
- `Entity.setXRot(float)`: 1.18.1 lines 3014–3020; 1.18.2 lines 3031–3037. The setter rejects non-finite input through `Float.isFinite` and otherwise stores the float unchanged; the setter itself does not clamp.
- A lawful large finite example is `pitchInput = 1.0E20D` with current pitch `0.0F`: the cast remains finite (`1.0E20F`), multiplication by `0.15F` remains finite (about `1.5E19F`), the first setter stores that out-of-range pitch, and the later identical clamp writes `90.0F`. Inputs whose cast or multiplication becomes infinity are rejected by the first setter; they are not evidence for finite-large-delta handling.
- Ordinary mouse caller context only: `client/MouseHandler.java`, 1.18.1 and 1.18.2 lines 237–245 call `minecraft.player.turn($$7, $$8 * $$13)` when a player exists. This establishes the ordinary caller shape, not scheduling or rendering behavior.

## Next active-local Elytra pitch read

`client/player/LocalPlayer.java` declares `LocalPlayer extends AbstractClientPlayer` (1.18.1 line 82; 1.18.2 line 83); `AbstractClientPlayer extends Player`, and `Player extends LivingEntity` in both releases. `LocalPlayer.tick()` calls `super.tick()` when its chunk is loaded (1.18.1 lines 187–190; 1.18.2 lines 183–186). In active fall-flying `LivingEntity.travel(Vec3)`, the first pitch consumption is through `getLookAngle()`: `Entity.getLookAngle()` calls `calculateViewVector(this.getXRot(), this.getYRot())` (1.18.1 lines 1813 and 1311; 1.18.2 lines 1816 and 1314). The branch then reads pitch explicitly in the identical expression: `float $$15 = this.getXRot() * (float) (Math.PI / 180.0)` (1.18.1 lines 2070–2081; 1.18.2 lines 2074–2085). This follows the locally active player type chain into the inherited movement path; it is not a view/render-only read. The stored pitch and degree-to-radian float conversion are unchanged.

The following Elytra cosine computation is a separate source delta and is not part of the setter result: 1.18.1 uses `float $$19 = Mth.cos($$15)` and a float assignment for the subsequent product (lines 2081–2083); 1.18.2 uses `double $$19 = Math.cos($$15)` and keeps that product in double (lines 2085–2087). Do not attribute that precision change to the `Entity.turn` pitch setter or its clamp.

