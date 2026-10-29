# Independent review: legacy structural materials 45, 47, 48, 49

Verdict: **ACCEPT** for the finding's bounded identity/state projection and local collision/ray-bound claims.

Reviewed inputs (immutable):

- `dda-legacy-material-family-45-47-48-49/FINDING.md` SHA-256 `4750829A150B0648B1E48364B79B71B608130DFE6DDF1EF4614A2AF5E808BC6A`.
- `dda-legacy-material-family-45-47-48-49/source-hashes.json` SHA-256 `5A697BD62CFA628D1A5FFCCEEE5C43C21EF31404D4DE61A535B083AC5A26356F`.

I independently recomputed all seven selected Java source hashes and matched each to both the finding's ledger and its canonical version manifest; no digest mismatches. The canonical source-manifest hashes also match the ready markers: 1.8.9 `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004`; 26.2 `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894`.

Source checks:

- 1.8.9 `Block.java:924–937` registers ID 45 as `brick_block`, ID 47 as `bookshelf`, ID 48 as `mossy_cobblestone`, and ID 49 as `obsidian`. The two named subclasses are `BookshelfBlock` and `ObsidianBlock`; their bodies only override drop/map-color behavior, not state, movement collision, or shape updates.
- `Block.getStateFromMetadata` returns the default state (`Block.java:182–184`); `getMetadataFromState` returns zero for propertyless state (`186–190`). The base state definition has no declared properties (`Block.java:801–803`), and neither subclass adds properties. Thus the metadata/state claim is supported.
- The base constructor initializes full-cell bounds (`Block.java:198–207`); base `getCollisionShape` returns those bounds and `addCollisions` appends on intersection (`339–350`). `rayTrace` calls `updateShape` before clipping (`456–461`); base `updateShape` is empty (`596–597`). None of the four concrete registered classes overrides those methods. The local full-cube provider and absence of per-query writes are supported.
- 26.2 `BlockStateData.java:1344,1347,1351–1352` maps legacy `brick_block` to current `bricks`, with same-name mappings for the other three IDs. The registration helper stores each legacy tag against the current numeric state ID (`686–699`), confirming mapping direction. `BlockItemIds.java:285,298,301–302` and `Blocks.java:1068–1080,1211–1222` confirm current keys and ordinary `Block` registrations. `Blocks.register(id, properties)` delegates to `Block::new` (`Blocks.java:5988–5995`); `BlockBehaviour` defaults `hasCollision` to true (`985`) and gives default cube shape/collision (`323–328`).

The evidence therefore supports the report's four-ID local-provider result, including the `brick_block` → `bricks` rename. No DDA visitation, cross-cell provider order, first-hit, complete adapter, or end-to-end ray-trace conclusion is accepted or implied.
