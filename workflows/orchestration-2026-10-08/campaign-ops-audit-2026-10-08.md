# Movement campaign operations audit — 2026-10-08

## Scope and result

This is a read-only audit of campaign chats, source checkpoints, the primary checkout, integration evidence, and canonical 26.2 source readiness. The paired scheduling proposal is in [campaign-work-queue.json](campaign-work-queue.json). Every one of the 26 source pairs remains **partial**; no worker completion, accepted snapshot, code change, or build was treated as pair completion or runtime validation.

The coordinator ledger used for the current roster is `feat/movement-coordinator-2026-10-08@28c96ce2a1bc3b6214de1801033f7f9122deecdb`. It supersedes the earlier stale copy. Current configured models match the request: coordinator `gpt-6.1-sol / medium`; worker session contexts checked in the campaign roster use `gpt-6-luna / high`.

## Recovered creation outcome and current roster

The title **Implement rising crouch edge restraint** resolved to thread `01a11b4a-7214-7b32-bc5c-2561635549af`. Its only turn is interrupted, has no user/assistant/tool items, and has no task branch or checkpoint. That reserved slot was released after the empty state was independently confirmed. The replacement implementation chat is `01a11bc2-2e8a-78e2-a4ec-cee2b9b52ee6`; it completed implementation on `fix/1182-ascending-sneak-edge` at `14a6e23432ab6a885b5c5ce265cf2404283756f6`. The source pair remains partial.

The latest direct chat-status snapshot contains **10 active roles including the coordinator**, below the limit of 15. The still-active old source chat `01a116c5-ef2e-7990-97d5-835aee2a0ce8` remains reserved for `1.17.1--1.18.2`. Other active source roles are `1.10.2--1.11.2`, `1.12.2--1.13.2`, `1.16.5--1.17.1`, and `1.18.2--1.19.2`. Active non-source roles are the operations audit, MCPK Wiki review, movement-patch static re-review, and swimming-finding reconciliation. The 1.9.4--1.10.2 worker completed its handoff, with the pair still partial.

There is no untracked active campaign role in the current ledger/status snapshot. The previously untracked blind review, Minecraft Wiki review, passenger-yaw correction, and static-review role have since been recorded or updated in the ledger; their statuses changed while this audit was in progress. The JSON queue keeps the active roster and completed role history separate.

## Primary and integration checkout

- Primary checkout: `D:/Javastuff/LegacyParkourCompat`, `main` at `ea812dc9171fdf41e02b3a2292db7d40b105c9c4`, clean with no tracked or untracked paths reported, 78 commits ahead of `origin/main`.
- Integration worktree identity: `C:/Users/Wolfi/.codex/worktrees/movement-campaign-integration/LegacyParkourCompat`, branch `feat/movement-campaign-integration-2026-10-08`, HEAD `ea812dc9171fdf41e02b3a2292db7d40b105c9c4`. Its identity is in `git worktree list`; direct `git -C` status access was denied in this audit context.
- The committed source diff from `457b350fa5490b90b08de4f97033718c77efec1d` to the integration HEAD is TICK-01 only: the `LocalPlayer.aiStep` injection moves from `TAIL` to `HEAD`, and the hook Javadoc changes from end-of-tick to start-of-AI-tick wording.
- The integration worktree contains `build/no-tests.init.gradle`. Its `beforeProject` and `projectsEvaluated` hooks disable Gradle `Test` tasks. The recorded `gradlew.bat build -x test --init-script build/no-tests.init.gradle` completed successfully with 18 tasks. The JAR at `C:/Users/Wolfi/.codex/worktrees/movement-campaign-integration/LegacyParkourCompat/build/libs/LegacyParkourCompat+26.2-1.0.0.jar` was directly hashed as `1C3198B1E05AC5FD4CE4D84683060576CA34ED636CA81E88728FF9F6A07BCBD1`.
- No build, tests, source edits, or runtime work were performed by this auditor.

## Canonical source readiness

The correct marker is `D:/Javastuff/LegacyParkourCompat/build/movement-campaign-2026-10-07/ready/26.2/unobfuscated.ready.json`; it exists and declares `ready`, version `26.2`, mapping `unobfuscated`. Its `LocalPlayer.java` exists at `D:/Javastuff/LegacyParkourCompat/build/movement-campaign-2026-10-07/ready/26.2/unobfuscated/net/minecraft/client/player/LocalPlayer.java` with SHA-256 `8D089AA09217E3607B38590F7C1623385562800943AC6DFD3D17804E041DA6D6`.

The two obsolete `stable-shared-minecraft/ready/26.2--unobfuscated.json` locations checked from the old handoff are missing (`ObjectNotFound`): one under the primary `D:` checkout and one under `C:/Users/Wolfi/.codex/worktrees/df7f/...`. These checks returned “does not exist,” not access denied. The Elytra task later found the canonical marker; the queue records the corrected path and source hash. No whole-tree rehash or decompilation was performed.

## Implementation, review, and wiki lanes

TICK-01 is the only code batch integrated into current `main`. Passenger yaw, pose-fit, Elytra-start, and rising-crouch implementations have since produced separate task-branch commits; they remain outside current `main` pending the listed independent review/integration work. For example, `32c8b0a` is a merge of current `main` **into** the passenger-yaw task branch, not a merge of that correction into `main`. The static re-review is active.

The Minecraft Wiki review finished with swimming pitch **accepted** and the fence/End Portal Frame finding **request changes** solely because its 1.9.4 source-manifest SHA is wrong. Swimming reconciliation is active, and wiki provenance correction is queued. MCPK review remains active. Neither wiki lane changes source-pair coverage; the 26 source pair statuses remain partial.

## Audit branch and commit

This audit and queue are committed on `ops/movement-campaign-audit-2026-10-08`, branched from the audited `main` snapshot. Main itself was not changed by this task.
