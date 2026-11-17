# Bounded source audit: fluid player-movement dependency

**Verdict: ACCEPT (bounded fluid/contact-response slice only).** This audit accepts the report's comparison of current-state liquid contact, water/lava travel formulas, and associated local-player predicates for exact pair `1.10.2→1.11`. The pair remains **partial**; this does not accept the separate `MoverType.SELF`/`Entity.move` candidate, close remaining providers/dependencies, or freeze pair coverage.

## Bound inputs and identity

- Report: `build/source-early-patches-20261117/1.10.2--1.11/dependencies/fluid-player-movement.md`
- Report SHA-256: `6e07e1afeea071df18d069ad6d2130aed2ec83c29db30bec3d5793c0f2d87f2d`
- Exact namespace: `decompiled_minecraft/<release>/ornithe-feather/`
- 1.10.2 ready marker SHA-256: `4c6d236e1d06ffc365452c48e73dfa2bf2414bbf4ea67a24a7010c5583906a2d`; source manifest SHA-256: `91b0f478acb7b6f13463c35b268a30d2806f583402631a56f54ce2eb70d1ec71`.
- 1.11 ready marker SHA-256: `3c4102230dba0f9d2d07f752bbd2a19d2717375f6e31fb618c0450b7fd82bd45`; source manifest SHA-256: `8b3e5da58494f1dba77410960753b0bf646425a1ada491d8242cc71e6c7a59ca`.
- I independently checked all 18 source-file hashes named by the report against the exact source files and the corresponding manifest rows; all match.

## Audit result

The reported `Entity.checkWaterCollisions` comparison has the same boat gate, bounds/state checks and water-contact effects. The expression change from assigning `onFireTimer = 0` to calling `extinguish()` is behavior-equivalent for this state: both exact `Entity.extinguish` bodies assign `onFireTimer = 0` (1.10.2 `Entity.java:429–431`; 1.11 `Entity.java:438–440`). The preceding base-tick placement and the travel section order match (1.10.2 `Entity.java:304–366`, `LivingEntity.java:1727–1748`; 1.11 `Entity.java:313–375`, `LivingEntity.java:1781–1802`).

`World.applyLiquidDrag` retains the same loaded-area gate, floor/ceil iteration bounds, x/y/z traversal, material-drag dispatch, normalization, and `0.014` current addition. `World.isAreaLoaded` and the client chunk-presence check have the same relevant behavior in both versions. The compared `LiquidBlock.getFlow`, depth/face helpers, height-loss expression, material drag, empty collision shape and the `isInLava`/submersion predicates support the report's current-vanilla-state boundary. `LiquidBlock.getHeight`/`getHeightY`, added in 1.11, are called in the reviewed source by boat, fishing-bobber and water-rendering paths, not local-player travel; the reported exclusion is accurate.

The water and lava branches of `LivingEntity.moveRelative` preserve their fluid gates and arithmetic/order: water depth-strider handling and damping, lava acceleration and damping, gravity updates, horizontal-collision `canMove` checks, and the `0.3F` vertical response match. The only `move` call-site signature addition is `MoverType.SELF`, which this report explicitly leaves to the separate mover-type candidate. The client-local control predicate remains separately visible in the call path: `PlayerEntity.isLocal` returns false in both base classes, and `LocalClientPlayerEntity` overrides it in both releases (1.10.2 `LocalClientPlayerEntity.java:317`; 1.11 `LocalClientPlayerEntity.java:323`).

The noted lava tick-rate and flowing-liquid neighbor-update edits concern block-state scheduling/production, not the compared player travel formulas. Both base `World.scheduleTick` overloads are empty (1.10.2 `World.java:1217–1223`; 1.11 `World.java:1239–1245`); `ClientWorld` has no override, and its `tickChunks` implementation performs chunk ambience/light work rather than scheduled block ticks (1.11 `ClientWorld.java:126–151`). No claim about historical server-side fluid state production is accepted.

## Boundaries

Acceptance is conditional on the compared current block states and the same entity movement/clipping path. It says nothing about production of those states, server fluid scheduling, damage/health outcomes, vehicles, rendering, or the separate mover-type candidate's applicability. Exact-pair coverage and all other dependencies remain open.
