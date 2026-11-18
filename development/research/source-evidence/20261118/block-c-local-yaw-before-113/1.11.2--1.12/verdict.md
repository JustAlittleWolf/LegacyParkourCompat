# Pair verdict: Feather 1.11.2 → 1.12

## Frozen scope

Blind, bounded source audit of the ordinary local player yaw update and the first dry ground/air movement-acceleration use of current yaw. Source identities are frozen in `IDENTITIES_FROZEN.md`. No product implementation, wiki evidence, Git history or runtime evidence was consulted.

## Ordinary yaw route and verdict

- 1.11.2 `net/minecraft/client/render/GameRenderer.java:835-862` and 1.12 `.../GameRenderer.java:835-867`: while the client is focused and the render path's `bl` gate is true, mouse tick runs; sensitivity is `f = mouseSensitivity * 0.6F + 0.2F`, gain is `g = f * f * f * 8.0F`, and float deltas are `h = mouse.x * g`, `i = mouse.y * g`. Y inversion uses integer `j = ±1`. Smooth camera replaces `h/i` with smoothed deltas times tick delta and the elapsed tick-delta difference. Both paths then directly call `player.updateLocalPlayerCamera(h, i * j)`.
- The sole route insertion in 1.12 is `getTutorial().onMouseInput(mouse)` after `mouse.tick()` and before the deltas are read. `Tutorial.onMouseInput` forwards to the active step; the only concrete mouse handler in this source set sets the tutorial's `looked` flag from the input magnitude. It does not change mouse deltas or player rotation.
- 1.11.2 `net/minecraft/entity/Entity.java:304-316` and 1.12 `.../Entity.java:307-319`: method arguments and stored `yaw`, `pitch`, `lastYaw`, `lastPitch` are `float`. It snapshots current pitch/yaw into local floats, adds `yaw * 0.15` to yaw and casts the double-promoted sum back to float, subtracts `pitch * 0.15` and casts back, clamps pitch to [-90, 90], then adds each post-clamp/current delta to its previous-angle accumulator. Passenger look notification follows. Yaw sign is positive for positive horizontal mouse delta; pitch sign is negative for positive vertical input after inversion handling. No wrap is applied here.
- Ordinary entity tick snapshots `lastYaw = yaw` and `lastPitch = pitch` (1.11.2 `Entity.java:335-340`; 1.12 `Entity.java:338-343`). The camera update then adjusts current yaw and advances `lastYaw` by the same float delta. Movement acceleration below reads `this.yaw`, not `lastYaw` or an interpolated angle.

## First dry ground/air acceleration dependency

- 1.11.2 `LivingEntity.java:1798-1804` decays `sidewaysSpeed` and `forwardSpeed` by `0.98F`, then calls `moveRelative(sidewaysSpeed, forwardSpeed)`. Its `PlayerEntity.java:1386-1402` override forwards those values to `super` in both ordinary and ability-flying branches.
- 1.11.2 `LivingEntity.moveRelative` (`LivingEntity.java:1388-1456`) takes the dry non-fall-flying route and chooses `u = getSpeed() * (0.16277136F / (s*s*s))` on ground or `u = speedInAir` airborne, then calls `updateVelocity(sideways, forwards, u)`. The subsequent friction read is separate from this acceleration.
- 1.11.2 `Entity.updateVelocity` (`Entity.java:1028-1043`) has a horizontal two-input norm, threshold `1.0E-4F`, float square root/minimum, and `scale / magnitude`. It reads current yaw as `MathHelper.sin/cos(yaw * (float)(Math.PI / 180.0))` and adds `sideways*cos - forwards*sin` to X and `forwards*cos + sideways*sin` to Z.
- 1.12 `LivingEntity.java:1842-1848` now calls `moveRelative(sidewaysSpeed, verticalSpeed, forwardSpeed)`; its `PlayerEntity.java:1385-1403` override forwards the same triple. In the dry branch `LivingEntity.java:1485-1493`, acceleration uses the ground/air `u` selection above and calls `updateVelocity(forwards, sideways, upwards, u)`.
- 1.12 `Entity.updateVelocity` (`Entity.java:1049-1067`) adds a third normalized component and writes it to Y. Its horizontal formulas still use current yaw with the same sin/cos expression and operation order. For a player, `verticalSpeed` is not assigned by the PlayerEntity/local-player source path (the only non-declaration assignment in this release's source tree is `MobEntity.java:475`); the player field therefore remains its JVM-default zero. The 3D norm consequently reduces to the previous horizontal norm for this player path, and horizontal X/Z acceleration is exactly preserved for dry ground and air.

## Finite witness

Take current `yaw=0.0F`, `lastYaw=0.0F`, and the direct camera method input `yaw=4.0F`, `pitch=0.0F`. All four arithmetic steps produce `yaw=0.6F`, unchanged pitch, and `lastYaw=0.6F`. On the following ordinary dry movement call, use finite forward input `1.0F`, sideways and player vertical input `0.0F`, and any finite dry ground/air scale `u`: both kernels return `velocityX = -u * MathHelper.sin(0.6F * (float)(Math.PI / 180.0))`, `velocityZ = u * MathHelper.cos(0.6F * (float)(Math.PI / 180.0))`, and no Y acceleration. This exhibits the positive-mouse/current-yaw/sign/cast route and the zero vertical component that makes the new normalization equivalent for the player.

## Verdict

No change to ordinary current/previous yaw handling or to the player's horizontal dry ground/air current-yaw acceleration. The generic `Entity.updateVelocity` API changes from two normalized axes to three, but the only added player call component is zero in the examined vanilla player path; do not accept this as a player movement delta for this operation. Patch-introduction status beyond this adjacent pair is not inferred.

## Source files and SHA-256

- 1.11.2 `GameRenderer.java`: `d8f54f58020e52b117c74cf248a1bac4fc541c4244340f6a0b43f5dbce245db5`
- 1.12 `GameRenderer.java`: `c6634f86b93421a4f1d05ab3df5bfdf8edef3a295190a3a06eba48a4cac874bb`
- 1.11.2 `Entity.java`: `ce8104a17ce783df639cf9726e7b1cd0936563eaa7ba308603335bbe05d49440`
- 1.12 `Entity.java`: `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a`
- 1.11.2 `LivingEntity.java`: `bb7dc6c9e423a9568d6433d51bba12e7aee4555fbf3fb3e2b87f618382279f2f`
- 1.12 `LivingEntity.java`: `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6`
- 1.11.2 `PlayerEntity.java`: `87fe94fa6cbf7aba18b9a5e3401664439eb8eba9958173da8fbcd05cc7ad948b`
- 1.12 `PlayerEntity.java`: `6a9d5f420b13625195b149c0d9468cf1284d5eb7dc1b2a46cf01b952a27ee063`
