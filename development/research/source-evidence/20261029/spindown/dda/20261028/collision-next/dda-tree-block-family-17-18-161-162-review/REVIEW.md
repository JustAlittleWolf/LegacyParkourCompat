# Independent review: 1.8.9 tree-block providers

Verdict: ACCEPT for the bounded source claims below.

## Reviewed artifacts and identity

Reviewed `dda-tree-block-family-17-18-161-162/FINDING.md` (SHA-256 `637CB63C9C4572D4A66E85EFDB3252372C029C8BDFE8951D0D25683180F6B88E`) and `SOURCE-HASHES.txt` (SHA-256 `7575F0DECD22BCF96774326606CCD9B97B664071E386502E30729340126E7046`). All 16 listed source files matched both their recorded SHA-256 and the corresponding entry in the exact-version source manifest. The adjacent readiness markers identify prepared 1.8.9 Ornithe/Feather and 26.2 unobfuscated sources; each marker's expected source-manifest digest matches the actual manifest.

## Independent source findings

- 1.8.9 `Block.java` registers 17 as `log`, 18 as `leaves`, 161 as `leaves2` with internal key `leaves`, and 162 as `log2` with internal key `log`. The class decoder therefore matters; numeric IDs alone do not identify state families.
- `LogBlock` decodes species from `metadata & 3`; `Log2Block` uses `(metadata & 3) + 4`. Both decode axis from `metadata & 12`: 0=Y, 4=X, 8=Z, 12=NONE. The leaf classes decode species with their corresponding low-bit masks and use bits 2 and 3 for `DECAYABLE` and `CHECK_DECAY`. The reviewed decoder code has no invalid metadata branch for values 0–15.
- Base `Block` initializes its mutable bounds to `[0,1]^3`; its default `getCollisionShape` offsets those bounds by the block position. `rayTrace` calls `updateShape` and intersects the same six bounds planes; base `updateShape` is empty. The reviewed log/leaf inheritance chain contains no override of `getCollisionShape`, `updateShape`, or `rayTrace`, and no subclass `setShape` call. Thus these six species families use the unit box for the default collision and block-ray shape, including axis-NONE logs. Leaf decay bits do not gate those providers.
- In 26.2, the listed log and wood registry entries are `RotatedPillarBlock` instances; `RotatedPillarBlock` does not override the default block shape. `BlockBehaviour` defaults to `Shapes.block()`, collision enabled, and collision shape equal to state shape. `LeavesBlock`'s relevant override is empty support shape, not collision shape; leaves properties call `noOcclusion`, while `noCollision` is the separate collision opt-out. `TintedParticleLeavesBlock` is outside the collision-shape path.
- The source-backed state migration maps old log species/axis to current `{species}_log` for X/Y/Z and `{species}_wood` for axis NONE; the bark-to-wood rename is explicit in the 26.2 data-fixer source. The six historical leaf species map to their exact current leaf keys. The removed legacy decay flags do not map to modern `distance`, `persistent`, or `waterlogged` properties.

## Scope and remaining gates

Acceptance covers source identity, metadata decoding, these alias families, and their default full-cube collision/ray provider shape. It does not establish DDA cell visitation, ordered block/provider queries, mutable-bound writes by other blocks, callback or hit ordering, chunk availability, caller flags, overall modern-observer equivalence, or complete 1.8.x profile applicability. It must not be used as closure for those dependencies or as a runtime-parity claim.

No source or product files were changed. No build, tests, or runtime were run.
