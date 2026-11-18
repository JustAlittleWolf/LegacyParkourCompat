# Coordinate movement work

Use this procedure when coordinating an authorized discovery, implementation or verification assignment. Keep results in `development/research/`; keep reusable instructions here.

## Establish the assignment

1. Reconcile the request with project scope and existing evidence. Reuse prior decisions and authorization; ask necessary unresolved questions together while continuing independent preparation.
2. State allowed phases, deliverable, resource budget, stop condition and any user-specified deadline or closing window in the coordinating chat. A workflow review does not authorize a movement campaign. Each dispatch message must make its assignment executable: role/delegation rights, bounded scope, exact inputs, working location and isolation, owned paths, output and destination, review/check limits, budget and stop condition.
3. Query the [backlog](#backlog-commands) for reusable evidence and open gates. Do not restart discovery merely because a chat is new.
4. For release planning, select the newest bugfix in each minor line from 1.8 through 1.20, relevant movement boundaries within 1.21, then the newest bugfix in each 26.x line through `gradle.properties`' exact target. Resolve exact releases and source readiness before dispatch. Refine toward all stable releases over time; endpoint pairs do not prove intermediate patch boundaries.

## Assign roles and capacity

In a multi-orchestrator run the coordinator allocates domains, appoints the sole technical publisher, manages urgent organizational issues and propagates stop. Each domain orchestrator chooses and dispatches the next tasks and sequences acceptance and integration using worker results. Technical workers perform discovery, independent audits, edits, integration checks, builds and Git operations. Delegate bounded investigations and fixes; spend coordinator time reconciling evidence and closing gates. Use native subagents, including direct workers and explicitly assigned suborchestrators. Give each suborchestrator explicit delegation rights and a maximum local worker count; workers must not delegate. Count direct workers and suborchestrators in the master's global pool; suborchestrators manage their assigned local pools. Account for descendants against host capacity and the overall budget. Separate user chats are not required.

Use the cheapest modern model suitable for the task; in Codex, follow the model policy below. Determine supported native concurrency with bounded probes and obey the user's worker target. A target is a ceiling to fill with useful, ready work, not a permanent default or a reason to invent assignments. Reserve capacity for independent source audits and integration. Assign one canonical source-preparation writer and one product integration/build owner; the integrator batches coherent ready changes within the current phase.

Before dispatching a lane, read its procedure in full and supply only its permitted inputs:

- **Source or wiki discovery:** [shared discovery rules](../discovery/WORKFLOW.md) plus exactly one of [source](../discovery/source/WORKFLOW.md), [Minecraft Wiki](../discovery/minecraft-wiki/WORKFLOW.md), or [MCPK Wiki](../discovery/mcpk-wiki/WORKFLOW.md).
- **Independent implementation and product integration:** [implementation](../implementation/WORKFLOW.md).
- **Explicitly authorized runtime verification:** [testing](../testing/WORKFLOW.md).

Start blind source auditors and independent implementers with fresh permitted context. Never fork mixed coordinator history into them. Give each worker a bounded queue of compatible items sharing a lane, release pair and evidence base. The worker can finish them in one context. Start a fresh context when independence, lane, input permissions or product isolation changes. Native subagents share the filesystem, so assign exclusive paths or isolated repositories/worktrees before allowing writes. Give source consumers read-only access to canonical `decompiled_minecraft/`; only its preparation owner writes there.

## Optimize for finished outcomes

### Finish known implementations before new discovery

Apply this order across all domains. Start with known MCPK claims. Read the existing MCPK inventory, current scope decisions and linked findings once. Prioritize outstanding in-scope movement behavior from these claims, then the rest of the known implementation backlog. A page-attribution gap alone can stay in the wiki inventory. Reuse accepted source evidence and checked effective providers.

Enumerate both `open` and `partial` findings. For each remaining in-scope operation, dispatch the accepted candidate or the smallest missing prerequisite for that specific operation (for example an independent verdict, a caller, or an exact patch boundary). Several teams may address different known operations at once. Bundle compatible work by shared hook and effective version range; give each worker the complete evidence references once. Use the existing backlog and publication refs. Count newly covered operations on main, partials advanced, and remaining blockers at each checkpoint. Track built changes awaiting publication separately.

Open fresh discovery only after the publisher and domain owners have accounted for the entire known queue: each eligible implementation has landed on main or has an evidence-backed existing-code disposition. Record exclusions at their actual scope. A blocked in-scope item, a candidate awaiting integration, or an exhausted local queue keeps the global gate closed. Reassign idle capacity to remaining known work or its prerequisites. When the gate opens, take one bounded MCPK page/section batch first, beginning with unchecked Version_Differences clauses. Each newly confirmed in-scope difference returns to the implementation queue before the next discovery batch. Give blind source workers only the permitted source projection.

### Reserve an end-to-end block

Grant each orchestrator a standing domain broad enough to supply independent contracts across many releases and operations. Record the owner, behavioral boundary, branch and closure criteria once. The grant covers operation-specific wiring in shared files on the owner's branch. Reading a common dependency creates no write conflict. Only a genuine overlap on the same behavior or an incompatible shared interface change requires coordination. Keep existing user edits outside the assignment.

The orchestrator chooses what runs next, assigns exclusive worker outputs, and keeps a ready queue. Give each worker a role, exact inputs and lane restrictions, owned work location, authorized checks, required evidence or commit, acceptance criterion and terminal condition. Workers resolve technical questions independently and return one success or failure to their own orchestrator. Interrupt that orchestrator only for an urgent organizational blocker. Preserve separate fresh contexts for blind source work, independent acceptance and sanitized candidate implementation.

The high-level coordinator remains dormant while orchestrators work. Wake on a publication-ready success, terminal failure, urgent organizational conflict or user stop. Use an event wait on the orchestrator chats. Worker rosters, technical choices, per-member grants, implementation reports and progress polling stay out of the coordinator's loop. Each orchestrator commits coherent batches locally, handles its own workers and selects its next in-domain task without a new grant. One technical publication worker serializes verified terminal batches and backlog associations. It handles routine intake directly from the orchestrators' integration repositories. The coordinator can reassign an unstarted slice when an orchestrator exhausts its ready queue.

- Within the implementation queue, prioritize publishing ready changes, building accepted candidates, and closing named evidence gates. Give each owner the callers, affected releases and exact remaining decision up front.
- One source worker can trace several related operations, record differences separately and return one coverage delta. One independent reviewer can check a compatible set against the exact sources and return individual verdicts. A failed claim leaves its siblings eligible. Forward an immutable ready subset immediately if waiting for a slower sibling would idle the next stage. This handoff requires no progress conversation.
- At each product or evidence checkpoint, count newly accepted operations, closed dependencies including substantiated no-difference coverage, and integrated operations separately. Compare the ready queues before dispatching more producers. If reviewed findings accumulate, move slots to candidates or integration preparation. If drafts accumulate, move slots to blind review. If successive checkpoints produce only partial reports for the same gate, give one owner the end-to-end dependency or park it with the exact missing input and pull a different closure target.
- The master or its single recordkeeper batches unrelated terminal messages for central recording and publishes completed findings with their backlog associations. Keep transport transcripts, generated TSV inventories and repeated copies of evidence in scratch. Retain unique evidence needed to reproduce a verdict once and reference its immutable identity. Verify that accepted evidence stays recoverable before discarding scratch.

## Git ownership and recovery

Use one writer per checkout and one publisher for the primary checkout and its index. The integrator owns the product branch and build. Where a recordkeeper prepares backlog updates, serialize its publication with product merges through the primary publisher. Workers in isolated repositories have their own indices. Blind lanes use source-only directories and independent candidates use the sanitized implementation export.

For concurrent integration clones, use Git refs as an intake queue. Register one local integration repository per orchestrator with the publisher. The integration worker commits a verified, product-history-based batch with only its intended ancestry, then creates a fresh ref in its own clone with `git update-ref refs/campaign-ready/<batch> <commit> ""` (empty expected old value means create only). Use one new name per batch. Publish the ref only after its evidence and build disposition are ready. Keep isolated source/candidate history out of these refs. An older unmerged batch can be withdrawn at its exact old SHA if a verified successor replaces it.

At intake, run `python development/workflows/orchestration/ready_batches.py --primary <primary-checkout> --source A <clone-A> --source B <clone-B> --source C <clone-C>`. The command reads local Git metadata and lists pending refs. Empty intake needs no chat message. The publisher checks at bounded intervals or when a ready batch arrives. It pins the reported SHA, fetches the exact ready ref and checks the fetched identity before integrating. Serially combine on an isolated integration checkout based on current main. Reuse the already verified build for an identical product tree; run the project-required build on a newly combined code tree. Fast-forward the primary branch only after checking the expected base and existing user edits. Preserve batch ancestry so Git can recognize publication on the next scan. For conflicting behavior, return the exact base and overlap to a technical integration worker, leave that batch pending, and continue other batches. Use the existing backlog tool for durable evidence associations, including after interrupted publication. Central Git intake and writing belong to the technical publisher. The coordinator receives only terminal results or an organizational decision it must make.

Commit each coherent evidence or product batch as soon as it meets its checks. Stage explicit owned paths, inspect the staged scope, and verify the resulting commit and paths with `git show --stat HEAD`. During continuing work, leads check at least every 30 minutes for finished unpublished results. Publish eligible subsets even while sibling jobs run. Keep unfinished evidence marked partial. When a domain runs out of eligible work, transfer capacity under the global implementation-first gate.

On Git failure capture the command, error, repository root, branch, status and existing worktrees. For a branch already checked out or a path collision, use an owned checkout or a fresh path and branch from the intended commit. For product work a separate local clone from that commit is also suitable where permitted. For a busy index, route publication to its owner. Preserve unrelated dirty paths and active locks. For denied writes use the host's approval mechanism. If approval is unavailable, preserve completed files or patches in authorized storage with base and paths, mark publication pending and continue independent work. Verify the first real commit early. A patch checkpoint is recoverable work and must be reported separately from a Git commit.

## Dispatch and completion loop

1. Build the first assignments from current backlog entries and the latest referenced checkpoint. Generated progress snapshots can lag the store. Prefer work that closes a named dependency, proves a missing patch boundary, or gets an accepted change into the product. Group adjacent source members with shared callers, evidence and ownership into one worker assignment. Give each write destination a single owner across all active coordinators. Keep a short, ordered ready queue in chat, containing only useful work with exact inputs, authorization, closed prerequisites, exclusive ownership and isolation. Recheck those gates at dispatch.
2. Dispatch the highest-priority ready assignment within the authorized target and budget. Refill useful capacity as slots open, while preserving source-audit/integration capacity; do not create speculative work to hit a number. Refill from the current global phase. A verified batch awaiting main publication remains implementation work.
3. Make each assignment self-contained and executable from its stated inputs, scope, permissions, owned paths, output and stop condition. Workers complete all authorized work autonomously and notify their own orchestrator during the assignment only about an urgent organizational blocker they cannot resolve. Resolve technical questions within the stated scope using project evidence and judgment. End with one terminal result, `COMPLETED` or `FAILED`, identifying the output and any remaining blocker.
4. Wait for a terminal result or organizational blocker across active workers, not for the whole batch or one worker at a time. Avoid unchanged periodic polling and repeated status requests. Refill a released slot from the ready queue without rechecking completed technical work; preserve explicit independent acceptance and audit gates for work that still depends on them. An attention request neither releases a slot nor closes a gate. A subworker releases its local slot; its suborchestrator retains its global slot until its assignment and descendants finish.
5. Preserve separate finding acceptance, pair coverage/dependencies/audit, implementation and runtime states. Require complete tick/inventory coverage, closed dependencies and independent blind audit before pair freeze. Early implementation needs an independently accepted immutable finding with closed dependencies and proven applicability; keep its source pair blind until freeze.
6. Replenish the queue and resume event-driven waiting. If nothing is ready, record the specific blocker and wait for evidence that can resolve it. Apply this loop within every suborchestrator's assigned scope.

The master assigns one exclusive central writer for multi-orchestrator work; the technical publisher can hold that role. In a multi-orchestrator run each orchestrator consolidates its workers' results and submits publication-ready batches to the central writer. In a single-level assignment workers return results to their assigning orchestrator.

Measure progress by substantive accepted findings, closed dependencies, integrated deltas and resolved blockers. Audit and commit counts describe activity, not throughput. For each independent source audit, identify the immutable claim/evidence revision and bounded acceptance criteria. Reuse its verdict while those inputs and dependencies remain unchanged; reopen the audit only for a changed claim, evidence, applicability boundary or dependency. If a source-audit handoff includes JSON, statically check it parses and matches its expected shape. If it includes hashes, verify them against exact payload bytes and the declared encoding/line-ending contract. Keep finding payload distinct from wrapper metadata such as provenance or transport fields; do not add tooling for these checks. Do not repeatedly republish unchanged evidence or verdicts.

Honor source-audit/integration reservations and any assigned closing window before each dispatch. For a user-stop run continue refilling across checkpoints until the user stops it, within host limits. At the closing window, stop assigning work, collect coherent commits/checkpoints and exact open blockers, and verify descendants have finished or stopped before responding. Forward pause/stop instructions to all active descendants; do not start replacement work or silently extend budgets.

For product handoff, require the integrator to create an integration branch from the current `main` and consolidate accepted changes into the established product structure. If `main` advances, merge it into the integration branch and inspect semantic conflicts. Honor explicit user check constraints; build with every Gradle `Test` task disabled plus `-x test`, unless the user explicitly defers the build. After consolidation and authorized checks, merge the integration branch into `main` and report the resulting commit. Source-only workers must not merge product history into their isolated inputs. Do not push unless the user requests it. Compilation does not prove runtime parity.

## Host mechanisms

For a persistent campaign, put the role, stop condition and path to the authoritative launch brief in each native goal when creating it. Include any mandatory worker model or cost limit in the objective. For orchestrator goals include exclusive block ownership and refill on the first worker completion. For coordinator goals include reservations, publication and stop propagation. When requirements change during an active goal, update the authoritative brief and give every affected chat its path and revision. After a restart or context loss, reload the current brief from the primary checkout before dispatch, including when a worker checkout is pinned to an older base.

Use the host's supported native delegation, messaging and completion mechanisms. Report unavailable native delegation before substituting another mechanism.

**Codex desktop:** Use `spawn_agent` for authorized delegation and native agent messaging and completion notifications to coordinate it. For fresh permitted context, use `fork_turns: "none"` and supply only the assignment's allowed inputs. Refill capacity when any worker finishes or produces an actionable event; do not wait for the whole batch. After a wait, inspect the event and dispatch explicitly; waiting does not run the ready queue. Existing separate chats may finish their interrupted assignments, but new delegated assignments use native subagents. Other hosts use equivalent supported mechanisms and their permission rules.

### Codex-only model policy

- Use GPT-6.2 Sol with medium or high reasoning for the master. Use GPT-6.2 Sol with medium reasoning for suborchestration, complex dependency reviews and product integration.
- Assign most work to GPT-6 Luna with high reasoning, including bounded source comparisons, wiki inventories, routine independent checks and narrow implementation candidates.
- Check the native model selector and host capacity at launch. If Luna is unavailable, report that once and use any fallback explicitly authorized for the run. Keep the user's cost ceiling across descendants. Use verified host capacity to budget all descendants sharing a pool.

These choices apply to all Codex delegations and do not grant additional delegation rights.

## Central records and ownership

| Record | Writer | Retain |
| --- | --- | --- |
| `development/research/backlog.json` | Master, or one explicitly assigned recordkeeper acting on the master's behalf, through the commands below | Finding revisions, independent verdicts/evidence, open applicability gates, code/commit associations, runtime evidence; pair coverage, dependencies, audits and next actions |
| `development/research/findings/<older>--<newer>/<topic>.md` | Assigned discovery writer | Permanent source difference in the discovery format; no progress status |
| `development/research/decisions.md` | Master | Scope exclusions and invalid witnesses needed to prevent unsupported work |
| `development/research/README.md` | Master | Record navigation and enduring limitations; no copied progress totals |
| Change source comments | Implementer/integrator | Implemented behavior and exact source references |

In coverage entries, name examined slices including no-difference checks, partial/unexamined areas, exact Git evidence and the next bounded action. Preserve historical audit verdicts bound to their inputs. Keep active ownership, allowed phase, pool limits, budgets and closing times only while relevant. Keep `wikiHints.minecraftWiki` and `wikiHints.mcpkWiki` separate, with checked pages, hints, source links, gaps and reconciliation state. Send blind source workers only their source coverage and exact inputs; never expose this mixed store to them.

Use chat for actionable changes, verdicts and commits; routine coordination and validation need no narration. At completion, report the result, evidence/output identity and any remaining blocker. Maintain no nested trackers, handoff reports or implementation status files. Preserve confirmed findings after integration. Remove completed temporary reports after transferring unique evidence and open gates; Git retains history. Keep necessary reference/comparison data in the authorized lab and only concise result associations centrally. Test execution reports failures only.

## Backlog commands

Run from the repository root with Python 3.10 or newer, using only the standard library. On Windows use `py -3` or an installed `python` executable (3.10+); on Linux/macOS use `python3`.

The master orchestrator owns the central record and appoints one exclusive recordkeeper or technical publisher in a multi-orchestrator run; that delegate uses the commands below until the assignment ends. Other workers send terminal outcomes to the recordkeeper and do not run write commands or create parallel trackers. Source workers must not query this mixed-lane store: the master supplies only their allowed source inputs and coverage. The tool is a convenience and consistency boundary, not an access-control system.

### Read only what is needed

```powershell
py -3 development/workflows/orchestration/backlog.py summary
py -3 development/workflows/orchestration/backlog.py list findings --status open --search jump --limit 10
py -3 development/workflows/orchestration/backlog.py list coverage --limit 10 --offset 0
py -3 development/workflows/orchestration/backlog.py show coverage 1.16.5--1.17.1 --field nextAction
py -3 development/workflows/orchestration/backlog.py validate
```

`list` returns compact summaries, exact IDs, entry revisions and a `nextOffset` for pagination. Search covers the entry's fields, not just its name. `--status` applies to findings only. `show` returns one complete entry, or one top-level field with `--field`; its revision always identifies the full entry. Validation checks structural consistency, duplicate keys/identities, finding names and current finding/Change paths. Historical evidence paths are Git identities and need not exist in the current checkout. Validation never proves source acceptance or runtime parity.

| Section | Entry ID |
| --- | --- |
| `findings` | Full repository-relative finding path |
| `coverage` | Exact `<older>--<newer>` pair |
| `unresolvedClaims` | `<pair>::<claim>` |
| `implementationEvidenceGaps` | Full repository-relative Change path |
| `excludedOperations` | Operation name |
| `metadata` | Top-level metadata key, such as `runtime` |

### Preview and apply a targeted update

Read the entry and use the returned revision as `--expect`. Coalesce routine changes for the same entry into one small UTF-8 JSON merge-patch file outside the tracked research tree; preserve every unchanged evidence/verdict field. Keep this patch payload separate from command arguments and wrapper/provenance metadata. Parse and inspect the patch shape before preview. For example, a coverage update file might contain:

```json
{
  "nextAction": "Inspect the remaining exact-version collision-provider dependency."
}
```

```powershell
py -3 development/workflows/orchestration/backlog.py show coverage 1.16.5--1.17.1
py -3 development/workflows/orchestration/backlog.py update coverage 1.16.5--1.17.1 --expect <returned-revision> --file C:\temp\coverage-patch.json
py -3 development/workflows/orchestration/backlog.py update coverage 1.16.5--1.17.1 --expect <returned-revision> --file C:\temp\coverage-patch.json --apply
```

Without `--apply`, mutation commands validate and preview only. `--file -` reads the patch from standard input. Object fields merge recursively; omitted fields stay unchanged, `null` removes a field, and arrays replace the whole array. Read an array before replacing it so existing evidence is preserved. Metadata updates take a JSON string. Entry identities cannot change in an update. Do not copy the example action into real coverage unless it describes the actual remaining work.

`add <section> <id> --file <record.json>` previews a complete new entry and rejects duplicate IDs. `delete <section> <id> --expect <revision>` previews deletion of a resolved `unresolvedClaims` or `implementationEvidenceGaps` entry. Both need `--apply` to write. Preserve unique evidence before deleting a resolved temporary entry. Permanent findings, coverage and scope dispositions cannot be deleted through this tool.

Writes acquire an exclusive lock, reject stale entry revisions, validate the complete proposed store and atomically replace the storage file using UTF-8 and LF line endings. Updates to unrelated entries do not invalidate a previously read entry revision; they are preserved. Batch same-entry field changes into one update, but apply separate entries with their own current revisions. If a crashed writer leaves `backlog.json.lock`, verify that its recorded process has stopped before removing the lock. Do not bypass an active writer.

`development/research/backlog.json` remains machine-managed, Git-readable storage. Agents do not need to load or hand-edit that large file; ordinary reads and edits use this interface. Existing records and evidence are preserved without a data migration. The serializer keeps one-record diffs for compact sections and readable coverage records. Commit the resulting focused storage diff normally. JSON patches are transient command input, not new reports or trackers.

### Source-set relocation

After moving Java files, use `relocate-code --file <mapping.json>` to preview an exact old-path to new-path object. It relocates only current finding `changes` and implementation-gap `change` references, preserving historical evidence identities and all statuses. Apply with `--expect <beforeRevision> --apply` from that preview. This operation alone can read the intermediate store with missing old code paths; it validates every destination and the complete resulting store before atomically writing under the same lock. It does not infer acceptance or close gaps. `add metadata <key> --file <json-string>` can preserve a new immutable evidence identity; duplicate keys are rejected. Long metadata values are abbreviated in `summary`; use `show metadata <key>` for exact contents.
