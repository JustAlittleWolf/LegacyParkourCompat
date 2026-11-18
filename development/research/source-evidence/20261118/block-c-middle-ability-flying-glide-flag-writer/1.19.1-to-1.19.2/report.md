# 1.19.1 → 1.19.2: creative ability-flight with active Elytra

## Scope and result

Compared the exact Mojmap-decompiled 1.19.1 and 1.19.2 sources for the local-player tick path where a lawful Creative player is already ability-flying, dry, airborne, not riding, has an enabled chest-slot Elytra, and has shared flag 7 set. The bounded result is **no source difference**: all five cited source files have matching SHA-256 values on both sides. Each release has its own independently pinned source manifest.

This is a bounded operation result, not a full-pair movement inventory or freeze. No product source, implementation, wiki, backlog, tests, or runtime evidence was consulted.

## Trace

- Creative grants `mayfly` (`GameType.updatePlayerAbilities`), and `LocalPlayer.aiStep` permits a player with `mayfly` to turn `abilities.flying` on when the jump toggle is activated. `MultiPlayerGameMode.isAlwaysFlying` is true only for Spectator; Creative flight can therefore be active without being forced on. [GameType.java:41–58; LocalPlayer.java:810–827; MultiPlayerGameMode.java:476–478]
- The local player's `aiStep` reaches `LivingEntity.aiStep`, which updates fall-flying state and then calls virtual `travel(...)` (`LivingEntity.java:2584–2587`). On the stated preconditions, `updateFallFlying` sees flag 7 set, airborne/nonpassenger state, no Levitation, and an enabled chest Elytra. It leaves the flag true on the client; the server-only write is guarded by `!level.isClientSide`. [LivingEntity.java:2626–2655]
- `Player.travel` takes its ability-flight branch because `abilities.flying && !isPassenger()`. It saves the incoming vertical velocity, sets the ability flying speed, and calls `super.travel(...)`, which reaches `LivingEntity.travel`. In the ordinary dry airborne case, the Elytra `isFallFlying()` branch performs gliding velocity math and calls `move(MoverType.SELF, ...)` before returning. Thus the tick performs a real glide move even though ability flight is active. [Player.java:1464–1494; LivingEntity.java:2042–2197]
- After that delegated travel returns, `Player.travel` first replaces vertical velocity with the saved value times `0.6`, then restores `flyingSpeed`, calls `resetFallDistance()`, and directly clears shared flag 7. The glide clear is its own state write, separate from the fall-distance reset; neither operation precedes nor substitutes for the glide `move`. The later glide-branch ground clear is server-only, so it does not account for this local-client clear. `isFallFlying()` reads flag 7 directly. [Player.java:1479–1493; LivingEntity.java:2147–2149, 3048–3050]

## Pair comparison

The five cited files below are identical by their exact SHA-256 values and those hashes match their respective `mojmap.sources.sha256` entries:

| Relative source file | 1.19.1 SHA-256 | 1.19.2 SHA-256 |
|---|---|---|
| `net/minecraft/client/multiplayer/MultiPlayerGameMode.java` | `d0c40fee7cbe09386fa3b0648d6839b9748c91f6ea4e6271e1ba5ae01d0a33d3` | same |
| `net/minecraft/client/player/LocalPlayer.java` | `36ae4aabd609b457fffb7a8b14abb50db9ac775857dde1774c0c68a8cf50deef` | same |
| `net/minecraft/world/entity/LivingEntity.java` | `fee2df5155449098556a138d2530d06b7a35979e43c6aa0ae1fffd6251853e77` | same |
| `net/minecraft/world/entity/player/Player.java` | `155c5fcfba322d968f3180383e7d283ddeb5edee4e04314906310d4e3ce0ccc1` | same |
| `net/minecraft/world/level/GameType.java` | `69679ff5e32ce012de988dd172e6a158927ccdda58bb5ee42ad00d4d29e4fb7a` | same |


## Exact source identities

Canonical read-only sources: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\<version>\mojmap\`.

- **1.19.1 Mojmap:** ready marker SHA-256 `c7b5373078eb777895a4f4b10d8c99eb289cb14fa1835004c1eb4563b6a8a690`; provenance SHA-256 `de0de05299c59d3b05d96e744b27f5a5cef377880ae4a7b402337aa0ffe00245`; source manifest SHA-256 `25a8e35111971be21a85fefd567ada685098f45e35e431163afdbe0fa66dfde1` (4,480 files; ready status `ready`). The provenance records client SHA-256 `146d4018cfed50c5e77483940653091f186d2eea9ad42fc40939f5c72d6b7f52`, official client-mappings SHA-256 `9b2dfe745719f4de1ce3fb23baeb24e31623c1fcc249e39bbdb068ad7f600236`, and Vineflower `1.12.0`.
- **1.19.2 Mojmap:** ready marker SHA-256 `90f5a351c60a1aab160b640567b716bbc563e62d92e7e555df55c4ce7952492c`; provenance SHA-256 `c0c148d3c3a0092adc59c29c25f2106f3a5398156a4f266882384804b3534280`; source manifest SHA-256 `95bc354ac219b9fe1ca8bf45053edad0119a75323d576902d59616a11647aac2` (4,480 files; ready status `ready`). The provenance records client SHA-256 `e1ac65de9b471b6916cc457fdcff00c1bafac17027aa79100c4df893b3d956db`, official client-mappings SHA-256 `9b2dfe745719f4de1ce3fb23baeb24e31623c1fcc249e39bbdb068ad7f600236`, and Vineflower `1.12.0`.

The exact two ready markers' declared source-manifest hashes match the measured source manifest files. The two mapping artifact hashes also match (`artifacts.sha256` SHA-256: 1.19.1 `aae6327967e07f5cdd92ac9bfa6d9c606e1be9a96e24dd30acad07fd2425df41`; 1.19.2 `d0197d78578241b8cdde1123a3dee64aa279341681a7edde9f867689f9bb3490`).


