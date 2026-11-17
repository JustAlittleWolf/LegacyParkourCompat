# Independent source audit: effect lifecycle and movement consumers

Verdict: **ACCEPT** for the bounded status-effect lifecycle and direct movement-consumer slice only.

## Bound inputs

- Pair: Minecraft 1.8.9 → 1.9, exact `ornithe-feather` sources.
- Report: `build/source-189-19-20261117/tick-state/effects-lifecycle-followup.md`, SHA-256 `B9D7E2084109A694B7748059C2FB48EF47DA9CDCB629F1734F0AD88EE37874DD`.
- 1.8.9 source manifest: `decompiled_minecraft/1.8.9/ornithe-feather.sources.sha256`, SHA-256 `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004`; provenance: `decompiled_minecraft/1.8.9/ornithe-feather.provenance.json`, SHA-256 `ba066fc8f1c7ceb9819d2106338d9e9f2c2b96eb5a8329eb40e171b1f9889185`.
- 1.9 mapping-local `.decompile-provenance.json` SHA-256 `8daacb7af8f210e716ee4b34c43b8a5d6f6b4b19831d2fd725cbe16ecf330a1a`.

## Audit result

The report's cited Java source hashes were independently recomputed and matched both the exact source files and their publication manifests/provenance rows. The full status-effect lifecycle claims were retraced in `StatusEffect`, `StatusEffectInstance`, and `LivingEntity`: legacy effect IDs remain aligned for speed/slowness/jump boost; duration/amplifier precedence and per-tick apply-before-decrement order are unchanged; effect modifier scaling remains `modifier * (amplifier + 1)`; and logical-server-only modifier installation/removal/reinstallation and expiration removal have the same gates.

The movement connections are source-supported. Speed and slowness retain their movement-speed UUIDs, base amounts and operation; `PlayerEntity.getSpeed()` reads `MOVEMENT_SPEED` in both versions, and `LivingEntity.moveRelative()` consumes the virtual getter for grounded acceleration and the depth-strider branch. Jump boost remains the same `(amplifier + 1) * 0.1F` vertical addition before the sprint impulse, with `PlayerEntity.jump()` invoking the superclass implementation in both versions. The extra levitation behavior is a 1.9-era movement feature without a 1.8.9 counterpart and is correctly excluded from this historical comparison.

Client packet application is also correctly bounded: status-effect packets update the client's effect map in both versions; the changed ambient field does not feed the compared movement formulas; removal handlers call map-only `removeEffect`; and the paired `EntityAttributesS2CPacket` sources are byte-identical. The two `handleEntityAttributes` implementations perform the same base-value, clear-modifiers, then add-modifiers steps.

No movement difference was found in this bounded effect slice. The report correctly leaves server-side packet production and ordering relative to effect packets outside its claim; this audit does not close that dependency or any other tick/provider slice.

## Limits

This ACCEPT binds only the report above and its exact source identities. It does not establish full 1.8.9→1.9 tick/provider coverage, pair freeze, implementation readiness, or runtime parity. Other pair dependencies and findings remain separate.
