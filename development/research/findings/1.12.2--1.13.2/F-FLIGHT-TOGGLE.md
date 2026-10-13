# F-FLIGHT-TOGGLE — 1.13.2 blocks double-tap flight toggle while swimming

1.12.2, ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny: `1.12.2/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`, `public void mobTick() {`, lines 649-817.
1.13.2, ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny: `1.13.2/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`, `public void mobTick() {`, lines 653-842.

Difference: when flight permission exists, the second jump edge toggles `abilities.flying` in 1.12.2; 1.13.2 adds `else if (!isSwimming())` before toggling.

The first press timer and spectator auto-flight route remain separate guards.

Movement meaning: - Reachability and dependencies: local-player input edge reaches the branch when not spectator, able to fly, and jumping transitions false-to-true without autojump. 1.13.2 swimming state producer is F-WATER-STATE.


## Current evidence and remaining work

Source decisions, implementation coverage and runtime parity are separate. Source paths are relative to `decompiled_minecraft/`; immutable Git evidence can be read with `git show <revision>:<path>`.

```json
{
  "implementation": {
    "status": "open",
    "changes": [],
    "hooks": [],
    "limits": [
      "Complete current Change/catalog inventory inspected; no dedicated operation implements F-FLIGHT-TOGGLE — 1.13.2 blocks double-tap flight toggle while swimming; later broadly related hooks do not establish this claimed delta."
    ]
  },
  "applicability": [],
  "sources": [
    {
      "version": "1.12.2",
      "mapping": "ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny",
      "path": "1.12.2/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java",
      "member": "public void mobTick() {",
      "lines": "649-817",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 01a58e94d8c6ff98a8e3794227cdc76a5fcbdbad795c70c9cf28854aff9823cc; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 7002c371c04d5e0458302e61b44727a289865e3cc3b1a283790944851bfd31bf."
    },
    {
      "version": "1.13.2",
      "mapping": "ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny",
      "path": "1.13.2/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java",
      "member": "public void mobTick() {",
      "lines": "653-842",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29b29d8d9bf42bf; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 17fe27977ef9dea1ac2f5e6641d9a5c06ce6ab942c9834c550bc46e99db756a6."
    }
  ],
  "sourceReviews": [],
  "openIssues": [
    {
      "missing": "Later applicability gate: exact introduction of campaign-F-FLIGHT-TOGGLE is unknown within (1.12.2, 1.13.2]",
      "next_action": "Preserve any exact historical verdict for its bounded assertion; verify intervening releases separately before assigning a new implementation range. Formatting alone requires no renewed acceptance."
    },
    {
      "missing": "Current implementation of campaign-F-FLIGHT-TOGGLE",
      "next_action": "After expert scope review and independent source acceptance, assign this specific operation to the separate implementation phase."
    },
    {
      "missing": "Exact independent source verdict/snapshot identity for F-FLIGHT-TOGGLE",
      "next_action": "Retain original source-confirmed status only; source-acceptance owner must resolve a preserved independent verdict or review this exact bounded assertion in the later campaign."
    },
    {
      "missing": "Exact independent source verdict/snapshot identity for flight-toggle-swimming",
      "next_action": "Retain original source-confirmed status only; source-acceptance owner must resolve a preserved independent verdict or review this exact bounded assertion in the later campaign."
    }
  ],
  "runtime": "unverified"
}
```
