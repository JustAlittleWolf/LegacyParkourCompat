# Independent source coverage audit verdict — 1.9.1 → 1.9.2 (r2 supplement)

**Verdict: ACCEPT — supplemental coverage only.**

## Bound evidence

- Coverage supplement: `build/source-189-19-20261117/patches/1.9.1--1.9.2/coverage-r2.md`, SHA-256 `BC5E124E8522EA86A68589728C523E160E76FDCC53E8A3AD13CCAED193B57919` (recomputed).
- 1.9.1 exact-source provenance file: `decompiled_minecraft/1.9.1/ornithe-feather/.decompile-provenance.json`, SHA-256 `e9fb0da6511597e2de953a143f9570b82243e5b9504603686e557f06271da64a`.
- 1.9.2 exact-source provenance file: `decompiled_minecraft/1.9.2/ornithe-feather/.decompile-provenance.json`, SHA-256 `9928b467deb35c7e7f891b4fee1627a68feecdf233c2076909fd4834b0380510`.

## Audit result

Both exact source manifests contain 1,819 Java paths. I verified all listed file hashes against disk, checked that each manifest covers exactly the on-disk Java files, and compared the path sets. Both sets match; there are no missing, extra or hash-mismatched files. The complete source-hash delta is 21 files.

The packet change is accurately described in r2: `net/minecraft/network/PacketByteBuf.java:76–77` passes `readableBytes() / 4` to the shared length check in 1.9.1 and `readableBytes()` in 1.9.2. The reader rejects a count above that bound and then reads the array elements as VarInts. A source-wide search confirms the only no-argument call is `net/minecraft/network/packet/s2c/play/EntityPassengersS2CPacket.java:30`; that packet source is identical in both versions (SHA-256 `c49ec4c182b096f73033bd12ea25d727fe9d68481beb219ea4387457ebaccb80`).

The identical `ClientPlayNetworkHandler.java` source (SHA-256 `7f67bd2daa792cfc4aac1744d30f9cfdb28306ec55583d474ddc1a251561b1d6`) processes the passenger IDs through `startRiding(entity, true)`. The generic passenger loop includes the local player; the local-player comparison after attachment only adds the onboard overlay. `LocalClientPlayerEntity.tick` calls `super.tick()` and then takes its `isRiding()` packet branch (angles, input and vehicle move) instead of `sendMovementToServer()`. Thus the bound change can affect local passenger attachment and the mounted-player control/send path. It does not change the player's on-foot movement procedure. Passenger/vehicle physics and synchronization are excluded by project scope, so r2's explicit exclusion is appropriate and does not leave an in-scope movement dependency open.

The save/load disposition is also supported. In `PlayerEntity.writeCustomNbt`, the persisted `DataVersion` changes from 175 to 176. `DataFixes.createDataFixerUpper` changes its current version from 175 to 176, and `DataFixerUpper.run` changes its version threshold from 175 to 176. The player schema in both versions invokes list fixes only for `Inventory` and `EnderItems`. Player loading invokes `DataFixerUpper.run(References.PLAYER, ...)` in the player storage paths; no player movement fields are transformed by this player schema. The added edge is an item-list migration path, not a movement-state change.

These checks close the prior audit's specific omissions: the packet-bound delta, its sole packet and client-handler path, and the 175→176 save/load/data-fixer edge are now explicitly covered. The packet-bound direction in r2 matches the exact source.

## Residual gates

- This verdict accepts only the r2 supplement. It does **not** accept the pair's overall no-finding conclusion or freeze full-pair coverage. The complete tick/inventory and provider/dependency inventory still needs its separate independent blind audit under the source-discovery procedure.
- This audit does not establish an intermediate patch introduction boundary, implementation status or runtime parity.
