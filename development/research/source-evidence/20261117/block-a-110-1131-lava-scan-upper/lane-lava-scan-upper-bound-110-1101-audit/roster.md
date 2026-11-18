# Independent blind source roster — 1.10 → 1.10.1 lava material scan upper bound

Frozen before opening either author artifact. Scope is the local player's client tick gate, entity lava-box producer, historical material scan, and in-scope travel consumer, including bounds/casts/loops and chunk/geometry gates.

## Source identity and publication gates

Canonical releases: Minecraft 1.10 and 1.10.1, Ornithe Feather mapping family; primary checkout `D:\Javastuff\LegacyParkourCompat`; mapping roots `decompiled_minecraft/1.10/ornithe-feather/` and `decompiled_minecraft/1.10.1/ornithe-feather/`. Both roots have hidden `.decompile-provenance.json` manifests. Each records exact-version Mojang client input, exact-version Feather metadata and Feather Gen2 build.2 mapping archive, tool/runtime/pipeline, and per-source SHA-256 entries. All 1,845 listed sources in each manifest were present and matched their recorded SHA-256.

| Version | Manifest SHA-256 | Mojang client input SHA-256 | Feather metadata SHA-256 | Feather mapping archive SHA-256 |
|---|---|---|---|---|
| 1.10 | `df266e2c3009dd1a873c1665c30fe388078ed1c6227668e10cde87409a18c6da` | `c00f8170ebf4fd834f552246743a64885a6dffb3dd1e3a3f3dba9676b7a0af35` | `a1eb1ccff25e2b76fa1d08e9b515508e97ac4837fbcacbd7f77b3a01026d3950` | `ad7cf6744d81d0260c9d97ddfe8250b2fdbd54fe332d4d00266b3545d8ddf8dd` |
| 1.10.1 | `a9a50497930848bf0ddec732b6d424e947f643a8fa6290e01425ac2bbfce219f` | `338b205f20c22b9c676d9d3227a5e6c5e190f30ad84d7caad035150b3e05ba72` | `8648e97ee398b11151cb2d441161629abde561afed9c933c18fbc622e2c9904a` | `6e8ba44832b7b77d913962462bde55084005d5de6389232453afc0bc0339ea37` |

## Frozen source roster

All paths below are relative to each version's `ornithe-feather/` root. Inspect matching methods and callers on both sides, then follow inherited/gated call flow as necessary:

1. `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`: `tick()` client loaded-chunk gate and call into superclass tick; identify whether it is relevant to reaching the consumer.
2. `net/minecraft/entity/Entity.java`: `isInLava()` and any exact caller/geometry used to pass its box to material scanning.
3. `net/minecraft/world/World.java`: `containsMaterial(Box, Material)` including outer bounds, integer conversions and loop inclusivity, loaded-chunk checks, block/material predicates, and early returns.
4. `net/minecraft/entity/living/LivingEntity.java`: local-player-reachable `travel(...)`/movement branch consuming `isInLava()`, including player flying and other applicability gates; verify the local player dispatch reaches it.
5. Reachable wrappers/callers only as needed to close tick/travel and block/material query gates. Exclude entity damage side effects, server-only replay, rendering, and unrelated material scans.

The frozen claim under review is whether 1.10 → 1.10.1 changes the material-scan integer upper-Y or another scan gate in a way reachable by local player lava movement. No concrete implementation, wiki inventory, product diff or Git history was consulted.

## Expected author artifacts (not opened at roster freeze)

- `comparison.corrected-r1.md` — expected SHA-256 `ec4ac3ff5d0ab706a94b8cfcc7bfe5f40acf465f57041ddacb1e130b7866c0a7`
- `provenance.md` — expected SHA-256 `9a58bfd522aff0170cf1b02c02be53b3bb84cd31b744212797c848c45509efe3`
