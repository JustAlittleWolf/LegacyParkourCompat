# Water and lava contact, current, and travel: 26.1.1 → 26.1.2

## Bounded result

**Verdict: no source difference in the assigned ordinary, non-riding local-player fluid path across these two prepared source sets.** The contact/height tracker, current sampling and push, and first normal water/lava travel gates and math are byte-identical in every consumed Java file below. This establishes a bounded no-change result for those slices, not full-pair coverage or runtime parity.

## Input identity and integrity

Both ready markers identify version `26.1.1` / `26.1.2`, namespace `unobfuscated`, and Vineflower `1.12.0`; they report `ready` and `versionMetadataId` equal to their version IDs. The source-manifest files hash to the exact digests recorded by their respective ready markers:

- 26.1.1 `unobfuscated.sources.sha256`: `88afdfb8264842713f8cce86c0bf88e95c2c991ee706349d6ed8b6c22d52fcb3`.
- 26.1.2 `unobfuscated.sources.sha256`: `54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0`.

The 26.1.1 provenance records requested and resolved metadata ID `26.1.1`, published unobfuscated mapping, official client URL `https://piston-data.mojang.com/v1/objects/377031a9e733ba8ab4d355959a8f6fb8eb707556/client.jar`, client SHA-1 `377031a9e733ba8ab4d355959a8f6fb8eb707556`, and original-client SHA-256 `8c109be6177f40d10d0afd820b06542f9609597874242b96e16c5b0dc9e1fef3` (matching its ready marker).

The 26.1.2 ready marker identifies version metadata ID `26.1.2` and client SHA-256 `b1b3158572666445eff01e82fad8c7de2e4953db6d354f311730d77a8359d0b0`. Its allowed provenance file records a 26.1.2-only unobfuscated decompile command and tool versions, but omits the official client URL/hash and version-metadata hash. Thus the local ready/source identity is exact by marker, while independent verification of the official 26.1.2 artifact identity remains open under the permitted evidence.

For every Java file consumed in this review, the current file SHA-256 matched its own version manifest entry, and the 26.1.1 and 26.1.2 SHA-256 values were equal:

| Relative source | SHA-256 (both versions) |
|---|---|
| `net/minecraft/client/player/LocalPlayer.java` | `433fd995ad317af0f6ef0e50c1e8e3483cb8f00e0e327d4edf27a4dd99666ebe` |
| `net/minecraft/world/entity/Entity.java` | `8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf` |
| `net/minecraft/world/entity/EntityFluidInteraction.java` | `5264ff4f1fddebc3fa9d63ad2edbe2eaf617392ff946a867a6817a78b478ee62` |
| `net/minecraft/world/entity/LivingEntity.java` | `c3b64de8dbaba8ad8a7ccd4f33255346d8206e66f12ca91260971bf5e5ac93bd` |
| `net/minecraft/world/level/material/FluidState.java` | `e35608d5775013c67653ea350309b5150a97f36aa65c27f1c6247ae2aa840f18` |
| `net/minecraft/world/level/material/Fluid.java` | `e7759a85dea543266b734086b2061332bc9f62ba3330be0f203f1f8baaa2c80d` |
| `net/minecraft/world/level/material/WaterFluid.java` | `14cb3d49ef1db28fdab367cd6a4f196f20b7886b8768153c6e82906e9d25f0ad` |
| `net/minecraft/world/level/material/LavaFluid.java` | `423ff44a57c316f58c21a66903a4ef2c1b9c3d26316bdc4d301727575f93703f` |
| `net/minecraft/world/level/material/FlowingFluid.java` | `f8c0c4430e81efdf39b221968272ed8e3a4d030fa5192e04e04d34c9fbd37b28` |

## Compared path

- **Tick admission/order:** `Entity.java:523–525` records prior eye-water state, updates fluid interaction, then updates swimming. `Entity.java:1558–1580` resets the tracker from current entity/world inputs, derives water/lava contact, resets fall distance for water, writes `wasTouchingWater`, and then applies currents for pushable entities. `LocalPlayer.java` is byte-identical and has no local-player travel override in this source. The normal player travel implementation is inherited from `LivingEntity`.
- **Contact and stored height:** `EntityFluidInteraction.java:32–82` uses the fluid-interaction box; floors minima and uses `ceil(max)-1` maxima; runs the same loaded-fluid broad gate; scans x/y/z; admits non-empty fluid where `fluidTop >= box.minY`; tracks eye inclusion with inclusive `eyeY >= fluidBottom && eyeY <= fluidTop`; and stores `max(fluidTop - entity bounding-box minY, prior tracker height)`. `isInFluid` is `getFluidHeight(...) > 0.0` (`:139–149`). `Entity.isInWater` reads the cached touching-water bit (`Entity.java:1509`); `isInLava` additionally rejects `firstTick` (`:1655`). All these gates and writes are identical.
- **Current sample/push:** For an ordinary player, `isPushedByFluid()` is true (`Entity.java:3206`), so the update samples each admitted fluid cell's `FluidState.getFlow` (`EntityFluidInteraction.java:74–79`). Flow is height-scaled when the tracker height is below `0.4`; the tracker accumulates samples in scan order. The entity then applies water current at `0.014`, and lava at `0.007` in fast-lava dimensions or `0.0023333333333333335` otherwise (`Entity.java:1570–1578`). The tracker averages accumulated flow for `Player`, skips zero/no-current cases under its `1.0E-5F` squared-length gate, applies the scale, and enforces the same `0.0045000000000000005` minimum impulse under the same horizontal-speed gates (`EntityFluidInteraction.java:166–185`). These values, types, and operation order match byte-for-byte.
- **First normal fluid travel:** `LivingEntity.java:2392–2407` routes to fluid travel only when cached water or lava contact, fluid susceptibility, and `!canStandOnFluid(current block-position state)` all hold. Fluid travel snapshots falling (`deltaY <= 0.0`), old Y, and effective gravity, then chooses water before lava (`:2455–2464`). Water uses sprint slow-down `0.9F` or water slow-down, speed `0.02F`, halves water-walker efficiency off-ground, applies the `0.54600006F`/speed interpolation and Dolphin's Grace `0.96F` override, moves, conditionally raises Y to `0.2` on climbable horizontal collision, damps by `(slowDown, 0.8F, slowDown)`, and applies the fluid-fall adjustment (`:2467–2493`). Lava moves at `0.02F`; at/below jump-threshold height it damps `(0.5, 0.8F, 0.5)` before the fall adjustment, otherwise scales all components by `0.5`; nonzero gravity then subtracts `baseGravity / 4.0` from Y before the shared jump-out check (`:2496–2520`). The helper subtracts `baseGravity / 16.0` except for its exact falling/near-terminal-velocity case, and only when gravity is nonzero and the entity is not sprinting (`:2635–2645`). These gates and math match byte-for-byte.

## Residuals

- The official artifact URL/hash for 26.1.2 cannot be independently checked from the permitted ready/provenance/source-manifest files because the provenance omits those fields. Request provenance-owner confirmation if official artifact identity is a hard acceptance gate.
- Only the requested fluid-contact/current/first-normal-water-and-lava-travel path was reviewed. This is not a full tick-graph coverage audit, all-release cutover, implementation recommendation, or runtime test.
- No positive difference finding is warranted for these slices.