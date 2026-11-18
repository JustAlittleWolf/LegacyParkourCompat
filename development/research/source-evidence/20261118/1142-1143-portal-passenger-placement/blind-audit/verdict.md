# Independent final claim review

## Binding and verdict

Reviewed author artifact SHA-256: `d0588c3ce0c10dbad594d1156e639c28d0bd7df7da534a544791b542ea3d6d30` (`portal-passenger-1142-1143/source/comparison.md`). The review is bound to the frozen blind audit SHA-256 `afa895eea5e799fcc565a5a750ca609fbce9c6ae10aefcb845a4db2247d583c4` (`blind-audit/frozen.md`). The frozen file was not modified.

**Verdict: Accept the four scoped equivalence claims.** The compared inputs resolve to the exact 1.14.2 and 1.14.3 Yarn trees and mapping builds recorded in the frozen audit. All relied-on source hashes checked in the author table match the corresponding `.decompile-provenance.json`; the additional `ServerPlayerEntity.java` hashes in the author comparison also match both manifests. The client's, Yarn artifact's, Intermediary artifact's and Yarn metadata's hashes agree with the manifests and local pins. Cached Yarn metadata lists highest builds 7 and 13.

## Claim review

1. **Generic dismount positioning:** Accepted for the scoped operation. `LivingEntity.stopRiding` invokes the dismount selector after the vehicle changes, and only under `!world.isClient`. The ordinary-vehicle scan order, floor/box construction, support and collision gates, fallback coordinates and teleport request match. The side expression is spelled `getMainHand() == AbsoluteHand.field_6183` in 1.14.2 and `getMainArm() == Arm.RIGHT` in 1.14.3. Paired `PlayerEntity` accessors map tracked value 0 to left and 1 to right in both releases, and the enum constants have matching left/right order. Thus the changed selector denotes the same side for the compared reachable player state.

2. **Server virtual correction and movement gate:** Accepted for the inspected call chain. `ServerPlayerEntity.stopRiding` calls `super.stopRiding`, then, when the vehicle changed and a handler exists, requests correction to the resulting player coordinates. The handler's main-thread/validation gates, outstanding `requestedTeleportPos` branch, more-than-20-tick resend, relative-coordinate construction, id/tick writes and packet send order match. This establishes the server-side requested correction and ordinary movement gate in these methods only.

3. **Boat and horse stand-up contract:** Accepted. Boat and `HorseBaseEntity` enter the same special branch in `LivingEntity.method_6038`; the boat's angle is zero and the horse's side angle follows the equivalent right-arm comparison. Distance, vertical retries and union-box collision fallback are unchanged. Their `updatePassengerPosition` overrides are riding-position updates and match in the checked position/rotation write order; they are not separate dismount-selector overrides.

4. **Client absolute/relative XYZ application:** Accepted. `PlayerPositionLookS2CPacket.apply` calls the client handler, which forces main-thread execution. The handler applies XYZ flags in order, updates render history and velocity under the same branches, then applies rotations, updates player position/angles, confirms the teleport and sends the movement response. The enum-name remap is the only relevant flag spelling change. This verifies the listener path and local application, not transport arrival or ordering.

## Supporting source identity added for the hand/arm equivalence

These files were not in the author's listed source roster, but are direct evidence for the changed selector names. Their local hashes were recomputed and match their provenance entries:

| Source | 1.14.2 SHA-256 | 1.14.3 SHA-256 |
|---|---|---|
| `net/minecraft/entity/player/PlayerEntity.java` (`getMainHand` / `getMainArm`, lines 1886–1891) | `333bb39db18af4cef320eb73b7abe359d438fb2a6ca7d8798ee449da6c684398` | `e3f361ac4e369d01473f3d84044ee33f9685d182c4485e2e80b7f62756950963` |
| `net/minecraft/util/AbsoluteHand.java` / `net/minecraft/util/Arm.java` | `adab76f8a18da074616b804fe8dfc021c094071581f1e5561896e3460202add7` | `7894f353ab99f6d245d894f7d6b13c31ade280cccb8c1772aa80b6368179701b` |

## Residuals

- The author's source roster omits the `PlayerEntity` accessors and the two hand/arm enum sources needed to substantiate the changed selector spelling. The paired sources were independently checked here and their identities are recorded above; adding those rows would make the author's evidence inventory self-contained.
- The frozen audit's prose overstates the end lines for the special `method_6038` branch as `1737` and `1748`; the actual method closing lines are `1728` and `1738`. The method body and verdict are unaffected, and the author comparison's method ranges are correct.
- These findings remain limited to the four assigned operations and endpoints. No full-pair or intermediate-patch conclusion follows. Source calls do not establish packet transport delivery or event-arrival timing.

## Verdict file digest

SHA-256 of this exact UTF-8 LF file with no BOM is reported in the handoff.