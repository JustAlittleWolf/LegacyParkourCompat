# Independent review: DDA rail-family provider closure

## Verdict

**ACCEPT within the stated alias, metadata, movement-provider, and local ray-outline scope.** The source report is `.native-source/20261028/collision-next/dda-rail-family-27-28-66-157/report.md` (SHA-256 `027AAF833B2BE99BEC7456463DA1D870C6AE98A2CCCE13158A94B1AA8F04F5CB`). Its 25-entry source manifest is `sources.sha256` (SHA-256 `C9E63B60F2F32FEDD8A2257B4D0B3D51A9F1C7DE11966F78296BDB7931975846`).

## Evidence integrity

All 25 manifest entries match the canonical read-only files. The ready markers identify Minecraft 1.8.9 / `ornithe-feather` and 26.2 / `unobfuscated`; both marker `sourceManifestSha256` values match their canonical source-manifest files. Every Java entry in the lane manifest is also present with the same digest in its version's canonical source manifest.

## Claim review

- The old 1.8.9 registry maps ID 27 to `golden_rail` / `PoweredRailBlock`, 28 to `detector_rail` / `DetectorRailBlock`, 66 to `rail` / `RailBlock`, and 157 to `activator_rail` / `PoweredRailBlock` (`Block.java:880-881,966,1173`). The distinct 26.2 registrations and `BlockItemIds` entries are `powered_rail`, `detector_rail`, `rail`, and `activator_rail`. `BlockStateData` pairs old `golden_rail` states with current `powered_rail`; the other three retain their names. This rules out same-name inference for the ID 27 rename.
- Old and current rail-shape enum ordering/serialized names agree: the six straight/slope shapes are IDs 0–5, followed by the four corner shapes at IDs 6–9. Normal-rail metadata uses `byId(metadata)`, whose out-of-range values reset to ID 0; therefore 10–15 select north-south. Powered, detector, and activator rails use `metadata & 7` for shape and bit 3 for `powered`. Their old straight-only property excludes corners; `StateDefinition` rejects setting a disallowed value, so metadata 6, 7, 14, and 15 do not produce a valid old powered-rail state. The corresponding current powered/detector shape property is straight-only; current activator rail uses `PoweredRailBlock`. Current rails add `WATERLOGGED`, outside the old state tuple; the reviewed local outline provider depends only on `SHAPE`.
- Old `AbstractRailBlock.getCollisionShape` returns `null`; `Block.addCollisions` adds a box only when that result is non-null. All four current block registrations use `Properties.noCollision()`, and `BlockBehaviour.getCollisionShape` returns `Shapes.empty()` when `hasCollision` is false. The empty movement collision-provider claim is supported for these four registrations.
- The old ray-outline path is separate: `AbstractRailBlock.rayTrace` calls `updateShape`, then `Block.rayTrace` calls it again. `updateShape` writes shared block-instance bounds with full X/Z footprint and Y maximum `0.625F` for ascending states, otherwise `0.125F`; it does not reset them after the query. 26.2 returns an immutable state-derived shape with Y maximum `8/16` for slopes and `2/16` otherwise, also with full X/Z footprint. Thus the reported ascending-outline difference is correct; no DDA visit or concrete camera movement consequence follows from this alone.

## Scope limits

The report correctly keeps neighbor-derived rail-state production, current-only waterlogging, signal logic, ray traversal order/early-hit behavior, camera reachability, and runtime movement parity open. The verified local provider facts do not establish a full DDA adapter or a reachable movement difference.

No product code, builds, tests, or runtime were used.
