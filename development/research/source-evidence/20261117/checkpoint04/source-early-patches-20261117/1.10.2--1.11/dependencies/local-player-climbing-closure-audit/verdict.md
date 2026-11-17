# Independent source audit: local-player climbing and pushability (1.10.2 → 1.11)

Verdict: **ACCEPT** for the bounded report `build/source-early-patches-20261117/1.10.2--1.11/dependencies/local-player-climbing-closure.md`, SHA-256 `5EDC1D395FD60E351D8861B84BFC4C80756C46C2784283B46DA28D8E12CB3E9D`.

The report’s exact pair ready-marker and source-manifest hashes match the root sidecars. I independently verified all six cited Java-file hashes against the two source trees: `LivingEntity.java`, `EntityFilter.java`, `Entity.java`, `PlayerEntity.java`, `ClientPlayerEntity.java`, and `LocalClientPlayerEntity.java`.

The paired `LivingEntity.isClimbing()` and `canClimbTrapdoor()` bodies match: the feet-cell ladder/vine test, spectator exclusion, and open trapdoor with matching ladder-facing test are unchanged. The clamping/fall-distance branch and post-move horizontal-collision `velocityY = 0.2` branch use the same predicate and are unchanged; the 1.11 `MoverType.SELF` call is correctly kept as a separate resolver concern.

The `isPushable()` difference is accurately bound: 1.10.2 returns `!removed`; 1.11 returns `isAlive() && !isClimbing()`. For an alive, unremoved, non-spectator local player in an ordinary ladder/vine or qualifying trapdoor state, this changes push eligibility from true to false. `EntityFilter.canBePushedBy` evaluates `isPushable()` before its client-side local-player gate, and the local player’s `isLocal()` returns true. The call and response path is present: `LivingEntity.mobTick()` invokes `pushAwayCollidingEntities()`, which supplies the filter to `World.getEntities`; `pushAway` calls `candidate.push(caller)`, and `Entity.push` adds horizontal velocity to both entities subject to its stated vehicle/noClip/distance/passenger guards. Ordinary overlap and the report’s explicit team/collision conditions are sufficient static preconditions for this bounded predicate claim.

For caller reachability, I additionally checked exact paired source: `Minecraft.tick()` calls `world.tickEntities()` on the client; `World.tickEntities()` visits the regular entity collection; `World.tickEntity()` ticks loaded in-chunk entities; and `LivingEntity.tick()` reaches `mobTick()`. `World.getEntities` and `WorldChunk.getEntities` pass the filter for intersecting entity shapes in both versions. These relevant methods support a legal local-client query; they do not establish a particular map occurrence or resulting trajectory.

The report correctly excludes the 1.11 cramming damage branch and non-player outcomes. It does not claim a net push trajectory, full tick/provider closure, or pair freeze. No product/wiki material or runtime evidence was used.

Supplemental exact source hashes checked for this audit:

| Source | 1.10.2 SHA-256 | 1.11 SHA-256 |
| --- | --- | --- |
| `net/minecraft/client/Minecraft.java` | `0979d770e8742a07bb8c54cf7fd3449a8c43a4f48910600ffabf75f1f67ba2d9` | `1889e0f8d212d696798c5345bec6a0fea7ae0b576c7beeb96c3de04ef0acea30` |
| `net/minecraft/world/World.java` | `888ed0e9de765def87b05c4126ecdf0b10e9dd448b4543dd1cb98211e9951646` | `f64905888809d5666a63c8e403f171c13907502b8d982c5b3b8fce192360ff47` |
| `net/minecraft/world/chunk/WorldChunk.java` | `dcfcda41f28cc205f17a547c69efdf70aede8f4770a43101d20ead08e8ed86b5` | `b80c4962661b31c9015faa9bb3d42e390528b59473956ba20e424c76625b844f` |