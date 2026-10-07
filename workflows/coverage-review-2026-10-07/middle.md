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

## First-change and finding-snapshot review (2026-10-07)

Exact release checks now supplement the 1.15.2 → 1.16.5 endpoint pair. Ready metadata and source, artifact, and diagnostics hashes match for the additional releases:

- 1.16.1 ready JSON `c19d707f7d6622733b5189599ff77aa7999cc11a3d900f29d150f9fab203ffcb`; source manifest `76fdd121070c2012f48cbdf5cf0c78490116afc94117c30b0858eedcd6ab9754`; artifact manifest `a113f9d59fa4582effb2ebf548c4388a54c9c5654fbf57fae7974dce896d1f98`; diagnostics `3a7c0831f5c6d4b5d3bc8aef2b7ef88f0c1ee7e3981379ea0181d77733a4a827`.
- 1.16.2 ready JSON `e5f69bab646b6143b0660a97fade59595ff2e924d36567b9cd285c8babd31214`; source manifest `10d312ca29ee48e25f4727adafb9fcfbd741dcebe81b343d3dcffa071952a7a7`; artifact manifest `7b8551bdc108a1584039ac22e35b6b75c3584ed85aba24561c7d340f5cbeb108`; diagnostics `a14aaa94b80b2783eb8903f2eaff520c9f623067df4d376480ca87cd26768902`.

The exact 1.16.1 `LocalPlayer.java` hash is `ee20523adfdb82f3753fa43312af170f34c4f4aa78570631c61bc8c0820dfb8b`; it contains the held-shift sprint reset and water-affectability gate, while retaining `checkInBlock` for escape. The exact 1.16.2 `LocalPlayer.java` hash is `6011569e766bb1568609147be9aa14e9c08c51948e3d3a60fd066e848f6a8c2b`; it contains `moveTowardsClosestSpace`. These checks establish the sprint and water deltas by 1.16.1, and the escape query change between the checked 1.16.1 and 1.16.2 sources. The corpus did not include exact 1.16.0 source, so phrase release scope as “present by 1.16.1” / “present by 1.16.2,” not as the earliest release.

Finding-specific decisions:

- **Sprint reset, `s1-01`: revision required.** The paired delta, timer’s complete local read/write set, captured-input timing, and present-by-1.16.1 check are independently verified. Candidate snapshot commit `3f1b615` has SHA-256 `45fd221742437d6bb01f820088a8513079b899c45d77264773d5fc982dc55f6d`. It remains blocked from implementation because the owner ledger still uses a different slice ID, says in-progress, and does not index this owner finding file.
- **Escape query, `s1-02`: revision required.** The source change is real, but the owner’s stated adjacent-player-box precondition is wrong: B queries the sampled block column with player Y bounds. Pane/bar layouts also fail the A suffocation predicate. The actual old-block predicate/shape combination that changes the selected escape result is still unproven. The 1.16.1→1.16.2 check confirms the query transition only; it does not establish a concrete consequence. Do not hand off.
- **Water descent, `s1-03`: revision required.** The exact predicate, both helper bodies, ability field, client packet writer, local ability writes, and present-by-1.16.1 source are independently verified. Candidate snapshot commit `c8b3c0d` has SHA-256 `94e2661a3275c2e10b0e7fccaaa461b414cb4516094aa6e396308b3af24345cc`. It remains blocked because the owner ledger explicitly leaves ability state provenance pending and has not reconciled its source-confirmed finding with a closed slice.
- **Elytra climb gate:** retain modern-only disposition for newer climbable-tag members; no older-block movement delta is established by the method rename.
- **Crouch field / dimensions:** remains an open audit route until all within-tick `isCrouching` consumers and actual pose/dimension refresh timing are closed.

Neither candidate snapshot is accepted for implementation. Both are review artifacts with explicit `revision required` decisions, not handoff inputs. Pair status remains active and no full-pair freeze has been accepted.

## Final live status refresh (2026-10-07)

This section supersedes the older readiness-marker and report-count summaries above. I verified all twelve pair endpoints in the shared artifact corpus. Each endpoint’s `mojmap.ready.json` says `status=ready`, has matching requested and metadata IDs, and its source-manifest, artifact-manifest and diagnostics hashes match the files on disk. This includes 1.19.4 and later endpoints; no pair endpoint is currently blocked on source readiness. The additional 1.16.1 and 1.16.2 records used for first-change checks were verified the same way.

A final canonical checker pass over the live owner reports returned 10 structurally valid non-complete reports (active or partial) and two structural failures. Current pair details:

- 1.14.4 → 1.15.2: partial; 7 slices, 7 inventories.
- 1.15.2 → 1.16.5: active; 10 slices; the seven inventory rows are not in canonical ID syntax, so the checker reports all required inventories missing.
- 1.16.5 → 1.17.1: active; 34 slices, 7 inventories.
- 1.17.1 → 1.18.2: partial; 11 slices, 7 inventories.
- 1.18.2 → 1.19.2: active; 27 slices, 7 inventories.
- 1.19.2 → 1.19.3: active; 19 slices, 7 inventories.
- 1.19.3 → 1.19.4: active; 7 slices, 7 inventories.
- 1.19.4 → 1.20.1: active; 7 slices; all seven required inventory IDs are missing.
- 1.20.1 → 1.20.2: active; 7 inventories, no bounded slices.
- 1.20.2 → 1.20.4: active; 46 slices, 7 inventories.
- 1.20.4 → 1.20.6: active; 51 slices, 7 inventories.
- 1.20.6 → 1.21.1: active; 35 slices, 7 inventories.

No pair is frozen or accepted. The two 1.15.2 → 1.16.5 snapshots have reviewer decisions `revision required`, so neither can be used as an implementation handoff.

## Scope and artifact-integrity refresh (2026-10-07)

The latest scope clarification in workflow commit `8ad33f978aa65e68108959a028b99313a96f5607` keeps direct player-side velocity, impulse, and knockback response in scope, while excluding attack/damage resolution, other-entity movement and vehicle physics. This changes the external-movement coverage audit. The assigned reports were scanned for bounded player-knockback coverage; several still list only packets, pushes, pistons, mounts or launches.

### New 1.15.2 → 1.16.5 player-knockback route

The latest owner run now explicitly states the broadened player-motion scope and has 16 slice blocks. Its `INV-EXTERNAL` row still lists only corrections, pushes, mounts, pistons and launches, with no knockback response slice. The paired `LivingEntity.knockback` bodies are a concrete missed route: A 1.15.2 lines 1195–1202 checks `random.nextDouble() < KNOCKBACK_RESISTANCE` and otherwise applies the full strength; B 1.16.5 lines 1319–1327 multiplies strength by `(1.0 - KNOCKBACK_RESISTANCE)` before the same velocity response. `Player` extends `LivingEntity` in both versions and has no override. With equal positive strength and resistance strictly between 0 and 1, A applies the full impulse probabilistically while B applies a reduced impulse deterministically. This is a source-confirmed direct motion delta under the clarified scope; the external attribute value and invocation-side boundary remain open. No attack/damage code was examined.

Candidate evidence snapshot: `1.15.2--1.16.5-S7-player-knockback.md`, commit `30a26b3`, SHA-256 `372249fe7a53dca98154f0ce90bb102a321efeb6468104b87d2aae1c8f75fbee`. Reviewer decision: **revision required**, handoff blocked until the owner adds and closes the player-only slice and commits an owner-authored snapshot for fresh blind review.

A scan of all twelve assigned runs found explicit knockback coverage language or a slice in only a subset. Concrete missing/underspecified external slice routes remain for 1.14.4 → 1.15.2, 1.16.5 → 1.17.1, 1.17.1 → 1.18.2, 1.18.2 → 1.19.2, 1.19.3 → 1.19.4, 1.19.4 → 1.20.1, and 1.20.1 → 1.20.2. Existing mentions in other reports remain active work, not independent acceptance; their method ranges, player reachability, state dependencies and evidence still need review.

### Independent Feather revision verification

I freshly verified revision `feather-r1-2026-10-07` for all six early Feather versions. For each version, the revised jar hash matched both `artifact.sha256` and `revision.json`; the revision metadata matched the exact version and namespace; the original ready source-manifest hash and artifact-manifest hash matched the revision record; every published source file and every original raw input entry except the unavailable old derived jar matched its manifest; and the referenced verification-log hash matched. Counts and results:

- 1.8.9: 1,612 source files, 36 raw inputs, zero differences; revised jar `5c4cff3e4ac10ea1e1da166279133801ad304b4557d5ee4a77a2430977afb6a5`.
- 1.9.4: 1,819 source files, 36 raw inputs, zero differences; revised jar `fbcf50795566e12b8eab0e733b136ed562c4009d707ef4a7a4994936491816a3`.
- 1.10.2: 1,845 source files, 36 raw inputs, zero differences; revised jar `0c1d71990c9c0d7cc88debf7e67663bd64c8dc7de5872176088a3119527fb28b`.
- 1.11.2: 1,921 source files, 36 raw inputs, zero differences; revised jar `ba1872d5fd341770a45aeeb8d372776a1b89f0b88a11a72f15aa0fe879b6a29f`.
- 1.12.2: 2,050 source files, 37 raw inputs, zero differences; revised jar `fcc17537a14a423e2086f600047725ec1fcfd4c7fcf5c0d1a5bda491966c1b87`.
- 1.13.2: 2,711 source files, 41 raw inputs, zero differences; revised jar `b28c33e023928045c8fd7ed7727a860a6820241e371ddb16108366c6192b718c`.

For every revision, `originalDerivedArtifactAvailable=false`. This proves consistency of the new snapshot with its revision record and preserves the unchanged source/raw-input evidence; it does not prove identity with the unavailable original mapped jar or that the hash change was metadata-only. None of my twelve assigned pair snapshots relies on Feather: the 1.14.4 → 1.15.2 pair uses Mojmap at both ends, and later assigned pairs use Mojmap. The 1.14.4 Feather and Mojmap ready records were both independently verified with matching exact IDs and namespace-specific source, artifact and diagnostics hashes.

### Latest owner report status

A fresh canonical checker pass over the twelve live runs returned 11 structurally valid non-complete reports and one structural failure. Current pair counts:

- 1.14.4 → 1.15.2: partial; 7 slices, 7 inventories.
- 1.15.2 → 1.16.5: active; 16 slices; checker reports all 7 inventories missing because their labels do not use canonical ID syntax.
- 1.16.5 → 1.17.1: active; 34 slices, 7 inventories.
- 1.17.1 → 1.18.2: partial; 11 slices, 7 inventories.
- 1.18.2 → 1.19.2: partial; 27 slices, 7 inventories.
- 1.19.2 → 1.19.3: active; 22 slices, 7 inventories.
- 1.19.3 → 1.19.4: active; 12 slices, 7 inventories.
- 1.19.4 → 1.20.1: active; 13 slices, 7 inventories.
- 1.20.1 → 1.20.2: active; 4 slices, 7 inventories.
- 1.20.2 → 1.20.4: active; 46 slices, 7 inventories.
- 1.20.4 → 1.20.6: partial; 56 slices, 7 inventories.
- 1.20.6 → 1.21.1: active; 35 slices, 7 inventories.

All endpoints for the assigned pairs are now ready with verified Mojmap metadata and manifest hashes. No pair is frozen or accepted. The sprint, water and knockback candidate snapshots for 1.15.2 → 1.16.5 all remain `revision required`, so none is an implementation handoff.

## Final partial checkpoint (2026-10-07)

- Review branch: `feat/coverage-review-middle`; this is the existing managed worktree at `C:\Users\Wolfi\.codex\worktrees\coverage-review-middle\LegacyParkourCompat`, not the main checkout. The earlier checkout-permission limitation recorded above is superseded.
- Scope remained source-only for the twelve assigned pairs. No implementation, runtime/game run, test, wiki, build, Docker operation, or message to an owner was performed.
- All twelve assigned endpoint readiness records and Mojmap source/artifact/diagnostics hashes were previously verified. The six Feather revisions were independently rechecked as recorded above; original derived jars remain unavailable, so identity with those jars is unverified.
- Canonical structural checker from commit `fba28fa154d29572263ea3f2c44cf1dc23134329` was run read-only against the latest owner reports. Eleven reports are structurally valid but non-complete. `1.15.2--1.16.5` is the sole structural failure because all seven inventory rows lack canonical inventory IDs. No checker result is source truth or pair acceptance.
- The `1.17.1--1.18.2` owner worktree is absent from the registered worktree list and its directory contains only `.codex-worktree-name`; its current report was read from owner branch commit `7813662bf933ede80b681735fd494180141d6dac`. Do not recreate a worktree. Check the latest owner branch/report on resume.
- Owner-designated terminal/open slices below are exact report states at this checkpoint. “Terminal” records the owner report’s state only; this review has not independently audited all those rows. No pair is accepted complete or frozen.

### Slice checkpoint by pair

| Pair | Owner terminal slices | Owner open slices |
|---|---|---|
| 1.14.4 → 1.15.2 (8) | `S-ELYTRA-START`, `S-ENTITY-MOVE`, `S-LIVING-JUMP`, `S-FRICTION-SAMPLE`, `S-HONEY-BLOCK` | In progress: `S-INPUT-KEYS`, `S-LOCAL-PRETRAVEL`, `S-EDGE-BACKOFF` |
| 1.15.2 → 1.16.5 (16) | `S1-INPUT-VECTOR`, `S1-KEYBOARD`, `S1-ESCAPE`, `S1-SPRINT-RESET`, `S1-ELYTRA`, `S1-WATER-DESCENT`, `S2-EDGE`, `S3-WATER`, `S3-LAVA`, `S3-FALL-FLYING`, `S3-JUMP`, `S5-WATER-CURRENT`, `S5-LAVA-CURRENT` | In progress: `S1-LOCAL-TICK`, `S1-LOCAL-AISTEP`, `S3-GROUND-AIR` |
| 1.16.5 → 1.17.1 (34) | `S3-01`, `S3-03`, `S3-06`, `S3-07` | In progress: `S1-01`, `S1-02`, `S1-03`, `S1-04`, `S1-05`, `S1-06`, `S2-01`, `S2-02`, `S2-03`, `S3-02`, `S3-04`, `S3-05`, `S3-08`, `S3-09`, `S4-01`, `S4-02`, `S4-03`. Pending: `S4-04`, `S4-05`, `S5-01`, `S5-02`, `S5-03`, `S5-04`, `S5-05`, `S6-01`, `S6-02`, `S6-03`, `S7-01`, `S7-02`, `S7-03` |
| 1.17.1 → 1.18.2 (14) | `T-SPRINT`, `T-ELYTRA`, `T-AUTOJUMP-ORDER`, `T-AUTOJUMP-BORDER`, `T-FALL-RESET`, `T-PLAYER-CORRECTION`, `T-PLAYER-IMPULSE`, `T-BOAT-PASSENGER`, `T-POSITION-THRESHOLD` | In progress: `T-INPUT`, `T-EDGE-GATE`, `T-ENTITY-COLLISION`, `T-WORLD-PROPERTIES`. Pending: `T-MODIFIERS` |
| 1.18.2 → 1.19.2 (27) | `T01`, `T03` | In progress: `T02`, `T04`, `T05`, `T06`, `T07`, `C02`, `C03`, `C04`, `W02`, `W03`, `M03`, `E01`, `E02`, `E03`. Pending: `P01`, `P02`, `P03`, `P04`, `C01`, `W01`, `W04`, `M01`, `M02`, `M04`, `X01` |
| 1.19.2 → 1.19.3 (35) | `S3-mounted-sprint`, `S5-water-source`, `S5-lava-source`, `S3-fall-distance-farm-trample`, `S6-player-movement-speed-effects`, `S6-direct-travel-effect-gates`, `S6-depth-strider-fluid`, `S6-soul-speed-ground`, `S6-swift-sneak`, `S7-player-rotation-correction`, `S5-block-coefficients`, `S5-movement-tags`, `S1-input-sample`, `S2-pose-refresh`, `S3-player-jump-travel`, `S4-entity-move-method` | Pending: `S1-input`, `S2-player-state`, `S3-travel-dispatch`, `S3-ground-air`, `S3-jump`, `S3-climb-swim`, `S3-glide`, `S4-move-core`, `S4-step-edge`, `S4-shapes-query`, `S4-callbacks`, `S5-block-registration`, `S5-block-shapes`, `S5-fluid-data`, `S6-effects-attributes`, `S6-enchantments-equipment`, `S7-external-velocity`, `S7-mount-transition`, `S7-closure` |
| 1.19.3 → 1.19.4 (24) | `I-TICK-AIR-SPEED`, `I-TICK-AUTO-JUMP`, `I-TICK-MOUNT-START`, `I-TICK-INPUT-SAMPLE`, `I-TICK-PORTAL-PROGRESS`, `I-TICK-UNSTUCK-POSITION`, `I-TICK-FLIGHT-SPEED`, `I-TICK-SWIM-HEAD-PROBE`, `I-TICK-LOCAL-TRAVEL-GATE`, `I-TICK-SPRINT-ELIGIBILITY`, `I-TICK-TRAVEL-FLUIDS`, `I-TICK-TRAVEL-FALL-FLYING`, `I-TICK-EDGE-NUDGE`, `I-COLLISION-ENTITY-MOVE`, `I-COLLISION-QUERY-ASSEMBLY`, `I-EXTERNAL-PACKET-VELOCITY`, `I-EXTERNAL-PLAYER-KNOCKBACK` | Pending: `I-TICK-REMAINDER`, `I-STATE-REMAINDER`, `I-COLLISION-REMAINDER`, `I-WORLD`, `I-MODIFIER`, `I-EXTERNAL`, `I-EXCLUSIONS` |
| 1.19.4 → 1.20.1 (19) | `S1-01`, `S1-PORTAL`, `S1-02`, `S1-03`, `S1-04`, `S1-05`, `S1-06`, `S3-01`, `S4-01`, `S4-02`, `S3-02`, `S3-03`, `S7-01`, `S7-02`, `S7-03`, `S7-05`, `S7-06`, `S7-07`, `SCOPE-01` | None reported open; independent full-pair review/freeze still pending |
| 1.20.1 → 1.20.2 (13) | `S1-TICK-ORDER`, `S1-PASSENGER-CROUCH-INPUT`, `S2-POSE-CLEARANCE`, `S3-PLAYER-TRAVEL`, `S3-LIVING-TRAVEL`, `S4-REMOTE-PLAYER-INTERPOLATION`, `S5-PARTIAL-UPDATE-LERP-TARGET`, `S6-LIVING-AISTEP-JUMP-GLIDE`, `S7-CLIENT-PLAYER-CORRECTIONS`, `S8-PLAYER-KNOCKBACK-PUSH`, `S9-ENTITY-MOVEMENT-AXES`, `S11-FLUID-CONTAINER-STATE-PATH` | In progress: `S10-BLOCK-COLLISION-PROVIDER-SWEEP` |
| 1.20.2 → 1.20.4 (46) | `S-EXCL-01` | In progress: `S1.1`, `S1.2`, `S1.3`, `S1.4`, `S1.5`, `S1.6`, `S1.7`, `S2.1`, `S2.2`, `S3.1`, `S7.1`, `S7.2`, `S7.3`. Pending: `S2.3`, `S2.4`, `S3.2`, `S3.3`, `S3.4`, `S3.5`, `S3.6`, `S3.7`, `S3.8`, `S3.9`, `S4.1`, `S4.2`, `S4.3`, `S4.4`, `S4.5`, `S4.6`, `S4.7`, `S4.8`, `S5.1`, `S5.2`, `S5.3`, `S5.4`, `S5.5`, `S5.6`, `S5.7`, `S6.1`, `S6.2`, `S6.3`, `S6.4`, `S6.5`, `S7.4`, `S7.5` |
| 1.20.4 → 1.20.6 (56) | `S1-input-sampling`, `S1-local-tick-call-order`, `S5-powder-snow-contact-state`, `S5-current-block-state-consumers`, `S7-client-motion-payloads`, `S7-player-knockback-push` | In progress: `S1-flight-tick`, `S2-dimensions`, `S3-velocity-cutoffs`, `S3-gravity-drag`, `S3-ground-jump`, `S3-sprint-jump`, `S4-step-candidates`, `S6-attributes`, `S6-server-boundary`, `S7-velocity-writers`. Pending: `S1-input-motion`, `S1-sprint-gates`, `S1-jump-gates`, `S1-tick-order`, `S2-pose-selection`, `S2-player-state`, `S2-sprint-consumers`, `S2-flight-abilities`, `S3-travel-dispatch`, `S3-ground-acceleration`, `S3-air-acceleration`, `S3-climbing`, `S3-water`, `S3-lava`, `S3-gliding`, `S3-effects-branch`, `S4-box-movement`, `S4-edge-probes`, `S4-grounding`, `S4-collision-query`, `S4-shapes`, `S4-callbacks`, `S4-fluid-contact`, `S5-landing-bounce`, `S5-friction-speed`, `S5-slowdown-contact`, `S5-climbables`, `S5-fluid-blocks`, `S5-pistons`, `S5-partial-shapes`, `S5-registrations`, `S5-modern-only`, `S6-effects`, `S6-enchantments`, `S6-equipment`, `S6-resources`, `S7-position-writers`, `S7-riding`, `S7-closure`, `SCOPE-exclusions` |
| 1.20.6 → 1.21.1 (35) | `I1 - local input sampling, input fields and keyboard`, `I2 - local player tick order, superclass tick, travel dispatch and repeated movement calls`, `I3 - yaw-to-motion conversion, normalization and input scaling`, `I4 - sprint start and stop gates`, `P3 - sprint gates and their vanilla state consumers; exclude hunger`, `L1 - travel dispatch and ground`, `L3 - jump power, sprint-jump impulse and jump gates`, `L5 - water` | In progress: `P1 - pose selection, dimensions, eye height, resize timing and collision`, `P2 - abilities, flight state`, `P4 - active-item state, edge sneaking, defaults, initialization, updates and reset timing`, `L2 - friction, gravity, drag and velocity thresholds`, `L4 - climbing clamps and movement callbacks`. Pending: `L6 - movement speed, jump and gravity attributes`, `C1 - bounding-box movement, position updates and axis ordering`, `C2 - step-up candidates, tie-breaking, edge probes and support`, `C3 - velocity cancellation`, `C4 - world collision queries, AABB`, `C5 - fluid contact, block callbacks and callback order; track repeated queries`, `B1 - base block`, `B2 - landing`, `B3 - friction`, `B4 - contact slowdown (webs, honey, powder snow) and historical applicability`, `B5 - climbables (ladders, vines and related blocks), callbacks and support`, `B6 - water`, `B7 - moving pistons and player displacement`, `B8 - shape`, `E1 - Speed, Slowness, Jump Boost, Levitation, Slow Falling, Dolphin's Grace and Blindness consumers`, `E2 - Depth Strider, Soul Speed, Swift Sneak, Frost Walker and Riptide consumer-to-registration`, `E3 - Elytra, use-item slowdown, equipment, movement item components and application`, `E4 - other discovered movement-affecting attributes`, `X1 - incoming velocity`, `X2 - player knockback`, `X3 - mount`, `X4 - final unresolved movement-state writers, changed dependencies and cross-mechanic interactions` |

### Independent finding-snapshot decision: 1.15.2 → 1.16.5

**Accepted as an independently source-reviewed finding snapshot:** `F-S1-OPEN-SHULKER-ESCAPE`, owner snapshot commit `d859478b9f6de63ba4a121a1f31d6dc90ad4f51e`; finding file SHA-256 `11860ba106197cae687735e641db4c9ef7057ddadcddeafe5c16f012e8e657e4`. The finding file is unchanged from its snapshot commit. I independently checked all eight cited source-file hashes against the ready source trees and source manifests. The checked route is A `LocalPlayer#checkInBlock/blocked` and `Player#freeAt`, with `ShulkerBoxBlock#isSuffocating`; B `LocalPlayer#moveTowardsClosestSpace/suffocatesAt`, `Blocks#shulkerBox`, `ShulkerBoxBlockEntity#isClosed`, `CollisionGetter#noBlockCollision`, and `CollisionSpliterator#collisionCheck`. A returns `true` for shulker suffocation unconditionally. B tests `isClosed()` and filters by that predicate before evaluating collision-shape intersection. Opening a shulker makes its block entity non-closed; when its sampled column is the only obstructing sample, A may write the 0.1 horizontal escape impulse while B does not enter that escape branch from the shulker. This is a source-level consequence, not a claim about final displacement.

The first changed release remains unknown within `(1.15.2, 1.16.5]`; runtime validation was not performed. This snapshot decision does not accept the pair complete. The owner run is still partial, has three in-progress slices, and retains five open inventories. Implementation handoff remains blocked pending full-pair source freeze. The sprint (`3f1b615`, `45fd221…`), water-descent (`c8b3c0d`, `94e266…`), and player-knockback (`30a26b3`, `372249…`) snapshots remain **revision required**.

### Resume commands and order

1. Resume this branch with `git -C 'C:\Users\Wolfi\.codex\worktrees\coverage-review-middle\LegacyParkourCompat' status --short --branch` and inspect its final commit before editing.
2. Refresh owner locations with `git worktree list --porcelain` from `D:\Javastuff\LegacyParkourCompat`. Do not recreate the missing 1.17.1 → 1.18.2 owner worktree; read the latest owner branch/report until its permitted checkout is available.
3. Run the canonical checker read-only against each current `workflows/source-campaign-2026-10-07/<A>--<B>/` report using `workflows/movement-discovery/check_completion.py` from the then-current workflow commit. Re-parse owner slice statuses because they may have advanced after this checkpoint.
4. Continue independent coverage review at 1.14.4 → 1.15.2 (`S-INPUT-KEYS`, then `S-LOCAL-PRETRAVEL`, then `S-EDGE-BACKOFF`), while retaining the accepted shulker snapshot decision and the three revision-required candidates for 1.15.2 → 1.16.5. Keep reviewing remaining pairs in order; do not treat owner-terminal rows as independently accepted.
5. Keep the broadened scope: direct player-side velocity/impulse/knockback response is in scope; attack/damage resolution, non-player movement and vehicle physics are excluded. No wiki or implementation feedback to source owners before the full pair freeze.