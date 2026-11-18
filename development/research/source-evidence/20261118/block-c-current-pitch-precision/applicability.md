# Applicability study: direct local pitch precision, 1.13–1.16.5

## Disposition

The supplied exact-source chain covers every adjacent physical release edge from 1.13 through 1.16.5 for the direct local pitch update, its current-pitch clamp, and the corresponding previous-pitch update/clamp. Across that range, the local turn path uses a `double` pitch delta (`input * 0.15`), adds it to the current float pitch in double precision, narrows the sum once to `float`, clamps current pitch to `[-90.0F, 90.0F]`, then independently updates and clamps previous pitch. The adjacent comparisons report no change to that sequence inside the range.

The source-confirmed behavior change is at the checked 1.16.5→1.17 edge: 1.17 narrows the input to `float` before multiplying by `0.15F`, performs the pitch addition in float precision through the first `setXRot`, and clamps current pitch afterward. A legal mouse-call witness gives different, unclamped in-range current pitches and a different next dry Elytra movement request. This supports restoring the older current-pitch arithmetic for selected profiles through 1.16.5, subject to keeping the implementation limited to the audited operation. It does not prove a patch cutover inside the 1.16.5→1.17 interval beyond that exact edge.

The previous-pitch field follows the same old double-versus-new float arithmetic at the 1.16.5→1.17 source boundary, but the accepted movement witness is about current pitch. In the cited Elytra path, movement reads current pitch; previous pitch is not its input. Therefore previous-pitch precision is not needed to support the in-scope physics consequence. If an implementation/finding explicitly claims restoration of the full current-and-previous rotation method, the independent 1.16.5→1.17 audit must be extended to accept the old-angle write as a claim; see the narrow gap below. Rendering/interpolation and camera history are outside the movement contract.

## Exact release coverage and enum aliases

The exact adjacent comparisons are:

`1.13→1.13.1`, `1.13.1→1.13.2`, `1.13.2→1.14`, `1.14→1.14.1`, `1.14.1→1.14.2`, `1.14.2→1.14.3`, `1.14.3→1.14.4`, `1.14.4→1.15`, `1.15→1.15.1`, `1.15.1→1.15.2`, `1.15.2→1.16`, `1.16→1.16.1`, `1.16.1→1.16.2`, `1.16.2→1.16.3`, `1.16.3→1.16.4`, and `1.16.4→1.16.5`.

Thus the covered physical releases are exactly: `1.13`, `1.13.1`, `1.13.2`, `1.14`, `1.14.1`, `1.14.2`, `1.14.3`, `1.14.4`, `1.15`, `1.15.1`, `1.15.2`, `1.16`, `1.16.1`, `1.16.2`, `1.16.3`, `1.16.4`, `1.16.5`. The earliest product profile alias is `ParkourVersion.V1_13` (exact 1.13); `V1_13_1` groups 1.13.1 and 1.13.2. The later aliases are `V1_14`, `V1_14_1`, `V1_14_2` (1.14.2–1.14.4), `V1_15` (1.15–1.15.1), `V1_15_2`, `V1_16` (1.16–1.16.1), and `V1_16_2` (1.16.2–1.16.5). The next exact source fence is `V1_17`, where the precision changes. These enum groups identify selected-profile aliases; they do not prove a `@MovementChange` annotation choice or permit widening the assignment before 1.13.

The pre-1.13 float API/input-device route is outside this assignment. The evidence starts at exact 1.13 for the bounded operation and should not be used to claim that pre-1.13 caller plumbing has been restored.

## Operation and numeric boundary witness

- In 1.13 / Feather, the local path is named `Entity.updateLocalPlayerCamera(double,double)`. Exact pair audits record float pitch fields and a double `d = cursorDeltaY * 0.15`; current pitch is assigned `(float)(pitch + d)`, clamped, and `lastPitch` is independently assigned `(float)(lastPitch + d)` and clamped. It is not copied from the new current pitch.
- Later aliases rename the operation (`Entity.changeLookDirection(double,double)` in the cited 1.14 Yarn sources and `Entity.turn(double,double)` in Mojmap sources). The arithmetic/order is preserved through 1.16.5: double multiply and addition, one float narrowing per current/previous value, current clamp before old-pitch update, then old-pitch clamp. The checked 1.16.4 and 1.16.5 Java files for this slice are byte-identical.
- At the 1.16.5→1.17 boundary, the accepted lawful mouse witness uses sensitivity 1.0, a +1.0 accumulated Y event after cursor initialization, and current pitch `-4.0696001052856445F`. The actual `Entity.turn` pitch input is `4.096000411987318` on both sides. The double path yields `-3.455199956893921F`; the float-narrow/product path yields `-3.4552001953125F`. Both values are within the clamp. The local dry active-Elytra path then converts current pitch to radians and through the source float sine table; the independently reviewed witness changes the vector passed to `LocalPlayer.move(SELF, ...)`. This is a source-derived movement-call result, not runtime parity.
- The broader raw-setter audit also checks finite large inputs and clamp ordering, but its `>360` intermediate case clamps to the same `90F` post-turn pitch. Do not use that case as the movement consequence of the precision change.

The complete current-and-previous method comparison at the boundary is described in the raw-setter report: 1.16.5 uses `double f = e * 0.15`, casts `(xRot + f)` once, and later does the analogous double addition/cast for `xRotO`; 1.17 uses `float f = (float)e * 0.15F`, current setter writes, then `xRotO += f` (float compound addition), with the later clamps and vehicle callback. The independent pitch-precision acceptance specifically establishes the changed current pitch and movement request, not the `xRotO`-only consequence.

## Independent finding/audit gate

All edges through 1.16.5 are present in the supplied archives. The two earlier archives document some exact-source publication qualifications (including 1.13.2 ready/provenance linkage and selected 1.14 Yarn/Feather sidecar/payload qualifications); these do not identify a missing physical release or an operation-body mismatch. Their verdicts bound those qualifications rather than asserting full-pair coverage. The 1.16.2→1.16.3 verdict also limits its rehash scope, while accepting the matching operation verdict. The 1.16.3→1.16.4 and 1.16.4→1.16.5 verdicts report byte-identical exact Java source slices.

The one precise additional acceptance needed only if the claim includes **previous-pitch numeric parity at the cutoff** is a dedicated independent check of `xRotO` at 1.16.5→1.17: confirm the 1.16.5 double product/add then narrowing versus 1.17 float input narrowing/product and float compound addition, and retain source order through the current pitch clamp, old-pitch write/clamp, and passenger callback. No other physical pair is missing from the stated interval. This additional camera-history claim is not required for the accepted current-pitch movement consequence and would otherwise expand into interpolation state outside scope.

## Existing implementation disposition and seam limits

The baseline disposition says the product currently has no ordinary turn-precision hook. The isolated raw-pitch-saturation candidate is a distinct, accepted 1.17–1.21.1 operation. Its concrete documented modern seam is the first `setXRot(float)` argument in modern `Entity.turn(double,double)`, via `@ModifyArg` and `EntityTurnPitchHook`; the documented providers are fenced at `ParkourVersion.V1_20_2` and `ParkourVersion.V1_21`. The candidate does not restore double-intermediate precision, change yaw, or adjust old-angle updates. No other modern seam is established by that disposition.

The saturation and precision ranges are disjoint by selected profile, and the saturation transform preserves finite in-range float bits. They can remain orthogonal only with distinct independently resolved hook interfaces/providers; reusing `EntityTurnPitchHook` would make the V1_20_2 fence compete with the precision resolver. The first-setter seam by itself also does not account for the old `xRotO` write. Any full-method precision claim must have access to the original double pitch delta (`yo`/`e`) and the pre-write float for each relevant write, without changing the order of current pitch, current yaw, current clamp, previous pitch/yaw, previous clamp, and the passenger notification. Do not combine the first and second `setXRot` calls or attribute raw saturation to the precision operation.

## Supplied immutable identities

SHA-256 was recomputed for each supplied bundle and matched the assignment:

- `c-raw-pitch-setter-189-1131-exact-evidence-20261118.bundle`: `04f9c854c2d7375b515f3707ed6b3c83034694c7b86b2d47f51158f9479173a3` (checked archive head `99aac33f9dd6c7d75075365fff97e227bbbb7c1d`).
- `c-raw-pitch-setter-1131-1162-evidence-final-20261118.bundle`: `67f6a661b26e73b271f31ecebd39d6175eed66d97c5c42dc55566ddcdf71d765` (checked archive head `8d2e74c77b82af37f14a4e44c8051d80093988b3`).
- `c-raw-pitch-setter-1162-1194-13edges-20261118.bundle`: `21af32782e3f8099d8301e438b8a0bbf71021a30b29cc42556043bfd87eb21bd` (checked archive head `9ef25ce2047fdeb52ea9b93e07c2e3a407800948`).
- `c-turn-precision-pitch-yaw-evidence-20261118.bundle`: `94d34e101039d0add02b2b88f951335a1120a53bdfe6559ab11131021f331846` (checked archive head `54d34113e2db3827eca267c57799edf18cb439d9`).

The first three archive directories preserve the reports/verdicts and edge inventories summarized above. The turn-precision archive supplies the independent 1.16.5→1.17 finite mouse/movement witness and its signed verdict. This report records applicability only: no product files, shared research records, source trees, tests, runtime, or network state were changed.

## Addendum: concrete current-pitch-only preflight

### Lower fence and exact target evidence

The separate lower-fence archive was rehashed and matches the assigned SHA-256 `950876e546956f69f15fbed2eeed4c6ae86fdbb42ccc09acec2ebe2e7f56096d`; its bundle HEAD is `46480232f23906ce03ad862e1b5eccfac3fd65cd`. Its signed 1.12.2→1.13 verdict accepts a sign-normalized ordinary-pitch witness: the old camera setter takes float input and narrows the input delta before its update, whereas 1.13 takes double cursor deltas and preserves the double `* 0.15` product until the single result cast. The adjacent float outputs cross a real next-tick active dry Elytra lookup and change its local movement request. This is the lower fence witness; it does not extend the assigned contract to pre-1.13 input plumbing.

For 26.2, canonical unobfuscated `Entity.java` SHA-256 is `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5`; the exact ready marker is `f9406adb6bf7cb4c1ab0792a798ab2cb90e082ad4c2eee032c9f674d0ea8070f`, with source-manifest SHA-256 `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894`. Read-only `javap -c -p` inspection used `C:\Users\Wolfi\.gradle\caches\fabric-loom\26.2\minecraft-client.jar`; its SHA-256 is `40896ee9f1e2bec3c934daac7e93d41e9e3d9c2f8ae0ca366d52ffbfd1afa290`, the same client artifact digest declared in the canonical 26.2 artifact manifest. No artifact was regenerated or downloaded.

The bytecode descriptor is `turn(DD)V`. In the instance-method slots, `xo` is loaded from the first double argument and `yo` from the second (`dload_3`); `yo` is narrowed and multiplied by `0.15F` for native `xDelta`. The first pitch call is `getXRot() + xDelta` → `setXRot(F)` at bytecode offsets 18–24. The setter invocation has not executed while a `@ModifyArg` callback runs, so the same target instance still exposes the pre-write current `xRot` through `getXRot()`. A MixinExtras argument-local capture with `@Local(argsOnly = true, index = 1) double yo` can obtain that original second method argument; this repo already uses `@Local(argsOnly = true, index = ...)` in its Mixin handlers. The callback can read current pitch before calling the existing hook. The target method also confirms the next pitch access is the later clamp call: after the first setter, only `getYRot`, `setYRot`, then `getXRot` for `Mth.clamp` execute. No movement/physics consumer reads pitch before that clamp. Previous pitch is touched only after it.

`setXRot(F)` in the exact target rejects non-finite values; otherwise it stores `Math.clamp(xRot % 360.0F, -90.0F, 90.0F)`. Thus a first argument already clamped into `[-90F,90F]` is stored unchanged by the target setter: the remainder is a no-op and the final clamp sees an in-range value.

### Minimal closed operation contract

For a finite ordinary local caller and finite current pitch, the historical current-pitch result can be supplied as the first setter argument, already clamped:

```java
double pitchDelta = originalYo * 0.15D;
float currentPitch = entity.getXRot();
float legacyPitch = Mth.clamp((float)(currentPitch + pitchDelta), -90.0F, 90.0F);
return legacyPitch;
```

This preserves the audited old order and widths: double multiplication by the double literal, promotion of current float to double for addition, one narrowing cast after the sum, then the float clamp. The modern setter's `% 360F` does not alter this already-clamped argument. The existing second turn clamp therefore stores the same current pitch as the historical operation. Finite `yo` large enough that the double sum casts to float infinity is also equivalent: the legacy float clamp produces finite `±90F`, which the setter accepts; no pitch consumer intervenes before the explicit second clamp. For finite mouse-input values, including the accepted legal caller witnesses, the transformation is closed. It does not preserve NaN behavior: legacy direct assignment followed by comparison clamp leaves NaN in `xRot`, whereas target `setXRot` rejects NaN and leaves the prior value. NaN is outside the accepted finite-ordinary-caller claim; including it would require a different write seam and a broader claim. `xRotO` is intentionally not changed: the accepted movement witness reads current pitch only, and camera interpolation is outside scope.

### Exact resolver fences and one-callsite composition

A minimal range encoding compatible with `ChangeResolver`'s “closest `emulates >= selected`” rule is one independent current-pitch precision hook with:

- an `@MovementChange(emulates = ParkourVersion.V1_12)` pass-through instance, fencing pre-1.13 selected profiles to native first-argument behavior; and
- an `@MovementChange(emulates = ParkourVersion.V1_16_2)` precision instance applying the formula above through all selected profiles from `V1_13` through `V1_16_2` inclusive.

`V1_12` covers 1.12–1.12.2; `V1_13` is exact 1.13; `V1_13_1` covers 1.13.1–1.13.2; and `V1_16_2` covers 1.16.2–1.16.5. With those two providers, earlier selections resolve to the pass-through, 1.13 through 1.16.5 resolve to double-intermediate precision, and `V1_17` plus later selections have no precision provider and retain native behavior for this operation. This is the exact source fence, without an exact-profile conditional in the mixin.

Keep the existing raw first-setter saturation contract on its own `EntityTurnPitchHook` interface. Use one thin `@ModifyArg` dispatcher at `Entity.turn(DD)V` → the first `Entity.setXRot(F)V` (`ordinal = 0`): if the precision interface resolves, pass current `xRot`, captured original `yo`, and the native argument to it; otherwise call the existing saturation interface; if neither resolves, return the argument untouched. That keeps one transformation at the shared callsite and keeps the technical operations independently resolved. Precision wins for 1.13–1.16.5; the lower-fence pass-through wins before 1.13; the precision provider is absent from 1.17 onward, leaving the existing saturation hook path unchanged for its accepted range. Do not add a second `@ModifyArg` at the same invocation, merge the hook interfaces, alter the second pitch setter, or write the prior-pitch field.

**Dispatch status:** Static source/bytecode evidence closes the current-pitch operation contract and demonstrates that the existing first-setter callsite can capture both required inputs. The caller can present an already-clamped finite result without changing the accepted movement result because no physics consumer runs before the final clamp. No product code or runtime check was performed.

**Separate raw-saturation candidate note:** The archived sanitized `c-raw-pitch-turn-candidate` has `EntityTurnPitchHook`, an `@V1_20_2` pass-through provider, and an `@V1_21` finite-clamp provider; it omits product mixins. The single-dispatch composition above leaves that hook path untouched, as requested. If those exact two candidate providers alone are being treated as the full accepted 1.17–1.21.1 implementation, their current resolver identities need a separate range audit: under the repository resolver, a selected `V1_17` would choose the closer `V1_20_2` pass-through over `V1_21`. This note does not change the precision contract, but the preserved candidate snapshot alone does not prove the wider raw-saturation range.
## Corrective addendum: local argument capture and saturation dispatch

This addendum is appended without editing any prior report bytes. The complete pre-addendum report SHA-256 was `8ae347fa0677eb0bbfe5952eedb02a256e9e90ca82d8c6dac89bfdab67d9da09`.

### Exact MixinExtras capture

The local Gradle cache's Fabric Loader `0.16.14` metadata declares `io.github.llamalad7:mixinextras-fabric:0.4.1`. Its cached runtime jar SHA-256 is `bb7042dd915cad67dc7c2ad0a4c0eabe6e097123785d7877beded6e0700f92ef`; `javap -v` on `com.llamalad7.mixinextras.sugar.Local` confirms the installed annotation has `ordinal`, `index`, and `argsOnly` members. The locally cached 0.5.4 source for the same annotation API documents `ordinal` as the zero-based occurrence among locals of the parameter type and `index` as the local-variable-table index; its source-jar SHA-256 is `cc5eba47bc9ba2134bf9673af0a48809ff4a78a2d16799c4180cfdffed970ef4`. The existing product also has a built-in `@ModifyArg` handler using `@Local(argsOnly = true, ...)` (`PistonMovingBlockEntityMixin.legacyparkourcompat$historicalCallbackScanOrigin`), demonstrating this injector/sugar composition in the current codebase.

For exact target descriptor `Entity.turn(DD)V`, JVM locals are `this` slot 0, `xo` slots 1–2, and `yo` slots 3–4. The pinned 26.2 bytecode independently shows `dload_1` for `xo` and `dload_3` for `yo`, then consumes `yo` to calculate the native pitch delta. Consequently, `@Local(argsOnly = true, index = 1) double yo` is incorrect: it selects the first argument (`xo`). The least brittle handler capture is `@Local(argsOnly = true, ordinal = 1) double yo`, because among the double-typed method arguments the second is the pitch input. Exact LVT-index alternative is `@Local(argsOnly = true, index = 3) double yo`. This is the original untouched double input, before any cast/product.

The callsite remains the first `setXRot(F)V` invocation (`@ModifyArg` argument index 0, target ordinal 0). At that callback, `this.getXRot()` is still the original current pitch because this first setter has not run. Therefore the handler can pass `(entity, entity.getXRot(), yo, nativeFirstSetterArgument)` through the one dispatch. The following instructions are yaw get/set, then the later pitch clamp/get/set; no physics consumer reads pitch before that final clamp. This establishes the capture and timing required by the current-pitch-only contract without a previous-field write or an additional injection.

### Corrected dispatch interpretation

The exact precision range remains selected profiles `V1_13` through `V1_16_2` inclusive (physical releases through 1.16.5), with the distinct `V1_12` pass-through floor provider. At the shared first-setter callsite, a single thin dispatcher uses the precision hook when resolved; otherwise it calls the existing `EntityTurnPitchHook` saturation hook, and if neither hook resolves it returns the native argument. The accepted earlier saturation implementation is the actual product commit `72adb0ed`, independently PASS with its native `V1_16_2` fence; the older sanitized candidate snapshot described in the previous note is not that implementation and its provider-only range caveat must not be applied to the accepted commit. Precision has no provider at `V1_17` or later, so the accepted saturation path is left intact there. This is a dispatch recommendation from the supplied accepted disposition, not a product edit or runtime claim.

**Dispatch status:** The local-capture concern is resolved: use `ordinal = 1` among `double` arguments (or exact local slot `index = 3`), not `index = 1`. Static pinned bytecode confirms argument order, descriptor, callsite ordinal, pre-write current-pitch timing, and lack of a pre-clamp physics read. No product file was modified, and no build or runtime test was run.