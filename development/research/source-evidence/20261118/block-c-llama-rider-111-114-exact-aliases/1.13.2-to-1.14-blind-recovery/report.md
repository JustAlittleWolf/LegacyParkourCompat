# Blind source recovery: Llama passenger Y placement, 1.13.2 → 1.14

## Result and bounds

No movement difference found in the assigned slice: the ordinary adult Llama's direct passenger `setPosition` Y expression, its height input, and the dynamic rider-position callback/authority are equivalent. This is a bounded source result, not a full-pair coverage or runtime-parity verdict. No product files, research index, old scratch reports, implementation, tests, or game processes were touched.

## Exact source identity and integrity

Both compared canonical trees use the Ornithe Feather mapping family. Local ready markers identify exact releases 1.13.2 and 1.14, with Feather build.2 inputs; each ready marker's source-manifest SHA-256 matches the corresponding manifest file, and each source file SHA-256 below matches its manifest entry.

- 1.13.2 mapping `ornithe-feather` (`feather-gen2-1.13.2+build.2`): provenance SHA-256 `e25950b7855fc4f2aee1a662f0be6b0af9f28788d9e27293075cfc5a3aa5d383`; ready-marker SHA-256 `1d1c644c1deb05c02d11dc575ad834f4f72489124701d9b2b90357f43dc37b38`; source-manifest SHA-256 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`.
- 1.14 mapping `ornithe-feather` (`feather-gen2-1.14+build.2`): provenance SHA-256 `bfb011801a64e9afaa4aca43e8a8ed40bf2a02f136ffc02e65b32e79315764fa`; ready-marker SHA-256 `e0ecb319a63d5d664b87ad7c00638e1cdb9dc302ee2c05bbc05a88ce96d1e60b`; source-manifest SHA-256 `68bc39e38bc501e7c9af315de4678263e1a64606b0b9780099187c7984ccbc98`.

Relevant source-file SHA-256 values:

- `decompiled_minecraft/1.13.2/ornithe-feather/net/minecraft/entity/living/mob/passive/animal/LlamaEntity.java`: `5be437bcd5600c4dbcd7ef5e5224ac31baa69efbd69b11152b0bc04081922089`.
- `decompiled_minecraft/1.14/ornithe-feather/net/minecraft/entity/living/mob/passive/animal/LlamaEntity.java`: `17190fe664a4284a4673efbcb4438906e4de1fcaa562d20e09b7b4b2ce7b3b46`.\n- `decompiled_minecraft/1.14/ornithe-feather/net/minecraft/entity/EntityType.java`: `f3aa0bbf209cd6e2bfd31869dbb292b063efd1aa3e9719bb0adf999d2f3e7017`.

## Compared behavior

- **1.13.2 Feather:** `LlamaEntity.java:64` sets dimensions with `setSize(0.9F, 1.87F)`. At lines 146–163, `updateRiderPositon` first checks `hasPassenger(rider)`, computes `f = cos(bodyYaw * (float)(PI / 180.0))` and `g = sin(...)`, then sets Y as `this.y + this.getMountHeight() + rider.getRideHeight()`. `getMountHeight` is `this.height * 0.67` (line 157); the Llama overrides `canBeControlledByRider` to return false (161–163).
- **1.14 Feather:** `LlamaEntity.java:149–166` has the same gate, angle casts, operation order, direct position expression, and false rider-control result. Its mount-height expression is `this.getHeight() * 0.67` (159–161). In `Entity.java:2571–2573`, `getHeight()` returns the float `dimensions.height`; in 1.13.2, `Entity.java:132` declares `height` as `float`. Thus the direct field versus accessor is float-for-float the same height input. The 1.13.2 constructor sets `0.9F, 1.87F` directly (`LlamaEntity.java:62–65`); in 1.14 the constructor delegates to its entity type, whose `EntityType.LLAMA` declaration supplies dimensions `0.9F, 1.87F` (`EntityType.java:233`).
- **Passenger input:** `PlayerEntity.java:980–982` (1.13.2) and `PlayerEntity.java:950–952` (1.14) both return `-0.35` from `getRideHeight`. The Y expression adds the same double-valued mount-height product and rider offset in the same order in both releases.
- **Dynamic callback:** 1.13.2 `Entity.java:1583–1594` ticks the rider and, if still riding, invokes `entity.updateRiderPositon(this)`; 1.14 `Entity.java:1519–1531` does the same through `this.getMount().updateRiderPositon(this)`. Both are virtual calls on the current mount, so an actual Llama passenger reaches the Llama override, whose `hasPassenger` guard controls the write. No change in callback authority or ordering relevant to this placement was found.

## Trader Llama check

The exact 1.14 Yarn provenance records client 1.14 (client SHA-256 `93907cbf0655aa5f10d049efa4d745f4250eecc5d0d5d1268046eec3144f323a`) and Yarn `1.14+build.21`. Its local `TraderLlamaEntity.java` source SHA-256 is `985323d40a7f35d80cc0f741c747e007c7a07e56e1332d9d1d4dfdb10b466ac5`, matching that provenance's per-source hash. The class extends `LlamaEntity` and declares no rider-position or rider-control override, so it inherits the checked 1.14 behavior. This Yarn tree has no separate ready marker; it was used only to check the subclass and does not replace Feather as the canonical pair comparison.
