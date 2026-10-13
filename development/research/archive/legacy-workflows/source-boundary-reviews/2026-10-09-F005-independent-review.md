# Independent source-only review: F-005 boundary memo

**Review date:** 2026-10-09  
**Candidate:** `79a57b1835e6a0b59f35998b33dda9a091cc6489` (memo blob `d468a724490e25cc889af9cb93e1bd1bfa502c88`)  
**Disposition:** **REQUEST CHANGES** to the memo's dependency-closure / witness claim. The first-changed-release boundary is accepted as 1.18.2.  
**Scope:** exact Mojmap sources and the three specified review inputs only. No implementation, wiki/MCPK, runtime, build, test, game, server, TAS, or source-preparation work was performed.

## Inputs and identity checks

- Candidate memo: `workflows/source-boundary-reviews/F005-boundary-evidence-2026-10-09.md`, commit `79a57b1835e6a0b59f35998b33dda9a091cc6489`, blob `d468a724490e25cc889af9cb93e1bd1bfa502c88`. It is byte-identical at the cited final author branch `fa8fd199c64f8d78c0c7c313f66b4217e512ef35`.
- Accepted finding: `workflows/source-campaign-2026-10-07/1.17.1--1.18.2/findings/F-005-fall-distance-reset-edge-gate.md`, commit `e531086eecc778432b40b2b9ef39499b8a7f0dba`, blob `8a9ff41d1c685bda185180863fe5069b5ec90783`.
- Prior independent bounded source review: `workflows/source-boundary-reviews/2026-10-08-mc1171-1182-snapshot-review.md`, commit `2fd121e116d7185165c76aea1c3708273afc7ea1`, blob `871ad5bccde2194ccc733ed8fef92472a1c96c2a`.
- Preparation publication identities in the memo were treated only as provenance. The cited exact Mojmap ready markers and source/artifact manifest hashes agree for all four releases. Direct class hashes checked for `Entity.java`, `Player.java`, `Blocks.java`, `BlockBehaviour.java`, and (where cited) `BlockGetter.java` agree with the memo. The water tag raw hash agrees in all four client JARs; `fall_damage_resetting` is absent in 1.17.1, 1.18, and 1.18.1, and its 1.18.2 raw hash and three entries agree with the memo.

## Boundary and dependencies accepted

The paired `Entity.move` bodies support the claimed boundary. In 1.17.1, 1.18, and 1.18.1, after `maybeBackOffFromEdge` and `collide`, the nonzero-length guard leads directly to the position write; there is no `FALLDAMAGE_RESETTING` clip or reset there. The 1.18 and 1.18.1 `Entity.java`, `Player.java`, and `ClipContext.java` sources are byte-identical for these reviewed bodies. In 1.18.2, the same move computes resolved length squared, and under the nested guards `lengthSqr > 1.0E-7`, `fallDistance != 0.0F`, and `lengthSqr >= 1.0`, clips from the current position through the resolved vector with the special block selector and water fluid selector. A non-MISS result calls `resetFallDistance()` before `setPos`. `ClipContext.Block.FALLDAMAGE_RESETTING` is new in 1.18.2 and selects a full block shape for `BlockTags.FALL_DAMAGE_RESETTING`, otherwise empty. This supports **1.18.2 as the first changed release**, with the version-key naming left outside this review.

The memo's selected resource and shape bindings also check out: the 1.18.2 tag includes cobweb; cobweb is registered with `noCollission`; the ordinary collision shape is empty when collision is disabled; `BlockGetter.clip` uses the selected block shape and compares its hit with the fluid hit. The ordinary movement shape and special clip shape are distinct as claimed. The unchanged later `Player.isAboveGround` predicate reads `fallDistance` before its downward collision probe. The movement reset is a player movement-state write; the review does not treat `checkFallDamage`'s fall/landing simulation or resulting damage/health as in scope.

## Required correction: close the post-move cobweb writer

The stated cobweb witness does not establish that F-005 is the writer whose value is later consumed. In 1.18.2, the movement sequence calls `tryCheckInsideBlocks()` after the F-005 clip/reset, position write, and `checkFallDamage`. `checkInsideBlocks()` enumerates blocks intersecting the entity's **final** bounding box and calls each state's `entityInside`. `WebBlock.entityInside` calls `Entity.makeStuckInBlock`, and that method independently calls `resetFallDistance()`.

The memo only requires a resolved horizontal segment that “crosses a cobweb cube.” That does not establish that the final player box is wholly clear of the cobweb. If it still overlaps the web, the later callback is an independent reset source, so the memo has not shown a later `Player.isAboveGround` read that distinguishes the F-005 movement gate from the callback. Nor does the memo give a bounded starting position and resolved displacement that proves the entity exits the web before that callback. `checkFallDamage` leaving zero unchanged does not close this separate writer.

Please amend the witness/dependency closure to do one of the following, with exact source support: (a) bound a reachable horizontal movement and endpoint whose final bounding box excludes the cobweb while the F-005 segment clips it, with the edge-backoff and length guards accounted for; or (b) select another tagged block and close its post-move callback/reset path. Then bind the resulting zero field to the later `Player.isAboveGround` consumer. This request is limited to proving the memo's source-level causal witness; it does not ask for a realized trajectory or runtime validation.

## Review outcome

**REQUEST CHANGES** for the memo's claim that the cobweb/stone dependency closure is complete. The first-changed-release boundary and the individual source/resource bindings listed above are accepted. No `ParkourVersion` key or hook recommendation is made. After the witness and post-move writer dependency are closed, the integration owner can request a fresh independent disposition before implementation.
