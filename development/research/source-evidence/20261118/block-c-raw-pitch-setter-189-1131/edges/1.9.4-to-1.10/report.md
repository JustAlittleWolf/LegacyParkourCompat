# C — direct local pitch turn, 1.9.4 → 1.10

## Bounded verdict

The ordinary local camera pitch-turn operation is source-identical in Feather 1.9.4 and Feather 1.10. No movement change is supported for this bounded operation. Both releases update current pitch from a `float` delta, clamp current pitch to `[-90.0F, 90.0F]`, then offset previous pitch by the clamped current-pitch change. This is not a clamp of `lastPitch` itself. A finite over-range turn therefore reaches the same finite ±90° current pitch in both versions, and the previous pitch follows that clamped change in both.

The current pitch reaches actual movement on the dry, active Elytra path: the local movement branch passes `isFallFlying()`, then calls `getLookVector()` before its explicit pitch-to-radians calculation. `LivingEntity.getLookVector()` delegates to `getRotationVector(1.0F)`, which reads current `pitch`. The bounded traces and calculations below are the same on both sides.

## Exact sources and integrity

Mapping family is Ornithe Feather (not Yarn or Mojmap). The inspected inputs are exact release roots `decompiled_minecraft/1.9.4/ornithe-feather/` and `decompiled_minecraft/1.10/ornithe-feather/`; this is 1.10, not 1.10.2.

| Release / source member | Relevant SHA-256 | Integrity result |
| --- | --- | --- |
| 1.9.4 `net/minecraft/entity/Entity.java` | `bcd7fa2206bf8d7271102f6da7fe2771f96dbe22ec79dab9f2101a3e29c17ef0` | Matches `1.9.4/ornithe-feather.sources.sha256` entry. |
| 1.9.4 `net/minecraft/entity/living/LivingEntity.java` | `bbb7703f18fd5da05c4e4a43a77ea644b388e63c01d34166d308ea52054be4e5` | Matches source manifest entry. |
| 1.9.4 `net/minecraft/client/render/GameRenderer.java` | `e08c2034c7fd4621a76ff5a4ba26dd80e065c895bc5c0a4120d4d36141203d6b` | Matches source manifest entry. |
| 1.9.4 `net/minecraft/util/math/MathHelper.java` | `43b2a4e3931733dbc3538024b7182b024d874a5f22a269a6199c6b4ca8c59151` | Matches source manifest entry. |
| 1.10 `net/minecraft/entity/Entity.java` | `05da145effa19a6ef7934cc276e89226373b67c12f4ce89a8ce2183f29039f77` | Current file matches nested `.decompile-provenance.json` source entry. |
| 1.10 `net/minecraft/entity/living/LivingEntity.java` | `d40dd476b6b68c6ce45b4202823475deb546ecda2284da330ff6724b33815e82` | Current file matches nested provenance source entry. |
| 1.10 `net/minecraft/client/render/GameRenderer.java` | `35467c3723b9b50bb7e25ee439b8262d091cee12a1b94881f25fe90d35a83c52` | Current file matches nested provenance source entry. |
| 1.10 `net/minecraft/util/math/MathHelper.java` | `43b2a4e3931733dbc3538024b7182b024d874a5f22a269a6199c6b4ca8c59151` | Current file matches nested provenance source entry. |

The 1.9.4 source-manifest SHA-256 is `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19`. Its version-root provenance identifies release `1.9.4`, mapping option Feather, mapping artifacts `feather-gen2-1.9.4+build.2` (mergedv2 JAR and Tiny file), and exact-run inputs; the ready marker reports 1,819 source files. The verification marker says published sources and raw inputs matched. Its one reported artifact difference was a regenerated derived remap JAR, explicitly excluded from raw-input differences and source-file differences.

For 1.10, current relevant files match their entries in the nested `ornithe-feather/.decompile-provenance.json` (that file's current SHA-256 is `df266e2c3009dd1a873c1665c30fe388078ed1c6227668e10cde87409a18c6da`). Its input records pin the 1.10 client URL/hash (`c00f8170…a0af35`), Feather `1.10+build.2` mergedv2 mapping JAR/hash (`ad7cf674…d8ddf8dd`), and Feather 1.10 metadata URL/hash. This is nested exact-input/source-hash evidence. **The 1.10 version root itself contains only `ornithe-feather/`; it lacks the sibling source manifest, version-root provenance, ready marker and verification marker present for 1.9.4. Thus I verified its nested source hashes and recorded exact input identities, but cannot claim version-root readiness or an independent release-artifact verification from this tree.** Hashes here establish file identity relative to the recorded manifests, not a digital signature.

## Direct local camera pitch turn

- `1.9.4`, `Entity.updateLocalPlayerCamera(float yaw, float pitch)`, `Entity.java:286–297`.
- `1.10`, same member, `Entity.java:291–302`.
- In both, the prior current pitch is captured as `float f = this.pitch`; pitch delta is a `float` parameter. `pitch * 0.15` uses a double literal and therefore a double intermediate, followed by an explicit narrowing cast to `float` when assigned to `this.pitch`. The resulting current pitch is passed to `MathHelper.clamp(float, -90.0F, 90.0F)`.
- Only after that clamp, `lastPitch` becomes `lastPitch + (this.pitch - f)`. All three values/fields and the subtraction/addition are `float`; there is no separate previous-pitch clamp. Mount look callback remains after both yaw/pitch history updates.
- `MathHelper.clamp(float,float,float)` is byte-for-byte equal in both source files at `MathHelper.java:80–86`: below-min check first, otherwise above-max check, otherwise return `x`. Hence ordinary finite over-range input clamps to the selected endpoint. The helper does not special-case NaN; that is separate from the finite-overrange claim.
- In both `GameRenderer.java:843–854`, the ordinary and smooth-camera branches call the local player’s `updateLocalPlayerCamera(h, i * j)`. This confirms the setter is on the client camera-turn path, not a server/entity simulation-only method.

## First active dry Elytra movement use

- `1.9.4`, `LivingEntity.moveRelative`, `LivingEntity.java:1302–1317`; `1.10`, same member, `LivingEntity.java:1332–1347`. The branch is guarded by `(isLocallyControlled() || isLogicalSideForUpdatingMovement())`, then by the non-water and non-lava conditions (each retains the player creative-flight exception), then `isFallFlying()`.
- On entry, both versions first call `this.getLookVector()` at the Elytra branch's first pitch-dependent operation. `LivingEntity.getLookVector()` is `return this.getRotationVector(1.0F)` in 1.9.4 `LivingEntity.java:1817–1819` and 1.10 `LivingEntity.java:1853–1855`; `LivingEntity.getRotationVector(1.0F)` uses `this.pitch` and `this.headYaw` directly in both (1.9.4 `LivingEntity.java:1822–1830`; 1.10 `LivingEntity.java:1858–1866`). The next explicit pitch read in the branch is `float i = this.pitch * (float)(Math.PI / 180.0)` (1.9.4 line 1312; 1.10 line 1342). Subsequent lift, dive and glide-velocity calculations consume the vector and/or `i` in the same order and with the same literals and types.
- The containing movement method calls `moveRelative(this.sidewaysSpeed, this.forwardSpeed)` in 1.9.4 `LivingEntity.java:1706–1712` and 1.10 `LivingEntity.java:1742–1748`. In both, `flyingTick()` immediately precedes it. That tick preserves the Elytra flag only when it was already set, the entity is airborne and not riding, and a chest-slot Elytra passes `ElytraItem.canFly`; it clears the flag otherwise (`1.9.4:1718–1737`, `1.10:1754–1773`). `isFallFlying()` reads flag 7 in both (`1.9.4:2052–2054`, `1.10:2088–2090`). Thus an active local Elytra player in dry movement reaches the pitch-sensitive flight branch; the source also retains the water/lava gates before it.

## Difference disposition and boundary

No difference is found in the selected setter, clamp helper, renderer call, or active Elytra movement consumer between the named endpoints. Source identity establishes only this endpoint comparison for this operation; it does not identify patch-level introduction history or close full-pair movement coverage. No source-only claim is made here about other pitch writers (teleports, network lerps, NBT, mob/player responses) or unrelated movement slices.

