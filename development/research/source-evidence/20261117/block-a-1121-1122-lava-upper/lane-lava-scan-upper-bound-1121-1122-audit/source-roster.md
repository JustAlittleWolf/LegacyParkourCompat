# Frozen source roster: lava-scan upper-bound audit, 1.12.1 → 1.12.2

This independent roster was frozen before reading any source file bodies or product implementation. It covers lava material scan, client tick/travel/input consumers, and the effective world/chunk/block/material read path. Rendering/particles, portal screen/UI, non-player movement and server simulation excluded.

## Source publication identity
- Older: Minecraft 1.12.1, Ornithe Feather, `decompiled_minecraft/1.12.1/ornithe-feather`; full `.decompile-provenance.json` SHA-256 `0FF5246E8D29C04554CC9EC93AE9B4ABEF539652325B6C0AC822851D7186A9C8`; pinned Feather-gen2 `1.12.1+build.2`, raw client and metadata inputs recorded in that manifest.
- Newer: Minecraft 1.12.2, Ornithe Feather, `decompiled_minecraft/1.12.2/ornithe-feather`; `ornithe-feather.provenance.json` SHA-256 `747425A26DA363D1ABC8A2CECD83A74504AEB1F58BE93AA53F48C690E35318CE`; source-list manifest `ornithe-feather.sources.sha256` SHA-256 `B8A37CCFCCD2AAC5F40F5E34FEC85873DBDFA93A266043C103E592E4A8C949DA`; ready marker confirms 2,050 files and this manifest digest.
- 1.12.1 publication manifest has exact input artifact hashes and per-source hashes; 1.12.2 source roster uses published per-source SHA-256 manifest plus ready marker. No online mappings or regeneration used.

## Frozen file roster and published source hashes

| Relative source path | 1.12.1 SHA-256 | 1.12.2 SHA-256 |
|---|---|---|
| `net/minecraft/block/Block.java` | `e4a90eca411e7b0e14f5018f7385ec29891f6cdf1a1e917fa648b5712f9b33a1` | `e4a90eca411e7b0e14f5018f7385ec29891f6cdf1a1e917fa648b5712f9b33a1` |
| `net/minecraft/block/LiquidBlock.java` | `00f7995dd49a48bf97316e30b3d6e41d8ee1e65dd212f3dc64891883621c4260` | `00f7995dd49a48bf97316e30b3d6e41d8ee1e65dd212f3dc64891883621c4260` |
| `net/minecraft/block/material/Material.java` | `5d682e9ea5a30540e62a68879fbdabd2dc1fb79781a8350c61370c1166654a7d` | `5d682e9ea5a30540e62a68879fbdabd2dc1fb79781a8350c61370c1166654a7d` |
| `net/minecraft/client/Minecraft.java` | `0291015504d83c68bc25f3c6d26d51f317454692fcb04dc844164467cb25e893` | `8af833a5d03a6f6516a7eff0ab463a96bfec64aac31fc80656f37d193b1c1310` |
| `net/minecraft/client/entity/living/player/ClientPlayerEntity.java` | `207a0ff4ee996916cd1fc73f47bfaae64f92719916cafed8395b1f2a8f797809` | `207a0ff4ee996916cd1fc73f47bfaae64f92719916cafed8395b1f2a8f797809` |
| `net/minecraft/client/entity/living/player/Input.java` | `9e704cfe7fdc55c4eab78670e60cf392ba6817adbd5e7451d3a86f11da8bf50e` | `9e704cfe7fdc55c4eab78670e60cf392ba6817adbd5e7451d3a86f11da8bf50e` |
| `net/minecraft/client/entity/living/player/KeyboardInput.java` | `7be11425906be051c83e275f359816546e4677b16d212156380e8d2e9258654a` | `7be11425906be051c83e275f359816546e4677b16d212156380e8d2e9258654a` |
| `net/minecraft/client/world/ClientWorld.java` | `996286a2b9a14b7396f1ff3501c0a2494af67534930ad5e9a0bbf5e88f12e01f` | `468def1eb1bd0791ce1d8b194ba22fe0fe49962bb3e696fb34b83bf4de7fb72c` |
| `net/minecraft/client/world/chunk/ClientChunkCache.java` | `235599d5eed2386ce4a5ed282ee893df15d8a79ff3c301f17ff3a6b45796e206` | `235599d5eed2386ce4a5ed282ee893df15d8a79ff3c301f17ff3a6b45796e206` |
| `net/minecraft/entity/Entity.java` | `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a` | `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a` |
| `net/minecraft/entity/living/LivingEntity.java` | `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6` | `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6` |
| `net/minecraft/entity/living/player/PlayerEntity.java` | `4dab93ea3178a28c616947004ecdc514e6542edca8c62ef83f686ccf6b2ce640` | `e4e0fdbe07a7d0a0ae4a70cbb6739a287c9d045a4a12b409895c220b5d91fe1e` |
| `net/minecraft/world/World.java` | `d182122a9b052f57803672c21b0941cb7e7d41ed756cd0a4ea8c0858684c8259` | `e9fa9b8d6d31ad57a5b876f5f63a5e3c554a23437decf1845a493daf48233594` |
| `net/minecraft/world/WorldView.java` | `628ce75d2c76ed025b62e905da8030024e1b89b145445fd6971bfef798bba7cc` | `628ce75d2c76ed025b62e905da8030024e1b89b145445fd6971bfef798bba7cc` |
| `net/minecraft/world/chunk/EmptyChunk.java` | `ffc3414f16ccb2b1523a7b9b2c9adfc2946c006bdb97b83c3c29f405ee5f8fd0` | `ffc3414f16ccb2b1523a7b9b2c9adfc2946c006bdb97b83c3c29f405ee5f8fd0` |
| `net/minecraft/world/chunk/WorldChunk.java` | `b80c4962661b31c9015faa9bb3d42e390528b59473956ba20e424c76625b844f` | `b80c4962661b31c9015faa9bb3d42e390528b59473956ba20e424c76625b844f` |

### Coverage intent
- `Entity` and `LivingEntity` / player subclasses: determine scan implementation, caller, bounding box and player-specific interactions.
- Local client player, keyboard input and Minecraft loop: follow actual locally controlled travel/input to establish reachability and invocation ordering.
- `World`, `WorldView`, client chunk cache, `EmptyChunk`, `WorldChunk`, `Block`, `LiquidBlock`, `Material`: resolve effective block/material reads, empty-chunk behavior and any AIR fallback.

Roster frozen before source body inspection; filename/path inventory only was used to select these sources.
