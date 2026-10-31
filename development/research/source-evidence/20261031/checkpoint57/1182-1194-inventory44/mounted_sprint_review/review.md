# Independent source review: mounted sprint survives an underwater dismount

## Decision

**ACCEPT**, candidate SHA-256 `821B3E125678A7F6FC3D71F67496948EF2EF1FF6A2DE448B741342A4E7832A28`.

## Source identity

The exact source-manifest files match the supplied hashes:

- 1.19.2 `mojmap.sources.sha256`: `95bc354ac219b9fe1ca8bf45053edad0119a75323d576902d59616a11647aac2`
- 1.19.3 `mojmap.sources.sha256`: `6f1cc6d07ab3902a7ea25a3f8817504723be70d82e97f541cffda95ea2c1c843`

Every source file named by the candidate was checked against its manifest entry. Relevant source hashes:

| Version | Source | SHA-256 |
|---|---|---|
| 1.19.2 | `net/minecraft/client/player/LocalPlayer.java` | `36ae4aabd609b457fffb7a8b14abb50db9ac775857dde1774c0c68a8cf50deef` |
| 1.19.2 | `net/minecraft/client/multiplayer/ClientPacketListener.java` | `98453fac8b16623d7b7f94f19199fa70c3b1d4885c464a2ab521a978a58ecbd3` |
| 1.19.2 | `net/minecraft/world/entity/Entity.java` | `759de9cded43bd882afcf5b7023bcf804d92419acb656b493f3b490c83eb18b6` |
| 1.19.2 | `net/minecraft/world/entity/player/Player.java` | `155c5fcfba322d968f3180383e7d283ddeb5edee4e04314906310d4e3ce0ccc1` |
| 1.19.2 | `net/minecraft/world/entity/LivingEntity.java` | `fee2df5155449098556a138d2530d06b7a35979e43c6aa0ae1fffd6251853e77` |
| 1.19.3 | `net/minecraft/client/player/LocalPlayer.java` | `64b670ee323d195b3928fb8ea629c26d2560e75a17379a12c376a7bc686d5479` |
| 1.19.3 | `net/minecraft/client/multiplayer/ClientPacketListener.java` | `c01c3c362e5eb7de780f4ca0f1b8964a49e40cfc166ae63cbe392cd71b6b1b9e` |
| 1.19.3 | `net/minecraft/world/entity/Entity.java` | `8c35bdef3bf2de7b70ac76ad767ba2c194305e0dda2e03fdf6544983667adc32` |
| 1.19.3 | `net/minecraft/world/entity/player/Player.java` | `02e64e197aec3f4f2a8abc9c5ce4545121ac5bcbbc71592ce478601a0db17203` |
| 1.19.3 | `net/minecraft/world/entity/LivingEntity.java` | `2b8befdc406176e01672175451dd480b14da0b52bfe11a982d368909c05e73db` |

## Findings

1. **The low-food passenger sprint gate is new in 1.19.3.** In 1.19.2 `LocalPlayer.aiStep` uses food above 6 or mayfly directly (lines 771–795). In 1.19.3 it calls `hasEnoughFoodToStartSprinting`; the helper returns true for passengers as well as for food above 6 or mayfly (lines 693–727, 1047–1049). Therefore a non-flying passenger with food at or below 6 can satisfy the food part of the sprint-start conditions in 1.19.3. The start still depends on the other stated input and state gates.

2. **The same empty-passenger packet route clears riding without clearing sprint or swimming.** Both versions' `ClientPacketListener.handleSetEntityPassengersPacket` ejects the vehicle's existing passengers and starts riding only for IDs in the received passenger list (1.19.2 lines 869–896; 1.19.3 lines 922–947). An empty list therefore ejects the local player. `Entity.ejectPassengers` calls passenger `stopRiding`, and `stopRiding`/`removeVehicle` only clear the vehicle relationship (1.19.3 lines 1778–1798). Neither route writes sprinting or swimming state. The 1.19.2 packet handler has the same behavior, so the dismount input is held constant for the version comparison.

3. **Tick order permits the state to persist into on-foot movement.** `Entity.baseTick` updates fluid state and then calls `updateSwimming` (1.19.3 lines 403–430). `LivingEntity.tick` calls its superclass tick before its later `aiStep` call (lines 2264–2304), so the next tick evaluates swimming after packet dismount and before local sprint maintenance. Once unmounted, underwater, already sprinting, and in water at the feet, `Entity.updateSwimming` sets swimming true (lines 1056–1064). In `LocalPlayer.aiStep`, with shift held, underwater state maintained, and the player swimming, the sprint-maintenance condition does not clear sprint: the swimming branch only clears it for the stated `!onGround && !shiftKeyDown && $$6` case or when no longer in water (lines 693–727). At low food, `$$6` is true, but shift held makes that first disjunct false.

4. **The resulting difference reaches an in-scope on-foot movement consumer.** `Player.travel` applies its swimming pitch adjustment when swimming and not riding (1.19.2 lines 1464–1480; 1.19.3 lines 1448–1461). It then delegates to `LivingEntity.travel`; in water, that method chooses sprint damping `0.9F` while sprinting instead of `getWaterSlowDown()` (1.19.2 lines 2042–2060; 1.19.3 lines 2052–2090). These are direct client player movement effects, within the declared movement scope.

## Applicability and limits

The result is conditional on the same incoming dismount packet trace and on the relevant local sprint/swimming and underwater state surviving until the next on-foot tick. The inspected client path directly changes the local player's state and consumes it locally. No claim about server-side historical physics, a server mirror, a server-generated reply, or the absence of later synchronization is necessary. The finding does not cover cases where another packet or state transition overwrites the relevant flags before the on-foot tick.
