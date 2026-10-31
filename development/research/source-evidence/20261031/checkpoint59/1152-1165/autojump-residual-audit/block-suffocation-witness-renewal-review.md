# Renewed independent binding: farmland LocalPlayer escape

## Verdict: ACCEPT

This review renews the prior `REVISE` only for the corrected canonical draft. The accepted witness and its evidence are unchanged. The class attribution correction is accurate against the exact 1.16.1 source, and the draft now binds the correct source boundary and consequence.

Reviewed immutable inputs:

- Canonical draft `.native-source/20261030/1152-1165-finish43/canonical-findings/1.16.1--1.16.2/farmland-suffocation-localplayer-escape.md`, SHA-256 `2889a2657d192d442e3fc17f8ef196b0b7f032e6932c699373c00c9a20b39ed8`.
- Concrete witness `.native-source/20261030/1152-1165-finish43/block-suffocation-player-witness/witness.md`, SHA-256 `c8277296919e394e4861f2d6d5264e09f7effe54f79db412d6e0968047e8df97`.
- Prior independent route review `.native-source/20261030/1152-1165-finish43/autojump-residual-audit/block-suffocation-witness-independent-review.md`, SHA-256 `9dd1272f99b9d7147c9d0d658407843fb6e2b822abb03c5707d2aa5ede16cb63`.

The corrected draft now distinguishes 1.15.2's `Block.isSuffocating` method from 1.16.1's `BlockBehaviour.Properties.isSuffocating` default predicate, dispatched by `BlockBehaviour.BlockStateBase.isSuffocating`. The exact 1.16.1 default at `BlockBehaviour.java:786–787` is `this.material.blocksMotion() && blockState.isCollisionShapeFullBlock(...)`; `BlockStateBase.isSuffocating` delegates to that predicate at `:633–635`. This confirms the claimed false Farmland result for the same 15/16 collision shape.

All four new probes and the final sequential result remain as previously recomputed: WEST, WEST, EAST, EAST; final `deltaMovement.x = +0.1`. The 1.16.1→1.16.2 cutover is verified for both `.isSuffocating(Blocks::always)` on Farmland and the new four-call `moveTowardsClosestSpace` consumer, with persistence in 1.16.3–1.16.5. Every relevant inspected source matched its own sidecar manifest or embedded 1.16.3/.4 provenance source map, as enumerated in the prior independent review.

The acceptance is conditional on the stated equal sampled block state and embedded pre-tick player box. It makes no ordinary-walking reachability, producer-frequency or later-trajectory claim. The witness independently exercises Farmland only; Soul Sand and Grass Path remain provider registration observations, not this accepted movement witness.
