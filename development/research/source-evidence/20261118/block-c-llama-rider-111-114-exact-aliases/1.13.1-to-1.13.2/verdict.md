# Independent source audit: Llama rider placement (1.13.1 → 1.13.2)

Verdict: **ACCEPT — bounded no-difference claim supported.** The reviewed claim is that an ordinary adult Llama's direct player-passenger placement and its client ride-tick route have no source difference across these two releases. This does not close the 1.13.1–1.13.2 pair or make claims about llama/non-player movement, vehicle physics, or other actors.

## Immutable input

- Draft reviewed: `C:/Users/Wolfi/.codex/visualizations/2026/11/17/01a1eb7b-b9ff-7a20-8686-0295dde20f26/c-llama-1131-1132-position-intermediate/report.md`
- SHA-256 of exact draft bytes: `1b037eec2afefe5c53590c5f32d82bd582a4b7f59b0a22264ec1276d541327c8`.
- Input revision and sources were read-only. No generation, product inspection, test, build, or launch was performed.

## Evidence identity and integrity

Both local source roots are `decompiled_minecraft/<version>/ornithe-feather/`, with exact mapping namespace `ornithe-feather` (Ornithe Feather). The 1.13.1 provenance names `net.ornithemc:feather-gen2:1.13.1+build.2` and pins merged V2 `6737db17e8cc724a018aad93d7fe5051a50e77136848d78d79b62310846fa65d` and Tiny `236423be826fb19d08edb3bf7cad9100eab72c5ddbbed8337afcf6ae304c4d14`. The 1.13.2 artifact manifest/ready marker identify `feather-gen2-1.13.2+build.2`, merged V2 `317384d4faccc2939c3b14252993d31745eb42aad59f2f0ea78893cbe4e7283b`, and Tiny `b3fd787448aed2c6e115d965d9edbb437ce47794ba63c7883e9014fd41262f71`.

The on-disk 1.13.1 and 1.13.2 source-manifest SHA-256 values match the report and their provenance/ready metadata: `21770b01ff3bf443db2e9e1b6df7db25d2fd6b7a72a86228259269c932d3abba` and `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`. Independently hashed every manifest entry against the local source tree: 2,710/2,710 files for 1.13.1 and 2,711/2,711 for 1.13.2, zero mismatches. The 1.13.2 verification record additionally says 2,711 sources and raw source inputs matched, with zero source-file differences; its documented mismatch is only a regenerated intermediate mapped-client JAR.

The mapping artifacts themselves are not present in the current checkout/cache, so I confirmed their exact identifiers and recorded hashes against the prepared provenance/artifact manifests but could not recompute the artifact hashes from the binary files. This limits independent input-artifact revalidation, not the local decompiled-source integrity check.

## Claim checks

- `LlamaEntity.java` is byte-identical across the releases (SHA-256 `5be437bcd5600c4dbcd7ef5e5224ac31baa69efbd69b11152b0bc04081922089`). The constructor sets dimensions to `(0.9F, 1.87F)`. The same `updateRiderPositon(Entity rider)` body in each version guards with `this.hasPassenger(rider)`, computes cosine then sine from `bodyYaw`, and calls `setPosition(x + 0.3F * sin, y + getMountHeight() + rider.getRideHeight(), z - 0.3F * cos)`. The declared `h = 0.3F` local is unused. `getMountHeight()` is `this.height * 0.67`; the float height is promoted for multiplication by the unsuffixed double literal.
- `Entity.java` is byte-identical (SHA-256 `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269`). `hasPassenger(Entity)` iterates the mount's passenger list and compares the direct passenger entity; base `Entity.rideTick()` calls `getMount().updateRiderPositon(this)` while still riding. The llama override does not invoke base placement.
- `PlayerEntity.java` differs elsewhere, but the cited methods match: `rideTick()` delegates through `super.rideTick()` on the client path, and `getRideHeight()` returns `-0.35` (`PlayerEntity.java` SHA-256 values: 1.13.1 `4c1b14b4727116071b1ba9e3eeae84b8077f3d767e10e49b6a68a2556da32272`; 1.13.2 `4ed22f6c5a3c55d67eed782070ac722201df4d624adbc90779f1fd29c2876633`). `LocalClientPlayerEntity extends ClientPlayerEntity extends PlayerEntity extends LivingEntity`; there is no intervening `getRideHeight()` override. The local-player `rideTick()` delegates to `super.rideTick()` in both releases; `LocalClientPlayerEntity.java` is byte-identical (SHA-256 `2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29b29d8d9bf42bf`) and `ClientPlayerEntity.java` is also byte-identical (SHA-256 `99b57acea68323125d859313ef5dd2c55d0703c08c5cce475c0227da0d12d1ee`).
- The real local-player callback is reachable: `Minecraft.tick()` adds its local player to the world entity list with `world.addEntityAlways(this.player)`; it then calls `world.tickEntities()`. `World.tickEntities()` iterates that list and calls `tickEntity(entity3)`. `World.tickEntity` dispatches `entity.rideTick()` for a riding entity. Dynamic dispatch reaches `LocalClientPlayerEntity.rideTick()`, then the player/entity ride chain above, then the mount's virtual `updateRiderPositon`; for a direct llama passenger this is `LlamaEntity.updateRiderPositon`. The `Minecraft.java` source is byte-identical across the pair. `World.java` hashes differ, but the examined dispatch slices match exactly.
- Search of both mapped source trees found `LlamaEntity` and no `TraderLlama` class. No evidence here is extended to other actor classes or vehicle/non-player behavior.

## Conclusion

The draft's bounded no-difference conclusion, arithmetic description, direct-passenger guard, and player callback explanation are source-supported. Keep it as a bounded slice; it is not pair-complete movement coverage.


