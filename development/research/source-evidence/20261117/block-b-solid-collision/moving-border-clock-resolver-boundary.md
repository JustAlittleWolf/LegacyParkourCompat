# Moving border clock resolver range

The [source review](moving-border-clock-scope-review.md) and [clock change finding](../../../findings/1.21.10--1.21.11/moving-border-collision-clock.md) establish the exact elapsed-millisecond operation in 1.21.4 through 1.21.10 and the 1.21.11 tick-based boundary. The mapped `WorldBorder`, `Entity`, and `ClientPacketListener` sources for those exact releases reproduce the checked manifest hashes. The older versions anchor the incoming lerp at packet handling and read elapsed time in `MovingBorderExtent.getSize`. The new extent caches the previous size on border ticks. The gate remains the projected entity distance followed by the position-within-extent check, and admitted collision bounds retain floor minima and ceil maxima.

The existing elapsed hook now also resolves for 1.21.4 and 1.21.5. The preceding product profile, `V1_21_2`, groups 1.21.2 and 1.21.3 and keeps its earlier native fallback. From 1.21.11 the older hook is ineligible. The existing packet capture, single elapsed-time sample, shape and admission dispatch, and missing-track fallback are reused. Runtime parity remains unverified.

Backlog path relocation:

- Old `src/client/java/me/wolfii/legacyparkourcompat/change/v1_21_5/WorldBorderCollisionBarrier.java`
- New `src/client/java/me/wolfii/legacyparkourcompat/change/v1_21_2/WorldBorderCollisionBarrier.java`
