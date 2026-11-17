# Independent source audit — SlimeBlock landing response r2

Verdict: **ACCEPT**

Reviewed report: `build/later-source-20261116/26.1.2--26.2/slime_bounce_transition_revision_r2/coverage-r2.md`
SHA-256: `d6fd005ad2fd60c3d10645036c5286063eb645808bba560e776301004390453f`

## Audit basis

The prior independent verdict `build/later-source-20261116/26.1.2--26.2/slime_bounce_transition_audit/verdict.md` (SHA-256 `B151DEAB0B28A0CBF84FA5DE1299B50797CA41EFD0063C31995F313B0516905C`) verified both exact endpoint identity/manifest bindings and all nine cited Java hashes, and accepted the SlimeBlock callback/restitution formulas, gates, ordering, and stated source/runtime limits. This r2 adds only the ordinary local-player caller path requested by that audit. The old report’s endpoint identity and unchanged source-file verdicts are reused; no tree rehash was done.

## Added caller path verified

I independently checked the exact cited source anchors in both `26.1.2/unobfuscated` and `26.2/unobfuscated`:

- `LocalPlayer.tick()` calls `super.tick()` when `connection.hasClientLoaded()` (`LocalPlayer.java:227–230`). `Player.tick()` calls `super.tick()` (`Player.java:231–259` in 26.1.2; `232–260` in 26.2). The inherited `LivingEntity.tick()` then invokes virtual `this.aiStep()` when not removed (`LivingEntity.java:2738–2740` old; `2804–2806` new).
- Virtual dispatch reaches `LocalPlayer.aiStep()`, whose tail calls `super.aiStep()` (`LocalPlayer.java:914` both); `Player.aiStep()` also calls `super.aiStep()` (`Player.java:442–453` both), reaching `LivingEntity.aiStep()`.
- The ordinary non-passenger branch reaches `this.travel(input)` when `canSimulateMovement()` and `isEffectiveAi()` are true (`LivingEntity.java:3070–3074` old; `3137–3141` new). `Player` implements both predicates as `!level().isClientSide() || isLocalPlayer()` (`Player.java:1269–1275` old; `1290–1296` new), and `LocalPlayer.isLocalPlayer()` returns true (`LocalPlayer.java:378–380` both).
- For a non-passenger, non-flying player, `Player.travel()` delegates to `super.travel(input)` (`Player.java:1381–1402` old; `1402–1423` new). `LivingEntity.travel()` selects `travelInAir(input)` when fluid travel and fall-flying do not apply (`LivingEntity.java:2392–2399` old; `2430–2437` new). `travelInAir()` calls `handleRelativeFrictionAndCalculateMovement()` (`LivingEntity.java:2430–2435` old; `2468–2476` new), and the previously audited helper calls `this.move(MoverType.SELF, this.getDeltaMovement())`.

This establishes the bounded source call path from an ordinary on-foot local-player tick to the shared movement body. The six source files used for these added anchors are already listed in the report’s hash table. Their current SHA-256 values match those rows: 26.1.2 LocalPlayer `433fd995ad317af0f6ef0e50c1e8e3483cb8f00e0e327d4edf27a4dd99666ebe`, Player `44cf28e0c64e78d39fd13368e9991381dbebab67029070cb9ddc43f09d45d14d`, LivingEntity `c3b64de8dbaba8ad8a7ccd4f33255346d8206e66f12ca91260971bf5e5ac93bd`; 26.2 LocalPlayer `8d089aa09217e3607b38590f7c1623385562800943ac6dfd3d17804e041da6d6`, Player `8decc71b9c780664578ddb14591db2a2f207c72c05b676edded6f8e964576531`, LivingEntity `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a`.

No correction is required. This audit accepts only the report’s bounded source claims and the added caller path. Slime placement/reachability, tag membership, runtime frequency, and whole-pair coverage remain unverified.
