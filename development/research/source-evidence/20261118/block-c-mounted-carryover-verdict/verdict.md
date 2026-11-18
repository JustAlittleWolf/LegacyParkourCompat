# Domain C mounted sprint carryover audit

**Scope:** Independent, read-only source audit of two immutable Domain C claims. Exact decompiled Mojmap sources only. No product implementation, wiki, tests, runtime, or edits outside this evidence file.

## Immutable claim identities

| Claim | Historical revision identity | Raw payload SHA-256 | Verdict |
|---|---|---|---|
| `passenger-sprint-carries-to-player-jump.md` (1.19.2→1.19.3) | Git blob `051b0f03cfd17c2acfd21de9206c12fcde50d63a`, requested as `583f8a9b:development/research/findings/1.19.2--1.19.3/passenger-sprint-carries-to-player-jump.md` | `9732a61258af2e4a6e7b6bba5885924b70ff27c06da0a888e0bc32abc8030894` | **REJECT as written** |
| `mounted-sprint-carryover.md` (1.19.3→1.19.4) | Git blob `832e02f44807fddc6b0c19d4c8e333313b15d59c`, requested as `583f8a9b:development/research/findings/1.19.3--1.19.4/mounted-sprint-carryover.md` | `b64f5dace5d1c45e0afd1e9f07a3bcf9b91f973a9ff16327d492a5480fbe13b3` | **ACCEPT within stated bounds** |

Payload SHA-256 values were computed over raw `git cat-file blob <object-id>` stdout bytes, not PowerShell-rendered text.

## Claim A — REJECT as written

**Claim under review:** With food above six, the player is a passenger of a grounded vehicle, the player is not grounded or underwater, forward impulse is at least `0.8`, and sprint is pressed, 1.19.3 starts sprint while 1.19.2 does not; that state can carry to a later grounded jump and add the sprint jump impulse.

**Exact source comparison:**

- 1.19.2 `LocalPlayer#aiStep`, `decompiled_minecraft/1.19.2/mojmap/net/minecraft/client/player/LocalPlayer.java:773-795`: the first sprint-start branch gates on `onGround || isUnderWater()`, then the subsequent independent held-key branch is at lines 788-795.
- 1.19.3 `LocalPlayer#aiStep`, `decompiled_minecraft/1.19.3/mojmap/net/minecraft/client/player/LocalPlayer.java:694-727`: the first branch adds `isPassenger() && getVehicle().isOnGround()` at line 694; the independent held-key branch is at lines 709-716.
- Both held-key branches omit a player-ground and vehicle-ground requirement. With the claim’s food > 6, **dry** state, forward impulse >= `0.8`, no item use, no blindness, and sprint key held, the direct branch can start sprint in **both** versions. Underwater is also common to both: the held-key water guard permits underwater, and both `LocalPlayer#hasEnoughImpulseToStartSprinting` helpers accept any forward impulse there (1.19.2 lines 1121-1124; 1.19.3 lines 1042-1045). A surface-water state (`isInWater() && !isUnderWater()`) instead fails that direct branch in both versions, so 1.19.3's added outer-branch vehicle-ground predicate can set sprint there. But in the same `LocalPlayer#aiStep`, the following sprint-retention check treats `isInWater() && !isUnderWater()` as a stop condition for a non-swimming player (1.19.3 lines 719-727). The note does not establish a reachable passenger state that remains swimming through the check and carries sprint past dismount; the ordinary passenger path updates swimming to false (`Entity#updateSwimming`, 1.19.3 `Entity.java:1057-1063`). Thus this omitted surface-water case does not establish the note’s bounded carried-sprint witness.
- Shift (`$$1`) gates the first/forward-double-tap branch only. It does not gate either held-sprint-key branch. Item use and blindness are checked on both direct branches. The 1.19.3 passenger food exception is not a distinction under the claim’s food > 6 precondition.
- Vehicle-ground availability distinguishes the first branch only: 1.19.3 adds the grounded-vehicle alternative, while 1.19.2 lacks it. That does not establish the note’s claimed sprint-key-pressed witness because the separate held-key branch bypasses this test on both sides.

Therefore the note’s ordinary dry/underwater pressed-key input guards do not establish a version-specific carried sprint flag. The downstream jump difference is conditionally correct but cannot repair the false premise: shared `LivingEntity#jumpFromGround` adds `(-sin(yaw) * 0.2F, 0, cos(yaw) * 0.2F)` when `isSprinting()` (1.19.2 `LivingEntity.java:2014-2021`; 1.19.3 `LivingEntity.java:2024-2031`).

**Separate unaccepted candidate — changed claim, not this verdict:** A forward double-tap/auto-sprint witness with the sprint key up may be version-distinguishing. On a dry passenger with a grounded vehicle, the 1.19.3 outer branch can arm `sprintTriggerTime` while the analogous 1.19.2 branch cannot; a later qualifying forward impulse while the timer remains positive may set sprint. This needs its own bounded source retrace of the exact input/timer sequence, including tick decrement/order and the distinction from a held sprint-key press. It is not accepted here and must not be merged into Claim A’s immutable reviewed claim.

## Claim B — ACCEPT within stated bounds

**Accepted bounded claim:** In 1.19.3, an eligible local player can set sprint while riding an ordinary existing vanilla mount; the sprint state survives an ordinary dismount and can affect following on-foot player movement. 1.19.4 blocks that mounted start for ordinary vehicles whose `canSprint()` is false. This accepts the source-proven eligibility/persistence difference, not a particular mounting sequence, vehicle simulation, network timing, or eventual trajectory.

**Mount-start gates and distinguishing inputs:**

- 1.19.3 `LocalPlayer#aiStep`, `decompiled_minecraft/1.19.3/mojmap/net/minecraft/client/player/LocalPlayer.java:694-727`: the forward-double-tap branch at 694-707 has the passenger-and-grounded-vehicle alternative and also has `!shiftKeyDown`; the direct held sprint-key branch at 709-716 has no vehicle-ground or shift predicate. Its shared gates include sufficient impulse, enough food, no item use and no blindness. `hasEnoughFoodToStartSprinting()` at 1047-1048 permits passengers regardless of food; the accepted claim narrows to food > 6.
- 1.19.4 `LocalPlayer#aiStep`, `decompiled_minecraft/1.19.4/mojmap/net/minecraft/client/player/LocalPlayer.java:685-708`, routes both start branches through `canStartSprinting()` (lines 1022-1034). That helper requires `!isPassenger() || vehicleCanSprint(vehicle)`; `vehicleCanSprint` requires both `canSprint()` and local control. The forward-double-tap branch additionally checks grounded player/vehicle state; the held-key branch does not, but both share the passenger eligibility denial.
- 1.19.4 `Entity#canSprint`, `decompiled_minecraft/1.19.4/mojmap/net/minecraft/world/entity/Entity.java:3163-3165`, returns false by default. Exact-version override search finds only `Player#canSprint` (`Player.java:2107-2109`) and `Camel#canSprint` (`Camel.java:276-278`) besides the default. Player/Camel are outside the note’s ordinary existing-mount bound (Camel was added in 1.19.4). Thus an ordinary existing vehicle inheriting Entity’s default cannot satisfy the 1.19.4 passenger gate. For the distinguishing pressed-key case, vehicle groundedness and shift are not needed; a dry player with food > 6, sufficient forward impulse, no item-use/blindness, and sprint held suffices in 1.19.3 but fails the 1.19.4 mount eligibility gate.

**Carryover and player consequence:**

- 1.19.3 `LocalPlayer#setSprinting` (`LocalPlayer.java:449-452`) delegates to `LivingEntity#setSprinting` (`LivingEntity.java:1964-1974`), which sets the sprint state and installs the sprint movement-speed modifier.
- 1.19.3 `Entity#removeVehicle` (`Entity.java:1784-1790`) only unlinks passenger/vehicle; `Player#removeVehicle` (`Player.java:1034-1038`) resets boarding cooldown, and `LocalPlayer#removeVehicle` (`LocalPlayer.java:172-175`) clears `handsBusy`. None clears sprint. The 1.19.4 counterparts likewise contain no sprint reset (`Entity.java:1798-1804`, `Player.java:1025-1029`, `LocalPlayer.java:171-174`).
- In 1.19.3 `LocalPlayer#aiStep` lines 719-727, an already-sprinting player is stopped for absent forward impulse/food, collision, or the stated water/swimming conditions; with continued forward input, food > 6, and those stop conditions absent, ordinary local dismount does not reset the flag before the next on-foot tick. The stated grounded on-foot movement can therefore consume the retained sprint speed modifier.
- 1.19.3 and 1.19.4 share the sprint-only horizontal `0.2F` jump impulse in `LivingEntity#jumpFromGround` (lines 2024-2031 and 1955-1962 respectively), although Claim B relies only on retained sprint-modified movement speed.

## Exact source data identities

SHA-256 values below identify the inspected canonical source files. Paths are relative to the project root and each includes the Mojmap family and exact version directory.

| Source file | SHA-256 |
|---|---|
| `decompiled_minecraft/1.19.2/mojmap/net/minecraft/client/player/LocalPlayer.java` | `36AE4AABD609B457FFFB7A8B14ABB50DB9AC775857DDE1774C0C68A8CF50DEEF` |
| `decompiled_minecraft/1.19.3/mojmap/net/minecraft/client/player/LocalPlayer.java` | `64B670EE323D195B3928FB8EA629C26D2560E75A17379A12C376A7BC686D5479` |
| `decompiled_minecraft/1.19.4/mojmap/net/minecraft/client/player/LocalPlayer.java` | `8E7DA18F42D09FBB994F522C2B0E65FCB2BB83CABB21024360299D44D9674C58` |
| `decompiled_minecraft/1.19.2/mojmap/net/minecraft/world/entity/LivingEntity.java` | `FEE2DF5155449098556A138D2530D06B7A35979E43C6AA0AE1FFFD6251853E77` |
| `decompiled_minecraft/1.19.3/mojmap/net/minecraft/world/entity/LivingEntity.java` | `2B8BEFDC406176E01672175451DD480B14DA0B52BFE11A982D368909C05E73DB` |
| `decompiled_minecraft/1.19.4/mojmap/net/minecraft/world/entity/LivingEntity.java` | `C8D91AF61F87AAA1666DE79696D7CD9D4A8212D05F873BDAF28D7BB2926BF165` |
| `decompiled_minecraft/1.19.2/mojmap/net/minecraft/world/entity/Entity.java` | `759DE9CDED43BD882AFCF5B7023BCF804D92419ACB656B493F3B490C83EB18B6` |
| `decompiled_minecraft/1.19.3/mojmap/net/minecraft/world/entity/Entity.java` | `8C35BDEF3BF2DE7B70AC76AD767BA2C194305E0DDA2E03FDF6544983667ADC32` |
| `decompiled_minecraft/1.19.4/mojmap/net/minecraft/world/entity/Entity.java` | `3667FEE610CBC5F58012E3A8FB8D6C4849F649FB7FE5300595158112D8B4B58B` |
| `decompiled_minecraft/1.19.3/mojmap/net/minecraft/world/entity/player/Player.java` | Used for `Player#removeVehicle`; no sprint reset. |
| `decompiled_minecraft/1.19.4/mojmap/net/minecraft/world/entity/player/Player.java` | Used for `Player#removeVehicle` and `Player#canSprint`; no sprint reset. |
| `decompiled_minecraft/1.19.4/mojmap/net/minecraft/world/entity/animal/camel/Camel.java` | Used only to bound the new Camel `canSprint` override outside Claim B. |
