# 26.2 callback built-descriptor audit

## Verdict

**One blocking static wiring defect.** `EntityCallbackFinalizerAccessor` is registered in the built client mixin JSON and requests an `Entity.wasOnFire` field. The exact cached 26.2 `Entity` class has no such field. `wasOnFire` appears only as a local-variable-table name in `Entity.applyEffectsFromBlocks(List)`. This accessor cannot resolve to the target field and must be corrected or removed before this callback mixin set is structurally valid. This is a static descriptor finding; no runtime launch was performed.

No other callback target descriptor mismatch was found in the reviewed additions. This verdict covers built callback wiring only; it does not establish callback behavioral parity.

## Immutable inputs

- Candidate/product artifact: clean `.native-integrations/callback-ready32`, commit `e199656178778526d656e866bb5fd7c823bf83c2`.
- Target: `C:\Users\Wolfi\.gradle\caches\fabric-loom\minecraftMaven\net\minecraft\minecraft-merged-deobf\26.2\minecraft-merged-deobf-26.2.jar`; SHA-256 `0468742D98FE5EC68508493EF2155C6E5209F991055BC0C17DDF26872EB0D110`.
- Built client mixin configuration: `build/resources/client/legacyparkourcompat.client.mixins.json`; SHA-256 `AFBFC37D504775113E905BD837D8367E6114435DF6FBE3B4CFB179119C2765B6`.
- Inspected compiled classes: `build/classes/java/client` from that clean candidate, including `EntityInsideBlocksHistoricalSweepMixin` SHA-256 `8ACB9CA4C7AE4AD278881A9693BE5B2E2E31ACF82E8056B3FDA1D31FD69F32A4`, `EntityCallbackTraversalMixin` `350F62B5579D35C0774A62B706B70DAA08F8CCF57F59462B4BF995DB42D05694`, `EntityCallbackBatchMixin` `49542531AC23C0A698C4E13A2678FDF6ADDF8C1724693541F615FE13D7E89FFE`, `EntityBlockCallbackSegmentMixin` `E0013DE35D0A28D56424A7DCA6142508324371F75C5D5E04E646AB7718E94C9B`, and `EntityMovementAccessor` `7028006A4CC610E8890A13775510C5A9BEACDC3174E1E54AE815A11A1EB1FC58`.
- The registered finalizer accessor class is `EntityCallbackFinalizerAccessor.class`, SHA-256 `22CE1B6C7D04B423499541AC9EA9DAF9633EC2C2982CD5499A1A7A1059BA1965`.

## Static checks

- The built client JSON registers the callback sweep, query-box, batch, traversal and segment mixins; `EntityMovementAccessor`, `EntityInvoker`, `EntityVisitedBlocksAccessor`, `EntityCallbackFinalizerAccessor`, and `AbstractCauldronBlockAccessor` are also listed.
- Exact target method signatures in 26.2 `Entity` match the registered list wrapper `applyEffectsFromBlocks(Ljava/util/List;)V`, list-level `checkInsideBlocks(Ljava/util/List;Lnet/minecraft/world/entity/InsideBlockEffectApplier$StepBasedCollector;)V`, and segment-level `checkInsideBlocks(Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/entity/InsideBlockEffectApplier$StepBasedCollector;Lit/unimi/dsi/fastutil/longs/LongSet;I)I`. The segment callback mixin's `makeBoundingBox(Vec3)` and `onInsideBlock(BlockState)` shadows also match.
- The `Entity$Movement` record in the target has private final fields `from: Vec3`, `to: Vec3`, and `axisDependentOriginalMovement: Optional`. `EntityMovementAccessor` field names and erased method descriptors match all three. The traversal redirect targets the record accessor with `()Ljava/util/Optional;`; `javap -c -p Entity` shows two calls in the list method, with ordinal 1 being the second call.
- `EntityInsideBlocksHistoricalSweepMixin` compiles with wrapper descriptor `(Ljava/util/List;Lcom/llamalad7/mixinextras/injector/wrapoperation/Operation;)V` and target annotation `applyEffectsFromBlocks(Ljava/util/List;)V`. `List<?>` erases to `List`; its classfile does not encode a reference to `Entity$Movement`. The pre-existing `EntityMovementAccessor` alone names the private nested target via a Mixin target string.
- `EntityInvoker` names and descriptors checked against the target: `makeBoundingBox(Vec3):AABB`, `onInsideBlock(BlockState):void`, `fillCrashReportCategory(CrashReportCategory):void`, `isInRain():boolean`, `getFireImmuneTicks():int`, `playEntityOnFireExtinguishedSound():void`, plus `getBlockPosBelowThatAffectsMyMovement():BlockPos`, `getBlockSpeedFactor():float`, `setSharedFlag(int,boolean):void`, `restituteMovementAfterCollisions(BlockState,boolean,boolean,Vec3):void`, and segment `checkInsideBlocks(...):int`. These target methods exist with those descriptors (including private/protected methods exposed through invokers).
- `AbstractCauldronBlockAccessor` targets protected `getContentHeight(BlockState):double` on `AbstractCauldronBlock`. The target `LavaCauldronBlock` declares an override with the identical descriptor; the candidate passes a `LavaCauldronBlock` object to the accessor. This static shape is consistent with virtual override dispatch.
- Auto-discovery has one `@MechanicType("entity.historical_block_callback_sweep")` hook and exactly two implementing changes in the versioned change sources: `HistoricalBlockCallbackSweepNativeFence` at `V1_21`, and `EntityInsideBlocksHistoricalSweep` at `V1_21_2`. Both are public concrete classes with public no-argument constructors in the compiled bytecode and carry their expected `@MovementChange` values. The change discovery code collects the annotated hook interface from implementations and registers by hook plus version, so these two entries do not duplicate an operation/version key; the earlier version is the native lower fence.

## Reproduction method

Read-only `javap -p -s` and `javap -p -v`/`-c` inspections of the cached target JAR and built classfiles, plus parsing the built mixin JSON and source discovery declarations. No tests, build, game launch, or decompile was run.
