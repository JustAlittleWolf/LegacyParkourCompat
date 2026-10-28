# Flying-player climbability veto

1.21.3, Mojmap: `net/minecraft/world/entity/player/Player.java` inherits `LivingEntity.onClimbable()` without a player-level flying veto.
1.21.4, Mojmap: `net/minecraft/world/entity/player/Player.java`, `onClimbable`, lines 2171–2172, returns false when `abilities.flying` and otherwise evaluates the inherited predicate.

Inherited movement consumers include `LivingEntity.handleOnClimbable` and its collision/jump response (`LivingEntity.java`, lines 2362–2370 and 2390–2404); `LocalPlayer.aiStep` also queries the predicate before fall-flying initiation (line 749). An otherwise climbable flying player can therefore take a different movement branch. Bypassing the new early veto must continue evaluating the inherited logic. Attack and other non-movement consumers are outside scope.

Historical dispatch is restricted to the local logical-client player. Logical-server and remote-player behavior remains native. Exact 1.21.2 and 1.21.3 Mojmap Player source files are byte-identical for their existing profile group; earlier applicability is not claimed. This source difference does not establish runtime trajectory parity.
