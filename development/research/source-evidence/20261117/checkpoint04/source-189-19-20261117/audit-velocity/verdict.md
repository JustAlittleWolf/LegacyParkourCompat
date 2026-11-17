# Independent source audit — small velocity component cutoff

Verdict: **ACCEPT** for the immutable candidate revision SHA-256 `2813369b5f70c5a2e1aa6be2122690c3df9c6223ae2342905624d1285170c0ce`.

## Evidence identity and integrity

- 1.8.9 Ornithe Feather source manifest SHA-256: `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004`; provenance SHA-256: `ba066fc8f1c7ceb9819d2106338d9e9f2c2b96eb5a8329eb40e171b1f9889185`.
- 1.9 Ornithe Feather decompile provenance SHA-256: `8daacb7af8f210e716ee4b34c43b8a5d6f6b4b19831d2fd725cbe16ecf330a1a`.
- The 1.8.9 source manifest entry for `net/minecraft/entity/living/LivingEntity.java` matches the file SHA-256 `082831c6578e3a70fa6cea5b90bc3eefc26678259b66334470de22b90b5b0e4e`. The 1.9 provenance `sources` entry for that path matches its file SHA-256 `5b5936656aacc64a4d75db7b370985f9456ac96076331da047bd246f76a705fb`.
- The candidate file hash was recomputed and matches the assigned revision.

## Audit findings

Both `LivingEntity.mobTick()` bodies perform the cutoff as three separate ordered blocks: X, then Y, then Z. In 1.8.9 (`decompiled_minecraft/1.8.9/ornithe-feather/net/minecraft/entity/living/LivingEntity.java:1406-1416`) each checks `Math.abs(velocity*) < 0.005` and writes `0.0`; in 1.9 (`decompiled_minecraft/1.9/ornithe-feather/net/minecraft/entity/living/LivingEntity.java:1653-1663`) the same form uses `0.003`. Both comparisons are strict. `Entity.velocityX/Y/Z` are `double` in both releases (`Entity.java:67-69` and `:94-96` respectively), so `Math.abs` and the decimal literals are double operations; no cast or intermediate float conversion is present. The claimed interval is therefore correct, including retention at exactly `0.003` in 1.9 and clearing at exactly `0.003` in 1.8.9.

The local player reaches this code during its normal tick in both versions: `LocalClientPlayerEntity.tick()` calls `super.tick()` (`LocalClientPlayerEntity.java:105-107` in 1.8.9; `:141-143` in 1.9), and `LivingEntity.tick()` invokes `this.mobTick()` (`LivingEntity.java:1296` / `:1538`). The local-player `mobTick()` override does its client work and then calls `super.mobTick()` (`LocalClientPlayerEntity.java:632` / `:750`). `LocalClientPlayerEntity.isLocallyControlled()` returns true (`:381-383` / `:443-445`). After the cutoffs, `LivingEntity.mobTick()` reaches `moveRelative()` in that same call (`LivingEntity.java:1450` / `:1698`). The local-control gates in `moveRelative()` admit this player (`:1120` / `:1290`); in the ordinary dry movement branch, `moveRelative()` calls `this.move(this.velocityX, this.velocityY, this.velocityZ)` in the same tick (`:1165` / `:1378`). Thus an unblocked residual component in the stated interval can affect the requested displacement and resulting position in the same reachable tick in 1.9, whereas the cutoff removes that component before movement in 1.8.9. `Entity.move()` resolves the request through gathered collision boxes in both releases (`Entity.java:439-446` in 1.8.9; `:506-514` in 1.9); collisions can constrain the resulting position, consistent with the candidate's conditional “can contribute” wording.

The candidate's thresholds, order, local-player path, same-tick effect, and wording are source-supported. No unresolved dependency remains for this bounded claim. This verdict does not audit full-pair movement coverage or prove runtime parity.