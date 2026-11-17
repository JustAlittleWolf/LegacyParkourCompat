# Source-only reviewer verdict: Elytra jump-edge operation

Result: COMPLETED

## Exact draft bindings

- Comparison draft: `elytra-luna/comparison.md`, SHA-256 `518689a8fb6d797fef1a7c2a5559b9d77a3c1e53e265db094676a0bfea825996`.
- Concise finding: `elytra-source/finding-for-audit.md`, SHA-256 `ab2b7aa8ee496f6da8cd309076ff3b79c8ea092441578ba4074306663378128c`.
- Both hashes were recomputed before review. This verdict is bound to those exact bytes; later edits require a renewed review.

## Accepted source claims

- **Earlier request branch, 1.9–1.14.4: ACCEPTED.** I retraced the named canonical sources and confirmed the operation requires a current jump with prior jump false, airborne state, vertical velocity strictly below zero, not already fall-flying, and ability flight off; a usable Elytra in the chest slot sends `START_FALL_FLYING`. This applies to all exact releases in the comparison table, including the intervening releases. The condition follows same-tick ability-flight handling and reads its resulting `abilities.flying` value. It has no explicit water, passenger, or climbing predicate. The request branch itself does not set the local fall-flying flag.
- **1.8.9 bounded absence: ACCEPTED.** In the checked local-player and player-action packet sources there is no scoped Elytra/fall-flying request or `START_FALL_FLYING` action. This is not a claim about unrelated item code.
- **1.15 and 1.15.1 request branch: ACCEPTED.** The earlier strict-negative-Y requirement is gone. The branch requires a jump edge, no ability-flight transition in the tick, ability flight off, and not a passenger; it checks chest Elytra identity/usability and calls `tryToStartFallFlying()` before sending. `1.15` and `1.15.1` have the same verified `LocalPlayer.java` hash and branch.
- **1.15 local-state change: ACCEPTED.** `Player.tryToStartFallFlying()` rejects grounded, already-fall-flying, or wet state, rechecks Elytra usability, and calls `startFallFlying()`. That setter writes shared flag 7 before the packet send. On the 1.15.2 local tick path this state is sampled and is available to the later fall-flying travel branch that tick. The comparison correctly limits this to source-visible local ordering, not a runtime trajectory result.
- **1.15.2 climb gate: ACCEPTED.** Its caller adds `!onLadder()` before chest lookup and the helper call. The checked `onLadder()` reads the feet block state (except spectators) and recognizes ladder, vine, scaffolding, and the specified open trapdoor/ladder relation. 1.15 and 1.15.1 do not have this caller gate.
- **Ordinary rising-jump witness: ACCEPTED as source-predicted.** A survival player can start from flat ground with usable chest Elytra, release jump, then press jump again during the rising airborne portion. With no ability-flight transition, passenger, water, or climbable block, the 1.14.4 negative-Y check rejects the edge; 1.15/1.15.1 accept it and set the local flag before travel; 1.15.2 does likewise off climbables. This is a bounded ordinary-player witness, not an observed run.
- **Same-tick toggle nuance: ACCEPTED.** Through 1.14.4, the request reads flight state after the toggle branch: toggling flight on blocks the Elytra request through the resulting `abilities.flying == true`, while toggling it off can leave the request eligible if the other gates pass. From 1.15, the explicit `bl8` transition marker blocks the request on either same-tick toggle, including a toggle that leaves flight off.

## Mounted witness disposition

- **Pre-1.15 syntactic absence of a passenger gate: ACCEPTED.** This is source-level evidence only; it does not establish a reachable mounted-descending branch.
- **Ordinary mounted-descending request: NOT ACCEPTED as a reachable witness; the draft's limitation is correct.** In 1.9.2, `Entity.rideTick()` sets rider X/Y/Z velocity to zero and then calls `tick()` (`Entity.java` 1423–1435); `LocalClientPlayerEntity.rideTick()` delegates to it (`LocalClientPlayerEntity.java` 760–767). The local Elytra request is evaluated during that tick before the riding jump-charge code and requires `velocityY < 0.0` (`LocalClientPlayerEntity.java` 703–708, 724 onward). The ordinary ride-tick path therefore does not provide the required negative-Y value at the request point. I found no source-established ordinary mounted witness. A stipulated predicate with a mounted player and negative Y is not evidence of reachability. From 1.15, the explicit passenger gate independently closes the request site.

## Exact evidence and reviewer limits

- Read-only exact source trees and permitted source manifests under `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/` only. Nested exact mapping provenance at the mapping-directory level was used for older Feather/Yarn trees; top-level release sidecars were used where present. Recomputed local-player source hashes matched their nested or release source-manifest entries for all versions in the table. The 1.9.2 ride-tick witness sources were checked directly; they are not product sources.
- Checked the complete enclosing caller condition and neighboring order at the named branch sites, the relevant Elytra usability check, the 1.15 helper/setter, and the mounted tick path needed to assess the proposed witness. No product implementation, Git history, wiki, or mixed backlog was read.
- This is an operation-level review, not complete tick-graph coverage, full-pair inventory closure, a source freeze, or implementation/runtime parity evidence. No tests or runtime were run. It does not prove a historical implementation cutover between the broad release endpoints.