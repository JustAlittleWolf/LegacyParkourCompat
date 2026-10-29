# Independent review: daylight detector DDA provider closure

## Verdict

**ACCEPT within the bounded alias, metadata, and collision-provider scope.** The report correctly maps old IDs 151 and 178 to the current daylight detector's ordinary and inverted states, respectively, and establishes equal collision geometry. It does not claim signal, ray traversal, DDA observer reachability, or runtime parity.

Reviewed report: `.native-source/20261028/collision-next/dda-daylight-detectors-151-178/report.md`, SHA-256 `FB0D07495BAAEA86176FC0CC743EF1A886329B1C9530A7EBDE105DCF4E0440F1`.

## Evidence checks

- Rehashed all 17 entries of the supplied `sources.sha256`; every entry matched its canonical file. The ready markers identify 1.8.9 `ornithe-feather` and 26.2 `unobfuscated`; their declared source-manifest digests match the supplied marker identities.
- Independently matched all 11 selected Java file hashes against the exact canonical source manifests.
- In 1.8.9 `Block.java`, ID 151 registers `daylight_detector` with `new DaylightDetectorBlock(false)` and ID 178 registers `daylight_detector_inverted` with `new DaylightDetectorBlock(true)`. `DaylightDetectorBlock` has only `POWER` (0–15); its metadata decoder/encoder directly use that property. Inversion is fixed by the two registration instances, not by metadata.
- The legacy constructor initializes `(0,0,0)–(1,0.375,1)` bounds and `updateShape` writes the same bounds. Neither registration overrides the base collision provider. Base `Block.getCollisionShape` uses these instance bounds, and `addCollisions` applies that collision box. `Block.rayTrace` invokes `updateShape`, so it also reads the same fixed detector bounds for either registration.
- 26.2 has one `daylight_detector` registration, with `POWER`, `INVERTED`, default `power=0,inverted=false`, and static `Block.column(16,0,6)` shape independent of state/context. The base collision provider gates the shape with `hasCollision`, which defaults true; the registration does not call `noCollision`.
- `BlockStateData` explicitly maps old ordinary `daylight_detector` tags and inverted `daylight_detector_inverted` tags to the common current key, retaining each power value and setting `inverted=false` or `true`. `V704` also flattens the old inverted name to the common key. The current shape is the same `[0,0,0]–[1,0.375,1]` box as the legacy collision bounds.

## Scope boundary

The provider and alias claims are supported for the two IDs and their 16 metadata values. The old ray-trace refresh is fixed geometry and is not evidence for general DDA visitation. Signal strength, inverted detector behavior beyond its stored state, block-entity ticking, ray traversal order, and runtime reachability remain outside this review.

No product files were changed. No builds, tests, or runtime were run.
