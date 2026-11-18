# Independent audit: early AutoJump west, north and south aliases

Status: **COMPLETED — conditional source route accepted, with a scan-row correction required.** This is a source-only review bound to the frozen candidate below. The correction changes the description of the earliest vertical scan candidate for the early two-cap releases; it does not change the three ray-hit cells or the one-tick timer outcome.

## Binding and allowed scope

Audited only the three named WEST/NORTH/SOUTH rows in `early-known-cardinal-alias-source/source-check.md`, SHA-256 `a7cce5fee3afb2cb1c73096891dc61d8a42c40434e43ca7f19b116d378909e0d`. The permitted 1.12.2 control is `development/research/source-evidence/20261117/block-b-solid-collision/autojump-border-cardinal-112/report-r3.md`, SHA-256 `e383daf63378cb6494788c37b87a0b82758440f604acb7df9ff44754cdcbd74c`, and `verdict.md`, SHA-256 `cf9dad860456cfb910449cc8657215934a6e34865d325e2eb43276c526354435`. I also read only the project instructions and discovery/orchestration procedures assigned for this audit. No other direction, corner, generic border behavior, wiki, product code/history, backlog or unrelated source lane was inspected.

## Exact source identities checked

Canonical read-only source is `decompiled_minecraft/<version>/ornithe-feather`, Feather Gen2 build.2. The three relevant source files were hashed directly in every release; source manifest/provenance identities are listed here. For 1.10 and 1.10.1 the provenance is embedded in the source tree; from 1.10.2 onward the `ornithe-feather.sources.sha256` manifest hash is listed.

| Release | Player file SHA-256 | World file SHA-256 | WorldBorder file SHA-256 | Embedded provenance / source manifest SHA-256 |
|---|---|---|---|---|
| 1.10 | `a9637065f21ad67464eb5c204c74ebf228c3bb0da8a96ddf4ae73c0490fed443` | `827fea19d95ddc0a2847859f0c288bf0b6d6dc3692feb30b52b84c60fa19d877` | `58904ff4e4905f2c3fff8b2a7f40dd0e0689110497ec49e39eb8c9b1ebdd2257` | `df266e2c3009dd1a873c1665c30fe388078ed1c6227668e10cde87409a18c6da` (embedded) |
| 1.10.1 | `a9637065f21ad67464eb5c204c74ebf228c3bb0da8a96ddf4ae73c0490fed443` | `41fd1edc24b39a5b9a979d15d54fbd3ebf9818651628109f232fcc24c7e28d0a` | `58904ff4e4905f2c3fff8b2a7f40dd0e0689110497ec49e39eb8c9b1ebdd2257` | `a9a50497930848bf0ddec732b6d424e947f643a8fa6290e01425ac2bbfce219f` (embedded) |
| 1.10.2 | `a9637065f21ad67464eb5c204c74ebf228c3bb0da8a96ddf4ae73c0490fed443` | `888ed0e9de765def87b05c4126ecdf0b10e9dd448b4543dd1cb98211e9951646` | `58904ff4e4905f2c3fff8b2a7f40dd0e0689110497ec49e39eb8c9b1ebdd2257` | `91b0f478acb7b6f13463c35b268a30d2806f583402631a56f54ce2eb70d1ec71` |
| 1.11 | `65c2747bd8c70def6be7f41f624d4c9493342b39ae7bed7967f9ff63608f59ed` | `f64905888809d5666a63c8e403f171c13907502b8d982c5b3b8fce192360ff47` | `58904ff4e4905f2c3fff8b2a7f40dd0e0689110497ec49e39eb8c9b1ebdd2257` | `8b3e5da58494f1dba77410960753b0bf646425a1ada491d8242cc71e6c7a59ca` |
| 1.11.1 | `65c2747bd8c70def6be7f41f624d4c9493342b39ae7bed7967f9ff63608f59ed` | `27cfaa5ff45c2d88c492fc5dae3ea4a2bb4536fd64bff8498a9a7d0cb82efb58` | `58904ff4e4905f2c3fff8b2a7f40dd0e0689110497ec49e39eb8c9b1ebdd2257` | `0d5f4697d4e4ce53b36d0dbfec89d44830eb76753a6643b49e42f0857753066d` |
| 1.11.2 | `65c2747bd8c70def6be7f41f624d4c9493342b39ae7bed7967f9ff63608f59ed` | `27cfaa5ff45c2d88c492fc5dae3ea4a2bb4536fd64bff8498a9a7d0cb82efb58` | `58904ff4e4905f2c3fff8b2a7f40dd0e0689110497ec49e39eb8c9b1ebdd2257` | `d908c2af76598ad26999563bfef0089f1d3c8c32483eee6b108f0defa2773df0` |

The 1.12.2 control source identity is the source manifest `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`; its `World.java` and `WorldBorder.java` hashes are `e9fa9b8d6d31ad57a5b876f5f63a5e3c554a23437decf1845a493daf48233594` and `58904ff4e4905f2c3fff8b2a7f40dd0e0689110497ec49e39eb8c9b1ebdd2257`. Its manifest-matched `LocalClientPlayerEntity.java` hash is `01a58e94d8c6ff98a8e3794227cdc76a5fcbdbad795c70c9cf28854aff9823cc`. The r3 report transcribes that player hash with `...c76a5fcbdad...` (one `d` missing); the existing verdict already identifies this typo. Canonical bytes match the manifest.

The complete `autoJump(float dx, float dz)` body was independently extracted and compared across all six early files. It is identical (101 lines; hash of UTF-8 text with LF separators `2ef3050e68d1ed7f2ce5e69d8899abb4d12a0a57d895e4e452a73c7682247184`); it begins at line 823 in 1.10–1.10.2 and line 835 in 1.11–1.11.2. The source makes the actual post-superclass horizontal displacement a float pair, then gates on enabled AutoJump, expired timer, grounded, non-sneaking/non-riding, nonzero input and look alignment. It samples two real blockstates above the player, forms and grows the query, raises the pair of lateral segments, gets world collision shapes, reads the candidate-top blockstate directly, and writes timer `1` only for an accepted rise.

## Direction and fixture result

For the supplied forward-only conditional (`vec2f.x=0`, `vec2f.y=1`), the fallback vector is `(-sin(yaw), cos(yaw))`. At yaw `+90°`, `180°`, and `0°` it points WEST, NORTH, and SOUTH respectively; float sine/cosine residues are tiny and remain within the stated tangential cells. The same horizontal look direction makes the three movement vectors pass the `m < -0.15F` rejection gate. The audit accepts the supplied movement as a conditional accepted outward displacement (about 0.10); it does not independently certify an ordinary movement producer or runtime playback.

With border center `(0,0)`, size 20, feet y=64, tangential center .5, and post-move normal center ±8.99, the exact strict eroded-center test is true for either prior outside-border flag. `WorldBorder.contains(BlockPos)` treats the listed outboard cells as outside. In 1.10 through 1.11.2 the entity collision query substitutes default Stone for those cells while the loaded physical blockstate remains AIR. The two listed y=63 support stones have tops exactly at the query minY=64, so strict intersection excludes them. Direct headroom reads at y=65 and y=66, and the candidate-top read above a y=64 synthetic wall shape, see the physical loaded AIR state.

Using the fixture's accepted displacement and Speed II, the query reaches past the border face: `p=max(f*7,1/l)` is about `.98` at `f≈.14`, and the endpoint includes both the actual `dx` and the lookahead (`≈.10 + .98 = 1.08` outward from the post-move center). That exceeds the 1.01 distance to the border face. In x/z/y ascending scan order and with the double-horizontal-edge skip, the ray-hit cells remain:

| Face | First exterior horizontal scan coordinate `(x,z)` | Earliest vertical candidate on that cell, 1.10–1.11 | Earliest vertical candidate on that cell, 1.11.1–1.11.2 | First synthetic full cube hit by either raised ray |
|---|---|---|---|---|
| West | `(-12,-1)` | `(-12,64,-1)` | `(-12,63,-1)` | `(-11,64,0)` |
| North | `(-2,-11)` | `(-2,64,-11)` | `(-2,63,-11)` | `(0,64,-11)` |
| South | `(-2,10)` | `(-2,64,10)` | `(-2,63,10)` | `(0,64,10)` |

The scan-row correction is material: for 1.10, 1.10.1, 1.10.2 and 1.11, the side-column predicate `q <= 0 || r != k && r != l - 1` prunes **both** vertical caps. Since the first listed cells are side columns, y=63 is skipped and the first provider cell processed there is y=64. In 1.11.1 and 1.11.2 the entity overload delegates to the non-enforcing path whose predicate `!bl3 && !bl4 || q != l - 1` prunes only the upper cap, so y=63 is first processed. Its full cube only touches query minY=64 and is rejected by strict collision intersection; y=64 is still the first added wall shape intersecting a raised segment. The 1.12.2 control has the same top-only pruning. Therefore the source-check report's y=63 entries are not valid as the earliest processed scan candidates for the first four releases, but its geometric first-exterior `(x,z)` cells and per-direction ray-hit cells are sound.

For the three first ray-hit cells, `maxY=65`; with feet at 64 and no Jump Boost the allowance is 1.2. The top sample loop runs only at `t=1` and reads the direct AIR block above the synthetic shape, leaving rise `u=1`; `u>0.5F && u<=1.2F` holds and AutoJump sets the one-tick timer on each named fixture. The ray-hit cells and final timer verdict are supported across all six early releases and the 1.12.2 control.

## Verdict and limits

Accepted: conditional WEST/NORTH/SOUTH source route for the named centered, loaded, stationary-border fixture with the stipulated accepted outward displacement, support, AIR, effect and AutoJump state. All three ray-hit cells, direct-AIR clearance, rise-1 acceptance and timer write are confirmed. Required correction before reusing the candidate's scan table: describe the y=63 entries as the first exterior horizontal scan positions only; y=63 is cap-pruned in the four early two-cap releases and included-but-nonintersecting in the later top-only releases. No claim is made for arbitrary positions, EAST, corners, other states, an ordinary producer, runtime parity or an implementation.

No build, test, launch, source generation, product/backlog write, Git operation or push was performed.
