# 1.20.1 → 1.20.2 LocalPlayer passenger crouch applicability

## Scope and exact sources

This isolated source slice follows the new `!isPassenger()` conjunct in the `LocalPlayer.crouching` update, its direct local-player consumers, and the `vehicle` state transition that makes the predicate true or false. It stops before vehicle movement/physics. No wiki, product, runtime or build/test evidence is used.

The paired local Mojmap ready markers identify 1.20.1 source manifest SHA-256 `858b56764113e591c60d09bc63e4d90f9c1d67a705645f0c793e8b28f2378465` and 1.20.2 source manifest SHA-256 `e439ff7e1d3f1d31e01be1edd141f94d917a9dc7fa0de5f69904544cbbe62887`.

## Predicate change and distinction from raw Shift / entity pose

- **1.20.1:** `LocalPlayer.aiStep` calculates `crouching` from not-flying, not-swimming, collision fit for `Pose.CROUCHING`, and either raw Shift-down or a sleeping/standing-fit fallback (`LocalPlayer.java:657–665`). It has no passenger condition.
- **1.20.2:** the same expression adds `&& !this.isPassenger()` before the crouch-pose collision check (`LocalPlayer.java:655–664`). If true, short-circuit evaluation assigns `crouching=false` and skips both pose-fit queries in this local expression.
- On foot (`isPassenger()==false`), this new conjunct is neutral; the remaining checks are the same collision-fit/crouch predicate. While a passenger, the new local field is false regardless of held Shift, swimming/flying checks having passed earlier in the chain.
- `LocalPlayer.isCrouching()` returns this private field, while `LocalPlayer.isShiftKeyDown()` returns the separate `input.shiftKeyDown` value (`LocalPlayer.java:597–609` in 1.20.1; `595–607` in 1.20.2). The new guard changes the local crouching field, not raw Shift input.
- The player entity pose is a separate state. `Player.updatePlayerPose` uses `isShiftKeyDown()` to choose a desired pose and separately allows `isPassenger()` to bypass the selected-pose fit check (`Player.java:372–399` in 1.20.1; `373–405` in 1.20.2). The passenger guard added to `LocalPlayer.crouching` does not by itself establish a changed `Pose` write.

## Mount and dismount state transition

`Entity.isPassenger()` is `getVehicle() != null` on both endpoints (`Entity.java:2076–2078` / `2110–2112`). A successful `Entity.startRiding` stores the vehicle in `this.vehicle` before returning (`Entity.java:1855–1885` / `1873–1903`). `Entity.removeVehicle` nulls that field; `stopRiding` calls `removeVehicle` (`Entity.java:1901–1911` / `1915–1925`). `LocalPlayer.startRiding` delegates to the superclass and only adds minecart sound setup; `LocalPlayer.removeVehicle` delegates to the superclass and clears `handsBusy` (`LocalPlayer.java:159–176` / `158–175`). Neither override sets or clears `crouching` directly.

The field is recomputed in `LocalPlayer.aiStep`. Therefore its predicate changes on the next such recomputation after `vehicle` changes; the mount/dismount methods themselves leave the previous field value until then. The exact schedule between network/vehicle state updates, `aiStep`, input sampling and movement consumption is not established in this source slice.

The raw Shift value remains independent. `LocalPlayer.isShiftKeyDown()` reads `input.shiftKeyDown`; `Entity.canRide` and `Player.wantsToStopRiding` consult `isShiftKeyDown`, not the local `crouching` field (`Entity.java:1887–1889` / `1905–1907`; `Player.java:306–312` on both releases, with line offsets). The new guard does not change those raw-key checks.

## Direct consumers and bounded movement-input witness

The concrete local-player input is initialized as `new KeyboardInput(options)` by `ClientPacketListener` in both releases (1.20.1 lines 427 and 1131; 1.20.2 lines 399 and 1086). Within `LocalPlayer`:

1. `isCrouching()` returns the field. `isMovingSlowly()` returns `isCrouching() || isVisuallyCrawling()` (`LocalPlayer.java:602–609` / `600–607`).
2. Immediately after writing `crouching`, `aiStep` calls `input.tick(isMovingSlowly(), multiplier)`. The multiplier is `clamp(0.3F + EnchantmentHelper.getSneakingSpeedBonus(this), 0.0F, 1.0F)` (`LocalPlayer.java:660–665` / `658–664`).
3. The concrete `KeyboardInput.tick` samples direction and key states and, only when its boolean argument is true, multiplies both `leftImpulse` and `forwardImpulse` by that multiplier (`KeyboardInput.java:20–33`, byte-identical in the pair).
4. `LocalPlayer.serverAiStep` copies those impulses to `xxa` and `zza` when this LocalPlayer is the controlled camera (`LocalPlayer.java:612–622` / `610–620`). This is a conditional source-backed movement-input witness: the 1.20.1 crouching predicate must have evaluated true, the player must now be a passenger, and `isVisuallyCrawling()` must be false. Under those conditions the new guard makes `isMovingSlowly()` false through this crouch branch, so KeyboardInput no longer enters its crouch-slow multiplication branch. A numerical horizontal impulse difference further requires the computed multiplier `clamp(0.3F + EnchantmentHelper.getSneakingSpeedBonus(this), 0.0F, 1.0F)` to be below 1 and the relevant input impulse to be nonzero; at multiplier 1, the boolean/branch differs but the scaled numeric input is unchanged. On foot, the new conjunct does not change the former crouch predicate.

This proves a changed input multiplier gate, not a changed player trajectory. Whether movement while passenger consumes these inputs, and how that interacts with mount controls, is outside this bounded review. A non-false `isVisuallyCrawling()` value can also keep the slow-input boolean true when `crouching` is false. The current equipment/enchantment bonus, camera control, and input sampling order remain runtime gates.

Other source references to `isCrouching()` resolve to event/sound gating, sprint particles, movement statistics, rendering, or passenger attachment positioning (`Entity.java:734–743, 1252–1254` / `736–745, 1262–1264`; `Player.java:1523–1528` / `1527–1532`; `Player.java:2094–2095` on both endpoints). The attachment-position use belongs to excluded vehicle positioning. No other direct player walk-translation consumer of the LocalPlayer field was established here.

## Applicability disposition

- **On foot:** the new passenger conjunct is false, so this delta alone does not alter the crouch-fit condition or its input slowdown result.
- **Passenger:** after `aiStep` observes a non-null vehicle, the local crouching field is forced false. To establish a difference from 1.20.1, that release's crouching predicate must otherwise be true; `isVisuallyCrawling()` must be false for the slow-input boolean itself to turn false. A numerical horizontal input difference also requires multiplier < 1 and a nonzero impulse. The guard does not change raw Shift, `Entity.isPassenger`, or directly force a player-pose change.
- **Transition boundary:** mount/dismount state changes are source-defined, but `crouching` is recalculated by `aiStep`; timing around that update and any one-cycle retained field value is an open scheduling input.
- **Movement result:** the changed scaled/unscaled input is the exact causal witness. Actual passenger displacement is deliberately left open because it requires the passenger-control/travel route, which is outside scope. The source does not support a general claim that all mounted movement changes.

## Evidence identity

Relevant source SHA-256 values (old 1.20.1 / new 1.20.2):

- `LocalPlayer.java`: `69a2d043d2c0595bd364d625a5445e4934d9e07fef020cd79fafb42f59952ac2` / `bb5cbfb03656a1866bb77c00431618befe792081223b35db4bd121ecbb151fd5`
- `KeyboardInput.java`: `a8064906872955a3520398ab5b2a326552d424f41887d1294aa6a038e2623ff0` / same
- `ClientPacketListener.java`: `8b21ccd4106abb0698f6872250e665ae3f0fc942c8953b9ddd45976660ed624b` / `0208f6942035adaf2e787546851ca296f0e7ec2179d3610f694911eda5283264`
- `Entity.java`: `94b9c3656715de2d61fa9a02ccef164261e50eb13d6090a6f07e58fe9c5b0759` / `d7ee49aaea5e862b92508e767562cabc8f10515605e8fb67565c8c8d5c23b01b`
- `Player.java`: `873d82c6f5471d06812929e471d970fd973bfbe21638e4e348a6fcde45637dac` / `25f263692fd6b2a737aa813315022a0caf4e18c3661ba3bd706c6297f909f25c`
