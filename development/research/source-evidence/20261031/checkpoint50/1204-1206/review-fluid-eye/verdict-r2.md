# Independent source verdict r2: accepted individual finding

Reviewed revised draft: `.native-source/20261030/1204-1206-pool44/travel-fluid/fluid-eye-sampling-height.md`
Exact reviewed SHA-256: `E4E4EEC9E4F8F0BF0D7931EBDC572ECAC3DC9D49FC8D0E6112E04F6852B279F4`.

## Verdict

**Accept this individual finding.** The revision resolves the prior correction: it states the same-state and same-block condition, gives the strict/inclusive surface interval, and explains that the current sample is consumed on the following player tick because `Player.tick` refreshes `wasUnderwater` before `Entity.baseTick` writes the new tags. It traces the local-player swimming and travel gates accurately. No remaining source dependency is open for this bounded finding.

The mechanism is source-supported: `1.20.4` `Entity.updateFluidOnEyes` samples `getEyeY() - 0.11111111F` (`Entity.java:1211-1223`), while `1.20.5` samples `getEyeY()` (`Entity.java:1227-1239`). Both use the same strict `surfaceY > sampleY` condition, and `1.20.6` retains the 1.20.5 sample. With the revised common-state precondition, the old-only interval is exactly `eyeY - 0.11111111F < surfaceY <= eyeY`.

The call-order account is correct for the local player. `Player.tick` calls virtual `updateIsUnderwater` before `super.tick`; `LocalPlayer.updateIsUnderwater` delegates to the player implementation and `LocalPlayer.isUnderWater` returns the refreshed `wasUnderwater`. `Entity.baseTick` then writes the new eye-fluid tags and calls `updateSwimming`. On the following player tick, the pre-super refresh consumes those tags before `updateSwimming` and `LocalPlayer.aiStep`; `LivingEntity.aiStep` reaches virtual `Player.travel`. The claimed effects remain conditional on the stated sprint/swimming, vehicle, flight, water and travel gates.

All cited `Entity.java`, `Player.java`, `LocalPlayer.java`, and `LivingEntity.java` files for 1.20.4, 1.20.5 and 1.20.6 were re-hashed and matched their exact `mojmap.sources.sha256` entries. Source identity is Mojmap / official Mojang client mappings, as recorded in r1. The r1 verdict was verified unchanged at SHA-256 `5B297DB1F65248ADFBA3B9BE2020807607BC6E76FAE5E2F57DC2230FF2CD70ED`.

This is an individual finding verdict only. It does not claim pair freeze or complete pair coverage.
