# DDA provider closure: 1.8.9 ore IDs 129 and 153

## Scope and source identity

This source-only result closes the old/current registry and collision-provider mapping for two state-independent full-cube ore blocks. It does not establish ray visitation equivalence, camera-path reachability, any cache mutation from unrelated blocks, a complete 1.8.9 ray adapter, or runtime parity.

The older source is exact 1.8.9, `ornithe-feather` (Feather Gen2 `1.8.9+build.2`, as identified by the ready marker and provenance). The current source is exact 26.2, `unobfuscated`. The ready-marker, provenance, and source-manifest identities, and every selected Java source, are pinned in `sources.sha256`. All eight selected Java file digests were recomputed and matched their release source manifest.

## Old registry and metadata

The 1.8.9 static registry in `net/minecraft/block/Block.java` registers ID 129 as `emerald_ore` at line 1112 and ID 153 as `quartz_ore` at line 1168. `Blocks.java` binds those names to `EMERALD_ORE` and `QUARTZ_ORE` at lines 360 and 385. Both registrations construct `OreBlock`; the quartz variant only passes `MapColor.NETHER` to the constructor.

Neither `OreBlock` nor these registrations add block-state properties or override movement collision geometry. `Block.getStateFromMetadata` returns the default state for every metadata value (lines 182–184), so all metadata values 0–15 yield the same state for each ID. The base block state definition is empty for these blocks, and the 1.8.9 `Block` constructor sets the instance bounds to `(0,0,0)–(1,1,1)` before creating its default state (lines 198–206). `OreBlock.java` contains drop/experience behavior but no collision-shape or bounds override.

The old movement collision provider is `Block.addCollisions` followed by `Block.getCollisionShape` (`Block.java:339–349`). The default collision-shape method returns a world-positioned box formed from the block instance min/max fields. These ore instances keep their constructor's unit bounds: the base `Block.updateShape` called by `Block.rayTrace` is empty (`Block.java:596–597`), and neither `OreBlock` nor a subclass overrides it. Thus the ray path does not change either ore instance's provider bounds.

## Current registry, state, and collision shape

In 26.2, `BlockItemIds.java` assigns the exact keys `emerald_ore` and `nether_quartz_ore` (lines 110 and 117). `Blocks.java` registers those keys as `EMERALD_ORE` (lines 2648–2652) and `NETHER_QUARTZ_ORE` (lines 2916–2925), each constructed as `DropExperienceBlock`. The properties passed to both registrations use the default `hasCollision=true`; neither registration applies `noCollision`.

`DropExperienceBlock` adds experience-drop behavior and does not define block-state properties or collision geometry. Its `Block` base constructs the default state from an empty `StateDefinition.Builder` (`Block.java:236–244, 531`). The inherited `BlockBehaviour.getShape` returns canonical `Shapes.block()` (`BlockBehaviour.java:323–325`), and `getCollisionShape` returns the state shape when `hasCollision` is true (`BlockBehaviour.java:327–329`). `Properties.hasCollision` defaults to true (`BlockBehaviour.java:982–986`). Therefore each ore has a single default state with no properties, and the current collision provider returns the canonical full unit cube for every world position/context.

## Bounded alias result

For collision-provider/state identity purposes:

| Old 1.8.9 block ID/key | Old metadata | Current 26.2 registry key | Current state | Old collision bounds | Current collision shape |
|---|---|---|---|---|---|
| `129` / `emerald_ore` | `0–15` → default state | `minecraft:emerald_ore` | sole state, no properties | unit cube | canonical full unit cube |
| `153` / `quartz_ore` | `0–15` → default state | `minecraft:nether_quartz_ore` | sole state, no properties | unit cube | canonical full unit cube |

Both providers are invariant to position and collision context apart from translating the unit cube to the block position. No per-query mutable-bounds writer or reset is present for either old ore block. This is only a local provider equivalence finding; it does not say that the old and current world scans visit the same positions or that a full DDA adapter can substitute these providers unconditionally.

## Exact source references

- 1.8.9 `Block.java:1112,1168`: numeric block IDs and registry keys.
- 1.8.9 `Blocks.java:360,385`: named registry fields.
- 1.8.9 `Block.java:182–206,339–349,596–597,801–813`: metadata/default state, constructor bounds, collision provider, ray update default, empty state definition/default-state access.
- 1.8.9 `OreBlock.java:14–76`: constructors and drop/experience behavior; no geometry override.
- 26.2 `BlockItemIds.java:110,117`: current registry-key constants.
- 26.2 `Blocks.java:2648–2652,2916–2925`: current registrations and properties.
- 26.2 `DropExperienceBlock.java:14–34`: subclass behavior.
- 26.2 `Block.java:236–244,531`: empty state definition and default-state registration.
- 26.2 `BlockBehaviour.java:323–329,982–986`: default unit collision shape and collision gate.

## Limits

The evidence covers only these two legacy IDs, metadata-to-default-state behavior, and their block collision provider. It does not cover ore drops, materials, tags, pathfinding, DDA cell ordering, any other mutable `Block` subclass, or overall movement/ray-trace parity.
