# Block-break acknowledgment predicate closure — Mojmap 1.15.2 → 1.16.5

## Finding

This callback has a concrete conditional local-player position-and-rotation delta for the same raw acknowledgment fields and the same current client block-state reference. With a pending entry for the exact `(BlockPos, Action)`, `allGood == false`, and `clientLevel.getBlockState(pos) == acknowledgedState` by reference, 1.15.2 enters its correction branch and restores the captured `PosAndRot`; 1.16.5's final `blockState2 != blockState` conjunct is false, so it does not change the block state or call `absMoveTo`. Choose a captured position and view angles different from the player's current values to expose the direct state difference. This conditional callback result does not depend on acknowledgment frequency, a server producer, or producer timing.

## Exact route and operation

Both `ClientPacketListener.handleBlockBreakAck` receivers first call `PacketUtils.ensureRunningOnSameThread`, then forward the listener's level and the packet position, state, action and `allGood` value to `minecraft.gameMode.handleBlockBreakAck`: 1.15.2 `ClientPacketListener.java:2354–2365`; 1.16.5 `:2380–2391`. `Minecraft.player` is statically `LocalPlayer` in both versions, and neither `LocalPlayer` declares `absMoveTo`.

Both `sendBlockAction` methods save an entry keyed by `(BlockPos, Action)`, and the callback removes that exact entry before evaluating the correction predicate. The 1.15.2 saved value is `PosAndRot(position, xRot, yRot)`; 1.16.5 saves only `Vec3 position`. Both maps also evict old entries at size 50, so the response requires the matching entry still to be present.

In 1.15.2, `MultiPlayerGameMode.handleBlockBreakAck` lines 424–434 enters the state-reconciliation branch when `saved == null || !allGood || (action != START_DESTROY_BLOCK && currentState != acknowledgedState)`. For any present saved value, it calls five-argument `minecraft.player.absMoveTo(...)` inside that branch. With equal current and acknowledged state and `allGood == false`, the branch is true for any action, so the old client calls `setKnownState` and restores saved position plus saved yaw and pitch.

In 1.16.5, lines 434–444 evaluate `(saved == null || !allGood || (action != START_DESTROY_BLOCK && different)) && different`, where `different` is the pre-correction reference comparison `currentState != acknowledgedState`. With the equal-state witness, `different` is false and the entire branch is skipped regardless of `allGood` or action. Thus the newer method neither calls `setKnownState` nor restores position. More generally, with a saved value the new state-correction gate reduces to `different && (!allGood || action != START_DESTROY_BLOCK)`; after it passes, player restoration further requires `clientLevel == player.level` and `player.isColliding(pos, acknowledgedState)`. The new response is position-only.

The old inherited `Entity.absMoveTo(x,y,z,yaw,pitch)` clamps X/Z, updates previous coordinates, calls `setPos` (which updates the position and bounding box), then writes current and previous yaw/pitch (`Entity.java:1094–1105`; `setPos`: `:315–320`). The new inherited three-coordinate overload clamps X/Z, updates previous coordinates and calls `setPos` (`Entity.java:1114–1121`; `setPos`: `:331–334`). Neither path writes delta movement. In the equal-state witness, 1.15.2 therefore changes the local player's position/bounding box and rotation to the saved values; 1.16.5 leaves those values as they were.

## Collision-query witness applicability

The proposed `CollisionSpliterator`/canonical-`Shapes.block()` witness does **not** apply to this callback. That witness is the 1.16.5 optimized raw-AABB candidate check in `CollisionSpliterator` used by `CollisionGetter.getBlockCollisions` during the `Entity.move` swept-box route. `handleBlockBreakAck` does not call that collector. Its new-only `Entity.isColliding(pos, state)` gate directly obtains the passed state's collision shape with `CollisionContext.of(player)`, translates it to block coordinates, and tests it against the player's current bounding box through `Shapes.joinIsNotEmpty(voxelShape2, Shapes.create(player.getBoundingBox()), AND)` (`Entity.java:214–218`). The supplied swept-query witness is not this callback's current-box query. Moreover, the equal-state movement witness above exits the new outer gate before `isColliding` runs. The direct response difference is established by the changed correction predicate without importing the separate collision-collection witness.

## Source identity

Read-only source roots: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.15.2\mojmap` and `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.16.5\mojmap`. Both ready markers report the exact version, `mojmap`, and `ready`; each marker's source-manifest SHA-256 matches a recomputation of the manifest. The provenance records identify the batch `1.15.2,1.16.5,1.17.1,1.18.2`, Vineflower 1.12.0 and Java 25.0.3. Relevant file SHA-256 values, checked against the manifests:

| File | 1.15.2 | 1.16.5 |
|---|---|---|
| `MultiPlayerGameMode.java` | `d69f96ab32445bdc5a2cfa1f7ca718d9213d107b7690a15799b660cd3548a4b4` | `80d1b28891b33de967cbc26e13acec097fb94091bbc226f92ad5ba2750015b66` |
| `ClientPacketListener.java` | `d806d286af4b93d7c61c3c884f344a5870ce1871fb0a811506031adf78fafc30` | `a1c3ea65f40a527d92b1e4d637cad4a8293fdddd1a9d530d778bf39cc2d8670e` |
| `Entity.java` | `191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e` | `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666` |
| `LocalPlayer.java` | `3a9019bd7b860e251c23fd8d0cd70b7f5b38566d34470c4e29b1014ef689ccbd` | `6011569e766bb1568609147be9aa14e9c08c51948e3d3a60fd066e848f6a8c2b` |
| `Minecraft.java` | `1d0cac30a36e2ff6cd5665bc6a08c29c3729317b66a892be85e6cff1016dbb11` | `3fc48eb93d2dc001496c11b66071db939cdaa3f1ff7c4e5b2b391e25a715facc` |
| `Shapes.java` | `81bf70d2c8a1d1336df72beedb30b0c9504f14decc9a06814a794aabf38203c2` | `ec218dca68cad3525364967aadfbb8c0bead0cc44aa3ee2beba31e00e55ae2d4` |
| `CollisionGetter.java` | `63daec0623c816d53f5dbdde407a27222fa21e62680c1096b63cf9082b1c25fd` | `b507d6be11e5985a62cfeb249a99dcb5f8edaf346f12cb2487797d9e01763eac` |
| `CollisionSpliterator.java` | absent | `19ab959c469b8edb5c371ff737a59a61b8a80bf397eff1db280064342094d297` |

Source-manifest SHA-256: 1.15.2 `cb7fd93f8730d8b2813857744f8572060535c7de44fcee86222acd1ceb6f43e7`; 1.16.5 `9499f2611d0e6dde37cb635f1a28a591416382d6bc188b37d5f99e2b2a20823b`.

No product/wiki/backlog edits, tests, build, runtime, or source generation were performed. This closes only the stated callback member and its direct player response; it does not establish the intermediate patch boundary, pair freeze, implementation, or runtime parity.
