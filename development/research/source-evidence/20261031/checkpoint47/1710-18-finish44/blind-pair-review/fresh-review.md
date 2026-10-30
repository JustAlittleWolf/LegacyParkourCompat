# Fresh blind source coverage record — 1.7.10 → 1.8 (Ornithe Feather)

## Phase 1: frozen independent coverage inventory

This inventory was created before opening any joined source-coverage review, tick/provider inventory from another reviewer, canonical finding, prior pair review, product source, wiki material, or pool42 report. It records my own traversal plan and source inventory only. The phase-1 record hash is to be captured before the assigned cross-review comparison.

### Exact inputs

| Release | Read-only root | `.decompile-provenance.json` SHA-256 | Assignment identity |
|---|---|---|---|
| 1.7.10 | `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.7.10\ornithe-feather` | `11bada441ebe07661402d1c40e1c490b78b279ecaa084f9a4dc3ae2dd54caf6f` | Feather mapping family; assignment manifest hash verified |
| 1.8 | `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.8\ornithe-feather` | `52c8312c34a1a491d4a200c84e90f5d8d95c7e5caa9571f864d8e70f9737db05` | Feather mapping family; assignment manifest hash verified |

The root manifests bind the decompiler inputs and per-source hashes. The hashes below independently bind the key files directly relied on during this phase. Source tree roots are read-only; no source regeneration was performed.

### Independently mapped local-player path and movement dependencies

| Slice | 1.7.10 path | 1.8 path | Coverage status before pair comparison |
|---|---|---|---|
| Local player tick entry and outgoing state | `client/entity/living/player/LocalClientPlayerEntity.tick/sendMovement` | `client/entity/living/player/LocalClientPlayerEntity.tick/sendMovement` | Entry identified; sending/correction interaction to be compared, excluding relative-only correction protocol applicability |
| Raw movement input interface and input state consumption | `client/entity/living/player/Input`, `KeyboardInput`; `InputClientPlayerEntity` | `client/entity/living/player/Input`, `KeyboardInput`; local-player `input` | Input-device sampling/plumbing excluded; equal raw logical input consumption remains in scope |
| Player tick and player-specific state | `entity/living/player/PlayerEntity.tick`, `mobTick`, sprint/sneak/pose/size/ability-related methods; `InputClientPlayerEntity.aiTick`, `mobTick`, movement-speed calculation | `entity/living/player/PlayerEntity.tick`, `mobTick`, sprint/sneak/pose/size/ability-related methods; `LocalClientPlayerEntity.mobTick` | Identified as the local-player-specific branch/state writers; paired method and call-order pass pending |
| Living movement and update lifecycle | `entity/living/LivingEntity.tick`, `baseTick`, `mobTick`, `aiTick`, `jump`, fall/climbing/water/lava predicates, movement-speed and sprint writers | Corresponding `entity/living/LivingEntity` members | Reachable shared local-player behavior; branch, gate and state read/write comparison pending |
| Entity tick and movement response | `entity/Entity.tick`, `baseTick`, `setSize`, `setPosition`, `move`, `canMove`, `updateVelocity`, `getCollisionShape`, water/liquid predicates, `checkBlockCollisions`, `checkFallDamage`, positioning and velocity writers | Corresponding `entity/Entity` members, including the 1.8 private shape update path | Primary position/velocity/box/collision response terminal; exact body/order comparison pending |
| World collision enumeration and entity query | `world/World.getCollisions`, `getBlockCollisions`, liquid/material scans, `world/chunk/WorldChunk` entity queries | Corresponding 1.8 methods | Block scan, dispatch and collision-list order dependency; paired exact body comparison pending |
| Player contact and direct response terminals | `PlayerEntity` entity-contact pass; `Entity.push`; block `onEntityCollision`, `onSteppedOn`, ladder and portal contact; piston collision/push path | Corresponding methods; 1.8 stateful block callback signatures | Direct player movement response stays in scope even where event production is excluded; callback reachability and bodies pending |
| State and shape dependency closure | `block/Block`, `Blocks`, `block/material/Material`; direct collision-provider owners below; old-era block registrations/state metadata | Corresponding 1.8 files plus same provider owners | Verify old-representable vanilla state mapping, shape mutations and immediate neighboring-block dependencies before any acceptance claim |

This is not yet a completeness or parity verdict. Remaining phase-1 work is the paired body/dependency pass for all enumerated collision providers and closure of each tick-path terminal. It explicitly leaves those slices open rather than inferring equality from names or caller similarity.

### Blind direct collision-provider declaration inventory

An independent recursive scan of `net/minecraft/block/**/*.java` enumerated declarations by exact case-sensitive method name `getCollisionShape` or `addCollisions`. There are 48 declarations in 44 declaring classes on each side. The 43 common declaring classes are to be paired by full old-era class/state behavior, not accepted by equal count. `CarpetBlock` is old-only in this direct-declaration set and `BannerBlock` is new-only; Banner is 1.8-only and excluded from old-era states, while removal of Carpet's override must be resolved against 1.8 `Block` default shape and registration/state dependencies. Multiple declarations per class are retained.

| 1.7.10 direct declarations (`net/minecraft/block`; line and member) | 1.8 direct declarations (`net/minecraft/block`; line and member) |
|---|---|
| `AbstractPressurePlateBlock.java:44 getCollisionShape` | `AbstractPressurePlateBlock.java:42 getCollisionShape` |
| `AbstractRailBlock.java:35 getCollisionShape` | `AbstractRailBlock.java:35 getCollisionShape` |
| `AirBlock.java:18 getCollisionShape` | `AirBlock.java:20 getCollisionShape` |
| `Block.java:836 addCollisions`; `Block.java:843 getCollisionShape` | `Block.java:292 addCollisions`; `Block.java:299 getCollisionShape` |
| `BrewingStandBlock.java:51 addCollisions` | `BrewingStandBlock.java:58 addCollisions` |
| `ButtonBlock.java:26 getCollisionShape` | `ButtonBlock.java:35 getCollisionShape` |
| `CactusBlock.java:46 getCollisionShape` | `CactusBlock.java:53 getCollisionShape` |
| `CakeBlock.java:41 getCollisionShape` | `CakeBlock.java:43 getCollisionShape` |
| `CarpetBlock.java:29 getCollisionShape` | **no direct declaration** |
| `CauldronBlock.java:56 addCollisions` | `CauldronBlock.java:35 addCollisions` |
| `CobwebBlock.java:29 getCollisionShape` | `CobwebBlock.java:32 getCollisionShape` |
| `CocoaBlock.java:77 getCollisionShape` | `CocoaBlock.java:58 getCollisionShape` |
| `DoorBlock.java:111 getCollisionShape` | `DoorBlock.java:65 getCollisionShape` |
| `EndPortalBlock.java:41 addCollisions` | `EndPortalBlock.java:42 addCollisions` |
| `EndPortalFrameBlock.java:60 addCollisions` | `EndPortalFrameBlock.java:39 addCollisions` |
| `FarmlandBlock.java:25 getCollisionShape` | `FarmlandBlock.java:29 getCollisionShape` |
| `FenceBlock.java:24 addCollisions` | `FenceBlock.java:31 addCollisions` |
| `FenceGateBlock.java:32 getCollisionShape` | `FenceGateBlock.java:53 getCollisionShape` |
| `FireBlock.java:55 getCollisionShape` | `FireBlock.java:119 getCollisionShape` |
| `HopperBlock.java:41 addCollisions` | `HopperBlock.java:42 addCollisions` |
| `LadderBlock.java:17 getCollisionShape` | `LadderBlock.java:28 getCollisionShape` |
| `LeverBlock.java:20 getCollisionShape` | `LeverBlock.java:29 getCollisionShape` |
| `LilyPadBlock.java:26 addCollisions`; `LilyPadBlock.java:33 getCollisionShape` | `LilyPadBlock.java:23 addCollisions`; `LilyPadBlock.java:30 getCollisionShape` |
| `LiquidBlock.java:125 getCollisionShape` | `LiquidBlock.java:110 getCollisionShape` |
| `MovingBlock.java:102 getCollisionShape`; `MovingBlock.java:141 getCollisionShape` | `MovingBlock.java:118 getCollisionShape`; `MovingBlock.java:162 getCollisionShape` |
| `PaneBlock.java:56 addCollisions` | `PaneBlock.java:62 addCollisions` |
| `PistonBaseBlock.java:272 addCollisions`; `PistonBaseBlock.java:278 getCollisionShape` | `PistonBaseBlock.java:211 addCollisions`; `PistonBaseBlock.java:217 getCollisionShape` |
| `PistonHeadBlock.java:113 addCollisions` | `PistonHeadBlock.java:86 addCollisions` |
| `PlantBlock.java:55 getCollisionShape` | `PlantBlock.java:57 getCollisionShape` |
| `PortalBlock.java:40 getCollisionShape` | `PortalBlock.java:50 getCollisionShape` |
| `RedstoneWireBlock.java:32 getCollisionShape` | `RedstoneWireBlock.java:71 getCollisionShape` |
| `SignBlock.java:33 getCollisionShape` | `SignBlock.java:26 getCollisionShape` |
| `SkullBlock.java:69 getCollisionShape` | `SkullBlock.java:80 getCollisionShape` |
| `SlabBlock.java:52 addCollisions` | `SlabBlock.java:63 addCollisions` |
| `SnowLayerBlock.java:32 getCollisionShape` | `SnowLayerBlock.java:40 getCollisionShape` |
| `SoulSandBlock.java:16 getCollisionShape` | `SoulSandBlock.java:18 getCollisionShape` |
| `StairsBlock.java:252 addCollisions` | `StairsBlock.java:386 addCollisions` |
| `SugarCaneBlock.java:79 getCollisionShape` | `SugarCaneBlock.java:92 getCollisionShape` |
| `TorchBlock.java:19 getCollisionShape` | `TorchBlock.java:32 getCollisionShape` |
| `TrapdoorBlock.java:48 getCollisionShape` | `TrapdoorBlock.java:58 getCollisionShape` |
| `TripwireBlock.java:28 getCollisionShape` | `TripwireBlock.java:57 getCollisionShape` |
| `TripwireHookBlock.java:19 getCollisionShape` | `TripwireHookBlock.java:40 getCollisionShape` |
| `VineBlock.java:108 getCollisionShape` | `VineBlock.java:132 getCollisionShape` |
| `WallBlock.java:91 getCollisionShape` | `WallBlock.java:100 getCollisionShape` |
| **No direct declaration** | `BannerBlock.java:33 getCollisionShape` |

### Phase-1 key source-file hashes (SHA-256)

These hashes bind each directly traversed local-player, movement, world-query, and central block-state/shape file used in the inventory. Collision-provider owner files are additionally bound by the two provenance manifests' complete per-source hash maps; their direct declaration locations are recorded above.

| Relative source path | 1.7.10 SHA-256 | 1.8 SHA-256 |
|---|---|---|
| `net/minecraft/client/entity/living/player/Input.java` | `aabf9cfed6156121c67c9f16002ccc02f14fa6213d5717732dcf9a1d26368b74` | `aabf9cfed6156121c67c9f16002ccc02f14fa6213d5717732dcf9a1d26368b74` |
| `net/minecraft/client/entity/living/player/KeyboardInput.java` | `e5fc1d40329a80720d5fccb60e97e94ca6cdd6f61c5cab47d91a2a9cc2470bb6` | `a5e5b2033322f8867cd845e4095cea7a82888f6382cbfafefc559cf9ba3a76dd` |
| `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `11b9c8e9691e434d9f80bdcfae3e5a62143817dc9ae0ca810c05974c8f8f548b` | `42a92a74e5c64992572760196a2052273d96bc061cd9d1120045ba03532f1e2c` |
| `net/minecraft/client/entity/living/player/InputClientPlayerEntity.java` | `1e37eeaebb22b759bfcae11c8c08f24b37dff7ebf5f5b17531f92e6cfbb48794` | n/a (no class at this path) |
| `net/minecraft/entity/living/player/PlayerEntity.java` | `350901699e449537372e1c4af6a79ae256de39638cdad049dfc7800b6607791c` | `13fa1367b71035efbfccc593de57838268b10a8ceebb0aa76ad3d819b44cb0ca` |
| `net/minecraft/entity/living/LivingEntity.java` | `5c1c5cf82d98c2f57a65b857fe02975b749c0562e4c1437ab5f072e202ca941c` | `645624913e265cad50293f659812a35c3c4303d1e8bdafdcca4eaf2e8ed5c69d` |
| `net/minecraft/entity/Entity.java` | `af4ef7b11bc709d96bd3eea1260a61d38a1a04996bdf87f2a77c459ddf070a69` | `2be081abe1720f89d7ab90f782b8411e71bc5b8a6b53966b49a2380e3c47ffd9` |
| `net/minecraft/world/World.java` | `2d648edfe7fdc953825a1df94709e889ea21354494d85762bca49b4a109d01f9` | `afbc701ef8b66020bdbb2831fd80683705d20237c68fbf23a4001c020876fc6d` |
| `net/minecraft/world/chunk/WorldChunk.java` | `04e7d26581387a7ec430b83fca9a25043fe02f88082a5b9fb26375b8e906f1e6` | `018ef0ca275c4afc5b7b0b41da1b2ca358b29a618c4ac56d438c5f09c98acd7d` |
| `net/minecraft/block/Block.java` | `462ea8943470de83f2d923d1d267bebc6b9af05eecf3c60c5e90eae870394d0b` | `4a4e6dfde24a135ecf87c359fd98f0380664e4bacaf679815c9016cb1ba0d814` |
| `net/minecraft/block/Blocks.java` | `b912e4c682f9e1ffecd916f3ee5cc6ff1bba3196e0634e20442b618a0a062e11` | `af42dc7d1fe95a3cc070427e32cd76213d6eab4df49118fee99aa3c7a3a004b0` |
| `net/minecraft/block/material/Material.java` | `4bab2fd59d23ad2f20afda4d1d5139fccd30ad3972024c810ff9db0c9218fe4a` | `a27bcc70e2712f150d8428773f8c2aafe98af9100a4fd05653057044d7d248a9` |

## Phase-1 freeze statement

The independent inventory is now frozen for comparison against the coordinator's joined coverage record. It contains a mapped ordinary local-player movement graph and a complete direct `getCollisionShape` / `addCollisions` declaration inventory as independently scanned. **It does not freeze the pair as source-complete:** provider body comparison, dependency closure, callback/state-writer terminal comparison, discrepancy reconciliation, and independent audit verdict remain open. Candidate acceptance and runtime parity are separate and are not asserted here.

Pre-comparison SHA-256 of the Phase-1 record at this freeze point: `A4BFB4585539A9BE018D0159E7B1EF7335B228283FE32FF0976782BB3A8A7D99`. The following is a post-freeze independent audit addendum; the Phase-1 record above is preserved verbatim.

## Phase 2: coverage comparison and corrections

### Independent source identity check

The two supplied manifest hashes match the assigned roots. I parsed each `.decompile-provenance.json` and rehashed every listed Java source under `net`: 1.7.10 has 1,803/1,803 listed files present and matching; 1.8 has 2,497/2,497. This binds the exact source files relied on below. Key directly audited sources in Phase 1 are also individually hashed there; the sleep response paths checked in Phase 2 are bound by these source hashes:

| Release | Relative path | SHA-256 |
|---|---|---|
| 1.7.10 | `net/minecraft/network/packet/s2c/play/PlayerSleepS2CPacket.java` | `da8a5443e9c2f3cc2788014ed090c0657cb0bc348a7ffed53043bc592b31a8df` |
| 1.8 | `net/minecraft/network/packet/s2c/play/PlayerSleepS2CPacket.java` | `19980bb6403941c405e59bdcc12dbe2c02585436b58d3020a450217d382a7350` |
| 1.7.10 | `net/minecraft/client/network/handler/ClientPlayNetworkHandler.java` | `bae3baa7bc005b339eac47d800cf391c3e35b0ea0e9162af344793d7d104e4d0` |
| 1.8 | `net/minecraft/client/network/handler/ClientPlayNetworkHandler.java` | `1cca0ded9f1112d407b149931c1f067a104d2beed74f1e7070200e8c035a102d` |
| 1.7.10 | `net/minecraft/entity/living/player/PlayerEntity.java` | `350901699e449537372e1c4af6a79ae256de39638cdad049dfc7800b6607791c` |
| 1.8 | `net/minecraft/entity/living/player/PlayerEntity.java` | `13fa1367b71035efbfccc593de57838268b10a8ceebb0aa76ad3d819b44cb0ca` |
| 1.7.10 | `net/minecraft/entity/vehicle/BoatEntity.java` | `572328b82321793b142117bcc9becfaa074f1dbb4405949e6bafe79bca7bcc44` |
| 1.8 | `net/minecraft/entity/vehicle/BoatEntity.java` | `d6f9f8ac97f5f337db499f0fe0b34a6e7b154b9fa79db270ef687ae0b412ec55` |
| 1.7.10 | `net/minecraft/entity/vehicle/MinecartEntity.java` | `1246cbc9263cbff7c448ff3bbcd148cb2bb8bc88184b129712448a0c3861ab95` |
| 1.8 | `net/minecraft/entity/vehicle/MinecartEntity.java` | `0cdb4811da9bf244c0e49e3dd4044f76a36b765a34af24bddd065e8582d90ffa` |
| 1.7.10 | `net/minecraft/client/entity/particle/FireworksParticles__Spark.java` | `88b25a2f08105f52af99faa9b9c8ea5a076407559492f5a3e62714b35ef104cf` |
| 1.8 | `net/minecraft/client/entity/particle/FireworksParticles__Spark.java` | `475ce364d16dccc21f7405d381d7894d3788565d7a6abcd9d1f51ad7b51f6639` |

### Ordinary local-player tick and response closure

Comparing the frozen inventory against the joined tick/core-role inventory and the paired coverage records disclosed a real Phase-1 under-enumeration: it began at `LocalClientPlayerEntity.tick` and named the tick consumers, but did not explicitly include `Minecraft` / loaded-area `World.tickEntity` entry, the `ClientWorld` role, or the full client packet/correction entry points. Those source routes were added to my Phase-2 traversal. The paired ordinary path closes through `Minecraft` → loaded-area world entity tick → local-player tick → living/player tick and movement-state decisions → living jump/travel and player `moveRelative` → `Entity.move` → block collection/query → collision-resolved local-player position, flags, and contact/liquid terminals. Class relocation from old `InputClientPlayerEntity` into new `LocalClientPlayerEntity` is accounted for.

The paired movement-source pass found the relevant body/order changes, not just matching method names: prior-sneak state enters the 1.8 sprint double-tap gate; flying sneak/jump vertical adjustment changes from the old fixed `0.15` increment to `flySpeed * 3.0F`; sprinting flight speed is doubled in new `PlayerEntity.moveRelative`; old step selection's airborne-sneak offset gate is removed and 1.8 adds a second horizontal-clearance candidate; and player default `stepHeight` changes from `0.5F` to `0.6F`. Shared `LivingEntity` travel/jump branches, ordinary entity movement resolver branches outside the cited step change, collision scan ordering/dispatch, block-contact and liquid terminals, and the old-representable vanilla state dependencies were compared through their bodies/readers. The 1.8-only depth-strider, slime, world-border, and spectator behavior is not projected onto old-era states. Relative-only correction protocol applicability remains unpaired by definition; the shared absolute local correction path was compared, with old/new player Y conventions normalized.

### Direct collision declaration inventory correction

The Phase-1 table is specifically a recursive scan of the block tree. A second whole-source-tree scan found four additional direct `Entity.getCollisionShape()` declarations on each side, for 52 total case-sensitive direct declarations (`getCollisionShape` or `addCollisions`) per tree. They are base `Entity`, `BoatEntity`, `MinecartEntity`, and nested `client/entity/particle/FireworksParticles__Spark`, each with one declaration. This corrects the Phase-1 label if read as a whole-tree declaration inventory: the 48 block declarations (44 declaring block classes) remain correct; the 43 common block classes were compared by the paired full body/dependency records, with old-only `CarpetBlock` and modern-only `BannerBlock` correctly classified. The remaining entity declarations were not silently counted as block providers.

The common entity declaration bodies were also paired: base `Entity` returns null in both; `MinecartEntity` returns null in both; and `BoatEntity` returns its current shape directly in old source and via `getShape()` in new source, whose implementation returns `this.shape`. The nested fireworks body returns null in both. These declarations add no extra old-era block provider delta; I did not extend the audit into general vehicle callers.

I checked the nested fireworks shape specifically. `FireworksParticles__Spark` extends `Particle` (and therefore inherits the entity type) but its `getCollisionShape()` returns `null` in both releases. `ParticleManager` owns it in particle collections; it is not inserted into the world's/chunk's entity list consumed by the local-player entity collision query. Consequently this nested declaration cannot supply a box to or reach the local-player collision response. This resolves the additional declaration without widening the audit into general non-player callers.

The provider-family comparison was not accepted on count alone. The full paired body/dependency records identify all 43 common block declaring classes by exact source location and cover 20 + 3 + 11 + 9 classes across the provider formula records, plus membership/replacement and moving-block alias closure. I checked the base `Block` fallback and the concrete Carpet and Cake bodies against the tree: the old Carpet override creates a zero-height collision plane while new Carpet inherits its registered 1/16 block shape; Cake's old top is `y + .5F - .0625F` and its new top is `y + .5F`, with the same bite inset. Moving-block aliasing has no old-era vanilla provider delta. The world collector, state/block query and registration dependencies, and old-representable state branches are included in the compared closure. No further common-class old-era shape delta was found in the paired body records.

### Preserved report errata and source corrections

The joined report identifies the correct 1.8 mergedv2 input hash as `3c020d1588b4aab403f5df077ca91b6e3776da2823d3b55202f4616f0d5ae5fe`; the earlier pool42 ordinary-tick inventory contains a one-character transcription typo (`...a403b5df...`). Keep that prior evidence unchanged and use the corrected value above.

I found a substantive source-inventory transcription error in the earlier callback/effects inventory: it described old `PlayerSleepS2CPacket` Y as an integer. The field is an `int`, but `read` uses `buffer.readByte()` and `write` uses `buffer.writeByte(this.y)`; signed byte decoding can wrap a high bed Y. The old handler passes those getters into `trySleep(int x, int y, int z)`, where a wrapped Y that is not loaded takes the fallback `setPosition(x + .5F, y + .9375F, z + .5F)` path. New packet stores `BlockPos`, reads/writes a block position, and handler calls `trySleep(BlockPos)`. The new corresponding fallback uses `.6875F` and new box placement semantics; the high-Y coordinate corruption is old-only. Preserve this erratum rather than editing its prior evidence. This discrepancy does not add an uninspected movement body; it corrects how the already-covered sleep response and the high-bed candidate are described.

### Independent coverage verdict and freeze state

**Verdict: ACCEPT WITH CORRECTIONS. Source coverage freeze: ACCEPTED after the corrections above.** The corrections are (1) Phase-1 did not itself enumerate the entire incoming tick/correction route, now closed in Phase 2; (2) its direct provider table was block-tree-scoped and must not be mistaken for the full-tree inventory, whose extra declarations are now classified; and (3) the prior sleep-packet inventory's integer-Y description is wrong and is corrected above. The independent full-tree declarations, all common block-provider bodies and immediate dependencies, ordinary local-player movement readers/writers, direct player response terminals, and the enumerated old-only/new-only dispositions have no remaining unpaired source body in assigned scope. The fireworks declaration does not reach player collision response.

Candidate acceptance is a separate decision represented by the paired candidate reviews; this coverage verdict neither upgrades nor substitutes for that decision. Runtime parity is unverified: no build, test, game, trajectory, or runtime experiment was performed, and no runtime-parity claim is made. This freeze is limited to equal raw logical input and corresponding old-era vanilla states in the assigned ordinary local-player movement/direct-response scope; exclusions and protocol-applicability boundary remain as stated in the assignment.
