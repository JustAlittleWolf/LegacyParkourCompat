# COMPLETED — bounded stationary fixture collision audit

## Scope and verdict

Independent blind comparison of exact decompiled 1.21.11 Mojmap to 26.1 published unobfuscated sources, limited to stationary dry solid movement collision for cauldron fill variants, Hopper, Brewing Stand, all Anvil variants, Grindstone, Stonecutter, Lectern, Enchanting Table, and their ordinary non-riding entity/player block collision provider. No wiki, product implementation, product history, or another lane was consulted. No movement collision difference was found in this bounded slice.

| Family | State and registration coverage | Occupied movement collision | Cache/context/provider verdict |
|---|---|---|---|
| Cauldrons | `Blocks` registers `cauldron`, `water_cauldron`, `powder_snow_cauldron`, `lava_cauldron`; water and powder snow instantiate `LayeredCauldronBlock`, lava `LavaCauldronBlock`. Layered `LEVEL_CAULDRON` permits 1–3; empty/lava have no level selector. | Both sides use `AbstractCauldronBlock.SHAPE` for ordinary collision: same `Shapes.block()` minus the same rim/interior components. Layered fill-level `FILLED_SHAPES` are used only by `getEntityInsideCollisionShape`; lava similarly has a filled entity-inside shape. This does not feed the ordinary movement collision provider in the checked path. | `getShape` ignores state/context; inherited collision returns state shape. Static/cache path is unchanged. Fill-dependent entity-inside/effect behavior excluded by assignment. |
| Hopper | One `hopper` registration and `HopperBlock`; `FACING_HOPPER` permits DOWN/NORTH/EAST/SOUTH/WEST (not UP); `ENABLED` is Boolean. | Same base, inner cavity, and spout shape constructors and joins on both sides; `FACING` chooses rotated spout. | Precomputed map generated with `getShapeForEachState(..., ENABLED)`, which canonicalizes the ignored selector to its first value on both sides. Shape ignores context; same ordinary provider. |
| Brewing Stand | One `brewing_stand` registration; three Boolean bottle selectors `HAS_BOTTLE_0..2`. | Same fixed `Shapes.or(Block.column(2,2,14), Block.column(14,0,2))`, independent of bottle flags. | Fixed shape, context ignored; same ordinary provider. |
| Anvil variants | `anvil`, `chipped_anvil`, `damaged_anvil` each register `AnvilBlock`; shared horizontal `FACING` selector (N/E/S/W). | Same horizontal-axis rotated union of four column boxes, selected by `FACING` axis on each side. All three registration aliases share that implementation. | Fixed per-axis precomputed shape; context ignored; same ordinary provider. |
| Grindstone | One `grindstone` registration; `FACING` horizontal and `FACE` is FLOOR/WALL/CEILING. | Same plate and two leg box unions, transformed by `rotateAttachFace`; both `getShape` and explicit `getCollisionShape` return the same state-selected shape. | State table is precomputed for `FACE`+`FACING`; context ignored. Explicit collision override remains identical in behavior. |
| Stonecutter | One `stonecutter` registration; horizontal `FACING`. | Same fixed full-width column from y=0 to 9/16; facing does not change it. | Fixed shape, context ignored; same ordinary provider. |
| Lectern | One `lectern` registration; horizontal `FACING`, Boolean `POWERED` and `HAS_BOOK`. | Both versions explicitly return the same base collision union (full-width base 0–2/16 plus central column 2–14/16); directional book/board additions exist in outline `getShape`, but ordinary collision is explicitly the base union only. Collision shape ignores all three selectors. | Outline orientation table only uses `FACING`; explicit collision override is fixed and context-insensitive on both sides. |
| Enchanting Table | One `enchanting_table` registration; no state selectors. | Same fixed full-width column from y=0 to 12/16. | Fixed shape, context ignored; same ordinary provider. |

## Paired evidence

All paths below are relative to each exact source root. Paired line numbers identify the compared definitions and methods; source manifests and per-file hashes are recorded below.

| Evidence | 1.21.11 Mojmap | 26.1 unobfuscated | Finding |
|---|---|---|---|
| Registration aliases | `net/minecraft/world/level/block/Blocks.java:2617–2645,2892–2920,2965–2969,5478–5502` | `net/minecraft/world/level/block/Blocks.java:2634–2661,2906–2934,2979–2983,5490–5514` | Same resource IDs and constructors/classes for all assigned families; cauldron water/snow and anvil aliases preserved. |
| Cauldron shapes and selectors | `AbstractCauldronBlock.java:27–39,61–64`; `LayeredCauldronBlock.java:36–79`; `LavaCauldronBlock.java:17–44`; `BlockStateProperties.java:104–109` | same files: `AbstractCauldronBlock.java:27–39,69–72`; `LayeredCauldronBlock.java:36–79`; `LavaCauldronBlock.java:17–44`; `BlockStateProperties.java:107–112` | Same ordinary static cauldron shape and level domain 1–3. Fill-dependent entity-inside shape remains distinct from ordinary collision. |
| Hopper | `HopperBlock.java:40–75,152–155`; `BlockStateProperties.java:56–57` | `HopperBlock.java:40–75,156–159`; `BlockStateProperties.java:56–57` | Same selectors, construction, shape function, and state table treatment. |
| Brewing Stand | `BrewingStandBlock.java:32–47,59–62,97–100`; `BlockStateProperties.java:23–25` | `BrewingStandBlock.java:32–47,59–62,99–102`; `BlockStateProperties.java:23–25` | Same static shape and three Boolean aliases. |
| Anvils | `AnvilBlock.java:32–37,47–50,72–75,114–117`; `HorizontalDirectionalBlock.java:10–11`; `BlockStateProperties.java:53–57` | `AnvilBlock.java:32–37,47–50,76–79,120–123`; `HorizontalDirectionalBlock.java:10–11`; `BlockStateProperties.java:53–57` | Same box union, axis lookup, and horizontal state domain; all three registrations resolve to `AnvilBlock`. |
| Grindstone | `GrindstoneBlock.java:30–52,54–66,98–101`; `AttachFace.java:5–8` | `GrindstoneBlock.java:30–52,54–66,102–105`; `AttachFace.java:5–8` | Same three-state attachment domain, facing map and explicit collision override. |
| Stonecutter | `StonecutterBlock.java:27–41,63–70,83–86` | `StonecutterBlock.java:27–41,67–74,87–90` | Same one fixed collision shape; horizontal facing retained but unused by the shape. |
| Lectern | `LecternBlock.java:44–68,96–104,116–119` | `LecternBlock.java:44–68,96–104,120–123` | Same base collision shape, directional outline shape, and selectors. |
| Enchanting Table | `EnchantingTableBlock.java:30–36,57–60` | `EnchantingTableBlock.java:30–36,57–60` | Same single fixed collision shape. |
| Shape table generation | `Block.java:549–568` | `Block.java:588–610` | Both variants enumerate state definitions into immutable shape maps. The overload with ignored properties records first legal values, filters table states, and canonicalizes queried states before lookup. |
| Collision defaults/cache | `BlockBehaviour.java:272–326,643–657,895–927` | `BlockBehaviour.java:293–347,672–686,916–948` | `getShape` defaults to full cube; default collision uses `hasCollision ? state.getShape(...) : empty`. Cached collision shape is computed with empty context and empty getter. Same behavior. |
| Local player route and collision provider | `LocalPlayer.java:105`, `AbstractClientPlayer.java:26`, `Player.java:124`, `Avatar.java:12`, `LivingEntity.java:145,2398–2411`, `Entity.java:1044–1048,1096–1118`; `CollisionGetter.java:84–106`; `BlockCollisions.java:69–89`; `CollisionContext.java:24–31` | `LocalPlayer.java:108`, `AbstractClientPlayer.java:19`, `Player.java:125`, `Avatar.java:13`, `LivingEntity.java:142,2485–2498`, `Entity.java:1077–1080,1131–1158`; `CollisionGetter.java:84–105`; `BlockCollisions.java:81–101`; `CollisionContext.java:24–31` | LocalPlayer inherits through Avatar/LivingEntity to Entity; living movement calls `move(SELF, deltaMovement)`. `Entity.collide` asks for entity collisions on the swept box, then `collectColliders` includes ordinary block collisions. `CollisionGetter.getBlockCollisions` builds a block iterator with `CollisionContext.of(entity)`; `BlockCollisions` asks each state's collision shape with that context and tests overlap. This confirms the same ordinary non-riding provider route. |

`BlockStateProperties.HORIZONTAL_FACING` (`BlockStateProperties.java:57`) uses `Direction.Plane.HORIZONTAL`; `HorizontalDirectionalBlock.FACING` (`HorizontalDirectionalBlock.java:11`) aliases it. The horizontal domain is north/east/south/west. `AttachFace.java:5–8` lists floor/wall/ceiling.

## Source identity and intact-body checks

Version roots were present and had ready markers. Ready marker versions/mappings were `1.21.11/mojmap` and `26.1/unobfuscated`, Vineflower and Java 25. Their marker `sourceManifestSha256` values exactly matched SHA-256 of the adjacent source manifests:

- `1.21.11/mojmap.sources.sha256`: `0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555` (6,622 sources).
- `26.1/unobfuscated.sources.sha256`: `b77289c135ac6e12aa35e75177cea52d968f49d49e1ec745e510b1a786c9aeba` (6,882 sources).

The paired provenance records identify exact release IDs, mappings, source roots, run metadata and ready markers. Every source file used in the paired evidence above (all assigned block classes, `Blocks`, `Block`, `BlockBehaviour`, `BlockStateProperties`, `AttachFace`, `Direction`, `HorizontalDirectionalBlock`, `FaceAttachedHorizontalDirectionalBlock`, `CollisionGetter`, `BlockCollisions`, `Entity`, `CollisionContext`, `LocalPlayer`, `AbstractClientPlayer`, `Player`, `Avatar`, and `LivingEntity`) was SHA-256 checked against its corresponding source-manifest entry. Each matched; therefore the inspected method bodies match the published source manifests.

The checked individual source hashes are:

| Relative source path | 1.21.11 Mojmap SHA-256 | 26.1 unobfuscated SHA-256 |
|---|---|---|
| `net/minecraft/world/level/block/AbstractCauldronBlock.java` | `7d884918d7596eb2fa0c5f799aa64fecaf9711fd6817e4a18953289c8b7d17ca` | `a4e10cccf88b3c0d1cc64f33a06d8ad39c28f6b823483b910e71b9bd16191492` |
| `net/minecraft/world/level/block/CauldronBlock.java` | `3a5855ac36738873cb97b9e926e037a7231ff2b6b29bd86cfe2b89e812b7fe9c` | `63d0a3da8b0198e2cec13384c16a7d7751f147b6a009784fd45a6807a9e17456` |
| `net/minecraft/world/level/block/LayeredCauldronBlock.java` | `ed031957418f8ca524cd75841c489379f8fba6a783f023f24a57819fb33613df` | `18be2624a0c45b6f807627a0f0febabc48a9a7092dc918411e94b49276976c2d` |
| `net/minecraft/world/level/block/LavaCauldronBlock.java` | `1aee2e6d7e1791861a069ac894a113c7c1ec99f9b526955cbe244375dee7619b` | `979d958a18b76671c53cadf2a2b669a2a268a53008345a5d6b8d736e0b3584c5` |
| `net/minecraft/world/level/block/HopperBlock.java` | `92b86ff292b1e5841dfd5497e2f6fe7469691bd69d029ad90016832678edf6b8` | `7a4e5afa06b90cac7800ab0984799d7933dea9954373653ac43c4697f075b1b5` |
| `net/minecraft/world/level/block/BrewingStandBlock.java` | `e7ea5251e00f4f7efc35bbd46fa894839d7c4459f78d830a20892a675cfd725d` | `08fbaa68f9b16510e7ff4585526e63043cac54eb476e222c11c13d8da404602f` |
| `net/minecraft/world/level/block/AnvilBlock.java` | `3e35a8dab98d72705b48197fbb3d44f579878efd44b09c7673d1d4965325f3f9` | `a15b5ef5067f098a2e6b83d3b934730f376a3420a4ce47c798a6a80d800b9391` |
| `net/minecraft/world/level/block/GrindstoneBlock.java` | `5c36c9c9ccf46321f88ead2e4c0d28ef9bf9ef326962b3794b11cc49199154d1` | `ffe4dd67c9b3c6ef2ed81b7baab0075aade78a92b9c7676cfa5d9ef79d763d73` |
| `net/minecraft/world/level/block/StonecutterBlock.java` | `fd86cc9b99999da3b28cb12aeb0480b37921590f9523726b7cfdd1cb7da86516` | `6fbb26aa6b535fe62ca25688c31f55635c3b1dd2c8038c7fdb7ed9b9bca61ab2` |
| `net/minecraft/world/level/block/LecternBlock.java` | `a8ec4304108ba2b4f02b5e1624061bb2b9b6b8b8a057a7f337afead670065996` | `a59665b6eba0ea0a87b20b05255a67d281102e312d99e63bfb041518b0a18949` |
| `net/minecraft/world/level/block/EnchantingTableBlock.java` | `cc0c51d77ade904ff44b3672766101269b72409836ed9c524a434f5cd38603a7` | `97c1f127b812ef20d375f53dcbff9afc09528e3dcb76823ca24ef2437cd5f852` |
| `net/minecraft/world/level/block/Blocks.java` | `cc6636f6ace603b32edd3db4c0cfcda60be08e2c6b5def783a698f105b8f7f23` | `ba8a258b33f73fe03f93e7b02f9c25d4f66cf3aaab04c4580d0863cc71bc866f` |
| `net/minecraft/world/level/block/Block.java` | `bd7f69ffe617fa1008c9311ad7c731b23f242acb5ff3bab8c1a81e41e7d15a73` | `1693cfb7b84190a2fe664470a56d59e78ed5bd7d722a2bd16888b036d4d8e977` |
| `net/minecraft/world/level/block/state/BlockBehaviour.java` | `cf7eff07efb53d95c8ffa633ff38c7d3f454e185f197de49eb4da279a8da9d17` | `9db85de84e502903e6fe497f043b58620b92089236ffb213f0b93db979428d13` |
| `net/minecraft/world/level/block/state/properties/BlockStateProperties.java` | `813590dfdd6df26314475c49794ee0bc5d4f73417c52fe8c01946a4411359d9b` | `0318b21d703f6a521d5ea4c5a74494097b9b8776c2f43a3084ad7569172c8a27` |
| `net/minecraft/world/level/block/state/properties/AttachFace.java` | `7ebaa4a9414d2e812ac846ea78d040c4dbde762554acd54c28ca58510a47bbec` | `e046cfdae9d24ddaa0c4fb7ded51ea258e1f58052118cb2a665d42fbd8bf9898` |
| `net/minecraft/core/Direction.java` | `eaefe276ef6732d1f99c6d8268458714d1f84a37dd24654e238a710cc707b5be` | `30fb61592f1a154bb8fc239ab8df1cd2b209f63317e0c7dde4d5b9b3e260d754` |
| `net/minecraft/world/level/block/HorizontalDirectionalBlock.java` | `485b628cce2e702b1d5be9e2c0839787c2881dbc8edab76f464725803e482475` | `7456a35ed53ecc1d7f24f01b1a9b7585d0bc1d512c026ebb398322abb54f811d` |
| `net/minecraft/world/level/block/FaceAttachedHorizontalDirectionalBlock.java` | `856d63b9c568e2ab567ee7a8c39148cdab4d567af027b9411e6d2480c59ccc24` | `8111162412a2f0b9ed69668870fc3ad6a03e962fab36bc6b5748c1fb76962e01` |
| `net/minecraft/world/level/CollisionGetter.java` | `ca2a5c4561fa8b263d7ce8513a152f9c1d6537f4f011859351ce58b39a7f4e70` | `dddff4897e8d5d01e3ac132d85c474e6a3b9bae928e48b8c907aa9eabdb7fb01` |
| `net/minecraft/world/level/BlockCollisions.java` | `05875bb3a6fa49628ac92174e68ec3b5bed1cf39ebd4e7158b1885aead0a6a68` | `eb8a8f6f07b1d6384f17987818f056d41adfd6b2ad120d26774bfa550f4fc51e` |
| `net/minecraft/world/entity/Entity.java` | `32314478c6036fa9f3f3cc409c61c622eefc5a282d60e1011a1a33cc29cf18a3` | `8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf` |
| `net/minecraft/world/phys/shapes/CollisionContext.java` | `6f1ade5fa21f38d217a6c766ac34a4d2ddf54798320486ea33a7f676abfdc640` | `8702d23bea0f119e0d63ce15ff59088559a319283186372fd69adebeed4dcd69` |
| `net/minecraft/client/player/LocalPlayer.java` | `948e94f8e874b2f72e9a689e6e3ba1933f5728ec381d64f3bb5e2388718bd477` | `433fd995ad317af0f6ef0e50c1e8e3483cb8f00e0e327d4edf27a4dd99666ebe` |
| `net/minecraft/client/player/AbstractClientPlayer.java` | `afe84ab1491a17aeeccf895ca4ecb17e941b8e517c980e31281b3d463fc593bd` | `7825a8d4e8e24928cba18f68109c91479b78fb14fcd8b81f0e045b8d02a559cc` |
| `net/minecraft/world/entity/player/Player.java` | `8e97167350a91741d0aa10d3b0d92a33150ed6dccdc94cd5b37d9c7ca22bcc81` | `44cf28e0c64e78d39fd13368e9991381dbebab67029070cb9ddc43f09d45d14d` |
| `net/minecraft/world/entity/Avatar.java` | `a3f54b9ff81ee203f77efcf5dcab50d69ec9a3d2c5430ce4c4b2ae0abb4bfca9` | `01a8092bc2637e7d42a261fb564bffcabadd28625cc02b120e943559e2d4fe78` |
| `net/minecraft/world/entity/LivingEntity.java` | `19ed2d565858c401c69a06750b054a633a20ea864cab3a753b5c767f0bdd60e8` | `c3b64de8dbaba8ad8a7ccd4f33255346d8206e66f12ca91260971bf5e5ac93bd` |

## Remaining gates

This is an independent bounded slice verdict only. It does not close the whole 1.21.11→26.1 movement pair, other families, intermediate patch boundaries, implementation acceptance, compilation, or runtime parity. Static audit only; no implementation, tests, or game launch.
