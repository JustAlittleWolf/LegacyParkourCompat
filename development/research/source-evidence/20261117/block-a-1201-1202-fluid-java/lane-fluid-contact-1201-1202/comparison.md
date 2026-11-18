# 1.20.1 → 1.20.2 ordinary on-foot water/lava contact

## Comparison status

The paired Java movement slices below have no source-level arithmetic, gate, state-write, or caller-order change. The effective membership of the data-driven `minecraft:water` and `minecraft:lava` fluid tags remains an unresolved resource dependency: the decompiled Java source and its source manifest identify the tag keys and consumers, but do not establish the exact versioned tag JSON membership. This is bounded slice evidence, not full-pair freeze or source acceptance.

## Source identity and integrity

Both inputs are exact Mojmap source exports. Their version-root `mojmap.ready.json` identifies the exact release and `mojmap` mapping namespace; `mojmap.provenance.json` records the exact decompile run and toolchain. The ready marker's `sourceManifestSha256` equals the SHA-256 of the listed source manifest. Every cited Java file's current SHA-256 also matches its entry in that manifest.

| Release | Ready marker SHA-256 | Provenance SHA-256 | Source manifest SHA-256 |
|---|---|---|---|
| 1.20.1 | `f8dc81374dc42521ee9d17a04ea0dc77f1c5384548cfc01c9621b3d84b670fed` | `5aeb1df09671480aeddb921c919e92098e33d0a0bce47e22f82a865cb32400aa` | `858b56764113e591c60d09bc63e4d90f9c1d67a705645f0c793e8b28f2378465` |
| 1.20.2 | `5ff3322f287c250ee914c7ca3840d053bffc3c2c5f35e91b59314d948292483f` | `1dad9f725f6cb25b6568700fa0294f83b66ea9633b0d62ed354d7bd8b45d4f05` | `e439ff7e1d3f1d31e01be1edd141f94d917a9dc7fa0de5f69904544cbbe62887` |

## Bounded coverage

### Body admission, stored depth, and current impulse

Compared `Entity.baseTick`, `updateInWaterStateAndDoFluidPushing`, `updateInWaterStateAndDoWaterCurrentPushing`, `updateFluidHeightAndDoFluidPushing`, `touchingUnloadedChunk`, `getFluidHeight`, `Player.isPushedByFluid`, `LevelReader.hasChunksAt`, `FluidState`, and `FlowingFluid.getFlow` in both versions.

`Entity.baseTick` performs the same refresh before `updateFluidOnEyes` and `updateSwimming`. The refresh clears `fluidHeight`, scans water first with push coefficient `0.014`, then lava with coefficient `0.007` in ultra-warm dimensions or `0.0023333333333333335` otherwise. Water sets `wasTouchingWater`; lava returns its scan result. The body scan first returns `false` on `touchingUnloadedChunk()`. That preflight inflates the full entity box by `1.0`, floors min X/Z, ceils max X/Z, and asks `hasChunksAt` for the inclusive horizontal chunk-section rectangle. If the preflight passes, the sampled box is deflated by `0.001`; floor(min) / ceil(max) bounds are iterated as half-open X/Y/Z loops.

A scanned cell qualifies when its fluid state matches the requested tag and `cellY + fluidHeight >= sampledBox.minY`. Stored depth is the maximum qualifying surface minus sampled-box min Y, starting from zero. Flow is accumulated only when `isPushedByFluid()` is true; the player override is `!abilities.flying`, so ordinary non-flying on-foot play takes the pushed path. Each qualifying cell contributes `FluidState.getFlow`; the contribution is scaled by current maximum depth when that depth is below `0.4`, then contributions are averaged by qualifying pushed-cell count. The player's sum is not normalized (normalization is for non-players); it is scaled by the fluid coefficient and added to current velocity. If horizontal velocity components are both below `0.003` and the scaled flow magnitude is below `0.0045000000000000005`, the scaled flow is normalized to that minimum magnitude. The method stores the resulting depth under the requested tag and returns whether any cell qualified.

`FlowingFluid.getFlow` uses same-fluid identity when examining neighboring fluids (including lower neighbors below a non-motion-blocking horizontal neighbor); falling flow gains the downward component when a neighboring side face is solid. `FluidTags.WATER` and `FluidTags.LAVA` are tag keys created for the fluid registry. Whether a particular fluid belongs to either tag is resource/data dependent and is not proven by these Java exports. The body scan's tag gate is therefore source-confirmed, while effective vanilla tag membership is unresolved.

No difference was found in the Java body-admission, preflight, loop bounds, surface comparison, depth write, player push override, flow aggregation, minimum impulse, flow vector, or refresh order for this pair.

### Vertical water/lava movement and collision-exit clearance

Compared `LivingEntity.travel`, `getFluidFallingAdjustedMovement`, `canStandOnFluid`, and `Entity.isFree` in both versions. The travel water branch has the same entry gate: controlled local instance, `isInWater()`, `isAffectedByFluids()`, and failure of `canStandOnFluid` for the fluid state at the player's block position. It preserves the same water slowdown, depth-strider caps/airborne halving, dolphins-grace override, relative input and move order, `0.8F` vertical drag, then falling adjustment. The falling helper retains the same no-gravity/sprinting bypass and the same `0.003` tolerance tests, `-0.003` terminal adjustment and `gravity / 16.0` subtraction. After horizontal collision, the same `isFree` probe uses the resulting X/Z displacement and the Y clearance expression `velocityY + 0.6F - currentY + priorY`; success sets Y velocity to `0.3F`.

The lava branch has the same local/fluid/stand-on gates and move order. At or below `getFluidJumpThreshold()`, it applies `(0.5, 0.8F, 0.5)` scaling and the shared falling adjustment; above the threshold it scales the whole vector by `0.5`. It then subtracts one quarter of the selected gravity unless no-gravity applies. The collision-exit probe has the same `0.6F` clearance and `0.3F` upward velocity.

`canStandOnFluid` remains `false` on `LivingEntity`; the caller still evaluates the current block-position fluid state for the gate. `Entity.isFree` remains `noCollision(entity, movedBox) && !containsAnyLiquid(movedBox)`, so exit clearance requires both collision-free and liquid-free space. No difference was found in these Java operations.

### Local jump and stored-depth handoff

Compared `LocalPlayer` input handoff, `Entity.baseTick` refresh order, `LivingEntity` jump dispatch, `jumpInLiquid`, and `getFluidJumpThreshold`. LocalPlayer still assigns `this.jumping = this.input.jumping` in the controlled-camera path. The base tick refreshes the tag-keyed depth map before the living-entity jump section consumes it. Jump dispatch selects lava depth when `isInLava()`, otherwise water depth; water's submerged-jump branch requires `isInWater() && depth > 0.0`. The same threshold is `0.0` for eye height below `0.4`, otherwise `0.4`; the same on-ground/depth comparisons choose ground jump versus liquid jump. `jumpInLiquid` still adds `0.04F` Y velocity. No local-player stored-depth or handoff change was found.

## Source references and hashes

Line references are relative to each version's `mojmap/` source root. All hashes below were checked against the corresponding version-root `mojmap.sources.sha256` entry.

| File | 1.20.1 lines; SHA-256 | 1.20.2 lines; SHA-256 |
|---|---|---|
| `net/minecraft/world/entity/Entity.java` | `409–436`, `1176–1198`, `3001–3079`; `94b9c3656715de2d61fa9a02ccef164261e50eb13d6090a6f07e58fe9c5b0759` | `411–438`, `1186–1208`, `3031–3112`; `d7ee49aaea5e862b92508e767562cabc8f10515605e8fb67565c8c8d5c23b01b` |
| `net/minecraft/world/entity/LivingEntity.java` | `1992–2002`, `2004–2067`, `2184–2197`, `2531–2558`; `decf8cd70d194098ad51d0c82e5a0087e0687881e0a2dc66456f2ba6ea76676f` | `2026–2036`, `2038–2101`, `2218–2231`, `2560–2587`; `5c481da1ffc8684c4b30171c92e61fad751e6a3a708d5bc6486ac9f96ff69828` |
| `net/minecraft/client/player/LocalPlayer.java` | `614–617`, `738–748`; `69a2d043d2c0595bd364d625a5445e4934d9e07fef020cd79fafb42f59952ac2` | `612–615`, `737–747`; `bb5cbfb03656a1866bb77c00431618befe792081223b35db4bd121ecbb151fd5` |
| `net/minecraft/world/entity/player/Player.java` | `1874–1876`; `873d82c6f5471d06812929e471d970fd973bfbe21638e4e348a6fcde45637dac` | `1878–1880`; `25f263692fd6b2a737aa813315022a0caf4e18c3661ba3bd706c6297f909f25c` |
| `net/minecraft/tags/FluidTags.java` | `1–19`; `df79262149bf0e684d877ca5d2bb87f02d8b66d55ed123d4c2e93bfa6462396f` | `1–19`; `df79262149bf0e684d877ca5d2bb87f02d8b66d55ed123d4c2e93bfa6462396f` |
| `net/minecraft/world/level/LevelReader.java` | `185–205`; `ec8c1f883dc1a71d2666f57cf7e124298272ae8b483770d32ecdb32c385b0bdd` | `185–205`; `ec8c1f883dc1a71d2666f57cf7e124298272ae8b483770d32ecdb32c385b0bdd` |
| `net/minecraft/world/level/material/FluidState.java` | `49–50`, `91–92`; `0d6d1d24deb58f056163acc3ae8976d2c7b3394aca2ae37fa43bc560c9d33b7c` | `49–50`, `91–92`; `0d6d1d24deb58f056163acc3ae8976d2c7b3394aca2ae37fa43bc560c9d33b7c` |
| `net/minecraft/world/level/material/FlowingFluid.java` | `54–115`, `430–437`; `ba1dff6ccb28b62760c74d66832d8fdbe7a1cdc37b9c5a0ff476bd200215c904` | `54–115`, `430–437`; `0f44b1af25533dd8a1fbfec13b279739341db0c563568337a2825d3e46a1a5bb` |

## Coverage boundary

Covered the paired source operations and callers named above for ordinary, local, on-foot player water/lava contact, flow push, vertical fluid movement, fall adjustment, collision-exit clearance, and jump-depth handoff. Effective tag resource membership, custom datapack/tag overrides, other fluid operations, other releases, and unrelated entity behavior were not examined.