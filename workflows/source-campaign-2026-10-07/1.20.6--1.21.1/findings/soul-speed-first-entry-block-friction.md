# soul-speed-first-entry-block-friction: First local entry step uses the block factor before Soul Speed attribute sync

- Older version A: 1.20.6
- Newer version B: 1.21.1
- Mechanic / coverage slice IDs: L2, L4, E2, X1
- Classification: changed behavior
- Confidence: source-confirmed for the first local entry movement under the stated zero-attribute precondition; independent snapshot review pending
- Applicability: historical player behavior
- First changed release: unknown within (1.20.6, 1.21.1]
- Runtime validation: not performed

## Paired evidence

- A manifest artifact: `ready/1.20.6/mojmap`; `LivingEntity#getBlockSpeedFactor()` and `onSoulSpeedBlock()`, lines 489-496, SHA-256 `c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2`, evaluate the current Soul Speed block and equipped enchantment level locally and return `1.0F` when eligible. `Player#onSoulSpeedBlock()` lines 975-977 and `Player#getBlockSpeedFactor()` lines 1978-1980, SHA-256 `785d93ccc94e1f912e545b2b0c355edeb352b44ee8e83a69364daec35266dbe2`, retain the flight/fall-flying bypass. `Entity#move()` lines 705-706, SHA-256 `71cd6b9f6c002684154dce11d3745e8714d82f13c8e1b3aa56743930131c18f3`, applies the block-speed multiplier after movement.
- B consumer/default: `LivingEntity#getBlockSpeedFactor()` lines 485-488, SHA-256 `324a3eee8496caab57cfaf5101ef576f1ae3c60c40e3857e96f35f3af9a3a0d8`, interpolates from `super.getBlockSpeedFactor()` to `1.0F` using `MOVEMENT_EFFICIENCY`; `Attributes.java` lines 71-73, SHA-256 `a9a19f556bb77fc218b5b2f4831eb6b285f4398c24fa2a3519009ca8dad2db7c`, declares the attribute syncable with default `0.0` and range `0..1`. `Player#getBlockSpeedFactor()` lines 1973-1975, SHA-256 `ed32b88c3c7c8418b83db41823520f2b6b0b49a98610306ef26e9681dc925c71`, retains the flight/fall-flying bypass. `Entity#move()` lines 706-707, SHA-256 `b81905c7879e2cc5c5063a41d865c4164ad919f156306705be791d1017b99850`, applies the corresponding multiplier after movement.
- B server producer: `Enchantments.java`, Soul Speed registration lines 506-522, SHA-256 `7848a2cd677aa0700434f85de37d8a0c47597741d2d1f26c8dc270f73d65b266`, attaches a location-changed `MOVEMENT_EFFICIENCY=1.0F` modifier when the entity is affected by a Soul Speed block. `EnchantmentAttributeEffect.java` lines 39-57, SHA-256 `c1e2edd5c870e1ecba364cbee07ae2f12720e47681dd1dbf16ac081e392f4270`, adds/removes the transient modifier. `LivingEntity#baseTick()` lines 433-439 and `onChangedBlock(ServerLevel,BlockPos)` lines 514-516, same B `LivingEntity.java` hash above, run that producer from a server-level block-position change; the callback cannot run on the client level. The landing callback in `checkFallDamage()` lines 327-355 is also guarded by `ServerLevel`.
- Local move precedes its position packet: B `LocalPlayer#tick()` lines 191-205, SHA-256 `c555e68ac3c63ab9b4f9a9e31933e263b96350a2bc599d11a0de5928bc24b583`, calls `super.tick()` before `sendPosition()`. `sendPosition()` lines 222-255 sends a `ServerboundMovePlayerPacket` for ordinary non-passenger movement after the local tick's movement path has run.
- B attribute return path: `ChunkMap#tick()` lines 1069-1093, SHA-256 `079b7c5e3d5bcdfff8b18a69513c93aaca56dfd739bde642c2ece79d127c5088`, invokes `ServerEntity#sendChanges()`. `ServerEntity#sendDirtyEntityData()` lines 314-335, SHA-256 `0994216505c8df7cc67a7a068ae8e71d1f023dbef5dc1dc31360e3e5bdc2ce14`, sends dirty syncable attributes; its `broadcastAndSend()` lines 332-335 also sends the packet through the connection when the entity is a `ServerPlayer`, so the local player receives its own update. `ClientPacketListener#handleUpdateAttributes()` lines 2062-2076, SHA-256 `822e00d89e8def4cf7a8c22fdfefba3b9dc2135a7630e4ebe561a9d547d7f79d`, applies the packet values to the client entity.
- Concrete tagged block: `Blocks.java` lines 2282-2298 sets Soul Sand `speedFactor(0.4F)` in both exact versions (A SHA-256 `684579bbcbe48b1f0984090a4ca044c0b2cee6d08c2dc1e449e2259389c5b129`; B SHA-256 `538ac50c164484ca4c2367a2a6c07cf2faedcac0c4d5117d8817d36c2d1bde6d`). The Soul Speed blocks tag is identical in both jars: `data/minecraft/tags/blocks/soul_speed_blocks.json`, SHA-256 `8ba7eac6f7c74d25601ef4455b71e3947fdb8e51b8fafd55dddee48397b144b6`.

## Source-level difference

For a non-flying, non-fall-flying local player with Soul Speed boots, start from a non-Soul-Speed block with the client `MOVEMENT_EFFICIENCY` at its normal `0.0` baseline, then make an ordinary walking step onto Soul Sand. In A, the local move reaches `Entity#move()` and the current-block query directly returns `1.0F`. In B, the local `super.tick()` performs the move and reads the still-current `0.0` attribute before `LocalPlayer#tick()` sends the new position to the server. With Soul Sand's base factor `0.4F`, B's interpolation returns `0.4F` for that entry move. The server can only observe the new position after that client movement packet and runs the location-changed producer on its server-level entity; the resulting dirty attribute is synchronized afterward through the tracked-entity send path, including to the owning `ServerPlayer` connection.

## Reachability and dependencies

Client local tick -> `LivingEntity#aiStep` -> `Player#travel` -> `LivingEntity#travel` -> `Entity#move` -> block-speed factor -> horizontal delta-movement multiplier. The ordinary non-passenger `LocalPlayer#tick` sends position only after `super.tick()` completes. On the server, the changed block position activates the Soul Speed location effect, and the dirty syncable attribute is later sent back to the same player and applied by `ClientPacketListener`. The affected local entry move therefore has a concrete client-side precondition where A and B read different values; later movement can converge after the server update arrives. The tag, player flight exclusions, Soul Sand property, modifier registration/application, and packet consumer are all traced above.

## Consequence and uncertainty

The source proves a one-entry-move difference in the horizontal multiplier under the stated reachable precondition: A uses `1.0F`; B uses `0.4F` while its local attribute remains at baseline. This predicts a lower horizontal delta movement after the B move. No position trace or gameplay result is claimed. Packet latency can affect later local ticks; it does not precede the local movement that first reports the newly entered block. The endpoint pair does not establish the first release containing the change.

## Handoff

Independent source snapshot for review: verify the first-entry ordering, exact local attribute precondition, and server-to-own-player packet path in the cited ranges. Pair discovery remains active/partial; this bounded finding does not close L2/L4, other modifier producers, or the full-pair audit. Implementation handoff is pending reviewer acceptance.
