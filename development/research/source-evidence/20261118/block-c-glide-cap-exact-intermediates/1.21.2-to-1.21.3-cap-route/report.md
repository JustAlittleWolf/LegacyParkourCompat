# 1.21.2 → 1.21.3 glide cap and route (bounded source comparison)

## Result

**No difference found in the assigned already-active, dry, on-foot player glide slice.** For an ordinary chest-slot Elytra and a player whose shared fall-flying flag is already true, the flag update/cancellation logic, pre-move fall-distance cap, and first glide movement route are byte-identical in the paired relevant source files. The climbable witness does not divert or cancel an already-active glide on the client. This is a source result for these two adjacent releases, not runtime parity evidence.

## Exact source identity and hash checks

The checked sources are `decompiled_minecraft/1.21.2/mojmap/` and `decompiled_minecraft/1.21.3/mojmap/` in the primary checkout; mapping namespace is Mojmap on both sides.

- **1.21.2:** `mojmap/.decompile-provenance.json` is embedded in the output tree. Its pinned input entries identify release metadata `.../1.21.2.json`, client JAR object `c7ac2d0d86f4ca416cab9064ff8a281852ad0c7b` (recorded SHA-256 `613091da0f62426ae8dcf9afb30eb639485f493b81cda3a2b96b1fb8a6b9976c`), and Mojang `client.txt` mapping object `ce0ed2eb99175df9ec5166324e3fdd1f87cee827` (recorded SHA-256 `e37560e4889168916a73dbc4922c563002eff70fbecfe1cf5c50a7b222f451b2`). The provenance source-hash entries match the local SHA-256 values below.
- **1.21.3:** sibling `mojmap.provenance.json`, `mojmap.ready.json`, and `mojmap.sources.sha256` identify exact version `1.21.3`, mapping `mojmap`, source output path `build/movement-campaign-2026-10-07/ready/1.21.3/mojmap`, and client SHA-256 `25190c556bc56d11070638ccab3b3c6efd76bb9684c92a2680a4de01c3c7138b`. The locally calculated source-manifest SHA-256 is `d673bb5464853e3a2a92ed9b1ffe1789d4e62c097bb884f67c336fcbb3b178ce`, matching the ready marker; its artifacts-manifest SHA-256 also matches (`0587668e5c70bb06dacf3496a4442f66dc9cdcab23f2350b844f14259cc40dcf`). Each local relevant source hash matches the entry in that source manifest.

| Mojmap source file | SHA-256, 1.21.2 | SHA-256, 1.21.3 | Paired result |
|---|---|---|---|
| `net/minecraft/client/player/LocalPlayer.java` | `fbd40f1f47adfa66dda9b15188e5dce82af3e8e8d7c3dd0543e602a354ad3fe0` | same | identical |
| `net/minecraft/world/entity/LivingEntity.java` | `087390495f4fdfd14b9e12230e7aea4fdc50913bb4d1d91119e72892881cfc52` | same | identical |
| `net/minecraft/world/entity/player/Player.java` | `a803203e92aa4729d5f5c9b16085b6a43ce51d9907d309eb96736e9c7c1340de` | same | identical |
| `net/minecraft/world/entity/Entity.java` | `a93719c302a0381a972af75ea360465e2e3551708dd07c34d4d40b7e5173c2b9` | same | identical |

Because the complete relevant files hash identically, their cited method bodies and enclosing order are identical, not just matching excerpts.

## Compared behavior

- `LocalPlayer.aiStep` (`LocalPlayer.java:637–795`) gates a **new jump-triggered** start on jump held, no ability-flight toggle this step, no auto-jump, `!onClimbable()`, then `tryToStartFallFlying()`. The helper in `Player.java:1525–1541` checks not already fall-flying, `canGlide()`, and not in water; starting sets shared flag 7 true. The climbable gate is only on this new-start input path. For the active-flight case assigned here, `LocalPlayer.aiStep` has no corresponding climbable-based flag clear.
- `Player.canGlide()` (`Player.java:1471–1473`) requires player abilities not flying plus `LivingEntity.canGlide()`. The latter (`LivingEntity.java:2836–2848`) checks not grounded, not passenger, no levitation, and a usable glider equipment item; it does not check climbability. `LivingEntity.updateFallFlying()` (`LivingEntity.java:2814–2833`) first invokes `checkSlowFallDistance()`. Only on a non-client level does it check `canGlide`; failure then clears shared flag 7 and returns. Therefore the active local client path does not locally cancel the flag on a ladder/vine witness. `Player.stopFallFlying()` (`Player.java:1538–1541`) writes flag 7 true and then false; this sequence is also unchanged.
- At `LivingEntity.aiStep` (`LivingEntity.java:2755–2772`), strafe/forward input is damped, active fall-flying invokes `updateFallFlying()`, then slow-falling/levitation may reset fall distance, then the entity enters `travel`. Thus the cap is before the first movement call for this active tick; for the assigned ordinary dry case the effect reset does not intervene.
- `Entity.checkSlowFallDistance()` (`Entity.java:2484–2488`) sets `fallDistance = 1.0F` exactly when `getDeltaMovement().y() > -0.5` and current `fallDistance > 1.0F`; otherwise it does nothing. This cap runs before travel on the active glide path.
- `LivingEntity.travel()` (`LivingEntity.java:2178–2189`) dispatches fluid travel first, then `travelFallFlying()` when the shared fall-flying flag is true, else ordinary air travel. `travelFallFlying()` (`LivingEntity.java:2268–2277`) snapshots horizontal speed, replaces velocity with `updateFallFlyingMovement`, calls `move(MoverType.SELF, deltaMovement)`, and does server-only glide collision handling. The glide movement route has no climbable predicate, so an already-active dry glide is not diverted by a ladder/vine feet witness. A water/lava case is an explicit earlier fluid branch and is outside this dry witness.
- `LivingEntity.onClimbable()` (`LivingEntity.java:1561–1575`) reads the entity's in-block state for climbable-tagged blocks and usable ladder trapdoors; the input gate is therefore a direct player predicate, not a change to vanilla block state.

## Boundaries

This report covers an already-active, ordinary dry, on-foot glide tick and its pre-move cap/route. It does not claim behavior for custom/data-component gliders, the mode-start or mode-final tick as independent witnesses, fluid travel, A/B geometry/contact or collision outcomes, or other movement slices in the pair. No runtime/game/TAS/server checks were performed.
