# EXT-04: player death velocity uses a different angle conversion

1.8.9, ornithe-feather; feather-gen2-1.8.9+build.2-mergedv2.jar, feather-gen2-1.8.9+build.2.tiny: `1.8.9/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java`, `public void die(DamageSource source) {`, lines 513-535.
1.9.4, ornithe-feather; feather-gen2-1.9.4+build.2-mergedv2.jar, feather-gen2-1.9.4+build.2.tiny: `1.9.4/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java`, `public void die(DamageSource source) {`, lines 484-506.

Difference: The degree-to-radian conversion has a different floating-point operation order: 1.8.9 converts `Math.PI` to float before multiplication/division; 1.9.4 divides in double precision and casts the quotient to float before multiplying.

The formula feeds the direct X/Z velocity assignment.

Both endpoints preserve the same vertical `0.1F` write and the same null-source horizontal-zero branch.

This records only a player velocity assignment in the death handler.

Health and damage production, death/respawn lifecycle, and subsequent motion are not evaluated.

Movement meaning: The degree-to-radian conversion has a different floating-point operation order: 1.8.9 converts `Math.PI` to float before multiplication/division; 1.9.4 divides in double precision and casts the quotient to float before multiplying.

The formula feeds the direct X/Z velocity assignment.

Both endpoints preserve the same vertical `0.1F` write and the same null-source horizontal-zero branch.

This records only a player velocity assignment in the death handler.

Health and damage production, death/respawn lifecycle, and subsequent motion are not evaluated.


## Current evidence and remaining work

Source decisions, implementation coverage and runtime parity are separate. Source paths are relative to `decompiled_minecraft/`; immutable Git evidence can be read with `git show <revision>:<path>`.

```json
{
  "implementation": {
    "status": "open",
    "changes": [],
    "hooks": [],
    "limits": [
      "Complete current Change/catalog inventory inspected; no dedicated operation implements EXT-04: player death velocity uses a different angle conversion; later broadly related hooks do not establish this claimed delta."
    ]
  },
  "applicability": [],
  "sources": [
    {
      "version": "1.8.9",
      "mapping": "ornithe-feather; feather-gen2-1.8.9+build.2-mergedv2.jar, feather-gen2-1.8.9+build.2.tiny",
      "path": "1.8.9/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java",
      "member": "public void die(DamageSource source) {",
      "lines": "513-535",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 e66cb294fc93118148a444bbafdf4dd57cbf66a23d69b1e8892cefccc690ab88; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 76bc95917eea57acc525dbf3fc4f2ecfa0ad7c3aba720dd852d7ed6514ef1669."
    },
    {
      "version": "1.9.4",
      "mapping": "ornithe-feather; feather-gen2-1.9.4+build.2-mergedv2.jar, feather-gen2-1.9.4+build.2.tiny",
      "path": "1.9.4/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java",
      "member": "public void die(DamageSource source) {",
      "lines": "484-506",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 d658a0d95452d12bb7e347bfd802240eeecaf7f938e10dcd43e2640434387f85; not bound to a source-file digest in this record. Checked full declaration/body; snippet SHA-256 a6add88dd350595685dd84a0fcfbe0df856b4626778ca677ff83f5fc1e9dca1a."
    }
  ],
  "sourceReviews": [],
  "openIssues": [
    {
      "missing": "Later applicability gate: exact introduction of campaign-EXT-04-player-death-velocity is unknown within (1.8.9, 1.9.4]",
      "next_action": "Preserve any exact historical verdict for its bounded assertion; verify intervening releases separately before assigning a new implementation range. Formatting alone requires no renewed acceptance."
    },
    {
      "missing": "Current implementation of campaign-EXT-04-player-death-velocity",
      "next_action": "After expert scope review and independent source acceptance, assign this specific operation to the separate implementation phase."
    },
    {
      "missing": "Exact independent source verdict/snapshot identity for EXT-04-player-death-velocity",
      "next_action": "Retain original source-confirmed status only; source-acceptance owner must resolve a preserved independent verdict or review this exact bounded assertion in the later campaign."
    }
  ],
  "runtime": "unverified"
}
```
