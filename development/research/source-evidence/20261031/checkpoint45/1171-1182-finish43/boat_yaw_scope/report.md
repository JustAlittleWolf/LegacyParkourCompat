# LocalPlayer boat boarding yaw response: 1.17.1 → 1.18.2

## Verdict

This is an eligible conditional local-player response difference. It directly writes the local player's yaw and head yaw when boarding a Boat after inherited `Entity.startRiding` succeeds. The write exists in 1.17.1, 1.18, and 1.18.1, and is removed in 1.18.2. Because yaw is local player state consumed by player look/movement, this is not the vehicle's position/physics computation and should not be grouped with excluded vehicle movement.

## Exact path and gate

`net/minecraft/client/player/LocalPlayer.java`, override `startRiding(Entity, boolean)`:

- 1.17.1 lines 155–171: first call `super.startRiding(entity, bl)`; return false immediately if it fails. On success, minecart handling only starts sounds. If `entity instanceof Boat`, write `this.yRotO = entity.getYRot()`, `this.setYRot(entity.getYRot())`, and `this.setYHeadRot(entity.getYRot())`; return true.
- 1.18 and 1.18.1 lines 152–168: same gate and three Boat yaw writes, with renamed decompiler locals only.
- 1.18.2 lines 153–164: same superclass-success gate and minecart sound handling; no Boat conditional and none of the three Boat yaw writes.

The inherited `Entity.startRiding(Entity, boolean)` admission method has the same relevant behavior in all four versions: rejects the current vehicle or a passenger cycle, then when forced or both `canRide` and `canAddPassenger` pass, stops an existing ride, sets standing pose, attaches as passenger and returns true. Otherwise it returns false. Thus under equal successful boarding and equal Boat yaw, the old endpoint directly sets the local player's current/previous/head yaw to Boat yaw while 1.18.2 retains the prior local yaw values at this override stage.

This is an orientation response, not a claim about boat-driven passenger positioning, vehicle physics, networking, later tick writes, or a trajectory outcome. The finding is gated on successful `LocalPlayer.startRiding` with a Boat. It is absent for minecarts and all non-Boat entities.

## Exact source identity

The canonical Mojmap source roots were read only. SHA-256:

| Source | 1.17.1 | 1.18 | 1.18.1 | 1.18.2 |
|---|---|---|---|---|
| `net/minecraft/client/player/LocalPlayer.java` | `c9a91cb6cb57806bc8d22e5bfe2d97daaf21d5d2a48f34a6e2c53164c61c5812` | `e38d0bbb6e5b9de2a698609b8491d0007406b3becd42ba9b95289f533788c996` | same as 1.18 | `99c2d18bcd23243afb8f95c5bafb21fb0be7ea04aacbb14fcf7be7ced2c9c095` |
| `net/minecraft/world/entity/Entity.java` | `ab28e1fba924771ec048140dfd293ee5a46a7dfe81f71a1a0b1aecc1927232de` | `69417675ae8e0a5bd88b7372baea1e73b1f5217154f39446c8ad5c92731437f1` | same as 1.18 | `2228fdaca5793171cbd94038306d571a6ada78ca96f5734efb4cada5b744c10a` |

No product, wiki or backlog material was read; no builds, tests, decompilation, launch, or source generation occurred.
