# Candidate review: level-eight water beside bed

**Decision: ACCEPT for the bounded `FlowingFluid.isSolidFace` predicate delta.** This does not accept or establish full 1.11.2 fluid-current parity or runtime parity.

- Candidate: `.native-candidates/water-bed-current38`, commit `d33b4ae62fb544eca36abf50a1b6a2be8ae5ee01` (parent neutral-base commit `63750d8f682212c4d14f9bf878f1066cc29d503b`).
- Candidate implementation: `src/client/java/me/wolfii/legacyparkourcompat/change/v1_11_2/water/WaterCurrentBedFace.java` (23–47), `src/client/java/me/wolfii/legacyparkourcompat/mixin/FlowingFluidMixin.java` (16–32), `src/client/java/me/wolfii/legacyparkourcompat/mixin/EntityFluidInteractionMixin.java` (10–15), and the water hook in `src/client/java/me/wolfii/legacyparkourcompat/mechanic/hook/water/WaterCurrentBedFaceHook.java` (10–21). The candidate’s 1.10.1 and 1.12 providers return the native result.
- The changed 1.11.2 provider SHA-256 is `A10747480751DAFB855AF44EC49E46CE3264429375BE0EE0DCE2BE59D7428F47`. The independent audit SHA-256 is `E3936FE60E95FF1E171ED577FD3521CD53A5F79E27B58B3B3B87CCC1DFF64C74`.

## Reachability and source binding

The exact 26.2 unobfuscated source binds the path as follows:

1. `EntityFluidInteraction.update(Entity, boolean)V` (`EntityFluidInteraction.java:32`) scans cells and calls `FluidState.getFlow(BlockGetter, BlockPos)Vec3` (`EntityFluidInteraction.java:74`).
2. That method delegates to `Fluid.getFlow(BlockGetter, BlockPos, FluidState)Vec3` (`FluidState.java:95–97`); for water this dispatches to `FlowingFluid.getFlow(BlockGetter, BlockPos, FluidState)Vec3` (`FlowingFluid.java:55`).
3. Its `FALLING` branch (`FlowingFluid.java:89–94`) calls `isSolidFace(BlockGetter, BlockPos, Direction)Z` first at the horizontal side and then above it, retaining short-circuit order. The predicate (`FlowingFluid.java:106–112`) reads the raw neighboring block/fluid state and otherwise delegates to `BlockState.isFaceSturdy`.
4. `LiquidBlock` maps level 8 to `fluid.getFlowing(8, true)` (`LiquidBlock.java:72–77`) and its state lookup uses the capped level index (`:124–127`). Thus the candidate’s injection is reached for the audited falling/level-eight vanilla water case. It changes only the bed-face boolean; native side/above traversal and the `normalize().add(0,-6,0)` then normalize arithmetic remain in `getFlow`.

In product wiring, `WaterScan.scan` deliberately sets `replacesCurrent=false` and skips custom flow for an in-domain `LiquidBlock` with `LEVEL >= 8` (`mechanic/water/WaterScan.java:176–180`). The later redirect then permits native water `applyCurrentTo` (`mixin/EntityMixin.java:760–765`). Consequently this is not a dead predicate: candidate `@WrapMethod(update)` scopes the entity context around the actual native fallback scan, and the scan’s `FluidState.getFlow` call reaches the target predicate. The existing product `EntityFluidInteractionMixin` already wraps that exact `FluidState.getFlow` invocation and sets `localPlayerFluidCurrent` (`:41–48`); product integration should merge the candidate’s context into that existing mixin rather than overwrite it with the same-FQN class from the export.

## Scope and gates

The old 1.11.2 source checks horizontal bed faces through the material predicate: `LiquidBlock.isFaceSolid` delegates to `Block.isFaceSolid`, and `BedBlock` uses solid `Material.WOOL`. In 1.12.2, the liquid predicate uses the state’s directional face shape and `BedBlock.getFaceShape` is `UNDEFINED`. The accepted audit’s level-eight, zero-gradient witness therefore gets the downward term beside a bed in 1.11.2 and no term in 1.12.2.

The candidate’s 1.11.2 implementation is limited to a water flow during an enabled client-player update, the actual local player, non-flying abilities, and no boat mount. The `FALLING` branch is the native high-level gate; modern `LiquidBlock` explicitly maps level 8 to the falling state. It uses raw `level.getBlockState(pos)` and only the red bed, preserving other modern bed colors. The V1_10_1 native provider prevents the new predicate from being backdated into uninspected 1.10.1. The V1_12 native provider makes the audited new-side behavior explicit, though absent registration would also fall through to native behavior.

The patch does not change or claim historical scan bounds, height weighting, provider support, current aggregation, or impulse scaling. `WaterScan` and `WaterCurrent` retain their existing unsupported-provider and falling-current native fallback. This candidate only alters the face predicate on calls that pass through that native calculation; it does not by itself reproduce the old current response in contexts where those other operations differ. Runtime verification remains outstanding.

## Product integration notes

- Product already has `mixin.fluid.FlowingFluidMixin`, which wraps the `BlockState.isFaceSturdy` call for a separate falling-fluid face operation. The candidate’s return injection can coexist at the predicate boundary, but integration should keep both dispatches in the existing client mixin and preserve that hook’s native fallback.
- Product’s mixin config is `legacyparkourcompat.client.mixins.json`; the candidate export’s common config layout must not be copied literally. Register any new class only in the client list.
- `ParkourVersion.V1_10_1` represents both 1.10.1 and 1.10.2. The candidate intentionally leaves that whole enum group native, although the supplementary accepted audit found the old predicate in exact 1.10.2. This verdict accepts the assigned 1.11.2-to-1.12.2 boundary only; extending the implementation to the 1.10 group needs a deliberate decision about the uninspected 1.10.1 member rather than treating the group as a per-patch profile.

No code, test, build, game launch, or source generation was performed.
