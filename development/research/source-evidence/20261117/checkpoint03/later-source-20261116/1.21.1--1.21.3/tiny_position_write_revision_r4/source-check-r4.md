# Source arithmetic and support-probe precondition (r4)

## Input recurrence

The route is `LocalPlayer` ordinary travel → `LivingEntity.handleRelativeFrictionAndCalculateMovement` → `handleOnClimbable` → `Entity.move(SELF, request)`; after that move, `travelInAir` stores horizontal components multiplied by `0.91F`. For one-axis input at yaw zero, the keyboard vector is not normalized. Swift Sneak III gives the cast float sneaking-speed value `0.75F`; the local input factor is `0.98F`; off-ground nonsprinting movement speed is `0.02F`.

Using float operations at the source points and promotion to double where `Vec3` does so:

- `inputScale = (float)(0.75F * 0.98F) = 0.7350000143051147F`.
- `a = (double)0.02F * (double)inputScale = 0.014699999957531684`.
- friction promoted from float is `(double)0.91F = 0.9100000262260437`.
- At tick start, source sets a stored component to zero if `abs(component) < 0.003`.
- For each sign, the current `SELF` request is `storedZ + sign*a`; after the movement, next tick's stored component is `requestZ * friction`.

| Tick | Input sign | SELF request Z | Stored after friction |
|---:|:---:|---:|---:|
|1|-|-0.014699999957531684|-0.013377000346876673|
|2|-|-0.028077000304408357|-0.025550071013360240|
|3|+|-0.010850071055828556|-0.009873564945358425|
|4|+|0.004826435012173259|0.004392055987655962|
|5|-|-0.010307943969875721|-0.009380229282923496|
|6|-|-0.024080229240455178|-0.021913009240343357|
|7|+|-0.007213009282811673|-0.0065638386365273185|
|8|-|-0.021263838594059004|-0.019350093678260055|
|9|-|-0.034050093635791740|-0.030985586101569730|
|10|+|-0.016285586144038047|-0.014819883818181116|
|11|+|-0.000119883860649431|-0.000109094316335062|

Before each tick's input, every nonzero prior stored component has absolute value >0.003; tick 11's request itself is computed from prior `-0.014819883818181116`, so it survives the clamp. The sum of the 11 SELF requests is `-0.16212122103327614` (minor last-digit rounding depends on the display path); starting center Z `0.5` ends near `0.33787877897` in the same trapdoor cell at integer Z 0. Every intermediate center remains within Z block 0.

## Tag-free trapdoor witness and support-query precondition

The player stands with center `(0.5,1.0,0.5)` and `blockPosition=(0,1,0)`. The state at `(0,1,0)` is an open `TrapDoorBlock` facing north; `(0,0,0)` is a north-facing `Blocks.LADDER`. `LivingEntity.onClimbable()` reads `getInBlockState()` at `blockPosition` and calls `trapdoorUsableAsLadder` for trapdoors. That helper returns true exactly when OPEN is true, the block at `$0.below()` is `Blocks.LADDER`, and its FACING equals the trapdoor FACING. This is tag-independent. Holding shift causes the ordinary climbable response to reset `fallDistance`, suppress negative Y, and pass zero Y into SELF movement.

For north FACING, `TrapDoorBlock.NORTH_OPEN_AABB` (`TrapDoorBlock.java` .1 lines 46, 71–86; .3 lines 50, 75–90) and `LadderBlock.NORTH_AABB` (`LadderBlock.java` .1 lines 30, 43–64; .3 lines 31, 44–65) both occupy z=13/16..1. The exact player dimensions are `.sized(0.6F,1.8F)` (`EntityType.java` .1 line 808; .3 line 1058), so the stated z center gives bounds [.2,.8]. Put a solid backing column at z+1 spanning ladder row y=0 and trapdoor row y=1, beginning at z=1. `BlockBehaviour.Properties` defaults `hasCollision = true` (.1 line 939; .3 line 946), and the vanilla trapdoor registrations in `Blocks.java` (.1 lines 2411–2420; .3 lines 2104–2113) use normal properties without disabling collision. Thus these shapes are collision shapes and stay outside the player's swept X/Z bounds as the 11 requests move the center from z=.5 to about .3378787789. The player's blockPosition remains the open trapdoor cell; the matching ladder remains directly below.

At each SELF move, require the complete `Player.canFallAtLeast(0,0,maxUpStep())` probe AABB collision-free (`Player.java` .1 support-probe lines 740–767; .3 789–816). It passes the entity and probe box to `CollisionGetter.noCollision(Entity,AABB)` (.1 lines 46–64; .3 lines 48–75). That query tests `BlockCollisions`, entity collision shapes, and world border. Thus no collision in the support probe makes `canFallAtLeast` true, `isAboveGround` false, and bypasses edge-backoff. Also require each horizontal swept AABB collision-free; the `Entity.collide` collision-query path then returns the request unchanged. This is an explicit source-level world precondition, not inferred from airborne state and not a runtime observation.

Exact endpoint support hashes (each matches its `.1` or `.3` source ledger):
- `net/minecraft/world/entity/LivingEntity.java`: 1.21.1 `324A3EEE8496CAAB57CFAF5101EF576F1AE3C60C40E3857E96F35F3AF9A3A0D8`; 1.21.3 `087390495F4FDFD14B9E12230E7AEA4FDC50913BB4D1D91119E72892881CFC52`.
- `net/minecraft/world/entity/Entity.java`: 1.21.1 `B81905C7879E2CC5C5063A41D865C4164AD919F156306705BE791D1017B99850`; 1.21.3 `A93719C302A0381A972AF75EA360465E2E3551708DD07C34D4D40B7E5173C2B9`.
- `net/minecraft/world/entity/player/Player.java`: 1.21.1 `ED32B88C3C7C8418B83DB41823520F2B6B0B49A98610306EF26E9681DC925C71`; 1.21.3 `A803203E92AA4729D5F5C9B16085B6A43CE51D9907D309EB96736E9C7C1340DE`.
- `net/minecraft/world/entity/EntityType.java`: 1.21.1 `23AA8FF6462783B83A09F058381A0EBEB8AFB066898806535E850E1F81CA1D0E`; 1.21.3 `433A855503EFF559BF0D9C009080B4FF1DEC73C497A455A1D22D6812AB9E7656`.
- `net/minecraft/world/level/block/TrapDoorBlock.java`: 1.21.1 `91DACBB5C9B33B5642266E3B9D3A402153237131A58554511EEB93699EFF3D6F`; 1.21.3 `F72063663C3791348F9A4496E56A94D900E52D356BF54AC595A5C85B113EE0C5`.
- `net/minecraft/world/level/block/LadderBlock.java`: 1.21.1 `AB51BD41E6FC33E605177F7131DE91EC7E383F80C11C279C72AE146E648D4167`; 1.21.3 `2A994DA48241C74ED3FC945CB073D7135B62F212E0AFA417A2797D9E6A1BEEB5`.
- `net/minecraft/world/level/block/state/BlockBehaviour.java`: 1.21.1 `35420EE4FCA120CCF1D1542A6B1746FCDD1E9DAFD125CF0B8193F9B8B406B292`; 1.21.3 `4D92417129EB6DEF084A67D249D2FF8C7A30A4767FA46E63A045667EA70D5133`.
- `net/minecraft/world/level/block/Blocks.java`: 1.21.1 `538AC50C164484CA4C2367A2A6C07CF2FAEDCAC0C4D5117D8817D36C2D1BDE6D`; 1.21.3 `28A66DA36CE98800226EC6D8E5FB4904FC7B137322A31F6D7EC822450374E62A`.
- `net/minecraft/world/level/CollisionGetter.java`: 1.21.1 `F21DAA1ED0365CC16A2638373D702FBAEB4242AD12A80A41105CF43C711E6D95`; 1.21.3 `42301653B90EEA59CAF787A92699BD707D2454155B7AA19E4FF2DE57BEEF774F`.

The default vanilla tag evidence is supplemental, not a witness dependency. `VanillaBlockTagsProvider.addTags` includes `Blocks.LADDER` under `BlockTags.CLIMBABLE` at both endpoints; `BlockTags.create` uses the default `minecraft` namespace. Packed endpoint tag JSON is absent; custom pack effects are not asserted.



