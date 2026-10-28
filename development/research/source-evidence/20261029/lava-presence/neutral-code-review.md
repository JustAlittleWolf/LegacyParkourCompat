# Independent code review: lava-presence candidate

## Verdict

**ACCEPT** for the neutral lava-presence transition code in commit `c3a8af3023ede96cf1e2ebcc3952e560c588d7b3` (`feat/af-lava-presence-impl-20261029`). Acceptance is limited to this candidate's hook contract, historical transition behavior, and automatic change registration. It does not establish product integration, runtime parity, or full 1.16.1→1.16.2 coverage.

## Bound evidence

The supplied source report, independent audit, design contract, and design review were rehashed and match their requested SHA-256 identities:

- Report: `d3bbd6c106a0051396aca17aa0269e17fc217542a44f07656ada428a99454b05`
- Audit: `02e6604f2c3115247747c43eb06c0fe041fd017a6f4cbc88b10442b4b1466b6e`
- Design: `6c9f2803458d19385097007a42e0abe2562fbeac2f8cd68f11058a113db307a4`
- Design review: `1273b0af53f3471ab3fb77e4eb5b421cb122e3756c520be37a6c5a2ab54dd5bd`

## Findings

- `HistoricalLavaPresence` implements the read phase by returning the supplied stored latch, and the reset phase by returning `false`. Its observation phase gates on `lavaTagged`, computes `blockY + fluidHeight` into a `float` before comparing, and applies the source expression `entityBox.minY < surfaceY || surfaceY > entityBox.maxY`. A qualifying callback sets true; later non-lava callbacks return the existing value and do not clear it.
- The 1.16.2+ query path remains native: `NativeLavaPresenceBoundary` returns empty for each operation at `V1_15_2`, while the historical implementation is annotated `V1_16`. The resolver chooses the closest applicable later change per interface, so `V1_16` wins for 1.16 and `V1_15_2` wins for 1.15.2 and earlier profiles without a closer provider. The boundary is a no-op and does not recreate cached fluid-height behavior.
- Both concrete changes have public no-argument constructors, `@MovementChange` version annotations, and implement the single `@MechanicType` `LavaPresenceHook`. Client discovery recursively registers annotated hook interfaces implemented by annotated change classes; no catalog edit is required.
- The comments identify the Mojmap source versions and relevant methods for the old latch and newer query/consumers. The source report and design review bound the old interval to 1.16/1.16.1 and native interval to 1.16.2–1.16.5, retaining the documented source-identity caveats.

## Product integration requirements and missing capabilities

This candidate intentionally supplies pure transition operations and does not add a product mixin or guess an injection seam. Product integration still needs:

1. Boolean storage owned by the logical-client local-player instance, initialized to false. The caller must pass this same stored value to `read` and `observe`, and write present results back; `reset` must clear it.
2. A query interception limited to the eligible local player/profile so reads use `read(storedLatch)` while servers, remote players, non-player entities, disabled profiles, and unresolved profiles keep native behavior.
3. Lifecycle call sites that reset only at the exact post-collision/post-fall-damage point before `Entity.checkInsideBlocks`, and observe callbacks in the existing source-ordered scan, preserving the existing `hasChunksAt` admission and actual post-movement box. The candidate does not itself provide scan bounds, callback capture, or a state carrier.
4. Exact-version product mixin/accessor evidence for obtaining the 1.16.1 lava-tag and fluid-height callback inputs without running a duplicate scan. Keep the 1.16.2+ fluid-height cache, `firstTick`, water/lava current order, eye-fluid state, and damage/fire path untouched.

These are integration prerequisites rather than defects in this neutral transition candidate. No product checkout, build, tests, or runtime were inspected or run.
