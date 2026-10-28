# Independent blind source audit: liquid-flow integration

Verdict: ACCEPT — the exact-version published source evidence shows a reachable ordinary-player boundary case where 1.8.9 can include an additional touching liquid cell in its flow accumulator, while 1.9.4 excludes that cell. It changes velocity when that cell contributes nonzero flow and the player passes `hasLiquidCollision()`; it is not a universal outcome for every boundary arrangement.

## Scope and method

Compared only these canonical source trees:
- `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/1.8.9/ornithe-feather`
- `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/1.9.4/ornithe-feather`

Read the hidden `ornithe-feather.ready.json`, `.provenance.json`, `.verification.json`, `ornithe-feather.sources.sha256`, and `artifacts.sha256` for each tree, plus the literal `World.java`, `LiquidBlock.java`, `Entity.java`, `LocalClientPlayerEntity.java`, and `PlayerEntity.java` source paths. Recomputed SHA-256 for each source manifest and the five cited files per release. Source-manifest hashes match their ready markers; each cited file hash matches its source manifest. Ready markers identify exact versions 1.8.9 and 1.9.4, `ornithe-feather`, Feather Gen2 build 2 merged mappings, and Vineflower. Verification records say 1,612 / 1,819 source files verified with zero differences against a second decompile; their stated limit is a regenerated derived remapped jar, while raw inputs and published source files matched.

## Provenance and cache availability

- 1.8.9 source manifest SHA-256: `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004`; ready marker agrees. Provenance records the exact batch `1.8.9,1.9.4`, Feather option, Java 25.0.3, Gradle 9.7.1, Vineflower 1.12.0 and mapping artifact names `feather-gen2-1.8.9+build.2-mergedv2.jar` / `.tiny`.
- 1.9.4 source manifest SHA-256: `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19`; ready marker agrees. Provenance records the same exact batch/toolchain and mapping artifact names for 1.9.4.
- The pinned cache root is `D:/Javastuff/LegacyParkourCompat/.minecraft-decompile-cache`. It contains neither `.minecraft-decompile-cache/1.8.9` nor `/1.9.4`; no `mapping-metadata/ornithe-feather-1.8.9.json` or `...1.9.4.json` exists, and neither version's named Feather mapping artifacts occur in the cache. Its 1.9-series version directories stop at 1.9.3. The audit did not substitute those adjacent releases. Published source/provenance is therefore hash-verified, but the requested pinned-cache copies of both exact releases are missing.
- Each ready marker records 37 artifacts. The immutable artifact manifests list the exact game clients, mapped client jars and Feather mappings; verification records one derived mapped-jar difference per release, explicitly explained as a regenerated intermediate. No claim is made that those absent exact-version cache artifacts were independently re-read.

## Source evidence and behavior

### Iteration bounds and gates

`World.applyLiquidDrag` in 1.8.9 (`World.java:1486–1527`) computes `i/k/m = floor(minX/minY/minZ)` and `j/l/n = floor(maxX/maxY/maxZ + 1.0)`, then iterates each coordinate `<` its corresponding upper bound (`1501–1515`). 1.9.4 (`World.java:1560–1602`) computes lower bounds with floor and upper bounds with `ceil(maxX/maxY/maxZ)`, iterating `<` those values (`1575–1589`). For a non-integral maximum these upper bounds agree. For a maximum exactly equal to integer `N`, 1.8.9's `floor(N+1)` is `N+1`, so it visits coordinate `N`; 1.9.4's `ceil(N)` is `N`, so it does not.

Both versions first require the whole integer region loaded (`World.java:1493–1495`, `1567–1569`), filter cells by matching liquid material (`1507`, `1581`), and then apply the same vertical gate `l >= p + 1 - LiquidBlock.getHeightLoss(level)` (`1508–1512`, `1582–1586`). Because the vertical upper bound `l` also differs at an exact integral `maxY`, an exact top-face cell can pass the older gate while being absent from the newer loop. Exact integral `maxX` or `maxZ` similarly adds a touching side layer when that cell passes the shared vertical gate.

### Flow accumulation and velocity response

The accumulator starts at zero in both versions. Each accepted liquid cell calls `block.applyMaterialDrag`, and the cell contributions are summed before one final normalization (`World.java:1497–1512,1518–1524`; `1571–1586,1593–1599`). `LiquidBlock.applyMaterialDrag` adds that cell's flow vector (`LiquidBlock.java:163–166` for 1.8.9; `175–177` for 1.9.4). `getFlow` in both uses the same horizontal neighbor depth-difference terms, the same downward-neighbor adjustment for an unblocked side, and the same falling-liquid wall adjustment of `(0,-6,0)`, with normalization of each cell vector before return (`LiquidBlock.java:129–160` in 1.8.9; `131–171` in 1.9.4). The newer version changes representation (separate scalar accumulators, `Direction` offsets, and an explicit state argument), not the relevant flow formula.

After all visited cells, both require `vec3d.length() > 0` and `entity.hasLiquidCollision()`, normalize the total once, and add `0.014 * normalizedFlow` to X/Y/Z velocity (`World.java:1518–1524`, `1593–1599`). Therefore, if an exact-boundary extra cell has nonzero flow, it can change the total's direction and the ordinary player's velocity; the old bound may also set the liquid-contact return flag for a cell whose upper face is only touched. If the extra cell's flow is zero or player liquid collision is disabled, that particular extra cell causes no flow impulse.

### Direct ordinary local-player reachability; lava

In both releases the class chain is `LocalClientPlayerEntity extends ClientPlayerEntity extends PlayerEntity extends LivingEntity extends Entity` (`LocalClientPlayerEntity.java:53/69`, `ClientPlayerEntity.java:20/21`, `PlayerEntity.java:79/92`, `LivingEntity.java:58/76`). Local-player `tick()` calls `super.tick()` when its chunk is loaded (`LocalClientPlayerEntity.java:104–108` / `143–145`); inherited `PlayerEntity.tick()` calls `super.tick()` (`PlayerEntity.java:228` / `200`), and `LivingEntity.tick()` calls `super.tick()` (`LivingEntity.java:1258–1260` / `1488–1490`). That reaches `Entity.tick()` and `Entity.baseTick()`, which calls `checkWaterCollisions()` directly (`Entity.java:245–247,249–301` / `299–305,307–361`). The water check calls `applyLiquidDrag` with `Material.WATER` and the player shape grown down 0.4 and contracted 0.001 (`Entity.java:746–747` / `849–853`). Both PlayerEntity implementations return `!abilities.flying` from `hasLiquidCollision()` (`PlayerEntity.java:1617–1619` / `1717–1719`), making the non-flying case reachable for ordinary local movement.

No `applyLiquidDrag(..., Material.LAVA, ...)` call exists in either canonical source tree. The direct base-entity movement caller supplies water only. This audit therefore finds no corresponding ordinary-player lava-flow impulse; lava is not inferred from the generic method's ability to accept a `Material`.

## Hashes for cited source files

Hashes below are SHA-256 and match the per-release `ornithe-feather.sources.sha256` manifest:

| Source path under `ornithe-feather/` | 1.8.9 | 1.9.4 |
|---|---|---|
| `net/minecraft/world/World.java` | `3c04f5b874fbb6692039164c882a3c47fe28c7eca864e91c9ac57798153888ee` | `2fe063e0ec224eed9fc7d01b8f788c32035eed5fee5a9d98ea5e82296236e05a` |
| `net/minecraft/block/LiquidBlock.java` | `1537fd6b1bca65e0f910f9a12bcd3bb27f5a86be7f13a1caa380c5e66544f1d5` | `b988d19e379d01d291cb014cb05e1579311c589654ce9ff4faa7f43bbba0533e` |
| `net/minecraft/entity/Entity.java` | `d4c10932cb5bb1067a5a58be4e1bb1b42bdde3b6fc893d88178cc07435a1696b` | `bcd7fa2206bf8d7271102f6da7fe2771f96dbe22ec79dab9f2101a3e29c17ef0` |
| `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `1762b116e6b06d682b7daaa0fc8cce39b0ff455b3db8cf79dab03ac74f6c4053` | `8aaf711948b7602c2e6c015a37e36ed06073d39727d999d80480b4910b704f5d` |
| `net/minecraft/entity/living/player/PlayerEntity.java` | `e66cb294fc93118148a444bbafdf4dd57cbf66a23d69b1e8892cefccc690ab88` | `d658a0d95452d12bb7e347bfd802240eeecaf7f938e10dcd43e2640434387f85` |
