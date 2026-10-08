# Wiki audit checkpoint

**Checkpoint status: PARTIAL.** This records a bounded Wiki/source audit slice. It is not full campaign coverage and does not freeze the whole source pair.

## Closed slice

- The immutable Feather revision snapshot `wiki-feather-r1-2026-10-07` was consumer-verified and accepted by an independent reviewer at commit `4c1a8a01e26b6f2c50a589cc64d51f43ddb53aeb`.
- Bounded source findings captured there: 1.8.9→1.9.4 negligible-motion cutoff, pane collision shape, sneaking dimensions, and ladder thickness; 1.8.9→1.10.2 auto-jump; 1.8.9/1.9.4→1.11.2 sneak-edge guard; 1.12.2→1.13.2 swimming pose and wall collision; 1.13.2→1.14.4 pose clearance and ladder jumping.
- Ops audit passed for the six revised Feather bundles (1.8.9, 1.9.4, 1.10.2, 1.11.2, 1.12.2, 1.13.2). Consumer verification confirmed the immutable JAR hashes, unchanged ready/source/raw-input manifest hashes, and zero source/raw-input differences.
- The original derived JARs are unavailable. Their equivalence to the revised snapshots remains **unproven**; the old cache mismatch is not waived.
- Source trees are published through 1.21.11 and 26.2; exact source identities and comparison citations for closed findings are in [minecraft-wiki.md](minecraft-wiki.md) and [feather-r1-findings-snapshot.md](feather-r1-findings-snapshot.md).

## Pending slice

- The wider Wiki candidate catalog in `minecraft-wiki.md` remains open wherever it lacks a direct exact-release-pair source comparison. The numeric 1.9 jump-apex claim remains Wiki-only. Sprint-swimming input/acceleration and the full swim/crawl transition sequence remain open beyond the checked pose dimensions. Creative-flight sprint acceleration and drag exceptions, collision states for iron bars/fences and remaining pane/wall states, walking onto beds, and any later-version change to the pre-1.8.9 ladder/slope behaviors remain open. Reconcile each unexamined boundary against the candidate-claim table before treating the catalog as covered.
- The 1.17.1→1.18.2 sprint collision predicate, the 1.21.3→1.21.5 sprint-stop add/revert with later checks, and the released 1.21.3→1.21.11 diagonal input path have already been source-compared in `minecraft-wiki.md`; they are not pending items in this checkpoint.
- The exact-version source roster being available does not itself establish those boundaries. Keep health/food/damage/combat-state production and non-player/vehicle physics excluded.
- Do not provide implementation feedback or treat bounded findings as a whole-pair freeze. Continue this Wiki lane independently; other source-only comparisons remain blind and Wiki lanes stay sealed until their respective full-pair freeze.

## Resume instructions

1. Confirm the checkout with `git status --short --branch` and record its current tip with `git rev-parse HEAD`.
2. Read this checkpoint, then continue only the pending Wiki-to-exact-source comparisons listed above using the already published exact-version trees.
3. For any new Feather evidence, use immutable `feather-r1-2026-10-07` snapshots and retain the explicit unproven original-derived-equivalence limitation.
4. Commit coherent audit records. Do not run tests/builds or launch a game, server, Docker stack, or decompiler for this checkpoint.

## Branch

Branch: `feat/minecraft-wiki-audit`. The accepted finding snapshot is commit `4c1a8a01e26b6f2c50a589cc64d51f43ddb53aeb`; reviewer acceptance and current audit status are recorded in the branch history. `main` was merged and was up to date at the last audit commit.
