# 1.14.4 → 1.15 Elytra start and climbable gate

## Result

The meaningful startup difference is the local Elytra start request. In 1.14.4, `LocalPlayer.aiStep` sends `START_FALL_FLYING` only on a rising jump input while airborne, not already fall-flying, not ability-flying, and with vertical movement strictly below `0.0` (`Vec3.y` is `double`). The 1.14.4 server independently enforces the same strict descent gate. In 1.15, the local path delegates eligibility to `Player.tryToStartFallFlying`; that method requires only `!onGround`, `!isFallFlying()`, and `!isInWater()`, plus an enabled chest Elytra. It has no vertical-speed / descent test, and the server packet handler calls the same method. Thus, for an ordinary dry local player with a usable chest Elytra at ladder/vine feet, the request may be sent while stationary or moving upward in 1.15, where the 1.14.4 client and server reject it until `deltaMovement.y < 0.0`.

This is a start-request/authoritative-flag difference, not a local client simulation switch: the request is a serverbound command. `LivingEntity.updateFallFlying` validates shared flag 7 against airborne/non-passenger state and the usable chest Elytra on each tick, but only writes that flag on the logical server. The local client reads the synchronized flag; its `LocalPlayer` callback plays the Elytra sound when the flag transitions to flying. The updater logic is otherwise the same in these endpoints.

## Bounded tick path

- The local player samples jump state in `LocalPlayer.aiStep`. 1.14.4 has the explicit strict `this.getDeltaMovement().y < 0.0` gate before sending the command; 1.15 has `!bl8 && !bl && !abilities.flying && !isPassenger()` and delegates the remaining checks to `tryToStartFallFlying`. The ordinary first jump edge is represented by `!bl` in both versions. See the source refs below.
- The server handles the request. 1.14.4 directly requires `!onGround && deltaMovement.y < 0.0 && !isFallFlying() && !isInWater()`, then validates the chest item and calls `startFallFlying`; 1.15 calls `tryToStartFallFlying`, which has the same state/item checks except for removal of the descent condition. Failed 1.15 requests call `stopFallFlying`. `startFallFlying` sets shared flag 7; `stopFallFlying` writes true then false in both versions.
- `onLadder()` reads the block at the player's feet. For a non-spectator, ladder and vine feet (also scaffolding) return true on both sides. `handleOnClimbable` is called only by the ordinary dry travel branch; it sets `fallDistance = 0.0F`, clamps X/Z to ±0.15 and Y to at least −0.15. For an unsneaking ordinary player the remaining ladder-specific downward clamp is inactive. This cleanup is unchanged at the requested state boundary. It is bypassed once the active Elytra branch is selected.
- In the active Elytra branch, before calculating the glide vector and before `move`, both versions set `fallDistance = 1.0F` exactly when the incoming vertical velocity satisfies strict `y > -0.5` (double comparison); when `y <= -0.5`, this branch leaves the existing value untouched. Therefore a prior ordinary climbable tick can have cleared fall distance, and the first glide move either replaces it with 1.0F or preserves that cleared value according to this strict threshold. The calculation and first `move` order match across the pair.
- `LivingEntity.travel` retains the same local/effective-AI authority gate and the same dry routing: a non-water/non-lava player (or a flying player in those fluids) reaches the Elytra-vs-ordinary branch. `updateFallFlying` runs immediately before `travel` in `LivingEntity.aiStep`. The glide branch's horizontal-collision damage and ground flag clear are server-gated; collision geometry and resulting contact are outside this assignment.

## Additional dry-travel source delta / dependency

In 1.14.4's ordinary dry branch, friction uses `new BlockPos(x, boundingBox.minY - 1.0, z)`. In 1.15 it uses `getBlockPosBelowThatAffectsMyMovement()`, defined as `new BlockPos(x, minY - 0.5000001, z)`. This is a direct source change in the value passed to `getFriction`; for feet heights with a fractional part at or above approximately 0.5000001, the selected block Y can differ. Whether that state is reachable for the assigned ladder/vine setup and what collision/support arrangement supplies the states is an external geometry dependency and is not claimed here. The 1.15 helper is also used by jump-factor and speed-factor accessors; those callers were not audited.

## Source references

All paths below are relative to `decompiled_minecraft/<version>/mojmap/`.

| Operation | 1.14.4 | 1.15 |
|---|---|---|
| Local jump request | `net/minecraft/client/player/LocalPlayer.java:707–712` | `net/minecraft/client/player/LocalPlayer.java:715–720` |
| Server command gate / authoritative start | `net/minecraft/server/network/ServerGamePacketListenerImpl.java:1122–1131`; `net/minecraft/server/level/ServerPlayer.java:1276–1283` | `net/minecraft/server/network/ServerGamePacketListenerImpl.java:1137–1141`; `net/minecraft/world/entity/player/Player.java:1586–1605` |
| Shared-flag updater and order before travel | `net/minecraft/world/entity/LivingEntity.java:2217–2224,2235–2254` | `net/minecraft/world/entity/LivingEntity.java:2282–2288,2299–2318` |
| Feet ladder/vine predicate | `net/minecraft/world/entity/LivingEntity.java:1228–1242` | `net/minecraft/world/entity/LivingEntity.java:1226–1240` |
| Dry travel and pre-move Elytra fall-distance write | `net/minecraft/world/entity/LivingEntity.java:1782–1846` | `net/minecraft/world/entity/LivingEntity.java:1831–1895` |
| Climbable cleanup | `net/minecraft/world/entity/LivingEntity.java:1940–1954` | `net/minecraft/world/entity/LivingEntity.java:1989–2003` |
| Sound on synchronized flag transition | `net/minecraft/client/player/LocalPlayer.java:526–528` | `net/minecraft/client/player/LocalPlayer.java:533–535` |
| Ordinary dry-branch friction block position | `net/minecraft/world/entity/LivingEntity.java:1836–1842` | `net/minecraft/world/entity/LivingEntity.java:1886–1891`; helper: `net/minecraft/world/entity/Entity.java:600–602` |

## Exact-source identities and hashes

- **1.14.4 Mojmap:** `decompiled_minecraft/1.14.4/mojmap`; ready marker declares version `1.14.4`, mapping `mojmap`, 3,250 files, and source-manifest SHA-256 `af98406f3d4ed31494fbdce1078d1f3df3f712501878f9a0e9ad5879bdf23c3b`. Its pinned client SHA-256 is `b3b2a798e2d67b566008fe4a03767ae2c7ff3f8c7ba6751e7b71fc7299672d0a`. Provenance records Mojmap, Vineflower 1.12.0, Java 25.0.3+9-LTS, Gradle 9.7.1, and the 1.14.4 exact run (2026-10-07).
- **1.15 Mojmap:** `decompiled_minecraft/1.15/mojmap`; ready marker declares version `1.15`, mapping `mojmap`, 3,329 files, source-manifest SHA-256 `251a3019ccc3fd067f66365f3545b69a983383a28b19a1038debf1791be29340`, and client SHA-256 `41eee66b4c8e76c018745a63a46a15097cc1c6482c94b38801054938a010b21b`. Provenance pins official client mappings SHA-256 `11415bc25024658f0ef1c1a0028df282135299e526a689de2f29c4662edf931a`, exact version metadata SHA-256 `d154eaf07885a7fd6dfc6014bb9caf08fecc19ccd9a0862b31b1c7d1639e7785`, and records Mojmap/Vineflower 1.12.0, Java 25.0.3+9-LTS, Gradle 9.7.1.
- Both source-manifest hashes were recomputed from the local manifest bytes and match their ready markers. The listed target source file hashes below were checked against their manifest entries and local bytes.

| Source file | 1.14.4 SHA-256 | 1.15 SHA-256 |
|---|---|---|
| `client/player/LocalPlayer.java` | `0795c1223198ce5acf5d2ed9e5db8435bbec4cd52b96f96b1ddf2865baaae85f` | `7227da5233e3c72ced008187b92a6a86842f84778eaa9f40fa1eb117286af823` |
| `server/network/ServerGamePacketListenerImpl.java` | `912f528c03da34fa703646b6383c2b49ac3493efd7ba78cb8ff27aa7f693b67d` | `3d46e455509891a3e0ef50472610d565255f5991987e971ba00af2140d25982c` |
| `world/entity/player/Player.java` | `e9ce5eb18c5581ffe2b610b273ffd85786587a00ba853e5dba0ac96593622b12` | `6985e11ad9be553e9a2f25e5decc20a9da5c3b59b68f4e83a5b9f448cd2e48a0` |
| `world/entity/LivingEntity.java` | `428762178a876efd4069086e6b7d51f571ea0401f44e7eff0927b7d26d9ef681` | `139c3866ad2c6a1ad73496f22ff1a4b5259998442092ffbafb8664428e28bf79` |
| `world/entity/Entity.java` | `31c6be42d165102d3e6dc4295fdfef6e8971fc3aea13f938a5b3a33b91d524a7` | `191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e` |
| `world/item/ElytraItem.java` | `be4dd30957df32b209eba321dfe6f5f9e7b398b2fdd4e5befb9739770fdf7dea` | `701e25f13e9ba8db74aca774f404a5281b2a58f4f7dc72e3aeabac76bbf28b43` |

## Coverage bounds and open dependencies

Examined: local start input gates; serverbound start command validation and shared-flag writes; usable default Elytra predicate; synchronized flag updater and local sound callback; feet-block ladder/vine predicate; dry travel branch selection; climbable fall-distance/velocity cleanup; first active glide fall-distance assignment and operation order; ordinary dry-branch friction position selection.

Not examined / external: intermediate releases (so the change boundary is only known to lie in `(1.14.4, 1.15]`); source branches for water/lava behavior beyond the dry predicate gates; support/contact/collision geometry and proof of a concrete fractional-feet witness; actual collision outcomes, horizontal impact damage and sound; other modifiers, fluids, block-shape providers, or unrelated player paths; the other callers of the 1.15 movement-block helper. This is a bounded source delta, not full-pair coverage, independent audit, runtime parity, or acceptance.
