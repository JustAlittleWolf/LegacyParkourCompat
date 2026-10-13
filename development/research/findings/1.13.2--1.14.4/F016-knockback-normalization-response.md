# F016: Knockback response normalization

1.13.2, ornithe-feather: `1.13.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `applyKnockback`, lines 1011–1027.
1.14.4, ornithe-feather: `1.14.4/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `applyKnockback`, lines 1208–1215.

Difference: Both versions first apply the same random knockback-resistance guard and set `velocityDirty`. 1.13.2 computes a float horizontal magnitude with `MathHelper.sqrt(x*x + z*z)`, halves current X/Z velocity, then subtracts each incoming component divided by that magnitude times `amount`. There is no zero-length check. 1.14.4 constructs `(x,0,z)`, calls `Vec3d.normalize()` (which returns ZERO when its length is below `1.0E-4`), scales by `amount`, and subtracts it from half the current horizontal velocity. For a zero or sufficiently small input vector, 1.14.4's direction term is zero while 1.13.2's division can produce non-finite components. 1.13.2's grounded Y path halves then adds `amount` and caps above `0.4F`; 1.14.4 evaluates `Math.min(0.4, oldY/2 + amount)` using the double literal.

Movement meaning: This is the direct response method inherited by players. 1.13.2 call reaches it only if the resistance random check passes.


## Current evidence and remaining work

Source decisions, implementation coverage and runtime parity are separate. Source paths are relative to `decompiled_minecraft/`; immutable Git evidence can be read with `git show <revision>:<path>`.

```json
{
  "implementation": {
    "status": "open",
    "changes": [],
    "hooks": [],
    "limits": [
      "Current annotated Change/interface/catalog inventory contains no operation implementing: F016: Knockback response normalization. Existing unrelated later operations do not establish coverage."
    ]
  },
  "applicability": [
    "Endpoint interval (1.13.2, 1.14.4]; exact cutover remains the original evidence’s separate applicability gate."
  ],
  "sources": [
    {
      "version": "1.13.2",
      "mapping": "ornithe-feather",
      "path": "1.13.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java",
      "member": "applyKnockback",
      "lines": [
        [
          1011,
          1027
        ]
      ],
      "status": "verified-content",
      "sha256": "bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c",
      "detail": "Canonical source read. Matches digest in archived original."
    },
    {
      "version": "1.14.4",
      "mapping": "ornithe-feather",
      "path": "1.14.4/ornithe-feather/net/minecraft/entity/living/LivingEntity.java",
      "member": "applyKnockback",
      "lines": [
        [
          1208,
          1215
        ]
      ],
      "status": "verified-content",
      "sha256": "2cccf4331ce9e62013eeb8e96163e5e146e1291619eb998ce9867daa87c02b61",
      "detail": "Canonical source read. Matches digest in archived original."
    }
  ],
  "sourceReviews": [],
  "openIssues": [
    {
      "missing": "1.13.2 original Feather mapped-client JAR d118ff5b9eb93fed06680466cb26c41a30647cdb2fc8744638cf9b35cf7864e5 remains unavailable; revised source equality does not prove original derived-JAR equivalence.",
      "next_action": "Retain existing Feather-r1 provenance caveat; original equivalence requires the exact preserved artifact, not regeneration during preparation."
    }
  ],
  "runtime": "unverified"
}
```
