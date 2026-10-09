# Independent MCPK-aware review: 1.13.2 player water-current impulse

**Review base:** immutable commit `2154ef3f483aa201b27b09358196d83e5b12e9d1`.

**Technical disposition:** `REVISION REQUIRED` for the current-flow memo's bounded finding. The central source interpretation is accurate. One statement about flying players is unsupported by the method's local dataflow, and the dependency/consumer evidence should be made explicit before source acceptance. The artifact mismatch identities are verified as recorded.

**Process qualification:** This report is not eligible as a clean independent reviewer record. Broad repository searches earlier in the session incidentally emitted excerpts from normal source-pair and Minecraft Wiki-lane reports, contrary to the lane restriction. Those excerpts were not used in adjudication; the source checks below were repeated directly against the assigned memos and exact ready files, and the MCPK claim boundary was taken from the MCPK lane. A clean reviewer should issue any formal acceptance after reviewing the exact input identities independently.

This review does not establish a first changed release, any water-current trajectory, runtime parity, or equivalence between the revised derived JAR and its unavailable original. No bytecode was inspected.

## Immutable input identities

| Input | Author commit | Git blob | Raw file SHA-256 |
|---|---|---|---|
| `workflows/source-campaign-2026-10-07/source-memos/fluid-current-player-impulse-1.13.2-2026-10-09.md` | `aac1ea3010da3de7956130b2a008e3f8af755708` — JustAlittleWolf | `4a347380378ea1acebce15f56c3c9ab8f7f22384` | `9138b0314678d5c116b90a23006777e62397139f0b9c0bb01487c663f6743e4d` |
| `workflows/source-campaign-2026-10-07/source-memos/incident-1.13.2-mapped-jar-manifest-mismatch-2026-10-09.md` | `04cf58e28f23ac43dea67f07f005bac31c5baf25` — JustAlittleWolf | `84c85b66c3512d27e30a729051cc1d26c5f43998` | `f0dbfa7828ea084578b2abbb8f7a67f07de088f357c4c52d8ab17ea2571965c9` |

Both files are present with those blobs at the review base. The incident path was resolved from that commit's tree. The assignment scope was the two memo artifacts, the exact 1.13.2 ready source set, and existing MCPK-lane provenance. The process qualification above records the accidental cross-lane exposure. The source checks below were made directly against the assigned memo and ready files.

## Source and artifact identity checks

The canonical ready root is `build/movement-campaign-2026-10-07/ready/1.13.2/ornithe-feather`. Its ready marker hashes to `1d1c644c1deb05c02d11dc575ad834f4f72489124701d9b2b90357f43dc37b38`, and its `ornithe-feather.sources.sha256` hashes to `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`, matching the marker. The marker identifies version `1.13.2`, mapping `ornithe-feather`, input client hash `3410887ba652f25792c7675bfaf9140e73b60e93cfbf113a803f8a98cb05c0f9`, and artifact-manifest hash `fdcacd9150f98ea70acafc5cab754027ab1a09828ff0eaa8e542dd7890d9ce1e`.

Each source file listed below was hashed directly and its value matched the exact row in that source manifest:

| Source file | SHA-256 |
|---|---|
| `net/minecraft/entity/Entity.java` | `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269` |
| `net/minecraft/entity/living/LivingEntity.java` | `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c` |
| `net/minecraft/entity/living/player/PlayerEntity.java` | `4ed22f6c5a3c55d67eed782070ac722201df4d624adbc90779f1fd29c2876633` |
| `net/minecraft/fluid/FlowingFluid.java` | `2398d109ddc5785cb1bc8872b8c157319137df8957faf8a03581a8f68afb1253` |
| `net/minecraft/fluid/state/FluidState.java` | `05e2df9377a3bc1e91645ce4282bcaa4edebe93d7ef1907407961baaecff23a6` |
| `net/minecraft/fluid/WaterFluid.java` | `183894d0a4f7a85becd97eb2c0f6cedc1d5adc4648dc6d3d2b5624df173df28b` |
| `net/minecraft/util/math/Vec3d.java` | `6941a43536e8aefaa435a4e5f1731b0fa26a9d96503d1f4d07de8950c297f3c5` |
| `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29b29d8d9bf42bf` |

The incident's identity chain also checks out. The ready marker binds the artifact manifest at the hash above, and that manifest records expected derived JAR hash `d118ff5b9eb93fed06680466cb26c41a30647cdb2fc8744638cf9b35cf7864e5`. The current file at `build/movement-campaign-2026-10-07/artifacts/1.13.2/client-ornithe-feather.jar` hashes to `b28c33e023928045c8fd7ed7727a860a6820241e371ddb16108366c6192b718c`. The retained `feather-r1-2026-10-07/1.13.2/ornithe-feather/client-ornithe-feather.jar` and its `artifact.sha256` sidecar have that same `b28c…718c` value. Its `revision.json` records the original derived artifact as unavailable, the source tree and raw inputs as unchanged, and explicitly leaves equivalence unproven. This verifies an expected-versus-current byte-identity mismatch; it does not say which JAR is correct or support a bytecode claim.

## Current-flow source review

The bounded operation in `Entity.m_69693160(FluidTags.WATER)` is accurately described at lines 2509–2570. It contracts the entity box by `0.001`, scans cells, and admits a matching fluid cell only when its surface reaches the contracted lower bound. It updates `d` to the greatest overlap depth seen so far before obtaining that cell's flow. When `d < 0.4`, it scales the cell vector by that running maximum. It adds each admitted vector and increments `o`; for a nonzero accumulated vector it scales by `1.0 / o` when `o > 0`, then normalizes only for non-players. The player exception therefore retains the averaged vector's magnitude. The final gate is accumulated-vector length `> 0.0`, followed by X, Y, and Z velocity additions in that order, each using the literal `0.014`.

`FluidState.getFlow()` delegates to the fluid. `WaterFluid` extends `FlowingFluid` and has no `getFlow()` override. `FlowingFluid.getFlow()` forms horizontal components from neighboring fluid-height differences, has the falling-fluid solid-face adjustment, then normalizes its result. `Vec3d.normalize()` returns zero below length `1.0E-4`; that helper is part of the exact vector behavior and is why its source hash is included here.

The player gate is exactly as stated: `Entity` snapshots `hasLiquidCollision()` before scanning, and `PlayerEntity.hasLiquidCollision()` returns `!abilities.flying`. With that gate false, this method adds no cell vectors. Its local accumulator starts at `Vec3d.ZERO` and has no other writer, so for a flying player it remains zero and the final impulse block is skipped. Water presence/depth scanning still runs and returns normally.

The movement consumer is reachable in the local-player tick. `LocalClientPlayerEntity.tick()` calls `super.tick()` when its chunk is loaded. `PlayerEntity.tick()` calls `super.tick()` after water-submersion refresh; the living/entity superclass path reaches `Entity.baseTick()`, which calls the water-state updater and then the current scan. After the superclass tick returns, `LivingEntity.tick()` calls `mobTick()`. Its movement dispatch calls virtual `moveRelative()`; the player override delegates to the living implementation, whose water path calls `move(MoverType.SELF, velocityX, velocityY, velocityZ)` before applying water drag. Thus a non-flying local player with a qualifying current vector can consume the added velocity in that same tick. This is source-path confirmation only, not a measured trajectory.

MCPK provenance remains bounded to Water and Lava revision 3122: the recorded page is marked WIP and leaves X/Z flow as TODO. Its direct page URL was unavailable in this session; the existing MCPK-lane follow-up records the revision and its limited claim. This memo must not be expanded into a claim that MCPK establishes full 1.13 water math.

### Required memo correction

The sentence saying that the flying gate “does not suppress … the final impulse block if another route produces a nonzero accumulated vector” should be removed or rewritten. In this method the accumulator is local, starts at zero, and is only changed inside the `hasLiquidCollision()` gate; there is no alternate writer. The following sentence already gives the correct result for flying players.

For source-acceptance readiness, add the `Vec3d.java` hash above to the memo's source identity table, and record the same-tick `Entity.baseTick()` → `LivingEntity.mobTick()` / player `moveRelative()` consumer path. The evidence here closes those two checks without changing the immutable input memo.

## Review angles

1. **Requirement:** Bounded 1.13.2 water-current producer and the separate provenance incident are addressed; no pair completion, release cutover, or trajectory is claimed. No scope defect found.
2. **Correctness and edge cases:** Averaging, shallow-depth scaling, player normalization, the flight gate, the `0.014` component updates, and the same-tick movement consumer match source. One overbroad alternate-route sentence needs correction.
3. **Missing evidence:** The memo does not list `Vec3d.java`'s normalization threshold or trace the velocity into same-tick player movement; both are verified in this review and should be added before acceptance. Full fluid-cell and neighbor eligibility remain explicitly open.
4. **Conventions and duplication:** Source hashes and the ready marker/manifests are internally consistent; no duplicated or conflicting claim found in the bounded MCPK record.
5. **Permissions and visibility:** No permission or user-visible behavior change is proposed or reviewed.
6. **Security:** No introduced attack path or security behavior is in scope.

## Not verified or outside this review

- The original derived JAR bytes are unavailable; equivalence to the retained revised JAR is unproven. No bytecode from either JAR was inspected.
- Complete fluid-overlap geometry, every neighbor/solid-face eligibility case, lava behavior, other release sources, and the first source cutover remain open.
- MCPK's reported outcomes and any water-current trajectory were not independently reproduced. No runtime, TAS, client, server, build, or tests were run.
- The MCPK page fetch was unavailable in this session; its revision identity and WIP/TODO boundary rely on the already recorded MCPK-lane page capture.

## Decision record

- **Current-flow memo:** `revision-required` for the exact wording correction and source/consumer evidence additions above. The bounded source mechanism itself is confirmed.
- **Artifact incident:** Identity/provenance claims verified. The expected and actual artifact hashes differ exactly as recorded, while the available source hashes match the published source manifest. No repair or equivalence conclusion follows; formal independent acceptance remains pending a clean reviewer.
- **Pair status:** unchanged; no source-pair completion or runtime status is asserted.
