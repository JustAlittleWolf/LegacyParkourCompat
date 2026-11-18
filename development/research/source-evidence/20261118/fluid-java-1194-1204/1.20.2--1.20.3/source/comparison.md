# Fluid operations: 1.20.2 to 1.20.3

## Bounded result

No difference found in the assigned ordinary, non-riding local-player fluid operations when effective fluid-tag membership and queried world fluid inputs are held equal. No canonical finding is warranted for this slice.

The compared caller sequence is the same: `Entity.baseTick` updates fluid contact/current, then eye-fluid state, then swimming state; `LocalPlayer.aiStep` invokes `LivingEntity.aiStep`; for an ordinary non-riding local player, the latter dispatches to `LivingEntity.travel` instead of `travelRidden`. The comparison excludes riding and boat behavior.

## Source identity and terminal hashes

Both sides are exact Mojmap decompilations. 1.20.2 `mojmap.ready.json` pins `versionId=1.20.2`, Java 25, client SHA-256 `fa1a19be56a506426308abbc1cad85f299a7fc6dae4335559351ba0246713fda`, and source-manifest SHA-256 `e439ff7e1d3f1d31e01be1edd141f94d917a9dc7fa0de5f69904544cbbe62887`; `mojmap.provenance.json` identifies `mappingOption=mojmap`, with Mojmap artifact `1.20.2/client_mappings.txt` SHA-256 `d4e2eb2b32ae7bd2e95ea3a8391813a7b6795e20884cf01d989dce68f906e2fd`.

The 1.20.3 embedded `.decompile-provenance.json` pins version metadata `1.20.3.json` SHA-256 `05e727aac55594b6014e9714ad38fa16af09f075daf5efa0f5743f2453fa8fc2`, client JAR SHA-256 `bef21938224902dd925e2d89dcfe1d2540656d4cf7a5abd2b93e0402d135c655`, and official client mapping input `client.txt` SHA-256 `ad03c803d866062909dd378ed5f1611687dc0ed0a76cc7fd57e968a99d641e5f`. Its embedded provenance pins each compared source file; recomputed file hashes match those entries.

Exact terminal SHA-256 values for the compared Java files:

| Source | 1.20.2 | 1.20.3 |
| --- | --- | --- |
| `net/minecraft/world/entity/Entity.java` | `d7ee49aaea5e862b92508e767562cabc8f10515605e8fb67565c8c8d5c23b01b` | `07383522bff169938136638ef8c3244ca511b56ca4266913524f99f9821331b9` |
| `net/minecraft/world/entity/LivingEntity.java` | `5c481da1ffc8684c4b30171c92e61fad751e6a3a708d5bc6486ac9f96ff69828` | `f7bc53db24c1798f19f9bd6f6356c86d5e560e9c8e8aac60decaf15cc785e07d` |
| `net/minecraft/client/player/LocalPlayer.java` | `bb5cbfb03656a1866bb77c00431618befe792081223b35db4bd121ecbb151fd5` | `bb5cbfb03656a1866bb77c00431618befe792081223b35db4bd121ecbb151fd5` |

## Compared operations

- **Body-water admission and stored height:** `Entity.updateInWaterStateAndDoFluidPushing`, `updateInWaterStateAndDoWaterCurrentPushing`, `isInWater`, `isInLava`, and `updateFluidHeightAndDoFluidPushing` are equivalent in both versions (1.20.2 `Entity.java` lines 1143–1145, 1186–1208, 1290–1292, 3031–3095; 1.20.3 `Entity.java` lines 1144–1146, 1187–1209, 1291–1293, 3037–3101). The scan deflates the entity box by `0.001`, loops floor/ceil integer bounds, accepts matching fluid states whose surface is at least the deflated box minimum Y, and stores the maximum surface-minus-minimum-Y depth for that tag. Water contact then writes `wasTouchingWater` from the scan result (with the same boat exception, splash gate, fall-distance reset, and fire clear); lava admission is `!firstTick && storedLavaHeight > 0`.
- **Current and player normalization:** The same method accumulates matching fluid flow vectors, scales each flow by the current maximum depth when that depth is below `0.4`, averages by contributing-fluid count, and normalizes only when the entity is not a `Player`. The vector is then scaled by the supplied fluid factor; the same low-horizontal-speed/low-push minimum of `0.0045000000000000005` applies before adding it to velocity. The local player therefore retains the averaged flow magnitude instead of receiving the non-player normalization. Call sites use water factor `0.014`; lava uses `0.007` in ultra-warm dimensions and `0.0023333333333333335` otherwise.
- **Eye-fluid write order:** `Entity.updateFluidOnEyes` has identical order (1.20.2 `Entity.java` lines 1210–1222; 1.20.3 lines 1211–1223): write `wasEyeInWater` first, clear `fluidOnEyes`, sample at `getEyeY() - 0.11111111F`, and add the sampled state's tags only if its fluid surface is strictly above that sample. The excluded boat check is retained identically in source.
- **First ordinary water/lava travel:** `LivingEntity.travel` is equivalent (1.20.2 `LivingEntity.java` lines 2038–2101; 1.20.3 lines 2035–2098). It requires `isControlledByLocalInstance`; water wins the branch order before lava; both require `isAffectedByFluids()` and `!canStandOnFluid(feetFluid)`. Water uses sprint slowdown `0.9F` or base `getWaterSlowDown()` `0.8F`, depth-strider capped at 3 and halved off ground, the same depth-strider interpolation, Dolphin's Grace override `0.96F`, move-relative then move, horizontal climbable lift, `(drag, 0.8F, drag)`, and fluid-falling adjustment. Lava uses relative acceleration `0.02F`, moves, then uses the same jump-height threshold branch: at/below threshold apply `(0.5, 0.8F, 0.5)` and fluid-falling adjustment; above threshold scale all components by `0.5`; apply quarter gravity only when not no-gravity; then use the same collision-clearance lift. The shared initial gravity is `0.08`, reduced to `0.01` only when vertical velocity is nonpositive and Slow Falling is present. LocalPlayer inherits this travel implementation; its `aiStep` calls `super.aiStep` in both releases.

## Further relevant gates and residuals

The bounded equivalence depends on equal queried fluid states, tag membership, fluid surfaces/flows, bounding box, current velocity, and dimension ultra-warm status. `updateFluidHeightAndDoFluidPushing` returns false before scanning if the entity box inflated by `1.0` touches an unloaded chunk; it consults `isPushedByFluid` before collecting flow, and the base implementation returns true. The positive-depth/surface test, `firstTick` lava suppression, and water-before-lava branch priority affect admission. Travel also depends on local control, fluid-effect eligibility, standable-fluid result, Slow Falling, vertical velocity, sprint state, ground state, Depth Strider, Dolphin's Grace, eye height/jump threshold, no-gravity, horizontal collision, climbability, and free-space checks. These predicates and constants were inspected on both versions and match for this scope.

This is a bounded source equivalence only. It does not claim packaged-resource equivalence, close the full player tick inventory, validate all callers/subclasses or data-driven fluid membership, or establish runtime parity. No source implementation cutover or intermediate patch boundary is inferred.