# Blind source comparison: 1.8.9 → 1.9

## Evidence identity

Compared the canonical local `ornithe-feather` decompilations for exact release IDs **1.8.9** and **1.9**. The 1.8.9 adjacent ready record identifies version `1.8.9`, Feather gen2 build.2 merged-v2 plus Tiny mappings, and the client SHA-256; its source manifest hashes the examined files. The 1.9 hidden `.decompile-provenance.json` records the exact 1.9 metadata input, client archive, Feather gen2 1.9 build.2 mapping artifact, decompiler/tool identity, and source-file hashes. For both releases, actual hashes below were recomputed from the source files and match the corresponding local manifest entries.

| Input | Exact local identity |
|---|---|
| 1.8.9 metadata | `decompiled_minecraft/1.8.9/ornithe-feather.ready.json`, SHA-256 `fe0ec792349ae43c91d0a30f23660c94b2f5c8d31a29643f73ed0de367110b95`; adjacent `.provenance.json`, SHA-256 `ba066fc8f1c7ceb9819d2106338d9e9f2c2b96eb5a8329eb40e171b1f9889185` |
| 1.8.9 source manifest | `decompiled_minecraft/1.8.9/ornithe-feather.sources.sha256`, SHA-256 `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004` |
| 1.8.9 client / mapping inputs | Client archive SHA-256 `14f0d96d1a56fb4f5c3b2233d00699525893fe5ce3dcf181e7de59120595d298`; Feather `1.8.9+build.2` merged-v2 SHA-256 `303c4530c79a593b828bd778a97d3577e67f99d6a2c50760e5f9bec6fb32a9da`; Tiny SHA-256 `de2023ea2cca9921402fbfcfe6e475f41da4932ea6dbc609e35c505b76a32c63` |
| 1.9 metadata | `decompiled_minecraft/1.9/ornithe-feather/.decompile-provenance.json`, SHA-256 `8daacb7af8f210e716ee4b34c43b8a5d6f6b4b19831d2fd725cbe16ecf330a1a` |
| 1.9 release / mapping inputs | Mojang exact metadata `.../1.9.json` SHA-256 `8d6f1e7f3a99f1236914b61e64eeade955ba21e8150563457142d3981aeee688`; client archive SHA-256 `0273b5c79d6b96799c6480ae61366d6b1d042655970de87f77f1a3f1ebea9307`; Feather `1.9+build.2` merged-v2 SHA-256 `a4e09a92eab0fbf6ef4668e295db1949e9d52c42f145d56b6dfbf3a10c2c7c2a` |

Examined source SHA-256 values (all actual file hashes match the listed manifests):

| Source | 1.8.9 SHA-256 | 1.9 SHA-256 |
|---|---|---|
| `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `1762b116e6b06d682b7daaa0fc8cce39b0ff455b3db8cf79dab03ac74f6c4053` | `04d6196e7e17595c0aed59b28bc1730f8be5ea3eccdb1f971e9e2492dfadfe06` |
| `net/minecraft/client/entity/living/player/KeyboardInput.java` | `a5e5b2033322f8867cd845e4095cea7a82888f6382cbfafefc559cf9ba3a76dd` | `7be11425906be051c83e275f359816546e4677b16d212156380e8d2e9258654a` |
| `net/minecraft/entity/Entity.java` | `d4c10932cb5bb1067a5a58be4e1bb1b42bdde3b6fc893d88178cc07435a1696b` | `74d3ac34d7e6355c8fdcdd777dcd95ba9ef4626718c8772c1b58d0e28419e42a` |
| `net/minecraft/entity/living/LivingEntity.java` | `082831c6578e3a70fa6cea5b90bc3eefc26678259b66334470de22b90b5b0e4e` | `5b5936656aacc64a4d75db7b370985f9456ac96076331da047bd246f76a705fb` |
| `net/minecraft/entity/living/player/PlayerEntity.java` | `e66cb294fc93118148a444bbafdf4dd57cbf66a23d69b1e8892cefccc690ab88` | `edbb46236d1e42630e600e4afcb434eb6e9adecbfc4ac7e15710a5dea50010b1` |

## Independent operation findings

### Local sprint timeout and double-tap window

- **1.8.9:** `LocalClientPlayerEntity.setSprinting` initializes `sprintTimer` to `600` when sprinting and `0` when stopping (`LocalClientPlayerEntity.java:349–352`). Its `mobTick` reads the timer at the top of the local player tick, decrements while positive, and calls `setSprinting(false)` when the decrement reaches zero (`LocalClientPlayerEntity.java:488–494`). The same tick decrements `doubleTapSprintTime` when positive (`:496–498`), clears it while using an item (`:540–544`), and sets it to `7` on the first qualifying grounded forward tap (`:551–564`).
- **1.9:** `setSprinting` instead resets `sprintTimer` to `0` for either transition (`LocalClientPlayerEntity.java:402–405`). `mobTick` increments it unconditionally (`:600–604`) and no `sprintTimer` read remains in either release's `net/minecraft` source tree; the 1.9 field no longer expires sprint after 600 ticks. The separate double-tap timer still decrements when positive and retains the item-use clear and `7`-tick initialization/read (`:602–604`, `:646–650`, `:658–671`).
- **Local tick path:** `LocalClientPlayerEntity.tick` calls `super.tick` (`1.8.9:105–107`; `1.9:141–143`); `LivingEntity.tick` dispatches `this.mobTick()` (`1.8.9:1296`; `1.9:1538`), reaching this local override. The timeout read/decrement therefore runs in normal local player ticking. Consequence: ordinary local sprinting in 1.8.9 auto-stops when the 600-count timer expires; that expiry path is absent in 1.9. This describes source behavior, not a runtime test.

### Ability flight fall-distance reset

- **1.8.9:** `PlayerEntity.moveRelative` enters its ability-flight branch for `abilities.flying && vehicle == null` (`PlayerEntity.java:1279–1291`). It saves vertical velocity and `speedInAir`, sets air speed from fly speed times `2` while sprinting (otherwise `1`), calls `super.moveRelative`, multiplies the saved vertical velocity by `0.6`, and restores `speedInAir`. This branch does not clear `fallDistance` or flying flag 7.
- **1.9:** The corresponding branch is gated by `abilities.flying && !isRiding()` (`PlayerEntity.java:1366–1380`). After `super.moveRelative`, vertical-velocity scaling and `speedInAir` restoration, it sets `fallDistance = 0.0F`, then clears flag 7 (`:1374–1378`).
- **Movement meaning:** Each non-riding ability-flight movement call now clears accumulated fall distance after the normal relative movement and vertical damping, and clears the fall-flying flag. The reset is not moved to the start of travel or made conditional on vertical velocity; its gate is the ability-flight branch. The corresponding 1.8.9 branch has no such state writes.

### Flying sneak horizontal input rescaling

- **Both releases:** `KeyboardInput.tick` samples the directional keys, then, if sneaking, stores each movement axis as `(float)(axis * 0.3)` (`1.8.9:32–37`; `1.9:44–49`). The `0.3` literal is unsuffixed, and each product is cast back to `float`.
- **1.8.9 local player tick:** With `abilities.flying && isCamera()`, sneaking subtracts `getFlySpeed() * 3.0F` from `velocityY`; jumping adds the same amount (`LocalClientPlayerEntity.java:596–604`). There is no horizontal-input undo. The input that reaches movement remains sneak-scaled.
- **1.9 local player tick:** Under the same flying-camera gate, the sneaking branch first assigns each axis `(float)(axis / 0.3)`, then subtracts `getFlySpeed() * 3.0F` from `velocityY`; the jumping vertical addition follows (`LocalClientPlayerEntity.java:710–720`). This division uses the unsuffixed `0.3` literal and casts each quotient to `float`. It follows `input.tick()` and the non-riding item-use `* 0.2F` slowdown (`:645–650`), so it undoes the keyboard's sneak factor in ordinary use but leaves that earlier item-use factor in place.
- **Normalization and movement meaning:** Downstream `Entity.updateVelocity` computes the squared input length as float, returns below `1.0E-4F`, takes the square root, raises lengths below `1.0F` to `1.0F`, divides the movement scale by that length, then scales and rotates both axes (`1.8.9:844–859`; `1.9:943–958`). Thus sneaking while camera-flying in 1.8.9 supplies reduced horizontal axes to this normalizer, whereas 1.9 restores approximately the pre-sneak axes first (with float-rounding from the two casts); diagonal input then follows the same length normalization. This changes local creative/ability-flight horizontal movement. Vertical sneak descent remains the same `flySpeed * 3.0F` subtraction in the compared code.

## Bounds

These are three source-level operations only. Source coverage here does not establish a full-pair tick inventory, an exact intermediate patch boundary, runtime parity, or a result for server-side movement. Keyboard/device handling is cited only to establish the player movement value passed into the historical movement routine. No tests or game launch were run.

