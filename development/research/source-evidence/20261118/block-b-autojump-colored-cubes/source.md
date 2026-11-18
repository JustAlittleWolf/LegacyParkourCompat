# AutoJump dye alias and provider source audit

Status: **PASS — no failing named variants found for the existing aggregate-vs-per-block provider-equivalence gate.** Scope is the supplied projection only: loaded, interior, entity-free, border-free queries; replacing an admitted white wool/concrete cell by one of its other 15 color variants must leave the candidate stream and clearance reads unchanged when those outputs depend on the block collision provider. This is source-only, not a complete AutoJump review or runtime parity claim.

## Result

For each historical source tree (1.13, 1.13.1, 1.13.2, 1.14, 1.14.1), all 16 wool IDs are independently registered as bare `Block` objects with `Material.WOOL`, and all 16 concrete IDs are independently registered as bare `Block` objects with `Material.STONE`. Hue changes only the map/dye color argument. The color family changes strength/sound, but those are uniform within each family. No color-specific subclass or color-sensitive shape override is involved.

The shared base block shape implementation returns the same full-cube shape singleton for every one of these blocks; its collision-shape method returns the state shape when the common collision flag is true. The legacy default shape ignores its world/position/context inputs, and 1.14.1 Yarn uses its default full-cube outline shape. Consequently these variants have the same collision provider flags, shape singleton, and per-position clearance contribution. There is no provider-selected candidate-order difference from substituting a dye color. Each color still has a distinct block registry identity and state; this conclusion does not extend to arbitrary identity predicates outside the collision-provider gate.

The light-gray registry spelling is `light_gray_*`, while the historical dye/map-color alias is `SILVER` in Feather sources (1.14.1 Yarn exposes the corresponding obfuscated `DyeColor.field_7967`). That alias changes only the color argument. No color ID is missing or aliased to the white block.

The 26.2 source establishes **raw-state representability only**: `BlockItemIds` creates the 16 `*_wool` and 16 `*_concrete` identifiers from the 16-color collection; modern implementation/provider behavior was not inspected. No specific variant failed representability.

Variants checked independently (wool and concrete): orange, magenta, light_blue, yellow, lime, pink, gray, light_gray, cyan, purple, blue, brown, green, red, black. **Failed variants: none.**

## Historical source references

- 1.13 Feather: `decompiled_minecraft/1.13/ornithe-feather/net/minecraft/block/Block.java`, wool registration lines 954–969 and concrete 1694–1709; default shape/collision methods lines 404–413.
- 1.13.1 and 1.13.2 Feather: `.../net/minecraft/block/Block.java`, wool lines 935–950, concrete lines 1675–1690; shape/collision methods lines 388–397. The 1.13.1 and 1.13.2 `Block.java` and `Blocks.java` byte hashes match exactly.
- 1.14 Feather: `decompiled_minecraft/1.14/ornithe-feather/net/minecraft/block/Blocks.java`, wool lines 285–332 and concrete lines 1551–1570; `Block.java` default shape/collision lines 360–367.
- 1.14.1 Yarn: `decompiled_minecraft/1.14.1/yarn/net/minecraft/block/Blocks.java`, wool lines 296–341 and concrete lines 1606–1621; `Block.java` default outline/collision shape lines 371–376.
- 1.13.2/1.13.1 Feather: `.../block/shape/VoxelShapes.java` lines 20–31 holds and returns static `BLOCK`; 1.13 Feather has corresponding singleton lines 20–31. 1.14 Feather: same path lines 25–47 holds/returns static `BLOCK`. 1.14.1 Yarn: `.../util/shape/VoxelShapes.java` lines 25–47 holds/returns static `FULL_CUBE` via `fullCube()`. This pins the shared full-cube object used by the generic block shape methods.
- 26.2 unobfuscated: `net/minecraft/references/BlockItemIds.java` lines 750–760 and its `createSimpleColored`/`prefixWithColor` definitions; `net/minecraft/world/level/block/ColorCollection.java` lines 15–32 establishes the 16 dye entries and shared registration; `net/minecraft/world/level/block/Blocks.java` lines 833–842 and 3895–3904 establishes generated raw block IDs for wool and concrete. This is identity coverage only.

## Exact source identities and hashes

All paths are relative to `D:/Javastuff/LegacyParkourCompat`. Provenance files and generated source trees were read-only. Provenance JSON file SHA-256 pins each source publication record; source hashes pin the relevant Java classes.

| Input | Mapping provenance | Provenance SHA-256 |
|---|---|---|
| 1.13 | Feather gen2 `1.13+build.2`; run `prep-1.13-1.13.1-feather-2026-10-09-r1` | `34652AEEF6914B5CBFF6619AE60A855AA676B2AE1F607ECCDAA623AD09DC5E8D` |
| 1.13.1 | Feather gen2 `1.13.1+build.2`; same run | `C28ED59F2C507B6133F7677742D316D38E9993EAF1FBB6C2054BD68D1085E904` |
| 1.13.2 | Feather gen2 `1.13.2+build.2` | `E25950B7855FC4F2AEE1A662F0BE6B0AF9F28788D9E27293075CFC5A3AA5D383` |
| 1.14 | Feather gen2 `1.14+build.2`; run `prep-1.14-exact-ornithe-feather-2026-10-09-r1` | `BFB011801A64E9AFAA4ACA43E8A8ED40BF2A02F136FFC02E65B32E79315764FA` |
| 1.14.1 | Yarn build.10; `.decompile-provenance.json` identity | `308537C5ADEC3104C5CA243EB5010554F00978635ED74F0D7801C804DD4D4907` |
| 26.2 | Unobfuscated | `A328F5333A1B27AADDE4422B23159A323AE219CCD26F11207C1A5461366F27D1` |

Relevant source SHA-256 values:

| Source file | SHA-256 |
|---|---|
| `1.13/.../block/Block.java` | `E5BE72B77A28E89595009289964BB031BBB187D15D5698570615E6FC9A21A476` |
| `1.13/.../block/Blocks.java` | `8E66B1AD391D61F4E42FECB313F99299810493CB87525493F9ACE8A1EB7CD000` |
| `1.13.1/.../block/Block.java` | `735030E8BB5FC7EAD2D6DBCB1B262A36A414B4349DAF197456337F460B444CD4` |
| `1.13.1/.../block/Blocks.java` | `3E128C9A4F6B53E1037FB85269C4D00BC85112AE52FE48F9534232E13EE2A303` |
| `1.13.2/.../block/Block.java` | `735030E8BB5FC7EAD2D6DBCB1B262A36A414B4349DAF197456337F460B444CD4` |
| `1.13.2/.../block/Blocks.java` | `3E128C9A4F6B53E1037FB85269C4D00BC85112AE52FE48F9534232E13EE2A303` |
| `1.14/.../block/Blocks.java` | `B568C300287713C9B5BA59C6382F60014830163008FCD3613FBE54373ED43042` |
| `1.14/.../block/Block.java` | `0308B22D47062863EE36DB68B907D42C7A95E060D5841F4D6E9CF67EDA66FD6A` |
| `1.14.1/.../block/Blocks.java` | `F5BA7169237468C68C085ECBF101E0E1C620013C00744A20DFAE7C8AF97C74DD` |
| `1.14.1/.../block/Block.java` | `F5EF3EC7CECD508D948AC5F4A09CE04A4882DA000E70A518A1882A00946D56FE` |
| `1.13/.../block/shape/VoxelShapes.java` | `6AE1A6FA0AB76098252208FAD00A6D4F3D3F1CF2023D76F63D450855DF00A330` |
| `1.13.1` and `1.13.2/.../block/shape/VoxelShapes.java` | `313968D4E5855B5EC380272B6ABABCC5849D478AB771926355912A8CB6AA9C95` |
| `1.14/.../block/shape/VoxelShapes.java` | `EA5E88613C93C6D15B11A9CE8AFCEF4681875CD4113D51282B2DC26BD8D4BE71` |
| `1.14.1/.../util/shape/VoxelShapes.java` | `6B04957D569CA67C9A5EA74B901BAA870DB2490BA2097C7AD6FBBC360991D61A` |
| `26.2/.../references/BlockItemIds.java` | `D9D57F2F04801DA5D8D583C23B87B08F45D8B6C681980D1A77308753C801BAC6` |
| `26.2/.../block/Blocks.java` | `F3F2FAEED23E9697407069A1D523107491590B8710175523EA05294D5BD00435` |
| `26.2/.../block/ColorCollection.java` | `717FB27A7B1D0F68B3C2F371408385AD84CCDCC1D76248338897D8CF59DB2BE2` |

## Gate boundary

This closes only the named provider-dependent dye alias subgate for the supplied query envelope. The modern side is not evidence for historical/provider parity. Distinct `Block`/state identity means any independent equality predicate keyed specifically to white would need its own evidence; such a predicate is not established by this provider audit.



## Process note

During the audit I accidentally opened the 26.2 BlockBehaviour.java provider source while tracing shape identity, contrary to the instruction limiting modern inspection to raw-state representability. I removed that source and hash from this evidence, and did not use its contents for conclusions. The historical provider conclusion is based only on the historical inputs listed above. The coordinator has been informed; the modern identity claim remains limited to ID generation/registration evidence.


