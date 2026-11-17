# 1.16.2 → 1.16.3 ordinary-player movement source coverage

Status: terminal source comparison candidate: **NO MOVEMENT DIFFERENCE FOUND in the exact pair**. The exact-version inventories are complete and compared. This is not independent acceptance or pair freeze; exact patch onset beyond this pair is not established.

## Exact source identities

- Older: Minecraft `1.16.2`, Mojmap, `decompiled_minecraft/1.16.2/mojmap/`. Ready marker `decompiled_minecraft/1.16.2/mojmap.ready.json` identifies version `1.16.2`, mapping `mojmap`, status `ready`; provenance `decompiled_minecraft/1.16.2/mojmap.provenance.json` SHA-256 `a9b166406064e877751542fe7df27acc32be3843f6fdc2280dac32ce0b54207c`; source manifest `decompiled_minecraft/1.16.2/mojmap.sources.sha256` SHA-256 `10d312ca29ee48e25f4727adafb9fcfbd741dcebe81b343d3dcffa071952a7a7`; 3,516 Java files.
- Newer: Minecraft `1.16.3`, Mojmap, `decompiled_minecraft/1.16.3/mojmap/`. Exact metadata URL in hidden provenance: `https://piston-meta.mojang.com/v1/packages/6485dd131ef68c968041a9f6fd73094b027e42e1/1.16.3.json`; exact client mapping artifact SHA-256 `c41c58faaf3a3b651d8883b9b1df355cb6ad59c4d6296b2e1ffc3dfe2bf9be3e`; hidden mapping-local provenance/source map `decompiled_minecraft/1.16.3/mojmap/.decompile-provenance.json` SHA-256 `513271804ead6757345d305717e7e0bfc1641434c35a3b254f287bd8fd281498`; 3,516 Java files. The `sources` object is the manifest; no root-sidecar manifest was assumed.
- Both inventories and source bindings match the input-readiness locator `build/source-inputs-115-116-20261117/README.md`. Each side has 3,516 paths; the complete path/hash comparison found four changed Java files and no added/removed source paths.

## Coverage inventory and result

The exact tick/dependency-relevant classes below are byte-identical by the complete source manifests. This covers local input and player tick/travel, entity and player movement state, the collision query and shape core, fluid state, block collision providers, movement attributes/effects/items/enchantments, and movement packet representations. Identical source bodies retain their identical enclosing call/gate order for this exact edge.

| Slice | Paired source evidence | Result |
|---|---|---|
| Client input and ordinary local-player tick/travel | `client/player/Input.java` `367c3a9b0b21d8f106a21fd2c73a3018685dbf07d9c8a9340e2d4c9d73359201`; `client/player/LocalPlayer.java` `6011569e766bb1568609147be9aa14e9c08c51948e3d3a60fd066e848f6a8c2b`; both endpoint hashes equal | No difference |
| Player state, inherited tick/travel, position/velocity/box writers and collision entry | `world/entity/player/Player.java` `d2e26589bdb6a20dc914266db06aa48f50811efc792d6e63b3008c9199914960`; `world/entity/LivingEntity.java` `b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88`; `world/entity/Entity.java` `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666`; equal endpoints | No difference |
| Collision queries, providers, shapes and fluid state | `world/level/CollisionGetter.java` `b507d6be11e5985a62cfeb249a99dcb5f8edaf346f12cb2487797d9e01763eac`; `world/level/block/Block.java` `2838aa4fb4052b3bd78d77b3d232a6321d03bd2dfd52275ffacfe6915a89e5b6`; `world/level/block/state/BlockBehaviour.java` `c62e2f07094e5a7bcad00cbae7d24497bf636cc740232a1472f4128a472def8b`; `world/phys/shapes/Shapes.java` `ec218dca68cad3525364967aadfbb8c0bead0cc44aa3ee2beba31e00e55ae2d4`; `world/phys/AABB.java` `514558cf4827679d84a4debd4f400d7d65bd4b8982b7ae8124a0d83a916ba878`; `world/level/material/FluidState.java` `665b4024a32f659d92ce1259b25f529616e44b6f3df8a6aab4a4a2c57c7bc26d`; equal endpoints | No difference |
| Movement modifiers and direct protocol responses | `world/entity/ai/attributes/Attributes.java` `839e9274a4ae91dc81802914b6f4f5f354d54ffa9005aa630da83f070b17efaa`; `world/effect/MobEffect.java` `665e2365c273a8004d327020615b76a0f5faa839bab20f96a99f412c7330e812`; `world/item/Item.java` `67acac1883a9fe9bd2c73046018a8c9f4545cc081ca65dedcbd78574f955e697`; `world/item/enchantment/Enchantment.java` `9ad634e8770a61baa3f292078d90d7067f3a0e69576cae7818a6a2ae253e9a49`; `network/protocol/game/ServerboundMovePlayerPacket.java` `d507e0f95601bd5ec9c180b58e456891dbcaa3b186ec8099e9654c5797c220d1`; `network/protocol/game/ClientboundPlayerPositionPacket.java` `893a920a818dfa91a977a367403f310811767998f4cd816f6a35d48f35c33e9d`; equal endpoints | No difference |

The four changed Java sources were also reviewed as a complete delta set:

| Changed file | Exact change | Movement disposition |
|---|---|---|
| `net/minecraft/DetectedVersion.java` | Source hash changes, but no player movement logic is introduced. | Excluded build/version-description code. |
| `net/minecraft/world/entity/Mob.java` (1.16.3 line 842) | After dropping an equipment stack, the mob clears that equipment slot with `setItemSlot(equipmentSlot, ItemStack.EMPTY)`. | Non-player mob equipment/drop state; out of the fixed player movement path. `Player` is not a `Mob` in this source hierarchy. |
| `net/minecraft/world/entity/ai/navigation/PathNavigation.java` (1.16.3 lines 48, 255–295, 363–364) | Adds a navigation stuck flag; sets/clears it in stuck and timeout handling; exposes `isStuck()`. | This is `Mob` navigation, not player navigation. |
| `net/minecraft/world/entity/ai/behavior/MoveToTargetSink.java` (1.16.3 lines 78–82) | Its `stop` cooldown reset additionally requires `mob.getNavigation().isStuck()`. | Brain behavior for `Mob` walk targets; not a player movement operation. |

The changed `PathNavigation`/`MoveToTargetSink` files form a non-player AI dependency edge only. No changed player-callable method, player collision provider, movement callback, or direct player response appeared in the four-file complete delta. The full manifests show every other Java source body is unchanged for these exact versions.

## Gates and limits

- No changed movement-relevant resource/tag provider was identified in the complete Java delta. Packaged resource membership was not inspected or extracted; this report makes no archive-membership claim.
- No runtime behavior or trajectory was tested or inferred.
- This report covers only exact 1.16.2→1.16.3 source behavior. It does not reopen or substitute for the frozen 1.15.2→1.16.5 endpoint pair, and it does not establish an earlier/later patch boundary.
- Independent source coverage audit is pending. Pair freeze is not claimed.