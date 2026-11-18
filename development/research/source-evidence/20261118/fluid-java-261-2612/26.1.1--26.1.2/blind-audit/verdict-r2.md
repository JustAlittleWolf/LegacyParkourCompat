# Final dependency supplement and conditional verdict r2

## Bound immutable artifacts

- Author comparison: `source/comparison.md`, SHA-256 `d4e863c4aef0ac682f67f4f0a4e605486532a36b7cfcac69836a68e60de13a3f`.
- Independent blind freeze: `blind-audit/frozen.md`, SHA-256 `f8c583b81c688442a121065b6bbcaa729ad9bbfb4c3d4450b8a03463d2d66c17`.
- Prior post-freeze verdict: `blind-audit/verdict-r1.md`, SHA-256 `4daca44b067bb66157860b701ec4ed5115e7ee3d9e1ce4ab324626856c73676f`.

This supplement adds no claim or comparison pair. It closes only the previously identified dependencies in the frozen no-change assessment. The older artifacts remain unchanged.

## Manifest and body checks

For every file in this supplement, the current SHA-256 was checked against its source-manifest row in both canonical trees (`decompiled_minecraft/26.1.1/unobfuscated/` and `decompiled_minecraft/26.1.2/unobfuscated/`). Each row and current file matched, and each source file has the same SHA-256 on both sides:

| Relative source | SHA-256 (both releases) |
| --- | --- |
| `net/minecraft/client/player/LocalPlayer.java` | `433fd995ad317af0f6ef0e50c1e8e3483cb8f00e0e327d4edf27a4dd99666ebe` |
| `net/minecraft/client/player/AbstractClientPlayer.java` | `7825a8d4e8e24928cba18f68109c91479b78fb14fcd8b81f0e045b8d02a559cc` |
| `net/minecraft/world/entity/player/Player.java` | `44cf28e0c64e78d39fd13368e9991381dbebab67029070cb9ddc43f09d45d14d` |
| `net/minecraft/world/entity/Entity.java` | `8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf` |
| `net/minecraft/world/entity/LivingEntity.java` | `c3b64de8dbaba8ad8a7ccd4f33255346d8206e66f12ca91260971bf5e5ac93bd` |
| `net/minecraft/world/entity/EntityFluidInteraction.java` | `5264ff4f1fddebc3fa9d63ad2edbe2eaf617392ff946a867a6817a78b478ee62` |
| `net/minecraft/world/level/material/FluidState.java` | `e35608d5775013c67653ea350309b5150a97f36aa65c27f1c6247ae2aa840f18` |
| `net/minecraft/world/level/material/Fluid.java` | `e7759a85dea543266b734086b2061332bc9f62ba3330be0f203f1f8baaa2c80d` |
| `net/minecraft/world/level/material/FlowingFluid.java` | `f8c0c4430e81efdf39b221968272ed8e3a4d030fa5192e04e04d34c9fbd37b28` |
| `net/minecraft/world/level/material/WaterFluid.java` | `14cb3d49ef1db28fdab367cd6a4f196f20b7886b8768153c6e82906e9d25f0ad` |
| `net/minecraft/world/level/material/LavaFluid.java` | `423ff44a57c316f58c21a66903a4ef2c1b9c3d26316bdc4d301727575f93703f` |

## Closed dependencies

### Local-player dispatch

`LocalPlayer` extends `AbstractClientPlayer` (`LocalPlayer.java:108`); `AbstractClientPlayer` extends `Player` (`AbstractClientPlayer.java:19`). The local-player class has no `travel` override. Its `aiStep` calls `super.aiStep` (`LocalPlayer.java:767, 914`); `AbstractClientPlayer.aiStep` and `Player.aiStep` also delegate to `super` (`AbstractClientPlayer.java:76-78`; `Player.java:442-453`). `LivingEntity.aiStep` reaches the dynamic `this.travel(input)` call for simulated movement (`LivingEntity.java:3073`).

There is an intermediate `Player.travel` override (`Player.java:1381-1404`), so the comparison's phrase that local-player travel is inherited directly from `LivingEntity` is imprecise. In the bounded ordinary branch—non-riding, non-swimming, and not flying—`Player.travel` reaches `super.travel(input)`, which is `LivingEntity.travel`; its fluid admission and water/lava math are the frozen methods (`LivingEntity.java:2392-2494, 2496-2510`). This route is identical between releases because `LocalPlayer`, `AbstractClientPlayer`, `Player`, and `LivingEntity` each have matching manifest-backed source hashes.

### Current flow and water/lava height dispatch

`FluidState.getFlow` and `FluidState.getHeight` dynamically call their fluid type's `getFlow` and `getHeight` (`FluidState.java:49-50, 95-96`). `WaterFluid` and `LavaFluid` extend `FlowingFluid` and do not override either method. `FlowingFluid` supplies flow at line 55, fluid height at lines 471-472, and own height at lines 476-477. Own height calls `fluidState.getAmount() / 9.0F`; each fluid's nested `Flowing` and `Source` classes provide the amount/source implementations (`WaterFluid.java:139-158`; `LavaFluid.java:247-266`). These subclass bodies and the inherited flow/height bodies are manifest-backed and byte-identical across the two releases. Thus the previously frozen flow and stored-height comparison includes the actual water/lava virtual implementations.

### Effective `isPushedByFluid` owner

The effective override for a player is `Player.isPushedByFluid()` (`Player.java:1673-1675`), which returns `!this.abilities.flying`. `Entity.isPushedByFluid()` is the base implementation and returns `true` (`Entity.java:3206-3208`), but virtual dispatch on `LocalPlayer` follows the `Player` override. For the bounded non-flying ordinary path, the result is true on both versions; the frozen review's `Player.java` citation is the effective owner. The comparison's `Entity.java:3206` citation identifies only the base implementation and should be corrected. `Player.java` and `Entity.java` hashes and the compared method bodies match across releases.

## Conditional verdict

With the above dependency closure, the three requested bounded clauses are accepted as a source-level no-difference result: water/lava contact admission and stored height; fluid current sampling and push; and first ordinary water/lava travel drag and gravity gates. The earlier r1 closure reservations are resolved for this pair and scope. Correct the comparison's two dispatch descriptions: `Player.travel` is an intermediate override that delegates in the ordinary branch, and the effective player push predicate is the `Player` override rather than the base `Entity` method.

This result assumes equal effective world/resources and player inputs/state, including fluid tags and states, dimension `FAST_LAVA`, movement attributes/effects, and non-flying/non-swimming/non-riding ordinary motion. These inputs are conditions of the comparison, not claims that resources or runtime state were independently normalized.

The 26.1.2 local ready/provenance metadata establishes the prepared source set's version label, unobfuscated namespace, decompile run, source manifest, and ready-marker client SHA-256. The local provenance omits the official client URL/hash and version-metadata hash, so official Mojang artifact origin remains unverified by this evidence. No full-pair coverage, exact intermediate cutover, implementation recommendation, or runtime parity is claimed.