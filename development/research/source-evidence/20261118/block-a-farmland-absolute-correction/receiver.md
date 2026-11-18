# Farmland historical receiver dependency

Terminal: **COMPLETED**. Bounded receiver comparison only; no product, wiki, or runtime source was consulted.

## Result

**New player correction-producing path in 1.12.2; no paired client receiver change.** In 1.11.2, 1.12, and 1.12.1, `FarmlandBlock.setDirt(World, BlockPos)` ends the relevant loop with `entity.setPosition(entity.x, box.maxY, entity.z)` (`net/minecraft/block/FarmlandBlock.java:71-78`). In 1.12.2, `FarmlandBlock.setDirt(World, BlockPos)` instead calls `entity.teleport(entity.x, entity.y + d + 0.001, entity.z)` (`FarmlandBlock.java:72-79`). This is a virtual call.

`Entity.teleport(double,double,double)` exists in every compared release (`net/minecraft/entity/Entity.java:2155` in 1.11.2; `:2181` in 1.12–1.12.2). More importantly for a player, `ServerPlayerEntity` already overrides that method in all four releases (`:883` in 1.11.2; `:999` in 1.12–1.12.2) and delegates to `networkHandler.teleport(x,y,z,yaw,pitch)`. The 1.12.2 farmland invocation therefore dispatches to this player override and enters the correction producer. The older farmland `setPosition` calls do not invoke the override. The player override is not a new 1.12.2 member; the new connection is the 1.12.2 farmland caller choosing virtual `teleport`.

The existing `ServerPlayNetworkHandler.teleport(double,double,double,float,float)` overload delegates with an empty relative-argument set (`:498` in 1.11.2; `:527`, `:529`, and `:534` in 1.12, 1.12.1, and 1.12.2 respectively). Its overload taking the set updates the requested teleport position and player position, advances the teleport ID, and sends a `PlayerMoveS2CPacket` (`:502`, `:531`, `:533`, `:538`). Thus the 1.12.2 call schedules a server-issued absolute position correction for a `ServerPlayerEntity`; the older farmland `setPosition` route does not enter this producer.

The paired client receiver is `ClientPlayNetworkHandler.handlePlayerMove(PlayerMoveS2CPacket)`: 1.11.2 `:565`, 1.12 `:576`, 1.12.1 `:578`, 1.12.2 `:578`. Compared bodies are identical. Each applies packet coordinates/angles (adding relative components only when flagged; otherwise zeroing the corresponding velocity), calls `playerEntity.updatePositionAndAngles`, acknowledges the teleport ID, and sends the same `PlayerMoveC2SPacket.PositionAndAngles` response using `playerEntity.getShape().minY`. No new client-side correction response or changed response to an equivalent movement-correction packet was found in 1.12.2. The newly reached player movement operation is the server-side correction producer; do not record a separate client receiver delta.

## Exact source identity and hashes

All four trees are `ornithe-feather`. Provenance identities:

- 1.11.2: `decompiled_minecraft/1.11.2/ornithe-feather.provenance.json`, exact run recorded as 1.11.2 and mapping artifacts `feather-gen2-1.11.2+build.2-mergedv2.jar` / `feather-gen2-1.11.2+build.2.tiny`. The five source files below match `ornithe-feather.sources.sha256`.
- 1.12: `decompiled_minecraft/1.12/ornithe-feather/.decompile-provenance.json`; mapping artifact `feather-gen2-1.12+build.2-mergedv2.jar`; raw client SHA-256 `59e84cf6acc3912e1705ab05c722485afb3ec33343eef4756d8c16edfe6411ca`. All five source files below match the embedded `sources` map.
- 1.12.1: `decompiled_minecraft/1.12.1/ornithe-feather/.decompile-provenance.json`; mapping artifact `feather-gen2-1.12.1+build.2-mergedv2.jar`; raw client SHA-256 `19331fb29bf8b8d5ed764677c94cca9d66481e10104f660eb555894a4300cf22`. All five source files below match the embedded `sources` map.
- 1.12.2: `decompiled_minecraft/1.12.2/ornithe-feather.provenance.json`, exact run recorded as 1.12.2 and mapping artifacts `feather-gen2-1.12.2+build.2-mergedv2.jar` / `feather-gen2-1.12.2+build.2.tiny`. The five source files below match `ornithe-feather.sources.sha256`.

SHA-256, in release order 1.11.2 / 1.12 / 1.12.1 / 1.12.2:

| Source file | 1.11.2 | 1.12 | 1.12.1 | 1.12.2 |
|---|---|---|---|---|
| `net/minecraft/block/FarmlandBlock.java` | `09e260528fd78b4bbdbc302726c7ae937fbc49f17b674c4a7ad802e0f7b6f5e8` | `b5902f8fab6bb9388be5e488021a4977783fea0ca9404798460f207980374da2` | `b5902f8fab6bb9388be5e488021a4977783fea0ca9404798460f207980374da2` | `d84c75dae139181b5d58cfa34f3cb77c5b2d4bda63d6ad9c3b52a47350bd0b98` |
| `net/minecraft/entity/Entity.java` | `ce8104a17ce783df639cf9726e7b1cd0936563eaa7ba308603335bbe05d49440` | `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a` | `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a` | `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a` |
| `net/minecraft/server/entity/living/player/ServerPlayerEntity.java` | `c2187f10b589bbfb47bef2a1b573781fecad55a13fb80c4c5dc11260d9f12197` | `ad4effc65edd98098d86af8ba725f6d6c2c94068f996739cf2734d34ecf804a9` | `ad4effc65edd98098d86af8ba725f6d6c2c94068f996739cf2734d34ecf804a9` | `ad4effc65edd98098d86af8ba725f6d6c2c94068f996739cf2734d34ecf804a9` |
| `net/minecraft/server/network/handler/ServerPlayNetworkHandler.java` | `947be17cf3d7217e7c8e563d4dd312cd133de1dc06bcd82b4431c9e5e74bc0bf` | `c9c5d46564e2d9b2f66a79f712a5cabcf2976d019675d1c129d1e08bef2e9ac0` | `e240844b55df7c3174d64212cdac3f2bebcd7654adc04ed5d7e893604dab9589` | `77bf65b2c48ff952713942e183af1cd5fb243ad4f8fd2e53aa1b97272e7acd7c` |
| `net/minecraft/client/network/handler/ClientPlayNetworkHandler.java` | `65d362c00de1adfd55fae5f858b91bf9b227525b987c09a2d223279376a66175` | `633c59b54bdbe79a2430b59215033206c886249b154044ca95965b375ba3f8e6` | `15e5e391df2e799ce04cac91af6feb7b9dc866a8963cfb03bc1bd04f375b986f` | `fce21d9902e555fd46545fb04bdb6c4a912eef6398790776ad1a122e09960c7c` |

## Stable disposition

Eligible receiver dependency: the new 1.12.2 caller enters the existing server player correction producer. The client correction-response method is unchanged across all four releases. No code change is required for this bounded receiver observation; retain the direct client movement-response hook as a separate concern only if another source comparison finds a behavioral delta. No tests, builds, launches, decompilation, or primary-source writes were performed.
