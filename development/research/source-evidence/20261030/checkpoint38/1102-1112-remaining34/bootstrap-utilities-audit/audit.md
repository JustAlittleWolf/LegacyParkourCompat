# Independent audit: bootstrap and utility route closure

## Verdict

**Revise the report's route descriptions; its no-new-movement-finding conclusion is supported for the inspected routes.** No changed operation in the five assigned files has been shown to change local-player movement math, pose, collision response, movement attributes, or a direct player response. Two relevant paths need to be recorded more precisely before treating this slice's closure as independently complete: `DefaultedList` reaches equipment modifier reads, and a Bootstrap fallback can equip a nearby player. A `BlockPos.Mutable` identity detail also narrows one report claim but is not a changed coordinate result on a movement route.

## Evidence identity

I recomputed SHA-256 for all five assigned source files under the two canonical read-only Feather roots named in the report. Every digest matches the report's per-file digest table (including `DefaultedList.java`, which is absent from 1.10.2). I used exact source files only for the comparison and caller tracing; no product implementation/history, wiki, build, test, or game evidence was used.

## Route findings

### `Bootstrap.java`

The report is right that the registered dispenser changes do not change player tick or collision code. There is, however, a reachable player-equipment route omitted by its characterization of the skull/pumpkin calls as “only failure/fallback handling.” In both versions, a failed golem-placement branch calls `ArmorItem.dispense` with the skull or pumpkin stack. That helper searches a one-block box for eligible `LivingEntity` instances. The filter maps skull/pumpkin to `EquipmentSlot.HEAD`; a nearby player with an empty head slot is eligible, and the helper calls `setEquipment` with a one-item copy. Thus the helper can directly equip a player, not merely report whether the dispenser should fall back.

The actual Bootstrap delta on that route is the empty-result/sound bookkeeping. In 1.10.2 the helper returns `null` when no entity is eligible, otherwise copies one item to the target, decrements the source and returns it; the caller tests `== null`. In 1.11.2 it returns `ItemStack.EMPTY` on failure, otherwise `split(1)` copies the same one-item stack to the target and decrements the source; the Bootstrap caller tests `.isEmpty()`. For a one-item successful equip, the returned source stack is now empty, so `OptionalDispenseBehavior` can select the failure sound even though the player was equipped. This is an audible event-state difference. The installed stack and its NBT are preserved by `copy`/size-one versus `split(1)`, so this caller delta does not establish a movement or attribute difference. Record the equip route and the sound-only outcome instead of calling the helper only failure handling.

The other listed Bootstrap changes remain outside player movement on their reachable routes: entity spawn coordinates/yaw and item consumption affect dispenser output or non-player entities; Shulker placement and block initialization produce state/registrations; optional behavior bookkeeping chooses dispenser events. Vehicle yaw is excluded.

### `DefaultedList.java`

The player-list path is movement-relevant and needs explicit closure. In 1.11.2 `PlayerInventory` stores hotbar, armor, and offhand slots as fixed-size `DefaultedList<ItemStack>` instances initialized with `ItemStack.EMPTY`. `PlayerEntity.getEquipment` reads those lists, and `LivingEntity.updateEquipment` consumes the resulting equipment and adds/removes each stack's attribute modifiers. This is the path by which an equipped stack can affect player movement attributes. `PlayerInventory.readNbt` and `clear` clear fixed-size compartments; `DefaultedList.clear` restores each slot to its default without changing size. The old player arrays are also fixed-size, and their empty `null` entries are skipped by the old null guards. The new `ItemStack.EMPTY` slots are skipped by `isEmpty` guards. For empty slots and ordinary non-empty stacks, the inspected getters/setters retain slot positions and the equipment modifier route sees the same empty/non-empty equipment and same stack data.

Also note that `LivingEntity` changes its equipment-cache arrays to `DefaultedList` in 1.11.2. `PlayerEntity` overrides equipment access with `PlayerInventory`, while the inherited lists are used as cached prior equipment in `LivingEntity.updateEquipment`; they are not a second authoritative player inventory. The `null`-to-`ItemStack.EMPTY` transition is guarded correspondingly before modifier removal/addition. This caller needs to appear in the route trace, even though it does not establish another movement delta. The report's malformed zero-count-stack lead remains distinct from behavior demonstrated by `DefaultedList` alone.

### `BlockPos.java`

The report's coordinate-order conclusion for `iterateRegion` and `iterateRegionMutable` is supported for the paired `BlockPos` endpoint overloads: both normalize endpoints, and both enumerate x, then y, then z; the mutable form still reuses one mutable position. The newly added integer-bound overloads have no movement/collision callers in the inspected 1.11.2 tree. The player's world collision collector instead performs its own shape-bound block scan; it does not call these region iterators. The new `rotate(BlockRotation)` is used by structure placement/generation, not player movement.

Qualify the `add(Vec3i)` zero-vector identity statement: it returns `this` on an immutable `BlockPos` in both versions, but in 1.11.2 a `Mutable` receiver dispatches to the new `Mutable.add(int,int,int)` override and returns an immutable copy even for zero. The coordinate values remain the same. Inspected `add(Vec3i)`/`subtract(Vec3i)` users are position/structure/village utilities, not the local-player tick or collision collector, so this identity change does not open a movement route.

### `Direction.java`

`byKey` and `Axis.byName` only change locale-independent lowercasing; the inspected callers are state/model parsing. `nearest(BlockPos, LivingEntity)` is new and its callers are dispenser, command block, observer, and piston placement/state-update methods. It reads the placer position/eye height or horizontal facing to produce a block state. That may affect a later world state, but it does not alter the direct player response for an already-present state; no movement-algorithm delta is established in this utility slice. Keep the producer route distinct from the separately inspected direct-response route.

### `MathHelper.java`

`positiveModulo(double,double)` is the same expression as the float overload with double operands. The new callers in `CompassItem` and `ClockItem` update item rotation animation. The `toRgb` edit is local-variable renaming. No movement math callsite is changed.

## Closure boundary

The supported conclusion is **no additional movement finding from these five assigned files**, not full-pair closure or runtime parity. Before relying on the report as a complete independent route-closure record, revise its Bootstrap section to include the possible player head-equipment insertion and sound-only difference, and its `DefaultedList`/`BlockPos` sections to include the modifier/cache path and mutable identity qualification. The exact source identities and the five hash checks are closed; this audit does not independently close unrelated pair inventory or other owned routes.
