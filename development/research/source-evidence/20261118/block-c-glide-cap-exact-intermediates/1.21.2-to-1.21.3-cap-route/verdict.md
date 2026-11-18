# Independent source audit verdict — 1.21.2 → 1.21.3 glide cap and route

## Audited revision and verdict

Audited only the frozen source-comparison report at `C:\Users\Wolfi\.codex\visualizations\2026\11\17\01a1eb7b-b9ff-7a20-8686-0295dde20f26\c-glide-1212-1213-cap-route-exact\report.md`.

- Frozen report SHA-256: `167c724d0ed08b1d7de6a20bb049a0232711fc8e55445553187f38947b8eec77`.
- Verdict: **Accept the bounded no-difference claim.** The exact local Mojmap source files needed for the already-active, dry, ordinary chest Elytra case are byte-identical across 1.21.2 and 1.21.3, and the operation order and ladder/vine distinction in the report agree with those source bodies.
- This verdict covers only the report’s stated slice. It does not establish full-pair coverage, intermediate-patch boundaries, or runtime parity.

## Exact source identity and integrity

Canonical local source roots inspected read-only:

- `decompiled_minecraft/1.21.2/mojmap/`
- `decompiled_minecraft/1.21.3/mojmap/`

The 1.21.2 embedded `.decompile-provenance.json` pins the exact release metadata URL ending in `/1.21.2.json` (SHA-256 `793e3fd0451354a26aaa00253dcb6600add11f9d2a90f7bdef75ddf5b38c2b45`), client object `c7ac2d0d86f4ca416cab9064ff8a281852ad0c7b` (client SHA-256 `613091da0f62426ae8dcf9afb30eb639485f493b81cda3a2b96b1fb8a6b9976c`), and Mojang `client.txt` mapping object `ce0ed2eb99175df9ec5166324e3fdd1f87cee827` (SHA-256 `e37560e4889168916a73dbc4922c563002eff70fbecfe1cf5c50a7b222f451b2`). The embedded per-source hashes for all listed files equal their locally calculated hashes.

The 1.21.3 provenance identifies `versionId: 1.21.3`, `namespace: mojmap`, and an exact-run batch containing 1.21.3. Its ready marker identifies the client SHA-256 `25190c556bc56d11070638ccab3b3c6efd76bb9684c92a2680a4de01c3c7138b`, source-manifest SHA-256 `d673bb5464853e3a2a92ed9b1ffe1789d4e62c097bb884f67c336fcbb3b178ce`, and artifact-manifest SHA-256 `0587668e5c70bb06dacf3496a4442f66dc9cdcab23f2350b844f14259cc40dcf`. Local hashes of both manifests match the ready marker. The four source hashes match entries in `mojmap.sources.sha256`.

| Relative Mojmap source | SHA-256 in 1.21.2 and local check in both trees | 1.21.3 manifest check | Pair result |
|---|---|---|---|
| `net/minecraft/client/player/LocalPlayer.java` | `fbd40f1f47adfa66dda9b15188e5dce82af3e8e8d7c3dd0543e602a354ad3fe0` | same | identical |
| `net/minecraft/world/entity/LivingEntity.java` | `087390495f4fdfd14b9e12230e7aea4fdc50913bb4d1d91119e72892881cfc52` | same | identical |
| `net/minecraft/world/entity/player/Player.java` | `a803203e92aa4729d5f5c9b16085b6a43ce51d9907d309eb96736e9c7c1340de` | same | identical |
| `net/minecraft/world/entity/Entity.java` | `a93719c302a0381a972af75ea360465e2e3551708dd07c34d4d40b7e5173c2b9` | same | identical |
| `net/minecraft/world/item/Items.java` | `bc99499e18f7099aab5c15f5921d583906e6cc48a7eaad8e81d09cd26ff7b7af` | same | identical |

Because the enclosing source files match byte-for-byte and their source-hash attestations match each side’s provenance, the compared method bodies and their surrounding order are identical, rather than merely similar excerpts.

## Independent trace of the bounded tick

All line references below are to the 1.21.2 Mojmap files; the same complete files, and thus the same lines and bodies, are present in 1.21.3.

1. **Ordinary item and active state.** `Items.java:959–968` defines the vanilla Elytra with `DataComponents.GLIDER` and an `EQUIPPABLE` component whose slot is `CHEST`. `LivingEntity.canGlideUsing` (`LivingEntity.java:3615–3622`) requires the GLIDER component, a matching equippable slot, and that the next damage does not break the stack. `LivingEntity.canGlide` (`2836–2848`) loops equipment slots and also requires airborne, not a passenger, and no Levitation; `Player.canGlide` (`Player.java:1471–1473`) additionally requires abilities flight off. This supports the stated ordinary, default chest-slot Elytra witness while leaving component-customized gliders outside the finding.
2. **Start gate versus active-flight cancellation.** The client start attempt at `LocalPlayer.java:735–737` requires jump input, the two local mode gates, `!onClimbable()`, then `tryToStartFallFlying()`. `Player.java:1525–1536` starts only if not already fall-flying, `canGlide()`, and not in water, and sets shared flag 7 true. This is a start path. In the already-active case, no corresponding climbability clear exists in `LocalPlayer.aiStep`; the later `wasFallFlying` assignment does not clear the shared flag. `Player.stopFallFlying` (`Player.java:1538–1541`) does explicitly write flag 7 true and then false, but the audited active client path does not call it due to a ladder/vine witness.
3. **Cap and cancellation stage.** `LivingEntity.aiStep` (`LivingEntity.java:2755–2772`) first applies `xxa *= 0.98F` and `zza *= 0.98F`; if the shared fall-flying flag is true it calls `updateFallFlying()` next. Only after that does it reset fall distance for Slow Falling/Levitation, then call `travel`. Inside `updateFallFlying` (`2814–2834`), `checkSlowFallDistance()` is first. Its exact rule in `Entity.java:2484–2488` is `getDeltaMovement().y() > -0.5 && fallDistance > 1.0F`, assigning exactly `1.0F`. The `canGlide` test and `setSharedFlag(7, false)` are inside `if (!level().isClientSide)` (`2816–2820`), so this method does not locally cancel an active glide on the logical client. For the report’s no-Slow-Falling/no-Levitation ordinary case, no later effect reset intervenes before travel.
4. **First dry glide update and travel.** `LivingEntity.travel` (`2178–2189`) tests fluid travel first, then selects `travelFallFlying` when the flag is true, otherwise ordinary air. Under the dry precondition, the fluid condition is false. `travelFallFlying` (`2268–2277`) snapshots horizontal speed, sets velocity from `updateFallFlyingMovement`, and calls `move(MoverType.SELF, deltaMovement)`; the only subsequent branch is server-only collision handling. The update method (`2279–2302`) uses look angle, float pitch converted to radians, horizontal look/velocity distances and effective gravity; it applies its negative-vertical/look alignment and downward-pitch terms when their conditions hold, aligns horizontal velocity, then multiplies by `(0.99F, 0.98F, 0.99F)`. There is no `onClimbable` check in the glide update or travel route. As a result, an already-active, dry glide takes this same first-dive update and route with either ordinary air or a climbable feet state; collision geometry/outcome itself remains outside this bounded assertion.
5. **Climbability witness.** `LivingEntity.onClimbable` (`1561–1576`) reads the in-block vanilla state for the climbable tag or a usable ladder trapdoor. That direct predicate matters to the input start gate above, not to active glide update/cancel or the dry glide route.

## Boundaries retained

No claim is made for custom/data-component gliders, other equipment arrangements, start/final mode-transition ticks as separate witnesses, fluid travel, Slow Falling or Levitation, mounted travel, collision geometry/contact outcomes, other operations in the 1.21.2→1.21.3 pair, or runtime behavior. No tests, game/TAS/server launch, source generation, product inspection, or Git operation was performed.
