# AutoJump plain full-cube base aliases

## Assignment and conclusion

Source-only verification for the AutoJump full-cube candidate-order dependency, in the loaded-interior query domain with no entity or world-border contribution. The separately established stone timing witness is not re-evaluated here.

**Conclusion: plain dirt, plain cobblestone, and oak planks are each valid exact-identity aliases for stone for this dependency in every inspected release: 1.13, 1.13.1, 1.13.2, 1.14, and 1.14.1.** Replacing the queried block identity among these four does not change query membership/order or collision/headroom/clearance shapes in the stated domain.

## Provenance and source integrity

- 1.13, Ornithe Feather gen2 build.2: `decompiled_minecraft/1.13/ornithe-feather.provenance.json` records exact version 1.13 and mapping coordinate `net.ornithemc:feather-gen2:1.13+build.2`; `.sources.sha256` digest `0509c2614b0bb0e616e2ebc6382f8eb5d031b5d86b834a4a6ed7fd96b6d68f1a` matches its provenance field. Relevant `Block.java`, `Blocks.java`, `WorldView.java`, and AutoJump source hashes match that manifest.
- 1.13.1, Ornithe Feather gen2 build.2: `decompiled_minecraft/1.13.1/ornithe-feather.provenance.json` records exact version 1.13.1 and mapping coordinate `net.ornithemc:feather-gen2:1.13.1+build.2`; `.sources.sha256` digest `21770b01ff3bf443db2e9e1b6df7db25d2fd6b7a72a86228259269c932d3abba` matches its provenance field. Relevant source hashes match.
- 1.13.2, Ornithe Feather gen2 build.2: `decompiled_minecraft/1.13.2/ornithe-feather.ready.json` records mapping artifacts `feather-gen2-1.13.2+build.2-mergedv2.jar` and `feather-gen2-1.13.2+build.2.tiny`, and source manifest SHA-256 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`; the manifest hash and relevant source-file entries match. Its lightweight `ornithe-feather.provenance.json` does not itself record raw artifact/source hashes; the ready marker is the metadata that supplies the source-manifest digest.
- 1.14, Ornithe Feather gen2 build.2: `decompiled_minecraft/1.14/ornithe-feather.provenance.json` records exact version and mapping coordinate `net.ornithemc:feather-gen2:1.14+build.2`; `.sources.sha256` digest `68bc39e38bc501e7c9af315de4678263e1a64606b0b9780099187c7984ccbc98` matches its provenance field. Relevant source hashes match.
- 1.14.1, Yarn build.10: `decompiled_minecraft/1.14.1/yarn/.decompile-provenance.json` pins `https://maven.fabricmc.net/net/fabricmc/yarn/1.14.1+build.10/yarn-1.14.1+build.10-v2.jar` at SHA-256 `f9462af25135abeb22cbdec829cc8618b1a8dd902f17259abfbe7399a222a630`. Relevant AutoJump, world-query, `Block`, `BlockState`, `Blocks`, and `VoxelShapes` source hashes match its per-file `sources` map. The manifest is JSON, not a separate `.sources.sha256` file.

## Evidence

### AutoJump reads

The AutoJump body has the same structural geometry path in all five versions:

- 1.13 `LocalClientPlayerEntity.autoJump`: `decompiled_minecraft/1.13/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java:878-957`. Its block-query shape is at 931; headroom/clearance reads and shape checks are at 906-909 and 942-954.
- 1.13.1 and 1.13.2 `LocalClientPlayerEntity.autoJump`: same path at `.../LocalClientPlayerEntity.java:882-962`; block query at 935; headroom/clearance reads at 910-913 and 947-959.
- 1.14 `LocalClientPlayerEntity.autoJump`: `decompiled_minecraft/1.14/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java:858-943`; query at 913; headroom/clearance reads at 887-890 and 927-939.
- 1.14.1 `ClientPlayerEntity.method_3148` (AutoJump): `decompiled_minecraft/1.14.1/yarn/net/minecraft/client/network/ClientPlayerEntity.java:846-925`; query at 901; headroom/clearance reads follow the candidate loop.

The preflight headroom block states and subsequent vertical clearance block states are tested only through their collision-shape emptiness and maximum Y, not by block identity. Each proposed replacement therefore yields the same result at these reads.

### Candidate registry identities and collision shapes

In 1.13, 1.13.1, and 1.13.2, `Block`'s registrations at lines 779-814 (1.13.1/1.13.2) and 795-830 (1.13) register stone, dirt, cobblestone, and oak planks as distinct ordinary `Block` instances. The plain dirt registration and candidates use `new Block`; stone/cobblestone/oak-planks variables also refer to `new Block` registrations. `Block.getShape` returns the singleton `VoxelShapes.block()` at lines 388-389 (1.13.1/1.13.2) or 404-405 (1.13); `Block.getCollisionShape` delegates to that state shape when collision is enabled at lines 393-394 (1.13.1/1.13.2) or 409-410 (1.13).

In 1.14, `Blocks.java:22,38,45-48` registers the four candidates as ordinary `Block` instances. `Block.java:360-366` returns `VoxelShapes.block()` for the base shape and delegates collision shape to the state shape. In 1.14.1 Yarn, `Blocks.java:17,31-43` registers the same IDs as ordinary base `Block` instances; `Block.java:366-372` returns `VoxelShapes.fullCube()` for the base outline and uses it as the collidable shape. Thus stone and each candidate provide the same full-block collision singleton in each version; only their registry singleton identities differ.

### Query inclusion/order

- 1.13 `WorldView.getBlockCollisions`: `decompiled_minecraft/1.13/ornithe-feather/net/minecraft/world/WorldView.java:113-168`. It visits the same coordinate loops regardless of block identity, folds any `voxelShape6 == VoxelShapes.block()` into the shared `VoxelSet`, then emits the aggregate. All four identities take that same branch; no per-block block-identity branch applies.
- 1.13.1/1.13.2 `WorldView.getBlockCollisions`: corresponding source lines 112-150. It traverses the same spatial iterator; any collision shape identical to `VoxelShapes.block()` is put in the same `VoxelSet`, with a final aggregate emitted after the per-cell stream. These candidates all take that full-cube route. Replacing one by another leaves the iterator and aggregate occupancy unchanged.
- 1.14 `WorldView.getCollisions`: `decompiled_minecraft/1.14/ornithe-feather/net/minecraft/world/WorldView.java:152-194` (method body). It likewise folds full-block singleton shapes into `VoxelSet`; candidate identities do not alter the branch.
- 1.14.1 `ViewableWorld.getCollisionShapes`: `decompiled_minecraft/1.14.1/yarn/net/minecraft/world/ViewableWorld.java:129-187`. The loaded-interior domain removes border contribution; the per-position iteration and overlap predicate depend on bounds and collision shape. The only explicit block-identity special case in this candidate iteration is `Blocks.field_10008`, registered as `moving_piston` at `Blocks.java:343-345`; none of stone, dirt, cobblestone, or oak planks has that identity. The adjacent `method_17900` predicate is computed from whether the collision shape extends outside unit-block coordinates (`BlockState.java:92` and `:369-396`), which is false for all four ordinary full cubes. Thus the candidates have equal eligibility and preserve per-block iteration order.

## Limits

This verifies only the stated plain-block aliases for AutoJump's full-cube query/candidate-order dependency in the five exact source trees. Coarse dirt, dye families, other shapes, other movement operations, source patch cutover timing beyond these exact releases, runtime parity, and full-pair acceptance are not covered. No product code, backlog, primary research, builds, tests, or game launches were touched.
