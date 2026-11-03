# Anvil mutable collision bounds

## Accepted source difference

In 1.8.9, the registered Anvil singleton (`Block.java:1141`) owns mutable `min`/`max` bounds. `AnvilBlock.updateShape(WorldView, BlockPos)` reads the queried state's `FACING` and writes the X- or Z-axis box to those shared fields (`AnvilBlock.java:67-74`). The ordinary `Block.addCollisions` / `getCollisionShape` path consumes those fields without calling `updateShape` (`Block.java:339-350`). A ray or render provider visit can therefore leave bounds that a later movement collision query reads at another Anvil position.

Starting in 1.9, `AnvilBlock.getShape(state, world, pos)` returns one of two static boxes from the queried state's `FACING` axis. The provider is state-resolved per query; it does not write or consume the old shared bounds. The paired local 1.9.1–1.9.4 sources keep that same provider method and formulas, so the endpoint distinction persists across the checked 1.9 patch snapshots. The transition described here is specifically the old cached bounds provider versus the new queried-state provider; damage tier does not affect either box.

For an old X-axis writer followed by a Z-facing Anvil movement query, with no intervening write to the singleton, the stale X box differs from the queried Z box. The preserved isolated airborne witness clips `dx=0.4` to `0.1` with the stale X bounds versus `0.225` with the Z bounds. This is a conditional movement consequence, not a claim that every ray, render frame, or player trajectory creates that ordering.

## Conditional ray-writer sequence

The accepted camera/DDA source evidence establishes that the 1.8.9 camera pick calls `World.rayTrace` before entity movement, that each visited eligible provider can write bounds before clipping, and that first non-null hit stops traversal. Thus one possible sequence is: the first non-null hit is an X-facing Anvil, which writes X bounds before clipping; later movement queries a Z-facing Anvil through the inherited collision reader; no Anvil writer runs between the two. Under those premises, movement reads the stale X bounds at the Z Anvil's position. The actual ray visitation, hit, later query location, and trajectory response remain conditional.

The accepted integer-origin DDA follow-up separately shows that the 26.2 traversal can visit one extra cell behind an integer ray origin. Its specific Anvil example is conditional on an adapter replaying old `updateShape` writes on modern provider visits and on no later Anvil write before movement. It does not establish that product code has such an observer or that the extra shape query hits. This evidence is about traversal order and a possible last-writer change; it does not extend the 1.9 provider finding or establish runtime parity.

## Exact local source identities

Read-only sources are under `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/<version>/ornithe-feather/net/minecraft/block/AnvilBlock.java`. The 1.8.9 source is the Feather Gen2 build 2 export (`feather-gen2-1.8.9+build.2-mergedv2.jar`, `feather-gen2-1.8.9+build.2.tiny`). The 1.9.4 source provenance records the Feather Gen2 build 2 mapping artifacts (`feather-gen2-1.9.4+build.2-mergedv2.jar`, `feather-gen2-1.9.4+build.2.tiny`). The local 1.9.1–1.9.3 decompile provenance files identify the Feather namespace and exact source/tool hashes. Direct SHA-256 checks of the relevant source files:

| Version | SHA-256 of `AnvilBlock.java` | Provider result |
|---|---|---|
| 1.8.9 | `7e52c4a86c29548d8dc491fe7d4ff17a79c1dc9f426f538a6c19e161f99f013e` | Mutable `updateShape` writer |
| 1.9 | `8e513b30fd701c150fcb1696987094da05954fc3eed88272543730ddf42c098e` | State-resolved `getShape` |
| 1.9.1 | `8e513b30fd701c150fcb1696987094da05954fc3eed88272543730ddf42c098e` | Same provider source as 1.9.2 |
| 1.9.2 | `8e513b30fd701c150fcb1696987094da05954fc3eed88272543730ddf42c098e` | State-resolved `getShape` |
| 1.9.3 | `ec6435b45c9a091b969ae2870839f2f7b1f3924658989226f8fdd23fe4320e0a` | State-resolved `getShape` |
| 1.9.4 | `ec6435b45c9a091b969ae2870839f2f7b1f3924658989226f8fdd23fe4320e0a` | Same provider source as 1.9.3 |

The accepted DDA cache-consumer closure is `development/research/source-evidence/20261029/spindown/dda/20261028/collision-next/dda-provider-closure/ray-cache-correction.md`; its integer-origin traversal follow-up is `.../dda-provider-closure/traversal-order-followup.md`. The accepted lifecycle report is `development/research/source-evidence/20261030/dda-bounds-order/report.md`. These source reports establish the bounded old writer-to-movement-reader dependency and the conditional traversal example; they do not establish an implementation.

## Status and limits

This finding records the source difference and conditional stale-cache consequence. Product integration remains on **HOLD** at candidate tip `993707517116faa21584be5bd101a53ebcf5cc66`: observing modern pick shape queries does not preserve historical provider visitation. Runtime parity is **unverified**. Exact intermediate patch coverage beyond the checked 1.9 snapshots, general cache lifecycle, and a product implementation of the conditional ray-writer sequence are separate gates.
