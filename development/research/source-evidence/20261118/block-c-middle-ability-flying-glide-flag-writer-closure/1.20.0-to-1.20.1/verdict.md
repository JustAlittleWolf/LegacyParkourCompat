# Independent audit: 1.20 → 1.20.1 ability-flight Elytra glide clear

**Verdict: ACCEPT — no difference in the assigned operation.**

## Frozen scope

Ordinary dry airborne locally controlled nonpassenger player; chest slot contains an active default Elytra; lawful ability flight is enabled; no status effects; fall-flying flag 7 is active when the travel step begins. Audit only the ability-flight wrapper after delegated real movement: its direct shared-flag-7 clear and separate `fallDistance` write.

## Independent source trace

In both exact Mojmap trees, `LivingEntity.aiStep` invokes `updateFallFlying()` before travel (`LivingEntity.java:2562`). With the stated preconditions and a valid Elytra, `updateFallFlying` retains its local flag value and does not clear flag 7 on the client (`LivingEntity.java:2610–2634`). The fall-flying path delegates actual movement through `LivingEntity.travel`; its own shared-flag clear is restricted to grounded, non-client execution (`LivingEntity.java:2004–2108`, specifically 2105–2107).

Then `Player.travel` takes the `abilities.flying && !isPassenger()` branch. It saves vertical velocity, calls `super.travel` (which performs the movement), restores horizontal velocity while applying the saved vertical velocity times `0.6`, calls `resetFallDistance()`, and directly writes shared flag 7 false, in that order (`Player.java:1446–1470`, writes at 1463–1467). `resetFallDistance()` writes `fallDistance = 0.0F` (`Entity.java:2280–2282`). These are separate state writes: the flag clear does not itself reset fall distance. The ordinary Elytra start gate is consistent with the stated airborne/no-effect/active-chest-Elytra precondition (`Player.tryToStartFallFlying`, `Player.java:1576–1585`).

The relevant whole Java files have byte-identical SHA-256 values across 1.20 and 1.20.1, so the cited gates, call order, math and state writes are identical. This accepts the report’s bounded no-difference conclusion; it makes no claim about effects or other movement slices.

## Evidence identity and hashes

Primary checkout: `D:\Javastuff\LegacyParkourCompat`; mapping family: Mojmap.

- Frozen source report reviewed only after the independent trace: `c-mid-120-1201-ability-flight-glide-clear-source/report.md`, SHA-256 `1f22d79e4d242c548b548aa56da175cfd515ec00ca83b740ba11482c797e0ce9` (verified).
- 1.20 hidden mapping-root manifest `decompiled_minecraft/1.20/mojmap/.decompile-provenance.json`, SHA-256 `573f0bb3e18e39ae882769a007a1c97d711da123034f9a1c894eb407ef58030f`. Its exact 1.20 input pins recompute against local cached inputs: client JAR `5ac9ead135746f5f27048f2e01d8d5bdc2dabc4d91422f73ef2435f4c3d24aff`; client mapping text `fdd921d6bcc7657850f78514d36bb57af0b8a340786d12c46ec18d9c46c039de`; version metadata `24f4700f86471b6f3d3e4d68fefcd4177820dade5ce003c095fcf0e429abbc5f`.
- 1.20.1 root provenance `decompiled_minecraft/1.20.1/mojmap.provenance.json`, SHA-256 `5aeb1df09671480aeddb921c919e92098e33d0a0bce47e22f82a865cb32400aa`; ready marker `decompiled_minecraft/1.20.1/mojmap.ready.json`, SHA-256 `f8dc81374dc42521ee9d17a04ea0dc77f1c5384548cfc01c9621b3d84b670fed`. The marker identifies version `1.20.1`, Mojmap namespace and client SHA-256 `56b71336d2b4fdffd197f56595b0da93e32a946f78f382a299b8f4b92758bb0f`.
- The 1.20.1 ready marker’s source-manifest and artifact-manifest hashes recompute: `mojmap.sources.sha256` = `858b56764113e591c60d09bc63e4d90f9c1d67a705645f0c793e8b28f2378465`; `artifacts.sha256` = `5f1fc7dcd4ee82ddb7ea0c3be6fd21ceb1910cc691594bda163bfa19b01c7db2`.

| Mojmap source | 1.20 SHA-256 | 1.20.1 SHA-256 |
|---|---|---|
| `net/minecraft/world/entity/player/Player.java` | `873d82c6f5471d06812929e471d970fd973bfbe21638e4e348a6fcde45637dac` | `873d82c6f5471d06812929e471d970fd973bfbe21638e4e348a6fcde45637dac` |
| `net/minecraft/world/entity/LivingEntity.java` | `decf8cd70d194098ad51d0c82e5a0087e0687881e0a2dc66456f2ba6ea76676f` | `decf8cd70d194098ad51d0c82e5a0087e0687881e0a2dc66456f2ba6ea76676f` |
| `net/minecraft/world/entity/Entity.java` | `94b9c3656715de2d61fa9a02ccef164261e50eb13d6090a6f07e58fe9c5b0759` | `94b9c3656715de2d61fa9a02ccef164261e50eb13d6090a6f07e58fe9c5b0759` |
| `net/minecraft/world/item/ElytraItem.java` | `fa4d677a5b109ebf323458f88f05eacb9ffdeca66d952c1a5eb8a7114a911889` | `fa4d677a5b109ebf323458f88f05eacb9ffdeca66d952c1a5eb8a7114a911889` |

**Signed:** independent source audit by `/root/c_mid_120_1201_ability_flight_glide_clear_audit`. Acceptance is bound to the exact report and source identities above. No product, research-index or source-tree files were changed.
