# Independent audit verdict - corrected 1.20.2 to 1.20.4 effect values

## Terminal status

**COMPLETED.** The correction resolves the prior source-member attribution error. The corrected draft's bounded no-difference claims and open packaged-resource gate remain unchanged and are supported by the reviewed exact sources.

## Immutable draft bindings

- Corrected draft: C:/Users/Wolfi/.codex/visualizations/2026/11/17/01a1eb7b-b9ff-7a20-8686-0295dde20f26/c-queue-1202-1204-effect-values/report-r2.md
- Corrected draft SHA-256: c242b5fb803a77322717f9f542fec859ed472a91d27877b1b9f3df0444ca5f33
- Original report.md remains unchanged at SHA-256 3c181aa3b462a0071ddd8623923bdaeb1f34a7aa657530411542482fd465601a.
- Prior verdict.md remains unchanged at SHA-256 77b581ba86e43ce08fbc16955ec36a2f7c73a93c37ebadbcce06d6644ca6747c. This verdict supersedes it for the corrected draft only.

## Correction verified against exact source

In both canonical Mojmap source trees, PotionItem.java lines 43-58 contain PotionItem#finishUsingItem. The order is: player/criterion setup; server-side gate at line 50; iterate PotionUtils.getMobEffects at line 51; branch for instantaneous effects at line 52; call applyInstantenousEffect with the instance amplifier and scale 1.0 at line 53; otherwise call addEffect with a copied MobEffectInstance at line 55. The compared source window is identical in 1.20.2 and 1.20.4. The corrected report now attributes the route to finishUsingItem and states this order accurately.

The report-r2 change from report.md corrects PotionItem#use to PotionItem#finishUsingItem in the main potion route and instantaneous boundary, and clarifies the call order. The other bounded source-value, payload and lifecycle claims are unchanged. The packaged-resource membership paragraph remains an explicit open gate.

## No-difference scope confirmed

The compared exact source identities remain Minecraft 1.20.2 / Mojmap and Minecraft 1.20.4 / Mojmap. The registered Speed and Slowness templates, Jump Boost and Dolphin's Grace declarations and movement-read values, ordinary Leaping/Swiftness/Slowness potion duration-amplifier tuples, potion effect-list materialization, update packet shape, effect instance duration tick and relevant LivingEntity/player payload/lifecycle reads show no difference in the reviewed members. This accepts only those named, bounded claims; it does not imply whole-file identity for LivingEntity, Player, PotionItem or ClientPacketListener.

## Open gate and coverage limits

The published canonical outputs contain Java source trees, not the packaged Minecraft resource/data tree. They cannot establish packaged potion/effect resource membership or absence. Packaged resource membership therefore remains open exactly as stated in report-r2; Java MobEffects and Potions registrations are separately established. This audit does not assess effect-production eligibility, fluid physics, other effect producers, health/damage or instantaneous outcomes, complete player-tick coverage, runtime parity, or intermediate patch boundaries. Source/static review only; no code, tests, launches or source generation.

## Exact source identity binding

The source manifests and provenance records reviewed previously are unchanged for this corrected draft:
- 1.20.2 Mojmap source manifest SHA-256: e439ff7e1d3f1d31e01be1edd141f94d917a9dc7fa0de5f69904544cbbe62887
- 1.20.4 Mojmap source manifest SHA-256: fd3c8668483e5ff208c847474f6cbd3952a909c4aa602ed98b24e3d11a9602a1
- 1.20.2 Mojmap provenance SHA-256: 1dad9f725f6cb25b6568700fa0294f83b66ea9633b0d62ed354d7bd8b45d4f05
- 1.20.4 Mojmap provenance SHA-256: 10914d9b7b3fc29de066ca24f7d905e4d4f0a451a6c81729a2d1981addfc1385