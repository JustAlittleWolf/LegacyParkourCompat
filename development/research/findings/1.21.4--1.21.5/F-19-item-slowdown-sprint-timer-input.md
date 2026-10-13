# 1.21.4--1.21.5/F-19

On dry ground, old LocalPlayer attenuates and retains forwardImpulse during non-passenger item use; newer movement modification leaves stored moveVector raw. After item-use release while forward remains held, old prior-input threshold arms sprintTriggerTime=7 and new positive-vector predicate does not. Release forward one tick, then press again: old starts sprint with positive timer; new only arms the timer.

Conditions: controlled local keyboard Player on clear dry ground, forward-only input; non-passenger, not shifting/crouching, not already sprinting, not fall-flying, no blindness; food above sprint threshold; sprint key released; initial sprintTriggerTime=0; one forward-held item-use tick, item-use release with forward still held, one forward-release tick, forward re-press.

Existing run reports ACCEPT for the exact d41bbfe8f6cf48e05fc210c297201095f83500f1 snapshot; standalone independent review artifact identity and exact applicability remain open. No implementation or runtime verification.

Source locators:

- `1.21.4/mojmap/net/minecraft/client/player/KeyboardInput.java`, calculateImpulse/tick, lines ['13-34'].
- `1.21.5/mojmap/net/minecraft/client/player/KeyboardInput.java`, tick, lines ['21-36'].
- `1.21.4/mojmap/net/minecraft/client/player/LocalPlayer.java`, aiStep/hasEnoughImpulseToStartSprinting, lines ['653-677', '698-708', '1068-1071'].
- `1.21.5/mojmap/net/minecraft/client/player/LocalPlayer.java`, applyInput/modifyInput/aiStep, lines ['608-638', '693-703', '718-733'].
- `1.21.4/mojmap/net/minecraft/client/player/ClientInput.java`, stored movement impulses, lines ['5-13'].
- `1.21.5/mojmap/net/minecraft/client/player/ClientInput.java`, hasForwardImpulse, lines ['17-19'].
- `1.21.4/mojmap/net/minecraft/world/entity/LivingEntity.java`, setSprinting, lines ['2077-2084'].
- `1.21.5/mojmap/net/minecraft/world/entity/LivingEntity.java`, setSprinting, lines ['2101-2108'].



## Current evidence and remaining work

Source decisions, implementation coverage and runtime parity are separate. Source paths are relative to `decompiled_minecraft/`; immutable Git evidence can be read with `git show <revision>:<path>`.

```json
{
  "implementation": {
    "status": "open",
    "changes": [],
    "hooks": [],
    "limits": [
      "Source claim; exact review artifact/applicability and implementation remain open."
    ]
  },
  "applicability": [
    {
      "older": "1.21.4",
      "newer": "1.21.5",
      "first_changed_release": "unknown within(1.21.4,1.21.5]; no independent exact applicability/boundary decision bound",
      "pair_status": "partial",
      "runtime": "not performed"
    }
  ],
  "sources": [
    {
      "version": "1.21.4",
      "mapping": "mojmap",
      "path": "1.21.4/mojmap/net/minecraft/client/player/KeyboardInput.java",
      "member": "calculateImpulse/tick",
      "lines": [
        "13-34"
      ],
      "status": "verified-content",
      "sha256": "c885bcc709e445c3ff91f03bf37acf9320e17fc9daf1bd2ba46ba20487f1d9ed",
      "detail": "Read only recorded source slices; actual file digest and valid ranges checked. This audit is not independent acceptance of the candidate."
    },
    {
      "version": "1.21.5",
      "mapping": "mojmap",
      "path": "1.21.5/mojmap/net/minecraft/client/player/KeyboardInput.java",
      "member": "tick",
      "lines": [
        "21-36"
      ],
      "status": "verified-content",
      "sha256": "d5cb0e93df7f66755172d74e028225012ee33c6e4f0d510a1d8e25ba5497c0a3",
      "detail": "Read only recorded source slices; actual file digest and valid ranges checked. This audit is not independent acceptance of the candidate."
    },
    {
      "version": "1.21.4",
      "mapping": "mojmap",
      "path": "1.21.4/mojmap/net/minecraft/client/player/LocalPlayer.java",
      "member": "aiStep/hasEnoughImpulseToStartSprinting",
      "lines": [
        "653-677",
        "698-708",
        "1068-1071"
      ],
      "status": "verified-content",
      "sha256": "145686ebdc7f0d12a64070309073665eb8695b7e09911a723e86113a77d04611",
      "detail": "Read only recorded source slices; actual file digest and valid ranges checked. This audit is not independent acceptance of the candidate."
    },
    {
      "version": "1.21.5",
      "mapping": "mojmap",
      "path": "1.21.5/mojmap/net/minecraft/client/player/LocalPlayer.java",
      "member": "applyInput/modifyInput/aiStep",
      "lines": [
        "608-638",
        "693-703",
        "718-733"
      ],
      "status": "verified-content",
      "sha256": "f1fcfed4a938732361e7ad951f93e9b73b02320ee56f0b219e2b3c7acdbfa2ef",
      "detail": "Read only recorded source slices; actual file digest and valid ranges checked. This audit is not independent acceptance of the candidate."
    },
    {
      "version": "1.21.4",
      "mapping": "mojmap",
      "path": "1.21.4/mojmap/net/minecraft/client/player/ClientInput.java",
      "member": "stored movement impulses",
      "lines": [
        "5-13"
      ],
      "status": "verified-content",
      "sha256": "0f2e4d03749ab7495e386af05aa77a0414b5d1c3ef76256db2b514f42cc6e3c4",
      "detail": "Read only recorded source slices; actual file digest and valid ranges checked. This audit is not independent acceptance of the candidate."
    },
    {
      "version": "1.21.5",
      "mapping": "mojmap",
      "path": "1.21.5/mojmap/net/minecraft/client/player/ClientInput.java",
      "member": "hasForwardImpulse",
      "lines": [
        "17-19"
      ],
      "status": "verified-content",
      "sha256": "597a44339a99f1bce1b081614c7c2984ca03e255b40e9d247675a31b6f810d78",
      "detail": "Read only recorded source slices; actual file digest and valid ranges checked. This audit is not independent acceptance of the candidate."
    },
    {
      "version": "1.21.4",
      "mapping": "mojmap",
      "path": "1.21.4/mojmap/net/minecraft/world/entity/LivingEntity.java",
      "member": "setSprinting",
      "lines": [
        "2077-2084"
      ],
      "status": "verified-content",
      "sha256": "e62ce650af0a5ac97e4d0a2ba7cad4ba68a8d098525609f8b5b6976a8ab0ae30",
      "detail": "Read only recorded source slices; actual file digest and valid ranges checked. This audit is not independent acceptance of the candidate."
    },
    {
      "version": "1.21.5",
      "mapping": "mojmap",
      "path": "1.21.5/mojmap/net/minecraft/world/entity/LivingEntity.java",
      "member": "setSprinting",
      "lines": [
        "2101-2108"
      ],
      "status": "verified-content",
      "sha256": "a8aed863d4fdc515c751dd2878a8bb9c13179228cb8dbb50edf1d19cd5404271",
      "detail": "Read only recorded source slices; actual file digest and valid ranges checked. This audit is not independent acceptance of the candidate."
    }
  ],
  "sourceReviews": [
    {
      "verdict": "ACCEPT reported for exact immutable candidate in D23/R-F19",
      "assertion_preserved": true,
      "binding_status": "Standalone R-F19 review commit/path/blob/raw SHA not located; latest run explicitly reports independent ACCEPT of exact candidate. Earlier finding/pair summary pending wording is stale.",
      "snapshot_sha256": "7e18e61fcdd1d20e6e7d21f17bcce711410f756d37285162ca72e94d15573230",
      "reportedEventEvidence": {
        "path": "workflows/source-campaign-2026-10-07/1.21.4--1.21.5/run.md",
        "sha256": "7884d200f37beb4f02d030f815975344473e9f4fadce56453eff89358756c935",
        "branch_tip": "2880760a8f555f4054e569576dbe9b4a45719a1a",
        "event": "D23/R-F19; verified prior-input sampling,stored slowdown,timer transitions and sprint modifier timing; no gap reported"
      }
    }
  ],
  "openIssues": [
    {
      "missing": "Exact standalone R-F19 independent ACCEPT review identity for the immutable source-produced sequence.",
      "next_action": "Recover existing independently issued ACCEPT artifact; preserve reported assertion rather than treating stale pending summary as a renewed rejection."
    },
    {
      "missing": "Exact stable applicability boundary and continuity for any intended implementation profile range.",
      "next_action": "Retain endpoint claim; no Changes/catalog/enum edit is authorized by this migration."
    },
    {
      "missing": "Complete S1 input/sprint-timer inventories and pair-wide independent audit.",
      "next_action": "Keep coverage partial; do not promote this bounded candidate to pair completion."
    }
  ],
  "runtime": "unverified",
  "snapshot": {
    "commit": "d41bbfe8f6cf48e05fc210c297201095f83500f1",
    "path": "workflows/source-campaign-2026-10-07/1.21.4--1.21.5/findings/F-19-item-slowdown-sprint-timer-input.md",
    "sha256": "7e18e61fcdd1d20e6e7d21f17bcce711410f756d37285162ca72e94d15573230",
    "relation": "incoming bytes reproduce the immutable candidate hash recorded by the run; no later corrected revision; run D23/R-F19 reports ACCEPT of the same immutable candidate, without standalone review artifact identity"
  },
  "preconditions": [
    "controlled local keyboard Player on clear dry ground, forward-only input",
    "non-passenger, not shifting/crouching, not already sprinting, not fall-flying, no blindness",
    "food above sprint threshold; sprint key released; initial sprintTriggerTime=0",
    "one forward-held item-use tick, item-use release with forward still held, one forward-release tick, forward re-press"
  ]
}
```
