# Independent review: Air and Fire ray-provider exclusion

## Verdict

**ACCEPT**, bounded to old IDs 0 (Air) and 51 (Fire): their old ray-provider rejection, null movement-collision results, and the reported current registration/collision/shape facts. This does not establish DDA visitation, first-hit order, modern ray-hit behavior, or overall parity.

## Reviewed inputs

- Finding: `.native-source/20261028/collision-next/dda-ray-rejected-family-0-51/FINDING.md`
- Finding SHA-256: `6A6D44D40252867EA9D356FEF9CD62C2CEB4E4E1EFC239E28499E26CA3448703`
- Source manifest: `.native-source/20261028/collision-next/dda-ray-rejected-family-0-51/source-hashes.json`
- Source manifest SHA-256: `1FF042A99FCE648D6B6BF8A63AC3C58C503985AE4FE9FDA13B61C26CE1CE20B4`

## Source and hash verification

I independently recomputed all 12 selected Java-file SHA-256 digests from the canonical read-only source tree and matched them both to the manifest entries in the finding and the canonical `ornithe-feather.sources.sha256` / `unobfuscated.sources.sha256` files. All six release identity hashes (ready marker, provenance, and source manifest for each release) also match their canonical bytes and the recorded values.

## Claim checks

- 1.8.9 `AirBlock.getCollisionShape` returns null and `canRayTrace(BlockState, boolean)` returns false. `FireBlock.getCollisionShape` returns null and its no-argument `canRayTrace()` returns false; the base state overload delegates to that method. `World.rayTrace` requires this gate before dispatching `Block.rayTrace` at both the start cell and later cells. Therefore, for these block states, the ray path skips the provider and its default `updateShape` call. This is a provider-gate statement only and makes no claim about which cells traversal visits.
- `Block.addCollisions` appends a box only when `getCollisionShape` is non-null, supporting the bounded no-movement-box conclusion for both legacy blocks.
- 26.2 `Blocks.AIR` and `Blocks.FIRE` are registered with `.noCollision()`. The base `BlockBehaviour.getCollisionShape` returns `Shapes.empty()` when collision is disabled. The report correctly keeps Air's empty shape distinct from Fire's outline: `BaseFireBlock.SHAPE` is a nonempty one-sixteenth-high column, and `FireBlock.getShape` uses that fallback when no direction attachment shape is selected.
- The explicit old Air mapping and Fire entries in `BlockStateData` support the stated key identity. The report correctly limits this to key mapping and does not assert full equivalence of every old Fire visual/state property.

## Conclusion

All checked bounded claims match the cited exact-version sources. No product code, tests, builds, or runtime were inspected or used.
