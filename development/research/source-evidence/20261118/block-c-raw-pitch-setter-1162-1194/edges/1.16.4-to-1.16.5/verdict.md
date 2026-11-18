# Independent source audit: direct pitch turn and Elytra read

## Frozen verdict

**No source change in the assigned operation between Minecraft 1.16.4 and 1.16.5.** The direct `Entity.turn(double, double)` path casts the accumulated pitch to `float` and then clamps the stored current and previous pitch to `[-90.0F, 90.0F]` in both releases, in the same operation order. A lawful finite input can produce an intermediate post-cast float greater than 360 degrees, but the outer clamp prevents that intermediate from becoming the stored pitch consumed by the next fall-flying movement. Do not treat the intermediate as a reachable Elytra pitch.

The compared source slice is complete for this assignment: ordinary local mouse turn caller; inherited raw pitch update and clamp; LocalPlayer-to-LivingEntity aiStep/travel dispatch; Player travel delegation; fall-flying branch and live pitch consumers; and the float clamp implementation. Device input is used only to bound the direct caller context. Scheduler, rendering, networking and other pitch writers were not examined.

## Exact source trace

All references below are Mojmap, exact-release decompiled source. Each referenced Java file is byte-identical between the two releases; the hashes are recorded below.

1. `net/minecraft/client/MouseHandler.java`, `turnPlayer()` (212–248): with the mouse grabbed and window active, sensitivity produces `g = f * f * f * 8.0`; the unsmoothed path sets `k = accumulatedDY * g`; inverted-Y selects `n = -1`, otherwise `n = 1`; if the player is non-null the only direct call is `player.turn(j, k * n)`. A valid witness context is sensitivity `0.5F`, smooth camera off, invert-Y off, `accumulatedDY = 2401.0`: the expression gives `f = 0.5`, `g = 1`, and finite `e = 2401.0`.
2. `net/minecraft/world/entity/Entity.java`, `turn(double d, double e)` (340–352): exact sequence is `f = e * 0.15`; `g = d * 0.15`; `xRot = (float)(xRot + f)`; `yRot = (float)(yRot + g)`; `xRot = Mth.clamp(xRot, -90.0F, 90.0F)`; then update `xRotO` and `yRotO` with their respective float casts and clamp `xRotO` to the same bounds. The `vehicle` callback follows. There is no normalization/modulo or alternate setter between the float cast and the clamp.
3. Finite witness from the direct caller above, starting at `xRot == 0.0F` and `xRotO == 0.0F`: `e * 0.15 = 360.15` as a finite `double`; the first cast yields the finite `float` nearest 360.15, which is greater than 360. The immediately following clamp stores `90.0F`. The independently updated `xRotO` follows the same cast-then-clamp result. Thus a post-cast `>360` intermediate exists, while no post-clamp `>360` pitch is produced by this operation.
4. `net/minecraft/util/Mth.java`, `clamp(float f, float g, float h)` (94–100): `f < g ? g : (f > h ? h : f)`. For the finite positive witness, the upper-bound result is exactly `90.0F`.
5. Local movement dispatch: `net/minecraft/client/player/LocalPlayer.java`, `aiStep()` calls `super.aiStep()` (788); `net/minecraft/world/entity/player/Player.java`, `aiStep()` calls `super.aiStep()` (500); `net/minecraft/world/entity/LivingEntity.java`, `aiStep()` invokes `updateFallFlying()` then `this.travel(...)` (2453–2460). `Player.travel(Vec3)` delegates to `super.travel(vec3)` in its non-creative-flying branch (1386–1417); `LivingEntity.travel(Vec3)` selects its fall-flying branch when `isFallFlying()` (1979 onward). This follows the real local player inheritance dispatch, not only the standalone base method.
6. Live pitch use in `net/minecraft/world/entity/LivingEntity.java`, fall-flying branch (1985–2008): `getLookAngle()` is read and `float j = this.xRot * (float)(Math.PI / 180.0)` is computed from the entity's current `xRot`; `j` then feeds the cosine and pitch-dependent velocity terms. `Entity.getLookAngle()` (1700–1702) calls `calculateViewVector(this.xRot, this.yRot)`, and `calculateViewVector` converts its supplied pitch directly (1243–1250). These are live fields, not interpolated view rotations. Given an active fall-flying flag and the ordinary direct-turn path, the next local fall-flying movement reads the clamped current pitch.

## Source byte proofs

For every listed path, the actual source bytes matched the 1.16.4 embedded source digest and the 1.16.5 `mojmap.sources.sha256` entry. Direct SHA-256 comparison also found the whole Java files identical across releases.

| Mojmap source path | SHA-256 in both releases |
|---|---|
| `net/minecraft/client/MouseHandler.java` | `2611dceb1078a63a33d621bf02f466ae70f6d015c902813935b9836f39415949` |
| `net/minecraft/client/player/LocalPlayer.java` | `6011569e766bb1568609147be9aa14e9c08c51948e3d3a60fd066e848f6a8c2b` |
| `net/minecraft/world/entity/Entity.java` | `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666` |
| `net/minecraft/world/entity/LivingEntity.java` | `b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88` |
| `net/minecraft/world/entity/player/Player.java` | `d2e26589bdb6a20dc914266db06aa48f50811efc792d6e63b3008c9199914960` |
| `net/minecraft/util/Mth.java` | `0e4424ffa12923314c309ae13e012768271dfcace4b5a84727f74e8170afe290` |

## Frozen source identity and canonical manifests

Both endpoints are exact `1.16.4` / `1.16.5` Mojmap decompilations. These are local canonical evidence paths, not regenerated during this audit.

### 1.16.4: hidden map-root provenance and local pins

- Source tree: `decompiled_minecraft/1.16.4/mojmap/`.
- Embedded provenance: `decompiled_minecraft/1.16.4/mojmap/.decompile-provenance.json`, SHA-256 `1e04555c915527a398a2c7150679fb24d056f54bd680261e3c1dd55da821147c`. Its `sources` object contains each matching digest above; its `inputs` identify the 1.16.4 client, official client mappings and exact-version metadata URLs/hashes.
- Local cache directory: `.minecraft-decompile-cache/1.16.4/`. The current `client.jar`, `client_mappings.txt`, and `version.json` bytes match their adjacent `.pin.json` SHA-256 pins: respectively `d6740464ee2bdf37d285c5d368d2e16497492427fb5783e2db3482bbf0079ed0`, `93f188a0038fec8c2402d78e7acca983680978d6a9237c1fab80f22caddb00fe`, and `667fff4ca7fd0f56ab78b757224515e8e64375868e8d1ce6040091d7b63a0cb5`.
- These existing cache pins establish the local content baseline; they do not independently prove historical download origin. The source tree's recorded provenance binds its decompilation to the pinned inputs and toolchain.

### 1.16.5: VERSION-ROOT legacy-ready evidence

- Source tree and manifests: `decompiled_minecraft/1.16.5/mojmap/`, `mojmap.provenance.json`, `mojmap.ready.json`, `mojmap.sources.sha256`, and `artifacts.sha256` at the version root. The ready record states `status=ready`, `versionId=1.16.5`, `mapping=mojmap`, 3,523 source files, source manifest SHA-256 `9499f2611d0e6dde37cb635f1a28a591416382d6bc188b37d5f99e2b2a20823b`, and artifact manifest SHA-256 `f9b9812d6995012cefcc1201a63855e5931e8551b508753be6c46d442d8b370c`. The actual manifest files match both recorded hashes.
- Immutable manifest-file identities observed: `mojmap.provenance.json` SHA-256 `b71df61eb775134119881915198f87c4337bae409e1f0de8d8d6c007e062902d`; `mojmap.ready.json` SHA-256 `e9dd4397c6ade5f68d27f50baf285086dc367b56d2119b03a116cf3cf3b23fee`.
- The artifact manifest identifies the exact client (`00b5ebbc33e95ea88c1ab80601599c9827e9aa861d93ccc2cfcbbfd996e86263`), official mappings (`7931ed6d723eceb1d621d05a76e10ddf643bf468c6ecf1c4ecf377bd72cf8b8c`), mapped client archive (`7c1a9f30983fa3c777d00116bdf3f3cef97c1c1aaf8294027052f5337fcde940`), and version metadata (`3eeeab7b3165cc5263dc26ff8fe114fdb718b75a0244efead9d19635d090ba72`). The usual `.minecraft-decompile-cache/1.16.5` raw-cache directory is absent; these version-root provenance, ready, source and artifact manifests are the available exact evidence.

## Boundary and disposition

This is a source-level equality verdict for the assigned ordinary direct turn → local Elytra pitch-read operation only. It identifies no 1.16.4→1.16.5 movement change and does not establish a cutover or any trajectory difference. Inputs that bypass `Entity.turn`, other external pitch writers, and runtime parity were outside this audit. The source-level intermediate witness does not demonstrate that an out-of-range pitch reaches movement; the code proves the opposite for this direct turn path.

## Phase-two signed comparison binding

The designated author report was read from the exact assigned path `C:/Users/Wolfi/.codex/visualizations/2026/11/17/01a1eb7b-b9ff-7a20-8686-0295dde20f26/lab/report.md`. Its observed SHA-256 is `86e35b22b1b87b926aae5d8d2bad7c5886826db66ebb3ddaec5b9ab65550c571`, exactly matching the phase-two assignment. This digest binds the comparison below to that report's literal bytes; the author report was not modified.

The report and this independent verdict agree on the assigned claim: 1.16.4 and 1.16.5 have byte-identical direct `Entity.turn` pitch cast/order/clamp behavior, and the finite `turn(0.0, 2401.0)` witness is above 360 only at the intermediate float stage before the clamp. They agree that a later active Elytra travel read sees clamped current pitch for this direct path. The author report adds a useful explicit applicability condition: finite pre-turn authoritative pitch and no intervening pitch writer before the read. That condition is consistent with this verdict's bounded direct-turn scope. Its additional `setRot` modulo and no-`setXRot` claims do not conflict with the reviewed `turn` operation; this comparison does not expand the audited operation to other pitch writers.

This phase-two binding is appended to the phase-one frozen verdict; its phase-one file digest above identifies the exact pre-comparison revision. The new complete-file SHA-256 is reported with this comparison to the coordinator.