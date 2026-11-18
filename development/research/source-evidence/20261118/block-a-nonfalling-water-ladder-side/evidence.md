# Source audit: occupied dry ladder in the nonfalling water-current side gate

Verdict: **SOURCE-CONFIRMED CANDIDATE** for one dry, occupied, nonblocking side cell in ordinary `LiquidBlock.getFlow`. This establishes source eligibility and a durable source fixture; it is not runtime movement parity or full-pair acceptance.

## Bounded operation and exact source set

The candidate is only the historical dry-horizontal-neighbor branch in `LiquidBlock.getFlow`: if the side has negative liquid depth and its block material does not block movement, sample the cell one block below that side. It excludes falling-liquid face obstruction, ladder climbing/occupancy, body-water contact, and other fluid behavior.

Read-only source root: `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/<version>/ornithe-feather/`. The 24 exact stable IDs checked were `1.8`, `1.8.1`–`1.8.9`, `1.9`, `1.9.1`–`1.9.4`, `1.10`, `1.10.1`, `1.10.2`, `1.11`, `1.11.1`, `1.11.2`, `1.12`, `1.12.1`, and `1.12.2`.

Every source directory was paired with its exact release identity and Feather Gen2 build.2 pin, not a moving alias. The 17 mapping-local `.decompile-provenance.json` records (1.8–1.8.8, 1.9–1.9.3, 1.10, 1.10.1, 1.12, 1.12.1) contain both the exact `https://meta.ornithemc.net/v3/versions/feather/<release>` metadata input and `feather-gen2/<release>+build.2/feather-gen2-<release>+build.2-mergedv2.jar`; all six relevant source hashes matched their recorded `sources` hashes. The seven version-root ready records (1.8.9, 1.9.4, 1.10.2, 1.11, 1.11.1, 1.11.2, 1.12.2) name the exact version, `ornithe-feather`, and `feather-gen2-<release>+build.2` merged jar and Tiny file; their source-manifest and artifact-manifest hashes matched the ready sidecars, and the six relevant files matched the source manifest. There were no release-ID aliases or path substitutions.

Raw SHA-256 of the selected provenance/ready identity record, in release order:

| Release | Record | Record SHA-256 |
|---|---|---|
| 1.8 | mapping-local provenance | `52c8312c34a1a491d4a200c84e90f5d8d95c7e5caa9571f864d8e70f9737db05` |
| 1.8.1 | mapping-local provenance | `f3a3baa039f0e0ade6e656dfa11a4529909a4b709ac489703c4250262cefb1ca` |
| 1.8.2 | mapping-local provenance | `8ad11ea36f2dda39ae30d57daa53316a8c0889a2f3c0c84ee5d79b9b21c25472` |
| 1.8.3 | mapping-local provenance | `7da5f772d7ed1433cb3da23861723ebaec7cb714ce49c4d1a5c30499fec3afed` |
| 1.8.4 | mapping-local provenance | `b2207bf9ab2a7561bf73ac92a63cc67760ccc6fd9e2378ff1e10710ecbb03a00` |
| 1.8.5 | mapping-local provenance | `986d25dfd11927abed51c92643e5beda48237da875fbcdf1a48a930456ea99ce` |
| 1.8.6 | mapping-local provenance | `c96f635864596f6cbd6e34ba7ba184c22a8139b25f099cc8fbbff46a85286ea9` |
| 1.8.7 | mapping-local provenance | `6c6a5b64243ae7931ea1fa72a6c33440d5eec4070461cba80cdf51bf95dd93ef` |
| 1.8.8 | mapping-local provenance | `588f83dc64ce0bbd0f11880d2da8e59d4d097d43225025a2a74a2b26cf909ac1` |
| 1.8.9 | version-root ready | `fe0ec792349ae43c91d0a30f23660c94b2f5c8d31a29643f73ed0de367110b95` |
| 1.9 | mapping-local provenance | `8daacb7af8f210e716ee4b34c43b8a5d6f6b4b19831d2fd725cbe16ecf330a1a` |
| 1.9.1 | mapping-local provenance | `e9fb0da6511597e2de953a143f9570b82243e5b9504603686e557f06271da64a` |
| 1.9.2 | mapping-local provenance | `9928b467deb35c7e7f891b4fee1627a68feecdf233c2076909fd4834b0380510` |
| 1.9.3 | mapping-local provenance | `43325cdd53ca34b88ee9af8464782b323a948b98c8a556c4761f1e5737b84c3a` |
| 1.9.4 | version-root ready | `3d0a810d9f3a93233d880ca07ffc806ea232230d33197197ad50ba69181171c6` |
| 1.10 | mapping-local provenance | `df266e2c3009dd1a873c1665c30fe388078ed1c6227668e10cde87409a18c6da` |
| 1.10.1 | mapping-local provenance | `a9a50497930848bf0ddec732b6d424e947f643a8fa6290e01425ac2bbfce219f` |
| 1.10.2 | version-root ready | `4c6d236e1d06ffc365452c48e73dfa2bf2414bbf4ea67a24a7010c5583906a2d` |
| 1.11 | version-root ready | `3c4102230dba0f9d2d07f752bbd2a19d2717375f6e31fb618c0450b7fd82bd45` |
| 1.11.1 | version-root ready | `454036a978074d3a34359dfebf03d6ef3e3335d1318b88d6b17e4f1f5ab566cf` |
| 1.11.2 | version-root ready | `ceabfb266993567aab8c867525365e18aa0a0d7ba5a2cb7b6cfcc83d21318c40` |
| 1.12 | mapping-local provenance | `27515e5f4c71e7db2f035a81716507128d77466c75380ad1ef7d140c06401467` |
| 1.12.1 | mapping-local provenance | `0ff5246e8d29c04554cc9ec93ae9b4abef539652325b6c0ac822851d7186a9c8` |
| 1.12.2 | version-root ready | `b0aeec721e5c0af02b33d9e217cb8272889fa139ded1ecc2a31e42b930138f7a` |

## Source behavior

The complete source path checked on every release is `net/minecraft/block/{LiquidBlock,FlowingLiquidBlock,LadderBlock,Block}.java` plus `net/minecraft/block/material/{Material,PlantMaterial}.java`.

- `Block.java` registers the ordinary `minecraft:ladder` as `new LadderBlock()` in all 24 releases (historical numeric registry ID is 65). `LadderBlock` calls `super(Material.DECORATION)` in every release.
- `Material.DECORATION` is a `PlantMaterial`; `PlantMaterial.blocksMovement()` returns `false` in every release. That is the predicate actually used by the ordinary `LiquidBlock.getFlow` side gate. Early sources obtain the material through the block; later sources read it directly from the side `BlockState`. It does not query ladder collision shape, `isSolidRender`, or climbing behavior.
- The placement state supports a horizontal facing. Through 1.11.2, `LadderBlock.canSurvive(world, pos, facing)` checks that the block at `pos.offset(facing.getOpposite())` is solid. In 1.12, 1.12.1, and 1.12.2, the corresponding `canAttach` check requires a solid face shape and rejects signal sources. In both forms, a stone support at `(2,64,0)` validly supports a WEST-facing ladder at `(1,64,0)`; its neighbor callback removes the ladder only if that support check fails.
- In every release, the dry-side current routine first obtains a negative liquid depth for the side, checks `!sideState.getBlock().getMaterial().blocksMovement()`, then samples `side.down()`. A nonnegative lower depth contributes `lowerDepth - (centerDepth - 8)` along the side's horizontal offset; the result is normalized before return. This is separate from `FlowingLiquidBlock.isLiquidBlocking`.
- `FlowingLiquidBlock.isLiquidBlocking` independently returns true for the ladder in every release (`block == Blocks.LADDER`; in the earliest mapped source the same check is present). Liquid propagation uses this predicate for horizontal and downward destination admission. This is the needed physical separation: the ladder's material admits the current sampler's below read, while the fluid spread gate prevents water from replacing the ladder.
- The exact ladder-specific propagation obstruction was explicitly checked in 1.8.9 and traced through every distinct raw `FlowingLiquidBlock.java` source hash group. The routine is unchanged in structure across the six raw-hash groups covering 1.8–1.12.2. The ordinary current gate was checked across all seven raw `LiquidBlock.java` groups; local variable and mutable-position API changes do not change the gate, below sample, weight, or normalization order.

Representative source lines (the listed source files are hash-bound above):

| Release | Source range | Evidence |
|---|---|---|
| 1.8 | `LiquidBlock.java:129–151`; `FlowingLiquidBlock.java:183–189`; `LadderBlock.java`; `Block.java:885`; `Material.java:20`; `PlantMaterial.java:20` | Earliest exact source: side gate and below read, ladder special fluid obstruction, decoration registration/material override. |
| 1.8.9 | `LiquidBlock.java:129–160`; `FlowingLiquidBlock.java:184–190`; `LadderBlock.java`; `Block.java:965`; `Material.java:20`; `PlantMaterial.java:20` | Exact midline source, including independent propagation obstruction. |
| 1.9.3 | `LiquidBlock.java:131–165`; `FlowingLiquidBlock.java:186–192`; `LadderBlock.java`; `Block.java:861`; `Material.java:22`; `PlantMaterial.java:20` | Mutable position and renamed aliases preserve side gate and fluid obstruction. |
| 1.10 | `LiquidBlock.java:131–165`; `FlowingLiquidBlock.java:186–192`; `LadderBlock.java`; `Block.java:866`; `Material.java:22`; `PlantMaterial.java:20` | Checked the portal/structure-void change does not affect the ladder case. |
| 1.12.2 | `LiquidBlock.java:139–173`; `FlowingLiquidBlock.java:186–192`; `LadderBlock.java`; `Block.java:890`; `Material.java:22`; `PlantMaterial.java:20` | Latest exact source in the assigned range; same ladder/material and flow admission. |

For hash-group reproducibility, the raw SHA-256 groups for the classes that carry the bounded claim are:

### `LiquidBlock.java`

- `1.8`: `e4ef9385d025aa4672d4b6a55afc72803267d0721469bf036217ab30a7760fe3`
- `1.8.1`: `36d345c4b0985902ed265602e391982cdc13bc447906e219d3efe568aed4676a`
- `1.8.2–1.8.9`: `1537fd6b1bca65e0f910f9a12bcd3bb27f5a86be7f13a1caa380c5e66544f1d5`
- `1.9–1.9.2`: `7e76c5ebb1a59d36be3517d09e5df60a0eb206ff5ace5ce889eac9152b53c6e8`
- `1.9.3–1.10.2`: `b988d19e379d01d291cb014cb05e1579311c589654ce9ff4faa7f43bbba0533e`
- `1.11–1.11.2`: `c91618c8445dd488dd61f4a01aed0799dae2faaf3585bc6f2d5164891e988244`
- `1.12–1.12.2`: `00f7995dd49a48bf97316e30b3d6e41d8ee1e65dd212f3dc64891883621c4260`

### `FlowingLiquidBlock.java`

- `1.8–1.8.1`: `d52c2ca1f9d145960da1342f2b74458a4327e0803d0d85a07c715d3079e57f8c`
- `1.8.2–1.8.9`: `16b30954bbb5bc9b40072ad19304a7efe8ac2483ee1a4baeda777fb4145daaf0`
- `1.9–1.9.2`: `66dce05969a86a163965a3e473e3e00b14e0da7dacffb4c75064c13605e3511b`
- `1.9.3–1.9.4`: `12bc1bdbcb17a4fa321a14094144919d5a6c00058a3d28c5f8383a1465d47104`
- `1.10–1.10.2`: `eac6b26e87f4b759bfea9adb1d1d7edcc4ad2b7c6a88677feab9fc83a7b50d79`
- `1.11–1.12.2`: `6be4da4408c29a730fb33227514d8ad93f20b0adc87eda66796d069e3ae93be2`

### `LadderBlock.java`

- `1.8–1.8.1`: `e94f204ebda56ce831be472cd30e3138a96306be5e692111cbbc52aca1e13177`
- `1.8.2–1.8.9`: `c13b123f8fd7427d7f646da3b789eb97d4e54aa2fe2860f66cf4fd1302865106`
- `1.9–1.9.2`: `42d3c814f8ba54b4fa09a979420f65e0ff73217fe7f36f0f06533207e97032ce`
- `1.9.3–1.10.2`: `f411d494a4f8c87b2b23d182527713d3b0c2fad4da329b82b84ea301a9d65fcb`
- `1.11–1.11.2`: `3ceafedeb39e0bc3862bed2cc784f490340d832563764adb225ecea40ee83ee7`
- `1.12–1.12.2`: `c9d59cd266af92a731710fda0e2a8e60c61fb07131fecdd3b04759277ae24d94`

### Material and registration support

- `Material.java`, `1.8`: `a27bcc70e2712f150d8428773f8c2aafe98af9100a4fd05653057044d7d248a9`
- `Material.java`, `1.8.1`: `c05f393fe2b5e77b96dac85250751bed03cad2be6377fc5c10209904c1f185bf`
- `Material.java`, `1.8.2–1.8.9`: `017713d76afe726ca243ce32cbc35c13d3f0f0e7e5d90d122f81103cc2ca1bd2`
- `Material.java`, `1.9–1.9.4`: `f198b08007c0acbe4e2737f7e484a9c85ec183220daef03b954cc90d0af95d19`
- `Material.java`, `1.10–1.12.2`: `5d682e9ea5a30540e62a68879fbdabd2dc1fb79781a8350c61370c1166654a7d`
- `PlantMaterial.java`, all 24 releases: `c3284411c2ab826853a1aca3c5b44d86fcc3a548cb9655cce4efa718bf419e59`
- `Block.java` registration groups: 1.8 `4a4e6dfde24a135ec87c359fd98f0380664e4bacaf679815c9016cb1ba0d814`; 1.8.1 `4f645cb1f25248ba16649d9d1ab17b3dbe4c7316191deb982f641beff437e67c`; 1.8.2–1.8.9 `ea10f05106a3cf7189aec85236a7ecf9c7106a717f37bed583b5ea4adeffa528`; 1.9–1.9.2 `91c053e11d6bedaac292b6be2f5670e5a5c4522f175c1e227163eeb5499780b2`; 1.9.3–1.9.4 `e62ece80c6a7e7121346f65f8fdfd9b148c29441a27de9f568bba9afe1d84fe6`; 1.10–1.10.2 `1971dbc284d511e2ed366f77bc77fd8cd07174baad3e7732e908d3daeb640c01`; 1.11 `3fe8fdabe68da83fcfa01c0e6b8f5272fe81d71f6bf553506bdcef54757b0488`; 1.11.1–1.11.2 `ab873436b24487ab0ee8055bf7a478349c7f7978398aea8e387796a6c820996a`; 1.12–1.12.2 `e4a90eca411e7b0e14f5018f7385ec29891f6cdf1a1e917fa648b5712f9b33a1`.

## Concrete durable source probe and numeric trace

All coordinates below are block coordinates in one loaded, ordinary world. They specify block states only; this report did not launch a world.

| Position | State |
|---|---|
| Center `(0,64,0)` | Still water source, `LEVEL=0` |
| East side `(1,64,0)` | Ladder, `FACING=WEST`, `Material.DECORATION` |
| Ladder support `(2,64,0)` | Stone/full solid |
| Below side `(1,63,0)` | Still water source, `LEVEL=0` |
| Center support `(0,63,0)` | Stone/full solid |
| Below-source support `(1,62,0)` | Stone/full solid |
| Center's west/north/south side and all four horizontal cells around the below source | Stone/full solid |
| Other ladder horizontal surroundings as needed | Stone/full solid |

The ladder is genuinely placeable and support-valid with WEST facing because its opposite-facing block at `(2,64,0)` is solid. The water source directly west of the ladder cannot spread east into it: the separate `isLiquidBlocking` routine explicitly treats ladder as a propagation obstruction. The lower source has stone below and stone at every horizontal neighbor; the center source has stone below, three stone horizontal sides, and the ladder as its fourth side. A source receiving an ordinary neighbor update can be converted temporarily to flowing level 0; the level-0 flowing tick converts it back to a source, and with the stated solid/ladder boundaries its spread set has no admitted destination. The lower source is diagonally adjacent to the center, so it does not create a same-height side contribution. These are source-traced fixture invariants, not a runtime test.

For the target ordinary water current read at the center, center depth `i=0`. The east ladder side has negative depth. Its actual queried material predicate is false (`DECORATION` → `PlantMaterial.blocksMovement()` false), so the code reads one below the ladder: the water source returns lower depth `j=0`. The historical weight is `j - (i - 8) = 0 - (0 - 8) = 8`, multiplied by the east offset `(1,0,0)`, giving `(8,0,0)` before the method's final normalization and **`(+1,0,0)`** from `getFlow` after normalization.

Comparator: replace only the east ladder with a full stone block and keep the below-source state. Stone's movement-blocking material suppresses the below read. The other three sides are also solid, so the sum and returned `getFlow` vector are **`(0,0,0)`**. This isolates the historical material gate from a general below-water contribution.

## Scope boundary

The evidence supports one nonfalling ordinary liquid-current material gate and one attached vanilla ladder state. It does not establish full-pair source coverage, exact intermediate change boundaries beyond the enumerated stable releases, client runtime parity, or an implementation recommendation. No wiki, product implementation/history, build, test, decompilation, game, or runtime was used.
