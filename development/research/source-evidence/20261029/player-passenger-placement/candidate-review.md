# Independent candidate review — 1.20.1–1.20.2 local player passenger placement

## Verdict

**ACCEPT for product integration.** The candidate represents the complete local Player passenger-placement operation for the bounded pair: its base formula and Player offset, all attachment-carrier aliases, all 15 old vehicle-offset overrides, five custom position paths, and the affected rotation side effects agree with the accepted source report and independent coverage audit. Integration must fulfill the operand-extraction contract documented on the hook, including reconstructing 1.20.1 getter values from the current runtime state. This verdict accepts the candidate contract for integration; it does not establish runtime parity.

## Bound inputs

- Candidate commit: `3bcc566d7c860682d0bad909c19923a9cb69f81f` on `feat/1201-1202-rider-position-20261028`, compared with export base `bdf9e691908d2e2fb0b8ec6216ed959a6cff3d93`.
- Accepted source report: `.native-source/20261028/rider-position-full-operation-closure/report.md`, SHA-256 `21c67eb18a77a59f4e1419c570a58a579b8d7f5425575ea62982da256a3fd235` (matches supplied identity).
- Independent coverage audit: `.native-source/20261028/frontier-c/1201-1202-all-passenger-writers-closure/coverage-audit.md`, SHA-256 `a1330651f1398ca12a799c7b4ed70d0278dfd708dff1caa64a0e9283d6adb781` (matches supplied identity).

## Review findings

- **Inventory:** the `Carrier` enum represents the base plus all 44 1.20.2 attachment-point owners from the source report, including the base/family aliases. `oldVehicleOffset` covers all 15 old overrides: Camel, AbstractChestedHorse, Llama, SkeletonHorse, Sniffer, EnderDragon, Hoglin, Phantom, Piglin, Ravager, Spider, Strider, Zoglin, AbstractMinecart, and Boat. The Minecart `0.0` is correct: it is the old override value documented in the report. Its new `0.1875F` attachment belongs only to the 1.20.2 path being emulated.
- **Base writer and Player offset:** the generic coordinate formula keeps vehicle X/Z and adds old vehicle offset then the Player's `-0.35` double offset to vehicle Y. This matches the 1.20.1 base writer and `Player.getMyRidingOffset()`.
- **Custom placement formulas:** Camel, Chicken, Horse, Llama, and Boat have explicit old formulas. Camel uses passenger index, removed state, body-anchor offset, body yaw, and the old clamp. Chicken uses `getY(0.5)` and body yaw. Horse uses the old vehicle offset and applies stand-animation X/Y/Z adjustment under the old positive-animation gate. Llama uses the old `0.3F` side offset and old riding-height override. Boat handles its single-passenger offset, bamboo variant, passenger index/count, removed-state Y branch, and rotated seat.
- **Animation and rotation effects:** Strider’s inherited old offset formula includes the old bounding-box height and walk bob operands. The Horse result retains the new body-yaw write only when the old stand-animation gate would write it; Camel requests its removed 1.20.1 passenger clamp. Boat’s yaw delta and clamp and Chicken’s body-yaw write remain in the native 1.20.2 callback.
- **Integration operand requirements:** the hook documentation expressly requires callers to extract or reconstruct the exact 1.20.1 values for Camel’s body-anchor offset, current pose/dimensions and bounding-box dimensions, Chicken `getY(0.5)`, Boat’s one-passenger side offset and variant, and Horse/Strider animation state. It also says these values must reproduce old getters and must not come from the new attachment vector. These are explicit integration requirements, not silently omitted behavior. `camelPoseHeight` is present as a general input but is not consumed by the custom Camel formula; that is correct because 1.20.1 Camel’s `positionRider` writes from its body-anchor animation offset directly instead of calling its separate `getPassengersRidingOffset` override.
- **Operation shape and scope:** the returned position plus `RotationEffect` can express the full client placement result while allowing native Boat/Chicken/Horse side effects to continue. The helper rejects invalid relations, non-client execution and `UNKNOWN`; `OTHER_BASE` uses inherited base arithmetic. It performs no vehicle tick/physics and does not opt remote Players or logical-server execution into historical behavior.

## Scope of this verdict

This is a source-contract review only. No code was changed, and no build, tests, or runtime launch was performed. Product integration still needs to implement the documented getter reconstruction and apply the position and rotation effects on the eligible local-client path. No runtime parity claim is made.
