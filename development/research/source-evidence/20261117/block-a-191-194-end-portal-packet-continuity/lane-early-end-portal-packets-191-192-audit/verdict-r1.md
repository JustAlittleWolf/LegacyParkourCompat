# Narrow independent rebind: corrected-r1

## Verdict

**PASS, within the corrected conditional scope.** In both exact versions, `ServerPlayNetworkHandler.tick()` calls `resetPosition()`, then `player.tickPlayer()`, then unconditionally writes `firstGoodX/Y/Z` with `updatePositionAndAngles` (`server/network/handler/ServerPlayNetworkHandler.java:161–165`). `resetPosition()` captures player XYZ before `tickPlayer()` (`:226–233`). `PlayerManager.changeDimension` calls the transfer helper, then `onChangedDimension`, then `player.networkHandler.teleport(...)` (`server/PlayerManager.java:502–504`); the handler's teleport method sends the position packet at `:515`. Therefore, **if** that transfer and source send execute inside this `tickPlayer()` call, returning from `tickPlayer()` reaches the later unconditional write with the captured pre-call XYZ. The corrected report states this conditional; it does not claim every portal collision is handled in that call path.

In both versions, `World.tickEntity(entity, requireLoaded)` invokes `entity.rideTick()` or `entity.tick()` only inside `if (requireLoaded && entity.inChunk)` (`world/World.java:1391–1407`). With the transfer helper's `false` argument, those full-tick calls are skipped. This rebind is limited to that guard and Java call order. It makes no claim about a runtime, transport, client receipt, arrival, or another transfer route.

## Immutable input identities

Actual SHA-256 values were computed from the named files:

- Author original `comparison.md`: `1448abc6c552a64dc09649e233e52ab054e3037d41a57bc60bedab20677ab628`
- Author corrected `comparison.corrected-r1.md`: `f5df1a64b6899cf0f2db72990fa13f06a75c6eb39605ead130dc5342ca6b658c`
- Author `roster.json`: `2c04fc34f9a264932fa0779598dac410097325f374fdcec68f55ca609dfd2de6`
- Author `sha256.txt`: `6875938dcfc901db67935344c84ac209eadc5e3843a7c6229708f0b46457bfcb`
- Earlier independent audit `roster.json`: `26c89ab7e64e098b25c5dff457cc1996c1b8ed9919ea2257d62c303cd76cfbfb`
- Earlier independent audit `verdict.md`: `0c8daa15721115a7db36af462435905656e733e3d64efe58cc0b71fd2b460d21`
- Earlier independent audit `sha256.txt`: `c37895cc390ca03aa682f1702ea329f0b0be3f28e0bfd318ed025a31ea349b70`

The exact source manifest SHA-256 values are `e9fb0da6511597e2de953a143f9570b82243e5b9504603686e557f06271da64a` (1.9.1) and `9928b467deb35c7e7f891b4fee1627a68feecdf233c2076909fd4834b0380510` (1.9.2). For each release, the actual hashes of the three source files used in this narrow rebind match the manifest and are equal across the pair:

| Source file | SHA-256 |
| --- | --- |
| `server/network/handler/ServerPlayNetworkHandler.java` | `89609533320e02ed781c4aa87caf9eb55417756bba8a6b0ea53bb5f2e1836634` |
| `server/PlayerManager.java` | `ec75fbd8978b92d18f16b649c65ed596ddce6a437fd22bb18fc0b86d2c937e86` |
| `world/World.java` | `cdda70c97f0e4d6f6c454c8f6d3c0d47f0e0ae612e75062d8fe93ddd3963e6b0` |

No other source route or behavior was audited for this rebind.
