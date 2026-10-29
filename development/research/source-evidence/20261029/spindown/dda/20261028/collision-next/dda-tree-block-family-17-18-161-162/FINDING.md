# DDA provider closure: 1.8.9 tree blocks (IDs 17/18/161/162)

Status: bounded source closure for historical provider shape, metadata decoding, and current block aliases. This is not DDA visitation or runtime parity evidence.

## Scope and source readiness

Read-only primary sources are the prepared 1.8.9 Ornithe/Feather and 26.2 unobfuscated decompilations. Their adjacent `.ready.json` and `.sources.sha256` artifacts were present. The exact Java source files consulted, their SHA-256 digests, and their matching source-manifest records are in `SOURCE-HASHES.txt`.

Old registration in 1.8.9 `Block.java` is exact: numeric ID 17 uses key `log`; ID 18 uses `leaves`; ID 161 (`leaves2`) is internally keyed `leaves`; ID 162 (`log2`) is internally keyed `log`. Do not infer key from numeric ID. The state decoder selects the distinct classes.

## Old metadata decoders and canonical aliases

All 16 metadata values are valid for each listed legacy block. The low two bits decode species; high bits decode the independent log axis or leaf decay flags.

| Old ID/key | metadata bits | 1.8.9 decoded state | Current 26.2 registry alias |
|---|---|---|---|
| 17 `log` | low two bits 0/1/2/3 | oak/spruce/birch/jungle; bits 2–3: Y/X/Z/NONE | `{species}_log` for Y/X/Z; `{species}_wood` for NONE |
| 162 `log2` | low two bits 0/1 | acacia/dark_oak; bits 2–3: Y/X/Z/NONE | `{species}_log` for Y/X/Z; `{species}_wood` for NONE |
| 18 `leaves` | low two bits 0/1/2/3 | oak/spruce/birch/jungle; bit 2 clear=set decayable true, bit 3 set=set check_decay true | `{species}_leaves`; historical `decayable`/`check_decay` are not present in current state |
| 161 `leaves2` | low two bits 0/1 | acacia/dark_oak; bit 2 clear=set decayable true, bit 3 set=set check_decay true | `{species}_leaves`; historical `decayable`/`check_decay` are not present in current state |

The old log classes use `metadata & 3` (plus 4 for `log2`) and `metadata & 12`. Axis decode is 0→Y, 4→X, 8→Z, 12→NONE. The modern `BlockStateData` table maps NONE-axis log states to intermediate `*_bark` names; the 26.2 `EntityTheRenameningFix` explicitly renames each `*_bark` to `*_wood`. Thus NONE is wood, not a current `*_log` with a guessed Y axis. Current `RotatedPillarBlock` only has X/Y/Z, while current `*_wood` also represents the all-sides-bark historical shape. Each alias family is registered as `RotatedPillarBlock` and uses `logProperties`.

For leaves, 1.8.9 `LeavesBlock` and `Leaves2Block` mask bits exactly as shown; no metadata value is rejected. `BlockStateData` gives explicit current key/species and all four combinations of the two old leaf flags for the six species. Current registry leaves use `TintedParticleLeavesBlock`/`LeavesBlock`, with default state `distance=7,persistent=false,waterlogged=false`. Those are modern lifecycle state properties; the old decay bits do not map to them and must not be treated as shape aliases.

## Historical and current collision/ray provider

In 1.8.9 `Block` initializes its mutable bounds to `[0,1]^3`. Its default `getCollisionShape` returns the world-offset bounds box. Its `rayTrace` first calls `updateShape(world,pos)` and then intersects those same six bounds planes. The tree-specific chain (`LogBlock`/`Log2Block` → `AbstractLogBlock` → `AxisBlock` → `Block`; `LeavesBlock`/`Leaves2Block` → `AbstractLeavesBlock` → `TranslucentBlock` → `Block`) does not override `getCollisionShape`, `updateShape`, `rayTrace`, or call `setShape`. The only matching `setShape` call in this reviewed class closure is the base `Block` constructor's full-cube initialization. Leaves decay ticks and log removal behavior modify block states/world contents, not provider bounds.

Therefore, for the ordinary/default block ray provider, all 1.8.9 logs (including axis NONE) and leaves expose a unit-cube collision box and the default six-plane ray intersection. The legacy leaf `DECAYABLE` and `CHECK_DECAY` flags do not gate collision or ray shape.

In 26.2, `RotatedPillarBlock` inherits the default block shape and collision implementation. `LeavesBlock` inherits the default block shape and collision implementation; it overrides support shape to empty, which is not collision shape. `leavesProperties` calls `.noOcclusion()` but not `.noCollision()`; `BlockBehaviour.Properties.hasCollision` defaults true; generic collision calls the block state shape, which defaults to `Shapes.block()`. `TintedParticleLeavesBlock` adds particle behavior only. Modern `LeavesBlock.updateShape` schedules water and distance ticks, but neither it nor the historical flags changes the collision shape. The leaves shape is not a dynamic-shape state in this source; changing `distance`, `persistent`, or `waterlogged` does not select another collision geometry.

## Applicability boundaries

This closes the source-defined default provider and exact aliases for these six log and six leaves species. A consumer still needs to map the current IDs to historical species families. For logs it must distinguish `{species}_log` (axis X/Y/Z) from `{species}_wood` (historical axis NONE); all six historical species have both mapped current key families. For leaves, accept only the six exact historical species aliases; unrelated modern leaf blocks are outside this closure.

This finding does not establish old-vs-modern DDA cell visitation, ordered provider callbacks, mutable bounds written by other block classes, caller/hit semantics, loaded-chunk behavior, or complete 1.8.x profile applicability. It also does not justify translating current leaves lifecycle properties into legacy decay flags.
