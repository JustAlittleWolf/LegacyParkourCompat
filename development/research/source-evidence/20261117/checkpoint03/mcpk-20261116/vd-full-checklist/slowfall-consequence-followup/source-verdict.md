# Slow Falling applicability follow-up

## Verdict

The prior source verdict for the immutable post-introduction formula claim is reusable: the packet SHA-256 is still `F4BCF864C6E5DE492FB35904F097BC4D6F0CA304D7A4A4677C22302672B56BB8`, and the 1.20.4, 1.20.5 `LivingEntity.java`, 1.20.5 `Attributes.java`, and 26.2 `LivingEntity.java` hashes match that packet. This follow-up closes the missing practical-applicability condition without changing the formula claim or its source pair.

A sub-`0.01` effective gravity is **not produced by ordinary vanilla movement inputs or the Slow Falling potion/effect alone**: a default player has gravity `0.08`; the vanilla Slow Falling effect has no gravity attribute modifier; no vanilla item/effect reference applies a gravity modifier in 1.20.5. However, it **is reachable with vanilla's authorized `/attribute` command** in 1.20.5 and later: player attributes inherit `GRAVITY`, the attribute is syncable, its allowed range includes `0.005`, and the command's `base set` path calls `setBaseValue`. With Slow Falling active and current Y velocity `<= 0.0`, this makes the 1.20.5 formula choose `0.005`; the client consumes it on that tick. Thus the old packet's conditional movement consequence is a reachable vanilla state if commands/map command resources are included. It is not a default survival trajectory difference.

## Exact evidence

- Packet hash: verified with `Get-FileHash` (SHA-256), unchanged.
- 1.20.4 Mojmap `LivingEntity.java`, SHA-256 `f7bc53db24c1798f19f9bd6f6356c86d5e560e9c8e8aac60decaf15cc785e07d`: `travel` 2035–2041 loads literal `0.08`, then writes literal `0.01` under local control, Y `<= 0.0`, and Slow Falling.
- 1.20.5 Mojmap `LivingEntity.java`, SHA-256 `c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2`: `getDefaultGravity` 2100–2102 reads `Attributes.GRAVITY`; `travel` 2104–2110 reads `getGravity()` and applies `Math.min(value, 0.01)` under the same gate. Air travel consumes that gravity at 2215–2226; fluid movement passes it to the falling adjustment at 2142–2144.
- 1.20.5 Mojmap `Attributes.java`, SHA-256 `7e30d87c7a58b14d0052d2f9f7319d997b49ae7d025579cd762d28e845b2e82e`: 42–44 declares `generic.gravity`, default `0.08`, range `[-1.0, 1.0]`, syncable. `LivingEntity.createLivingAttributes` 292–305 includes it; `Player.createAttributes` 221–229 builds from that supplier. `Entity.getGravity` 1101–1107 returns zero only for no-gravity, otherwise the living default gravity.
- 1.20.5 Mojmap `AttributeCommand.java`, source SHA-256 `f6d62af4f39595b3f4efffd240acb8e9e83d4d9a67dc873e5110998ac23fb009`: command registration 72–83 exposes `base set` with a double argument; helper 273–276 calls `setBaseValue`. `RangedAttribute.sanitizeValue` clamps to `[-1, 1]` (`RangedAttribute.java:34–37`), so `0.005` is accepted. The calculation used by `getAttributeValue` sanitizes the resulting value (`AttributeInstance.java:140–158`).
- Attribute synchronization is explicit: `AttributeMap.onAttributeModified` queues syncable attributes (30–34); `ServerEntity.sendDirtyEntityData` sends dirty attributes in `ClientboundUpdateAttributesPacket` (297–303); `ClientPacketListener.handleUpdateAttributes` applies the received base and modifiers (2059–2079). Thus a server-side vanilla command change reaches the client-side `getGravity()` path.
- 1.20.5 Mojmap `MobEffects.java`, source SHA-256 `9b24dee8a23a4b8730fdafa7ea9cbd560c9370599cc282fac1c0755f5894e7645`: Slow Falling is a bare `MobEffect` at 87, with no attribute modifier. A full 1.20.5 source search found no other `Attributes.GRAVITY` or `generic.gravity` references beyond the declaration, common living-attribute builder, gravity getter, and this movement operation.
- Client timing in 1.20.5: `LocalPlayer.tick` 190–192 calls `super.tick`; inherited `Player.tick` calls `super.tick` at 271; `LivingEntity.tick` 2375–2377 calls `aiStep`; `LocalPlayer.aiStep` 807 delegates through `Player.aiStep` 532 to `LivingEntity.aiStep`; `LivingEntity.aiStep` 2670–2683 reaches `travel` after applying input and checks/reset bookkeeping; `Player.travel`'s ordinary branch calls `super.travel` at 1493. `Entity.isControlledByLocalInstance` 2960–2962 recognizes the local player, so the historical gravity branch executes client-side. The Y/effect predicate is sampled in `LivingEntity.travel` before that tick's gravity is applied.
- Default consequence: at default gravity `0.08`, `Math.min(0.08, 0.01) == 0.01`; this is identical to the pre-1.20.5 literal. Positive Y bypasses the Slow Falling clamp in both versions.
- 26.2 unobfuscated `Attributes.java`, 55–57 still gives GRAVITY default `0.08` and range `[-1,1]`. `LivingEntity.getDefaultGravity` 2419–2422 reads it; `getEffectiveGravity` 2424–2428 retains the same Y/effect gate and `Math.min`. Air, fluid, and fall-flying movement consume the helper at 2480–2495, 2504–2513, and 2606–2610. The extracted-helper endpoint does not change this formula claim.

## Remaining qualification

If applicability is defined as unmodified survival gameplay with no command-driven attribute edits, below-`0.01` is not reachable from ordinary player input or vanilla Slow Falling/equipment, so no default trajectory delta is established. If vanilla commands or command-enabled map resources count—as they should for a source-level reachable vanilla state—the condition is closed by `/attribute`'s source path. Runtime trajectory parity was not measured.




