# 1.13.2 to 1.14.4 wall crosswalk r3 correction r3

This immutable narrow correction supersedes the state-domain metadata in correction r2 only. It retains all prior artifacts unchanged and does not repeat source geometry or provider comparisons.

## Bound prior inputs

- Correction-r2 coverage report SHA-256 e55899f68bdbe66155328ea97a19f20b0c0010728691bc42dc39d82dcf548246.
- Correction-r2 partial-row closure TSV SHA-256 f50513c78620d10fc5327c2481371080f46407c37e6d7f023ceeb9d47b353fee.
- Correction-r2 seven-row residual TSV SHA-256 ebcc9aa9cd3a9b256a6588d62fb18707edc8f42357747df5ce340d06ef591e35.
- Correction-r2 residual disposition TSV SHA-256 1ffa701c110aa6897a1ba65d5e59ae83f0e8ee8918cf36772538202bad162704.
- Independent correction-r2 audit verdict SHA-256 bdfb1808ed8de808edfbd64cbbf024156653d10ec37d049258004da3a838d02a (REQUEST CHANGES, limited to four slab state-domain metadata fields).

## Four corrected slab rows

Only these closure-table fields change for minecraft:brick_slab, minecraft:purpur_slab, minecraft:quartz_slab, and minecraft:stone_slab:

- Old and target cardinalities now read HALF:3,WATERLOGGED:2; product=6.
- Domain basis now states HALF={TOP,BOTTOM,DOUBLE} × WATERLOGGED={false,true}.

The existing accepted report wall_slab_face_projection/report.md SHA-256 593cb79845085d5d1d22b96ea3c3dc966aaf939e0dd45ccae4d967177a212542 and independent ACCEPT verdict wall_slab_face_projection_audit/verdict.md SHA-256 aa3dbad6b5a02b94a923d6ee7d20f6ddfbf4f2e7c85e4c8a3446938e33a13694 explicitly bind these four keys and all three HALF values with both WATERLOGGED values. This correction changes the crosswalk metadata only; accepted geometry and wall-predicate findings are unchanged.

All other closure-row associations and fields are retained from correction r2. The residual dispositions and unexamined rows are byte copies from correction r2. The seven unsupported stair keys remain open: cobblestone, dark oak, dark prismarine, nether brick, prismarine brick, red sandstone, and stone brick stairs.

## Counts and retained limits

- Accepted baseline keys: 215.
- Formerly unexamined rows accepted: 354.
- Formerly partial rows accepted: 20 (10 stairs, 4 slabs, 6 fence gates).
- Still unexamined: 7.
- Arithmetic remains 215 + 354 + 20 + 7 = 596.
- Historical r1 TSV overwrite/byte-diff limitation remains recorded.
- Overall pair remains partial. Generic non-gate wall neighbors, broader resource/state/context closure, remaining pair dependencies, and full blind pair audit remain open.

No source comparison, product/wiki/history inspection, canonical write, or pair completion claim was made.