# Independent source audit: fall-distance boundary r4

## Verdict

**REQUEST CHANGES** for candidate `coverage-r4.md`, SHA-256 `FFCA062E77F47E05017551115FD2C08FE53167705800B323EA55DE284F44920C`.

The 1.21.1→1.21.2 reset relocation and the stated input/velocity arithmetic are supported by the exact sources and identities. The proposed support geometry does not establish the claimed backoff difference: the player moves downward during the preceding flight tick, and the 0.45-deep query still intersects the stated support block.

## Independent source and identity checks

I read the repository guidance and orchestration, discovery, and source-lane workflows before auditing. The candidate hash above was recomputed from its bytes. I then independently inspected the canonical 1.21.1 Mojmap source and the 1.21.2 Mojmap source and provenance before reading the candidate. The 1.21.1 ready marker binds version `1.21.1`, Mojmap, and its source manifest; 1.21.2 mapping-local `.decompile-provenance.json` binds source paths and hashes. The provenance file SHA-256 is `00F399407DC447737EA83E3E46562418F8C712F66E3F51CB85D46AFA2ECAC575`; the candidate's corrected 1.21.2 `KeyboardInput.java` hash, `2a0994e8a6a2fdf9e76480227216db4a4252734fca8ca0aedd25dde77be75ba6`, matches canonical bytes and provenance.

All seven Java-file pairs listed by the candidate matched canonical bytes and the endpoint manifests/provenance: Player (`ed32b88c…925c71` / `a803203e…1340de`), Entity (`b81905c7…b99850` / `a93719c3…173c2b9`), LivingEntity (`324a3eee…a3a0d8` / `08739049…81cfc52`), LocalPlayer (`c555e68a…24b583` / `fbd40f1f…ad3fe0`), KeyboardInput (`a8064906…623ff0` / `2a0994e8…be75ba6`), Abilities (same hash at both endpoints, `a4f952ad…609edc`), and Attributes (`a9a19f55…d2db7c` / `14bcc582…13a3ea`). Ellipses here abbreviate hashes already given in full in the hash-bound candidate; no hash mismatch was found.

## Confirmed source findings

- In 1.21.1, non-passenger creative flight in `Player.travel` calls inherited travel, applies the vertical velocity write, then resets fall distance. In 1.21.2, the non-passenger flying reset occurs in `Player.aiStep` before inherited `LivingEntity.aiStep`; the `Player.travel` flight branch has no post-travel reset. The adjacent cutover is correctly identified.
- The 1.21.2 local-flight shift path adds `-1 * 0.05F * 3.0F = -0.15F` to vertical velocity. `LivingEntity.handleRelativeFrictionAndCalculateMovement` calls `move(SELF, getDeltaMovement())` before subsequent travel velocity updates. With the candidate's clean airborne conditions, a preceding tick therefore changes player feet height by `-0.15` and records fall distance `0.15` on the 1.21.2 path; the 1.21.1 post-travel reset clears that accumulated value.
- `LocalPlayer` supplies the slow-movement factor from `Attributes.SNEAKING_SPEED`; `KeyboardInput.tick` scales forward input by that factor. `LivingEntity.aiStep` applies `0.98F`; the non-flying airborne `Player.getFlyingSpeed()` is `0.02F`. At yaw 0 with forward-only input this supports the candidate's approximately `+0.005880000459849821` requested Z displacement. The `0.05` backoff threshold is correctly applied.
- The query's `noCollision` checks both block and entity collisions. The geometric witness must explicitly keep the relevant query volume clear of entities and the world border, in addition to the stated effects/collisions conditions.

## Blocking geometry correction

The candidate starts the preceding creative-flight tick with feet `0.525` above a full-block top at `y=0`. After the source-derived `-0.15` movement, feet are at `0.375`. On the 1.21.2 path, `canFallAtLeast(0,0,0.45)` then tests down to `0.375 - 0.45 - 0.00001 = -0.07501`. That volume overlaps the full support block whose top is `y=0`; `noCollision` is false, `isAboveGround(0.6)` is true, and the proposed old/new branch difference does not follow. The candidate's sentence that the 0.45 probe does not reach that block assumes the pre-flight position is still the post-flight position.

A source-consistent repair is to set feet to `0.675` **before** the preceding flight tick, so the post-tick feet are `0.525`. Then the old 0.6 query extends to `-0.07501` and intersects the support block, while the new 0.45 query extends only to `+0.07499` and misses it. Keep the narrow `minZ=-0.001` overlap and state explicitly that no entity or world-border collision intersects the query. Recompute the edge witness against that post-flight position and preserve the `isFallFlying()==false` precondition before both ticks.

No full-pair, runtime, implementation, or product claim was evaluated. No build or test was run.
