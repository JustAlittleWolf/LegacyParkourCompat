# 1.11.2 → 1.12 ability-flight / active Elytra flag trace

## Bounded result

For a local player whose Elytra flag 7 is already live and whose Elytra still passes the active-flight check, the `PlayerEntity.moveRelative` ability-flight tail is unchanged from exact release `1.11.2` to initial stable release ID `1.12` (not `1.12.2`). With `abilities.flying && !isRiding()`, the inherited glide path reaches `this.move(MoverType.SELF, ...)`; local-player dispatch reaches `Entity.move` through `LocalClientPlayerEntity.move`, returns through the base movement call, and only then executes the player's direct `setFlag(7, false)`. That player-owned clear has no client/server guard in either release. The negative branch (`!abilities.flying || isRiding()`) does not execute that tail clear.

The only relevant movement API expansion is that 1.12 adds the third `verticalSpeed` argument to `LivingEntity.moveRelative` and to the caller from `LivingEntity.tick`. The existing Elytra glide branch remains before ordinary velocity-input processing and does not consume that argument. The local player has no `verticalSpeed` writer in either of the paired player classes; the paired movement-source search finds the explicit writer in `MobEntity`, not player input. This is not a player glide-path difference.

## Exact source identities

Both sides use Ornithe Feather mapping sources.

- **1.11.2**: `decompiled_minecraft/1.11.2/ornithe-feather/`. Exact run metadata is `decompiled_minecraft/1.11.2/ornithe-feather.provenance.json` (SHA-256 `dcdb65e1361297a584c5b93f581b1b539911911c6cec1a203740baa5379732c6`); ready marker identifies version `1.11.2`, `ornithe-feather`, and 1,921 source files. The source manifest SHA-256 is `d908c2af76598ad26999563bfef0089f1d3c8c32483eee6b108f0defa2773df0`; artifact manifest SHA-256 is `69327982116b0ce1efe32e13031cb83a93dc5f30dcd1b936bf6af003a584ac5f`. The pinned client artifact is `be3fff4f2cc005a1310a96389efdeb983d2bcb4b8e747c402acd616ae73d0ba2`; Feather 1.11.2 build.2 merged mapping archive is `d14500101ac23c874b0fe394eae21a382c410ec4f3bbc2e58042e5234a236757`, with Tiny mapping `4fa160c09d83bf61ae21bb74ab1e33b6aabe9b8ec89904b266ad53cecc9c36e6`.
- **1.12**: `decompiled_minecraft/1.12/ornithe-feather/`, with hidden canonical `.decompile-provenance.json` SHA-256 `27515e5f4c71e7db2f035a81716507128d77466c75380ad1ef7d140c06401467`. The manifest pins release metadata ending `/1.12.json` at `33cc40a05466b4e34e04e65def732094388e9149eb6bd37e5daa227c06cbde4b`, client SHA-256 `59e84cf6acc3912e1705ab05c722485afb3ec33343eef4756d8c16edfe6411ca`, Feather 1.12 build.2 merged archive `73ce7d6d0a281db15fa030ae09e836387dae1172fdffcaf3e2d1a4840830b719`, and Feather metadata `5b4555cb0473f14b4bde13b7f60f7f979dc4f8e149187f5696ae8283e437fac4`. The provenance lists 2,048 source files. Every examined 1.12 source file matched its manifest entry.

The 1.11.2 verification record says all 1,921 published source files matched with zero missing files or source differences, and raw input artifact differences were empty. It records one changed *derived remapped intermediate* (`1.11.2/client-ornithe-feather.jar`); that intermediate is not used as source evidence here. The 1.11.2 legacy sidecar provenance is outside the source tree rather than a hidden per-tree manifest; its ready marker, sidecars, source manifest, and verification record supply the checked identity. I independently hashed the four examined Java files on both sides and checked the 1.12 hashes against its hidden canonical manifest and the 1.11.2 hashes against its source manifest.

Examined source hashes (SHA-256):

| Source | 1.11.2 | 1.12 |
|---|---|---|
| `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `65c2747bd8c70def6be7f41f624d4c9493342b39ae7bed7967f9ff63608f59ed` | `d7237b8b9a68525bbfec6af1c234d2aafb1e4aa7d52059331d1d243313d6d7d1` |
| `net/minecraft/entity/living/player/PlayerEntity.java` | `87fe94fa6cbf7aba18b9a5e3401664439eb8eba9958173da8fbcd05cc7ad948b` | `6a9d5f420b13625195b149c0d9468cf1284d5eb7dc1b2a46cf01b952a27ee063` |
| `net/minecraft/entity/living/LivingEntity.java` | `bb7dc6c9e423a9568d6433d51bba12e7aee4555fbf3fb3e2b87f618382279f2f` | `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6` |
| `net/minecraft/entity/Entity.java` | `ce8104a17ce783df639cf9726e7b1cd0936563eaa7ba308603335bbe05d49440` | `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a` |

## Ordered behavior and gates

1. `LocalClientPlayerEntity.tick()` calls `super.tick()` in both sources. Its `isLocallyControlled()` returns `true`, so the inherited movement's local-control gate is open for this player.
2. In `LivingEntity.tick()`, `flyingTick()` precedes `moveRelative(...)`. Its positive existing-glide gate is `getFlag(7) && !onGround && !isRiding()`, followed by chest-slot Elytra identity and `ElytraItem.canFly`. The server alone writes the validated `bl` back to flag 7 (`if (!world.isClient)`); the client leaves the already-received flag untouched at this point. A failed validation yields false for the server write.
3. The player override gates its special flight path on `abilities.flying && !isRiding()`. Under the negative branch it only delegates to base movement and does not execute the override's direct flag clear. Under the positive branch it temporarily sets `speedInAir`, delegates to `LivingEntity.moveRelative`, restores/damps vertical velocity, performs the separately scoped fall-distance write, then directly clears flag 7. The direct clear is not conditioned on `world.isClient`.
4. If flag 7 is still live, `LivingEntity.moveRelative` enters its `isFallFlying()` branch before normal movement-input handling, updates glide velocity, then calls `this.move(MoverType.SELF, ...)`. On the local player this dispatches to `LocalClientPlayerEntity.move()`, which calls `super.move(...)` (the `Entity.move` implementation) and then its auto-jump hook. After that returns, the base glide branch has a separate landing clear gated by `onGround && !world.isClient` (server only). Control then returns to `PlayerEntity.moveRelative`, whose direct flag-7 clear occurs afterward on either side when its ability-flight gate was positive.

The same gates and ordering are present in both pair members. In 1.12 the `LivingEntity.tick()` caller passes `(sidewaysSpeed, verticalSpeed, forwardSpeed)`; in 1.11.2 it passes `(sidewaysSpeed, forwardSpeed)`. `LivingEntity.moveRelative`'s 1.12 method adds `upwards` to its normal `updateVelocity` calls and walk-animation calculation. The live Elytra branch, its `Entity.move` call, and its server-only post-move landing clear retain their order. The only additions found inside the paired `Entity.move` method are flap-sound bookkeeping lines, outside this flag-order conclusion.

## Source locations

- 1.11.2 `LocalClientPlayerEntity.tick` and `isLocallyControlled`: lines 159–173, 468–470; `move` dispatch: 823–829.
- 1.11.2 `LivingEntity.moveRelative` local/glide gates and `move`: lines 1388–1428; `tick` caller and `flyingTick`: lines 1798–1829.
- 1.11.2 `PlayerEntity.moveRelative`: lines 1386–1405; `Entity.move`: starts at line 459.
- 1.12 `LocalClientPlayerEntity.tick` and `isLocallyControlled`: lines 164–178, 484–486; `move` dispatch: 840–846.
- 1.12 `LivingEntity.moveRelative` local/glide gates and `move`: lines 1425–1465; `tick` caller and `flyingTick`: lines 1842–1873.
- 1.12 `PlayerEntity.moveRelative`: lines 1385–1404; `Entity.move`: starts at line 462.

## Boundary

This report covers only the specified ability-flight / already-live Elytra flag call chain. It does not assess `fallDistance` parity, NBT, Elytra-start initiation/networking, or A/B state, and it is not a full-pair coverage audit, independent acceptance, implementation, or runtime-parity claim. No tests, game launches, source regeneration, product edits, or Git operations were performed.