# Independent verdict: dry trapdoor variant equivalence

**Verdict: ACCEPT** for the fixed-state extension of the previously accepted oak marginal water-admission witness to spruce, birch, jungle, acacia, dark oak, and iron trapdoors in exact 1.13, 1.13.1, and 1.13.2. Every variant in every release supports the supplied dry, open, north-facing state over the matching north ladder and has the same collision and climb-predicate behavior. This establishes fixed-property equivalence only; it does not establish general activation or repeat the accepted oak water-scan/collision analysis.

## Inputs and source identity

- Prior proof: `dry-trapdoor-variant-equivalence` commit `fe3f8baedb423ad07178997837023ed05a6c612e`, `proof.md`; raw Git blob SHA-256 `75032cefcdef02fe5a89b344818930ddad28f33caac62dbac757cbc17d5c95c3` verified with binary-safe Git blob output.
- Accepted fixture: `marginal-climb-water-admission` commit `bec48f6c1d8371645699061895c2f753cc795087`, `admission.md`; raw worktree SHA-256 `3837bc51f392464b9d1824f3f23f306efc05f07535fdb3c91266db0b8479f325`.
- Prior independent fixture verdict: `marginal-climb-admission-audit` commit `539091e2c7e2ff72b12e112765995c11bdd1addf`, `verdict.md`; raw worktree SHA-256 `1293ea7e4c87662896972a7ac58dbb67d88715f43c6c511b1a1c0adb795ea66a`.
- Canonical read-only sources: `decompiled_minecraft/<release>/ornithe-feather/`. Ready markers identify 1.13, 1.13.1, and 1.13.2 and the matching Feather gen2 build.2 artifacts. Their source-manifest hashes and the provenance pins match the supplied identities. I independently matched each relevant `Block.java`, `Blocks.java`, `TrapdoorBlock.java`, `BlockState.java`, `LadderBlock.java`, and `LivingEntity.java` hash to its release manifest.

## Paired source result

Across all three releases, registration assigns each of the six IDs to the same `TrapdoorBlock` provider class. The wood variants differ in map color; iron differs in material and strength/sound properties. Those values do not enter `TrapdoorBlock.getShape`, `LivingEntity.isClimbing`, or the matching-ladder helper. The shared state definition supports `FACING`, `OPEN`, `HALF`, `POWERED`, and `WATERLOGGED`; for `OPEN=true`, shape selection uses facing and ignores half. North uses `Block.block(0,0,13,16,16,16)`, so the slab spans Z=13/16 through 1.

`BlockState.getCollisionShape` dispatches through the provider, and `Block.getCollisionShape` uses the state's shape when collision is enabled. Trapdoor properties retain the default collision flag. `TrapdoorBlock.getFluidState` returns water only if `WATERLOGGED`; otherwise inherited `Block.getFluidState` returns `Fluids.EMPTY`. These provider and dispatch bodies are identical in each examined release.

The climb check accepts a trapdoor only if `OPEN` and the ladder directly below is a `Blocks.LADDER` whose `FACING` equals the trapdoor's facing. It does not test wood species, material, half, powered, or waterlogged. `LadderBlock` and the climb predicate bodies are byte-identical across the three source trees. Thus all six listed IDs meet the same predicate for the supplied north/north fixture.

The fixed tuple is attainable for every listed variant. Placement sets `WATERLOGGED` from fluid in that trapdoor's own cell, so an unwaterlogged trapdoor can coexist with water in the fixture's adjacent scan cell. Neighbor-signal handling sets both `OPEN` and `POWERED` for either material. Iron's `use` deliberately returns false, so manual interaction does not open it; the redstone neighbor-signal route does. This closes the fixed-state applicability check without claiming equal activation mechanics or general reachability for every state arrangement.

| Release | Spruce | Birch | Jungle | Acacia | Dark oak | Iron |
|---|---|---|---|---|---|---|
| 1.13 | Equivalent | Equivalent | Equivalent | Equivalent | Equivalent | Equivalent |
| 1.13.1 | Equivalent | Equivalent | Equivalent | Equivalent | Equivalent | Equivalent |
| 1.13.2 | Equivalent | Equivalent | Equivalent | Equivalent | Equivalent | Equivalent |

No per-release or per-variant exception remains in the bounded claim. This review does not rescan the accepted oak fixture, water admission, numeric collision margins, trajectories, or general activation behavior.
