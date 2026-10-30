# Blind whole-Java source inventory: 1.9.4 → 1.10.2

This is a bounded whole-Java path/hash inventory and changed-edge routing aid for the independent blind final coverage audit. It is **not** a pair-freeze or full-coverage verdict. No wiki, implementation, issue/backlog or product-history source was consulted.

## Exact evidence identity and integrity

Read-only roots: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.9.4\ornithe-feather\` and `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.10.2\ornithe-feather\`. Exact mapping: Ornithe Feather Gen2, `feather-gen2-1.9.4+build.2` / `feather-gen2-1.10.2+build.2`; provenance is bound by each release's parent mapping JAR/Tiny artifacts and tools (Java 25.0.3+9-LTS, Gradle 9.7.1, Vineflower 1.12.0). Source-manifest SHA-256 values: 1.9.4 `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19`; 1.10.2 `91b0f478acb7b6f13463c35b268a30d2806f583402631a56f54ce2eb70d1ec71`. Each ready marker matched its version, mapping, source count and manifest digest; each manifest file digest matched its ready marker; every listed Java file matched its manifest hash; all on-disk Java paths were in the manifest.

| Release | Java files | Manifest SHA-256 | Client input SHA-256 |
|---|---:|---|---|
| 1.9.4 | 1819 | `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19` | `23e90103a1ca2ac71100004c6d5846de09f85695f579843ef8da41571e60c908` |
| 1.10.2 | 1845 | `91b0f478acb7b6f13463c35b268a30d2806f583402631a56f54ce2eb70d1ec71` | `7cdf7fcdc1c92584a233bf3c42bd7f0df1bdad3007d306831fe50410692be1e9` |

`whole-java-matrix.tsv` includes every path in the union with both file hashes and a changed-edge disposition. Counts: added-1.10.2=26, changed=760, identical=1059; no 1.9.4-only paths; 26 newer-only. Verification manifests say exact published source tree matches independently decompiled output with zero source differences per release. They show only regenerated derived remapped-jar differences and `rawInputArtifactDifferences: []`; that does not invalidate source findings.

## Existing bounded lane evidence retained

- `194-1102-ordinary31/report.md` and `194-1102-ordinary-audit31/audit.md`: local auto-jump is a real candidate delta. Keep it independent of raw keyboard input. Audit correction required: rise ceiling starts at 1.2F and gains 0.75F per jump-boost level; option copy is within the camera branch. This inventory does not rewrite or suppress it.
- `194-1102-collision31/report.md` and `farmland-collision-shape.md`: farmland collision top differs (1.9.4 full-block vs 1.10.2 inherited 15/16 outline). Preserve this candidate.
- `194-1102-responses31/report.md`: listed network receipt/player response paths were equivalent. It leaves block/world callbacks, piston callback paths and full respawn initialization open.
- `.native-source/20261028/frontier-c/194-1102-tick-boundary/report.md`: identity/ordinary context only, not a full inventory.

Identity transcription issue: `194-1102-ordinary31/report.md` lists the 1.9.4 `LivingEntity.java` digest as `bbb7703f18fd5da05c4e4a43a77ea644b388e63c01d34166d308ea520be4e5`; canonical manifest and actual hash are `bbb7703f18fd5da05c4e4a43a77ea644b388e63c01d34166d308ea52054be4e5`. The matrix uses the canonical value; bytes are intact.

## Changed-edge dispositions and open edges

- **Open candidate to triage:** `net/minecraft/item/ChorusFruitItem.java` adds `stopRiding()` inside the 1.10.2 teleport-attempt loop, before `tryTeleport`. This mutates mount state for a ridden entity during direct teleport response; independently judge whether this player item-use response is in scope. Not covered by the three current lanes.
- Collision lane covered `Entity.move`, swept collision collection, movement AABB operations, state-to-shape delegation and collision-shape providers. Farmland is the candidate delta. Supplementary scan of changed block callback bodies found `Block`, `EndPortalBlock`, `PortalBlock`, and `RedstoneOreBlock` bodies equal for `onSteppedOn`/`onEntityCollision`. **Callback dispatch/call-order remains open** across the block/world traversal, including piston response path as the response report says.
- Other changed movement-like operations checked from whole-class diffs: `World.containsMaterial` adds normalized fluid-current velocity using `0.014` (old local double vs new literal); `Entity.changeDimension` uses the same factor `8.0` but is server-only; changed entity position assignment expansions preserve values/order; golem/dragon direct impulse snippets preserve gates/constants. `FlowingLiquidBlock` and Frost Walker touch `STRUCTURE_VOID`/`Material.AIR`, newer-only block states.
- The changed `FishingBobberEntity` direct local-player reel response (`doEvent` → `pullHookedEntity`) was traced: the same client/local-player gate and `double 0.1` impulse arithmetic are used on both sides. Its changed hook collision/flight routines affect the bobber or hooked non-player entities; no changed player-velocity response was established there.
- The global `Minecraft.tick` method body retains its player/world tick order; the changed hunks in that file are initialization, rendering, diagnostics and utility. The changed `WorldChunk`/`WorldChunkSection`/`PalettedContainer`/`WorldRegion` hunks refactor storage/iteration or block-entity lookup; block-state/palette reads used by collision remain unchanged in the inspected methods.
- **Input:** `GameOptions.autoJump` / `Input.getMovement` support the auto-jump candidate; `KeyboardInput` is identical. `KeyBinding`'s changed key-code gate is raw keyboard/device plumbing and excluded.
- **Registration/resources:** Java `Blocks`/`Item` register newer blocks (including Magma and Structure Void); those are not 1.9.4-era states. No tag-like source path or tag-registry reference appeared in either Java tree. Non-Java resource/tag payloads are not present in the decompiled Java manifests; their registration gate is explicitly not closed here and must be decided by the independent auditor.

Actual open edges for blind final audit:

1. Triage `ChorusFruitItem.finishUsing` and trace `Entity.tryTeleport`/player item-use caller.
2. Independently close player-facing block/world callback dispatch order and direct responses, including piston movement, portal callbacks and state receipt; method-body sameness or packet-handler sameness alone does not close dispatch.
3. Decide whether any external resource/tag registration data participates in movement-relevant block selection.
4. Review the corrected auto-jump evidence revision; intermediate introduction remains unknown within 1.9.4–1.10.2.
5. Have a distinct blind reviewer retrace the complete player tick/dependency graph and terminal paths. This report does not self-close/freeze the pair.

No product implementation, wiki lane, backlog/history review, build, test, game/runtime, decompilation, download or source regeneration was performed.
