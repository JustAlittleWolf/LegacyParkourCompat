# Incoming 26.2 mixin-signature review

## Scope and verdict

Reviewed only the two-line diff in `b30ca6a6d7cec35451ce083db23dd69e9d41bda0` over `5cabebdd1cdb3afede09bef8a676afbbaf8317b9`, on `fix/resume-movement-20261030`. Verdict: **both signature corrections are supported by exact 26.2 source and the cached remapped 26.2 class files; no semantic conflict found.** This is a static signature/target review, not a build or parity claim.

## Evidence

Exact 26.2 unobfuscated source is at `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/26.2/unobfuscated/`:

- `net/minecraft/world/entity/Entity.java:3683–3684`: `getFluidHeight(final TagKey<Fluid> type)` returns `double` and delegates with `type`. SHA-256: `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5`.
- `net/minecraft/world/entity/player/Player.java:304–306`: `updateIsUnderwater()` assigns `this.wasUnderwater = this.isEyeInFluid(FluidTags.WATER)` and returns the field. SHA-256: `8decc71b9c780664578ddb14591db2a2f207c72c05b676edded6f8e964576531`.

The same class files were inspected with `javap -p -s -c -l -v` from cached `minecraft-merged-deobf-26.2.jar` (POM version 26.2; JAR SHA-256 `0468742d98fe5ec68508493ef2155c6e5209f991055bc0c17ddf26872eb0d110`):

- `Entity.getFluidHeight(TagKey<Fluid>)` has descriptor `(Lnet/minecraft/tags/TagKey;)D`; its local-variable table names slot 1 `type` with descriptor `Lnet/minecraft/tags/TagKey;`. The exact class-entry hash is `2dbeaf9e8512d715a1887c4fc3892fea94029db09116274e0fe1b091870ab8e3`.
- `Player.updateIsUnderwater()` has descriptor `()Z`; its bytecode loads `this`, `FluidTags.WATER`, invokes `Player.isEyeInFluid(TagKey)Z`, and stores the result to `Player.wasUnderwater`. The constant-pool `Methodref` owner is explicitly `net/minecraft/world/entity/player/Player`, even though the invoked method is inherited from `Entity`. The local-variable table identifies `this` as `Player`. The exact class-entry hash is `d006df5ddc43dc2fc5a5088c66f6d9df6166ec66e4d2aeea44a8e244d294f05d`.

The cached MixinExtras 0.5.5 injector was also inspected statically (`mixinextras-fabric-0.5.5.jar`, SHA-256 `5da883dc4bfb16e4ceca3f16d8c4ad937bdfb1ce337ed5ba15472d4d81dd6242`; injector class SHA-256 `0b378968c7adbd92f1077059c0e9bf75ec7aac5bfc189b58b0ab2a140825edf2`). Its bytecode validates the target return type as the handler's first value and appends captured target arguments afterward. The cached 0.5.4 source JAR documents the same contract explicitly: original return value, optionally followed by enclosing method parameters.

## Assessment of the two changes

1. **`EntityMixin` `@ModifyReturnValue`:** `getFluidHeight` returns `double` and accepts `TagKey<Fluid>`, so the handler's required order is `(double original, TagKey<Fluid> type)`. The corrected declaration matches both the MixinExtras contract and exact target descriptor. It preserves the native return unless the existing lava/context gate applies. The prior `(TagKey, double)` order is incompatible with the injector's handler contract and prevents a valid application; this would disable the existing water-first lava-depth projection, whose intended behavior remains narrowly guarded to lava and the player/base-tick probe context.

2. **`PlayerMixin` `@Redirect`:** the exact source call occurs inside `Player.updateIsUnderwater`; the compiled invokevirtual's symbolic owner is `Player`, not `Entity`. The corrected selector therefore matches the actual invocation descriptor `Lnet/minecraft/world/entity/player/Player;isEyeInFluid(Lnet/minecraft/tags/TagKey;)Z`. The handler's leading `Player` is the invocation receiver and the `TagKey<Fluid>` is its sole argument. The selector is scoped to `updateIsUnderwater`; the existing hook lookup and native fallback remain intact. The previous `Entity` owner does not identify this recorded Player-owned invocation and would leave the historical cached-eye query redirect unapplied (or fail a required redirect match).

These are call-site/handler signature repairs. They do not alter historical computations, add new gates, broaden the target methods, or change behavior for nonmatching profiles when dispatch falls back to vanilla.

## Review identity and limits

- Product revision reviewed: `b30ca6a6d7cec35451ce083db23dd69e9d41bda0`; base: `5cabebdd1cdb3afede09bef8a676afbbaf8317b9`.
- Source evidence cited above is exact 26.2 unobfuscated source; local cached classes are the remapped/deobfuscated 26.2 artifact used for descriptor, constant-pool owner, bytecode and LVT corroboration.
- No product files were changed. No build, test, game launch, or runtime validation was performed.
