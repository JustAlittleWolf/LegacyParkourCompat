# 1.20.2 → 1.20.4 ground-jump response: bounded source coverage

**Verdict:** NO_DIFF in the paired ordinary ground-jump vector calculation and `Player.jumpFromGround` override. The examined jump power formula, block-factor fallback, jump-boost effect term, vertical replacement, sprint-relative impulse, and impulse flag are textually unchanged. This is one bounded source slice; it does not freeze the release pair or establish a complete local-player jump lifecycle.

## Exact source identity

Both endpoints are the ready Mojmap publications. Root-sidecar `mojmap.provenance.json` SHA-256: 1.20.2 `1DAD9F725F6CB25B6568700FA0294F83B66EA9633B0D62ED354D7BD8B45D4F05`; 1.20.4 `10914D9B7B3FC29DE066CA24F7D905E4D4F0A451A6C81729A2D1981ADDFC1385`. The corresponding `mojmap.ready.json` files report `status=ready`, exact version IDs and `mapping=mojmap`. Root source manifest SHA-256s from those ready records: 1.20.2 `e439ff7e1d3f1d31e01be1edd141f94d917a9dc7fa0de5f69904544cbbe62887`; 1.20.4 `fd3c8668483e5ff208c847474f6cbd3952a909c4aa602ed98b24e3d11a9602a1`.

Every Java file cited below was rehashed from current bytes and matched its version's `mojmap.sources.sha256` entry. File SHA-256s:

| Source | 1.20.2 | 1.20.4 |
|---|---|---|
| `net/minecraft/world/entity/LivingEntity.java` | `5c481da1ffc8684c4b30171c92e61fad751e6a3a708d5bc6486ac9f96ff69828` | `f7bc53db24c1798f19f9bd6f6356c86d5e560e9c8e8aac60decaf15cc785e07d` |
| `net/minecraft/world/entity/player/Player.java` | `25f263692fd6b2a737aa813315022a0caf4e18c3661ba3bd706c6297f909f25c` | `218da60bc4f8c9279f56eb87e2cf2c0af79562fbe3d818a408429c656efb95dd` |
| `net/minecraft/world/entity/Entity.java` | `d7ee49aaea5e862b92508e767562cabc8f10515605e8fb67565c8c8d5c23b01b` | `07383522bff169938136638ef8c3244ca511b56ca4266913524f99f9821331b9` |
| `net/minecraft/client/player/LocalPlayer.java` | `bb5cbfb03656a1866bb77c00431618befe792081223b35db4bd121ecbb151fd5` | same |
| `net/minecraft/client/player/KeyboardInput.java` | `a8064906872955a3520398ab5b2a326552d424f41887d1294aa6a038e2623ff0` | same |
| `net/minecraft/client/player/Input.java` | `b302ffbc45c5f900ea18a4d4af2df6fa0454ea7cb7744a0d249061e5fcb97fbb` | same |
| `net/minecraft/server/network/ServerGamePacketListenerImpl.java` | `8451076ce3a5a4c10e1983d5c576d01ca8bae5e3541211ea9c517e8a73a63644` | `7e2026bc5dc571031a52fa6be38a71cee20e7ccfbb2e77aec48ea4bc7af11527` |
| `net/minecraft/world/level/block/Block.java` | checked declaration at line 394 | checked declaration at line 401 |

## Paired operations

- `LivingEntity.getJumpPower()` (1.20.2 `LivingEntity.java:2003-2005`; 1.20.4 `:2000-2002`) returns `0.42F * getBlockJumpFactor() + getJumpBoostPower()` in both.
- `LivingEntity.getJumpBoostPower()` (old `:2007-2009`; new `:2004-2006`) returns `0.1F * (JUMP effect amplifier + 1.0F)` when `MobEffects.JUMP` exists, else `0.0F`, unchanged. This records the source formula only; it does not establish effect level/amplifier values or resource data.
- `Entity.getBlockJumpFactor()` (old `Entity.java:819-823`; new `:820-824`) reads the block at `blockPosition()` and the block below that affects movement, returning the first factor unless it equals `1.0`, otherwise the lower block's factor. The method is unchanged. `Block.getJumpFactor()` returns stored `jumpFactor` in both (`Block.java:394-396` and `:401-403`). Concrete block values and packaged/configured block data were not examined.
- `LivingEntity.jumpFromGround()` (old `LivingEntity.java:2011-2019`; new `:2008-2016`) preserves existing X/Z velocity, replaces Y with `getJumpPower()`, and when sprinting adds `(-sin(yawRadians)*0.2F, 0, cos(yawRadians)*0.2F)` using yaw converted by `PI/180.0`; it then sets `hasImpulse = true`. Same expression and order in both.
- `Player.jumpFromGround()` (old `Player.java:1439-1448`; new `:1440-1449`) calls `super.jumpFromGround()` then awards the jump stat. The following exhaustion side effect is food production and outside the movement claim. No player-specific vector override delta found.

## Input and caller gates inspected

- `KeyboardInput.tick()` sets `jumping` from the jump key (`KeyboardInput.java:28` both). `LocalPlayer.serverAiStep()` copies input impulses and `input.jumping` into entity fields under `isControlledCamera()` (`LocalPlayer.java:609-620` both). `LocalPlayer.tick()` sends a `ServerboundPlayerInputPacket` with jump state when passenger (`:187-200` both); otherwise it sends position (`sendPosition`).
- The dry ordinary ground-jump call is not directly present in the client source call-site search: exact `jumpFromGround()` call-sites in the two source trees are `LivingEntity.aiStep`'s fluid-affected jump branch, plus the server movement packet handler (and non-player animal callers). The `LivingEntity.aiStep` jump block and no-jump-delay handling are unchanged (`old :2509-2585`; `new :2511-2587`), but its `this.jumping && this.isAffectedByFluids()` outer condition means it does not establish the dry-ground input caller. Its inner ground/fluid gate calls `jumpFromGround()` when `(onGround || inWater && fluidHeight <= threshold) && noJumpDelay == 0`, then sets delay to 10; otherwise it uses fluid-jump methods. For non-fluid cases this branch resets `noJumpDelay` to 0.
- The server movement packet listener invokes `player.jumpFromGround()` when `player.onGround() && !packet.isOnGround() && requestedDy > 0.0` before applying the movement (`ServerGamePacketListenerImpl.java:883-894` old; `:900-911` new). This condition and call order are the same. This is server packet processing evidence, not a claim that historical movement runs on the logical server or a complete client prediction trace.
- `LocalPlayer.aiStep()` also has separate jump handling for flight-toggle, elytra start, flight vertical velocity, and rideable vehicles (`LocalPlayer.java:717-800` both); those paths were inspected to keep this report scoped and were not treated as ordinary ground-jump formula differences.

## Scope and open dependencies

Compared the named paired jump formulas/writers, their direct effect/block-factor inputs, local key-to-input propagation references, and the matching server rising-movement call gate. No delta was found in this slice. Jump Boost amplifier/resource values, concrete block jump-factor values, packaged effect/tag data, and the exact full local-player dry-ground prediction lifecycle remain unexamined dependencies. The wider pair inventories (ordinary travel, support friction, attributes, impulse/push/knockback/explosion, other collision/callback/tick/inventory slices, and any remaining movement operations) are outside this slice and remain open. This is not an accepted finding and not a full-pair coverage freeze.
