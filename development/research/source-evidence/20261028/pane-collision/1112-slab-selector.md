# 1.11.2 pane slab selector disambiguation

## Relationship to the initial closure

This source-backed supplement supersedes the interim statement in `closure.md` that the 16 conflicting slab aliases must remain native solely because the ID crosswalk does not distinguish them. It does not alter the accepted 236-row/406-alias registry roster or claim arbitrary-state geometry equivalence. The needed pane predicate can be selected from the current slab state's explicit `SlabType` value.

## Exact old provider outputs

The accepted 1.11.2 crosswalk records eight slab IDs whose provider is `this.isDouble()`. Exact registrations and implementations establish four double/single class pairs:

- IDs 43/44 use `DoubleStoneSlabBlock`/`SingleStoneSlabBlock`; `isDouble()` returns true/false.
- IDs 125/126 use `DoubleWoodenSlabBlock`/`SingleWoodenSlabBlock`; `isDouble()` returns true/false.
- IDs 181/182 use `DoubleRedSandstoneSlabBlock`/`SingleRedSandstoneSlabBlock`; `isDouble()` returns true/false.
- IDs 204/205 use nested `PurpurSlabBlock.Double`/`.Single`; their overrides return true/false.

`SlabBlock.isCube(BlockState)` returns `this.isDouble()` (1.11.2 `SlabBlock.java:73–75`), so its provider output is determined by the registered old block class and is invariant across that block's states. The old `StoneSlabBlock`, `WoodenSlabBlock`, `RedSandstoneSlabBlock`, and `PurpurSlabBlock` source builds different state definitions/metadata forms for double versus single classes. The registered old identity, not the encountered half property, selects true or false.

## Current state discriminator

In 26.2:

- `SlabBlock.TYPE` is `BlockStateProperties.SLAB_TYPE` (`SlabBlock.java:33`).
- `SlabType` has exactly `TOP`, `BOTTOM`, and `DOUBLE` (`SlabType.java:5–8`).
- The constructor defaults to `BOTTOM` (`SlabBlock.java:43–46`), and `getShape` returns full shape for `DOUBLE` and half-height shapes for `TOP`/`BOTTOM` (`SlabBlock.java:59–64`).

For each of the 16 current IDs that alias both an old double and old single registration, use this bounded selector:

| Current slab type | Corresponding old registered class group | Old `defaultState().isCube()` | Pane eligible by generic cube branch |
|---|---|---:|---:|
| `DOUBLE` | `Double*SlabBlock` (IDs 43, 125, 181, 204) | true | yes |
| `TOP` | `Single*SlabBlock` (IDs 44, 126, 182, 205) | false | no |
| `BOTTOM` | `Single*SlabBlock` (IDs 44, 126, 182, 205) | false | no |

This follows both source class partitions and the current slab shape partition. For these aliases, the selector is `state.getValue(SlabBlock.TYPE) == SlabType.DOUBLE`; do not classify by block ID alone, current default state, or full-cube shape query. `TOP` and `BOTTOM` both select the old single-class result because the old `isCube` result is class-constant.

The 16 IDs are `minecraft:stone_slab`, `minecraft:sandstone_slab`, `minecraft:petrified_oak_slab`, `minecraft:cobblestone_slab`, `minecraft:brick_slab`, `minecraft:stone_brick_slab`, `minecraft:nether_brick_slab`, `minecraft:quartz_slab`, `minecraft:oak_slab`, `minecraft:spruce_slab`, `minecraft:birch_slab`, `minecraft:jungle_slab`, `minecraft:acacia_slab`, `minecraft:dark_oak_slab`, `minecraft:red_sandstone_slab`, and `minecraft:purpur_slab`.

This closes the eligibility selector for those current states; it does not claim raw legacy metadata conversion or all-state collision geometry equivalence. The crosswalk remains registry-ID-only. If a callsite cannot inspect the current slab type, native fallback is required for these aliases.

## Boundary and separate dependencies

This closes the selector conflict between the audited old registered-class partition and the 26.2 slab type partition. All other 1.11.2 crosswalk rows continue to use their source-backed class-level result. Current IDs outside the 406 audited aliases, modded IDs, and inputs without an audited old identity retain native behavior.

The policy is bounded to exact 1.11.2 evidence. It neither widens the profile range nor replaces the separate 1.12 FaceShape policy. No core version-resolution or profile-model change is implied.

The 1.11.2 pane predicate's stair registration is direct `isCube=false`; this selector does not query stair shape or neighboring stair states. Any secondary modern stair-shape dependency for 1.12 FaceShape belongs to the later policy and is separate.

## Exact evidence

- Corrected 1.11.2 ID crosswalk SHA-256 `1BAC7F23D7DB8561099CB8DBC0350BEE73B39AC89FE738358EE8A348DD59B982`; independent alias audit SHA-256 `CDA0060136C96785F3009A7918F5E82040A99814631EC82EAA633AC806BA32FF`.
- 1.11.2 `Block.java` registrations at lines 818–821, 1031–1034, 1151–1157, and 1255–1259; SHA-256 `AB873436B24487AB0EE8055BF7A478349C7F7978398AEA8E387796A6C820996A`.
- 1.11.2 `SlabBlock.java:73–75`; SHA-256 `15955DB0D4E2607BDF4D90FF93876BD0D0875AF3A378100225FB99B7EBB6BC8D`.
- 1.11.2 family state/metadata definitions: `StoneSlabBlock.java` `C9DB2D58FA430F83BFF769CF04C79FC29F08B6AAA10933EF03C2FAB4DB0FEF4D`; `WoodenSlabBlock.java` `1CD90938DC9996F3442027776E3E03A6878E2A8ACD0CF9C3DBD56BF38C546638`; `RedSandstoneSlabBlock.java` `658118961E31E62912694CC85EE42F2F339DFE420C1879DF8010B77D4B6D5377`; `PurpurSlabBlock.java` `0A49F331E58CE2B75153DC36C38179EDE0A927A9E92E3F91F8BDC859BD304E28`.
- 1.11.2 constant class results: `DoubleStoneSlabBlock.java` `134F7FAFBBC635B8367F1FBF0F0375E0EB95A387BC1D8667EF3C9160E899121F`; `SingleStoneSlabBlock.java` `3B3808A68671D1F559F20AC6CC71A367223D50E9C646DBF470F2DA3A503CCA98`; `DoubleWoodenSlabBlock.java` `7E00E9517411D8E8A6CE48A79203ECE8BBB64E10A9D1070C597A55EFB466E21A`; `SingleWoodenSlabBlock.java` `367DBDD8FF807D45734D4074AC236DCDD8F7CEB75324EB4E9DAD0AC56B11D4D9`; `DoubleRedSandstoneSlabBlock.java` `71DF9E432C231B3C6D0892A58957BBAD1B2ECE2DAA3526A1D330B9944824B203`; `SingleRedSandstoneSlabBlock.java` `80782D5BB0D60BC09E895EFA4F09BE5B321764F9E9F2DD7D2AD68AB192BB3B8A`.
- 26.2 `SlabBlock.java:33,43–46,59–64`; SHA-256 `FD5F80E73FD577605FF2924ADCCFCD7BA5037BB400E481CD142363A385C45BA1`.
- 26.2 `SlabType.java:5–8`; SHA-256 `E513C3BF499CE936576D6B5EF6062CAB2965BF536D87FD37E54E8961009EAB7D`.
- Row-level old IDs and aliases are in `selector-roster.json`; its hash manifest binds the original roster and source inputs. The 1.11.2 selector remains limited to the accepted 1.11.2 finding `pane-neighbor-components-1.11.2.md` SHA-256 `B65F0A121F394243B4A0D47A4C8326F44A964E32E37F98DFB8CA0985A5CE434A`.
