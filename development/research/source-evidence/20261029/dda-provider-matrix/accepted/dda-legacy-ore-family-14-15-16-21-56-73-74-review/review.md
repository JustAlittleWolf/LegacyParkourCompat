# Independent review — legacy ore IDs 14/15/16/21/56/73/74

**Verdict: ACCEPT** for the bounded alias/state and local collision/ray-bound claims.

## Inputs reviewed

- Finding: `.native-source/20261028/collision-next/dda-legacy-ore-family-14-15-16-21-56-73-74/FINDING.md`
  - SHA-256: `170FD3B1E1A9A74001A5D3AFE487E15420A1B59EC8A4B51A745977BD6442743F`
- Source manifest: `.native-source/20261028/collision-next/dda-legacy-ore-family-14-15-16-21-56-73-74/source-hashes.json`
  - SHA-256: `0514601011A426CE4C6309C87265306A2C3A499C8A59B895663C3E0658A1111A`

All 12 listed canonical source files were independently hashed at `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft`; every digest matched the report manifest.

## Verification

The 1.8.9 registry confirms IDs 14, 15, 16, 21, and 56 as gold, iron, coal, lapis, and diamond ore. IDs 73 and 74 are separate `RedstoneOreBlock(false/true)` registrations. Their state is constructor-fixed; inherited metadata conversion returns the default state and metadata zero. The cited `EntityBlockStateFix` IDs preserve the listed numeric/name identities.

In 26.2, the ordinary old ore names remain corresponding blocks, while both redstone IDs map to `redstone_ore` with `LIT=false/true` via `BlockStateData`. The report excludes deepslate and other modern ores from the alias domain.

The old ore classes do not override collision or ray-bound updates. The base `Block` constructor initializes unit bounds, collision uses those bounds, and `rayTrace` calls the empty inherited `updateShape`. Current `DropExperienceBlock` and `RedStoneOreBlock` add no shape override; the cited base `BlockBehaviour` uses `Shapes.block()` and returns the state shape when collision is enabled by default. This supports the stated local full-cube provider equivalence for equal position and correctly projected state.

## Limits

This review accepts only the bounded ID aliases, state projection, and local shape-provider claims. It does not close DDA cell visitation, traversal ordering, candidate enumeration, first-hit behavior, or overall ray-trace parity.
