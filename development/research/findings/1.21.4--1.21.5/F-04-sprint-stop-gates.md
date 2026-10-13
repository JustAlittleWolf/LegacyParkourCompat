# F-04: Sprinting can persist through item use or slow movement in 1.21.5

1.21.4, mojmap: `1.21.4/mojmap/net/minecraft/world/entity/LivingEntity.java`, setSprinting 2077-2084.
1.21.5, mojmap: `1.21.5/mojmap/net/minecraft/world/entity/LivingEntity.java`, setSprinting 2101-2108.
1.21.4, mojmap: `1.21.4/mojmap/net/minecraft/client/player/LocalPlayer.java`, aiStep 662-678.
1.21.5, mojmap: `1.21.5/mojmap/net/minecraft/client/player/LocalPlayer.java`, aiStep 718-744.

Difference: If a player is already sprinting on land and begins using an item while still moving forward, 1.21.4 calls `setSprinting(false)` in the early `shouldStopSprinting` check. 1.21.5 makes `canStartSprinting` false, but its later stop predicate does not stop an already-sprinting player for item use. 1.21.5 still scales the directional input for item use in `modifyInput`; it leaves the sprint flag and sprint speed modifier active. The same start-versus-stop asymmetry applies to slow movement (crouching or visually crawling): 1.21.4 stops the sprint state; 1.21.5's start gate prevents a new sprint, while its active-sprint stop predicates omit that condition. The timer also has a separate predicate change: 1.21.4 clears the double-tap timer on shift and qualifying item use, while 1.21.5 additionally clears it on backward key input. This is recorded with the same sprint state-machine slice because the timer drives the same sprint transition.

Movement meaning: `LocalPlayer.aiStep` reads local key/input, item-use, pose/slow movement, water, collision, vehicle and food predicates and writes sprint state through `setSprinting`. `LivingEntity#setSprinting` applies the transient movement-speed modifier consumed by player travel. Food-level production is excluded; its direct sprint predicate read remains in the gate. The findings concern the local player path. For the stated land/item-use precondition, source shows 1.21.4 removes the sprint speed modifier while 1.21.5 leaves it active; 1.21.5's separate item-use directional scale still reduces input. Slow movement has the same state retention difference. The exact resulting displacement depends on travel branch, attribute value, collisions and input. Backward input's added timer reset changes a later double-tap transition when backward input occurs inside the open trigger window; no timing run was performed.


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
      "version": "1.21.4",
      "mapping": "mojmap",
      "path": "1.21.4/mojmap/net/minecraft/client/player/LocalPlayer.java",
      "member": "aiStep",
      "lines": "662-678",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 145686ebdc7f0d12a64070309073665eb8695b7e09911a723e86113a77d04611; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 48b502ccc5e855b49da8aa9c0c0d7c565bec54c9f5da3a3664528aff7b9fdf23 *1.21.4/client_mappings.txt. Canonical source-manifest SHA-256 f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.4",
      "mapping": "mojmap",
      "path": "1.21.4/mojmap/net/minecraft/world/entity/LivingEntity.java",
      "member": "setSprinting",
      "lines": "2077-2084",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 e62ce650af0a5ac97e4d0a2ba7cad4ba68a8d098525609f8b5b6976a8ab0ae30; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 48b502ccc5e855b49da8aa9c0c0d7c565bec54c9f5da3a3664528aff7b9fdf23 *1.21.4/client_mappings.txt. Canonical source-manifest SHA-256 f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.5",
      "mapping": "mojmap",
      "path": "1.21.5/mojmap/net/minecraft/client/player/LocalPlayer.java",
      "member": "aiStep",
      "lines": "718-744",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 f1fcfed4a938732361e7ad951f93e9b73b02320ee56f0b219e2b3c7acdbfa2ef; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 3907657ade3e61bc8cffb4ca0a1bcba15f57986be4e613900947119364a206e5 *1.21.5/client_mappings.txt. Canonical source-manifest SHA-256 365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.5",
      "mapping": "mojmap",
      "path": "1.21.5/mojmap/net/minecraft/world/entity/LivingEntity.java",
      "member": "setSprinting",
      "lines": "2101-2108",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 a8aed863d4fdc515c751dd2878a8bb9c13179228cb8dbb50edf1d19cd5404271; archived source-hash equivalence not established. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 3907657ade3e61bc8cffb4ca0a1bcba15f57986be4e613900947119364a206e5 *1.21.5/client_mappings.txt. Canonical source-manifest SHA-256 365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9 matches archived publication; cited file hash is present in that manifest."
    }
  ],
  "sourceReviews": [
    {
      "verdict": "reported ACCEPT, digest-only corrected revision",
      "assertion_preserved": true,
      "binding": "Latest run reports review event; standalone independent review commit/path/blob/SHA is not supplied. Source assertion unchanged.",
      "snapshot_sha256": "d716098459fc9ba8f34d1b45d440f9d48e8dda4608875ec0453b3401aa417788",
      "identity": "4a5eae32b5819418dbd59eb4a763ee24ea1f2c52:development/research/reconciliation/branch-doc-review-late.md"
    }
  ],
  "openIssues": [
    {
      "missing": "Standalone independent digest-correction review artifact identity for F04 is absent.",
      "next_action": "Preserve reported acceptance and unchanged assertion; bind exact artifact if recovered, do not invent a verdict."
    }
  ],
  "runtime": "unverified"
}
```
