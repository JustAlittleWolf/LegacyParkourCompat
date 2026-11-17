# Workbench and music block wall-face predicate slice

## Scope and source identity

This is a source-only comparison of the exact unexamined registry rows `minecraft:bookshelf`, `minecraft:crafting_table`, `minecraft:jukebox`, and `minecraft:note_block` from `wall_full_registry_owner_crosswalk/unexamined-wall-projection-keys.tsv`. It checks the vanilla wall-neighbor bit only. It does not establish a movement trajectory or freeze either release pair.

The exact endpoints are Ornithe Feather Gen2 build 2. Ready markers bind source manifests 1.13.2 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211` and 1.14.4 `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdbb5ea3c9f75bc`; the marker values match the corresponding `.sources.sha256` sidecar file hashes. The source files consulted are bound by those sidecars and listed below.

## Exact predicates and providers

At 1.13.2, the wall query reaches `BlockState.getFaceShape` (`BlockState.java:285–286`), which delegates to the block provider. `WallBlock.shouldConnectTo` (`WallBlock.java:51–55`) accepts a neighbor with `FaceShape.SOLID` unless `isExceptionForConnection` applies; the separate thick-pole/fence-gate branch does not apply to these four keys. `Block.getFaceShape` (`Block.java:383–385`) returns `FaceShape.SOLID`; none of the four registrations has a more-derived face-shape override. Old `Block.getShape` (`Block.java:388–390`) returns the full unit cube, and `Block.getCollisionShape` (`Block.java:393–395`) returns that shape when collision is enabled.

At 1.14.4, `WallBlock.m_71853799` (`WallBlock.java:48–51`) obtains its solid-face result through `BlockState.m_87223014` (`BlockState.java:326–327`), which delegates to `Block.isFaceSolid`. The wall then accepts that result unless `Block.isExceptionForAttachment` applies, with separate WALLS-tag and fence-gate arms. The target `Block.isFaceSolid` (`Block.java:402–408`) tests the collision shape's face for a full unit face. Target `Block.getShape` (`Block.java:371–373`) returns a full unit cube, and `Block.getCollisionShape` (`Block.java:376–378`) uses it when `hasCollision` is true. `Block.Properties.hasCollision` defaults true (`Block.java:811`); none of these target constructors calls `noCollision`.

The exact old registrations are in `Block.init`: `note_block` line 900, `bookshelf` line 983, `crafting_table` line 998, and `jukebox` line 1074. Target registrations are `Blocks.NOTE_BLOCK` line 211, `BOOKSHELF` line 391, `CRAFTING_TABLE` lines 419–421, and `JUKEBOX` line 572. Target bookshelf is directly a base `Block`; the other three retain their named class. None is a leaves/attachment exception, a wall-tag member, or a fence gate. Consequently, all four produce a true generic wall-neighbor bit at both endpoints.

## Registered state domains

| Key | Old states | Target states | Predicate result |
|---|---:|---:|---|
| `minecraft:bookshelf` | 1 | 1 | true → true |
| `minecraft:crafting_table` | 1 | 1 | true → true |
| `minecraft:jukebox` | `HAS_RECORD` 2 | `HAS_RECORD` 2 | true → true |
| `minecraft:note_block` | `INSTRUMENT` 10 × `POWERED` 2 × `NOTE` 25 = 500 | `INSTRUMENT` 16 × `POWERED` 2 × `NOTE` 25 = 800 | true → true |

The note-block instrument cardinalities follow the exact enum declarations in `Instrument.java` (ten values in 1.13.2, sixteen in 1.14.4); `Properties.NOTE` is 0–24 and `Properties.POWERED` is Boolean at both endpoints. The extra six target instrument values alter the state count, but the note block inherits the same full-cube shape/collision path for every state. Across the four keys, 504 old and 804 target registered key-state combinations all yield true on N/E/S/W (2,016 old and 3,216 target state-direction checks).

## Source hashes

SHA-256 values below were recomputed from canonical files and matched their exact endpoint source sidecars.

| Source path | 1.13.2 | 1.14.4 |
|---|---|---|
| `net/minecraft/block/Block.java` | `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4` | `1f8ec3628f08e196c2793e91ca0b94feacea6bc3939e7fb770f0682a6e66f2c5` |
| `net/minecraft/block/WallBlock.java` | `aeb67adf292a3b750e72a7d2fdc13f7b42f8633c6183964d00dfe8d6960e07bf` | `033da2cef0973f5d380a81b53ee76e23324fffece72edc74e12fcee0d8764be7` |
| `net/minecraft/block/state/BlockState.java` | `0496fda381628e90b3b3ff8ae376a5bae24cbd662366f34d70991252fab2fe70` | `6c6703c7c2f7203a7507d8a621bc83e1dad613b32089394f5af4fbd8636b165f` |
| `net/minecraft/block/BookshelfBlock.java` | `2522570fc04f4af776f32a806cdf978ee8f488e55c94d94d515d83add38d7517` | target registration is directly `Block` in `Blocks.java` |
| `net/minecraft/block/CraftingTableBlock.java` | `67d68a1efdc53188613e2627fb3cdd353668df752effac7efbb7a0a330e97aa3` | `273e77bd4a088f38f43968e3f711b08be71fd899cd77bd5523efab224ba7717d` |
| `net/minecraft/block/JukeboxBlock.java` | `01374fa0573e3baf830b570b3b99bf38b179fc989cb640bb794169f819fca1c1` | `602164cef02a29ee9cc2a63fd923993e0b0eec39e676c1f6b1494350a842ca83` |
| `net/minecraft/block/NoteBlock.java` | `e4b31df5b62b6c1d265e49e4f2b06b02175d3259637454f22e4a8f87ed1bc659` | `635a2ba92aaabdd059cef7d8b104559521522b14ad497682fb76552447adb9e9` |
| `net/minecraft/block/Blocks.java` (target registry) | n/a (old registrations in `Block.java`) | `9c0323497b31f8a284456e5692898897b92b6f0817d0371fae9a8afb54c638f8` |
| `net/minecraft/state/property/Properties.java` | `3882c1b832ac8b2b16f5a83978da441ab03cdfe66f053a873342758045827f4c` | `9b524af5065100919c60667778e38986380c9df954ef70a9d0ac0a739132ddca` |
| `net/minecraft/block/state/property/Instrument.java` | `2503fbbe7a99bd8a79156a8237af9773ca4fac552ecfba910652aba2e50ffdaa` | `6a7dfd13df47329dab12f46e0271d0ca11e7833a76d22fab1bc225c98ba254c7` |

## Result and limits

This bounded slice is no-difference for the wall-neighbor bit: all four keys remain true across their registered states and horizontal directions. The note-block state domain grows because its instrument enum adds six values, without changing this shape predicate. The result does not imply movement impact, broad provider closure, or full-pair completion. The parent crosswalk's r1 TSV overwrite limitation remains applicable to the wider inventory; this note makes no claim to repair it.


