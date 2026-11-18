# Independent source audit: 1.10 → 1.10.1 glide/climb gate

## Verdict

**Concur: no difference in the assigned active-glide/climbing slice.** For an ordinary dry locally controlled player whose fall-flying flag is already active and who has a usable chest Elytra, both releases select the glide branch before the ordinary climbing branch. The climb clamps and post-move climbing bounce are not reached on that path. Before glide acceleration or `move`, both versions use the same strict `velocityY > -0.5` test to assign `fallDistance = 1.0F`. No paired source change was found in this bounded slice. This is not full-pair coverage.

The server is the authority for setting the active state through `START_FALL_FLYING`: it accepts only airborne, descending, not-already-flying, non-water players with a chest Elytra accepted by `ElytraItem.canFly`, then `setFlying()` sets flag 7. `LivingEntity.isFallFlying()` reads flag 7. The server gate and the flag writers are identical across the pair. No climbing predicate occurs in that acceptance condition. This audit does not evaluate client initiation or end-of-tick cleanup.

## Input identity and exact source provenance

Input report: `c-glide-climb-110-1101-alias-gate/report.md`, SHA-256 `8c7b53ee68a29d9a829778ee23110b93f8b7a0b2d56b64721fea7e2fe20a634b`.

Sources were read from the exact local canonical paths `decompiled_minecraft/1.10/ornithe-feather/` and `decompiled_minecraft/1.10.1/ornithe-feather/`; neither a `latest` directory nor another alias was used. Each local `.decompile-provenance.json` names the corresponding exact release, its version-specific Feather metadata input, and a separate exact Feather artifact. Both manifests use `lpc-decompile-v1` with the same recorded pipeline settings and runtime.

| Release | Feather artifact SHA-256 | Feather metadata input SHA-256 | Mojang client SHA-256 | Manifest SHA-256 |
|---|---|---|---|---|
| 1.10 | `ad7cf6744d81d0260c9d97ddfe8250b2fdbd54fe332d4d00266b3545d8ddf8dd` | `a1eb1ccff25e2b76fa1d08e9b515508e97ac4837fbcacbd7f77b3a01026d3950` | `c00f8170ebf4fd834f552246743a64885a6dffb3dd1e3a3f3dba9676b7a0af35` | `df266e2c3009dd1a873c1665c30fe388078ed1c6227668e10cde87409a18c6da` |
| 1.10.1 | `6e8ba44832b7b77d913962462bde55084005d5de6389232453afc0bc0339ea37` | `8648e97ee398b11151cb2d441161629abde561afed9c933c18fbc622e2c9904a` | `338b205f20c22b9c676d9d3227a5e6c5e190f30ad84d7caad035150b3e05ba72` | `a9a50497930848bf0ddec732b6d424e947f643a8fa6290e01425ac2bbfce219f` |

The following SHA-256 values were recomputed from each local source file and match that release manifest’s `sources` entry. The paired bytes are identical:

| Relative source | SHA-256 in both releases |
|---|---|
| `net/minecraft/entity/living/LivingEntity.java` | `d40dd476b6b68c6ce45b4202823475deb546ecda2284da330ff6724b33815e82` |
| `net/minecraft/entity/Entity.java` | `05da145effa19a6ef7934cc276e89226373b67c12f4ce89a8ce2183f29039f77` |
| `net/minecraft/server/network/handler/ServerPlayNetworkHandler.java` | `14b537a3f11e57d8364e90508f45b996b7e0593b741064167d85256166a287c6` |
| `net/minecraft/server/entity/living/player/ServerPlayerEntity.java` | `de500c8bf8e82a3f89d3a7f7208f3eed3d47f616344fb1e72aaacfd9e331a03f` |

## Paired trace

- `LivingEntity.isClimbing()` (`1.10` and `1.10.1`, lines 928–940) floors player X/Z and `getShape().minY`; for a non-spectator it returns true for ordinary ladder/vine blocks. This same predicate feeds the ordinary branch below.
- `LivingEntity.moveRelative()` (both, lines 1332–1336) first requires local control or logical-side movement update; water and lava gates follow. In the ordinary dry local case those gates pass, then `isFallFlying()` selects the first movement branch. `isFallFlying()` is just `getFlag(7)` (lines 2088–2089), with `velocityY` declared `double` in `Entity.java` lines 96–97.
- In the glide branch (lines 1336–1371), the pre-move threshold is literally `if (this.velocityY > -0.5)`, assigning the float literal `1.0F` to `fallDistance` at line 1338. Equality at `-0.5` does not assign. Look-vector/pitch calculations and glide acceleration follow; X/Z are multiplied by `0.99F`, Y by `0.98F`, and `move(...)` is called at line 1371.
- The alternative ordinary branch begins at line 1385. Its climbing block at lines 1407–1418 clamps X/Z to `±0.15F`, clears fall distance to `0.0F`, limits downward Y to `-0.15`, and stops negative Y while sneaking. After its `move`, horizontal collision plus climbing sets Y to `0.2` (lines 1421–1423). Because this is the `else` of the active glide condition, none of those climb operations runs during the assigned active-glide path.
- In the server handler (both, lines 845–854), `START_FALL_FLYING` checks airborne, `velocityY < 0.0`, not already fall-flying, not in water, and an accepted chest Elytra before calling `setFlying()`; its rejection branch calls `clearFlying()`. `ServerPlayerEntity` lines 1042–1048 sets flag 7 true for `setFlying()` and clears it for `clearFlying()` (which writes true then false). These relevant authority/flag operations are byte-identical.

## Scope boundary

Only the already-active, ordinary dry local glide path at ordinary ladder/vine feet, its flag/authority basis, branch precedence, threshold assignment, and later in-branch velocity multipliers / move were audited. Client start-request initiation, end-of-tick flight cleanup, unrelated A/B material, and all other movement inventory were excluded. No build, tests, runtime launch, generation, Git/history inspection, or network access was performed.
