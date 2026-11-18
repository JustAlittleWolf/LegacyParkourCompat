# 1.19.3 mounted water zero-contact: absolute position packet gate

## Scope and prior boundary

This is a fresh source-only check of one distinct receipt path for the previously recorded 1.19.3 zero-depth body-contact condition. It does not revisit the accepted positive-depth travel scope, compare elemental scans, or model boat motion. Prior source artifact: `C:/Users/Wolfi/.codex/visualizations/2026/11/18/01a1f01a-5c56-7280-8791-914a15288686/mounted-water-zero-contact-source/evidence.md`.

## Source identity

Canonical source is `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/1.19.3/mojmap`, ready marker `decompiled_minecraft/1.19.3/mojmap.ready.json` (version 1.19.3, mapping mojmap, status ready). Its source-manifest SHA-256 is `6f1cc6d07ab3902a7ea25a3f8817504723be70d82e97f541cffda95ea2c1c843`; artifact-manifest SHA-256 is `13e9e1a1458d0c6352207dac6edb36745427b012d30408e7cb42035b2f042652`. The ready marker records the exact release and source-manifest identities. Relevant source-file SHA-256:

- `net/minecraft/client/multiplayer/ClientPacketListener.java`: `c01c3c362e5eb7de780f4ca0f1b8964a49e40cfc166ae63cbe392cd71b6b1b9e`
- `net/minecraft/client/player/LocalPlayer.java`: `64b670ee323d195b3928fb8ea629c26d2560e75a17379a12c376a7bc686d5479`
- `net/minecraft/world/entity/Entity.java`: `8c35bdef3bf2de7b70ac76ad767ba2c194305e0dda2e03fdf6544983667adc32`
- `net/minecraft/world/entity/EntityDimensions.java`: `ced817743f3bd35462c94c0112adf6f54eabf6132b0a349b05160e519bad3502`
- `net/minecraft/world/entity/EntityType.java`: `b63be8727a2fa406bef2ae45c1c9ebe06a56440e01b6dc85c443cbb738c32c06`
- `net/minecraft/world/entity/player/Player.java`: `02e64e197aec3f4f2a8abc9c5ce4545121ac5bcbbc71592ce478601a0db17203`
- `net/minecraft/world/entity/vehicle/Boat.java`: `034e238686cc019c4285f68631586053c7fef2627cfba0e7220d29646270c253`
- `net/minecraft/world/level/material/FlowingFluid.java`: `d84ceb3c0b83da25fb68599e16cc2298106e745a9ef0c9f32f616d097a8ec56c`
- `net/minecraft/world/level/material/WaterFluid.java`: `3e10320356b11d415d8e4f3ea2b7fbb27e5bd8abdc45d524afb18b834e613e5f`

## Receipt and ordered-position trace

`ClientPacketListener.handleMovePlayer(ClientboundPlayerPositionPacket)` (592–661) only detaches the player if `requestDismountVehicle()` is true (595–597). Otherwise, when Y is not in the relative-argument set, `$$11` is exactly `packet.getY()` (619–628). It calls `player.setPos($$7, $$11, $$15)` (645). There is no `isPassenger`/vehicle guard before that write. Thus an absolute server position packet with `requestDismountVehicle()==false` applies its exact Y to the local player while the existing passenger relation remains intact.

`Entity.setPos` (376–383) stores the position and reconstructs the box through `EntityDimensions.makeBoundingBox`; that box has `minY == position.y` (EntityDimensions.java 21–25). `LocalPlayer.rideTick` delegates first to `super.rideTick()` (874–875). `Entity.rideTick` does `setDeltaMovement(Vec3.ZERO)`, calls `tick()`, then only afterward calls `vehicle.positionRider(this)` if still a passenger (1704–1709). `Entity.tick` calls `baseTick()` (403–405), and `baseTick` reaches `updateInWaterStateAndDoFluidPushing()` (428) before returning. Consequently, the next inherited ride tick scans the packet-position box before the vehicle's rider-position writer can overwrite that position. `LocalPlayer.rideTick` has no intervening position writer after the inherited call; its remaining lines only update hands/boat input (875–882).

## Concrete contact geometry

Use the ordinary standing player shape: Player's standing dimensions are scalable `(0.6F, 1.8F)` (Player.java 124–133); `EntityDimensions.makeBoundingBox` centers width around X/Z and takes Y as the player position (EntityDimensions.java 21–25). Let the packet place the player at `(0.5, 10.776777671813965, 0.5)` with an absolute Y and no dismount. The body AABB is X/Z `[0.2,0.8]` and Y `[10.776777671813965, 12.57677762413025]`; after `deflate(0.001)`, its minY is `10.777777671813965`, and its scan footprint samples only X=0, Z=0 and Y=10,11,12.

Set the one water state in that sampled prism to flowing water amount/level 7 at block `(0,10,0)`, with no same-type water above and no other WATER state in the sampled prism. `FlowingFluid.getOwnHeight` is `amount / 9.0F` and `getHeight` returns that own height when no same fluid is above (FlowingFluid.java 429–440); amount 7 therefore yields float height `0.7777778F`. Java's `int + float` expression for cell Y plus that height is evaluated as float before widening to the scan's double local, giving top `10.777777671813965` (float bits `0x412c71c7`). The packet Y shown above is `double(top) - 0.001`; adding the scan's `0.001` deflation back gives exactly the same double top. Therefore the 1.19.3 predicate `top >= deflated.minY` accepts equality, sets contact true, and contributes exactly zero to the positive-depth accumulator. The lower edge is non-integral, so the partial-fluid cell is in the half-open integer scan; cells Y=11 and 12 are sampled but contain no deeper water.

Keep the vanilla Boat `isUnderWater` predicate true as an external boat state (Boat.java 871–873; its under-water scan/status is at 559–582). This packet/player-body arrangement imposes no requirement that the boat's status-producing water also occupy the player's deeper sampled cells. No collision-shape or block-state modification is needed for the player contact scan.

## Result and remaining gate

**Receipt route found.** The exact absolute packet Y is applied to a mounted local player without detaching, and that position is observed by the subsequent body-fluid scan before `positionRider` runs. The displayed flowing-water geometry gives an exact zero-depth equality using the actual standing rider dimensions and integer scan cells, while keeping the boat predicate independently true.

The ordinary reach gate remains that a normal authoritative server must send this particular absolute coordinate, without a dismount request, while the specified water placement and under-water Boat state coexist through the next passenger ride tick. Source proves packet application and scan ordering, but does not prove that a server naturally emits this exact correction or that ordinary Boat dynamics hold the external state; no motion simulation was performed. It also does not establish runtime occurrence.

