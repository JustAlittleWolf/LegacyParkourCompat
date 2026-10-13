# Coordinate authorized movement work

Coordinate only the phases and checks authorized for the assignment. Keep current scope and progress in [research](../../research/README.md); this directory holds reusable instructions only.

Before dispatch, reconcile the current request with project scope and existing evidence. Identify missing inputs, conflicting requirements and decisions that would change the work; ask necessary questions together, while continuing independent preparation. Reuse decisions and authorization already supplied. State the allowed phases, intended deliverable, resource limits and any unresolved blockers in the coordinating chat, then coordinate autonomously within that scope. Reviewing or correcting workflows does not itself authorize a new discovery or implementation campaign.

## Authority, roles and capacity

The master creates a few separate suborchestrator chats for non-overlapping workstreams, rather than substituting subagents within the master's chat. Suborchestrators may use subagents within explicitly granted delegation limits. Workers do not delegate by default. Grant each suborchestrator explicit delegation rights with a maximum number of additional agents and permitted hierarchy levels; its own slot also counts toward capacity. Do not make one chat per tiny slice. State each role's writable paths, allowed inputs, integration and central-record ownership, global resource budget and absolute end time. Child budgets and concurrent work count against the same global limit.

This hierarchy is platform-neutral. Use the host's supported mechanisms for separate conversations, delegated workers, messaging and completion waits; tool names and model availability are platform details. If the host cannot provide separate chats, report that limitation before changing the hierarchy.

**Codex desktop only:** Where available, use `create_thread` for separate suborchestrator chats, `send_message_to_thread` for authorized assignments and follow-ups, and `wait_threads` to wait for completion before continuing dependent work. `spawn_agent` creates a subagent within the current chat and does not replace a separate suborchestrator chat. Codex requires explicit user authorization to create new chats or message another chat; this repository procedure alone does not supply it. Once authorized, reuse that authorization within its scope. Other hosts should use their own equivalent capabilities and permission rules.

Reserve a closing window before the deadline to stop dispatch, finish bounded current steps and collect commits/checkpoints. Forward pause/stop instructions to every active descendant; do not launch replacement work while paused. Verify that active workers have stopped or finished before the closing response, and report exact open work. Do not silently extend the global budget or end time.

Use an economical reasoning model for bounded work and a stronger model for unresolved complexity; do not hard-code model names. Determine actual concurrency with bounded capacity probes. Desktop chat capacity and per-chat subagent slots are distinct; neither proves unlimited global parallelism. Reserve resources for independent reviews and serial integration. Maintain one exclusive source-preparation owner and one product integration/build owner.

Source preparation writes only canonical `decompiled_minecraft/` in the main repository; all consumers are read-only. Source workers/reviewers receive source-only inputs and do not inspect implementation or either wiki lane before full-pair freeze. Wiki lanes remain separate and provide hints plus located source evidence for reconciliation after freeze. Independent implementation uses the clean infrastructure export described in [implementation](../implementation/README.md), with no old implementation/history. The product integrator alone reconciles that output with existing implementations.

## Release planning

Start with the newest bugfix release in each minor line from 1.8 through 1.20, multiple movement-relevant boundaries within 1.21, then the newest bugfix release in each 26.1/26.2/… line through the exact modern target configured in `gradle.properties`. Resolve the exact release list and source readiness before dispatch. Long-term coverage targets all stable releases, refining broad comparisons with intermediate exact versions. Existing coarse endpoint pairs neither prove patch boundaries nor complete a release line.

## Dispatch and handoff

1. Use the [backlog commands](#backlog-commands) to inspect progress, accepted immutable evidence and unresolved queues. Reuse valid work; do not repeat discovery just because a chat is new. Assign exclusive finding/code writers and independent reviewers.
2. Dispatch ready bounded work with complete inputs. Discovery assignments name exactly one of the [source](../discovery/source/README.md), [Minecraft Wiki](../discovery/minecraft-wiki/README.md) or [MCPK Wiki](../discovery/mcpk-wiki/README.md) procedures plus the shared discovery rules. When discovery and implementation are jointly authorized, ready accepted implementation takes priority for the next free capacity; do not interrupt running assignments. Close dependencies and reviews before opening more slices. A pending gate or real capacity limit is a recorded blocker, not a reason to duplicate assignments.
3. Track pair coverage/dependency/audit separately from per-finding source acceptance, product integration and runtime parity. Never equate a worker handoff, source-confirmed declaration, catalog entry or build with complete discovery or runtime validation.
4. Before source-pair freeze, require full tick/inventory coverage, closed dependencies and independent blind coverage audit. Early implementation can consume only an independently accepted immutable finding with closed dependencies and established applicability; its pair remains open and blind.
5. Require coherent commits and checkpoints from writing workers. The product integrator merges the default branch before handoff, inspects semantic conflicts, compiles with all Gradle `Test` tasks disabled plus `-x test`, and records corrections. Source-only lanes must not merge product changes that reveal excluded material.
6. Incorporate verified worker results with the backlog procedure below, validate the updated record and inspect its Git diff. Return completed work, immutable evidence, exact open blockers and next actions within the global budget/end time. Runtime work follows [testing](../testing/README.md) only when explicitly authorized.

### Ready queue and completion loop

While workers run, prepare a small queue of the next bounded assignments in the coordinating chat, derived from the central backlog rather than a new tracker. Check exact inputs, dependency gates, authorization, exclusive path ownership and required isolation before marking an assignment ready. Keep enough ready work to refill available capacity promptly; dependent work stays blocked until its required evidence is accepted. Recheck readiness at dispatch because ownership, priorities and evidence can change.

Use the host's first-completion or actionable-event wait across active workers, so one worker finishing allows coordination to resume while the others continue. Do not wait for an entire batch or wait on workers one by one. On each event, inspect the result, confirm which worker and descendant capacity is actually free, update ownership and relevant central state, then dispatch the highest-priority ready assignment within the remaining budget. A request for attention does not by itself release capacity or satisfy an acceptance gate. Refill independent work without waiting for unrelated reviews or integration; dependent work still requires those gates to close. Replenish the ready queue and resume waiting.

Reuse an idle chat when its role and prior context are compatible with the assignment. Create a separate chat when isolation or review independence requires it; never reuse implementation- or wiki-exposed context for blind source work. Honor review/integration reservations, delegation limits, pauses and the closing window before every dispatch. If nothing is ready, record the blocker and wait for an event that can resolve it instead of inventing work to fill slots. Apply the same loop within each suborchestrator's authorized scope.

**Codex desktop only:** `wait_threads` wakes on the first watched chat that completes or needs attention; retain its returned cursors for subsequent waits. Use a bounded multi-target wait within the tool's supported capacity. The master must process the event and explicitly dispatch the prepared assignment: the wait tool does not launch queued work automatically.

## Central records and ownership

The master orchestrator is the only backlog writer and uses the [backlog procedure below](#backlog-commands). `development/research/backlog.json` is machine-managed storage; do not read or hand-edit the whole file for routine work. This existing file is the single concise global progress list: finding paths, independent acceptance and unresolved evidence/applicability gates, implementation links and integrated commit references, plus pair coverage, open dependencies, audit and next action. Each coverage entry names examined slices (including checked areas without a finding), partial/unexamined areas, exact Git evidence references and the concrete next bounded source action. Historical coverage/audit decisions stay bound to their inputs; generic partial flags must not erase a valid historical audit or imply that unexamined areas were checked. Keep these distinct from runtime parity. Within its coverage entries, record active ownership, allowed phase, global capacity/budget, deadline and closing window only while relevant. Suborchestrators send bounded updates in chat for the master to incorporate; they do not create nested trackers. Use paginated tool queries and single-entry reads, then preview targeted updates against the returned revision before applying them. Keep the wiki hint inventories separate within each coverage entry under `wikiHints.minecraftWiki` and `wikiHints.mcpkWiki`; each inventory retains checked pages, bounded hints, source-verification links, open gaps and reconciliation state. Do not send these fields or implementation fields to blind source workers; prepare only their permitted source coverage and exact inputs.

`development/research/findings/<older>--<newer>/<topic>.md` contains permanent source differences in the [discovery format](../discovery/README.md). The assigned discovery writer owns each finding; an independent reviewer supplies its verdict in chat for the central record. Do not duplicate progress or implementation status in findings. Preserve confirmed findings after implementation.

`development/research/decisions.md` holds scope exclusions and invalid witnesses still needed to prevent repeated unsupported work; the master maintains it. `development/research/README.md` explains these records and enduring limitations, without copying individual progress entries. Change comments retain implemented behavior and exact source references; implementers own their narrow patches.

Assignments are direct chat messages stating role and delegation limits, bounded action, exact inputs, branch/worktree and owned paths, isolation, shared budget/deadline, authorized checks and expected patch or finding. Implementers return commit references in chat, without implementation, handoff, status or completion report files. No assignment template or additional tracker hierarchy is needed. Keep necessary reference recordings and comparison data in the authorized lab. Test execution reports failures; only a concise final finding/commit/comparison association belongs in the central record.

Isolation includes conversation context. Start blind source workers/reviewers and independent implementers with fresh context containing only their permitted inputs; do not fork a coordinator's mixed-lane history into them. A clean checkout cannot undo information already present in a worker's chat. For discovery output ownership and overlapping lane drafts, follow the [shared discovery rules](../discovery/README.md#permanent-findings).

Research keeps permanent findings and current relevant state. Remove obsolete preparation, migration, handoff and completed remainder reports instead of archiving them. Transfer unique finding evidence and still-open gates into these records first. Git preserves history; implementation alone never justifies deleting a confirmed finding.

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
