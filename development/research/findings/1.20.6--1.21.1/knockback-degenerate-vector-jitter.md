# knockback-degenerate-vector-jitter: 1.21.1 randomizes a zero horizontal knockback vector

1.20.6, mojmap: `1.20.6/mojmap/net/minecraft/world/entity/LivingEntity.java`, knockback 1475-1483; hurt 1139-1145.
1.21.1, mojmap: `1.21.1/mojmap/net/minecraft/world/entity/LivingEntity.java`, knockback 1441-1455; hurt 1139-1145.

Difference: The changed guard is inside the common LivingEntity knockback method and applies to direct callers, not only the ordinary damage path. For a reachable shield callback whose attacker and blocker have equal X/Z coordinates, 1.20.6 normalizes (0,0) to the zero vector. 1.21.1 instead draws random tiny components until their squared length is at least 1.0E-5F, normalizes that direction, and subtracts the resulting strength-scaled vector from the attacker’s horizontal velocity. The player velocity write therefore differs under this exact input.

Movement meaning: The positive-strength guard must pass after knockback resistance. 1.20.6 player can be the direct LivingEntity attacker in a blocked non-projectile hit; the shield callback computes knockback direction from the two entities’ X/Z positions. Equal planar coordinates make the input exactly zero. The common knockback method then writes the velocity and sets hasImpulse. For the stated reachable player precondition, 1.20.6 adds no horizontal knockback impulse from this callback, while 1.21.1 adds a random horizontal impulse with the configured knockback strength.


## Current evidence and remaining work

Source decisions, implementation coverage and runtime parity are separate. Source paths are relative to `decompiled_minecraft/`; immutable Git evidence can be read with `git show <revision>:<path>`.

```json
{
  "implementation": {
    "status": "open",
    "changes": [],
    "hooks": [],
    "limits": [
      "No catalog Change implements the operation stated in this record. The current 104 annotated Changes/catalog were checked; unrelated hooks do not close this operation. Registration establishes static correspondence only; no runtime parity or renewed applicability acceptance."
    ]
  },
  "applicability": [],
  "sources": [
    {
      "version": "1.20.6",
      "mapping": "mojmap",
      "path": "1.20.6/mojmap/net/minecraft/world/entity/LivingEntity.java",
      "member": "knockback",
      "lines": "1475-1483",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 27f4ef3a9362e9874e2c33edac981b6f485ad97f92c4e35b497525b646aae745 *1.20.6/client_mappings.txt. Canonical source-manifest SHA-256 56aae10684471d7abb1c366bd5dd431ab976112a87e6cc5c687a68f1eff06311 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.1",
      "mapping": "mojmap",
      "path": "1.21.1/mojmap/net/minecraft/world/entity/LivingEntity.java",
      "member": "knockback",
      "lines": "1441-1455",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 324a3eee8496caab57cfaf5101ef576f1ae3c60c40e3857e96f35f3af9a3a0d8; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 140c47931cccc8fc9e4c22d7603e2d714d1a953a146f51ea7397d95c955536ec *1.21.1/client_mappings.txt. Canonical source-manifest SHA-256 900f956e00f6fc1300bb3d689ea49df2b1a57bcaa54617344ef456d95d47cb48 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.20.6",
      "mapping": "mojmap",
      "path": "1.20.6/mojmap/net/minecraft/world/entity/LivingEntity.java",
      "member": "hurt",
      "lines": "1139-1145",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 27f4ef3a9362e9874e2c33edac981b6f485ad97f92c4e35b497525b646aae745 *1.20.6/client_mappings.txt. Canonical source-manifest SHA-256 56aae10684471d7abb1c366bd5dd431ab976112a87e6cc5c687a68f1eff06311 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.1",
      "mapping": "mojmap",
      "path": "1.21.1/mojmap/net/minecraft/world/entity/LivingEntity.java",
      "member": "hurt",
      "lines": "1139-1145",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 324a3eee8496caab57cfaf5101ef576f1ae3c60c40e3857e96f35f3af9a3a0d8; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 140c47931cccc8fc9e4c22d7603e2d714d1a953a146f51ea7397d95c955536ec *1.21.1/client_mappings.txt. Canonical source-manifest SHA-256 900f956e00f6fc1300bb3d689ea49df2b1a57bcaa54617344ef456d95d47cb48 matches archived publication; cited file hash is present in that manifest."
    }
  ],
  "sourceReviews": [],
  "openIssues": [],
  "runtime": "unverified"
}
```
