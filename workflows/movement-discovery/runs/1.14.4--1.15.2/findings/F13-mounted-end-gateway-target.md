# F13: 1.15.2 End Gateways target a mounted player's root vehicle

- Older version A: 1.14.4
- Newer version B: 1.15.2
- Mechanic / coverage slice IDs: stages 4 and 7; End Gateway teleport target and passenger position propagation
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: a player is riding another entity and is the first entity returned by the End Gateway's intersecting-entity query
- First changed release: unknown within (1.14.4, 1.15.2]
- Runtime validation: not performed

## Paired evidence

- A `TheEndGatewayBlockEntity.tick` passes the first entity in the gateway AABB directly to `teleportEntity`. B calls `getRootVehicle()` on that same first entity before passing it. A source hash: `EC4279C2B216F60F36ADF382F404689C23F836C6FB9CE1919A34A7D3B832A3AF`; B source hash: `4BE661E53F4EE7E06C2754CEF1D4F5837766F776B0AC51AF4ADB3AC855668D10`.
- Both `teleportEntity` methods select the exit position, call `teleportToWithTicket` with the same centered block coordinates, and trigger the same cooldown. The client/server guard differs in spelling (`!level.isClientSide` versus `level instanceof ServerLevel`), but these are the corresponding server-only conditions.
- A base `Entity.teleportTo` marks and moves its target, then updates that entity's chunk position. B moves the target, then walks `getSelfAndPassengers()`, updating chunk positions and `teleported` flags and calling `repositionDirectPassengers(Entity::forceMove)` for each. A source hash: `31C6BE42D165102D3E6DC4295FDFEF6E8971FC3AEA13F938A5B3A33B91D524A7`; B source hash: `191B3AD3E7348C9BAC1E703FFF896706D23A751BF15162AACF676F5F97C0A10E`.
- `ServerPlayer.teleportTo(double, double, double)` sends the player teleport through its connection in both versions. A source hash: `0B07BB15E2E3001F03DAB2DC943CBCA4814ADA36C82B6D83F933BE4FF01C4B92`; B source hash: `719416651F82FF78E9990A77E5BEA0998938BF8070898865C37AE2DF6E3B22DA`.

## Source-level difference

When the queried first entity is a player riding another entity, A targets that player for the gateway teleport. B targets the player's root vehicle. B's common entity teleport path then updates the root/passenger chunk and teleport state and repositions its direct passengers, so the mounted player is carried through the passenger chain as part of the root vehicle teleport. This is a changed player position route; it does not establish any difference in independent vehicle movement.

## Reachability and dependencies

End Gateway server tick -> first intersecting entity -> (B only) root-vehicle selection -> ticketed teleport -> `Entity.teleportTo` / `ServerPlayer.teleportTo` -> passenger position propagation.

## Consequence and uncertainty

The target selection and passenger propagation follow from the paired source paths. No gameplay result was observed. The exact first release is unknown within the endpoint interval. The finding is limited to a mounted player selected by the gateway's first-entity query; other tracked-entity and external position/velocity writers remain under audit.

## Handoff

The ordinary non-mounted Ender Pearl and Chorus Fruit player teleport candidates are unchanged in their paired methods. F13 closes the mounted End Gateway route only; it does not close the remaining external-input inventory.
