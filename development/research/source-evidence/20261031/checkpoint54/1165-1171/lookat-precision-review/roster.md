# Frozen source roster: `handleLookAt` precision candidate

This reviewer froze the candidate-only roster before inspecting the proposing worker's report or derivative calculations. Exact releases are 1.16.5, 1.17, and 1.17.1, Mojmap, from the assigned read-only decompiled trees.

The candidate route and numeric dependencies require these files:

- `net/minecraft/client/multiplayer/ClientPacketListener.java` — `handleLookAt` dispatch and packet-resolution gate.
- `net/minecraft/network/protocol/game/ClientboundPlayerLookAtPacket.java` — packet anchor and raw target coordinates.
- `net/minecraft/world/entity/Entity.java` — `lookAt`, `getLookAngle`, rotation writes/reads.
- `net/minecraft/world/entity/LivingEntity.java` — virtual `lookAt` override and local-player `travel` entry if inherited.
- `net/minecraft/world/entity/player/Player.java` — `travel` swimming adjustment and `isSwimming`/passenger gates.
- `net/minecraft/client/player/LocalPlayer.java` — client tick / AI-step route into player travel.
- `net/minecraft/commands/arguments/EntityAnchorArgument.java` — FEET/EYES anchor application.
- `net/minecraft/world/phys/Vec3.java` — rotation-to-look-vector construction.
- `net/minecraft/util/Mth.java` — square-root, atan2 and trigonometric table semantics used by the candidate math.

The paired claims to test are (a) candidate-path dispatch and inherited dynamic overrides, (b) the exact boundary of any changed math and its downstream state, and (c) whether equal finite packet coordinates/current player state can yield a different local-player movement-facing value on the normal tick route. Server-side look target production is outside this candidate review and is not evidence of a client-side difference.

No prior reports or source-lane findings were inspected to freeze this list. After freezing, this reviewer will compare exact source bodies and source identities, then consult the proposed witness only to reproduce or correct it. This is a single-finding review, not a complete pair audit.
