# Independent source audit: 1.8.2–1.8.4 movement-speed cap boundary

## Bound input and verdict

Audited the final report `.native-source/20261028/1818-speed-boundary/REPORT.md`, SHA-256 `3ab8af23b8f8bd76d3e7c0e0d15d288f427916b8778d696bba2c1b6beedfe411`. The adjacent `REPORT.md.sha256` sidecar contains the same digest. The claim is bounded to exact local `ornithe-feather` Gen2 sources for 1.8.2, 1.8.3, and 1.8.4, and explicitly leaves 1.8.0/.1 unresolved.

**Verdict: ACCEPT with boundary limit.** The listed exact source bodies and full Java inventories independently match. All three releases register `generic.movementSpeed` with maximum `Double.MAX_VALUE`, and the inspected player land/Depth-Strider movement sinks use `PlayerEntity.getSpeed()`. The report correctly does not extend this result to the whole existing V1_8 profile group because canonical 1.8.0 and 1.8.1 source trees are unavailable/unbound.

## Identity and integrity

The three canonical trees are under `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\{1.8.2,1.8.3,1.8.4}\ornithe-feather`. Their `.decompile-provenance.json` SHA-256 values independently match the report: 1.8.2 `8ad11ea36f2dda39ae30d57daa53316a8c0889a2f3c0c84ee5d79b9b21c25472`, 1.8.3 `7da5f772d7ed1433cb3da23861723ebaec7cb714ce49c4d1a5c30499fec3afed`, and 1.8.4 `b2207bf9ab2a7561bf73ac92a63cc67760ccc6fd9e2378ff1e10710ecbb03a00`.

Each provenance input map binds the exact client JAR, `feather-gen2` merged-v2 artifact at `1.8.x+build.2`, Ornithe Feather version metadata URL, and Mojang version JSON/package hash. For example, 1.8.4 binds client `d68a3aa5…c5d458b`, mapped artifact `8ee5582a…dde9b8c`, metadata URL `meta.ornithemc.net/v3/versions/feather/1.8.4` hash `33b3ffd3…9aabe4d`, and package ID `043b84efde9fc25d849e979329c03101ac9b7795` with JSON hash `c7220eab…904587`. Corresponding .2 and .3 pins match the report table. I verified these pins from the embedded provenance; I did not fetch or independently rehash remote metadata archives.

I recomputed all embedded source-map entries: each tree has 1,612 expected and 1,612 actual Java files, with zero missing, mismatched, or extra files. For all 11 reported core files—`SharedConstants`, `EntityAttributes`, `RangedEntityAttribute`, `BaseEntityAttribute`, `EntityAttribute`, `EntityAttributeInstance`, `ModifiableEntityAttributeInstance`, `AbstractEntityAttributeContainer`, `EntityAttributeContainer`, `LivingEntity`, and `PlayerEntity`—the exact hashes match the report and are byte-identical across all three releases. `EnchantmentHelper.java` differs as a whole file between .2 and .3/.4, but the cited `getDepthStriderLevel(Entity)` method body is identical in all three (returns the highest Depth Strider level from equipped items).

## Claim verification

- `EntityAttributes.MOVEMENT_SPEED` at `EntityAttributes.java:18` is `new RangedEntityAttribute(..., 0.7F, 0.0, Double.MAX_VALUE)` in each release.
- `RangedEntityAttribute.clamp` applies the attribute instance's configured min/max (`RangedEntityAttribute.java:36–39`); there is no 1024 movement-speed cap in these exact sources.
- `ModifiableEntityAttributeInstance.computeValue` applies operation 0 additions, operation 1 additions relative to the operation-0 result, operation 2 multipliers, then clamps (`ModifiableEntityAttributeInstance.java:128–155`). The exact file hash matches across .2–.4.
- `PlayerEntity.initAttributes` sets movement-speed base to `0.1F`; `PlayerEntity.getSpeed()` casts the computed attribute value to float (`PlayerEntity.java:131–135, 1298–1300`).
- Grounded land movement in `LivingEntity.moveRelative` uses `getSpeed() * 0.16277136F / (friction^3)` only in the locally controlled, on-ground, non-water/lava branch; airborne movement uses `speedInAir`. Player flight has a distinct ability-speed substitution.
- Depth Strider's water acceleration interpolates toward `getSpeed()` after capping the level at 3 and halving it when airborne (`LivingEntity.java:1199–1218`). The method obtains the level through the identical `EnchantmentHelper.getDepthStriderLevel` body.
- A source-wide scan of `getSpeed()` call sites also found AI/guardian/horse consumers, which are non-player or excluded mechanics; the report's player movement sinks are the grounded land and Depth-Strider water branches.

## Applicability and limits

The exact 1.8.0 canonical source directory is absent; the 1.8.1 canonical directory has no mapped source files or bound output provenance. The prior cached mapped outputs were not used as evidence. Therefore the report's unresolved 1.8.0/.1 applicability is correct. The .2–.4 evidence alone cannot safely authorize a provider across a V1_8 alias that includes .0/.1; it also does not establish map-specific values above the cap, runtime parity, or behavior for intermediate patches not inspected here.

No source generation, cache mutation, network fetch, product inspection, build, test, or runtime execution was performed.
