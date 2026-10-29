# 1.8.9 registered opaque-neighbor roster for iron bars

## Result and scope

This roster enumerates all numeric block registrations 0–197 from the exact 1.8.9 `Block.init` table and resolves each registration to its constructed singleton class. For each singleton it records the constructor-time `opaqueCube` value used by `Block.isOpaque()`, then evaluates ID 101 `PaneBlock.shouldConnectTo` using that value and the explicit identity/class exceptions. The machine-readable 198-row table is `old-opaque-roster.csv`; exact source and accepted-report hashes are in `source-hashes.json`.

The census is complete for old numeric IDs: exactly one row exists for every integer 0 through 197. The result is 72 `opaqueCube=true`, 126 `false`, and zero unresolved values. `false` does not mean noncolliding or unsupported; it only resolves the old opaque predicate. The table preserves `PaneBlock`'s explicit positive exceptions separately.

## Source semantics

In old `Block.java`, `Block.isOpaque()` returns the `opaqueCube` field. The `Block(Material, MapColor)` constructor initializes `opaqueCube` by a virtual call to `this.isSolidRender()` before the object constructor chain reaches subclass bodies. The source roster resolves the nearest override through the registered class hierarchy, and records that basis per row. Inherited `Block.isSolidRender()` returns true; all direct boolean-return overrides in the enumerated tree return false except `AbstractLeavesBlock.isSolidRender()` and `SlabBlock.isSolidRender()`. For leaves, the virtual call occurs during `Block` construction before `TranslucentBlock` assigns `culling`, so the Java-default `false` yields `!culling == true`. For slabs, the virtual `isDouble()` call dispatches to the registered concrete double/single subclass; `SlabBlock` also explicitly sets `opaqueCube=true` for double instances. No other `opaqueCube` assignment exists in the old block package.

The accepted source rule for ID 101 is `neighbor.isOpaque() || neighbor == this || neighbor == Blocks.GLASS || neighbor == Blocks.STAINED_GLASS || neighbor == Blocks.STAINED_GLASS_PANE || neighbor instanceof PaneBlock`. In the row's `PaneBlock_shouldConnectTo_for_ID101` column, `ADMIT:opaqueCube` is evaluated first; nonopaque IDs 20, 95, and 160 are separately admitted by the named exception, while registered pane IDs 101, 102, and 160 are admitted by `instanceof PaneBlock`. All other resolved false rows have no such exception and are rejected. ID 160 satisfies both named and class exceptions.

## Current identity and state boundary

The current-state column deliberately closes only facts bound by the accepted ID 101 admission supplement: exact current identities for old glass IDs 20 and 95 and pane IDs 101, 102, and 160, plus the accepted old non-admission of fence, gate, and wall families. For panes, current class membership alone does not identify historical blocks because modern-only subclasses also extend `IronBarsBlock`. For fences, gates, and wall, old rejection is closed while the current per-state/per-face outcome remains open. Every other row remains `OPEN` for current identity/state projection unless its exact mapping is supported by a separately accepted crosswalk. The roster does not turn a name, material, cube flag, sturdy face, or tag into an alias proof.

The current ID 101 rule is source-bound in the cited accepted supplement: `(!Block.isExceptionForConnection(state) && faceSolid) || state.getBlock() instanceof IronBarsBlock || state.is(BlockTags.WALLS)`, with `faceSolid` queried from the neighbor's facing side. This is recorded as a distinct modern predicate, not claimed equivalent to old `opaqueCube`.

## Evidence limitations

This is a source-only old registration classifier and a bounded current-neighbor evidence index. It does not establish that all old opaque IDs can be projected to current states, that every neighbor provider is translatable, that runtime reachability exists, or that a full adapter is ready. The generic opaque-ID-to-current-state mapping remains an explicit gate. No product source was inspected and no runtime, build, or test was run.
