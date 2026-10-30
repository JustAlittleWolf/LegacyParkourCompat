# Correction review: move-time historical fire sample

## Verdict

**Accept the unbuilt correction for the assigned static scope.** Removing the nonexistent `Entity.wasOnFire` accessor and capturing the historical sample in the already registered `EntityInsideBlocksHistoricalSweepMixin` addresses the prior target-field mismatch. The `move` injection descriptor matches the cached 26.2 target; the gate and state lifetime are consistent with the requested old field semantics for fixed profiles. No actionable defect was found in this correction. This review does not validate the transformed Mixin output, build, callback math, or runtime parity.

## Exact reviewed inputs

- Root worktree base commit before these uncommitted edits: `61b73a3b9021077c6be5ba224932deebf940e537`.
- Cached target: `C:\Users\Wolfi\.gradle\caches\fabric-loom\minecraftMaven\net\minecraft\minecraft-merged-deobf\26.2\minecraft-merged-deobf-26.2.jar`, SHA-256 `0468742D98FE5EC68508493EF2155C6E5209F991055BC0C17DDF26872EB0D110`.
- `EntityInsideBlocksHistoricalSweepMixin.java`: SHA-256 `C21AC40722509652A8C8AD9A75DB74347B3149B527636D4FB8F6B52314C65900`.
- New `EntityHistoricalCallbackState.java`: SHA-256 `D5B4370B7D6BF2A0F4DF8BBF43A82B3F3944706388DBC83776EE05DD5B4DDA54`.
- `EntityInsideBlocksHistoricalSweep.java`: SHA-256 `1C3F1107F08D6CBBF866BE43CBB927383413220B326C50A5472B67C73AEB32B0`.
- `legacyparkourcompat.client.mixins.json`: SHA-256 `1DC2EA660D613633F5379E0211E0457A9EB3389F627CF2A827C0D5C563303FA2`.

## Findings

- `Entity.move` exists in the cached target with descriptor `(Lnet/minecraft/world/entity/MoverType;Lnet/minecraft/world/phys/Vec3;)V`, exactly matching the new `@Inject` target and handler argument types. The handler also receives `CallbackInfo`, and the injected method is registered through the existing client mixin entry.
- `javap -c -p Entity` shows `move` starts by reading `noPhysics` at bytecode offset 1. The no-physics branch returns at offset 58. The normal branch starts at offset 59, checks `MoverType.PISTON` at 60, and calls `limitPistonMovement` at 68. Thus `@At("HEAD")` plus the explicit `!entity.noPhysics` condition samples before the piston limiter and skips the no-physics path, matching the requested historical sample point.
- The capture also requires `LocalPlayer`, client-side level, and a selected `HistoricalBlockCallbackSweepHook` whose `usesHistoricalSweep()` is true. The wrapped historical application has the same local-player/client-side gate and resolves the same hook before calling `apply`; the capture is limited to entities/operations that can consume it. The earlier-version native fence reports false, so it does not activate this sample.
- The new boolean is an instance field on the mixed-in Entity and JVM-defaults to false. It is overwritten at each eligible non-no-physics move HEAD. Calls excluded by the gate leave the previous value intact; the historical accessor is only consumed on the fixed selected historical sweep path. That persistence matches the intended sampled-field behavior across no-physics/limited or absent subsequent movement calls; profile switching remains outside defined behavior.
- `Entity.isOnFire():boolean` exists in the exact target. The historical implementation now reads the mixin-owned interface value instead of looking up an absent vanilla field. The old `@Accessor("wasOnFire")` file is deleted, its JSON registration is removed, and the remaining client JSON retains `EntityInsideBlocksHistoricalSweepMixin`.

## Limits

Only `javap` inspection of the cached target JAR and static inspection of the uncommitted source/JSON were used. No build, tests, game launch, bootstrap, decompile, or download was run.
