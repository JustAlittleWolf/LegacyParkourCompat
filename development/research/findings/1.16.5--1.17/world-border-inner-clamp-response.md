# WorldBorder extent clamp changes local collision response

**Boundary:** 1.16.5 → 1.17; the 1.17 collision shape persists in 1.17.1.

For the same raw client border state (`absoluteMaxSize = 29,999,984`, center `(59,999,984,0)`, size `59,999,968`), 1.16.5's `StaticBorderExtent.updateBox` computes reversed clamped X limits and `Shapes.box` sorts them, leaving an interior strip through X `30,000,000`. In 1.17/1.17.1 both X limits clamp to `29,999,984`; the zero-width inner box becomes empty, so the border collision shape is full space.

With a standing on-foot LocalPlayer at X `29,999,999.69`, Z `0`, and the same eastward SELF displacement `(0.02,0,0)`, the old `Entity.collide` overlap gate retains the outside-border shape and clips X to about `0.00999998674`. The new full-space shape overlaps the current player box, so the gate omits that border shape and leaves `0.02` (assuming no other colliders). The anchor and AABB remain below 30 million.

**Applicability:** equal raw border/player/request inputs and open eastward movement. This is a representable but unusual client-side border state; server policy, border producer behavior, runtime trajectory and frequency are outside the finding. The distinct `Shapes.create(current Player AABB)` gate was independently closed as no-difference; the changed response comes from border geometry construction.

**Independent review binding:** `.native-source/20261031/1165-1171-finish45/worldborder-review/review.md`, SHA-256 `0720348DCE8455D9EF95F38A1B8BBBB56A2CFC95A853BD41F7A06DF37E59F292`. The final blind source audit classifies this as an accepted conditional direct response in `final-blind-audit/coverage.tsv`.
