# Independent source audit: fluid query admission and dimension minY

**Reviewed draft:** `.native-source/20261028/frontier-c/1165-1171-fluid-miny-admission/fluid-miny-admission.md`

**Reviewed draft SHA-256 before this audit:** `B60F7C80248211FA2341F866153B8F89B7881AC96BAA6C71DB75CEEC398D1EE2`

**Audit scope:** 1.16.5 → 1.17.1 Mojmap endpoint sources in the canonical read-only tree `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft`. I independently retraced single-cell fluid query admission, entity fluid-scan preflight and sampled-cell path, the 1.17.1 `updateSwimming` addition, and the `LocalPlayer.tick` route. This audit does not establish an intermediate patch boundary, fluid contents, runtime behavior, trajectory, or movement parity.

## Evidence identity

Both selected version roots contain `mojmap.ready.json`, `mojmap.provenance.json`, `mojmap.sources.sha256`, and `artifacts.sha256`. The ready markers identify the selected exact versions, Mojmap namespace, and client JAR hashes recorded in the draft. Their source-manifest hashes are `9499f2611d0e6dde37cb635f1a28a591416382d6bc188b37d5f99e2b2a20823b` (1.16.5) and `93270d229acfb751bf56daf1e7be26ce3dcff26b29e94aa157de621405a3463b` (1.17.1); their artifact-manifest hashes are `f9b9812d6995012cefcc1201a63855e5931e8551b508753be6c46d442d8b370c` and `e52c5dbae7d9663190ccc55a4f9b44a8e0615fb1fbbd8280811df43f70ec8aaa`, respectively. Each value matches the SHA-256 of its corresponding manifest file and the value in that version's ready marker.

Both provenance records identify the same exact run: version batch `1.15.2,1.16.5,1.17.1,1.18.2`, `--mappings=mojmap`, Java `25.0.3+9-LTS`, Vineflower `1.12.0`, created `2026-10-07T14:47:33.0330302Z`. The records have `mappingArtifacts: null`. The artifact manifests list `client_mappings.txt` and `client-mojmap.jar` hashes, but neither file is present at the recorded cache paths for either version, so those mapping-artifact bytes could not be independently rehashed. The manifests retain claimed hashes only; this audit does not treat them as byte-verified.

I recalculated the selected Java file hashes and confirmed they match the corresponding `mojmap.sources.sha256` entries. This covers every Java file cited by the draft, including `Entity.java`, `LivingEntity.java`, `Player.java`, `AbstractClientPlayer.java`, `LocalPlayer.java`, `ClientLevel.java`, `Level.java`, `LevelReader.java`, `BlockGetter.java` and, in 1.17.1, `LevelHeightAccessor.java`. The evidence therefore supports the draft's source-hash and source-publication claims, subject to the mapping-artifact limitation above.

## Independent retrace

### Single-position fluid admission

- **1.16.5:** `net/minecraft/world/level/Level.java`:158–164 defines outside-build-height as `y < 0 || y >= 256`. `Level.getFluidState(BlockPos)`:378–385 returns `Fluids.EMPTY.defaultFluidState()` when outside, otherwise reading the chunk's fluid state. Admission is fixed `[0, 256)`.
- **1.17.1:** `net/minecraft/world/level/LevelReader.java`:65–75 derives min and height from `dimensionType().minY()` and `.height()`. `LevelHeightAccessor.java`:11–12 defines max as `min + height`; :27–32 rejects `y < min` or `y >= max`. `Level.getFluidState(BlockPos)`:387–394 returns the empty default fluid state outside that interval and otherwise reads the chunk. The upper edge is exclusive. This is query admission for the active dimension type, not evidence of the fluid state returned for an admitted cell.

### Entity scan preflight and sampled cells

- **1.16.5:** `Entity.updateFluidHeightAndDoFluidPushing(Tag<Fluid>, double)`:2641–2651 deflates the bounding box by `0.001`, computes floor minima and ceil maxima, and calls `hasChunksAt(i, k, m, j, l, n)`. At `LevelReader.java`:168–186, the callee's vertical preflight is `m >= 0 && j < 256`. With the call's argument order this means `ceil(box.maxY) >= 0 && floor(box.minY) < 256`; it can admit a fractional box whose actual samples are below Y=0. The method's loops :2660–2665 sample the half-open integer envelope and call `getFluidState` per cell, where the fixed query gate can return empty.
- **1.17.1:** `Entity.updateFluidHeightAndDoFluidPushing(Tag<Fluid>, double)`:2773–2797 first rejects when `touchingUnloadedChunk()` is true, then computes the same `0.001` deflated bounds and half-open floor/ceil cell loops. `touchingUnloadedChunk()`:2840–2846 instead inflates by `1.0` and tests horizontal X/Z chunks through the four-argument `hasChunksAt`. Its preflight has no vertical bound. The six-argument `LevelReader.hasChunksAt`:185–187 still includes a dimension-height preflight, but this fluid method does not call it. Its individual sampled cells pass through `Level.getFluidState`, where out-of-range positions receive the empty default state.

The two checks have distinct meanings: scan admission controls whether the entity loops at all; single-cell admission controls whether a sampled coordinate reads chunk fluid state or receives the empty fallback. Neither check identifies cell contents. The draft states this distinction correctly.

### Other direct fluid queries and swimming

- `Entity.updateFluidOnEyes()` directly samples `getFluidState` in both releases (1.16.5 `Entity.java`:981–1006; 1.17.1 :1090–1107), subject to its separate boat-overlap return. The sample consequently inherits each version's single-cell admission rule.
- In 1.16.5 `Entity.updateSwimming()`:949–955 sets swimming from sprinting, cached water/underwater state and passenger status. In 1.17.1 :1056–1064, the not-already-swimming branch adds `level.getFluidState(blockPosition).is(FluidTags.WATER)` as a final condition after the same sprint/underwater/passenger conditions. That is a direct query using the 1.17.1 active-dimension admission rule. This finding concerns the query predicate; no water or other fluid contents were inspected.

### Local-player tick route

In both versions `ClientLevel` extends `Level` (1.16.5 `ClientLevel.java`:74; 1.17.1 :80). The class declarations establish `LocalPlayer → AbstractClientPlayer → Player → LivingEntity → Entity` (1.16.5: `LocalPlayer.java`:79, `AbstractClientPlayer.java`:21, `Player.java`:111, `LivingEntity.java`:111; 1.17.1: `LocalPlayer.java`:80, `AbstractClientPlayer.java`:23, `Player.java`:113, `LivingEntity.java`:117).

`LocalPlayer.tick()` calls `super.tick()` only after a horizontal chunk check (1.16.5 `LocalPlayer.java`:184–186; 1.17.1 :191–193). In 1.16.5 it supplies a block position at Y=0 to `hasChunkAt(BlockPos)`; in 1.17.1 it calls `hasChunkAt(x,z)` directly. Both overloads are horizontal chunk gates and impose no player-Y admission. `Player.tick()` calls `super.tick()` (1.16.5 :233–234; 1.17.1 :246–247); `LivingEntity.tick()` also calls `super.tick()` (1.16.5 :2124–2126; 1.17.1 :2218–2220). The Entity layer performs fluid updates during its inherited tick: 1.16.5 `Entity.tick()` calls `updateInWaterStateAndDoFluidPushing()`, `updateFluidOnEyes()` and `updateSwimming()` at :380–382. In 1.17.1 `Entity.tick()` delegates to `baseTick()` (:391–393), which calls those operations at :415–417. Thus the direct admission/query differences are reachable through the ordinary `LocalPlayer` inherited client tick when its horizontal chunk gate passes. `LocalPlayer.aiStep()` also reads cached water/eye-in-fluid state (1.16.5 :733–739; 1.17.1 :755–761). No distinct LocalPlayer fluid-scan override was found in the traced route.

## Verdict

The bounded source claims in the reviewed draft are supported by the exact endpoint source bodies and their hash-verified source-manifest entries. The scan-preflight distinction, the added 1.17.1 swimming query, and the local-player route all retrace as stated. I found no source-level correction required within this slice. Keep the explicit limitation that mapping artifact content cannot be rehashed from the recorded cache, and keep query admission separate from fluid contents. This is an endpoint source audit only; it does not accept runtime or parity claims or prove the exact change release inside 1.16.5–1.17.1.
