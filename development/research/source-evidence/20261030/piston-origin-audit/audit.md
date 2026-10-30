# Independent audit: piston movement and block-effect origin

Audit target: `.native-source/20261029/partial-sweep-piston-origin-source/report.md`.
Scope: exact-source review of piston direct player dispatch, no-argument block-effect endpoints, zero-vector return, NOCLIP ordering/lifetime, retraction-base correction, and the report's provenance. This is not pair-wide source coverage, runtime parity, or implementation approval. I did not inspect product code, wiki material, backlog/history, or game behavior.

## Verdict

The bounded source claims in the target report are accurate, with two qualifications:

1. The 1.21.4 `checkInsideBlocks` traversal has separate source changes (query-box construction and the movement-origin collision predicate) that the target report explicitly excludes. Those dependencies must be reopened if anyone broadens the claim from callback order/bookkeeping to the set of blocks reached or the resulting trajectory.
2. The 1.21.3/1.21.4 decompile provenance and artifact manifests do record the embedded Mojmap mapping inputs. The target report verified source-manifest and source-file hashes but did not include the mapping-root hashes. This audit records them below. The provenance records `mappingOption=mojmap` and artifact entries, rather than mapping-input hashes in the provenance JSON itself.

No difference was found in the piston helper, its active tick ordering, the zero-vector early return in `Entity.move`, the no-argument `oldPosition()` origin, or `LocalPlayer.move` ordering across the checked releases. A concrete source change is confirmed in successful-path list timing and exceptional-path list retention between 1.21.2/1.21.3 and 1.21.4. Whether it yields a reachable player trajectory difference is not established by this audit.

## Immutable evidence identity

Canonical read-only sources: `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/<version>/mojmap/`.

The source file SHA-256 values reported by the target match the actual files and the corresponding source manifest/provenance entries:

| Release | Provenance / ready identity | PistonMovingBlockEntity.java | Entity.java | LocalPlayer.java |
|---|---|---|---|---|
| 1.21.2 | `.decompile-provenance.json`, SHA-256 `00f399407dc447737ea83e3e46562418f8c712f66e3f51cb85d46afa2ecac575`; embedded version metadata `1.21.2` | `426442149f89a28fc5973a52b8156aec7c18d76faf8b62eecae9f120b2fdba16` | `a93719c302a0381a972af75ea360465e2e3551708dd07c34d4d40b7e5173c2b9` | `fbd40f1f47adfa66dda9b15188e5dce82af3e8e8d7c3dd0543e602a354ad3fe0` |
| 1.21.3 | root `mojmap.provenance.json`: version `1.21.3`, namespace `mojmap`, SHA-256 `24093437c0cd41d807ab0829068ef97828b65bbb95b38dfdc4f706883a8483d7`; ready marker client SHA-256 `25190c556bc56d11070638ccab3b3c6efd76bb9684c92a2680a4de01c3c7138b`; source manifest SHA-256 `d673bb5464853e3a2a92ed9b1ffe1789d4e62c097bb884f67c336fcbb3b178ce` | `426442149f89a28fc5973a52b8156aec7c18d76faf8b62eecae9f120b2fdba16` | `a93719c302a0381a972af75ea360465e2e3551708dd07c34d4d40b7e5173c2b9` | `fbd40f1f47adfa66dda9b15188e5dce82af3e8e8d7c3dd0543e602a354ad3fe0` |
| 1.21.4 | root `mojmap.provenance.json`: version `1.21.4`, namespace `mojmap`, SHA-256 `ab0439864c89f4e0dab09daae7b57ab973368eeb0320626822e06f5a9bce9fb9`; ready marker client SHA-256 `c17c450c6e72cc51297daa57ce38f800aa01cf022b743daa21a0512d326d894e`; source manifest SHA-256 `f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0` | `426442149f89a28fc5973a52b8156aec7c18d76faf8b62eecae9f120b2fdba16` | `05f18ef2ec0413fc010230407c812a11553eb5123b68d21b5d7b2c0c175698ad` | `145686ebdc7f0d12a64070309073665eb8695b7e09911a723e86113a77d04611` |

For 1.21.3 and 1.21.4, each ready marker's `sourceManifestSha256` matches the actual `mojmap.sources.sha256` file; the three class hashes match its entries. Their `artifacts.sha256` actual hashes match `artifactManifestSha256` in the ready markers. The artifact manifests embed these relevant mapping inputs:

- 1.21.2: `.decompile-provenance.json` input `https://piston-data.mojang.com/v1/objects/ce0ed2eb99175df9ec5166324e3fdd1f87cee827/client.txt`, SHA-256 `e37560e4889168916a73dbc4922c563002eff70fbecfe1cf5c50a7b222f451b2`; this is the official client mapping input.
- 1.21.3: `artifacts.sha256` entry `1.21.3/client_mappings.txt`, SHA-256 `e92a9cb01c233075b51a6233d0c22f56dcda3fcab46438d3f7a1781d104dcd41`; Mojmap-remapped client artifact `1.21.3/client-mojmap.jar`, SHA-256 `9e5d42c42acaf0076ee92b41b13aa7440667967266ca67ecd50b08e5e4ba8024`.
- 1.21.4: `artifacts.sha256` entry `1.21.4/client_mappings.txt`, SHA-256 `48b502ccc5e855b49da8aa9c0c0d7c565bec54c9f5da3a3664528aff7b9fdf23`; Mojmap-remapped client artifact `1.21.4/client-mojmap.jar`, SHA-256 `56995548c9cb8bd7cdb9996b676daeafae02bde9d6ef4029b9eeca6bfd7dcc74`.

The 1.21.3/1.21.4 provenance JSON records `mappingOption=mojmap`, the exact batch versions and tool versions; the mapping hashes are in each embedded artifact manifest, not as fields of the provenance JSON. Thus mapping input integrity is independently checkable even though the target report omitted those hashes.

## Reviewed claims and source evidence

All paths below are relative to the canonical version root, under `mojmap/`.

### Piston caller and direct player response

- All three versions: `net/minecraft/world/level/block/piston/PistonMovingBlockEntity.java`, `tick`, lines 286–319: active branch computes `progress + 0.5F`, calls `moveCollidedEntities`, then `moveStuckEntities`, and only then stores/clamps progress.
- All three versions: same file, `moveCollidedEntities`, lines 110–180: only a positive calculated push amount enters `moveEntityByPiston` (lines 170–175); for retracting source piston, `fixEntityWithinPistonBase` follows the first helper call.
- All three versions: same file, `moveEntityByPiston`, lines 182–187: `NOCLIP.set(direction)` → virtual `entity.move(MoverType.PISTON, vector)` → `entity.applyEffectsFromBlocks()` → `NOCLIP.set(null)`. There is no `finally` or movement-success check. The helper reaches block effects when `move` returns normally, even if the physics-enabled move returned early for limited `Vec3.ZERO`.
- All three versions: same file, `fixEntityWithinPistonBase`, lines 241–253: after intersection and the `abs(a-b) < .01` gate, it calls the same helper with the opposite direction at line 250. This is the second effects invocation only if the earlier movement completed normally and the correction gates pass.
- 1.21.2/1.21.3: `net/minecraft/world/entity/Entity.java`, `move`, lines 619–701; 1.21.4 lines 627–712. In each, `noPhysics` directly updates position, while the physics path limits piston movement and returns at `Vec3.ZERO` before ordinary move updates.
- All three: `net/minecraft/client/player/LocalPlayer.java`, class declaration at 92 (1.21.2/1.21.3) or 93 (1.21.4), `move` at 873–882 or 903–912: calls `super.move`, then updates auto-jump and `walkDist`. `LocalPlayer` has no `onInsideBlock` override; the inherited empty `Entity.onInsideBlock` is at `Entity.java` 1079 (1.21.2/1.21.3) or 1098 (1.21.4).

### No-argument endpoint and inside-block callback order

- All releases: `Entity.applyEffectsFromBlocks()` delegates to `applyEffectsFromBlocks(oldPosition(), position)` (`Entity.java` 736–738 for 1.21.2/1.21.3; 747–749 for 1.21.4). `oldPosition()` builds a `Vec3` from `xOld`, `yOld`, `zOld` (`Entity.java` 1501–1503 for 1.21.2/1.21.3; 1522–1524 for 1.21.4). The piston helper does not supply a local pre-move endpoint. Every helper invocation therefore uses stored old position plus then-current position. A second retract correction helper obtains the same stored old-position source again.
- 1.21.2/1.21.3: `Entity.applyEffectsFromBlocks(Vec3,Vec3)`, lines 740–767: under `isAffectedByBlocks`, grounded `stepOn` runs first; the segment is appended; `checkInsideBlocks` runs synchronously on the mutable list; fire/lava states are checked; then both movement and collected-state lists are cleared.
- 1.21.4: same method, lines 751–779: grounded `stepOn`; append segment; `List.copyOf(movementThisTick)`; clear the mutable movement list; synchronously walk the immutable snapshot; then check collected states and clear `blocksInside`.
- All versions: `Entity.checkInsideBlocks` traverses records in list iteration order and synchronously calls `BlockState.entityInside` followed by `Entity.onInsideBlock` when its per-block gates pass; throwables from shape/callback work are wrapped and rethrown as `ReportedException`. Cleanup of visited positions is at the normal tail and is skipped on throw.
- Target-report exceptional-path statements check out: a thrown `move` skips both the effects call and `NOCLIP.set(null)`; a throw during effects skips `NOCLIP.set(null)` and prevents the subsequent retract correction; a thrown check skips caller cleanup. In 1.21.2/1.21.3 the mutable movement list remains uncleared and can be supplied by a later invocation; in 1.21.4 the taken snapshot was removed from the mutable list before callback entry. A later invocation may still encounter uncleared `blocksInside`/visited-position state after either throw.
- In 1.21.2/1.21.3, `isAffectedByBlocks()` at `Entity.java` 769–771 excludes removed and `noPhysics` entities. Thus the helper's no-physics position update is followed by the effects method's gate, which does not append or traverse a piston record and does not clear any preexisting movement records. `teleportSetPosition` clears movement records after resetting old position (lines 2721–2732; 2749–2760 in 1.21.4), a distinct path.

## Source facts versus consequences

**Source facts:** The piston helper and ordering are byte-identical across these versions. Direct local-player `move` is virtual and completes its override before the helper's no-argument block effects. A physics-enabled zero-vector piston return still reaches block effects normally; no-physics movement instead fails the `isAffectedByBlocks` gate. The movement origin remains stored `oldPosition`, not a captured piston displacement origin. NOCLIP stays set during both movement and synchronous callbacks, clears only on normal return, and a retraction-base correction can invoke the same helper after that first normal return. In 1.21.4 pending movement records are detached and cleared before callback execution; 1.21.2/1.21.3 clear them only after successful callback processing.

**Reachability / trajectory inference not established:** Those facts alone do not prove that a piston-triggered callback changes a local player's position or other movement state differently on a successful path. The exception-path list-retention difference is established; its player-visible trajectory consequence is not. To accept a runtime movement finding, independently close which callbacks and pending records are reachable for a `LocalPlayer`, their state/shape gates, and whether any record affects movement before a later effects call.

**Exact dependencies / exclusions:** The complete callback condition also depends on `checkInsideBlocks`, its movement records, `visitedBlocks`, entity-inside shapes and `collidedWithShapeMovingFrom`. The last methods differ outside this audit's requested operation: 1.21.2/1.21.3 use the current bounding box at `Entity.java` 1040–1047; 1.21.4 constructs a box from each record's `to` endpoint at 1057–1060, and the collision predicate / shape coordinates are also a separate change. As the source report explicitly excludes final-query-box and recentered partial-shape work, this audit neither adjudicates those differences nor permits using this bounded verdict to accept/reject them. Those gates are required if the conclusion expands to the set of blocks whose callbacks run.

## Independent disposition

- Source review of the report's bounded claims: **confirmed**.
- Equal behavior across 1.21.2 and 1.21.3 for the checked files: **confirmed by equal file hashes**.
- New 1.21.4 source behavior in this bounded chain: **confirmed** for movement-list snapshot/clear timing.
- Reachable successful-path player trajectory difference from this change: **not established**.
- Pair coverage/freeze, runtime parity, implementation readiness, and excluded block-query/shape changes: **open**.

Audit artifact hash is to be computed after this file is finalized.
