# Slow Falling 1.21.2 applicability source follow-up

## Identity and hash verification

The supplied identity is the local directory `decompiled_minecraft/1.21.2/mojmap/`, identified as 1.21.2 / Mojmap by the recordkeeper. The supplied `.decompile-provenance.json` SHA-256 is `00f399407dc447737ea83e3e46562418f8c712f66e3f51cb85d46afa2ecac575`; recomputation matches. The provenance has no `version` or `mapping` fields and no campaign `*.ready.json` marker was used, so this note relies on that supplied neutral directory identity and does not claim independent source-publication readiness.

The provenance `sources` object has exactly one entry: `net/minecraft/world/entity/LivingEntity.java` = `087390495f4fdfd14b9e12230e7aea4fdc50913bb4d1d91119e72892881cfc52`. Its local SHA-256 matches the embedded digest. Other files consulted below are not individually covered by the provenance `sources` object; their measured local SHA-256 values are listed for reproducibility, not as provenance-manifest verification.

| 1.21.2 Mojmap source | SHA-256 |
| --- | --- |
| `net/minecraft/world/entity/LivingEntity.java` | `087390495f4fdfd14b9e12230e7aea4fdc50913bb4d1d91119e72892881cfc52` |
| `net/minecraft/client/player/LocalPlayer.java` | `fbd40f1f47adfa66dda9b15188e5dce82af3e8e8d7c3dd0543e602a354ad3fe0` |
| `net/minecraft/world/entity/player/Player.java` | `a803203e92aa4729d5f5c9b16085b6a43ce51d9907d309eb96736e9c7c1340de` |
| `net/minecraft/world/entity/ai/attributes/Attributes.java` | `14bcc5821d0f67f41fdc3e512556181823cff5c43f84fc1007e51d25f413a3ea` |
| `net/minecraft/server/commands/AttributeCommand.java` | `78bac6ba4f4bb0fd7665b97dd063f63e078c67f6a95e224799113d052c2be256` |
| `net/minecraft/world/entity/ai/attributes/RangedAttribute.java` | `7885aa1e82e9b1e89427ce81a5ed8f0e1cfad796e12d577ead81a52fd9791c3c` |
| `net/minecraft/world/entity/ai/attributes/AttributeInstance.java` | `2a5ed44096b45b3b050a8df947cd44c788e6a35d51eb81d4c023f331f6f62870` |
| `net/minecraft/server/level/ServerEntity.java` | `c45400e92eb0ad334212bda63549050259ca1cab74dd9a50ec2f2738f86fa3a6` |
| `net/minecraft/client/multiplayer/ClientPacketListener.java` | `169e1edaf666ebeb4ad736323565e28935a247748fecb67333dd5db53f24fb8a` |

## Formula and movement call path

- `LivingEntity.getEffectiveGravity` at lines 2173–2176 stores `getDeltaMovement().y <= 0.0` in a boolean and returns `Math.min(getGravity(), 0.01)` iff that predicate and `hasEffect(MobEffects.SLOW_FALLING)` are both true; otherwise it returns `getGravity()`.
- `LivingEntity.getDefaultGravity` at 2169–2171 reads `getAttributeValue(Attributes.GRAVITY)`. `Entity.getGravity` at 1197–1198 uses zero only for no-gravity, otherwise the living default gravity.
- `LivingEntity.travel` at 2178–2185 is gated by `isControlledByLocalInstance()` and dispatches fluid, fall-flying, or air travel. Air travel subtracts `getEffectiveGravity()` at 2201; fluid travel obtains it at 2216–2219; fall-flying movement obtains it at 2279–2284.
- Player tick dispatch: `LocalPlayer.tick` 189–192 calls `super.tick`; `Player.tick` calls `super.tick` at 280; `LivingEntity.tick` calls `this.aiStep()` at 2458 when not removed. Dynamic dispatch reaches `LocalPlayer.aiStep` (638), which calls `super.aiStep` at 795; `Player.aiStep` calls `super.aiStep` at 555; `LivingEntity.aiStep` reaches its travel section at 2758–2772 and calls `this.travel($$10)` at 2771. `Player.travel` calls `super.travel($$0)` in ordinary non-passenger travel at 1445–1467. The falling/effect predicate is sampled by the gravity helper during that travel call, before the helper's result is consumed by movement in that travel path.

## Applicability of the accepted formula difference

The selected 1.21.2 source retains exactly the accepted 1.20.5 formula and gate. In inspected 1.20.5 and 1.21.1 Mojmap sources, `LivingEntity.travel` loads `getGravity()`, checks Y `<= 0.0` plus Slow Falling, then applies `Math.min(value, 0.01)` inline (1.20.5 lines 2104–2110; 1.21.1 lines 2092–2098 per the accepted packet). The same expression/predicate is extracted to `getEffectiveGravity` in 1.21.2. The accepted 1.20.5-vs-1.20.4 difference therefore remains present in the selected 1.21.2 profile: for a falling/equal-Y player with effective gravity below `0.01`, 1.21.2 applies that smaller value where 1.20.4 used literal `0.01`; at default gravity `0.08`, both yield `0.01`. This establishes no formula boundary later than the already accepted 1.20.4–1.20.5 pair.

The command-reachable state used to close practical applicability also exists in the inspected 1.21.2 sources: `Attributes.GRAVITY` is default `0.08`, range `[-1.0, 1.0]`, and syncable (`Attributes.java:41–43`); `LivingEntity.createLivingAttributes` includes it (`LivingEntity.java:318–329`), and `Player.createAttributes` builds from that supplier (`Player.java:225–229`). `AttributeCommand` exposes a `base set` double argument (registration lines 72–83) and its handler calls `setBaseValue` (266–269); `0.005` is within the attribute range. `ServerEntity` sends syncable living attributes in `ClientboundUpdateAttributesPacket` (282–286), and `ClientPacketListener.handleUpdateAttributes` writes the received base value and modifiers into the client instance (2156–2174). Thus an authorized vanilla `/attribute` adjustment to `0.005` can be consumed by the local player's `getGravity()` path in 1.21.2, and with Slow Falling plus Y `<= 0.0` the clamp returns `0.005`. This is command-enabled/map-command applicability, not a default survival trajectory difference.

## Verdict and boundary limit

**Applicability closed for the selected 1.21.2 profile.** The prior accepted 1.20.5 formula difference remains applicable in 1.21.2, including the same command-reachable sub-`0.01` gravity case; the call path is the local player's ordinary travel path. The 1.21.2 implementation is a structural extraction from the inline form observed in 1.21.1, with no arithmetic or predicate change. The inspected endpoints bound that helper extraction to between exact 1.21.1 and 1.21.2; no narrower introduction point is asserted. The accepted formula introduction boundary remains only 1.20.4–1.20.5. Runtime trajectory parity was not measured.

## Input bindings

The historical claim packet supplied for reuse hashes to `F4BCF864C6E5DE492FB35904F097BC4D6F0CA304D7A4A4677C22302672B56BB8`, matching its declared SHA-256. The supplied `slowfall-consequence-followup/source-verdict.md` hashes locally to `42b03b823700ce6c78202c5e6f4f34123b46319530bfb2c26d439ba664b75c58`; it was read as the applicability input. No backlog, product source, wiki inventory, decompilation, build, tests, or runtime was consulted or run.
