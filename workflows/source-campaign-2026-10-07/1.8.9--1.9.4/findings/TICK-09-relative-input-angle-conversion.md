# TICK-09: relative movement input angle conversion changes

- Older version A: 1.8.9
- Newer version B: 1.9.4
- Mechanic / coverage slice IDs: player-relative velocity input rotation; `TICK-09`, `INV-TICK`, `INV-STATE`
- Classification: source-confirmed floating-point operation-order change in horizontal movement acceleration
- Confidence: source-confirmed for the shared `Entity.updateVelocity()` method and its normal player travel callers
- Applicability: a player reaches a non-fall-flying `LivingEntity.moveRelative()` branch that invokes `updateVelocity()`
- First changed release: unknown within (1.8.9, 1.9.4]
- Runtime validation: not performed

## Paired evidence

- A `Entity.updateVelocity(FFF)V`, lines 844-860, applies the same input-length threshold, normalization, scale, yaw sine/cosine and X/Z component additions as B, but computes the angle as `yaw * (float) Math.PI / 180.0F`. `Entity.java` SHA-256 `d4c10932cb5bb1067a5a58be4e1bb1b42bdde3b6fc893d88178cc07435a1696b`.
- B `Entity.updateVelocity(FFF)V`, lines 947-963, uses the same threshold, normalization, scale and component additions, but computes the angle as `yaw * (float) (Math.PI / 180.0)`. `Entity.java` SHA-256 `bcd7fa2206bf8d7271102f6da7fe2771f96dbe22ec79dab9f2101a3e29c17ef0`.
- Reachable player callers: A `LivingEntity.moveRelative(FF)V` calls this method for ground/air land movement at line 1140 and the water/lava branches at 1189 and 1217. B calls it for non-fall-flying land movement at line 1370 and water/lava branches at 1419 and 1447. `LivingEntity.java` SHA-256 values are A `082831c6578e3a70fa6cea5b90bc3eefc26678259b66334470de22b90b5b0e4e` and B `bbb7703f18fd5da05c4e4a43a77ea644b388e63c01d34166d308ea52054be4e5`. PlayerEntity inherits LivingEntity in both versions. B's fall-flying branch does not call this operation and is outside this slice.

## Source-level difference

A multiplies yaw by float-cast PI and then divides by `180.0F`. B multiplies yaw by float-cast `(Math.PI / 180.0)`. The remaining threshold, length normalization, scalar multiplication order, trigonometric function calls and X/Z arithmetic are the same. Because the angle arithmetic differs, the source does not establish bit-identical trigonometric inputs or the resulting acceleration values. No position trajectory is inferred.

## Handoff

Source finding only. Preserve for full-pair reconciliation; no implementation or runtime claim is made.
