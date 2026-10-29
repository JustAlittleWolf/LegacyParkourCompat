# Ray-rejected Air and Fire provider closure (1.8.9 → 26.2)

## Bounded result

Legacy block ID 0 (`air`) and ID 51 (`fire`) are both explicitly rejected by the 1.8.9 ray-provider gate. `AirBlock.canRayTrace(state, allowLiquids)` returns `false`; `FireBlock.canRayTrace()` returns `false`. In `World.rayTrace`, the cell provider call occurs only after this gate succeeds, for both the initial cell and later DDA cells. Therefore neither block reaches `Block.rayTrace`, and its default `updateShape` call is bypassed for these providers. This is a provider-exclusion result; it does not assert cell visitation or traversal equivalence.

Both historical classes return `null` from `getCollisionShape`, so the standard `Block.addCollisions` path appends no movement box for either state. Air is registered as ID 0 and fire as ID 51 in the exact old `Block.java` source. Fire stores AGE 0–15 in metadata (`getStateFromMetadata` / `getMetadataFromState`); its additional virtual visual properties are irrelevant to the blocked ray provider and null movement collision in this bounded result.

In 26.2, `Blocks.AIR` is registered under `BlockItemIds.AIR` with `noCollision()` and `air()`; `AirBlock.getShape` returns `Shapes.empty()`. `Blocks.FIRE` is registered under `BlockIds.FIRE` with `noCollision()`, so the base collision provider returns `Shapes.empty()`. Fire's outline shape is separate: `FireBlock.getShape` selects a per-state shape, and when there are no directional attachments the fallback `BaseFireBlock.SHAPE` is a non-empty column from y=0 to 1/16. Thus `noCollision()` must not be treated as proof that modern outline geometry is empty. This report makes no claim about a current client ray caller's eligibility or the resulting hit.

The 26.2 `BlockStateData` table includes the explicit old Air tag mapping at state index 0 and Fire state mappings beginning at 816 (`51 << 4`) to current `minecraft:fire` states. This supports the bounded key identity; it does not claim full semantic equivalence for every legacy Fire visual property.

## Exact source references

- 1.8.9 `ornithe-feather`: `net/minecraft/block/Block.java:826,939` (ID registrations); `AirBlock.java:20,30`; `FireBlock.java:22,119,297,402–413`; `Block.java:340–360,456–459`; `net/minecraft/world/World.java:633–650,746–748` (provider gate before provider dispatch).
- 26.2 `unobfuscated`: `net/minecraft/world/level/block/Blocks.java:55,1233–1242`; `AirBlock.java:30–33`; `FireBlock.java:77–107`; `BaseFireBlock.java:30,52–54`; `state/BlockBehaviour.java:327–328,1094–1096`; `util/datafix/fixes/BlockStateData.java:747,1359–1362` and the remainder of the fire mapping table; `references/BlockIds.java:18`; `references/BlockItemIds.java:7`.

## Evidence identity

Canonical sources were read-only at `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft`. The ready markers identify 1.8.9 / `ornithe-feather` (Feather Gen2 build.2 merged-v2) and 26.2 / `unobfuscated`. The selected Java-file hashes and exact ready/provenance/source-manifest hashes are in [source-hashes.json](source-hashes.json). All 12 selected Java-file digests were recomputed and matched their canonical `.sources.sha256` rows.

## Limits

This closes only old ray-provider rejection and local movement-collision results for IDs 0 and 51, plus the stated current registration/collision/shape facts. It does not establish overall DDA order, current ray-hit behavior, other aliases, provider-cache history, movement trajectory, or end-to-end parity.
