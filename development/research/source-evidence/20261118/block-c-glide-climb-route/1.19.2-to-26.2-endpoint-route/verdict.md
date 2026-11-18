# Independent source-route audit verdict

## Verdict

**PASS for the bounded endpoint route claim.** The frozen report accurately traces a dry, local ordinary player who enters the relevant tick already fall-flying, with an eligible chest-slot Elytra, airborne/on foot, no levitation or slow falling, and `onClimbable()` true. The evidence supports endpoint reachability and the described same-tick state/call ordering. It does not establish an introduction release, an intermediate patch boundary, an implementation design, or runtime parity.

## Immutable input and source identity

- Input report: `c-glide-active-climb-1192-262-source-route/report.md`; SHA-256 `ac6c82cf3d7bb4d0e65eda567b9d5f8b9f1fa1e9e67f962da15409b9e2a9273f` (matches the assigned frozen digest).
- 1.19.2 Mojmap tree: `decompiled_minecraft/1.19.2/mojmap`; provenance and ready marker both identify version `1.19.2`, mapping `mojmap`. Ready marker source-manifest SHA-256 `95bc354ac219b9fe1ca8bf45053edad0119a75323d576902d59616a11647aac2`; recomputed manifest hash matches. The `artifacts.sha256` entry identifies `1.19.2/client-mojmap.jar` as `a257c4c97ceac50fbc069dc6051dff5f0263716547ede05e85c44d675ade592f`.
- 26.2 unobfuscated tree: `decompiled_minecraft/26.2/unobfuscated`; provenance and ready marker both identify version `26.2`, mapping `unobfuscated`. Ready marker source-manifest SHA-256 `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894`; recomputed manifest hash matches. The `artifacts.sha256` entry identifies `26.2/client.jar` as `40896ee9f1e2bec3c934daac7e93d41e9e3d9c2f8ae0ca366d52ffbfd1afa290`.
- Recomputed source-file SHA-256 values match both the report and their source-manifest entries:
  - 1.19.2 `LivingEntity.java`: `fee2df5155449098556a138d2530d06b7a35979e43c6aa0ae1fffd6251853e77`.
  - 1.19.2 `LocalPlayer.java`: `36ae4aabd609b457fffb7a8b14abb50db9ac775857dde1774c0c68a8cf50deef`.
  - 26.2 `LivingEntity.java`: `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a`.
  - 26.2 `Entity.java`: `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5`.
  - 26.2 `LocalPlayer.java`: `8d089aa09217e3607b38590f7c1623385562800943ac6dfd3d17804e041da6d6`.

## Independently checked route

**1.19.2 Mojmap.** `LivingEntity.aiStep` scales `xxa`/`zza`, calls `updateFallFlying()`, then calls `travel(...)` (`LivingEntity.java:2583–2589`). For the stipulated valid active glide, `updateFallFlying()` retains shared flag 7 on the client and has no climbable test (`2626–2650`). Dry `travel(...)` reaches the `isFallFlying()` arm after water/lava checks, without a climbable guard (`2042–2107`). That arm assigns float `fallDistance = 1.0F` when current vertical velocity is greater than `-0.5` (`2107–2111`), then sets glide velocity and calls its move (`2131–2136`). Consequently the ordinary climb handler is not selected before this glide move. The report also correctly treats the client start request as out of scope: the jump-start predicate excludes climbables and sends a command rather than setting shared flag 7 directly (`LocalPlayer.java:830–837`); `super.aiStep()` follows (`893`).

**26.2 unobfuscated.** `LivingEntity.aiStep` invokes `updateFallFlying()` when already fall-flying before forming input and travel (`LivingEntity.java:3125–3135`). That update calls inherited `Entity.checkFallDistanceAccumulation()` before its server-only block (`LivingEntity.java:3193–3213`); the inherited cap writes `1.0` only if vertical velocity is greater than `-0.5` **and** accumulated `fallDistance > 1.0` (`Entity.java:2902–2906`). The client path therefore performs this pre-travel cap without the server-side flag clear. In dry travel, active flight dispatches into `travelFallFlying` (`LivingEntity.java:2430–2437`); its climbable branch calls `travelInAir(input)` and only then `stopFallFlying()` (`2581–2595`). Ordinary air travel reaches `handleRelativeFrictionAndCalculateMovement`, which applies `handleOnClimbable` before its first `move` (`2468–2476`, `2676–2680`). The climb handler resets fall distance before clamping the delta (`2703–2717`; reset writes zero at `Entity.java:2908–2910`). Thus the climb branch skips the glide velocity update/move, but still performs an ordinary air move; if the prior cap wrote 1.0, climb handling resets it before that move. After air travel returns, `stopFallFlying()` writes flag 7 `true` then `false` (`LivingEntity.java:2597–2600`). The report's start-only note is also supported: local input excludes `onClimbable()` before sending the start command (`LocalPlayer.java:854–858`) and then calls `super.aiStep()` (`914`).

## Limits

This audit checks source identities and the cited causal route only. It assumes `onClimbable()` is true and does not inspect climbable geometry/predicates, fluid internals, starting or packet/server routes, post-tick synchronization, other entities, or other input/effect/equipment states. Source evidence does not prove a player trajectory, accepted finding, implementation cutover, or runtime parity. No test, build, game/TAS/Gym/server launch, source generation, Git/history inspection, wiki research, or product-implementation review was performed.

## Digests

- Input report SHA-256: `ac6c82cf3d7bb4d0e65eda567b9d5f8b9f1fa1e9e67f962da15409b9e2a9273f`.
- Output content SHA-256 (UTF-8/LF bytes preceding this Digests section): 1b10d79172746ffad11ab015aa08dd2da9eb56808474324bca818b657f51bbb9.
