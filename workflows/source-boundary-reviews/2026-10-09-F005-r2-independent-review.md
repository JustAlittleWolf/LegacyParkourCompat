# Independent source-only review: F-005 witness closure r2

**Review date:** 2026-10-09  
**Disposition:** **ACCEPT** the r2 witness dependency correction at the stated source-only scope.  
**Candidate:** memo commit `96b6f7253491903c0da443d74102dcc451be1245`; report-only merge `84bdc9a56bebbc91bc18cfcf1e5fbadda3637dbb`; memo blob `012209d04cc41482729b0bf4f66cd9c23686bf8e` at both commits.  
**Prior verdict:** r1 `REQUEST CHANGES`, commit `6702eda57a1f378576fca06778e11648cc0e5c8c`, report blob `65bc5ff0c0a7c222267f882f5ffc62036360f54b`. This r2 disposition addresses that verdict's post-move cobweb-writer gap. The accepted 1.18.2 release boundary is not reopened.

## Independent reproduction

I checked the r2 memo against the cited exact 1.18.2 Mojmap sources under `build/movement-campaign-2026-10-07/ready/1.18.2/mojmap`. The new source file hashes match the memo and the frozen source manifest:

- `LivingEntity.java` — `db4168d531caf18f22e3fefd073365e776da4075ce01452bb9f7671d9b458782`
- `FireworkRocketEntity.java` — `bbece25fbba0db7536138627c88aed68ba2c62371943b5e685610a926a1dc3cf`
- `FireworkRocketItem.java` — `907fdf635dba1932f244ea75024f332f5f65a02e8985662b25695d6d467993bf`
- `WebBlock.java` — `eb8f4433705367aa7ce23c5a6071955b4d67728e299a2993285c6405ce638e49`

The r1 `Entity.java` and `Player.java` bindings also match (`2228fdaca5793171cbd94038306d571a6ada78ca96f5734efb4cada5b744c10a` and `bf639c1962ff90d69e4569b2b18f6fcf57ac46ef80b19686f0fbc1687fca744a`). Their reset gates, final-box callback order, dimensions, and later Player predicate support the stated bounds.

I independently recomputed the displayed recurrence from the attached-firework velocity update and the fall-flying branch in `LivingEntity.travel`, including its pitch lift, horizontal alignment, and float damping. The ten movement X/Y pairs and cumulative sums agree with the table to the shown six decimal places. The final move is approximately `(1.619486, 0.311542, 0)`, so its squared length is about `2.72`: it clears both the `> 1.0E-7` nonzero-move gate and the `>= 1.0` F-005 gate. Fall-flying travel sets `fallDistance` to `1.0F` for these positive vertical velocities, satisfying the nonzero-field gate. With no sneak input, Player's ground-surface back-off condition is false; the cobweb has empty ordinary movement collision, so the resolved segment remains available to the special clip.

The special clip ray runs from about `(0.69, 19.10, 0.50)` to `(2.3095, 19.4115, 0.50)`. It crosses cobweb `(1, 19, 0)`, whose 1.18.2 fall-damage-resetting selector supplies the full block shape. Thus a non-MISS clip and reset before `setPos` follow from the cited gate and source geometry.

## Callback and later-consumer closure

The fall-flying Player dimensions are `0.6 × 0.6`, centered on X/Z with feet at Y. At the final position the AABB is approximately X `[2.0095, 2.6095]`, Y `[19.4115, 20.0115]`, Z `[0.20, 0.80]`. `checkInsideBlocks()` derives its cell range from this final AABB (with its `0.001` inset), so the checked X cell is 2; cobweb cell 1 is excluded. The only non-air cell named in the memo at the endpoint is stone `(2,18,0)`, below the final AABB. Therefore `WebBlock.entityInside` cannot run for this endpoint. This closes the independent `makeStuckInBlock` reset that caused the r1 request for changes.

The remaining post-reset writers are closed for the stated state: resolved Y is positive and `onGround` is false, so `checkFallDamage` neither resets nor accumulates `fallDistance`; the final-box callback cells are air; the witness is not in water. The slow-falling reset in `LivingEntity.travel` is gated on nonpositive vertical velocity, which is false for the stated positive value. The subsequent `updateFallFlying` clears fall-flying when the Elytra is removed. Ordinary travel then has no fall-flying assignment to `1.0F`; positive vertical movement leaves `checkFallDamage` unchanged. With sneak held, the next `Player.maybeBackOffFromEdge` call reads `isAboveGround`: the reset path passes `0.0F < 0.6F` and its downward probe intersects the stone support, while the comparison path retains `1.0F` and fails the strict `< 0.6F` comparison before probing. This is a direct later movement-predicate consumer of the F-005 field.

## Disposition and limits

**ACCEPT r2**: the revised source-level cobweb witness closes the post-move callback path and binds the reset to a later Player movement predicate. This accepts the source equations and conditional state path only; it does not claim a realized trajectory or runtime validation. The movement-state reset is kept separate from fall/landing damage resolution and health. The already accepted first-changed-release boundary remains 1.18.2. No version key, hook, implementation, or runtime recommendation is made.

Static source/hash review only. No build, tests, game, TAS, server, Docker, wiki/MCPK, or shared-source preparation was run.

