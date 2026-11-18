# Narrow re-audit verdict r1

**Verdict: accept the corrected comparison for this bounded source claim.** Its call-order correction matches both exact source pairs. This is not a full-pair freeze or runtime/arrival assessment.

## Bound artifact hashes

Hashes below were computed from the immutable artifact bytes with SHA-256.

| Artifact | SHA-256 |
|---|---|
| Corrected author comparison, `comparison.corrected-r1.md` | `850bb8cbd6847ebb5cb7d09d3128f2163fce1d9511b68237434986754a8fc3d9` |
| Original author comparison, `comparison.md` | `ddc5b432e2e6dfea23b5490a7f822c1f13764d602591c9300a65b5c7bd4c4d00` |
| Original author source roster, `source-roster.json` | `75a5c718cdd481a27bf24192c5ce1097c818df95b106ba02575b670d6b997959` |
| Original audit verdict, `verdict.json` | `89f87b77ac3b061d39e6fad6c8259c99165b602557781232ba563190e9c852e3` |
| Frozen independent audit roster, `source-roster.json` | `9579006ef4de7b5accb6b2e50bf68b5dd9921afdb797b9ec7002ea910f510e14` |

## Source checks

- `LivingEntity.stopRiding` in both releases captures the old vehicle, calls `super.stopRiding`, then invokes its stand-up helper only when the old vehicle is nonnull, the vehicle relation changed, and execution is on the logical server. The helper call is virtual: the old `requestTeleport` dispatches to `ServerPlayerEntity.requestTeleport`; the new `teleportTo` dispatches to `ServerPlayer.teleportTo`.
- For non-Boat/non-Horse vehicles, the helper's early valid-top return or final fallback invokes correction 1 before `super.stopRiding` returns. For Boat/Horse, the helper instead takes its direct `setPosition`/`setPos` branch and does not invoke correction 1.
- After `super.stopRiding`, the outer ServerPlayer override independently sends correction 2 at current XYZ and angles when the relation changed and its network handler/connection is nonnull. This outer correction remains possible for Boat/Horse.
- These gates, virtual dispatch paths, branch distinctions, and ordering are present in both releases. The corrected comparison's account of the two-stage path is accurate.
- The corrected comparison explicitly limits itself to source call order and disclaims packet delivery or client processing order. It makes no arrival or runtime claim; none is inferred by this audit.

## Evidence boundary

This re-audit checks only the corrected dismount call order, its helper gates and Boat/Horse branch, and the corrected document's claim boundary. The original audit JSON and frozen roster remain unchanged. No primary sources, product files, wiki, backlog, build, tests, runtime, network, or Git operation were used.
