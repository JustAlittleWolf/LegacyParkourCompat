# Independent narrow source audit: local-player travel cramming correction r2

**Verdict: ACCEPT — correction to the RNG disposition only.**

Bound correction: `build/source-early-patches-20261117/1.10.2--1.11/dependencies/local-player-travel-closure-r2.md`  
SHA-256: `4C5C468B7CE2BF934A8F745208AE6FE46DEE2C76921B3C71178F5055E4610C7A`

This correction binds original travel report `dependencies/local-player-travel-closure.md` SHA-256 `C87AD6538577529AA4CDA6F1C771B9DAC52308E3ED74CEBCA69519A8E4E94506` and its prior independent REQUEST CHANGES verdict SHA-256 `C98022FBAFBBDA60C342DDF2CCF40AFDE8415EEFD1EC446D308E8C468CBA2352`.

## Verified correction

The correction's exact source identities match the local files: 1.10.2 `LivingEntity.java` SHA-256 `d40dd476b6b68c6ce45b4202823475deb546ecda2284da330ff6724b33815e82`; 1.11 `LivingEntity.java` SHA-256 `1f2e31fa6a905ccc2a51a0338f2aaf448f2b0569f28644e8205d864e570d9b2b`.

In 1.11 `pushAwayCollidingEntities()`, after the nonempty-list check and when `maxEntityCramming > 0` and `list.size() > i - 1`, evaluation calls `this.random.nextInt(4)`. Only when it returns zero does the method count non-riding entities and potentially call `takeDamage`. The corresponding 1.10.2 method has no such branch. The shared `mobTick()` calls `moveRelative()` before `pushAwayCollidingEntities()`, and `LocalClientPlayerEntity.mobTick()` calls `super.mobTick()` at both endpoints. Thus the correction accurately identifies a possible local-player entity RNG advancement after that tick's travel and leaves any later movement consumer unresolved. It makes no downstream displacement or trajectory claim.

## Boundary

Accept only this corrected RNG/damage disposition. The unchanged travel comparison is reused from the prior verdict and was not repeated. This verdict does not establish resolver applicability or full exact-pair coverage, and it makes no implementation/runtime parity claim.
