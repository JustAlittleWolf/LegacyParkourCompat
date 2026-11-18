# Minecraft 1.15.1 → 1.15.2 pitch setter source slice

## Evidence identity and integrity

Read-only canonical source roots: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.15.1\mojmap` and `...\1.15.2\mojmap`. Both root `mojmap.ready.json` markers state `ready`, exact version IDs, and `mojmap`; each declared source-manifest SHA-256 was recomputed from disk. Every source-file digest below was independently recomputed and matches its entry in that version's `mojmap.sources.sha256`.

| Version | Ready marker SHA-256 | Provenance SHA-256 | Source-manifest SHA-256 | Artifact-manifest SHA-256 | Source files / bytes | Client SHA-256 |
| --- | --- | --- | --- | --- | ---: | --- |
| 1.15.1 Mojmap | `afbb16c3cb832ffee8e4763bf9933d9635ecd811c9fbd7166554b78e59788504` | `6a7af1f5d65d7af165c2ab9a90c1fae20169b38e70c8e92dbc5f59d94efe9461` | `c8fdce2f9432b72fc13c19a48f9ab3be203cac41a617139e81bac3552245912d` | `d2c40f0770820752fe879670b35bd718b87e4f14491b3ec782e2e74df6b38bbb` | 3,329 / 17,524,592 | `6e28ecd008d0ca5e1dcd346dbc2882430f02a77a78864d57acc49fc8bc1b5358` |
| 1.15.2 Mojmap | `64a0e40b784d525ce50937c63e465fc4f564efb1f7af17f2bac3c77a36907be0` | `d4802d35ee2927a44d753871f184c3255c060eb94457a5c65a8bc121a087954e` | `cb7fd93f8730d8b2813857744f8572060535c7de44fcee86222acd1ceb6f43e7` | `208ab867640097a0c188e452de4876934deb217d75ac726d358cfa6730260406` | 3,332 / 17,543,871 | `4a73008a73f3824b7c711750a5a37556df8614f193c0a531e292dad159a73a7c` |

The 1.15.1 ready marker references and binds its provenance digest. The 1.15.2 root has a separate `mojmap.provenance.json` (digest above), but its ready marker does not contain a provenance field/digest; its ready marker does bind the source and artifact manifests and client hash. Both markers declare the exact version metadata ID and mapping. No source generation or online mapping lookup was performed.

| Mojmap source file | 1.15.1 SHA-256 | 1.15.2 SHA-256 |
| --- | --- | --- |
| `net/minecraft/world/entity/Entity.java` | `191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e` | `191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e` |
| `net/minecraft/world/entity/LivingEntity.java` | `139c3866ad2c6a1ad73496f22ff1a4b5259998442092ffbafb8664428e28bf79` | `46d243bb7e51f7b54404aa1d7d6e6b827682d0f5925d02847c4193306c4d5e54` |
| `net/minecraft/world/entity/player/Player.java` | `6985e11ad9be553e9a2f25e5decc20a9da5c3b59b68f4e83a5b9f448cd2e48a0` | `1ba2724c22163862b8f7fdfdea5a04a66e4db26a119d7e5360ba024724a34793` |
| `net/minecraft/client/player/LocalPlayer.java` | `7227da5233e3c72ced008187b92a6a86842f84778eaa9f40fa1eb117286af823` | `3a9019bd7b860e251c23fd8d0cd70b7f5b38566d34470c4e29b1014ef689ccbd` |
| `net/minecraft/client/MouseHandler.java` | `925ef4e75c1da041e078eb71b6bc79180ebbb1329ce9d18efb5203777fd1fe5b` | `925ef4e75c1da041e078eb71b6bc79180ebbb1329ce9d18efb5203777fd1fe5b` |

## Bounded comparison

**`Entity.turn(double d, double e)` — both versions, `net/minecraft/world/entity/Entity.java:326–338`.** The complete method body is byte-for-byte equal across this pair. The pitch argument is `double`; first `f = e * 0.15` is computed in double precision. The live pitch write is `(float)(this.xRot + f)`, so the prior float pitch is promoted for the addition and the result is narrowed to float. It then changes yaw, clamps live pitch to `[-90.0F, 90.0F]`, adds the same double `f` to `xRotO` and narrows that result, changes old yaw, then separately clamps `xRotO` to the same range. It does not copy final live pitch into previous pitch. Passenger notification follows both clamps. No 1.15.1→1.15.2 difference was found in signature, precision, casts, writes, or order.

**Finite caller-side argument witness, not a live-pitch result.** In both exact `MouseHandler.turnPlayer()` bodies (`MouseHandler.java:192–223`), the unsmoothed branch computes `k = accumulatedDY * g`, clears the accumulators, applies `n` as either `+1` or `-1`, and calls `player.turn(j, k * n)` without an argument clamp. For the finite ordinary sensitivity value `0.5F`, `f = sensitivity * 0.6F + 0.2F` is `0.5F` and `g = f * f * f * 8.0` is `1.0`; a finite `accumulatedDY = 2401.0` therefore yields `e = ±2401.0` at the call and `f = ±360.15` in `Entity.turn`. This is a caller-arithmetic witness conditional on that finite accumulator value. The callback/device producer and scheduling are outside this slice, so this report does not claim or validate a particular hardware event. The setter's clamp means this witness cannot leave live pitch above 90 degrees: with initial pitch zero, live and previous pitch each clamp to the corresponding signed 90-degree bound.

**Live-pitch consumer on the local Elytra travel path.** `LocalPlayer.aiStep()` calls `super.aiStep()` (`LocalPlayer.java:778`); the `LivingEntity.aiStep()` inherited call chain reaches `this.travel(...)` (`LivingEntity.java:2281–2288`). `Player.travel(...)` calls `super.travel(vec3)` in both its abilities-flying and ordinary branches (`Player.java:1456–1485` in 1.15.1; `1448–1477` in 1.15.2). In `LivingEntity.travel`, after the effective-AI/local-control gate and the water/lava exclusions (each retaining the existing player-flying exception), the `isFallFlying()` branch obtains the look vector and reads live `this.xRot` as `l = this.xRot * (float)(Math.PI / 180.0)` (`LivingEntity.java:1831–1854`). Its first movement call in that branch is `this.move(MoverType.SELF, this.getDeltaMovement())` after the Elytra velocity updates (`:1855–1871`). This exact `LivingEntity.travel` method body is equal in both versions. The first active local Elytra movement therefore consumes live pitch directly; it does not first normalize pitch in this branch. This comparison establishes the read/order only, not a reachable local Elytra state with pitch outside the setter's clamp; NBT, teleport, networking, callbacks and other pitch writers were not investigated per assignment bounds.

## Result / boundary

No pitch-setter or Elytra-travel difference is present in the requested 1.15.1→1.15.2 slice. The exact `Entity.turn`, `LivingEntity.travel`, `Player.travel`, and `MouseHandler.turnPlayer` method bodies compare equal. Caller-side arithmetic admits a conditional finite argument greater than 360, but `turn` clamps both live and previous pitch afterward. No callback/device scheduling, rendering, teleport or network paths were analyzed.
