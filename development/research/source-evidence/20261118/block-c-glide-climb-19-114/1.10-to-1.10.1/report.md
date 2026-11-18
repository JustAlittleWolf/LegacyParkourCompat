# C source comparison: Elytra glide and ladder/vine gate, 1.10 → 1.10.1

## Verdict

No source change was found in the assigned local-player Elytra request, server acceptance, glide movement branch, climbing branch, or flight-flag lifecycle between the exact stable releases 1.10 and 1.10.1. Each compared relevant Java file has identical full-file SHA-256 in both trees and matches the SHA recorded in that tree's provenance manifest. The bounded result is **no difference**; it does not establish full-pair tick coverage.

## Exact local source identity

Both directories are canonical `decompiled_minecraft/<exact-version>/ornithe-feather/` outputs, and each `.decompile-provenance.json` pins its own exact release inputs. No `latest` or other alias was used. The manifests identify separate exact client jars and separate Feather mappings:

| Release | Feather mapping artifact (SHA-256) | Feather version metadata input (SHA-256) | Mojang client artifact (SHA-256) | Provenance manifest (SHA-256) |
|---|---|---|---|---|
| 1.10 | `1.10+build.2-mergedv2.jar` — `ad7cf6744d81d0260c9d97ddfe8250b2fdbd54fe332d4d00266b3545d8ddf8dd` | `meta.ornithemc.net/v3/versions/feather/1.10` — `a1eb1ccff25e2b76fa1d08e9b515508e97ac4837fbcacbd7f77b3a01026d3950` | client object `ba038efbc6d9e4a046927a7658413d0276895739` — `c00f8170ebf4fd834f552246743a64885a6dffb3dd1e3a3f3dba9676b7a0af35` | `df266e2c3009dd1a873c1665c30fe388078ed1c6227668e10cde87409a18c6da` |
| 1.10.1 | `1.10.1+build.2-mergedv2.jar` — `6e8ba44832b7b77d913962462bde55084005d5de6389232453afc0bc0339ea37` | `meta.ornithemc.net/v3/versions/feather/1.10.1` — `8648e97ee398b11151cb2d441161629abde561afed9c933c18fbc622e2c9904a` | client object `44b389fff90324c4ca18796d4428a7b8ec6c2eb0` — `338b205f20c22b9c676d9d3227a5e6c5e190f30ad84d7caad035150b3e05ba72` | `a9a50497930848bf0ddec732b6d424e947f643a8fa6290e01425ac2bbfce219f` |

Both manifests record `lpc-decompile-v1` with the same pipeline settings and `Eclipse Adoptium:25.0.3+9-LTS:Windows 11:amd64`. For each file below, the listed digest was recomputed from the local file and equals that version manifest's `sources` entry. Full contents are equal across the pair where the digest matches.

| Relative source file | SHA-256 in 1.10 and 1.10.1 |
|---|---|
| `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `a9637065f21ad67464eb5c204c74ebf228c3bb0da8a96ddf4ae73c0490fed443` |
| `net/minecraft/entity/living/LivingEntity.java` | `d40dd476b6b68c6ce45b4202823475deb546ecda2284da330ff6724b33815e82` |
| `net/minecraft/server/network/handler/ServerPlayNetworkHandler.java` | `14b537a3f11e57d8364e90508f45b996b7e0593b741064167d85256166a287c6` |
| `net/minecraft/server/entity/living/player/ServerPlayerEntity.java` | `de500c8bf8e82a3f89d3a7f7208f3eed3d47f616344fb1e72aaacfd9e331a03f` |

## Compared behavior and bounds

- **Client request / updater flags:** `LocalClientPlayerEntity.tick`, both releases, lines 661–737. It snapshots previous jump input into `bl`, ticks input, and sends `START_FALL_FLYING` only for a rising jump (`input.jumping && !bl`) while airborne, `velocityY < 0.0`, not already fall-flying, and not creative-flying. It then requires a chest Elytra that `ElytraItem.canFly` accepts. The updater contains no climbing predicate. `this.falling = this.isFallFlying()` follows the request block; the request itself does not set flag 7.
- **Server authority:** `ServerPlayNetworkHandler.handlePlayerMovementAction`, both releases, lines 805 and 845–854. The server independently accepts the start request only while airborne, descending (`velocityY < 0.0`), not already flying, and not in water, then checks the chest Elytra and `canFly` before `setFlying()`. It does not reject climbing. The rejection/clear path is explicit in the `else` branch. `ServerPlayerEntity.setFlying` / `clearFlying`, lines 1042–1048, writes shared flag 7 (clear writes true then false); identical in both releases.
- **Ordinary ladder/vine predicate:** `LivingEntity.isClimbing`, both releases, lines 928–950, reads the block at floored player X/Z and floored shape minimum Y; ordinary ladder or vine returns true (spectators return false). In `moveRelative`, lines 1332–1445, the fall-flying test is the first travel branch after water/lava gates. When flag 7 is already active, glide movement therefore takes precedence over the later ordinary-ground/air branch's ladder/vine clamp (`velocityX/Z` to ±0.15, fall distance to `0.0F`, downward velocity clamp to `-0.15`, sneaking stop) and post-move climbing bounce (`velocityY = 0.2`). This branch ordering is identical for the pair.
- **Glide pre-move write:** `LivingEntity.moveRelative`, lines 1336–1371. Before reading look direction, applying glide acceleration, drag, or calling `move`, the glide branch performs `if (velocityY > -0.5) fallDistance = 1.0F`. The threshold is a strict double comparison (`-0.5`); equality does not write. The assignment is the float literal `1.0F`. Other glide operations then proceed in source order: gravity/pitch terms, descent adjustment, look alignment, `0.99F`/`0.98F` velocity multipliers, then `move`.
- **Separate post-move / cleanup paths:** after `move`, lines 1372–1384, horizontal-collision sound/damage is gated to non-client worlds; landing clears flag 7 only on the non-client side. Separately, `flyingTick`, lines 1754–1773, retains the flag only when airborne, not riding, and wearing a usable Elytra; the server side writes the resulting flag, and server-only Elytra durability is handled there. These checks do not alter the pre-move `-0.5`/`1.0F` write.

## Pair scope note

`PlayerEntity.java` differs in full-file hash, but the only line difference is `DataVersion` changing from `510` to `511`; it is unrelated to player movement and is not an Elytra finding. The four files controlling this bounded request, authority and movement path above are byte-for-byte equal. No build, test, runtime launch, source generation, Git/history inspection, or network access was performed.



