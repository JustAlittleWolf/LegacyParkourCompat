# Independent source audit: dry item-use horizontal input

## Bounded verdict

For the assigned ordinary dry, unmounted, local-camera, nonsprinting item-use input slice, 1.14 and 1.14.1 have **no source difference found** in keyboard input production, the item-use input slowdown, the local input-to-speed-field copy, or the pre-travel horizontal vector handoff. This is a bounded no-difference result, not full-pair movement coverage.

The client-player gate itself is `isUsingItem() && !hasVehicle()`; it contains no medium or sprint predicate. “Dry” and “nonsprinting” are assignment preconditions, not conditions encoded by this particular transform. The active-item state is treated as a supplied input; item-use mechanics were not reviewed.

## Exact source identity

Windows host, PowerShell 7.6.9. Sources were read-only under `decompiled_minecraft/<version>/yarn/`.

| Version | Client input | Yarn mapping input | Provenance manifest SHA-256 |
|---|---|---|---|
| 1.14 | `https://piston-data.mojang.com/v1/objects/7a762a59345c13af7d87111207a93f5a8607f6c0/client.jar` — `93907cbf0655aa5f10d049efa4d745f4250eecc5d0d5d1268046eec3144f323a` | Yarn `1.14+build.21`; artifact SHA-256 `0233edadf36a28099f6bb86e920c423b728ab5d75618da9046d92d287f12de64` | `EA70A955A12F377CD3B9E8D5F346F4625B044D55DA726B6BC472405AE91F4341` (matches assignment) |
| 1.14.1 | `https://piston-data.mojang.com/v1/objects/55ba86ddcbc3579397f41910463ffd4056e1e523/client.jar` — `7194d1326cf796f62ac55f3cb56f851c232895639ae27510f3207d5a73f91814` | Yarn `1.14.1+build.10`; artifact SHA-256 `f9462af25135abeb22cbdec829cc8618b1a8dd902f17259abfbe7399a222a630` | `308537C5ADEC3104C5CA243EB5010554F00978635ED74F0D7801C804DD4D4907` (matches assignment) |

The exact source-file SHA-256 values below were checked against each tree’s `sources` entries in its provenance manifest and against the files read.

| Source file | 1.14 SHA-256 | 1.14.1 SHA-256 |
|---|---|---|
| `net/minecraft/client/input/KeyboardInput.java` | `ade8c1b46b4b25888d326a963f645dd4fe82f53ef0797c2e499fe37e9bb47aa0` | `ade8c1b46b4b25888d326a963f645dd4fe82f53ef0797c2e499fe37e9bb47aa0` |
| `net/minecraft/client/input/Input.java` | `abb37170563ca28cafcf20b27b75036b4ee99cb0ec9775702beef6f97cebc351` | `abb37170563ca28cafcf20b27b75036b4ee99cb0ec9775702beef6f97cebc351` |
| `net/minecraft/client/network/ClientPlayerEntity.java` | `92d0f97a9293f7bd57d49af00b70f13d78fc8b2202e7ed67edcfed917e0071be` | `6af7e75e80619ad77ce2c84692136b0a1d562a44d0e7910dcc3f6040b2dc9933` |
| `net/minecraft/entity/player/PlayerEntity.java` | `ebda4ad25a265f8c85e88345efcce753b78e248a29a9b910c5cfd311a4a69319` | `ebda4ad25a265f8c85e88345efcce753b78e248a29a9b910c5cfd311a4a69319` |
| `net/minecraft/entity/LivingEntity.java` | `8889e22779b9124998931bc97ce8b0d183d335c32a9bcbb089e49de65882bae6` | `331e2d7dfa936a6f7197910ba374c4a60b479c8565ff7c64a4d6a9e1e8b52dda` |
| `net/minecraft/entity/Entity.java` | `f52483d5e1434114b81b1c353e89f246d1557bda7a95400832f5650b89cc744a` | `ce429699f2d8f0a54f4be516783a474c6c7808572950164508ceed0baf68ed5b` |

## Compared behavior and evidence

- `client/input/KeyboardInput.java`, `KeyboardInput.tick`, lines 13–25, is identical in both versions. It samples opposing direction keys into `0.0F` or signed `1`, sets jump/sneak, and when `!bl2 && (sneaking || bl)` multiplies sideways then forward by `(float)(value * 0.3)`. `Input.java` lines 5–20 is also identical: the horizontal inputs are public floats and `getMovementInput()` returns `new Vec2f(movementSideways, movementForward)`.
- `client/network/ClientPlayerEntity.java`, `tickMovement`, lines 613–625, is identical. It snapshots jump/sneak and the forward threshold, calls `input.tick(bl4, isSpectator())`, notifies the tutorial manager, then—if item use is active and there is no vehicle—mutates `input.movementSideways *= 0.2F` followed by `input.movementForward *= 0.2F`, and sets `field_3935 = 0`. Thus this slowdown follows keyboard/sneak input processing and acts on the input object’s fields in place. The method later delegates at line 762.
- The local copy occurs in `ClientPlayerEntity.tickNewAi`, lines 588–600: after `super.tickNewAi()`, the `isCamera()` branch assigns the current `input.movementSideways` and `input.movementForward` to `sidewaysSpeed` and `forwardSpeed`. Both versions match. The delegation chain is `ClientPlayerEntity.tickMovement` → `PlayerEntity.tickMovement` → `LivingEntity.tickMovement`; `PlayerEntity.java` lines 475–492 confirms its superclass delegation. In `LivingEntity.java` lines 2168–2178, `tickNewAi()` is called before travel when the entity can move voluntarily and is not in the `cannotMove()` branch, so the local copy sees the already item-scaled fields on this ordinary path.
- `LivingEntity.java` lines 2197–2205, identical in both versions, then multiplies `sidewaysSpeed` and `forwardSpeed` separately by `0.98F`, and passes `new Vec3d(sidewaysSpeed, upwardSpeed, forwardSpeed)` to `travel`. `Entity.java`, `updateVelocity` and `movementInputToVelocity`, lines 1009–1023, is identical in this slice: the supplied movement vector is zeroed below the squared-length threshold, normalized only when its squared length exceeds `1.0`, scaled, and its horizontal components rotated by yaw. No air/ground acceleration body was compared.

## Limits

This audit covers only the specified input-to-pre-travel dependency. It does not assess item mechanics, sprint logic or timers, pose selection, camera setters, acceleration branches, fluids, flight, climbing, jumping, collision, support, block providers, or other tick areas. No implementation, tests, builds, game/runtime launch, network access, or source regeneration was performed.

## Phase 2 independent audit of author report

Author report reviewed: `C:\Users\Wolfi\.codex\visualizations\2026\11\17\01a1eb7b-b9ff-7a20-8686-0295dde20f26\c-stream-horizontal-114-1165\1.14--1.14.1\author\report.md`; verified before reading at 7204 bytes, SHA-256 `6085815A0E80CEBF3A873A10DA6FC7FB49A3FA30A73B115EDAFDB79EF930D964`.

**Verdict: QUALIFIED ACCEPT.** The report’s scoped item-use input and pre-travel handoff claims agree with this independent trace: the keyboard tick processes its input before `ClientPlayerEntity.tickMovement` applies the in-place `0.2F` multiplier to sideways then forward input; the later local-camera `tickNewAi` copy supplies those scaled fields to `sidewaysSpeed` and `forwardSpeed`; inherited travel then applies `0.98F` damping and receives the corresponding horizontal components in its `Vec3d` input. These examined operations match between 1.14 and 1.14.1. The report’s broader air/ground acceleration, slipperiness/drag and horizontal-travel conclusions exceed this audit’s assigned scope and are not accepted or rejected here.