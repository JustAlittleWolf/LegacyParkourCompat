# Feather 1.10.2 → 1.11.0: direct local-player pitch turn

## Bounded result

This source-only comparison covers the ordinary mouse-driven pitch update and its earliest current-pitch consumer in dry, active local Elytra movement. In the inspected endpoint sources, `Entity.updateLocalPlayerCamera(float yaw, float pitch)` has the same pitch arithmetic and statement order. No operation difference was found for this setter. This does not establish an implementation cutover or full-pair source freeze.

The exact source publication provenance is unresolved: neither legacy source tree contains `.decompile-provenance.json`, and no pinned input/mapping publication identity is available alongside these trees. Per `buildSrc/README.md`, such legacy trees are reusable with an explicit unverified-provenance warning; their presence and local file hashes do not prove the exact release/mapping inputs. The source claims below remain provisional until the preparation owner supplies verifiable provenance. No source generation was performed.

## Endpoint source evidence

- Older endpoint: Minecraft `1.10.2`, Feather-mapped tree `decompiled_minecraft/1.10.2/ornithe-feather`; pitch setter `net/minecraft/entity/Entity.java`, `Entity.updateLocalPlayerCamera`, lines 291–301. Mouse caller: `net/minecraft/client/render/GameRenderer.java`, lines 850 and 854.
- Newer endpoint: Minecraft `1.11.0` (directory ID `1.11`), Feather-mapped tree `decompiled_minecraft/1.11/ornithe-feather`; pitch setter `net/minecraft/entity/Entity.java`, `Entity.updateLocalPlayerCamera`, lines 300–310. Mouse caller: `net/minecraft/client/render/GameRenderer.java`, lines 862 and 866.
- Both setter bodies perform, in order: save old current pitch in `float f`; update yaw; assign current pitch with `(float)(this.pitch - pitch * 0.15)`; clamp current pitch to `[-90.0F, 90.0F]`; then update previous pitch with `this.lastPitch + (this.pitch - f)`; update previous yaw; notify a mount if present.
- Java numeric types are material: `pitch * 0.15` uses the unsuffixed `double` literal, so the float argument is promoted to double for the product and subtraction; the result is cast to float before `MathHelper.clamp`. The later `(this.pitch - f)` and addition to `lastPitch` are float operations. The previous pitch is adjusted from the already-clamped current pitch and is not itself clamped in this method.
- The endpoint setters are operation-identical by direct source inspection. No per-method source diff was produced; no test or runtime execution was performed.

## Finite large witness

Use a direct ordinary setter call with old current pitch `0.0F`, old previous pitch `80.0F`, and finite input pitch `-Float.MAX_VALUE`. The double product and subtraction produce about `+5.1042352E37`, a finite value representable as a finite float; current pitch then clamps to `90.0F`. The subsequent previous-pitch update uses the clamped delta `90.0F - 0.0F`, yielding `170.0F`. This witnesses both the current-before-previous ordering and the absence of a previous-pitch clamp for this call. It is an arithmetic witness for the direct method input, not a claim that a physical mouse can generate this delta.

## Earliest current-pitch consumer in dry active local Elytra movement

At both endpoints, `PlayerEntity.moveRelative` delegates to `LivingEntity.moveRelative` when the player is not in ability flight. In a dry state, the water/lava gates allow the method through; when `isFallFlying()` is true, its first pitch-dependent expression is the call `this.getLookVector()` (older lines 1341 onward; newer lines 1395 onward). `LivingEntity.getLookVector()` calls `this.getRotationVector(1.0F)`, and `LocalClientPlayerEntity.getRotationVector(float)` dispatches to the vector overload using current `this.pitch` (newer LocalClientPlayerEntity lines 154–155). The `1.0F` path in `LivingEntity.getRotationVector` selects current pitch rather than interpolating from `lastPitch` (newer lines 1919–1925). Thus the first current-pitch read in this active Elytra movement branch is through the look-vector dispatch, before the explicit later assignment `float i = this.pitch * (float)(Math.PI / 180.0)` in the same branch (newer lines 1395–1396). The preceding `PlayerEntity.moveRelative` override calls `super.moveRelative` for a normally controlled player whose `abilities.flying` is false (newer lines 1385–1400). The local-player tick enters its superclass tick at newer `LocalClientPlayerEntity.java:159–171`; `PlayerEntity.tick` delegates to `super.tick` at line 204; `LivingEntity.tick` reaches movement processing through its tick/mob-tick path, including the `moveRelative` call at line 1801. `flyingTick()` runs immediately before this call and does not read pitch (newer `LivingEntity.java:1808–1827`).

## Intermediate-release coverage

Only the assigned endpoints were inspected. The local exact-source inventory contains directories for `1.10.2` and `1.11`, but that is not a supported-release catalog. No literal intervening supported release was identified from permitted local evidence; absence of another local directory does not prove none is supported. Any intermediate supported release, if present, remains an unresolved gap, and the setter's introduction/change boundary remains unknown within `1.10.2`–`1.11.0`.

## SHA-256 of cited source files

Hashes identify the exact inspected local files; they do not repair the missing decompiler provenance manifests.

| Endpoint | Relative source path | SHA-256 |
|---|---|---|
| 1.10.2 Feather | `net/minecraft/entity/Entity.java` | `05DA145EFFA19A6EF7934CC276E89226373B67C12F4CE89A8CE2183F29039F77` |
| 1.11 Feather | `net/minecraft/entity/Entity.java` | `3209FEAACD1E798B521A193EEB081D92C96881A70A18254EF24A58E911A4FD97` |
| 1.10.2 Feather | `net/minecraft/client/render/GameRenderer.java` | `35467C3723B9B50BB7E25EE439B8262D091CEE12A1B94881F25FE90D35A83C52` |
| 1.11 Feather | `net/minecraft/client/render/GameRenderer.java` | `7B46D9BB3A0E78F3921DFBF75C0563BEFB2CFB521745B3235274F73CDC817D09` |
| 1.10.2 Feather | `net/minecraft/entity/living/LivingEntity.java` | `D40DD476B6B68C6CE45B4202823475DEB546ECDA2284DA330FF6724B33815E82` |
| 1.11 Feather | `net/minecraft/entity/living/LivingEntity.java` | `1F2E31FA6A905CCC2A51A0338F2AAF448F2B0569F28644E8205D864E570D9B2B` |
| 1.10.2 Feather | `net/minecraft/util/math/MathHelper.java` | `43B2A4E3931733DBC3538024B7182B024D874A5F22A269A6199C6B4CA8C59151` |
| 1.11 Feather | `net/minecraft/util/math/MathHelper.java` | `97AA2499681FDD8CEE6564F777310EE396BC447FEF78C2273A990E9202D9641B` |
| 1.10.2 Feather | `net/minecraft/entity/living/player/PlayerEntity.java` | `A055B84B98D98E828E177CAD9BBA47A1334EE9BF3ECD4331792979106235A302` |
| 1.11 Feather | `net/minecraft/entity/living/player/PlayerEntity.java` | `A0D787681DE5CAECDA03FEA1536DAC0C07E6CA5CFBFE3B5FBB700CBE77C86FB6` |
| 1.11 Feather | `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `65C2747BD8C70DEF6BE7F41F624D4C9493342B39AE7BED7967F9FF63608F59ED` |
