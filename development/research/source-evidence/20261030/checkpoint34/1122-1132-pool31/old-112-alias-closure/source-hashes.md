# Source and method hashes

Canonical root: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\<version>\ornithe-feather`

## Source identity metadata

- 1.12 `.decompile-provenance.json` SHA-256: `27515e5f4c71e7db2f035a81716507128d77466c75380ad1ef7d140c06401467`
  - Feather Gen2 1.12 build 2 merged JAR input SHA-256: `73ce7d6d0a281db15fa030ae09e836387dae1172fdffcaf3e2d1a4840830b719`
  - Minecraft client input object `909823f9c467f9934687f136bc95a667a0d19d7f`, SHA-256: `59e84cf6acc3912e1705ab05c722485afb3ec33343eef4756d8c16edfe6411ca`
- 1.12.1 `.decompile-provenance.json` SHA-256: `0ff5246e8d29c04554cc9ec93ae9b4abef539652325b6c0ac822851d7186a9c8`
  - Feather Gen2 1.12.1 build 2 merged JAR input SHA-256: `f94febd2167d6091b4c157aef0a85c8791e6f8fb27b883e27a82a68c7b02b9c7`
  - Minecraft client input object `83385d346cf7f97d1e447b888750fb88e9928b93`, SHA-256: `19331fb29bf8b8d5ed764677c94cca9d66481e10104f660eb555894a4300cf22`
- 1.12.2 `ornithe-feather.provenance.json` SHA-256: `747425a26da363d1abc8a2cecd83a74504aeb1f58be93aa53f48c690e35318ce`
  - `ornithe-feather.ready.json`: exact version 1.12.2, `ornithe-feather`, Feather Gen2 build 2 merged JAR plus `.tiny` mapping.
  - `ornithe-feather.sources.sha256` SHA-256 from ready record: `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`.

The 1.12/1.12.1 direct file digests below were checked against embedded `.decompile-provenance.json` source entries. The 1.12.2 digests were checked against the matching source-manifest rows.

## Direct source SHA-256

| Source file | 1.12 | 1.12.1 | 1.12.2 |
|---|---|---|---|
| `net/minecraft/entity/living/LivingEntity.java` | `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6` | `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6` | `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6` |
| `net/minecraft/entity/Entity.java` | `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a` | `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a` | `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a` |
| `net/minecraft/world/World.java` | `f7ab7a34d456e720e7ee4f34fbd0eb10f7f96151596c8c615ab2edef05a884d8` | `d182122a9b052f57803672c21b0941cb7e7d41ed756cd0a4ea8c0858684c8259` | `e9fa9b8d6d31ad57a5b876f5f63a5e3c554a23437decf1845a493daf48233594` |
| `net/minecraft/block/LiquidBlock.java` | `00f7995dd49a48bf97316e30b3d6e41d8ee1e65dd212f3dc64891883621c4260` | `00f7995dd49a48bf97316e30b3d6e41d8ee1e65dd212f3dc64891883621c4260` | `00f7995dd49a48bf97316e30b3d6e41d8ee1e65dd212f3dc64891883621c4260` |
| `net/minecraft/util/math/Box.java` | `f788b8146b14f299ccb58ea0854609f845c298a963295d503de2e30e15d66f3a` | `f788b8146b14f299ccb58ea0854609f845c298a963295d503de2e30e15d66f3a` | `f788b8146b14f299ccb58ea0854609f845c298a963295d503de2e30e15d66f3a` |

## Complete method-body SHA-256

Body extraction includes braces and uses UTF-8. Each value is equal across 1.12, 1.12.1, and 1.12.2.

| Method | SHA-256 |
|---|---|
| `LivingEntity.jumpInWater()` | `45ada6f40c31397692fead02e195b20d9e5c737c9ab27caa6d01a48042e4cfa0` |
| `LivingEntity.moveRelative(float,float,float)` | `82b08ec68591b107e60c32b128359b6275de4531df3c1dd8deb8b8759d36d209` |
| `LivingEntity.mobTick()` | `34b8d985ba8a6df3b3b11059246d03eeaa7f955a35f75b497419c42fe129832` |
| `Entity.checkWaterCollisions()` | `0ebe4f13e1f3f49a4f29072918ef82aa85a656c7610be8077306145a944f4669` |
| `World.applyLiquidDrag(Box,Material,Entity)` | `55239dba546004396895e3d5b5667a4fc051318bd2feb23d8ec6c5a36c8394b3` |
| `LiquidBlock.getFlow(WorldView,BlockPos,BlockState)` | `41acbbf61dcfbc4f0a8cfd33f54963dbbad67d92db8ba0d6ab7ce3d298594eb7` |
| `LiquidBlock.applyMaterialDrag(World,BlockPos,Entity,Vec3d)` | `5fd971ddd1196df352e27e9953a673ae4e135786764165db05323e5ef25d68c3` |
