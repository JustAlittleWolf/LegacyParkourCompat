# Fresh source audit: sleep versus fall-flying resize gate

Status: COMPLETED — ACCEPT (bounded source claim).

## Verdict

The frozen `direct-resize-fit.md` claim is supported for every stable alias from 1.10 through 1.12.2: if fall-flying and sleeping are both true at `PlayerEntity.updatePlayerPose()`, the first branch selects `0.6F × 0.6F`; sleeping is not consulted. A size comparison then skips all query/write work when dimensions already match. Otherwise the method builds one candidate `Box` at current `minX/minY/minZ`, extends it by the selected width/height, calls `world.getCollisions(candidate)`, and calls `setSize` only if the query returns false. Thus from a sleeping `0.2F × 0.2F` box the dual-predicate result is a fit-gated expansion to `0.6F × 0.6F`.

The tick-level call to `updatePlayerPose()` is present in each checked release's `PlayerEntity` tick path. The selector method body is the same across the nine checked aliases (1.10/.1/.2, 1.11/.1/.2, 1.12/.1/.2); exact full-source PlayerEntity hashes are listed in the frozen bound and matched the source files read in this audit.

## Resize behavior and edge case

For 1.10/.1/.2, `Entity.setSize` writes dimensions and a new box from the old box minima plus new width/height. Its width-growth compensation calls `move(oldWidth-newWidth, 0, oldWidth-newWidth)` only when `!firstTick && !world.isClient`; the logical client still receives the dimension/box write but not that compensation.

From 1.11 through 1.12.2, a width decrease takes a distinct early-return branch: the new box is centered on entity `x/z` and bottomed at `y`. For the requested simultaneous-predicate case starting at the known sleeping width 0.2, desired width 0.6 is an expansion, so the client box remains min-anchored and agrees with the candidate. From an already 0.6 width it is unchanged. However, if some external state supplied a prior width greater than 0.6, the desired 0.6 width takes the shrink branch; then the min-anchored fit candidate is not the eventual resized box. Likewise, a fall-flying-to-sleeping transition in 1.11–1.12.2 shrinks from 0.6 to 0.2 and recenters. Do not generalize candidate/post-write equivalence to those shrink cases.

## Query boundary

In 1.10–1.11, `World.getCollisions(Box)` checks block-state collision shapes and returns true when an examined X/Z block coordinate is below `-30,000,000` or at/above `30,000,000`. The overload takes no entity argument and does not query other entities. From 1.11.1–1.12.2, the method delegates as `getCollisions(null, shape, true, emptyList)`. The helper applies the same hard X/Z coordinate bound and block-shape checks; `entity == null` skips the entity-collision query. Its configured `WorldBorder` replacement branch requires `!enforceWorldBorder`, which is false for this wrapper. Therefore this fit gate checks blocks and its hard coordinate bound, not entity occupancy or the configured world-border edge.

## Evidence and limits

- Frozen inputs were verified by SHA-256: `direct-resize-fit.md` = `BAE6C476F7D819C94191E887D681541D4F2821C066EE8EEBEA9DC45154E2FD01`; `stable-aliases.md` = `A7447EB210121A4DD4659F1D58DCCF1F154B459C4F7FC32B86D7AC124EA6A534`.
- Source identity supplied with the assignment: `52399d310b7366244457d5c472c41378f2064262`; canonical Feather Gen2 build.2 source trees were read-only. `PlayerEntity.java` hashes for all nine releases matched the frozen per-release table; selector-body hashing across the aliases yielded one identical body. Exact references: `decompiled_minecraft/<version>/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java`, `updatePlayerPose()` (1.10: 296–320; 1.11: 295–319; 1.12: 291–315); `.../entity/Entity.java`, `setSize` (1.10: 264–275; 1.11: 272–284; 1.12: 279–291); `.../world/World.java`, single-Box query and, from 1.11.1, helper (`getCollisions(Box)` at 1.10 1022–1059, 1.11 1044–1081, 1.11.1/.2 1058–1060, 1.12/.1/.2 1062–1064; helper at 1.11.1/.2 960–1011 and 1.12/.1/.2 964–1015).
- The verdict is conditional on selector state and does not establish ordinary-play reachability of simultaneous sleeping/fall-flying truth. No trajectory, collision-shape internals, entity clearance, configured-border occupancy, or runtime behavior was evaluated. It is a bounded source acceptance, not runtime parity or broad world-geometry coverage.