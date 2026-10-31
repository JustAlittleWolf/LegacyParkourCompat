# Independent product review — look-at and stair fluid current

**Verdict bound to product commit:** `78712f79651e86c4173a35c7e6b0fe811d43a71e`
**Reviewed comparison:** `26f4ba2e8f146ac9f8ddf778d41b1eb46c71eddd..78712f79651e86c4173a35c7e6b0fe811d43a71e`

## Verdict

**Look-at integration: accept. Fluid stair integration: blocked on fluid-type scope.** The product implementation correctly replaces the candidate's unavailable `currentEntity()` with an existing, reachable scoped LocalPlayer fluid-current context, but applies the newly accepted stair predicate to both water and lava flows. The accepted source finding establishes the player water-current path only. Exact 26.2 source confirms that the scoped `EntityFluidInteraction.update` also computes and later applies lava current, and there is no older-profile lava-current suppression before the existing 1.15.2 boundary. Restrict this delta to water before accepting it for product integration.

## Findings

### Blocker — stair override is reached by lava current without source applicability

`EntityFluidInteractionMixin.legacyparkourcompat$localPlayerFluidCurrent` wraps `EntityFluidInteraction.update` and sets the context for the entire query. `FlowingFluidMixin` then dispatches `FallingFluidFaceSturdyHook` for any `FlowingFluid` reached in that query; `LegacyStairFluidFace` does not inspect the fluid type. `MovementRuntime.withLocalPlayerFluidCurrent` sets the context for a client-side `LocalPlayer`, and its `finally` restores the prior context, including nested calls and exceptions. The path is reachable and safely scoped, but is broader than the accepted evidence.

Exact 26.2 `Entity.java` constructs `EntityFluidInteraction` with both `FluidTags.WATER` and `FluidTags.LAVA`, calls `update`, and applies both current trackers (`Entity.java:250`, `1646–1664`). `EntityFluidInteraction.update` calls `FluidState.getFlow` for every nonempty fluid with a tracker (`EntityFluidInteraction.java:56–74`). Thus the product's `isSolidFace` wrapper can change lava flow vectors over the same 17 stairs. The product has a `LavaCurrent` hook only at `V1_15_2`; profiles resolved to `V1_13_1` do not suppress the later client's lava current. By comparison, exact 1.13 source's player current route is `checkWaterState` -> `m_69693160(FluidTags.WATER)` and applies that water scan's vector to velocity (`Entity.java:947, 960–963, 2507–2564`); no player lava-current writer is part of the accepted 1.13 finding. The accepted candidate review explicitly limits its witness to water and does not establish lava applicability.

**Required correction:** gate this stair-current override to water fluid queries, preserving native lava flow under the selected historical profiles. Keep the existing generic `FlowingFluid` fallback for other fluid calls. This is a source-scope correction, not a claim that every older release's lava behavior has been established.

### Look-at integration — accepted

- 26.2 unobfuscated `Entity.lookAt(EntityAnchorArgument.Anchor, Vec3)` contains the targeted `Math.sqrt(DD)D` operation in the horizontal-length intermediate (`Entity.java:3657–3663`); the redirect owner and descriptor match the actual call site.
- The mixin returns native `Math.sqrt` for non-local entities and when no change resolves. For the local player it dispatches only the horizontal-length hook through `MovementRuntime`.
- `EntityLookAtMixin` occurs once in `legacyparkourcompat.client.mixins.json`; that config is referenced once by `fabric.mod.json`. It is client-only.
- The two hook implementations are public, non-keyed no-argument changes, each implements one annotated hook and are eligible for client discovery. `MovementChangeDiscovery` scans version-package classes from Fabric mod roots and registers implemented hook interfaces. `ChangeResolver` chooses the closest emulation version at or after the selected profile: `V1_16` preserves native double precision before the proven boundary, `V1_16_2` applies the float intermediate for the accepted 1.16.2–1.16.5 range, and `V1_17` and later resolve no hook.
- The `Math.sqrt` redirect's identity check is specifically the active `Minecraft.getInstance().player`, so it does not alter remote entity look-at calls.

### Stair hook integration — other checked points

- The `FlowingFluid.isSolidFace` interception is reached from the client's current query through the existing `EntityFluidInteraction.update` wrapper, and its `finally` restores thread-local context on normal, nested, and exceptional return paths.
- Both stair factory classes are public and expose public static no-argument factories. Each factory enumerates the same 17 accepted 1.13 stair IDs; variant lookup is by block key. The `V1_12` provider is a native-preservation resolver barrier; the single `V1_13_1` false provider resolves for 1.13–1.13.2. For 1.14+ no stair provider is selected, so the native predicate runs, including for modern-only stairs.
- Existing water-bed and chorus-flower providers use different keyed blocks and are not displaced by these stair variants.
- No direct evidence in this review supports changing the stair predicate for unrelated fluid-query operations; the needed water gate keeps scope aligned with the accepted player-current witness.

## Review boundary

Reviewed all source roots (`src/main`, `src/client`, `src/test`) for relevant registration, dispatch, and existing fluid hooks; exact local 1.13, 1.13.1, 1.13.2, 1.14.4, 1.16.5, 1.17, and 26.2 source where cited; and accepted candidate verdicts. Read README and implementation workflow. No build, tests, game launch, decompile, product edit, staging, or commit was performed. The root agent owns the build.

## Final product correction addendum

**Final verdict bound to product commit:** `4f714c1e8c4622e7d20b99a341dc141bcb4c8da0`

**Final verdict: accept both reviewed integrations at this commit.** Commit `4f714c1e` changes the stair predicate to return `false` only when the queried fluid is a `WaterFluid` and the face axis is horizontal; all other queries call the original predicate. Exact 26.2 source declares `WaterFluid extends FlowingFluid` and `LavaFluid extends FlowingFluid`, so both water variants remain eligible for the historical stair rule while lava and other flowing fluids retain native behavior. This closes the prior scope blocker against the accepted water-only player-current evidence. The accompanying source comment records that scope.

The final correction changes only `LegacyStairFluidFace.java`; the lower native-preservation resolver barrier, keyed stair domain, fluid-query context and all look-at code are unchanged. The prior look-at acceptance and remaining fluid checks therefore carry forward. This is a technical source/integration review only; it does not claim runtime parity or build success.
