# Independent bounded source review: 1.21.4–1.21.5 D7 input application follow-up

- Review date: 2026-10-09
- Decision: **REQUEST CHANGES — D7 closure evidence incomplete**
- Reviewed checkpoint: `ce2cb45d14311769c4cde28013c7fe9fd87dce8b`
- Exact parent: `b8c502b89d08df1436b16e5d98e156440a7bad40`
- Reviewed scope: the added D7 controlled-camera input path, direct movement-vector consumer, and D9 discrepancy routing. D8 additions are not reviewed here.
- Pair status: remains `partial`. This review does not revise the bounded acceptance of the earlier `b8c502b8` checkpoint or extend it to inherited coverage.

## Source verification and confirmed observations

The 1.21.4 and 1.21.5 canonical Mojmap readiness marker, source manifest, artifact manifest, and diagnostics hashes match the publication identities in the source run. Every source hash listed in the new checkpoint's additional-source table matches its exact source manifest. I also rechecked the cited inherited `LocalPlayer.java`, `Player.java`, `LivingEntity.java`, `Entity.java`, `Vec2.java`, and `Mth.java` hashes against both manifests.

The controlled-camera comparisons are source-accurate:

- A `LocalPlayer#serverAiStep()` (`LocalPlayer.java:607–618`) calls `super.serverAiStep()` and then copies input axes under `isControlledCamera()` (`620–622`). `Player#serverAiStep()` calls the empty `LivingEntity#serverAiStep()` and updates swing/head rotation (`Player.java:539–543`; `LivingEntity.java:2861–2862`).
- A `LivingEntity#aiStep()` reaches `serverAiStep()` only after the immobility check and under `isEffectiveAi()` (`2727–2735`), then multiplies `xxa` and `zza` by `0.98F` (`2766–2768`) before constructing the travel vector (`2773–2783`).
- B calls `applyInput()` before immobility handling (`LivingEntity.java:2712–2721`). The controlled-camera override (`LocalPlayer.java:608–621`, gate at `660–662`) routes through `modifyInput()` (`623–639`), which applies `0.98F`, then item-use `0.2F`, then the sneaking-speed factor, then square-movement correction. The square helper is `641–658`. The non-controlled-camera route calls `super.applyInput()`; the base method applies `0.98F` (`LivingEntity.java:2806–2809`).
- `Vec2#scale()` multiplies each float component by the factor (A `Vec2.java:22–24`; B `28–30`). The direct `Entity#getInputVector()` consumer is `Entity#moveRelative()` (A `1424–1427`; B `1453–1456`). Its paired vector methods are A `1429–1439` and B `1458–1468`. The called `Vec3#normalize()`, `scale()` and `multiply()` bodies are byte-identical across the pair at `Vec3.java:83–86`, `152–154`, and `164–166`; the exact `Vec3.java` SHA-256 is `62581d3005cc32401545f9229a9c293dfd61c52e7d30f88456c49d2c2867e68d` on both sides. The bounded consumer path has no source delta in those helpers.
- `LivingEntity#travelInAir()` reaches `moveRelative()` at A `2248` and B `2270`. Broader travel branch coverage remains outside this D7 slice.

The initial F-09 concern is real at the source-claim level: B does contain `0.98F` in the controlled-camera `modifyInput()` path. The operation moved from after the A axis transfer to before B item/sneak scaling and square correction, so the source evidence does not establish that the factors are behaviorally interchangeable. The checkpoint correctly preserves the immutable F-09 snapshot and routes the conflicting claim to a separate source-only re-review under D9; this review does not accept, reject, or edit F-09.

## Required correction before D7 closure

The checkpoint records A's caller gate but does not establish that the local-player override passes it. A `LocalPlayer#isEffectiveAi()` override returns `true` at `LocalPlayer.java:483–485`; this is the missing source edge that makes A's `serverAiStep()` transfer reachable through `LivingEntity#aiStep()` on the local player, subject to the already recorded `!isImmobile()` gate. Add that exact evidence and connect it to the controlled-camera gate before marking D7 closed. B's input transfer is called before immobility handling, as the checkpoint states.

The `Vec3` helper evidence above was independently checked during this review but is absent from the checkpoint's D7 evidence table. Record its paired range/hash with the direct consumer closure so the run itself preserves that dependency evidence. The `Vec2#scale()` call is also part of `modifyInput()`'s exact float operation chain; include its paired range in D7 evidence. These are narrow evidence corrections, not newly discovered movement differences.

## Missed route, dependencies, and next action

- Missed route: A `LivingEntity#aiStep()` effective-AI gate → A `LocalPlayer#isEffectiveAi()` override (`LocalPlayer.java:483–485`) → A controlled-camera `serverAiStep()` transfer. The current row stops at the caller gate without resolving this local-player predicate.
- Add the paired `Vec2#scale()` and `Vec3#normalize/scale/multiply()` evidence to the D7 record, retaining exact source hashes.
- Keep D9 open for a different source-only reviewer to re-review immutable F-09 evidence. Preserve the original finding bytes and review history.
- D8 and broader D1–D6 coverage remain open; they were not assessed in this review. Next action is a corrected D7 source record followed by fresh independent bounded review, while D9 proceeds separately.
