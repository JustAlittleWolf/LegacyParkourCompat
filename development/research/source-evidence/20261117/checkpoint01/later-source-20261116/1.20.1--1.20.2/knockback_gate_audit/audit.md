# Independent source audit: player knockback source-tag gate

Verdict: ACCEPT.

## Bound claim

Candidate: `build/later-source-20261116/1.20.1--1.20.2/knockback_gate/development/research/findings/1.20.1--1.20.2/player-knockback-source-tag-gate.md`
Candidate SHA-256 (exact bytes): `F5C96107E5918FB7E3C139BD937F93F0FAFF389294E12360A13975E8AABFBACA`.
Exact source identities supplied for this review: 1.20.1 Mojmap `5AEB1DF09671480AEDDB921C919E92098E33D0A0BCE47E22F82A865CB32400AA`; 1.20.2 Mojmap `1DAD9F725F6CB25B6568700FA0294F83B66EA9633B0D62ED354D7BD8B45D4F05`.

## Evidence and applicability

- In both `decompiled_minecraft/1.20.1/mojmap/net/minecraft/client/player/LocalPlayer.java:149-152` and `decompiled_minecraft/1.20.2/mojmap/net/minecraft/client/player/LocalPlayer.java:148-151`, `LocalPlayer.hurt(DamageSource,float)` returns `false`. The inherited `LivingEntity.hurt` also returns before its body on a logical client (`1.20.1 .../LivingEntity.java:1065-1072`; `1.20.2 .../LivingEntity.java:1090-1097`). Thus the tag check is not a local client hurt/damage-prediction branch.
- Server-side `ServerPlayer.hurt` delegates to `super.hurt` after its player/PvP gates in both versions (`.../ServerPlayer.java:685-699` in 1.20.1; `:684-700` in 1.20.2). The inherited `LivingEntity.hurt` then reaches the differing tag gate only after its successful-hit and `$$7` gates: 1.20.1 `.../LivingEntity.java:1147-1168` tests `IS_EXPLOSION`; 1.20.2 `:1172-1193` tests `NO_KNOCKBACK`. The resulting call is `LivingEntity.knockback`, which sets `hasImpulse` and writes the new velocity (`1.20.1 .../LivingEntity.java:1419-1426`; 1.20.2 `:1445-1452`).
- For successful hits where the source is not `NO_IMPACT` and the other `markHurt` gate passes, this server-computed Player velocity is sent to the owning client as a direct local-player movement response. `markHurt` sets `hurtMarked` (`1.20.1 .../Entity.java:1423-1425`; 1.20.2 `:1433-1435`), and `ServerEntity` responds to `hurtMarked` with `ClientboundSetEntityMotionPacket` through `broadcastAndSend` (`1.20.1 .../ServerEntity.java:199-201,296-300`; 1.20.2 `:202-204,299-303`); that method explicitly sends to tracked recipients and, for `ServerPlayer`, the owning player's connection. On the client, `ClientPacketListener.handleSetEntityMotion` finds the entity and calls `lerpMotion` (`1.20.1 .../ClientPacketListener.java:501-506`; 1.20.2 `:481-486`); `Entity.lerpMotion` writes delta movement in both versions (`1.20.1 .../Entity.java:2039-2041`; 1.20.2 `:2073-2075`). Thus the gate affects a server-authored velocity that becomes the local player's client movement state under this reachable gate. This closes the candidate's applicability question without claiming that the client evaluates the tag. No claim is made for `NO_IMPACT` sources whose path need not send the owner this velocity packet.
- The 1.20.2 `NO_KNOCKBACK` key is declared at `.../tags/DamageTypeTags.java:33`; no such declaration is present in the 1.20.1 file. The compared knockback formula and direct velocity writer are otherwise the same as stated in the candidate.

## Scope boundary

Accept this as an in-scope direct player movement response, not as historical server physics or client-side damage prediction. The eligible consequence is the Player velocity that the owning client receives and uses for movement. Damage resolution and server execution remain excluded; any implementation must respect the project invariant that historical movement does not run on logical servers. This verdict makes no claim about which runtime damage types satisfy either tag, synchronized client tag membership, or the resulting trajectory.


