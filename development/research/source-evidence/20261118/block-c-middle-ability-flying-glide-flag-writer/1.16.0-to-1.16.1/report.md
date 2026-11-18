# 1.16.0 → 1.16.1 ability-flight / Elytra trace

## Result

No source change was found in the assigned slice. The canonical `1.16` source tree is the exact 1.16.0 release (version metadata ID `1.16`); comparing it with exact 1.16.1 Mojmap sources shows byte-identical `LocalPlayer.java`, `LivingEntity.java`, `Player.java`, and `ElytraItem.java`. In both releases, a locally controlled player with lawful `abilities.mayfly` and already-active `abilities.flying`, airborne and not a passenger, can enter `Player.travel`'s ability-flight branch while glide flag 7 is set. With a valid chest Elytra and dry-air conditions, the nested `LivingEntity.travel` glide branch performs its real `move`; after it returns, `Player.travel` clears flag 7 and resets fall distance in that order. The pair therefore has no boundary here; this report makes no claim about other tick slices or the pair's full coverage.

## Trace and source ranges

All ranges below are identical in 1.16 and 1.16.1 (Mojmap; root-relative paths under `decompiled_minecraft/<version>/mojmap/`).

- `client/player/LocalPlayer.java:730-757`: for `abilities.mayfly`, the always-flying game mode restores flight; otherwise jump toggles `abilities.flying`. The Elytra launch-input path explicitly requires `!abilities.flying`, `!isPassenger()`, and `!onClimbable()`, then checks chest-slot `Items.ELYTRA` and `ElytraItem.isFlyEnabled` before `tryToStartFallFlying`. This assignment is the already-active glide case, so it does not depend on that new-launch path.
- `world/entity/LivingEntity.java:2452-2458,2476-2493`: `aiStep` calls `updateFallFlying()` before virtual `travel(...)`. Flag 7 remains active only when airborne, not a passenger, without Levitation, and with a fly-enabled Elytra in the chest slot; otherwise the method clears it. `isFallFlying()` reads shared flag 7 (`:2875-2877`).
- `world/entity/player/Player.java:1380-1407`: `travel` first checks `abilities.flying && !isPassenger()`. In that branch it saves vertical velocity and `flyingSpeed`, sets flight speed, calls `super.travel(vec3)`, restores vertical velocity as `i * 0.6` and the saved speed, then sets `fallDistance = 0.0F` and calls `setSharedFlag(7, false)`. The clear is directly after the real superclass travel and is unconditional inside this branch; it is not an `Entity.move` post-hook.
- `world/entity/LivingEntity.java:1912-1920,1977-2019`: in dry air, active `isFallFlying()` chooses the glide branch. It may set `fallDistance = 1.0F` when pre-move Y velocity is greater than `-0.5`, applies Elytra velocity updates, and calls `move(MoverType.SELF, this.getDeltaMovement())` at `:2006`. Its own glide clear at `:2017-2019` is gated by `onGround && !level.isClientSide`, so it is not the client-side clear in this scenario. The outer `Player.travel` then resets fall distance separately from clearing flag 7.
- `world/entity/player/Player.java:1513-1527` and `world/item/ElytraItem.java:17-18`: the start helper requires airborne, not already gliding, not in water, and no Levitation, then checks a fly-enabled chest Elytra; fly-enabled means damage is less than max damage minus one. `startFallFlying()` sets flag 7. These predicates also match byte-for-byte in both releases.

## Source readiness and provenance

- 1.16.0: canonical directory `decompiled_minecraft/1.16/mojmap`; ready marker `mojmap.ready.json` SHA-256 `5b6cb24f95e83b856c53f646d6477f2e46dae2276737dee56dd07c5bd745e4a2`; provenance `mojmap.provenance.json` SHA-256 `371d50521a183f4a3f42fac2d18021f228e0a4e6b2e8a9838c2805391709f2a9`; source manifest `mojmap.sources.sha256` SHA-256 `e3c23e94ac985c25dec0c768aed17b443ba4823662cf1e152235c99756ae5d4a`. Ready metadata says status `ready`, version ID `1.16`, Mojmap, 3,572 source files; provenance pins the exact client SHA-256 `00740ef4475d4cd0a1d7064f89602f759c45ff9afe29da9b0ee0b9037ebb2a71` and Mojmap artifact SHA-256 `53d46abe149aadd367be8789207c53a7cee46ba8382e5bfd60247eb22984119d`.
- 1.16.1: canonical directory `decompiled_minecraft/1.16.1/mojmap`; ready marker SHA-256 `c19d707f7d6622733b5189599ff77aa7999cc11a3d900f29d150f9fab203ffcb`; provenance SHA-256 `a6ff065188a400ec891980cdf2e6c0c238bf5e05168a2f69b29c6cecc2214b98`; source manifest SHA-256 `76fdd121070c2012f48cbdf5cf0c78490116afc94117c30b0858eedcd6ab9754`. Ready metadata says status `ready`, version ID `1.16.1`, Mojmap, 3,572 source files; pinned client SHA-256 `b4831e7b63b10588ff06ea86322108d916c536446f3aea3ae988b7fe3e1f66ef`.

For both releases, the source-manifest entries for the four cited Java files match the computed file SHA-256 values exactly:

| Source file | SHA-256 (both releases) |
|---|---|
| `net/minecraft/client/player/LocalPlayer.java` | `ee20523adfdb82f3753fa43312af170f34c4f4aa78570631c61bc8c0820dfb8b` |
| `net/minecraft/world/entity/LivingEntity.java` | `b6ea2b9b037b79fc3ea6e1f6bcd2c4c1d89d4a96315968a8f247641f6c335f26` |
| `net/minecraft/world/entity/player/Player.java` | `a849960d47bf00a2cddfb6636c78c518532a11967ba08829f19c5ebced08681e` |
| `net/minecraft/world/item/ElytraItem.java` | `d20a679b8822eaa57e5216ce14ed755c1f86a160eb5dad0dc9e3d8f4b5ea018d` |

Readiness is source publication evidence, not pair acceptance. In particular, the 1.16 provenance says no source-pair review was performed; this bounded trace does not close that gate. No generation, network access, tests, builds, or runtime actions were performed.