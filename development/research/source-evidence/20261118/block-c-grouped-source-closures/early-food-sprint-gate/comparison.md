# Sprint food and permission gates: exact-source comparison

## Scope and evidence

Compared `LocalClientPlayerEntity.mobTick()` for 1.8.9, 1.9, 1.9.1, 1.9.2, 1.9.3, 1.9.4, and 1.10.2 in the canonical local `decompiled_minecraft/<version>/ornithe-feather/` tree. The relevant input preparation is before sprint eligibility; current sprint is read through inherited `Entity.isSprinting()` / flag 3. This report covers sprint start/stop food and `canFly` conditions, held sprint-key vs forward double-tap paths, and the adjacent flight double-jump gate. It does not assess hunger production, item-use slowdown/order, later sprint mechanics, or runtime parity.

Exact source publication/provenance is locally marked for 1.8.9, 1.9.4, and 1.10.2: each has `ornithe-feather.provenance.json`, `ornithe-feather.ready.json`, and `ornithe-feather.sources.sha256`; the inspected `LocalClientPlayerEntity.java` SHA-256 matches the source manifest entry. Mapping artifacts are Feather Gen2 build.2 in each provenance manifest. Source SHA-256 values are 1.8.9 `1762b116e6b06d682b7daaa0fc8cce39b0ff455b3db8cf79dab03ac74f6c4053`, 1.9.4 `8aaf711948b7602c2e6c015a37e36ed06073d39727d999d80480b4910b704f5d`, and 1.10.2 `a9637065f21ad67464eb5c204c74ebf228c3bb0da8a96ddf4ae73c0490fed443`.

The 1.9, 1.9.1, 1.9.2, and 1.9.3 directories have no local provenance, ready marker, or source manifest. Their Java files were read and hashed locally (1.9 `04d6196e7e17595c0aed59b28bc1730f8be5ea3eccdb1f971e9e2492dfadfe06`; 1.9.1 and 1.9.2 both `de9ad48bcfb2705ef7a9a63e9401c1742aead183e1e9c2d13f8fe40cca0139eb`; 1.9.3 `8aaf711948b7602c2e6c015a37e36ed06073d39727d999d80480b4910b704f5d`), but those local byte hashes do not prove exact-release or mapping provenance. Their bodies agree in the examined gates with the marked 1.9.4 source. Treat their exact patch coverage as unverified until local provenance is supplied.

## Gate behavior

In 1.8.9 (lines 538–579), 1.9.4 (lines 649–686), and 1.10.2 (lines 664–710), the permission local is `foodLevel > 6.0F || abilities.canFly` (`bl4` in 1.8.9/1.9.4, `bl5` in 1.10.2). It reads the food level as a float and uses strict `> 6.0F`; `canFly` is the alternative. The local is not `abilities.flying`. Therefore, with identical hunger and abilities, food 7+ permits sprint start; food 6 or below blocks sprint unless `canFly` is true. Merely being currently in flight does not replace `canFly`, while having `canFly` permits sprint even if `flying` is false.

There are two independent start routes, both requiring forward input `>= 0.8F`, permission, not already sprinting, not using an item, and no blindness:

- Forward double-tap route additionally requires on-ground, not sneaking, and the forward value captured before `input.tick()` to be below 0.8. After `input.tick()`, if the double-tap timer is expired and the sprint key is not held, it sets the timer to 7; otherwise it starts sprinting.
- Held sprint-key route has no on-ground, sneaking, or prior-forward-below-threshold condition. It starts sprinting when the sprint key is pressed.

After both routes, an active sprint is stopped when post-tick forward input falls below 0.8, horizontal collision is true, or the food/`canFly` permission is false. Thus depleted hunger stops an already-sprinting player in that same `mobTick`, even if sprint began earlier. The two route gates and cancellation ordering are the same in every locally available body examined from 1.8.9 through 1.10.2. Names changed from `hasItemInUse()` / `StatusEffect` to `isUsingItem()` / `StatusEffects`; item behavior is outside this assignment.

The `!isSprinting()` checks read the current flag before each start route. The double-tap route can set the flag before the held-key route executes; the second route then sees sprinting and skips. `Entity.isSprinting()` reads flag 3 and `setSprinting` writes flag 3 (1.8.9 `Entity.java:1460–1465`; 1.9.4 `Entity.java:1613–1618`; 1.10.2 `Entity.java:1655–1660`). This is the player movement state consumed by subsequent movement; no difference to this state transition follows from the food/permission gate across the compared marked endpoints under equal input, hunger, and abilities.

## Adjacent flight gate

The flight toggle outer gate remains `abilities.canFly`, with spectator mode forcing `flying = true`. In 1.8.9 and 1.9.4, non-spectator double-jump handling is entered on `!previousJumping && input.jumping`; its 7-tick timer then toggles `abilities.flying` on the second qualifying press. Current `abilities.flying` is the value toggled, not the eligibility capability.

In 1.10.2, the same tap edge additionally requires `!bl4` (lines 713–726), where `bl4` is the earlier local set when `ticksToNextAutojump > 0` and `input.jumping` is synthesized (lines 672–677). This is an extra auto-jump suppression condition for the flight double-tap gate. It does not alter sprint's food / `canFly` local or the sprint held-key vs forward-double-tap conditions.

## Coverage result and limits

No change was found in the sprint food threshold, `canFly` exception, held sprint-key route, forward double-tap route, current-sprint predicate, or permission-based stop condition among the examined source bodies. The provenance-marked 1.8.9 and 1.9.4 pair establishes no difference for those gates; 1.10.2 also matches. The requested 1.9.0–1.9.3 exact-release check is source-body-complete but provenance-incomplete as explained above, so it cannot close exact patch-boundary coverage. This is source review only, not independent coverage audit, implementation acceptance, or runtime parity.

### Source locations

- `decompiled_minecraft/1.8.9/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`: `mobTick`, lines 488–637; sprint gates 550–577; flight gate 579–594.
- `decompiled_minecraft/1.9/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`: `mobTick`, lines 600–755; sprint gates 657–684.
- `decompiled_minecraft/1.9.1/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`: `mobTick`, lines 601–757; sprint gates 658–685.
- `decompiled_minecraft/1.9.2/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`: same source hash/body as local 1.9.1; sprint gates 658–685.
- `decompiled_minecraft/1.9.3/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`: `mobTick`, lines 604–760; sprint gates 661–688.
- `decompiled_minecraft/1.9.4/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`: `mobTick`, lines 604–760; sprint gates 661–688; flight gate 691–706.
- `decompiled_minecraft/1.10.2/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`: `mobTick`, lines 620–783; sprint gates 684–711; auto-jump setup 672–677; flight gate 713–726.
- `decompiled_minecraft/1.8.9/ornithe-feather/net/minecraft/entity/Entity.java`: `isSprinting` / `setSprinting`, lines 1460–1465.
- `decompiled_minecraft/1.9.4/ornithe-feather/net/minecraft/entity/Entity.java`: `isSprinting` / `setSprinting`, lines 1613–1618.
- `decompiled_minecraft/1.10.2/ornithe-feather/net/minecraft/entity/Entity.java`: `isSprinting` / `setSprinting`, lines 1655–1660.

## Terminal status

COMPLETED — bounded source comparison artifact written. Exact Feather provenance is verified for 1.8.9, 1.9.4, and 1.10.2. Exact release provenance remains unresolved for 1.9, 1.9.1, 1.9.2, and 1.9.3; do not treat those patch checks as accepted coverage.
