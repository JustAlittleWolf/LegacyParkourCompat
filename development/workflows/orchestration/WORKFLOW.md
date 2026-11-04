# Coordinate movement work

Use this procedure when coordinating an authorized discovery, implementation or verification assignment. Keep results in `development/research/`; keep reusable instructions here.

## Establish the assignment

1. Reconcile the request with project scope and existing evidence. Reuse prior decisions and authorization; ask necessary unresolved questions together while continuing independent preparation.
2. State allowed phases, deliverable, resource budget, deadline and closing window in the coordinating chat. A workflow review does not authorize a movement campaign. Before dispatch, make each assignment executable from its message: state role and delegation rights, bounded scope, exact input identities and locations, expected output format/schema and destination, owned paths, isolation, review bounds, allowed checks, budget/deadline and stop condition. Workers should not need to infer a missing handoff contract.
3. Query the [backlog](#backlog-commands) for reusable evidence and open gates. Do not restart discovery merely because a chat is new.
4. For release planning, select the newest bugfix in each minor line from 1.8 through 1.20, relevant movement boundaries within 1.21, then the newest bugfix in each 26.x line through `gradle.properties`' exact target. Resolve exact releases and source readiness before dispatch. Refine toward all stable releases over time; endpoint pairs do not prove intermediate patch boundaries.

## Assign roles and capacity

The coordinator owns assignments, exclusive ownership, queue priorities, phase gates, reviews and handoffs. Delegate bounded investigations and fixes; spend coordinator time reconciling evidence and closing gates. Use native subagents, including direct workers and explicitly assigned suborchestrators. Give each suborchestrator explicit delegation rights and a maximum local worker count; workers must not delegate. Count direct workers and suborchestrators in the master's global pool; suborchestrators manage their assigned local pools. Account for descendants against host capacity and the overall budget. Separate user chats are not required.

Use the cheapest modern model suitable for the task; in Codex, follow the model policy below. Determine supported native concurrency with bounded probes and obey the user's worker target. A target is a ceiling to fill with useful, ready work, not a permanent default or a reason to invent assignments. Reserve capacity for independent review and integration. Assign one canonical source-preparation writer and one product integration/build owner; the integrator batches coherent ready changes while independent discovery continues.

Send assignments directly in chat with role/delegation limits, bounded action, exact input identities, branch or working directory, owned paths, isolation, expected output format/schema and location, review bounds, budget/deadline, authorized checks and completion/stop condition. Do not create assignment templates or per-worker status files.

Before dispatching a lane, read its procedure in full and supply only its permitted inputs:

- **Source or wiki discovery:** [shared discovery rules](../discovery/WORKFLOW.md) plus exactly one of [source](../discovery/source/WORKFLOW.md), [Minecraft Wiki](../discovery/minecraft-wiki/WORKFLOW.md), or [MCPK Wiki](../discovery/mcpk-wiki/WORKFLOW.md).
- **Independent implementation and product integration:** [implementation](../implementation/WORKFLOW.md).
- **Explicitly authorized runtime verification:** [testing](../testing/WORKFLOW.md).

Start blind workers/reviewers and independent implementers with fresh permitted context. Never fork mixed coordinator history into them. Start a fresh subagent for each new assignment; a compatible continuation may finish its existing assignment. Native subagents share the filesystem, so assign exclusive paths or isolated repositories/worktrees before allowing writes. Give source consumers read-only access to canonical `decompiled_minecraft/`; only its preparation owner writes there.

## Dispatch and completion loop

1. Keep a short, ordered ready queue in chat, containing only useful work with exact inputs, authorization, closed prerequisites, exclusive ownership and isolation. Recheck those gates at dispatch.
2. Dispatch the highest-priority ready assignment within the authorized target and budget. Refill useful capacity as slots open, while preserving review/integration capacity; do not create speculative work to hit a number. When accepted implementation and independent discovery are both ready, assign available capacity to both as possible. Integration batches coherent ready work and does not hold up independent discovery.
3. Wait for a completion or actionable event across active workers, not for the whole batch or one worker at a time. Avoid unchanged periodic polling and repeated status requests. If an event makes no new action possible, acknowledge it once and resume waiting.
4. Inspect the result, update ownership and central records, then refill useful free capacity without waiting for unrelated reviews or integration. An attention request neither releases a slot nor closes a gate. A subworker releases its local slot; its suborchestrator retains its global slot until its assignment and descendants finish.
5. Preserve separate finding acceptance, pair coverage/dependencies/audit, implementation and runtime states. Require complete tick/inventory coverage, closed dependencies and independent blind audit before pair freeze. Early implementation needs an independently accepted immutable finding with closed dependencies and proven applicability; keep its source pair blind until freeze.
6. Replenish the queue and resume event-driven waiting. If nothing is ready, record the specific blocker and wait for evidence that can resolve it. Apply this loop within every suborchestrator's assigned scope.

Measure progress by substantive accepted findings, closed dependencies, integrated deltas and resolved blockers. Review and commit counts describe activity, not throughput. For independent review, identify the immutable claim/evidence revision and the bounded acceptance criteria. Reuse a verdict while those inputs and dependencies remain unchanged; reopen review only for a changed claim, evidence, applicability boundary or dependency. If a reviewer handoff includes JSON, statically check it parses and matches its expected shape. If it includes hashes, verify them against exact payload bytes and the declared encoding/line-ending contract. Keep patch/finding payload distinct from wrapper metadata such as provenance or transport fields; do not add tooling for these checks. Do not repeatedly re-pin or republish unchanged evidence/verdicts.

Honor review/integration reservations and the closing window before each dispatch. At the closing window, stop assigning work, collect coherent commits/checkpoints and exact open blockers, and verify descendants have finished or stopped before responding. Forward pause/stop instructions to all active descendants; do not start replacement work or silently extend budgets.

For product handoff, require the integrator to merge the default branch, inspect semantic conflicts, report corrections and build with every Gradle `Test` task disabled plus `-x test`. Source-only workers must not merge product history into their isolated inputs. Compilation does not prove runtime parity.

## Host mechanisms

Use the host's supported native delegation, messaging and completion mechanisms. Report unavailable native delegation before substituting another mechanism.

**Codex desktop:** Use `spawn_agent` for authorized delegation and native agent messaging and completion notifications to coordinate it. For fresh permitted context, use `fork_turns: "none"` and supply only the assignment's allowed inputs. Refill capacity when any worker finishes or produces an actionable event; do not wait for the whole batch. After a wait, inspect the event and dispatch explicitly; waiting does not run the ready queue. Existing separate chats may finish their interrupted assignments, but new delegated assignments use native subagents. Other hosts use equivalent supported mechanisms and their permission rules.

### Codex-only model policy

- Default to the latest available GPT Luna with `high` reasoning.
- Use GPT-6.1 Sol (`gpt-6.1-sol`) with `low` reasoning only for very complex tasks; briefly state why.
- Never launch or switch delegated work to a model costlier than your own. Return work requiring a costlier model to an eligible coordinator.
- Check model availability and relative cost before dispatch. Report unavailable settings or unknown costs rather than silently substituting or exceeding the ceiling.

These choices apply to all Codex delegations and do not grant additional delegation rights.

## Central records and ownership

| Record | Writer | Retain |
| --- | --- | --- |
| `development/research/backlog.json` | Master, through the commands below | Finding revisions, independent verdicts/evidence, open applicability gates, code/commit associations, runtime evidence; pair coverage, dependencies, audits and next actions |
| `development/research/findings/<older>--<newer>/<topic>.md` | Assigned discovery writer | Permanent source difference in the discovery format; no progress status |
| `development/research/decisions.md` | Master | Scope exclusions and invalid witnesses needed to prevent unsupported work |
| `development/research/README.md` | Master | Record navigation and enduring limitations; no copied progress totals |
| Change source comments | Implementer/integrator | Implemented behavior and exact source references |

In coverage entries, name examined slices including no-difference checks, partial/unexamined areas, exact Git evidence and the next bounded action. Preserve historical audit verdicts bound to their inputs. Keep active ownership, allowed phase, pool limits, budgets and closing times only while relevant. Keep `wikiHints.minecraftWiki` and `wikiHints.mcpkWiki` separate, with checked pages, hints, source links, gaps and reconciliation state. Send blind source workers only their source coverage and exact inputs; never expose this mixed store to them.

Have suborchestrators and workers report bounded updates, reviewer verdicts and commits in chat. Send a final only when the assignment is complete, blocked, or needs a decision; it should state the result, evidence/output identity and remaining blocker, not repeat an unchanged status. Maintain no nested trackers, handoff reports or implementation status files. Preserve confirmed findings after integration. Remove completed temporary reports after transferring unique evidence and open gates; Git retains history. Keep necessary reference/comparison data in the authorized lab and only concise result associations centrally. Test execution reports failures only.

## Backlog commands

Run from the repository root with Python 3.10 or newer, using only the standard library. On Windows use `py -3`; on Linux/macOS use `python3`.

The master orchestrator is the sole writer. Workers send bounded updates in chat; they do not run write commands or create parallel trackers. Source workers must not query this mixed-lane store: the master supplies only their allowed source inputs and coverage. The tool is a convenience and consistency boundary, not an access-control system.

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
