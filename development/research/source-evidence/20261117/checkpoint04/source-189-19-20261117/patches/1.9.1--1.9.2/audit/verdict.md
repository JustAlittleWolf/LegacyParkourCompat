# Independent source coverage audit verdict — 1.9.1 → 1.9.2

**Verdict: REQUEST CHANGES**

## Bound evidence

- Coverage report: `build/source-189-19-20261117/patches/1.9.1--1.9.2/coverage.md`, SHA-256 `645AC96B881BFA148A9B17BB6708A2C0FA506F150BB009E47F903DBADBEE05C5`.
- Source namespace: `ornithe-feather`.
- 1.9.1 provenance/source-inventory identity: SHA-256 `e9fb0da6511597e2de953a143f9570b82243e5b9504603686e557f06271da64a`; Feather Gen2 artifact `1.9.1+build.2`, SHA-256 `6d5768353bf2ff2756d38537312548a8ac91d570f130d23c23e9412d2edd97b3`.
- 1.9.2 provenance/source-inventory identity: SHA-256 `9928b467deb35c7e7f891b4fee1627a68feecdf233c2076909fd4834b0380510`; Feather Gen2 artifact `1.9.2+build.2`, SHA-256 `e2b98ececbff8fa0bd0c89283bbb32b7f9f9dcd4b89fa3f137666fa3ac0b6859`.

## Audit result

I independently recomputed both provenance-file hashes, checked each manifest's 1,819 Java paths against disk, and verified every listed source hash. Both path sets are identical; neither side has missing, extra, or mismatched files. Their complete content-hash delta is 21 files, with no path additions or removals.

The traced client path is `Minecraft.tick` → `World.tickEntities` → `LocalClientPlayerEntity.tick`/`mobTick` → `Input.tick` and local input transfer → `PlayerEntity.mobTick` → `LivingEntity.mobTick` jump/travel branches → `moveRelative` → `Entity.move`. The collision/state dependencies include world collision collection and `BlockState`/`Block` shape dispatch, box intersection/order, liquid checks and drag, block contact callbacks, and entity/player pushes. The compared tick, input, living/entity, world, box, block-state/provider, and callback sources are byte-identical across this pair. `PlayerEntity.java` differs only in the saved `DataVersion` literal (175→176); that line is not on the tick path. The other changed client files update version display/handshake protocol values. The changed data-fixer version gate can run the existing player inventory item schemas when loading a 1.9.1-version save; the schemas inspected translate item block-entity/spawn-egg tags and do not alter movement predicates or player motion.

One changed source is not adequately disposed in `coverage.md`: `net/minecraft/network/PacketByteBuf.java`. `readIntArray()` changes its maximum-length argument from `readableBytes()` to `readableBytes() / 4`, although the array entries are VarInts. The only source call site is the unchanged `EntityPassengersS2CPacket.read`; its client handler applies the result with `startRiding`, including when the local player is among the passengers. Thus the changed parser can affect passenger attachment/state for a local player (for example, the bound may reject a short valid VarInt array). This lies on the vehicle/passenger path, whose physics are excluded by project scope, so I do **not** propose a movement finding from it. However, it is a changed network dependency with a reachable player-state consequence and is absent from the report's delta disposition; the report's description of the remaining changes as network-handshake changes is incomplete.

## Required revision and residual gates

Revise the coverage report to identify the `PacketByteBuf.readIntArray` delta, its sole `EntityPassengersS2CPacket` use and the client passenger handler, then disposition it explicitly as excluded vehicle/passenger synchronization (or reopen it if the pair's movement scope includes mounted-player motion). Also record the checked 175→176 save-load/data-fixer gate in the disposition, rather than treating the `PlayerEntity` NBT literal as the only relevant save-state edge. Keep the no-finding conclusion provisional until that coverage correction is reviewed. No finding revision is accepted by this audit.

This verdict is limited to the immutable report and the two exact source inputs above. It does not establish intermediate-version boundaries, implementation status, or runtime parity.
