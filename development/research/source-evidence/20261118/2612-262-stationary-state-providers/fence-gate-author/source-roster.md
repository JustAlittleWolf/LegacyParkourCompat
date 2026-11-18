# Fence gate source roster

Exact pair: 26.1.2 `unobfuscated` -> 26.2 `unobfuscated`. SHA-256 below is the recomputed source file digest; each digest equals that path's row in the corresponding `unobfuscated.sources.sha256` manifest.

| Source path | 26.1.2 SHA-256 | 26.2 SHA-256 | Slice covered |
|---|---|---|---|
| `net/minecraft/world/level/block/FenceGateBlock.java` | `e9e51b00bf59a66951741e0f0ef28d5d92815df9591e4f229fdb838588d105df` | `0a98db4316efae2e8450c7d8afe496ed45fa5ee9cddb8bd27359d15c355471e3` | Selectors; outline, collision, support and occlusion providers; gate methods |
| `net/minecraft/world/level/block/HorizontalDirectionalBlock.java` | `7456a35ed53ecc1d7f24f01b1a9b7585d0bc1d512c026ebb398322abb54f811d` | `7456a35ed53ecc1d7f24f01b1a9b7585d0bc1d512c026ebb398322abb54f811d` | Inherited facing property |
| `net/minecraft/world/level/block/state/properties/BlockStateProperties.java` | `0318b21d703f6a521d5ea4c5a74494097b9b8776c2f43a3084ad7569172c8a27` | `c6e4b49663a09110e96a3b51628f92defd83da9267a5d569a8ac8fc0c8893cd0` | Property domains |
| `net/minecraft/core/Direction.java` | `30fb61592f1a154bb8fc239ab8df1cd2b209f63317e0c7dde4d5b9b3e260d754` | `30fb61592f1a154bb8fc239ab8df1cd2b209f63317e0c7dde4d5b9b3e260d754` | Horizontal facing values and axes |
| `net/minecraft/world/level/block/state/StateDefinition.java` | `195bfe795c1c908f332afd90e9f59036fe3bf51997fe156c240c3c7c7eb3d4d0` | `195bfe795c1c908f332afd90e9f59036fe3bf51997fe156c240c3c7c7eb3d4d0` | Cartesian legal state set |
| `net/minecraft/world/level/block/Block.java` | `1693cfb7b84190a2fe664470a56d59e78ed5bd7d722a2bd16888b036d4d8e977` | `cec6a05e644e4a7feb8253cc4ca772a98f0e116fb098a1b7ee7302984ac7ecab` | Column geometry and 1/16 scaling |
| `net/minecraft/world/phys/shapes/Shapes.java` | `cfa63cbed77c922a68e7e8a6a3b78b09a22c7de51e0146e481e9c9d26db66f58` | `6ed9460d58727770bdec59c4f3184f075644f3c411be1eeab2eae17e8ece1891` | Axis rotation mapping |
| `net/minecraft/world/level/block/state/BlockBehaviour.java` | `9db85de84e502903e6fe497f043b58620b92089236ffb213f0b93db979428d13` | `9c7a103492d0714c90397da88eb696912ff6a9ca1c005d984c4746d52637fd1e` | State callback dispatch and cache |
| `net/minecraft/world/level/BlockCollisions.java` | `eb8a8f6f07b1d6384f17987818f056d41adfd6b2ad120d26774bfa550f4fc51e` | `eb8a8f6f07b1d6384f17987818f056d41adfd6b2ad120d26774bfa550f4fc51e` | Candidate state collision-shape request |
| `net/minecraft/world/level/CollisionGetter.java` | `dddff4897e8d5d01e3ac132d85c474e6a3b9bae928e48b8c907aa9eabdb7fb01` | `8e7d1a54d27e0f187667439539c59b8892f966f1b9c8107eb162c6d43f830f6c` | Interface route to local collision iterator; generic helper visibility delta noted |
| `net/minecraft/world/level/block/Blocks.java` | `ba8a258b33f73fe03f93e7b02f9c25d4f66cf3aaab04c4580d0863cc71bc866f` | `f3f2faeed23e9697407069a1d523107491590b8710175523ea05294d5bd00435` | Registered gate variants |