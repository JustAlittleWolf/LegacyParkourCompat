# C llama passenger Y placement: 1.14 → 1.14.1

## Result

Bounded no-difference for ordinary adult direct Player passenger placement on `LlamaEntity` and inherited `TraderLlamaEntity`. Both exact releases use canonical Yarn sources (`1.14` and `1.14.1`), not Mojmap: these are the only available source namespaces in the canonical adjacent directories. The requested operation is directly source-compared in this exact Yarn pair; no mapping alias or different patch release was substituted.

## Exact source identity

- `decompiled_minecraft/1.14/yarn/.decompile-provenance.json` SHA-256: `ea70a955a12f377cd3b9e8d5f346f4625b044d55da726b6bc472405ae91f4341`. It pins the exact `/1.14.json` release metadata (SHA-256 `3d05fc911c8559105767713365ea1c29bbbeccf57214f286c86a598ce51f22a6`) and Yarn artifact `1.14+build.21` (SHA-256 `0233edadf36a28099f6bb86e920c423b728ab5d75618da9046d92d287f12de64`).
- `decompiled_minecraft/1.14.1/yarn/.decompile-provenance.json` SHA-256: `308537c5adec3104c5ca243eb5010554f00978635ed74f0d7801c804dd4d4907`. It pins exact `/1.14.1.json` release metadata (SHA-256 `71c39840d012fcbe8fedb7be876bd008365b62ec7b1c9c5c7bd34d9a29c8ca22`) and Yarn artifact `1.14.1+build.10` (SHA-256 `f9462af25135abeb22cbdec829cc8618b1a8dd902f17259abfbe7399a222a630`).
- Each source hash below was recomputed from the local file and matches that release tree's hidden provenance `sources` entry.

| Release / mapping | Source reference | SHA-256 |
|---|---|---|
| 1.14 Yarn | `net/minecraft/entity/Entity.java` | `f52483d5e1434114b81b1c353e89f246d1557bda7a95400832f5650b89cc744a` |
| 1.14 Yarn | `net/minecraft/entity/passive/LlamaEntity.java` | `b9d8391860e33f3c97d75eddaa6519f1c614fba81d77a3baa9f9392fd8ecb998` |
| 1.14 Yarn | `net/minecraft/entity/passive/TraderLlamaEntity.java` | `985323d40a7f35d80cc0f741c747e007c7a07e56e1332d9d1d4dfdb10b466ac5` |
| 1.14 Yarn | `net/minecraft/entity/passive/HorseBaseEntity.java` | `317ec0256a38c0f35b3dd20e8a03b17a7819bb903ad41c77f95cde6e78e47387` |
| 1.14 Yarn | `net/minecraft/entity/player/PlayerEntity.java` | `ebda4ad25a265f8c85e88345efcce753b78e248a29a9b910c5cfd311a4a69319` |
| 1.14.1 Yarn | `net/minecraft/entity/Entity.java` | `ce429699f2d8f0a54f4be516783a474c6c7808572950164508ceed0baf68ed5b` |
| 1.14.1 Yarn | `net/minecraft/entity/passive/LlamaEntity.java` | `b9d8391860e33f3c97d75eddaa6519f1c614fba81d77a3baa9f9392fd8ecb998` |
| 1.14.1 Yarn | `net/minecraft/entity/passive/TraderLlamaEntity.java` | `998fb0e745c3656115fbaa46f9febb59eb651ec6947d66b3bfb95872163308a5` |
| 1.14.1 Yarn | `net/minecraft/entity/passive/HorseBaseEntity.java` | `317ec0256a38c0f35b3dd20e8a03b17a7819bb903ad41c77f95cde6e78e47387` |
| 1.14.1 Yarn | `net/minecraft/entity/player/PlayerEntity.java` | `ebda4ad25a265f8c85e88345efcce753b78e248a29a9b910c5cfd311a4a69319` |

## Compared behavior

The source pair's `Entity.tickRiding` invokes `getVehicle().updatePassengerPosition(this)` after the passenger tick when `hasVehicle()` is true (`Entity.java:1522–1527` in both releases). Dynamic dispatch for a llama goes to `LlamaEntity.updatePassengerPosition`, which has the same source bytes and same body in both releases (`LlamaEntity.java:147–159`; identical full-file SHA above). Its only placement guard is `this.hasPassenger(arg)`. For a direct Player passenger, the Y argument is the same expression in both versions:

`this.y + this.getMountedHeightOffset() + arg.getHeightOffset()`

`LlamaEntity.getMountedHeightOffset()` returns `this.getHeight() * 0.67` (`LlamaEntity.java:156–159`); `getHeight()` is `float`, and the unsuffixed `0.67` makes this multiplication double precision. `PlayerEntity.getHeightOffset()` returns `-0.35` as a `double` (`PlayerEntity.java:951–954`) in both versions. Thus the Y placement is `mountY + (double mount height × 0.67) - 0.35`, with the same left-to-right double additions and no rounding/cast change. The existing size value supplies `getHeight()`; this result does not assume a numeric adult dimension.

The inherited vanilla parent method `Entity.updatePassengerPosition` is unchanged in behavior and guard in both versions (`Entity.java:1530–1534`): it checks `hasPassenger` and places at vehicle x/z and `y + getMountedHeightOffset() + passenger.getHeightOffset()`. `Entity.hasPassenger` is also identical (`Entity.java:2355–2363`): it iterates `getPassengerList()`, returns true when a listed entity equals the argument, and otherwise returns false. The llama override does not call `super`; it adds yaw-relative X/Z placement but keeps the same `hasPassenger` gate and Y expression. The intermediate `HorseBaseEntity.updatePassengerPosition` also has identical source bytes across the pair (`HorseBaseEntity.java:914–930`); it calls the Entity parent and conditionally applies angry-animation position shifts, but Llama's override replaces that callback and does not delegate to it.

`TraderLlamaEntity` extends `LlamaEntity` and does not override `updatePassengerPosition` in either release (`TraderLlamaEntity.java:20–25`). Therefore default TraderLlama inherits the same callback, guard, mount-height formula, and Player height offset. No difference relevant to this bounded Y-placement operation was found.

## Scope and disposition

Examined: exact `tickRiding` dispatch; Entity passenger membership guard and vanilla fallback; HorseBase intermediate callback; Llama callback and mounted-height method; Player height offset; TraderLlama inheritance. All have paired source identities above. Finding: no difference in the bounded operation between exact 1.14 and 1.14.1.

This says nothing about other carriers, vehicle or non-player physics, or whether/how a player becomes mounted. It is source coverage only and does not establish runtime parity or a boundary before/after these releases.