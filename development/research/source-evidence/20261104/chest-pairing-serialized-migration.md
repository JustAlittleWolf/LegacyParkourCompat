# Serialized chest pairing migration and chunk-upgrade reconstruction (1.12.2 → 1.13.2)

## Scope and permitted input

This is a bounded source-only supplement to the accepted `build/source-drafts/chest-pairing-state-coverage.md`, SHA-256 `05dc658dc7d7d473e6d468f0e1b1cc6e8a4cb2d17bd677610ad35b5e473e96e6`. That input explicitly left datafix migration and chunk-load re-pairing unaudited. This note traces legacy chest blockstate conversion, the generated upgrade data and its chunk post-processing consumer, plus trapped-chest block-entity relabeling. It does not revisit placement or collision geometry.

## Exact source identity

Both endpoints are local Ornithe Feather Gen2 build.2 sources. The ready marker, provenance and source-manifest hashes were checked for each endpoint; every cited file hash below matches that endpoint manifest.

| Release | Ready marker SHA-256 | Provenance SHA-256 | Source manifest SHA-256 |
|---|---|---|---|
| 1.12.2 | `b0aeec721e5c0af02b33d9e217cb8272889fa139ded1ecc2a31e42b930138f7a` | `747425a26da363d1abc8a2cecd83a74504aeb1f58be93aa53f48c690e35318ce` | `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da` |
| 1.13.2 | `1d1c644c1deb05c02d11dc575ad834f4f72489124701d9b2b90357f43dc37b38` | `e25950b7855fc4f2aee1a662f0be6b0af9f28788d9e27293075cfc5a3aa5d383` | `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211` |

Relevant 1.12.2 files: `Block.java` SHA-256 `e4a90eca411e7b0e14f5018f7385ec29891f6cdf1a1e917fa648b5712f9b33a1`; `ChestBlock.java` `cc63486b970a8a7670910b9d4e24c8de4b616dc53c9ce2f01260cfad53a5e178`.

Relevant 1.13.2 files: `DataFixes.java` `eb0e351b692e79b3dda9c04afe75fb242ba7d7ff9c789a04ee8b18ac13f6b1e8`; `C_36170522.java` `0f53d470214cfeaf6755215ff9c1635155030217c89b542c955b53c64ea4115a`; `C_80754635.java` `2aff1baacd621e5719ee221181cac4593e382a6fd1c3f0215f1a7bddbbb7330e`; `UpgradeData.java` `155002cd5fa86b7d69653e2c25a94181d1514db9ad51f38de2fa3af673d7cfe7`; `WorldChunk.java` `d800fac11aa2672a4dd69cb5a599e5f84bb6e2c71cfc8080fa972acdabfb4914`; `ChunkStatus.java` `5d2f23c40f994fe86c9ca1b9aaa0e6b4f5822de24f41980e4c36a75e8e174ea4`; `AnvilChunkStorage.java` `9037625793eb4b046d4c80ebf352d73a0b750f969dfaa1fc2a53b35467dc5031`; `ChestBlock.java` `483ed7dc673f8acb33fbac6d5d883c652db05de2314931c522e0bee482792541`; `TrappedChestBlock.java` `ce2db33ed4b4e5dec67d757ad0211b78c9f92d406bee833485961a3a3ff611e8`; `C_05024358.java` `2802a25a5b2dac698fdb8ce998983b2718bffec0e316aab568e56b2f82036ea5`; and `C_51231156.java` `d1a6fb5940c85b9c5bac45176a94fcb50781a02043f37aa41a438169f022cf0e`.

## What the migration sources do

### Normal chest

- In 1.12.2, `Block.java:865` registers normal chest as numeric block ID 54. Its `ChestBlock` blockstate contains only `FACING`; metadata conversion serializes the direction ID (`ChestBlock.java:450–471`). No pair side/type is serialized in that old blockstate.
- In 1.13.2, `DataFixes.java:228–233` registers schema 1451 and `C_36170522` as `ChunkPalettedStorageFix`. `C_36170522.C_41900556.m_99127153` reads legacy `Blocks`, `Data`, and optional `Add` arrays, combines each cell's ID and metadata as `Add << 12 | Blocks << 4 | Data`, converts the value with `C_80754635.m_46884624`, then writes `Palette`/`BlockStates` and removes the old arrays (`C_36170522.java:595–663`).
- `C_80754635.java:1604–1607` maps legacy normal-chest values `866–869` (ID 54 with horizontal metadata 2–5) to `minecraft:chest` with `facing` north/south/west/east and `type:single`. This aligns with the new `ChestBlock` default `TYPE=SINGLE` (`ChestBlock.java:53`).

### Trapped chest

- In 1.12.2, `Block.java:1086` registers trapped chest separately as ID 146 and `Type.TRAP`; it is a distinct block instance from ID 54.
- The same `ChunkPalettedStorageFix` recognizes IDs 54 and 146 as update-relevant (`C_36170522.java:467–468, 612–631`). `C_80754635.java:6033–6044` maps trapped-chest legacy values `2338–2341` (ID 146 with horizontal metadata 2–5) to `minecraft:trapped_chest`, with the corresponding facing and `type:single`.
- Separately, `DataFixes.java:419` registers `C_05024358` as the later “Trapped Chest fix”; it scans converted chunk palettes for `minecraft:trapped_chest`, gathers their local positions, and rewrites matching tile-entity IDs from `minecraft:chest` to `minecraft:trapped_chest` (`C_05024358.java:33–91`). If the found block entity ID is not `minecraft:chest`, the code warns and still changes the choice ID. This is a trapped chest block-entity identity repair; it does not set the chest pair `TYPE`.
- In 1.13.2, `TrappedChestBlock` subclasses `ChestBlock` (`TrappedChestBlock.java:15–17`) and inherits its pair-state behavior; the update fixer below independently gates pairing on exact block instance equality. Thus normal and trapped chests can each pair with the same kind, not with one another.

## Post-load pair reconstruction

- While converting each legacy section, `C_36170522.C_41900556.m_99127153` records interior positions of IDs 54 and 146 into its per-section `f_10804099` list; boundary positions set side bits instead (`C_36170522.java:615–631`). The chunk fixer writes these as `UpgradeData.Indices` and `UpgradeData.Sides` alongside converted `Sections` (`:945–966`). Therefore the initial migrated palette states are single chests, but candidate chest positions and boundary work are retained for the chunk upgrade.
- Old populated chunks are assigned `Status="mobs_spawned"` by `C_51231156` when `TerrainPopulated` is true and `LightPopulated` is absent or true; otherwise it assigns `empty` (`C_51231156.java:42–50`). `ChunkStatus` orders `MOBS_SPAWNED` before `FULL_CHUNK` and `POST_PROCESSED` (`ChunkStatus.java:44–48,104–113`). `AnvilChunkStorage` runs chunk datafixes for old `DataVersion` values before constructing chunks (`:94–105,164–201,455–515`).
- `WorldChunk.doPostProcessing` waits until the chunk has eight neighbors and its status is below `POST_PROCESSED`; it processes saved post-processing entries and then calls `upgradeData.upgrade(this)` (`WorldChunk.java:1063–1094`). `UpgradeData.upgrade` visits recorded interior indices and flagged edges (`UpgradeData.java:66–114,118–164`).
- The built-in `UpgradeData.CHEST` fixer is registered for both `Blocks.CHEST` and `Blocks.TRAPPED_CHEST`. For each updated block, it changes a `SINGLE` state only when the neighbor is the same block, horizontal, `SINGLE`, shares the same facing, and is on the perpendicular axis; it writes complementary `LEFT`/`RIGHT` states. For north/east facings it also swaps the two `ChestBlockEntity` contents (`UpgradeData.java:242–265`; exact file hash above). `Block.updateOrBreak(..., 18)` applies each returned state. This reconstructs pairing from old adjacency and facing during the chunk upgrade, rather than recovering an old persisted side value.

## Bounded conclusion and gaps

The inspected 1.13.2 migration path does include explicit post-load reconstruction for normal and trapped chest pairs. The palette conversion first yields single states, records legacy chest positions in `UpgradeData`, and the chunk-upgrade pass reconstructs complementary sides for eligible same-kind adjacent chests with matching facing. Trapped chests additionally receive a distinct block-entity ID repair. This is source evidence of the mechanism, not a claim that every malformed, multiply-adjacent, missing-neighbor, custom-ID, or otherwise irregular old layout is reconstructed; those cases do not satisfy the observed guard or were not traced. The upgrade call is gated by chunk status and the eight-neighbor post-processing path, so this note does not claim it runs immediately for every possible saved chunk. No actual save file or runtime migration was exercised.

This supplement closes only the previously open serialized migration and chunk-upgrade slice. It makes no collision-output, player-trajectory, or universal datafix compatibility claim.


