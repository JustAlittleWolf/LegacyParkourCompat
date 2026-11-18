# Independent audit - ordinary local pitch turn, 1.15 -> 1.15.1

Verdict: **No difference in the bounded source path.** The ordinary local mouse turn preserves the same double-to-float pitch arithmetic, current/previous rotation write order, and final pitch clamps in both exact Mojmap source sets. The next dry, actively fall-flying player movement reads the clamped current pitch through the same path. This verdict closes only this bounded slice; it does not assert full-pair coverage.

## Pinned source identity

Both source sets are Mojmap, produced in run `prep-1.15-1.15.1-mojmap-2026-10-09-r1` with Vineflower 1.12.0. The ready marker's `sourceManifestSha256` agrees with the actual source-manifest bytes; its `provenanceSha256` agrees with the actual provenance bytes. Every cited Java file's actual SHA-256 agrees with its entry in that version's manifest. The listed Java hashes are equal across the pair.

| Version | Ready marker SHA-256 | Provenance SHA-256 | Source manifest SHA-256 |
| --- | --- | --- | --- |
| 1.15 Mojmap | `c6b66b63bafaf2096ad7606111f1d0fae1f2178d624d6a8c9ded5ad3ac1aaa31` | `de4a538ebbaf82d1615683b08ecae3cd76553345f3079436764eb095be343949` | `251a3019ccc3fd067f66365f3545b69a983383a28b19a1038debf1791be29340` |
| 1.15.1 Mojmap | `afbb16c3cb832ffee8e4763bf9933d9635ecd811c9fbd7166554b78e59788504` | `6a7af1f5d65d7af165c2ab9a90c1fae20169b38e70c8e92dbc5f59d94efe9461` | `c8fdce2f9432b72fc13c19a48f9ab3be203cac41a617139e81bac3552245912d` |

| Cited source file, identical SHA-256 in both releases | SHA-256 |
| --- | --- |
| `net/minecraft/client/Minecraft.java` | `10d049b37e9b9f3bbd3bff5a9cbabe39742c53a8e7b0fd95c2cfb6fbe3bf3ef4` |
| `net/minecraft/client/MouseHandler.java` | `925ef4e75c1da041e078eb71b6bc79180ebbb1329ce9d18efb5203777fd1fe5b` |
| `net/minecraft/client/player/LocalPlayer.java` | `7227da5233e3c72ced008187b92a6a86842f84778eaa9f40fa1eb117286af823` |
| `net/minecraft/util/Mth.java` | `a842059e498147d368126c896f0036916a90bdd125a09b09f9fe6658f2aaf0a4` |
| `net/minecraft/world/entity/Entity.java` | `191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e` |
| `net/minecraft/world/entity/LivingEntity.java` | `139c3866ad2c6a1ad73496f22ff1a4b5259998442092ffbafb8664428e28bf79` |
| `net/minecraft/world/entity/player/Player.java` | `6985e11ad9be553e9a2f25e5decc20a9da5c3b59b68f4e83a5b9f448cd2e48a0` |

## Compared behavior

In both versions, `Minecraft.runTick` executes zero or more game ticks and then calls `MouseHandler.turnPlayer()` (`Minecraft.java:840-847`). On the ordinary unsmoothed mouse path, `turnPlayer` forms its pitch delta as a `double`, `accumulatedDY * g`, applies the integer inversion sign, and calls `player.turn(j, k * n)` without narrowing it (`MouseHandler.java:197-223`). The smoothing branch also supplies a `double` delta.

`Entity.xRot`, `Entity.xRotO`, `yRot`, and `yRotO` are `float` fields (`Entity.java:126-129`). `Entity.turn(double, double)` computes pitch increment `f = e * 0.15` as a `double`; it evaluates `(this.xRot + f)` in double precision, casts that result to `float` for the first current-pitch write, and immediately clamps the float to [-90, 90]. It then independently adds the same double increment to `xRotO`, casts to float, and clamps the previous pitch. Yaw current/previous values are separately incremented and cast without a turn-time wrap. A finite pitch sum above +90 (including an increment over 360) therefore reaches the first float write before being clamped to +90; a sum below -90 clamps to -90. Float overflow to infinity is also brought to the corresponding bound. The helper's comparison-based float clamp leaves NaN unchanged (`Mth.java:98-104`). Order is unchanged: current pitch and yaw writes, current-pitch clamp, previous pitch and yaw writes, previous-pitch clamp, then the vehicle callback (`Entity.java:326-337)).

Because the mouse turn follows the current frame's ticks, the next entity tick first copies current pitch/yaw into previous pitch/yaw (`Entity.java:358-360`). On the next local-player AI path, `LocalPlayer.aiStep` calls `super.aiStep()` (`LocalPlayer.java:778`), after which `LivingEntity.aiStep` updates fall-flying state and calls `travel` (`LivingEntity.java:2282-2288`). For a non-ability-flying player, `Player.travel` delegates to `super.travel` (`Player.java:1471-1483`); when the player is dry and `isFallFlying()` is true, `LivingEntity.travel` reads `getLookAngle()` and converts current `xRot` to radians for the gliding equations (`LivingEntity.java:1840-1871`; `Entity.java:1661-1663`). Thus this movement read consumes the clamped current pitch, not an unbounded intermediate turn sum. The corresponding methods and ordering are byte-identical in 1.15.1.

## Exact source references

The references below apply to both `decompiled_minecraft/1.15/mojmap/` and `decompiled_minecraft/1.15.1/mojmap/`:

- `net/minecraft/client/Minecraft.java`: `runTick`, lines 840-847.
- `net/minecraft/client/MouseHandler.java`: `turnPlayer`, lines 192-223.
- `net/minecraft/world/entity/Entity.java`: rotation fields, lines 119-129; `turn`, lines 326-337; tick previous-state copy, lines 358-360; `getLookAngle`, lines 1661-1663.
- `net/minecraft/util/Mth.java`: float `clamp`, lines 98-104.
- `net/minecraft/client/player/LocalPlayer.java`: `aiStep` and Elytra jump gate, lines 625-723; parent AI call, line 778.
- `net/minecraft/world/entity/player/Player.java`: `travel`, lines 1456-1485.
- `net/minecraft/world/entity/LivingEntity.java`: active fall-flying travel, lines 1831-1871; AI travel order, lines 2282-2288.

No build, test, runtime launch, or source generation was performed.

## Phase-two comparison with the frozen source report

The compared report is `c-raw-pitch-setter-115-1151-source/report.md`, SHA-256 `a4a286452ca6108fadee6583f5df69851103bcfc6636714d97ef48b806bcae93`. Before this append, this verdict's complete bytes matched the authorized original-prefix SHA-256 `9720026c554f561c9f888e87bf4a93173587d6888245544136588f52cd6f8110`.

The report agrees with this audit on the exact operation, release pair, source identity, double increment and float narrowing, immediate current-pitch clamp, independent previous-pitch update and clamp, and live current-pitch read in dry active Elytra travel. It adds an explicit ordinary input witness: with the stated sensitivity, raw vertical delta 1000 supplies a finite increment around 150, narrowed to a finite float before clamping to 90. That witness is above the clamp but not above 360. The report also establishes an uncapped mouse-delta path; scaling the same stated input to raw vertical delta 3000 gives a finite increment around 450, so the first float pitch write is finite and above 360 before the same final clamp to 90. This is source-arithmetic inference from the report's formula, not a runtime observation. No contradiction was found; the verdict remains limited to this source slice.