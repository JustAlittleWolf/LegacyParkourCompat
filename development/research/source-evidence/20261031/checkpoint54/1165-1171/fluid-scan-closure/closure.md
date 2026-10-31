# Fluid scan admission closure: 1.16.5 → 1.17 → 1.17.1

## Result

No static client chunk-boundary witness exists for this delta. In both 1.16.5 and 1.17, `ClientLevel.hasChunk(int, int)` unconditionally returns `true` (`ClientLevel.java:265–267` and `:214–217`). `Level` does not override `LevelReader.hasChunksAt`; its default methods call virtual `hasChunk`. Therefore a client `LocalPlayer`'s `updateFluidHeightAndDoFluidPushing` scan is never denied by client chunk availability in either release, including when `ClientChunkCache` has no corresponding chunk. The new `touchingUnloadedChunk()` expands the entity box and asks for horizontal chunk availability, but on a client its call resolves to that unconditional `true`; the helper returns `false`.

The abstract gates do differ. In 1.16.5, `Entity.updateFluidHeightAndDoFluidPushing` deflates the entity box by `0.001`, derives scan bounds, then calls six-argument `LevelReader.hasChunksAt`; this checks the fixed vertical gate (`ceil(maxY) >= 0` and `floor(minY) < 256`) and iterates inclusive X/Z chunk endpoints. In 1.17, the entity first calls `touchingUnloadedChunk`, which inflates the original box by `1.0`, floors/ceils its X/Z bounds and calls four-argument `hasChunksAt`; that method maps endpoints to section coordinates and checks every chunk inclusively. On `ClientLevel`, all those chunk checks pass. The fluid scan then proceeds without a vertical admission check. The 1.17.1 methods retain the 1.17 bodies.

Consequently, identical client block/fluid states and client chunk-cache contents cannot produce different contact state solely at an X/Z chunk boundary. The only source-level client admission distinction left is vertical: the 1.16.5 fixed `[0,256)` gate versus the 1.17+ active dimension's `getMinBuildHeight()` / `getMaxBuildHeight()` gate in the six-argument default (which is not used by the new helper). The built-in 1.17 and 1.17.1 dimension types set `minY=0, height=256`; 1.16.5's fixed gate is the same range. No ordinary built-in dimension witness is established. A custom/dynamically supplied dimension whose build range differs could expose a difference, but its client dimension registry, block/fluid contents and reachable player state are not established by these decompiled Java sources. Keep the candidate conditional and nonaccepted pending that evidence.

## Local-player path and state consequence

`LocalPlayer.tick()` in all three sources gates its call to `super.tick()` with `level.hasChunkAt(player block X/Z)` (`LocalPlayer.java:184–202` in 1.16.5; `:191–209` in 1.17/1.17.1). The chunk-at check also resolves to `ClientLevel.hasChunk`, so it passes. The inherited `Entity.tick()` / base-tick path invokes `updateInWaterStateAndDoFluidPushing` (1.16.5 `Entity.java:380`, 1.17/1.17.1 `:415`), which scans water, clears/sets `wasTouchingWater`, and updates the water `fluidHeight` map (`1.16.5 Entity.java:957–979`; 1.17/1.17.1 `:1066–1088`). `isInWater()` reads `wasTouchingWater`; fluid height is read by movement/jump paths. Thus a genuine admission difference could reach local-player movement, but the client chunk-boundary premise is false and no built-in vertical witness was found. This establishes source-level call reachability only, not a runtime trajectory or parity claim.

## Exact source evidence

All paths below are relative to `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\<version>\mojmap\`.

| Source | Member/range | SHA-256 |
|---|---|---|
| 1.16.5 `net/minecraft/world/entity/Entity.java` | `tick`, `updateInWaterStateAndDoFluidPushing`, `updateFluidHeightAndDoFluidPushing`, lines 354–382, 957–979, 2641–2684 | `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666` |
| 1.16.5 `net/minecraft/world/level/LevelReader.java` | six-argument `hasChunksAt`, lines 168–187 | `d301b992d71b05306a3139e69f3f1529190f82404791ec60f9a693188d29577a` |
| 1.16.5 `net/minecraft/world/level/Level.java` | no `hasChunksAt` override | `e9a23488863fd43a7d8975efc23f4c7cf4af874554bab5b76eaa2c79f028db80` |
| 1.16.5 `net/minecraft/client/multiplayer/ClientLevel.java` | `hasChunk`, lines 265–267 | `96c80a10623d41f136fa70391b3920cf7f3ef15547923f7d772b78d356f16d93` |
| 1.16.5 `net/minecraft/client/player/LocalPlayer.java` | `tick`, lines 184–202 | `6011569e766bb1568609147be9aa14e9c08c51948e3d3a60fd066e848f6a8c2b` |
| 1.17 `net/minecraft/world/entity/Entity.java` | tick/fluid scan/helper, lines 391–415, 1066–1088, 2771–2813, 2838–2845 | `81231ad8ac1b9a7a9f7ee186352c7e73c0c5211a3034c3a35c04badc6914c8ef` |
| 1.17 `net/minecraft/world/level/LevelReader.java` | vertical and horizontal `hasChunksAt`, lines 185–205 | `4d041cdbc702d532c5b9da800c34bd1c23f8d3b554ecbdd89decbb765a6b451a` |
| 1.17 `net/minecraft/client/multiplayer/ClientLevel.java` | `hasChunk`, lines 214–217 | `fd2b3ebade86250cf1ab05f6086dd48f11b6bcd3a01e67c8a03f5c34f553fbc2` |
| 1.17 `net/minecraft/client/player/LocalPlayer.java` | `tick`, lines 191–209 | `08dbb8615cc6c7776f35ce269223699d39f758d15786babfe11e497421c5f4d4` |
| 1.17 / 1.17.1 `net/minecraft/world/level/dimension/DimensionType.java` | built-in dimension declarations; min Y / height arguments | `2dc118f16b671d3b1b9736c10e960e199790f7930cfa18983f379beb8d00f27b` |
| 1.17.1 `net/minecraft/world/entity/Entity.java` | same fluid scan/helper ranges as 1.17, lines 391–415, 1066–1088, 2773–2815, 2840–2847 | `ab28e1fba924771ec048140dfd293ee5a46a7dfe81f71a1a0b1aecc1927232de` |
| 1.17.1 `net/minecraft/world/level/LevelReader.java` | same admission defaults as 1.17 | `4d041cdbc702d532c5b9da800c34bd1c23f8d3b554ecbdd89decbb765a6b451a` |
| 1.17.1 `net/minecraft/client/multiplayer/ClientLevel.java` | same `hasChunk` override as 1.17 | `fd2b3ebade86250cf1ab05f6086dd48f11b6bcd3a01e67c8a03f5c34f553fbc2` |
| 1.17.1 `net/minecraft/client/player/LocalPlayer.java` | same tick guard as 1.17 | `c9a91cb6cb57806bc8d22e5bfe2d97daaf21d5d2a48f34a6e2c53164c61c5812` |

The 1.16.5 and 1.17.1 Java-file hashes above were recomputed and matched their `mojmap.sources.sha256` sidecars. Their sidecar-file SHA-256 values are respectively `9499f2611d0e6dde37cb635f1a28a591416382d6bc188b37d5f99e2b2a20823b` and `93270d229acfb751bf56daf1e7be26ce3dcff26b29e94aa157de621405a3463b`, matching the prior permitted source identity record. The 1.17 tree has no separate source sidecar; each cited Java hash matches its entry in `.decompile-provenance.json`, whose SHA-256 is `afc9dc30cd8f6406d8ea306e64cc0b3601cc8d7c7bcd314f737573d830a79357`, matching the permitted source-only identity record. The 1.16.5 and 1.17.1 ready-marker hashes also match that record (`e9dd4397c6ade5f68d27f50baf285086dc367b56d2119b03a116cf3cf3b23fee`; `c7180dce94d7b57b53a964445b0c788c71f23ba3ba40eea593c11cc939185e9b`). Mojmap source identities are locally verified; this report does not independently reopen raw client or mapping artifact bytes.

## Scope limits

This closes the requested client-side admission/caller question only. It does not infer chunk production or server availability from the client read behavior, inspect custom world data/resources, or make runtime movement-parity claims. No tests, build, game launch, source generation, product/history, wiki, or research/backlog material was used.
