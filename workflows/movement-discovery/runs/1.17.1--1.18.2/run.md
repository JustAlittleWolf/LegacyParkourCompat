# Discovery: 1.17.1 to 1.18.2

- Status: active
- Scope: client player movement; older A = exact 1.17.1; newer B = exact 1.18.2
- Repository revision and start date: `c133c29`; 2026-09-26
- Naming: aligned official Mojang names. Both sides explicitly generated with `mojmap`, using their own exact version's Mojang mappings.
- A source command (from owner-provided run context): `decompileMinecraft --versions=1.17.1 --mappings=mojmap`; successful generation and artifacts verified by coordinator; no A raw Gradle log copied into this worktree.
- B source command: `gradlew.bat -g .gradle-user-home decompileMinecraft --versions=1.18.2 --mappings=mojmap`; output confirmed exact requested/resolved 1.18.2, official mapping applied, finished with mojmap, build successful. Live tool log is not persisted.
- Toolchain: Gradle 9.7.1; Java 25.0.3+9-LTS decompiler JVM; B requires Java 17 bytecode. Vineflower 1.12.0; Tiny Remapper 0.14.1; Mapping IO 0.9.1; Gson 2.14.0; ASM 9.10.1. Vineflower warning severity; several lambda “processed twice” warnings noted at B generation, to disposition if relevant.
- Shared source output is accessed through a verified worktree junction to `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft`. No source tree is tracked.

## Artifact manifest

Paths in this manifest are relative to the respective `decompiled_minecraft/<version>/mojmap/` source root. SHA-1 values are publisher identities; SHA-256 values were computed locally.

### A: Minecraft 1.17.1

- Exact version JSON ID: `1.17.1`; source owner cache: its isolated `build/minecraft-decompile-cache/1.17.1/`.
- Original client URL: `https://piston-data.mojang.com/v1/objects/8d9b65467c7913fcf6f5b2e729d44a1e00fde150/client.jar`; publisher SHA-1 `8d9b65467c7913fcf6f5b2e729d44a1e00fde150`; local SHA-256 `A49B4A56C5BBE15C9ED9FE53EFA9A591F265A1F5BA7D6AA9739A58EA7A92B79D`.
- Official mappings URL: `https://piston-data.mojang.com/v1/objects/e4d540e0cba05a6097e885dffdf363e621f87d3f/client.txt`; publisher SHA-1 `e4d540e0cba05a6097e885dffdf363e621f87d3f`; local SHA-256 `2B28DED68F8602AAF2F35EF92DD10EE41BA2CF9723E29570155D60E1723848E9`.
- Remapped jar local SHA-256 `7277878475794E10CC0169A3E6F431C039186F366B9A5FF0741331EE50E0847C`.
- Shared source root `decompiled_minecraft/1.17.1/mojmap/`; verified B-side access to A `LocalPlayer.java` SHA-256 `C9A91CB6CB57806BC8D22E5BFE2D97DAAF21D5D2A48F34A6E2C53164C61C5812`.
- Never regenerate A; its owner resolved a prior remapped-jar lock and supplied the verified artifacts/source.

### B: Minecraft 1.18.2

- Exact resolved version `1.18.2`; version JSON release time `2022-02-28T11:42:45+00:00`; client bytecode Java 17.
- Original client URL: `https://piston-data.mojang.com/v1/objects/2e9a3e3107cca00d6bc9c97bf7d149cae163ef21/client.jar`; publisher SHA-1 `2e9a3e3107cca00d6bc9c97bf7d149cae163ef21`; SHA-256 `1D09E3639644B6B2254499469D0765CC005A286D19F3FA595B0ED8FB07971EC7`.
- Official mappings URL: `https://piston-data.mojang.com/v1/objects/a661c6a55a0600bd391bdbbd6827654c05b2109c/client.txt`; publisher SHA-1 `a661c6a55a0600bd391bdbbd6827654c05b2109c`; SHA-256 `A2AA6EE1030BFEF79E9B2E08E79DE1637FDD7ECB5BF8891CF2E9A4B186042543`.
- Remapped jar SHA-256 `2D0C4B2EAC022E43DBE4706B7FE18C51547E4FBED86B675E92AB2040A9CF51D4`.
- Source root `decompiled_minecraft/1.18.2/mojmap/`; 4,236 Java source files.

## Source hash index

SHA-256; every path is relative to that version's Mojmap source root. Extend this inventory for every cited method/resource.

| Source path | A hash | B hash |
|---|---|---|
| `net/minecraft/client/player/KeyboardInput.java` | `EA41065C909E53F1A2CC29ECDB6A9A8F9265D2801CD8B95B996E182C318ECD69` | `281622F8481654035196A7BC1554D5251C1040518375E3AC6F6439E5EC894A75` |
| `net/minecraft/client/player/Input.java` | `367C3A9B0B21D8F106A21FD2C73A3018685DBF07D9C8A9340E2D4C9D73359201` | `EB50A4E268EC5FF8423D2805499CA3C7BAE33765CB44CFECEF808E38FA6DE3C3` |
| `net/minecraft/world/entity/player/Player.java` | pending | `BF639C1962FF90D69E4569B2B18F6FCF57AC46EF80B19686F0FBC1687FCA744A` |
| `net/minecraft/world/entity/LivingEntity.java` | `33FD081AADB2B6FDC9EBF487DB6DA5B38C54F4B8676572790EE2203690D15E6F` | `DB4168D531CAF18F22E3FEFD073365E776DA4075CE01452BB9F7671D9B458782` |
| `net/minecraft/util/Mth.java` | `24515C4549E01E985017227DCCF7159166A675B232BE9135D5F896022A9CB113` | `32747C5B09FC184BAAE356E39A0088FD66C9F08F69D9E98B67C19E1DE6F1BB2E` |
| `net/minecraft/world/entity/Entity.java` | `AB28E1FBA924771EC048140DFD293EE5A46A7DFE81F71A1A0B1AECC1927232DE` | `2228FDACA5793171CBD94038306D571A6ADA78CA96F5734EFB4CADA5B744C10A` |
| `net/minecraft/client/player/LocalPlayer.java` | `C9A91CB6CB57806BC8D22E5BFE2D97DAAF21D5D2A48F34A6E2C53164C61C5812` | `99C2D18BCD23243AFB8F95C5BAFB21FB0BE7EA04AACBB14FCF7BE7CED2C9C095` |
| `net/minecraft/world/phys/AABB.java` | pending | `12134682C7F0C19A4DF431E4509D661B866B84F680B6DB82194AF8ABA7D0A50F` |
| `net/minecraft/world/phys/shapes/VoxelShape.java` | pending | `99F8B6E44E6C249B251D98A99E38158EBCB459733B26CC98BAE15D51D3B87417` |
| `net/minecraft/world/level/block/Block.java` | pending | `57C42EE375691755EF5D47FAD3F226F34A2043558704EC332C1A7E092FDABBA6` |
| `net/minecraft/world/level/material/FlowingFluid.java` | pending | `BBFB661B524AC92F74579CD4B61C1A25DF00ECC4BFE775A5516F7E4A7C8768F3` |
| `net/minecraft/world/effect/MobEffects.java` | pending | `92BDAB264537C8ACF1AF38A25BBBCEEF557A4CD24E248446C6463FA9812A524E` |
| `net/minecraft/world/entity/ai/attributes/Attributes.java` | pending | `C41860B83315D5265632E9A90978E38794D83D1A0CD996DBB9C7FD8E56560DF5` |
| `net/minecraft/world/item/enchantment/EnchantmentHelper.java` | pending | `73D83D685F1F7872B5B85DE26E274FC547C6094C4E2C2FA13291FB8CDE712105` |
| `net/minecraft/world/item/enchantment/Enchantments.java` | pending | `BB945530CB616FFE8C23156EC0BDD5819094C92D4FB808BD9254EDFDB7953BF2` |
| `net/minecraft/client/multiplayer/ClientPacketListener.java` | pending | `E718016022C2AE86A2C354AF2D34FE4DE7D6E36DC8792D2E2C1F08ABB6B77B7B` |

## Correspondence and call order

See [index.md](index.md). Resolve actual members, descriptors, inheritance and state reads/writes by paired source inspection; no symbol-only correspondence is accepted.

## Coverage ledger

- Stage 1 input and tick ordering: findings; F-001 covers the only movement-relevant difference found in the inspected `LocalPlayer.tick()`/`aiStep()` and input producer slice. A/B `Input` structure, forward-impulse sprint threshold, key-to-impulse assignments, sampling order and `aiStep()` order were checked. Keyboard slowdown changes from double multiplication plus float cast to float multiplication, but the vanilla producer supplies only -1/0/1 before the 0.3 slowdown, yielding the same representable float results; no behavioral delta was retained.
- Stage 2 player-specific state and gates: pending; anchors indexed.
- Stage 3 living movement integration: in-progress; F-002 covers fall-flying lift coefficient. Remaining travel branches and dependencies pending.
- Stage 4 entity movement and collision: in-progress; `Entity.move()` collision flag producer and B local-player classifier examined for F-001; broader axes/step/support/callback slices pending.
- Stage 5 blocks and fluids: pending; anchors indexed.
- Stage 6 effects, enchantments, attributes and equipment: pending; B resources still need inspection.
- Stage 7 external influences and dependency closure: pending; anchors indexed.

## Dependency queue and blockers

- B jar resource entries/tags/defaults for stages 5–6: not inspected yet.
- Any source warnings affecting movement members: review per slice; global lambda warnings do not by themselves invalidate unrelated methods.
- New dependencies discovered by slices: add with parent slice and resolution evidence.

## Findings

None yet. Candidates require a concrete precondition and reachable client-player chain.

## Resume checkpoint

- Completed: exact pair artifact/source anchor checks, ordered B-side index, stage-1 bounded slice and findings F-001/F-002.
- Next: stage 2 player-specific gates; later resolve stage-4 collision classifier and continue remaining travel, block/resource, effect and external-input slices.
- Runtime validation: not performed.

## Source audit closure

- Coverage counts: 1 terminal (`findings`); 2 in-progress; 4 pending; two findings recorded.
- Paired evidence limited to files named in the source hash inventory; add every cited source/resource hash before closing a slice.
- Unresolved dependencies remain open; this is not a complete audit.
