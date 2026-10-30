# 1.7.10 to 1.8 collision and shape source slice

## Evidence identity

- Sources: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.7.10\ornithe-feather` (Feather 1.7.10+build.2; source-tree manifest SHA-256 `11bada441ebe07661402d1c40e1c490b78b279ecaa084f9a4dc3ae2dd54caf6f`) and `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.8\ornithe-feather` (Feather 1.8+build.2; manifest SHA-256 `52c8312c34a1a491d4a200c84e90f5d8d95c7e5caa9571f864d8e70f9737db05`). Pair and source hashes were supplied by the source preparer; per-file hashes below were re-read.
- Mappings: Ornithe Feather on both sides. Raw client hashes: 1.7.10 `a4fc2284657544e0f4bcc964f927c2fda3e3a205178ed1d5d58883aaf9780cce`; 1.8 `ccccd0a92f76fbb24279691daad9b67f85d80471a37e59e0da76d7754982dfe`.
- File hashes: `entity/Entity.java`: 1.7.10 `AF4EF7B11BC709D96BD3EEA1260A61D38A1A04996BDF87F2A77C459DDF070A69`, 1.8 `2BE081ABE1720F89D7AB90F782B8411E71BC5B8A6B53966B49A2380E3C47FFD9`; `world/World.java`: 1.7.10 `2D648EDFE7FDC953825A1DF94709E889EA21354494D85762BCA49B4A109D01F9`, 1.8 `AFBC701EF8B66020BDBB2831FD80683705D20237C68FBF23A4001C020876FC6D`; `block/Block.java`: 1.7.10 `462EA8943470DE83F2D923D1D267BEBC6B9AF05EECF3C60C5E90EAE870394D0B`, 1.8 `4A4E6DFDE24A135ECF87C359FD98F0380664E4BACAF679815C9016CB1BA0D814`; `block/StairsBlock.java`: 1.7.10 `3367469608D32C3AA26158763A4C82E498091C9EAD3844832B3265FB06AC7506`, 1.8 `B50094C97DADAAF056ADA6466BAE8005A72C820E9846EF352886F1FD78CAA5CB`; `block/LadderBlock.java`: 1.7.10 `623BFED2A95F27EA0DD452DE46261383BDE9E6017707D8C2D33F943221FC6628`, 1.8 `E94F204EBDA56CE831BE472CD30E3138A96306BE5E692111CBBC52ACA1E13177`.
- Additional per-file hashes: `block/CarpetBlock.java`: 1.7.10 `BBDD4B80D0CFD7B5AA69313D05D2C70154D5D37545F1DE6DF776CEBA14D7A6F2`, 1.8 `75016CB9C1C7E237B2A09833D9AB9169C0A542F849D8F22ADAA0E804B820B9CF`; `block/CakeBlock.java`: 1.7.10 `AF1414847135AB0AA22BBD32235939532D63AC6207A79AB1355F56EF39A4F58B`, 1.8 `D860CC75B2C238B7283DD7E9C22EDFCA508273D6E762C5DDEDBA2A827BCC00E6`; `block/SnowLayerBlock.java`: 1.7.10 `E68B477B30DFC38924F49859CD9E25722C5BC6CA6D5EE09D681285939AC415B4`, 1.8 `EA7497CCDEB306339315149C20C299B4BD95850DFC5F511604B668BB60C3DABC`; `block/PistonHeadBlock.java`: 1.7.10 `07FCC8EF896456E45AE5D48FFAEB8F011FC62D35BD935AF0A368E9B06C4CA4A7`, 1.8 `5A142EDBAB2151D0987BB940957B6D7CD86C564F8960D28CA0FA00C3A8F69704`.

## Paired slice inventory

| Slice | State | Evidence / dependency closure |
|---|---|---|
| `Entity.move(double,double,double)` collision clipping, sneak edge support, step branch, position/ground/collision writes | Examined | 1.7.10 `entity/Entity.java:383-584`; 1.8 `entity/Entity.java:370-608`. Reads collision boxes through `World.getCollisions`, with state flowing into `Block.addCollisions` / `getCollisionShape`. Confirmed difference described below. |
| Collision list construction, block scan order, adjacent/entity collision collection | Block-provider path examined; entity-list path partial | 1.7.10 `world/World.java:972-1014`; 1.8 `world/World.java:873-946`. In-bounds block scan keeps X, Z, Y nesting and floors `min` / `max + 1.0`, including Y-1 start; each block routes to `addCollisions`. 1.8 also updates world-border state and substitutes border stone; this is tied to the 1.8 world-border feature and remains native-modern. Entity-list behavior is not fully closed against all player contexts. |
| Local player suffocation push response (`pushAwayFrom`) and block readers | Examined | Exact class move: 1.7.10 `client/entity/living/player/InputClientPlayerEntity.java:400-460`; 1.8 `client/entity/living/player/LocalClientPlayerEntity.java:300-356`. Ordinary-player test is equivalent: old checks solid at point and above and chooses nearest open west/east/north/south side using strict distance comparisons; new `canSurvive(pos)` requires both current and above blocks non-solid and uses the same side order, offset, 0.1F push. 1.8 adds a `noClip` early return at `LocalClientPlayerEntity.java:301`; `PlayerEntity.java:186` sets noClip from `isSpectator()`, a modern-only mode, so this has no older-profile counterpart to emulate. |
| `World.getBlockCollisions` and support/standing shape readers | Block-query path examined; downstream rider/displacement callers partial | 1.7.10 `world/World.java:1016-1048`; 1.8 `world/World.java:948-980`. Same X, Z, Y scan order and inclusive floor range; calls block `addCollisions`. 1.7.10 `Entity.pushAwayFrom` and 1.8 base Entity equivalent call this query, and `World.isFullCube` uses collision-shape average side length `>= 1.0` on both. Player-specific suffocation response is overridden as listed above. Dismount placement and all downstream readers are not fully audited. |
| Block collision API and default geometry | Examined | 1.7.10 `block/Block.java:836-847`; 1.8 `block/Block.java:292-303`. Both add one collision box iff non-null and `shape.intersects(box)`; both form it from stored min/max bounds plus block position. Signature/state plumbing differs. |
| Full-cube support query and `hasSolidTop` | Examined | 1.7.10 `world/World.java:1834-1854`; 1.8 `world/World.java:1692-1712`. `isFullCube` uses collision-shape average-side-length `>= 1.0` on both. `hasSolidTop` retains material/cube gate and block-specific stairs/slab/hopper handling, with metadata migrated to state properties. |
| Stair collision geometry and neighbor dependency | Partial | 1.7.10 `block/StairsBlock.java:47-252`; 1.8 `block/StairsBlock.java:40-385`. Both build lower/upper half-box, derive corner and inner-step masks from same-half neighboring stairs, add base + corner candidate boxes, optionally add inner step box, then restore full bounds. Full branch-by-branch orientation equivalence was not proven. |
| Ladder geometry and state reader | Examined | 1.7.10 `block/LadderBlock.java:18-57`; 1.8 `block/LadderBlock.java:27-63`. Both use 1/8-thick wall-mounted boxes for the four horizontal orientations; 1.7 metadata values 2/3/4/5 correspond geometrically to 1.8 NORTH/SOUTH/WEST/EAST. 1.8's default state is NORTH. `LivingEntity.isClimbing` 1.7.10 `entity/living/LivingEntity.java:781-787` and 1.8 `:790-796` both detect ladder or vine for ordinary players; 1.8 additionally excludes spectator players (1.8-only spectator mode, native modern feature). |
| Other existing block collision providers / registration closure | Examined for paired collision methods; registration closure partial | Provider member inventory below includes per-class paired status. State decoding branches relevant to orientations/layers were checked for the shape-bearing providers; block registration is not being audited here. |
| Other direct player block/state predicates and callbacks | Partial / unexamined | `LivingEntity.isClimbing` reviewed as above. `Entity` post-move block callback and fire/material checks were not fully compared. Other `Block` callbacks reading state and affecting player response remain unexamined. |
| Position/box writers and support readers outside this lane | Partial / unexamined | `Entity.move` box/position/onGround writes reviewed. Full tick callers and all downstream `onGround`, `collidingVertically`, and climbing readers in `LivingEntity`/`PlayerEntity` not compared here; acceleration/jump internals intentionally outside this lane. |

### Collision-provider member inventory

All class paths below are `net/minecraft/block/<Class>.java` unless called out. Each entry lists the exact old/new collision member lines and a paired verdict. `Same` means collision box geometry and query behavior were equivalent for the corresponding vanilla state/orientation, apart from signature/state representation. `No collision` means both sides return null or add no boxes. `Different` identifies source behavior changes. A class has one provider declaration on each side except the explicit Carpet membership change. `MovingBlockEntity` is included as the 44th common query-time shape caller; it does not declare the block collision API.

| Class | 1.7.10 member | 1.8 member | Verdict |
|---|---:|---:|---|
| `AbstractPressurePlateBlock` | `getCollisionShape:44` | `:42` | No collision; both return null. |
| `AbstractRailBlock` | `getCollisionShape:35` | `:35` | No collision; both return null. |
| `AirBlock` | `getCollisionShape:18` | `:20` | No collision; both return null. |
| `Block` | `addCollisions:836`, `getCollisionShape:843` | `:292`, `:299` | Same; intersecting non-null shape is added; bounds are stored min/max plus position. |
| `BrewingStandBlock` | `addCollisions:51` | `:58` | Same; central stem, then reset base shape are added in that order. |
| `ButtonBlock` | `getCollisionShape:26` | `:35` | No collision; both return null. |
| `CactusBlock` | `getCollisionShape:46` | `:53` | Same; 1/16 inset and 1/16-short top/south/east bounds. |
| `CakeBlock` | `getCollisionShape:41` | `:43` | **Different**; old top is `y + 0.5F - 0.0625F`, new top is `y + 0.5F`. |
| `CauldronBlock` | `addCollisions:56` | `:35` | Same; bottom basin and four 1/8-thick walls, same order. |
| `CobwebBlock` | `getCollisionShape:29` | `:32` | No collision; both return null. |
| `CocoaBlock` | `getCollisionShape:77`, `updateShape:89` | `:58`, `:70` | Same geometry formulas and four directions; old metadata facing/stage become `FACING`/`AGE` state reads. |
| `DoorBlock` | `getCollisionShape:111`, `updateShape:117`, private `updateShape(int):129` | `:65`, `:71`, private `updateShape(int):75` | Same shape table for combined orientation/open/hinge metadata. |
| `EndPortalBlock` | `addCollisions:41` | `:42` | No collision; both methods are empty. |
| `EndPortalFrameBlock` | `addCollisions:60` | `:39` | Same; 13/16-high base plus 3/8-square eye only when set. |
| `FarmlandBlock` | `getCollisionShape:25` | `:29` | Same full-block collision box. |
| `FenceBlock` | `addCollisions:24`, `shouldConnectTo` | `:31`, `shouldConnectTo` | Same two connected-arm boxes, 3/8-thick and 1.5 high. 1.8 excludes modern `BARRIER` in connection predicate; for pre-existing neighbor blocks, same. |
| `FenceGateBlock` | `getCollisionShape:32`, `updateShape:42` | `:53`, `:65` | Same; open yields null; closed gate is 3/8-thick, 1.5 high, orientation selects X/Z axis. |
| `FireBlock` | `getCollisionShape:55` | `:119` | No collision; both return null. |
| `HopperBlock` | `addCollisions:41` | `:42` | Same; 5/8-high pan and four 1/8 walls; reset full bounds. |
| `LadderBlock` | `getCollisionShape:17`, `updateShape:33` | `:28`, `:40` | Same 1/8-thick box on each of the four mounted faces. |
| `LeverBlock` | `getCollisionShape:20` | `:29` | No collision; both return null. |
| `LilyPadBlock` | `addCollisions:26`, `getCollisionShape:33` | `:23`, `:30` | Same shape; still omits boat collision only (player path unaffected by that gate). |
| `LiquidBlock` | `getCollisionShape:125` | `:110` | No collision; both return null. |
| `MovingBlock` | `getCollisionShape:102`, overload `:141` | `:118`, overload `:162` | Same translated AABB bounds for matching facing/progress, but old mutates the moved block's returned Box and new copies coordinates to doubles and returns a new Box; aliasing side effect only, no player effect established. |
| `MovingBlockEntity` | `getCollisionShape` caller at `block/entity/MovingBlockEntity.java:87` | caller at same path `:87` | Same query path: uses `getProgress(0.0F)`, inverts progress when extending, then supplies moved block/state, facing, and progress to `MovingBlock`. |
| `PaneBlock` | `addCollisions:56` | `:62` | Same neighbor-dependent center/cross arm boxes; connected west/east/north/south branches map to same coordinates. |
| `PistonBaseBlock` | `addCollisions:272`, `getCollisionShape:278`, `updateShape:238` | `:211`, `:217`, `updateShape:174` | Same; collision-list override adds full cube; direct query update uses 1/4-trimmed face when extended, matching all six direction cases. |
| `PistonHeadBlock` | `addCollisions:113`, `updateShape:162` | `:86`, `:122`, state helpers `:94-120` | Same head face and shaft boxes for DOWN/UP/NORTH/SOUTH/WEST/EAST, same two-box order. |
| `PlantBlock` | `getCollisionShape:55` | `:57` | No collision; both return null. |
| `PortalBlock` | `getCollisionShape:40` | `:50` | No collision; both return null. |
| `RedstoneWireBlock` | `getCollisionShape:32` | `:71` | No collision; both return null. |
| `SignBlock` | `getCollisionShape:33` | `:26` | No collision; both return null (including wall signs). |
| `SkullBlock` | `getCollisionShape:69`, `updateShape:47` | `:80`, `:59` | Same five directional boxes; standing/default head plus four wall-facing boxes. |
| `SlabBlock` | `addCollisions:52`, `updateShape:29` | `:63`, `:38` | Same; double slab full block, single slab bottom/top half from metadata/state. |
| `SnowLayerBlock` | `getCollisionShape:32` | `:40` | Same when state encoding is aligned: old `(metadata & 7) * 1/8`; new `(LAYERS - 1) * 1/8`, where layer count is old metadata + 1. |
| `SoulSandBlock` | `getCollisionShape:16` | `:18` | Same; top at 7/8. |
| `StairsBlock` | `addCollisions:252`, `isInnerStair:88`, `setStepShapeForCollisions:167` | `:386`, `isInnerStair:218`, `setStepShapeForCollisions:299` | Same collision decomposition and neighbor-dependent masks: half-box, corner box, and optional inner-step box. Direction/state encodings correspond to old 2-bit facing; full exact corner branch-by-branch pairing is still partial. |
| `SugarCaneBlock` | `getCollisionShape:79` | `:92` | No collision; both return null. |
| `TorchBlock` | `getCollisionShape:19` | `:32` | No collision; both return null. |
| `TrapdoorBlock` | `getCollisionShape:48`, `updateShape:52` | `:58`, `:62` | Same; open/closed, half, and facing state feed corresponding panel coordinates. |
| `TripwireBlock` | `getCollisionShape:28` | `:57` | No collision; both return null. |
| `TripwireHookBlock` | `getCollisionShape:19` | `:40` | No collision; both return null. |
| `VineBlock` | `getCollisionShape:108` | `:132` | No collision; both return null. |
| `WallBlock` | `getCollisionShape:91`, `shouldConnectTo:96` | `:100`, `shouldConnectTo:105` | Same wall geometry and 1.5 height. 1.8 adds explicit non-connect to modern `BARRIER`; other old neighbor connections remain same. |

Additional membership difference: 1.7.10 `CarpetBlock.java:29` overrides collision shape and returns max Y as `y + 0 * 0.0625F`, a zero-height box; `Box.intersects` rejects it on both versions (`util/math/Box.java:172` and `:153`). 1.8 `CarpetBlock` has no collision override and inherits `Block.getCollisionShape` (`block/Block.java:299`) with constructor bounds `y..y+0.0625F` (`CarpetBlock.java:23`). Thus 1.7.10 carpets add no player collision; 1.8 carpets have a 1/16-high collision shape. Carpet itself existed in 1.7.10; this is not a modern-only block.

`BannerBlock` has a 1.8 collision-shape declaration at `block/BannerBlock.java:33`, with no 1.7.10 class; it was introduced after 1.7.10 and is not a historical shape candidate.

## Confirmed difference: carpet collision shape

1.7.10, Ornithe Feather: `net/minecraft/block/CarpetBlock.java`, `getCollisionShape(World,int,int,int)`, line 29. 1.8, Ornithe Feather: `net/minecraft/block/CarpetBlock.java` has no collision override; it inherits `net/minecraft/block/Block.java`, `getCollisionShape(World,BlockPos,BlockState)`, line 299. Carpet constructor keeps bounds `[0,0,0]..[1,0.0625,1]` in both releases (1.7.10 `CarpetBlock.java:17`, 1.8 `:23`). Hashes listed above.

Difference: 1.7.10's override sets `byte var5 = 0` and returns its upper Y as `y + var5 * 0.0625F`, equal to the lower Y `y`; `Box.intersects` uses strict positive overlap and rejects this zero-height box. 1.8 removes that override and the inherited base method returns Y bounds `y..y+this.maxY`, so a 1/16-high box is added by the ordinary `Block.addCollisions` intersection gate.

Reachable precondition/effect: a player moving over a carpet block queries its collision volume. The old profile gets no collision from the degenerate box; the 1.8 profile can clip or step against the 1/16-high carpet. Predicted trajectory effects include a small vertical offset or different horizontal clipping at carpet edges; no trajectory was run.

## Confirmed difference: cake collision height

1.7.10: `net/minecraft/block/CakeBlock.java`, `getCollisionShape(World,int,int,int)`, line 41. 1.8: same relative path, `getCollisionShape(World,BlockPos,BlockState)`, line 43. Hashes listed above.

Difference: both use `var6 = 0.0625F` for inset and bite-dependent X start `(1 + bites * 2) / 16.0F`. 1.7.10 sets collision max Y to `y + var8 - var6` where `var8 = 0.5F`, giving 7/16. 1.8 sets max Y to `pos.getY() + var6` where `var6 = 0.5F`, giving 1/2. The paired `bites` state/metadata conversion preserves the bite count; only top height differs.

Reachable precondition/effect: a player with positive step height (1.7.10 `LivingEntity.java:127` sets 0.5F; 1.8 `:131` sets 0.6F), grounded and moving horizontally into a cake can enter the step path. The old cake top is 1/16 lower; when stepped onto, the resulting collision support/box height can differ by 1/16. This is a predicted movement effect from the exact boxes and step gate, not a runtime trajectory result.

## Confirmed difference: step eligibility gate

1.7.10, Ornithe Feather: `net/minecraft/entity/Entity.java`, `move(double,double,double)`, lines 383-558. 1.8, Ornithe Feather: same relative file/member, lines 370-534. Hashes above.

Both versions first clip ordinary requested movement Y, X, then Z. Both require `stepHeight > 0.0F`, pre-step support (`onGround || requestedY != clippedY && requestedY < 0.0`), and horizontal clipping (`requestedX != clippedX || requestedZ != clippedZ`). 1.7.10 adds `(onGround && isSneaking() && this instanceof PlayerEntity) || eyeHeightSneakOffset < 0.05F`; 1.8 removes that condition.

Reachable witness: the ordinary 1.7.10 local player tick sets `eyeHeightSneakOffset = 0.2F` when `input.sneaking` is true (`client/entity/living/player/InputClientPlayerEntity.java:150-153`), without requiring `onGround`. `LivingEntity.moveRelative` makes one movement call in its selected medium branch; `Entity.move` multiplies the offset by `0.4F` at entry (`Entity.java:391`), so the landing-tick gate reads `0.08F`. If initially airborne (`onGround == false`), sneaking, descending, horizontal movement clips, and requested Y is clipped by support on this move, then the pre-step support predicate is true but `var20` is false and `0.08F < 0.05F` is false. Old code declines step attempts; 1.8 enters step resolution. This is reachable when a sneaking player descends onto support while moving into an obstacle. Post-crouch release is a no-difference disposition: the previous move leaves `0.08F`, and a non-sneaking next movement decays it to `0.032F` before the gate, so the old gate permits steps. The gate is therefore not differential on ordinary non-sneaking local ticks. No trajectory was run.

## Confirmed difference: step candidate resolution

Same paired `Entity.move` members/lines and hashes as above. This is separate from the eligibility gate.

When both versions pass the common step preconditions, 1.7.10 restores the original box, raises by `stepHeight`, clips Y/X/Z in sequence against the expanded swept collision list, clips back down by `-stepHeight`, then compares the single stepped horizontal squared distance with ordinary movement (`ordinary >= stepped` restores ordinary). 1.8 computes two stepped candidates against the same swept list: candidate A clips Y against `box.expanded(dx, 0, dz)`, then X/Z; candidate B clips Y/X/Z against the original box. It selects A only when `var75*var75 + var76*var76 > var82*var82 + var83*var83` (`var84 > var48`, so ties select B), clips the selected result down, then restores ordinary movement when its horizontal squared distance is greater than or equal to the selected step.

Reachable precondition/effect: a grounded local player with positive step height moving into a collision (ordinary support already makes the common gate true), particularly a composition of adjacent partial collision boxes such as stairs/corners. The old route resolves one raised box; the new route can resolve a different box because it evaluates a second vertical clipping base and explicitly picks the farther horizontal candidate. Predicted consequence is a different stepped position or final box when the two candidate clips differ; exact numeric trajectories were not run.

The old code also gates each collision-displacement reset on `!immovable`; 1.8 has no equivalent. `immovable` is initialized true in the 1.7.10 `Entity` constructor and has no other assignment in the supplied entity tree, so no player-reaching effect from that field is established.

## Findings and limits

- The step eligibility and step candidate solver differences are source-confirmed and bounded to `Entity.move`; this slice does not establish their patch introduction within 1.7.10--1.8 or close the full pair. Carpet and cake shape differences are separate source-confirmed candidates.
- No-difference observations above are limited to the explicitly examined APIs/blocks. They are not a verdict for all block providers.
- No full-pair freeze claim: the 44-class provider set is paired at member level, but exact stair corner branch pairing, shared callback/state-reader closure, all downstream tick readers, registration closure and independent audit remain partial or unexamined.
