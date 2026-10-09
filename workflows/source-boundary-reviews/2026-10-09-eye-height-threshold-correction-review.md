# Independent review: eye-height threshold correction

**Review date:** 2026-10-09
**Disposition:** **ACCEPTED narrowly:** the prior REQUEST CHANGES issue about an “exactly 0.4” cached-float case is corrected. The memo's additional vanilla Player provider witness remains **unverified independently** because the cited frozen source files are unavailable in this review worktree.

## Bound immutable records

- Prior review: `86a47f1784d57c6374a8ca28c401e29a4b9a4bec`, `workflows/source-boundary-reviews/2026-10-09-eye-fluid-checkpoint-review.md`, blob `b93a40a76200975da2b2cd02962a4347849ee8cc`. Its only request for changes was to remove the impossible exact-equality cached-float case. Its paired source-body/order acceptances remain unchanged.
- Original reviewed checkpoint: `a0716b793aedb9018a9c42aac5845bad852d8704`, `workflows/source-campaign-2026-10-07/1.21.11--26.1.2/run.md`, blob `42612691277298a756f0dacd5785d66f1062fa21`.
- Correction memo: `ff956771f753f8d2a92c2fb3e51caebbfaa7f4a2`, `workflows/source-boundary-reviews/2026-10-09-eye-height-threshold-correction.md`, Git blob `2f890681c6bc6026034710417a3c0265ca8087e3`. The source-owner binding records raw-file SHA-256 `6ecb26f940fada9074238ac485178eabf6ce43b1b270fee4adf55a6f38387ef6`.
- Source-owner binding: `51f414abd08ec1dc247c7a4af9e1a52507094b89`, pair `run.md` blob `4cf067ae7f105023f03fc9349aa870418a506cc6`.

## Float-boundary correction

The reviewed method returns `getEyeHeight() < 0.4 ? 0.0 : 0.4`; the prior review established that `getEyeHeight()` is a cached `float`, while the unsuffixed literal is a `double`. The memo correctly removes the unreachable equality case. The adjacent float values it gives are `0x3ecccccc` (approximately `0.3999999761581421`), which compares less than `0.4D`, and `0x3ecccccd` (approximately `0.4000000059604645`, the widened value of `0.4F`), which compares greater than `0.4D`. Therefore no cached float compares equal to `0.4D`; the two strict-comparison outcomes are accurately described. The exact-equality wording objection is resolved.

This accepts only the requested arithmetic correction. The same-expression/method-body and paired update-order conclusions remain bounded to the earlier accepted ranges. No source comparison was repeated, and no pair-wide or movement-finding acceptance is implied.

## Supplemental vanilla Player witness

The memo additionally claims that the exact vanilla Player type uses `.eyeHeight(1.62F)`, that construction and pose refresh copy the type/pose dimension eye height into the cached field, and that Player does not override `getDimensions(Pose)`. Its table binds SHA-256 identities for `EntityType.java`, `Entity.java`, and `Player.java` on A and B, and it gives line ranges for the Player registration.

I could not independently reopen those bodies: the frozen ready-source files are absent at the source roots cited in the pair manifest (`build/movement-campaign-2026-10-07/ready/1.21.11/mojmap` and `.../ready/26.1.2/unobfuscated`) in the review worktree. Thus I do not independently accept the added constructor/pose-refresh/provider path on this pass. Keep this supplemental witness from closing the cached-eye-height producer inventory or any other provider route. This limitation does not affect the float-arithmetic correction above.

**Next action for that supplemental witness:** make the already-prepared exact A/B source files available read-only, then independently check only `EntityType.PLAYER` registration, `Entity` construction and `getDimensions(Pose)`/refresh writes, and the corresponding `Player` override absence against the memo's recorded hashes/ranges. Do not regenerate/decompile sources for this review.

## Preserved open scope

Keep mounted applicability and boat/vehicle states; fluid-height, loaded-region and resource inputs; eye-top predicate and movement consequence; other cached-eye-height writers; remaining S1/S7 callers and inputs; pair-wide audit/freeze; first-changed-release determination; and runtime validation open. The source-run binding itself records the pair as active with broad coverage and dependency rows still in progress. F-4 remains limited to its separate mounted Nether LAVA snapshot.

This review is source-only. No implementation, wiki/MCPK, mixed-coordinator review, main merge, build, tests, runtime, client/server, TAS, Docker, or decompile was performed.
