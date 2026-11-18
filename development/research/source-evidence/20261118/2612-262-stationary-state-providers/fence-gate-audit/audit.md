# Fence gate stationary collision audit: 26.1.2 → 26.2

Status: frozen blind pre-claim audit. Scope is stationary, dry player collision for legal `FenceGateBlock` states. Surface/step behavior, dynamic placement and neighbor updates, other gate families, water responses, outline/occlusion, and all other movement operations are excluded.

## Immutable inputs

Both published unobfuscated source manifests were byte-hashed and match the assignment identities; each ready marker names the matching source-manifest digest and the artifact/diagnostics digests shown here.

| Version | Root sidecar | SHA-256 |
|---|---|---|
| 26.1.2 | `unobfuscated.sources.sha256` | `54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0` |
| 26.1.2 | `unobfuscated.provenance.json` | `8d727f880cc91f4a297e148c5b75976303d051457334bc689268fbdf631a012a` |
| 26.1.2 | `unobfuscated.ready.json` | `f24af0f4d38543c2bd9c19b50f347d4e89d6027050e4b21ae070734843946bb7` |
| 26.1.2 | `unobfuscated.success.log` | `f1823db562592e0f3e61aae1ee71709ed039f56338ba466a4071ed84c8e6f782` |
| 26.1.2 | `artifacts.sha256` | `89f648229de83b0109460695c9f1f3bed7ef36efb84ede9224e80cce87118f92` |
| 26.1.2 | `movement-diagnostics.txt` | `b4bd3875263a7155ac32cffd64cfacabc7bd54c82c5fe4082f1ef8a809024c02` |
| 26.2 | `unobfuscated.sources.sha256` | `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894` |
| 26.2 | `unobfuscated.provenance.json` | `a328f5333a1b27aadde4422b23159a323ae219ccd26f11207c1a5461366f27d1` |
| 26.2 | `unobfuscated.ready.json` | `f9406adb6bf7cb4c1ab0792a798ab2cb90e082ad4c2eee032c9f674d0ea8070f` |
| 26.2 | `unobfuscated.success.log` | `ff7d9d064e64f60c4f1bd38a042272782de4bfaaf1feefec3e6496e0a27a59a3` |
| 26.2 | `artifacts.sha256` | `ba9dc53a41bca9744ec1c8ab2ce9363764a22333c39ef64d5105f4e3bf5c98ba` |
| 26.2 | `movement-diagnostics.txt` | `ba6fd6c5b1c77b3ee2988f0c54968cc5b915680670fdb66699dbcbf8b9fe21d3` |

The 26.1.2 provenance records exact version `26.1.2`, unobfuscated namespace, Vineflower 1.12.0 / Java 25.0.3; the 26.2 provenance records exact version `26.2` and the same decompiler/runtime. Ready markers say `ready`, matching source manifest SHA-256 values, and source counts 6,882 and 7,055 respectively.

## Frozen source roster

Every Java digest below was checked against its corresponding source-manifest entry and independently hashed from the exact file bytes. Files are `decompiled_minecraft/<version>/unobfuscated/<relative path>`.

| Relative Java source | 26.1.2 SHA-256 | 26.2 SHA-256 | Use |
|---|---|---|---|
| `net/minecraft/world/level/block/FenceGateBlock.java` | `e9e51b00bf59a66951741e0f0ef28d5d92815df9591e4f229fdb838588d105df` | `0a98db4316efae2e8450c7d8afe496ed45fa5ee9cddb8bd27359d15c355471e3` | State defaults, shape maps and accessors |
| `net/minecraft/world/level/block/Blocks.java` | `ba8a258b33f73fe03f93e7b02f9c25d4f66cf3aaab04c4580d0863cc71bc866f` | `f3f2faeed23e9697407069a1d523107491590b8710175523ea05294d5bd00435` | Every built-in fence-gate registration/property recipe |
| `net/minecraft/world/level/block/Block.java` | `1693cfb7b84190a2fe664470a56d59e78ed5bd7d722a2bd16888b036d4d8e977` | `cec6a05e644e4a7feb8253cc4ca772a98f0e116fb098a1b7ee7302984ac7ecab` | Box/column geometry helpers |
| `net/minecraft/world/level/block/state/BlockBehaviour.java` | `9db85de84e502903e6fe497f043b58620b92089236ffb213f0b93db979428d13` | `9c7a103492d0714c90397da88eb696912ff6a9ca1c005d984c4746d52637fd1e` | Collision dispatch, flags and state-local cache |
| `net/minecraft/world/level/block/state/BlockState.java` | `df5b24701ee69145a78901ee0e370e063b63b3e74a0aae32c89b2ae45bc6d205` | `df5b24701ee69145a78901ee0e370e063b63b3e74a0aae32c89b2ae45bc6d205` | State wrapper / access path |
| `net/minecraft/world/phys/shapes/Shapes.java` | `cfa63cbed77c922a68e7e8a6a3b78b09a22c7de51e0146e481e9c9d26db66f58` | `6ed9460d58727770bdec59c4f3184f075644f3c411be1eeab2eae17e8ece1891` | Horizontal-axis map construction |

## Bounded finding

No stationary dry collision-shape difference found between 26.1.2 and 26.2 for `FenceGateBlock`.

Legal state dimensions are `FACING` ∈ {north, south, east, west}, `OPEN` ∈ {false,true}, `POWERED` ∈ {false,true}, and `IN_WALL` ∈ {false,true}; the class defines all four properties. For `getCollisionShape`, `POWERED` and `IN_WALL` are ignored. `OPEN=true` returns the shared empty shape for every facing and wall state. `OPEN=false` returns the static axis map built from `Block.column(16,4,0,24)`: N/S (`Axis.Z`) occupies `[0,1] × [0,1.5] × [3/8,5/8]`; E/W (`Axis.X`) occupies `[3/8,5/8] × [0,1.5] × [0,1]` (coordinates are local x,y,z).

The native block properties for each of the 12 registrations on both sides set `forceSolidOn`, bass instrument, strength `(2.0F,3.0F)`, and lava ignition, with wood-specific plank map color. Defaults remain `hasCollision=true`, `canOcclude=true`, and `dynamicShape=false`; no offset function is supplied. The override directly returns its open/closed shape and does not consult the base `hasCollision` flag. The class-level `SHAPE_COLLISION` axis map is static and shared by the registrations. Vanilla `BlockBehaviour.BlockStateBase.initCache` creates the ordinary per-state collision cache because the gate is not dynamic; its cache invokes this state-only collision method with empty context and empty block getter. `forceSolidOn` affects the legacy solid flag independently of the partial collision box. The per-state cache and class map are vanilla provider behavior, not a source difference in this pair.

The only `FenceGateBlock.java` text delta found is the `isPathfindable` switch changing statement syntax to switch-expression syntax; its LAND/AIR return `OPEN`, WATER returns false, and default returns false in both. This path is outside the assigned water-response scope and does not affect the collision result above.

## Coverage boundary

Closed only: exact-pair stationary dry `FenceGateBlock` collision shape over legal state values, including static provider construction, class registrations/properties, inherited collision gating/cache behavior, and the horizontal-axis shape helper. No claim is made about outline/occlusion shapes, movement-loop consumers beyond the direct collision shape, surface/step mechanics, placements or neighbor transitions, other gate classes, fluid/pathfinding behavior, other versions, or runtime parity.