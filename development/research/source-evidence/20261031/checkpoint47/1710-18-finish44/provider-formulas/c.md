# Independent collision-provider pass C: SoulSand–Wall

Scope: source-only comparison of the nine assigned common provider classes for ordinary player block collision queries. Sources are the exact read-only Feather trees: 1.7.10 manifest SHA-256 `11bada441ebe07661402d1c40e1c490b78b279ecaa084f9a4dc3ae2dd54caf6f`; 1.8 manifest SHA-256 `52c8312c34a1a491d4a200c84e90f5d8d95c7e5caa9571f864d8e70f9737db05`.

Per-file SHA-256 identity:

| Class | 1.7.10 | 1.8 |
|---|---|---|
| SoulSandBlock | `6216E0BD9C35B694BA0269B164FFB3349923BF7731AA791CAE6CF963390C0650` | `F699793AD9DB4BCD39EB3EA2872D67217B75CE8361DC415E4CA6B846A6A29975` |
| StairsBlock | `3367469608D32C3AA26158763A4C82E498091C9EAD3844832B3265FB06AC7506` | `B50094C97DADAAF056ADA6466BAE8005A72C820E9846EF352886F1FD78CAA5CB` |
| SugarCaneBlock | `7EE9CD2BD836D92FCEAEC1FFF633A62DFF7025416BC66FF8A1B044FB5923ACCE` | `7F8A1CEA0B0A73E984D7DA9B39E4AAB1230A375861A66B58CCB0AF8A1DFE8BF1` |
| TorchBlock | `E20112C2A46D4C97C850A0805B0B03E7369EEA297ABD1B8E5AE7D996A616CE44` | `33E8AEDB1536035DD5B10BAA074CA2AA92654B57151469023C57CED16AFBAE46` |
| TrapdoorBlock | `E57047DE4C6566F445689823589B11A92D3CC2E6445B90DC13E5F66E8F500903` | `5B44CEB9F6DAC6528B0A45F8D2CA402599D52FA698ABC33B0D5CF6B497517892` |
| TripwireBlock | `16B36C23FEF69DBD8800DBD1BA1750D7E1DDB231C2A2DD69DA0B33EE4858ED69` | `C2EFA5CDB782F83E201796E31BE9A91440A2B2CA62DC9EE861C4E72308C4AE2F` |
| TripwireHookBlock | `D14DD34A4CE94D9F44BE0E0F7A610C8AB3526587D4C372E1E1A579A4E277ED58` | `A6A6A9624BD67910711762D49F28A90E3A16285B8DB23F89254C43179AE3527D` |
| VineBlock | `CFF5DBB061E56A677FEC9682E4552B9EF1B226C0BFCB16087D7C3767366C4B08` | `7AED3C3AA200C0A91DFFFA5F165564784054A4DDA9F512A4BB74FE2DB5ECFE2D` |
| WallBlock | `D1A029496C8D2328C074CE1027078EE6D8CBFFB82A744F6ED53B940688804B91` | `0D06C52B179DD48DD2E69340A7FD742209926ECE1A2BB99A0CA3070A08702623` |

## Method dispositions

| Class | Compared movement-bearing methods/dependencies | Verdict |
|---|---|---|
| `SoulSandBlock` | `getCollisionShape` returns the same full XZ box with `maxY = y + 1 - 0.125F`; coordinate object conversion in 1.8 is equivalent. Both `onEntityCollision` methods multiply player X/Z velocity by `0.4`. | Equivalent collision and velocity response. |
| `StairsBlock` | Compared base half box, metadata/state facing and half mapping, inner/outer branch tests and masks, neighbor coordinates, shape bounds, add-collision order, and shape reset. 1.8 metadata decoding maps `metadata & 3` by `Direction.byId(5 - bits)` and top half by bit 2, preserving the four 1.7 facings and bit-2 half. Branch decision and box construction for all cardinal orientations pair; old metadata-derived corner type is only a cached representation. `addCollisions` adds base, base again after inner selection, optional step, then resets full bounds on each side. | Equivalent provider decomposition for the old-era state/facing inputs; no stair orientation gap found. |
| `SugarCaneBlock` | `getCollisionShape` returns null in both. | Equivalent no-collision provider. |
| `TorchBlock` | `getCollisionShape` returns null in both. | Equivalent no-collision provider. |
| `TrapdoorBlock` | `getCollisionShape` refreshes shape then delegates to base on both sides. Old metadata bits map to 1.8 state exactly: low two bits 0/1/2/3 map NORTH/SOUTH/WEST/EAST, bit 2 is open, bit 3 is top half. Closed lower/upper slab and all four open side boxes use identical 3/16 thickness and bounds. | Equivalent dynamic provider for equal legacy metadata/state. |
| `TripwireBlock` | `getCollisionShape` returns null in both. | Equivalent no-collision provider. |
| `TripwireHookBlock` | `getCollisionShape` returns null in both. | Equivalent no-collision provider. |
| `VineBlock` | `getCollisionShape` returns null in both; its separate outline-shape attachment logic is not the collision-provider output. | Equivalent no-collision provider. |
| `WallBlock` | `updateShape` reads north, south, west, east in that order and uses the same 1/4-to-3/4 central bounds, connection extensions, and 13/16 central post for straight north-south or east-west pairs. `getCollisionShape` raises maxY to 1.5 then delegates to base on both sides. `shouldConnectTo` agrees for old-era wall, fence gate, solid full cubes other than pumpkin. 1.8 adds an explicit barrier rejection; Barrier has no 1.7.10 counterpart. | Equivalent for old-era neighboring states; 1.8 Barrier branch is modern-only. |

All null/equal paths and the stateful provider bodies were inspected in the paired source files identified by the hashes above. No new historical player movement candidate arose in this slice. This pass closes only the assigned rows; the other provider ranges are recorded by sibling reports. It does not establish full-pair freeze or runtime parity.
