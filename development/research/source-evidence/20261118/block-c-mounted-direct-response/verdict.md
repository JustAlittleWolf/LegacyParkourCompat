# Independent verdict: mounted direct player response

## Machine binding

```json
{
  "verdict": "ACCEPTED_WITH_LIMITS",
  "terminal": "COMPLETED",
  "audit_scope": "exact-source audit of mounted local passenger direct movement response; no vehicle trajectory claim",
  "inputs": {
    "prior_B_source_audit": {
      "path": "C:/Users/Wolfi/.codex/visualizations/2026/11/18/01a1f01a-693d-7972-bfe2-805d6db43b98/domain-b-mounted-clip-source/source-audit.md",
      "sha256": "b5a38759285eaea0dc60dbd2803e445356a8a0f07b0246269af4309ceed509d5"
    },
    "frozen_C_comparison": {
      "path": "C:/Users/Wolfi/.codex/visualizations/2026/11/18/01a1f01a-5064-75c0-a594-b2e7fa3b99e2/c-mounted-direct-response/comparison.md",
      "sha256": "93c97731a8945a4bddaed1011f877e54334df0c502c42f5a2d708a2b9210b474e"
    },
    "reused_claim": "ordinary 1.8.9 local mounted boat horizontal displacement <= 0.35 per tick; seated geometry and carrier-before-rider order; no x/z axis separation under that bound",
    "exact_sources": {
      "1.8.9": "Ornithe Feather",
      "1.9": "Ornithe Feather",
      "26.2": "unobfuscated"
    }
  },
  "claims": {
    "modern_same_root_exclusion": "confirmed",
    "old_direct_player_horizontal_own_boat_effect_under_bound": "none established",
    "modern_direct_player_horizontal_own_boat_effect_under_bound": "excluded before shape resolution",
    "vertical_exceptional_separated_state_and_consuming_sweep": "not proven"
  },
  "limitations": [
    "No comparison of complete native player movement algorithms.",
    "No whole-trajectory or unrelated-collider conclusion.",
    "No vehicle physics, non-player behavior, external relocation, or server correction claim.",
    "No intermediate patch boundary inferred."
  ]
}
```

## Evidence and independent audit

The bound B audit and frozen C comparison hashes above were recomputed and match. Canonical modern source hashes also match the B audit for `Entity.java` (`7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dfaff869bb811d5`), `EntityGetter.java` (`e37ea40ed0f9ebb8234addc98a5e05271bfad2fe9ffce5a7babf3bfaaa700d29`), `LivingEntity.java` (`7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a`), `AbstractBoat.java` (`9e69c4d86df7da415b84b1206f7539b95dcd89af050bb6ebff33a572b2c4064e`), `Player.java` (`8decc71b9c780664578ddb14591db2a2f207c72c05b676edded6f8e964576531`), and `LocalPlayer.java` (`8d089aa09217e3607b38590f7c1623385562800943ac6dfd3d17804e041da6d6`). The 1.8.9 canonical `Entity.java` hash also matches the B audit (`d4c10932cb5bb1067a5a58be4e1bb1b42bdde3b6fc893d88178cc07435a1696b`).

In 26.2, `EntityGetter.getEntityCollisions` at lines 54–60 filters candidates with `source::canCollideWith` before producing collision shapes. `Entity.canCollideWith` at lines 2373–2375 rejects `isPassengerOfSameVehicle`, and lines 3576–3578 define that predicate by equal root vehicles. `AbstractBoat.canVehicleCollide` at lines 109–115 has the same exclusion. Consequently, the passenger's own boat is absent from the local player's `Entity.move` collision candidates even if the seat transform is stale. The direct movement path remains present: `LivingEntity` calls `move(MoverType.SELF, ...)` at lines 2676–2679, and `Entity.collide` gathers entity candidates at lines 1141–1144. The own-boat exclusion makes its direct-move collision contribution zero, including any clipped-vector or resulting velocity/position response from that candidate.

In 1.8.9, `Entity.rideTick` zeros passenger velocity, ticks the passenger, and only then updates the rider position (lines 1275–1285). `LivingEntity.moveRelative` reaches `move(velocityX, velocityY, velocityZ)` (as already bound in B/C). The accepted local carrier bound and the seated dimensions put the boat and stale passenger boxes overlapping along both horizontal axes, so the old candidate cannot be an x- or z-axis obstacle for that direct player move. This supports only the own-boat contribution: it does not compare whole movement outcomes, unrelated collisions, or full trajectories.

## Vertical stale interval

The old player source allows a positive vertical movement request through its jump gate; the prior bound review identifies the gate as `jumping && onGround && jumpingCooldown == 0`, then passes the resulting velocity to the player move. From the accepted seated intervals, a vertical collision would require a vertically separated boat and a player sweep toward it: e.g. boat displacement upward beyond 1.15 before the query plus a positive player sweep, or boat displacement downward beyond 0.65 plus a negative player sweep. The exact source and supplied audits do not establish an ordinary reachable local mounted state satisfying either paired condition. They therefore do not prove any concrete exceptional vertical stale-interval separated consuming sweep. This is unresolved reachability, not a claim that such a state is impossible.

## Final disposition

The modern same-root exclusion is independently confirmed. Under the accepted ordinary horizontal bound, no old own-boat x/z clip is established, and the modern own boat cannot participate in the direct player collision response. No horizontal own-boat-induced direct movement difference is established under those inputs. The vertical exceptional case remains unproved. This verdict applies only to the bound direct-response question and does not make claims about complete trajectories or vehicle physics.
