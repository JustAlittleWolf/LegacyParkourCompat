# Blind source report: 1.12 → 1.12.1

## Bounded scope and result

Compared the exact local **Ornithe Feather** decompilations for only these two ordinary local-player operations:

1. First movement while fall-flying is already active with a valid default chest-slot Elytra; trace the look vector and yaw source.
2. Ordinary player dismount from a non-boat, non-`HorseBaseEntity` mount, including the direct position-write route. Collision/clearance feasibility is left conditional for the paired geometry lane (B), as assigned.

**Result:** no difference found in the compared operation bodies, gates, ordering, types, or routing. This is bounded slice coverage only; it does not freeze or accept the full 1.12→1.12.1 pair, prove the intermediate 1.12.1 patch boundary, or establish runtime parity.

## Glide: already-active Elytra first movement

Exact refs in both releases:

- `net/minecraft/entity/living/LivingEntity.java`, `tick` travel phase / `flyingTick`: lines 1842–1847, 1854–1873.
- `net/minecraft/entity/living/player/PlayerEntity.java`, `moveRelative`: lines 1385–1404.
- `net/minecraft/entity/living/LivingEntity.java`, `moveRelative`: lines 1425–1464.
- `net/minecraft/entity/Entity.java`, `getLookVector`: lines 1690–1692; raw rotation-vector math: lines 1239–1245.
- `net/minecraft/entity/living/LivingEntity.java`, interpolated `getRotationVector(float)`: lines 1960–1968.

Travel decays sideways and forward input by `0.98F`, rotation input by `0.9F`, calls `flyingTick()`, then calls `moveRelative(sidewaysSpeed, verticalSpeed, forwardSpeed)`. For a non-ability-flying player, `PlayerEntity.moveRelative` delegates to `LivingEntity.moveRelative`. The glide branch is gated by locally controlled/logical-side movement, the water and lava conditions, then `isFallFlying()`.

On entry to that branch, `fallDistance` becomes `1.0F` when `velocityY > -0.5`. It obtains `vec3d` from `getLookVector()` and converts the current `pitch` to radians. The following calculations use that look vector for lift/dive and horizontal speed steering before applying drag (`0.99F`, `0.98F`, `0.99F`) and calling `move(SELF, velocityX, velocityY, velocityZ)`. The movement procedure and order are the same in both versions.

**Yaw source:** `Entity.getLookVector()` directly invokes the protected final rotation-vector overload with `this.pitch, this.yaw`. This is the player's current `yaw` field. It does not use head yaw, body yaw, previous yaw, or tick-delta interpolation. The similarly named `LivingEntity.getRotationVector(float)` does use head yaw (and last-head-yaw interpolation for non-1.0 tick delta), but the glide call does not invoke that method; `getLookVector()` calls the raw `(pitch, yaw)` overload. That distinction is present and identical in both releases.

`flyingTick()` validates active flag, airborne/not-riding state, and usable chest Elytra. Only server-side code updates flag 7 there; this report holds that the flag is already active for the requested first-move case and does not infer the activation packet or its timing.

## Ordinary non-boat/non-horse dismount

Exact refs in both releases:

- `net/minecraft/world/World.java`, entity tick routing: lines 1408–1433.
- `net/minecraft/entity/living/player/PlayerEntity.java`, `rideTick`: lines 374–397.
- `net/minecraft/entity/living/LivingEntity.java`, `stopRiding`: lines 1907–1914; `dismountRider`: lines 1321–1387.
- `net/minecraft/entity/Entity.java`, `stopRiding`: lines 1648–1654; `teleport`: lines 2181–2185; `setPositionAndAngles`: lines 1122–1135; `setPosition`: lines 298–305.

`World.tickEntity` routes a riding entity to `rideTick()` (and sets previous coordinates and last yaw/pitch before that phase). `PlayerEntity.rideTick()` enters its dismount branch only when the world is not client-side, the player is sneaking, and the player is riding. It calls `stopRiding()` and clears sneaking before the normal `super.rideTick()` path.

`LivingEntity.stopRiding()` saves the old mount, detaches via `Entity.stopRiding()`, then calls `dismountRider(oldMount)` only when a mount was detached and the world is not client-side. Therefore this direct dismount-placement routine is reached in the authoritative server rider tick, not a client historical-physics tick. The local client inherits the same player routine but does not enter that server-only sneaking branch. This is evidence about vanilla routing, not a proposal to replay it on a server.

For a non-boat/non-`HorseBaseEntity` mount, `dismountRider` starts with mount X/Z and `mount.getShape().minY + mount.height`. If movement direction is non-null, it visits its fixed ordered offset array, deriving candidate X/Z from the dismounting entity's floored coordinates plus `0.5`. Source gates then test collision-box clearance and full-block/water support; a clear candidate with a full block at the entity's current Y calls `teleport(u, this.y + 1.0, v)` and returns. Other supported/one-block-up candidates update the final coordinates. If no early return occurs, the routine calls `teleport(l,m,n)`. Whether any candidate is feasible or clear is intentionally not evaluated here.

The route is `LivingEntity.dismountRider` → `Entity.teleport`, not a direct `setPosition` call. `teleport` sets `teleported`, then calls `setPositionAndAngles(x,y,z,yaw,pitch)`, preserving current yaw/pitch while resetting current, last, and previous position coordinates; that method calls `setPosition`, which rebuilds the entity shape. Finally `teleport` calls `world.tickEntity(this, false)` synchronously. The method bodies and call route are identical in both versions.

## Local source identity and hashes

Both source roots are `decompiled_minecraft/<version>/ornithe-feather/`. Their hidden canonical manifests are `.decompile-provenance.json`; each manifest's recorded SHA-256 for every relevant Java source below matched the bytes read locally. The canonical mappings are Feather Gen2 build 2 for the respective exact release.

Canonical manifest SHA-256:

- 1.12: `27515e5f4c71e7db2f035a81716507128d77466c75380ad1ef7d140c06401467`
- 1.12.1: `0ff5246e8d29c04554cc9ec93ae9b4abef539652325b6c0ac822851d7186a9c8`

Relevant source SHA-256 (actual local file; each matches the same version's provenance `sources` entry):

| Source | 1.12 | 1.12.1 |
|---|---|---|
| `net/minecraft/world/World.java` | `f7ab7a34d456e720e7ee4f34fbd0eb10f7f96151596c8c615ab2edef05a884d8` | `d182122a9b052f57803672c21b0941cb7e7d41ed756cd0a4ea8c0858684c8259` |
| `net/minecraft/entity/Entity.java` | `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a` | `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a` |
| `net/minecraft/entity/living/LivingEntity.java` | `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6` | `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6` |
| `net/minecraft/entity/living/player/PlayerEntity.java` | `6a9d5f420b13625195b149c0d9468cf1284d5eb7dc1b2a46cf01b952a27ee063` | `4dab93ea3178a28c616947004ecdc514e6542edca8c62ef83f686ccf6b2ce640` |
| `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `d7237b8b9a68525bbfec6af1c234d2aafb1e4aa7d52059331d1d243313d6d7d1` | `d7237b8b9a68525bbfec6af1c234d2aafb1e4aa7d52059331d1d243313d6d7d1` |

Although `PlayerEntity.java` differs elsewhere, the selected `rideTick` excerpt (374–397) has matching normalized UTF-8/LF SHA-256 `189a9427c03bcc32037cd159a9036875ce6c206ec2fad7eac175fada0653897b`; its `moveRelative` excerpt (1385–1404) has matching normalized UTF-8/LF SHA-256 `3ad36639775e587367b48c7e3cdf990c57fdfba9b0e0bedd8783cd8f262722c9`.

The adjacent local cache pin sidecars also resolve the exact mapping inputs recorded by provenance:

- `.minecraft-decompile-cache/mapping-metadata/ornithe-feather-1.12.json.pin.json` SHA-256 `76a51caf10558eb4e29fbcda24e60d38fb70b338d7af721e55696700b6467aa0`; metadata bytes match `5b4555cb0473f14b4bde13b7f60f7f979dc4f8e149187f5696ae8283e437fac4`.
- `.minecraft-decompile-cache/yarn/feather-gen2-1.12+build.2-mergedv2.jar.pin.json` SHA-256 `2ba8c39f328203c18f8ff59136c416d29505fbdf1dd4bc745e1d4e264d52d24d`; artifact bytes match `73ce7d6d0a281db15fa030ae09e836387dae1172fdffcaf3e2d1a4840830b719`.
- `.minecraft-decompile-cache/mapping-metadata/ornithe-feather-1.12.1.json.pin.json` SHA-256 `dcc069db752e88c44e9561d219f3e71745d273151d723ce3287e5bdb0467546f`; metadata bytes match `1893deb3a1a74c6f2054876e2b8512817f744188aa8a9d8aafdba8525c0e896b`.
- `.minecraft-decompile-cache/yarn/feather-gen2-1.12.1+build.2-mergedv2.jar.pin.json` SHA-256 `b5e66f4d33eefea63eca72a27b574da23c31d3f4b3e4cdd3e2f8a920c4f4b890`; artifact bytes match `f94febd2167d6091b4c157aef0a85c8791e6f8fb27b883e27a82a68c7b02b9c7`.

The provenance manifests additionally record exact client and version-metadata inputs; their local bytes were checked against those recorded hashes. No source generation, product inspection, Git inspection, build, test, or game launch was performed.

