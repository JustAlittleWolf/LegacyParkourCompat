# Frozen source roster — farmland historical receiver audit

Scope: exact Ornithe Feather 1.11.2, 1.12, 1.12.1, 1.12.2; trace four FarmlandBlock.setDirt closures through the server-player teleport receiver and packet contract, then compare ServerPlayNetworkHandler.handlePlayerMove input handling. Source inventory was frozen before reading author evidence. This is not a general farmland comparison.

Mapping: Ornithe Feather on all four releases.

Sources, relative to each release `decompiled_minecraft/<version>/ornithe-feather/`:

- `net/minecraft/block/FarmlandBlock.java` — all four `setDirt` callers/writer.
- `net/minecraft/entity/Entity.java` — base `teleport(double,double,double)` and entity position/shape accessors.
- `net/minecraft/server/entity/living/player/ServerPlayerEntity.java` — virtual override receiver.
- `net/minecraft/server/network/handler/ServerPlayNetworkHandler.java` — teleport packet send and `handlePlayerMove`.
- `net/minecraft/network/packet/c2s/play/PlayerMoveC2SPacket.java` — incoming movement payload accessors.
- `net/minecraft/network/packet/s2c/play/PlayerMoveS2CPacket.java` — outgoing correction payload contract.

Every listed source file's exact byte hash matched its release index. For 1.11.2 and 1.12.2 the index is `decompiled_minecraft/<version>/ornithe-feather.sources.sha256`; for 1.12 and 1.12.1 it is hidden `decompiled_minecraft/<version>/ornithe-feather/.decompile-provenance.json` (`sources` object). Index SHA-256: 1.11.2 `d908c2af76598ad26999563bfef0089f1d3c8c32483eee6b108f0defa2773df0`; 1.12 `27515e5f4c71e7db2f035a81716507128d77466c75380ad1ef7d140c06401467`; 1.12.1 `0ff5246e8d29c04554cc9ec93ae9b4abef539652325b6c0ac822851d7186a9c8`; 1.12.2 `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`.

Per-file hashes:

| file | 1.11.2 | 1.12 | 1.12.1 | 1.12.2 |
|---|---|---|---|---|
| FarmlandBlock.java | `09e260528fd78b4bbdbc302726c7ae937fbc49f17b674c4a7ad802e0f7b6f5e8` | `b5902f8fab6bb9388be5e488021a4977783fea0ca9404798460f207980374da2` | `b5902f8fab6bb9388be5e488021a4977783fea0ca9404798460f207980374da2` | `d84c75dae139181b5d58cfa34f3cb77c5b2d4bda63d6ad9c3b52a47350bd0b98` |
| Entity.java | `ce8104a17ce783df639cf9726e7b1cd0936563eaa7ba308603335bbe05d49440` | `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a` | same as 1.12 | same as 1.12 |
| ServerPlayerEntity.java | `c2187f10b589bbfb47bef2a1b573781fecad55a13fb80c4c5dc11260d9f12197` | `ad4effc65edd98098d86af8ba725f6d6c2c94068f996739cf2734d34ecf804a9` | same as 1.12 | same as 1.12 |
| ServerPlayNetworkHandler.java | `947be17cf3d7217e7c8e563d4dd312cd133de1dc06bcd82b4431c9e5e74bc0bf` | `c9c5d46564e2d9b2f66a79f712a5cabcf2976d019675d1c129d1e08bef2e9ac0` | `e240844b55df7c3174d64212cdac3f2bebcd7654adc04ed5d7e893604dab9589` | `77bf65b2c48ff952713942e183af1cd5fb243ad4f8fd2e53aa1b97272e7acd7c` |
| PlayerMoveC2SPacket.java | `acc2ec0bf5f5ef64bff47b411a065ee3c41b07053c95de9a5ed19d2eb2f941a7` | same as 1.11.2 | same as 1.11.2 | same as 1.11.2 |
| PlayerMoveS2CPacket.java | `8bad09e146ce746085103081b7e3c6d09855114868267e12be8d869f34f0e77e` | same as 1.11.2 | same as 1.11.2 | same as 1.11.2 |
