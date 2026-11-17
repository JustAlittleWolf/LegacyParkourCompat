# Independent source audit: moving-piston player response r2

## Verdict

**ACCEPT — bounded conditional source observation only.** The report accurately describes a source-level difference in the direct player-response procedure when the stated query and shape conditions hold. This does not establish ordinary map reachability, a realized net displacement or trajectory, provider equivalence, an implementation-ready movement finding, or pair-wide coverage/freeze.

## Bound inputs and identity checks

- Pair: Minecraft `1.8.9` → `1.9`, Ornithe Feather exact namespaces.
- Audited r2 report: `build/source-189-19-20261117/tick-state/piston-entity-response-r2.md`, SHA-256 `AA25A8EC6767B9B0B443C8CADBD7FC3B818B212EF5E897EA7AE20CA5F5A82B61`.
- Original report on disk: `build/source-189-19-20261117/tick-state/piston-entity-response.md`, SHA-256 `A8B276FC3E4908A556DCEFF5B0CCED05E7E83B40803A4AD357812BD29D5DC9F7`. The assignment's earlier hash `1288399d…` does not match the file; r2 correctly binds the observed digest and leaves the original unchanged.
- 1.8.9 source sidecar `ornithe-feather.sources.sha256` SHA-256 `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004`; provenance SHA-256 `ba066fc8f1c7ceb9819d2106338d9e9f2c2b96eb5a8329eb40e171b1f9889185`.
- 1.9 mapping-local `.decompile-provenance.json` SHA-256 `8daacb7af8f210e716ee4b34c43b8a5d6f6b4b19831d2fd725cbe16ecf330a1a`.
- Recomputed all 18 cited source-file SHA-256 values in the report. Every value matches the 1.8.9 source sidecar or 1.9 provenance source row, and the files match their release roots. The source identity manifests above match disk.

## Audited source path

- Both client `Minecraft` loops call `world.tickEntities()` when unpaused. Both `ClientWorld` constructors pass `true` to the `World` constructor. Neither `ClientWorld` overrides `tickEntities`; the inherited `World.tickEntities` loop ticks nonremoved block entities with a world when their position is in a loaded chunk and inside the world border. No logical-client guard suppresses `MovingBlockEntity.tick` or the response call on that path.
- Local-player inclusion in the entity query is source-backed: client setup adds the player through `World.addEntity`; `World.addEntity` adds it to its `WorldChunk` and world entity lists. `World.getEntities(null, box)` walks loaded chunks intersecting the box; `WorldChunk.getEntities` admits intersecting entity boxes using `EntityFilter.NOT_SPECTATOR`. The local player is therefore eligible when its chunk is available and its box intersects the response query. In 1.9, `Entity.getPistonMoveBehavior()` returns `NORMAL`; `PlayerEntity` does not override it, so the new `IGNORE` check does not reject an ordinary player.
- The paired method bodies support the reported response delta. In 1.8.9, `moveEntities(progress, amount)` samples one moved-block collision box; extension pushes use the progress-derived amount, an extending slime block writes facing-axis velocity without calling `move`, and the terminal call uses `0.25F` along facing regardless of extension/retraction. In 1.9, `moveEntities()` unions the current and previous moved-state shape samples, chooses facing or opposite facing for extension/retraction, computes overlap on the movement axis plus `0.01`, and calls `Entity.move`; a slime block writes that axis velocity and still proceeds to the positional move. The tick bodies call the response on each nonterminal progress tick in 1.9, but only during extension in 1.8.9; both have a terminal response call.
- Under `noClip == false`, both `Entity.move` methods query world collisions and clip Y, then X, then Z. The r2 report correctly limits this to the requested direct movement call and does not infer the final displacement from the requested amount. With `noClip == true`, the collision path is bypassed; the report explicitly conditions the clipping discussion on the ordinary non-noClip case.
- The changed shape dependency remains open and is stated accurately: 1.8.9 obtains a moved-state `getCollisionShape` box; 1.9 obtains the moved state's `getShape` boxes and unions progress samples. This audit does not compare or assert equivalence of provider-specific shapes, pane connections, piston-head arms, or other moved states. The previously accepted pane and piston-head arm slices were not reopened.

## Scope limit

Accepted claim: for a client-ticking moving block entity in an in-border loaded chunk, with a non-null/intersecting moved-state response shape and a non-spectator player selected by the loaded-chunk entity query, the versions apply different direct response procedures. Ordinary player/map reachability for a particular moved state, movement after collision resolution, and pair-wide coverage remain open.
