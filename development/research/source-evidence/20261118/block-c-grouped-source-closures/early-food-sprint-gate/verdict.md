# Independent source verdict: early sprint food and permission gates

Status: ACCEPTED for the bounded 1.8.9 ↔ 1.9.4 gate comparison; accepted subordinate observation for the 1.10.2 flight double-tap guard.

## Immutable input

Comparison artifact: `early-food-sprint-gate-source/comparison.md`
SHA-256 (UTF-8 bytes): `a764421ee8d782d98937371ce9aabaffbff2df2420c71586e32be3671a18d874` (verified).

## Source identity and alias manifests

For 1.8.9 and 1.9.4, `ornithe-feather.provenance.json` identifies the exact version and `ornithe-feather` namespace, with Feather Gen2 build.2 mapping artifacts; `ornithe-feather.ready.json` declares ready status and a SHA-256 for the source manifest. In both releases, the ready marker's manifest hash matches the adjacent `ornithe-feather.sources.sha256` bytes, and the manifest entry for `LocalClientPlayerEntity.java` matches the inspected file bytes. The exact entries are 1.8.9 `1762b116e6b06d682b7daaa0fc8cce39b0ff455b3db8cf79dab03ac74f6c4053` and 1.9.4 `8aaf711948b7602c2e6c015a37e36ed06073d39727d999d80480b4910b704f5d`.

## Finding

The examined sprint transition is equivalent at the two provenance-marked endpoints. Each computes permission as `getFoodLevel() > 6.0F || abilities.canFly` (1.8.9 `LocalClientPlayerEntity.java:550`; 1.9.4 `:661`). This is a strict food threshold with `canFly` as the alternative. The gate reads capability `canFly`, not current `flying` state.

Both sources retain the same two start paths and stop check, in the same order. Forward double-tap requires grounded, not sneaking, pre-tick forward below 0.8, post-tick forward at least 0.8, not already sprinting, permission, no item use and no blindness; it either sets the seven-tick timer or sets sprinting. The held sprint-key path requires post-tick forward at least 0.8, not already sprinting, permission, no item use, no blindness and the key pressed, without the grounded/sneaking/prior-forward checks. The later cancellation stops sprinting on forward below 0.8, horizontal collision or permission loss. In both sources `isSprinting` reads entity flag 3 and `setSprinting` writes flag 3 (1.8.9 `Entity.java:1460–1465`; 1.9.4 `:1613–1618`), so the gate drives the same sprint state bit.

Alias check: in both releases `f = 0.8F`, `bl3` captures pre-`input.tick()` forward `>= f`, and `bl4` (permission) is computed after `input.tick()` and item slowdown. The decompiler-local names and item API names differ, but the conditions and ordering do not. The sequential routes are guarded by current `isSprinting`; the report's use of “independent” should be understood as two distinct eligibility routes, not independent state writes.

## Subordinate 1.10.2 observation

The food/`canFly` sprint permission and both sprint paths remain equivalent in 1.10.2 (local renamed to `bl5`; `LocalClientPlayerEntity.java:684–710`). Separately, non-spectator flight double-tap now requires `!bl4` at `:719`; `bl4` is set when `ticksToNextAutojump > 0` and that path synthesizes `input.jumping` at `:672–677`. This verifies the distinct flight-tap suppression gate only. AutoJump geometry and food production were not assessed.

## Limits

This verdict accepts only the stated gate slices at exact, provenance-marked 1.8.9 and 1.9.4 endpoints, plus the narrow 1.10.2 observation. It does not establish behavior for every intermediate 1.9 patch: their local sources lack adjacent exact provenance/ready/source manifests as already disclosed by the input artifact. It is not a full movement inventory, patch-introduction boundary, implementation acceptance or runtime parity result.

## Terminal status

COMPLETED