# Direct pitch setter and Elytra pitch use: 1.18.2 → 1.19

Scope: ordinary direct `Entity.turn(double, double)` pitch update for the local player, its finite computed value path through the first `setXRot` and final clamp, and the active fall-flying travel use of pitch. The only caller context examined is the direct mouse-device call in `MouseHandler`. Callback plumbing, rendering, networking, teleportation, and unrelated rotation writes were not assessed.

## Exact source identity

Both directories contain Mojmap sources with ready markers for the exact requested versions. Ready-marker `sourceManifestSha256` matches the SHA-256 of the adjacent source manifest; the per-file entries below match the SHA-256 of the cited source files.

| Version | Mapping | Ready marker SHA-256 | Provenance SHA-256 | Source manifest SHA-256 | Client artifact SHA-256 |
|---|---|---|---|---|---|
| 1.18.2 | Mojmap | `d8057d47468c37880de38d658e95070ec3b750305b7856189997604822480946` | `ce14d5dfc2d51cffa7b1512878d908e37c930d414147be24e69439a7f1061ca9` | `aea0cb9c6fc8f7a46f0eb82b0388ad58a4659f513be6c0a2be06c0df0c1eb07a` | `1d09e3639644b6b2254499469d0765cc005a286d19f3fa595b0ed8fb07971ec7` |
| 1.19 | Mojmap | `b00a68db8600ec8cdc3917357aff59eb0537be6f382e621e5ed3eeb900898b88` | `833cf50d6eab0f24475dcc11085c1414c85eee0e708ff17c59bc8dcd2ae9c35a` | `78bf3f9d5b4b5437240dd0707fa7a9ff56e6d1e267bd8edc13adc8313b535e26` | `ffd4fb3037d4110085b4d3d2dc47b760695528eb14292c3e5f7c758983c6d764` |

The source manifest records these cited file hashes:

| Version | Source file | SHA-256 |
|---|---|---|
| 1.18.2 | `net/minecraft/world/entity/Entity.java` | `2228fdaca5793171cbd94038306d571a6ada78ca96f5734efb4cada5b744c10a` |
| 1.18.2 | `net/minecraft/world/entity/LivingEntity.java` | `db4168d531caf18f22e3fefd073365e776da4075ce01452bb9f7671d9b458782` |
| 1.18.2 | `net/minecraft/world/entity/player/Player.java` | `bf639c1962ff90d69e4569b2b18f6fcf57ac46ef80b19686f0fbc1687fca744a` |
| 1.18.2 | `net/minecraft/client/MouseHandler.java` | `fa651c8c0e0f75b5ffa816b57e734159b34465b37e288116e9fd4f08da831ca1` |
| 1.19 | `net/minecraft/world/entity/Entity.java` | `3a8ce3090d29aad1bbfeca31de300d34b42a7e902f0e78b13efd5653cd588766` |
| 1.19 | `net/minecraft/world/entity/LivingEntity.java` | `00c591dd2cac49ee5ccad8705f7dd879ad5d29f0360b98742166294989b3531c` |
| 1.19 | `net/minecraft/world/entity/player/Player.java` | `4b5d3d71ac1c4b64efdeb1ad86a4b6f84afffe210e272850e620339430942de1` |
| 1.19 | `net/minecraft/client/MouseHandler.java` | `fd2b9d6c1079df233db76139a0060d4449f7b725d66f6805cc9401c2c1ff64fb` |

## Compared slices and result

- `Entity.java`, `turn(double, double)`: 1.18.2 lines 385–397; 1.19 lines 389–401. Both compute pitch delta as `(float) pitchArg * 0.15F`, then call `setXRot(getXRot() + delta)`, then call `setXRot(Mth.clamp(getXRot(), -90.0F, 90.0F))`. The argument is double, cast to float before multiplication; the multiplier, delta, getter result, addition, clamp bounds, and returned clamp value are float operations. There is no intermediate widening or changed cast/order. The yaw update between the two pitch setter calls is also present in both releases.
- `Entity.java`, `setXRot(float)`: 1.18.2 lines 3031–3037; 1.19 lines 3059–3065. Both reject a non-finite float with the same `Float.isFinite` gate/log call; otherwise assign directly to `xRot`. Thus the first setter can discard an invalid computed pitch, after which the later clamp reads the retained value and the same finite check applies. For finite inputs and ordinary finite current pitch, both versions produce the same first write and final clamp.
- Caller context, `MouseHandler.java` lines 237–245 in both releases: after deriving mouse deltas and applying the integer invert-Y sign, the handler calls `minecraft.player.turn($$7, $$8 * $$13)` when the player exists. The direct caller and argument shape are unchanged. The options API used to obtain invert-Y differs (`options.invertYMouse` versus `options.invertYMouse().get()`), outside this direct `turn` slice.
- Active Elytra pitch use, `LivingEntity.java`: 1.18.2 lines 2074–2116; 1.19 lines 2107–2149. In the `isFallFlying()` travel branch, both read look angle and compute `float pitchRadians = getXRot() * (float)(Math.PI / 180.0)`, then use `Math.cos(pitchRadians)` for lift and `Mth.sin(pitchRadians)` for the negative-pitch dive term, with the same branch gates, constants, types, and operation order. This is active movement math for a locally controlled player: `Player.travel` delegates to `LivingEntity.travel` outside the abilities-flying branch in both versions; the inherited travel method admits `isControlledByLocalInstance()` and enters the branch when `isFallFlying()` is true. These pitch-dependent travel operations are unchanged.

## Finding disposition

No source difference was found in the assigned direct-pitch setter or Elytra pitch-use slices. This comparison supports no independent 1.18.2-to-1.19 finding for this operation. It does not establish pair-wide movement coverage, an intermediate patch boundary, or runtime parity.
