# 1.12.1 → 1.12.2: ability flight while glide flag is live

## Result

No source difference was found for this bounded operation. In both exact releases, a locally controlled player with `abilities.flying == true`, not riding, and flag 7 already true enters `PlayerEntity.moveRelative`'s ability-flight branch. Its `super.moveRelative` call dispatches to the inherited `LivingEntity.moveRelative`, whose live-glide branch applies glide velocity math and calls `this.move(...)`. On return, the owning `PlayerEntity` method restores its saved vertical velocity multiplied by `0.6`, restores `speedInAir`, sets `fallDistance` to `0.0F`, then directly clears flag 7. Thus this call can use glide math for actual local displacement and leave flag 7 false afterward. The inherited `LivingEntity` grounded clear is separately gated by `onGround && !world.isClient`; it does not account for this local post-super clear.

The relevant inherited and owning method bodies are text-identical between releases. The complete `LivingEntity.java` files also have the same SHA-256. This is a checked no-difference result for the stated path, not a finding of a 1.12.1/1.12.2 behavior boundary.

## Exact source identity and file hashes

Mapping family on both sides: Ornithe Feather (`ornithe-feather`). Sources were consumed read-only from the canonical `decompiled_minecraft/` tree.

* **1.12.1** — `decompiled_minecraft/1.12.1/ornithe-feather/`. The source tree's `.decompile-provenance.json` is a legacy-shaped manifest without top-level version/namespace fields. Its pinned inputs identify Feather Gen2 `1.12.1+build.2` mergedv2 (`f94febd2167d6091b4c157aef0a85c8791e6f8fb27b883e27a82a68c7b02b9c7`) and exact-version metadata (`https://meta.ornithemc.net/v3/versions/feather/1.12.1`, SHA-256 `1893deb3a1a74c6f2054876e2b8512817f744188aa8a9d8aafdba8525c0e896b`). Its source manifest hashes match the following local file hashes.
* **1.12.2** — `decompiled_minecraft/1.12.2/ornithe-feather/`; provenance manifest states version `1.12.2`, namespace `ornithe-feather`, mapping option `feather`, and Feather Gen2 `1.12.2+build.2` mergedv2 plus Tiny mapping artifacts. Its `.sources.sha256` entries match the following local file hashes.

| Exact source file, relative to its version/mapping root | 1.12.1 SHA-256 | 1.12.2 SHA-256 |
|---|---|---|
| `net/minecraft/entity/living/player/PlayerEntity.java` | `4dab93ea3178a28c616947004ecdc514e6542edca8c62ef83f686ccf6b2ce640` | `e4e0fdbe07a7d0a0ae4a70cbb6739a287c9d045a4a12b409895c220b5d91fe1e` |
| `net/minecraft/entity/living/LivingEntity.java` | `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6` | `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6` |
| `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `d7237b8b9a68525bbfec6af1c234d2aafb1e4aa7d52059331d1d243313d6d7d1` | `01a58e94d8c6ff98a8e3794227cdc76a5fcbdbad795c70c9cf28854aff9823cc` |

The 1.12.2 provenance manifest records buildSrc decompiler tool versions Java `25.0.3+9-LTS`, Gradle `9.7.1`, Vineflower `1.12.0`, ASM `9.10.1`, Mapping IO `0.9.1`, Gson `2.14.0`, Tiny Remapper `0.14.1`, and Feather mapping artifacts `feather-gen2-1.12.2+build.2-mergedv2.jar` and `feather-gen2-1.12.2+build.2.tiny`.

## Compared operation and evidence

* `net/minecraft/entity/living/LivingEntity.java`, `moveRelative(float,float,float)`, both versions lines 1425–1477: the same local/logical-side gate; water and lava exceptions for player ability flight; the `isFallFlying()` branch; velocity/fall-distance math; velocity multipliers; actual `this.move(MoverType.SELF, ...)`; and grounded server-side flag clear. The entire source file is byte-identical by SHA-256.
* `net/minecraft/entity/living/player/PlayerEntity.java`, `moveRelative(float,float,float)`, both versions lines 1385–1403: exact method text comparison is equal. The owning guard is `abilities.flying && !isRiding()`. After `super.moveRelative(...)`, it performs `velocityY = savedVelocityY * 0.6`, restores the saved `speedInAir`, writes `fallDistance = 0.0F`, and calls `setFlag(7, false)`. The `else` invokes the superclass without those writes.
* `LivingEntity.tickMovement`, both versions line 1847, calls `this.moveRelative(...)`, so the player override owns this operation through normal virtual dispatch. `LocalClientPlayerEntity.move`, 1.12.1 lines 841–846 and 1.12.2 lines 846–851, delegates to `super.move(...)` before `autoJump(...)`; those method bodies are text-identical. The inherited glide branch's `this.move(...)` therefore reaches the local-player move override and the ordinary entity displacement implementation.

## Bounds

This checks only the normal local movement call with an already-live flag 7 and authorized player ability flight. It does not establish how a concurrent ability/glide state arose, infer packet latency or server correction behavior, or rely on crafted state. Although the owning method also writes `fallDistance`, this is not a separate fall-distance finding. Gliding start eligibility, alternate flag writers, NBT load/save, and other flag-7 transitions were not inventoried here.
