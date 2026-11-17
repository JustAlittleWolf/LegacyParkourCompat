# Independent audit verdict: caller-reachability addendum

**ACCEPT — evidence binding only.**

- Addendum: `build/source-residual-20261116/1.12.2--1.13.2/structure_piece_raw_state_route/caller_reachability_addendum.md`
- Addendum SHA-256: `629383f4a4fac0e1cc46cac816833a2414135aafe9f165165eb27f66b765ecde`
- Bound author report SHA-256: `577bae5780ba1e637739219319f1a11a53ea2916c62bcbe05677c737705b0e1c`
- Bound prior REQUEST CHANGES verdict SHA-256: `8578d88544b7561952a136d0c9f6338ebf2c1f0136865e8ff0ada9351b36c7cf`

Verified the four newly bound 1.13.2 files against canonical bytes and `ornithe-feather.sources.sha256`; the manifest hash matches the ready marker:

| File | SHA-256 |
|---|---|
| `net/minecraft/world/biome/Biome.java` | `4126ed24002f6797f11b307f8787b716dced2ae98c17797425d1ce483f26c996` |
| `net/minecraft/world/gen/feature/ConfiguredFeature.java` | `5aca1702cda51aaa00a14b511ec453e2c7edd59d41772285c0d77155a69837a5` |
| `net/minecraft/world/gen/feature/decorator/NopeDecorator.java` | `a0f8b36d55a94b7742df87be7ec6bb689f0c584a9a1c2d88ced1b71cac05c6d0` |
| `net/minecraft/world/gen/feature/Feature.java` | `61bc91bbb0a859eae11edfa7f7ba7631146d2c16a188a340bb424e6ef37cd589` |

The source anchors support the stated forwarding: biome structure entries use `NOPE_DECORATOR`; `Biome.placeFeatures` forwards its `world` argument into `ConfiguredFeature.place`; that method forwards it to the decorator; `NopeDecorator.place` forwards the same argument to `Feature.place`; and `StructureFeature.place(WorldAccess world, ...)` uses that argument in the `StructureStart.postProcess` call. The existing report’s pinned caller chain supplies the `WorldGenRegion` passed into biome feature placement.

The addendum resolves only the missing caller-reachability source binding. The raw-write conclusion, lack of a concrete movement-relevant stale-state witness, and partial pair status remain unchanged.
