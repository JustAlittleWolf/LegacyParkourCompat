# Correction 1: endpoint `clearFire` helper differs

This supplements `.native-source/20261030/ordinary-pool/fire-lava-cauldron/closure.md` (SHA-256 `049f663455719a749717866ed00a4f3986298b71da4aa9e453b27be364d1157d`). Its sentence that `Entity.clearFire()` sets remaining fire ticks to zero “in both endpoints” is inaccurate.

- 1.21.2/1.21.3 `Entity.clearFire()` sets remaining fire ticks to exactly `0` (`Entity.java:562–563`).
- 26.2 `Entity.clearFire()` sets remaining ticks to `Math.min(0, currentRemainingFireTicks)` (`Entity.java:650–651`), preserving an already negative immunity timer.
- Old `LayeredCauldronBlock.entityInside` reaches `clearFire` only after the `ServerLevel && entity.isOnFire() && isEntityInsideContent` gate; for that eligible callback, remaining ticks are positive and both helper calls produce zero. The modern collector's `EXTINGUISH` applies `Entity::clearFire` at flush (`InsideBlockEffectType.java:16`) and has the helper's modern clamp behavior. The callback closure must preserve the old gate and immediate write.

The paired Entity hashes and provenance remain those in the closure: 1.21.2/1.21.3 SHA-256 `a93719c302a0381a972af75ea360465e2e3551708dd07c34d4d40b7e5173c2b9`; 26.2 SHA-256 `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5`. This correction narrows the helper statement; it does not change the cauldron's eligible-response conclusion or broader finalizer contract.
