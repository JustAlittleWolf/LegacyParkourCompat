# Source review: 1.9.3 → 1.9.4

## Exact evidence and contract

- Namespace: `ornithe-feather`.
- 1.9.3 mapping-local provenance: `decompiled_minecraft/1.9.3/ornithe-feather/.decompile-provenance.json`, SHA-256 `43325cdd53ca34b88ee9af8464782b323a948b98c8a556c4761f1e5737b84c3a`.
- 1.9.4 mapping-named root provenance: `decompiled_minecraft/1.9.4/ornithe-feather.provenance.json`, SHA-256 `5c913715bed763369ceb5fd171cab3e63c43298023078c3683f96c7ea8fed3cd`.
- 1.9.4 sidecar source manifest: `decompiled_minecraft/1.9.4/ornithe-feather.sources.sha256`, SHA-256 `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19`.
- Manifest validation: 1,819 1.9.3 provenance source rows and Java files; 1,819 unique 1.9.4 manifest rows and Java files. Every listed source path exists and hashes to its declared SHA-256; no missing/extra Java paths or mismatches. The path union is exactly 1,819. No hidden 1.9.4 mapping-local provenance was found or assumed.
- Paired path/hash comparison found 16 changed Java source files. Core local-player movement sources were checked directly; their hashes match between releases except `PlayerEntity.java`, whose sole source diff is the NBT `DataVersion` value described below. Exact source bodies remain available and intact.

## Tick and movement inventory

### Examined; no movement difference found

- Input acquisition and scaling: `client/entity/living/player/Input.java:3-14`, `KeyboardInput.java:13-50`; hashes identical across the pair.
- Local client tick and input-to-player flow: `client/entity/living/player/LocalClientPlayerEntity.java:143-157, 586-597, 645-725, 727-750`; local tick invokes the normal player tick, records movement input in `serverTickAi`, and handles sprint, flight, elytra-start, and mount-jump input in `mobTick`. The paired file hash is identical (`8aaf7119…b704f5d`).
- Player tick/pose handoff: `entity/living/player/PlayerEntity.java:169-246, 285-310`; the inspected movement-related bodies are identical. The only changed body in this class is `writeCustomNbt` at line 717: `DataVersion` changes from 183 to 184. It affects saved-data version metadata, not player movement.
- Inherited movement and jump: `entity/living/LivingEntity.java:1275-1457, 1489-1702`, including jump impulse, sprint jump impulse, elytra/walking/water/lava branches, ground slipperiness, climb restrictions, collision movement, gravity/drag, and jumping gate. File hash is identical (`bbb7703f…54be4e5`).
- Position, box and collision resolution: `entity/Entity.java:277-307, 432-727`, including no-clip, cobweb damping, sneaking edge backoff, ordered Y/X/Z collision clipping, step-up fallback, position reconstruction, ground flags, block callbacks, and fall-damage dispatch. File hash is identical (`bcd7fa22…29c17ef0`).
- Collision query and block-state dependencies: `world/World.java:899-1018`, `block/Block.java`, `block/state/BlockState.java`, and `block/state/AbstractBlockState.java`; all paired hashes identical. Other selected modifier dependencies (`entity/living/attribute/EntityAttributeInstance.java`, `entity/living/effect/StatusEffect.java`, `StatusEffectInstance.java`) also hash identically.
- Direct player knockback/velocity, jump overrides, speed accessors, and pose dimensions are in the identical `PlayerEntity.java` bodies. No changed block, fluid, effect, attribute, item, or collision-provider source appeared among changed paths.

### Changed-source triage

The complete manifest hash comparison found only these changed paths:

`client/gui/overlay/DebugOverlay.java`, `client/gui/screen/menu/TitleScreen.java`, `client/Minecraft.java`, `client/options/ServerListEntry.java`, `entity/living/player/PlayerEntity.java`, `realms/RealmsSharedConstants.java`, `server/integrated/IntegratedServer.java`, `server/MinecraftServer.java`, `server/network/handler/RemoteHandshakeNetworkHandler.java`, `snooper/Snooper.java`, `util/crash/CrashReport.java`, `util/datafix/DataFixerUpper.java`, `util/datafix/DataFixes.java`, `world/chunk/storage/AnvilChunkStorage.java`, `world/storage/WorldSaveInfo.java`, and `world/WorldData.java`.

The client UI/display/version, telemetry, crash, datafix, chunk/storage and world metadata deltas do not alter the local player tick or movement. Integrated/dedicated server paths are excluded by project invariants. `server/network/handler/RemoteHandshakeNetworkHandler.java` is intentionally not opened because packet/protocol claims are out of scope for this worker. The changed `PlayerEntity.java` source diff is only the `DataVersion` integer and is unrelated to movement.

### Frozen exclusions

Pane geometry and piston-head-provider claims accepted through these releases were excluded from inspection and are not re-reported. Packet sources were not read.

## Findings and dependencies

- Candidate movement findings: none.
- The only relevant changed Java file has a metadata-only NBT version change; no movement dependency is opened by it.
- No intermediate release was inspected; no patch-introduction boundary within 1.9.3–1.9.4 is asserted.

## Coverage status and next action

- **Examined:** exact manifest/path/hash contract; changed-source inventory; client input and local-player tick path; player tick/pose, inherited movement/jump, entity position/collision resolution; selected collision/world and movement-modifier dependencies.
- **No difference:** all examined movement bodies and dependencies listed above, plus `PlayerEntity.writeCustomNbt` as a non-movement metadata-only difference.
- **Partial:** the complete movement tick's reachable callback/provider inventory has not been enumerated method-by-method across every block/fluid/equipment/effect implementation. Pair-wide hashes establish that sources outside the 16 changed paths are byte-identical, but this report does not claim full tick/provider inventory closure.
- **Unexamined:** the packet/protocol source named above; the excluded pane geometry and piston-head provider claims; exhaustive per-provider call-graph audit beyond the listed core sources.
- **Next action:** a separate blind reviewer should retrace the full tick and provider inventory, preserve the frozen exclusions, and verify dependencies before the pair can be frozen. No implementation action is supported by this comparison.
