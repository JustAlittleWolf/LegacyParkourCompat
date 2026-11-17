# Moving-piston entity response: client player path (r2)

## Immutable evidence identity

- Pair: Minecraft `1.8.9` → `1.9`; mapping family: Ornithe Feather; exact namespaces: `decompiled_minecraft/1.8.9/ornithe-feather/` and `decompiled_minecraft/1.9/ornithe-feather/`.
- 1.8.9 source sidecar `ornithe-feather.sources.sha256`: SHA-256 `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004`; provenance sidecar: `ba066fc8f1c7ceb9819d2106338d9e9f2c2b96eb5a8329eb40e171b1f9889185`.
- 1.9 mapping-local `.decompile-provenance.json`: SHA-256 `8daacb7af8f210e716ee4b34c43b8a5d6f6b4b19831d2fd725cbe16ecf330a1a`. All cited Java-file hashes below were rechecked against the corresponding release sidecar rows.
- The prior draft `tick-state/piston-entity-response.md` currently hashes to `A8B276FC3E4908A556DCEFF5B0CCED05E7E83B40803A4AD357812BD29D5DC9F7`. The dispatch supplied `1288399db66cec306eeaecf20e58a06052ded76e8014d0411753b58da96c774b`, which does not match the bytes on disk; this r2 binds the observed immutable file hash and does not modify that draft.

## Client-side execution and player selection

Both `ClientWorld` constructors pass `true` to the `World` constructor (`ClientWorld.java:48` in 1.8.9; `:57` in 1.9). The client loop calls `world.tickEntities()` when unpaused (`Minecraft.java:1635` and `:1446`). `World.tickEntities()` processes its ticking block-entity list and invokes `Tickable.tick()` for a non-removed block entity with a world in a loaded chunk inside the world border (`World.java:1159, 1240–1266` and `:1216, 1298–1325`). `MovingBlockEntity` implements `Tickable` in both releases. These paths contain no `world.isClient` guard around the moving-block entity's tick or its entity response. Thus the response runs client-side when such a moving block entity is present and passes the normal loaded-chunk and world-border gates.

The response calls `world.getEntities(null, box)` (`MovingBlockEntity.java:87` in 1.8.9; `:91–92` in 1.9). Both `World.getEntities` overloads use `EntityFilter.NOT_SPECTATOR` by default (`World.java:2134–2138` and `:2135–2139`); `WorldChunk.getEntities` includes non-excluded entities whose entity box intersects the query (`WorldChunk.java:710–729` and `:663–682`). Therefore a non-spectator local player is selected when its box intersects the moving response box and its chunk is available. In 1.9, the extra `getPistonMoveBehavior() != IGNORE` gate does not reject players: base `Entity.getPistonMoveBehavior()` returns `NORMAL` (`Entity.java:2246–2248`) and `PlayerEntity` does not override it. Spectator exclusion is shared by both releases.

## Response difference

In 1.8.9, `MovingBlockEntity.moveEntities(float progress, float amount)` transforms the sample to `1-progress` while extending, or `progress-1` while retracting, then requests one box from `MovingBlock.getCollisionShape(...)` (`MovingBlockEntity.java:80–89`; `MovingBlock.java:118–130, 162–197`). For each entity returned by the query, an extending moved slime block sets the facing-axis velocity to the facing offset and does not call `Entity.move`. Every other case calls `entity.move(amount * facingOffsetX, amount * facingOffsetY, amount * facingOffsetZ)` (`MovingBlockEntity.java:90–112`). On each nonterminal tick, progress increases by `0.5F` up to `1.0F`; the response is invoked only for extension, with amount `progress - lastProgress + 0.0625F`. On the terminal tick it is called once with `(1.0F, 0.25F)` regardless of extending/retracting, so this old response moves along `facing` even during retraction (`:128–149`).

In 1.9, `getShape(world,pos)` unions the moved-state shape sampled at `progress` and `lastProgress`; each sample is translated by `getAmountExtended(progress)`, which is `progress-1` for extension and `1-progress` for retraction (`MovingBlockEntity.java:75–88`). `moveEntities()` translates that union by the block position, queries entities, and chooses `facing` for extension or its opposite for retraction (`:90–96`). It skips `IGNORE` entities. For a moved slime block it first sets the chosen axis velocity to the direction offset, but it still proceeds to calculate displacement and call `entity.move` (`:97–123`). For other eligible entities it does the same displacement calculation without the velocity write. On every nonterminal tick, both extending and retracting blocks invoke this response after advancing progress by `0.5F`; it is invoked again at completion (`:164–182`).

The 1.9 displacement is calculated from overlap along the movement axis: for positive X, `box.maxX - entityBox.minX`; for negative X, `entityBox.maxX - box.minX`, with analogous Y/Z branches. It then adds `0.01` and calls `entity.move` along that signed direction; the two orthogonal components remain zero (`MovingBlockEntity.java:109–123`). This differs from the 1.8.9 fixed progress-derived amount and its extension/terminal call schedule. Retraction direction, extending-slime response, and repeated progress-tick response also differ.

For an ordinary player with `noClip == false`, the invoked `Entity.move` runs the world collision query and clips requested movement in Y, then X, then Z order in both releases (`Entity.java:371–459` in 1.8.9; `:438–529` in 1.9). Therefore these piston response calls enter the player's direct movement and collision path; no particular final displacement follows from the requested amount alone.

## Scope and unresolved dependencies

The supported source claim is conditional: if a client-side moving piston entity is ticking in an in-border loaded chunk, its response box has a non-null overlapping shape, and a non-spectator player is in that box, the two versions apply different direct response procedures. This report does not establish an ordinary map/tick witness, net trajectory or realized displacement after world collision clipping.

The moved-state shape dependency remains open. 1.8.9 asks for the moved block's `getCollisionShape`; 1.9 asks for its `getShape`. Provider-specific equivalence and resulting overlap are not asserted here. The already accepted pane connection and piston-head arm geometry packets were not opened; no claim here changes or broadens those frozen slices. Exact client population/order of a particular moving block entity and moved-state shape are left to the relevant source coverage lanes. This is a bounded source observation only, not an accepted movement finding or pair-freeze verdict.

## Cited source hashes (SHA-256)

| Release | Relative source path | SHA-256 |
| --- | --- | --- |
| 1.8.9 | `net/minecraft/block/entity/MovingBlockEntity.java` | `05A5BFBF2556EAA621996BDA365B7BAB01F5B9060CF773E7B9949EBE2F39FFF6` |
| 1.9 | `net/minecraft/block/entity/MovingBlockEntity.java` | `7AD3426BB74C9E69B4F521D20E045B1DC1EAFA9B725DAF0E2C0B9B718BC2683C` |
| 1.8.9 | `net/minecraft/block/MovingBlock.java` | `700010B071738738F04E0508050590D8D625F01115FC4886FEF6E262F85C6E85` |
| 1.9 | `net/minecraft/block/MovingBlock.java` | `A5B2584BC575DC888F1D32D9D4B683B91FC8B145EE079E66FF4722718FC8906F` |
| 1.8.9 | `net/minecraft/world/World.java` | `3C04F5B874FBB6692039164C882A3C47FE28C7ECA864E91C9AC57798153888EE` |
| 1.9 | `net/minecraft/world/World.java` | `D1E23DB2E20C86EB37B964A0DF6838F1422ADE7B0C2FFF9C869FF8345979AA98` |
| 1.8.9 | `net/minecraft/client/world/ClientWorld.java` | `710ABDE0F70C471A081ABDFB3B70DA681C6D817329801F8A2A49AF88A25EE02B` |
| 1.9 | `net/minecraft/client/world/ClientWorld.java` | `17322CE757CEF1D167A4E99E45FA98EBF8A134ADE80C9637AF657359269723A8` |
| 1.8.9 | `net/minecraft/client/Minecraft.java` | `1181BB145EB52ECBA28FCE3DB96FCA1E639C85012472D96EC0BA71BD02E15020` |
| 1.9 | `net/minecraft/client/Minecraft.java` | `85AE1E4910B90A1D3E5196427E1409300E3D62F60B83919E26E50EFF23CE5FFF` |
| 1.8.9 | `net/minecraft/entity/Entity.java` | `D4C10932CB5BB1067A5A58BE4E1BB1B42BDDE3B6FC893D88178CC07435A1696B` |
| 1.9 | `net/minecraft/entity/Entity.java` | `74D3AC34D7E6355C8FDCDD777DCD95BA9EF4626718C8772C1B58D0E28419E42A` |
| 1.8.9 | `net/minecraft/world/chunk/WorldChunk.java` | `A658562B5DF4A209E802D8BF25033C8399599A4D4652DDB012E32937E6CD4CA2` |
| 1.9 | `net/minecraft/world/chunk/WorldChunk.java` | `B98BCCF16277A5A62B09AC2E77DB7D77E098A62D8029C03A44FBED62E01FF267` |
| 1.8.9 | `net/minecraft/entity/EntityFilter.java` | `D97BE58E0B8E965E06A0B989685F156BE517D89D3B4D0F3154ADF7BD3612B6B9` |
| 1.9 | `net/minecraft/entity/EntityFilter.java` | `F45827FFEBAD59C151AA5EFA834824BA4D63BF60A645D14909DF5D41F4B6E440` |
| 1.8.9 | `net/minecraft/entity/living/player/PlayerEntity.java` | `E66CB294FC93118148A444BBAFDF4DD57CBF66A23D69B1E8892CEFCCC690AB88` |
| 1.9 | `net/minecraft/entity/living/player/PlayerEntity.java` | `EDBB46236D1E42630E600E4AFCB434EB6E9ADECBFC4AC7E15710A5DEA50010B1` |
