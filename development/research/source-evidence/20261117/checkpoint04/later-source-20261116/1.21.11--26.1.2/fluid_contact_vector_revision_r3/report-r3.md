# S4.6 fluid contact and current: bounded source revision r3

## Revision binding and exact evidence identity

- Revision r3 corrects the sign of the 0.001 contact-height origin delta and withdraws the impossible below-box contact interval from r2. Prior r2 SHA-256: `43e3a870e35cd9aa9fbe5dcb13623802b3bdabfe1956b90f103adae65e6414f6`. Source identities are unchanged; changed source logic and report payload were rechecked.

- Prior bounded report SHA-256: `06c147f2d8aa1ab53955fceae55210efcfe7120b59d6ec16916f7fcdf19c133f`.
- Endpoints: 1.21.11 Mojmap and 26.1.2 unobfuscated, exact ready declarations in `decompiled_minecraft/1.21.11/mojmap.ready.json` and `decompiled_minecraft/26.1.2/unobfuscated.ready.json`. Both declare Vineflower and Java 25. Provenance is in the corresponding root sidecars `mojmap.provenance.json` and `unobfuscated.provenance.json`.
- The requested dot-prefixed provenance and source-manifest spellings (`.mojmap.*`, `.unobfuscated.*`) are absent. The canonical root sidecars use names without a leading dot: `mojmap.provenance.json`, `mojmap.sources.sha256`, `unobfuscated.provenance.json`, and `unobfuscated.sources.sha256`.
- 1.21.11 ready source manifest SHA-256 `0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555` (6,622 rows) and ready-declared `artifacts.sha256` SHA-256 `c8210b15012dcc4e109c2f73015b4117abec1f8ab04fdd375dbc11419e9e246c` (257 rows) match the ready marker. 26.1.2 source manifest SHA-256 `54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0` (6,882 rows) and ready-declared `artifacts.sha256` SHA-256 `89f648229de83b0109460695c9f1f3bed7ef36efb84ede9224e80cce87118f92` (78 rows) match its ready marker. These manifests were rehashed; row counts match the declared counts. The absolute `outputPath` values in the ready markers are not present here; the canonical published source trees were read without modification.
- Root-sidecar hashes: 1.21.11 ready `0ad98d0ebd654650492c97eb58bc324d33864787f0ce10d99faec3ae2f5b804b`, provenance `99c9fb741eb5e4ac8fb5780e2158da324d7c8b6afc179d494e5986f78fe29f72`; 26.1.2 ready `f24af0f4d38543c2bd9c19b50f347d4e89d6027050e4b21ae070734843946bb7`, provenance `8d727f880cc91f4a297e148c5b75976303d051457334bc689268fbdf631a012a`.

## Compared source identities

Paths below are relative to each ready-declared source tree (`decompiled_minecraft/<version>/<mapping>/`). Each file's rehashed SHA-256 matches exactly one row in that release's source manifest.

| Version / mapping | Source file | SHA-256 |
| --- | --- | --- |
| 1.21.11 Mojmap | `net/minecraft/world/entity/Entity.java` | `32314478c6036fa9f3f3cc409c61c622eefc5a282d60e1011a1a33cc29cf18a3` |
| 1.21.11 Mojmap | `net/minecraft/world/level/material/FluidState.java` | `146a5e6a19a93da631a6f4ed57020791bde31087fcedc6d52c68b1c582b71b0f` |
| 1.21.11 Mojmap | `net/minecraft/world/level/material/FlowingFluid.java` | `905e5a02ab10b23b5f6e64a0f732ee552ae8895801e1944d9850a41cc8bce91a` |
| 26.1.2 unobfuscated | `net/minecraft/world/entity/Entity.java` | `8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf` |
| 26.1.2 unobfuscated | `net/minecraft/world/entity/EntityFluidInteraction.java` | `5264ff4f1fddebc3fa9d63ad2edbe2eaf617392ff946a867a6817a78b478ee62` |
| 26.1.2 unobfuscated | `net/minecraft/world/level/material/FluidState.java` | `e35608d5775013c67653ea350309b5150a97f36aa65c27f1c6247ae2aa840f18` |
| 26.1.2 unobfuscated | `net/minecraft/world/level/material/FlowingFluid.java` | `f8c0c4430e81efdf39b221968272ed8e3a4d030fa5192e04e04d34c9fbd37b28` |

## Bounded comparison

1. **Tick forwarding and ordinary contact path.** 1.21.11 `Entity.baseTick` reaches fluid contact/current handling before eye-fluid and swimming updates (`Entity.java` 1501–1522; report's earlier inspection covered the surrounding base-tick calls). 26.1.2 `Entity.baseTick` calls `updateFluidInteraction` before `updateSwimming` (`Entity.java` 503–525); `updateFluidInteraction` updates trackers, reads water/lava contact, resets fall distance on water entry, then applies water/lava currents (`Entity.java` 1558–1582). The old scan filters states by the requested fluid tag and gates contact by `fluidTop >= deflatedBox.minY` (`Entity.java` 3509–3573). The new scan uses the deflated interaction box and contact gate `fluidTop >= box.minY`, selects a tracker for a fluid/tag, and computes the tracker's maximum height from `fluidTop - entity.getBoundingBox().minY` (`EntityFluidInteraction.java` 32–89; default player/non-passenger box in `Entity.java` 4015–4024).

2. **Corrected height-origin difference.** Let `B` be an ordinary player's bounding box and `D = B.deflate(0.001)`. Deflating contracts the box, so `D.minY = B.minY + 0.001`. In 1.21.11, the scan tests `fluidTop >= D.minY` and updates the stored height with `max(fluidTop - D.minY, 0)` (`Entity.java` 3514–3537, 3572–3573). In 26.1.2, `getFluidInteractionBox()` supplies the deflated box for the bounded ordinary-player path; the contact test is also `fluidTop >= box.minY`, while `entityY` is captured from `B.minY` and tracker height is `max(fluidTop - entityY, priorHeight)` (`EntityFluidInteraction.java` 34–44, 58–72). Thus both paths require the same cell top to reach `B.minY + 0.001`. For the same accepted cell and unchanged box, when that cell establishes the running maximum, the newer stored height is 0.001 greater than the older value. This is a source-level difference in per-fluid contact height; it does not establish a contact-boolean difference.

3. **Conditional current consequence; contact-state sliver withdrawn.** The r2 interval `[B.minY - 0.001, B.minY)` cannot pass either contact gate because both require `fluidTop >= D.minY = B.minY + 0.001`; r2's proposed contact-state difference in that interval is withdrawn. For a same accepted cell, define the older height as `h = max(fluidTop - D.minY, 0)` when it establishes the running maximum. The newer height is then `h + 0.001`. Both current paths attenuate flow when their running height is `< 0.4` (old `Entity.java` 3534–3545; new `EntityFluidInteraction.java` 72–80). Therefore, if the cell establishes the maximum with `h < 0.4 <= h + 0.001`, the old path scales that cell's flow by `h`, while the new path leaves it unscaled; at `h = 0`, the old contribution is scaled to zero and the new one by `0.001`. This is only a conditional per-cell current-contribution difference for a nonzero flow and enabled current path, not an aggregate current or trajectory prediction. Fluid/tag membership, scan preflight and current-state dependencies remain open.

4. **Flow-height and vector formulas retained as no-difference findings.** `FluidState.getHeight` and `getFlow` forward directly to the fluid type on both sides (1.21.11 `FluidState.java` 53–59, 95–97; 26.1.2 `FluidState.java` 49–55, 95–97). The examined `FlowingFluid.getFlow` operations also match: horizontal-neighbor traversal; neighbor own-height comparisons; the empty-neighbor/below-fluid `ownHeight - (belowHeight - 0.8888889F)` term; horizontal accumulation in direction order; the falling-fluid solid-side-face addition of `(0, -6, 0)`; and final normalization (1.21.11 `FlowingFluid.java` 55–100; 26.1.2 `FlowingFluid.java` 55–100). `getHeight` remains `1.0F` when the same fluid is above, otherwise own height, and own height remains `amount / 9.0F` (1.21.11 `FlowingFluid.java` 434–446; 26.1.2 `FlowingFluid.java` 466–478). This revision preserves those no-difference findings: the newly identified delta is the entity-height reference in contact scanning, not fluid height/flow production or `FluidState` forwarding.

5. **Previously accepted boundary excluded.** The accepted tracker threshold/latch boundary is explicitly not re-audited or revised here. Its acceptance remains separate from this contact-height-origin claim.

## Partial / unexamined dependencies

Fluid/tag resource payloads, effective membership and data-pack overrides remain uninspected; the 26.1.2 tracker selects a per-fluid tracker through fluid-to-tag membership. Chunk/section loaded-state preflight and edge cases remain open. Passenger-specific box modification and other callbacks are not included in the ordinary non-passenger comparison. The two scanning/preflight designs and any behavior outside the paired accepted-cell comparison remain dependencies. No assertion of packaged resource equivalence is made.

Pair inventory remains partial. This revision does not establish intermediate patch onset, full-pair freeze, implementation acceptance or runtime parity.

## Bounded verdict and next gate

For an ordinary non-passenger player and the same accepted fluid cell, the 1.21.11 height uses the deflated-box minimum Y, while 26.1.2 uses the original bounding-box minimum Y, making the newer reported height 0.001 greater. Both gates require the fluid top to reach the deflated-box minimum; the r2 below-box contact-state sliver is invalid and withdrawn. The source formulas establish a conditional per-cell current-attenuation difference around the `< 0.4` gate, but do not predict aggregate current or trajectory outcome. The unchanged `FluidState` forwarding and `FlowingFluid` height/vector formulas remain no-difference findings; the already accepted tracker threshold/latch remains excluded. Fluid/tag membership and chunk/section preflight stay open. This immutable correction requires fresh independent audit.
