# Independent provider formula audit A–L (1.7.10 → 1.8)

## Evidence identity and method

I read the exact read-only Ornithe Feather source roots `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.7.10\ornithe-feather` and `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.8\ornithe-feather`. Direct SHA-256 of `.decompile-provenance.json` matches 1.7.10 `11bada441ebe07661402d1c40e1c490b78b279ecaa084f9a4dc3ae2dd54caf6f` and 1.8 `52c8312c34a1a491d4a200c84e90f5d8d95c7e5caa9571f864d8e70f9737db05`. Both use Ornithe Feather Gen2 `build.2` (1.7.10 mergedv2 `1a22781aa3bac248e25c619842ec2ec5c126d015436d528d411a9e85a6f491a7`; 1.8 `3c020d1588b4aab403f5df077ca91b6e3776da2823d3b55202f4616f0d5ae5fe`). I independently re-read each listed Java file, checked its full SHA-256, and compared the complete direct collision provider body plus state-dependent helper/mapping bodies where the provider dispatches to them. The prior inventory was used as a file index only.

## Direct method dispositions

| Provider | Direct method/body comparison | Disposition for equal old-representable block states |
|---|---|---|
| `AbstractPressurePlateBlock` | `getCollisionShape` returns null on both sides. | Equal; no provider box. |
| `AbstractRailBlock` | `getCollisionShape` returns null on both sides. | Equal; no provider box. |
| `AirBlock` | `getCollisionShape` returns null on both sides. | Equal; no provider box. |
| Base `Block` | `addCollisions` gets the virtual shape and appends it iff non-null and `shape.intersects(candidate)`. Base shape coordinates use `x + minX ... z + maxZ`; 1.8 uses `BlockPos` coordinates with the same additions. | Equal query gate and coordinate formula. `Box.of` versus `new Box` changes construction syntax only. Strict intersection details are owned by paired `Box` source. |
| `BrewingStandBlock` | Sets central column `[.4375,0,.4375]..[.5625,.875,.5625]`, invokes base collector, restores default shape, invokes base collector again. | Equal boxes, order, reset and duplicate collector calls. |
| `ButtonBlock` | `getCollisionShape` returns null on both sides. | Equal; no movement collision box. |
| `CactusBlock` | Returns `[x+.0625,y,z+.0625]..[x+.9375,y+.9375,z+.9375]`; 1.8 uses corresponding position coordinates. | Equal formula. |
| `CakeBlock` | Reads bite metadata/state and uses equal X inset `(1+2*bites)/16` and Z inset `.0625`. Old top is `y + .5F - .0625F`; new top is `y + .5F`. | **Geometry delta:** upper face is 1/16 higher in 1.8 for every bite state. This is directly player-query-visible; independently accepted concrete falling witness is in `canonical-findings/1.7.10--1.8/cake-collision-height.md`. |
| `CauldronBlock` | Adds one bottom pan with top `.3125`, then four `.125`-thick perimeter walls in same west, north, east, south method order and resets shape. | Equal formula, order and reset. |
| `CobwebBlock` | `getCollisionShape` returns null on both sides. | Equal; no solid movement box. |
| `CocoaBlock` | Provider calls `updateShape` then base box. Both update from stage and facing; each stage uses `width=4+2*age`, `height=5+2*age`, half-width `width/2.0F`, identical four oriented coordinate formulas and fixed y max `.75`. 1.8 property `AGE` maps bits `(metadata & 12)>>2`; horizontal facing metadata mapping is retained. | Equal formula for paired metadata/state. No candidate found. |
| `DoorBlock` | Provider updates shape from combined two-half metadata then calls base shape. Each state branch uses same `.1875` thickness and same orientation/open/hinge face formulas. 1.8 `getStateFromMetadata` and `getMetadataFromState` preserve lower facing/open and upper hinge/powered bits; combined metadata assembles the same halves before shape selection. | Equal formula for paired door states. No candidate found. |
| `EndPortalBlock` | Empty `addCollisions` body on both sides. | Equal; no box. |
| `EndPortalFrameBlock` | Adds base `[0,0,0]..[1,.8125,1]`; if eye bit/property set, adds `[.3125,.8125,.3125]..[.6875,1,.6875]`, then resets shape. Metadata `hasEye` bit 4 maps to 1.8 `EYE`. | Equal formula and call order for paired states. |
| `FarmlandBlock` | Returns a unit box in both versions. | Equal provider geometry (independent of rendered surface height). |
| `FenceBlock` | Computes north/south/west/east connectivity in same order, builds the same two rail boxes at y=0..1.5 with center span `.375..625`, connects matching edges, and leaves final shape at y=0..1. | **Conditional modern-neighbor difference only:** 1.8 explicitly rejects `BarrierBlock` and connects any `FenceBlock` sharing material; 1.7.10 connects only `this` fence instance or gate before solid-cube fallback. The explicit barrier and cross-variant cases require 1.8-only neighboring states; for paired 1.7.10-representable states (the same wood fence singleton, same gate, and solid non-pumpkin cubes), the connection formula and resulting old fence boxes are equal. No old-representable movement delta accepted. |
| `FenceGateBlock` | Returns null when open. When closed, old metadata orientations 0/2 select a Z-thickness box and 1/3 select an X-thickness box, with y max 1.5. 1.8 uses the facing axis and identical `.375..625` thickness. `OPEN` and facing properties encode the same paired metadata. | Equal formula for paired gate states. |
| `FireBlock` | `getCollisionShape` returns null on both sides. | Equal; no solid movement box. |
| `HopperBlock` | Adds basin `[0,0,0]..[1,.625,1]`, then four `.125` rim walls in the same order, then restores full shape without adding it. | Equal boxes, order, and final reset-to-full-state mutation. |
| `LadderBlock` | Provider updates orientation-derived shape then calls base. Old metadata cases 2,3,4,5 use thickness `.125` at north,south,west,east faces respectively; 1.8 Direction metadata uses the same face mapping and shape coordinates. | Equal formula for actual ladder state. The new same-block state guard is satisfied by virtual dispatch from the ladder instance on its own cell; no old-representable delta found. |

## File integrity

Exact file SHA-256 values were checked during the direct reads; values match the supplied provider inventory row identities:

| File | 1.7.10 SHA-256 | 1.8 SHA-256 |
|---|---|---|
| `AbstractPressurePlateBlock.java` | `A59FA2015292BCAB88E2AA06C2773AF7D470C795D2BFE565B9689DEB44B16A12` | `9D434C5D170727A9A6DAE9F1C711FFF280B873A9B03014666F001E4ADCC451EA` |
| `AbstractRailBlock.java` | `E823F20FDA6FDA81E1D14ABCDC4F1D2808153394214C621272B193FBD57710E7` | `3B5A3AC400B533250C1DBEC132A1CD9ED4B10B96F29D7B744B8FD10223BEAD04` |
| `AirBlock.java` | `42D7F22CD935FE2AEDC62385B8B1894E2BFDEB61EA00145D7EB0D050CDF5FE66` | `1BB1D0D02EE71276855EA32552E7F91679BA07974AC13C146BA79003A85BBFC5` |
| `Block.java` | `462EA8943470DE83F2D923D1D267BEBC6B9AF05EECF3C60C5E90EAE870394D0B` | `4A4E6DFDE24A135ECF87C359FD98F0380664E4BACAF679815C9016CB1BA0D814` |
| `BrewingStandBlock.java` | `5BA218484C4954C60C6393581EB4C1BB11C5B9D0424C20F8D7649FC3ACC414A9` | `884A948B81D58C845807057B3E1D2439A8FE10920AF6031A3C5D6294617729AF` |
| `ButtonBlock.java` | `A6FFDA347C58ACE466CE1D0E01C19E86586A69FDE55DB69252A7DA238C9C50AE` | `EC96B0211F28CFABDF9B0E249D6CE5790BCEE02BB89E75E91C206DDB7A788B45` |
| `CactusBlock.java` | `EE2E97A98F9E3F7194F3421CABDDF6379DE57F47D1062A8308018EDB823E5618` | `43662795DCC7C7814F4D30EEFAEE198D068985C0F9FA787C2A00807C20B4177B` |
| `CakeBlock.java` | `AF1414847135AB0AA22BBD32235939532D63AC6207A79AB1355F56EF39A4F58B` | `D860CC75B2C238B7283DD7E9C22EDFCA508273D6E762C5DDEDBA2A827BCC00E6` |
| `CauldronBlock.java` | `715295D307AA2925E1F30BAAF7BAA261905B2CEA614A16D8291DB905E39CF854` | `B69111377B6B72F2E86BE0163287C89DFCFF9DC9B71D2E4EE3CDE6074A58C749` |
| `CobwebBlock.java` | `2C287985F8CF05C1DD15B8800EF9995E7F00208D68A2DC12E3D5D54A9A9B3B3C` | `60CB99FC4C1A891305F7E86C4268794F34958DC1FDB3C6E14F955BD6DF91AF62` |
| `CocoaBlock.java` | `A529843F469151A71C2B8AD1269ABCA7B881B2011FADBAFC2FCEA03409A417FC` | `AF7D10295415C50DEBB68D7C35363FF081B030E9EB9E0ED4C5C122D37F0F00DD` |
| `DoorBlock.java` | `21E1C557AA19D26DCABACDD3C97F61E7BEE544ECDC2A52C04D3B2E3F97B0E8D6` | `8B77AB5F8FC1D509DACA4CA783A5DC145DF6139BBCDA5D42C01430F5A325D347` |
| `EndPortalBlock.java` | `E615D175E9EEFB2CE95D148EED4AF0B9A7B07AE4676945584A83C4E464041ABD` | `D5887C633E4154E5E1C1A726AC5793B6B79E980F2F756D07ADAFBC4AE010667C` |
| `EndPortalFrameBlock.java` | `82698024292953B3A6DA03F84667426AF8B7CC799B4BD778F2580431F2AD1D6B` | `93E57410C9BC722782553B2F16C0848E4752281FD0A0C1B074BF77317B10FC36` |
| `FarmlandBlock.java` | `92F5D09D690417DD71EF2C9CFC048AFD7C5FEA6F2AEEB998C045BE924C541579` | `B756E101960960E64C113CD45730C041A28D945C511BEE82DAD5F62154578590` |
| `FenceBlock.java` | `B10331286AFBB3DF1CD0B2D10ACEAC634F175B063B018CE09751E54BE5165502` | `2E75C087705278524A3A54ACF3B56C4A476294357A11741640B457CEA4E781E1` |
| `FenceGateBlock.java` | `83B12BEE14A05AE4D5F603BCAD4743C785969FC0B5F8B3B7D55173C81F37994C` | `80931B83E031D5EBA66557F1DE29095BDE3A1AD61F358F172372C3D4B61546C8` |
| `FireBlock.java` | `7FEA8F3D3F8D0C0C41BA489EC78035573C1A8DEC19B250B593B3F274E13CD0E9` | `C3D4D726DCD5EEC156F8D33E2220352C40A60E4F4F3405656F7CE6ABD1AA8BEB` |
| `HopperBlock.java` | `0438B21175F0079E732D875136DCBABC291A578FB8CCB0E41F6007C8EBA61E4B` | `213D75F7715FE681399916BF53C90C61BBCCEF881FC53D4135791461C501F529` |
| `LadderBlock.java` | `623BFED2A95F27EA0DD452DE46261383BDE9E6017707D8C2D33F943221FC6628` | `E94F204EBDA56CE831BE472CD30E3138A96306BE5E692111CBBC52ACA1E13177` |

## Boundary

This report completes the paired provider formula pass only for these 20 alphabetically assigned classes, not every provider class. It compares old-representable block states and does not treat 1.8-only neighboring states (such as barrier) as old movement deltas. `CakeBlock` is the only concrete ordinary-player collision-box difference found in this segment. The full source pair still requires the other assigned provider segments and joined inventory review; no runtime parity is claimed.
