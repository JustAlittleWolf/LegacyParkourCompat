# Brewing stand collision provider: 1.11 → 1.11.1

## Exact source identity

The compared roots are `decompiled_minecraft/1.11/ornithe-feather/` and `decompiled_minecraft/1.11.1/ornithe-feather/` (Feather Gen2 mappings). Publication sidecars checked:

| Release | provenance SHA-256 | source-manifest SHA-256 | ready-marker SHA-256 | artifact-manifest SHA-256 |
| --- | --- | --- | --- | --- |
| 1.11 | `6F775C806FD1401A4D1756E9EF88DA8D73185FE1D13598235F85C424405F4C40` | `8B3E5DA58494F1DBA77410960753B0BF646425A1ADA491D8242CC71E6C7A59CA` | `3C4102230DBA0F9D2D07F752BBD2A19D2717375F6E31FB618C0450B7FD82BD45` | `AE874615F961D67E402C978FF01CD3F26F61145E6B3C21EF318C1AB71F7794FD` |
| 1.11.1 | `0F6C92B8FBF4B1053A26A4CB61DC8F20C695E74A1963B49D911B8C7FBF01A055` | `0D5F4697D4E4CE53B36D0DBFEC89D44830EB76753A6643B49E42F0857753066D` | `454036A978074D3A34359DFEBF03D6EF3E3335D1318B88D6B17E4F1F5AB566CF` | `EBBF181F60D7BC922DF56826F528D1EA8A064783AC0F0F2B576E73DB1FC2A958` |

The pinned mapped-client identities recorded in provenance are `d9359dadc1c81c2795ba84e5b577bf65af364ed1d33890b2ff6c8bf9059bb589` (1.11, Feather Gen2 1.11+build.2) and `c6febce489d293f72f3cb0a3ba53f82cfdb854b2c1807056f7a86331235068db` (1.11.1, Feather Gen2 1.11.1+build.2). The bound pair inventory reports 1,918/1,921 exact source entries and zero manifest mismatches.

## Provider state and shape output

- 1.11 `net/minecraft/block/BrewingStandBlock.java` SHA-256 `B2B56E8BB930874F22BB77E4676FDA0250C2695A27E83C92CA796D3B52D6E00C`; 1.11.1 same path SHA-256 `D890D11F9123D2F82F3358B732F7A93464A8E3117D8AE7183FB0A66527BE5E18`.
- `HAS_BOTTLE` consists of three booleans; default state sets all false and metadata maps the three bits independently (1.11 lines 33–41, 151–168; 1.11.1 same members/lines). This gives eight state combinations. No orientation or other shape property is present.
- Both versions use the same two relative collision boxes: `COLLISION_SHAPE = (0.4375, 0, 0.4375)–(0.5625, 0.875, 0.5625)` and `SHAPE = (0, 0, 0)–(1, 0.125, 1)` (lines 36–37). The selected state is not read by collision output.
- 1.11 `addCollisions` appends the central column and then the bottom platform through `addCollision` (lines 70–72). 1.11.1 does the same in the same order (lines 70–72); it adds the trailing `forceShape` parameter (line 70) but does not read it. The common helper translates each box by the block position and appends it only when it intersects the query shape (`Block.java`, 1.11 lines 356–361; 1.11.1 lines 356–361). Thus each state yields the same ordered provider output for a given query in both endpoints.

## Local-player query binding and conclusion

The local player’s `move` override delegates to `super.move` (`client/entity/living/player/LocalClientPlayerEntity.java`, lines 824–827 in both releases). `Entity.move` collects collisions through `World.getCollisions(this, queryBox)` (1.11 lines 523/569; 1.11.1 lines 564/610), retaining the player entity as the query argument. `World.getCollisions` dispatches the selected block state’s `addCollisions` method (1.11 line 994; 1.11.1 line 997); the 1.11.1 path passes `false` for `forceShape`.

This bounded comparison finds no BrewingStand collision-provider shape/state delta from 1.11 to 1.11.1. It does not assess brewing block-entity behavior, state production/reachability, other block providers, or the complete movement pair; 1.11→1.11.1 remains partial pending full inventory and independent coverage audit.

### Caller source identities

The following exact-version caller/provider dispatch sources were also checked against their corresponding `ornithe-feather.sources.sha256` rows:

| Source | 1.11 SHA-256 | 1.11.1 SHA-256 |
| --- | --- | --- |
| `net/minecraft/block/Block.java` | `3FE8FDABE68DA83FCFA01C0E6B8F5272FE81D71F6BF553506BDCEF54757B0488` | `AB873436B24487AB0EE8055BF7A478349C7F7978398AEA8E387796A6C820996A` |
| `net/minecraft/world/World.java` | `F64905888809D5666A63C8E403F171C13907502B8D982C5B3B8FCE192360FF47` | `27CFAA5FF45C2D88C492FC5DAE3EA4A2BB4536FD64BFF8498A9A7D0CB82EFB58` |
| `net/minecraft/entity/Entity.java` | `3209FEAACD1E798B521A193EEB081D92C96881A70A18254EF24A58E911A4FD97` | `5A906DE1D40A4A5BA8AEDC9A6A9DC704E0ED862DF177CF967FB1A9454765FA96` |
| `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `65C2747BD8C70DEF6BE7F41F624D4C9493342B39AE7BED7967F9FF63608F59ED` | `65C2747BD8C70DEF6BE7F41F624D4C9493342B39AE7BED7967F9FF63608F59ED` |
