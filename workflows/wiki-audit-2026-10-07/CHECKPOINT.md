# Wiki audit checkpoint

**Checkpoint status: PARTIAL.** This records a bounded Wiki/source audit slice. It is not full campaign coverage and does not freeze the whole source pair.

## Closed slice

- The immutable Feather revision snapshot `wiki-feather-r1-2026-10-07` was consumer-verified and accepted by an independent reviewer at commit `4c1a8a01e26b6f2c50a589cc64d51f43ddb53aeb`.
- Bounded source findings captured there: 1.8.9→1.9.4 negligible-motion cutoff, pane collision shape, sneaking dimensions, and ladder thickness; 1.8.9→1.10.2 auto-jump; 1.8.9/1.9.4→1.11.2 sneak-edge guard; 1.12.2→1.13.2 swimming pose and wall collision; 1.13.2→1.14.4 pose clearance and ladder jumping.
- Ops audit passed for the six revised Feather bundles (1.8.9, 1.9.4, 1.10.2, 1.11.2, 1.12.2, 1.13.2). Consumer verification confirmed the immutable JAR hashes, unchanged ready/source/raw-input manifest hashes, and zero source/raw-input differences.
- The original derived JARs are unavailable. Their equivalence to the revised snapshots remains **unproven**; the old cache mismatch is not waived.
- Source trees are published through 1.21.11 and 26.2; exact source identities and comparison citations for closed findings are in [minecraft-wiki.md](minecraft-wiki.md) and [feather-r1-findings-snapshot.md](feather-r1-findings-snapshot.md).

## Newly published, awaiting Wiki-lane review

- Normal 1.8.9→1.9.4 jump apex: snapshot `wiki-jump-apex-cutoff-1.8.9-1.9.4`, commit `ab102b5d5da292e76460801ab362711acd734996`, finding-file SHA-256 `3a413b1e0e62cb5da276a6209779e60c3bd97874d7c166f5999142f067bdfd3a`. Source-ordered recurrence reproduces the Wiki's rounded maxima; exact snapshot cutover and other jump paths remain open. Independent review pending.
- Swimming pitch control: exact 1.12.2→1.13.2 source comparison; snapshot `wiki-swimming-pitch-1.12.2-1.13.2`, commit `b20730f64ed07090b618f6101467f8bde07e0024`, SHA-256 `dd0abd5a90ac1813197e4c09ab97ed0e0027c48e237a148bbf95bde2f64e6a89`. It is source-confirmed and not yet independently accepted; original revised Feather-JAR equivalence remains unproven.
- Sprint-swimming: exact 1.12.2→1.13.2 local input and water-travel comparison; snapshot `wiki-sprint-swimming-1.12.2-1.13.2`, commit `bb0c70e808818dc6b1155a016e4434d6867cbea6`, finding-file SHA-256 `334d7608e28efd543d2ecb5f417ac2cc758f9e38688f422e438a46f03ffc802d`. It confirms submerged sprint entry and the 0.8→0.9 horizontal water multiplier while sprinting. Independent Wiki-lane review pending.
- Unconnected iron bars: exact 1.8.9→1.9.4 comparison; snapshot `wiki-iron-bars-unconnected-shape-1.8.9-1.9.4`, commit `9fb2bb9f0fb399100e9bd455930757db2f327b61`, finding-file SHA-256 `198b766542b8b790c919e1609271112cb2863eba30083273b9f97e009ba25b17`. Both releases register them as `PaneBlock`, so the no-connection crossing-strips→central-post delta is source-confirmed. Connected states and the changed neighbor predicate remain open; independent review pending.
- Creative flight: bounded 1.8.9→1.9.4 snapshot `wiki-creative-flight-1.8.9-1.9.4`, commit `bf7847b53d53300c40c24837f3eead1076022726`, finding-file SHA-256 `854bc8c2bd7f6c4d4df568fa870c4034ec533dff1901d5631f966af4a6f411cd`. The sprint multiplier, fluid bypass, and cobweb bypass are source-equivalent; the 1.9.4 flight branch also writes fall distance and flag 7, whose state consumers and later intervals remain open. Independent review pending.
- Fence connection to End Portal Frame: exact 1.8.9→1.9.4 source comparison; corrected immutable snapshot `wiki-fence-end-portal-frame-1.8.9-1.9.4`, commit `103786e872f13a44aa0232a562f2eaab8eb5e185`, finding-file SHA-256 `e5edb59eaa858f4905ec9f87862344bfbece0fe5e22315e86c531c071e638852`. It corrects the 1.9.4 source-manifest provenance in the earlier snapshot; the bounded source finding is awaiting independent Wiki-lane review. No runtime validation was performed.

## Resume progress (2026-10-08)

- Fence state-dependency comparison is now closed for the End Portal Frame neighbor only. `Material.STONE` remains solid-blocking in both versions; 1.8.9 inherits `Block.isCube() == true`, while 1.9.4 `EndPortalFrameBlock.isCube(BlockState)` returns false and the resolved state delegates to it. As a result, an adjacent fence adds that cardinal collision arm in 1.8.9 and omits it in 1.9.4. Snapshot `wiki-fence-end-portal-frame-1.8.9-1.9.4` records source and manifest hashes; independent review remains pending. This does not close the broader fence-shape catalog.
- The 1.13.2→1.14.4 crawl-speed lead has not produced a separate finding: the inspected `PlayerEntity.moveRelative` pitch correction and local sprint-stop branch match the 1.13.2 forms. Travel input/acceleration and pose transitions remain open; do not infer full crawl equivalence from these methods.
- The swimming-pitch and sprint-swimming source comparisons remain bounded; both snapshots are awaiting independent Wiki-lane review. The sprint-swimming release difference covers submerged sprint entry and horizontal water drag, not the full swim/crawl transition sequence, surface swimming, or every submersion gate.
- Resume started at `2d20e330083ca7bb738e750ae329bbf67d618ac2` on `feat/minecraft-wiki-audit`. The original fence snapshot was committed as `005e6475f09a4126ff8265651626fe71a5ccf616` and then superseded solely to correct the 1.9.4 source-manifest hash; the corrected snapshot is `103786e872f13a44aa0232a562f2eaab8eb5e185`. Local `main` was an ancestor at resume with no later commits to merge.

## Pending slice

- The wider Wiki candidate catalog in `minecraft-wiki.md` remains open wherever it lacks a direct exact-release-pair source comparison. The ordinary 1.8.9→1.9.4 jump-apex calculation is source-confirmed; exact snapshot cutover and other jump paths remain open. Sprint-swimming submerged entry and horizontal water drag are source-confirmed for 1.12.2→1.13.2; full swim/crawl transitions and surface swimming remain open. Creative-flight sprint multiplier, fluid bypass, and cobweb bypass are source-equivalent for 1.8.9→1.9.4, but flight-state writes and later intervals remain open. Connected iron-bars/pane states, other fence and wall states, and any later-version change to the pre-1.8.9 ladder/slope behaviors remain open. The bed-walking first-introduction lead is dated to 1.8/14w32c, before the 1.8.9 audit baseline; later continuity remains open. The 2026-10-08 Wiki fetch/access log is in `wiki-page-fetch-log.md`. Reconcile each unexamined boundary against the candidate-claim table before treating the catalog as covered.
- The 1.17.1→1.18.2 sprint collision predicate, the 1.21.3→1.21.5 sprint-stop add/revert with later checks, and the released 1.21.3→1.21.11 diagonal input path have already been source-compared in `minecraft-wiki.md`; they are not pending items in this checkpoint.
- The exact-version source roster being available does not itself establish those boundaries. Keep health/food/damage/combat-state production and non-player/vehicle physics excluded.
- The committed swimming pitch snapshot is an additional narrow finding, not closure of the broader swim/crawl inventory.
- Do not provide implementation feedback or treat bounded findings as a whole-pair freeze. Continue this Wiki lane independently; other source-only comparisons remain blind and Wiki lanes stay sealed until their respective full-pair freeze.

## Resume instructions

1. Confirm the checkout with `git status --short --branch` and record its current tip with `git rev-parse HEAD`.
2. Read this checkpoint, then continue only the pending Wiki-to-exact-source comparisons listed above using the already published exact-version trees.
3. For any new Feather evidence, use immutable `feather-r1-2026-10-07` snapshots and retain the explicit unproven original-derived-equivalence limitation.
4. Commit coherent audit records. Do not run tests/builds or launch a game, server, Docker stack, or decompiler for this checkpoint.

## Branch

Branch: `feat/minecraft-wiki-audit`. At the 2026-10-08 resume, the accepted Feather finding snapshot is commit `4c1a8a01e26b6f2c50a589cc64d51f43ddb53aeb`, the swimming pitch finding is commit `b20730f64ed07090b618f6101467f8bde07e0024`, and the report index is committed at `b74b447da3a1ef578909436898569cf7ab1d769b`. `main` was already an ancestor with no post-branch commits. The broader catalog remains partial.
