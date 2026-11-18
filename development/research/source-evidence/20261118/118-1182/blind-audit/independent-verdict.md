# Independent blind audit verdict: 1.18 → 1.18.1 → 1.18.2 fluids

Terminal status: **COMPLETED** for the bounded Java-source audit. This is a source review only; it does not establish tick-by-tick runtime parity or independently reproduce decompilation from raw client archives.

## Frozen input and artifact integrity

- Host/execution: Windows, native PowerShell.
- Frozen independent roster: `frozen-roster.md`, SHA-256 `B78B369136469752F6DE19AD4F7ACF28A63022A982E3AD8ED548FEC4A5F7AC71`. It was frozen before cross-version comparison or any author-artifact read.
- Post-freeze dependency expansion: `roster-expansion.md`, SHA-256 recorded in the terminal handoff. The original roster is unchanged.
- The supplied SHA-256 for each `mojmap.ready.json` matches the local file: 1.18 `4537EDF0E88C88631215BEB1C14F1D67F5A5D6D021C982C0AF549B7B27856E5F`; 1.18.1 `7A6F3D9D86776E5A95E4722FC2C36F41165C34177F2993978DFAAB410641FC81`; 1.18.2 `D8057D47468C37880DE38D658E95070EC3B750305B7856189997604822480946`.
- All three source-manifest and artifact-manifest digests match the values in their ready markers. Every Java file read for this audit was rehashed against its version's `mojmap.sources.sha256`; full rows and SHA-256s are in `source-integrity.tsv`.
- The pinned input identities are present in the provenance/sidecars: raw client SHA-256s are 1.18 `381e090ef22584526113bcccf26ae38a310a22094d14c2d0dec72b71fdf65aaa`, 1.18.1 `737c28a56c8980fcd8574465c61538ebde8f511bdd035c69d0cbe27953f892aa`, 1.18.2 `1d09e3639644b6b2254499469d0765cc005a286d19f3fa595b0ed8fb07971ec7`. Official client-mapping SHA-256s in `artifacts.sha256` are 1.18 `b4c7d514328050fa09ac04818d21e4be0b58e75c6b0847e56ce09f6637b8877b`, 1.18.1 `a7a4109255dfb74b9552b339482f0714bb0e7e7143d1059b0bf62a526d2c2a84`, and 1.18.2 `a2aa6ee1030bfef79e9b2e08e79de1637fdd7ecb5bf8891cf2e9a4b186042543`.
- **Unresolved input identity check:** the actual client and mapping artifacts are absent under the provenance cache path `D:\Javastuff\LegacyParkourCompat\build\movement-campaign-2026-10-07\artifacts\<version>\`. Their SHA-256 identities can be checked against sidecars, but the bytes could not be rehashed or used to rerun decompilation. The published source trees, readiness markers, and source manifests are present and verified. The 1.18.2 provenance also records `mappingArtifacts: null`; its mapping digest is available in the artifact sidecar.

Two author artifacts were read only after their supplied hashes matched: `source/coverage.md` SHA-256 `806B2141891D22B2336B641C8701EC8A172CE24E6CC5174F4E1F3DFE0AA0C4C2`, and `source/eye-fluid-tag-membership.md` SHA-256 `28EE43D9F1AA9324D214E82BB2D1A36E43AC57594273CC7AC9BB5E570DFABE5B`.

## Independent operation verdicts

### Body water/lava immersion and current pushing

**Verdict: ACCEPT — no source behavior difference on either adjacent edge for the same tag membership and world inputs.**

1.18 and 1.18.1 have identical bytes for `Entity`, `LivingEntity`, `Player`, `LocalPlayer`, `FluidState`, `Fluid`, `Fluids`, `FlowingFluid`, `FluidTags`, and the relevant world readers. Between 1.18.1 and 1.18.2, `Entity.updateFluidHeightAndDoFluidPushing` and `getFluidHeight` migrate the map key/parameter from `Tag<Fluid>` to `TagKey<Fluid>`. The scan order, box deflation, floor/ceiling bounds, surface-height comparison, depth scaling, flow averaging/normalization, small-motion floor, coefficient multiplication, and delta/map writes are unchanged (`Entity.java`: 1.18.1 2756-2821; 1.18.2 2769-2834).

`FluidState.is` changes its membership implementation from `Fluid.getType().is(Tag)` to `builtInRegistryHolder().is(TagKey)`. For a given loaded tag assignment, the old `Tag.Named`/`TagCollection` lookup and new holder-key lookup both test that same tag membership. The source `FluidTagsProvider.addTags` assignment is identical in all three decompilations: water contains `WATER` and `FLOWING_WATER`; lava contains `LAVA` and `FLOWING_LAVA` (`net/minecraft/data/tags/FluidTagsProvider.java`: 1.18/1.18.1 lines 18-19; 1.18.2 lines 16-17).

The ordered body state update is unchanged: `Entity.baseTick` clears `fluidHeight`, updates water first, updates lava second using `ultraWarm ? 0.007 : 0.0023333333333333335`, then samples eye state and swimming (`Entity.java`: 1.18/1.18.1 401-425; 1.18.2 403-426). Water updates `wasTouchingWater`, resets fall distance and clears fire on a positive immersion scan; lava membership retains the `!firstTick` gate in `isInLava`.

`touchingUnloadedChunk` inflates the entity box by `1.0`, floors minimum and ceils maximum X/Z, and calls the four-coordinate `LevelReader.hasChunksAt`; this is the horizontal all-chunks-present overload, not the six-coordinate height-aware overload. Its false return occurs before scan/map writes. Separately, `Level.getFluidState` returns `Fluids.EMPTY` for an out-of-build-height position before chunk lookup; `LevelChunk` then selects the section and `LevelChunkSection` returns that block state's fluid state. These providers are textually unchanged across the edges (`Entity.java`: 2823/2836; `Level.java`: 380/379; `LevelChunk.java`: 209-230; `LevelReader.java`: 188/189; `LevelChunkSection.java`: 49/50).

### Eye-water/lava current/previous state

**Verdict: ACCEPT the source-level storage/query change; REJECT the claimed vanilla-player movement consequence as currently phrased.**

1.18→1.18.1 has no difference. On 1.18.1→1.18.2, `Entity.updateFluidOnEyes` changes from a nullable one-tag field populated by the first matching `FluidTags.getStaticTags()` entry to a set cleared and populated from every tag on the fluid state's registry holder when the eye-surface gate passes. `isEyeInFluid` changes from `fluidOnEyes == tag` to set membership (`Entity.java`: 1.18.1 1061-1080, 1140-1142; 1.18.2 1071-1083, 1143-1145). In both versions `wasEyeInWater` snapshots the prior current membership before the current eye sample is cleared/rebuilt. `Player.tick` samples `wasUnderwater` before `super.tick`; `Player.updateSwimming`, `Entity.updateSwimming`, and `LocalPlayer.updateIsUnderwater`/`isUnderWater` keep the same ordering and bodies in the compared releases.

The source default provider lists disjoint water and lava fluid members. Player/LocalPlayer/LivingEntity eye queries in the examined source call `isEyeInFluid(FluidTags.WATER)`; no vanilla player movement path calls `isEyeInFluid(FluidTags.LAVA)`. If an external tag assignment puts a fluid in both WATER and LAVA, the old fixed order selects WATER and the new set can additionally report LAVA, but the WATER result and its prior-water snapshot remain true in both. A custom tag key is newly retained by the set, but no vanilla player movement caller queries such a key. Therefore the source change is real at the generic eye-tag query boundary, while this audit establishes no vanilla-player movement difference from it. External data can affect membership; it must not be used to turn the generic API delta into an unconditional player movement finding.

The author eye-state artifact accurately identifies the representation change and retained previous-sample order. Its final movement statement that player underwater/swimming predicates “may differ” under WATER/LAVA overlap is not supported by the vanilla player call graph just described. A source finding can remain conditional as a generic eye-tag behavior change; player movement applicability needs a concrete vanilla player consumer whose queried membership changes.

### Ordinary non-flight water/lava travel

**Verdict: ACCEPT — no player movement difference on either edge.**

1.18 and 1.18.1 travel source is byte-identical. In 1.18.2 the ordinary water/lava branches preserve the effective-AI/local-control gate, `isInWater`/`isInLava` and `isAffectedByFluids` gates, depth-strider and dolphins-grace adjustments, acceleration/move order, water and lava drag, falling adjustment, gravity and horizontal-collision exit impulse (`LivingEntity.travel`: 1.18/1.18.1 2005 onward; 1.18.2 2009 onward). The only in-scope predicate call input change is `canStandOnFluid(Fluid)` to `canStandOnFluid(FluidState)`. The base method returns false in both APIs; `Player` and `LocalPlayer` do not override it. `Strider` is the only exact-source override and is outside player scope. The new argument therefore does not alter the vanilla player's branch result.

The methods `getWaterSlowDown`, `getFluidFallingAdjustedMovement`, `Player.travel`, `Player.isAffectedByFluids`, `Player.isPushedByFluid`, and water/lava current/surface providers are unchanged for this scope. A separate 1.18.2 edit changes numeric typing in the fall-flying branch of `LivingEntity.travel` (`Mth.cos`/float to `Math.cos`/double); it is excluded flight physics and is not a water/lava travel finding.

### Post-move and player state closure

`Entity.move` calls `checkFallDamage`; `LivingEntity.checkFallDamage` performs another `updateInWaterStateAndDoWaterCurrentPushing()` call if `!isInWater()`. This second possible water scan/current push is unchanged (1.18/1.18.1 `LivingEntity.java:286`, 1.18.2 `:290`; caller `Entity.move`: 1.18/1.18.1 `:579`, 1.18.2 `:593`). `Player.updateIsUnderwater` and player/local-player swimming/underwater consumers were additionally checked and have no movement-affecting difference in these releases.

## Bounded coverage and exact open dependencies

Examined: both adjacent edges; entity base-tick fluid update order; water/lava flags and fluid-height map; unloaded-chunk and build-height sampling paths; fluid-surface and flow providers; tag defaults and runtime tag binding path; player and local-player eye-water state order; ordinary player water/lava travel, drag, and exit impulse; post-move water current retry; relevant type/predicate overrides.

Not examined as behavior: flight, liquid jump, non-player physics, boat physics, effects/damage, rendering/audio, fluid propagation, generic collision/geometry implementation, or actual in-world dynamic tag/dimension data. The flight-branch type change is recorded as an exclusion. No tests, build, game, server, or runtime launches were performed.

Exact unresolved dependency: the pinned raw client/mapping bytes and actual default/runtime tag and dimension resource payloads are absent locally. This blocks independent raw re-decompilation and claims tied to a specific running data-pack/tag/dimension configuration, but it does not invalidate the verified, manifest-matched Java-source comparisons above. No runtime parity verdict is made.