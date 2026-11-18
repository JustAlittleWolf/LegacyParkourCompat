# 1.12 → 1.12.1: creative-flight / Elytra flag overlap

## Result

No source difference was found in the assigned movement slice. Both exact Feather sources have the same `PlayerEntity.moveRelative` method body and the same `LivingEntity.moveRelative` method body. The direct flag-7 clear is present in both releases, after `super.moveRelative(...)` returns; it is not absent on either side. This closes only the assigned operation slice, not the release pair.

## Exact source identity and integrity

Canonical trees: `decompiled_minecraft/1.12/ornithe-feather/` and `decompiled_minecraft/1.12.1/ornithe-feather/`.

- **1.12, Ornithe Feather Gen2 1.12+build.2:** client artifact `https://piston-data.mojang.com/v1/objects/909823f9c467f9934687f136bc95a667a0d19d7f/client.jar`, SHA-256 `59e84cf6acc3912e1705ab05c722485afb3ec33343eef4756d8c16edfe6411ca`; Feather merged-v2 artifact SHA-256 `73ce7d6d0a281db15fa030ae09e836387dae1172fdffcaf3e2d1a4840830b719`; Feather metadata response SHA-256 `5b4555cb0473f14b4bde13b7f60f7f979dc4f8e149187f5696ae8283e437fac4`. Hidden `.decompile-provenance.json` SHA-256 `27515e5f4c71e7db2f035a81716507128d77466c75380ad1ef7d140c06401467`.
- **1.12.1, Ornithe Feather Gen2 1.12.1+build.2:** client artifact `https://piston-data.mojang.com/v1/objects/83385d346cf7f97d1e447b888750fb88e9928b93/client.jar`, SHA-256 `19331fb29bf8b8d5ed764677c94cca9d66481e10104f660eb555894a4300cf22`; Feather merged-v2 artifact SHA-256 `f94febd2167d6091b4c157aef0a85c8791e6f8fb27b883e27a82a68c7b02b9c7`; Feather metadata response SHA-256 `1893deb3a1a74c6f2054876e2b8512817f744188aa8a9d8aafdba8525c0e896b`. Hidden `.decompile-provenance.json` SHA-256 `0ff5246e8d29c04554cc9ec93ae9b4abef539652325b6c0ac822851d7186a9c8`.

Both manifests declare pipeline `lpc-decompile-v1;threads=1;generic=true;ascii=true;synthetic=remove;indent=4;inner-warnings=false;runtime=false;prefix=net/minecraft,com/mojang;remap=ignoreConflicts,renameInvalidLocals,rebuildSourceFilenames,fixPackageAccess` and runtime `Eclipse Adoptium:25.0.3+9-LTS:Windows 11:amd64`. Recomputed SHA-256 for every Java file named by each hidden manifest: 1.12, 2,048/2,048 present and matching; 1.12.1, 2,050/2,050 present and matching. No source regeneration was performed.

Relevant source SHA-256 values (the values recorded in the manifests matched disk):

| Source | 1.12 | 1.12.1 |
| --- | --- | --- |
| `net/minecraft/entity/living/player/PlayerEntity.java` | `6a9d5f420b13625195b149c0d9468cf1284d5eb7dc1b2a46cf01b952a27ee063` | `4dab93ea3178a28c616947004ecdc514e6542edca8c62ef83f686ccf6b2ce640` |
| `net/minecraft/entity/living/LivingEntity.java` | `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6` | same |
| `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `d7237b8b9a68525bbfec6af1c234d2aafb1e4aa7d52059331d1d243313d6d7d1` | same |
| `net/minecraft/entity/Entity.java` | `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a` | same |

## Compared operation and ordinary overlap

For both versions, `LivingEntity`'s movement tick calls `flyingTick()` and then `this.moveRelative(this.sidewaysSpeed, this.verticalSpeed, this.forwardSpeed)` (`LivingEntity.java:1842–1847`). This is a virtual call, so a player uses `PlayerEntity.moveRelative` (`PlayerEntity.java:1385–1401`). The local client player reports `isLocallyControlled() == true` (`LocalClientPlayerEntity.java:483–486`); the superclass gate is `isLocallyControlled() || isLogicalSideForUpdatingMovement()` (`LivingEntity.java:1425–1428`; the latter predicate is at `Entity.java:2428–2431`). These lines are identical on both endpoints.

When `abilities.flying && !isRiding()` holds, `PlayerEntity.moveRelative` saves `velocityY` and `speedInAir`, sets the temporary flight speed, calls `super.moveRelative(...)`, then sets `velocityY` to the saved value times `0.6`, restores `speedInAir`, sets `fallDistance = 0.0F`, and directly calls `setFlag(7, false)`. The `else` path only calls `super.moveRelative(...)`. In the superclass, an active `isFallFlying()` enters the glide calculation (`LivingEntity.java:1429–1477`): it computes the glide velocity and invokes `this.move(MoverType.SELF, ...)` at line 1464. For the local player this dispatches to `LocalClientPlayerEntity.move`, which calls `super.move(...)` and then auto-jump handling (`LocalClientPlayerEntity.java:841–846`). Thus, if both flight predicates are already true in an ordinary movement pass while not riding, the inherited glide branch and its move execute first; the player override then overwrites vertical velocity from its saved pre-call value and clears flag 7. This statement is the source-defined condition and ordering; no artificial state was injected and no runtime trajectory was asserted.

The superclass's independent ground-contact clear is only `onGround && !world.isClient` after its glide move (`LivingEntity.java:1475–1477`). It does not replace or relocate the player override's post-super clear. The `fallDistance` reset is a separate write in that same player branch (line 1397), not evidence for the flag operation.

## Kept separate from this comparison

- The local glide-start packet remains gated by jumping, not a sprint-key transition, airborne state, negative `velocityY`, `!isFallFlying()`, and `!abilities.flying`, followed by chest Elytra identity/durability checks (`LocalClientPlayerEntity.java:759–763`); the relevant method body is unchanged across these endpoints.
- Elytra flag NBT write/read remains at `LivingEntity.java:471` and `508–510`, and is unchanged. It is outside the post-super movement write being compared.
- A/B scope is the exact source pair only. No artificial bit writes, packets, tests, game launches, product sources, prior findings, wiki material, or Git history were consulted.

## Method comparison fingerprints

Extracted source method text (signature through matching closing brace), SHA-256 over UTF-8 bytes:

- `PlayerEntity.moveRelative(float,float,float)`: both releases `4f6cfd1cd93faa4f02f96f20a5c2a48c05297254287277c8068de73ad9cf1e27` (765 characters each).
- `LivingEntity.moveRelative(float,float,float)`: both releases `c336b9240c99be7f4bc066c2536dedea38f6f5ec3e9439b5adaf8fa23bd1e03c` (8,659 characters each).
