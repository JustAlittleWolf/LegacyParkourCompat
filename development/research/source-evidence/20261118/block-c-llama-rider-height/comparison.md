# Local player passenger Y on llamas — source comparison

Status: **COMPLETED**

## Scope and source identity

Compared exact canonical decompilations for 1.17.1, 1.18, 1.18.1, and 1.18.2, Mojmap namespace. For every side, `mojmap.ready.json` says `status: ready`; the SHA-256 of `mojmap.sources.sha256` matches `sourceManifestSha256`, and the source-file hashes below match the entries in that manifest. I read the canonical sources and manifests read-only.

| Version | Source-manifest SHA-256 | Client SHA-256 | Provenance note |
|---|---|---|---|
| 1.17.1 | `93270d229acfb751bf56daf1e7be26ce3dcff26b29e94aa157de621405a3463b` | `a49b4a56c5bbe15c9ed9fe53efa9a591f265a1f5ba7d6aa9739a58ea7a92b79d` | `mojmap.provenance.json` identifies version/namespace and exact run; this older batch record has no per-version raw mapping/client artifact hashes. |
| 1.18 | `a43c61223ddedbdd8ede4d2daf6a750a58cbba324b30c025429ee1030cfc0d78` | `381e090ef22584526113bcccf26ae38a310a22094d14c2d0dec72b71fdf65aaa` | Version metadata SHA-256 `40e076b63d116d21b8eaf7f9d3e09651e4bbc5853cde47edc13b4d5ea0b6631b`; official client mappings SHA-256 `b4c7d514328050fa09ac04818d21e4be0b58e75c6b0847e56ce09f6637b8877b`. |
| 1.18.1 | `52aa98450b5cfa55ac2bd2054f108e16df7fa939a3f061991b6a9adb434d8d2d` | `737c28a56c8980fcd8574465c61538ebde8f511bdd035c69d0cbe27953f892aa` | Version metadata SHA-256 `b854990db7e1948661c0f0359e3b2dbf32610a7a61b4efa4d5471399bcf68fd1`; official client mappings SHA-256 `a7a4109255dfb74b9552b339482f0714bb0e7e7143d1059b0bf62a526d2c2a84`. |
| 1.18.2 | `aea0cb9c6fc8f7a46f0eb82b0388ad58a4659f513be6c0a2be06c0df0c1eb07a` | `1d09e3639644b6b2254499469d0765cc005a286d19f3fa595b0ed8fb07971ec7` | `mojmap.provenance.json` identifies version/namespace and exact run; this batch record has no per-version raw mapping/client artifact hashes. |

## Finding

**A llama places a player passenger lower from 1.17.1 to 1.18; this remains unchanged through 1.18.2.**

- 1.17.1 `net/minecraft/world/entity/animal/horse/Llama.java:154-165`: `positionRider` gates on `hasPassenger(entity)` and writes Y as `this.getY() + this.getPassengersRidingOffset() + entity.getMyRidingOffset()`. The override returns `this.getBbHeight() * 0.67`.
- 1.18, 1.18.1, 1.18.2 `net/minecraft/world/entity/animal/horse/Llama.java:156-167`: same passenger gate and position expression; the override instead returns `this.getBbHeight() * 0.6`.
- The enclosing position expression keeps left-to-right double additions. `getBbHeight()` is a `float` (`Entity.java`, 2861 in 1.17.1 / 2844 in 1.18; same definition in the compared 1.18 patch releases), promoted for multiplication by the unsuffixed double literal. Both factors are therefore double precision in the product; there is no new cast or rounding step in the Y expression.
- `EntityType.java` registers LLAMA and TRADER_LLAMA with unchanged dimensions `.sized(0.9F, 1.87F)` in all four releases. The llama override therefore uses the current `dimensions.height` through `getBbHeight`; adult/default height is 1.87F. `Player.java` returns `-0.35` from `getMyRidingOffset()` in all four releases.
- For an adult/default llama with a player passenger, Y is `llamaY + 1.87F * 0.67 - 0.35` in 1.17.1 and `llamaY + 1.87F * 0.6 - 0.35` from 1.18 onward. The latter is lower by about `0.1309` blocks (exact source path computes with the float dimension promoted to double, then double arithmetic). Any non-default current llama height scales this delta by that height relative to 1.87F.

## Client writer and reachable witness

`ClientLevel.tickNonPassenger` walks passengers through `tickPassenger`; `tickPassenger` removes stale links, and otherwise ticks a passenger when it is a `Player` or is in the client ticking set. It calls `rideTick()` and recurses. `LocalPlayer.rideTick()` delegates to `Player`/`LivingEntity`/`Entity`; `Entity.rideTick()` zeroes rider delta movement, calls `tick()`, then, if still a passenger, calls `getVehicle().positionRider(this)`. On a llama this dispatches to `Llama.positionRider`; its `hasPassenger` check gates the `setPos` writer. Thus a live local player passenger is directly repositioned client-side by this path each client entity tick. The same vanilla methods also exist in server source paths; this report covers the local logical-client writer only.

A normal adult Llama is a reachable witness: Llama inherits `AbstractChestedHorse.mobInteract`; its non-baby interaction path calls `doPlayerRide`, and `AbstractHorse.doPlayerRide` invokes `player.startRiding(this)` on the server. The client passenger tick above then exercises the placement writer after the riding relationship is synchronized. TraderLlama inherits the same Llama seat-height override; its `doPlayerRide` override can suppress mounting while it remains attached to its Wandering Trader, but does not replace the offset method.

## Coverage limits

This is an operation-scoped comparison of local-player Y placement on Llama and its TraderLlama subclass, not a full player tick inventory or a blanket comparison of every rideable entity. No conclusion is made for boats, minecarts, other horse families, non-player passengers, stand/vehicle physics, or intermediate releases between 1.17.1 and 1.18. The directly relevant shared hooks (`Entity.rideTick`, `Player.getMyRidingOffset`, client `tickPassenger`, and llama type dimensions) were checked across all four named releases; default `Entity.getPassengersRidingOffset()` and unrelated family-specific offsets are outside this assignment.


The reachable-witness gates are in `AbstractChestedHorse.mobInteract` (1.17.1: lines 148-193; 1.18+ same lines) and `AbstractHorse.doPlayerRide` (1.17.1 line 505; 1.18+ line 506). Their manifest-matching SHA-256 values are:

| Version | `AbstractChestedHorse.java` | `AbstractHorse.java` |
|---|---|---|
| 1.17.1 | `b302997039f3b9b9db5115f796cdf1ad160eb271019420355f29098a196d5e50` | `8ef7b1f59605daad9d17c401fe9992a46151bc6ae3bd17e5225d004edca359e1` |
| 1.18 | `47d22d9cc4455587211f0aa5881f8639e49250206e600a1a04d90174b5fb76a4` | `2ab94ae9c55dc5413d5a13f6cd2dcc432f97544fa6d9db6d07c793ce572ebbc9` |
| 1.18.1 | `47d22d9cc4455587211f0aa5881f8639e49250206e600a1a04d90174b5fb76a4` | `2ab94ae9c55dc5413d5a13f6cd2dcc432f97544fa6d9db6d07c793ce572ebbc9` |
| 1.18.2 | `47d22d9cc4455587211f0aa5881f8639e49250206e600a1a04d90174b5fb76a4` | `2ab94ae9c55dc5413d5a13f6cd2dcc432f97544fa6d9db6d07c793ce572ebbc9` |
## Evidence file hashes

These source SHA-256 values match their same-path records in each version's `mojmap.sources.sha256`:

| Version | `Llama.java` | `Entity.java` | `Player.java` | `ClientLevel.java` | `LocalPlayer.java` | `EntityType.java` |
|---|---|---|---|---|---|---|
| 1.17.1 | `0b41ea3feac6b413976171f45fca83fad0eb9e24f06de5f3ece40d20bfb04d72` | `ab28e1fba924771ec048140dfd293ee5a46a7dfe81f71a1a0b1aecc1927232de` | `724bb298499dabe489dffd5ce7ec81c9773619a8d2c70044911eae4c9e8ff481` | `fd2b3ebade86250cf1ab05f6086dd48f11b6bcd3a01e67c8a03f5c34f553fbc2` | `c9a91cb6cb57806bc8d22e5bfe2d97daaf21d5d2a48f34a6e2c53164c61c5812` | `acea872f54bf5b2996aa7af28791b91da62f2b4bbd12c43af52a40f563e88b52` |
| 1.18 | `48adc67e9e4746ae08ef77e813792c192a7a69ce76e499e87b4501bd658309de` | `69417675ae8e0a5bd88b7372baea1e73b1f5217154f39446c8ad5c92731437f1` | `1fb9dfb9f700323d77f4196dbc3618d01a65e56438b9cce3ee38fa0b258bd7e2` | `085d5348cdd80e512047eba62000c7c18f6c74e853fe329060eb0d2f18cd36b2` | `e38d0bbb6e5b9de2a698609b8491d0007406b3becd42ba9b95289f533788c996` | `c778591e4ddda1ed1d48090bf1b45e2be4b99c83f69af7465e5e4ae28d49f02b` |
| 1.18.1 | `48adc67e9e4746ae08ef77e813792c192a7a69ce76e499e87b4501bd658309de` | `69417675ae8e0a5bd88b7372baea1e73b1f5217154f39446c8ad5c92731437f1` | `1fb9dfb9f700323d77f4196dbc3618d01a65e56438b9cce3ee38fa0b258bd7e2` | `085d5348cdd80e512047eba62000c7c18f6c74e853fe329060eb0d2f18cd36b2` | `e38d0bbb6e5b9de2a698609b8491d0007406b3becd42ba9b95289f533788c996` | `c778591e4ddda1ed1d48090bf1b45e2be4b99c83f69af7465e5e4ae28d49f02b` |
| 1.18.2 | `48adc67e9e4746ae08ef77e813792c192a7a69ce76e499e87b4501bd658309de` | `2228fdaca5793171cbd94038306d571a6ada78ca96f5734efb4cada5b744c10a` | `bf639c1962ff90d69e4569b2b18f6fcf57ac46ef80b19686f0fbc1687fca744a` | `e44cb1126b9b8093655892fb6ca17fa934cd79cc0453cee5e443969d8792b82e` | `99c2d18bcd23243afb8f95c5bafb21fb0be7ea04aacbb14fcf7be7ced2c9c095` | `1fed7ce770fe7f1e49efefb0760f28c798510f5f947e1adba29c8d8fd6c72887` |