# Independent source audit: fall-distance boundary r5

**Verdict: REQUEST CHANGES**

Candidate: `build/later-source-20261116/1.20.6--1.21.11/fall_distance_boundary_revision_r5/coverage-r5.md`  
Candidate SHA-256: `f75bebafd12db80f0b576749ef7b34f35fdb249c67ec31b7b299660063d52ef0`

## Verified

- The candidate's five source identity table pairs match the exact bytes in the canonical 1.21.1 and 1.21.2 Mojmap trees. The 1.21.1 ready-marker/source-manifest binding and 1.21.2 hidden mapping-local provenance binding match the identities recorded in the report.
- `Player.aiStep`/`Player.travel` support the stated reset relocation: 1.21.1 resets after inherited travel in the nonpassenger flying branch; 1.21.2 resets before inherited aiStep movement and its travel branch has no corresponding post-travel reset. Ordinary negative-Y `Entity.move` fall accounting therefore yields the stated reset-versus-retained-fall-distance distinction under the stated no-collision conditions.
- Both `Player.java` sources retain the edge-backoff gates, shift-based `isStayingOnGroundSurface`, `isAboveGround` test, `.05` probe/backoff loop, and `canFallAtLeast` AABB expansion as described. The corrected vertical arithmetic is consistent: at feet `0.525`, depth `.6` reaches `-.07501`; depth `.45` reaches `+.07499`. Given only the stated support collision, the former query collides and the latter clears it. The `0.001` support overlap and a positive-Z request greater than `.001` and at most `.05` create the described old-only edge-backoff result.
- The `CollisionGetter.noCollision(entity, AABB)` source in both versions checks block collisions, entity collisions, and (for a non-null entity) world-border collision. The candidate correctly states these as construction preconditions and calls out other block collisions in the query/travel volume.
- I independently recomputed every SHA listed in the candidate's table, including the candidate SHA. The report's line anchors and mapping paths are consistent with the cited source.

## Required correction

The draft does not include either `KeyboardInput.java` identity in its source table and does not derive or state the assigned exact follow-up displacement (`0.005880000459849821`). It only calls it “a small positive-Z SELF displacement.” This leaves the witness request unbound to exact source bytes and does not let a reader audit the stated `.98` input damping and `0.3` shift slowdown. Add each endpoint's canonical `net/minecraft/client/player/KeyboardInput.java` path and SHA, then state the constrained input derivation and resulting SELF vector. For the 1.21.2 side, the relevant source chain is `LocalPlayer` calling `input.tick(isMovingSlowly(), sneakingSpeed)`, `KeyboardInput.tick` applying the supplied factor, `LivingEntity.aiStep` multiplying `zza` by `0.98F`, and `getInputVector`/`moveRelative` using `0.02F` speed for a nonpassenger airborne player. Explicitly hold the assumptions needed for that exact value (forward-only, shift slow factor `0.3`, no item-use scaling, no other horizontal momentum, no sprint, yaw zero). Verify whether the previous endpoint needs its own analogous call-path statement or whether the witness uses a directly constructed identical SELF request in both profiles.

This is a narrow evidence-completeness request; the corrected vertical witness and stated collision preconditions pass. No runtime, pair-coverage closure, implementation, product, build, test, or Git claim is made.
