# Post-build callback correction artifact review

## Verdict

The corrected clean integration artifact at `57afe412ea53d23c79349b360150d6f5054b3020` contains no registration, stale class, or bytecode reference to the invalid `EntityCallbackFinalizerAccessor`. Its built sweep mixin owns the sample state under `@Unique`, implements the state interface, and contains the exact move injection plus the previously reviewed erased-`List` wrapper. The existing `EntityMovementAccessor` remains separate and unchanged. No actionable static artifact issue found.

This is an inspection of an already-built artifact only. No build, tests, or runtime launch was run for this review.

## Artifact identities

- Clean ready worktree commit: `57afe412ea53d23c79349b360150d6f5054b3020`; worktree status clean.
- Cached 26.2 target JAR: SHA-256 `0468742D98FE5EC68508493EF2155C6E5209F991055BC0C17DDF26872EB0D110`.
- Built `build/resources/client/legacyparkourcompat.client.mixins.json`: SHA-256 `1DC2EA660D613633F5379E0211E0457A9EB3389F627CF2A827C0D5C563303FA2`.
- Built `EntityInsideBlocksHistoricalSweepMixin.class`: SHA-256 `D1CCCF8229B7CEC72C2212F55C5A1D07DBEF0703C2EA2565ACE9A7ADC7C2EA3C`.
- Built `EntityHistoricalCallbackState.class`: SHA-256 `06F4F649BDD6D00B39A52C9F881C6A15EE9BAAF74BF0BE2441ABC1F6FEDD937D`.
- Built `EntityInsideBlocksHistoricalSweep.class`: SHA-256 `29F57B93C24C8450D14B3A547CE870F63247903BA0F45EE092FFFF7EF53A57C2`.
- Existing `EntityMovementAccessor.class`: SHA-256 `7028006A4CC610E8890A13775510C5A9BEACDC3174E1E54AE815A11A1EB1FC58`.

## Checks

- The built client JSON still registers `EntityInsideBlocksHistoricalSweepMixin`; it no longer registers `accessor.EntityCallbackFinalizerAccessor`.
- The old accessor `.class` is absent from the built client classes, and a binary search of built client classfiles found no `EntityCallbackFinalizerAccessor` reference.
- The built mixin class implements `EntityHistoricalCallbackState`; it owns private boolean `legacyparkourcompat$wasOnFireBeforeMove` with the compiled `@Unique` annotation and exposes `legacyparkourcompat$wasOnFireBeforeMove():boolean`. Its capture handler reads the unique field after checking `LocalPlayer`, client level, `!noPhysics`, and selected historical sweep.
- Compiled annotation metadata records `@Inject(method="move(Lnet/minecraft/world/entity/MoverType;Lnet/minecraft/world/phys/Vec3;)V", at=@At("HEAD"))`; the handler descriptor is `(MoverType, Vec3, CallbackInfo):void`, matching cached target `Entity.move`.
- The compiled `@WrapMethod` remains `applyEffectsFromBlocks(Ljava/util/List;)V`; its wrapper descriptor erases the parameter to `Ljava/util/List;`. No `Entity$Movement` class reference appears in the wrapper mixin classfile. The retained `EntityMovementAccessor` still targets the nested record through its Mixin target string and exposes the matching fields separately.
- The updated sweep implementation invokes `EntityHistoricalCallbackState.legacyparkourcompat$wasOnFireBeforeMove()`; its classfile has no reference to the deleted finalizer accessor.
