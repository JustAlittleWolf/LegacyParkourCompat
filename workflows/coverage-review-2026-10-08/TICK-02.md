# Independent source review: TICK-02

## Exact snapshot reviewed

- Decision: **accepted for the bounded source-level horizontal-argument change** under the stated flight, camera, sneaking, no-item-use and no-riding guards, with at least one nonzero keyboard movement axis.
- Finding commit: `20d100ee7d4c1f64bb905e5b05b242b42625af6e`, `workflows/source-campaign-2026-10-07/1.8.9--1.9.4/findings/TICK-02-flight-sneak-input-rescaling.md`, SHA-256 `2e81aceef1bde41ac6597e28c2fa5f6f3e457e9a8ff22163f38e3b07546d2693` (verified from the immutable Git blob).
- Owner snapshot event: `SNAP-TICK-02-01`, recorded by run commit `1dbafbb7899c57ddf1718e421aa2c32e283fc7c7`, `workflows/source-campaign-2026-10-07/1.8.9--1.9.4/run.md`, SHA-256 `410733b99e194916c399cd0a113f433a4fc8323afaea1c879d68b22aa791222e`.
- Source manifests: A `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004`; B `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248a37a27a1b4d77`.
- Immutable source artifacts: revision `feather-r1-2026-10-07`; A SHA-256 `5c4cff3e4ac10ea1e1da166279133801ad304b4557d5ee4a77a2430977afb6a5`; B SHA-256 `fbcf50795566e12b8eab0e733b136ed562c4009d707ef4a7a4994936491816a3`. The original mapped JARs are unavailable, so their identity/equivalence remains unproven; this decision makes no bytecode or runtime claim.

## Independent trace

- Both `KeyboardInput#tick()V` methods derive each axis from pressed keys as `-1`, `0`, or `1`, then, while sneaking, multiply each by `0.3` and cast to float. A: lines 34-36, SHA-256 `a5e5b2033322f8867cd845e4095cea7a82888f6382cbfafefc559cf9ba3a76dd`; B: lines 46-48, SHA-256 `7be11425906be051c83e275f359816546e4677b16d212156380e8d2e9258654a`.
- A `LocalClientPlayerEntity#mobTick()V` takes the `abilities.flying && isCamera()` branch and changes vertical velocity for sneak, leaving the two scaled input axes untouched (lines 596-603). B uses the same guard and, when sneaking, divides both axes by `0.3` before the same vertical adjustment (lines 715-725). Source SHA-256: A `1762b116e6b06d682b7daaa0fc8cce39b0ff455b3db8cf79dab03ac74f6c4053`; B `8aaf711948b7602c2e6c015a37e36ed06073d39727d999d80480b4910b704f5d`.
- The input-to-travel handoff is reachable: `LocalClientPlayerEntity#serverTickAi()V` copies the input axes into sideways/forward speed under `isCamera()` (A lines 472-475; B lines 588-591); `LivingEntity#mobTick()V` invokes that player path before passing those speeds to `moveRelative` (A line 1450; B line 1711); and `PlayerEntity#moveRelative(FF)V` takes the creative-flight branch and forwards both arguments to its superclass (A lines 1279-1294; B lines 1372-1390). These cited files’ hashes match the immutable source manifest.
- For a nonzero axis produced by keyboard input, A supplies `±0.3F`; B computes `(float)(±0.3F / 0.3)` and supplies `±1.0F`. A zero axis remains zero. The finding proves different arguments entering flight movement, not a final velocity or displacement; speed normalization, velocity cutoff, collision and other movement state can affect later results.

## Scope and uncertainty

The conditional source difference is accepted. The first changed release remains unknown within `(1.8.9, 1.9.4]`. The snapshot’s exclusions of collision outcomes, velocity cutoff and final displacement are respected. No implementation, wiki, test, build, client or runtime evidence was used.
