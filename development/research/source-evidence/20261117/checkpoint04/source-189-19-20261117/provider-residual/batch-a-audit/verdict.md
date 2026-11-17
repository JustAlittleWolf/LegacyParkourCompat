# Bounded independent provider-row audit: 1.8.9 to 1.9

Verdict: REQUEST CHANGES for the bound report `build/source-189-19-20261117/provider-residual/supplemental-coverage.md` (SHA-256 `C68DA62AC7964E7B6FBBFD78FCD87C028C3B1BCC3B636C1E0D6BCEB9E03D3ED3`). This verdict covers only the report rows matching the assigned class names. It is not pair-wide coverage acceptance; the exact pair remains partial.

## Source identity and files

The audited source identity is the assigned 1.8.9 source-manifest SHA-256 `9E75F46DC0ED43B6A355BD65DB8A92C93A4DFEAECFA92284187C6FE9410D8004` and provenance SHA-256 `BA066FC8F1C7CEB9819D2106338D9E9F2C2B96EB5A8329EB40E171B1F9889185`; both root sidecars were rehashed. The 1.9 mapping-local `.decompile-provenance.json` SHA-256 is `8DAACB7AF8F210E716EE4B34C43B8A5D6F6B4B19831D2FD725CBE16ECF330A1A`; it was rehashed. For each row below, source paths are under `decompiled_minecraft/<release>/ornithe-feather/net/minecraft/block/`. The file hashes correspond to the sidecar rows on 1.8.9 and were checked against the 1.9 provenance `sources` entries and actual bytes.

| Class | 1.8.9 file SHA-256 | 1.9 file SHA-256 | Audit result |
|---|---|---|---|
| `CakeBlock.java` | `297DEFF656DFF4F9D91913E54AD6179F5AE21C0C7B21C9BCE86DA289E8A882CC` | `E98FB0FC5386FA95F41BC919E8CC387EC26F0F89664380EACEF463B98294A3EF` | Claim supported: for each `BITES` value 0–6, collision box has min X `(1 + BITES*2)/16`, max X 15/16, Z 1/16–15/16, Y 0–1/2. 1.9 precomputes the same seven boxes. Old `getCollisionShape` lines 44–49; new `SHAPES`/`getShape` lines 23–42. |
| `DaylightDetectorBlock.java` | `2810C80B4EDFEF9D50AC5193161550DA4B25A98E05DD7E7DF5F8C2FAA1F9E822` | `B41ABB758254CD30CDB801B518C9CB8D08A6B8085CB439F7A4056BE4E961BEA0` | Claim supported: old constructor/update sets unit X/Z and 3/8 Y; new `SHAPE` is the same bounds (old lines 26–40; new lines 26–44). `POWER` does not affect collision geometry. |
| `FlowerPotBlock.java` | `DBAB0443AE1D820786F92D6C1E0A875C0D5D33CC16347B2760F7FEE405164F09` | `3BDC6BA29E415F52E15A08CFFB6FAB1DB22757FA2A392FD4554FC440950A149D` | Claim supported: old `resetShape` sets a centered 3/8-square, 3/8-high box (lines 41–45); new static `SHAPE` encodes the same bounds (lines 28–45), independent of contents. |
| `LeverBlock.java` | `D8359D88616D0BD09462F90E98D5F4BDB76BF30B991CE841FAF068424F0B5881` | `6E0704CDC33E53F691BC906A00AF16B3EECB06C86B7FB70C0A16A7524058CEF6` | Claim supported: old `getCollisionShape` returns `null` (lines 28–31); new returns `EMPTY_BLOCK_SHAPE` (39–41). Orientation-specific bounds are a non-collision shape and do not add player collision. |
| `LilyPadBlock.java` | `C5D5C92A927E5D86FEB5FBF22102E7200037314CDBA6C1207ABBE52445580F35` | `4E266CCC4F6F1530ABC4F3FDB04ACFF50E171288B0735E5171D8CE34857AF429` | **Claim incorrect; see below.** |
| `RedstoneWireBlock.java` | `8A5115B44772531811908704F51B6FC233B89836B15440CA42138680FA8ABBB2` | `9A9ECCB80DE550A1E3D9B1CDFD9D23D263553250842CDD1C8B42231D679C33AB` | Claim supported: `getCollisionShape` returns `null` old (lines 70–73) and `EMPTY_BLOCK_SHAPE` new (127–130). Virtual connection properties alter `getShape` only (new 68–70); collision is empty regardless. |
| `SignBlock.java` | `7CCC9F3DA44069426C3FC808E97631A56B8AD10045B89459219B2C2D062E81F6` | `C9E9B98AB8DC312F5B79F1D4B810D5FF4F8150CCB679F56D3974B42FAE9CB8CC` | Claim supported: old collision provider returns null (lines 25–28); new returns `EMPTY_BLOCK_SHAPE` (31–34). |
| `WallSignBlock.java` | `5FA003C5CA40BFA2C901E35F8FA21FDE096264038C8150780A2E49F8F24E97B2` | `F416CE6C705828C1A5D77DFAA8A777F1EF081369B70FB61DCB0442DDA1B15ABA` | Claim supported as inherited behavior: old orientation-dependent `updateShape` changes the outline bounds (lines 19–40) while `SignBlock.getCollisionShape` remains null; new `getShape` switches by `FACING` (lines 23–36), while new inherited `SignBlock.getCollisionShape` is empty. No player collision in either. |
| `TripwireHookBlock.java` | `616C122A5CF5458C667188B04614C72488F298000C95835A5C4D239B39C6A67A` | `39841C939CE3C48687F684FDB9DA268CE6C5B7D801C5FF3FACCEC86BB980FE82` | Claim supported: old collision provider returns null (lines 38–41); new returns `EMPTY_BLOCK_SHAPE` (54–56). Facing shapes are only `getShape` output (new 39–51). |
| `VineBlock.java` | `C5052B5D6B70598C06ECB351FE2E4FCF5D2674BDA6FB2AD1E9D0BF4AEAB816B6` | `4EDF417C8DE4743F2E99F8AD1B8DA98A017B84543B546F39CEFAAB26201FD569` | Claim supported: old collision provider returns null (lines 126–129); new returns `EMPTY_BLOCK_SHAPE` (44–46). The new virtual-property resolution at lines 49–78 only constructs `getShape` outline geometry. |

## Correction required: `LilyPadBlock`

The report states the two player-collision bounds have the same footprint and 3/32 height. Exact source contradicts this:

- 1.8.9 `LilyPadBlock` constructor lines 15–18 sets `f = 0.5F`, `g = 0.015625F`, then bounds `(0, 0, 0)` through `(1, 1/64, 1)`. `getCollisionShape` lines 30–34 returns those mutable bounds offset by block position. Its `addCollisions` lines 23–27 admits null/player entities and excludes only boats.
- 1.9 lines 15–15 declares `SHAPE = (1/16, 0, 1/16)` through `(15/16, 3/32, 15/16)`. `addCollisions` lines 22–25 admits players and excludes boats. `getShape` lines 36–39 returns the same nonempty local shape.

Thus the 1.9 provider narrows the X/Z footprint from full block width to 14/16 and raises collision height from 1/64 to 3/32. The shape is reached by the block-collision provider and can affect player clipping when a movement query intersects the changed boundary. This is an eligible source-level collision-shape difference. The report must correct the row and route it as a distinct candidate; this audit does not establish a specific trajectory or implementation applicability beyond the source-verified collision path. The 1.9-added `onEntityCollision` branch remains boat-only and is not the difference.

## Row coverage boundary

Among the assigned names, this report contains rows for `CakeBlock`, `DaylightDetectorBlock`, `FlowerPotBlock`, `LeverBlock`, `LilyPadBlock`, `RedstoneWireBlock`, `SignBlock`/`WallSignBlock`, `TripwireHookBlock`, and `VineBlock`. It contains no rows for `AbstractPressurePlateBlock`, `ButtonBlock`, `CauldronBlock`, `DoorBlock`, `FenceBlock`, or `FenceGateBlock`; those classes receive no verdict from this audit. No other report was opened. No pane/piston frozen source packets, wiki inputs, product sources/history, runtime, or pair-wide coverage were reviewed.
