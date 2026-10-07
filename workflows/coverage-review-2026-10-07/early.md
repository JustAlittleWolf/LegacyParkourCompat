# Independent source coverage review — early pairs

- Reviewer branch: `feat/coverage-review-early`
- Base: `002137b227676caea77f6832b9f4c8d0b6200bff` (`main`)
- Status: `active`; no assigned pair is accepted as complete.
- Scope: source-only coverage audit of the six assigned adjacent pairs. Runtime code, wiki pages, wiki audit output, tests, game clients, TAS, Gym, server, and Docker were not consulted or run.
- Review output is intentionally separate from each source owner’s status. The owner reports are still active and may gain evidence after this checkpoint.

## Source admission checkpoint

I read the global and project `AGENTS.md`, repository `README.md`, movement workflow README, ordered source navigation, and the hardened campaign README from the workflow worktree. That campaign requires a complete client-player tick graph, bounded member-level slices, producer-to-consumer closure, explicit inventories for dimensions/poses, shapes/registrations/neighbors, fluids, attributes/effects/equipment, and external movement writers. A static schema check cannot establish source truth.

I independently read the actual shared readiness JSON files for `1.8.9`, `1.9.4`, `1.10.2`, `1.11.2`, `1.12.2`, and `1.13.2`. Each has the exact `versionId`, `ornithe-feather` namespace, and `ready` status. I verified every source-manifest entry against the published source tree and every artifact-manifest entry against the campaign artifact root. All six source sets and all six artifact sets had zero missing or mismatched entries. Each source/artifact/diagnostics manifest file hash matched its readiness JSON. Counts: 1,612/37; 1,819/37; 1,845/37; 1,921/37; 2,050/38; 2,711/42 (source/artifact rows). `1.14.4` has no ready record yet, so the `1.13.2 → 1.14.4` pair cannot admit B-side source evidence.

## Pair disposition snapshot

| Pair | Owner report state observed | Independent disposition |
|---|---|---|
| 1.8.9 → 1.9.4 | No new campaign `run.md` was present in the owner worktree at the first inventory; owner is now actively building the fresh report. The prior discovery ledger is explicitly partial. | Not accepted. Fresh report, complete per-tick graph, bounded evidence, and all remaining stages are required. |
| 1.9.4 → 1.10.2 | New campaign `run.md` is active. It has detailed planned leaf rows, but the observed rows are pending; input sampling is the only paired body comparison described so far. | Not accepted. A plan and identical input method do not close the other slices. |
| 1.10.2 → 1.11.2 | New campaign `run.md` is active. At the observed checkpoint, readiness validation and exact method comparisons were still in progress. | Not accepted. |
| 1.11.2 → 1.12.2 | New campaign `run.md` is active. Source inventory and shape/provider closures remain underway. | Not accepted. |
| 1.12.2 → 1.13.2 | New campaign `run.md` is active. Both endpoints now have ready records, but the observed ledger remains stage-level/pending and the B-side index is not yet closed. | Not accepted. |
| 1.13.2 → 1.14.4 | New campaign `run.md` is active; exact 1.14.4 readiness is pending. | Not accepted; B-side evidence is unavailable. |

The previous `workflows/movement-discovery/runs/1.8.9--1.9.4/run.md` and `1.9.4--1.10.2/run.md` are partial historical catalogs, not fresh campaign completion. Their open collision, block/fluid, effect/equipment/resource, and external-influence slices remain explicit audit risks. Historical findings do not substitute for the new scope or for current source verification.

## Independently identified early-pair audit leads

These are source-backed review leads, not accepted owner findings. Hashes identify complete source files; each owner must cite exact bounded method ranges, verify the relevant mapped-jar bodies where diagnostics require it, and close the player reachability/dependency chain.

### 1.8.9 → 1.9.4: vertical velocity cutoff

- A `LivingEntity.mobTick()` lines 1406–1415 (`ready/1.8.9/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, SHA-256 `082831C6578E3A70FA6CEA5B90BC3EEFC26678259B66334470DE22B90B5B0E4E`) sets X/Y/Z velocity to zero when `abs(component) < 0.005`.
- B `LivingEntity.mobTick()` lines 1666–1675 (same relative path under 1.9.4, SHA-256 `BBB7703F18FD5DA05C4E4A43A77EA644B388E63C01D34166D308EA52054BE4E5`) uses `< 0.003` for all three components.
- The Y component is consumed by the subsequent jump dispatch and travel path, so this is directly relevant around a jump apex as well as other low vertical motion. The report must trace local-player `tick → mobTick` dispatch, pre-travel cancellation, jump ordering, all travel branches, and post-travel writes; verify `mobTick` bytecode/body integrity; and separate the source-proven cutoff from any predicted timing consequence. Do not describe this as a change to `jump()` unless that method itself differs.

### 1.8.9 → 1.9.4: pane and iron-bar shape closure

- A `PaneBlock.addCollisions()` lines 61–92 recomputes four neighbor connection predicates and adds mutable boxes. `PaneBlock.updateShape()` lines 100–132 writes a state shape. File SHA-256 `F099CDC306C365B73632FC703F72B61BC6B304CB5DF06C43234965F89E28DE61`.
- B `PaneBlock.addCollisions()` lines 52–71 adds a center box and directional entries from `SHAPES`; `getShape()` lines 77–81 and `resolveVirtualProperties()` lines 104–110 derive the selected geometry from neighbor connections. `shouldConnectTo()` lines 133–140 uses `block.defaultState().isCube()` where A uses `block.isOpaque()`; B file SHA-256 `E6E3DD856EFB96146634AB8F56C915AFBC6116FD51973C25229EF7AA2CE0E5C8`.
- This is not closed by comparing only the common central thickness or by observing similar arrays. Enumerate all relevant pane subclasses/registrations (including iron bars and stained glass panes), compare the complete collision-union geometry for every connection combination, and establish which historical neighboring blocks can make `isOpaque()` and default-state cube-ness diverge. Trace the state/neighbor resolution path into the player collision query. Keep any B-only block outside historical emulation scope.

### 1.8.9 → 1.9.4: sneaking and player collision dimensions

- A and B `Entity` initialize width/height as `0.6F`/`1.8F`; `Entity.setSize()` updates the box from those dimensions. The player constructors use `setSize(0.6F, 1.8F)`; `setSize(0.2F, 0.2F)` is used for sleeping and the wake path restores normal dimensions. No sneaking-conditioned size write was found in the inspected `Entity`, `LivingEntity`, or `PlayerEntity` methods.
- Both `Entity.move()` implementations have a separate sneaking support-edge probe. That predicate changes the requested horizontal displacement; it is not evidence that the player bounding box height changes. `PlayerEntity.getEyeHeight()` must be tracked independently because sneak state can affect eye-height consumers and collision/fluid probes.
- Disposition remains open until each owner records all player pose/dimension writers and resize timing, compares `Entity.move()` edge probing/axis and step paths, and checks the eye-height producer-to-consumer closure. Current evidence supports a narrow no-sneak-resize lead only; it does not prove overall sneaking collision parity.

## Required owner requeue requests

1. **All six reports:** replace broad stage-only closure with bounded member slices, exact A/B ranges and source/resource hashes, explicit method descriptors/callers, and the source-proven producer → consumer dependency chain. Keep any stage incomplete while a slice or dependency remains pending, in progress, blocked, or unreviewed.
2. **Every pair:** write the ordered local-player tick graph from input sampling through superclass tick, pre-travel field updates/cutoffs, jump dispatch, every travel branch, collision/move calls and post-travel writes. Include every reachable cancellation/reset/velocity writer and callback ordering; whole-branch textual equality does not establish tick parity.
3. **Every pair:** inventory all pose/dimension/eye-height state writers and when resize/query happens; all collision axis/step/support/grounding/restitution paths; block/fluid shape producers, subclasses/registrations, state and neighbor predicates; direct movement attributes/effects/equipment; and external player position/velocity influences. Use explicit checked absences with inheritance/caller/registration evidence.
4. **1.8.9 → 1.9.4:** include the `mobTick` cutoff as a high-priority bounded slice; verify its exact mapped-jar body and local-player reachability, then state the jump-apex consequence only to the level source proves.
5. **1.8.9 → 1.9.4:** close pane/bar geometry and the `isOpaque()` versus `defaultState().isCube()` neighbor predicate across historical registrations and every reachable neighbor class.
6. **1.8.9 → 1.9.4:** resolve sneaking edge probing separately from player bounding-box resizing and eye-height checks. Do not mark a generic “sneaking” stage complete.
7. **1.13.2 → 1.14.4:** hold all paired source comparisons pending until the exact 1.14.4 Feather readiness JSON and its source/artifact/diagnostic hashes validate.
8. **All pairs:** keep source discovery, implementation reconciliation, and runtime validation as separate statuses. The reviewer has not run the completion checker because the fresh reports are still changing; when frozen, run the specified static checker and then record the independent semantic disposition separately.

## Next checkpoint

Wait for each owner to freeze a source-only report and make its branch/commit available. Re-read the full report and exact source ranges for every claimed terminal row, independently trace any suspicious dependencies, and append exact missing rows with a pair-by-pair accept/partial disposition. Do not mark any pair accepted while its owner remains active or its scoped evidence closure is incomplete.
