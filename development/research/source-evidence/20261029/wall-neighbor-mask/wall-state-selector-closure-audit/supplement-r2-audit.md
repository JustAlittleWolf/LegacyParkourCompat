# Independent R2 audit: source-closed wall selector

## Verdict: ACCEPT (source-level contract only)

I accept `.native-source/20261029/wall-state-selector-closure/supplement-r2.md`, SHA-256 `3fc036aafd62b848e4f4cc3e60d85c26ad1142fff5b7296a5706eafefcdb9406`, as a source-level selector contract. The target identities are exactly the two mapped current wall keys with `waterlogged=false`; current `UP` and wall-side values do not enter the described operation. For each of the four sides, the admitted neighbor key set is exactly current air plus the 14 old stair keys, and every admitted neighbor yields old 1.11.2 false. Recomputing four booleans and using the old 16-slot collision index is source-feasible without converting a live 26.2 wall `BlockState` to a legacy state.

This verdict does not certify an implementation. R2 explicitly leaves product behavior unverified; I did not inspect product code or Git history.

## Identity and mapping verification

- 1.11.2 and 1.12.2 `Block.java` each register numeric ID 139 as one `WallBlock`; each `WallBlock` metadata decoder maps 0/1 to `NORMAL`/`MOSSY`. The corresponding old class variants are correct for the exact identity keys.
- 26.2 `BlockStateData.java` rows 2224/2225 store `minecraft:cobblestone_wall` and `minecraft:mossy_cobblestone_wall` as their target names, with the corresponding old aliases distinguished by `variant=cobblestone` or `variant=mossy_cobblestone`. Its `register(id,target,legacy...)` stores the target and binds each alias. `Blocks.java:2699–2703` registers both exact current keys as `WallBlock` instances.
- The cited 14-stair list was previously independently checked against both old `Block.java` registrations and 26.2 mappings in the bound supplement `supplement.md` (SHA-256 below). Each old ID constructs `StairsBlock`; the 26.2 table aliases the old facing/half/shape combinations to its exact current target key. For this operation's 1.11 predicate, the old stair selector is identity-only because `StairsBlock.isCube` returns literal false and the concrete state delegates to that method.
- `minecraft:air` is the exact current property-free air state. Old ID 0 is AirBlock; its old predicate is false and 1.12 `AirBlock.getFaceShape` is undefined for every direction.

## Current target property boundary

I verified the current wall declarations and conversion sources against the 26.2 source manifest:

- `WallSide` has exactly `NONE("none")`, `LOW("low")`, `TALL("tall")`. `WallBlock` uses Boolean `UP` and `WATERLOGGED`, four `EnumProperty<WallSide>` sides, and includes them in its state definition. The default state is `UP=true`, all sides `NONE`, `WATERLOGGED=false`; wall updates can produce all three side values.
- `DataFixers.java:847–848` installs `WallPropertyFix` at schema 2503. `WallPropertyFix` applies to both admitted current wall keys and maps a legacy side string `true` to `low`, other boolean strings to `none`, for east/west/north/south. It does not change `UP` or `WATERLOGGED`, and does not produce `tall`.
- The old wall mapping target records omit `waterlogged`; the current WallBlock default is false. Therefore restricting the live target selector to `waterlogged=false` is source-supported. The R2 contract correctly does not claim that every admitted current wall-side combination is itself a legacy migration output.

The source distinction is sound: old `resolveVirtualProperties` obtains N/E/S/W booleans from neighbor predicates and writes them onto its legacy state; old `getShapeIndex` reads only those four booleans; the collision caller indexes the 16-entry collision array with that index. `UP`, `VARIANT`, and live current target-side values do not affect that operation. A direct historical mask calculation can therefore avoid both converting a current `WallSide` enum back to boolean and using the current wall's computed sides. The R2 text states this as a source-level requirement and explicitly does not assert the product does it.

The neighbor domain is complete for the stated operation: old wall predicate false for air; old stairs are not the wall-self/fence-gate special cases and have `isCube=false` for every state. Thus each of N/E/S/W is freshly computed false for every admitted tuple. The stated 1.12 oak-stair witness remains the expected false-positive arm; it does not change the 1.11.2 zero-mask result. Whole-shape native fallback remains specified for all unadmitted target or neighbor states.

## Bound hashes

The R2 report hash above was recomputed. Its parents are: first supplement `ffcfd9626bb56acaa3ec572194318e8b471f2c706389930488410d24d44adc5c`; original report `0d82feadde804ac8035114421520723169095527852e1e26a34013a45a6c988a`; prior independent supplement audit `ad6512fa9262f93fec4e9759e97d9e36b2601ff57cf636bb8b4c94e2f9be3f34`.

Source manifest hashes match ready metadata: 1.11.2 Ornithe Feather `d908c2af76598ad26999563bfef0089f1d3c8c32483eee6b108f0defa2773df0`; 1.12.2 Ornithe Feather `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`; 26.2 unobfuscated `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894`.

Relevant exact 26.2 files, each recomputed and matched to the manifest: `BlockStateData.java` `6196f9039890d5f67b59cb372ec06da987e8a5ea80b49fd8bee9eeca0a1784a4`; `Blocks.java` `f3f2faeed23e9697407069a1d523107491590b8710175523ea05294d5bd00435`; `WallBlock.java` `b2bcd69d81e5a19a21eb50e7e5ac259269aca3871cec5899caef03ff4507a9c7`; `WallSide.java` `1a93c26666f20c844aa6b68006043542a732627ce23e59bc8e81b638e665adc3`; `WallPropertyFix.java` `fe07247694564755b89d260cd272d696066ae73e952b9e3b65a9594048484b4e`; `DataFixers.java` `4a7de861c83f395255d6045d5f9fb98d932abd42724e54316adc59116ee6fb50`.

Old exact source hashes relevant to identity/predicate: 1.11.2 `Block.java` `ab873436b24487ab0ee8055bf7a478349c7f7978398aea8e387796a6c820996a`, `WallBlock.java` `17da6d881365e4dce5134f88cb9a9e5273d1fa1734812d13410dc6924a3acbfd`, `StairsBlock.java` `5df1782da6eb7acdb9935b3910a0484d6417661bfd6230ecf81df0de6d782627`; 1.12.2 `Block.java` `e4a90eca411e7b0e14f5018f7385ec29891f6cdf1a1e917fa648b5712f9b33a1`, `WallBlock.java` `7e4378c46e367c32deca14475e5161b6e4223947d83153ce3e7ba5c8a0d18e50`, `StairsBlock.java` `c52a011a759b6f3ef6d45162d3b61f464ceceee5d2d8735de6f6e25a56e55611`, `AirBlock.java` `ac009eaac5edc3693f5827dfe330ec4f36749d51814e8901efdef95480043202`.

Prior bounded input hashes: 20261023 classifier `feb9b3850bfeae923ed2b7dbb3d455e53d0ed06398de599e4fa9b543d99103fa`; flattening mappings `af7c95fdab33b37fd8eefa8814fb26aa6030880979be241982a4187a929a69fa`; prior whole-mask contract `44be2ea9aad699051e9e40e915910e8cdf973b7052125d585b3883adbdc8269f`.

No product source, product history, backlog, build, tests, game, or runtime was inspected.
