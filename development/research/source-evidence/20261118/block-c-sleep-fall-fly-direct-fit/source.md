# Direct resize fit and post-gate box write

Status: COMPLETED. Bounded source gate for the sleeping-versus-fall-flying desired-dimension selector in 1.10–1.12.2 stable aliases, with 1.9.4 as the prior same-lane comparison. Source-tree identity supplied by assignment: `52399d310b7366244457d5c472c41378f2064262`. Exact Feather sources and their sidecars/manifests were read-only; no product, Git, wiki, runtime, test, build, or primary-research changes.

## Finding

For every checked 1.10–1.12.2 alias, `PlayerEntity.updatePlayerPose()` first chooses desired width/height, then only when either differs from the current dimensions builds a candidate box from the current box's `minX/minY/minZ` and adds the desired width/height. It calls `world.getCollisions(candidate)` and invokes `setSize(width,height)` only when that call returns false. The priority chain is fall-flying before sleeping, so simultaneous externally stipulated truth chooses the 0.6F × 0.6F fall-flying dimensions. If dimensions are already 0.6F × 0.6F there is no candidate query or resize; if dimensions are the sleeping 0.2F × 0.2F size, the fit check gates expansion to 0.6F × 0.6F.

The fit is a specific vanilla block collision and fixed-coordinate-bound query, not a general test that the candidate is free of all entities or inside the configured world border. Through 1.11, `World.getCollisions(Box)` directly walks relevant blocks, tests each block state's collision shapes with a null entity, and returns true for either a reported block collision or coordinates outside `[-30,000,000, 30,000,000)`. From 1.11.1 through 1.12.2 the public method delegates to the shared helper with `entity=null` and `enforceWorldBorder=true`; that helper returns true for the same hard coordinate bound or a block collision. With null entity, the entity-collision query path is skipped. Its `WorldBorder.contains` branch does not apply to this call, so the configured world-border edge is not a candidate rejection. The gate therefore supports a precise block-shape/hard-bound projection, but not a claim of entity-clear or configured-world-border-clear occupancy.

The selected candidate matches the resulting local-player box in the dual-predicate case for the historical dimensions relevant here. `Entity.setSize` always writes the new width/height and collision box; when width expands it preserves the current box minima, matching the candidate construction. Its corrective `move(oldWidth-newWidth,0,oldWidth-newWidth)` is guarded by `!world.isClient`, so expansion compensation is server-only; the logical client receives the box write and no such movement. In 1.11–1.12.2, a width shrink instead recenters the new box around `x/z` and returns. That differs from the min-anchored candidate, but is not the dual-predicate branch outcome when the preceding selector dimensions are 0.2F (sleeping) or 0.6F (fall-flying): fall-flying wins and the new width is 0.6F, so width stays equal or expands. For a separate fall-flying-to-sleeping transition with fall-flying false, the shrink-path mismatch means the pre-fit candidate must not be treated as the exact final box in 1.11–1.12.2.

The proof is source-level and conditional on state values. It does not establish ordinary gameplay reachability of simultaneous sleeping/fall-flying truth; no runtime or trajectory was examined.

## Version slices and authenticated source files

All files below are `decompiled_minecraft/<version>/ornithe-feather/net/minecraft/...` and map with the version's Feather Gen2 `+build.2` artifact. Each listed source file's full SHA-256 was matched to its exact-version source-hash record. For rows marked `nested`, the record is the `sources` map in `ornithe-feather/.decompile-provenance.json`; for rows marked `parent`, it is the matching row in `decompiled_minecraft/<version>/ornithe-feather.sources.sha256`. The manifest-file SHA-256 binds the listed class hashes.

| Version | `updatePlayerPose` lines | `Entity.setSize` lines | PlayerEntity.java SHA-256 | Entity.java SHA-256 | World.java SHA-256 | Source manifest SHA-256 |
|---|---:|---:|---|---|---|---|
| 1.9.4 | 285–309 | 259–270 | `D658A0D95452D12BB7E347BFD802240EEECAF7F938E10DCD43E2640434387F85` | `BCD7FA2206BF8D7271102F6DA7FE2771F96DBE22EC79DAB9F2101A3E29C17EF0` | `2FE063E0EC224EED9FC7D01B8F788C32035EED5FEE5A9D98EA5E82296236E05A` | parent `C7B508FE01634887B65919DCD3A900C311A21D9510A1F1AB248D5C17C528AB19` |
| 1.10 | 296–320 | 264–275 | `DB7BBBC15E2BCB0DE18476FEC1B88E73ECCF8AE63B170276B1D8327DD0BE796B` | `05DA145EFFA19A6EF7934CC276E89226373B67C12F4CE89A8CE2183F29039F77` | `827FEA19D95DDC0A2847859F0C288BF0B6D6DC3692FEB30B52B84C60FA19D877` | nested `DF266E2C3009DD1A873C1665C30FE388078ED1C6227668E10CDE87409A18C6DA` |
| 1.10.1 | 296–320 | 264–275 | `020370CFFDE2D1FE9F1B63FA5D51F1AF2FAB095908B19E79F88A7BCAC7DFD501` | `05DA145EFFA19A6EF7934CC276E89226373B67C12F4CE89A8CE2183F29039F77` | `41FD1EDC24B39A5B9A979D15D54FBD3EBF9818651628109F232FCC24C7E28D0A` | nested `A9A50497930848BF0DDEC732B6D424E947F643A8FA6290E01425AC2BBFCE219F` |
| 1.10.2 | 296–320 | 264–275 | `A055B84B98D98E828E177CAD9BBA47A1334EE9BF3ECD4331792979106235A302` | `05DA145EFFA19A6EF7934CC276E89226373B67C12F4CE89A8CE2183F29039F77` | `888ED0E9DE765DEF87B05C4126ECDF0B10E9DD448B4543DD1CB98211E9951646` | parent `91B0F478ACB7B6F13463C35B268A30D2806F583402631A56F54CE2EB70D1EC71` |
| 1.11 | 295–319 | 267–284 | `A0D787681DE5CAECDA03FEA1536DAC0C07E6CA5CFBFE3B5FBB700CBE77C86FB6` | `3209FEAACD1E798B521A193EEB081D92C96881A70A18254EF24A58E911A4FD97` | `F64905888809D5666A63C8E403F171C13907502B8D982C5B3B8FCE192360FF47` | parent `8B3E5DA58494F1DBA77410960753B0BF646425A1ADA491D8242CC71E6C7A59CA` |
| 1.11.1 | 295–319 | 271–288 | `8B37FD286B2DAC1B343BD708B80284B43BB31C61BAFC63BDCF6620BA3A59065C` | `5A906DE1D40A4A5BA8AEDC9A6A9DC704E0ED862DF177CF967FB1A9454765FA96` | `27CFAA5FF45C2D88C492FC5DAE3EA4A2BB4536FD64BFF8498A9A7D0CB82EFB58` | parent `0D5F4697D4E4CE53B36D0DBFEC89D44830EB76753A6643B49E42F0857753066D` |
| 1.11.2 | 295–319 | 271–288 | `87FE94FA6CBF7ABA18B9A5E3401664439EB8EBA9958173DA8FBCD05CC7AD948B` | `CE8104A17CE783DF639CF9726E7B1CD0936563EAA7BA308603335BBE05D49440` | `27CFAA5FF45C2D88C492FC5DAE3EA4A2BB4536FD64BFF8498A9A7D0CB82EFB58` | parent `D908C2AF76598AD26999563BFEF0089F1D3C8C32483EEE6B108F0DEFA2773DF0` |
| 1.12 | 291–315 | 274–291 | `6A9D5F420B13625195B149C0D9468CF1284D5EB7DC1B2A46CF01B952A27EE063` | `80F091BF32166C88CF8BBD31CAF72D84FA16224410733C7D2A0F00563F294A0A` | `F7AB7A34D456E720E7EE4F34FBD0EB10F7F96151596C8C615AB2EDEF05A884D8` | nested `27515E5F4C71E7DB2F035A81716507128D77466C75380AD1EF7D140C06401467` |
| 1.12.1 | 291–315 | 274–291 | `4DAB93EA3178A28C616947004ECDC514E6542EDCA8C62EF83F686CCF6B2CE640` | `80F091BF32166C88CF8BBD31CAF72D84FA16224410733C7D2A0F00563F294A0A` | `D182122A9B052F57803672C21B0941CB7E7D41ED756CD0A4EA8C0858684C8259` | nested `0FF5246E8D29C04554CC9EC93AE9B4ABEF539652325B6C0AC822851D7186A9C8` |
| 1.12.2 | 291–315 | 274–291 | `E4E0FDBE07A7D0A0AE4A70CBB6739A287C9D045A4A12B409895C220B5D91FE1E` | `80F091BF32166C88CF8BBD31CAF72D84FA16224410733C7D2A0F00563F294A0A` | `E9FA9B8D6D31AD57A5B876F5F63A5E3C554A23437DECF1845A493DAF48233594` | parent `B8A37CCFCCD2AAC5F40F5E34FEC85873DBDFA93A266043C103E592E4A8C949DA` |

The Feather mapping artifact identity is `net.ornithemc:feather-gen2:<same exact Minecraft version>+build.2` for every row. Exact artifact file names and pinned input identity are in the corresponding provenance sidecar. The 1.9.4 reference is the separately accepted same-lane predecessor and its source manifest is included above.

## Procedure details

### Candidate gate

The method body is identical across all nine 1.10–1.12.2 aliases: fall-flying (`0.6F,0.6F`), sleeping (`0.2F,0.2F`), sneaking (`0.6F,1.65F`), standing (`0.6F,1.8F`); candidate box uses existing minima plus chosen dimensions; a size-difference guard precedes `!world.getCollisions(box)` and `setSize`. The fit candidate is computed once from the selected branch; no sleeping recheck happens after fall-flying wins.

### World query and occupancy scope

In 1.9.4–1.11 the `World.getCollisions(Box)` body SHA-256 is `4ABE08659510D6F4763C90E70A288F849FDBB363128297D1E3B9E08C4FF0A144`; exact ranges: 1.9.4 1018–1055, 1.10/.1/.2 1022–1059, 1.11 1044–1081. It evaluates block collisions with `entity=null`; no entities are queried. It returns true on a block collision or if a scanned X/Z block coordinate is below -30,000,000 or at/above 30,000,000. It does not test the configured border edge.

From 1.11.1 through 1.12.2, `World.getCollisions(Box)` is a thin wrapper at 1058–1060 (1.11.1/.2), 1062–1064 (1.12/.1/.2), with body SHA-256 `E8247E4EAC95F3994EDF5F156D3A8D66B316378969D1167CFDEB7575EE0D4299`. It passes null entity, `enforceWorldBorder=true`, and an empty list to `World.getCollisions(Entity,Box,boolean,List)` (1.11.1+ helper lines 960–1011; 1.12+ 964–1015). Under those explicit arguments the helper checks the same fixed coordinate bound and block-state collisions, while the optional entity-collision path is skipped. `WorldBorder.contains` is not a rejection gate for this wrapper: the helper's border containment branch requires `!enforceWorldBorder` and an entity within the border. The method name therefore must not be read as proof of configured-border or entity occupancy safety for this candidate.

The prior same-lane 1.8→1.9 source finding records the 1.9 `PlayerEntity` candidate construction and the `getCollisions(Box)` block-state query, and bounds its `PlayerEntity` selector/query/size gate as identical through 1.9.4. The exact 1.9.4 version here confirms that baseline.

### Post-gate box write and client/server side effect

For 1.9.4 and 1.10/.1/.2, `Entity.setSize` changes dimensions when either differs, then writes a box from the current shape minima and new width/height; the body is otherwise identical. It performs a compensating `move(oldWidth-newWidth,0,oldWidth-newWidth)` only if width grew, `!firstTick`, and `!world.isClient` (body SHA-256 `D8A96622F5C44830981DD706C78F15CB0CD748B044E3F2D5715F0DA4DB84AA6F`). Thus the box write occurs on client and server, but width-expansion movement compensation is server-only.

Starting at 1.11, `Entity.setSize` adds a width-shrink branch: it centers the new box at `x/z`, retains `y` as the bottom, writes the box, and returns. Non-shrink changes preserve old minima. Width expansion still runs the same server-only compensation (body SHA-256 `6E3C25E2020E9248360A9C465093454E8FEB31B08BF752228CF11F1465B202C5`; unchanged across 1.11–1.12.2). The direct candidate box is therefore not generally the exact post-resize box for shrink transitions. For the disputed simultaneous-state result, desired width is 0.6F: from the known sleeping width 0.2F it expands, so the min-anchored candidate and client box write agree; from an existing 0.6F width it does not shrink and the box remains min-anchored. The server-only movement side effect is not run by the logical client.

## Gaps kept open

- Both predicate truths are stipulated source state for this comparison; the overlap's reachability in ordinary vanilla play remains unproven.
- The candidate query does not prove absence of entity blockers or configured-world-border violations.
- No collision-shape implementation internals, temporary-occupancy behavior, or unrelated world policy was audited.
