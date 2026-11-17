# Classic Elytra jump start and local fall-flying state

Bounded source comparison: the local-player jump-edge request and the client-local fall-flying flag, plus only the tick/travel callers needed to determine immediate local effect. No product source or history was consulted. This is not a full-pair inventory or freeze.

## Exact inputs and identity checks

Canonical inputs are under `decompiled_minecraft/`. The checked source identities are:

- 1.8.9: Ornithe Feather Gen2 build.2, identified by the release ready/provenance markers.
- 1.9–1.9.3: Ornithe Feather Gen2 build.2, recorded in each nested decompile provenance manifest. 1.9.4–1.14.4 Feather trees use their release ready/provenance markers and build.2 mapping artifacts.
- 1.14–1.14.3: Yarn mappings, with the exact Yarn/intermediary artifact identities in each nested manifest (1.14 is Yarn 1.14+build.21). 1.14.4 Yarn was only present as staging; I did not use it.
- 1.14.4–1.15.2: Mojmap named sources, identified by each release's Mojmap ready/provenance marker. 1.14.4 Feather is also present and its checked branch agrees with Mojmap.

Per-source SHA-256 checks matched the recorded source hashes for the checked local-player files in 1.8.9, every release from 1.9 through 1.14.4, the 1.14 Yarn sample, and each 1.15 release; 1.15.2 `Player.java` and `LivingEntity.java` also matched. Legacy marker trees establish their recorded artifacts/source-list identity; this is not an independent whole-tree hash audit.

## Earlier request rule and applicability

1.8.9 Feather has no Elytra item class, no Elytra/fall-flying term in the local-player or player-action packet sources, and no `START_FALL_FLYING` action. This is a bounded absence check in those exact sources, not a claim about unrelated item features.

The jump-edge request is present at 1.9. It remains the same rule across the checked Feather releases and the available 1.14 Yarn/Feather/Mojmap mappings through 1.14.4: current jump true, prior jump false, airborne, strict negative vertical velocity, not already fall-flying, and not ability-flying; then a usable Elytra in the chest slot causes a start-fall-flying packet. The request block itself does not write the local fall-flying bit. In the 1.14 client sources, the request is followed by a sample of `isFallFlying()` into the prior-state field. No water, passenger, or climbing check is in this earlier client request branch.

| Exact release and mapping | `LocalClientPlayerEntity.mobTick` / `ClientPlayerEntity.tick` / `LocalPlayer.aiStep` request slice |
| --- | --- |
| 1.9 Feather | `LocalClientPlayerEntity.java` 703–708 |
| 1.9.1 Feather | same file 704–709 |
| 1.9.2 Feather | same file 704–709 |
| 1.9.3, 1.9.4 Feather | same file 707–712 |
| 1.10, 1.10.1, 1.10.2 Feather | same file 730–735 |
| 1.11, 1.11.1, 1.11.2 Feather | same file 742–747 |
| 1.12 Feather | same file 759–764 |
| 1.12.1 Feather | same file 759–764 |
| 1.12.2 Feather | same file 764–769 |
| 1.13 Feather | same file 773–778 |
| 1.13.1, 1.13.2 Feather | same file 777–782 |
| 1.14 Feather | same file 709–714 |
| 1.14 Yarn | `ClientPlayerEntity.java` 697–702 |
| 1.14.1 Yarn | same file 697–702 |
| 1.14.2, 1.14.3 Yarn | same file 696–701 |
| 1.14.4 Feather | `LocalClientPlayerEntity.java` 708–713 |
| 1.14.4 Mojmap | `LocalPlayer.java` 707–712 |

Across these exact files, the request order is: save the previous jump state, refresh input, process ability-flight logic, then evaluate the Elytra request. The representative 1.9.2 Feather source has `bl = input.jumping` at line 642, `input.tick()` at 646, and the request at 704–708. In 1.14.4 Mojmap, `bl` and `input.tick(...)` are at 625–629, the request at 707–712, and `wasFallFlying = isFallFlying()` at 714. The old-state comparison therefore describes a rising edge, not a held-jump condition.

The chest predicate is item identity plus usability. 1.9.2 Feather `LocalClientPlayerEntity.java` 704–708 checks non-null chest stack, Elytra identity, and `ElytraItem.canFly`; 1.14.4 Mojmap `LocalPlayer.java` 707–710 checks `Items.ELYTRA` and `ElytraItem.isFlyEnabled`. The damage gate remains `damage < maxDamage - 1` in the checked 1.14.4 and 1.15.2 `ElytraItem` sources.

## Change by 1.15 and the 1.15.2 climbing gate

In 1.15 and 1.15.1 Mojmap, `LocalPlayer.aiStep` still captures old jump state and ticks input first (`LocalPlayer.java` 632–635). The outer request condition is current jump, no ability-flight transition in this tick, a rising edge, ability flight off, and not a passenger (`LocalPlayer.java` 715–720). It no longer requires negative Y. It checks chest Elytra identity/usability and calls `Player.tryToStartFallFlying()` before sending the packet. The 1.15 and 1.15.1 `LocalPlayer.java` sources have identical hashes for this slice.

`Player.tryToStartFallFlying()` in 1.15/1.15.1 (`Player.java` 1586–1600) and 1.15.2 (`Player.java` 1578–1592) requires not grounded, not already fall-flying, and not in water; it rechecks chest Elytra usability, calls `startFallFlying()`, and returns true. `startFallFlying()` sets shared flag bit 7 immediately. Thus a successful 1.15+ local request changes the local client state before the packet is sent. The caller then samples that bit into `wasFallFlying` (`LocalPlayer.java` 722 in 1.15.2) and calls `super.aiStep()` at 778.

The immediate movement consequence is source-visible: 1.15.2 `LivingEntity.aiStep` performs the normal jump step, calls `updateFallFlying()`, then calls `travel()` (2265–2287). `updateFallFlying()` only writes the shared bit on the server (`LivingEntity.java` 2299–2317). On the client, the bit written by `Player.startFallFlying()` remains readable by `travel()`. In the dry path, `LivingEntity.travel` selects its fall-flying branch when that bit is set and updates velocity before moving (1831–1871); ordinary `Player.travel` delegates to this superclass path (1448–1475). This is local same-tick state/branch evidence, not a runtime trajectory claim. In 1.14.4 the request branch only sends the packet and samples the existing flag; it has no call to the local setter, so this same-tick local state change is absent there.

Water and mount gates differ at the client request point. The 1.14.4 branch has no water or passenger predicate. In 1.15+, the helper rejects a wet player before local flag write or packet send, and the outer gate rejects passengers. The water predicate is `isInWater()`; 1.15.2 `Entity.java` exposes it from `wasInWater`, so this gate reads the entity's tracked water state. 1.15 and 1.15.1 have no climbing condition. 1.15.2 adds `!onLadder()` to the outer gate (`LocalPlayer.java` 715–718).

`LivingEntity.onLadder()` in 1.15.2 (1226–1250) returns false for spectators; otherwise it reads the feet block state at `new BlockPos(this)` and accepts ladder, vine, scaffolding, or an open trapdoor above a ladder with matching facing. The new gate therefore blocks an otherwise valid fresh jump edge while the player's feet occupy an era-existing climbable block. It runs before chest equipment lookup and before the helper can set bit 7.

## Reachable state witnesses and mounted case

A survival player can jump from flat ground in a usable chest Elytra, release jump, and press again while still airborne with nonnegative Y velocity. With no ability-flight transition, passenger state, water, or ladder, 1.14.4 rejects the second edge on `y < 0.0` and does not set the local flag. 1.15/1.15.1 pass the outer gates, the helper writes bit 7, and local travel sees fall-flying that tick. This isolates the removed negative-Y gate and the new local flag write. The same witness applies in 1.15.2 when the feet block is not climbable.

A separate era-existing climbing witness is an airborne player in the feet-block range of a wall ladder (or vine/scaffolding), with a fresh jump edge and usable Elytra. 1.15/1.15.1 have no climbing rejection; 1.15.2 rejects before gear lookup and local flag write. This is a source-predicted branch difference, not an observed game run.

The earlier Feather branch has no passenger check syntactically, but its obvious mounted-descending candidate is not a closed witness: 1.9.2 `LocalClientPlayerEntity.mobTick` evaluates the request before the riding jump-charge block (704–710, then 724 onward), while the ordinary `Entity.rideTick` path zeroes rider X/Y/Z velocity before calling `tick()` and then repositions the rider (`Entity.java` 1423–1435); `LocalClientPlayerEntity.rideTick` delegates to it (760–767). That path cannot satisfy the strict negative-Y request gate. I therefore report the missing old passenger predicate as source evidence only, not as a proven mounted request or local glide. By 1.15 the explicit passenger gate closes this question at the request site.

## Proven limits

The checked stable Feather sequence establishes the classic negative-Y packet-request rule for the exact releases listed above, beginning at 1.9; 1.8.9 lacks the scoped Elytra/fall-flying sources. The client local-flag transition is absent in 1.14.4 and present in 1.15 and 1.15.1. The `onLadder()` rejection is present in 1.15.2 and absent in the checked 1.15/1.15.1 caller. These are exact-release comparisons, not claims about uninspected snapshots or all movement operations. No runtime parity was tested, and no full-pair coverage audit or freeze is claimed.