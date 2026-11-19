# Independent source audit: sleep width reduction gate

Status: COMPLETED. Verdict: ACCEPTED for the bounded source claim; no ready width-reduction finding for ordinary vanilla local-player lifecycle in 1.10–1.12.2.

Reviewer path: `C:\Users\Wolfi\.codex\visualizations\2026\11\18\01a1f01a-5064-75c0-a594-b2e7fa3b99e2\c-pose-sleep-width-reduction-audit\sleep-width-reduction-audit-verdict.md`

## Authenticated inputs

Raw SHA-256 readback matched both assigned source-only inputs:

- `c-pose-sleep-width-reduction-source/sleep-width-reduction-source.md`: `EF8BD6E623641CE49BA868F53184E9549A2D6254864473A3C6C47AA5C94D80DE`
- `c-pose-sleep-priority-bound/direct-resize-fit.md`: `BAE6C476F7D819C94191E887D681541D4F2821C066EE8EEBEA9DC45154E2FD01`

Canonical read-only sources are `decompiled_minecraft/<version>/ornithe-feather/`, Feather Gen2 build.2 per exact version. Independently recomputed source and provenance-manifest SHA-256 values for every assigned alias:

| Version | PlayerEntity.java | Entity.java | Provenance/source manifest |
|---|---|---|---|
| 1.10 | `DB7BBBC15E2BCB0DE18476FEC1B88E73ECCF8AE63B170276B1D8327DD0BE796B` | `05DA145EFFA19A6EF7934CC276E89226373B67C12F4CE89A8CE2183F29039F77` | `DF266E2C3009DD1A873C1665C30FE388078ED1C6227668E10CDE87409A18C6DA` |
| 1.10.1 | `020370CFFDE2D1FE9F1B63FA5D51F1AF2FAB095908B19E79F88A7BCAC7DFD501` | `05DA145EFFA19A6EF7934CC276E89226373B67C12F4CE89A8CE2183F29039F77` | `A9A50497930848BF0DDEC732B6D424E947F643A8FA6290E01425AC2BBFCE219F` |
| 1.10.2 | `A055B84B98D98E828E177CAD9BBA47A1334EE9BF3ECD4331792979106235A302` | `05DA145EFFA19A6EF7934CC276E89226373B67C12F4CE89A8CE2183F29039F77` | `91B0F478ACB7B6F13463C35B268A30D2806F583402631A56F54CE2EB70D1EC71` |
| 1.11 | `A0D787681DE5CAECDA03FEA1536DAC0C07E6CA5CFBFE3B5FBB700CBE77C86FB6` | `3209FEAACD1E798B521A193EEB081D92C96881A70A18254EF24A58E911A4FD97` | `8B3E5DA58494F1DBA77410960753B0BF646425A1ADA491D8242CC71E6C7A59CA` |
| 1.11.1 | `8B37FD286B2DAC1B343BD708B80284B43BB31C61BAFC63BDCF6620BA3A59065C` | `5A906DE1D40A4A5BA8AEDC9A6A9DC704E0ED862DF177CF967FB1A9454765FA96` | `0D5F4697D4E4CE53B36D0DBFEC89D44830EB76753A6643B49E42F0857753066D` |
| 1.11.2 | `87FE94FA6CBF7ABA18B9A5E3401664439EB8EBA9958173DA8FBCD05CC7AD948B` | `CE8104A17CE783DF639CF9726E7B1CD0936563EAA7BA308603335BBE05D49440` | `D908C2AF76598AD26999563BFEF0089F1D3C8C32483EEE6B108F0DEFA2773DF0` |
| 1.12 | `6A9D5F420B13625195B149C0D9468CF1284D5EB7DC1B2A46CF01B952A27EE063` | `80F091BF32166C88CF8BBD31CAF72D84FA16224410733C7D2A0F00563F294A0A` | `27515E5F4C71E7DB2F035A81716507128D77466C75380AD1EF7D140C06401467` |
| 1.12.1 | `4DAB93EA3178A28C616947004ECDC514E6542EDCA8C62EF83F686CCF6B2CE640` | `80F091BF32166C88CF8BBD31CAF72D84FA16224410733C7D2A0F00563F294A0A` | `0FF5246E8D29C04554CC9EC93AE9B4ABEF539652325B6C0AC822851D7186A9C8` |
| 1.12.2 | `E4E0FDBE07A7D0A0AE4A70CBB6739A287C9D045A4A12B409895C220B5D91FE1E` | `80F091BF32166C88CF8BBD31CAF72D84FA16224410733C7D2A0F00563F294A0A` | `B8A37CCFCCD2AAC5F40F5E34FEC85873DBDFA93A266043C103E592E4A8C949DA` |

## Verdict and source evidence

Across all nine aliases, `Entity` initializes `width` to `0.6F` and `height` to `1.8F`; the local-player constructor chain delegates through `ClientPlayerEntity` and `PlayerEntity` to that constructor. `PlayerEntity.updatePlayerPose()` chooses only widths `0.6F` (fall-flying, sneaking, standing) or `0.2F` (sleeping). Its other player `setSize` calls normalize/reset to `(0.6F,1.8F)` or set sleeping/death size to `(0.2F,0.2F)`. I checked the constructor, selector and all player-local `setSize` call sites across the exact aliases. No supported ordinary vanilla path supplies a player width above `0.6F` before this selector.

The source fields are not restored from NBT or synchronized entity data: the entity serialization and player persistence code contain no width/height entry; player synced-data registration contains no dimension field. Mounting, movement and collision update position or the stored shape, not the width field. `Entity.setShape(Box)` writes only the shape. The only vanilla field assignments on the player hierarchy are root `Entity` construction and `Entity.setSize`; player-specific requested values remain at or below `0.6F`. This disposition is limited to vanilla lifecycle, excluding mods, mixins, reflection and arbitrary external calls/state.

The exact 1.11 `Entity.setSize(float,float)` branch (`Entity.java`, lines 267–284; same shrink behavior through 1.12.2) detects `width < oldWidth`, centers the new box at `x/z`, preserves the bottom `y`, writes the shape and returns. The 1.10.x implementation lacks this shrink branch and preserves the old minima for a size change. This branch distinction matters for a genuine shrink transition, but cannot establish an initial width above `0.6F`; the sleeping-to-standing/fall-flying width transition is an expansion from `0.2F`, and fall-flying retains `0.6F`. A separate fall-flying-to-sleeping shrink remains governed by its own selector/fit behavior and is not accepted here as a prior-wide reduction case.

Modern scale mechanics are outside this exact 1.10–1.12.2 vanilla source claim. This verdict makes no statement about scale-capable later versions, modded clients, or arbitrary state. It also does not establish reachability of simultaneous sleeping and fall-flying predicates; that predicate question remains separate from the width-precondition gate.

No product checkout, Git status/history, wiki lane, backlog, primary research, tests, build, or runtime was used or changed. No broader resize behavior or simultaneous-predicate conclusion is included.