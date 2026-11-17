# Product readiness: 1.16.1→1.16.2 source-lava-before-jump witness

Status: **COVERED_NO_CODE**, bounded to the accepted command-reachable source-lava witness and the two canonical fluid operations identified below. This is product/provider correspondence only. It does not establish pair closure or runtime parity.

## Accepted evidence identity

All listed SHA-256 values were recomputed from the files in this checkout on 2026-11-17.

| Input | SHA-256 |
|---|---|
| Corrected teleported source-lava witness r2 | `22C54126534A5291F8FAEB251E1DF1A6D805F3AE0B5636CC91193AAA7921FC2C` |
| Independent witness audit r2 (ACCEPT for bounded witness) | `7228D905AEB6ED83E584EDC91A2EAAA74DD5B01B42E998F3E1B1C93EFA9A8EE2` |
| Fluid caller/dependency report r3 | `80D66EDB973BA4C102E00200EFFB5A3D901800894A9983A1EECBB7454D382BD1` |
| Independent fluid report audit r3 (ACCEPT for bounded dependency slice) | `74D9F0468BCAEAEF62125A56196A79DAFFA45E66D83DB2ECFE57544D71CCDD2A` |
| Lava tag membership input reference | `527C182B62211E234A2A320C4372155BBF5C2599154671B5E1F106FC2A3E1F87` |
| Lava tag resource audit (ACCEPT) | `FEF9FA423063152AEE899B2A87505128B9D62CCDED805D38A2F7686B5B54E6D9` |
| Canonical lava-presence finding | `832400495DC6AB129018F9EAC71054A5A7987D46B2DCCA3949AF1F5518AAA619` |
| Canonical water-first depth/current finding | `9EFD19EA67C4D26940E95B23E69B385B1C82EEFE49BF25E32A2FB6E83FF33BF8` |

## Profile and provider resolution

`ParkourVersion.V1_16` aliases `1.16` and `1.16.1`; `V1_16_2` aliases `1.16.2` through `1.16.5` (`src/main/java/me/wolfii/legacyparkourcompat/api/ParkourVersion.java:86-87`). `ChangeResolver` excludes providers older than the selected profile and chooses the least later ordinal independently for each hook (`src/main/java/me/wolfii/legacyparkourcompat/impl/ChangeResolver.java:28-37`). Thus the witness endpoints select different lava-presence behavior even though the mod target is later. `MovementRuntime.appliesTo` requires a client-side Player with movement emulation enabled; `find` returns no hook otherwise (`src/client/java/me/wolfii/legacyparkourcompat/mechanic/MovementRuntime.java:129-135, 383-387`). The lava-predicate Mixin further narrows its direct dispatch to `LocalPlayer`, and the latch update is only issued for that local player after a move.

| Operation / hook | Selected 1.16 / 1.16.1 | Selected 1.16.2–1.16.5 | Witness correspondence and fallback |
|---|---|---|---|
| Lava predicate, `LavaPresenceHook` (`entity.lava_presence`) | `change/v1_16/LavaPresenceLatch`, emulates V1_16 | No applicable provider: earlier providers are excluded and V1_16 is too old; native `Entity.isInLava()` remains | `EntityLavaPresenceMixin` dispatches only for `LocalPlayer`; if no resolved provider or it returns empty, it does not cancel vanilla (`mixin/EntityLavaPresenceMixin.java:18-32`). V1_16 latch returns its saved state (`LavaPresenceLatch.java:28-44`) and refreshes it after a local-player move (`EntityMixin.java:989-1004`). For the accepted already-airborne, false-latch teleport, this preserves false through the AI jump decision, then movement refreshes it. V1_16_2 uses the native first-tick-gated fluid-height predicate from its base-tick scan. |
| Fluid scan admission, `FluidScanLoadedChunksHook` (`player.fluid.scan_loaded_chunks`) | `change/v1_16_2/FluidScanLoadedChunks`, the closest applicable annotation | Same provider | The provider applies only to non-passenger unit-scale players and boxes within old Y `[0,256]`; it returns `level.hasChunksAt` for the deflated box (`FluidScanLoadedChunks.java:24-41`). The witness player at Y=63 satisfies the explicit gates. If its gates reject or no provider resolves, `EntityFluidInteractionMixin` calls the native admission query (`:105-113`). This permits the cached fluid-height input used by the newer predicate where the witness envelope is admitted. |
| Water-first lava depth/current, `LavaResultProjectionHook` (`fluid.lava_result_projection`) | `change/v1_16/fluid/WaterFirstLavaResultProjection` | `change/v1_16_2/fluid/NativeLavaResultProjection` | V1_16 captures the exact legacy water presence probe immediately before the base-tick fluid refresh; a positive probe masks public LAVA depth and skips LAVA current, without changing the separate lava-presence latch (`EntityMixin.java:232-266, 841-863`). In the witness there is air rather than water: V1_16 enters the probe path because `allowLavaResults(true)` is false, then stores the exact probe result `false`. That stored false allows lava depth and current through. V1_16_2’s exact provider always allows native LAVA results (`NativeLavaResultProjection.java:12-16`). The older V1_15_2 projection is a resolver barrier; it is not selected at either endpoint. |

Provider inventory for the queried mechanics: `LavaPresenceHook` is implemented by `LavaPresence` (V1_13_1), `LavaPresenceCache` (V1_14_2), `NativeLavaPresence` (V1_15_2), and `LavaPresenceLatch` (V1_16); `LavaResultProjectionHook` by the V1_15_2 native barrier, V1_16 water-first projection, and V1_16_2 native projection; `FluidScanLoadedChunksHook` by `FluidScanLoadedChunks` (V1_16_2), `NativeFluidScanAdmission` (V1_21_10), and `ClientFluidScanAdmission` (V1_21_11); `LavaMaterialScanUpperBoundHook` by V1_8_9 and V1_13_1 providers only; `FluidCurrentSurfaceHook` and `FluidCurrentDepthOriginHook` by V1_21_11 `FluidCurrentDepthChange`; `ShallowLavaCurrentCutoffHook` and `PostMoveLavaCurrentHook` by V1_21_11 providers; and `LavaCurrentHook` plus `FluidCurrentMinimumHook` by V1_15_2 providers only. At both selected profiles the V1_21_11 depth provider is applicable and its LAVA path is active for selections at least V1_16. The V1_21_11 shallow-current provider applies for positive lava height; its post-move provider returns false, causing the current refresh nested in `checkFallDamage` to suppress LAVA current. These later operation providers are separate from the V1_16 base-tick water-first gate.

The V1_16 lava latch does not require `LavaMaterialScanUpperBoundHook`; it ignores that optional input. The V1_15_2 `LavaCurrentHook`/`FluidCurrentMinimumHook` are too old to resolve for either endpoint, so they do not override native base-tick application. The V1_16 `LavaResultProjectionHook` supplies that base-tick water-positive mask; the dry witness's false probe proceeds through the other eligible current operations. Current-provider details do not expand the accepted predicate/jump witness.

The earlier `FluidJumpHook` and `RetainedWaterJumpHook` providers at V1_15_2 and `LiquidJumpGateHook` at V1_11_2 are likewise older than both endpoints and are excluded. No resolved jump hook replaces the native `LivingEntity.aiStep()` lava-jump choice for these profiles; the changed input is the lava predicate. Existing Mixin interception invokes native `jumpInLiquid` unless an applicable gate says otherwise (`LivingEntityMixin.java:506-530, 567-580`).

## Bounded disposition

The accepted static witness sets a source lava state and teleports an already-airborne, established local player from dry air after the old movement latch was cleared. For selected 1.16.1, the V1_16 presence provider still reads false at the next jump decision; for selected 1.16.2, the native predicate sees positive lava depth from the admitted current-tick scan and enters the lava jump branch. The selected scan and projection providers do not mask that dry-air lava input. Existing product coverage therefore corresponds to the accepted endpoint difference; no new product code is indicated for this exact case.

This is not a runtime test. It does not certify unrelated fluid callers, broader water/lava arrangements, full 1.16.1→1.16.2 coverage, pair closure, or tick-level runtime parity. No product source, prepared patch, or approval snapshot was edited; no build, test, or game/server launch was run.

