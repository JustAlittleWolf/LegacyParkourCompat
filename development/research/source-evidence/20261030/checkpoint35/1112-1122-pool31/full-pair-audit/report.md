# Independent full-pair source coverage audit: 1.11.2 → 1.12.2

Audit date: 2026-10-30. Scope: fixed-profile local-client player tick and terminal dependencies, including inherited tick/travel, direct velocity/position/box writers, block/shape/provider/fluid dependencies, equipment/effect/modifier inputs, and external player responses. This is an independent, source-only audit. No implementation, product diff/index/history, wiki inventory, build, test, game launch, or source generation was consulted or run.

## Verdict

**Pair-wide freeze is not defensible from this audit.** The source inventory is intact and its full path/hash matrix is independently confirmed by the separately supplied inventory audit. The ordinary, responses, and collision reports cover substantial terminal slices, and the ordinary/response reports have independent lane audits. However, no single artifact provides a closed and independently retraced full tick graph with all terminal slices and dependencies. The legal entity insertion/iteration path for attached-firework look-vector writes remains unresolved for all local-player states, as detailed below. The ordinary report's look-vector dependency was downgraded by its lane auditor for the normal local-player path; that bounded conclusion does not prove absence under every legal insertion, receive, re-entry, or player replacement order.

This report does **not** self-certify or freeze the pair. No new confirmed movement difference is established here. Existing source-confirmed bed bounce remains a reachable difference; the farmland disposition remains server-only as described in its source artifact.

## Immutable source and inventory identity

Canonical read-only roots: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.11.2\ornithe-feather` and `...\1.12.2\ornithe-feather`. Both endpoint records identify Ornithe Feather Gen2 build 2 and Vineflower 1.12.0. Release parents (the mapping roots themselves have no embedded provenance manifest) contain:

| Release | Ready marker | Provenance | Source manifest | Verification |
|---|---|---|---|---|
| 1.11.2 | `ceabfb266993567aab8c867525365e18aa0a0d7ba5a2cb7b6cfcc83d21318c40` | `dcdb65e1361297a584c5b93f581b1b539911911c6cec1a203740baa5379732c6` | `d908c2af76598ad26999563bfef0089f1d3c8c32483eee6b108f0defa2773df0` | `1ff7d450309db002c7b9e42319f933ea6db75f0622d45996b3674ecb7e5c8fb4` |
| 1.12.2 | `b0aeec721e5c0af02b33d9e217cb8272889fa139ded1ecc2a31e42b930138f7a` | `747425a26da363d1abc8a2cecd83a74504aeb1f58be93aa53f48c690e35318ce` | `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da` | `7f836ebc38f06b3cffe663d0899360028e9d695a04606fea1a9ff185e84651c0` |

The ready/provenance/verification records identify exact endpoint versions, client inputs, Feather merged mappings and Tiny maps, and the pinned decompile toolchain. The inventory reports 1,921 and 2,050 Java sources respectively; the two complete source manifests declare those source trees. The source inventory audit independently recomputed all file hashes, counts, byte totals, and union classifications.

The requested audit inputs were hash-verified:

| Artifact | SHA-256 |
|---|---|
| `java-inventory/java-file-matrix.csv` | `f41e7591f77d44268dc58d7b42a496b7385ffeb79992568c2dac50785d0f948c` |
| `java-inventory-audit/AUDIT.md` | `b0ae4debea5013e8710958e48327f44f57de616e5ababaf8d4b168e93830ef3a` |
| `ordinary/report.md` | `aee37d5f3508f063355c77bcc5bb8871fe7ce521099ca0957b8b418825c285ec` |
| `ordinary-audit/report.md` | `dc6a6f7a7ec1ef4c041bed0f4910ef1ce171ff3e2fe6285c10c5a590f257039d` |
| `responses/report.md` | `dbd17701e69e4650b58b70a1a98a801ec168b343e7365ba92854e897a88de388` |
| `responses-audit/report.md` | `14e73071d4490bfbbaad0b6d09b36b0fecffd0d3f9be476f24ef7fbb4c0ff3a2` |
| `collision/coverage.md` | `08925604ea1f5ce47888ed26bcf0a71b4ba2b19d8ac978334a4b22bb56f08d8e` |
| `collision/bed-bounce-on-landing.md` | `065582cbbf16452d55a350c01f2a9b2ef822e8031266059d6539829ce200f621` |
| `collision/farmland-server-side-replacement-disposition.md` | `7527f4fefca90c98d4e60fcf0ea866216330a2055aae0ea320a168b7a2f0fcdc` |

The bounded historical source-only report `.native-source/20261028/frontier-c/1112-1122-tick-inputs/report.md` hashes to `f437b2f41b1e3c7e2a7a83d1793f30d82640192dd4b0bb74e3d54f9bde80309f`. It identifies the same endpoint publication and mapping artifacts, but is treated only as bounded historical evidence, not a substitute for pair closure.

## Independently inspected source slices

### Attached-firework look-vector and tick insertion order — open

Exact paired source hashes:

- `World.java`: 1.11.2 `27cfaa5ff45c2d88c492fc5dae3ea4a2bb4536fd64bff8498a9a7d0cb82efb58`; 1.12.2 `e9fa9b8d6d31ad57a5b876f5f63a5e3c554a23437decf1845a493daf48233594`.
- `ClientWorld.java`: `996286a2b9a14b7396f1ff3501c0a2494af67534930ad5e9a0bbf5e88f12e01f`; `468def1eb1bd0791ce1d8b194ba22fe0fe49962bb3e696fb34b83bf4de7fb72c`.
- `ClientPlayNetworkHandler.java`: `65d362c00de1adfd55fae5f858b91bf9b227525b987c09a2d223279376a66175`; `fce21d9902e555fd46545fb04bdb6c4a912eef6398790776ad1a122e09960c7c`.
- `FireworksEntity.java`: `3a0edbb5d7f411d3f12d139121141fddfd812d70b0cdc71925ce2a846467398c` on both endpoints.

The inspected `World.addEntity` appends accepted entities to `World.entities`. `World.tickEntities` calls `tickPlayers()` and then iterates that list. The base `World.tickPlayers()` is empty; the local client player is therefore ticked through the regular entity list. `ClientWorld.addEntity` places non-loaded-chunk entities in a `pendingEntities` `HashSet`; `ClientWorld.tick()` processes up to ten re-entries after `super.tick()`, so a re-entered entity is appended after that tick's entity iteration. The normal `ClientPlayNetworkHandler.handleAddEntity` path constructs a detached firework from a spawn packet; attached state is resolved from synced attached-entity ID in `FireworksEntity.tick`. The local attached-firework constructor records the player's entity ID and sets the direct attached reference. In either form, once the firework tick sees an attached fall-flying living entity, it reads `getLookVector()` and directly rewrites the player's three velocity fields.

These facts establish append order for a fresh firework and explain why the ordinary “player already present, firework later inserted” route synchronizes `headYaw` before firework tick. They do **not** establish that every legal state has that ordering. I did not exhaustively close: client login/player insertion relative to preexisting entity entries; local player replacement/respawn/world-transfer removal and re-addition relative to already-present attached fireworks; accepted duplicate/forced network IDs and `forceEntity`/pending-set transitions; or all packet delivery sequences that can attach a previously spawned firework to the local player before the relevant tick. The `pendingEntities` set has no iteration-order guarantee. The primary report's normal-order argument cannot be promoted to a pair-wide negative until those states are either proven unreachable or bounded by the fixed-profile assignment.

Conclusion: keep the changed polymorphic look-vector consumer as an **open dependency**, not a confirmed movement finding and not a pair-wide exclusion. For local ordinary elytra travel, the existing ordinary report/audit show the local-player tick writes `headYaw=yaw` before the call; that closes only that caller path. A server-controlled remote player's yaw/head-yaw divergence does not by itself establish a local-client path.

### Other sources currently covered by bounded reports

The ordinary report and its audit cover the principal input, local tick, travel, look-vector, pose, dimensions and modifier/effect candidates. The ordinary audit supports the bounded negative for ordinary fall-flight travel and reports no independent movement finding; it does not close the insertion-order states above or every equipment/effect/provider branch. The response report/audit close named velocity/explosion/correction/teleport/respawn writers, but explicitly stop short of all indirect event writers. The collision report covers collision solver, candidate enumeration, block providers, callbacks, fluids and its two disposition artifacts; its author marked pair freeze and full player tick graph open. I reviewed those as bounded claims and did not independently re-diff every listed provider or handler body in this audit.

### Confirmed source difference retained

`BedBlock.beforeCollision` in 1.12.2 adds a non-sneaking downward-velocity bounce response, reached from `Entity.move` after vertical collision resolution. The provided finding identifies the local-player inheritance/call path and the direct client-side velocity write. This is a concrete movement difference. It does not close other terminal slices.

The farmland replacement position write is guarded server-side and belongs to server world evolution; it is not a client historical movement delta under the project invariants.

## Coverage disposition

| Area | Status | Audit evidence / gap |
|---|---|---|
| Source publication identity, mapping family, Java file union and file hashes | Examined | Parent ready/provenance/manifests and independently checked matrix audit; all requested artifact hashes matched. |
| Input and ordinary local tick/travel | Partial | Frozen report plus independent bounded audit; full paired dependency closure not retraced here, and no single ordered inventory binds every terminal. |
| Inherited movement and position/velocity/box writers | Partial | Ordinary and response reports cover selected writers; all callbacks and side-effect callers not independently exhausted. |
| Collision solver, shape providers, fluids/material drag, block registrations | Partial | Collision report has broad paired coverage; full provider/class diff not independently rerun in this audit, and report itself leaves full graph open. |
| Equipment/effects/attributes/modifiers | Partial | Ordinary report screens selected relevant implementations; item use, all modifier registrations and all callbacks not fully retraced. |
| External player responses (packet, teleport, explosion, respawn) | Examined for named slices; partial globally | Response lane and audit close its named gaps, not every indirect writer. |
| Attached-firework `getLookVector` direct velocity writer and legal ordering | Partial / open | Common append order traced; unusual but source-permitted insertion/re-entry/receive states not closed. |
| Bed landing response | Examined, confirmed difference | Exact finding supplied; local movement call chain and lack of server-only gate stated in source artifact. |
| Farmland replacement response | Examined, excluded | Server-only replacement disposition. |
| Other terminal callbacks, fluids, effect/equipment hooks, and all changed matrix classes potentially feeding player state | Unexamined or partial | No exhaustive class-by-class semantic disposition tied to the full changed-path matrix was completed in this audit. |

## Corrections and required next work

1. Keep the ordinary look-vector audit's negative conclusion bounded to the ordinary local fall-flight path and the normal already-inserted-player/fresh-firework order. It does not close all legal receive/re-entry/replacement states. The attached-firework caller remains open pending exact closure of those states.
2. Do not claim full-pair coverage from matching source hashes, lane-level no-difference reports, or the closed response subset. A complete audit must map every reachable tick terminal and all direct state writers back to callers, then independently reconcile each changed source inventory path or prove it irrelevant.
3. Preserve the bed bounce as a source-confirmed delta; no additional finding was established by this audit.

No source files were modified. The sole output is this bounded, non-freezing audit record.
