# Independent review: 1.8.9 door provider and 26.2 state alias closure

Verdict: ACCEPT for the stated source-closed door-family facts and scope limits.

## Inputs and source readiness

Reviewed `FINDING.md`, `source-hashes.json`, and `row-coverage.json` in `../dda-door-family-64-71-193-197/`. Exact source hashes in the bundle match all 11 listed files (the 1.8.9 and 26.2 source trees); both canonical ready markers say `ready`, and recomputed source-manifest SHA-256 values match their ready markers and the bundle entries. The source roots were `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/1.8.9/ornithe-feather` and `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/26.2/unobfuscated`.

## Findings

- `Block.java` confirms old numeric registrations 64=`wooden_door`, 71=`iron_door`, and 193–197=`spruce_door`, `birch_door`, `jungle_door`, `acacia_door`, `dark_oak_door`; each is constructed with the same 1.8.9 `DoorBlock` implementation (wood or iron material as reported). The corresponding `Blocks.java` constants corroborate the identities.
- `DoorBlock.getStateFromMetadata` and `getMetadataFromState` confirm the report's split: lower metadata 0–7 stores facing/open, upper 8–11 stores half/hinge/powered. Metadata bit 2 is ignored on the upper branch and is not emitted by the encoder; therefore upper raw 12–15 normalize to 8–11 on decode/encode. The report correctly limits flattening claims for those noncanonical rows.
- `DoorBlock.getCombinedMetadata` reads queried, below, and above states in that order, gets each block's metadata, chooses the lower bits by queried-half bit, then selects hinge/powered from upper bits. It does not validate neighbor block identity before composing. The report correctly distinguishes this historical pair/world dependency from current per-state shape evaluation and limits the adapter domain accordingly.
- The old `updateShape(int)` first resets bounds to `(0,0,0)-(1,2,1)`, then writes the stated one-block-high 3/16-thick leaf for every direction/open/hinge branch. Collision shape, outline shape, and rayTrace each invoke the world-based shape update before delegating, so the stated write/reset ordering and formula table are source-supported.
- `BlockStateData` registers all 16 flattened numeric rows for oak and iron, and rows 0–11 for each of the five species doors. A static source scan found exact explicit ID counts of 16, 16, then 12 for each species range. `register` sets the first destination tag as `MAP[id]`, initializes per-block defaults from the first tag, and `finalizeMaps` fills null rows with that default. This supports the report's 12-explicit/4-default-filled distinction; `row-coverage.json` agrees with the source counts and destination keys.
- Current `DoorBlock` declares `FACING`, `OPEN`, `HINGE`, `POWERED`, and `HALF`. `getShape` derives its horizontal shape from facing/open/hinge; `POWERED` does not enter the formula. Its neighbor update only handles half maintenance/removal, which supports the report's caveat that current `getShape` is not the old pair recombination. The report does not overclaim malformed-pair parity.

No correction is required for the stated closure. This acceptance does not establish DDA traversal/first-hit equivalence, complete provider-registry coverage, runtime reachability, or arbitrary malformed-door parity; those exclusions in the finding are appropriate.

## Verification record

- 1.8.9 ready status: `ready`; source-manifest SHA-256: `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004` (matches ready marker).
- 26.2 ready status: `ready`; source-manifest SHA-256: `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894` (matches ready marker).
- All source-file hashes in `source-hashes.json`: matched canonical source bytes.
- Explicit `BlockStateData` rows: 1024–1039 = 16; 1136–1151 = 16; 3088–3099, 3104–3115, 3120–3131, 3136–3147, 3152–3163 = 12 each. Default-fill behavior is independently established by `finalizeMaps()`.
- No product files, tests, or builds were used.
