# Independent source verdict: needs correction

Reviewed immutable draft: `.native-source/20261030/1204-1206-pool44/travel-fluid/fluid-eye-sampling-height.md`
Draft SHA-256: `C9A8F7AA55CBD498E52C0C262A81C53E3C9C427EF1777ABCE25270B08AC9ED5F`.

## Verdict

**Needs correction before acceptance.** The sampling-height delta and the stated surface interval are source-confirmed. The movement path is reachable for the local player. Clarify that the new sample is consumed one player tick later; the currently drafted paragraph can be read as if the sample written in `baseTick` immediately controls that same tick's swimming update/travel.

## Independent trace

- `1.20.4` Mojmap `net/minecraft/world/entity/Entity.java`, `Entity.baseTick` lines 416-439 calls `updateFluidOnEyes` then `updateSwimming`; `Entity.updateFluidOnEyes` lines 1211-1223 samples `getEyeY() - 0.11111111F`. `Entity.isEyeInFluid` line 1287 reads the `fluidOnEyes` set.
- `1.20.5` Mojmap `Entity.baseTick` lines 418-441 has the same call order; `Entity.updateFluidOnEyes` lines 1227-1239 instead samples `getEyeY()`. `Entity.isEyeInFluid` line 1303 reads the same set. `1.20.6` Mojmap `Entity.baseTick` lines 418-441 and `Entity.updateFluidOnEyes` lines 1227-1239 retain the 1.20.5 sample and gates.
- On each compared source, `Player.tick` invokes `updateIsUnderwater()` before `super.tick()` (`1.20.4` lines 224-251; `1.20.5` lines 244-271). On the local client, `LocalPlayer.isUnderWater()` returns `wasUnderwater` (`1.20.4` lines 1059-1060; `1.20.5` lines 1066-1067), and its `updateIsUnderwater` calls the superclass implementation (`1.20.4` lines 1064-1080; `1.20.5` lines 1071-1087). That refresh reads `fluidOnEyes` as it existed on entry to the player tick.
- Consequently, `updateFluidOnEyes` writes the current sample's tags after that tick's `wasUnderwater` refresh. The next player tick's pre-super refresh sees those tags; that tick's `Entity.updateSwimming` can then use the updated local-player `wasUnderwater`. `LocalPlayer.aiStep` also reads `isUnderWater` in its sprint gates (`1.20.4` lines 693, 701-708; `1.20.5` lines 696, 704-711). `LivingEntity.aiStep` reaches virtual `travel` (`1.20.4` line 2603; `1.20.5` line 2683), and `Player.travel` gates its vertical swimming steering on `isSwimming() && !isPassenger()` (`1.20.4` lines 1451-1474; `1.20.5` lines 1473-1496). Thus the downstream effect is possible on the following player tick, subject to the ordinary sprint/swimming/travel gates.

## Same-raw-input precondition

Hold the compared invocation's player position/pose and `getEyeY`, x/z, world fluid state and height, prior `fluidOnEyes` contents, and boat-gate result constant across versions. For the narrow sampling difference, require both sample y values to resolve to the same block/fluid state and the boat suppression gate to be false at both values. If `surfaceY` is that fluid state's top surface, the old-only water-tag result occurs for:

`getEyeY() - 0.11111111F < surfaceY && surfaceY <= getEyeY()`.

The strict lower comparison and inclusive upper comparison follow from the source's `surfaceY > sampleY` test. This isolates the sample-height change from unrelated position, state, cache, or boat differences. For a swimming-entry path, additionally require a local player that is sprinting, not already swimming, not flying or a passenger, and whose block position has water fluid; the sample's changed tag must persist in the eye cache until the next player tick.

## Source identity and integrity

All paths below are read-only sources under `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\<version>\mojmap`. Mapping family is Mojmap / official Mojang client mappings. Each cited Java file's current SHA-256 was checked against that version's `mojmap.sources.sha256` entry; every check matched.

| Version | Ready source-manifest SHA-256 | Official client SHA-256 | Official mapping file SHA-256 |
|---|---|---|---|
| 1.20.4 | `fd3c8668483e5ff208c847474f6cbd3952a909c4aa602ed98b24e3d11a9602a1` | `9221ab461a491bf9661cd8e773a5e662aaa43d600fa7970b8c12bbfb0431b838` | `ad03c803d866062909dd378ed5f1611687dc0ed0a76cc7fd57e968a99d641e5f` |
| 1.20.5 | `162dac6c4f1d539a1c2cea0255b7938a5281c207ab460bdc265358016d732484` | `49d5949adbb1021a7eb00c1b949f9e39934b10249cbf86d25004774d1b3ff510` | `9afdb3b7fcc4c6acbec3bc6ccb46192bfc6460e12bf9e268e29bfee46bc7fbac` |
| 1.20.6 context | `56aae10684471d7abb1c366bd5dd431ab976112a87e6cc5c687a68f1eff06311` | `02dfd345ac1ad55692d5dbc8486ac7e4fea72cd54ac494a79cd48963048e56b2` | `27f4ef3a9362e9874e2c33edac981b6f485ad97f92c4e35b497525b646aae745` |

Checked source file SHA-256 values (all equal their exact manifest entries):

- 1.20.4: `Entity.java` `07383522bff169938136638ef8c3244ca511b56ca4266913524f99f9821331b9`; `Player.java` `218da60bc4f8c9279f56eb87e2cf2c0af79562fbe3d818a408429c656efb95dd`; `LocalPlayer.java` `bb5cbfb03656a1866bb77c00431618befe792081223b35db4bd121ecbb151fd5`; `LivingEntity.java` `f7bc53db24c1798f19f9bd6f6356c86d5e560e9c8e8aac60decaf15cc785e07d`.
- 1.20.5: `Entity.java` `71cd6b9f6c002684154dce11d3745e8714d82f13c8e1b3aa56743930131c18f3`; `Player.java` `785d93ccc94e1f912e545b2b0c355edeb352b44ee8e83a69364daec35266dbe2`; `LocalPlayer.java` `6b429dfa6e0681251ec985dda1627f808652a7bbe5b70dc85c8fa0fe0ed46ffa`; `LivingEntity.java` `c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2`.
- 1.20.6 context: `Entity.java` `71cd6b9f6c002684154dce11d3745e8714d82f13c8e1b3aa56743930131c18f3`.

This is an individual bounded review only; it does not claim pair freeze or complete pair coverage.
