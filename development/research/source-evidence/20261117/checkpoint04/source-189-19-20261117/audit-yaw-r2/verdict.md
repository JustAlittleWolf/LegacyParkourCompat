# Independent blind source audit — yaw precision r2

Decision: **ACCEPT**.

## Immutable inputs

- Candidate: `build/source-189-19-20261117/input-travel/movement-input-yaw-angle-precision-r2.md`; SHA-256 `698cbc7b69f3d92cfb865a53c443e77ea4db001dbe76dddb62b1f52408f12ec4`.
- 1.8.9 Ornithe Feather source manifest: `decompiled_minecraft/1.8.9/ornithe-feather.sources.sha256`; SHA-256 `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004`. Mapping artifacts are Feather Gen2 `1.8.9+build.2`; provenance `decompiled_minecraft/1.8.9/ornithe-feather.provenance.json`, SHA-256 `ba066fc8f1c7ceb9819d2106338d9e9f2c2b96eb5a8329eb40e171b1f9889185`.
- 1.9 Ornithe Feather provenance: `decompiled_minecraft/1.9/ornithe-feather/.decompile-provenance.json`; SHA-256 `8daacb7af8f210e716ee4b34c43b8a5d6f6b4b19831d2fd725cbe16ecf330a1a`. It identifies Feather Gen2 `1.9+build.2` inputs.
- Checked source bodies: 1.8.9 `Entity.java` SHA-256 `d4c10932cb5bb1067a5a58be4e1bb1b42bdde3b6fc893d88178cc07435a1696b`, matching the manifest row; 1.9 `Entity.java` SHA-256 `74d3ac34d7e6355c8fdcdd777dcd95ba9ef4626718c8772c1b58d0e28419e42a`, matching its provenance source entry.

## Evidence and decision

The paired `Entity.updateVelocity` methods (1.8.9 lines 844–860; 1.9 lines 943–959) confirm the same pre-normalization `!(f < 1.0E-4F)` gate, float square root, minimum magnitude clamp, `scale / f`, input scaling, trig call order, and X then Z velocity additions. The angle expressions differ exactly as the candidate states: 1.8.9 uses `yaw * (float) Math.PI / 180.0F`; 1.9 uses `yaw * (float) (Math.PI / 180.0)`.

The static witness is valid against both `MathHelper` implementations. Their `sin` and `cos` index formulas and sine-table initialization are unchanged (1.8.9 `MathHelper.java` lines 15–20, 392–395; 1.9 lines 16–20, 422–425). Evaluating the Java float operations at yaw `258.75F` gives angles `4.516039848327637F` and `4.5160393714904785F`; sine indices `47104`/`47103` and cosine indices `63488`/`63487`, with distinct table values. For a reachable forward input of `1.0F`, the living tick scales it by `0.98F`; the ordinary airborne branch uses `speedInAir = 0.02F`, passes the nonzero input through `updateVelocity`, then passes the updated X/Y/Z velocity to `move` in that same tick (1.8.9 `LivingEntity.java` lines 87, 1140, 1165, 1450; 1.9 lines 112, 1357, 1378, 1698). The local player copies its input into movement speed fields in `serverTickAi`, and overrides `isLocallyControlled()` to true (1.8.9 `LocalClientPlayerEntity.java` lines 381, 470–474; 1.9 lines 443, 582–586). Thus the claimed player path is reachable without relying on a broader trajectory.

The r2 wording accurately puts the magnitude gate before normalization, establishes a nonzero airborne input witness, and limits the consequence to changed velocity additions consumed by the same-tick `move` call. No unsupported trajectory claim was found.
