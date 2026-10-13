# Coordinate movement work

Use this procedure when coordinating an authorized discovery, implementation or verification assignment. Keep results in `development/research/`; keep reusable instructions here.

## Establish the assignment

1. Reconcile the request with project scope and existing evidence. Reuse prior decisions and authorization; ask necessary unresolved questions together while continuing independent preparation.
2. State allowed phases, deliverable, resource budget, deadline and closing window in the coordinating chat. A workflow review does not authorize a movement campaign.
3. Query the [backlog](#backlog-commands) for reusable evidence and open gates. Do not restart discovery merely because a chat is new.
4. For release planning, select the newest bugfix in each minor line from 1.8 through 1.20, relevant movement boundaries within 1.21, then the newest bugfix in each 26.x line through `gradle.properties`' exact target. Resolve exact releases and source readiness before dispatch. Refine toward all stable releases over time; endpoint pairs do not prove intermediate patch boundaries.

## Assign roles and capacity

Create a few separate suborchestrator chats for non-overlapping workstreams. Give each explicit delegation rights, a maximum worker count and permitted hierarchy depth. Workers must not delegate. Count direct workers and suborchestrators in the master's global concurrency pool; exclude suborchestrators' subworkers, which use their separately assigned local pools. Apply overall time/resource budgets to all descendants and obey actual host limits.

Use economical reasoning models for bounded work and stronger models for unresolved complexity; in Codex, apply the specific model policy below. Determine supported concurrency with bounded probes; separate-chat capacity and per-chat subagent capacity are distinct. Reserve capacity for independent review and serial integration. Assign one canonical source-preparation writer and one product integration/build owner.

Send assignments directly in chat with role/delegation limits, bounded action, exact inputs, branch or working directory, owned paths, isolation, budget/deadline, authorized checks and expected finding or commit. Do not create assignment templates or per-worker status files.

Before dispatching a lane, read its procedure in full and supply only its permitted inputs:

- **Source or wiki discovery:** [shared discovery rules](../discovery/WORKFLOW.md) plus exactly one of [source](../discovery/source/WORKFLOW.md), [Minecraft Wiki](../discovery/minecraft-wiki/WORKFLOW.md), or [MCPK Wiki](../discovery/mcpk-wiki/WORKFLOW.md).
- **Independent implementation and product integration:** [implementation](../implementation/WORKFLOW.md).
- **Explicitly authorized runtime verification:** [testing](../testing/WORKFLOW.md).

Start blind workers/reviewers and independent implementers with fresh permitted context. Never fork mixed coordinator history into them. Reuse a chat only when its prior exposure is compatible with its next assignment. Give source consumers read-only access to canonical `decompiled_minecraft/`; only its preparation owner writes there.

## Dispatch and completion loop

1. While workers run, prepare a small ready queue in chat from the backlog. Check exact inputs, authorization, dependency gates, exclusive ownership and isolation. Recheck these at dispatch.
2. Dispatch the highest-priority ready assignment within capacity and budget. When discovery and implementation are both authorized, accepted implementation takes the next free slot; do not interrupt running work.
3. Wait for the first completion or actionable event across active workers, not for the whole batch or one worker at a time.
4. Inspect the result, update ownership and central records, then refill free capacity without waiting for unrelated reviews or integration. An attention request neither releases a slot nor closes a gate. A subworker releases its local slot; its suborchestrator retains its global slot until its assignment and descendants finish.
5. Preserve separate finding acceptance, pair coverage/dependencies/audit, implementation and runtime states. Require complete tick/inventory coverage, closed dependencies and independent blind audit before pair freeze. Early implementation needs an independently accepted immutable finding with closed dependencies and proven applicability; keep its source pair blind until freeze.
6. Replenish the queue and resume waiting. If nothing is ready, record the blocker and wait for evidence that can resolve it. Apply this loop within every suborchestrator's assigned scope.

Honor review/integration reservations and the closing window before each dispatch. At the closing window, stop assigning work, collect coherent commits/checkpoints and exact open blockers, and verify descendants have finished or stopped before responding. Forward pause/stop instructions to all active descendants; do not start replacement work or silently extend budgets.

For product handoff, require the integrator to merge the default branch, inspect semantic conflicts, report corrections and build with every Gradle `Test` task disabled plus `-x test`. Source-only workers must not merge product history into their isolated inputs. Compilation does not prove runtime parity.

## Host mechanisms

Use the host's supported conversation, delegation, messaging and completion mechanisms. Report missing separate-chat support before changing the hierarchy.

**Codex desktop:** Use `create_thread` for separate suborchestrator chats, `send_message_to_thread` for assignments/follow-ups, and bounded multi-target `wait_threads` calls with returned cursors for first-completion waits. Explicit user authorization is required for creating or messaging chats; reuse it within scope. `spawn_agent` creates a subagent, not a separate chat. After a wait, inspect the event and dispatch explicitly; waiting does not run the ready queue. Other hosts use equivalent supported mechanisms and their permission rules.

### Codex-only model policy

- For most delegated work, including routine coordination, discovery, implementation and review, explicitly select the latest GPT Luna model available on the execution host with reasoning effort `high`. Resolve the current Luna identifier at dispatch; do not pin an older Luna release or silently inherit the caller's model.
- Only very complex tasks may use GPT-6.1 Sol (`gpt-6.1-sol`) with reasoning effort `low`. Examples include unresolved interacting version boundaries or difficult cross-component reasoning that cannot reasonably be reduced to bounded Luna assignments. State the concrete complexity reason in the assignment; a task being important or a review is not sufficient.
- An agent must never launch or switch delegated work to a model more costly than its own current model. Apply this ceiling to direct workers, suborchestrators, subagents, new chats and follow-up model overrides. Check the host's current model cost ranking or authoritative pricing; lower reasoning effort alone does not establish a cheaper model. Do not infer cost from release numbers. If the caller's identity or relative cost is unknown, resolve it before dispatch or report the limitation; do not assume permission.
- A Luna agent must not launch Sol to handle complexity. Return the bounded escalation and its reason to its existing coordinator through an authorized channel; an eligible coordinator may assign Sol directly within its own cost ceiling. Do not route around the ceiling by having a cheaper agent create or upgrade a more expensive coordinator.
- Pass the selected model, reasoning effort and cost ceiling with each delegation. Use fresh permitted context when the host requires it for explicit model selection, and always for blind work. If a required model or reasoning level is unavailable, report the limitation and seek a revised assignment instead of silently substituting another model or effort. These model choices do not grant delegation, chat-creation or messaging rights.

This policy is specific to Codex. Other hosts retain the capability/cost-based guidance above.

## Central records and ownership

| Record | Writer | Retain |
| --- | --- | --- |
| `development/research/backlog.json` | Master, through the commands below | Finding revisions, independent verdicts/evidence, open applicability gates, code/commit associations, runtime evidence; pair coverage, dependencies, audits and next actions |
| `development/research/findings/<older>--<newer>/<topic>.md` | Assigned discovery writer | Permanent source difference in the discovery format; no progress status |
| `development/research/decisions.md` | Master | Scope exclusions and invalid witnesses needed to prevent unsupported work |
| `development/research/README.md` | Master | Record navigation and enduring limitations; no copied progress totals |
| Change source comments | Implementer/integrator | Implemented behavior and exact source references |

In coverage entries, name examined slices including no-difference checks, partial/unexamined areas, exact Git evidence and the next bounded action. Preserve historical audit verdicts bound to their inputs. Keep active ownership, allowed phase, pool limits, budgets and closing times only while relevant. Keep `wikiHints.minecraftWiki` and `wikiHints.mcpkWiki` separate, with checked pages, hints, source links, gaps and reconciliation state. Send blind source workers only their source coverage and exact inputs; never expose this mixed store to them.

Have suborchestrators and workers report bounded updates, reviewer verdicts and commits in chat. Maintain no nested trackers, handoff reports or implementation status files. Preserve confirmed findings after integration. Remove completed temporary reports after transferring unique evidence and open gates; Git retains history. Keep necessary reference/comparison data in the authorized lab and only concise result associations centrally. Test execution reports failures only.

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

Read the entry and use the returned revision as `--expect`. Put only the changed fields in a small UTF-8 JSON merge-patch file outside the tracked research tree. For example, a coverage update file might contain:

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

Writes acquire an exclusive lock, reject stale entry revisions, validate the complete proposed store and atomically replace the storage file. Updates to unrelated entries do not invalidate a previously read entry revision; they are preserved. If a crashed writer leaves `backlog.json.lock`, verify that its recorded process has stopped before removing the lock. Do not bypass an active writer.

`development/research/backlog.json` remains machine-managed, Git-readable storage. Agents do not need to load or hand-edit that large file; ordinary reads and edits use this interface. Existing records and evidence are preserved without a data migration. The serializer keeps one-record diffs for compact sections and readable coverage records. Commit the resulting focused storage diff normally. JSON patches are transient command input, not new reports or trackers.

### Source-set relocation

After moving Java files, use `relocate-code --file <mapping.json>` to preview an exact old-path to new-path object. It relocates only current finding `changes` and implementation-gap `change` references, preserving historical evidence identities and all statuses. Apply with `--expect <beforeRevision> --apply` from that preview. This operation alone can read the intermediate store with missing old code paths; it validates every destination and the complete resulting store before atomically writing under the same lock. It does not infer acceptance or close gaps. `add metadata <key> --file <json-string>` can preserve a new immutable evidence identity; duplicate keys are rejected. Long metadata values are abbreviated in `summary`; use `show metadata <key>` for exact contents.
