# Independent audit: 1.11.2 pane neighbor selector

## Scope and inputs

Independent read-only audit of the registered 1.11.2 pane-neighbor eligibility selector and its current block-ID aliases. Source identity is 1.11.2 Ornithe Feather Gen2 (`feather-gen2-1.11.2+build.2`) and 26.2 unobfuscated. The exact old source tree is `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/1.11.2/ornithe-feather/`; current sources are under `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/26.2/unobfuscated/`.

This audit binds the supplied immutable artifacts:

| Artifact | SHA-256 |
|---|---|
| `pane-faceshape-overrides/legacy-1112-block-id-crosswalk.md` | `1BAC7F23D7DB8561099CB8DBC0350BEE73B39AC89FE738358EE8A348DD59B982` |
| `pane-full-alias-audit-r2/audit.md` | `CDA0060136C96785F3009A7918F5E82040A99814631EC82EAA633AC806BA32FF` |
| `pane-neighbor-1112/pane-neighbor-components-1.11.2.md` | `B65F0A121F394243B4A0D47A4C8326F44A964E32E37F98DFB8CA0985A5CE434A` |
| `pane-1112-selector-closure/slab-type-disambiguation.md` | `9F492B401C6DAB11A4A6DC00029D280952E17C4476909491B5C8DAC8048E68B8` |
| `pane-1112-selector-closure/slab-state-selector.json` | `760693743F81139CDC3EC82AC2A87A7E428D96E06040BA8B0AC69A6F54E6DED2` |
| `pane-1112-selector-closure/hash-manifest-r2.json` | `199060AC033092C6D7ABE6547E8534ED93A204639B887BF19C1E1C304B2B9B9D` |

The 1.11.2 ready/provenance/manifest artifacts are present and hash to `CEABFB266993567AAB8C867525365E18AA0A0D7BA5A2CB7B6CFCC83D21318C40`, `DCDB65E1361297A584C5B93F581B1B539911911C6CEC1A203740BAA5379732C6`, and `D908C2AF76598AD26999563BFEF0089F1D3C8C32483EEE6B108F0DEFA2773DF0`, respectively.

## Source rule and registry audit

1.11.2 `PaneBlock.resolveVirtualProperties` obtains the neighboring `Block` from each adjacent state and calls `shouldConnectTo(Block)` ( `net/minecraft/block/PaneBlock.java:109–113`). `shouldConnectTo` at lines 136–143 returns true for `block.defaultState().isCube()`, the same pane instance, `Blocks.GLASS`, `Blocks.STAINED_GLASS`, `Blocks.STAINED_GLASS_PANE`, or any `PaneBlock` subclass. `StateDefinition.State.isCube()` dispatches to `block.isCube(this)` (`net/minecraft/block/state/StateDefinition.java:273–274`); base `Block.isCube(BlockState)` returns true (`net/minecraft/block/Block.java:234–236`). The pane rule does not inspect the encountered neighbor state, material, face, or collision shape except insofar as the current block state supplies its block identity.

I independently extracted the numeric registrations and joined them to the crosswalk. There are 235 numeric registrations with string keys plus ID 0 through `AIR_KEY` (`Block.java:48,728`, `AIR_KEY = new Identifier("air")`). The crosswalk contains 236 unique IDs; every source ID and registry key matches exactly, with no missing or extra crosswalk ID. The crosswalk rows classify as 143 registrations whose effective `isCube` provider returns false, 85 registrations inheriting the base-true provider, and 8 slab registrations whose provider is `this.isDouble()`.

The 8 slab registrations resolve in source to four double classes returning true and four single classes returning false: IDs 43/44 (`DoubleStoneSlabBlock`/`SingleStoneSlabBlock`), 125/126 (`DoubleWoodenSlabBlock`/`SingleWoodenSlabBlock`), 181/182 (`DoubleRedSandstoneSlabBlock`/`SingleRedSandstoneSlabBlock`), and 204/205 (`PurpurSlabBlock.Double`/`.Single`). Their exact `isDouble()` implementations are constants in the corresponding class files. The old pane selector uses the registered block object’s default state, so these are class-identity outcomes, not a query of the encountered half/state.

## Current aliases and selector partition

The crosswalk has 406 unique current aliases. I checked every alias against the current `BlockIds` and `BlockItemIds` source declarations and the generated color keys: `createSimpleColored` prefixes `ColorCollection.NAMES`, whose 16 names come from the current `DyeColor` constants. No alias was unresolved. The 406 aliases partition into three disjoint groups:

| Old registered provider category | Old registrations | Unique current IDs | Historical pane result |
|---|---:|---:|---|
| Base `isCube == true` | 85 | 133 | Connect |
| Effective `isCube == false` | 143 | 257 | Does not connect, except explicit pane/glass clauses |
| `isCube == this.isDouble()` | 8 | 16 | Four double identities connect; four single identities do not |

There is no alias overlap among those three crosswalk categories. The explicit false-provider exceptions are exactly 35 IDs from old glass/pane registrations: `glass` (ID 20), all stained-glass colors (ID 95), iron bars (ID 101), glass pane (ID 102), and stained-glass-pane colors (ID 160). IDs 101, 102, and 160 use `PaneBlock` or its subclass; IDs 20 and 95 are admitted by the glass identity checks. The old `StainedGlassPaneBlock` extends `PaneBlock`.

The crosswalk therefore supplies a complete selector for registered old identities when used with its exact alias whitelist, plus the pane/glass exceptions and a state discriminator for the slab aliases. An ID-only set cannot distinguish the old double/single slab classes: each pair has identical current candidate IDs, jointly covering 16 unique IDs. The separately audited slab selector resolves them with current `SlabType`: `DOUBLE` maps to the four old double classes and `TOP`/`BOTTOM` map to the four old single classes. I independently matched its 16 IDs to the union of the eight slab crosswalk rows and rechecked the old class `isDouble` constants and current `TYPE` enum/default/shape source. Current 26.2 has one `SlabBlock` per material; `SlabBlock` sets its default `TYPE` to `BOTTOM` (`world/level/block/SlabBlock.java:43–46`) and exposes `TYPE == DOUBLE` as a separate state (`:49–65`). A product selector that needs to recover the old double class must use that state representation for only these 16 historical IDs; querying the unified current block object’s default state would report `BOTTOM` even for an encountered double slab. Other current-only slab IDs remain outside the old alias whitelist and must retain native behavior. This is a bounded identity mapping, not proof of arbitrary-state 1.11.2 geometry or raw legacy metadata conversion.

## Exact source fingerprints

| Source | Relevant member/range | SHA-256 |
|---|---|---|
| 1.11.2 `net/minecraft/block/PaneBlock.java` | neighbor resolution 109–113; `isCube` 127–129; `shouldConnectTo` 136–143 | `1E8968E7D642358CCE90F0D3156F07F19C5BCA28D2008D5D2D4BF0ECCC7266A9` |
| 1.11.2 `net/minecraft/block/Block.java` | base `isCube` 234–236; `AIR_KEY`/registration 48,728 | `AB873436B24487AB0EE8055BF7A478349C7F7978398AEA8E387796A6C820996A` |
| 1.11.2 `net/minecraft/block/state/StateDefinition.java` | state `isCube` dispatch 273–274 | `95704C944742EFB2EC9CBA7EB73399A330195556E669451131BA542E6A9FAC65` |
| 1.11.2 `net/minecraft/block/SlabBlock.java` | `isCube` 73–75 | `15955DB0D4E2607BDF4D90FF93876BD0D0875AF3A378100225FB99B7EBB6BC8D` |
| 1.11.2 `DoubleStoneSlabBlock.java` / `SingleStoneSlabBlock.java` | `isDouble` 5–7 | `134F7FAFBBC635B8367F1FBF0F0375E0EB95A387BC1D8667EF3C9160E899121F` / `3B3808A68671D1F559F20AC6CC71A367223D50E9C646DBF470F2DA3A503CCA98` |
| 1.11.2 `DoubleWoodenSlabBlock.java` / `SingleWoodenSlabBlock.java` | `isDouble` 5–7 | `7E00E9517411D8E8A6CE48A79203ECE8BBB64E10A9D1070C597A55EFB466E21A` / `367DBDD8FF807D45734D4074AC236DCDD8F7CEB75324EB4E9DAD0AC56B11D4D9` |
| 1.11.2 `DoubleRedSandstoneSlabBlock.java` / `SingleRedSandstoneSlabBlock.java` | `isDouble` 5–7 | `71DF9E432C231B3C6D0892A58957BBAD1B2ECE2DAA3526A1D330B9944824B203` / `80782D5BB0D60BC09E895EFA4F09BE5B321764F9E9F2DD7D2AD68AB192BB3B8A` |
| 1.11.2 `PurpurSlabBlock.java` | nested `Double`/`Single` 81–92 | `0A49F331E58CE2B75153DC36C38179EDE0A927A9E92E3F91F8BDC859BD304E28` |
| 1.11.2 `GlassBlock.java` / `StainedGlassBlock.java` / `StainedGlassPaneBlock.java` | `isCube`, inheritance | `554A0884BFEF7BC61B4A2535193037DFB0DE0E338227AE0335DBD6432EF62471` / `D38CF13B537D0F04163011020A1B815C7207D334582E6E61A50A4091964C8045` / `099E09540402B2BEE2F57B0D0D8B0E1703F6295CFA1B409387E12D9BACEA9AA7` |
| 26.2 `net/minecraft/references/BlockIds.java` / `BlockItemIds.java` | key names and colored key factories | `0F342F57727CFE99966DA9706DA7DDB330654CB59684529166B6F175171BA69F` / `D9D57F2F04801DA5D8D583C23B87B08F45D8B6C681980D1A77308753C801BAC6` |
| 26.2 `net/minecraft/world/level/block/ColorCollection.java` / `net/minecraft/world/item/DyeColor.java` | color key expansion and names | `717FB27A7B1D0F68B3C2F371408385AD84CCDCC1D76248338897D8CF59DB2BE2` / `9EE74711A37EDA938A4DEAA56828EEB6A46234D722BE1F720D240378B2DB0ABF` |
| 26.2 `net/minecraft/world/level/block/SlabBlock.java` | default TYPE and shape cases 43–65 | `FD5F80E73FD577605FF2924ADCCFCD7BA5037BB400E481CD142363A385C45BA1` |

## Additional 1.12 stair dependency check

The exact-source check requested during this audit is recorded separately in [`modern-stair-dependency.md`](modern-stair-dependency.md), SHA-256 `0C6506E022D7A7B922E1908D38FA05D96C8B7A96D28AE7BC011B5543CA6E4EE4`. The old class-only stair test and current `mud_brick_stairs` registration show why an unwhitelisted current stair encountered at an actually consulted front, back, or conditional side dependency should trigger native fallback under the bounded historical provider contract. The side dependency position is conditional and derived from the tested neighbor's facing.

## Verdict and limits

**ACCEPT, bounded selector source.** The old predicate, all 236 registry rows, current aliases, false providers, explicit pane/glass exceptions, and all four double/single slab pairs are source-supported. For implementation, the exact policy is the base-true alias set plus explicit pane/glass aliases plus `TYPE == DOUBLE` on the 16 old slab aliases; false aliases otherwise fail. Unknown/current-only blocks and unrepresented modded IDs remain native. Do not substitute modern face solidity or a modern block-wide `isCube` predicate.

The source closure is limited to registered 1.11.2 default-state provider selection and the stated old-to-current aliases. It does not claim arbitrary metadata/state migration, general historical geometry equivalence, player reachability for every neighbor arrangement, or runtime/tick parity. No product implementation, product history, backlog, build, tests, or runtime was inspected or used.
