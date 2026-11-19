# Mixed stone slab alias closure (source only)

## Verdict

**Source alias/state/provider gate: CLOSED for the supplied mixed-stone/top-slab candidate.** The raw 1.13.1/1.13.2 `minecraft:stone_slab` identity is the historical alias that 1.14's data fixer renames to `minecraft:smooth_stone_slab`; the new `minecraft:stone_slab` remains a distinct registered block. The candidate's top, dry state exists on both sides, returns the top-half shape through the movement collision-shape query, and reaches the ordinary client AutoJump callback through the normal movement method. 1.14.1 keeps both IDs and the same state schema. 26.2 still registers both IDs separately with the same `type`/`waterlogged` shape model.

This closes only the requested raw-ID/property/provider/callback alias question. It does not independently re-audit the full AutoJump pair, recalculate all supplied coordinates, or establish runtime parity.

## Exact evidence

- **1.13.1 and 1.13.2 (Feather Gen2 build.2):** `Block.java` registers only `minecraft:stone_slab` as the stone slab block. `SlabBlock` defines `HALF` and `WATERLOGGED`, defaults to bottom/dry, exposes those properties, and returns `TOP_SHAPE` when `HALF == SlabType.TOP`. `SlabType.TOP` serializes to `top`. The class has no separate `smooth_stone_slab` registration. Therefore the concrete legacy state is `minecraft:stone_slab[type=top,waterlogged=false]`.
- **1.14 (Feather Gen2 build.2):** `DataFixes.java` schema 1802 explicitly applies the “Rename sign blocks & stone slabs” mapping `minecraft:stone_slab` → `minecraft:smooth_stone_slab`; it separately maps the sign item and stone slab item the same way. `Blocks.java` registers `stone_slab` and `smooth_stone_slab` as separate `SlabBlock` instances. `SlabBlock` preserves `HALF`/`WATERLOGGED`, and its top state returns the 8–16/16-high top shape. Thus the legacy state aliases to `minecraft:smooth_stone_slab[type=top,waterlogged=false]`; the distinct 1.14 `minecraft:stone_slab` is not the legacy ID.
- **1.14.1 (Yarn build.10):** `Blocks.java` again registers separate `stone_slab` and `smooth_stone_slab` IDs. `SlabBlock.TYPE` and `WATERLOGGED` form the state schema; enum field `field_12679` serializes as `top` (shown by `SlabType.java`), and the top enum case returns `TOP_SHAPE`. The stated dry top state is valid.
- **26.2 (raw unobfuscated):** `BlockItemIds.java` gives separate IDs `stone_slab` and `smooth_stone_slab`, both registered separately in `Blocks.java`. `SlabBlock` still uses `TYPE` and `WATERLOGGED`, defaults to bottom/dry, and returns `SHAPE_TOP` for `SlabType.TOP`. `SlabType.TOP` serializes as `top`. Both identities and the dry top state remain present; this source check does not claim a mapping between 26.2 native identities and old profile state IDs.
- **Ordinary callback aliases:** in both 1.13 releases and 1.14, `LocalClientPlayerEntity.move(...)` calls `autoJump(float,float)` after `super.move(...)`. In 1.14.1 Yarn, `ClientPlayerEntity.move(...)` calls the unmapped method `method_3148(float,float)` at the corresponding point; that method contains the same AutoJump gates and state/shape query sequence. The 1.13/1.14 start-state queries use `BlockState.getCollisionShape(world,pos)`; 1.14 adds `CollisionContext.of(this)` and passes it. The nearby collision-sweep stream and later stand-clearance queries are ordinary callback work; no manually invoked or unsupported entry point is needed. For the supplied ordinary supported movement, the callback alias is therefore available in every requested old version.

For the task's supplied scene, the dry top slab at `(1,8,1)` has a source-defined top-half collision shape, while the full stone/cap remain the supplied separate lander arrangement. This confirms the formerly uncertain state/provider alias for that scene; it is not a new geometric traversal proof.

## Source identities and file hashes

Source roots are the exact roots assigned in the task: `1.13.1/ornithe-feather` (Feather Gen2 build.2), `1.13.2/ornithe-feather` (Feather Gen2 build.2), `1.14/ornithe-feather` (Feather Gen2 build.2), `1.14.1/yarn` (Yarn build.10), and `26.2/unobfuscated` (raw names/IDs). Read-only SHA-256 checks of the complete relevant files follow. The 1.13.1 and 1.13.2 selected files have identical full-file hashes.

| Release | File (relative to repo root) | SHA-256 |
|---|---|---|
| 1.13.1, 1.13.2 | `decompiled_minecraft/1.13.1/ornithe-feather/net/minecraft/block/Block.java` (same bytes as 1.13.2 path) | `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4` |
| 1.13.1, 1.13.2 | `.../net/minecraft/block/SlabBlock.java` | `2e7ff3fcfa5faa83aa4af7ea9b68f07af8d8fe7047e08ee3a9d7d37b78c7128f` |
| 1.13.1, 1.13.2 | `.../net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29b29d8d9bf42bf` |
| 1.14 | `decompiled_minecraft/1.14/ornithe-feather/net/minecraft/block/Blocks.java` | `b568c300287713c9b5ba59c6382f60014830163008fcd3613fbe54373ed43042` |
| 1.14 | `.../net/minecraft/block/SlabBlock.java` | `9e0eb825d3224c4720ca5fa94616bef65b691226dc090e2594bf801dfd54c322` |
| 1.14 | `.../net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `2b1ad3b3416949a9da2607a3ec2251aa69b2d9eebd76638c92e20749d625338f` |
| 1.14 | `.../net/minecraft/util/datafix/DataFixes.java` | `26273c0d51646b2061bcce5356e5ebe1a304018b22ed46fdc5392fccad750d32` |
| 1.14.1 | `decompiled_minecraft/1.14.1/yarn/net/minecraft/block/Blocks.java` | `f5ba7169237468c68c085ecbf101e0e1c620013c00744a20dfae7c8af97c74dd` |
| 1.14.1 | `.../net/minecraft/block/SlabBlock.java` | `3ebf0457f6af0cf81aa6a66927b25a8bd213c3aecbad61bfc79f504ae6abb72f` |
| 1.14.1 | `.../net/minecraft/block/enums/SlabType.java` | `a56108aa5643708024374ef6a2a824395da3a608e4f610e863b3288b24fdc409` |
| 1.14.1 | `.../net/minecraft/client/network/ClientPlayerEntity.java` | `6af7e75e80619ad77ce2c84692136b0a1d562a44d0e7910dcc3f6040b2dc9933` |
| 26.2 | `decompiled_minecraft/26.2/unobfuscated/net/minecraft/references/BlockItemIds.java` | `d9d57f2f04801da5d8d583c23b87b08f45d8b6c681980d1a77308753c801bac6` |
| 26.2 | `.../net/minecraft/world/level/block/Blocks.java` | `f3f2faeed23e9697407069a1d523107491590b8710175523ea05294d5bd00435` |
| 26.2 | `.../net/minecraft/world/level/block/SlabBlock.java` | `fd5f80e73fd577605ff2924adccfcd7ba5037bb400e481cd142363a385c45ba1` |
| 26.2 | `.../net/minecraft/world/level/block/state/properties/SlabType.java` | `e513c3bf499ce936576d6b5ef6062cab2965bf536d87fd37e54e8961009eab7d` |
| 26.2 | `.../net/minecraft/world/level/block/state/properties/BlockStateProperties.java` | `c6e4b49663a09110e96a3b51628f92defd83da9267a5d569a8ac8fc0c8893cd0` |

`1.14.1/yarn/.decompile-provenance.json` is present (SHA-256 `308537c5adec3104c5ca243eb5010554f00978635ed74f0d7801c804dd4d4907`); its inputs pin Yarn `1.14.1+build.10` artifact SHA-256 `f9462af25135abeb22cbdec829cc8618b1a8dd902f17259abfbe7399a222a630`, intermediary artifact `486f4e72a87d697c5d9e155a49158bec28117faa73e3f1201c3f8a616b42569d`, and raw 1.14.1 client SHA-256 `7194d1326cf796f62ac55f3cb56f851c232895639ae27510f3207d5a73f91814`. The other roots use the version-level sidecars `<mapping>.provenance.json`, `<mapping>.ready.json` and `<mapping>.sources.sha256`; they are present for all requested versions. Every relied-on source file checked against its source list exactly matched its listed SHA-256 (1.13.1: 5/5; 1.13.2: 5/5; 1.14: 7/7 including the rename implementation; 1.14.1: 5/5 against its embedded `sources` map; 26.2: 5/5 including the raw ID constants). Each version-level `ready.json` source-manifest digest matched the adjacent `.sources.sha256` file. For 1.13.1 and 1.14, ready markers also bind the provenance file by SHA-256. The 1.13.2 and 26.2 ready markers do not contain a provenance SHA-256 field; their provenance records reference the ready marker by filename, so the sidecars lack a cryptographic cross-link to each other. Their exact source bytes remain directly pinned by the ready-checked source manifest. 1.13.2 ready/provenance identify Feather Gen2 1.13.2 build.2 by the mapping artifact filenames, although its provenance omits a resolved-coordinate field; 26.2 provenance explicitly identifies the mapping option as unobfuscated.

## Member hashes

Member hashes are SHA-256 over the inclusive displayed source lines, decoded and re-encoded UTF-8 with LF line endings and one final LF. The full-file hashes above are over the exact files as stored.

| Member/range | SHA-256 |
|---|---|
| 1.13.1 and 1.13.2 `Block.java:1555` registration | `d07e0ed1b8884092f9516c18f6259b1b3ac5a84f0a36ce1014b389142031d0a9` |
| 1.13.1 and 1.13.2 `SlabBlock.java:24-59` state schema/top shape | `55cb8dc07a21c8fbbc38e1497807dec77dd28dbb3affafdc2b2a38e12b16888d` |
| 1.13.1 and 1.13.2 `LocalClientPlayerEntity.java:871-915` move/callback and initial provider stream | `1662cd31b8f11dd8678ff86043d7da3718618b0e9d061e1f708191bddd50472c` |
| 1.14 `Blocks.java:1313-1316` two registrations | `618aa3e0b6c306bd99f572984fd96357ddcdaeeb58f437d49aa2cf02eeceb22e` |
| 1.14 `SlabBlock.java:24-53` schema/top shape | `4ba5a951def7c03aee9d3ac4b4c89d0ac06fe5bf4e9a3a6791e3d28bf08f5bcc` |
| 1.14 `LocalClientPlayerEntity.java:847-894` move/callback and initial provider stream | `7948ecc084fd53227d2c87846cb6a6df9ef2d79f9c7f3f8b538e6493b7bbe74d` |
| 1.14 `DataFixes.java:452-476` block/item rename mapping | `08eaaf22d9c013e0ad09c2f12a124f7cb6623b5b1c11938b4760518ae9158ea9` |
| 1.14.1 `Blocks.java:1355-1358` two registrations | `d091f27083d9b81a8dc4d3a638f81ac32edacec5169ef01c6913e176dbdddc17` |
| 1.14.1 `SlabBlock.java:23-52` schema/top shape | `a82cacb216648c122717c9889b9cabc8b2c793ea69ffb3ed05e1affc86732559` |
| 1.14.1 `SlabType.java:5-21` Yarn enum serialization | `7df4990f7c85c5d8d815b13c5f346cae962f544ebac68d1a5a49d7ea5bbe00db` |
| 1.14.1 `ClientPlayerEntity.java:835-891` ordinary move/callback | `d89ed4017d6fb4e838598bb81a048d85fba0df70da89762003717ff2d9156718` |
| 26.2 `BlockItemIds.java:264-265` raw ID constants | `5b295834b73b017cbb73634b5fe40ccf743d8a631d90fcad19f4a28f72fd7779` |
| 26.2 `Blocks.java:3296-3305` separate registrations | `f12a42c7f90ea33365af8f1a75749e4d0c890b59f4bb395251399934efff6c2e` |
| 26.2 `SlabBlock.java:31-65` schema/top shape | `769ee2a297db0bf9b5a5febe9e364fc51c5da1cc0c1b9d7efef600a2316b22ab` |

## Serialized property-name evidence

`Properties.java` in 1.13.1/.2 declares `WATERLOGGED` as `waterlogged` and `SLAB_TYPE` as `type`; 1.14 and 1.14.1 keep those same serialized names. 26.2's `BlockStateProperties.java` does too. The selected complete property files have these SHA-256 values:

| Release(s) | Property file | SHA-256 |
|---|---|---|
| 1.13.1 and 1.13.2 | `decompiled_minecraft/1.13.1/ornithe-feather/net/minecraft/state/property/Properties.java` (same bytes in .2) | `3882c1b832ac8b2b16f5a83978da441ab03cdfe66f053a873342758045827f4c` |
| 1.14 | `decompiled_minecraft/1.14/ornithe-feather/net/minecraft/state/property/Properties.java` | `9b524af5065100919c60667778e38986380c9df954ef70a9d0ac0a739132ddca` |
| 1.14.1 | `decompiled_minecraft/1.14.1/yarn/net/minecraft/state/property/Properties.java` | `c28bcb45c6c1e748369f4db3ffa6c6d9b12b16db1d683f272907c1deb6c87626` |

The 26.2 `BlockStateProperties.java` full-file hash is already listed above. Additional exact member hashes for the property declarations (same normalized-line algorithm) are:

| Member/range | SHA-256 |
|---|---|
| 1.13.1/.2 `Properties.java:44` waterlogged name | `5403f3813d04ebf463b7ac25be7212e9bb818495de345e44077d0251dc0135ab` |
| 1.13.1/.2 `Properties.java:98` slab type name | `e0c5f6b40906174e78edf3d5fe35dc691630fd8c844abcb908c741e301a54004` |
| 1.14 `Properties.java:50` waterlogged name | `5403f3813d04ebf463b7ac25be7212e9bb818495de345e44077d0251dc0135ab` |
| 1.14 `Properties.java:108` slab type name | `e0c5f6b40906174e78edf3d5fe35dc691630fd8c844abcb908c741e301a54004` |
| 1.14.1 `Properties.java:50` waterlogged name | `6f7bded8a123d4457679dbdc810fd2cb080d449200be1cc6add5099c5afacf70` |
| 1.14.1 `Properties.java:108` slab type name | `223ea2de4d687a5157bf039f200799798a3e8a962efa7f2e9eab2a382bfe1b75` |
| 26.2 `BlockStateProperties.java:44` waterlogged name | `6f7bded8a123d4457679dbdc810fd2cb080d449200be1cc6add5099c5afacf70` |
| 26.2 `BlockStateProperties.java:135` slab type name | `223ea2de4d687a5157bf039f200799798a3e8a962efa7f2e9eab2a382bfe1b75` |

The 1.14 rename implementation is also pinned: `net/minecraft/unmapped/C_81767953.java` rewrites the serialized `BLOCK_STATE` `Name` and leaves the rest of the dynamic record in the remainder unchanged, so its `Properties` payload carries through the ID alias. Full-file SHA-256: `540ead0a554858ad2c9fcb9fc6602daa490e013a890f255a08c19e0b48856c89`; member lines 23-41 SHA-256: `8aff25675b4a363b3957712a2ae8631f9a0dd9239577c6dae034c79b698ba46f`.


## Exact sidecar pins

The 1.13.1/.2 and 1.14 `SlabType.java` full-file SHA-256 is `20ef3591cf5f5cf83886d7ab5034fd8402418f68e517180cbba2fe0c7535f697` (all three selected files are byte-identical and listed in their corresponding source manifests).

| Release/mapping | `.sources.sha256` SHA-256 | `.provenance.json` SHA-256 | `.ready.json` SHA-256 | Ready-to-source link | Ready-to-provenance link |
|---|---|---|---|---|---|
| 1.13.1 / Feather | `21770b01ff3bf443db2e9e1b6df7db25d2fd6b7a72a86228259269c932d3abba` | `c28ed59f2c507b6133f7677742d316d38e9993eaf1fbb6c2054bd68d1085e904` | `f098b3a08b37276cc1a465a8a122df8de76d68786c854b92bac1ca51a79670de` | match | match |
| 1.13.2 / Feather | `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211` | `e25950b7855fc4f2aee1a662f0be6b0af9f28788d9e27293075cfc5a3aa5d383` | `1d1c644c1deb05c02d11dc575ad834f4f72489124701d9b2b90357f43dc37b38` | match | no provenance digest field |
| 1.14 / Feather | `68bc39e38bc501e7c9af315de4678263e1a64606b0b9780099187c7984ccbc98` | `bfb011801a64e9afaa4aca43e8a8ed40bf2a02f136ffc02e65b32e79315764fa` | `e0ecb319a63d5d664b87ad7c00638e1cdb9dc302ee2c05bbc05a88ce96d1e60b` | match | match |
| 26.2 / unobfuscated | `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894` | `a328f5333a1b27aadde4422b23159a323ae219ccd26f11207c1a5461366f27d1` | `f9406adb6bf7cb4c1ab0792a798ab2cb90e082ad4c2eee032c9f674d0ea8070f` | match | no provenance digest field |

The 1.13.1 and 1.14 provenance coordinates are `net.ornithemc:feather-gen2:1.13.1+build.2` and `net.ornithemc:feather-gen2:1.14+build.2`. The 1.13.2 ready marker names `feather-gen2-1.13.2+build.2` artifacts and its provenance names the same artifacts. 26.2 ready/provenance identify the version as `26.2` and namespace/option as `unobfuscated`. For 1.14.1, all five relied-on source files match their `.decompile-provenance.json` `sources` entries; that manifest also pins Yarn `1.14.1+build.10` and its raw client input as stated above.

The only remaining provenance bookkeeping limitation is the missing ready-to-provenance digest link for 1.13.2 and 26.2. It does not affect the checked source bytes: every relied-on file matches the source sidecar, and each ready marker's source-manifest digest matches that sidecar.
