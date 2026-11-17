# Stairs face-shape and connected-state matrix: 1.11.2 → 1.12

## Binding and scope

Bound to the exact source pair `decompiled_minecraft/1.11.2/ornithe-feather/` → `decompiled_minecraft/1.12/ornithe-feather/` and to pair coverage `coverage.md` SHA-256 `da8f52d9b0e6e6445612cdf1f80096712e10b824f72c3d499f829b6024313514`. This closes only the StairsBlock state/domain crosswalk as a dependency of the already bounded fence and wall neighbor-face claims. It does not re-audit their boxes, other providers, panes, or pistons, and does not freeze the pair.

Input identities: 1.11.2 `ornithe-feather.ready.json` SHA-256 `ceabfb266993567aab8c867525365e18aa0a0d7ba5a2cb7b6cfcc83d21318c40`; 1.11.2 `ornithe-feather.sources.sha256` SHA-256 `d908c2af76598ad26999563bfef0089f1d3c8c32483eee6b108f0defa2773df0`; 1.12 mapping-local `.decompile-provenance.json` SHA-256 `27515e5f4c71e7db2f035a81716507128d77466c75380ad1ef7d140c06401467`. Both sides use the `ornithe-feather` mapping family.

Pair-context identities checked: provider/callback inventory SHA-256 `39052ed2f95606f012876136a3277b2ff9fc55c075d19936e5e384afc6b193d8`; full-pair audit r2 SHA-256 `82145bec322ff77fecf967fa5cf0f440f11ecb4b07dbfd499f10b5ca227df80b`; connected-shape crosswalk correction r2 SHA-256 `eda620f3a69bf7115b45dec4328cc48e2e23a720595d66e34b0ae83eaa3694de`; its audit verdict SHA-256 `87da39170d47770ff91890486a11b8713493f9065297a2a57e691a0176c69b65`. The crosswalk's accepted fence and wall claims/verdicts are reused, not reopened: fence claim/verdict `3c5bb22d0700858739e0815ffc295c119aab0787d846705c02fd9db4835c6066` / `b2c3d12448447f22433ab648d92cb63e2bcb5e75037a38ef9bbd836268bc2075`; wall claim/verdict `d9012e7c589f6a72a0716f17e19ab43a060772c8c1e5bd93df371e5656ac1d8d` / `2fc8c608e6b5ecf75dabb530d4448efe94466c6476754850048990cd15d7129f`.

Exact source identities (SHA-256):

| Source | 1.11.2 | 1.12 |
|---|---|---|
| `net/minecraft/block/StairsBlock.java` | `5df1782da6eb7acdb9935b3910a0484d6417661bfd6230ecf81df0de6d782627` | `c52a011a759b6f3ef6d45162d3b61f464ceceee5d2d8735de6f6e25a56e55611` |
| `net/minecraft/block/FenceBlock.java` | `856cb92d4613289747a34a6bc85f306ca9d44b7057a45c79f2020d13025d1cf7` | `633ad3b20250bd010a28d0b1362b24c81a5508be8a81a9d826953a3c9c4a703e` |
| `net/minecraft/block/WallBlock.java` | `17da6d881365e4dce5134f88cb9a9e5273d1fa1734812d13410dc6924a3acbfd` | `7e4378c46e367c32deca14475e5161b6e4223947d83153ce3e7ba5c8a0d18e50` |
| `net/minecraft/block/Block.java` | `ab873436b24487ab0ee8055bf7a478349c7f7978398aea8e387796a6c820996a` | `e4a90eca411e7b0e14f5018f7385ec29891f6cdf1a1e917fa648b5712f9b33a1` |

## Registered states and legal combinations

Both `StairsBlock.createStateDefinition` bodies register exactly `FACING`, `HALF`, and `SHAPE`; no `WATERLOGGED` property exists. `FACING` is the four horizontal directions `{NORTH, EAST, SOUTH, WEST}`; `HALF` is `{TOP, BOTTOM}`; `SHAPE` is `{STRAIGHT, INNER_LEFT, INNER_RIGHT, OUTER_LEFT, OUTER_RIGHT}`. The state definition therefore contains 4 × 2 × 5 = 40 property tuples on each side. The declarations and enum values are unchanged.

Ordinary placements set facing from the placing entity, select top/bottom from placement face and hit height, and start as `STRAIGHT`. Neighbor stair arrangements of matching half can resolve that shape to an inner or outer turn: the resolver checks a stair in front of the current facing for an outer turn and behind for an inner turn, requiring a perpendicular facing and applying its same-facing side guard. A lone stair is therefore an ordinary `STRAIGHT` case; same-half perpendicular neighbors provide ordinary inner/outer cases. The SHAPE value used by 1.12 `getFaceShape` is the resolver's current virtual value, not blindly the stored tuple value.

## 1.12 StairsBlock result for all six query directions

`getFaceShape` first calls `resolveVirtualProperties(state, world, pos)`. Let `F` be the resolved stair facing, `CW(F)` its clockwise-Y horizontal direction, and `CCW(F)` its counterclockwise-Y direction. Horizontal results do not depend on `HALF`. Vertical results do not depend on `FACING` or `SHAPE`.

| Queried face | `HALF=TOP` | `HALF=BOTTOM` |
|---|---|---|
| `UP` | `SOLID` | `UNDEFINED` |
| `DOWN` | `UNDEFINED` | `SOLID` |
| `NORTH` | Apply horizontal rule below | Apply horizontal rule below |
| `EAST` | Apply horizontal rule below | Apply horizontal rule below |
| `SOUTH` | Apply horizontal rule below | Apply horizontal rule below |
| `WEST` | Apply horizontal rule below | Apply horizontal rule below |

For each of the four horizontal directions, the result is `SOLID` exactly as follows; every other horizontal direction is `UNDEFINED`:

| Resolved `SHAPE` | Horizontal faces returning `SOLID` |
|---|---|
| `STRAIGHT` | `{F}` |
| `INNER_LEFT` | `{F, CCW(F)}` |
| `INNER_RIGHT` | `{F, CW(F)}` |
| `OUTER_LEFT` | `{}` |
| `OUTER_RIGHT` | `{}` |

This compact direction-relative table enumerates each cardinal query for every declared facing: substitute `F=NORTH`, `EAST`, `SOUTH`, or `WEST` and rotate `CW`/`CCW` in the horizontal plane. Together with the vertical table it covers all six directions and both halves for all five resolved shapes. The source branches explicitly return only `SOLID` or `UNDEFINED` for stairs; they never return a pole or center shape.

## 1.11.2 behavior and connected-predicate comparison

1.11.2 has no StairsBlock `getFaceShape` override or face-shape query API. `StairsBlock.isCube(state)` returns `false` for every facing, half, and shape; `isFullBlock` returning true for top stairs does not change that cube result.

The old fence predicate connects to same-material fences, any fence gate, or a neighbor whose material is solid-blocking and whose state is a cube, except pumpkin and barrier. The old wall predicate connects to itself, any gate, or that same solid-blocking cube condition, with pumpkin and barrier excluded. Stairs are neither a fence nor gate (for fence), nor the same wall nor a gate (for wall); `isCube=false` prevents the generic branch. Thus all six neighboring stair query directions and all 40 registered stair tuples produce **no fence or wall connection in 1.11.2**.

In 1.12 both predicates query the neighbor's face toward the connector. Fence accepts a non-exception `SOLID` face, or `MIDDLE_POLE` when neighbor material matches the fence material or the neighbor is a fence gate. Wall accepts a non-exception `SOLID` face, or `MIDDLE_POLE_THICK`, or `MIDDLE_POLE` from a fence gate. Stairs are not in the attachment/connection exception set and return only `SOLID`/`UNDEFINED`, so neither pole branch applies. Therefore each queried stair face listed as `SOLID` in the matrix now sets the adjacent fence/wall connection bit; each `UNDEFINED` face leaves it unset.

The connection direction is the face on the stair, not the direction from stair to connector. For a fence/wall north of the stair, the stair is queried on `SOUTH`; east uses `WEST`, south uses `NORTH`, and west uses `EAST`. This is the opposite-neighbor mapping in both 1.12 `resolveVirtualProperties` implementations.

Concrete ordinary witness: a standalone north-facing bottom stair resolves `STRAIGHT`, has `NORTH=SOLID` and `SOUTH=UNDEFINED`. A fence or wall immediately south queries its `NORTH` face and connects in 1.12; one immediately north queries its `SOUTH` face and does not. Both positions were disconnected in 1.11.2. A top stair adds `UP=SOLID` and `DOWN=UNDEFINED`, but those vertical faces are not queried by fence/wall neighbor resolution.

## Bounded conclusion

The stair state/domain dependency is closed for this crosswalk: the 1.12 face result is deterministic from the resolved facing/half/shape and queried face, with shape derived from matching-half neighboring stairs; there is no waterlogging dimension. The changed face classification can change virtual fence and wall connection state for ordinary legal stair arrangements and therefore changes their already-established collision-provider state for an eligible local player adjacent to those blocks. No stair-specific connection delta exists for shapes/faces returning `UNDEFINED`, and top/bottom vertical classifications do not enter fence/wall adjacency. This result supplements the accepted fence/wall source claims; it does not re-audit collision boxes, establish trajectories, or close the broader pair inventory.
