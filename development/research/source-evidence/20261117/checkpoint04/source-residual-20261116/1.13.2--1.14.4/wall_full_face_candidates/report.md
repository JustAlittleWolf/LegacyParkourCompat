# Wall full-face exception candidate closure

## Scope and immutable inputs

This is a source-only closure of the explicitly assigned remainder in `wall_generic_neighbor_inventory/report.md`. It compares only the six named old attachment-exception families (glass, glowstone, ice, sea lantern, conduit, cauldron) and the overlap between old registered wall blocks and the target `WALLS` tag. It does not re-extract resources and does not claim full pair coverage or pair freeze.

- Older source: Minecraft 1.13.2, Ornithe Feather Gen2; `ornithe-feather.sources.sha256` SHA-256 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`.
- Newer source: Minecraft 1.14.4, Ornithe Feather Gen2; `ornithe-feather.sources.sha256` SHA-256 `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdbb5ea3c9f75bc`.
- Prior inventory SHA-256: `1dc175827e5b8c7cbaecb20fc13dc2f55a8cf3450be8c76106ff855d56efcdbc`.
- Reused verified resource input: target `walls.json` SHA-256 `f20174f51f66d27681fd9d469a77c86cae82012c0d9125e7d246c426b772391c`; target `leaves.json` SHA-256 `c09bb9af30197720b232b9c84079039d4f344bfc2970fa0d02a1b43bc091b991`. Their extraction identity and payload hashes are in `wall_resource_membership/extraction-record.md`.

Source file hashes below match each release's `ornithe-feather.sources.sha256` entry (relative paths rooted at `net/minecraft/block/`).

| Source file | 1.13.2 SHA-256 | 1.14.4 SHA-256 |
|---|---|---|
| `Block.java` | `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4` | `1f8ec3628f08e196c2793e91ca0b94feacea6bc3939e7fb770f0682a6e66f2c5` |
| `Blocks.java` | `3e128c9a4f6b53e1037fb85269c4d00bc85112ae52fe48f9534232e13ee2a303` | `9c0323497b31f8a284456e5692898897b92b6f0817d0371fae9a8afb54c638f8` |
| `GlassBlock.java` | `5faf248e82edb63d4ed19766bff9e328d28b5adf8c6bb6447f23059ff45082ac` | `e4cd19886a974896ca997d5994cd83ea8faaae0255f52617a6f64f926c931185` |
| `TransparentBlock.java` | `392cbb712f2e3cc70b23f40b917ed837958c37173a72f3a6aedb3403769c07f6` | `392cbb712f2e3cc70b23f40b917ed837958c37173a72f3a6aedb3403769c07f6` |
| `IceBlock.java` | `8c0a9bed03f026be289f183114c6bc71b7e321d6ab4e915921cd2f51e3ce0cec` | `031de8fc5c554d672e8d46c9de5628651f7653893c5bd1127ca8e3125e303aed` |
| `ConduitBlock.java` | `66b110a324d9c3fe60eaa6595e59eab1b720a92a234307a8a77796b8b26a8eee` | `462f208e190b2364efc53f442b1861752272d28038696cf036786169bd1ecd70` |
| `CauldronBlock.java` | `6948b2e208e1d7a52123fece400aa717b6fa5c35189fff1137490a96c170a3ac` | `686519b540dcb313c5226ebae2f77e902eb1d5b76997ba8fdf8cd37ab772179e` |
| `WallBlock.java` | `aeb67adf292a3b750e72a7d2fdc13f7b42f8633c6183964d00dfe8d6960e07bf` | `033da2cef0973f5d380a81b53ee76e23324fffece72edc74e12fcee0d8764be7` |

## Comparison rule

1.13.2 `WallBlock.shouldConnectTo` accepts the generic arm only when the block is not `isExceptionForConnection` and the directional old `FaceShape` is `SOLID` (`WallBlock.java:51–56`). The old attachment chain expressly includes `CAULDRON`, `GLASS`, `GLOWSTONE`, `ICE`, `SEA_LANTERN`, and `CONDUIT` (`Block.java:241–256`); these are thus rejected regardless of old face shape. 1.14.4 computes the generic arm using `!isExceptionForAttachment(block) && Block.isFaceSolid(state, world, pos, face)`, plus its independent `BlockTags.WALLS` / fence-gate arm (`WallBlock.java:48–52`). The newer attachment exception list is only leaves, barrier, carved/lit pumpkins, melon, and pumpkin (`Block.java:260–267`). `isFaceSolid` tests the directional face of the state's collision shape and excludes leaves (`Block.java:402–408`). Default collision shape is the full block (`Block.java:370–378`); `GlassBlock` and `IceBlock` inherit `TransparentBlock`, which adds no shape override in either release.

## Exact candidate verdicts

| Candidate family / common key | Common states examined | Old 1.13.2 result | Target 1.14.4 result | Verdict |
|---|---|---|---|---|
| Glass — `minecraft:glass` | Its registered state (no state properties) | Old attachment exception rejects it. Its inherited old `getFaceShape` is `SOLID`, but that cannot pass the exception gate. | Registered as `GlassBlock`; it inherits the default full-block collision shape through `TransparentBlock`, is absent from `LEAVES`, and is no longer an attachment exception. Each horizontal side is face-solid. | **Difference:** old false → target true for each directional neighbor bit. |
| Glowstone — `minecraft:glowstone` | Its registered state (no state properties) | Old attachment exception rejects it. | Registered directly as `Block`; default full-block collision shape makes each horizontal face solid; not in the target attachment-exception list or leaves tag. | **Difference:** old false → target true for each directional neighbor bit. |
| Ice — `minecraft:ice` | Its registered state (no state properties) | Old attachment exception rejects it; old class does not override the default solid face shape. | Registered as `IceBlock`, inheriting the default full-block collision shape through `TransparentBlock`; not in leaves; each horizontal face is solid. | **Difference:** old false → target true for each directional neighbor bit. This is the `ice` key only; packed/blue/frosted ice were not named members of the old exception chain and are outside this residual slice. |
| Sea lantern — `minecraft:sea_lantern` | Its registered state (no state properties) | Old attachment exception rejects it. | Registered directly as `Block`; default full-block collision shape makes each horizontal face solid; not in leaves or target attachment exceptions. | **Difference:** old false → target true for each directional neighbor bit. |
| Cauldron — `minecraft:cauldron` | All four `LEVEL` values (`0..3`) | Old attachment exception rejects all levels. Old `CauldronBlock.getFaceShape` returns `SOLID` on horizontal faces (`CauldronBlock.java:234–239`), but the exception gate dominates. | The newer attachment list no longer rejects cauldrons. Its fixed collision `SHAPE` is the full block minus the bowl interior and base-volume pieces (`CauldronBlock.java:34–42`); at each horizontal outer boundary the interior begins inset by 2/16, leaving the entire outer face occupied. The shape is independent of `LEVEL`, so every level is face-solid on each horizontal side. | **Difference:** old false → target true for every level and horizontal direction. |
| Conduit — `minecraft:conduit` | Both `WATERLOGGED` values | Old attachment exception rejects it; `ConduitBlock.getFaceShape` returns `UNDEFINED` (`ConduitBlock.java:98–101`). | The target attachment list no longer rejects it, but `ConduitBlock.getShape` is the centered 5/16–11/16 cube (`ConduitBlock.java:28, 66–68`), independent of `WATERLOGGED`; its horizontal projected face is not a full block face. `Block.isFaceSolid` is false. | **No difference:** old false → target false for both states and every horizontal direction. |

Registration evidence: 1.13.2 `Block.java` registers glass at 892, ice at 1068, glowstone at 1080, cauldron at 1216, old walls at 1240–1241, sea lantern at 1396, and conduit at 1871. Target `Blocks.java` registers glass at 204, ice at 561, glowstone at 583, cauldron at 756, the two old walls at 796–797, sea lantern at 1099, and conduit at 1834. Target `BlockState` stores the collision-face cache per state (`BlockState.java:327, 416`, as enumerated in the prior inventory); cauldron's and conduit's listed state properties do not alter their shape functions.

## Old shared wall overlap with target `WALLS`

The exact reused target tag payload contains 14 IDs. Its intersection with 1.13.2 `Block.java` registrations is exactly `minecraft:cobblestone_wall` and `minecraft:mossy_cobblestone_wall`; these are both present in target `walls.json`. The remaining 12 target tag IDs (`brick_wall`, `prismarine_wall`, `red_sandstone_wall`, `mossy_stone_brick_wall`, `granite_wall`, `stone_brick_wall`, `nether_brick_wall`, `andesite_wall`, `red_nether_brick_wall`, `sandstone_wall`, `end_stone_brick_wall`, `diorite_wall`) have no 1.13.2 registration and are not common-key states.

Both old IDs instantiate `WallBlock` at 1.13.2 registration lines 1240–1241 and target `Blocks.java` lines 796–797. The old `getFaceShape` returns `MIDDLE_POLE_THICK` for every horizontal direction and is independent of `UP`, `NORTH`, `EAST`, `SOUTH`, `WEST`, and `WATERLOGGED` (`WallBlock.java:121–124`). That old pole arm connects. In 1.14.4, `BlockTags.WALLS` membership independently connects each target wall block through `bl2`, regardless of collision face (`WallBlock.java:48–52`); tag membership applies to every state. Therefore all common states of both IDs retain true horizontal neighbor bits: **no difference** across the common registered wall states. New target wall IDs do not participate in this common-key verdict.

## Closure result

Differences established in this assigned residual slice: `minecraft:glass`, `minecraft:glowstone`, `minecraft:ice`, `minecraft:sea_lantern`, and all four `minecraft:cauldron[level=0..3]` states, each for all four horizontal directions. No difference for either conduit waterlogged state or either old registered wall key. **Remaining keys in this assigned slice: none.** This closes only the six supplied exception-family candidates and old shared-wall tag overlap; it does not close other registered neighbor classes or assert pair freeze.
