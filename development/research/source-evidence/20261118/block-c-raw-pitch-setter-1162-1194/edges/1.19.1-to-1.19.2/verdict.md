# Independent source verdict: raw pitch via `Entity.turn`, 1.19.1 → 1.19.2

## Verdict

**No source change in the assigned operation; no finding for this pair.** In the exact Mojmap source trees, the bounded caller, pitch setter, clamp, and immediate active-local Elytra read are byte-identical across 1.19.1 and 1.19.2. The temporary unbounded pitch value can be finite and exceed 360 degrees, but the ordinary `Entity.turn` path clamps current and previous pitch before returning. A subsequent active-local Elytra travel reads the returned current pitch, not that temporary value.

This verdict covers only the assigned pitch-turn slice and its ordinary local input and next movement read. It is not a full 1.19.1/1.19.2 movement-pair coverage freeze.

## Exact source identity and integrity

Source: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\<version>\mojmap\`, Mojmap. The version-root ready/provenance records and manifests were present. Their hashes and published input identities:

| Release | Ready marker SHA-256 | Provenance SHA-256 | Source-manifest SHA-256 | Artifact-manifest SHA-256 | Client SHA-256 |
|---|---|---|---|---|---|
| 1.19.1 | `C7B5373078EB777895A4F4B10D8C99EB289CB14FA1835004C1EB4563B6A8A690` | `DE0DE05299C59D3B05D96E744B27F5A5CEF377880AE4A7B402337AA0FFE00245` | `25A8E35111971BE21A85FEFD567ADA685098F45E35E431163AFDBE0FA66DFDE1` | `AAE6327967E07F5CDD92AC9BFA6D9C606E1BE9A96E24DD30ACAD07FD2425DF41` | `146d4018cfed50c5e77483940653091f186d2eea9ad42fc40939f5c72d6b7f52` |
| 1.19.2 | `90F5A351C60A1AAB160B640567B716BBC563E62D92E7E555DF55C4CE7952492C` | `C0C148D3C3A0092ADC59C29C25F2106F3A5398156A4F266882384804B3534280` | `95BC354AC219B9FE1CA8BF45053EDAD0119A75323D576902D59616A11647AAC2` | `D0197D78578241B8CDDE1123A3DEE64AA279341681A7EDDE9F867689F9BB3490` | `e1ac65de9b471b6916cc457fdcff00c1bafac17027aa79100c4df893b3d956db` |

The artifact manifests pin each release's `client_mappings.txt`, mapped client JAR, and client JAR. For 1.19.1 these hashes are respectively `9b2dfe745719f4de1ce3fb23baeb24e31623c1fcc249e39bbdb068ad7f600236`, `d0a828070e9516eaf0d853b07198a0b27aa006fd68b1b93d66df5aee5b236f4b`, and the client hash above. For 1.19.2 they are `c5db94c44c1ce6c5d3bfce64152831090310c202f4abe4375adbb3454afcec76`, `a257c4c97ceac50fbc069dc6051dff5f0263716547ede05e85c44d675ade592f`, and the client hash above. The 1.19.1 provenance also records Mojang client-mapping SHA-256 `9b2dfe745719f4de1ce3fb23baeb24e31623c1fcc249e39bbdb068ad7f600236` and mapped-client SHA-256 `d0a828070e9516eaf0d853b07198a0b27aa006fd68b1b93d66df5aee5b236f4b`.

For every file below, the current decompiled source bytes match the release's `mojmap.sources.sha256` entry. The per-file SHA-256 is identical between the releases:

| Source file | SHA-256 (both releases) |
|---|---|
| `net/minecraft/client/MouseHandler.java` | `fd2b9d6c1079df233db76139a0060d4449f7b725d66f6805cc9401c2c1ff64fb` |
| `net/minecraft/client/Options.java` | `8970d581c46996909fa655fd90cfcfed2d88d7938812643e87f672fec25bb645` |
| `net/minecraft/client/player/LocalPlayer.java` | `36ae4aabd609b457fffb7a8b14abb50db9ac775857dde1774c0c68a8cf50deef` |
| `net/minecraft/util/Mth.java` | `61727897223a78846a5f1fa270d57aa714c21b3c8d6f2b0e12fea4a07e6c327f` |
| `net/minecraft/world/entity/Entity.java` | `759de9cded43bd882afcf5b7023bcf804d92419acb656b493f3b490c83eb18b6` |
| `net/minecraft/world/entity/LivingEntity.java` | `fee2df5155449098556a138d2530d06b7a35979e43c6aa0ae1fffd6251853e77` |
| `net/minecraft/world/entity/player/Player.java` | `155c5fcfba322d968f3180383e7d283ddeb5edee4e04314906310d4e3ce0ccc1` |

The ready markers' recorded `outputPath` points into `build/movement-campaign-2026-10-07/ready/`, which is not present in this checkout. I therefore verified the materialized `decompiled_minecraft/<version>/mojmap` bytes directly against the exact source manifests and retained the ready/provenance records as artifact provenance; I do not claim the vanished staging paths themselves were inspected.

## Paired source trace

- **Ordinary mouse caller:** `MouseHandler.java:195–201, 208–245`. While the mouse is grabbed and the window active, cursor displacement is accumulated without a clamp (`:196–199`). `turnPlayer` derives the pitch argument from the accumulated Y delta and sensitivity, applies the invert-Y sign, and calls `minecraft.player.turn($$7, $$8 * $$13)` (`:208–245`). The `Options` sensitivity instance has `UnitDouble` and labels 1.0 as the maximum (`Options.java:95–101`); with sensitivity 1.0 and accumulated Y displacement 600.0, the ordinary unsmoothed/non-scoping path supplies about 2457.6 as the finite pitch argument. `Entity.turn` then casts it to `float` before multiplying by `0.15F`, yielding about `368.64F`, a finite post-float delta greater than 360 degrees.
- **Turn argument width and order:** `Entity.java:389–400`. Both arguments are `double`; pitch is the second argument. `float $$2 = (float)$$1 * 0.15F` rounds the argument to binary32 before the binary32 multiplication. The method first calls `setXRot(getXRot() + $$2)` (`:392`), then updates yaw (`:393`), then clamps current pitch via `setXRot(Mth.clamp(..., -90.0F, 90.0F))` (`:394`). Only afterward does it add the raw float delta to `xRotO` and clamp that previous pitch (`:395–397`); the vehicle callback, if any, is last (`:398–400`).
- **First setter and final clamp:** `Entity.java:3055–3064` shows `getXRot` returns the field and `setXRot(float)` rejects nonfinite values but stores any finite float unchanged. It neither wraps nor clamps. `Mth.java:121–127` clamps by testing below-minimum first, then above-maximum. Thus, for an ordinary initial pitch and finite delta about `368.64F`, the first setter can temporarily store a value above 360, but the final current-pitch setter stores `90.0F` before `turn` returns; `xRotO` is separately clamped to `90.0F`.
- **Next active-local Elytra read:** `LocalPlayer.java:581–583` overrides `isEffectiveAi()` to return `true`. Its `tick` delegates to `super.tick()` (`LocalPlayer.java:199–202`), and its `aiStep` delegates through `Player.aiStep` to `LivingEntity.aiStep` (`LocalPlayer.java:893`, `Player.java:500–517`). `LivingEntity.aiStep` reaches `updateFallFlying()` followed by dynamic `this.travel(...)` (`LivingEntity.java:2583–2589`). `Player.travel` delegates to `super.travel` when not in the player ability-flight branch (`Player.java:1464–1494`); `LivingEntity.travel` is active for this local player because of `isEffectiveAi() == true` (`LivingEntity.java:2042–2044`). When `isFallFlying()` is true, the Elytra branch reads `getLookAngle()` and current `getXRot()` (converts pitch to radians) and uses those values in the glide velocity equations (`LivingEntity.java:2107–2129`). This is a real next-tick active-local movement read; it observes the clamped return pitch, not the temporary first-setter value.

## Pair conclusion

The paired source file hashes are identical, and the compared method bodies and caller/read path therefore contain no 1.19.1 → 1.19.2 change in argument width, early cast, finite-setter behavior, clamp order, or Elytra pitch consumption. The transient >360-degree intermediate is source-real for finite ordinary mouse input, but this pair introduces no difference in whether it is immediately clamped before the next local Elytra travel read.

## Phase-two independent cross-check and signed attestation

I rehashed and read only the authorized source-lane report at `c-raw-pitch-setter-1191-1192-source/report.md`; its SHA-256 matches the supplied identity `264194e83a2e984f164df53bd19c9fb4fa57869ef8473ad5eb557474b68feb9b`. Its bounded conclusion agrees with this frozen independent trace: no 1.19.1→1.19.2 change in `Entity.turn`, finite `setXRot`, clamp ordering, or the fall-flying pitch consumer. The source author's `yDelta = 1.0e38` example is valid at the direct method boundary and also produces a finite binary32 delta before the final clamp. It is not a mouse-sized example; the prior trace independently documents a finite ordinary mouse path above 360 degrees.

The additional local-player chain in this report shows `LocalPlayer.isEffectiveAi() == true`, then inherited `LivingEntity.aiStep`/`Player.travel` reach the fall-flying branch on the active client. This complements the author report's `Player.travel` and `LivingEntity.travel` evidence and resolves no disagreement. The author report's stated slice-only limit is retained; this still is not a complete pair freeze.

**Signed attestation:** source-audit worker `/root/c_raw_pitch_setter_1191_1192_audit`, affirming the no-difference verdict and append-only review. The unchanged phase-one report prefix was frozen as SHA-256 `25a8dd6c7575291c8de29177bce9a508356fa9a4bda91c5e911952ec28597a62` (7,375 bytes) before this addendum.
