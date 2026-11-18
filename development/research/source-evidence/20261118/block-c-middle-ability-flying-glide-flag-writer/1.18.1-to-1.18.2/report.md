# 1.18.1 → 1.18.2 ability-flight glide-flag source trace

## Scope and disposition

Bounded comparison: a lawful local creative player is airborne, dry, not a passenger, has an enabled Elytra in the chest slot, and begins the tick with fall-flight shared flag 7 set. Trace the real movement call, the ability-flight post-move flag clear, and fall-distance reset as separate operations.

**Disposition: no source difference in this operation between 1.18.1 and 1.18.2.** Both releases run the ability-flight travel branch when `abilities.flying && !isPassenger()`. It calls superclass travel first, then restores vertical velocity from the pre-travel value multiplied by `0.6`, restores `flyingSpeed`, calls `resetFallDistance()`, and directly clears flag 7. The release pair does not establish when this behavior was introduced; intermediate versions were not inspected.

## Reachable sequence

1. In `LocalPlayer.aiStep`, a player with `mayfly` can toggle `abilities.flying`; when that ability is active, the local Elytra-start branch is gated by `!abilities.flying`. The assigned state here is already-gliding, so no new Elytra start is needed.
2. `LivingEntity.aiStep` calls `updateFallFlying()` immediately before `travel(...)`. For the stated state, its flag-7, airborne, nonpassenger, no-levitation gate passes; an enabled chest-slot Elytra keeps the local flag value true. The helper writes the recomputed flag only when `!level.isClientSide`, so it does not clear the local client's flag at this point.
3. Inherited `LivingEntity.travel` performs its ordinary movement path; its fall-flight branch calls `move(MoverType.SELF, ...)`. `Player.travel` surrounds that call with the player-specific ability-flight branch, so the movement happens before the post-move actions below.
4. `Player.travel` calls `resetFallDistance()` (which writes `fallDistance = 0.0F`) and then `setSharedFlag(7, false)`. The flag clear is direct and follows movement; it is not the separate fall-distance reset. `Entity.setSharedFlag` clears the bit with a byte mask.

This establishes an end-of-travel state with flag 7 clear and fall distance zero for the stated path. The behavioral conclusion is limited to this checked local-client path; it does not assert a trajectory beyond the source operations.

## Compared source locations

| Operation | 1.18.1 Mojmap | 1.18.2 Mojmap |
|---|---|---|
| Local ability-flight toggle / Elytra-start gate | `net/minecraft/client/player/LocalPlayer.java`, `aiStep`, lines 726–752 | same file, `aiStep`, lines 721–747 |
| Caller order (`updateFallFlying` then `travel`) | `net/minecraft/world/entity/LivingEntity.java`, `aiStep`, lines 2457–2548 | same file, `aiStep`, lines 2461–2552 |
| Elytra validation / local-vs-server flag write | `net/minecraft/world/entity/LivingEntity.java`, `updateFallFlying`, lines 2586–2610 | same file, lines 2590–2614 |
| Actual inherited movement branch | `net/minecraft/world/entity/LivingEntity.java`, `travel`, lines 2005–2112; fall-flight `move(MoverType.SELF, ...)` at 2099 | same file, lines 2009–2116; fall-flight move at 2103 |
| Post-move ability branch, reset then flag clear | `net/minecraft/world/entity/player/Player.java`, `travel`, lines 1409–1436; reset at 1432, clear at 1433 | same file, lines 1422–1449; reset at 1445, clear at 1446 |
| Flag mask and fall-distance write | `net/minecraft/world/entity/Entity.java`, `setSharedFlag`, lines 2022–2029; `resetFallDistance`, lines 2101–2103 | same file, lines 2035–2042; reset, lines 2114–2116 |

The listed operation bodies and relevant gates match across the pair. Line offsets reflect surrounding source changes, not a change to this trace.

## Source identity and readiness

Canonical read-only roots: `decompiled_minecraft/1.18.1/mojmap/` and `decompiled_minecraft/1.18.2/mojmap/`. Both ready markers name exact release IDs, `mojmap`, Vineflower, Java 25, and their source manifests. The 1.18.1 provenance has a full exact-run record, pinned client SHA-256 `737c28a56c8980fcd8574465c61538ebde8f511bdd035c69d0cbe27953f892aa`, and official client-mapping SHA-256 `a7a4109255dfb74b9552b339482f0714bb0e7e7143d1059b0bf62a526d2c2a84`. The 1.18.2 ready marker pins client SHA-256 `1d09e3639644b6b2254499469d0765cc005a286d19f3fa595b0ed8fb07971ec7`; its provenance file is a shorter summary and does not contain the raw-input mapping/client hash record present in the 1.18.1 provenance. Readiness/source identities are still pinned below; no regeneration was performed.

SHA-256 values are lowercase. `mojmap.sources.sha256` entries below match the cited source-file bytes.

| Identity | 1.18.1 | 1.18.2 |
|---|---|---|
| `mojmap.provenance.json` | `4b181e586201aa2695835e31155c96d8ae3d05b00c9bbb83e99eaa5b2b9db139` | `ce14d5dfc2d51cffa7b1512878d908e37c930d414147be24e69439a7f1061ca9` |
| `mojmap.ready.json` | `7a6f3d9d86776e5a95e4722fc2c36f41165c34177f2993978dfaab410641fc81` | `d8057d47468c37880de38d658e95070ec3b750305b7856189997604822480946` |
| `mojmap.sources.sha256` | `52aa98450b5cfa55ac2bd2054f108e16df7fa939a3f061991b6a9adb434d8d2d` | `aea0cb9c6fc8f7a46f0eb82b0388ad58a4659f513be6c0a2be06c0df0c1eb07a` |
| `artifacts.sha256` | `b8237a01cdebe7d784caae113886f42565677ce6897ea960a49223cfec4c00ec` | `a1507e4875faee892aca4c59686bbd68933db21a2274559e81eea8a32b021036` |
| `movement-diagnostics.txt` | `0e3e7d1310deeb834a8c1598ad63f24fcff01fcb26efce326945fcc42ecc3ecd` | `0bc8857ee048b0ede8e696f3b7b050006618b278936cf182b3464c6c99483ac8` |

### Cited Java source SHA-256

| Relative source file | 1.18.1 | 1.18.2 |
|---|---|---|
| `net/minecraft/client/player/LocalPlayer.java` | `e38d0bbb6e5b9de2a698609b8491d0007406b3becd42ba9b95289f533788c996` | `99c2d18bcd23243afb8f95c5bafb21fb0be7ea04aacbb14fcf7be7ced2c9c095` |
| `net/minecraft/world/entity/player/Player.java` | `1fb9dfb9f700323d77f4196dbc3618d01a65e56438b9cce3ee38fa0b258bd7e2` | `bf639c1962ff90d69e4569b2b18f6fcf57ac46ef80b19686f0fbc1687fca744a` |
| `net/minecraft/world/entity/LivingEntity.java` | `68fc9b15eb31690377afe92acfc783c86945fedec5739d28faacd96da4f9e0e7` | `db4168d531caf18f22e3fefd073365e776da4075ce01452bb9f7671d9b458782` |
| `net/minecraft/world/entity/Entity.java` | `69417675ae8e0a5bd88b7372baea1e73b1f5217154f39446c8ad5c92731437f1` | `2228fdaca5793171cbd94038306d571a6ada78ca96f5734efb4cada5b744c10a` |

## Coverage boundary

Checked the local ability-flight toggle/start gating, living-entity tick ordering, Elytra validation and client/server guard, inherited movement invocation, player travel branch, flag-bit write, and fall-distance reset in both exact sources. No source difference was found in those slices. This is not a complete 1.18.1→1.18.2 movement inventory or independent pair audit.
