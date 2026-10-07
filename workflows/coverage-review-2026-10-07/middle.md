# Independent source-only coverage review — middle campaign

- Review status: active; no assigned pair accepted complete.
- Review base: `main` at `002137b227676caea77f6832b9f4c8d0b6200bff`.
- Requested review branch: `feat/coverage-review-middle`.
- Scope: source-discovery reports for the twelve assigned pairs only. No runtime implementation, wiki pages, prior wiki audits, tests, game, Gym, server, Docker, Gradle build, or decompilation was used.
- Review limitation: the managed checkout was created at `C:\Users\Wolfi\.codex\worktrees\coverage-review-middle\LegacyParkourCompat`, detached at the main commit. `git -C '<that path>' status --short --branch` returned `fatal: cannot change to '<that path>': Permission denied`. Per handoff instructions, no checkout was recreated. This checkpoint is therefore in the writable main checkout and is not committed on the requested review branch.

## Evidence inspected

- Read the global `C:/Users/Wolfi/.codex/AGENTS.md`, project `README.md`, `workflows/README.md`, and `workflows/movement-discovery/README.md`.
- Read the committed source-only `run.md` reports on the assigned owner branches where present. These are active run manifests, not frozen discovery catalogs.
- Read the exact readiness JSON at `build/movement-campaign-2026-10-07/ready/1.14.4/ornithe-feather.ready.json`. It reports exact version `1.14.4`, namespace `ornithe-feather`, ready status, and source/artifact/diagnostics manifests.
- Independently recalculated the SHA-256 values of the three cited manifests/diagnostics and matched the JSON values. The source manifest entries for `net/minecraft/entity/Entity.java`, `net/minecraft/entity/living/LivingEntity.java`, and `net/minecraft/entity/living/player/PlayerEntity.java` match the actual published source bytes.
- The diagnostics identify `Entity.move`, `LivingEntity.jump`/`moveRelative` and `PlayerEntity.jump`/`moveRelative`, but this verifies source presence only. It does not establish semantic coverage or body-by-body parity. The second endpoint for 1.14.4 → 1.15.2 is not published, so no paired comparison has begun.
- At the latest ready-directory inventory, 1.14.4 was the only newly published assigned endpoint. No readiness marker was present for 1.15.2, 1.16.5, 1.17.1, 1.18.2, 1.19.2, 1.19.3, 1.19.4, 1.20.1, 1.20.2, 1.20.4, 1.20.6, or 1.21.1. Directory presence alone is not treated as readiness.

## Pair disposition

Every pair remains **unaccepted / pending source comparison**. Current owner manifests and their coverage readiness:

| Pair | Owner report state observed | Independent disposition |
|---|---|---|
| 1.14.4 → 1.15.2 | Active; seven stage-level rows pending. | No paired source evidence; 1.14.4 provenance partially verified, 1.15.2 absent. Seven coarse rows are an inventory, not closure. |
| 1.15.2 → 1.16.5 | Active; 34 named bounded slices pending. | No pair artifacts. Keep all rows open; source/body checks and dependency closure not started. |
| 1.16.5 → 1.17.1 | Active; 34 bounded slices explicitly pending. | No pair artifacts. Ledger is appropriately explicit that it is a work plan, not findings or equivalence. |
| 1.17.1 → 1.18.2 | Active; 49 bounded slices pending. | No pair artifacts. No findings or terminal rows accepted. |
| 1.20.6 → 1.21.1 | Active; 35 named slices pending. | No pair artifacts. No findings or terminal rows accepted. |
| 1.18.2 → 1.19.2 | No committed report found on the named owner branch at this checkpoint. | Cannot review owner coverage until a report is committed or otherwise made available through the authorized worktree. |
| 1.19.2 → 1.19.3 | Active; 20 broad initial families pending. | No pair artifacts. Rows must be refined to method/resource slices before acceptance. |
| 1.19.3 → 1.19.4 | Active; seven coarse stage inventories pending. | No pair artifacts. These rows cannot establish whole-stage coverage. |
| 1.19.4 → 1.20.1 | Active; seven stage-level rows pending. | No pair artifacts. No paired evidence or terminal coverage. |
| 1.20.1 → 1.20.2 | No committed report found on the named owner branch at this checkpoint. | Cannot review owner coverage until a report is committed or otherwise made available through the authorized worktree. |
| 1.20.2 → 1.20.4 | Active; seven coarse stage registrations pending, with resource dependency open. | No pair artifacts. Owner correctly labels rows coarse; expand them and close cited resources before any completion claim. |
| 1.20.4 → 1.20.6 | No committed report found on the named owner branch at this checkpoint. | Cannot review owner coverage until a report is committed or otherwise made available through the authorized worktree. |

No behavioral omission is marked confirmed from these drafts: no paired source comparisons have been presented. A report missing from the branch is an availability gap, not evidence that the owner omitted a mechanic.

## Review checklist to apply as reports freeze

For each pair, I will require source-backed rows and dependency closure for:

1. The whole reachable client player tick chain, including input capture, repeated or pre/post travel movement, and the timing of velocity cancellation before and after travel.
2. Jump producer and dispatch order, guards, cooldowns, arithmetic precision, casts, literal types, operation order, and relevant cutoff behavior.
3. Every player movement-state writer and reader: pose and dimensions, resize timing, eye height, ground/fluid/collision flags, input history, abilities, sprint/jump timers, and the collision queries affected by those writes.
4. Every collision and step path: axis order, candidate selection and ties, support/edge probes, grounding, shape contexts, and callback/query timing.
5. Enumerated collision-shape providers and registrations for blocks present in the older release, including state, neighbor and context dependencies. The user hypotheses about panes/bar shapes and sneaking collision boxes remain unverified.
6. Direct movement effects, enchantments, attributes, equipment and item-use inputs, traced from consumer to producer/registration/application/removal/default/resource. Health, food, hunger, saturation, exhaustion, damage and combat emulation remain excluded.
7. Direct player position/velocity inputs from packet corrections, pushes, explosions, pistons, mounts and launch items, with external or server-supplied values identified as such.

No broad stage label, matching method name, source hash, or unchanged travel branch is accepted as a substitute for exact member ranges, both-side evidence, callers/writers and dependency closure. For relevant damaged decompiler bodies, the owner must obtain exact bytecode/source assistance before that row can close.

## Requeue requests for the parent to route

- Publish and announce exact 1.15.2 readiness JSON and cited manifests/diagnostics to unblock the first assigned pair. 1.14.4 has a ready marker whose declared manifest hashes and three core movement source entries were verified here.
- Publish exact readiness records for 1.16.5, 1.17.1, 1.18.2, 1.19.2, 1.19.3, 1.19.4, 1.20.1, 1.20.2, 1.20.4, 1.20.6 and 1.21.1. Continue to serialize source writes under the source-owner lock.
- Commit or expose the missing source-only run reports for 1.18.2 → 1.19.2, 1.20.1 → 1.20.2 and 1.20.4 → 1.20.6 so their coverage can be reviewed independently.
- Resolve the review checkout’s exact `Permission denied` access issue or provide the permitted workspace path. Do not create another checkout; the task explicitly says to report Git worktree-operation failures instead of recreating worktrees.

## Resume point

Continue from the newest committed owner reports and readiness records. First verify each pair’s exact IDs, namespace, cited hashes, source method diagnostics and actual relevant bodies. Then inspect the frozen discovery report against the checklist above and record pair-specific accepted rows, confirmed omissions, overstated claims, missing evidence and unresolved dependencies. Do not mark a pair accepted while its owner report remains active or any scoped row remains open.

## Live draft and source refresh (2026-10-07)

The assigned owner worktrees now expose active drafts for all twelve pairs. They remain owner-authored drafts, not frozen reports. The canonical ready catalog now has verified Mojmap markers for exact releases 1.14.4, 1.15.2, 1.16.5, 1.17.1, 1.18.2, 1.19.2, and 1.19.3. For 1.15.2 through 1.19.3, each ready JSON reports the exact requested ID and `mojmap`; each cited source-manifest, artifact-manifest and diagnostics-file SHA-256 matches the JSON. Pair readiness is therefore available through 1.18.2 → 1.19.2. The 1.19.3 → 1.19.4 pair still lacks 1.19.4’s ready marker; later endpoints remain queued.

The fixed interim checker from commit `3d91c3a` was run read-only on all twelve current owner drafts. It now correctly returns `run status is active; this is not a completion pass` for structurally valid active ledgers. Five drafts still fail because they lack all seven required inventory rows and bounded slice entries; 1.20.1 → 1.20.2 has the inventory rows but no bounded slices. The 3d91c3a version is not the canonical follow-up; the parent has announced `fba28fa154d29572263ea3f2c44cf1dc23134329`, which also handles the required `->` notation. Checker success or failure is not movement evidence.

### Preliminary independent audit: 1.15.2 → 1.16.5

The owner’s current committed run is stale relative to its thread commentary: its manifest still has 33 broad pending rows, no `### Slice` evidence blocks, and no findings files, while the owner reports having compared several call-graph candidates. Do not accept its progress until the tracked report contains the exact comparisons, dependencies and dispositions. Both exact Mojmap readiness markers and all cited manifest/diagnostics hashes have now been independently verified. The following source file hashes also match their source manifests:

- A `1.15.2/mojmap/net/minecraft/client/player/LocalPlayer.java`: `3a9019bd7b860e251c23fd8d0cd70b7f5b38566d34470c4e29b1014ef689ccbd`
- A `1.15.2/mojmap/net/minecraft/world/entity/player/Player.java`: `1ba2724c22163862b8f7fdfdea5a04a66e4db26a119d7e5360ba024724a34793`
- A `1.15.2/mojmap/net/minecraft/world/entity/LivingEntity.java`: `46d243bb7e51f7b54404aa1d7d6e6b827682d0f5925d02847c4193306c4d5e54`
- B `1.16.5/mojmap/net/minecraft/client/player/LocalPlayer.java`: `6011569e766bb1568609147be9aa14e9c08c51948e3d3a60fd066e848f6a8c2b`
- B `1.16.5/mojmap/net/minecraft/world/entity/player/Player.java`: `d2e26589bdb6a20dc914266db06aa48f50811efc792d6e63b3008c9199914960`
- B `1.16.5/mojmap/net/minecraft/world/entity/LivingEntity.java`: `b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88`

Candidate and scope dispositions from exact source reads (none accepted as a frozen finding yet):

- **Player escape probe — source delta; shape/provider closure open.** A `LocalPlayer.aiStep` calls `checkInBlock` at four X/Z probes (lines 650–655); `LocalPlayer.checkInBlock` lines 385–429 scans integer Y positions via `Player.freeAt` lines 1489–1491. B `LocalPlayer.aiStep` calls `moveTowardsClosestSpace` at four probes (lines 656–661); its helper lines 414–441 tests a bounding-box slice via `suffocatesAt` lines 443–447, which delegates to `CollisionGetter.noBlockCollision` lines 62–67 and the `CollisionSpliterator` block-shape path. This changes the escape predicate from per-cell suffocation flags over the player’s vertical cell span to a collision-shape/AABB intersection query. Close the query/shape context, all relevant partial-block providers, state/neighbors and exact historical applicability before accepting the movement consequence.
- **Water descent — source delta reachable for a flying player.** A `LocalPlayer.aiStep` descends on water+shift (lines 723–725) via `LivingEntity.goDownInWater` lines 1819–1821, which adds `(0.0, -0.04F, 0.0)`. B adds `isAffectedByFluids()` to the same gate (LocalPlayer lines 733–735); the inherited `Player` override at lines 1004–1007 returns `!abilities.flying`. B’s unchanged `goDownInWater` lines 1898–1900 therefore does not add the downward velocity while flying. Close the ability writer/packet provenance and tick ordering; do not call the added gate a no-difference merely because the LivingEntity default returns true.
- **Sprint-trigger timer — likely in-scope source delta.** In A, item-use slowdown resets `sprintTriggerTime` (LocalPlayer lines 637–641), with no corresponding shift-key reset in the same method. B retains the item-use reset (lines 643–647) and adds `if (bl2) sprintTriggerTime = 0` (lines 663–665). `bl2` is captured from `input.shiftKeyDown` before `input.tick`. Trace its per-tick caller, timer consumer and transition after sneaking is released before promoting it to a finding.
- **Elytra start gate — no older-block difference found; modern-only expansion.** A’s LocalPlayer gate at lines 715–720 excludes `onLadder`; `LivingEntity.onLadder` lines 1226–1235 explicitly covers ladder, vine, scaffolding and usable trapdoors. B’s gate at LocalPlayer lines 725–730 excludes `onClimbable`; its method and trapdoor helper (LivingEntity lines 1363–1395) retain those old cases. The verified 1.16.5 client resource `data/minecraft/tags/blocks/climbable.json` (entry SHA-256 `33bf334558c77268ba9bac414806b4b3842f8029e860857ff11abaf8447d03ff`) adds weeping/twisting vine blocks. These blocks are newer than A and must be classified modern-only. Require a checked resource-backed disposition; the method rename alone is not a historical movement finding.
- **Crouch field/dimensions — scope hypothesis remains open.** B adds a cached `LocalPlayer.crouching` field (line 91); `isCrouching()` changes from the computed predicate in A (LocalPlayer lines 596–600) to that field in B (lines 600–602). B writes the field at LocalPlayer lines 637–641 before `input.tick`; A computes from `isShiftKeyDown()` on demand. `Player.updatePlayerPose` still runs at the end of `Player.tick` in both versions (A line 283, B line 276), selects crouching from `isShiftKeyDown`, and both player dimension lookups return the `POSES` entry. Do not claim a collision-box change from the new field alone: trace all within-tick `isCrouching` consumers, actual `setPose`/`refreshDimensions` timing, the pose dimensions and shape-query contexts. This is a required route for the user’s sneaking-box hypothesis.

Owner requeue for this pair: bring the current source comparisons into the new tracked run and finding files; add bounded member rows for the escape-shape query, flying-water descent, sprint timer, local crouch cache/pose timing, and a checked modern-only disposition for new climbable tags. Record source/resource hashes and close producers/consumers. Preserve all other pending rows; no stage or travel-branch summary can stand for them.

### Live inventory structural status

All 12 owner runs remain active. The current structured reports with slice blocks are 1.16.5 → 1.17.1 (34 pending), 1.18.2 → 1.19.2 (27 pending), 1.19.2 → 1.19.3 (19 pending), 1.19.3 → 1.19.4 (7 pending), 1.20.2 → 1.20.4 (45 pending), and 1.20.4 → 1.20.6 (51 pending). Every one of these slices still lacks both-side source ranges and is pending, so none is a terminal disposition. Five other current drafts have no bounded slice blocks and no required inventory IDs; 1.20.1 → 1.20.2 has the seven inventory IDs, all pending, but no slices. This is a report-structure gap, not proof that the owners failed to research a behavior.

The 1.8 jump-apex and pane/bar hypotheses are still unverified for the assigned intervals. The collision escape candidate above creates a concrete shape-query route for pane/bar applicability, but its source/provider/resource closure remains open. No report is complete or accepted.

## Canonical checker refresh (2026-10-07)

After the prior interim pass, owner reports changed. I ran the canonical `check_completion.py` from commit `fba28fa154d29572263ea3f2c44cf1dc23134329` read-only on the live `run.md` in each of the twelve existing owner worktrees. This refresh supersedes the interim checker and draft counts above. A structurally valid `active` result is not a frozen report or source audit.

- 1.14.4 → 1.15.2: structurally valid `active`; 7 slice blocks, 7 inventories.
- 1.15.2 → 1.16.5: missing all 7 required inventory IDs; 10 slice blocks exist, but their existence does not cover the absent inventory contract.
- 1.16.5 → 1.17.1: structurally valid `active`; 34 slice blocks, 7 inventories.
- 1.17.1 → 1.18.2: missing the required-source-inventories, blind-freeze, implementation-reconciliation and independent-audit sections; missing all 7 inventory IDs and has no bounded slices.
- 1.18.2 → 1.19.2: missing the blind-discovery-freeze section; 27 slice blocks, 7 inventories.
- 1.19.2 → 1.19.3: structurally valid `active`; 19 slice blocks, 7 inventories.
- 1.19.3 → 1.19.4: structurally valid `active`; 7 slice blocks, 7 inventories.
- 1.19.4 → 1.20.1: missing all 7 required inventory IDs; 2 slice blocks.
- 1.20.1 → 1.20.2: has all 7 inventory IDs but no bounded slices.
- 1.20.2 → 1.20.4: structurally valid `active`; 45 slice blocks, 7 inventories.
- 1.20.4 → 1.20.6: structurally valid `active`; 51 slice blocks, 7 inventories.
- 1.20.6 → 1.21.1: structurally valid `active`; 35 slice blocks, 7 inventories.

No report has been frozen or accepted. The canonical checker specifically does not validate that a cited source range is complete, that the source hashes identify the checked files, or that the movement behavior and dependency closure are correct. Those remain source-review requirements. The 1.15.2 → 1.16.5 source candidates and missing inventory contract are therefore still requeue items.
### Reconciliation of refreshed 1.15.2 → 1.16.5 owner draft

The current campaign draft has since grown to 10 bounded slice blocks and eight candidate files exist under the separate legacy `workflows/movement-discovery/runs/1.15.2--1.16.5/findings/` directory. The campaign draft itself still says no findings are complete and keeps the candidate slices in-progress. Its required inventory rows are written as `- INV-TICK ...` instead of the canonical ``- `INV-TICK` ...`` form, so the canonical checker reports all seven missing. The candidate files are not indexed from this campaign run, and their slice IDs do not match the campaign ledger IDs: e.g. ledger `S1-SPRINT-RESET` vs finding `S1-SPRINT-TRIGGER`, ledger `S1-ESCAPE` vs finding `S1-WALL-ESCAPE`. Treat the “source-confirmed” labels as owner claims pending reconciliation and freeze, not as accepted dispositions.

Independent correction to `s1-02-wall-escape.md`: B’s `suffocatesAt` creates a one-block-wide X/Z column at the sampled `BlockPos` and uses the player’s `minY..maxY`; it does not query the full player X/Z bounding box, and therefore does not reach an adjacent cell merely because that cell clips the player’s bounding-box edge. A’s `blocked` loop checks the same selected block column over `floor(minY)..ceil(maxY)`, but asks the suffocation predicate per state rather than testing collision-shape intersection. The candidate is a real predicate/query source change, but its stated concrete differing precondition is unsupported and the old-block reachability remains unclosed.

The suggested pane/bar route is not established: A’s `AbstractGlassBlock.isSuffocating` explicitly returns false (A source SHA-256 `76da11cb7a61bd8f9fa82a0b66e452a568f5d1d50a6c148d9098258078985108`), and iron bars inherit the default `Block.isSuffocating`, which requires a full collision shape (A `Block.java` SHA-256 `a5819a4c676d2b7e08cce7f80ae80a13726dd17b15e0b29efdce7e37efa2bf0d`). Pane/bar shapes can therefore not satisfy the predicate used by this escape path. A relevant block route must instead identify a state where `isSuffocating` is true while its collision shape fails to intersect the Y slice; check both predicate and shape for that exact state. A/B collision-query source hash for B `CollisionGetter.java` is `b507d6be11e5985a62cfeb249a99dcb5f8edaf346f12cb2487797d9e01763eac`; A `CrossCollisionBlock.java` hash is `5088eb166ca1ca36eb496ab120338d713c30bec84ff5815ec266a52ea1444ecd`.

The sprint and water candidates are consistent with the paired LocalPlayer/Player sources already recorded above, but the current campaign ledger still marks them in-progress and no pair freeze exists. “First changed release” claims of 1.16 and 1.16.2 need exact ready source evidence for those releases, beyond this pair’s 1.15.2 and 1.16.5 artifacts. No runtime trajectory was reviewed or run.
