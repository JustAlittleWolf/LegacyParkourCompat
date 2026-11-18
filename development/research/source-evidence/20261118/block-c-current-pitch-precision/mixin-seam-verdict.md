# C pitch-width Mixin seam verdict

Verdict: **supported; keep the existing first-setter `@ModifyArg` seam.** MixinExtras `@Local` can supply the second original `double` parameter to a `@ModifyArg` handler. It is not necessary to recalculate the existing pitch argument: 26.2 has already computed it at the injection point. If a hook separately needs the original pitch input or current pitch, that value can be captured/read before the first setter executes without moving the operation.

## Bounded identities

- Product snapshot inspected read-only: `elytra-product`, accepted code commit `72adb0edccf5dc8f850ecfb727c0fe10afeb15ce`.
- Exact cached 26.2 client artifact used for bytecode: `.gradle/caches/fabric-loom/26.2/minecraft-client.jar`, SHA-256 `40896EE9F1E2BEC3C934DAAC7E93D41E9E3D9C2F8AE0CA366D52FFBFD1AFA290`.
- Product configuration pins `minecraft_version=26.2`, `loader_version=0.19.3`. The cached Fabric Loader 0.19.3 JAR (SHA-256 `73EED8C34BBAD0320A2A3CBA5346351E822F74F82B3F3C060574068474132958`) embeds `META-INF/jars/mixinextras-fabric-0.5.4.jar` (embedded JAR SHA-256 `BD79DDB2F54B39A08DEEB50CB12439BBA01481264DD5A9F5EA830551BFEF31FF`). The product mixin config requires MixinExtras >= 0.5.4.
- The product already uses `@ModifyArg` and MixinExtras `@Local` together in `FluidCurrentDepthMixin` (`@ModifyArg` handler parameters include `@Local(argsOnly=true)`, `@Local(ordinal=1)`, and `@Local(ordinal=3)`). This confirms the exact handler shape is established in this product/dependency setup.

## Exact target and argument identity

On the cached client JAR, `javap -p -c -s net.minecraft.world.entity.Entity` reports:

- Owner: `net/minecraft/world/entity/Entity`
- Method: `turn(double, double)`, descriptor `(DD)V`
- First parameter (yaw input) starts in local slot 1; second parameter (pitch input, source name `yo`) starts in local slot 3 because `this` occupies slot 0 and a `double` consumes two slots.
- The bytecode narrows slot 3 to float, multiplies by `0.15F`, then stores the pitch delta before invoking the first `Entity.setXRot(F)V`. The first invocation is bytecode offset 24; the second is offset 52. Thus the current product target `method = "turn(DD)V"`, owner `Lnet/minecraft/world/entity/Entity;setXRot(F)V`, `ordinal = 0`, `@ModifyArg(index = 0)` identifies the first pitch setter and its sole float argument.

For a handler on that injection, `@Local(argsOnly = true, ordinal = 1) double pitchInput` selects the second `double` method argument (`yo`). The equivalent explicit local-slot form is `@Local(argsOnly = true, index = 3) double pitchInput`. These are different index spaces: `@Local(index=3)` is a target-method JVM local slot; `@ModifyArg(index=0)` is argument zero of the invoked `setXRot(float)`.

## Arithmetic and ordering

The 26.2 source is `decompiled_minecraft/26.2/unobfuscated/net/minecraft/world/entity/Entity.java:489` (`turn`) and line 3910 (`setXRot`). The bytecode confirms the source order: compute pitch delta, read current pitch, add the float delta, then call the first setter. There is no intervening write or movement/physics consumer before that call. The first setter validates finiteness, applies remainder by `360.0F`, then clamps to `[-90.0F, 90.0F]`; the later turn clamp and prior-pitch update remain after the first setter.

At `@ModifyArg`, the original first-setter value already contains the vanilla current-pitch addition, with the original cast and float operation order intact. Keep passing that value through the existing hook when it is sufficient. If the operation needs the original `yo`, append `@Local(argsOnly = true, ordinal = 1) double yo` to the same handler. If it needs the current pitch, a `getXRot()` read in this handler happens before the first setter invocation and therefore reads the pre-setter pitch; there is no product movement consumer between that read and the first setter. This preserves the existing turn route and leaves native yaw, later pitch clamp, history update, and vehicle callback ordering in place.

The smallest feasible seam is therefore the existing thin `EntityMixin` `@ModifyArg` on the first `setXRot(F)V`, with an optional MixinExtras `@Local` parameter. No redirect, extra accessor, broader hook, or alternative injector is needed. This verdict establishes injector feasibility and bytecode order only; it does not claim runtime parity.

