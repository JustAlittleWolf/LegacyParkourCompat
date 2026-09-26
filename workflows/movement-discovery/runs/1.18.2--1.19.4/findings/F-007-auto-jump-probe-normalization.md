# F-007: Auto-jump collision probe uses a different inverse-square-root calculation

- Older version A: Minecraft 1.18.2
- Newer version B: Minecraft 1.19.4
- Mechanic / coverage slice IDs: stage 1 auto-jump trigger; stage 4 collision probe
- Classification: changed behavior
- Confidence: source-confirmed numeric-path change; exact obstacle outcomes not evaluated
- Applicability: local auto-jump is enabled and active, and the horizontal displacement/input vector has squared length greater than `0.001F`
- First changed release: unknown within (1.18.2, 1.19.4]
- Runtime validation: not performed

## Paired evidence

- A `decompiled_minecraft/1.18.2/mojmap/net/minecraft/client/player/LocalPlayer.java`, `updateAutoJump(float, float)`, lines 900–996; SHA-256 `99C2D18BCD23243AFB8F95C5BAFB21FB0BE7EA04AACBB14FCF7BE7CED2C9C095`. The method computes the horizontal vector's squared length and calls `Mth.fastInvSqrt(float)` before scaling the vector used by the facing check and collision probe.
- A `decompiled_minecraft/1.18.2/mojmap/net/minecraft/util/Mth.java`, `fastInvSqrt(float)`, lines 485–491; SHA-256 `32747C5B09FC184BAAE356E39A0088FD66C9F08F69D9E98B67C19E1DE6F1BB2E`. It uses the float bit-hack initial estimate and one Newton correction, with float intermediates.
- B `decompiled_minecraft/1.19.4/mojmap/net/minecraft/client/player/LocalPlayer.java`, `updateAutoJump(float, float)`, lines 891–987; SHA-256 `8E7DA18F42D09FBB994F522C2B0E65FCB2BB83CABB21024360299D44D9674C58`. The corresponding path calls `Mth.invSqrt(float)` before scaling the same probe vector; it retains the same `0.001F` early return, facing threshold `-0.15F`, probe-distance expression, height tests and final `autoJumpTime = 1` gate. `BlockPos` construction was also rewritten, but the A double-coordinate constructors floor through `Mth.floor`, matching B's `BlockPos.containing` implementation.
- B `decompiled_minecraft/1.19.4/mojmap/net/minecraft/util/Mth.java`, `invSqrt(float)`, lines 387–389; SHA-256 `201D17EA024637036761C2703E407D4AB1DC7651A1F4B00A857C09803754C797`. It delegates to `org.joml.Math.invsqrt(float)`. Bytecode inspection of the exact 1.19.4 client-library artifact `org.joml:joml:1.10.5` shows `1.0F / (float)Math.sqrt((double)value)`. The jar SHA-256 is `CAC9F22F83A7AA33EEBDA73C16FF5261E3CB4911B6BAFCF4C79EA486099D0C9A`; `version.json` identifies the coordinate and artifact hash.

## Source-level difference

For the same float squared length, A's `fastInvSqrt` approximate result and B's JOML inverse-square-root result are produced by different binary32 paths. For example, at `$$6 = 0.01F`, A's one-step approximation returns `9.982522F` (`0x411fb869`), while B's JOML helper returns `10.0F` (`0x41200000`). `updateAutoJump` multiplies its horizontal vector by that result, then uses the normalized vector in the forward-dot cutoff, scan endpoint, and side-ray endpoints. The remainder of the inspected scan and trigger predicates is unchanged apart from floor-equivalent block-position construction.

## Consequence and uncertainty

The vector supplied to the auto-jump facing and collision probes can differ numerically, which can shift their endpoints or boundary comparisons for inputs and collision geometry close to a probe threshold. Source confirms the numeric path change; this audit did not calculate a concrete block layout whose trigger outcome changes and did not run a trajectory. Exact introduction release is unknown within `(1.18.2, 1.19.4]`.

## Handoff

This finding covers the inverse-square-root dependency of the auto-jump probe only. Auto-jump input timers, all collision-probe geometry/shape dependencies and other edge-sneaking probes remain separate coverage. Runtime validation is deferred.
