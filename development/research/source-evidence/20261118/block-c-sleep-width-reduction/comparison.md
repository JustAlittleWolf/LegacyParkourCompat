# Sleep width reduction source gate

Status: COMPLETED. Bounded source-only census for 1.10, 1.10.1, 1.10.2, 1.11, 1.11.1, 1.11.2, 1.12, 1.12.1 and 1.12.2. Question: at the sleeping + fall-flying desired-dimension selector, can ordinary vanilla local-player lifecycle supply a prior `width > 0.6F`? Exact decompiled sources are read-only. No product code, Git history/diff, backlog/wiki, primary research, source generation, tests, build, or runtime was consulted or changed. An inadvertent `git status --short` command was run at the end; it returned no output and exposed no source or history.

## Disposition

No supported historical vanilla path was found that gives a local human player's `width > 0.6F` before `PlayerEntity.updatePlayerPose()` in the assigned versions. The dual-predicate path remains source-state conditional, but its width-reduction candidate is not applicable to ordinary vanilla player lifecycle: initialization and every vanilla player size request are at most `0.6F` wide. There is no ready finding for prior-width shrink behavior in this scope. The prior direct-resize finding's selector/query/write analysis remains conditional; this gate does not establish natural overlap reachability (owned by another worker).

This is a source-backed no-ready disposition for supported vanilla behavior, not a claim about modified clients or arbitrary external geometry. A mod, mixin, subclass, reflective write, or direct call to the public box setter could create unsupported state; these are outside the assigned ordinary vanilla lifecycle. A box whose extents differ from the entity's size fields is not evidence that `width > 0.6F`.

## Census and source facts

* `Entity` is the root constructor on the local player chain. In every checked alias it initializes `width = 0.6F`, `height = 1.8F`, then creates the initial box through `setPosition`. `PlayerEntity(World, GameProfile)` calls `super(world)` and sets spawn position; it introduces no larger width. `ClientPlayerEntity` extends `PlayerEntity`, and `LocalClientPlayerEntity` extends the client-player chain and delegates to its constructor. The local-player construction therefore inherits the same 0.6F root width.
* The source declarations expose just two vanilla writes to the `Entity.width` field: the root constructor and `Entity.setSize(float,float)`. The same method writes height and changes the box. Box-only writers are separate: `setPosition` recomputes a box from the current dimensions, and movement/collision code translates or restores box values. `setShape(Box)` directly replaces the box but does not modify width or height; no vanilla caller outside `Entity` was found in the player sources. Thus changed box extents alone cannot establish a larger width field.
* Player-local `setSize` callsites in `PlayerEntity` are the pose selector, constructor normalization to `(0.6F,1.8F)`, sleeping `(0.2F,0.2F)`, waking/reset `(0.6F,1.8F)`. The selector's complete target set is fall-flying `(0.6F,0.6F)`, sleeping `(0.2F,0.2F)`, sneaking `(0.6F,1.65F)`, and standing `(0.6F,1.8F)`. Its fit gate conditionally applies those values; it has no value above `0.6F`. The same selector body is covered by the frozen prior alias record for each assigned release.
* `Entity.setSize` is the only size writer and accepts its method parameters without any larger player-specific multiplier. In 1.11–1.12.2, its shrink branch recenters a smaller shape around `x/z`; in 1.10.x it preserves minima. Neither path can increase the width field above the requested width. The possible 1.11+ centering difference does not make the initial width larger.
* The player/entity synchronized-data registrations include flags, breath, names, health/effect presentation, score, model-part and arm data, not width, height, or a scale field. Entity serialization (`writeNbtWithoutId` / `readNbt`) stores position, rotation, motion, identity and ordinary entity state, with no width/height. Networked player pose/fall-flying predicates may vary independently, but that synchronized predicate state does not initialize the local player's size fields.
* The historical player attributes initialized in these sources include movement speed and combat attributes; no player-size attribute, potion/equipment size modifier, or vanilla scaling mechanism is present. Age scaling is on other living-entity subclasses, not player lifecycle. No client or server player subclass declares a larger dimension initialization or overrides `setSize` in these source trees.

## Exact source authority and authenticated identities

All sources are the assigned canonical read-only tree `decompiled_minecraft/<version>/ornithe-feather/`, Feather Gen2 `+build.2` for that exact release. Prior frozen identities were supplied as source-only inputs; both were independently SHA-256 checked before use:

* `c-pose-sleep-priority-bound/direct-resize-fit.md`: `BAE6C476F7D819C94191E887D681541D4F2821C066EE8EEBEA9DC45154E2FD01`
* `c-pose-sleep-priority-bound/stable-aliases.md`: `A7447EB210121A4DD4659F1D58DCCF1F154B459C4F7FC32B86D7AC124EA6A534`
* Their declared exact-source-tree identity: `52399d310b7366244457d5c472c41378f2064262`.

The previous stable-alias closure authenticates each listed `PlayerEntity.java` digest and each manifest digest. Here are those player hashes plus independently rehashed `Entity.java` files, for the files used by this census. A manifest marked nested is `ornithe-feather/.decompile-provenance.json`; parent is `decompiled_minecraft/<version>/ornithe-feather.sources.sha256`. Exact artifact identity per row is `net.ornithemc:feather-gen2:<version>+build.2`.

| Version | PlayerEntity.java SHA-256 | Entity.java SHA-256 | Source manifest SHA-256 |
|---|---|---|---|
| 1.10 | `DB7BBBC15E2BCB0DE18476FEC1B88E73ECCF8AE63B170276B1D8327DD0BE796B` | `05DA145EFFA19A6EF7934CC276E89226373B67C12F4CE89A8CE2183F29039F77` | nested `DF266E2C3009DD1A873C1665C30FE388078ED1C6227668E10CDE87409A18C6DA` |
| 1.10.1 | `020370CFFDE2D1FE9F1B63FA5D51F1AF2FAB095908B19E79F88A7BCAC7DFD501` | `05DA145EFFA19A6EF7934CC276E89226373B67C12F4CE89A8CE2183F29039F77` | nested `A9A50497930848BF0DDEC732B6D424E947F643A8FA6290E01425AC2BBFCE219F` |
| 1.10.2 | `A055B84B98D98E828E177CAD9BBA47A1334EE9BF3ECD4331792979106235A302` | `05DA145EFFA19A6EF7934CC276E89226373B67C12F4CE89A8CE2183F29039F77` | parent `91B0F478ACB7B6F13463C35B268A30D2806F583402631A56F54CE2EB70D1EC71` |
| 1.11 | `A0D787681DE5CAECDA03FEA1536DAC0C07E6CA5CFBFE3B5FBB700CBE77C86FB6` | `3209FEAACD1E798B521A193EEB081D92C96881A70A18254EF24A58E911A4FD97` | parent `8B3E5DA58494F1DBA77410960753B0BF646425A1ADA491D8242CC71E6C7A59CA` |
| 1.11.1 | `8B37FD286B2DAC1B343BD708B80284B43BB31C61BAFC63BDCF6620BA3A59065C` | `5A906DE1D40A4A5BA8AEDC9A6A9DC704E0ED862DF177CF967FB1A9454765FA96` | parent `0D5F4697D4E4CE53B36D0DBFEC89D44830EB76753A6643B49E42F0857753066D` |
| 1.11.2 | `87FE94FA6CBF7ABA18B9A5E3401664439EB8EBA9958173DA8FBCD05CC7AD948B` | `CE8104A17CE783DF639CF9726E7B1CD0936563EAA7BA308603335BBE05D49440` | parent `D908C2AF76598AD26999563BFEF0089F1D3C8C32483EEE6B108F0DEFA2773DF0` |
| 1.12 | `6A9D5F420B13625195B149C0D9468CF1284D5EB7DC1B2A46CF01B952A27EE063` | `80F091BF32166C88CF8BBD31CAF72D84FA16224410733C7D2A0F00563F294A0A` | nested `27515E5F4C71E7DB2F035A81716507128D77466C75380AD1EF7D140C06401467` |
| 1.12.1 | `4DAB93EA3178A28C616947004ECDC514E6542EDCA8C62EF83F686CCF6B2CE640` | `80F091BF32166C88CF8BBD31CAF72D84FA16224410733C7D2A0F00563F294A0A` | nested `0FF5246E8D29C04554CC9EC93AE9B4ABEF539652325B6C0AC822851D7186A9C8` |
| 1.12.2 | `E4E0FDBE07A7D0A0AE4A70CBB6739A287C9D045A4A12B409895C220B5D91FE1E` | `80F091BF32166C88CF8BBD31CAF72D84FA16224410733C7D2A0F00563F294A0A` | parent `B8A37CCFCCD2AAC5F40F5E34FEC85873DBDFA93A266043C103E592E4A8C949DA` |

Representative exact locations (the above per-version digests bind the corresponding exact alias files):

* `Entity.java`: constructor initializes width/height and positions the box (1.10.2 lines 161–192; 1.11 lines 165–198; 1.12.2 lines 170–203); `setSize` is the field/size-box writer (1.10.2 lines 264–275; 1.11 lines 269–289; 1.12.2 lines 274–292); `setPosition` recomputes the box from current dimensions (1.10.2 lines 282–289; 1.11 lines 296–304; 1.12.2 lines 298–305); `setShape` only stores the box (1.12.2 lines 2226–2228). Movement/collision shape writes translate/restore boxes without changing dimension fields.
* `PlayerEntity.java`: constructor chains to `Entity` (1.10.2 lines 144–145; 1.12.2 lines 142–143); pose selector (1.10.2 lines 296–320; 1.11 lines 295–319; 1.12.2 lines 291–315); constructor/reset size normalization to `(0.6F,1.8F)` and sleeping/wake size calls are captured by the exact PlayerEntity hashes above. The selector body SHA-256 is `848A1FE07E3D14340968AB4BA35455662DEE494E8ABB7FEA21D944E0555DEB31` across the covered 1.10–1.12.2 stable aliases, as recorded in the frozen alias closure.
* `LocalClientPlayerEntity.java`: exact aliases delegate from local client construction to `ClientPlayerEntity`; `ClientPlayerEntity` extends `PlayerEntity`. `ServerPlayerEntity` also extends `PlayerEntity` and contains no size override in the player hierarchy. The class declarations and constructor delegation were checked across all nine alias trees.

No broader finding is proposed. This gate establishes only whether the precondition `width > 0.6F` belongs to ordinary vanilla local-player lifecycle for the named aliases; it does not resolve predicate-overlap reachability or any unrelated size/box behavior.

Terminal status: COMPLETED