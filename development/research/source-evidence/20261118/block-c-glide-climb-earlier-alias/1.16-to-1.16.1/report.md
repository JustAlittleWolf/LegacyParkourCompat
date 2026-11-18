# Elytra glide precedence at climbables: 1.16 → 1.16.1

## Exact source identity

Both sides are canonical `decompiled_minecraft/<version>/mojmap/` exports. Their `mojmap.ready.json` markers report the exact matching `versionId`, `mapping: mojmap`, `status: ready`, and 3,572 source files; each source manifest SHA-256 matches the marker. Provenance identifies Mojmap and the requested exact releases. The 1.16.1 provenance records the exact batch `1.16.1, 1.16.2`; only 1.16.1 is used here.

| Release | Mojmap client SHA-256 | `mojmap.sources.sha256` SHA-256 | `LocalPlayer.java` SHA-256 | `LivingEntity.java` SHA-256 |
|---|---|---|---|---|
| 1.16 | `00740ef4475d4cd0a1d7064f89602f759c45ff9afe29da9b0ee0b9037ebb2a71` | `e3c23e94ac985c25dec0c768aed17b443ba4823662cf1e152235c99756ae5d4a` | `ee20523adfdb82f3753fa43312af170f34c4f4aa78570631c61bc8c0820dfb8b` | `b6ea2b9b037b79fc3ea6e1f6bcd2c4c1d89d4a96315968a8f247641f6c335f26` |
| 1.16.1 | `b4831e7b63b10588ff06ea86322108d916c536446f3aea3ae988b7fe3e1f66ef` | `76fdd121070c2012f48cbdf5cf0c78490116afc94117c30b0858eedcd6ab9754` | `ee20523adfdb82f3753fa43312af170f34c4f4aa78570631c61bc8c0820dfb8b` | `b6ea2b9b037b79fc3ea6e1f6bcd2c4c1d89d4a96315968a8f247641f6c335f26` |

The source file digests were recomputed from the canonical files and matched their respective manifest entries. Both compared Java files are byte-identical across this adjacent pair.

## Bounded source comparison

- `net/minecraft/client/player/LocalPlayer.java`, both releases, `aiStep` lines 750–757: jump-initiated Elytra start is gated by `!this.onClimbable()` and enabled chest Elytra, then `wasFallFlying` is sampled from the active flag. This start gate does not decide travel for an already-active flyer.
- `net/minecraft/world/entity/LivingEntity.java`, both releases, `tick` lines 2451–2457: after input scaling, `updateFallFlying()` runs immediately before the `travel(...)` call.
- `LivingEntity.java`, both releases, `updateFallFlying` lines 2476–2494: an already-set shared flag is validated against airborne, non-passenger, non-levitating state and an enabled chest Elytra. Invalid cases make local `bl` false; the method writes the shared flag only when `!level.isClientSide`. The active flag is therefore not cleared by this validation on the client side.
- `LivingEntity.java`, both releases, `travel` lines 1912–2044: for a local-controlled player in dry conditions, water and lava branches are tested first; `else if (this.isFallFlying())` precedes the normal movement `else`. At lines 1977–1981 the glide branch reads `Vec3 vec36 = this.getDeltaMovement()`, then uses the strict signed **double** predicate `vec36.y > -0.5` to assign `this.fallDistance = 1.0F` before glide velocity updates and `move(...)`. A value equal to `-0.5` does not satisfy it. This branch does not call `onClimbable()` or the normal climbable cleanup. Later wall-impact damage and fall-flying flag clearing are explicitly server-gated at lines 2007–2019.
- `LivingEntity.java`, both releases, `handleRelativeFrictionAndCalculateMovement` lines 2060–2063 and `handleOnClimbable` lines 2087 onward: the normal path performs climbable handling through this helper. It is bypassed when the earlier fall-flying branch is selected.

## Result and bound

No 1.16→1.16.1 source difference was found in the requested already-active, dry, on-foot, enabled chest-Elytra path at a ladder/vine. With the active flag valid, `travel` takes the glide branch before normal climbable handling; for signed vertical delta greater than `-0.5`, it directly seeds fall distance to `1.0F` before moving. The precondition is active flight already established: the separate jump-to-start condition excludes climbables. This closes only the specified operation slice, not the pair's full movement inventory or other Elytra behavior.
