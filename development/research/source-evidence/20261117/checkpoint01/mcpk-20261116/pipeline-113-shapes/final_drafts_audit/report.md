# Final revised-draft source audit

Audited against the exact 1.12.2, 1.13.0 (`versionId: 1.13`), and 1.13.2 Ornithe Feather Gen2 build 2 sources. Ready/provenance markers identify the requested releases and mapping family. For every cited source file, the current source SHA-256 matches its release's `ornithe-feather.sources.sha256` entry. The relevant 1.13.0 and 1.13.2 files have matching source hashes, including Hopper's `inventory/Hopper.java`.

## Verdicts

### `anvil-shape-profile.md` — ACCEPT

Immutable draft SHA-256: `E4709C7B8AA5C8209D9B60E7569B29A5316A4324F134BD8CC48676ED92284E1A`.

The 1.12.2 `AnvilBlock` defines two orientation-selected, full-height boxes: `SHAPE_X` spans all X and 1/8..7/8 Z; `SHAPE_Z` spans 1/8..7/8 X and all Z, selected by `FACING` axis in `getShape` (lines 31–32, 92–95). The 1.13.0 source unions a 12/16-square base, two stepped leg shapes, and the corresponding top; selection uses the facing axis, with a 10/16 cross-axis top (lines 29–37, 71–74). This is a concrete intermediate-height side-space change for player collision. 1.13.0 and 1.13.2 `AnvilBlock.java` hashes match. Draft references and the bounded unknown patch-introduction statement are accurate.

### `wall-shape-state-gate.md` — ACCEPT

Immutable draft SHA-256: `DD252AC1FF7C0BBFDB7FBB9667E073E9608740952DB3FE5CC5C1614D1EFA92F9`.

The 1.12.2 wall indexes its 16 connection states and derives collision boxes by extending each selected table box to Y=1.5 (tables at lines 28–63; provider/index at lines 84–123). In 1.13.0 the `UP` gate selects the wall's connection-indexed shape/collision tables when true; when false, both methods delegate to `PaneBlock` (WallBlock lines 17–39). The inherited pane tables use their own connection index and geometry, establishing the asserted state-dependent collision provider and player-shape change (PaneBlock lines 22–73). `UP` and directional connection state are genuine selection gates. Wall and Pane source hashes match between 1.13.0 and 1.13.2; the references are accurate.

### `hopper-collision-shape.md` — ACCEPT

Immutable draft SHA-256: `5C5CCD1DE0E92F57EB52E8E8BC9CEA857A4EEBEF3C800BAC0D0E8A930761884C`.

The 1.12.2 `addCollisions` always emits the 10/16-high full-footprint bottom box plus four 2/16-thick full-height side boxes, without reading `FACING` (box constants lines 38–42; provider lines 56–62). In 1.13.0, the `FACING` switch selects the direction-specific shape; the base subtracts `Hopper.INSIDE`, whose bounds start at Y=11/16, and adds the central down outlet or a horizontal outlet (HopperBlock lines 33–41, 54–69; `Hopper.java` line 10). This changes reachable player collision regions and yields direction-specific openings/outlets. All cited 1.13.0 shape members and `Hopper.INSIDE` have matching 1.13.2 source hashes. Draft references and bounded unknown patch-introduction statement are accurate.

## Draft and source hash verification

Draft SHA-256 values above were recomputed from the current candidate files. Current cited Java source hashes matched their release source-manifest entries. Compared 1.13.0 → 1.13.2 member-source hashes:

- AnvilBlock.java: `156505373606a0cf2c0df329e170465c207b72b2ab59798272cbec389373092e`
- WallBlock.java: `aeb67adf292a3b750e72a7d2fdc13f7b42f8633c6183964d00dfe8d6960e07bf`
- PaneBlock.java: `9422e3f2aa9843ecd041f5ced13d05a4c7472d697f7dfd838450e49969e5326c`
- HopperBlock.java: `c43588fd0bdf0c17ec7356090e67af8101335b5ee2a565088837d6d75a3a73ae`
- inventory/Hopper.java: `875ea61c315306699ac6d0e9d4551037515d4f9408521a391f6140044218b820`

Older source manifest matches were also verified: 1.12.2 AnvilBlock `80729f34a74ff8b1b4b2d59e55c5d8dd9d61fff94c47f862c61c12d9947eb8bb`, WallBlock `7e4378c46e367c32deca14475e5161b6e4223947d83153ce3e7ba5c8a0d18e50`, HopperBlock `3510f351f1a6b913dd834d1c8696d49e888d3a7cd9dd307388d3551c9cabfb6c`.
