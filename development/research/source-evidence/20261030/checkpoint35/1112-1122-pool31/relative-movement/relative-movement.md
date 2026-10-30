# Relative movement: 1.11.2 → 1.12.2

## Verdict

No vanilla player movement difference found in the bounded `moveRelative`/relative-input slice. In 1.12.2 the shared entity helper gains a third input and adds it to Y velocity, but every vanilla player path supplies `verticalSpeed` as that component and no `PlayerEntity` subclass writes the field. The player input and packet paths still provide lateral/forward axes plus jump/sneak only. For a player, the third component is positive zero: the added squared term leaves the two-axis norm unchanged, and the added Y increment is zero before the existing gravity, fluid, jump, climbing, or flight response. No player movement candidate is proposed.

## Exact source identity

Both source trees are `ornithe-feather`, published as Feather Gen2 build 2. The ready markers identify exact versions 1.11.2 / 1.12.2 and their mapping artifacts (`feather-gen2-1.11.2+build.2-mergedv2.jar` plus its `.tiny`, and the corresponding 1.12.2 build.2 artifacts). The ready marker's source-manifest hash matches the actual manifest hash in each tree:

- 1.11.2 manifest: `d908c2af76598ad26999563bfef0089f1d3c8c32483eee6b108f0defa2773df0`
- 1.12.2 manifest: `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`

Every listed source below was SHA-256 checked against that version's `ornithe-feather.sources.sha256` manifest; all matched.

| Source file (relative to `ornithe-feather/`) | 1.11.2 SHA-256 | 1.12.2 SHA-256 |
|---|---|---|
| `net/minecraft/entity/Entity.java` | `ce8104a17ce783df639cf9726e7b1cd0936563eaa7ba308603335bbe05d49440` | `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a` |
| `net/minecraft/entity/living/LivingEntity.java` | `bb7dc6c9e423a9568d6433d51bba12e7aee4555fbf3fb3e2b87f618382279f2f` | `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6` |
| `net/minecraft/entity/living/player/PlayerEntity.java` | `87fe94fa6cbf7aba18b9a5e3401664439eb8eba9958173da8fbcd05cc7ad948b` | `e4e0fdbe07a7d0a0ae4a70cbb6739a287c9d045a4a12b409895c220b5d91fe1e` |
| `net/minecraft/client/entity/living/player/ClientPlayerEntity.java` | `207a0ff4ee996916cd1fc73f47bfaae64f92719916cafed8395b1f2a8f797809` | `207a0ff4ee996916cd1fc73f47bfaae64f92719916cafed8395b1f2a8f797809` |
| `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `65c2747bd8c70def6be7f41f624d4c9493342b39ae7bed7967f9ff63608f59ed` | `01a58e94d8c6ff98a8e3794227cdc76a5fcbdbad795c70c9cf28854aff9823cc` |
| `net/minecraft/client/entity/living/player/RemoteClientPlayerEntity.java` | `e7e248b439f7356695b1bf196d08e997bb19b24c23b206763c772663ee7086a1` | `ea18eed3f59dc3f21bd6a2239392fd8652ea9612871fb7165e602994ce4ecbb3` |
| `net/minecraft/client/entity/living/player/Input.java` | `9e704cfe7fdc55c4eab78670e60cf392ba6817adbd5e7451d3a86f11da8bf50e` | same |
| `net/minecraft/client/entity/living/player/KeyboardInput.java` | `7be11425906be051c83e275f359816546e4677b16d212156380e8d2e9258654a` | same |
| `net/minecraft/entity/living/mob/MobEntity.java` | `19af2d8ce9297c8e149c8a1140ed31b5c50bc8fff07da12bfddeaa4d1f4d60b6` | `dda723979d5c1bda2532b15c758bd6c5572dfe1f201d32d8f51d40007773c3c4` |
| `net/minecraft/server/entity/living/player/ServerPlayerEntity.java` | `c2187f10b589bbfb47bef2a1b573781fecad55a13fb80c4c5dc11260d9f12197` | `ad4effc65edd98098d86af8ba725f6d6c2c94068f996739cf2734d34ecf804a9` |
| `net/minecraft/network/packet/c2s/play/PlayerInputC2SPacket.java` | `fd6f486dd2a06f22883d297d056d40fef9b6f0385e541b21115659a5ad2fd070` | same |
| `net/minecraft/server/network/handler/ServerPlayNetworkHandler.java` | `947be17cf3d7217e7c8e563d4dd312cd133de1dc06bcd82b4431c9e5e74bc0bf` | `77bf65b2c48ff952713942e183af1cd5fb243ad4f8fd2e53aa1b97272e7acd7c` |

## Paired source slices and player call coverage

- `entity/living/LivingEntity.java`, 1.11.2 lines 1388–1561; 1.12.2 lines 1425–1599: `moveRelative` gates, elytra, land/air, lava and water branches, input application, climbing, levitation/gravity and movement-stat tail. `mobTick` dispatches to the virtual method at 1.11.2 line 1803 and 1.12.2 line 1847.
- `entity/Entity.java`, 1.11.2 lines 1028–1044; 1.12.2 lines 1049–1067: `updateVelocity` math.
- `entity/living/player/PlayerEntity.java`, 1.11.2 lines 1386–1405; 1.12.2 lines 1385–1404: player override and its flying-speed wrapper. Both versions call the superclass in the same branches; the only change is forwarding the new third float.
- Local client player input: `LocalClientPlayerEntity.serverTickAi`, 1.11.2 lines 614–625 and 1.12.2 lines 631–642, copies `Input.movementSideways`, `Input.movementForward`, and `Input.jumping`; `LocalClientPlayerEntity.mobTick` reaches `super.mobTick` at 1.11.2 line 790 / 1.12.2 line 812. `KeyboardInput.tick` is identical (same source hash): it computes just side/forward and jump/sneak, scaling the two axes for sneak. Flight sneak and jump adjust `velocityY` directly (1.11.2 lines 749–759; 1.12.2 lines 772–782), not `verticalSpeed`.
- Remote client players override `mobTick` (both at line 69) for interpolation/animation/push and do not call the living superclass, so they do not reach this `moveRelative` call through that tick path.
- Server player input is also only two-axis: `PlayerInputC2SPacket` has `sidewaysSpeed`, `forwardSpeed`, `jumping`, `sneaking` (identical source hash in both versions); the handler forwards those four values to `ServerPlayerEntity.setPlayerInput` (1.11.2 lines 258–260; 1.12.2 lines 271–273), which writes side/forward and jumping/sneaking. Server movement is outside the mod's historical runtime scope, but it has no third input either.

## Changed helper versus player reachability

`Entity.updateVelocity` changes from `f = sideways * sideways + forwards * forwards` and horizontal X/Z updates (1.11.2) to `f = sideways * sideways + forwards * forwards + upwards * upwards`, followed by horizontal X/Z updates plus `velocityY += forwards` (1.12.2). The `LivingEntity` call order is `moveRelative(this.sidewaysSpeed, this.verticalSpeed, this.forwardSpeed)` in 1.12.2, while 1.11.2 passes only `sidewaysSpeed, forwardSpeed`.

`verticalSpeed` exists only in 1.12.2. The complete source search finds its declaration/use in `LivingEntity` and its only write in `MobEntity.setVerticalSpeed` (lines 474–475). `PlayerEntity` extends `LivingEntity` directly (1.11.2 line 96; 1.12.2 line 94), not `MobEntity`; `LocalClientPlayerEntity`, remote client players and server players extend the player class chain. No vanilla player class writes the field, and neither local input nor the server packet has an upward field. It therefore has its Java default `+0.0F` in vanilla player flow.

With `verticalSpeed == +0.0F`, the new float normalization expression evaluates the prior two-axis sum with an intervening `+ 0.0F`, which leaves the finite nonnegative squared-input sum unchanged; the new Y increment adds zero. Player sprint still changes the same movement-speed/air-speed scale; sneak still scales the same horizontal inputs; jump and flight vertical responses still modify `velocityY` via their existing branches. Elytra flight uses the same look/velocity calculations and does not consume any movement-input components. Water/lava and land/air branches pass the zero component only to the new helper; their surrounding fluid multipliers, friction, gravity, climbing and collision gates are otherwise unchanged for the player.

One adjacent non-player/animation difference appears in the 1.12.2 method tail: `FlyingAnimal` vertical displacement is newly included in the walk-animation speed calculation (1.12.2 lines 1591–1596); `PlayerEntity` is not `FlyingAnimal`, and animation is outside this movement slice. `MobEntity.setVerticalSpeed` is likewise non-player steering and outside scope.

No permanent finding was drafted because no player movement difference was established. No source files were modified or generated, and no build, test or launch was run.
