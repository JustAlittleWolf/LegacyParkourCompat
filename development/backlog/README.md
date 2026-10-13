# Backlog tool

Run from the repository root with Python 3.10 or newer, using only the standard library. On Windows use `py -3`; on Linux/macOS use `python3`.

The master orchestrator is the sole writer. Workers send bounded updates in chat; they do not run write commands or create parallel trackers. Source workers must not query this mixed-lane store: the master supplies only their allowed source inputs and coverage. The tool is a convenience and consistency boundary, not an access-control system.

## Read only what is needed

```powershell
py -3 development/backlog.py summary
py -3 development/backlog.py list findings --status open --search jump --limit 10
py -3 development/backlog.py list coverage --limit 10 --offset 0
py -3 development/backlog.py show coverage 1.16.5--1.17.1 --field nextAction
py -3 development/backlog.py validate
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

## Preview and apply a targeted update

Read the entry and use the returned revision as `--expect`. Put only the changed fields in a small UTF-8 JSON merge-patch file outside the tracked research tree. For example, a coverage update file might contain:

```json
{
  "nextAction": "Inspect the remaining exact-version collision-provider dependency."
}
```

```powershell
py -3 development/backlog.py show coverage 1.16.5--1.17.1
py -3 development/backlog.py update coverage 1.16.5--1.17.1 --expect <returned-revision> --file C:\temp\coverage-patch.json
py -3 development/backlog.py update coverage 1.16.5--1.17.1 --expect <returned-revision> --file C:\temp\coverage-patch.json --apply
```

Without `--apply`, mutation commands validate and preview only. `--file -` reads the patch from standard input. Object fields merge recursively; omitted fields stay unchanged, `null` removes a field, and arrays replace the whole array. Read an array before replacing it so existing evidence is preserved. Metadata updates take a JSON string. Entry identities cannot change in an update. Do not copy the example action into real coverage unless it describes the actual remaining work.

`add <section> <id> --file <record.json>` previews a complete new entry and rejects duplicate IDs. `delete <section> <id> --expect <revision>` previews deletion of a resolved `unresolvedClaims` or `implementationEvidenceGaps` entry. Both need `--apply` to write. Preserve unique evidence before deleting a resolved temporary entry. Permanent findings, coverage and scope dispositions cannot be deleted through this tool.

Writes acquire an exclusive lock, reject stale entry revisions, validate the complete proposed store and atomically replace the storage file. Updates to unrelated entries do not invalidate a previously read entry revision; they are preserved. If a crashed writer leaves `backlog.json.lock`, verify that its recorded process has stopped before removing the lock. Do not bypass an active writer.

`development/research/backlog.json` remains machine-managed, Git-readable storage. Agents do not need to load or hand-edit that large file; ordinary reads and edits use this interface. Existing records and evidence are preserved without a data migration. The serializer keeps one-record diffs for compact sections and readable coverage records. Commit the resulting focused storage diff normally. JSON patches are transient command input, not new reports or trackers.
