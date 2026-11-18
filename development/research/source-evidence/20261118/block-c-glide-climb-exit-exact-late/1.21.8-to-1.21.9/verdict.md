# Independent source audit: Elytra glide on climbable blocks

## Bound input

Audited the exact bytes of `c-glide-1218-1219-actual-climb-exit/report.md` at `C:\Users\Wolfi\.codex\visualizations\2026\11\17\01a1eb7b-b9ff-7a20-8686-0295dde20f26\c-glide-1218-1219-actual-climb-exit\report.md`.

- Full report SHA-256: `7f24b5c2271c503bcca6cf10e23a78307a35004b4b7798c7e67ea4b22105ee34`
- Scope: the report's already-active, ordinary dry, on-foot player Elytra route when travel begins on a climbable block; pre-travel fall-distance cap, glide and climb predicates/authority gates, first movement ordering, and shared flag writes.
- This verdict accepts only that bounded comparison. It makes no full-pair coverage or runtime-parity claim.

## Source identity and integrity

Mojmap source files were read from the canonical version trees; no source generation was run.

- **1.21.8:** `decompiled_minecraft/1.21.8/mojmap/`. The adjacent `decompiled_minecraft/1.21.8/mojmap.ready.json` has SHA-256 `2e3bf302503eb127061cc605797b390f5f4a4b508f09350a59d78ced857ba7d7`; it identifies `versionId=1.21.8`, `mapping=mojmap`, `status=ready`, 6,105 files, Java 25, and client SHA-256 `ea74e9c5e92d01f95f3d39196ddb734e19a077a58b4c433c34109487d600276f`. The adjacent source manifest `decompiled_minecraft/1.21.8/mojmap.sources.sha256` has SHA-256 `8ffb76cea647a2ba4fe58e000678f751bea2c40ea6358bae492e962ed1d9d008`.
- **1.21.9:** `decompiled_minecraft/1.21.9/mojmap/` has no adjacent `mojmap.ready.json` or `mojmap.sources.sha256`. Its hidden `decompiled_minecraft/1.21.9/mojmap/.decompile-provenance.json` exists and has SHA-256 `37d3049478585a43ffcace14fc8f2ee814b35ceedd8c9bad8b60adaed9b72c4f`. Provenance records `lpc-decompile-v1`, Eclipse Adoptium Java `25.0.3+9-LTS` on Windows 11 amd64, and pinned 1.21.9 version metadata/client-JAR input hashes. Absence of the adjacent ready marker limits formal campaign-readiness evidence only; it does not invalidate the audited source bytes.
- For each compared source file below, the actual 1.21.8 SHA-256 equals its entry in `mojmap.sources.sha256`; the actual 1.21.9 SHA-256 equals its path entry in `.decompile-provenance.json`'s `sources` object:

| Source | 1.21.8 SHA-256 | 1.21.9 SHA-256 |
| --- | --- | --- |
| `world/entity/LivingEntity.java` | `609f0197a0b4551ab42279e452c11cdd256135b1d467c2a95950b0e9fd7ddef8` | `b8b49d60769203f7bd5afe4a1bffcdcdbec30be28960324cdc43a2df85a6eb66` |
| `world/entity/Entity.java` | `c403e6176d27b5bfd6aaa0dea3735ffa4fcf80dbae58766661dd84453b7e9704` | `bd6074b108212bde667877700af1a52649472630c906c57c2a36c96a6fcee74c` |
| `world/entity/player/Player.java` | `8dc5514fe44311f39268692f5188840b9f65cb71143228fa8b84242585ea7987` | `af857617b66a5776e63830771360b96f75e21d47d20db08f164a2dbeee801d82` |

## Independent comparison

- `LivingEntity.travel(Vec3)`: 1.21.8 lines 2245–2253; 1.21.9 lines 2275–2283. The same water/lava-and-fluid-affect gate precedes the same `isFallFlying()` dispatch. For the stipulated dry route with the active bit set, control reaches `travelFallFlying`.
- `LivingEntity.updateFallFlying()`: 1.21.8 lines 2871–2890; 1.21.9 lines 2901–2920. Both first call `checkFallDistanceAccumulation()`. The remaining eligibility, bit-clear, Elytra durability and event work is under `!level().isClientSide` (method form differs only syntactically). Therefore the server-side revalidation and Elytra durability/event branch does not reject or alter the ordinary local-client climb route at this point.
- `Entity.checkFallDistanceAccumulation()` / `resetFallDistance()`: 1.21.8 lines 2674–2682; 1.21.9 lines 2742–2750. The cap is identical: only when `deltaMovement.y() > -0.5` and `fallDistance > 1.0`, write `1.0`; reset writes `0.0`.
- `LivingEntity.travelFallFlying(Vec3)` and `stopFallFlying()`: 1.21.8 lines 2353–2372; 1.21.9 lines 2383–2402. Both query `onClimbable()` first. If true, they call `travelInAir(input)` before `stopFallFlying()`. That first ordinary movement path therefore precedes the glide-bit stop; the special Elytra movement and server collision correction branch is bypassed.
- First movement and climb cap/reset: `LivingEntity.travelInAir(Vec3)` at 1.21.8 lines 2276–2299 and 1.21.9 lines 2306–2329 calls `handleRelativeFrictionAndCalculateMovement` first. That helper (`LivingEntity` 1.21.8 lines 2446–2455; 1.21.9 lines 2476–2485) applies `handleOnClimbable`, then calls `move`. `handleOnClimbable` (1.21.8 lines 2473–2488; 1.21.9 lines 2503–2518) resets fall distance before that move and applies the same horizontal clamps, downward clamp and ladder-sliding rule. Thus the earlier conditional cap is followed by the same climb reset before the first ordinary move in both releases.
- Eligibility and predicates: `LivingEntity.canGlide()` (1.21.8 lines 2893–2905; 1.21.9 lines 2923–2935) has the same airborne, not-passenger, no-Levitation and eligible-gear checks. `Player.canGlide()` adds the same `!abilities.flying` check (1.21.8 `Player.java` 1464–1466; 1.21.9 1321–1323). `Player.tryToStartFallFlying()` uses the same not-already-gliding, `canGlide()` and not-in-water checks (1.21.8 1519–1526; 1.21.9 1376–1383); it adds no climb rejection. This is adjacent eligibility context, not a start-glide route in the accepted scope.
- `LivingEntity.onClimbable()` (1.21.8 lines 1632–1648; 1.21.9 lines 1662–1678) has the same spectator rejection and block-tag/open-ladder-trapdoor checks. `Player.onClimbable()` delegates to it unless abilities flight is active (1.21.8 `Player.java` 2123–2125; 1.21.9 1955–1957).
- `LivingEntity.stopFallFlying()` writes shared flag 7 to true then false in both releases. `Player.startFallFlying()` writes shared flag 7 true in both (`Player.java` 1528–1530 / 1385–1387). The only relevant update authority gate is unchanged: `updateFallFlying` performs its equipment/eligibility invalidation only when not client-side; travel's climb decision and stop call have no side gate. Player ability-flight handling in the climb predicate and `canGlide` is also unchanged.

The paired scoped conditions, cap, reset, movement call order, client/server gate, and shared-flag writes match. Line shifts and the `isClientSide` accessor form are surrounding/source API drift only. **Bounded verdict: ACCEPT — no 1.21.8 → 1.21.9 movement difference found for the stated already-active dry climb exit branch.**

## Limits

No claim about other Elytra paths, glide startup/final tick, input or sprint differences, fluid/contact/geometry, other effects or gear, pair inventory closure, or runtime parity. No implementation, wiki, product history, Git, source generation, build, test, or launch evidence was consulted or produced.