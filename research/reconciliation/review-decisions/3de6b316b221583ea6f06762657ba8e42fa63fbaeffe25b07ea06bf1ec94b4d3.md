# Focused blind source review: 1.8.9 to 1.9.4

- Reviewer branch: `feat/critical-1-8-movement-source-review`, created from `main` at `002137b227676caea77f6832b9f4c8d0b6200bff`.
- Review scope: exact Minecraft source for player jump impulse/height, PaneBlock collision geometry, and sneaking pose dimensions; limited source-level dependencies of those findings.
- Blindness: no mod implementation, wiki, or wiki-audit report was read. This is a focused additional source audit, not a full-pair acceptance. No test, build, client, TAS, Gym, server, or Docker work was run.

## Artifact and source identity checked

The source owner’s latest committed report ref visible during this review was commit `8e03c2de01b609934a8f3740055a2557bf9059a3`; its pair run file SHA-256 is `206500514711c86a825e15b987b4728febf88bf3e39061a1e1279ad06457dd0e`. The individual STATE-01 and STATE-03 finding blobs at that ref hash to `8d74c339535db2e86dee838bef9b3a1cf1a52872b97b3c3a20a505552d8dc412` and `d0965cb48b6b7bdddc41fca9c6210c5c498814aaf1405d7f34e2ff9f9f8529b3`, respectively. STATE-01’s recorded incremental snapshot is the earlier immutable commit `7437cfb2782e7085bd63b9360ba36e07b605f23f`; the current report ref does not record an accepted blind review for it.

I independently verified both source-manifest hashes, the cited source files against those manifests, the diagnostics file hashes, and both immutable derived-artifact snapshots and their sidecars/revision identities:

| Evidence | 1.8.9 | 1.9.4 |
| --- | --- | --- |
| Source manifest SHA-256 | `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004` | `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19` |
| Original artifact manifest SHA-256 | `da003358256d1c4402ebb20614651e5410310e871ee913de2b9c1295a64e1446` | `9527dca544694daa3b4a7741be1a5b4802d8b6665c8a152a408a37a27a1b4d77` |
| Diagnostics SHA-256 | `62dc9b445bec2f62b6dac9da501e875377636d08682891891212aea464999d28` | `51bd42a633c04931814ab78a841cedd3bf87460e04f7877676599b51b59bbb1d` |
| Immutable snapshot SHA-256 | `5c4cff3e4ac10ea1e1da166279133801ad304b4557d5ee4a77a2430977afb6a5` | `fbcf50795566e12b8eab0e733b136ed562c4009d707ef4a7a4994936491816a3` |
| Revision JSON SHA-256 | `95e2dc4aa3edba2d287f2bab092c61c0f66874f790af1b8c5d98196c980e105d` | `df0a26fd4c65292530cdad638e6789cc26fcd47875fe1c3eb2daef6de7e3f915` |
| Revision | `feather-r1-2026-10-07` | `feather-r1-2026-10-07` |

Snapshot paths are `build/movement-campaign-2026-10-07/revisions/derived-artifact-snapshots/feather-r1-2026-10-07/{1.8.9,1.9.4}/ornithe-feather/client-ornithe-feather.jar`. Each actual JAR hash equals both its `artifact.sha256` sidecar and `revision.json`. All 36 other rows in each original artifact manifest, including the raw client, libraries, mapping inputs, and version metadata, matched; the only mismatch is the unavailable original derived mapped JAR. The original mapped JARs are unavailable and their identity/equivalence is unproven. I do not treat the mismatch as metadata-only. The source manifests and relevant source bytes are unchanged and verified. The diagnostics report successful exact releases and no damaged relevant movement method.

## Independent exact-method trace

### Base jump impulse and height

A: `LivingEntity#getJumpStrength()F` and `jump()V`, lines 1092-1108, source SHA-256 `082831c6578e3a70fa6cea5b90bc3eefc26678259b66334470de22b90b5b0e4e`.

B: `LivingEntity#getJumpStrength()F` and `jump()V`, lines 1275-1291, source SHA-256 `bbb7703f18fd5da05c4e4a43a77ea644b388e63c01d34166d308ea52054be4e5`.

Both return `0.42F`; both assign that result to `velocityY`, then add `(amplifier + 1) * 0.1F` for Jump Boost, then apply the sprint horizontal impulses using `sin/cos(yaw * (float)(PI / 180.0)) * 0.2F`. The ground-jump branch in `LivingEntity#mobTick()V` has the same water/lava/on-ground/cooldown ordering. `PlayerEntity` inherits `LivingEntity` in both versions and defines no `getJumpStrength` override. For an ordinary ground jump, the vertical assignment occurs after the per-axis velocity cutoff, so that cutoff does not change the base 0.42F launch value. This closes only the direct player jump-impulse slice; it makes no claim about every travel or effect path.

### Pane collision geometry

A: `PaneBlock#addCollisions(World,BlockPos,BlockState,Box,List,Entity)`, lines 62-90, plus `Block#addCollisions` / `getCollisionShape`, lines 339-350. PaneBlock SHA-256 `f099cdc306c365b73632fc703f72b61bc6b304cb5df06c43234965f89e28de61`; Block SHA-256 `ea10f05106a3cf7189aec85236a7ecf9c7106a717f37bed583b5ea4adeffa528`.

B: `PaneBlock#addCollisions(BlockState,World,BlockPos,Box,List,Entity)`, lines 53-70; `getShape()` and `resolveVirtualProperties()`, lines 77-100; shape table, lines 25-42. PaneBlock SHA-256 `e6e3dd856efb96146634ab8f56c915afbc6116fd51973c25229ef7aa2ce0e5c8`; Block SHA-256 `e62ece80c6a7e7121346f65f8fdf9b148c29441a27de9f568bba9afe1d84fe6`.

Both versions register `iron_bars`, `glass_pane`, and `stained_glass_pane` as PaneBlock variants. A computes neighbors in the collision callback. When all four neighboring blocks fail `shouldConnectTo`, each of its two axis branches falls through to a full-length 1/8-wide center strip, yielding a plus-shaped union. B resolves the four Boolean connection properties from the same horizontal neighbors, always adds `SHAPES[0]`, and adds arms only for true properties; with no connections, `SHAPES[0]` is only the central 1/8-by-1/8 post. Thus a player box intersecting an isolated pane’s former arms but not its center post sees a different collision result. This is a source-proven shape difference, not a claim that a particular trajectory was observed.

Registration evidence: A `Block.java` lines 1040-1041 and 1180; B lines 950-952 and 1099 register iron bars, clear glass panes, and stained glass panes through PaneBlock or its subclass. A/B `Block.java` hashes are listed above. Reachability: A `World#getCollisions(Entity,Box)` lines 891-923 (block dispatch at 921), and B lines 899-935 (dispatch at 933), enumerate block cells and call each block state’s `addCollisions`. A `Entity#move(DDD)V` begins at line 371 and clips against collision boxes at lines 439-469; B begins at line 441 and clips at lines 509-542. These movement methods are inherited by the player path. A/B `World.java` SHA-256: `3c04f5b874fbb6692039164c882a3c47fe28c7eca864e91c9ac57798153888ee` / `2fe063e0ec224eed9fc7d01b8f788c32035eed5fee5a9d98ea5e82296236e05a`. A/B `Entity.java` SHA-256: `d4c10932cb5bb1067a5a58be4e1bb1b42bdde3b6fc893d88178cc07435a1696b` / `bcd7fa2206bf8d7271102f6da7fe2771f96dbe22ec79dab9f2101a3e29c17ef0`. Collision shape, neighbor predicates, registrations, and cell-query dependencies are in scope.

### Sneaking dimensions and collision-fit path

A: `PlayerEntity#tick()V` contains no pose-size update. Its only player size writes are reset/death/sleep/wake cases; the source-wide size-writer inventory has no sneaking-conditioned dimension write. `getEyeHeight()F` has a separate sneak adjustment. PlayerEntity SHA-256 `e66cb294fc93118148a444bbafdf4dd57cbf66a23d69b1e8892cefccc690ab88`.

B: `PlayerEntity#tick()V` calls `updatePlayerPose()V` at tick end (line 245). That method, lines 285-308, prioritizes fall-flying, sleeping, sneaking, then standing; for ordinary sneaking it proposes width `0.6F`, height `1.65F`, builds a candidate box at the current minimum coordinates, and calls `world.getCollisions(box)`. It calls `setSize(f,g)` only if the candidate has no collision. PlayerEntity SHA-256 `d658a0d95452d12bb7e347bfd802240eeecaf7f938e10dcd43e2640434387f85`. Entity#setSize is present in both versions and preserves the box base; width stays `0.6F`, so its width-growth displacement branch is not selected.

The local player path in both versions is `LocalClientPlayerEntity extends ClientPlayerEntity extends PlayerEntity extends LivingEntity`; `LocalClientPlayerEntity.tick()` calls `super.tick()`, and `LivingEntity.tick()` calls `mobTick()`. Exact chain call sites: LocalClientPlayerEntity.tick() A lines 105-108/B 143-146 calls super.tick(); PlayerEntity.tick() A line 228/B 200 calls super.tick(); LivingEntity.tick() A lines 1259-1296/B 1489-1551 calls mobTick(). LocalClientPlayerEntity SHA-256: A `1762b116e6b06d682b7daaa0fc8cce39b0ff455b3db8cf79dab03ac74f6c4053`, B `8aaf711948b7602c2e6c015a37e36ed06073d39727d999d80480b4910b704f5d`. ClientPlayerEntity SHA-256: A `b322144fcc965c9bf5f6ff18882d17f6f0e1cdaf0e545b1c7663c662aceb029b`, B `68f6e640808d8b31c1a99d1470075606a42290600fd45435aa86c15eb8db5ef0`.

Dependency note: the B pose-fit query uses the same collision-provider and world-cell path as player movement. The isolated pane change above can change whether a candidate height box is collision-free near a pane. Therefore the sneak-height finding depends on the pane geometry slice being separately frozen/reviewed or explicitly bounded around that collision case. This review does not combine the two root causes.

## Finding snapshot decisions

### STATE-01 — per-axis velocity cutoff

- Exact candidate snapshot: `SNAP-STATE-01-02`, commit `7437cfb2782e7085bd63b9360ba36e07b605f23f`, file `workflows/source-campaign-2026-10-07/1.8.9--1.9.4/findings/STATE-01-velocity-zero-threshold.md`, SHA-256 `8d74c339535db2e86dee838bef9b3a1cf1a52872b97b3c3a20a505552d8dc412`.
- Source evidence: A/B `LivingEntity#mobTick()V`, A lines 1406-1416, B lines 1666-1676; source SHA-256s shown above. The exact state difference is strict per-component cutoff `0.005` versus `0.003`, before AI/jump/travel; components in `[0.003, 0.005)` by absolute value are retained in B and zeroed in A.
- Artifact evidence: revision `feather-r1-2026-10-07`; immutable A/B snapshot paths and hashes listed above; source and raw input records verified. Original derived JAR identity/equivalence remains unproven.
- Local producer/consumer trace for the bounded claim: `LivingEntity#mobTick()V` receives the entity’s current velocity; for non-locally-controlled entities it first multiplies all three components by `0.98` (A lines 1400-1404; B lines 1660-1664), then applies the differing cutoff. `Entity#addVelocity(DDD)V` adds to the three components (A Entity lines 980-985; B lines 1094-1099), and `Entity#lerpVelocity(DDD)V` assigns them (A lines 1424-1428; B lines 1565-1569); previous movement also leaves velocity state for a later tick. In the player path, `PlayerEntity#mobTick()V` calls `super.mobTick()` (A lines 431-448; B lines 402-419), so the filter is reached. After it, player `serverTickAi()` only updates arm swing and head yaw (A lines 424-428; B lines 395-399); the base `mobTick` then takes the jump branch (A lines 1434-1445; B lines 1702-1709) and calls virtual `moveRelative` (A line 1450; B line 1711). `PlayerEntity#moveRelative(FF)V` calls `super.moveRelative` (A lines 1279-1295; B lines 1372-1390), where the current velocity is used by `move` (A `LivingEntity` line 1165; B line 1391) and the entity movement/collision path. Ground jump overwrites vertical velocity; water/lava jump adds to it; sprinting jump adjusts horizontal velocity. Thus the finding is limited to the exact pre-jump, pre-travel component state and does not imply that every component difference survives every branch or produces an observed trajectory. The wider inventory of every field writer and every later consumer is outside this bounded state-transform claim.
- Decision: **accepted for the bounded source-level cutoff delta** in this exact immutable snapshot: commit `7437cfb2782e7085bd63b9360ba36e07b605f23f`, path `workflows/source-campaign-2026-10-07/1.8.9--1.9.4/findings/STATE-01-velocity-zero-threshold.md`, SHA-256 `8d74c339535db2e86dee838bef9b3a1cf1a52872b97b3c3a20a505552d8dc412`. Paired exact source identities and immutable Feather-r1 snapshots are recorded above. This is source-only acceptance: the original derived mapped JAR identity/equivalence remains unproven, no bytecode or runtime claim is accepted, and the first changed release remains unknown within the endpoint interval. This does not accept the whole pair.

### STATE-03 — sneaking collision height

- Current committed candidate at source-owner report commit `8e03c2de01b609934a8f3740055a2557bf9059a3`: `findings/STATE-03-sneak-collision-height.md`, SHA-256 `d0965cb48b6b7bdddc41fca9c6210c5c498814aaf1405d7f34e2ff9f9f8529b3`. No immutable STATE-03 snapshot entry is recorded in that report ref.
- Decision: **pending**. The direct B resize code is source-confirmed. The previously reopened pose-resize path was independently re-read here and is confirmed; it is no longer an open path. The finding still depends on the pane difference above through its collision-fit query; it awaits an exact immutable snapshot with both-side identity and dependency closure. Ordinary sneak eye-height changes are not evidence of a collision-box resize.

### Pane collision — no committed finding snapshot

- No pane/iron-bar collision finding exists in the source owner’s committed finding index at the audited ref; COLL-02 remains in-progress there.
- Independent review confirms an isolated-pane shape difference and a reachable player collision query. **Exact requeue request:** create one bounded source finding for A/B `PaneBlock#addCollisions` and B `resolveVirtualProperties/getShape`, covering isolated and connected neighbor cases, PaneBlock subclass registrations, Block collision dispatch, World cell iteration, and Entity/player movement consumer; hash both source sides and cite revision `feather-r1-2026-10-07` snapshot identities with the unavailable-original limitation. Re-review any newly committed snapshot independently.

## Coverage outcome

The direct jump-height symptom is unchanged in the traced base method. The pane collision difference is real in exact sources. The sneak-height candidate is real, but remains pending its collision-fit dependency and immutable snapshot. STATE-01’s exact threshold difference is real, but the current immutable snapshot remains pending its own declared closure and a fresh snapshot. The 1.8.9–1.9.4 pair remains active/partial; this review accepts no whole-pair coverage, equivalence, implementation, or runtime claim.


