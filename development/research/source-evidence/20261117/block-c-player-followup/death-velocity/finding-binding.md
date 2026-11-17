# Corrected finding source binding

Terminal: **COMPLETED**

Reviewed finding SHA-256: `cb7e30fe4a5ee65ef3e32ff136c5283d6376d8576f57658e70e9db6e683371de` (verified against the supplied path).

Binding: **The finding's source claims and stated limits are supported.**

- In exact 1.8.9 `PlayerEntity.die(DamageSource)` at lines 513-535 and 1.9.0 at lines 483-506, the non-null-source horizontal velocity branch changes from multiplying by `(float) Math.PI / 180.0F` to multiplying by `(float) (Math.PI / 180.0)`. Both versions write vertical velocity first, keep the same negative sine/cosine expressions and 0.1F scale, and keep zero horizontal velocity for a null source.
- The event-3 path reaches that player override on the recipient's client: server `LivingEntity.die` emits event 3; `ServerWorld.doEntityEvent` routes the `EntityEventS2CPacket` through `EntityMap`/`TrackedEntity`; for a `ServerPlayerEntity`, `TrackedEntity.sendPacketToAll` also sends the packet to that player's own connection (confirmed in both 1.8.9 and 1.9.0). Client `handleEntityEvent` looks up the packet entity and calls `entity.doEvent`; inherited `LivingEntity.doEvent` handles event 3 with `setHealth(0.0F)` followed by `this.die(DamageSource.GENERIC)`. Those calls are at 1.8.9 lines 973-974 and 1.9.0 lines 1081-1082. The client path calls `die` directly, so it is not blocked by `takeDamage`'s client-side early return. `PlayerEntity.die` receives a non-null generic source and executes the changed branch.
- The finding accurately distinguishes this local event response from any subsequent velocity packet: `handleEntityVelocity` calls `Entity.lerpVelocity`, which assigns X/Y/Z and can overwrite the local death values. It does not claim a final trajectory or a server-side producer change. The exact 1.9.0 handler and lerp method locations in the finding are verified: `ClientPlayNetworkHandler.handleEntityVelocity` line 434; `Entity.lerpVelocity` line 1556.

No product implementation files were read or changed for this binding. No tests or launches were run.

Hashes:

- Reviewed finding: `cb7e30fe4a5ee65ef3e32ff136c5283d6376d8576f57658e70e9db6e683371de`
- Prior audit verdict: `b706facbce02ab5e01b9adb82be39626ba96bf0ba1c4f4402b12c78a80b01d57`