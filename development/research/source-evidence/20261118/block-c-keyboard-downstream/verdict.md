# Independent keyboard damping and correction scope verdict

Bound draft: `c-keyboard-scope-source/comparison.md`
Bound draft SHA-256: `619ac89357a07c2f0eda7462558b6ea94320a44860957b9d4d063bdb709d632`

## Verdict

**ACCEPT the bounded 1.21.4 → 1.21.5 source scope, with a gate wording clarification.** The examined .3/.4/.5 Mojmap ready markers match their version IDs and mapping namespace; all listed relevant files match their source manifests. Their manifests identify the shared decompileMinecraft batch and toolchain recorded in the bound comparison.

- **Earlier damping:** .3 and .4 `LivingEntity.aiStep` apply `xxa *= 0.98F` followed by `zza *= 0.98F` before constructing the travel vector; neither assignment modifies `yya` (.3 `LivingEntity.java:2755-2757`; .4 `:2765-2774`). In .4 `LocalPlayer.serverAiStep` supplies xxa from lateral input and zza from forward input (`LocalPlayer.java:607-617`). The input producer still emits signed, unnormalized axis values (`KeyboardInput.java:13-34`).
- **Gate clarification:** the .4 `isControlledByLocalInstance()` test belongs to `LivingEntity.travel(Vec3)`'s travel-mode dispatch; it does not guard the earlier xxa/zza multiplications in `LivingEntity.aiStep`. Those multiplications are before the later travel call. Keep the travel dispatch gate separate from the damping operation.
- **New .5 path:** `LivingEntity.aiStep` invokes virtual `applyInput()` before its jump/travel section (`LivingEntity.java:2712`). Base `applyInput()` retains x-then-z 0.98F component damping (`:2806-2809`). Controlled-camera `LocalPlayer.applyInput()` instead transforms the input vector, stores x/y into xxa/zza, and sets jump; its false-camera branch delegates to the base implementation (`LocalPlayer.java:608-620`).
- **LocalPlayer gates/order:** `modifyInput` preserves exact zero, then applies 0.98F; applies 0.2F only for item-use while not a passenger; applies sneaking speed only when crouching or visually crawling; and then invokes square correction (`LocalPlayer.java:623-639`, `:603-605`). Square correction is a separate subsequent float operation, with a nonpositive-length early return, normalization, square-distance factor, unit cap, and rescale (`:641-658`). The checked .4 path has no corresponding correction.
- **Keyboard producer remains a separate change:** .4 stores raw forward/lateral impulses; .5 constructs `(lateral, forward)` and calls `Vec2.normalized()` (`KeyboardInput.java:22-36`). The normalization threshold is `< 1.0E-4F` (`Vec2.java:48-50`). This is distinct from LocalPlayer's later 0.98 damping and square correction.

The draft correctly distinguishes horizontal-only damping from square correction and does not conflate sprint decisions or multiplier-order changes with correction. Its endpoint comparison is source-supported; the exact patch introduction inside the 1.21.4–1.21.5 interval, runtime parity, and complete movement-pair coverage remain open. The only requested edit before reuse is to clarify that the .4 local-instance travel dispatch gate does not gate the earlier damping assignments.
