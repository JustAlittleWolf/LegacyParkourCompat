# Independent source verdict: body-fluid contextual height binding, 1.14–1.15.2

Verdict: **ACCEPT** — the bounded height dependency is confirmed in each exact release listed below. This verdict supplements, and does not change, the accepted body-admission finding.

## Binding and scope

The reviewed immutable prerequisite is the accepted body-admission source `legacy-fluid/body-admission-114-115-cutover`, commit `e3f6c0f7432964116917edb8dffc69617963ea20`, with independent admission audit commit `f9f47293e1c52e68c0af0c44afdf45edb392e92a`. That evidence explicitly left contextual-height behavior unaudited. This review closes only the linked height dependency for 1.14, 1.14.1, 1.14.2, 1.14.3, 1.14.4, 1.15, 1.15.1 and 1.15.2. It does not reopen chunk admission, client-world dispatch, eye-fluid predicates, current/flow response, or other body scans.

Canonical sources were read-only under `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/`. Exact mapping/source identities were Feather Gen2 `1.14+build.2` for 1.14; Yarn `1.14.1+build.10`, `1.14.2+build.7`, and `1.14.3+build.13`; Mojmap for exact 1.14.4; and Mojmap for exact 1.15, 1.15.1, and 1.15.2. The 1.14.1–1.14.3 nested `.decompile-provenance.json` files identify those exact client and Yarn inputs; the other releases have their versioned mapping provenance and source hash sidecars. No sources were generated or modified.

## Exact source trace

Each body-water refresh calls the body helper with WATER (`Entity.checkWaterState` / `m_69693160` in Feather 1.14; `Entity.updateInWaterState` / `checkAndHandleWater` in Yarn 1.14.1–1.14.3, Mojmap 1.14.4, and Mojmap 1.15.x). In the body-cell loop, every version first requires its sampled `FluidState` to match/is WATER. It then computes the sampled surface as `double e = q + fluidState.getHeight(worldOrLevel, pos)` (the local names vary), and admits body contact when `e >= deflatedBox.minY`.

The arithmetic is exact: `q` is `int` and `FluidState#getHeight` returns `float`; Java promotes the addition to `float`, then widens that rounded result to `double` for `e`. The body-surface comparison is inclusive (`>=`). The helper retains the same deflated box and strict cell-loop bounds already established by the accepted admission evidence.

`FluidState#getHeight(BlockView/BlockGetter, BlockPos)` is a callable public default method on the interface in every listed release. It delegates to the associated fluid's world-aware `getHeight(state, view, pos)`. The flowing-fluid implementation checks whether the sampled fluid type is the same as the fluid in the block above; if so it returns `1.0F`, otherwise it returns the state height (`level/9.0F` in 1.14; `amount/9.0F` in Mojmap 1.14.4/1.15). Since the body helper already gated the sampled state as WATER, the contextual branch is specifically same WATER above and gives a full-block height of `1.0F`.

## Per-release source references and checked source hashes

| Release / mapping | Entity body scan | Interface delegation | Contextual fluid height | SHA-256: Entity / fluid implementation / FluidState |
|---|---|---|---|---|
| 1.14 Feather | `Entity.java:2502–2528` | `fluid/state/FluidState.java:40–41` | `FlowingFluid.java:442–449` | `15e9d4cde45d857ca3117ccfe2d5d9071c08e7ee4a67b4053f7b006fe7a4bfa9` / `a92395f2e8219aaf7b2c80444dd14ba5427577a7f1d2027c780e822eb8793a15` / `24e19cc8d205245037af10360be21f74614fffd0afdce7294cbb8bbfa026aef7` |
| 1.14.1 Yarn | `Entity.java:2502–2528` | `FluidState.java:39–40` | `BaseFluid.java:430–436` | `ce429699f2d8f0a54f4be516783a474c6c7808572950164508ceed0baf68ed5b` / `c596bd4fa6aaf4f176c4be18d9e3125ff8b3e47846cf5a99253cccc9ba7bf5b5` / `a51dd636ca8b133aecf0193e2714da73cff08c560cd897a01a84dc3c9ad865e6` |
| 1.14.2 Yarn | `Entity.java:2513–2539` | `FluidState.java:39–40` | `BaseFluid.java:430–436` | `7c0e9cab0842a45e42fa9f27c9661de3fd0fd076c35ff989aa0477198eb38edc` / `a2509164dc7d354fdef23957f96d2d05c0f6bff73446f8362c6acff8f5a81e1d` / `a51dd636ca8b133aecf0193e2714da73cff08c560cd897a01a84dc3c9ad865e6` |
| 1.14.3 Yarn | `Entity.java:2554–2580` | `FluidState.java:39–40` | `BaseFluid.java:426–432` | `9b204a5f9af6ca50c09db4b6185ff37c39980c1a8cd1b5fd004713ecffae5c14` / `7a178ec425b1103d1231387e4bb1c88ee67babeb288822fb0e9f7a2aa854776a` / `597487f58eab5f0ce803e0a84e31cde9e3e69fafc6923123fb9e65c55bfeb237` |
| 1.14.4 Mojmap | `Entity.java:2588–2614` | `FluidState.java:39–40` | `FlowingFluid.java:465–471` | `31c6be42d165102d3e6dc4295dfef6e8971fc3aea13f938a5b3a33b91d524a7` / `e8a395552e53f33bcb5648325615d31bc1796ce2a8aeb501bdda3127622dccdf` / `26070b6d83813243a7a7c34cf9af9e3e4217681a8e7a97285c6dbb7d473f480d` |
| 1.15 Mojmap | `Entity.java:2593–2619` | `FluidState.java:38–39` | `FlowingFluid.java:465–471` | `191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e` / `e8a395552e53f33bcb5648325615d31bc1796ce2a8aeb501bdda3127622dccdf` / `f88bff7d54ea0220ee67e0fe36a78df67871e52ae636e584ea26dd107f652c15` |
| 1.15.1 Mojmap | `Entity.java:2593–2619` | `FluidState.java:38–39` | `FlowingFluid.java:465–471` | `191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e` / `e8a395552e53f33bcb5648325615d31bc1796ce2a8aeb501bdda3127622dccdf` / `f88bff7d54ea0220ee67e0fe36a78df67871e52ae636e584ea26dd107f652c15` |
| 1.15.2 Mojmap | `Entity.java:2593–2619` | `FluidState.java:38–39` | `FlowingFluid.java:465–471` | `191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e` / `e8a395552e53f33bcb5648325615d31bc1796ce2a8aeb501bdda3127622dccdf` / `f88bff7d54ea0220ee67e0fe36a78df67871e52ae636e584ea26dd107f652c15` |

The 1.14.1–1.14.3 entity, implementation and interface hashes match their nested provenance `sources` entries. The 1.14, 1.14.4 and 1.15.x trees carry the expected versioned provenance/readiness records and source hash sidecars; the checked source bytes match the pins recorded in the previously accepted 1.14–1.15 admission evidence where those files overlap.

## Independent-review status

This commit is the independent source audit for this bounded contextual-height dependency. No further independent review is needed to reuse it as the dependency for the already accepted admission witness. Reopen only if this source claim, the exact source evidence, or the applicability boundary changes.
