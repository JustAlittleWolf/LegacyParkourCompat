# Hidden movement-effect restoration

1.14.4, mojmap: `1.14.4/mojmap/net/minecraft/world/effect/MobEffectInstance.java`, `update`, lines 54–120.
1.15.2, mojmap: `1.15.2/mojmap/net/minecraft/world/effect/MobEffectInstance.java`, `update`, lines 66–147.
1.14.4, mojmap: `1.14.4/mojmap/net/minecraft/world/entity/LivingEntity.java`, `tickEffects, addEffect`, lines 605–662, 761–777.
1.15.2, mojmap: `1.15.2/mojmap/net/minecraft/world/entity/LivingEntity.java`, `tickEffects, addEffect`, lines 573–630, 729–745.

Difference: If a shorter, stronger Speed or Slowness instance replaces a weaker instance with more remaining duration, 1.15.2 keeps the weaker instance and counts its remaining duration down behind the active one. When the stronger instance expires, 1.15.2 restores the weaker amplifier and refreshes the movement-speed attribute modifier immediately. 1.14.4 discards the weaker instance, so expiry removes the movement-speed modifier entirely. This changes the player's movement speed after the stronger effect ends. The same restoration also resumes Jump Boost, Slow Falling, Levitation, Dolphin’s Grace and Blindness; their existing movement readers consume the restored active instance.

Movement meaning: Restored Speed/Slowness modifiers change movement speed; restored active effects change existing jump/travel/sprint predicates. Server modifier refresh and client active-instance replacement are separate paths.


## Current evidence and remaining work

Source decisions, implementation coverage and runtime parity are separate. Source paths are relative to `decompiled_minecraft/`; immutable Git evidence can be read with `git show <revision>:<path>`.

```json
{
  "implementation": {
    "status": "open",
    "changes": [],
    "hooks": [],
    "limits": [
      "Current annotated Change/interface/catalog inventory contains no operation implementing: F08: 1.15.2 restores a hidden weaker movement effect. Existing unrelated later operations do not establish coverage.",
      "Current annotated Change/interface/catalog inventory contains no operation implementing: F14: 1.15.2 resumes hidden movement-affecting effects. Existing unrelated later operations do not establish coverage."
    ]
  },
  "applicability": [
    "Endpoint interval (1.14.4, 1.15.2]; exact cutover remains the original evidence’s separate applicability gate."
  ],
  "sources": [
    {
      "version": "1.14.4",
      "mapping": "mojmap",
      "path": "1.14.4/mojmap/net/minecraft/world/effect/MobEffectInstance.java",
      "member": "update",
      "lines": [
        [
          54,
          120
        ]
      ],
      "status": "verified-content",
      "sha256": "80cc2a91b77d4687a9b36bcb38e3107e3b2b8f859c69893e4eec0e98b4941b5e",
      "detail": "Canonical source read. Matches digest in archived original."
    },
    {
      "version": "1.15.2",
      "mapping": "mojmap",
      "path": "1.15.2/mojmap/net/minecraft/world/effect/MobEffectInstance.java",
      "member": "update",
      "lines": [
        [
          66,
          147
        ]
      ],
      "status": "verified-content",
      "sha256": "4999d12cd468e402506f067dbd8694b1c8125dd9131e4f8a2fcc1848f616f180",
      "detail": "Canonical source read. Matches digest in archived original."
    },
    {
      "version": "1.14.4",
      "mapping": "mojmap",
      "path": "1.14.4/mojmap/net/minecraft/world/entity/LivingEntity.java",
      "member": "tickEffects, addEffect",
      "lines": [
        [
          605,
          662
        ],
        [
          761,
          777
        ]
      ],
      "status": "verified-content",
      "sha256": "428762178a876efd4069086e6b7d51f571ea0401f44e7eff0927b7d26d9ef681",
      "detail": "Canonical source read. Matches digest in archived original."
    },
    {
      "version": "1.15.2",
      "mapping": "mojmap",
      "path": "1.15.2/mojmap/net/minecraft/world/entity/LivingEntity.java",
      "member": "tickEffects, addEffect",
      "lines": [
        [
          573,
          630
        ],
        [
          729,
          745
        ]
      ],
      "status": "verified-content",
      "sha256": "46d243bb7e51f7b54404aa1d7d6e6b827682d0f5925d02847c4193306c4d5e54",
      "detail": "Canonical source read. Matches digest in archived original."
    }
  ],
  "sourceReviews": [],
  "openIssues": [],
  "runtime": "unverified"
}
```
