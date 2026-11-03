# Explosion knockback packet precision

## Evidence identity

Exact Mojmap source trees: `decompiled_minecraft/1.21.1/mojmap`, `decompiled_minecraft/1.21.2/mojmap`, and `decompiled_minecraft/1.21.3/mojmap`.

The 1.21.1 and 1.21.3 ready markers identify their version and Mojmap namespace. Their relevant source files match `mojmap.sources.sha256`. The 1.21.2 directory has no root ready marker or source-manifest sidecar; its embedded `mojmap/.decompile-provenance.json` (SHA-256 `00F399407DC447737EA83E3E46562418F8C712F66E3F51CB85D46AFA2ECAC575`) records client artifact metadata URL `.../1.21.2.json` and the relevant per-file hashes, all of which match the local files. The three source directories and this embedded provenance are the evidence used here.

## Server-produced vector

In 1.21.1, `world/level/Explosion.java:235–276` derives a `Vec3` knockback in double precision, adds it to entity velocity, and records it for players satisfying `!isSpectator()` and `(!isCreative() || !abilities.flying)` (`:273–275`). `server/level/ServerLevel.java:1072–1085` sends the stored player vector to each player with distance-squared below `4096.0`.

In 1.21.2, `world/level/ServerExplosion.java:171–215` derives a `Vec3` in double precision, adds it to entity velocity, and stores it for the same player eligibility condition (`:212`). `server/level/ServerLevel.java:1108–1109` wraps the map value in `Optional<Vec3>` and sends the packet. The corresponding 1.21.3 files are byte-identical to 1.21.2 for these paths.

## First wire-precision change: 1.21.1 to 1.21.2

In 1.21.1, `network/protocol/game/ClientboundExplodePacket.java:28–30,52–61` stores three float knockback fields and casts each component of the server's `Vec3` to `float` in the packet constructor. Lines `83–85,108–110` read and write those three values as floats. `client/multiplayer/ClientPacketListener.java:1172–1188` adds those decoded components to the local player's existing delta movement. Thus the server retains its full double vector while the client receives the float-quantized vector.

In 1.21.2, `ClientboundExplodePacket.java:15–24` changes the payload to `Optional<Vec3>` using `Vec3.STREAM_CODEC`. `world/phys/Vec3.java:24–31` delegates to `FriendlyByteBuf.readVec3/writeVec3`; `network/FriendlyByteBuf.java:446–458` reads and writes x, y, and z using `readDouble/writeDouble`. `ClientPacketListener.java:1225–1241` adds the optional decoded `Vec3` to the local player's existing delta movement. This preserves the server-produced double components through the packet and client addition. The same packet, codec, and local-add implementation persists in 1.21.3.

This is reachable on the normal player path: an explosion includes the player's vector only when the player is in its entity query/radius, is not ignored, has nonzero direction length, and is not a spectator or creative-flying. `ServerLevel` sends the per-player packet within 64 blocks; on receipt, the client handler applies the optional vector. An unobstructed ordinary explosion can produce a non-float-representable component. For example, with player feet at `(2, 0, 0)`, eye height `h`, explosion center at `(0, h, 0)`, explosion radius field `4`, no resistance, and unobstructed exposure, the server's radial distance factor is `sqrt(4 + h²) / 8` while its normalized x direction is `1`; the x knockback is therefore `1 - sqrt(4 + h²) / 8` (default multiplier `1`). This irrational double-valued expression is not exactly representable as float; the 1.21.1 packet constructor rounds it, while 1.21.2 transmits it as a double. The example describes source-reachable geometry and default calculator behavior; it is not a runtime test.

The producer multiplication remains double-led and left-to-right across these versions: `(1 - distanceRatio) * seenPercent * knockbackMultiplier`, followed by resistance. In 1.21.2/1.21.3, the `getSeenPercent` call is skipped when the entity is not damageable and the knockback multiplier is zero; that case still yields a zero knockback magnitude. No separate producer-precision change is claimed here. Damage handling and all other explosion responses are outside this finding.

## Selected source hashes

- 1.21.1 `Explosion.java`: `2e8ae9259c5a24b13c840c6437583a61874877a6880ffdf62e9d5b32a822f5e1`
- 1.21.1 `ServerLevel.java`: `a87ef1125c53a0ada419f583d17e841402235ca569994220ec8a63baa5457181`
- 1.21.1 `ClientboundExplodePacket.java`: `41b8d60fd85e4811bbc0b7b6bc623825f7824ec8266bb00ab4e2fb371438038c`
- 1.21.1 `FriendlyByteBuf.java`: `10a8a9bc44d26077b14580356f81109e34c9852981be845b7d5b922c7b2a97ad`
- 1.21.1 `ClientPacketListener.java`: `822e00d89e8def4cf7a8c22fdfefba3b9dc2135a7630e4ebe561a9d547d7f79d`
- 1.21.2 and 1.21.3 `ServerLevel.java`: `c664c8ff5807eed64b5f627afd6908f5baccf4acab504ef4dab6e42ad2fef00d`
- 1.21.2 and 1.21.3 `ServerExplosion.java`: `c6b558532c1d0503fc6219d29b71ad3bd6f4de28c73847d0e2c533bd73f96dde`
- 1.21.2 and 1.21.3 `ClientboundExplodePacket.java`: `54ccf7ddc395f360de1b6f84ed4d0489fdc4a36db3ce83e71d02c31a9138fcc7`
- 1.21.2 and 1.21.3 `FriendlyByteBuf.java`: `5bd936862c1e0c6ab73f09418feb940a92b36cd699dcbafc10cbe08fece82a2b`
- 1.21.2 and 1.21.3 `ClientPacketListener.java`: `169e1edaf666ebeb4ad736323565e28935a247748fecb67333dd5db53f24fb8a`

No code, game runtime, or tests were changed or run.
