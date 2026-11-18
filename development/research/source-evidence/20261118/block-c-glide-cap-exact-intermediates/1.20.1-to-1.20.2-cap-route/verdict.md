# Independent audit verdict — 1.20.1 → 1.20.2 active Elytra route at climbable and ordinary feet

## Verdict

**Accept the report’s no-difference result for its stated slices, with a required coverage addendum.** Its active-flag, branch-precedence, ordinary climb-cap, and first-glide-move claims match both exact sources. The report omits the actual fall-distance limiter that runs immediately before glide calculations and the first glide move. That limiter is the assigned route’s relevant pre-move state write and should be included to make the slice complete. It is present and unchanged in both releases, so the omission does not reverse the reported no-difference result.

## Frozen input and source identity

Input: `c-glide-1201-1202-cap-route/report.md`, SHA-256 `6e2c4bd5dc7ad8c7a2729437aa20c9494723c334d11df7d52c24aaef8d69e578` (independently recomputed).

Canonical sources: `decompiled_minecraft/<version>/mojmap/`, exact ready markers and Mojmap provenance sidecars. For 1.20.1, `mojmap.ready.json` reports ready / `1.20.1` / `mojmap`, source-manifest SHA-256 `858b56764113e591c60d09bc63e4d90f9c1d67a705645f0c793e8b28f2378465`, client input SHA-256 `56b71336d2b4fdffd197f56595b0da93e32a946f78f382a299b8f4b92758bb0f`; recomputed manifest hash matches. For 1.20.2, the corresponding values are ready / `1.20.2` / `mojmap`, `e439ff7e1d3f1d31e01be1edd141f94d917a9dc7fa0de5f69904544cbbe62887`, and `fa1a19be56a506426308abbc1cad85f299a7fc6dae4335559351ba0246713fda`; recomputed manifest hash matches. Each provenance sidecar identifies that exact version and Mojmap decompile run.

Relevant source SHA-256 values, each independently recomputed and matched to the side’s source manifest:

| Source | 1.20.1 | 1.20.2 |
|---|---|---|
| `net/minecraft/world/entity/LivingEntity.java` | `decf8cd70d194098ad51d0c82e5a0087e0687881e0a2dc66456f2ba6ea76676f` | `5c481da1ffc8684c4b30171c92e61fad751e6a3a708d5bc6486ac9f96ff69828` |
| `net/minecraft/world/entity/Entity.java` | `94b9c3656715de2d61fa9a02ccef164261e50eb13d6090a6f07e58fe9c5b0759` | `d7ee49aaea5e862b92508e767562cabc8f10515605e8fb67565c8c8d5c23b01b` |
| `net/minecraft/world/entity/player/Player.java` | `873d82c6f5471d06812929e471d970fd973bfbe21638e4e348a6fcde45637dac` | `25f263692fd6b2a737aa813315022a0caf4e18c3661ba3bd706c6297f909f25c` |

The report’s cited `LivingEntity.java` and `Player.java` source hashes and both manifest identities are correct. The added `Entity.java` source is required to verify the inherited limiter.

Sidecar file hashes, recomputed locally: 1.20.1 `mojmap.ready.json` `f8dc81374dc42521ee9d17a04ea0dc77f1c5384548cfc01c9621b3d84b670fed` and `mojmap.provenance.json` `5aeb1df09671480aeddb921c919e92098e33d0a0bce47e22f82a865cb32400aa`; 1.20.2 `mojmap.ready.json` `5ff3322f287c250ee914c7ca3840d053bffc3c2c5f35e91b59314d948292483f` and `mojmap.provenance.json` `1dad9f725f6cb25b6568700fa0294f83b66ea9633b0d62ed354d7bd8b45d4f05`.

## Audited behavior and exact bounds

- **Active flag update:** `LivingEntity.updateFallFlying()` reads shared flag 7, requires the ordered `!onGround()`, `!isPassenger()`, and `!LEVITATION` gate, then checks chest Elytra and `ElytraItem.isFlyEnabled`. The only write back to shared flag 7 is guarded by `!level().isClientSide`; a failed predicate does not itself clear the active local client flag. `onClimbable()` is absent from this cancellation gate. `LivingEntity.java:2610-2634` (1.20.1) and `2639-2663` (1.20.2); `aiStep` calls it before `travel` at `2560-2573` and `2589-2602` respectively. These predicates and writes match.
- **Dry local-player route:** `Player.travel` calls `super.travel` except for abilities flight while not a passenger (`Player.java:1446-1473`; `1450-1477`). In `LivingEntity.travel`, water and lava branches precede `isFallFlying()`, and the ordinary friction branch follows it (`LivingEntity.java:2012-2112`; `2046-2146`). With the report’s dry, active-glide, on-foot-local-player preconditions, the Elytra branch wins at either ordinary or climbable feet.
- **Omitted flight fall-distance limiter:** The glide branch calls `checkSlowFallDistance()` before reading the glide vector, computing glide deltas, applying drag, and issuing its first `move(MoverType.SELF, ...)` (`LivingEntity.java:2068-2094`; `2102-2128`). The inherited implementation is `if (this.getDeltaMovement().y() > -0.5 && this.fallDistance > 1.0F) { this.fallDistance = 1.0F; }` (`Entity.java:2274-2277`; `2312-2315`). Thus the velocity threshold compares the Y component (double) against `-0.5` (double literal); fall distance is compared against and assigned the float literal `1.0F`. It caps an existing value to 1.0F only when the current Y velocity is greater than -0.5; it does not reset fall distance to zero. Condition, float cap, and placement are identical in both releases.
- **Distinct ordinary climb handling:** Only the ordinary friction path calls `handleRelativeFrictionAndCalculateMovement`, which applies `handleOnClimbable` to velocity before its ordinary `move`. When `onClimbable()` is true, that helper separately calls `resetFallDistance()` (assigning `0.0F` in `Entity.resetFallDistance`), clamps X/Z to `[-0.15F, 0.15F]`, floors Y at `-0.15F`, and applies the ordered ladder/player suppression condition (`LivingEntity.java:2171-2215`, helper `2199-2217`; 1.20.2 `2205-2248`, helper `2233-2250`). The glide branch bypasses this handler, so it has neither this zero reset nor the ±0.15F / -0.15F velocity cap before its first glide move. Both route separation and helper behavior match across the pair.

## Bounds

This verdict covers only the exact 1.20.1-to-1.20.2 active, default-chest-Elytra, dry, on-foot local-player tick route and the cited pre-move state/cap operations. It does not establish start eligibility, all tick-state consumers or writes, collision/contact outcomes, intermediate patch boundaries, any other release pair, full-pair inventory closure, or runtime parity. No implementation, tests, launch, source generation, wiki inspection, product inspection, or Git operation was performed.
