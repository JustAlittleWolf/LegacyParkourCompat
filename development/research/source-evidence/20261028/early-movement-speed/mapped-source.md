# Minecraft 1.8.2–1.8.4 movement-speed cap source closure

Scope: exact canonical Ornithe Feather mapped sources for the `generic.movementSpeed` attribute and ordinary player movement consumers. This report establishes only the 1.8.2, 1.8.3, and 1.8.4 source bodies; it does not establish 1.8.0 or 1.8.1 behavior, runtime parity, or a product profile boundary.

## Source identity and integrity

Canonical root: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft`. The three inspected trees are `1.8.2\ornithe-feather`, `1.8.3\ornithe-feather`, and `1.8.4\ornithe-feather`. Each provenance record binds the corresponding exact client input, exact `feather-gen2` mapped artifact, and `1.8.x+build.2` mapping selection:

| Release | Client input SHA-256 | Feather mapped artifact SHA-256 | Mapping metadata SHA-256 | Version JSON package id / SHA-256 | Provenance SHA-256 |
|---|---|---|---|---|
| 1.8.2 | `5cc5b24312f3d182bf06e7f1a4da3a05ce94be6c861d5c9995c86e6d8811fbe9` | `8235d85a8471d541f2bad73c2a60c6132a9432fe804b98369982fc8de847f37b` | `9019d76f144d8ea04b525db9e293a140cf70ca9333f056baead1391c17e1525e` | `1e35829856a71261f5a7b2b3a83012c7434b2203` / `305e67478038f9a3fad4cdb7699b2093261fd6301175a782220c2dfb94ae8820` | `8ad11ea36f2dda39ae30d57daa53316a8c0889a2f3c0c84ee5d79b9b21c25472` |
| 1.8.3 | `4da43d06c8c1605a9a3ba1bf22893245cf1b108eb5c8fed859012252370c2c06` | `e0ad2917dcc3a91586fb6f9b1bde78e55f1ece908572672885395e0d045dd9a2` | `db7a3bc38e53ffa3c0b71ae69435f9187c32480617b52721584e3ab4a2c4eda2` | `413ad8fdcf8f29f4f1c2b4425aa0dade00f75dd4` / `4afafb220ce18063d3aec5f8bab178a5192d84ca2d63b358fee7d369aea96938` | `7da5f772d7ed1433cb3da23861723ebaec7cb714ce49c4d1a5c30499fec3afed` |
| 1.8.4 | `d68a3aa5271abece4ce2b7048f6caf4f58071476344d9a52fe9ab1410c5d458b` | `8ee5582ac3f6581e85ac27de5c9df9427a4bf67d0652531cf2e94eac4dde9b8c` | `33b3ffd33108795da1723d0f3d9c433c4e03e75daa156c71f99e7b2829aabe4d` | `043b84efde9fc25d849e979329c03101ac9b7795` / `c7220eabc36645636c8597b167d9ca67d16580611ed2d7d8cd7a3c0aec904587` | `b2207bf9ab2a7561bf73ac92a63cc67760ccc6fd9e2378ff1e10710ecbb03a00` |

The three provenance inventories each contain 1,612 Java files. A full inventory/hash check against each provenance `sources` map found 1,612 present, zero missing, zero mismatched, and zero extra files per release. All three use the same recorded decompile pipeline: `lpc-decompile-v1;threads=1;generic=true;ascii=true;synthetic=remove;indent=4;inner-warnings=false;runtime=false;prefix=net/minecraft,com/mojang;remap=ignoreConflicts,renameInvalidLocals,rebuildSourceFilenames,fixPackageAccess`.

## Exact source comparison

The following relevant files are byte-identical across 1.8.2, 1.8.3, and 1.8.4 (SHA-256):

| File | SHA-256 |
|---|---|
| `net/minecraft/SharedConstants.java` | `f31678e2028c1223b90aea2b5a7cff037c79597413cc2765c10c41f0bb5549e5` |
| `net/minecraft/entity/living/attribute/EntityAttributes.java` | `945f39931934b378359d428a2f0fb0b57c97bb2e162dcec7e5d8562efaf663aa` |
| `net/minecraft/entity/living/attribute/RangedEntityAttribute.java` | `907be80906539d0a2323ffc7d37f6c8e19498bcb2671b23395f342c2c57d5b03` |
| `net/minecraft/entity/living/attribute/BaseEntityAttribute.java` | `65aa76932a1c44aa4118845edbcbbd0fab82970df3600dcf698c400f60ed604e` |
| `net/minecraft/entity/living/attribute/EntityAttribute.java` | `57951a8b8fe967284d20db1017892c4b37d849299263ed5a0ab5098e4894aa58` |
| `net/minecraft/entity/living/attribute/EntityAttributeInstance.java` | `358d9b031fe281ab7837d3ae70d46654b0704db1e8c199234ba0bfd68a19692b` |
| `net/minecraft/entity/living/attribute/ModifiableEntityAttributeInstance.java` | `3a8ddba11c80ee90640a1b0b85fd498d5e557459ed5b8bb64ea6a30ab7a0157d` |
| `net/minecraft/entity/living/attribute/AbstractEntityAttributeContainer.java` | `b1576cdc11ee7715080bfdd205601bf40f884af9efa8f5dc0eae07233c43786f` |
| `net/minecraft/entity/living/attribute/EntityAttributeContainer.java` | `63eac4b5c49e216a7bef6d03da42eba9737c6e2d215acbb6ee27c11dde89a506` |
| `net/minecraft/entity/living/LivingEntity.java` | `082831c6578e3a70fa6cea5b90bc3eefc26678259b66334470de22b90b5b0e4e` |
| `net/minecraft/entity/living/player/PlayerEntity.java` | `e66cb294fc93118148a444bbafdf4dd57cbf66a23d69b1e8892cefccc690ab88` |

In these sources, `EntityAttributes.MOVEMENT_SPEED` is a `RangedEntityAttribute` with default `0.7F`, minimum `0.0`, and maximum `Double.MAX_VALUE` (`EntityAttributes.java:18`). `RangedEntityAttribute.clamp` applies the attribute's configured min/max (`RangedEntityAttribute.java:36–39`). The cached attribute calculation applies operation 0 additions, operation 1 additions relative to the operation-0 result, then operation 2 multipliers, and finally clamps using that ranged attribute (`ModifiableEntityAttributeInstance.java:128–155`). Therefore this version family has no 1024 upper bound on the computed movement-speed attribute.

`LivingEntity.initAttributes` registers `MOVEMENT_SPEED` (`LivingEntity.java:144–148`); `PlayerEntity.initAttributes` sets a player's base to `0.1F` (`PlayerEntity.java:131–135`). `PlayerEntity.getSpeed` casts the computed attribute value to `float` (`PlayerEntity.java:1298–1300`). Ordinary grounded land movement consumes `getSpeed()` multiplied by `0.16277136F/(friction^3)` in `LivingEntity.moveRelative` when locally controlled and not in water/lava; the airborne branch instead uses `speedInAir` (`LivingEntity.java:1119–1140`). Player flight with no vehicle substitutes ability flight speed around the superclass call (`PlayerEntity.moveRelative`, `1279–1291`), so that path does not consume the ordinary movement attribute in the same way. In water, Depth Strider is capped at level 3 and halved off-ground; its acceleration interpolates toward `getSpeed()` (`LivingEntity.java:1199–1218`). `EnchantmentHelper.getDepthStriderLevel` has the same method body in all three releases.

## Bounded conclusion

The exact 1.8.2, 1.8.3, and 1.8.4 mapped sources share the no-1024-cap movement-speed attribute calculation and the same inspected ordinary land/water movement sinks. The 1.8.4 boundary cannot be applied to the whole current 1.8 profile alias based on this evidence: the exact 1.8.0 and 1.8.1 mapped sources are not present with bound provenance in the canonical source tree, and their prior cached mapped outputs lack the required output provenance. Their behavior remains unresolved. No source generation or cache repair was attempted.


