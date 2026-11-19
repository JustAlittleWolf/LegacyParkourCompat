# Independent vertical collision verdict

I independently confirmed the ordinary vertical signed-zero change in the published unobfuscated 26.1.2 and 26.2 client source. Both source manifests and the common movement dependencies were verified in the [horizontal contact audit](../audit_contacts/horizontal-wall-zero.md). The source manifest SHA-256 values are `54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0` and `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894`, respectively.

## Exact operation and applicability

Paths are relative to `decompiled_minecraft/<release>/unobfuscated/net/minecraft/`.

- 26.1.2 `world/entity/Entity.java` 752–785 applies a vertical callback whenever `delta.y != movement.y`, the entity remains present and `canSimulateMovement()`. The callback receiver is the effect block under the feet, including on a ceiling hit. Ordinary `world/level/block/Block.java` 498–500 multiplies by `(1.0, 0.0, 1.0)`. Stone and air use this ordinary response.
- 26.2 `world/entity/Entity.java` 759–785 and 802–852 use vertical restitution under the simulation gate. The living-entity restitution base is +0.0 through `LivingEntity.java` 2201–2204 and `world/entity/ai/attributes/Attributes.java` 31. `world/level/block/Blocks.java` 56–59 uses the default zero block restitution from `world/level/block/state/BlockBehaviour.java` 992–995 and 113 for stone. A blocked floor move can use the block restitution lookup or fall back to zero through the gravity/suppression gates. A blocked upward ceiling move uses the entity restitution only. At zero restitution the final vertical write is `(0.0 - currentMovement.y) * 1.0 * 0.0`. Neither ordinary path emits a bounce event in 26.2.
- For an ordinary floor hit with strictly negative current velocity this reverses the raw Y sign from -0.0 to +0.0. For an ordinary ceiling hit with strictly positive current velocity it reverses the sign from +0.0 to -0.0. This is a velocity response after displacement and contact flags are computed. It is separate from any horizontal response.
- Both paths preserve the sign through the Y multiplier of 1.0 during the move. For a loaded chunk and default gravity, the following ordinary travel subtracts positive 0.08 and applies positive vertical drag, leaving equal end-of-tick Y for this slice. For base gravity +0.0 the subtraction instead preserves either zero sign. See `LivingEntity.java` 2383–2389, 2430–2453, 2623–2633 (26.1.2) and 2420–2427, 2468–2497, 2676–2686 (26.2). Stable positive drag at default modifiers preserves both zero signs.
- Player simulation and effective-AI predicates admit the local client, and local authority applies collision flags. `client/player/LocalPlayer.java` 227–250, 378–380, 692–699, 914–919 and 986–994 and `client/player/AbstractClientPlayer.java` 44–47, 75–90 cover the player entry and move wrapper. `world/entity/player/Player.java` 1260–1276/1281–1297 admits the logical server too in vanilla. `server/level/ServerPlayer.java` 640–645/648–653 dispatches `super.tick()` for an ordinary player, and `server/network/ServerGamePacketListenerImpl.java` 314/318 and 1127/1134 also distinguish server ticks from packet-driven moves. Historical replay applicability is the logical local client under project scope. These vanilla server entry points do not imply any historical replay there.

## Grounded jump witness at zero gravity

Take a standing player at (0.5, 64, 0.5) on a normal stone floor, under a stone ceiling with underside at y=66. The two air blocks in between are loaded. Use no horizontal input, fluids, climbing, effects, powder snow, equipment modifiers, auto-jump, passenger state or other entities, and retain default jump strength, friction and restitution. Arrange base `GRAVITY` to be positive zero at the collision tick and the client's `onGround` to still be true at jump processing, with `noJumpDelay == 0`. For example, start from an ordinary grounded tick at default gravity with stored downward Y, then use the first client jump tick after the positive-zero attribute update, before standing with zero Y has cleared `onGround`. The local-authority contact update can clear `onGround` on a subsequent zero-displacement tick, so an arbitrarily delayed stationary jump is not the witness.

The same preexisting syncable ranged attribute is registered at `world/entity/ai/attributes/Attributes.java` 45–47/55–57 and included in `LivingEntity.java` 328/342. Dirty server attributes dispatch to the player itself via `server/level/ServerEntity.java` 338–351/343–356. Client packets apply the base value and modifiers to the living entity at `client/multiplayer/ClientPacketListener.java` 2355–2379/2357–2381. Positive zero is within the registered range in both versions. The positive `0.42F` ordinary jump wins against the initial nonpositive Y through `LivingEntity.java` 2339–2364 and 3031–3056 / 2376–2401 and 3098–3123. The standing height is `1.8F` (`world/entity/EntityType.java` 1189–1197 / `world/entity/EntityTypes.java` 1163–1171). With `EntityDimensions.java` 15–23, the clearance and clipped displacement are `0.20000004768371582`. The vertical collision is above rather than below and the block effect position's ordinary callback writes +0.0 in 26.1.2. In 26.2 zero restitution writes -0.0.

Both Y values survive `getDeltaMovement().y` at the end of this loaded local-player tick. After travel writes Y, ordinary stone/air block effects and the empty entity push list do not modify it. The remaining player tick work and send path do not replace velocity under these conditions. See `LivingEntity.java` 3058–3111 and 2738–2814 / 3125–3178 and 2804–2880, plus `Player.java` 231–284 and 442–482 / 232–285 and 442–482, and `LocalPlayer.java` 227–250 and 914–919 in both. The next tick's pre-travel near-zero clamp sets Y to positive zero if the state stays otherwise untouched (`LivingEntity.java` 3012–3016 / 3079–3083). The witness establishes a raw bit difference at the collision tick boundary, with unchanged displacement for this ordinary collision. Runtime parity and the exact introduction patch remain open.

## Additional verified class hashes

```text
26.1.2
27fa1fb5c3e9bf49123a238675f6bea29e65ad5b0362a9a64dcb5d5f5407376a *net/minecraft/world/entity/EntityDimensions.java
e5f37ee2dd639b7271197e58f7a6f3bdbedc0f0f1d618039d159a0e7de315229 *net/minecraft/world/entity/EntityType.java
eb70d05e4f8429341fc41823eae2cc662560efa1e44c8b729db5477501718d82 *net/minecraft/client/multiplayer/ClientPacketListener.java
fe7146799ebe95ba92220ad21a38a80f5352ddbb21ccefb93063b8f9a89631dd *net/minecraft/server/level/ServerPlayer.java
7e643834e2ab54bd1b1aeb79f72146ac4e1f6d30b34e7608091a9ab15494d3e7 *net/minecraft/server/network/ServerGamePacketListenerImpl.java
8f531f9ba5117e66b596d6770702801b81b115b5af5dd896d685ff54b7a02cad *net/minecraft/server/commands/AttributeCommand.java
ca262f3d973642a2887b235a549b14f335706413f9551b417059e72e6bcf8cfd *net/minecraft/world/entity/ai/attributes/AttributeInstance.java
b2ba4e6331f6b1e44aee186436b141e903d528553c5ff78516edd6a1b4ad7ac3 *net/minecraft/server/level/ServerEntity.java
26.2
27fa1fb5c3e9bf49123a238675f6bea29e65ad5b0362a9a64dcb5d5f5407376a *net/minecraft/world/entity/EntityDimensions.java
350a5090fa1ae3afdc6490101f2e719aeef84864b0d4a247816d54788e37ae7f *net/minecraft/world/entity/EntityType.java
1b1b09812ed04d143f487d754aff5ed10ed696e8da04ce836ccbdbe0794d5a6c *net/minecraft/world/entity/EntityTypes.java
9cb0cc8afebae9f4e42f428a52719c645817d03893dc371e6711c7bd16eac1b6 *net/minecraft/client/multiplayer/ClientPacketListener.java
37e617a5beccbf3558c8cf8f6624ba9d704593ccc5d6633bedf71cf4f792e82e *net/minecraft/server/level/ServerPlayer.java
2015d9e3c8d3e277bc2ef9e709ad4c991894df1deaa2eaca3a5a5dcc29fb51d2 *net/minecraft/server/network/ServerGamePacketListenerImpl.java
3234f11758052d5d10cabb8ceb1454d39bde8c53b3205914538e77759ca31e7c *net/minecraft/server/commands/AttributeCommand.java
c7cbe38bfdc1b4aafcf623bd1b604200cc6190641091369ee9bb9303e8f07ad5 *net/minecraft/world/entity/ai/attributes/AttributeInstance.java
3c55dbb107c3659313b64ab1ecfc2bb9549d33989cf9fc9fbc20b22366478dc7 *net/minecraft/server/level/ServerEntity.java
```
