# Auto-jump introduction: 1.9.4 → 1.10.2

## Scope and verdict

This bounded source follow-up verifies the local-player auto-jump addition and its earliest stable applicability in the inspected releases. It covers option setup and cache, input access, detector dispatch and predicates, cooldown scheduling/consumption, synthetic input, and the adjacent flight-toggle guard. It does not close collision-shape providers, world collision queries, inherited `Entity.move`, or full-pair tick coverage.

**Verdict:** exact 1.9.4 source has no auto-jump setting or player detector; the feature is present in 1.10, 1.10.1, and 1.10.2. The earliest inspected source-present release is **1.10**. The auto-jump movement code has no behavior change across those three releases. Treat `1.10` as the first confirmed applicable release; this finding does not claim a commit-level introduction date or inspect any other 1.9.x release.

The independent ordinary audit's option-cache and jump-boost corrections are incorporated below. In particular, the cached option copy is inside `isCamera()`, and jump boost raises the detector ceiling.

## Exact source identities

All trees are canonical, read-only exact decompilations under `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft`, namespace/mapping family `ornithe-feather` (Ornithe Feather Gen2 / Vineflower). The four relevant working-tree source files per release were checked against that release's pinned whole-source manifest or embedded full source-hash map.

| Release | Source identity | Mapping/client pins | Relevant source file SHA-256s |
|---|---|---|---|
| 1.9.4 | `ornithe-feather.sources.sha256` SHA-256 `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19`; source count 1,819 | Feather Gen2 `1.9.4+build.2`; client `23e90103a1ca2ac71100004c6d5846de09f85695f579843ef8da41571e60c908`; merged mapping artifact `49a38d0adfbda1749e519c29844116e9f22e895cb505633261b7f587268f4125`; tiny mapping `9e21708d4bc32a43ac404735ea3238465889797110204a0375bcb069a3798027` | `LocalClientPlayerEntity.java` `8aaf711948b7602c2e6c015a37e36ed06073d39727d999d80480b4910b704f5d`; `Input.java` `206dea2ff5977599596c907c54f36300c4e72d07d6778d82e8850f57bb0fb009`; `GameOptions.java` `1bcddb6d5c8aa3f32119a11c35a9c351b4bd6bf36aefc7aa0599c0ab329ad41e`; `ControlsOptionsScreen.java` `a3b878360e97a06e44cc352bb6a7f23afa86a3d7dc4aea594e65b4ae212fc5b2` |
| 1.10 | `ornithe-feather/.decompile-provenance.json` SHA-256 `df266e2c3009dd1a873c1665c30fe388078ed1c6227668e10cde87409a18c6da`; embedded full source map | Feather Gen2 `1.10+build.2`, merged mapping artifact `ad7cf6744d81d0260c9d97ddfe8250b2fdbd54fe332d4d00266b3545d8ddf8dd`; client `c00f8170ebf4fd834f552246743a64885a6dffb3dd1e3a3f3dba9676b7a0af35` | local player `a9637065f21ad67464eb5c204c74ebf228c3bb0da8a96ddf4ae73c0490fed443`; input `9e704cfe7fdc55c4eab78670e60cf392ba6817adbd5e7451d3a86f11da8bf50e`; options `035c70cb9479e5943e9d1d232bcdf5eb823b4f2e1f7107e6b2fad68b9a7c62c5`; controls screen `ddf26d661ea89ec9580cbd5432b18c2325e3864ae686269e3f339d83a47ffb2e` |
| 1.10.1 | `ornithe-feather/.decompile-provenance.json` SHA-256 `a9a50497930848bf0ddec732b6d424e947f643a8fa6290e01425ac2bbfce219f`; embedded full source map | Feather Gen2 `1.10.1+build.2`, merged mapping artifact `6e8ba44832b7b77d913962462bde55084005d5de6389232453afc0bc0339ea37`; client `338b205f20c22b9c676d9d3227a5e6c5e190f30ad84d7caad035150b3e05ba72` | local player `a9637065f21ad67464eb5c204c74ebf228c3bb0da8a96ddf4ae73c0490fed443`; input `9e704cfe7fdc55c4eab78670e60cf392ba6817adbd5e7451d3a86f11da8bf50e`; options `8e34d181f4b66f79fcf2ad477832edb08ca1ff6099f507fdfd97d1329da3178e`; controls screen `ddf26d661ea89ec9580cbd5432b18c2325e3864ae686269e3f339d83a47ffb2e` |
| 1.10.2 | `ornithe-feather.sources.sha256` SHA-256 `91b0f478acb7b6f13463c35b268a30d2806f583402631a56f54ce2eb70d1ec71`; source count 1,845 | Feather Gen2 `1.10.2+build.2`; client `7cdf7fcdc1c92584a233bf3c42bd7f0df1bdad3007d306831fe50410692be1e9`; merged mapping artifact `18cffc56c2d8de89b50cb0328b174566236553c4aafb62afdc58a1e0ff0cadb0`; tiny mapping `7c4055aa9becb027462fe4f4a9af8822de4df9b70ecb80a84e38fd9e81e33af1` | local player `a9637065f21ad67464eb5c204c74ebf228c3bb0da8a96ddf4ae73c0490fed443`; input `9e704cfe7fdc55c4eab78670e60cf392ba6817adbd5e7451d3a86f11da8bf50e`; options `767ed017470a0909148017461668fa95a988f2f15582b4e2c6636b03502b4d43`; controls screen `ddf26d661ea89ec9580cbd5432b18c2325e3864ae686269e3f339d83a47ffb2e` |

For 1.10 and 1.10.1, the supplied `.decompile-provenance.json` includes full per-source hashes, mapping/client artifact pins, tool identity, and decompilation settings. The local-player, `Input`, options, and controls-screen file hashes listed above match those pins. For 1.9.4 and 1.10.2, the same four actual file hashes match their whole-source manifests. The 1.9.4 and 1.10.2 ready records bind the manifest to their exact client and Feather Gen2 builds.

## Paired source evidence

### 1.9.4 absence

- `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`: `mobTick()` lines 603–771 has no countdown read or synthetic-jump injection; the class has no `move` override, `isAutoJumpEnabled()`, or detector method.
- `net/minecraft/client/entity/living/player/Input.java`: fields and empty `tick()` at lines 3–15; no movement-vector accessor.
- `net/minecraft/client/options/GameOptions.java`: no `autoJump`, `AUTO_JUMP`, persisted key, or setting logic.
- `net/minecraft/client/gui/screen/menu/options/ControlsOptionsScreen.java`: no auto-jump setting entry.
- A case-insensitive whole-tree search over the local player, input, options, and controls-screen source paths found no `autoJump`, `ticksToNextAutojump`, `isAutoJumpEnabled`, or `getMovement()` auto-jump alias in 1.9.4.

### 1.10 presence and complete player-local path

The same source behavior and line references below are exact and hash-identical in 1.10, 1.10.1, and 1.10.2 (`LocalClientPlayerEntity.java` and `Input.java` each have identical SHA-256s across all three):

- **Setting:** `GameOptions.java:108` defaults `autoJump = true`; `:396–398` toggles it, `:469–470` reads it, `:830–831` loads the saved value, and `:948` saves it. `Option.AUTO_JUMP` is registered at `:1065` in 1.10 and `:1066` in 1.10.1/.2. `ControlsOptionsScreen.java` includes `AUTO_JUMP` in its options array. No auto-jump-specific change occurs between 1.10 and 1.10.2; other lines in `GameOptions.java` shift/change for unrelated settings.
- **Cached gate:** `LocalClientPlayerEntity.java:168–229` copies `minecraft.options.autoJump` to `autoJumpEnabled` at `:228` only inside the enclosing `if (this.isCamera())` at `:191`. The getter at `:819–821` reads that cache; it does not read `GameOptions` directly.
- **Input support:** `Input.java:18–20` returns `new Vec2f(movementSideways, movementForward)`. `KeyboardInput.tick()` is unchanged; the new accessor is a detector dependency, not a keyboard-input behavior rewrite.
- **Trigger and detector:** `LocalClientPlayerEntity.move(dx,dy,dz)` records x/z, invokes `super.move`, then calls `autoJump` with the actual post-move horizontal displacement (`:812–817`). `autoJump` checks enabled, no pending timer, grounded, not sneaking, not riding, and at least one nonzero movement component (`:823–828`). It uses actual displacement unless its float squared length is at most `0.001F`; then it derives a direction from speed and input rotated by yaw, and returns if that second squared length is also at most `0.001F` (`:830–844`). It normalizes with `fastInvSqrt`, compares the horizontal facing dot product, and rejects only values below `-0.15F` (`:845–849`). It requires both initial block collision shapes above the player to be null (`:850–855`).
- **Obstacle scan and schedule:** Jump ceiling `o` starts at `1.2F`, increased by `(jump-boost amplifier + 1) * 0.75F` while Jump Boost is active (`:856–860`). The swept query distance is `max(speed * 7.0F, 1.0F / l)` (`:862–867`); endpoints are raised by `0.51F`, a width-based lateral cross-product builds two test segments, and the world collision list is queried (`:868–883`). For an intersecting box, candidate collision shapes are checked upward; paths exceeding `o` or blocked continuation return (`:884–909`). A detected rise must be strictly greater than `0.5F` and no greater than `o`, after which the code writes `ticksToNextAutojump = 1` (`:911–915`).
- **Next local tick:** `mobTick()` snapshots the physical `input.jumping` state at `:661`, then, if the countdown is positive, decrements it, marks `bl4`, and sets `input.jumping = true` (`:672–677`). The later flight double-tap branch requires `!bl4` (`:713–727`), so the synthetic input cannot toggle flight. In this method's order, the detector is reached later through the `super.mobTick()` call at `:778`; it cannot be consumed before the countdown check just passed. The queued press is therefore applied on the next local tick.

## 1.10 group comparison

`LocalClientPlayerEntity.java`, `Input.java`, and `ControlsOptionsScreen.java` are byte-identical across 1.10, 1.10.1, and 1.10.2 by pinned source hash. The auto-jump-specific `GameOptions` segments retain the same `true` default, option toggle/read/load/save, and enum registration; source-wide hashes differ because surrounding unrelated option content changes. Whole-tree occurrence inventories show the same four feature-bearing source classes/files (local player, `Input`, `GameOptions`, controls screen), with no additional player-facing alias in 1.10.1 or 1.10.2. Thus no algorithm, gate, timer, default, synthetic-input, or flight-guard change is established within the inspected 1.10 group.

## Minimum independently resolved operations

For a source-level decomposition of the accepted behavior, the minimum is three independently addressable operations:

1. **Option state/cache gate:** setting default and persisted setting feed the local player's cached enabled state, copied only under `isCamera()`, then exposed through `isAutoJumpEnabled()`.
2. **Post-move detection and enqueue:** local movement dispatch after `super.move`, input-vector fallback, orientation/obstacle checks, jump-boost-adjusted height threshold, and the one-tick queue write. `Input.getMovement()` is a direct data accessor within this operation, not a separate movement operation.
3. **Queued jump consumption:** countdown decrement/injected `input.jumping` plus `bl4` protection against the flight double-tap branch.

The collision scan inside operation 2 still depends on exact collision shapes and world collision query behavior assigned to the collision lane. This follow-up establishes the auto-jump source identities and applicability boundary; it does not independently close those dependencies or claim full movement-pair acceptance.

## Existing finding and correction binding

The source input draft is `.native-source/20261030/194-1102-ordinary31/local-auto-jump.md` (SHA-256 `65ca1b9a69e8823f870ef6523e9f3d8f3df12ecf76406ca98d80db8f2c573f88`), reviewed in `.native-source/20261030/194-1102-ordinary-audit31/audit.md`. The independent correction supplement is `.native-source/20261030/194-1102-ordinary-audit31/local-auto-jump-correction.md`. This report preserves their distinct revisions and records a corrected replacement finding at `local-auto-jump.md` in this lane; the correction does not retroactively edit either frozen input.
