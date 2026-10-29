# Independent neighbor-admission supplement review — ID 101 iron bars

**Verdict: ACCEPT, bounded to the supplement's stated source findings.**

## Reviewed artifact bindings

- `../dda-iron-bars-101/neighbor-admission-supplement.md` SHA-256: `a0dcc70776adf0b7e2d1e1c54bd6fcba3be9ec931851ea9f93c2b0aa982ac8e6`.
- `../dda-iron-bars-101/neighbor-source-hashes.json` SHA-256: `c4f816e9e8701f62276a1a380eed100d6c25a911d6464edb47ca0998466b0fbc`.
- Existing base review remains unchanged: `../dda-iron-bars-101-review/review.md` SHA-256 `4ed98bb7824f0eb97f7a16fd83cb42f594f7a74e3f2b28d06206f524b6bdd7e8`.

## Checks performed

- Independently recalculated the SHA-256 and byte length for all 16 Java source entries. Every value matches both the ledger and its line in the ready source tree's canonical `*.sources.sha256` manifest. The two source manifest hashes and ready-marker hashes also match the ledger.
- In 1.8.9 `PaneBlock.java`, `shouldConnectTo` is exactly: `block.isOpaque() || block == this || block == Blocks.GLASS || block == Blocks.STAINED_GLASS || block == Blocks.STAINED_GLASS_PANE || block instanceof PaneBlock`.
- In 1.8.9 `Block.java`, `isOpaque()` returns `opaqueCube`; the constructor initializes `opaqueCube` from `isSolidRender()`. This makes the generic branch an instance-construction classification rather than material, collision shape, or neighbor state test. Since ID 101 itself is a `PaneBlock`, `block == this` is subsumed for that source instance by the final `instanceof PaneBlock` branch, while remaining an explicit source clause.
- The old registrations confirm ID 101 `iron_bars` and 102 `glass_pane` are `PaneBlock`; ID 20 `glass`, ID 95 `stained_glass`, ID 160 `stained_glass_pane`; ID 85 and 113 are `FenceBlock`; ID 107 and 183–187 are `FenceGateBlock`; IDs 188–192 are `FenceBlock`; and ID 139 is `WallBlock`. The respective legacy fence, gate, and wall class overrides return `false` from `isSolidRender()`, and the pane predicate gives them no dedicated admission exception. Glass IDs 20/95 and pane ID 160 are explicit exceptions/admissions as stated.
- In 26.2 `IronBarsBlock`, placement and horizontal `updateShape` query each neighboring state and its opposite-facing `isFaceSturdy` result. `attachsTo` returns `(!Block.isExceptionForConnection(state) && faceSolid) || state.getBlock() instanceof IronBarsBlock || state.is(BlockTags.WALLS)`. `CrossCollisionBlock` stores direction connection booleans in state for its shape. The stated modern-vs-old inputs and distinctions are accurate. `StainedGlassPaneBlock` extends `IronBarsBlock`; `WeatheringCopperBarsBlock` does too, supporting the supplement's caution about modern-only subclass membership.
- Current `Block.isExceptionForConnection` includes leaves, barrier, carved pumpkin, jack o'lantern, melon, pumpkin, and shulker boxes. The legacy predicate has no corresponding blanket exclusion; its generic result depends on the old instance's `isOpaque()` value.
- Current `EntityBlockStateFix` maps `minecraft:glass` to 20 and `minecraft:stained_glass` to 95; current registrations contain those same block names. The supplement correctly says those legacy inclusions still need explicit representation in any adapter.

## Boundaries retained

The generic legacy `opaqueCube=true` registry remainder is not enumerated or mapped to current keys/states. Therefore the generic opaque-ID mapping gate remains open. The supplement does not establish broad adjacency-adapter readiness, runtime reachability, traversal behavior, or DDA parity; no such claim is accepted here. Preserve the separate base review and this immutable focused verdict as distinct evidence.
