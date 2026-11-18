# Signed correction: swim-pitch vertical gate

**Applies to original report SHA-256:** `ea02cb51cd7f723fac3233c09717cf2f9f22d3ac1328c66a3278b5efdf805f7b` (`c-swim-pitch-vertical-1217-1218-source/report.md`).

**Bound audit verdict SHA-256:** `5fc67256e5c87ed98b6ad3a88a9dafff03bbab1105685695cf9d0142c8f5a9a5` (`c-swim-pitch-vertical-1217-1218-audit/verdict.md`).

## Correction

In both exact Mojmap releases, `Player.travel(Vec3)` uses the gate at `decompiled_minecraft/1.21.7/mojmap/net/minecraft/world/entity/player/Player.java:1445–1446` and the byte-identical 1.21.8 file:

```java
lookY <= 0.0 || jumping || !level().getFluidState(BlockPos.containing(x, y + 1.0 - 0.1, z)).isEmpty()
```

The original report inverted the last disjunct when describing when the velocity write runs. If `lookY` is positive and `jumping` is false, the correction runs only when the fluid state sampled at the pre-super position is **nonempty**. If `lookY <= 0.0` or `jumping` is true, either earlier disjunct allows the write regardless of that fluid state. An empty state does not satisfy the third disjunct.

This corrects only the gate's reachability description. It does not change the source-level Y formula, the sample/write order, the byte-identical 1.21.7→1.21.8 comparison, or the bounded no-difference conclusion.

**Signed:** `/root/c_swim_pitch_vertical_1217_1218_source`, source comparison worker. 2026-11-18.
