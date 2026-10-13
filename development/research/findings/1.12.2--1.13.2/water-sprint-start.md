# Water sprint start predicates

1.12.2, ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny: `1.12.2/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`, `public void mobTick() {`, lines 649-817.
1.13.2, ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny: `1.13.2/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`, `public void mobTick() {`, lines 653-842.

Difference: 1.12.2 double-tap requires onGround and its held-key gate has no water predicate. 1.13.2 double-tap permits onGround or eye-submerged water; held-key start additionally requires dry or eye-submerged state. Other input/food/item/blindness gates and input sampling order remain as recorded.

Movement meaning: The same eligible forward/sprint input can start sprint in different water/support states. This assertion retains the independently accepted endpoint start-gate comparison.


## Current evidence and remaining work

Source decisions, implementation coverage and runtime parity are separate. Source paths are relative to `decompiled_minecraft/`; immutable Git evidence can be read with `git show <revision>:<path>`.

```json
{
  "implementation": {
    "status": "partial",
    "changes": [
      "src/main/java/me/wolfii/legacyparkourcompat/change/v1_12/SprintInputStart.java",
      "src/main/java/me/wolfii/legacyparkourcompat/change/v1_12/WaterSprintGate.java"
    ],
    "hooks": [
      "DoubleTapSprintStartHook",
      "ShallowWaterSprintStartHook",
      "SprintKeyStartHook",
      "WaterSprintGateHook"
    ],
    "limits": [
      "SprintInputStart supplies grounded double-tap gating; WaterSprintGate allows shallow-water sprint. The archived finding also distinguishes swimming/non-swimming sprint-stop conditions, which are not implemented by these start gates."
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
  "sourceReviews": [
    {
      "verdict": "ACCEPT, bounded local-player sprint-state rules",
      "assertion_preserved": true,
      "detail": "Shortened wording retains the bounded behavior assertion of this exact historical snapshot; hashes identify the original reviewed bytes only. Source-content transfer does not assert mapped-JAR equivalence, implementation fidelity, patch cutover or runtime parity.",
      "identity": {
        "id": "1.12.2-1.13.2/F-WATER-SPRINT",
        "lane": "vanilla-source",
        "finding": "Bounded local-player sprint-state eligibility changes between 1.12.2 and 1.13.2 for double-tap and held-key paths, with reachable dry, surface-water, and eye-submerged input states as conditioned in the source review.",
        "snapshot": {
          "commit": "3a60fe735560e478bf0aa0d05f5e306c74800f6a",
          "path": "workflows/source-campaign-2026-10-07/1.12.2--1.13.2/findings/F-WATER-SPRINT.md",
          "gitBlob": "ec3f21a967cb1b71e775295de3e5d1be060cd95f",
          "sha256": "8698af8d4474a11725abdcfb1aabcea8a84e6fb44c31a8254bf6a40810b5f114"
        },
        "review": {
          "commit": "92bf9c5b944f46849b72042b02480125e6d37435",
          "path": "workflows/source-campaign-2026-10-07/independent-review-water-sprint-provenance-2026-10-08.md",
          "decision": "ACCEPT, bounded local-player sprint-state rules",
          "gitBlob": "257bdd8df55eaaae3315ccd819846bd76299cd68",
          "sha256": "edd011c3c98547fe5082a44a530a707eda310295a02a44a1cc2559043e51aed7"
        },
        "additionalReviewedSnapshot": {
          "commit": "2dae7b235bde47c7837fd4092ec67ccb014352f2",
          "path": "workflows/source-campaign-2026-10-07/1.12.2--1.13.2/findings/F-WATER-SPRINT.md",
          "gitBlob": "2595ba0c83a20f5543235759e789121bb1b0aa68",
          "sha256": "ebc678ad4522a14a10549bc38a1f7958dcedcabd6de290a262c81d6c726d46f1",
          "decision": "separately ACCEPTED by same bounded review; do not rebind acceptance to integrated snapshot"
        },
        "limits": [
          "Only endpoint comparison; first changed release within (1.12.2,1.13.2] remains unknown.",
          "The B surface-water cancellation applies to the non-swimming branch only; swimming continuation remains separate.",
          "Full pair inventories remain open; source pair remains PARTIAL.",
          "Feather-derived artifact equivalence to the unavailable original derived artifact remains unproven.",
          "No runtime parity claim."
        ]
      }
    },
    {
      "verdict": "source-review-pending",
      "assertion_preserved": true,
      "detail": "Shortened wording retains the bounded behavior assertion of this exact historical snapshot; hashes identify the original reviewed bytes only. Source-content transfer does not assert mapped-JAR equivalence, implementation fidelity, patch cutover or runtime parity.",
      "identity": {
        "kind": "source-review-pending",
        "id": "1.12.2-1.13.2/F-WATER-SPRINT-SOURCE-ACCEPTANCE",
        "state": "F-WATER-SPRINT remains a source candidate. The snapshot itself says independent review and full-pair dependencies remain open. No exact independent acceptance report/commit for this vanilla source finding was located. Do not infer source acceptance from the accepted Wiki r2 snapshot or from the implementation correction review/integration.",
        "snapshot": {
          "path": "workflows/source-campaign-2026-10-07/1.12.2--1.13.2/findings/F-WATER-SPRINT.md",
          "gitBlob": "ec3f21a967cb1b71e775295de3e5d1be060cd95f",
          "sha256": "8698af8d4474a11725abdcfb1aabcea8a84e6fb44c31a8254bf6a40810b5f114"
        },
        "reviewStatus": "no exact independent source acceptance record found; review needed",
        "relatedWikiAcceptance": "1e5b3a65 is a distinct Wiki snapshot and does not accept this source finding",
        "pairStatus": "PARTIAL"
      }
    },
    {
      "verdict": "source-review-provenance-open",
      "assertion_preserved": true,
      "detail": "Shortened wording retains the bounded behavior assertion of this exact historical snapshot; hashes identify the original reviewed bytes only. Source-content transfer does not assert mapped-JAR equivalence, implementation fidelity, patch cutover or runtime parity.",
      "identity": {
        "kind": "source-review-provenance-open",
        "id": "1.12.2-1.13.2/F-WATER-SPRINT-acceptance-chain-audit",
        "state": "Resolved for these two immutable snapshots by bounded ACCEPT report 92bf9c5b. The integrated 3a60 snapshot and revised 2dae7b snapshot remain distinct; no acceptance rebind. Full-pair/first-cutover/Feather-equivalence questions remain open.",
        "source": {
          "path": "workflows/source-campaign-2026-10-07/1.12.2--1.13.2/findings/F-WATER-SPRINT.md",
          "gitBlob": "ec3f21a967cb1b71e775295de3e5d1be060cd95f",
          "sha256": "8698af8d4474a11725abdcfb1aabcea8a84e6fb44c31a8254bf6a40810b5f114",
          "status": "candidate; no independent source acceptance established"
        },
        "nextLookup": "Locate a report that binds this exact snapshot and explicitly accepts its bounded source claim, or keep the source finding pending.",
        "priorState": "The exact F-WATER-SPRINT source file is still labeled candidate and says independent review is open. A review or acceptance bound to this vanilla source snapshot was not found in the inspected record chain. The separately accepted Wiki r2 snapshot and accepted implementation code review do not accept this source finding.",
        "reviewCommit": "92bf9c5b944f46849b72042b02480125e6d37435",
        "reviewDecision": "ACCEPT bounded source claims for each snapshot separately"
      }
    },
    {
      "verdict": "ACCEPT, bounded local-player sprint-state rules",
      "assertion_preserved": true,
      "detail": "Shortened wording retains the bounded behavior assertion of this exact historical snapshot; hashes identify the original reviewed bytes only. Source-content transfer does not assert mapped-JAR equivalence, implementation fidelity, patch cutover or runtime parity.",
      "identity": {
        "id": "1.12.2-1.13.2/F-WATER-SPRINT",
        "finding": "Bounded local-player sprint-state eligibility changes between 1.12.2 and 1.13.2 for double-tap and held-key paths, with reachable dry, surface-water, and eye-submerged input states as conditioned in the source review.",
        "limits": [
          "Only endpoint comparison; first changed release within (1.12.2,1.13.2] remains unknown.",
          "The B surface-water cancellation applies to the non-swimming branch only; swimming continuation remains separate.",
          "Full pair inventories remain open; source pair remains PARTIAL.",
          "Feather-derived artifact equivalence to the unavailable original derived artifact remains unproven.",
          "No runtime parity claim."
        ],
        "snapshot": {
          "commit": "3a60fe735560e478bf0aa0d05f5e306c74800f6a",
          "path": "workflows/source-campaign-2026-10-07/1.12.2--1.13.2/findings/F-WATER-SPRINT.md",
          "gitBlob": "ec3f21a967cb1b71e775295de3e5d1be060cd95f",
          "sha256": "8698af8d4474a11725abdcfb1aabcea8a84e6fb44c31a8254bf6a40810b5f114",
          "resolved_commit": "3a60fe735560e478bf0aa0d05f5e306c74800f6a",
          "git_blob_sha256": "8698af8d4474a11725abdcfb1aabcea8a84e6fb44c31a8254bf6a40810b5f114",
          "recovered_exact_snapshot": "development/research/reconciliation/reviewed-snapshots/8698af8d4474a11725abdcfb1aabcea8a84e6fb44c31a8254bf6a40810b5f114.md"
        },
        "review": {
          "commit": "92bf9c5b944f46849b72042b02480125e6d37435",
          "path": "workflows/source-campaign-2026-10-07/independent-review-water-sprint-provenance-2026-10-08.md",
          "decision": "ACCEPT, bounded local-player sprint-state rules",
          "gitBlob": "257bdd8df55eaaae3315ccd819846bd76299cd68",
          "sha256": "edd011c3c98547fe5082a44a530a707eda310295a02a44a1cc2559043e51aed7",
          "git_blob_available": true,
          "git_blob_sha256": "edd011c3c98547fe5082a44a530a707eda310295a02a44a1cc2559043e51aed7"
        }
      }
    },
    {
      "verdict": "[{'section': 'Frozen snapshot identities and provenance comparison', 'verdict': ['Both raw digests were computed from the respective Git blob bytes. A direct diff shows exactly one added line in the revised snapshot: `Evidence artifact records: EA-FEATHER-R1-A and EA-FEATHER-R1-B (see run.md, Artifact evidence identities).` The integrated snapshot already contains the revised-artifact SHA values, source-manifest hashes, original-artifact-manifest hashes, and the warning that original derived jars are unavailable and equivalence is unproven. It lacks only that evidence-record pointer. This report keeps the identities and verdicts separate so the integrated old bytes cannot be represented as the revised bytes.'], 'snapshot_hash_matches': True}]",
      "assertion_preserved": true,
      "detail": "Archived independent report contains the exact original digest. Section-specific verdict remains bound to that snapshot; later corrections in this report supersede its earlier sections.",
      "identity": "development/research/archive/legacy-workflows/source-campaign-2026-10-07/independent-review-water-sprint-provenance-2026-10-08.md"
    }
  ],
  "openIssues": [
    {
      "missing": "Later applicability gate: exact introduction of campaign-F-WATER-SPRINT is unknown within (1.12.2, 1.13.2]",
      "next_action": "Preserve any exact historical verdict for its bounded assertion; verify intervening releases separately before assigning a new implementation range. Formatting alone requires no renewed acceptance."
    },
    {
      "missing": "Exact independent source verdict/snapshot identity for water-sprint-gates",
      "next_action": "Retain original source-confirmed status only; source-acceptance owner must resolve a preserved independent verdict or review this exact bounded assertion in the later campaign."
    }
  ],
  "runtime": "unverified"
}
```
