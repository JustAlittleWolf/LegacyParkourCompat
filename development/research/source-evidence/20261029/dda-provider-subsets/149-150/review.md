# Independent review: 1.8.9 comparator provider closure

## Verdict

**ACCEPT for the bounded key/state and movement collision-provider claims.** The report correctly identifies old IDs 149/150 as unpowered/powered comparator registrations, not repeaters; it supports their current comparator alias and equal 1/8-height collision geometry. No redstone, DDA traversal, camera reachability, or runtime-parity conclusion is accepted.

## Evidence identity

Reviewed report: `.native-source/20261028/collision-next/dda-redstone-devices-149-150/report.md`, SHA-256 `CB488EBEC3FCCFA6426E627CF98AD2641B2CD05085183776189993687CC939DD`.

Its 19-entry `sources.sha256` has SHA-256 `70524B09B56A6E5595C0713EF4B3339631DDA8C800DD58519E235CBC6CB18CE1`. I recomputed all 19 entries against the canonical read-only roots; all matched. Each of the 13 selected Java source hashes also matches the corresponding entry in the 1.8.9/26.2 canonical source manifest. Ready markers identify 1.8.9 / `ornithe-feather` and 26.2 / `unobfuscated`, and their declared source-manifest digests match the listed manifests.

The exact source-file hashes used in this review are those in the bound report manifest. Primary paths and inspected members are:

- 1.8.9 `net/minecraft/block/Block.java`, registry entries 149/150 and 93/94; bounds, collision shape and `rayTrace`/default `updateShape`.
- 1.8.9 `ComparatorBlock.java`, `DiodeBlock.java`, and `Direction.java`, constructors, default state, metadata conversion and shape initialization.
- 26.2 `V704.java`, old registry-key rename; `BlockStateData.java`, comparator state records; `Blocks.java`, `ComparatorBlock.java`, `DiodeBlock.java`, `Block.java`, and `BlockBehaviour.java`, current key, schema, shape, and collision gate.

## Claim checks

- **Registry distinction:** exact 1.8.9 `Block.java` registers IDs 93/94 as repeaters and IDs 149/150 as `unpowered_comparator`/`powered_comparator`, each constructed with `ComparatorBlock(false/true)`. The task-label correction in the report is right.
- **Metadata/state:** `ComparatorBlock.getStateFromMetadata` selects horizontal direction through `Direction.byIdHorizontal(metadata)`, `POWERED` from bit 3, and compare/subtract mode from bit 2. `BY_ID_HORIZONTAL` is populated by each direction's `idHorizontal`; the 1.8.9 enum assigns South=0, West=1, North=2, East=3. Thus all metadata 0–15 decode to valid four-way states, with no invalid high-bit case. The encoder uses the same fields. Both registrations have the same north/false/compare default block state; the extra powered constructor field is not used by the collision bounds.
- **Current alias/state:** 26.2 `V704.java` maps both old block names to `minecraft:comparator`. The legacy-state records cover both old names for the 16 facing/mode/powered tuples, and the current comparator schema exposes `FACING`, `MODE`, and `POWERED`. The alias is valid for this bounded collision-state mapping; it does not merge redstone behavior claims.
- **Collision geometry/lifecycle:** old `DiodeBlock` writes `[0,0,0]–[1,1/8,1]` in its constructor. Neither old comparator nor diode adds a provider or shape-update override; `Block.getCollisionShape` translates the instance bounds, while base `rayTrace` calls an empty `updateShape`. The 26.2 diode's static `Block.column(16,0,2)` becomes the same unit-footprint, 2/16-high shape. The base current collision provider uses the state's shape when `hasCollision` is true; `Properties.hasCollision` defaults true and `Blocks.COMPARATOR` does not opt out. The collision provider is state/context independent on both sides as claimed.

## Citation correction

The report cites `BlockStateData.java:2384–2415`. Those numbers are the table's `register` state IDs, not physical Java line numbers. The corresponding current source calls are physically at lines 6731–6886 and register IDs 2384–2415 for the two legacy comparator keys and the current comparator. This is a citation-format correction only; the source hash and the state-record claim are correct. Future references should say `BlockStateData.java register IDs 2384–2415` or give the physical source-line range.

## Scope

The accepted result closes the comparator block-key/state alias and movement collision-provider geometry only. It does not close any world-ray visitation, observer reachability, mutable-state DDA adapter, comparator timing, or redstone behavior dependency. No product code, build, test, or runtime was inspected or run.
