# Independent sleeping pitch source audit

## Input identity

- Immutable draft: `sleep-pitch-source/comparison.md`; SHA-256 `ff5165c4ffe59dd62e521bb66b97636617224049d5a1a305d7f71b2ee895d867` (verified before review).
- Scope: exact decompiled source and local provenance for 1.13.2 → 1.14.0, plus stable aliases 1.14.1–1.14.4. No product code, wiki, Git history, or research/backlog material inspected.

## Provenance audit

- 1.13.2 Feather: `decompiled_minecraft/1.13.2/ornithe-feather.provenance.json` identifies `net.ornithemc:feather-gen2-1.13.2+build.2` and the local canonical source run. The relevant `LivingEntity.java` SHA-256 is `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c`, matching `ornithe-feather.sources.sha256`.
- 1.14.0 Feather: `decompiled_minecraft/1.14/ornithe-feather.provenance.json` identifies `net.ornithemc:feather-gen2:1.14+build.2`. `LivingEntity.java` SHA-256 `1db4dd1aa95a06c48511e5567c0d3836f2a6e5a12a5cf03ab5c892e429198b79` matches `ornithe-feather.sources.sha256`.
- The 1.14.1, 1.14.2 and 1.14.3 Yarn provenance is present as hidden nested files `decompiled_minecraft/<version>/yarn/.decompile-provenance.json`. These identify Yarn `1.14.1+build.10`, `1.14.2+build.7` and `1.14.3+build.13`, respectively. Each local `sources` hash was checked against the corresponding `LivingEntity.java`, `PlayerEntity.java` and `ClientPlayerEntity.java`; all nine matched.
- 1.14.4 Feather: `decompiled_minecraft/1.14.4/ornithe-feather.provenance.json` identifies Feather `1.14.4+build.2`; `LivingEntity.java` SHA-256 `2cccf4331ce9e62013eeb8e96163e5e146e1291619eb998ce9867daa87c02b61` matches `ornithe-feather.sources.sha256`.

## Source comparison

- 1.13.2 Feather `net/minecraft/entity/living/LivingEntity.java`, `tick()` (around line 1689; file SHA-256 above): the examined complete `tick()` has no sleeping-conditioned `pitch = 0.0F` write. The corresponding `PlayerEntity.java` (SHA-256 `4ed22f6c5a3c55d67eed782070ac722201df4d624adbc90779f1fd29c2876633`) was also searched; no such reset exists there.
- 1.14.0 Feather `net/minecraft/entity/living/LivingEntity.java`, `tick()` lines 2113–2115: after `tickMovement()` and post-travel bookkeeping, `if (this.isSleeping()) { this.pitch = 0.0F; }`. The condition has no logical-side gate. This establishes the difference between these exact endpoints, not the patch release inside the interval.
- Stable aliases repeat the same post-travel write: 1.14.1 Yarn `net/minecraft/entity/LivingEntity.java` lines 2097–2099 (SHA-256 `331e2d7dfa936a6f7197910ba374c4a60b479c8565ff7c64a4d6a9e1e8b52dda`); 1.14.2 Yarn lines 2103–2105 (SHA-256 `e8224e7df98ccf0986bb390f35e64293263d17b9ab1dbdfeb661c65d28b6ee78`); 1.14.3 Yarn lines 2113–2115 (SHA-256 `e8aed9c63c0dfbe88e4b9e11c58881bc6e04b9cbaecbde5e3606e3afa406d72b`); and 1.14.4 Feather lines 2120–2122 (SHA-256 above).

## Movement relevance and bounded witness

- In 1.14.1 Yarn, `LivingEntity.tick()` calls `tickMovement()` before the write (the movement call is at line 2023). The pitch reset therefore cannot change that tick's already-completed travel. `PlayerEntity.cannotMove()` at lines 962–965 includes `this.isSleeping()`, restricting ordinary voluntary player movement while asleep.
- The local-client path is reachable: `ClientPlayerEntity.tick()` lines 172–184 calls `super.tick()` and then sends movement packets. In its camera-player branch, lines 212–226 compare `this.pitch - this.lastPitch` and send a `PlayerMoveC2SPacket` containing `this.pitch` when the look changed. Thus if a loaded local player enters the tick asleep with nonzero pitch, this write produces a zero pitch delta for the client movement packet. In the vehicle branch, line 176 also sends a LookOnly packet with pitch. This is a bounded, source-supported player movement-state/network witness; it is not merely a rendering write.
- Later movement can read pitch: 1.14.1 `LivingEntity` fall-flying travel at lines 1774–1788 uses `this.pitch` to derive glide motion, behind `isFallFlying()`. That operation is movement physics. The pitch reset is after travel, so it can only feed a later invocation. The reviewed slices do not prove a concrete sleeping-to-fall-flying continuation or establish that a sleeping player can retain that state; no such trajectory is claimed.
- Classification: direct player orientation state and movement-packet behavior, with a possible later fall-flying movement input; no current-tick movement-physics delta is established. The visible camera orientation also changes, but “camera/rendering only” is too narrow because the value is serialized in player movement packets.

## Limitations and terminal status

- The exact introduction is bounded to after 1.13.2 and by 1.14.0; intermediate patch releases were not source-compared to refine it.
- This is a bounded independent audit of the assigned pitch-reset slices, not a full-pair movement inventory or proof of all sleep/wake/flight state reachability.
- **COMPLETED** — the immutable draft hash, canonical source provenance and relevant source hashes were verified, and the movement-state classification was bounded as above.