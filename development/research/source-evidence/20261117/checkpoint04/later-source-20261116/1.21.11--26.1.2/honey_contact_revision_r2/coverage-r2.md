# Honey block contact response: 1.21.11 → 26.1.2

## Bounded verdict

**NO_DIFF for Honey block's direct player slide predicate and velocity response** in the exact 1.21.11 Mojmap and 26.1.2 unobfuscated sources. The callback order, predicates, numeric expressions, writes, and Honey block movement-factor registration match. This is a bounded source slice, not pair freeze, applicability closure, or runtime parity.

## Exact source identity

Read-only roots: `decompiled_minecraft/1.21.11/mojmap` (version ID `1.21.11`, namespace `mojmap`) and `decompiled_minecraft/26.1.2/unobfuscated` (version ID `26.1.2`, namespace `unobfuscated`). Each root's `.ready.json` binds its matching version and namespace to its source-manifest and artifact-manifest digests. Sidecar identities:

| Endpoint | Ready manifest | Provenance | Source manifest | Artifact manifest |
| --- | --- | --- | --- | --- |
| 1.21.11 Mojmap | `mojmap.ready.json` `0ad98d0ebd654650492c97eb58bc324d33864787f0ce10d99faec3ae2f5b804b` | `mojmap.provenance.json` `99c9fb741eb5e4ac8fb5780e2158da324d7c8b6afc179d494e5986f78fe29f72` | `mojmap.sources.sha256` `0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555` | `artifacts.sha256` `c8210b15012dcc4e109c2f73015b4117abec1f8ab04fdd375dbc11419e9e246c` |
| 26.1.2 unobfuscated | `unobfuscated.ready.json` `f24af0f4d38543c2bd9c19b50f347d4e89d6027050e4b21ae070734843946bb7` | `unobfuscated.provenance.json` `8d727f880cc91f4a297e148c5b75976303d051457334bc689268fbdf631a012a` | `unobfuscated.sources.sha256` `54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0` | `artifacts.sha256` `89f648229de83b0109460695c9f1f3bed7ef36efb84ede9224e80cce87118f92` |

The ready records report `ready`, source counts 6,622 and 6,882 respectively, and source/artifact manifest hashes matching the listed files. The decompiler provenance records the mapping option and exact version; no online mapping source was used. Every Java file below was SHA-256 checked against its endpoint's source manifest.

| Relative Java path | 1.21.11 SHA-256 | 26.1.2 SHA-256 |
| --- | --- | --- |
| `net/minecraft/world/level/block/HoneyBlock.java` | `d6f11b868160cba12b45b1818265ab870fcb96bbb6f28fdc13952013bdf2ab32` | `998aff733f7bb654fcd837680375ba740574d3371d39bd708dbdae352d4782ec` |
| `net/minecraft/world/entity/Entity.java` | `32314478c6036fa9f3f3cc409c61c622eefc5a282d60e1011a1a33cc29cf18a3` | `8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf` |
| `net/minecraft/world/entity/LivingEntity.java` | `19ed2d565858c401c69a06750b054a633a20ea864cab3a753b5c767f0bdd60e8` | `c3b64de8dbaba8ad8a7ccd4f33255346d8206e66f12ca91260971bf5e5ac93bd` |
| `net/minecraft/world/entity/player/Player.java` | `8e97167350a91741d0aa10d3b0d92a33150ed6dccdc94cd5b37d9c7ca22bcc81` | `44cf28e0c64e78d39fd13368e9991381dbebab67029070cb9ddc43f09d45d14d` |
| `net/minecraft/client/player/LocalPlayer.java` | `948e94f8e874b2f72e9a689e6e3ba1933f5728ec381d64f3bb5e2388718bd477` | `433fd995ad317af0f6ef0e50c1e8e3483cb8f00e0e327d4edf27a4dd99666ebe` |
| `net/minecraft/world/level/block/Blocks.java` | `cc6636f6ace603b32edd3db4c0cfcda60be08e2c6b5def783a698f105b8f7f23` | `ba8a258b33f73fe03f93e7b02f9c25d4f66cf3aaab04c4580d0863cc71bc866f` |
| `net/minecraft/world/level/block/state/BlockBehaviour.java` | `cf7eff07efb53d95c8ffa633ff38c7d3f454e185f197de49eb4da279a8da9d17` | `9db85de84e502903e6fe497f043b58620b92089236ffb213f0b93db979428d13` |
| `net/minecraft/world/level/Level.java` | `de329fdbeca632a1b63074e1735890b2624abfc57b7a579f43eef36caefef98d` | `77cc835fc1a79cd970bc3f4897fe5f398958fcbc874400c297f465f0c7819f01` |

## Paired source evidence

- `HoneyBlock.entityInside` (1.21.11 lines 63–71; 26.1.2 lines 64–79) calls `isSlidingDown`; on true it calls achievement handling, `doSlideMovement`, then slide effects, and afterward delegates to the superclass. This order matches. In the sound helper, 1.21.11 reads `level.random` while 26.1.2 first reads `level.getRandom()`; `Level.getRandom()` returns `this.random` on both endpoints (1.21.11 line 921; 26.1.2 line 1044), so this accessor spelling preserves the same random source.
- `isSlidingDown` (1.21.11 lines 81–98; 26.1.2 lines 89–106) has the same gates in the same order: reject grounded entities; reject entity Y above block Y + `0.9375 - 1.0E-7`; reject when `getOldDeltaY(currentY) >= -0.08`; then compare absolute X/Z offsets from block center against `0.4375 + width/2`, using `+1.0E-7` and OR. The `getOldDeltaY` expression is `y / 0.98F + 0.08` on both sides.
- `doSlideMovement` (1.21.11 lines 106–116; 26.1.2 lines 114–124) uses the same strict threshold `getOldDeltaY(currentY) < -0.13`. Below it, both horizontal components are multiplied by `-0.05 / getOldDeltaY(currentY)`; otherwise they are preserved. Both replace vertical movement with `getNewDeltaY(-0.05)`, where `getNewDeltaY(y) = (y - 0.08) * 0.98F`, then reset fall distance. Casts and float/double expressions match. The reset is part of the source response but damage calculation is outside this slice.
- `Blocks.HONEY_BLOCK` (1.21.11 lines 5916–5920; 26.1.2 lines 5928–5932) has the same registration and properties, including `.speedFactor(0.4F)` and `.jumpFactor(0.5F)`. No directly used movement-factor delta is present.
- Dispatch is source-reachable for the local player subject to block intersection and the entity's block-effect gate: `LocalPlayer.aiStep` delegates to `Player.aiStep` (old line 875; new line 914), and `Player.aiStep` delegates into `LivingEntity.aiStep` (old line 463; new line 453). `LivingEntity.aiStep` calls `applyEffectsFromBlocks` under `!clientSide || isLocalInstanceAuthoritative` (1.21.11 lines 2977–2979; 26.1.2 lines 3076–3078). `Entity.isLocalInstanceAuthoritative` delegates to `isLocalClientAuthoritative` client-side (old lines 3424–3431; new 3516–3523); `Player.isLocalClientAuthoritative` returns `isLocalPlayer` (old 1242–1244; new 1260–1262), and `LocalPlayer.isLocalPlayer` returns true (old 359–362; new 377–380). `Entity.checkInsideBlocks` calls `state.entityInside` on intersected block states (old lines 1186–1219; new 1225–1269); `BlockBehaviour` forwards that state callback to the block implementation (old lines 741–743; new 774–778). Pair-wide travel and contact coverage are not inferred from these routes.

## Limits and separate observations

The Honey slide effect helper also emits sound/event effects; this report's movement verdict covers the predicate and delta movement/reset operations only. Non-player consequences, Honey collision shape, fall damage, ordinary-world state reachability, resources, other movement operations, intermediate release boundaries, implementation applicability, and runtime parity are unexamined here. The pair remains partial pending its independent coverage inventory and audit.



