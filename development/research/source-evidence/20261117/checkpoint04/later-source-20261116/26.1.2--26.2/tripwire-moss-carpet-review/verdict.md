# TripWire and pale moss carpet shape/support review

## Exact input identity

Paired canonical sources: `decompiled_minecraft/26.1.2/unobfuscated` and `decompiled_minecraft/26.2/unobfuscated`; mapping namespace is `unobfuscated` on both sides.

- 26.1.2 ready marker identifies version `26.1.2`, namespace `unobfuscated`, 6,882 source files, and source-manifest SHA-256 `54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0`. Actual `unobfuscated.sources.sha256` SHA-256 recomputed: same. Ready-marker JSON SHA-256 recomputed: `f24af0f4d38543c2bd9c19b50f347d4e89d6027050e4b21ae070734843946bb7`.
- 26.2 ready marker identifies version `26.2`, namespace `unobfuscated`, 7,055 source files, and source-manifest SHA-256 `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894`. Actual `unobfuscated.sources.sha256` SHA-256 recomputed: same. Ready-marker JSON SHA-256 recomputed: `f9406adb6bf7cb4c1ab0792a798ab2cb90e082ad4c2eee032c9f674d0ea8070f`.

Each source hash below was recomputed from the actual source file and compared against that version's manifest entry; every comparison matched. Hashes are SHA-256.

| Relative source | 26.1.2 | 26.2 |
| --- | --- | --- |
| `net/minecraft/world/level/block/TripWireBlock.java` | `84a31077e77005805cb8f3e697f02c3d3f90c5e671f4ae4304730105d2a6a80d` | `3b379d732599e55b156ffde5ebd0f7043ab4d59e6556f128e830038c77c9249a` |
| `net/minecraft/world/level/block/TripWireHookBlock.java` | `8e9897c46acd70209e82cbc609b92de636c2655b400d823b35390dd63b756f94` | `c08a22c1da82367373a3aab1fcddae4e4821e523216c090a44c60a1b2786a52f` |
| `net/minecraft/world/level/block/MossyCarpetBlock.java` | `2aa1fcd17313b35a87c5d35cb93290c281cce8047903ff91d5ed60bdda886284` | `2aab884a6b8be3dd4e7fa0f971ca1b916bdde03a863603e98d698abf4be87f44` |
| `net/minecraft/world/level/block/CarpetBlock.java` | `ce669310bf7ab918e5bbdb07253b9030a516ae143cd4aa9e464bd7f2fdedffdc` | `ce669310bf7ab918e5bbdb07253b9030a516ae143cd4aa9e464bd7f2fdedffdc` |
| `net/minecraft/world/level/block/MultifaceBlock.java` | `07229d3657de1ac039cabca91ccef80d84c356330f701e7c2fcdcee2f8382b36` | `07229d3657de1ac039cabca91ccef80d84c356330f701e7c2fcdcee2f8382b36` |
| `net/minecraft/world/level/block/Blocks.java` | `ba8a258b33f73fe03f93e7b02f9c25d4f66cf3aaab04c4580d0863cc71bc866f` | `f3f2faeed23e9697407069a1d523107491590b8710175523ea05294d5bd00435` |
| `net/minecraft/world/level/block/state/BlockBehaviour.java` | `9db85de84e502903e6fe497f043b58620b92089236ffb213f0b93db979428d13` | `9c7a103492d0714c90397da88eb696912ff6a9ca1c005d984c4746d52637fd1e` |
| `net/minecraft/world/level/BlockCollisions.java` | `eb8a8f6f07b1d6384f17987818f056d41adfd6b2ad120d26774bfa550f4fc51e` | `eb8a8f6f07b1d6384f17987818f056d41adfd6b2ad120d26774bfa550f4fc51e` |

## Bounded verdict

No difference was found between 26.1.2 and 26.2 in TripWire or pale moss carpet outline, collision, or support shape selection, or in the connected-block face-support path checked below. This is a scoped no-difference report, not a movement finding.

### TripWire and adjacent hook

`TripWireBlock.java`, 26.1.2 lines 43-44 and 69-72; 26.2 lines 43-44 and 69-72: both releases define the same attached/unattached shapes and choose between them from `ATTACHED`. Constructor defaults at lines 55-65 in each release set `POWERED`, `ATTACHED`, `DISARMED`, and the four horizontal connection properties to false.

`Blocks.java`, 26.1.2 lines 2739-2741; 26.2 lines 2668-2670: `TRIPWIRE` remains registered with `Properties.of().noCollision().pushReaction(PushReaction.DESTROY)`. Thus the state outline shape is not its movement collision shape: inherited `BlockBehaviour.getCollisionShape` returns empty when `hasCollision` is false. It supplies no support through the default support selector, which delegates to collision.

`TripWireHookBlock.java`, both releases lines 42, 55-56: the same facing-indexed outline shape is selected. Hook registration remains no-collision (26.1.2 `Blocks.java` lines 2736-2738; 26.2 lines 2664-2667), as does tripwire. The distinct source hashes on TripWire/Hook reflect non-shape edits; the hook selector and registration behavior above are equal.

### Pale moss carpet and neighboring face support

`Blocks.java`, 26.1.2 lines 6985-6995; 26.2 lines 5787-5797: `PALE_MOSS_CARPET` remains a `MossyCarpetBlock` with the same properties. The registry key changes from string literal `"pale_moss_carpet"` to `BlockItemIds.PALE_MOSS_CARPET`; no shape/default change follows from this spelling change.

`MossyCarpetBlock.java`, both releases lines 54-63: defaults are `BASE=true` and all four `WallSide` values `NONE`. Lines 66-96 in each release construct the same state outline: the base plate and any LOW/TALL side pieces are combined; an empty result maps to a full-block outline fallback. The distinct collision override at lines 94-97 is equal across releases: when `BASE=true`, collision is calculated from the default state (the base plate with no side pieces); when `BASE=false`, collision is empty. Consequently wall-side outline pieces are not collision/support faces. The generic `CarpetBlock.java` is byte-for-byte identical across the pair and is not the pale moss carpet implementation.

The adjacent-state path is unchanged. `MossyCarpetBlock.canSupportAtFace` and `getUpdatedState` (26.1.2 lines 124-159; same lines in 26.2) use `MultifaceBlock.canAttachTo` to create LOW/TALL sides and consult above/below pale moss carpet states. `MultifaceBlock.canAttachTo` is byte-for-byte identical (lines 250-260): it accepts a neighbor if the requested face is full in either `getBlockSupportShape` or `getCollisionShape`.

### Selectors and consumers

`BlockBehaviour` selector implementations are equal in behavior (line offsets below reflect source edits elsewhere):

- support defaults to collision: 26.1.2 lines 297-299; 26.2 lines 291-293;
- outline defaults to a full block: 26.1.2 lines 329-331; 26.2 lines 323-325;
- collision defaults to `hasCollision ? state.getShape(level, pos) : Shapes.empty()`: 26.1.2 lines 333-335; 26.2 lines 327-329;
- visual defaults to collision: 26.1.2 lines 345-347; 26.2 lines 339-341.

The `BlockState` wrappers dispatch outline, collision, support, and visual independently to the matching block selector (26.1.2 lines 672-703; 26.2 lines 678-705). Ordinary block collision enumeration uses the collision selector, not the outline selector: `BlockCollisions.computeNext` calls `context.getCollisionShape(...)` at line 93 in both versions. For connected-block face placement, `MultifaceBlock.canAttachTo` consumes support/collision shapes as described above. These paired consumer paths reveal no selector change affecting either audited block.

## Excluded from this slice

Water movement/current/flow is a separate fluid movement operation; this review is bounded to these blocks' geometry, support state and their face-support consumer. Water-related behavior was not treated as evidence for a TripWire/carpet shape difference.

TripWire's entity trigger and redstone tick scheduling are not movement collision/support. `TripWireBlock.entityInside` is gated by `!level.isClientSide()` (26.1.2 lines 150-163; 26.2 same region); 26.2 adds a scheduled-tick guard, and the later pressed-state recheck scheduling changes are server trigger behavior. They do not change `getShape`, collision or support selection, and historical movement never runs on the logical server. They are therefore excluded from this client movement shape audit.

