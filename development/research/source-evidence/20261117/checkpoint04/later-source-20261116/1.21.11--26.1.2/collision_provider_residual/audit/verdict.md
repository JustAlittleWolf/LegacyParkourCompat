# Independent audit — collision provider residual

Verdict: **ACCEPT — bounded NO_DIFF only** for the report SHA-256 `E313150BEE1C14E9BA91A6FB6F0C97B796F82D399DBD290792759D8292AFBA3F`. The result is limited to the listed connected/oriented fence and wall providers, state-selected stair/slab providers, and their stated dispatch/neighbor predicates. It does not imply tag membership, support-face population, complete block-provider inventory, pair closure, or runtime parity.

## Identity and immutable binding

Recomputed the report hash and verified the endpoint ready markers, source manifests, and provenance sidecars:

| Endpoint | Namespace/root | Ready marker SHA-256 | Source manifest SHA-256 | Provenance SHA-256 |
|---|---|---|---|---|
| 1.21.11 | Mojmap `decompiled_minecraft/1.21.11/mojmap/` | `0AD98D0EBD654650492C97EB58BC324D33864787F0CE10D99FAEC3AE2F5B804B` | `0C4D83FEF84C101D9DB88F331ACEC7960F3C8346228C28995E6CF57BBFCAE555` | `99C9FB741EB5E4AC8FB5780E2158DA324D7C8B6AFC179D494E5986F78FE29F72` |
| 26.1.2 | unobfuscated `decompiled_minecraft/26.1.2/unobfuscated/` | `F24AF0F4D38543C2BD9C19B50F347D4E89D6027050E4B21AE070734843946BB7` | `54AE320660CA911A6D20514C965C33EB414FBEDD1AD6727BBAA95FE906AA23C0` | `8D727F880CC91F4A297E148C5B75976303D051457334BC689268FBDF631A012A` |

The eight Java files in the report's hash table match their current source-manifest rows and on-disk SHA-256 values at both endpoints. I additionally checked the direct `FenceGateBlock.connectsToDirection` helper reached by the reported fence/wall predicates; it has the same axis comparison at both endpoints. Its exact source bindings are:

| Relative source path | 1.21.11 SHA-256 | 26.1.2 SHA-256 |
|---|---|---|
| `net/minecraft/world/level/block/FenceGateBlock.java` | `9F82118F3202E696688B6A83E0FD3CB649169EA1AA25CFE7CF86302A8E40C2CE` | `E9E51B00BF59A66951741E0F0EF28D5D92815DF9591E4F229FDB838588D105DF` |

## Source review

- `CrossCollisionBlock.makeShapes` builds the same center column and direction-indexed arm unions; `getShape` and `getCollisionShape` select the same precomputed maps. `FenceBlock` supplies the same dimensions, state properties, same-fence classification (`FENCES` plus `WOODEN_FENCES`), exception predicate, gate direction predicate, and neighbor-side sturdy checks/update.
- `WallBlock` uses matching post/arm constants, per-state NONE/LOW/TALL unions, `connectsTo` predicate order, side/top update paths, `isCovered` operand semantics, post selection, and collision-shape dispatch. The helper calls `FenceGateBlock.connectsToDirection` in both versions; the verified helper compares the same facing axis to `direction.getClockWise().getAxis()`.
- `StairBlock` uses the same HALF/SHAPE-selected base map and FACING/SHAPE orientation; the behind outer-corner check precedes the front inner-corner check, with matching HALF/axis/can-take-shape conditions and STRAIGHT fallback.
- `SlabBlock.getShape` selects the same top, bottom, and full-block shapes from TYPE; WATERLOGGED is not consulted by this selector.
- `BlockBehaviour.getCollisionShape` has the same `hasCollision ? state.getShape(level, pos) : Shapes.empty()` default. `Block.isExceptionForConnection` has the same leaves/vanilla-block/shulker-tag exclusions.
- The source constants are exactly `FENCES`, `WOODEN_FENCES`, `WALLS`, `WALL_POST_OVERRIDE`, and `SHULKER_BOXES`; both endpoint `BlockTags.create` helpers use the default namespace. The path spelling `data/minecraft/tags/block/<key>.json` is correct for these published releases.

The reviewed methods and constants are equal in behavior for this bounded slice. Parameter names and formatting differ in the unobfuscated publication; no altered collision state selection or shape math was found in the reviewed operations. This audit reuses the unchanged accepted `Entity.move` report SHA `3F44A596DD8F9D8885C565CB2269355B5B6503D0B4EB3898FACD6EBCD77F73B` / audit SHA `5F80FEB0CA4138EDB835A56A8A11CAA86FB558DE819BCE31915BDB3021FA109E`, and core-shape r2 report SHA `1DEA060FEEFE1812E68CC6D6A33D70EA32672948088B238BCDCE4B9123266393` / audit SHA `19D0E14E6F5388910EB0E31D14EBC4C3C29ADEE66E700E07C7B9CF9309B35B72`.

## Remaining gates

The report correctly leaves packaged/effective membership for `fences`, `wooden_fences`, `walls`, `wall_post_override`, and `shulker_boxes` open. It also leaves support-face state-provider coverage, neighbor-state update/population, and all other concrete/dynamic collision providers open. This acceptance does not claim those dependencies are resolved and does not freeze the pair.

No build, tests, runtime, product, backlog, wiki, or Git-history review was performed; canonical sources were read-only.
