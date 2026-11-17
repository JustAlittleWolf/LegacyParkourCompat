# 1.21.11 → 26.1.2 support-face and neighbor-state provider residual

## Scope

This is a source-only residual check of the shared support-face query and the provider inventory relevant to movement-adjacent neighbor state. It reuses the independently accepted `Entity.move`/collision and core-shape reports; it does not repeat their collision-shape comparisons. Its call-site boundary is the already reported fence/wall neighbor connection state selection plus the accepted `ScaffoldingBlock.getDistance` and `SnowLayerBlock.canSurvive` support queries. It makes no finding or full-pair claim.

## Exact source identities

- 1.21.11 Mojmap: root `decompiled_minecraft/1.21.11/mojmap/`; ready marker SHA-256 `0AD98D0EBD654650492C97EB58BC324D33864787F0CE10D99FAEC3AE2F5B804B`; source manifest `decompiled_minecraft/1.21.11/mojmap.sources.sha256`, SHA-256 `0C4D83FEF84C101D9DB88F331ACEC7960F3C8346228C28995E6CF57BBFCAE555`; provenance `decompiled_minecraft/1.21.11/mojmap.provenance.json`, SHA-256 `99C9FB741EB5E4AC8FB5780E2158DA324D7C8B6AFC179D494E5986F78FE29F72`.
- 26.1.2 unobfuscated: root `decompiled_minecraft/26.1.2/unobfuscated/`; ready marker SHA-256 `F24AF0F4D38543C2BD9C19B50F347D4E89D6027050E4B21AE070734843946BB7`; source manifest `decompiled_minecraft/26.1.2/unobfuscated.sources.sha256`, SHA-256 `54AE320660CA911A6D20514C965C33EB414FBEDD1AD6727BBAA95FE906AA23C0`; provenance `decompiled_minecraft/26.1.2/unobfuscated.provenance.json`, SHA-256 `8D727F880CC91F4A297E148C5B75976303D051457334BC689268FBDF631A012A`.

These identities and namespaces are the exact pair identities bound in the accepted `entity_move_collision/source-report.md` and `collision_provider_residual/collision-provider-residual.md`; canonical source trees remained read-only.

## Support-query path

For both endpoints, `BlockBehaviour.BlockStateBase.getBlockSupportShape(level,pos)` delegates to the block's `getBlockSupportShape(state,level,pos)`; the default block provider returns `getCollisionShape(state,level,pos,CollisionContext.empty())`. `BlockStateBase.isFaceSturdy` defaults to `SupportType.FULL`; its typed overload reads a precomputed cache when present and otherwise calls `SupportType.isSupporting(state,level,pos,direction)`. The cache constructor precomputes each direction × each `SupportType` through the same `isSupporting` operation using `EmptyBlockGetter.INSTANCE` and `BlockPos.ZERO`. The helper ordering, fallback, and cache behavior are paired at 1.21.11 `BlockBehaviour.java:276,663–664,865–870,895–929` and 26.1.2 `BlockBehaviour.java:297,692–693,886–891,916–950`.

`SupportType.FULL` checks whether the selected support-shape face is full; `CENTER` and `RIGID` intersect that face against their respective fixed support voxel shapes with `BooleanOp.ONLY_SECOND`. `Block.isFaceFull` extracts the directional face shape and delegates to the same full-block shape cache. The formulas and operation order match at both endpoints: `SupportType.java:14–35`; `Block.java:326–340` (1.21.11) and `SupportType.java:14–35`; `Block.java:340–360` (26.1.2). No support-query helper difference was found in this bounded slice.

Direct movement-adjacent consumers already covered in the accepted pair packets remain unchanged: `FenceBlock` and `WallBlock` use neighbor `isFaceSturdy` predicates while selecting connection properties; those properties select their collision shapes. `ScaffoldingBlock.getDistance` checks the block below's upward sturdy face before evaluating horizontal scaffolding distances, which can select a different scaffolding collision shape. `SnowLayerBlock.canSurvive` uses support-face state after its tag checks. The existing accepted collision-provider report explicitly leaves support-face state-provider coverage and neighbor-state population open; the accepted core-shape r2 explicitly excludes the full support-state set for scaffolding. This report reuses those exact accepted boundaries without reopening their claims.

## Provider inventory census

A source-tree search for `getBlockSupportShape(` found the same override class paths at both endpoints, plus the `BlockBehaviour` default:

`CeilingHangingSignBlock`, `ChorusFlowerBlock`, `FenceGateBlock`, `LeavesBlock`, `MudBlock`, `MultifaceBlock`, `ShulkerBoxBlock`, `SnowLayerBlock`, `SoulSandBlock`, and `WallHangingSignBlock`.

The inventory also includes `SupportType`, `Block`, `BlockBehaviour`, `ScaffoldingBlock`, `FenceBlock`, and `WallBlock` because their helper/consumer paths are in scope. All listed files were SHA-256 checked against their exact endpoint source-manifest rows and current bytes:

| Relative source path | 1.21.11 Mojmap SHA-256 | 26.1.2 unobfuscated SHA-256 |
| --- | --- | --- |
| `net/minecraft/world/level/block/CeilingHangingSignBlock.java` | `2B71B3DE0CE75DC4155F0F425ACC493D549284BA34C57E0D28D42FC881D8C0FF` | `E86FD628676765DE7BC4F5103B7C18FC450B5238D150FB82F564F444FE9A4FA6` |
| `net/minecraft/world/level/block/ChorusFlowerBlock.java` | `8829FA5707921145D85DBAF3EA2B770354483754653C76FC598D763F87973FD4` | `C89F9F49AD0BCC17ED856E0C385D9354817216009642DCA0DBBB3EC61DD0C529` |
| `net/minecraft/world/level/block/FenceGateBlock.java` | `9F82118F3202E696688B6A83E0FD3CB649169EA1AA25CFE7CF86302A8E40C2CE` | `E9E51B00BF59A66951741E0F0EF28D5D92815DF9591E4F229FDB838588D105DF` |
| `net/minecraft/world/level/block/LeavesBlock.java` | `BDF679C9BDAC2FD4A8D1CE67B59AAFC1800BDBAAD7866BAE3E24358A0F6FB329` | `81BDC1398E374DAC86B3A9C352ABE5D50B742C665E475D8D45FE11E3D1282770` |
| `net/minecraft/world/level/block/MudBlock.java` | `E3D72C3443EA99FB2895DF38CBDB3DD6FD2A4BA051E792C3CABFD17E5E4704F3` | `BFCD4D62FF875BB7FA0B230AEB2B7D0B0D060005BD84C502676F8BFE966C2C8A` |
| `net/minecraft/world/level/block/MultifaceBlock.java` | `4DC6E4C2396D7FF404AD3475F92CB853C84B6AEA267FDB3F8E72818D5AB346DD` | `07229D3657DE1AC039CABCA91CCEF80D84C356330F701E7C2FCDCEE2F8382B36` |
| `net/minecraft/world/level/block/ShulkerBoxBlock.java` | `A2F381AAA67CC103EAE473CCE8B45A4FD207CC17F604CEE984E0D93B3E9452E3` | `44E2C4FAD3728D567F774D4ABFEEAFE3C3FF7F5223344ABFE43CFCF9C1BF9E95` |
| `net/minecraft/world/level/block/SnowLayerBlock.java` | `E62FEBB4017BAA7935485E214CA114E1A6E7F3973A647A45D72EB427E247D5AB` | `312952C724788F30A2255BC34EF7D3242D4737067C23939F0BD0A76059B473CC` |
| `net/minecraft/world/level/block/SoulSandBlock.java` | `4B06907170B532C353A778406FC7361DB433656DD3E885A579FE9D27AB887A82` | `6BE030C181DD96298C219E6001EB69F05468531C22CBAB0DF2B7A066DF74A9FC` |
| `net/minecraft/world/level/block/WallHangingSignBlock.java` | `6B2C9EDDDB6E3939AD66B9F5B443B20E4AEA04BF4CB1A49F98BBA34FA569CF81` | `63D25E57E77FD58716343F806ACA21065C0993916C980BF67DB6013F5C9DCA5C` |
| `net/minecraft/world/level/block/SupportType.java` | `C29C3451FE7CD6054B235DB34574836AF599DD20689AF2DF061E2E6262CB6117` | `EA19A02F2559AFE4A8E4C0F6E12CD1480ABB0F62FE6853E37FA93771BD7DBEDC` |
| `net/minecraft/world/level/block/Block.java` | `BD7F69FFE617FA1008C9311AD7C731B23F242ACB5FF3BAB8C1A81E41E7D15A73` | `1693CFB7B84190A2FE664470A56D59E78ED5BD7D722A2BD16888B036D4D8E977` |
| `net/minecraft/world/level/block/state/BlockBehaviour.java` | `CF7EFF07EFB53D95C8FFA633FF38C7D3F454E185F197DE49EB4DA279A8DA9D17` | `9DB85DE84E502903E6FE497F043B58620B92089236FFB213F0B93DB979428D13` |
| `net/minecraft/world/level/block/ScaffoldingBlock.java` | `9CF5E367EF3C237238AB8FF1F63CEC907134AF8328AD09F7EE2CE8956946D74B` | `A55B102F47B261F808EF31C0B74CDA9EFDBC6FA53CCC4BDA8CE46230904B22FA` |
| `net/minecraft/world/level/block/FenceBlock.java` | `5CD60AC6466EFBD64DB7971EE2F6FCD519D12794BE084AFE40D1C4E085065326` | `BE5E1AA19A07D68457FF21968FB4E264669588FB6E83A86A6F51E76833F17F93` |
| `net/minecraft/world/level/block/WallBlock.java` | `30F65426C97E2507C13E55EDBD078AB86F12D24A1496ADB80E5D3CD44B2880F4` | `4DC61223DCE4023DEB3A7986983DB677CCF4C89A53778692BACB301927B9B345` |

This census establishes that these override classes are present in both published source trees; it is not an exhaustive comparison of every overridden method body, every registered block state, or every neighbor-update schedule. The previously accepted collision report already compares the fence/wall consumers and its listed shape providers; the accepted core-shape report compares the snow/scaffolding methods but leaves the full support-state population open.

## Residual gate

No source-level change was found in the shared `isFaceSturdy`/`SupportType` helper chain or in the finite override-class census. The source-state population that can satisfy or fail the fence/wall and scaffolding support queries is still not closed: all relevant provider method bodies/states, relevant block registrations/tags, and vanilla neighbor-update population/scheduling have not been comprehensively joined to these consumers. Resource membership for fence/wall connection tags is a separate gate handled by the pair's resource packet. Next source action is a bounded provider-and-registration join for only the support providers reachable through these movement-adjacent consumers, reusing the listed accepted collision/core-shape comparisons and the exact tag packet; it must not broaden into all worldgen/attachment callers of `isFaceSturdy`.

No source generation, canonical source writes, builds, tests, runtime checks, product/backlog/wiki/Git-history review, or pair freeze was performed.
## Exact accepted packet bindings (r2 citation-only)

This revision adds immutable identities for the three accepted source packets reused above. It does not alter the source comparisons, conclusions, or open gates.

| Packet | Report SHA-256 | Independent ACCEPT verdict SHA-256 |
| --- | --- | --- |
| `entity_move_collision/source-report.md` (full path `build/later-source-20261116/1.21.11--26.1.2/entity_move_collision/source-report.md`) | `3F44A596DD8F9D8885C565CB2269355B5B6503D0B4EB3898FACD6EBCD77F73B7` | `5F80FEB0CA4138EDB835A56A8A11CAA86FB558DE819BCE31915BDB3021FA109E` (`entity_move_collision_audit/audit.md`) |
| `core_shape_providers_revision_r2/coverage-r2.md` (full path `build/later-source-20261116/1.21.11--26.1.2/core_shape_providers_revision_r2/coverage-r2.md`) | `1DEA060FEEFE1812E68CC6D6A33D70EA32672948088B238BCDCE4B9123266393` | `19D0E14E6F5388910EB0E31D14EBC4C3C29ADEE66E700E07C7B9CF9309B35B72` (`core_shape_providers_r2_audit/verdict-r2.md`) |
| `collision_provider_residual/collision-provider-residual.md` (full path `build/later-source-20261116/1.21.11--26.1.2/collision_provider_residual/collision-provider-residual.md`) | `E313150BEE1C14E9BA91A6FB6F0C97B796F82D399DBD290792759D8292AFBA3F` | `693EC589325F8599E9373AACD961DB474920F6A1B153A36573B906B3E6DCB557` (`collision_provider_residual/audit/verdict.md`) |