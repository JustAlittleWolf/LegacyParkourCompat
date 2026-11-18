# Independent source audit: Llama local-player Y placement

## Verdict

**Accept the bounded no-difference claim** for direct local-player Y placement on an ordinary adult default Llama or TraderLlama in Minecraft 1.14.2 → 1.14.3, using the exact Yarn-mapped decompiled sources. No operation-order, gate, numeric-type, or value change was found in the placement path or its dependencies in scope. This verdict does not establish full-pair coverage or an intermediate-patch boundary.

The comparison input's callback summary needs one precision correction: `ClientWorld.tickPassenger` invokes the eligible player's `tickRiding()` before checking the `field_6016` / `updateNeeded` flag. That flag gates recursive ticking of the passenger's own passengers, not the current player's tick or the Llama placement callback. The relevant reachability conditions are that the passenger is not removed, still has the current vehicle, and is a `PlayerEntity` (or is otherwise eligible for ticking).

## Independently traced behavior

- `LlamaEntity.updatePassengerPosition(Entity)` in both versions first checks `this.hasPassenger(arg)`, then calls `arg.setPosition` with Y `this.y + this.getMountedHeightOffset() + arg.getHeightOffset()`. The expression is left-associative: `(baseY + mountedHeightOffset) + passengerOffset`, with double additions. References: `decompiled_minecraft/1.14.2/yarn/net/minecraft/entity/passive/LlamaEntity.java:147-158`; same lines in 1.14.3.
- Both `getMountedHeightOffset()` bodies return `this.getHeight() * 0.67`. `Entity.getHeight()` returns `float` in each version (`Entity.java:2582-2584` in 1.14.2; `2623-2625` in 1.14.3). The `0.67` literal is double, so the float height is promoted to double before multiplication. Both default Llama entity types use height `1.87F` (`EntityType.java:248,360` in 1.14.2; `245-246,357-358` in 1.14.3). `LivingEntity.getScaleFactor()` is `isBaby() ? 0.5F : 1.0F` in both; repository-wide source search found no Llama ancestry override. For the stated adult default, current height is the float `1.87F` (exactly promoted as `1.8700000047683716`), making the mount term about `1.252900003194809`.
- `PlayerEntity.getHeightOffset()` returns double `-0.35` in both (`PlayerEntity.java:952-954` in 1.14.2; `953-955` in 1.14.3). The default `Entity.getHeightOffset()` fallback is `0.0`, but the virtual call for a player resolves to this player override. At base Y zero the terms sum to about `0.902900003194809`; at other base Y values, preserve the source's two sequential double additions.
- Client callback and inheritance are equivalent: `ClientWorld.tickPassenger` admits a `PlayerEntity` and calls `tickRiding` (`ClientWorld.java:163-184` in 1.14.2; `164-184` in 1.14.3). `ClientPlayerEntity.tickRiding` calls `super.tickRiding()` first (`ClientPlayerEntity.java:808-816` both); `PlayerEntity.tickRiding` only dismounts for sneaking when `!world.isClient`, then calls `super.tickRiding()` (`PlayerEntity.java:437-457` / `438-458`). `LivingEntity.tickRiding()` delegates to `super.tickRiding()` (`LivingEntity.java:2321-2326` / `2331-2336`). `Entity.tickRiding()` zeroes velocity, ticks, then if still riding calls `vehicle.updatePassengerPosition(this)` (`Entity.java:1524-1536` / `1564-1576`). A Llama vehicle therefore dispatches to the guarded Llama override. `TraderLlamaEntity` extends `LlamaEntity` in both versions and has no placement override; it inherits the same implementation. The base `Entity.updatePassengerPosition` native fallback is not selected for either carrier.
- The surrounding yaw locals/helper signatures and X/Z placement expressions in the Llama override are unchanged; `MathHelper.sin/cos(float)` return float. No other callback or parent path changes the direct player's Y formula in this bounded route.

## Evidence identity

- Comparison input: `yarn-comparison.md`, SHA-256 `b235154b70c18a64893ccd81341d5a2d907761a2ef312d9424ad315b0006d6f1`.
- Exact source roots: `decompiled_minecraft/1.14.2/yarn/` and `decompiled_minecraft/1.14.3/yarn/`; provenance manifests identify Yarn `1.14.2+build.7` and `1.14.3+build.13` from their pinned mapping-artifact input URLs.
- 1.14.2 provenance manifest SHA-256: `786546f99684423cc2563be45c9c3334dfcc2c7db58ddb74ba39ce403bd0869a`.
- 1.14.3 provenance manifest SHA-256: `81ea0021eb122d4e3340e20d47d6ecb7079365ba767a038d26a8264cddacc28a`.
- Independently recomputed SHA-256 values for each source file cited in the comparison input match that file's entry in its version's provenance manifest. The central movement, callback, type-default, inheritance, offset, and scale sources were reread directly; no online lookup, source generation, build, test, or launch was used.

## Bounds

Audited direct local-player Y placement on default adult Llama and TraderLlama, including the carrier override, mount-height provider, exact type/default dimensions and adult scale, player height offset, client rider callback/inheritance, and fallback dispatch. Excluded vehicle motion, non-player passengers, other carriers, and whole-pair movement coverage.
