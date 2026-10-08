# F-16: Wind Burst attacker impulse depends on sweep-target damage acceptance

- Older version A: 1.21.4
- Newer version B: 1.21.5
- Mechanic / coverage slice IDs: S6-ENCHANTMENTS, S6-ATTRIBUTES, S7-PUSH
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: player movement response to a swept attack with Wind Burst
- First changed release: unknown within (1.21.4, 1.21.5]
- Runtime validation: not performed

## Paired evidence

- A: `ready/1.21.4/mojmap.sources.sha256`; `Player#attack` sweep loop lines 1209-1223, especially `hurt` at 1219 followed by server-side `EnchantmentHelper.doPostAttackEffects` at 1220-1222, unconditionally after the hurt return value is ignored. `Player.java` SHA-256 `c45f41b9784ce50a88a498e84a13edcef0a23e175233d726ed7f90940f19ebc1`.
- B: `ready/1.21.5/mojmap.sources.sha256`; `Player#attack` sweep loop lines 1174-1184, where both `knockback` and `EnchantmentHelper.doPostAttackEffects` are nested in `level() instanceof ServerLevel && hurtServer(...)`. When `hurtServer` returns false, the post-attack enchantment dispatch is skipped. `Player.java` SHA-256 `8fc187f33999db9dfc49251e95e92a17645a50ad16ca0f93f4948209f62036`.
- Shared dispatch: A/B `EnchantmentHelper#doPostAttackEffects` lines 190-221 invokes victim equipment effects and, for a living damage-source entity, iterates its weapon enchantments as `EnchantmentTarget.ATTACKER` (A hash `5926f519897e629b369754a33fcd338140c57b930bb908bbd738ed430e38423c`; B hash `b1c8ce1ef431d57488b4c6f991aa01e9d8372adaa077df8ff55a419d9b9448a0`). `Enchantment#doPostAttack` lines 260-280 selects matching targeted effects and resolves `affected == ATTACKER` from `DamageSource.getEntity()`; source hash matches in both versions: `67394bfb374d6f5bcd989dc46aa7353379a5bcfb767fcf5e2950c7ec485482e4`.
- Wind Burst: A/B `Enchantments.java` lines 1158-1193 register a main-hand mace `POST_ATTACK` effect targeting `ATTACKER`; the condition requires the direct attacker not be flying and have fall distance at least `1.5`. The configured `ExplodeEffect` has no damage type, radius `3.5F`, and the `BLOCKS_WIND_CHARGE_EXPLOSIONS` block set. A/B `Enchantments.java` hashes: `01bdf94faa8089e50a282ec0b50c2b9956ddfb4260ae37dd9ec23e460e9d1bd1` / `ee37699b95dc530fdd91dadef27d19a54bc7c4febe79bca40cc1ee438a626356`. Paired client resource `data/minecraft/enchantment/wind_burst.json` hash: `d0fbe1c97f977fb24a6e293b8097a5aa9ecfc4a6c86bc741fcf51d1d5be47537`; `data/minecraft/tags/item/enchantable/mace.json` hash: `8678d6ef9b0cd32527d2b49f7e0230b26f18f5409de32bb9c4e7b603882f5306`.
- Shared movement consumer: A/B `ExplodeEffect#apply` lines 56-77 calls `ServerLevel.explode` at the affected attacker's position with the configured radius and knockback multiplier; `ExplodeEffect.java` SHA-256 matches: `c62612e19828b0684973b6ffb420cc891dd78526b5f92f0e9d1a71413930e367`. `ServerExplosion` lines 180-215 computes exposure-weighted knockback, reads `EXPLOSION_KNOCKBACK_RESISTANCE`, then calls `Entity.push`; the Player impulse is retained for server motion synchronization. Its A/B hash matches: `d49f486abfa0d97a8da26f763c51c917c8b1f8064588962f21a3b113002c4f71`. The paired `SimpleExplosionDamageCalculator` hash also matches: `65728f68493bf507bba7db3b66c517db8bb559caef01db70667d17139e53681a`; it returns zero explosion knockback for abilities-flying Players.
- Blast Protection is a movement-modifier dependency on this path: paired `Enchantments.java` lines 227-243 add `EXPLOSION_KNOCKBACK_RESISTANCE` by `0.15F` per level; `data/minecraft/enchantment/blast_protection.json` hash is `476b06d6f27a1a221f93e1c6c21137a04875f84dbc6c736eaea0bc1b5753f7ea` in both client jars.

## Source-level difference

For a server-side player sweep attack where the target's `hurt`/`hurtServer` result is false, A still runs post-attack enchantment effects, while B skips them. If the attacking player holds a Wind Burst mace and satisfies the non-flying, fall-distance-at-least-1.5 condition, A therefore runs Wind Burst's explosion at the attacker and applies its direct player impulse; B does not. The source difference concerns the attack-effect dispatch gate. Damage calculation and outcomes are not emulated by this finding.

## Reachability and dependencies

The player must reach the sweep-attack loop, the target must reject the swept `hurt` attempt in B, and the attacker must satisfy Wind Burst's direct-attacker predicate. The effect path then runs on the server through the attacker weapon's enchantment, `ExplodeEffect`, `ServerExplosion`, and `Entity.push`. The player impulse is suppressed when abilities-based flying is active and is scaled by explosion exposure and `EXPLOSION_KNOCKBACK_RESISTANCE`. This is separate from the primary-target hit path, whose dispatch site remains outside this bounded finding.

## Consequence and uncertainty

In the stated branch, A can apply a Wind Burst impulse to the attacking player after a rejected sweep-target hit; B cannot. The exact trajectory and downstream persistence are not established here. Runtime validation was not performed.

## Snapshot source/artifact identity

- A source manifest SHA-256 `f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0`; artifact manifest SHA-256 `1a0929ca8c88cfe7874f323918dfa3b044964007d3ff0d3943317bdb35caf841`; client jar SHA-256 `c17c450c6e72cc51297daa57ce38f800aa01cf022b743daa21a0512d326d894e`.
- B source manifest SHA-256 `365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9`; artifact manifest SHA-256 `d35b1b9389d8958e33893faa63d94c86f22af30385233ca947fd410aa5c36656`; client jar SHA-256 `522672ad20b460c02c2e39b6c5035ef6a849af28eb11ab2ac7eb293d395a8c11`.

## Handoff

Independent delta: the 1.21.5 sweep-attack damage-acceptance gate suppresses the attacker's Wind Burst movement effect after a rejected swept hit. Related slices: S6-ENCHANTMENTS, S6-ATTRIBUTES, S7-PUSH. Pending independent source review; no implementation handoff.
