# Water contact shape and player push gate: focused correction

## Bound inputs

This supplement is limited to the pinned raw client JARs and Tiny mappings in `180181-water-raw-bytecode/REPORT.md` (report SHA-256 `3c43839c0e9e91efe6a4ec60074e17c78aff0bed43bf7f234724704cbd2d5eca`). The local `client.jar` hashes match its pins: 1.8 `ccccd0a92f76fbb24279691daad9b67f85d80471a37e59e0da76d7754982dfe8`; 1.8.1 `66a749ad95adcb929439155c0341309e89e437444867e108dcb36fd16493b0bd`. Tiny mappings bind 1.8 `wv.W()Z` and 1.8.1 `wx.W()Z` to `Entity.checkWaterCollisions()Z`; raw AABB classes are `brt` and `brr`. `World` is raw `aqu` / `aqr`; `PlayerEntity` is raw `ahd` / `aha` in the respective Tiny payloads. The `hasLiquidCollision()Z` mapping is `aK` / `aL` on the Entity and PlayerEntity method entries.

## Corrected shape transform

The report's wording “moves it down by the float constant `0.4000000059604645`” should be read as a correction target: the operation is **not a downward translation**. In both raw `Entity.checkWaterCollisions` methods, the call sequence is AABB `b(0.0, -0.4000000059604645, 0.0)` then AABB `d(0.001, 0.001, 0.001)`, followed by the water `World` call. On `brt` (1.8) and `brr` (1.8.1), `b(DDD)` returns bounds `min' = min - delta`, `max' = max + delta`; `d(DDD)` returns `min' = min + delta`, `max' = max - delta`. Therefore, for original bounds `(minX,minY,minZ,maxX,maxY,maxZ)`, the submitted query bounds in both versions are:

- `minX + 0.001`, `minY + 0.4010000059604645`, `minZ + 0.001`
- `maxX - 0.001`, `maxY - 0.4010000059604645`, `maxZ - 0.001`

This symmetrically narrows the vertical interval by `0.802000011920929` total and insets X/Z by `0.001` per side. It does not translate the box or preserve its height while shifting it. The operation order above is established by the raw `javap -p -c -s` bodies; the Tiny bindings identify the raw AABB methods as the queried Box operations.

## Player water-push dispatch and flight gate

In raw 1.8, `PlayerEntity` `ahd.aK()Z` reads its abilities' `flying` boolean and returns its negation. In raw 1.8.1, `PlayerEntity` `aha.aL()Z` does the same. The corresponding `World.applyLiquidDrag(Box,Material,Entity)Z` bodies invoke the Entity virtual method on their `Entity` argument (`invokevirtual wv.aK:()Z` / `invokevirtual wx.aL:()Z`) after checking whether accumulated flow is nonzero. For a local player, ordinary inheritance from PlayerEntity therefore dispatches to the PlayerEntity override, not Entity's base `true` implementation. In both versions the water-current velocity push is enabled for non-flying players and suppressed while `abilities.flying` is true.

The accepted exact 1.8.9 `PlayerEntity` source independently records the same override: `hasLiquidCollision()` returns `!abilities.flying` (`PlayerEntity.java:1617–1619`; source SHA-256 `e66cb294fc93118148a444bbafdf4dd57cbf66a23d69b1e8892cefccc690ab88`, matching the provenance-bound source manifest cited by the accepted audit). Thus the raw 1.8/1.8.1 local-player water-push gate matches 1.8.9, including flight suppression. The raw Entity base method remains `true`; it is not the effective local-player result when virtual dispatch reaches the PlayerEntity override.

This supplement corrects the transform description and closes only the requested PlayerEntity virtual-dispatch/flight-gate detail. It does not modify the bound report or broaden its source/runtime claims.

Method used: read-only `javap -p -c -s` on the exact pinned raw client classes and Tiny entries. No class execution, mapped output, network, source/cache writes, build, test, game or runtime was used.
