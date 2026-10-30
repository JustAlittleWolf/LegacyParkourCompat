# Old 1.12 water-operation alias closure

## Scope and verdict

Read-only alias/applicability audit of the specified operations in the canonical `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\{1.12,1.12.1,1.12.2}\ornithe-feather` sources. The comparison covers the water-jump gate and impulse, grounded acceleration literal, existing-water drag/gravity branch, water-contact box and boolean producer, liquid-current aggregation, and `LiquidBlock` current provider.

**Verdict:** all requested operation bodies are exact aliases across 1.12, 1.12.1, and 1.12.2. No relevant source-body delta or cutover occurs within these three releases. Therefore the 1.12.2 endpoint behavior for these operations was already present in 1.12 (earliest inspected release); this audit does not locate any earlier introduction. `World.java` as a whole differs across releases, but the extracted `applyLiquidDrag` body is byte-for-byte equal after UTF-8 extraction and has the same method-body SHA-256 in all three releases.

The comparison establishes source alias applicability only. It is not pair freeze, full source coverage, acceptance, implementation, or runtime parity.

## Evidence identity

All three trees are exact-release Feather Gen2 build 2 sources in the `ornithe-feather` namespace. For 1.12 and 1.12.1, the endpoint parent ready/manifest files are absent; identity is established from each tree's embedded `.decompile-provenance.json` and the directly computed source SHA-256 values, which match the corresponding embedded `sources` entries. These embedded provenance records identify the source client input and exact Feather Gen2 mapping JAR. For 1.12.2, parent `ornithe-feather.ready.json` identifies version 1.12.2 and its Feather Gen2 build 2 merged JAR and tiny mapping; all directly computed relevant source hashes match `ornithe-feather.sources.sha256` rows, and the ready marker records that manifest digest.

Provenance file hashes and mapping/client artifact identities are in `source-hashes.txt`. No source was declared absent because an endpoint parent ready file was missing.

## Alias findings

Line numbers below are stable across these releases for the cited method locations. Where a full source file was not identical, equality was checked on the complete extracted method body, not inferred from matching line snippets.

1. **Water-jump gate and impulse — exact alias.** `LivingEntity.mobTick()` routes `jumping` to `jumpInWater()` when `isInWater()` is true; otherwise it checks lava, then only executes ordinary jump when `onGround && jumpingCooldown == 0`, setting the cooldown to 10. `jumpInWater()` adds `0.04F` to vertical velocity. The enclosing order/gates and impulse are unchanged (`LivingEntity.java:1413-1415, 1827-1839`). The separate tall `isSubmergedInWater()`/water-state query is not this contact gate.

2. **Grounded acceleration literal — exact alias.** `moveRelative()` uses `0.16277136F / (s * s * s)`; on ground it multiplies by `getSpeed()`, while airborne it uses `speedInAir` (`LivingEntity.java:1479-1493`). No 1.12.x cutover to `0.16277137F` is present.

3. **Default existing-water drag/gravity — exact alias.** In the water branch, Depth Strider is capped at 3 and halved off ground; friction/input scales are interpolated; input velocity is updated and moved; horizontal velocity is multiplied by the interpolated friction; vertical velocity is multiplied by `0.8F`; then, when gravity is enabled (`!isNoGravity()`), `0.02` is subtracted. The post-move order and gates match exactly (`LivingEntity.java:1555-1580`).

4. **Entity water-contact box and boolean producer — exact alias.** `checkWaterCollisions()` first special-cases boat mounts by setting `inWater=false`; otherwise it queries `applyLiquidDrag(getShape().grown(0.0, -0.4F, 0.0).contract(0.001), WATER, this)`. A positive result triggers transition splash logic, resets fall distance, sets `inWater=true`, and extinguishes; a negative result clears `inWater`; the method returns `inWater` (`Entity.java:948-964`). `Box.grown` subtracts `dy` from `minY` and adds it to `maxY`, so `dy=-0.4F` contracts both vertical faces by 0.4 before the 0.001 contract (`Box.java:132-140`). This is distinct from the separate `getShape().grown(0.0, -20.0, 0.0)` water-state query at `Entity.java:945`.

5. **World liquid-current aggregation — exact alias.** `applyLiquidDrag()` floors/ceils box bounds, returns false when the area is not loaded, scans X then Y then Z, accepts cells whose material matches and whose computed liquid surface is reached by `ceil(bounds.maxY)`, sets the boolean contact result, and aggregates `block.applyMaterialDrag(...)`. After releasing the pooled position, it applies current only when the aggregate is nonzero and `entity.hasLiquidCollision()` is true; it normalizes the sum and adds `0.014` times each component to velocity. It returns the contact boolean regardless of the current-application gate (`World.java:1579-1621`). This full method body is identical in all three versions despite unrelated changes elsewhere in `World.java`.

6. **LiquidBlock provider — exact alias.** `getFlow()` accumulates horizontal neighbor liquid-depth differences, includes the below-neighbor fallback when an adjacent cell is nonblocking and has liquid below, applies the level-8 downward-flow/solid-face special case, releases the pooled position, and returns a normalized vector. `applyMaterialDrag()` adds that provider vector to the accumulated current (`LiquidBlock.java:139-185`). Both method bodies are identical in all three versions.

## Method-body equality evidence

SHA-256 values below cover the complete brace-delimited method body (opening `{` through closing `}`), with UTF-8 encoding. The method signature is shown in the table. Every listed body hash matches for 1.12, 1.12.1, and 1.12.2.

| Complete method | Body SHA-256 (each release) |
|---|---|
| `LivingEntity.jumpInWater()` | `45ada6f40c31397692fead02e195b20d9e5c737c9ab27caa6d01a48042e4cfa0` |
| `LivingEntity.moveRelative(float,float,float)` | `82b08ec68591b107e60c32b128359b6275de4531df3c1dd8deb8b8759d36d209` |
| `LivingEntity.mobTick()` | `34b8d985ba8a6df3b3b11059246d03eeaa7f955a35f75b497419c42fe129832` |
| `Entity.checkWaterCollisions()` | `0ebe4f13e1f3f49a4f29072918ef82aa85a656c7610be8077306145a944f4669` |
| `World.applyLiquidDrag(Box,Material,Entity)` | `55239dba546004396895e3d5b5667a4fc051318bd2feb23d8ec6c5a36c8394b3` |
| `LiquidBlock.getFlow(WorldView,BlockPos,BlockState)` | `41acbbf61dcfbc4f0a8cfd33f54963dbbad67d92db8ba0d6ab7ce3d298594eb7` |
| `LiquidBlock.applyMaterialDrag(World,BlockPos,Entity,Vec3d)` | `5fd971ddd1196df352e27e9953a673ae4e135786764165db05323e5ef25d68c3` |

## Boundary

This closes only old-release alias applicability for the named operations. It does not re-review 1.12.2 to 1.13.2, establish the exact 1.13 cutover patch, inspect omitted releases before 1.12, duplicate the collision/current lane, or freeze the complete release pair. No product/history/wiki/backlog context, source generation, code changes, tests/builds, network, or game/runtime launches were used.
