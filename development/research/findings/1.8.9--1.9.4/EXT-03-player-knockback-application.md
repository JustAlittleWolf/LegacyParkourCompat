# EXT-03: player knockback application changes

1.8.9, ornithe-feather; feather-gen2-1.8.9+build.2-mergedv2.jar, feather-gen2-1.8.9+build.2.tiny: `1.8.9/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `public void applyKnockback(Entity entity, float amount, double velocityX, double velocityZ) {`, lines 763-778.
1.9.4, ornithe-feather; feather-gen2-1.9.4+build.2-mergedv2.jar, feather-gen2-1.9.4+build.2.tiny: `1.9.4/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `public void applyKnockback(Entity entity, float amount, double velocityX, double velocityZ) {`, lines 870-886.

Difference: For the same knockback-resistance outcome and direction inputs, 1.8.9 always halves and adds to Y and uses fixed `0.4F` for horizontal and vertical response. 1.9.4 leaves Y unchanged in air, applies its vertical half/add only on ground, and uses the passed `amount` for the horizontal and grounded vertical response, subject to the same `0.4F` upward cap.

Both set the dirty flag before applying the response. 1.9.4 additionally routes a shield-bypass recoil response to a living attacker under its source guard.

These are direct velocity writes on a player instance when the described call receiver is a player.

No damage amount, attack outcome, collision result, or subsequent player trajectory is inferred.

Movement meaning: For the same knockback-resistance outcome and direction inputs, 1.8.9 always halves and adds to Y and uses fixed `0.4F` for horizontal and vertical response. 1.9.4 leaves Y unchanged in air, applies its vertical half/add only on ground, and uses the passed `amount` for the horizontal and grounded vertical response, subject to the same `0.4F` upward cap.

Both set the dirty flag before applying the response. 1.9.4 additionally routes a shield-bypass recoil response to a living attacker under its source guard.

These are direct velocity writes on a player instance when the described call receiver is a player.

No damage amount, attack outcome, collision result, or subsequent player trajectory is inferred.


## Current evidence and remaining work

Source decisions, implementation coverage and runtime parity are separate. Source paths are relative to `decompiled_minecraft/`; immutable Git evidence can be read with `git show <revision>:<path>`.

```json
{
  "implementation": {
    "status": "open",
    "changes": [],
    "hooks": [],
    "limits": [
      "Complete current Change/catalog inventory inspected; no dedicated operation implements EXT-03: player knockback application changes; later broadly related hooks do not establish this claimed delta."
    ]
  },
  "applicability": [],
  "sources": [
    {
      "version": "1.8.9",
      "mapping": "ornithe-feather; feather-gen2-1.8.9+build.2-mergedv2.jar, feather-gen2-1.8.9+build.2.tiny",
      "path": "1.8.9/ornithe-feather/net/minecraft/entity/living/LivingEntity.java",
      "member": "public void applyKnockback(Entity entity, float amount, double velocityX, double velocityZ) {",
      "lines": "763-778",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 082831c6578e3a70fa6cea5b90bc3eefc26678259b66334470de22b90b5b0e4e; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 8608313e1cbc82f83aee3b43679022a4450672710c3819dd00980ac2d395ff43."
    },
    {
      "version": "1.9.4",
      "mapping": "ornithe-feather; feather-gen2-1.9.4+build.2-mergedv2.jar, feather-gen2-1.9.4+build.2.tiny",
      "path": "1.9.4/ornithe-feather/net/minecraft/entity/living/LivingEntity.java",
      "member": "public void applyKnockback(Entity entity, float amount, double velocityX, double velocityZ) {",
      "lines": "870-886",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 bbb7703f18fd5da05c4e4a43a77ea644b388e63c01d34166d308ea52054be4e5; not bound to a source-file digest in this record. Checked full declaration/body; snippet SHA-256 0cb8c97dca7074022a0339309b97b1b1bb6fa62839ef81f7f065051192a05d97."
    }
  ],
  "sourceReviews": [],
  "openIssues": [
    {
      "missing": "Later applicability gate: exact introduction of campaign-EXT-03-player-knockback-application is unknown within (1.8.9, 1.9.4]",
      "next_action": "Preserve any exact historical verdict for its bounded assertion; verify intervening releases separately before assigning a new implementation range. Formatting alone requires no renewed acceptance."
    },
    {
      "missing": "Current implementation of campaign-EXT-03-player-knockback-application",
      "next_action": "After expert scope review and independent source acceptance, assign this specific operation to the separate implementation phase."
    },
    {
      "missing": "Exact independent source verdict/snapshot identity for EXT-03-player-knockback-application",
      "next_action": "Retain original source-confirmed status only; source-acceptance owner must resolve a preserved independent verdict or review this exact bounded assertion in the later campaign."
    }
  ],
  "runtime": "unverified"
}
```
