# Independent review r1: corrected 1.14 → 1.14.1 source comparison

## Bound immutable inputs

- Corrected author artifact: `C:/Users/Wolfi/.codex/visualizations/2026/11/17/01a1eb7b-ac27-7712-9259-3041950bc340/lane-portal-passenger-position-114-1141/roster-comparison.corrected-r1.md`, raw SHA-256 `1db37e05d24e1bde81c9130d66deae09b0615d33436eadebc35f9cbd6dd47fb8` (recomputed and matched).
- Frozen independent pre-claim source roster: `.../lane-portal-passenger-position-114-1141-audit/preclaim-source-roster-analysis.md`, raw SHA-256 `e267659ad3cdcd4170b5f356ae8d084ba31ea40e9c75cfda7b3510144fd86ab5` (recomputed and matched).
- Prior independent semantic verdict: `.../lane-portal-passenger-position-114-1141-audit/verdict.md`, raw SHA-256 `9125ee28fd7ecf1e0b037fb03a650a6a63ba0f148baa55537c17fcfea7724202` (recomputed and matched).

## Verdict

**Accept corrected r1 for the bounded source-only no-difference claim.** The corrected artifact removes the defective method-range digest assertions and retains release/mapping/provenance identities plus raw full-file hashes for its listed Java sources. The exact Yarn builds, client/mapping/intermediary pins, manifest hashes, and source hashes remain bound to the frozen roster and canonical local provenance checks.

The operative source semantics and scope are unchanged from the already accepted prior verdict: the generic living dismount helper is gated by a changed vehicle relation and logical-server side; the server-player outer correction independently checks relation change and handler presence; the portal transfer gate does not itself dismount; and the mounted client passenger XYZ write flows through `Entity.tickRiding` → the vehicle passenger-position update → `Entity.setPosition`, with `ClientPlayerEntity.tickRiding` adding boat inputs afterward. The server handler's empty relative-flag path that makes the correction coordinates absolute is covered by the frozen source roster and prior verdict.

There is no new technical divergence in corrected r1. Its remaining limits are correctly retained: this closes only the examined 1.14→1.14.1 slices, does not establish another patch's introduction boundary or full-pair coverage, and makes no runtime-parity claim. No finding is proposed. The corrected artifact's listed source table does not separately repeat the server-handler file hash; reuse it with the frozen roster/prior verdict for that dependency.

## Superseded caveat

The previous verdict's caveat about malformed/unverifiable method-range digests is superseded for corrected r1: no method-range digests remain. The retained whole-file hashes are the declared evidence identity.

## Evidence identity

The adjacent `.sha256` file contains the SHA-256 of these exact UTF-8/LF bytes.