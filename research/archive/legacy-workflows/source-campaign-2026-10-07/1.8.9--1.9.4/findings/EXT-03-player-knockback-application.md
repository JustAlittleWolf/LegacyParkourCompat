# EXT-03: player knockback application changes

- Older version A: 1.8.9
- Newer version B: 1.9.4
- Mechanic / coverage slice IDs: direct player knockback velocity response; `EXT-03`, `INV-STATE`, `INV-EXTERNAL`
- Classification: source-confirmed player velocity response change; damage resolution and resulting trajectory not evaluated
- Confidence: source-confirmed for paired knockback writers and their in-scope player call paths
- Applicability: a player `LivingEntity` reaches `applyKnockback()` and passes the knockback-resistance random gate; Y response additionally depends on B's `onGround`
- First changed release: unknown within (1.8.9, 1.9.4]
- Runtime validation: not performed

## Paired evidence

- A `LivingEntity.applyKnockback(Entity, float, double, double)V`, lines 763-777, sets `velocityDirty`, computes the horizontal direction length, halves all three current velocity components, subtracts normalized horizontal direction times fixed `0.4F`, adds fixed `0.4F` vertically, and caps positive vertical velocity at `0.4F`. The method is called on the damaged receiver from `takeDamage()` lines 681-690. A `LivingEntity.java` SHA-256 `082831c6578e3a70fa6cea5b90bc3eefc26678259b66334470de22b90b5b0e4e`.
- B `LivingEntity.applyKnockback(Entity, float, double, double)V`, lines 870-885, sets `velocityDirty`, computes the same horizontal direction length, halves only X/Z, subtracts normalized horizontal direction times the supplied `amount`, and changes Y only if `onGround`: halve current Y, add `amount`, and cap positive Y at `0.4F`. B `takeDamage()` calls it on the damaged receiver at lines 765-773. B `LivingEntity.java` SHA-256 `bbb7703f18fd5da05c4e4a43a77ea644b388e63c01d34166d308ea52054be4e`.
- Player reachability: `PlayerEntity` extends `LivingEntity` at A line 79 (SHA-256 `e66cb294fc93118148a444bbafdf4dd57cbf66a23d69b1e8892cefccc690ab88`) and B line 92 (SHA-256 `d658a0d95452d12bb7e347bfd802240eeecaf7f938e10dcd43e2640434387f85`). In B, the shield-bypass branch also calls `applyKnockback()` on the living attacker at lines 707-709, which can directly alter an attacking player's velocity; A has no matching source-attacker call in its paired `takeDamage()` path. These call sites establish reachability only; attack and damage resolution remain excluded.

## Source-level difference

For the same knockback-resistance outcome and direction inputs, A always halves and adds to Y and uses fixed `0.4F` for horizontal and vertical response. B leaves Y unchanged in air, applies its vertical half/add only on ground, and uses the passed `amount` for the horizontal and grounded vertical response, subject to the same `0.4F` upward cap. Both set the dirty flag before applying the response. B additionally routes a shield-bypass recoil response to a living attacker under its source guard.

These are direct velocity writes on a player instance when the described call receiver is a player. No damage amount, attack outcome, collision result, or subsequent player trajectory is inferred.

## Handoff

Source finding only. Preserve for full-pair reconciliation; no implementation or runtime claim is made.
