# Independent technical review: F-008 sneak-edge border query

- Review date: 2026-10-09.
- Decision: **ACCEPT** for the exact implementation commit and merged review tree below, limited to the border-query operation and source-supported profiles described here.
- Candidate: `2aede8384f0a9ea4ea8518b7ec8dcfedfcc12732` (`Implement versioned sneak edge border query`), on `fix/f008-border-edge-gate-2026-10-09`.
- Candidate handoff: `1249e133da5a90cd02ff08f1ffdf90292eaa1f4b`, `workflows/fix-implementation/handoffs/2026-10-09-F008-border-edge-gate.md`, blob `8e16b8ce41352f5692968ca6cdf34bded65f6aed`.
- Reviewed code tree: merge commit `a198e74cb0cfe6027d24ab7294bff11c1fe87d0c` on `fix/review-f008-technical-binding-2026-10-09`; it merges the candidate branch (first parent `534c0c7098ce441ed8d07b91312ce18744c34b21`) with current `main` `ee7ce616ae00aea11b335c80ece87f847d3cb3b5` (second parent).
- Accepted source input: memo `1bfc993c0602c00b22622d651c93fe640d4a4ac1` and its original independent **ACCEPT** `c578b68bbbef1442fd23367bfef5fe6dc9e39f89`.
- Metadata input: provenance-only record `6fd3bcce82235228dd892f28a4c07bfad3b0c08b`; it binds artifacts and explicitly supplies no additional blind source-physics verdict.

## Technical basis

The candidate introduces one narrow `SneakEdgeCollisionQueryBehavior` operation. `PlayerMixin` resolves it only while dispatching an existing historical `SneakEdgeBehavior`; the existing edge-backoff loop still owns the X, Z, then combined candidate sequence and its `0.05D` reductions. Passing no query retains the modern `level.noCollision(player, candidate)` fallback. `CURRENT` resolves no changes, so the original player method remains active.

The query providers preserve the historical border decision before testing candidate block and entity collisions:

- `V1_15_2` uses the player's current box, `1.0E-7D` deflation/inflation, and the original shape intersection predicate without the rounded-bounds admission gate.
- `V1_16` and `V1_17_1` first use strict comparisons against `floor(minX/minZ)` and `ceil(maxX/maxZ)` for all four horizontal current-box bounds, then apply the same border-shape tests. Candidate collision checks still use the proposed AABB.

The paired exact sources support these methods: 1.15, 1.15.1 and 1.15.2 `CollisionGetter.java` have SHA-256 `63daec0623c816d53f5dbdde407a27222fa21e62680c1096b63cf9082b1c25fd`; 1.16, 1.16.1, 1.16.2 and 1.16.5 `CollisionSpliterator.java` have SHA-256 `19ab959c469b8edb5c371ff737a59a61b8a80bf397eff1db280064342094d297`; 1.17.1 has SHA-256 `c4f8158bd6778159946ebf16c22a6c12f5a4bccb40e2f1215f4fc930bb218bd0`. Their Mojmap ready markers and source-manifest identities were checked against the shared read-only source publication. The accepted boundary memo establishes the 1.17.1-to-1.18 behavior change; it remains bounded to that source finding.

`ChangeResolver` selects the closest registered `emulates` key not older than the selected profile and returns no changes for `CURRENT`. The three registrations therefore keep the ungated 1.15.2 behavior separate from the gated 1.16/1.17.1 behavior; profiles from 1.18 onward do not resolve this query hook and use the native query through the existing edge handler. Hook, mixin bridge, catalog entries and implementations are consistent with that resolver direction. No block states or non-player movement are changed.

## Merge and interaction review

Both default-branch merges were inspected. The first brought `main` `a2af029ee9d051f5b44ceaf3bf1d6087690935d2`; the later merge brought the current `main` tip `ee7ce616ae00aea11b335c80ece87f847d3cb3b5`. The overlapping catalog edits register separate hook keys: shallow-lava cutoff and sprint-air behavior do not alter the F-008 registration. Incoming fluid/entity mixin work and sprint-air changes do not edit the player edge bridge, query providers, `SneakEdgeBackoff`, or `ChangeResolver`. The merges were clean; no semantic conflict affecting F-008 was found.

The existing independent ACCEPT material found in the handoff was not a portable technical report binding the implementation blobs and post-merge tree. The named `c578b68...` and `6fd3bc...` records accept or verify source-boundary evidence only. This report records the implementation verdict without repeating the completed source-boundary comparison.

## Limits

- Exact 1.17.0 source was unavailable. `ParkourVersion.V1_17` resolves to the `V1_17_1` provider, so the 1.17.0 profile's use of the strict gate remains source-unverified.
- Pre-1.15 profiles can resolve the `V1_15_2` provider; pre-1.15 source behavior was not checked here. No claim of exact pre-1.15 parity is made.
- The accepted source finding and boundary acceptance remain limited to the 1.17.1 → 1.18.2 edge-query behavior. This technical review does not enlarge their source scope.
- No build, tests, game/client, TAS, Gym, server, Docker, or runtime parity check was performed. Static review and source correspondence do not establish tick-level runtime parity.

## Implementation object bindings

At reviewed merge commit `a198e74cb0cfe6027d24ab7294bff11c1fe87d0c`, the implementation blobs are: `SneakEdgeBackoff.java` `57700c77b3a797a8207acf9b75531f31d0413a63`; V1_15_2 query `332eff48d5f46037e0cf4a60b244f9eea8cd135f`; V1_16 query `c9a6223fe70de981a1c42af4f5527ba0ec1440fe`; V1_17_1 query `a09b2f9da32e8864e42195f40ebc48e800273e65`; hook interface `f574bca20d76bd8fd84295ddeecd2dc458341539`; `PlayerMixin.java` `53a0a12fe38f3b2918aa1eae3db36203c3299529`; merged `MovementChangeCatalog.java` `686662511dc5b4f8fae3a31bd05f889aedd8f9c6`. The handoff file at the candidate handoff commit is bound above. These object IDs, candidate commit and reviewed merge commit identify the exact implementation reviewed.
