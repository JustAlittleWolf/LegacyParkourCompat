# Independent audit verdict — S5.2 ground friction

Audited report: `build/later-source-20261116/1.21.11--26.1.2/ground_friction_unobfuscated_revision_r2/report-r2.md`

Audited report SHA-256: `1b24ef978d38a2d5a89543f0ee8b208a288d371e8a29377ccc16fedac50b691f`

## Verdict

**CONCUR — NO DIFFERENCE OBSERVED in the bounded S5.2 slice.** The reported source expressions, operation order, gates, casts and float constants match across 1.21.11 Mojmap and 26.1.2 unobfuscated source for movement-support position selection, ordinary in-air friction acquisition/consumption, the LivingEntity/Player speed-factor override path, base speed-factor fallback and X/Z movement scaling, and the stated property defaults/accessors. This verdict is limited to the report's bounded slice; it does not close tag/resource membership, full `Entity.move` control flow, provider inventory, or whole-pair coverage/parity gates.

## Evidence identity audit

The report's own SHA-256 matches the supplied revision. Both ready markers declare `status: ready` and the stated exact version/namespace pairs: `1.21.11`/`mojmap` and `26.1.2`/`unobfuscated`. The corresponding provenance files declare those same version/namespace identities and exact `decompileMinecraft` runs. Ready-marker manifest digests agree with the root-level `mojmap.sources.sha256` and `unobfuscated.sources.sha256` manifests.

Individually recomputed SHA-256 values for all six cited files on both endpoints match the cited values and manifest entries: `Entity.java`, `LivingEntity.java`, `Player.java`, `Block.java`, `BlockBehaviour.java`, and `Blocks.java`.

## Independent source retrace

- `Entity.getBlockPosBelowThatAffectsMyMovement()` uses `getOnPos(0.500001F)` on both endpoints. The paired `getOnPos(float)` bodies retain the same support-position branch, `offset > 1.0E-5F` guard, fence threshold/tag condition, wall and fence-gate checks, support-Y adjustment through `Mth.floor(position.y - offset)`, and fallback X/Y/Z floor order. Only local names/formatting differ.
- `LivingEntity.travelInAir` uses the movement-support position, reads block friction only under `onGround()`, otherwise uses `1.0F`, multiplies the block friction by `0.91F`, and passes the unmultiplied block friction into `handleRelativeFrictionAndCalculateMovement` on both sides. The paired helper uses the same grounded expression `getSpeed() * (0.21600002F / (friction * friction * friction))`, otherwise `getFlyingSpeed()`, before `moveRelative`; the inspected post-move branch retains the same `shouldDiscardFriction()` gate, horizontal `friction * 0.91F`, `FlyingAnimal` selection, and `0.98F` vertical fallback.
- `LivingEntity.getBlockSpeedFactor()` is exactly `Mth.lerp((float)this.getAttributeValue(Attributes.MOVEMENT_EFFICIENCY), super.getBlockSpeedFactor(), 1.0F)` on each endpoint. `Player.getBlockSpeedFactor()` has the same flying/fall-flying gate and delegates to `super` otherwise. Base `Entity.getBlockSpeedFactor()` retains in-block lookup first, water/bubble-column exception, `== 1.0` fallback to movement-support block, and same return ordering. The inspected movement consumer scales X and Z by the selected factor while multiplying Y by `1.0` on both sides.
- `BlockBehaviour` retains defaults `friction = 0.6F` and `speedFactor = 1.0F`, copies these properties into the block, and has equivalent float setters. `Block` getters return the corresponding fields. The report's sampled explicit provider literals are present with matching values on both sides; this check does not establish a complete provider inventory or effective tag membership.

No concrete discrepancy was found that would overturn the report's bounded verdict. Tag/resource contents, full `Entity.move` surrounding gates, other movement slices, and whole-pair inventory remain open as stated in the report. No source, backlog, product, or Git files were changed; no decompilation, build, tests, or runtime actions were performed.
