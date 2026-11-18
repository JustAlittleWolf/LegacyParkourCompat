# Independent blind audit — 1.21.3 → 1.21.4 block-contact record lifecycle

## Result

Both bounded source claims are **accepted**, with the lifecycle claim accepted subject to the scope qualifications below. This is an independent verdict on these two claim revisions; it is not a full-pair coverage audit or runtime-parity finding.

## Evidence identity

- Exact releases and mapping: 1.21.3 Mojmap → 1.21.4 Mojmap. Both ready markers identify their exact release/mapping and have matching source-manifest SHA-256 values. Their provenance records identify the same release and namespace. Relevant relied-on source files were hashed against the corresponding `mojmap.sources.sha256` rows.
- Source-manifest SHA-256: 1.21.3 `d673bb5464853e3a2a92ed9b1ffe1789d4e62c097bb884f67c336fcbb3b178ce`; 1.21.4 `f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0`.
- Frozen independent roster: `source-roster.md`, SHA-256 `9654d43b1877882fc2652106349c21fe9f42245168ab73fe4416b8a7dcea52a6`.
- Submitted `movement-contact-record-lifecycle.md`: SHA-256 `902110291be5a2340bf01b02e511a317eb5fb478d7471a1c3617bba1182c5777`.
- Submitted `movement-contact-block-cell-traversal.md`: SHA-256 `50d7567e8f4bfdda0cd45f35eef68a3403a55b61bd421b3c68add76be87bb9c0`.
- Submitted `coverage-roster.md`: SHA-256 `2074670a21d9b562569d3d8af6b445965de581d5dd119cfcf58737f9b52fef6e`.
- Submitted `sources.sha256`: SHA-256 `df6165295e7a2b039e09aeea74694f48e9c958e771df76233d52d447b1d94d60`.

## Per-claim verdicts

### `movement-contact-record-lifecycle.md` — ACCEPT, with qualifications

Verified in both `Entity.java` sources: the per-entity `movementThisTick` holds `Movement(from,to)`; `applyEffectsFromBlocks()` delegates with `oldPosition()` and current `position`; the overload, when `isAffectedByBlocks()` passes, optionally runs `stepOn` and appends that pair. The regular `Entity.move` path updates position but does not append a record. The zeroed `MoverType.PISTON` early return also does not append in `move`; control returns to the enclosing tick, where the later post-travel `applyEffectsFromBlocks()` can append the old-position→current-position fallback if `isAffectedByBlocks()` passes. Piston suppression and this tick-level fallback are unchanged across the pair. `teleportSetPosition` clears the accumulator in both versions.

The consumer difference is as stated: 1.21.3 passes the live list into `checkInsideBlocks` and clears it after traversal; 1.21.4 uses `List.copyOf`, clears the mutable accumulator before traversal, and checks the snapshot. The bounding-box difference is also as stated: 1.21.3 reuses `getBoundingBox().deflate(1.0E-5F)` for each record; 1.21.4 forms `makeBoundingBox(to).deflate(1.0E-5F)` per record. The paired `AABB.java` files have the same source hash.

Reachability is verified through `LocalPlayer.tick` → `AbstractClientPlayer` → `Player` → `LivingEntity.tick`; after `travel`, the existing client/local-instance gate calls `applyEffectsFromBlocks()`. `LocalPlayer.isEffectiveAi()` is true, so a local player without a player controlling passenger reaches that gate. The inspected `LocalPlayer.move` override only delegates then updates auto-jump and walk distance; it has no record append.

Qualifications: automatic record creation is conditional on `isAffectedByBlocks()` (`!isRemoved() && !noPhysics`), and the piston-zero fallback is the later enclosing-tick append, not an append by the piston branch itself. Also, `isControlledByLocalInstance()` falls back to `isEffectiveAi()` whenever the controlling passenger is not a `Player`, not strictly only when there is no passenger. This wording refinement does not change LocalPlayer reachability.

The per-record AABB change is real, but the inspected ordinary local-player tick creates one automatic old-position→final-position record. Therefore the draft correctly avoids claiming a demonstrated trajectory consequence for that one-segment case. The snapshot/live-list difference is a source-level lifecycle change; this audit does not establish a callback-induced second in-scope append on the LocalPlayer path.

### `movement-contact-block-cell-traversal.md` — ACCEPT

`BlockGetter.boxTraverseBlocks` confirms the pair difference for the long path (displacement squared at least `Mth.square(0.99999F)`). 1.21.3 offsets the minimum endpoint by normalized displacement times `1.0E-7` and the reverse-displacement endpoint by the negative offset; 1.21.4 uses the unadjusted minimum and its subtraction by displacement. The short-path return, set type/order, enumeration of the AABB endpoint cells, and `addCollisionsAlongTravel` body are unchanged in the reviewed source. The paired `AABB.java` source hashes match. `Entity.checkInsideBlocks` passes each record's `from`, `to`, and selected AABB to this traversal. The claim stays within candidate block-cell traversal and makes no solid-collision or trajectory assertion.

## Boundaries

No product implementation, wiki inventory, product Git history, collision-shape result, non-player movement writer, build, test, runtime, decompilation, or network source was used. The result accepts only the two immutable claims above, not pair completeness, intermediate patch boundaries, or runtime parity.