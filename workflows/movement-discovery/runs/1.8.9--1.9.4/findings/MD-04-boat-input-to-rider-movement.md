# MD-04: Boat controls change the local rider's movement

- Older version A: 1.8.9
- Newer version B: 1.9.4
- Mechanic / coverage slice IDs: stages 1.4 directional input; 7.2 mounted player movement
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: player interaction with another entity
- First changed release: unknown within (1.8.9, 1.9.4]
- Runtime validation: not performed

## Paired evidence

- A input path: `../../../../../decompiled_minecraft/1.8.9/ornithe-feather/net/minecraft/client/entity/living/player/Input.java` declares only movement floats and jumping/sneaking flags; SHA-256 `AABF9CFED6156121C67C9F16002CCC02F14FA6213D5717732DCF9A1D26368B74`. `KeyboardInput.tick()` updates sideways/forward floats and scales those floats while sneaking; SHA-256 `A5E5B2033322F8867CD845E4095CEA7A82888F6382CBFAFEFC559CF9BA3A76DD`. `LocalClientPlayerEntity.serverTickAi()` lines 470-485 copies the float magnitudes to `sidewaysSpeed`/`forwardSpeed` for the camera; class SHA-256 `1762B116E6B06D682B7DAAA0FC8CCE39B0FF455B3DB8CF79DAB03AC74F6C4053`.
- A boat input consumer: `../../../../../decompiled_minecraft/1.8.9/ornithe-feather/net/minecraft/entity/vehicle/BoatEntity.java`, `BoatEntity.tick()` lines 259-264 reads `rider.yaw`, `LivingEntity.sidewaysSpeed`, and `forwardSpeed`; SHA-256 `77EF72C28024B754FF76F2C67E14DD61832394BAC2BD7B62B29708560E588DA2`. Its horizontal velocity additions multiply the direction terms by `forwardSpeed`.
- B input path: `../../../../../decompiled_minecraft/1.9.4/ornithe-feather/net/minecraft/client/entity/living/player/Input.java` declares the four direction booleans; SHA-256 `206DEA2FF5977599596C907C54F36300C4E72D07D6778D82E8850F57BB0FB009`. `KeyboardInput.tick()` sets them before applying the same `0.3` sneak scaling to the separate movement floats; SHA-256 `7BE11425906BE051C83E275F359816546E4677B16D212156380E8D2E9258654A`. `LocalClientPlayerEntity.rideTick()` lines 762-770, SHA-256 `8AAF711948B7602C2E6C015A37E36ED06073D39727D999D80480B4910B704F5D`, calls the base rider tick and passes those booleans to `BoatEntity.setInput`.
- B boat input consumer: `../../../../../decompiled_minecraft/1.9.4/ornithe-feather/net/minecraft/entity/vehicle/BoatEntity.java`, `setInput(boolean,boolean,boolean,boolean)` lines 704-709 stores the four booleans; `updatePaddles()` lines 527-555 reads them. When there are passengers, left/right change yaw velocity; a single left or right press with neither forward nor backward adds `0.005F` to the horizontal acceleration scale. The boat tick calls `updatePaddles()` on the client-authoritative movement side and then `move(velocityX,velocityY,velocityZ)` at lines 237-245. Boat source SHA-256 `3D41B9DF0EF0D36158105235C92B35DAC4B51070476E7EF688520961D1D56E57`.
- Local authority: B `Entity.isLogicalSideForUpdatingMovement()` lines 2254-2257 checks whether the controlling player is local; `LocalClientPlayerEntity.isLocal()` lines 304-307 returns true. `Entity.java` SHA-256 `BCD7FA2206BF8D7271102F6DA7FE2771F96DBE22EC79DAB9F2101A3E29C17EF0`.
- Rider position path, A: `../../../../../decompiled_minecraft/1.8.9/ornithe-feather/net/minecraft/world/World.java` lines 1339-1343 dispatches `rideTick()` for an entity with a vehicle. `Entity.rideTick()` lines 1275-1290 ticks the player then invokes `vehicle.updateRiderPositon()`; `BoatEntity.updateRiderPositon()` lines 366-370 sets the rider position from the boat position. Hashes: `3C04F5B874FBB6692039164C882A3C47FE28C7ECA864E91C9AC57798153888EE`, `D4C10932CB5BB1067A5A58BE4E1BB1B42BDDE3B6FC893D88178CC07435A1696B`, and `77EF72C28024B754FF76F2C67E14DD61832394BAC2BD7B62B29708560E588DA2`.
- Rider position path, B: `../../../../../decompiled_minecraft/1.9.4/ornithe-feather/net/minecraft/world/World.java` lines 1406-1410 dispatches `rideTick()` for a riding entity. `Entity.rideTick()` lines 1432-1444 ticks the player then invokes `mount.updateRiderPositon(this)`; the boat override sets the passenger position from the boat position. Hashes: `2FE063E0EC224EED9FC7D01B8F788C32035EED5FEE5A9D98EA5E82296236E05A`, `BCD7FA2206BF8D7271102F6DA7FE2771F96DBE22EC79DAB9F2101A3E29C17EF0`, and `3D41B9DF0EF0D36158105235C92B35DAC4B51070476E7EF688520961D1D56E57`.

## Source-level difference

A boat's direct horizontal propulsion reads the ridden living entity's float movement values. If the player holds left only, `forwardSpeed` is zero, so this control expression contributes no horizontal velocity. B routes discrete direction booleans to the boat instead. With a passenger and left-only input, B turns the boat and adds a `0.005F` horizontal acceleration scale even though forward input is absent; the boat then moves using the updated velocity. Sneak scaling applies to A's float inputs but not to B's direction booleans, so the control channels also differ while sneaking.

## Reachability and dependencies

The world dispatches a mounted local player through `rideTick()`. In A the inherited rider tick runs `LocalClientPlayerEntity.tick()`, whose input update copies float movement to the player fields read by `BoatEntity.tick()`. In B the local player's override samples the same tick then passes direction booleans to the boat. B's controlling passenger makes the client the logical side that updates boat movement. The base rider-tick path in both versions updates the passenger's position from the vehicle after the rider tick, so changes in boat movement feed into the player's world position. The analysis is limited to this player-on-boat interaction; it does not catalog general boat physics.

## Consequence and uncertainty

Source proves the control inputs and horizontal acceleration differ. For a stationary, locally controlled boat with a player passenger pressing left only in water, the A rider-speed expression adds no horizontal acceleration while B's paddle control adds `0.005F` in the boat's yaw-relative direction before movement. The predicted consequence is different boat displacement and, through the rider-position update, different player position. No trajectory was tested.

## Handoff

Independent delta: the player's boat-control path changes from continuous movement-speed floats to discrete paddle inputs that rotate and propel the boat. Applicability is limited to mounted player movement; boat physics outside the rider interaction remains out of scope.
