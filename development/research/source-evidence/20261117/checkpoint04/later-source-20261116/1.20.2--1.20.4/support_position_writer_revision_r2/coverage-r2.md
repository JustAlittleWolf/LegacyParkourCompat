# Support-position selector and consumers — bounded source slice

Pair: Minecraft 1.20.2 Mojmap → 1.20.4 Mojmap. This report checks the read-side support-position selector and direct movement consumers for the open S5.3 production dependency. It does not reopen the Entity.move collision kernel, establish pair coverage, or claim runtime parity.

## Exact source identity

Canonical inputs are `decompiled_minecraft/1.20.2/mojmap/` and `decompiled_minecraft/1.20.4/mojmap/`. Both are ready Mojmap publications with root-sidecar provenance:

| Version | Provenance sidecar SHA-256 | Ready sidecar SHA-256 | Source manifest SHA-256 |
|---|---|---|---|
| 1.20.2 | `1DAD9F725F6CB25B6568700FA0294F83B66EA9633B0D62ED354D7BD8B45D4F05` | `5FF3322F287C250EE914C7CA3840D053BFFC3C2C5F35E91B59314D948292483F` | `E439FF7E1D3F1D31E01BE1EDD141F94D917A9DC7FA0DE5F69904544CBBE62887` |
| 1.20.4 | `10914D9B7B3FC29DE066CA24F7D905E4D4F0A451A6C81729A2D1981ADDFC1385` | `6225B97979420CC743C8B7156A0CE512EF27D0DBF48BFFFBC40D3E1C8CB044D1` | `FD3C8668483E5FF208C847474F6CBD3952A909C4AA602ED98B24E3D11A9602A1` |

Sidecar forms present at each version root are `<version>/mojmap.provenance.json`, `<version>/mojmap.ready.json`, and `<version>/mojmap.sources.sha256`. The current Entity, LivingEntity, and Player file hashes match the respective source-manifest rows and the file bytes:

| Relative Java path | 1.20.2 SHA-256 | 1.20.4 SHA-256 |
|---|---|---|
| `net/minecraft/world/entity/Entity.java` | `d7ee49aaea5e862b92508e767562cabc8f10515605e8fb67565c8c8d5c23b01b` | `07383522bff169938136638ef8c3244ca511b56ca4266913524f99f9821331b9` |
| `net/minecraft/world/entity/LivingEntity.java` | `5c481da1ffc8684c4b30171c92e61fad751e6a3a708d5bc6486ac9f96ff69828` | `f7bc53db24c1798f19f9bd6f6356c86d5e560e9c8e8aac60decaf15cc785e07d` |
| `net/minecraft/world/entity/player/Player.java` | `25f263692fd6b2a737aa813315022a0caf4e18c3661ba3bd706c6297f909f25c` | `218da60bc4f8c9279f56eb87e2cf2c0af79562fbe3d818a408429c656efb95dd` |

## Selector behavior

`Entity.getBlockPosBelowThatAffectsMyMovement` passes `0.500001F` to `getOnPos(float)` in both endpoints (1.20.2 `Entity.java:792–793`; 1.20.4 `:793–794`). The paired selector bodies are equal:

1. If `mainSupportingBlockPos` is empty, select `floor(x)`, `floor(y - offset)`, `floor(z)`.
2. If it is present, read that position's block state. For offsets not greater than `1.0E-5F`, return the stored position unchanged.
3. For the movement selector's `0.500001F` offset, the fence exception (`offset <= 0.5`) is false. A fence therefore uses the stored X/Z and `floor(entityY - 0.500001F)` for Y.
4. `WALLS` and `FenceGateBlock` retain the stored support position for any positive offset; other stored-support blocks use the floored entity Y.

Thus the movement selector offsets and fence/wall/gate branches do not differ for the same entity position, same optional stored support position, and same queried block state. This is a conditional selector result: the writer that populates `mainSupportingBlockPos` and its movement-kernel inputs were explicitly outside this slice. No conclusion is made that both releases produce the same optional support value during a full move.

## Direct player movement consumers

The paired sources use the inherited selector in the same direct read routes:

- `Entity.getBlockJumpFactor` unconditionally reads the in-entity block's jump factor and the selected support block's jump factor, then returns the support value only when the in-entity factor equals `1.0` (1.20.2 `Entity.java:819–822`; 1.20.4 `:820–823`). `LivingEntity.getJumpPower` consumes that selected factor, and `Player.jumpFromGround` delegates to the living-entity path outside its separate flight behavior.
- `Entity.getBlockSpeedFactor` returns the in-entity speed factor for water and bubble columns. Otherwise it returns a non-`1.0` in-entity factor, or reads the selected support block's factor (1.20.2 `Entity.java:825–833`; 1.20.4 `:826–834`). Player's override returns `1.0F` while flying/fall-flying and otherwise delegates.
- `LivingEntity.onSoulSpeedBlock` tests the selected support state against `BlockTags.SOUL_SPEED_BLOCKS` (1.20.2 `LivingEntity.java:472–473`; 1.20.4 `:471–472`). Its matching player override additionally requires not flying.
- The ordinary non-fluid, non-fall-flying `LivingEntity.travel` branch reads the selected support block's friction before its grounded `friction * 0.91F` computation (1.20.2 `LivingEntity.java:2143–2146`; 1.20.4 `:2140–2143`). Player delegates to this travel route outside its flight branch.

The relevant declarations, calls, constants, and selector inputs match across these sources; no read-side support-position difference was found in this bounded route. The actual `SOUL_SPEED_BLOCKS`, `FENCES`, and `WALLS` effective tag membership remains a separate packaged-resource dependency and was not inspected here.

## Bounded disposition and remaining gate

For identical `position`, identical `mainSupportingBlockPos` presence/value, and identical queried state/tag answers, the block position selected by the movement-support accessor is the same at both endpoints; the direct jump-factor, speed-factor, Soul Speed predicate, and travel-friction consumers invoke that selector in the same order. This closes only the read-side selector dependency.

The population of `mainSupportingBlockPos` remains open because it belongs to the explicitly excluded Entity.move/support-production slice; other direct support writers are not inventoried here. Resource tag membership, other movement operations, pair-wide coverage audit, onset, implementation, and runtime parity remain open. No build, tests, runtime, product/wiki/backlog/history review, source generation, or canonical source/backlog writes were performed.
