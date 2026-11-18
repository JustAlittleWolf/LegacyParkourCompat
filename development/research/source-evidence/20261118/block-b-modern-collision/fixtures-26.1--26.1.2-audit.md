# Independent source audit: 26.1 fixtures, 26.1–26.1.2

Status: **COMPLETED — bounded source slice audited; no movement-shape difference found on either assigned edge.**

## Scope

Read-only audit of ordinary stationary player/block collision shapes for cauldron fill states, hopper, brewing stand, anvil damage variants, grindstone, stonecutter, lectern, and enchanting table. Checked exact registration, state selectors, shape construction/caching, and the local entity collision provider path. Compared only 26.1 → 26.1.1 and 26.1.1 → 26.1.2. No neighboring release edges were examined. Sources are the exact unobfuscated trees under `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\<version>\unobfuscated`.

## Evidence identity

All three ready markers have `status: ready`, matching `versionId` and `mapping: unobfuscated`. Their `sourceManifestSha256` values match the supplied release-root identities:

- 26.1: `b77289c135ac6e12aa35e75177cea52d968f49d49e1ec745e510b1a786c9aeba`
- 26.1.1: `88afdfb8264842713f8cce86c0bf88e95c2c991ee706349d6ed8b6c22d52fcb3`
- 26.1.2: `54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0`

For each edge, the paired source bodies were intact and their computed SHA-256 hashes matched each side's `unobfuscated.sources.sha256`. Every listed block implementation and the shared registration/provider sources below has the same file hash across all three releases, so each paired body comparison is byte-identical:

| Source | SHA-256 (all three releases) |
|---|---|
| `net/minecraft/world/level/block/AbstractCauldronBlock.java` | `a4e10cccf88b3c0d1cc64f33a06d8ad39c28f6b823483b910e71b9bd16191492` |
| `CauldronBlock.java` | `63d0a3da8b0198e2cec13384c16a7d7751f147b6a009784fd45a6807a9e17456` |
| `LayeredCauldronBlock.java` | `18be2624a0c45b6f807627a0f0febabc48a9a7092dc918411e94b49276976c2d` |
| `LavaCauldronBlock.java` | `979d958a18b76671c53cadf2a2b669a2a268a53008345a5d6b8d736e0b3584c5` |
| `HopperBlock.java` | `7a4e5afa06b90cac7800ab0984799d7933dea9954373653ac43c4697f075b1b5` |
| `BrewingStandBlock.java` | `08fbaa68f9b16510e7ff4585526e63043cac54eb476e222c11c13d8da404602f` |
| `AnvilBlock.java` | `a15b5ef5067f098a2e6b83d3b934730f376a3420a4ce47c798a6a80d800b9391` |
| `GrindstoneBlock.java` | `ffe4dd67c9b3c6ef2ed81b7baab0075aade78a92b9c7676cfa5d9ef79d763d73` |
| `StonecutterBlock.java` | `6fbb26aa6b535fe62ca25688c31f55635c3b1dd2c8038c7fdb7ed9b9bca61ab2` |
| `LecternBlock.java` | `a59665b6eba0ea0a87b20b05255a67d281102e312d99e63bfb041518b0a18949` |
| `EnchantingTableBlock.java` | `97c1f127b812ef20d375f53dcbff9afc09528e3dcb76823ca24ef2437cd5f852` |
| `net/minecraft/world/level/block/Blocks.java` | `ba8a258b33f73fe03f93e7b02f9c25d4f66cf3aaab04c4580d0863cc71bc866f` |
| `net/minecraft/world/level/block/Block.java` | `1693cfb7b84190a2fe664470a56d59e78ed5bd7d722a2bd16888b036d4d8e977` |
| `net/minecraft/world/level/block/state/BlockBehaviour.java` | `9db85de84e502903e6fe497f043b58620b92089236ffb213f0b93db979428d13` |
| `net/minecraft/world/level/CollisionGetter.java` | `dddff4897e8d5d01e3ac132d85c474e6a3b9bae928e48b8c907aa9eabdb7fb01` |
| `net/minecraft/world/level/BlockGetter.java` | `623a44206db93d8fb2eefc9353345689cc35e0c1742b5d61789eab2b098b687d` |
| `net/minecraft/world/phys/shapes/CollisionContext.java` | `8702d23bea0f119e0d63ce15ff59088559a319283186372fd69adebeed4dcd69` |
| `net/minecraft/world/phys/shapes/EntityCollisionContext.java` | `2f0a2bfd72a3d35121e3ef4ab80755f04cefaa7dd933988e2246b39ff5c39291` |

## Paired findings and verdicts

### Edge 26.1 → 26.1.1

**Verdict: no difference in the assigned slice.** The exact block implementations, `Blocks` registrations, cache logic, and `CollisionGetter`/`CollisionContext` path have byte-identical paired bodies. Cauldron fill-specific shapes, selectors, collision-vs-outline distinctions, shape rotations, and cached state functions therefore retain the same behavior over this edge.

### Edge 26.1.1 → 26.1.2

**Verdict: no difference in the assigned slice.** The same checked source set is byte-identical in this paired comparison; no registration, selector, shape construction, cache, or local provider-input delta was found.

## Coverage notes (26.1 source line references; paired files are identical)

- **Registration:** `Blocks.java:2634–2662` registers enchanting table, brewing stand, empty/water/lava/powder-snow cauldrons with the same constructors/properties; `2906–2935` registers normal/chipped/damaged anvils with `AnvilBlock::new`; `2979–2983` hopper; `5490–5514` grindstone, lectern, stonecutter. All entries are byte-identical on both edges.
- **Cauldrons:** `AbstractCauldronBlock.java:28–38,70–76` defines the static hollow outline and returns that outline for ordinary shape. `LayeredCauldronBlock.java:39–43,75–82` constructs two cached filled shapes and selects `FILLED_SHAPES[level - 1]` for the entity-inside collision shape; this level selector is not used by its ordinary block collision shape. `LavaCauldronBlock.java:19–20,43` uses a fixed filled shape. `CauldronBlock` is empty cauldron and has no fill-level selector.
- **Hopper:** `HopperBlock.java:54–80` defaults `FACING=DOWN`, `ENABLED=true`; ordinary outline is precomputed through `getShapeForEachState(..., ENABLED)`, so the enabled property is ignored for that mapping while facing determines the spout; interaction shape is independently precomputed by facing. The overload in `Block.java:592–602` builds an immutable state→shape map after replacing ignored properties with their first possible value.
- **Brewing stand:** `BrewingStandBlock.java:37,60–61` returns one static shape, independent of bottle state.
- **Anvils:** `AnvilBlock.java:35–36,77–78` selects a precomputed shape by `FACING` axis. Damage variants share this class, confirmed by `Blocks.java:2906–2935`.
- **Grindstone:** `GrindstoneBlock.java:47–65` precomputes rotated shapes and selects by `FACE` and `FACING`; its outline and collision shape both use that same selected shape.
- **Stonecutter / enchanting table:** their shape methods return fixed cached shapes (`StonecutterBlock.java:31,68–69`; `EnchantingTableBlock.java:36,58–59`), not state-selected shapes.
- **Lectern:** `LecternBlock.java:49–55,97–103` separates the fixed collision footprint from facing-rotated outline; collision does not vary with facing.
- **Caching:** `BlockBehaviour.java:680–685,509–514,924–947` keeps a collision-shape cache for non-dynamic shapes, computing from the state with `EmptyBlockGetter`, origin position, and `CollisionContext.empty()`. Context-taking collision queries dispatch to the block method. These cache rules are identical on both edges.
- **Local provider path:** `CollisionGetter.java:96–105` builds the block collision context from the supplied entity (`CollisionContext.of(source)`, or empty when null) and uses it while enumerating shapes. `CollisionContext` / `EntityCollisionContext` sources are identical. For a non-riding local player, this confirms an entity-backed collision context; these listed blocks' assigned shape methods do not branch on riding state or player-specific context. Their state selectors above govern the returned shapes.

## Residual gates

This closes only the named stationary block-shape/provider slice for the two assigned adjacent edges. It does not establish full player tick coverage, neighboring edges (including 26.1.2 → 26.2), dynamic movement through/against these blocks, vehicle/non-player behavior, or runtime parity. No implementation, tests, or game launch was performed.

