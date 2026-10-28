# Independent source audit: 1.21.4–1.21.9 movement-segment lifecycle

## Verdict

**ACCEPT for the bounded lifecycle claims, with one required source-reference correction.** The exact source identities, selected-file hashes, record/drain order, 1.21.6 cap/merge behavior, 1.21.9 attempted-motion hint, callback collector phase, and the stated local-player and piston consumers are supported. The author report twice names `LivingEntity.tick` as the method containing post-travel `applyEffectsFromBlocks()` calls. The calls are inside `LivingEntity.aiStep()`; exact line locations are correct. Publish a separate correction supplement so the source member name is accurate; preserve the original report.

This review does not establish block traversal ties/epsilon math, visited-block admission, exhaustive callbacks, client correction reachability, runtime trajectory, or a full-pair freeze.

## Bound inputs and independent identity checks

Audited author report: `.native-source/20261028/frontier-c/1214-1219-segment-recording-lifecycle/report.md`, SHA-256 `c4468295b043b2457e561c49d2c741d7c783f80a766ec303390cb6d889651db6`.

The author source identity table is `source-identities.csv` SHA-256 `e3459bdcc617df2a36a40160a3956111234c3c40843be51a267ff95a9c3b981b`; the author selected-source table is `source-hashes.csv` SHA-256 `08caf45995ee11a5f248face476ba0c672cfa811091ae6e31b1a2add25355d66`.

I independently checked the six exact Mojmap identities and all 60 rows in the selected-source table. All 58 present Java files hash-match their source manifest or embedded provenance entry; the two absent rows are `InsideBlockEffectApplier.java` and `InsideBlockEffectType.java` in 1.21.4, and those files are present and hash-verified in 1.21.5. My checked file-by-file table is `verified-source-hashes.csv`, SHA-256 `39691e5e9630d0727ebbc59a95324c933eccaa2ad8491e2996cf33b7b0af78bb`.

| Release | Source identity and entry count | Readiness/provenance check |
| --- | ---: | --- |
| 1.21.4 | `mojmap.sources.sha256`, SHA `f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0`; 5,744 entries | ready marker SHA `1c6617b7acf8bb357f77c6c6c276881c73d882bda1f93f6dd217bb54eb8aa4be`; parent provenance SHA `ab0439864c89f4e0dab09daae7b57ab973368eeb0320626822e06f5a9bce9fb9` |
| 1.21.5 | `mojmap.sources.sha256`, SHA `365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9`; 5,921 entries | ready marker SHA `5f65fb143209abcc6e28e77a7150aecefdbc9de00b6ef3fc924e53bc0e1a667e`; parent provenance SHA `b0832a4e1507bd74ac300fae2fca78668964b21ef2d05be8cff01b9d1d60da8e` |
| 1.21.6 | embedded `.decompile-provenance.json`, SHA `56b036c61094d1d936882bdf5b2b59044512189cb33adbb075880c727da9b19e`; 6,103 entries | no separate release ready marker |
| 1.21.7 | embedded `.decompile-provenance.json`, SHA `45bf296d921b2239fd0aedb2684e2c1cb9b7d7532b4106b8a2fd9b09309ca82d`; 6,104 entries | no separate release ready marker |
| 1.21.8 | `mojmap.sources.sha256`, SHA `8ffb76cea647a2ba4fe58e000678f751bea2c40ea6358bae492e962ed1d9d008`; 6,105 entries | ready marker SHA `2e3bf302503eb127061cc605797b390f5f4a4b508f09350a59d78ced857ba7d7`; parent provenance SHA `90fcb3707371f2cfc5414e0089cfa231f505821d7aca45211c7c816332bb4cfa` |
| 1.21.9 | embedded `.decompile-provenance.json`, SHA `37d3049478585a43ffcace14fc8f2ee814b35ceedd8c9bad8b60adaed9b72c4f`; 6,386 entries | no separate release ready marker; `.mojmap.staging-7509591801405925509` exists and was not read or modified |

I also independently compared the ten source paths listed for the .6–.8 unchanged claim across all three release manifests: all ten hashes are identical. No `.mojmap.previous-*` snapshot was present in the six release parents.

## Source claims checked

- **1.21.4→1.21.5 recording and drain:** In .4, `Entity.move` does not append its accepted displacement to the movement list. The no-argument applier uses `oldPosition()` to `position`; the two-position overload adds that segment, copies the list, clears the live list, then scans (`Entity.java:747–765`). The only source callers found for `recordMovementThroughBlocks` are the .4 server packet listener paths; no client replay is inferred. In .5, a physical move constructs ordered axis-aligned pieces from resolved displacement and adds the batch before `setPos` (`Entity.java:636–681`). The no-argument drain flattens to the final list, clears the live batches before callback work, then uses old/current fallback or the stated `9.9999994E-11F` terminal residual (`:768–779`). No cap is present in .5.
- **1.21.5 callback phase:** `InsideBlockEffectApplier.StepBasedCollector.advanceStep` flushes queued per-type work when the visitor step changes. `APPLY_ORDER` is the enum value order, with before consumers, one coalesced effect, and after consumers for each type. `applyAndClear` flushes the last step, runs final effects while the entity remains alive, clears the queue and resets the step. `Entity.checkInsideBlocks` calls `advanceStep` immediately before passing block and fluid callbacks. Synchronous callback code itself is not deferred. `stepOn` remains before segment scanning; collector flush precedes later rain/fire bookkeeping.
- **1.21.6→1.21.8 queue/flag:** .6 uses `ArrayDeque(100)` and explicitly caps size at 100 by removing the oldest two entries and inserting their endpoint merge with `false`, then appending the new move (`Entity.java:798–807`). A physical move records `(position, position + resolved, true)` before `setPos` (`:663–698`); true splits by axis order derived from resolved displacement. The drain/fallback/residual and piston removal remain as reported. The shared entity teleport setter changes state and calls `clearMovementThisTick`; the report appropriately leaves a client-local caller unproven. Ten selected lifecycle dependencies are byte-identical across .6/.7/.8.
- **1.21.8→1.21.9 hint:** The movement record adds `Optional<Vec3> axisDependentOriginalMovement` (`Entity.java:3962–3967`). After piston limiting, stuck multiplier and edge-backoff, `Entity.move` records the resolved endpoints with the post-gate attempted vector as the optional hint (`:670–711`). The checker uses that hint only to choose axis order while segment lengths come from resolved displacement; absent hints use the direct segment. The oldest-two merge constructor and explicit `(from,to)` applier create no hint. The 100-record cap remains. The new `CLEAR_FREEZE` enum value follows `FREEZE` and therefore enters enum-derived callback effect order.
- **Local player and piston reachability:** In .4, `LivingEntity.aiStep` applies effects after its travel call at `LivingEntity.java:2785–2787`, under the client/local-control guard; `LocalPlayer.isEffectiveAi()` returns true. In .5 the same post-travel site is at `:2769–2771`; .6–.8 at `:2821–2823`; .9 at `:2851–2853`. `LocalPlayer.isLocalPlayer()` plus the Player local-authority override makes the local-client branch true, while `isAffectedByBlocks()` still excludes removed and `noPhysics` entities. The piston helper calls its immediate explicit before/after scan after `move`, then removes that movement's queued record (1.21.5 batch removal; .6 onward single-record removal). The existing 1.21.3→1.21.4 caller report is bound by the author report and was reused for the earlier client call path rather than re-audited here.

## Required wording correction

The report phrases the .5 and later consumers as `LivingEntity.tick` at lines 2764–2771/2821–2823/2851–2853. Those line ranges are inside `LivingEntity.aiStep()` in the exact sources: method starts are .4 line 2691, .5 line 2669, .6/.7/.8 line 2721, and .9 line 2751. The ordering and gates are right; correct the member name in a separate supplement bound to the original report hash.

## Scope limit

No claim is made here about BlockGetter voxel traversal ties or epsilon, Direction tie order, visited-block admission, every block/fluid callback, all teleport corrections, server movement replay, or final movement trajectory. These remain the separately assigned source gates or full-pair coverage work.
