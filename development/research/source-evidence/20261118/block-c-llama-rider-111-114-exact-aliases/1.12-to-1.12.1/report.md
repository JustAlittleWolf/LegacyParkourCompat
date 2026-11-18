# 1.12 → 1.12.1 llama-to-player Y placement (blind source intermediate)

## Scope and result

Compared the sole direct local `PlayerEntity` position write in the ordinary adult vanilla Llama rider callback. No difference was found between Minecraft 1.12 and 1.12.1 in this operation, its relevant virtual accessors, or its mounted-player callback gates. This is a bounded no-difference result, not pair coverage or a runtime-parity claim. Horizontal offsets and all other mount/vehicle behavior are excluded.

## Exact sources and immutable identities

Both sides use the canonical local `decompiled_minecraft/<release>/ornithe-feather/` tree. Mapping family: Ornithe Feather Gen2, exact stable build 2 for each game version; source namespace is `net.minecraft...`.

- 1.12: `net.ornithemc:feather-gen2:1.12+build.2`; mapping artifact SHA-256 `73ce7d6d0a281db15fa030ae09e836387dae1172fdffcaf3e2d1a4840830b719`; pinned mapping metadata SHA-256 `5b4555cb0473f14b4bde13b7f60f7f979dc4f8e149187f5696ae8283e437fac4`; exact client artifact SHA-256 `59e84cf6acc3912e1705ab05c722485afb3ec33343eef4756d8c16edfe6411ca`.
- 1.12.1: `net.ornithemc:feather-gen2:1.12.1+build.2`; mapping artifact SHA-256 `f94febd2167d6091b4c157aef0a85c8791e6f8fb27b883e27a82a68c7b02b9c7`; pinned mapping metadata SHA-256 `1893deb3a1a74c6f2054876e2b8512817f744188aa8a9d8aafdba8525c0e896b`; exact client artifact SHA-256 `19331fb29bf8b8d5ed764677c94cca9d66481e10104f660eb555894a4300cf22`.

Each SHA above matches the corresponding exact-version `.decompile-provenance.json` input pin. The exact source-tree provenance manifest hashes are 1.12 `27515e5f4c71e7db2f035a81716507128d77466c75380ad1ef7d140c06401467` and 1.12.1 `0ff5246e8d29c04554cc9ec93ae9b4abef539652325b6c0ac822851d7186a9c8`. For each release, SHA-256 was recomputed from every cited source file and matched its provenance `sources` entry. Relevant source hashes (identical on both sides unless stated):

- `net/minecraft/entity/living/mob/passive/animal/LlamaEntity.java`: `487c7209a69391f1a19dce5fbdf34d609d2e9bc53c47b04a225e9c0d0c93cf7e`
- `net/minecraft/entity/Entity.java`: `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a`
- `net/minecraft/entity/living/player/PlayerEntity.java`: 1.12 `6a9d5f420b13625195b149c0d9468cf1284d5eb7dc1b2a46cf01b952a27ee063`; 1.12.1 `4dab93ea3178a28c616947004ecdc514e6542edca8c62ef83f686ccf6b2ce640`
- `net/minecraft/world/World.java`: 1.12 `f7ab7a34d456e720e7ee4f34fbd0eb10f7f96151596c8c615ab2edef05a884d8`; 1.12.1 `d182122a9b052f57803672c21b0941cb7e7d41ed756cd0a4ea8c0858684c8259`

## Compared behavior and gates

In both releases, `net.minecraft.entity.living.mob.passive.animal.LlamaEntity` constructor (lines 60–63) sets dimensions to `(0.9F, 1.87F)`. Its override `getMountHeight()` (lines 153–156) returns `this.height * 0.67`. Since `height` is `float` and `0.67` is a `double` literal, the multiplication is in double precision after widening the float height. For this ordinary adult instance the mount-height term is the double product of its current `1.87F` height and `0.67d`.

`LlamaEntity.updateRiderPositon(Entity rider)` (lines 143–150) first gates on `this.hasPassenger(rider)`. If true, it calculates yaw-related `float` values `f = cos(bodyYaw * (float)(PI / 180.0))` and `g = sin(bodyYaw * (float)(PI / 180.0))`, then calls `rider.setPosition(...)`. The Y argument, preserving Java left-to-right addition, is `this.y + this.getMountHeight() + rider.getRideHeight()`. The method signature `Entity.setPosition(double, double, double)` makes this Y a `double`; it is not separately rounded or cast between additions.

For a `PlayerEntity`, virtual dispatch resolves `getRideHeight()` to the player override in `net.minecraft.entity.living.player.PlayerEntity` (lines 942–945), which returns double `-0.35`. Thus the direct Y result is `(mountY + (widenedMountHeightFloat * 0.67d)) + (-0.35d)` in that operation order. The player offset is not the inherited `Entity.getRideHeight()` default of `0.0`.

The reachable client callback chain is also unchanged. `World` (lines 1427–1433) sends an in-chunk entity with `isRiding()` through virtual `rideTick()` instead of ordinary `tick()`. `PlayerEntity.rideTick()` (lines 374–385) only detaches for the `!world.isClient && isSneaking() && isRiding()` case; on a logical client it continues through `LivingEntity` to `Entity.rideTick()`. In `Entity.rideTick()` (lines 1582–1594), a removed current mount triggers `stopRiding()`; otherwise rider velocity is zeroed, `tick()` runs, and the still-riding check gates the call `entity.updateRiderPositon(this)`. That callback is virtual and therefore invokes the Llama override. The Llama override's own `hasPassenger(rider)` condition is the final direct-position-write gate. No player-specific `updateRiderPositon` override intervenes.

## Exact compared source locations

All locations below were compared in both releases; these relevant bodies are text-identical across the pair except where noted as whole-file hashes above:

- `net/minecraft/entity/living/mob/passive/animal/LlamaEntity.java`: constructor 60–63; override `updateRiderPositon` 143–150; override `getMountHeight` 153–156.
- `net/minecraft/entity/living/player/PlayerEntity.java`: `rideTick` 374–385; `getRideHeight` 942–945.
- `net/minecraft/entity/Entity.java`: `rideTick` 1582–1595; default `updateRiderPositon` 1597–1601; default `getRideHeight` 1606–1608; `startRiding` gates 1614–1635.
- `net/minecraft/world/World.java`: mounted entity tick dispatch 1427–1433.

The `startRiding` eligibility method is unchanged and not a claimed difference: it rejects a mount-chain cycle, then requires `force` or `(canRide && canAddPassenger)`, stops an existing ride, assigns the mount, and adds the passenger. No horizontal placement, vehicle motion, non-player rider, server movement, or broader mount eligibility behavior is claimed here.
