# Independent claim review: 1.20.3 → 1.20.4

## Bound revisions and inputs

Reviewed author draft source/comparison.md, raw SHA-256 98da72622791eaa98392a970833ac80ee6f005ef522bd090f016201494f10c5d. The independent blind freeze remains unchanged: blind-audit/frozen.md, raw SHA-256 a9fd27434b2393fcef4bcfff80f62ae103482adbda3535e40a5121bfd5c7ccb9.

The draft endpoint identity claims were checked against the exact canonical Mojmap trees and both provenance/source manifests. The 1.20.3 embedded provenance digest is c3d605e55b287e800a86af8567a75edcb009d1a9a1744cfcb4e6f47667dcee0f; 1.20.4 ready/provenance/source-manifest values match the assigned pins in the draft. All source files listed below matched their actual side-specific source pin and had equal full-file SHA-256 across endpoints.

| Source used for this review | SHA-256 in both versions |
|---|---|
| net/minecraft/client/MouseHandler.java | a75d99f45510c6689ddd7b241b28033b07ccbedc59e8a4288fc15d367bcc0b56 |
| net/minecraft/client/Minecraft.java | 4c672a48906a76408bfbf77fda303d6cead7b22c6e4632aed7a5d8182e057b0a |
| net/minecraft/util/thread/BlockableEventLoop.java | 823f4da3c37d7e69cc7d90441081a30614ea9e0b722f67e482d50fb374293489 |
| net/minecraft/util/thread/ReentrantBlockableEventLoop.java | 72da2dd66931993ef2b5c039712dbe84f1ba24e225703f0c6f24dee1d347b2f1 |
| net/minecraft/world/entity/Entity.java | 07383522bff169938136638ef8c3244ca511b56ca4266913524f99f9821331b9 |
| net/minecraft/world/entity/LivingEntity.java | f7bc53db24c1798f19f9bd6f6356c86d5e560e9c8e8aac60decaf15cc785e07d |
| net/minecraft/world/entity/player/Player.java | 218da60bc4f8c9279f56eb87e2cf2c0af79562fbe3d818a408429c656efb95dd |
| net/minecraft/client/player/AbstractClientPlayer.java | 759f8f03b89dab1f6b65c73e1e25a68ae6a6083cb49b5d2719136edda7edf696 |
| net/minecraft/client/player/LocalPlayer.java | bb5cbfb03656a1866bb77c00431618befe792081223b35db4bd121ecbb151fd5 |
| net/minecraft/world/item/ElytraItem.java | fa4d677a5b109ebf323458f88f05eacb9ffdeca66d952c1a5eb8a7114a911889 |
| net/minecraft/util/Mth.java | f211066a32f5fd2ff3605d0db66f79d7811d7c90ea205caf6b5ce957c0652075 |

## Verdicts

### Callback arithmetic and gates — ACCEPT, bounded

The cited MouseHandler setup/onMove/turnPlayer path is supported by identical full-file hashes. Registration wraps mouse movement in minecraft.execute; when onMove executes for the active window, it accumulates coordinate deltas, calls turnPlayer, then stores the new cursor coordinates. The selected captured, active-window, finite, smooth-off, unscoped branch uses the sensitivity factor and operation order stated in the draft. Elapsed time is computed and stored but does not enter that branch's delta arithmetic. The finite witness avoids setter/error paths for invalid rotation.

### Virtual pitch setter and clamp — ACCEPT

The cited Entity.turn order and setter resolution are correct: float casts and 0.15F multiplications, virtual pitch write, yaw write, pitch clamp through a second virtual pitch write, then history updates/clamp. LocalPlayer inherits through AbstractClientPlayer and Player; none declares setXRot. The inherited finite setter assigns pitch, and the same source bytes establish endpoint equivalence.

### First active Elytra move — ACCEPT with witness tightened

The Elytra path is distinct from the previously frozen creative-flight path. The creative branch is Player.travel guarded by abilities.flying && !isPassenger(); an Elytra witness has abilities flight off and reaches the separate LivingEntity.travel fall-flying branch. Evidence for the Elytra claim does not depend on the creative branch.

The draft correctly traces the start and consumption path: LocalPlayer.aiStep accepts the jump edge only when mayfly-toggle handling did not consume it, abilities flight is off, the player is not riding or climbable, and the chest Elytra passes ElytraItem.isFlyEnabled. Player.tryToStartFallFlying additionally requires airborne, not already fall-flying, not in water, and no levitation; it rechecks the chest Elytra and sets shared flag 7. In the subsequent LivingEntity.aiStep, updateFallFlying rechecks flag 7, airborne/passenger/levitation gates and the enabled Elytra before travel. A local player's isEffectiveAi() returns true, so Entity.isControlledByLocalInstance() permits the local travel branch. With abilities flight off, Player.travel delegates to LivingEntity.travel; in LivingEntity.travel, water and lava movement branches precede the isFallFlying() branch. That branch reads look direction and pitch, applies the lift/dive calculations in source order, damps velocity, then calls move.

Tighten the concrete witness to state that the player is not swimming and outside both water and lava. The start predicate itself does not exclude lava, while the travel method's lava branch precedes Elytra movement. Also retain airborne, on-foot, awake, not levitating, abilities flight off, enabled Elytra, locally controlled, and accepted jump-edge conditions. The awake condition excludes the sleeping pitch reset. Under this explicit witness, the branch is reachable and uses the callback-updated, clamped pitch/look direction. Its full owning files and Mth are byte-identical across the endpoints.

## Scheduler boundary and residuals

Source-defined ordering is established only after the callback runnable executes: onMove invokes turnPlayer immediately in its body, and the later local-player tick runs LivingEntity.aiStep before travel. Minecraft.runTick drains scheduled tasks before its client ticks; BlockableEventLoop.execute can run inline on its owner thread or enqueue work from another thread, and the reentrant loop can defer execution while already running a task. Therefore this review does not claim that any arbitrary real mouse event necessarily arrives or executes before a particular player tick. The witness is conditional on the finite movement callback being processed before the Elytra travel call whose pitch it is said to affect.

Accepted source result: no version difference in the bounded callback, rotation, or reachable Elytra movement path. No intermediate patch claim or runtime parity is established. Full tick inventory, other movement paths, and complete collision/world-provider coverage remain unresolved.