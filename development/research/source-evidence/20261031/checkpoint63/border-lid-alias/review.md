# Border and Shulker source alias verification

Scope: verify that the accepted 1.16.5 → 1.17 static WorldBorder operation and the tiny-progress ShulkerBox collision-shape provider carry across exact releases 1.16, 1.16.1, 1.16.2–1.16.5, 1.17, and 1.17.1. This is a bounded source alias audit of the accepted operations, not a rediscovery of the 1.16.5→1.17 pair.

## Verdict

Both accepted old-side operations apply throughout 1.16.0–1.16.5 and remain changed in 1.17 and 1.17.1. Their old-side implementation group therefore begins at `V1_16`, not `V1_16_2`, if the group is keyed to these operations. The earlier 1.16 and 1.16.1 containing classes are not all whole-file-identical to 1.16.2, but the relevant method bodies and direct collision route have matching SHA-256s across 1.16–1.16.5. All examined source rows match their release source manifest: sidecar source manifest for 1.16, 1.16.1, 1.16.2, 1.16.5 and 1.17.1; embedded `.decompile-provenance.json` source map for 1.16.3, 1.16.4 and 1.17. The detailed 176 class hashes are in `source-hashes.tsv`; the operation member body hashes are in `operation-method-hashes.tsv`.

## Exact release identity and hashes

Primary read-only sources: `D:\\Javastuff\\LegacyParkourCompat\\decompiled_minecraft\\<version>\\mojmap`. Mapping namespace is Mojmap for each snapshot. For 1.16.3 and 1.16.4, I used each source tree's embedded `.decompile-provenance.json` `sources` map as required; its SHA-256 is recorded as `manifest_sha256` for those rows. All 22 selected source files per release matched the corresponding sidecar or embedded manifest (176/176 matches; no missing files).

The accepted endpoints' source identities remain the original frozen identities: 1.16.5 source manifest `9499f2611d0e6dde37cb635f1a28a591416382d6bc188b37d5f99e2b2a20823b`, and the 1.17 input client and Mojmap mapping hashes `14122287d4e4256c2d65ec8c0ccfa3b56448495fa3ccabc87d0f0eeb4fa1c30f` / `191b957aaa40b294402feef29651ae7a9f1263ec22c5382f3b8fa0a4918b0471`. The expanded hashes are an applicability check against the exact version source trees and manifests.

## Static WorldBorder inner clamp and reversed endpoints

For 1.16 through 1.16.5, `WorldBorder.StaticBorderExtent.updateBox()` is the same method body in every checked version (`operation-method-hashes.tsv`). It computes `minX/minZ` with `Math.max(center - size / 2, -absoluteMaxSize)` and `maxX/maxZ` with `Math.min(center + size / 2, absoluteMaxSize)`. It does not clamp each endpoint to both bounds. For the accepted equal raw state, this permits `minX=30,000,000` and `maxX=29,999,984`. `Shapes.box` calls the six-coordinate AABB constructor, which sorts each endpoint pair; `Shapes.join(INFINITY, box, ONLY_FIRST)` therefore retains the nonempty sorted interior rectangle. `WorldBorder.java`, `Shapes.java`, and `AABB.java` relevant bodies/hashes are identical across all 1.16 snapshots, including 1.16 and 1.16.1.

In 1.17 and 1.17.1 the same update now applies `Mth.clamp(rawEndpoint, -absoluteMaxSize, absoluteMaxSize)` independently to all four endpoints. The accepted input consequently collapses X to equal endpoints at `29,999,984`. The `Shapes.box`/`Shapes.create` threshold produces an empty inner shape, leaving the collision shape as `INFINITY`. The relevant WorldBorder, Shapes and AABB source hashes match between 1.17 and 1.17.1. `Entity.collide(Vec3)` retains the same border-shape overlap polarity and `1.0E-7` deflation gate across all inspected versions; the whole Entity file changes at release boundaries, but the relevant method hash is stable within 1.16–1.16.5 and within 1.17/1.17.1. Thus the original conditional direct-response witness remains valid at every 1.16 release and both 1.17 releases.

## Tiny ShulkerBox lid provider encoding

From 1.16 through 1.16.5, the provider remains `ShulkerBoxBlock.getShape()` → block entity `getBoundingBox(state)` → `getBoundingBox(direction)` → `Shapes.block().bounds().expandTowards(0.5F * progress * directionStep)`. The relevant method hashes are identical across all six 1.16 releases. The animation update, `getProgress(1.0F)` interpolation, `Mth.lerp`, and their dependencies are also unchanged. The formerly accepted reachable residual is still produced by three OPENING ticks then three CLOSING ticks from zero: `1.4901161193847656E-8F`; West/Down/North therefore still yield an outward endpoint `-7.450580596923828E-9`.

The old `Shapes.create(AABB)` plus floor-based `findBits` rejects that negative endpoint at each tested grid denominator and uses `ArrayVoxelShape` with exact AABB bounds. The exact old create/findBits method bodies match across 1.16–1.16.5. 1.17 and 1.17.1 use the same provider method interface, but the block-entity geometry delegates to `Shulker.getProgressAabb`; new `Shapes.create` routes to round-based `findBits`, which rounds the tiny negative endpoint to zero and returns the full-block special case when all axes encode zero. The provider AABB, raw progress, collision query and one-axis response conditions from the frozen erratum remain unchanged; the tiny strip is lost at the shape-encoding step in both 1.17 releases.

The direct player response uses the single-axis route `Entity.collide(Vec3)` → `Shapes.collide(Axis.X, AABB, LevelReader, dx, CollisionContext, stream)` → direct block-state collision-shape lookup and clipping. The `Entity.collide(Vec3)` and both `Shapes.collide` LevelReader wrapper/implementation method bodies are identical within 1.16–1.16.5 and within 1.17/1.17.1. No `CollisionSpliterator` admission filter is part of this one-axis fixture. The old/new movement difference is therefore still specifically attributable to the provider VoxelShape encoding. The collision-call route and math do not establish entity-interaction or block-side push behavior, which are outside this alias claim.

## Release boundaries and limits

- 1.16, 1.16.1, 1.16.2, 1.16.3, 1.16.4 and 1.16.5: both old operations' relevant code is byte-for-byte identical at the checked member level.
- 1.17 and 1.17.1: relevant new operation members and their source files are identical for WorldBorder/Shapes/provider code; `Entity.java` whole-file hash differs but the reviewed collision method bodies match.
- The old WorldBorder update and Shulker `Shapes.create/findBits` code is unchanged from the first inspected 1.16 snapshot; no 1.16.2-specific introduction was found. The applicable old-side cutover for these two operations is the first 1.16 release.
- The original equal-input WorldBorder witness remains conditional on supplied client-side border values; no border producer claim is added. The Shulker claim remains limited to the accepted raw collision-shape encoding and reachable tiny animation residue. This is source evidence, not runtime parity.

No source files were changed. No build, test, game launch, source generation, staging or Git-history review was performed.
