# Independent source audit: keyboard non-controlled-camera residual

**Status:** COMPLETED  
**Verdict:** NO DIFFERENCE in the audited non-controlled-camera `xxa`/`zza` path; the cited comparison supports a relocated damping operation, not an independently established behavior delta.  
**Frozen comparison SHA-256:** `5bf24d20c0764dd13668c440ecfe115a345b40561d359782c7f9a509cb0f18fb`

## Evidence binding

The frozen comparison hash above was recomputed from its assigned file. I independently recomputed the SHA-256 digests for both relevant Java source files on all three assigned sides. All six matched the comparison's table:

- 1.21.4 Mojmap `LocalPlayer.java` `145686EBDC7F0D12A64070309073665EB8695B7E09911A723E86113A77D04611`; `LivingEntity.java` `E62CE650AF0A5AC97E4D0A2BA7CAD4BA68A8D098525609F8B5B6976A8AB0AE30`.
- 1.21.5 Mojmap `LocalPlayer.java` `F1FCFED4A938732361E7AD951F93E9B73B02320EE56F0B219E2B3C7ACDBFA2EF`; `LivingEntity.java` `A8AED863D4FDC515C751DD2878A8BB9C13179228CB8DBB50EDF1D19CD5404271`.
- 26.2 unobfuscated `LocalPlayer.java` `8D089AA09217E3607B38590F7C1623385562800943AC6DFD3D17804E041DA6D6`; `LivingEntity.java` `7FFD9C70966EDC50C9CB4D9A8FE17A518E2678FF44C8026E763D0B94AC0AE51A`.

## Claim audit

1. **1.21.4 gate:** `LocalPlayer.serverAiStep()` calls `super.serverAiStep()` and copies `input.leftImpulse` / `input.forwardImpulse` to `xxa` / `zza` only inside `isControlledCamera()` (`LocalPlayer.java:607–622`). A false gate therefore skips both copies. The subsequent `LivingEntity.travel()` multiplies the existing fields by `0.98F` in x-then-z order before movement input is consumed (`LivingEntity.java:2767–2774`).
2. **1.21.5 and 26.2 fallback:** `LivingEntity.aiStep()` dispatches `applyInput()` virtually (`1.21.5 LivingEntity.java:2712`; 26.2 `:3086`). `LocalPlayer.applyInput()` enters the keyboard-copy/`modifyInput` branch only for the controlled camera and otherwise calls `super.applyInput()` (`1.21.5 LocalPlayer.java:608–620`; 26.2 `:692–705`). The base fallback multiplies existing `xxa`, then `zza`, by `0.98F` (`1.21.5 LivingEntity.java:2806–2809`; 26.2 `:3180–3183`). In these newer sources `travel()` no longer performs the 1.21.4 axis multiplication. Thus, for the claimed false-camera axis path, the same stored axes receive the same ordered float scaling before their travel use; no fresh keyboard axes are copied in either false-camera branch. The placement moves earlier in the tick, but this audit found no intervening write to these axes in the specified fallback path that would establish a different result.
3. **Reachability boundary:** The comparison identifies the vanilla spectator camera-target route (server target selection, client camera packet, `Minecraft.setCameraEntity`) as a concrete way to make the equality gate false. The local player remains on `ClientLevel`'s normal entity tick path; `LivingEntity.aiStep()` reaches the virtual fallback, and the normal player travel path follows. This establishes reachability of the narrow local-player fallback path in that spectator-camera state. It does not establish other ways to detach the camera or extend the finding to arbitrary camera manipulation. The direct consequence supported here is damping of already-stored local-player axes, not copying or processing the newly polled keyboard vector.
4. **Controlled-camera changes excluded from this verdict:** 1.21.5/26.2 `modifyInput()` scaling and square correction occur only after the true-camera gate. They do not establish a false-camera delta. The controlled-camera `0.98F` input change is outside this bounded non-camera audit.

The frozen comparison's source facts and line references are accurate for this bounded claim. Its language that the damping is “historical” in 1.21.4 and occurs in the newer fallback is accurate, but those facts alone do not constitute a non-camera behavior difference. Do not accept an independent non-camera keyboard finding from this slice without additional evidence of an intervening field write, changed consumer, or other changed gate.
