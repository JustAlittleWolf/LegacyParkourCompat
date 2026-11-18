# Frozen blind audit: ordinary player block slowdown

**Pair:** 26.1.1 → 26.1.2, published unobfuscated sources.  
**Bound:** ordinary local, non-riding Player contact with honey, cobweb (`WebBlock`), and sweet berry bush. Excludes damage resolution. This is a bounded slice verdict, not full-pair coverage or a runtime-parity claim.

## Verdict

No source difference was found in the compared block-contact paths, player stuck-response override, movement consumer, or local tick dispatch. All six compared source files have identical SHA-256 content in both releases, and each file hash matches its release source manifest. Under the bounded same-input condition that collision/contact geometry and the engine's block-intersection query results are equal, these contacts produce the same player movement state and next-move consumption in the two releases.

## Trace and concrete behavior

For a locally authoritative client Player that is not riding and is not flying, `LivingEntity` calls `travel(input)` and then `applyEffectsFromBlocks()` in the same tick. `Entity` walks its recorded movement path, queries intersected block states and entity-inside shapes, and dispatches `state.entityInside(...)` for accepted block intersections. These producer calls therefore happen after that tick's travel movement. A stuck-speed vector they set is consumed by the next `Entity.move` call.

- **Honey:** `HoneyBlock.entityInside` runs the slide only when the entity is airborne, its feet are at or below `blockY + 0.9375 - 1.0E-7`, reconstructed old vertical delta `deltaY / 0.98F + 0.08` is below `-0.08`, and its horizontal center is sufficiently outside the block's overlap distance on X or Z. If reconstructed old vertical delta is below `-0.13`, X/Z velocity is multiplied by `-0.05 / oldDeltaY`; otherwise X/Z is retained. Both branches set Y from `(−0.05 − 0.08) * 0.98F`, then reset fall distance. The gate, float constants, operation order, write order, and reset are byte-identical across this pair.
- **Cobweb:** `WebBlock.entityInside` selects `(0.25, 0.05F, 0.25)`, or `(0.5, 0.25, 0.5)` for a living entity with Weaving, and calls `makeStuckInBlock`.
- **Sweet berry bush:** for a living entity other than Fox or Bee, `SweetBerryBushBlock.entityInside` calls `makeStuckInBlock` with `(0.8F, 0.75, 0.8)`. Its server-side bush damage branch is outside this audit and does not change this local movement response.
- **Player and next move:** the Player override delegates to `Entity.makeStuckInBlock` when not flying; that method resets fall distance and stores the vector. At the start of `Entity.move`, a stored vector with squared length over `1.0E-7` multiplies requested movement unless the mover type is piston, then clears the vector and zeros delta movement. The ordinary non-riding player path consumes the pending block slowdown once, on its next move. Each of the cobweb and berry multipliers is above the consumer threshold.

The inspected caller and producer/consumer bodies are identical by whole-file SHA, so their same-input operation order and Java numeric behavior are unchanged. This conclusion holds the collision shape, support state and query results equal; it does not establish those inputs or trajectory parity for arbitrary worlds.

## Paired source evidence

The relative paths below are under `decompiled_minecraft/<version>/unobfuscated/`. All entries on a row have the same listed SHA in both 26.1.1 and 26.1.2, and the actual bytes in each tree match the corresponding `unobfuscated.sources.sha256` entry.

| Source file and member | Lines | SHA-256 |
| --- | ---: | --- |
| `net/minecraft/world/entity/LivingEntity.java`, `tick` travel and client block-effect dispatch | 3058–3085 | `c3b64de8dbaba8ad8a7ccd4f33255346d8206e66f12ca91260971bf5e5ac93bd` |
| `net/minecraft/world/entity/Entity.java`, `move` stored multiplier consumer | 704–729 | `8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf` |
| `net/minecraft/world/entity/Entity.java`, block-effect dispatch and intersection path | 881–900, 1195–1279 | same `Entity.java` SHA above |
| `net/minecraft/world/entity/Entity.java`, `makeStuckInBlock` | 2876–2879 | same `Entity.java` SHA above |
| `net/minecraft/world/entity/player/Player.java`, `makeStuckInBlock` override | 1494–1500 | `44cf28e0c64e78d39fd13368e9991381dbebab67029070cb9ddc43f09d45d14d` |
| `net/minecraft/world/level/block/HoneyBlock.java`, contact, slide gates and movement | 64–124 | `998aff733f7bb654fcd837680375ba740574d3371d39bd708dbdae352d4782ec` |
| `net/minecraft/world/level/block/WebBlock.java`, contact speed selection | 31–41 | `8afb61bea4d6fc8502971d0729c6a2f87f4f55d618ae6ed3251b95141f524af5` |
| `net/minecraft/world/level/block/SweetBerryBushBlock.java`, contact and speed selection | 82–101 | `ca930f1c40d72a03e55e9972a58b4cc3df645ea918625125b4255343bf893a1d` |

## Local source pins and origin limits

The 26.1.1 and 26.1.2 ready markers each report `status: ready`, exact version ID, `unobfuscated` mapping, 6,882 sources, and source-byte totals that match the canonical local trees. Each ready marker's source-manifest and artifact-manifest SHA-256 matches the local manifest file. The listed source file hashes also match their manifest entries. Marker SHA-256: 26.1.1 `29540fe56da79d19a9b125a091cf89cdb45e4493e4c5898c677658dca1474802`; 26.1.2 `f24af0f4d38543c2bd9c19b50f347d4e89d6027050e4b21ae070734843946bb7`.

- **26.1.1:** ready marker pins client SHA-256 `8c109be6177f40d10d0afd820b06542f9609597874242b96e16c5b0dc9e1fef3`; artifact manifest contains the same client hash and version-metadata SHA-256 `86b232149a2ae224b06443730fe32c46c306f035a1599e3477c5237df1c950ef`. Provenance records the official client URL and SHA-1, the metadata SHA-256, exact release ID, unobfuscated mode, and tool/runtime versions. The ready/provenance record points to a dated campaign staging/ready location; the canonical tree is tied to that record through matching whole-source-manifest content, not by its own output-path field.
- **26.1.2:** ready marker pins client SHA-256 `b1b3158572666445eff01e82fad8c7de2e4953db6d354f311730d77a8359d0b0`; artifact manifest contains the same client hash and version-metadata SHA-256 `739c0e0520086c1a6d3dcd3ceec4f9c209848146e8a6012503241a3ba8117775`. Provenance records exact release ID, unobfuscated mode and tool/runtime versions, but unlike 26.1.1 it does not record the client URL/SHA-1, metadata URL, or metadata SHA-256 in the provenance object. The local ready/artifact manifests pin those content hashes; they do not independently establish the upstream origin. Its provenance likewise points to a dated staging output, with the canonical source tree tied by the matching whole-source-manifest hash.
