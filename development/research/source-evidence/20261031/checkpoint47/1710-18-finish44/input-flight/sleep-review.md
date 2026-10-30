# Independent review: sleep-response placement

## Verdict

**REJECT AS WRITTEN.** The box-placement difference is source-confirmed for ordinary server bed positions `0 ≤ bedY ≤ 127` and in-border X/Z coordinates, but the finding states the same bed position and an unqualified “newer box is lower” result. The 1.7.10 packet serializes Y as a signed byte, so successful sleep responses for bed positions `128 ≤ bedY ≤ 255` reach a different client placement branch with a different Y value. This needs correction before this finding can be accepted. The bed placement math itself is valid with the range and precision correction below.

## Exact source identity

Read-only trees: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.7.10\ornithe-feather` and `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.8\ornithe-feather`. Mapping family on both sides: Ornithe Feather build.2.

- 1.7.10 `.decompile-provenance.json` SHA-256: `11bada441ebe07661402d1c40e1c490b78b279ecaa084f9a4dc3ae2dd54caf6f`; raw client SHA-256: `a4fc2284657544e0f4bcc964f927c2fda3e3a205178ed1d5d58883aaf9780cce`; Feather artifact SHA-256: `1a22781aa3bac248e25c619842ec2ec5c126d015436d528d411a9e85a6f491a7`.
- 1.8 `.decompile-provenance.json` SHA-256: `52c8312c34a1a491d4a200c84e90f5d8d95c7e5caa9571f864d8e70f9737db05`; raw client SHA-256: `ccccd0a92f76fbb24279691daad9b67f85d80471a37e59e0da76d7754982dfe8`; Feather artifact SHA-256: `3c020d1588b4aab403f5df077ca91b6e3776da2823d3b55202f4616f0d5ae5fe`.

The inspected source members' SHA-256 values match their provenance manifests:

- 1.7.10: `ServerPlayerEntity.java` `63133206f3005973615df175e546d97032718d6ca20eb934c8535c9fa17268f2`; `ClientPlayNetworkHandler.java` `bae3baa7bc005b339eac47d800cf391c3e35b0ea0e9162af344793d7d104e4d0`; `PlayerSleepS2CPacket.java` `da8a5443e9c2f3cc2788014ed090c0657cb0bc348a7ffed53043bc592b31a8df`; `PacketByteBuf.java` `b84babc39069bbefdf1922d346c4b9292702aa7c27335e0d7ea748e6c6b6dee1`; `PlayerEntity.java` `350901699e449537372e1c4af6a79ae256de39638cdad049dfc7800b6607791c`; `Entity.java` `af4ef7b11bc709d96bd3eea1260a61d38a1a04996bdf87f2a77c459ddf070a69`; `World.java` `2d648edfe7fdc953825a1df94709e889ea21354494d85762bca49b4a109d01f9`; `ClientWorld.java` `e4e3814806ad29fd94f1a25888c199f97900cec68237faff7194faa5ede4652b`; `BedBlock.java` `90def62a050c87c5172b6a925d542e731b533a8c9393bb36a2e5ca77c82dc315`; `HorizontalFacingBlock.java` `e328ffb7ff9c16917c9cdcd3b8e5009811258c0e334a786c2344be80ed5a1bb4`.
- 1.8: `ServerPlayerEntity.java` `407a88a1fd11219ddda99abbe38019ce01c1f53f3089702709f84ccbddee67d6`; `ClientPlayNetworkHandler.java` `1cca0ded9f1112d407b149931c1f067a104d2beed74f1e7070200e8c035a102d`; `PlayerSleepS2CPacket.java` `19980bb6403941c405e59bdcc12dbe2c02585436b58d3020a450217d382a7350`; `PacketByteBuf.java` `5775b29749af414798703fa307e126ae2af9b36509e894a81bdcf2b40f0e586f`; `BlockPos.java` `c46b85a9129d56f50b52dd3a8255d8da14f16c4c38164deb312cb03ff7eff11c`; `PlayerEntity.java` `13fa1367b71035efbfccc593de57838268b10a8ceebb0a76ad3d819b44cb0ca`; `Entity.java` `2be081abe1720f89d7ab90f782b8411e71bc5b8a6b53966b49a2380e3c47ffd9`; `World.java` `afbc701ef8b66020bdbb2831fd80683705d20237c68fbf23a4001c020876fc6d`; `ClientWorld.java` `6886f5682f606427571d559ce15eb4b758e3f3dacac8f1db37da6dbb354086b7`; `BedBlock.java` `f11cdfc42b9a3b22ea1f400693e473f2de96a96eae4b2ec79fd1322d21a3ad0c`; `Direction.java` `37fa05dd3915d54a959e6f6c0df40cd4137a02f299fb9096f1b40cd3a2c378e1`.

## Trace and claim evidence

### Successful server response reaches client placement

- 1.7.10 `ServerPlayerEntity.trySleep(int,int,int)` calls `super.trySleep`, then constructs and sends `PlayerSleepS2CPacket` only when the status is `OK` (`ServerPlayerEntity.java:444–453`). The packet stores X/Y/Z and the player network ID (`PlayerSleepS2CPacket.java:18–23`). The client handler resolves the packet's player from its world and calls `trySleep(x,y,z)` (`ClientPlayNetworkHandler.java:619–622`).
- 1.8 `ServerPlayerEntity.trySleep(BlockPos)` likewise sends only after `OK` (`ServerPlayerEntity.java:495–504`). The packet stores player ID and `BlockPos` (`PlayerSleepS2CPacket.java:17–20`). The client handler ensures the packet is on the client thread, resolves its player, and calls `trySleep(pos)` (`ClientPlayNetworkHandler.java:683–687`).
- Both client `PlayerEntity.trySleep` methods skip server-side sleep eligibility checks on the client: the guard is `!world.isMultiplayer` in 1.7.10 (`PlayerEntity.java:1014–1038`) and `!world.isClient` in 1.8 (`PlayerEntity.java:1071–1099`). The 1.7.10 login handler marks its newly created client world multiplayer (`ClientPlayNetworkHandler.java:226–234`; respawn path also writes it at `:731–738`); 1.8 `ClientWorld` passes `true` to the `World` constructor (`ClientWorld.java:47–50`, `World.java:104–110`). So the client packet path skips those checks and executes placement.

### Placement and box-minY math

- Both placement methods detach from a vehicle, set size to `0.2F × 0.2F`, then, after choosing a placement, mark sleeping, reset sleep time, store the sleep position, and zero all velocity components (`1.7.10 PlayerEntity.java:1040–1074`; `1.8 PlayerEntity.java:1101–1133`). 1.7.10 assigns `eyeHeight = 0.2F` before placement (`:1044–1046`). In 1.8, `getEyeHeight()` returns `0.2F` while sleeping (`PlayerEntity.java:1634–1645`), though `Entity.setPosition` no longer uses eye height.
- 1.7.10 requests `y + 0.9375F`; `Entity.setPosition` makes box minY `requestedY - eyeHeight + eyeHeightSneakOffset` (`PlayerEntity.java:1065–1068`; `Entity.java:215–223`). 1.8 requests `pos.getY() + 0.6875F`; `Entity.setPosition` makes box minY equal requested Y (`PlayerEntity.java:1124–1128`; `Entity.java:225–232`).
- For a bed Y that arrives unchanged and is within ordinary world bounds, let `o` be the stored 1.7.10 `eyeHeightSneakOffset`. In exact double arithmetic, the old minY is `double(y + 0.9375F) - double(0.2F) + double(o)`; the new minY is `double(y + 0.6875F)`. For integer `y` in `[0,255]`, the fixed fractional parts are representable at these magnitudes, so old minY is above new by `0.04999999701976776 + double(o)`. Describing this as approximately `0.05 + o` is fine; spelling it as exact `0.05F + o` is not.

### Packet coordinate correction required

- 1.7.10 packet serialization writes Y with `writeByte(this.y)` and reads it with `readByte()` into an `int` (`PlayerSleepS2CPacket.java:26–31,34–39`; `PacketByteBuf.java:486–489,606–609`). `readByte()` is signed. A server bed Y from 128 through 255 therefore reaches client `trySleep` as `bedY - 256`.
- In 1.7.10, the client `isChunkLoaded(x,y,z)` gate rejects every negative Y (`World.java:236–238`). Consequently that response takes the fallback center placement, at X/Z `+0.5F` and Y `bedY - 256 + 0.9375F`, rather than reading the actual loaded bed's facing.
- 1.8 writes/reads a packed `BlockPos` long (`PlayerSleepS2CPacket.java:23–31`; `PacketByteBuf.java:57–63`). `BlockPos` uses a 12-bit Y field and signed extraction (`BlockPos.java:8–14,120–128`), preserving valid Y 128–255. Its client placement can take the loaded-bed branch at the actual position.
- Thus the finding's “newer box is lower” comparison only holds for `0 ≤ bedY ≤ 127` (and equivalent in-border positions). For a loaded successful bed at `128 ≤ bedY ≤ 255`, 1.7.10 places from the negative decoded Y through the center fallback, while 1.8 places from the original Y. That is a separately source-proven response difference the unqualified candidate omits; it can be documented as a separate packet-coordinate operation or explicitly included after the scope is revised.

### Horizontal placement and gate comparison

For normal positions inside the world border, loaded-branch horizontal offsets match. In 1.7.10 bed facing is `metadata & 3` (`HorizontalFacingBlock.java:10–12`): 0 sets Z to `0.9F`, 1 sets X to `0.1F`, 2 sets Z to `0.1F`, and 3 sets X to `0.9F` (`PlayerEntity.java:1046–1066`). 1.8 decodes bed metadata through `Direction.byIdHorizontal(metadata)` (`BedBlock.java:199–204`); `Direction` maps horizontal IDs 0/1/2/3 to SOUTH/WEST/NORTH/EAST (`Direction.java:11–14`) and `PlayerEntity.trySleep` applies the same corresponding offsets (`:1106–1127`). Both sleeping-camera offset helpers have the same facing-to-offset mapping (`1.7.10 PlayerEntity.java:1082–1098`; `1.8 PlayerEntity.java:1141–1157`). When the bed is not considered loaded, both use centered X/Z `+0.5F`.

The loaded-chunk gates are not literally identical: 1.7.10 checks Y in `[0,255]` then chunk coordinates (`World.java:236–238`); 1.8 first checks full world containment, including X/Z in `[-30000000,29999999]` and Y in `[0,255]`, then chunk presence (`World.java:159–174`). This does not alter ordinary in-border placement but should be included in any claim of unconditional gate sameness. At high Y the signed-byte correction above also selects different loaded/fallback branches.

## Exact correction to the candidate

At minimum, restrict the lower-box statement to successful server sleep responses with bed Y `0–127` and in-border X/Z, and state the exact offset as `oldMinY - newMinY = 0.04999999701976776 + double(o)` for the inspected float/double operations (or mark it as an approximation). Separately record that 1.7.10's signed-byte sleep packet misplaces beds at Y `128–255` relative to 1.8's `BlockPos` packet; that operation is source-proven and movement-relevant. I did not edit the candidate.

## Remaining scope

This review covers the response packet, local placement routine, placement bounds/facing, and the `Entity.setPosition` box write. It does not assess other sleep/wake tick paths, subsequent player movement/collision effects, world/provider completeness, or full-pair coverage/freeze. No runtime testing was performed.
