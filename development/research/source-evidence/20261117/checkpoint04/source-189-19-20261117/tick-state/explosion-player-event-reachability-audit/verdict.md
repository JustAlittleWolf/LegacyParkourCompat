# Bounded source audit: 1.8.9 → 1.9 explosion player-event reachability

## Verdict

**ACCEPT**, for the bounded ordinary TNT event-chain and conditional Creative/flying-off player-response reachability claim only.

## Audited input identity

`build/source-189-19-20261117/tick-state/explosion-player-event-reachability.md`, SHA-256 `D966324600A350020F56236B91D6E42ABA372710BDB5898BDFFB25C9B95A2BCD`.

The 1.8.9 and 1.9 source-file hashes listed by the report were recomputed and match its cited identities for `TntBlock`, `PrimedTntEntity`, `Entity`, `EntityFilter`, `World`, `ServerWorld`, `Explosion`, `ExplosionS2CPacket`, and `ClientPlayNetworkHandler`. The cited producer/crosswalk reports and their prior audit verdict identities also match their recorded SHA-256 values. This audit is bound to the report revision above.

## Audit result

The cited source path supports the ordinary event chain. `TntBlock.use` accepts flint-and-steel or fire-charge use, sets `EXPLODE` for ignition, and removes the block; `ignite` creates/adds primed TNT only on the logical server. The entity starts with fuse 80 and power 4, decrements its fuse, and at expiry removes itself and calls `world.explode`. The call is virtual and the server implementation constructs the explosion and runs `damageEntities` before sending the packet.

The query and response gates are correctly traced. `Explosion.damageEntities` queries the `power * 2` bounding box (radius 8 for TNT). The default `World.getEntities` predicate is `NOT_SPECTATOR`, which permits Creative players; the query retrieves entities from chunks intersecting its bounds. The player proceeds when not explosion-immune, within the distance ratio (`z <= 1`) and with a nonzero eye-direction length. Base `Entity.isImmuneToExplosions` returns false, with no PlayerEntity override identified in the bound crosswalk. The report correctly observes that `takeDamage(...)` is called but its return value does not gate the subsequent vector insertion. Damage success and amount are therefore not premises of the response claim.

For the stated loaded, unobstructed off-center configuration, the formulas imply `z < 1`, nonzero direction length, positive block density, and a positive x component of the stored 1.9 player vector. The accepted crosswalk establishes that applied Creative with `flying=false` is a valid server state and is synchronized to the local client; the entity query does not exclude Creative. In 1.8.9 the invulnerability gate suppresses the vector, while 1.9's spectator/creative/flying predicate admits it.

`ServerWorld.explode` sends the packet to each server player whose squared distance is `< 4096`; the packet copies a non-null player vector into float components and leaves absent-vector components zero. Both client handlers add the three received components to local player velocity. The report's radius and client response description match the exact source.

## Scope limits

This ACCEPT establishes static conditional source reachability through an ordinary TNT use path with the synchronized Creative/non-flying state and the stated in-range, unobstructed geometry. It does not establish a particular map placement, map-use mechanics, a resulting trajectory, damage or fall damage, or pair-wide source coverage/freeze. It makes no runtime claim. The unconditional `takeDamage` call is specifically not a prerequisite for vector admission.