# Independent bounded source-audit verdict

**Scope:** Exact Mojmap sources for 1.19.1 → 1.19.2; ordinary dry airborne local player, not riding, active chest-slot Elytra, shared flag 7 already set, lawful Creative ability flight active. This verdict is limited to this path and claim; it does not establish full-pair coverage or freeze.

**Reviewed source report:** `c-mid-1191-1192-ability-flight-glide-clear-source/report.md`, SHA-256 `327111AA187A3C2374866F9E56646551C53E647B971A99DE25C44D8795D93077`.

## Verdict

**ACCEPT the bounded no-difference claim, with one provenance-description correction.** The report's source-level conclusion and tick ordering are independently confirmed. Its statement that the 1.19.2 provenance document records the client-mappings input hash is inaccurate: that release's `mojmap.provenance.json` records exact-run/tool information but no metadata or input hashes. The 1.19.2 `artifacts.sha256` instead records client-mappings SHA-256 `c5db94c44c1ce6c5d3bfce64152831090310c202f4abe4375adbb3454afcec76` and mapped-client SHA-256 `a257c4c97ceac50fbc069dc6051dff5f0263716547ede05e85c44d675ade592f`; `mojmap.ready.json` records client SHA-256 `e1ac65de9b471b6916cc457fdcff00c1bafac17027aa79100c4df893b3d956db`. The `9b2d...` mappings hash cited as applying to both releases is the 1.19.1 client-mappings hash, not 1.19.2. This documentation issue does not invalidate the bounded finding: the exact-version ready markers, source manifests, cited source-file hashes, and the independent source trace all check out.

## Independently verified source identities

Canonical sources were read-only at `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\<version>\mojmap\`. For each side, I recomputed SHA-256 for `mojmap.ready.json`, `mojmap.provenance.json`, `mojmap.sources.sha256`, and `artifacts.sha256`; the values match the report's four declared file identities. Each ready marker says `ready`, identifies the expected exact version and `mojmap`, and declares 4,480 source files. The source-manifest digest matches the ready marker.

All five files cited in the report have these identical, manifest-matching hashes on both releases:

| Source file | SHA-256 on each side |
|---|---|
| `net/minecraft/client/multiplayer/MultiPlayerGameMode.java` | `d0c40fee7cbe09386fa3b0648d6839b9748c91f6ea4e6271e1ba5ae01d0a33d3` |
| `net/minecraft/client/player/LocalPlayer.java` | `36ae4aabd609b457fffb7a8b14abb50db9ac775857dde1774c0c68a8cf50deef` |
| `net/minecraft/world/entity/LivingEntity.java` | `fee2df5155449098556a138d2530d06b7a35979e43c6aa0ae1fffd6251853e77` |
| `net/minecraft/world/entity/player/Player.java` | `155c5fcfba322d968f3180383e7d283ddeb5edee4e04314906310d4e3ce0ccc1` |
| `net/minecraft/world/level/GameType.java` | `69679ff5e32ce012de988dd172e6a158927ccdda58bb5ee42ad00d4d29e4fb7a` |

I additionally checked the `Entity.java` dependency because fall distance must be distinguished from the flag clear. It is also identical and manifest-matching on both sides: `759de9cded43bd882afcf5b7023bcf804d92419acb656b493f3b490c83eb18b6`.

The report's pinned ready-marker hashes are 1.19.1 `c7b5373078eb777895a4f4b10d8c99eb289cb14fa1835004c1eb4563b6a8a690` and 1.19.2 `90f5a351c60a1aab160b640567b716bbc563e62d92e7e555df55c4ce7952492c`; provenance-file hashes are `de0de05299c59d3b05d96e744b27f5a5cef377880ae4a7b402337aa0ffe00245` and `c0c148d3c3a0092adc59c29c25f2106f3a5398156a4f266882384804b3534280`; source-manifest hashes are `25a8e35111971be21a85fefd567ada685098f45e35e431163afdbe0fa66dfde1` and `95bc354ac219b9fe1ca8bf45053edad0119a75323d576902d59616a11647aac2`, respectively. Both artifact-manifest file hashes reported by the source report also match.

## Independent path trace

- `GameType.updatePlayerAbilities` grants Creative `mayfly`; `LocalPlayer.aiStep` can toggle `abilities.flying` for a mayfly player, while `MultiPlayerGameMode.isAlwaysFlying` returns true only for Spectator. The requested Creative flight state is therefore lawful and need not be forced flight.
- `LocalPlayer.aiStep` delegates to `LivingEntity.aiStep`. The latter calls `updateFallFlying()` immediately before virtual `travel(...)`. With flag 7 already set, airborne/nonpassenger state, no Levitation, and an enabled chest Elytra, `updateFallFlying` retains its local true value. Its `setSharedFlag(7, ...)` write is guarded by `!level.isClientSide`, so on the client the pre-existing flag remains set.
- `Player.travel` takes the ability-flight branch on `abilities.flying && !isPassenger()`, saves incoming vertical velocity, adjusts flying speed, then invokes `super.travel`. In dry conditions, `LivingEntity.travel` reaches the `isFallFlying()` branch, applies glide velocity math, and calls `move(MoverType.SELF, getDeltaMovement())`. Its subsequent grounded glide clear is server-gated and the stated player is airborne.
- Only after that delegated travel (including the glide move) returns, `Player.travel` writes vertical velocity as the saved value times `0.6`, restores `flyingSpeed`, invokes `resetFallDistance()`, then calls `setSharedFlag(7, false)`. `Entity.resetFallDistance()` separately sets `fallDistance = 0.0F`. Thus the glide branch's conditional `fallDistance = 1.0F` write (when incoming vertical velocity is greater than `-0.5`) is later reset as its own state update; it is neither the flag clear nor a replacement for the glide movement. `isFallFlying()` directly reads shared flag 7.

The same source-file hashes on both sides establish no source difference for this specifically traced operation. No implementation, wiki, product history, backlog, runtime or test evidence was used.

**Signed:** Independent source-audit reviewer, Codex worker. Verdict bound to the report SHA-256 above and the exact source identities listed here.
