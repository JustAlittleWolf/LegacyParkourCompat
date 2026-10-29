# Fence metadata serialization correction supplement

This immutable supplement corrects one claim in the original fence/gate finding and leaves every other source-supported provider, alias, geometry, lifecycle, and scope statement intact.

## Bound artifacts

- Original finding: `.native-source/20261028/collision-next/dda-fence-gate-family-legacy/FINDING.md`
  - SHA-256: `1A573FC598C9A35E4A3A89D593519272C534680301639DEA35606B651F49B3DB`
- Independent review: `.native-source/20261028/collision-next/dda-fence-gate-family-legacy-review/REVIEW.md`
  - SHA-256: `5777493F363A20F6D3720D7B57E3DE5912CCAEB8E6AC6B3D5177AD5B525E85E8`
- Canonical old source: `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/1.8.9/ornithe-feather/net/minecraft/block/FenceBlock.java`
  - SHA-256: `8D6A803988D77FC51F364520F2E0CD5567B1D5C29A4D28D01CDCF93E6D5890D9`

## Correction

The original finding incorrectly says `FenceBlock.getMetadataFromState` is inherited from `Block` and rejects a nonempty property set. `FenceBlock` explicitly overrides it and returns `0` for every state (`FenceBlock.java`, immediately after `use`, source lines 149–152). The state decoder is still inherited from `Block.getStateFromMetadata`, which returns `defaultState`; thus metadata does not encode fence connection flags. This override also means serializing a fence state returns zero rather than throwing, including when the input state's virtual directional properties are populated.

The old collision provider conclusion is unchanged: `addCollisions` recomputes its four neighbor tests from the live world, independently of the virtual-state booleans and metadata. No other claim in the bound original finding is changed by this supplement.
