# 1.20 to 1.20.1: creative-flight Elytra glide clearing

## Bounded result

No source difference was found in the assigned operation for an ordinary dry, airborne, locally controlled, nonpassenger player with active Elytra fall-flying state and lawful creative ability flight. The clear is reached after a real `super.travel` call and its movement, then the player-specific creative-flight postprocessing resets fall distance and directly clears shared flag 7. The checked call order is identical in 1.20 and 1.20.1.

## Exact source evidence

- 1.20, Mojmap: `net/minecraft/world/entity/player/Player.java`, `Player.travel(Vec3)`, lines 1446–1472. The `abilities.flying && !isPassenger()` branch saves the pre-travel vertical velocity, calls `super.travel`, restores horizontal velocity from the superclass result while setting vertical velocity to the saved value times `0.6`, calls `resetFallDistance()`, then `setSharedFlag(7, false)`. The `else` invokes `super.travel` without this player flight postprocessing.
- 1.20.1, Mojmap: same file, member and lines; same gate, order, expression and writes.
- 1.20, Mojmap: `net/minecraft/world/entity/LivingEntity.java`, `LivingEntity.travel(Vec3)`, lines 2004–2108. Under local control, the fall-flying branch performs the gliding movement with `move(MoverType.SELF, ...)`; its own flag-7 clear is separately gated by `onGround() && !level().isClientSide`.
- 1.20.1, Mojmap: same file, member and lines; same movement and separate ground/server clear.
- 1.20 and 1.20.1, Mojmap: `Player.tryToStartFallFlying()` at `Player.java` lines 1576–1585 checks airborne state, not already fall-flying, not in water, and absence of Levitation; it then requires the chest-slot item to be Elytra and `ElytraItem.isFlyEnabled`. Both releases match. The ability-flight wrapper in `Player.travel` does not omit the delegated real travel call.

The two relied source files are byte-identical across the pair, as their independently checked SHA-256 values confirm. No claim is made here about status-effect interactions or their dependencies.

## Provenance and source hashes

Both roots are Mojmap exact-version decompilations. On 1.20, the hidden mapping-root `.decompile-provenance.json` identifies its exact client, mapping and version-metadata inputs and records source hashes. On 1.20.1, `mojmap.provenance.json` identifies version `1.20.1` and namespace `mojmap`; `mojmap.ready.json` binds its source-manifest and artifact-manifest hashes.

| Source | 1.20 SHA-256 | 1.20.1 SHA-256 | Manifest verification |
|---|---|---|---|
| `net/minecraft/world/entity/player/Player.java` | `873d82c6f5471d06812929e471d970fd973bfbe21638e4e348a6fcde45637dac` | `873d82c6f5471d06812929e471d970fd973bfbe21638e4e348a6fcde45637dac` | Matches 1.20 provenance `sources`; matches 1.20.1 `mojmap.sources.sha256` |
| `net/minecraft/world/entity/LivingEntity.java` | `decf8cd70d194098ad51d0c82e5a0087e0687881e0a2dc66456f2ba6ea76676f` | `decf8cd70d194098ad51d0c82e5a0087e0687881e0a2dc66456f2ba6ea76676f` | Matches 1.20 provenance `sources`; matches 1.20.1 `mojmap.sources.sha256` |

The 1.20 provenance input hashes for the client mapping text, client JAR and `1.20.json` metadata were recomputed against the corresponding cached files and matched: `fdd921d6bcc7657850f78514d36bb57af0b8a340786d12c46ec18d9c46c039de`, `5ac9ead135746f5f27048f2e01d8d5bdc2dabc4d91422f73ef2435f4c3d24aff`, and `24f4700f86471b6f3d3e4d68fefcd4177820dade5ce003c095fcf0e429abbc5f`. The 1.20.1 ready marker's recorded SHA-256 values for `mojmap.sources.sha256` and `artifacts.sha256` were independently recomputed and matched: `858b56764113e591c60d09bc63e4d90f9c1d67a705645f0c793e8b28f2378465` and `5f1fc7dcd4ee82ddb7ea0c3be6fd21ceb1910cc691594bda163bfa19b01c7db2`.

## Coverage limit

This closes only the assigned creative-ability-flight wrapper, its delegated Elytra travel/flag clear, and the preceding fall-flight start gate. Status-effect behavior and other movement slices are not assessed.
