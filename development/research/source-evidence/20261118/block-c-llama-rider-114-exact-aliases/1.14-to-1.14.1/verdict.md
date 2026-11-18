# Independent source audit: Llama passenger Y placement

## Verdict

**Accept the bounded no-difference claim** for ordinary adult `LlamaEntity` and inherited `TraderLlamaEntity` placing a direct local `PlayerEntity` passenger in exact Minecraft 1.14 → 1.14.1 Yarn sources. The callback, guard, Y operands, numeric types and order match. This verdict covers only the stated source operation; it does not assert runtime parity or a patch boundary outside this pair.

## Input binding

Audited frozen input:

- `C:/Users/Wolfi/.codex/visualizations/2026/11/17/01a1eb7b-b9ff-7a20-8686-0295dde20f26/c-llama-114-1141-position-intermediate/report.md`
- SHA-256: `d1b9602824d2666c733b498f0ea552cf6e572473748b85c44ad6abfb7a9e3d13`

## Exact evidence identity

The canonical local Yarn trees identify the requested exact releases and mapping builds in their hidden provenance files:

| Release | Provenance SHA-256 | Exact mapping input (from provenance) | Mapping artifact SHA-256 | Release metadata SHA-256 |
|---|---|---|---|---|
| 1.14 | `ea70a955a12f377cd3b9e8d5f346f4625b044d55da726b6bc472405ae91f4341` | Yarn `1.14+build.21` | `0233edadf36a28099f6bb86e920c423b728ab5d75618da9046d92d287f12de64` | `3d05fc911c8559105767713365ea1c29bbbeccf57214f286c86a598ce51f22a6` |
| 1.14.1 | `308537c5adec3104c5ca243eb5010554f00978635ed74f0d7801c804dd4d4907` | Yarn `1.14.1+build.10` | `f9462af25135abeb22cbdec829cc8618b1a8dd902f17259abfbe7399a222a630` | `71c39840d012fcbe8fedb7be876bd008365b62ec7b1c9c5c7bd34d9a29c8ca22` |

For each source below, the recomputed local SHA-256 equals that version tree's `.decompile-provenance.json` `sources` entry:

| Yarn source, relative to `decompiled_minecraft/<version>/yarn/` | 1.14 SHA-256 | 1.14.1 SHA-256 |
|---|---|---|
| `net/minecraft/entity/Entity.java` | `f52483d5e1434114b81b1c353e89f246d1557bda7a95400832f5650b89cc744a` | `ce429699f2d8f0a54f4be516783a474c6c7808572950164508ceed0baf68ed5b` |
| `net/minecraft/entity/passive/LlamaEntity.java` | `b9d8391860e33f3c97d75eddaa6519f1c614fba81d77a3baa9f9392fd8ecb998` | `b9d8391860e33f3c97d75eddaa6519f1c614fba81d77a3baa9f9392fd8ecb998` |
| `net/minecraft/entity/passive/TraderLlamaEntity.java` | `985323d40a7f35d80cc0f741c747e007c7a07e56e1332d9d1d4dfdb10b466ac5` | `998fb0e745c3656115fbaa46f9febb59eb651ec6947d66b3bfb95872163308a5` |
| `net/minecraft/entity/passive/HorseBaseEntity.java` | `317ec0256a38c0f35b3dd20e8a03b17a7819bb903ad41c77f95cde6e78e47387` | `317ec0256a38c0f35b3dd20e8a03b17a7819bb903ad41c77f95cde6e78e47387` |
| `net/minecraft/entity/player/PlayerEntity.java` | `ebda4ad25a265f8c85e88345efcce753b78e248a29a9b910c5cfd311a4a69319` | `ebda4ad25a265f8c85e88345efcce753b78e248a29a9b910c5cfd311a4a69319` |

## Independent trace and comparison

All references below are to the Yarn source paths in the evidence table, with the concrete version directory prefixed.

- In both `1.14/yarn/net/minecraft/entity/player/PlayerEntity.java:437–447` and `1.14.1/yarn/net/minecraft/entity/player/PlayerEntity.java:437–447`, the ordinary client `tickRiding` path calls `super.tickRiding()` (the server-only sneaking dismount guard does not apply to a local client player).
- In both `1.14/yarn/net/minecraft/entity/Entity.java:1522–1527` and `1.14.1/yarn/net/minecraft/entity/Entity.java:1522–1527`, `Entity.tickRiding` runs `tick()` and, if `hasVehicle()`, invokes `this.getVehicle().updatePassengerPosition(this)`. This is virtual dispatch to the concrete vehicle callback.
- In both `1.14/yarn/net/minecraft/entity/passive/LlamaEntity.java:147–159` and `1.14.1/yarn/net/minecraft/entity/passive/LlamaEntity.java:147–159`, `LlamaEntity.updatePassengerPosition` first checks `this.hasPassenger(arg)` and then calls one `arg.setPosition`. Its Y argument is exactly `this.y + this.getMountedHeightOffset() + arg.getHeightOffset()` in both files. The Llama files are byte-identical by their matching full-file SHA-256.
- In both Llama files, `getMountedHeightOffset` returns `this.getHeight() * 0.67`. `getHeight()` is a `float`; unsuffixed `0.67` is a `double`, so Java promotes the height to double for multiplication. The player's `getHeightOffset` in `PlayerEntity.java:951–954` returns double `-0.35` in both versions. The Y evaluation therefore uses the same left-associative double operations: mount Y plus double mounted-height result plus double player offset. No cast or rounding step differs.
- `Entity.hasPassenger` in both `Entity.java:2355–2363` uses the same passenger-list membership check. `Entity.updatePassengerPosition` (`Entity.java:1530–1534`) is the unchanged guarded vanilla fallback, but the Llama override does not call it.
- `HorseBaseEntity.updatePassengerPosition` (`HorseBaseEntity.java:914–930`) is byte-identical in this pair. Llama's override replaces that inherited callback and does not delegate to it, so HorseBase's later angry-animation placement is not a second Llama Y placement.
- `TraderLlamaEntity.java:20–25` shows `TraderLlamaEntity extends LlamaEntity`; the class has no `updatePassengerPosition` override in either file. A Trader Llama therefore inherits the same Llama callback and mounted-height method.

The only direct Llama passenger position write has the same `hasPassenger` gate and Y expression in the exact pair. Adult dimension values need not be assumed: the method consumes the entity's existing `getHeight()` result identically on both sides. Other carriers, actor movement, mount acquisition, vehicle physics and runtime behavior remain outside this verdict.
