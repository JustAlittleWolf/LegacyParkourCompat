# 1.21.4--1.21.5/F-20

Start ordinary glide while dry, then enter water while valid glide equipment retains the fall-flying state. On underwater held-sprint+forward tick,1.21.4 stops and cannot start sprint because fallFlying is unconditionally vetoed;1.21.5 permits fallFlying when underwater and its run/swim stop predicates omit that veto. The unchanged fluid-first water travel consumes the differing sprint state, selecting0.9F versus non-sprint slowdown under the stated modifier exclusions.

Conditions: airborne non-passenger/no Levitation Player starts valid-equipment glide in air before entering water; underwater held sprint key and forward input; enough food; no blindness,item use,slow movement,horizontal collision or disallowed passenger; fluid contact refreshed while glide remains valid; for differing water drag,no DolphinGrace and water-movement efficiency<1.0F.

Existing run reports ACCEPT for the exact 0d2eab26f122484d1549a0fc53fec7fd084a8e6b snapshot; standalone independent review artifact identity and exact applicability remain open. No implementation or runtime verification.

Source locators:

- `1.21.4/mojmap/net/minecraft/client/player/LocalPlayer.java`, aiStep/shouldStopSprinting/canStartSprinting, lines ['664-666', '816-822', '1053-1061'].
- `1.21.5/mojmap/net/minecraft/client/player/LocalPlayer.java`, aiStep/shouldStopRunSprinting/shouldStopSwimSprinting/canStartSprinting, lines ['722-752', '837-852', '1065-1075'].
- `1.21.4/mojmap/net/minecraft/world/entity/player/Player.java`, tryToStartFallFlying, lines ['1528-1535'].
- `1.21.5/mojmap/net/minecraft/world/entity/player/Player.java`, tryToStartFallFlying, lines ['1507-1514'].
- `1.21.4/mojmap/net/minecraft/world/entity/LivingEntity.java`, travel/updateFallFlying/canGlide, lines ['2189-2199', '2825-2830', '2847-2859'].
- `1.21.5/mojmap/net/minecraft/world/entity/LivingEntity.java`, travel/updateFallFlying/canGlide, lines ['2213-2222', '2815-2820', '2837-2849'].
- `1.21.4/mojmap/net/minecraft/world/entity/Entity.java`, baseTick, lines ['453-455'].
- `1.21.5/mojmap/net/minecraft/world/entity/Entity.java`, baseTick, lines ['461-463'].



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
      "first_changed_release": "unknown within(1.21.4,1.21.5]; acceptance report and exact applicability decision not fully bound",
      "pair_status": "partial",
      "runtime": "not performed",
      "lineage": "Run preserves source-author implementation exposure before freeze; preserve bounded independent decisions once exactly bound; do not count later author coverage as independent audit."
    }
  ],
  "sources": [
    {
      "version": "1.21.4",
      "mapping": "mojmap",
      "path": "1.21.4/mojmap/net/minecraft/client/player/LocalPlayer.java",
      "member": "aiStep/shouldStopSprinting/canStartSprinting",
      "lines": [
        "664-666",
        "816-822",
        "1053-1061"
      ],
      "status": "verified-content",
      "sha256": "145686ebdc7f0d12a64070309073665eb8695b7e09911a723e86113a77d04611",
      "detail": "Read only recorded source slices; actual file digest and valid ranges checked. This audit is not independent acceptance of the candidate."
    },
    {
      "version": "1.21.5",
      "mapping": "mojmap",
      "path": "1.21.5/mojmap/net/minecraft/client/player/LocalPlayer.java",
      "member": "aiStep/shouldStopRunSprinting/shouldStopSwimSprinting/canStartSprinting",
      "lines": [
        "722-752",
        "837-852",
        "1065-1075"
      ],
      "status": "verified-content",
      "sha256": "f1fcfed4a938732361e7ad951f93e9b73b02320ee56f0b219e2b3c7acdbfa2ef",
      "detail": "Read only recorded source slices; actual file digest and valid ranges checked. This audit is not independent acceptance of the candidate."
    },
    {
      "version": "1.21.4",
      "mapping": "mojmap",
      "path": "1.21.4/mojmap/net/minecraft/world/entity/player/Player.java",
      "member": "tryToStartFallFlying",
      "lines": [
        "1528-1535"
      ],
      "status": "verified-content",
      "sha256": "c45f41b9784ce50a88a498e84a13edcef0a23e175233d726ed7f90940f19ebc1",
      "detail": "Read only recorded source slices; actual file digest and valid ranges checked. This audit is not independent acceptance of the candidate."
    },
    {
      "version": "1.21.5",
      "mapping": "mojmap",
      "path": "1.21.5/mojmap/net/minecraft/world/entity/player/Player.java",
      "member": "tryToStartFallFlying",
      "lines": [
        "1507-1514"
      ],
      "status": "verified-content",
      "sha256": "8fc187f33999db9dfc49251e95e92a17645a50adab16ca0f93f4948209f62036",
      "detail": "Read only recorded source slices; actual file digest and valid ranges checked. This audit is not independent acceptance of the candidate."
    },
    {
      "version": "1.21.4",
      "mapping": "mojmap",
      "path": "1.21.4/mojmap/net/minecraft/world/entity/LivingEntity.java",
      "member": "travel/updateFallFlying/canGlide",
      "lines": [
        "2189-2199",
        "2825-2830",
        "2847-2859"
      ],
      "status": "verified-content",
      "sha256": "e62ce650af0a5ac97e4d0a2ba7cad4ba68a8d098525609f8b5b6976a8ab0ae30",
      "detail": "Read only recorded source slices; actual file digest and valid ranges checked. This audit is not independent acceptance of the candidate."
    },
    {
      "version": "1.21.5",
      "mapping": "mojmap",
      "path": "1.21.5/mojmap/net/minecraft/world/entity/LivingEntity.java",
      "member": "travel/updateFallFlying/canGlide",
      "lines": [
        "2213-2222",
        "2815-2820",
        "2837-2849"
      ],
      "status": "verified-content",
      "sha256": "a8aed863d4fdc515c751dd2878a8bb9c13179228cb8dbb50edf1d19cd5404271",
      "detail": "Read only recorded source slices; actual file digest and valid ranges checked. This audit is not independent acceptance of the candidate."
    },
    {
      "version": "1.21.4",
      "mapping": "mojmap",
      "path": "1.21.4/mojmap/net/minecraft/world/entity/Entity.java",
      "member": "baseTick",
      "lines": [
        "453-455"
      ],
      "status": "verified-content",
      "sha256": "05f18ef2ec0413fc010230407c812a11553eb5123b68d21b5d7b2c0c175698ad",
      "detail": "Read only recorded source slices; actual file digest and valid ranges checked. This audit is not independent acceptance of the candidate."
    },
    {
      "version": "1.21.5",
      "mapping": "mojmap",
      "path": "1.21.5/mojmap/net/minecraft/world/entity/Entity.java",
      "member": "baseTick",
      "lines": [
        "461-463"
      ],
      "status": "verified-content",
      "sha256": "fc177733e9cc4b2d5cf2562e5529d0f4e0b9c3690f081fbd58aa9119eb173dc4",
      "detail": "Read only recorded source slices; actual file digest and valid ranges checked. This audit is not independent acceptance of the candidate."
    }
  ],
  "sourceReviews": [
    {
      "verdict": "ACCEPT reported for corrected snapshot only",
      "assertion_preserved": true,
      "binding_status": "standalone independent review commit/path/blob/SHA not identified in supplied branch-doc inventory",
      "snapshot_sha256": "19d104739d201066aabeadc5d7dd76f93fc40602016059d9bcff1fec1201940e",
      "reportedEventEvidence": {
        "path": "workflows/source-campaign-2026-10-07/1.21.4--1.21.5/run.md",
        "sha256": "7884d200f37beb4f02d030f815975344473e9f4fadce56453eff89358756c935",
        "branch_tip": "2880760a8f555f4054e569576dbe9b4a45719a1a",
        "detail": "Run correction event explicitly reports source reviewer ACCEPT,corrected source digest checks,held-key/dry-glide reachability and modifier exclusions; it supplies no separate review artifact identity."
      }
    }
  ],
  "openIssues": [
    {
      "missing": "Exact independent corrected F20 ACCEPT review artifact(commit,path,Git blob,raw SHA256).",
      "next_action": "Recover existing review bytes/identity if available; preserve reported event and exact corrected snapshot without inventing a verdict artifact."
    },
    {
      "missing": "Independent exact profile applicability/boundary and full input/water inventory closure.",
      "next_action": "Keep bounded source assertion,partial pair and separate implementation gate; no code or new discovery now."
    }
  ],
  "runtime": "unverified",
  "snapshot": {
    "commit": "0d2eab26f122484d1549a0fc53fec7fd084a8e6b",
    "path": "workflows/source-campaign-2026-10-07/1.21.4--1.21.5/findings/F-20-underwater-fall-flying-sprint.md",
    "git_blob": "a3fa01be5e1e79080d34bf2940f45868d8f02a35",
    "sha256": "19d104739d201066aabeadc5d7dd76f93fc40602016059d9bcff1fec1201940e",
    "relation": "incoming bytes match corrected immutable snapshot whose ACCEPT is reported in the run"
  },
  "history": [
    {
      "commit": "d74c2b08cc7afe0d48f9d48fa2827f03c8a679dc",
      "git_blob": "c05e7879f0f0e439eb50d11db138c15e42e569eb",
      "sha256": "5dfc7eac54a7dc5365a781afa0d6e88ef7d091c23cf4dd9dd9fa12d81db2113c",
      "decision": "original candidate; held sprint-key precondition then made explicit"
    },
    {
      "commit": "737e1e31331f0b807c4d3def46650d5f4ad4acc3",
      "git_blob": "877a56f51088125fefe3147f4d837175b26acaf9",
      "sha256": "2c22a59bbf389476c769fbf2d5d6bf27c88c092e2e834ee1a253bb40ec54d078",
      "decision": "reported REQUEST CHANGES for B LivingEntity digest typo and missing DolphinGrace exclusion; supported held-key sprint behavior preserved"
    }
  ],
  "preconditions": [
    "airborne non-passenger/no Levitation Player starts valid-equipment glide in air before entering water",
    "underwater held sprint key and forward input; enough food; no blindness,item use,slow movement,horizontal collision or disallowed passenger",
    "fluid contact refreshed while glide remains valid",
    "for differing water drag,no DolphinGrace and water-movement efficiency<1.0F"
  ]
}
```
