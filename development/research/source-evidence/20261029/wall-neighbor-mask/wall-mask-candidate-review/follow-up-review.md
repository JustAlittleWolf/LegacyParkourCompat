# Wall neighbor mask candidate follow-up review

Verdict: ACCEPT candidate code, with source acceptance tracked separately.

Candidate repository: `.native-candidates/wall-neighbor-mask-20261029`
Candidate commit: `a387074da5cfe0ea40e5131c57526c1f10c97f80`
Required parent: `e7676634c33ee4825be1e400cca8e254fcfaa49e`

## Bound inputs

| Input | SHA-256 |
| --- | --- |
| `development/research/findings/1.11.2--1.12.2/wall-neighbor-connectivity.md` | `00ace08a3db2cf0c0cce2db7e5e193690817367e611781b704776e3fe91fc651` |
| `.native-source/20261029/wall-state-selector-closure/supplement-r2.md` | `3fc036aafd62b848e4f4cc3e60d85c26ad1142fff5b7296a5706eafefcdb9406` |
| `.native-source/20261029/wall-state-selector-closure/supplement-r3.md` | `7bad1170df6e31864064e7f5e3b3d60d001cee31a973b48e19feee5302dd418d` |

## Follow-up findings

The stair key corrections now match the r3 list of 14 mapped identities: `minecraft:cobblestone_stairs` replaces the unsupported `minecraft:stone_stairs`, and `minecraft:purpur_stairs` is included. This closes the admitted stair identity set without changing the `waterlogged=false` gate.

The Javadoc correction cites the 26.2 unobfuscated `BlockStateData.register` and `create` mapping members and the `Block.CODEC` / `StateHolder.codec` / `StateDefinition.createCodec` path, as well as the current wall and stair property defaults. These citations align with the supplied r2/r3 evidence and identify the source members used for the current target and neighbor gates. The comment contains no hashes.

Carried forward from the parent review: behavior remains correct for the r2/r3 admitted selector; all four cardinal inputs must be classified before a mask is returned; unsupported targets, unknown neighbors, and waterlogged stair states return `Optional.empty()` for whole-shape native fallback. `@MovementChange(emulates = V1_11_2)`, the public no-argument constructor, and single hook granularity remain correct under the candidate resolver semantics. The candidate resolver applies this delta for selections at or before 1.11.2 when it is the closest registered wall-mask change, and excludes it for 1.12 and later.

Source acceptance remains a separate status: the r3 contract is the bound review input, and its independent source audit is still in progress. This ACCEPT covers only candidate fidelity to the supplied contract; it does not convert that pending source audit into an accepted finding.

Scope: follow-up diff in the isolated candidate and the supplied accepted wall finding/r2/r3 contracts. No product implementation, product history, backlog, builds, tests, or runtime state were inspected.
