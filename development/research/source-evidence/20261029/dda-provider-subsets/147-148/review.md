# Independent review: weighted pressure plates, IDs 147 and 148

## Verdict

**ACCEPT within the report's block-key, state, movement-collision and outline-provider scope.** The report makes no DDA traversal, player reachability, or runtime-parity claim, and this review does not extend it.

Reviewed source report: `.native-source/20261028/collision-next/dda-weighted-pressure-plates-147-148/report.md`, SHA-256 `7548C739CC3EAB87B9CF59F34139C03DEA5FC5D9FE64F3B55D95BD2CF6700B6B`. Its source manifest is `sources.sha256`, SHA-256 `A5491FD3B1791F7B9842A6FEAF712464F5ED4F187DE823B900045AEBFD5BC8D2`.

## Evidence verification

I recomputed all 22 entries in the source ledger against the canonical read-only source roots. The 16 Java hashes also match their exact source-manifest entries. The ready markers identify 1.8.9 / `ornithe-feather` and 26.2 / `unobfuscated`; each declared manifest digest matches the canonical `.sources.sha256` file.

The old 1.8.9 registry identifies IDs 147 and 148 as light and heavy weighted pressure plates. `WeightedPressurePlateBlock` defines one integer `POWER` property over 0–15, defaults it to zero and decodes metadata directly into it. The old abstract pressure-plate collision provider returns `null` independently of that state. The report correctly treats this as an empty movement-collision provider and does not infer emptiness from the outline shape.

In 26.2 both registrations use the same corresponding registry keys and `.noCollision()`. `BlockBehaviour.Properties` defaults `hasCollision` to true, `.noCollision()` sets it false, and `BlockBehaviour.getCollisionShape` returns `Shapes.empty()` when the flag is false. The property is the same 0–15 `POWER`, and the weighted block defaults it to zero. The resulting movement-provider equivalence is supported for these two registrations.

The outline/cache distinction is also supported. Old `AbstractPressurePlateBlock.updateShape` mutates shared bounds to the 14/16-wide, 1/16-high unpressed plate or 14/16-wide, 1/32-high pressed plate. `Block.rayTrace` calls `updateShape` before reading those fields and does not restore them on its return paths. In 26.2, `BasePressurePlateBlock` returns a state-selected immutable outline built from `Block.column`; its `/16` scaling produces the same dimensions. The report properly leaves the observer/reachability effect unclaimed and does not conflate outline with movement collision.

## Limits

No discrepancy was found in the bounded claims. This does not establish power-signal equivalence, mutable-bound read ordering outside the cited ray-trace call, DDA visitation, camera reachability, or runtime behavior. No product code, build, tests, or runtime were used.
