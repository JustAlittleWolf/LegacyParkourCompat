# Final independent product review: border-lid65 correction

## Verdict

**ACCEPT the corrected product integration** at commit `e921645f0ee3dbee33f0458a4c2c522dd30e702a` (parent `61f0e9f4267e79c529d4e29cbbc9a6092f7c2f52`). The correction resolves both blockers from the review of `61f0e9f4`: static historical geometry now participates in border admission and is carried into collection as the same shape instance, and the new static operation is restricted to the active client `LocalPlayer` and its border. No material source/call-path blocker remains within this review scope.

## Composition and witness path

`StaticWorldBorderCollisionShape.resolve` requires the source to be the active `Minecraft.getInstance().player`, requires the player's level border to be the supplied border, requires client movement to be enabled, and requires zero lerp time and speed. Only then does it resolve the versioned static-shape hook. This rejects remote players, server entities, other borders, moving extents, and native/current profiles; the native marker returns empty for earlier profiles without an accepted old shape implementation.

Both the ordinary `Entity.collide` wrapper and the custom step-up wrapper resolve the static shape before evaluating `BorderCollisionAdmissionHook`. They pass that shape (or the native border shape when no static historical shape resolves) into the admission hook. When admission is produced, `BorderAdmissionContext.CollisionAdmission` carries the resolved historical shape. The collector uses that carried object for the matching entity. The existing `WorldBorderCollisionHook` non-null historical collision branch remains first in both admission and shape collection, preserving moving-border precedence. If no static shape was carried, collection re-resolves only under the same local-player/static-profile guards; otherwise it calls the native border getter.

For the accepted 1.16 witness, the static implementation computes raw X endpoints `30,000,000` and `29,999,984`, sorts the rounded inner-box endpoints to `[29,999,984, 30,000,000]`, and returns `INFINITY ONLY_FIRST` that interior. The updated admission hook evaluates overlap against this same finite geometry, returns inclusion for the witness's deflated player box, and the collector then inserts the same carried shape. The native admission would be false for these inputs, but the historical result admits the collider. The existing 1.16 empty-shape hook resolves to its earlier boundary implementation, which returns empty for this one-axis request; vanilla axis clipping therefore reaches the finite border edge and clips the accepted `0.02` X request to approximately `0.0099999867`.

## Other checks

- The static extent calculation remains byte-for-byte equivalent in operation order to the accepted 1.16 endpoint clamp, floor/ceil, endpoint-sort, and `INFINITY ONLY_FIRST` contract. Resolver annotations retain the 1.16 range and native fallback below it.
- The shulker provider and raw-array helper are unchanged from the reviewed product commit. Its exact residual-progress and WEST/DOWN/NORTH gates, raw block-entity bounds, one-cell discrete shape, and non-quantized coordinate arrays still match the accepted finding. The 26.2 constructor descriptor and client-only invoker registration remain correct. No block entity, block-state, or state-producer changes were introduced.
- The 26.2 `Entity.collide` → `collideBoundingBox(Entity,...)` → entity-argument `collectCollidersIgnoringWorldBorder` call and target descriptor match the mixins. The separate `CollisionContext` overload remains untouched.
- The prior product-review P1 admission blocker and P2 LocalPlayer-scope blocker are both resolved by this commit.

This is an independent source and call-path review, not runtime parity evidence. I did not edit product files or run a build, tests, game launch, or staging. The integration build is outside this review.
