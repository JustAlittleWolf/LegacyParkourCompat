# Corrected rebind verdict — r1

## Binding

The revised author report `comparison.corrected-r1.md` was SHA-256 checked before reading and matches the requested hash `0e9bebe9aba84ae1b6d4fc8f9e319b064092eb727e4e808cbaddc4229df67846`.

This is a narrow rebind to the prior independent verdict at [verdict.md](verdict.md), SHA-256 `ab93268fad063c7365f86db3ead3a87fd5f0a0b1d2725a5367422787043482d8`, and its frozen source roster [source-roster.md](source-roster.md), SHA-256 `d99f773290e4b882caa2006737639c78b6494cba63117c0206493a2d1dafbaf5`. Neither original artifact was changed. The supplemental Box identities and exact contraction source anchors are in [box-evidence-r1.md](box-evidence-r1.md).

## Applicability verdict

**Accept the corrected report’s narrowed disposition.** The integer maximum witness qualifies the LocalClientPlayer outer current-cell range and corresponding `isAreaLoaded` bounds only. For contracted maximum `M=1`, 1.8.9 uses exclusive end 2 and can visit coordinate 1; 1.9.4, 1.10.2 and 1.11.2 use exclusive end 1 and do not. The input contraction does not rule out integer maxima. The corrected report now states this difference and limits per-cell equality to cases where the central fluid cell is admitted and passes its liquid-height gate.

The prior verdict’s accepted per-cell finding remains applicable: across all four named exact Feather releases, when the central falling-water cell is reached, the upper dry default oak fence produces the same material-based face result; the same-height air / one-cell-lower water probes and normalized direction/downward cue are unchanged. This per-cell finding does not establish that the older and newer outer scans admit identical cells.

## Recheck boundary and caveats

- Re-read only the corrected report’s changed applicability, exact Box contraction dependencies, and World upper-index/load-gate expressions. Per-cell source findings are rebound to the original accepted verdict and the corrected report’s narrowed statement; the frozen slices were not repeated.
- The exact Box and World files in all four releases were checked against their source-manifest entries; their content hashes are listed in the supplement.
- Only 1.8.9, 1.9.4, 1.10.2 and 1.11.2 are covered. Runtime reachability of an integer contracted maximum is not established here.
- No primary project writes, product/wiki/backlog access, test, build or runtime action occurred.
