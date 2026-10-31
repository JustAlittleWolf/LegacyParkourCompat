# Frozen collision-query/player-witness source roster

This roster was frozen before opening any prior source coverage report. Inputs are the exact supplied Mojmap trees and the assigned path `Shapes.create(AABB)` / `findBits` → collision iteration → `Entity.collide` gate. The investigation is source-only and compares 1.16.5→1.17 (retained 1.17.1 where it bounds the later pair).

- `core-roster.tsv` lists exact core query, shape-engine, world-border, and LocalPlayer route files to compare.
- `provider-roster.tsv` is the case-sensitive union of every `world/level/block/**/*.java` file in the three trees declaring a shape-provider method (`getCollisionShape`, `getShape`, `getVisualShape`, or `getOcclusionShape`). It is a candidate provider roster, not a claim every provider affects player collision.

The audit will trace provider state inputs and classify provider families by whether they can enter LocalPlayer block collision, border collision, entity collision, or an excluded/nonmovement route. No product, wiki, backlog, runtime, or prior report informed this frozen list.
