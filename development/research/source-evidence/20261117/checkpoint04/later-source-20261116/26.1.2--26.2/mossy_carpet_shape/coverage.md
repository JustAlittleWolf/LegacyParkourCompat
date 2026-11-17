# Pale moss carpet shape-provider comparison: 26.1.2 → 26.2

## Scope and result

This bounded source-only slice compares the direct shape/state providers and registration for `Blocks.PALE_MOSS_CARPET` (`MossyCarpetBlock`). The reviewed outline, collision, and inherited support-shape operations match. No movement difference was found in this provider slice. This is not a complete pair inventory, ordinary-world reachability proof, trajectory result, or runtime check.

## Exact source identity

The source roots are `decompiled_minecraft/26.1.2/unobfuscated/` and `decompiled_minecraft/26.2/unobfuscated/` (both declared namespace `unobfuscated`). Version-root sidecars are outside the mapping folders:

| Release | Ready marker | Provenance | Source manifest binding | Artifact manifest |
| --- | --- | --- | --- | --- |
| 26.1.2 | `unobfuscated.ready.json`, SHA-256 `f24af0f4d38543c2bd9c19b50f347d4e89d6027050e4b21ae070734843946bb7` | `unobfuscated.provenance.json`, SHA-256 `8d727f880cc91f4a297e148c5b75976303d051457334bc689268fbdf631a012a` | `unobfuscated.sources.sha256`; ready-declared and actual SHA-256 `54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0` | `artifacts.sha256`; ready-declared and actual SHA-256 `89f648229de83b0109460695c9f1f3bed7ef36efb84ede9224e80cce87118f92` |
| 26.2 | `unobfuscated.ready.json`, SHA-256 `f9406adb6bf7cb4c1ab0792a798ab2cb90e082ad4c2eee032c9f674d0ea8070f` | `unobfuscated.provenance.json`, SHA-256 `a328f5333a1b27aadde4422b23159a323ae219ccd26f11207c1a5461366f27d1` | `unobfuscated.sources.sha256`; ready-declared and actual SHA-256 `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894` | `artifacts.sha256`; ready-declared and actual SHA-256 `ba9dc53a41bca9744ec1c8ab2ce9363764a22333c39ef64d5105f4e3bf5c98ba` |

The ready markers bind their exact version, `unobfuscated` mapping, source manifest, and artifact manifest. The following cited Java-file SHA-256 values match the respective source-manifest rows.

| Source file | 26.1.2 SHA-256 | 26.2 SHA-256 |
| --- | --- | --- |
| `net/minecraft/world/level/block/MossyCarpetBlock.java` | `2aa1fcd17313b35a87c5d35cb93290c281cce8047903ff91d5ed60bdda886284` | `2aab884a6b8be3dd4e7fa0f971ca1b916bdde03a863603e98d698abf4be87f44` |
| `net/minecraft/world/level/block/Blocks.java` | `ba8a258b33f73fe03f93e7b02f9c25d4f66cf3aaab04c4580d0863cc71bc866f` | `f3f2faeed23e9697407069a1d523107491590b8710175523ea05294d5bd00435` |
| `net/minecraft/world/level/block/Block.java` | `1693cfb7b84190a2fe664470a56d59e78ed5bd7d722a2bd16888b036d4d8e977` | `cec6a05e644e4a7feb8253cc4ca772a98f0e116fb098a1b7ee7302984ac7ecab` |
| `net/minecraft/world/level/block/state/BlockBehaviour.java` | `9db85de84e502903e6fe497f043b58620b92089236ffb213f0b93db979428d13` | `9c7a103492d0714c90397da88eb696912ff6a9ca1c005d984c4746d52637fd1e` |
| `net/minecraft/world/phys/shapes/Shapes.java` | `cfa63cbed77c922a68e7e8a6a3b78b09a22c7de51e0146e481e9c9d26db66f58` | `6ed9460d58727770bdec59c4f3184f075644f3c411be1eeab2eae17e8ece1891` |

## Paired provider operations

- `MossyCarpetBlock` declares `BASE` and horizontal `WallSide` properties. The default state sets `BASE=true` and all four side properties to `NONE` (both endpoints, `MossyCarpetBlock.java:42–64`).
- `makeShapes` builds the same rotated `low` and `tall` shape maps in both versions: `Block.boxZ(16, 0, 10, 0, 1)` rotated horizontally for low side pieces, and `Block.boxZ(16, 0, 1)` rotated in all directions for tall pieces. For each state, it starts with the downward-rotated tall shape when `BASE` is true, then unions the selected low/tall side pieces for each non-`NONE` wall property; an empty result falls back to `Shapes.block()` (`MossyCarpetBlock.java:66–87` both sides).
- `getShape` returns the cached state-specific shape map result (`MossyCarpetBlock.java:90–92` both sides). `getCollisionShape` differs from that outline selector in the same way at both endpoints: if `BASE` is true, it applies the shape map to `defaultBlockState()` (the base-only state), regardless of this state's wall-side values; if `BASE` is false, it returns `Shapes.empty()` (`MossyCarpetBlock.java:95–97`). Thus adding wall-side outline pieces does not add those pieces to this class's collision shape.
- `MossyCarpetBlock` does not override `getBlockSupportShape`. The inherited `BlockBehaviour.getBlockSupportShape` delegates to `getCollisionShape(..., CollisionContext.empty())` (26.1.2 `BlockBehaviour.java:297–298`; 26.2 `:291–292`), so its support-shape result follows the same base-only/empty collision selector. `BlockBehaviour.BlockState` delegates shape, collision, and support queries to the block provider (26.1.2 `:672–693`; 26.2 `:678–699`).
- Shape-construction helper bodies compared for this call path are the same: `Block.boxZ` overloads used above (26.1.2 `Block.java:191–203`; 26.2 `:187–199`) and `Shapes.rotateHorizontal` / `Shapes.rotateAll` (26.1.2 `Shapes.java:403–440`; 26.2 `:404–441`).
- `Blocks.PALE_MOSS_CARPET` uses `MossyCarpetBlock::new` at both endpoints. Its relevant property chain is the same: `of()`, `ignitedByLava()`, pale-moss map color, strength `0.1F`, moss-carpet sound, destroy push reaction, and `noOcclusion()` (26.1.2 `Blocks.java:6985–6996`; 26.2 `:5787–5798`). The registration key is a string literal in 26.1.2 and a `BlockItemIds` constant in 26.2; this representation change is not treated as a shape difference.

The full `MossyCarpetBlock.java` files are not byte-identical; their only text difference is the `@Block.UpdateFlags` parameter annotation in the unrelated `placeAt` method. The paired shape, collision, and inherited support methods above have the same bodies. No state population, ordinary player placement route, world-generation input, or collision-consumer trajectory was examined.

## Limits

This report establishes only the bounded provider operations and exact supporting helper paths listed above. It does not establish which pale moss carpet states are normally generated or reachable, or close other shape, contact, movement-factor, resource, tick, packet, or pair-level dependencies. No implementation, build, test, runtime, or gameplay conclusions are made.
