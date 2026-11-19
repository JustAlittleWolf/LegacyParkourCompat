# Early 1.8.x plain Glass source alias audit

Scope: the frozen positive-thickness witness only: vanilla plain GLASS, non-source moving block, EAST extension, fixed phase progress=1.0F,lastProgress=0.5F, owner cell (0,0,0), ordinary world collision scan, no nested piston move or direct entity-push route. This checks continuity from exact 1.8 through 1.8.8; it does not repeat endpoint movement or authorize conclusions for other block states, piston directions/phases, or runtime parity.

## Verdict

For this witness, the 1.8 implementation applies without a source-supported 1.8.x patch exception through 1.8.8. The exact patch source trees and mapping artifacts are distinct, but the selected plain Glass registration is the same in every patch. Early source diffs include constructor/map-color additions and a World scan refactor; neither changes the Glass default unit bounds and collision-box construction, piston movement eligibility/production, moving-block progress/shape math, or the ordinary loaded-cell collision result for this witness. 1.8.2 through 1.8.8 have byte-identical source files for each compared movement dependency below. This is only an alias-safety verdict for the bounded Glass witness.

## Exact input identity

All nine folders are decompiled_minecraft/<version>/ornithe-feather/, with .decompile-provenance.json checked for each. For every source named below, its on-disk SHA-256 matched that manifest's sources entry. The inputs entry is the exact mapped Feather archive SHA-256; the client SHA is also recorded to identify each release source input. Manifest file SHA-256s are recorded as local audit anchors.

| Release | Feather mapping archive SHA-256 | Client SHA-256 | Provenance manifest SHA-256 |
|---|---|---|---|
| 1.8 | 3c020d1588b4aab403f5df077ca91b6e3776da2823d3b55202f4616f0d5ae5fe | ccccd0a92f76fbb24279691daad9b67f85d80471a37e59e0da76d7754982dfe8 | 52C8312C34A1A491D4A200C84E90F5D8D95C7E5CAA9571F864D8E70F9737DB05 |
| 1.8.1 | dac40cbd25c4d76d99d4263fd9754ab0195efd658756b6828fd06f0c4a8002f7 | 66a749ad95adcb929439155c0341309e89e437444867e108dcb36fd16493b0bd | F3A3BAA039F0E0ADE6E656DFA11A4529909A4B709AC489703C4250262CEFB1CA |
| 1.8.2 | 8235d85a8471d541f2bad73c2a60c6132a9432fe804b98369982fc8de847f37b | 5cc5b24312f3d182bf06e7f1a4da3a05ce94be6c861d5c9995c86e6d8811fbe9 | 8AD11EA36F2DDA39AE30D57DAA53316A8C0889A2F3C0C84EE5D79B9B21C25472 |
| 1.8.3 | e0ad2917dcc3a91586fb6f9b1bde78e55f1ece908572672885395e0d045dd9a2 | 4da43d06c8c1605a9a3ba1bf22893245cf1b108eb5c8fed859012252370c2c06 | 7DA5F772D7ED1433CB3DA23861723EBAEC7CB714CE49C4D1A5C30499FEC3AFED |
| 1.8.4 | 8ee5582ac3f6581e85ac27de5c9df9427a4bf67d0652531cf2e94eac4dde9b8c | d68a3aa5271abece4ce2b7048f6caf4f58071476344d9a52fe9ab1410c5d458b | B2207BF9AB2A7561BF73AC92A63CC67760CCC6FD9E2378FF1E10710ECBB03A00 |
| 1.8.5 | 18ec5e1a8f4cdfc3a886a7e24fe6a8594f04f51f6669bb0619d727f27cdd887d | 2d825073de0da4383c0525e81d181ec81f92d17daa2f0b81faff3850492e461d | 986D25DFD11927ABED51C92643E5BEDA48237DA875FBCDF1A48A930456EA99CE |
| 1.8.6 | d1507e3253a944d59dd0b924b6495deb7732ecadb5f8bd06f7139c241639ad81 | e597384cf964950b02650a39f73c2cbf5ce9e51d934cdfa1291530896e7cf100 | C96F635864596F6CBD6E34BA7BA184C22A8139B25F099CC8FBBFF46A85286EA9 |
| 1.8.7 | e0cd7842acda42ae23d2e9d031358c1faac1ac01a139bb29ac8034fbaa5fa27b | 2e45066ec194c6daec5f7dbb27822ef0b5855d5829f62392906620145689a3e0 | 6C6A5B64243AE7931EA1FA72A6C33440D5EEC4070461CBA80CDF51BF95DD93EF |
| 1.8.8 | 934d6112c5c1fa2d1801dbe78aee37ffbcec7ec03e1ed3a62c73de3c7925437f | 9481ed51d7fc4be54ec38c509f84124fbac5d9fea238bbde5b2e6c4f753b1ac8 | 588F83DC64CE0BBD0F11880D2DA8E59D4D097D43225025A2A74A2B26CF909AC1 |

The raw client inputs are pinned by each manifest's Mojang package URL, and the Feather archive URL names that exact patch/build. This audit read published canonical sources only; it did not run or regenerate decompilation.

## Checked source slices and meaning

- Plain Glass and full unit collision geometry. block/Blocks.java registers new GlassBlock(Material.GLASS, false) for glass in every release (1.8 and 1.8.1 around line 808/819; 1.8.2–1.8.8 line 863). GlassBlock.java continues to extend TransparentBlock; it has no shape/collision override. TransparentBlock.java changes across patch source but only changes its material/map-color constructor plumbing; isSolidRender() and face-culling/render predicates do not override movement collision. In block/Block.java, all checked constructors set bounds to (0,0,0)-(1,1,1) and getCollisionShape(World, BlockPos, BlockState) builds its Box directly from those bounds; the source diff between 1.8, 1.8.1 and 1.8.2+ does not change either operation. getOutlineShape uses the same bounds. isCube()==false on Glass and isSolidRender()==false on TransparentBlock affect cube/render/light behavior, not the collision-box path.
- Piston admission and state production. In block/material/Material.java, GLASS uses new Material(...).setTranslucent().setCanBeBrokenInAdventureMode() in every patch; the default pistonMoveBehavior int remains zero, and the getter returns it. Block.getPistonMoveBehavior() forwards to the material. PistonBaseBlock.canMoveBlock admits the normal zero-behavior material; extension uses the same common move(..., extend=true) route for normal and sticky pistons. That route removes the source block, writes Blocks.MOVING_BLOCK, and creates its entity with the exact moved Glass state and EAST direction. The sticky flag only selects the moving piston-head type for this extension. The sticky-only retraction pull is outside this extending witness and is not asserted.
- Progress and moved Glass shape. block/entity/MovingBlockEntity.getProgress(tickDelta) interpolates from lastProgress to progress; the collision entry point in block/MovingBlock.java requests delta 0.0F, inverts for extension, then calls its moved-state overload. For EAST, that overload subtracts progress from the positive X maximum bound. At the selected fixed phase the values are lastProgress=.5F, progress=1.0F, so the EAST collision shape is the full Glass unit box trimmed at max-X to .5; the World query does not invoke MovingBlockEntity.moveEntities or an entity push. MovingBlockEntity.tick keeps the same .5F increment/clamp and extension moveEntities call order in all nine.
- Ordinary world block scan. world/World.java:getCollisions(Entity, Box) scans from floored minima through floored (max+1) exclusive bounds, checks loaded chunks, and calls each scanned state's addCollisions; Block.addCollisions appends only a non-null collision box that strictly intersects the query. For the frozen query x [0.6,1.7], y [0.2,2.0], z [0.2,0.8], cell (0,0,0) is included (x scan 0 and 1; z scan 0; y scan -1 through 2) in each version. The exact World source differs at 1.8 and 1.8.1 due allocation/mutable-position/border-scan refactoring, but these bounds, loaded-cell gate, state lookup, and collision dispatch still include the fixture cell.

## Source-byte audit

All listed hashes below matched the exact source hash in that version's provenance file. Full-file identity groups are used where bytes are identical; where files differ, the member-level operations above were checked in the exact bodies.

| Relative source file | 1.8–1.8.1 SHA-256 | 1.8.2–1.8.8 SHA-256 |
|---|---|---|
| net/minecraft/block/Blocks.java | AF42DC7D1FE95A3CC070427E32CD76213D6EAB4DF49118FEE99AA3C7A3A004B0 | 1DA2D85406ED991E8F6BC1F420BABF0BD43598478F347C6A4DC9FEA322ED0695 |
| net/minecraft/block/GlassBlock.java | A7C3403C4E6D0D0014EF722755962F5F449071BB2EBFFE0FD2D200BD5CD42144 | same |
| net/minecraft/block/TransparentBlock.java | AB92EEDCD484036FE4433FEE8E63DE97110D790219812F92BC8FD2F28C48D3F0 (1.8); 8A06D66E534253DE6F581E8D23270D1A2217158E99EEDB92B0E2DF7CEC710B84 (1.8.1) | 10780460A36E5E73D61381CD91B05A988E30A67E71FC182875A52BDCF387D244 |
| net/minecraft/block/Block.java | 4A4E6DFDE24A135ECF87C359FD98F0380664E4BACAF679815C9016CB1BA0D814 (1.8); 4F645CB1F25248BA16649D9D1AB17B3DBE4C7316191DEB982F641BEFF437E67C (1.8.1) | EA10F05106A3CF7189AEC85236A7ECF9C7106A717F37BED583B5EA4ADEFFA528 |
| net/minecraft/block/material/Material.java | A27BCC70E2712F150D8428773F8C2AAFE98AF9100A4FD05653057044D7D248A9 (1.8); C05F393FE2B5E77B96DAC85250751BED03CAD2BE6377FC5C10209904C1F185BF (1.8.1) | 017713D76AFE726CA243CE32CBC35C13D3F0F0E7E5D90D122F81103CC2CA1BD2 |
| net/minecraft/block/PistonBaseBlock.java | A103503013C2919EAD9B7AD411098FF12CD352B3A4E734815F8ED87058CE9227 | 3C96698A674714446F9FD0D6CC1C4D5EB72937D9A2F396516DDB2461C3EA4929 |
| net/minecraft/block/piston/PistonMoveStructureResolver.java | 36A121CDAFB0C2BCCB90ACDF1D1CF10DEC205C62223EAD3A0A8B8FA790C2C66F | FEF3BE5C0785C567E49B945D2A984F252615576D01220CCCA9A0B90C929C3E7B |
| net/minecraft/block/MovingBlock.java | 614CC52B357152F6F515B33EA59440DFE338227EA0CE4E7DAC7E43A13A95BA7D | 700010B071738738F04E0508050590D8D625F01115FC4886FEF6E262F85C6E85 |
| net/minecraft/block/entity/MovingBlockEntity.java | 37B472840FE4E9AC2427B293BF21EB0A3979D7C73137612DB1D048D40C891E4B | 05A5BFBF2556EAA621996BDA365B7BAB01F5B9060CF773E7B9949EBE2F39FFF6 |
| net/minecraft/world/World.java | AFBC701EF8B66020BDBB2831FD80683705D20237C68FBF23A4001C020876FC6D (1.8); F0873207C5C7A793189597470392FA0184513B3C5A682A6F8795E1C9FFDE9F71 (1.8.1) | 3C04F5B874FBB6692039164C882A3C47FE28C7ECA864E91C9AC57798153888EE |

## Exact source anchors

- 1.8 / 1.8.1 / 1.8.2–1.8.8: block/Blocks.java plain glass registration (line 808 / 819 / 863 respectively); block/GlassBlock.java:8; block/TransparentBlock.java:9–42 (1.8) or :10–46 (later); block/Block.java unit-bound constructor (:156–160, :161–173, :201–209 respectively), outline and collision box methods (:286–304, :293–311, :333–351 respectively).
- 1.8 / 1.8.1 / 1.8.2–1.8.8: block/material/Material.java:22,103–104 (1.8/.1) or :22,108–109 (.2+); block/Block.java:671–672 (.8/.1) or :722–723 (.2+); block/PistonBaseBlock.java:120–161,247–274,287–346; block/piston/PistonMoveStructureResolver.java:38–138 (1.8/.1) or :37–137 (.2+).
- 1.8 / 1.8.1 / 1.8.2–1.8.8: block/MovingBlock.java:118–197; block/entity/MovingBlockEntity.java:54–60,80–114,116–149; world/World.java:873–929 (1.8/.1; same scan method in .2+).

## Disposition

Early alias continuity established only for the frozen plain-Glass east positive-thickness witness, source path and ordinary collision scan. The separate 1.8.9 vs later endpoint audit remains authoritative for its own comparison; this result does not independently reproduce endpoint math or extend to other operations/states.
