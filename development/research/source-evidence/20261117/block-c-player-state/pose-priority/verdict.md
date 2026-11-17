# Independent source audit: pose priority

TERMINAL: COMPLETED

Reviewed immutable draft SHA-256: `5676f9b12058a6a5f0de2d5e01d88ce451dff41bb5c7d6d32a08c0f86bd024fe`.

## Verdict

The draft's core source claims are supported. In the inspected Mojmap Player sources for 1.21.1 through 1.21.4, the desired-pose chain checks fall-flying, sleeping, then swimming. In 1.21.5, `getDesiredPose()` checks sleeping, swimming, then fall-flying. If all three predicates are true, this means fall-flying wins in 1.21.1–1.21.4; sleeping wins in 1.21.5. If only swimming and fall-flying are true, fall-flying wins through 1.21.4 and swimming wins in 1.21.5. Sleeping remains ahead of swimming in both orders.

The draft correctly keeps desired-pose priority distinct from fit resolution. In all inspected modern versions, `updatePlayerPose()` first checks whether the swimming pose fits. If it does not, the method does not set a new pose. If it does, the desired pose is accepted for spectators/passengers or when it fits; otherwise the method tries crouching, then swimming. Therefore the ordering difference can change the selected desired pose only when the outer swimming-fit gate passes and predicate overlap exists; fit fallback can still determine the final pose.

The 1.13.x/1.14 structural comparison is also supported. In 1.13, 1.13.1 and 1.13.2, `PlayerEntity.updatePlayerPose()` selects width and height floats in the order fall-flying, sleeping, swimming-or-spin, sneaking, standing, then only changes size if the candidate box has no collision. The inspected 1.14 Feather source has a `Pose` enum and a pose-selection method with a swimming-fit outer gate, a desired-pose chain, and a separate fit fallback. The 1.14 Yarn source independently has the enum and analogous `updateSize()` structure. The inspected 1.13.x Feather trees contain no `Pose.java` or `EntityPose.java`. This establishes the contrast for these inspected releases, not an exact introduction boundary within the interval.

## Applicability limits and evidence notes

The predicate chains establish conditional branch outcomes, not that ordinary vanilla play reaches simultaneous fall-flying and sleeping or swimming states. The pose method has no mutual-exclusion branch among those predicates, but the reviewed selection code alone does not prove a reachable trajectory. The draft's explicit qualification on simultaneous truth and trajectory reachability is appropriate. The pose routine is `Player` code, with local-player tick paths reaching it through the superclass tick path; this finding does not establish a client-only production rule or any server-side historical movement behavior.

The outer swimming-fit gate and desired-pose fallback must not be treated as witnesses that the three desired-pose predicates are simultaneously true. Likewise, the fact that vanilla state fields and update paths exist is not a trajectory experiment. No fit check, server validation, trajectory, or runtime result was used to infer simultaneous truth.

The pinned draft's file hashes and cited method ranges were checked against the canonical decompiled source files. Source publication identity was checked in the per-version provenance sidecars. The 1.21.2 version has no sibling ready/provenance sidecar; its mapping-tree `.decompile-provenance.json` records the embedded `Player.java` source hash, which matches the source file. This remains a provenance-packaging limitation, not a source-body mismatch. The audit found no material source error requiring the draft to change.

No product implementation, wiki material, Git history, backlog, tests, game launch, source generation, or code changes were used.
