# F-16: Wind Burst attacker impulse depends on sweep-target damage acceptance

- **1.21.4**, mojmap: `Player.java`; lines 1209-1223.
- **1.21.5**, mojmap: `Player.java`; lines 1174-1184.

## Difference

For a server-side player sweep attack where the target's `hurt`/`hurtServer` result is false, 1.21.4 still runs post-attack enchantment effects, while 1.21.5 skips them.

If the attacking player holds a Wind Burst mace and satisfies the non-flying, fall-distance-at-least-1.5 condition, 1.21.4 therefore runs Wind Burst's explosion at the attacker and applies its direct player impulse; 1.21.5 does not.

The source difference concerns the attack-effect dispatch gate.

Damage calculation and outcomes are not emulated by this finding.

## Movement meaning

The player must reach the sweep-attack loop, the target must reject the swept `hurt` attempt in 1.21.5, and the attacker must satisfy Wind Burst's direct-attacker predicate.

The effect path then runs on the server through the attacker weapon's enchantment, `ExplodeEffect`, `ServerExplosion`, and `Entity.push`.

The player impulse is suppressed when abilities-based flying is active and is scaled by explosion exposure and `EXPLOSION_KNOCKBACK_RESISTANCE`.

This is separate from the primary-target hit path, whose dispatch site remains outside this bounded finding.

In the stated branch, 1.21.4 can apply a Wind Burst impulse to the attacking player after a rejected sweep-target hit; 1.21.5 cannot.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.21.4--1.21.5/findings/F-16-wind-burst-sweep-hit-gate.md).
