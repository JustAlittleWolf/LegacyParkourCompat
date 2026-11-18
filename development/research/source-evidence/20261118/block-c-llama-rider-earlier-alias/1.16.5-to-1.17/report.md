# Llama passenger placement, 1.16.5 → 1.17

## Bounded verdict

**No source difference found** for an ordinary adult default Llama or TraderLlama with one direct local-player passenger. The Y-placement operation, the per-tick position callback path, the Llama passenger gate, and the Player riding offset are identical in the compared sources. TraderLlama inherits the Llama placement override in both versions. The AbstractHorse post-super stand-animation adjustment is not on this Llama path because `Llama.positionRider` does not call `super.positionRider`.

This is a bounded result for passenger placement only. It says nothing about vehicle steering or physics.

## Exact operation and bounds

| Source | Exact references | Operation |
|---|---|---|
| 1.16.5 `net/minecraft/world/entity/animal/horse/Llama.java` | `positionRider(Entity)`, lines 151–159; `getPassengersRidingOffset()`, lines 161–164 | `if (this.hasPassenger(entity))`; compute `f = cos(yBodyRot * (float)(PI / 180.0))`, `g = sin(...)`, then `entity.setPos(x + 0.3F*g, y + getPassengersRidingOffset() + entity.getMyRidingOffset(), z - 0.3F*f)`. Rider Y is `llama Y + llama bounding-box height * 0.67 + rider offset`. |
| 1.17 `net/minecraft/world/entity/animal/horse/Llama.java` | `positionRider(Entity)`, lines 153–161; `getPassengersRidingOffset()`, lines 163–166 | Same gate, expressions, casts, call, and order. |
| 1.16.5 `net/minecraft/world/entity/player/Player.java` | `getMyRidingOffset()`, lines 988–991 | Returns `-0.35` as a `double`; no pose/crouch branch. |
| 1.17 `net/minecraft/world/entity/player/Player.java` | `getMyRidingOffset()`, lines 1003–1006 | Same `-0.35` return; no pose/crouch branch. |
| 1.16.5 `net/minecraft/world/entity/Entity.java` | `rideTick()`, lines 1577–1583; `positionRider(Entity)`, lines 1585–1594 | `rideTick` zeros movement, ticks, then if passenger invokes `getVehicle().positionRider(this)`. The base callback also checks `hasPassenger` before setting position. Llama overrides this callback. |
| 1.17 `net/minecraft/world/entity/Entity.java` | `rideTick()`, lines 1687–1693; `positionRider(Entity)`, lines 1695–1704 | Same callback sequence and base gate/math. |
| 1.16.5 `net/minecraft/world/entity/animal/horse/AbstractHorse.java` | `positionRider(Entity)`, lines 906–924 | Calls super, copies rider mob body yaw, and adds `0.15F * standAnimO` to Y only inside `standAnimO > 0.0F`. Llama overrides without calling this method. |
| 1.17 `net/minecraft/world/entity/animal/horse/AbstractHorse.java` | `positionRider(Entity)`, lines 918–935 | Same super call and gate/formula; still bypassed by Llama's override. |
| Both `TraderLlama.java` | Class declaration and constructor near lines 20–25 | `TraderLlama extends Llama`; no `positionRider` or passenger-offset override in either file. |

For the specified player, the Y expression is therefore `llamaY + llamaBbHeight * 0.67 - 0.35`. Body yaw affects only the X/Z terms. The Llama placement method contains no adult/baby, TraderLlama, player subtype, yaw, or crouch condition; its only local callback gate is direct passenger membership. The caller's passenger check and the Llama's `hasPassenger(entity)` guard are unchanged.

## Canonical source and provenance checks

Sources were read in place from the primary checkout's `decompiled_minecraft/<version>/mojmap/` trees; no generation or source edits were performed.

- **1.16.5:** hidden campaign metadata `decompiled_minecraft/1.16.5/mojmap.provenance.json` records `versionId=1.16.5`, `namespace=mojmap`, mapping option `mojmap`, the exact run, Java 25.0.3, Gradle 9.7.1, Vineflower 1.12.0 and Tiny Remapper 0.14.1. `mojmap.ready.json` says ready, reports 3,523 source files, and binds `mojmap.sources.sha256` by SHA-256 `9499f2611d0e6dde37cb635f1a28a591416382d6bc188b37d5f99e2b2a20823b`; its client input SHA-256 is `00b5ebbc33e95ea88c1ab80601599c9827e9aa861d93ccc2cfcbbfd996e86263`. The source-list file itself hashes to the marker value. It has no modern per-file provenance manifest; the campaign record does not provide the mapping artifact SHA or per-tool JAR hashes, so those identities are not independently claimed here.
- **1.17:** `decompiled_minecraft/1.17/mojmap/.decompile-provenance.json` records Mojang client jar SHA-256 `14122287d4e4256c2d65ec8c0ccfa3b56448495fa3ccabc87d0f0eeb4fa1c30f`, official `client.txt` mappings SHA-256 `191b957aaa40b294402feef29651ae7a9f1263ec22c5382f3b8fa0a4918b0471`, and version metadata SHA-256 `537c6fa7e6ab13caa76ce2e84e4748882d36fcd5a076fa6903f894880e1df0f6`. It records Vineflower 1.12.0 JAR SHA-256 `1dfcfe974395734fa467ce620661c7623d05ba83670de0529b1fbd63ff548b9d` and Tiny Remapper 0.14.1 JAR SHA-256 `c4d3b3e27089a5b96d21eb5184a75898e6c5062ebca4fee5f92db34c389b8952`. Its embedded source hashes match the current files for every cited Llama, TraderLlama, AbstractHorse, Entity and Player class.

Relevant source-file SHA-256 values (also matched against the 1.16.5 source manifest and 1.17 embedded source map):

| Class | 1.16.5 | 1.17 |
|---|---|---|
| `Llama.java` | `18bf21b8d7559e4c9a3195126dc0b30b691e8e869fa1c214956a6395534e5f2d` | `0b41ea3feac6b413976171f45fca83fad0eb9e24f06de5f3ece40d20bfb04d72` |
| `TraderLlama.java` | `2d5d0d9bbb694ec542d6d167f1e98c95a0c1991b66b6ef69c7881b326c54c8f3` | `7902fcba73b9b9a8c3c941f047b47b9fa05c3f7a2bd6ddda4a9ba3dd1876ee0e` |
| `AbstractHorse.java` | `c4e33cd5a8d52099fbcd2c90c5d0a55a0471ae768e682f3a45e53c4dd7925672` | `19fda974a022c9521309661ebea103049d9d1ab96d29afebb04b89bd9e7405c0` |
| `Entity.java` | `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666` | `81231ad8ac1b9a7a9f7ee186352c7e73c0c5211a3034c3a35c04badc6914c8ef` |
| `Player.java` | `d2e26589bdb6a20dc914266db06aa48f50811efc792d6e63b3008c9199914960` | `9ad4c439814caf5f51cf6da9a86c5d22906b0a28b5b606bdeb1b770617d90516` |

