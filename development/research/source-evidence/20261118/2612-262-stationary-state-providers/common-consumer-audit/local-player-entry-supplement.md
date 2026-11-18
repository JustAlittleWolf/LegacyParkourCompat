# Local-player entry supplement

This supplements the previously frozen shared-consumer roster with the narrow caller path for an ordinary logical-client `LocalPlayer` that is not riding. It does not expand the collision-consumer comparison or cover a full tick.

## Direct source path

- `LocalPlayer` extends `AbstractClientPlayer`; that extends `Player`. The inherited player types continue through `Avatar` and `LivingEntity` to `Entity`.
- `LocalPlayer.aiStep()` calls `super.aiStep()`. `AbstractClientPlayer.aiStep()` and `Player.aiStep()` also call `super.aiStep()`, reaching `LivingEntity.aiStep()`.
- `LivingEntity.aiStep()` chooses `travel(input)` when there is no live controlling passenger and both `canSimulateMovement()` and `isEffectiveAi()` are true. `LocalPlayer.isLocalPlayer()` returns true. On a logical client, Player's two simulation gates return `isLocalPlayer()`, so this exact LocalPlayer passes them.
- `Player.travel()` has a passenger branch; when `isPassenger()` is false, the normal branch delegates to `LivingEntity.travel()`. For a dry, non-fall-flying travel state, `LivingEntity.travel()` selects `travelInAir()`.
- The ordinary air movement helper calls `move(MoverType.SELF, getDeltaMovement())`. Dynamic dispatch reaches `LocalPlayer.move()`, which calls `super.move(moverType, delta)`; the intervening `AbstractClientPlayer`, `Player`, `Avatar`, and `LivingEntity` sources do not override `move`, so this reaches the shared `Entity.move()` consumer in the frozen roster.

`LocalPlayer.move()` performs its AutoJump and walked-distance work after `super.move`; those effects are outside this supplement's claim. Fluid, fall-flying, riding, step-up, and other movement paths are outside the stated ordinary dry non-riding caller path.

## Manifest-bound source files
### 26.1.2, unobfuscated

- `net/minecraft/client/player/LocalPlayer.java` — `433fd995ad317af0f6ef0e50c1e8e3483cb8f00e0e327d4edf27a4dd99666ebe`
- `net/minecraft/client/player/AbstractClientPlayer.java` — `7825a8d4e8e24928cba18f68109c91479b78fb14fcd8b81f0e045b8d02a559cc`
- `net/minecraft/world/entity/player/Player.java` — `44cf28e0c64e78d39fd13368e9991381dbebab67029070cb9ddc43f09d45d14d`
- `net/minecraft/world/entity/Avatar.java` — `01a8092bc2637e7d42a261fb564bffcabadd28625cc02b120e943559e2d4fe78`
- `net/minecraft/world/entity/LivingEntity.java` — `c3b64de8dbaba8ad8a7ccd4f33255346d8206e66f12ca91260971bf5e5ac93bd`
- `net/minecraft/world/entity/Entity.java` — `8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf`

Source manifest SHA-256: `54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0` (matches the frozen ready identity `54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0`).

### 26.2, unobfuscated

- `net/minecraft/client/player/LocalPlayer.java` — `8d089aa09217e3607b38590f7c1623385562800943ac6dfd3d17804e041da6d6`
- `net/minecraft/client/player/AbstractClientPlayer.java` — `7825a8d4e8e24928cba18f68109c91479b78fb14fcd8b81f0e045b8d02a559cc`
- `net/minecraft/world/entity/player/Player.java` — `8decc71b9c780664578ddb14591db2a2f207c72c05b676edded6f8e964576531`
- `net/minecraft/world/entity/Avatar.java` — `01a8092bc2637e7d42a261fb564bffcabadd28625cc02b120e943559e2d4fe78`
- `net/minecraft/world/entity/LivingEntity.java` — `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a`
- `net/minecraft/world/entity/Entity.java` — `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5`

Source manifest SHA-256: `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894` (matches the frozen ready identity `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894`).

