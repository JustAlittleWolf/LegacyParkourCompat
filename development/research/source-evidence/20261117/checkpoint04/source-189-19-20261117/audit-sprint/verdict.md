# Independent source audit: sprint timeout removed

Verdict: **ACCEPT**

This verdict applies to candidate `build/source-189-19-20261117/input-travel/sprint-timeout-removed.md`, SHA-256 `03168131b43b2f07ce5277c1044892f5e177582325e513065eb609a34f9bd4c4`.

## Evidence identity

- 1.8.9 Ornithe Feather source set: source manifest SHA-256 `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004`; provenance SHA-256 `ba066fc8f1c7ceb9819d2106338d9e9f2c2b96eb5a8329eb40e171b1f9889185`.
- 1.9 Ornithe Feather local provenance SHA-256 `8daacb7af8f210e716ee4b34c43b8a5d6f6b4b19831d2fd725cbe16ecf330a1a`.
- Checked source file SHA-256s: 1.8.9 `LocalClientPlayerEntity.java` `1762b116e6b06d682b7daaa0fc8cce39b0ff455b3db8cf79dab03ac74f6c4053`; 1.9 same path `04d6196e7e17595c0aed59b28bc1730f8be5ea3eccdb1f971e9e2492dfadfe06`. The 1.8.9 source file hash matches its source manifest entry; both provenance files match the supplied identities.

## Audit

The candidate's cited members and line ranges are accurate. In 1.8.9 `LocalClientPlayerEntity.setSprinting(boolean)` calls `super` then sets `sprintTimer` to `600` for true or `0` for false (lines 348–352). Its `mobTick()` decrements a positive timer and calls `setSprinting(false)` when the decrement reaches zero (489–494). In 1.9 the override calls `super` then sets the timer to zero (402–405); `mobTick()` increments the timer and has no timeout branch (599–604).

The enclosing behavior is reachable on the local player: the two `mobTick()` overrides retain the sprint-input logic (1.8.9 lines 550–577; 1.9 lines 657–684), and `LivingEntity` invokes `mobTick()` from its tick path (1.8.9 line 1296; 1.9 line 1538). A player can initiate sprint through the double-tap-forward path and keep moving forward without holding the sprint key. With no intervening cancellation, the 1.8.9 timer expires and clears sprinting; 1.9 has no corresponding timeout, so sprinting persists until another existing cancellation condition applies. Holding the sprint key can re-enable sprinting in the same 1.8.9 `mobTick`, so that is not the distinguishing scenario; the reachable double-tap path avoids that ambiguity.

The candidate's previously open `LivingEntity.setSprinting` dependency is closed by source review. Both versions call the entity setter, remove any existing sprint modifier, and add the same `SPRINTING_SPEED_MODIFIER` when enabling sprint (1.8.9 lines 1026–1036; 1.9 lines 1167–1177). `PlayerEntity.getSpeed()` reads `EntityAttributes.MOVEMENT_SPEED` in both versions (1.8.9 lines 1297–1300; 1.9 lines 1386–1389), feeding the inherited `LivingEntity.moveRelative` movement path (1.8.9 lines 1119–1135; 1.9 lines 1289–1352). The setter's relevant movement consequence is therefore the sprint speed modifier; no additional changed dependency is needed to establish this finding.

The finding is appropriately limited to this player sprint timeout operation. Its stated 600-tick threshold and removal of the timeout cancellation are source-confirmed; no exact intermediate patch boundary is established by this endpoint pair.
