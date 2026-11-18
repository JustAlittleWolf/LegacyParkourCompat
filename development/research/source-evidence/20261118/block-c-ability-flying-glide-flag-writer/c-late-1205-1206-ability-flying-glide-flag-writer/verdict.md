# Independent audit: 1.20.5–1.20.6 creative flight during Elytra glide

## Verdict

**Accepted, bounded no-difference claim.** The frozen report’s claim that a normally reachable local-player creative-flight toggle can overlap an already-live Elytra flag for one travel call, with the glide move occurring before `Player.travel` clears bit 7, is supported. The paired exact source files relevant to that path are byte-identical. This verdict applies only to that behavior and pair; it does not freeze the full pair or accept any implementation.

## Immutable claim and source identity

Frozen input: `c-late-1205-1206-ability-flying-glide-flag-writer/report.md`, SHA-256 `2d3d2cd150aeb9d7d414512ef5a34ed97291149ec23361c3eff2adcc4e0c5ce2`.

Canonical local sources: `decompiled_minecraft/1.20.5/mojmap/` and `decompiled_minecraft/1.20.6/mojmap/`, exact Mojmap, `status: ready` markers for version IDs 1.20.5 and 1.20.6. The 1.20.5 ready-marker SHA-256 is `4a587caa86e4ddd68ba63a5431417933dc4df4822e33aaf29ce77ebda53947c2`; its declared and actual source-manifest hash is `162dac6c4f1d539a1c2cea0255b7938a5281c207ab460bdc265358016d732484`. The 1.20.5 provenance SHA-256 is `1d9b247fb839ad0e234054cba1668073245e60d86783eca40d44c2b7b23c6242`, matching its ready marker. The 1.20.6 ready-marker SHA-256 is `1ec1dc773ea2a8a3ef48a9fc868893bd2f83d6b13b057d75b151b711788c3d41`; its declared and actual source-manifest hash is `56aae10684471d7abb1c366bd5dd431ab976112a87e6cc5c687a68f1eff06311`. Its provenance SHA-256 is `ee53e67e7ab51069a0eaae5a0ce13d49a9f972bdc13e3c31e041afa77f4d26e8`; the provenance batch explicitly includes 1.20.6. The 1.20.6 ready marker does not declare a provenance digest field, so that digest was checked directly rather than matched to a ready-marker field.

The actual Java-file hashes match each version’s source manifest and are equal across the pair:

| Paired file | SHA-256 in both releases |
|---|---|
| `net/minecraft/client/player/LocalPlayer.java` | `6b429dfa6e0681251ec985dda1627f808652a7bbe5b70dc85c8fa0fe0ed46ffa` |
| `net/minecraft/server/network/ServerGamePacketListenerImpl.java` | `be4268a7616ed94e2a03411a6fdf002b0e6dc3303ea959c9f41fef322867d533` |
| `net/minecraft/world/entity/Entity.java` | `71cd6b9f6c002684154dce11d3745e8714d82f13c8e1b3aa56743930131c18f3` |
| `net/minecraft/world/entity/LivingEntity.java` | `c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2` |
| `net/minecraft/world/entity/player/Player.java` | `785d93ccc94e1f912e545b2b0c355edeb352b44ee8e83a69364daec35266dbe2` |

## Independent path trace

1. A prior ordinary local Elytra-start input can establish the live flag: `LocalPlayer.aiStep` checks for jump input, a transition from not-jumping, no flight toggle, `!abilities.flying`, not being a passenger, and not being on a climbable; `Player.tryToStartFallFlying` further requires airborne, not already fall-flying, not in water, not levitating, and a fly-enabled Elytra (`LocalPlayer.java:744–749`; `Player.java:1534–1548`). The player method sets shared bit 7 and the client sends the normal start command.
2. On a later input edge, `LocalPlayer.aiStep` requires `abilities.mayfly`. Unless the game mode is always-flying, the jump-toggle path waits for its trigger timing and excludes swimming; it then toggles `abilities.flying` and calls `onUpdateAbilities` (`LocalPlayer.java:720–741`). It records the existing fall-flying state at line 751. Because this toggle sets the local `$$11` marker, the adjacent “start a new Elytra glide” block is suppressed; that block also independently excludes `abilities.flying` (`LocalPlayer.java:744–751`). `onUpdateAbilities` sends the current abilities packet (`LocalPlayer.java:343–345`).
3. `LocalPlayer.aiStep` then calls `super.aiStep` (`LocalPlayer.java:807`). In the living travel phase, `LivingEntity.aiStep` calls `updateFallFlying()` before creating travel input and dispatching `travel` (`LivingEntity.java:2670–2684`). For a live bit with an airborne, non-passenger player who is not levitating and wears an enabled Elytra, `updateFallFlying` computes true; it has no creative-flight exclusion. If a condition fails it computes false, but the final bit write is guarded by `!level().isClientSide`, so this method itself does not clear or rewrite the local client’s shared bit in either case (`LivingEntity.java:2721–2745`). In the valid overlap being audited, bit 7 therefore remains live up to `Player.travel`.
4. For a dry, non-passenger local player with creative flight and a live Elytra bit, `Player.travel` captures incoming Y velocity, calls `super.travel`, then writes vertical velocity as incoming Y times `0.6`, resets fall distance, and clears bit 7—in that order (`Player.java:1473–1494`). In the ordinary airborne glide case, the local-control gate and water/lava branch ordering reach `LivingEntity.travel`’s `isFallFlying()` branch. It calculates glide velocity, writes the 0.99F/0.98F/0.99F-scaled velocity, and calls `move(MoverType.SELF, ...)` at line 2194 before returning to the player wrapper (`LivingEntity.java:2104–2107, 2168–2195`). Thus a glide displacement is applied on this travel call before the wrapper’s bit-7 clear. `LocalPlayer.aiStep`’s earlier `moveTowardsClosestSpace` calls can move an embedded player; this verdict concerns the ordinary unembedded travel path and does not call the glide move the unconditional first `move` invocation in every state.

## Writer and side boundaries

`Entity.getSharedFlag` reads bit 7 and `setSharedFlag` changes only the selected bit in the shared byte (`Entity.java:2236–2247`). The 1.20.5 source-wide search finds the relevant bit-7 writes in `LivingEntity` (NBT restoration at 787; server-only landing clear at 2205–2206; server-only `updateFallFlying` write at 2743–2744) and `Player` (the `travel` clear at 1491; start at 1547; `stopFallFlying`’s true-then-false sequence at 1550–1552). The paired file hashes establish those methods are identical in 1.20.6.

The `Player.travel` clear has **no logical-side guard**. The demonstrated local path executes it on the client, but the same vanilla branch can execute for a server-side player with flying ability; it should not be described as a client-exclusive writer. Separately, the server handles `START_FALL_FLYING` by validating with `tryToStartFallFlying` and stopping an invalid start (`ServerGamePacketListenerImpl.java:1410–1413`), and accepts flying ability only as `packet.isFlying() && mayfly` (`:1689–1692`). The movement-packet handler also snapshots `isFallFlying()` before its Elytra speed-check gate and chooses the 300.0F versus 100.0F multiplier from that snapshot (`ServerGamePacketListenerImpl.java:865–879`); this is a vanilla server check, not evidence that the server replays historical physics. No latency or crafted-packet behavior is inferred here.

## Separated behaviors and limits

`resetFallDistance()` at `Player.java:1490` is a distinct write immediately before the flag clear; no fall-distance change or consequence is claimed. Normal glide activation and `stopFallFlying`’s two-write sequence are distinct operations from the post-travel direct clear. The server landing clear and `updateFallFlying` writer are also separately gated and are not substituted for the client travel sequence. No older-version A/B source was inspected. No intermediate patch boundary, complete player-tick coverage, runtime parity, or implementation acceptance is established.


