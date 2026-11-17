# Candidate result: FAILED

Scope: implement the accepted 1.9–1.9.4 flight/sneak horizontal input rescaling in this sanitized export only.

No source files were changed. No patch artifact is emitted because the neutral dispatch contract does not establish a safe exact implementation seam.

## Verified export identity

- Product base revision: `4862322ea6c4d6e847e30a8e57f467c8eded2ddb`
- Content manifest SHA-256: `983D5DE5AC8E50DAF405BB7F7C3B621485D1922FB0F9C52AA793209AA57FC6A2`
- All files listed in `manifest.json` matched their recorded SHA-256 values before this report was written.
- Accepted canonical finding SHA-256: `e1f0aa7d6609365838aea49b6b6898b77e47cb1a5b7863ad5bc031cc6079cf52`
- Accepted source report SHA-256: `4116f0bcbeab6343c9cb8df34a6de335afae7cb3cc897da60e5e23b130e008d2`
- Independent ACCEPT verdict SHA-256: `654254F567817B3667442A013A7A447564EEDFAD6B17CDDD67D5A2EA2D0DBB71`

## Blocking contract gap

The accepted historical source establishes this ordered operation for 1.9 and 1.9.4: keyboard sneak scaling `(float)(axis * 0.3)`, then the optional item-use multiplier `0.2F`, then the flying/camera/sneak division `(float)(current / 0.3)`. The resolver also requires a V1_8_9 identity provider and explicit V1_9 and V1_9_3 providers if this operation is represented as one independently resolved hook; an annotation at V1_9 alone would also resolve for V1_8_9 and would not cover the V1_9_3 boundary as requested.

The supplied 26.2 seam is `LocalPlayer.applyInput()V` around `modifyInput(Vec2)Vec2`. Its native input path produces raw axes, then `modifyInput` applies `0.98F`, item-use scaling, moving-slowly scaling, and square adjustment. Flight sets `crouching=false`; held shift is available via `LocalPlayer.input.keyPresses.shift()`. Therefore a post-native `/0.3` would apply an unjustified 3.33x boost, while synthesizing a pre-scale before native processing would place native transforms between the historical stages. Neither available option proves the accepted exact operation order and rounding while preserving unrelated native behavior.

The missing neutral contract is the effective selected-profile input pipeline and exact seam that exposes the historical pre-item-use axis, applies the `0.3` keyboard-stage float cast, preserves the native/item-use stage in the required order, then applies the `/0.3` float cast under the historical flying + controlled-camera + held-sneak gate. Until that stage contract is established, no hook, provider, mixin, or mixin-list change is safe to author.

No build, tests, runtime, game/server launch, network access, or Git operation was performed.
