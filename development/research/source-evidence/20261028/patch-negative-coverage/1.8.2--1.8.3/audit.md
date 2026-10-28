# Independent blind source audit: 1.8.2 → 1.8.3

## Scope and method

Compared the canonical, read-only decompilation trees at `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/{1.8.2,1.8.3}/ornithe-feather/`. The comparison was a blind whole-tree Java source hash/path inventory followed by inspection of every changed source file and its player-reachable dependency where relevant. No wiki inventory or product implementation/history was consulted.

The project scope reviewed was fixed-profile local-player movement: ordinary client tick/input, pre-travel and travel branches, jump and movement values, position/velocity/box writers, collision and block-shape consumers, fluids, effects/equipment reads, and common direct block/player responses. The scope excludes rendering, server physics, non-player physics, and damage calculation.

## Source identities and integrity

| Side | Release identity | Mapping identity | Provenance manifest SHA-256 |
|---|---|---|---|
| Older | Mojang version metadata `1.8.2`, type `release`, no `inheritsFrom`; cached metadata pin URL `https://piston-meta.mojang.com/v1/packages/1e35829856a71261f5a7b2b3a83012c7434b2203/1.8.2.json`, SHA-256 `305e67478038f9a3fad4cdb7699b2093261fd6301175a782220c2dfb94ae8820` | Ornithe Feather Gen2 `1.8.2+build.2`, stable metadata selects build 2 for game version 1.8.2; artifact `feather-gen2-1.8.2+build.2-mergedv2.jar`, SHA-256 `8235d85a8471d541f2bad73c2a60c6132a9432fe804b98369982fc8de847f37b` | `8ad11ea36f2dda39ae30d57daa53316a8c0889a2f3c0c84ee5d79b9b21c25472` |
| Newer | Mojang version metadata `1.8.3`, type `release`, no `inheritsFrom`; cached metadata pin URL `https://piston-meta.mojang.com/v1/packages/413ad8fdcf8f29f4f1c2b4425aa0dade00f75dd4/1.8.3.json`, SHA-256 `4afafb220ce18063d3aec5f8bab178a5192d84ca2d63b358fee7d369aea96938` | Ornithe Feather Gen2 `1.8.3+build.2`, stable metadata selects build 2 for game version 1.8.3; artifact `feather-gen2-1.8.3+build.2-mergedv2.jar`, SHA-256 `e0ad2917dcc3a91586fb6f9b1bde78e55f1ece908572672885395e0d045dd9a2` | `7da5f772d7ed1433cb3da23861723ebaec7cb714ce49c4d1a5c30499fec3afed` |

Both cached client JAR hashes match the provenance inputs: 1.8.2 `5cc5b24312f3d182bf06e7f1a4da3a05ce94be6c861d5c9995c86e6d8811fbe9`; 1.8.3 `4da43d06c8c1605a9a3ba1bf22893245cf1b108eb5c8fed859012252370c2c06`. The version JSON pin sidecars and Feather metadata pin sidecars match the provenance URLs/hashes. The Feather metadata explicitly names each corresponding game version; no snapshot metadata or parent release was used.

Each embedded `.decompile-provenance.json` lists 1,612 Java source hashes; each tree has exactly 1,612 Java files, zero source hash mismatches, and zero Java files absent from its manifest. The decompile pipeline and tool hashes are recorded in the manifests; sources are from the pinned Java 25 / Vineflower 1.12.0 process. The cached client and mapping artifact bytes were re-hashed and match the manifest inputs. No source generation or canonical tree mutation was performed.

## Ordered coverage and verdict

1. **Tick/input and pre-travel:** `client/entity/living/player/ClientPlayerEntity.java`, `LocalClientPlayerEntity.java`, `Input.java`, `KeyboardInput.java`, and `Minecraft.java`. Local-player tick/input and its invocation path have identical source bytes. `Minecraft.java` differs only in version title and HTTP version string.
2. **Jump, travel, movement values, velocity, position, and bounding box:** `entity/Entity.java`, `entity/living/LivingEntity.java`, `entity/living/player/PlayerEntity.java`, and the client-player classes above are byte-identical. No changed movement branch, operation order, cast, numeric constant, or state write was found.
3. **Collision, shapes, and world dependencies:** `block/Block.java`, `world/World.java`, `client/world/ClientWorld.java`, and the era-existing movement-relevant block providers below are byte-identical. No changed collision query, clipping order, shape, or client-world callback was found.
4. **Fluids and common direct block responses:** `block/FlowingLiquidBlock.java`, `LiquidBlock.java`, `SlimeBlock.java`, `SoulSandBlock.java`, `IceBlock.java`, `CobwebBlock.java`, `LadderBlock.java`, `VineBlock.java`, `PistonBaseBlock.java`, `PistonHeadBlock.java`, `StairsBlock.java`, `FenceBlock.java`, `PaneBlock.java`, `TrapdoorBlock.java`, `CarpetBlock.java`, and `SnowLayerBlock.java` are byte-identical. No changed fluid, shape, velocity, or player callback behavior was found.
5. **Effects/equipment dependencies:** `entity/living/effect/StatusEffect.java`, `StatusEffectInstance.java`, and `item/ItemStack.java` are byte-identical. `EnchantmentHelper.java` changes only protection-value clamping in damage calculation, which is out of scope. `ThornsEnchantment.java` adds a null guard around damage/sound and durability handling; it does not write movement state.
6. **Whole-tree terminal comparison:** both trees have the same 1,612 Java paths. Exactly 16 Java files differ; all were inspected. Besides the out-of-scope damage changes above, differences are release-version labels/UI, hardcore death-screen flow, server command logging, spawner robustness, and exception handling in `ThrownEntity.getThrower()`.

The 16 changed paths are `block/spawner/MobSpawner.java`; `client/gui/overlay/DebugOverlay.java`; `client/gui/screen/game/DeathScreen.java`; `client/gui/screen/menu/TitleScreen.java`; `client/Minecraft.java`; `client/options/ServerListEntry.java`; `enchantment/EnchantmentHelper.java`; `enchantment/ThornsEnchantment.java`; `entity/projectile/ThrownEntity.java`; `realms/RealmsSharedConstants.java`; `server/command/handler/CommandRegistry.java`; `server/integrated/IntegratedServer.java`; `server/MinecraftServer.java`; `server/network/handler/RemoteHandshakeNetworkHandler.java`; `snooper/Snooper.java`; and `util/crash/CrashReport.java` (all beneath `net/minecraft/`).

The `ThrownEntity` change catches malformed owner-UUID parsing only in the `ServerWorld` lookup. Client worlds do not enter that branch. `EnderPearlEntity`'s client-reachable code is unchanged; the separate server-side pearl teleport response remains server physics and is excluded by the project invariant. No player movement delta is evidenced by this changed source.

**Difference evidence:** none found for the assigned player-movement scope. The 16 whole-tree differences do not alter a client-local-player movement operation or its client-side movement dependencies.

## Coverage state

- **Checked:** complete Java source path/hash inventory; local tick/input; jump/travel; position/velocity/box; collision and world access; common historical block shapes/effects; liquids; direct block/player responses; relevant effects/equipment dependency; every changed source file and the changed thrown-owner call chain.
- **Partial:** none within the stated bounded scope.
- **Unexamined:** non-player and vehicle physics, rendering-only behavior beyond classifying version-string changes, general server behavior except the branch needed to scope the changed UUID guard, and unrelated world/gameplay systems.

This is an independent bounded audit report only; it does not assert pair freeze or source acceptance for any other finding.
