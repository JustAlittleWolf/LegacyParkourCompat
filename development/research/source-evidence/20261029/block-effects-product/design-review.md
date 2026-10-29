# Independent review: 1.21.4–1.21.5 segment lifecycle candidate

## Verdict

**ACCEPT for bounded product integration.** The candidate describes source-operation behavior in the cited 1.21.4 and 1.21.5 releases and keeps the 1.21.6 record model separate. It does not claim a runtime trajectory, universal witness inequality, or complete pair coverage.

## Rebound evidence

The candidate file SHA-256 is `ce3a3bd5358abe4c7fa56534a580d0da9cd3d866423e5daaf3210c67672b877e`. I independently recomputed every cited report, audit, correction supplement, and supplement-review hash. They match the candidate's listed identities:

- 1.21.3→1.21.4 caller report `11c1e42f76911b4f527e3dc360e24834d81ea15374a12f6e41fc90a25a5b0c50`; audit `66772dbc839f21fa0243b5f3e87f40be2726bd35916a53fadda62c64fcd0651f`.
- 1.21.4–1.21.9 lifecycle report `c4468295b043b2457e561c49d2c741d7c783f80a766ec303390cb6d889651db6`; audit `cc9dea88864e9a62a552f9c04ad05f7f5c9c4eb6f1ddb7609861afa293cbf400`; correction supplement `54c53d4af8c0602e7db6b50232f34fd404abaee635f36bb24138a9c1e3a6133b`; independent review `14eb9bb2c079bc7d2ad141ad67df006d8b99a50a79637c365e03d20bc203d5ed`.
- Visited-block admission report `c43ec8e8b47edeae326d5a68f5a9d006492781c12b091a562585778477ea4261`; audit `8e9de3f919e978b12f7a58dc05137c858a7591390a2e413794da5f3a8a9afd6a`.
- Swept-AABB report `5b7ed6ee05dda849182d8201ec90fab8a6b23feab3a860bbdfb73ed504c3b25f`; audit `4ab7f818896302a85f9e30940bb01821fe670ffba79ff2089907ee4068c7b800`; correction supplement `1a61b3770f0031380be1df36a01c820126a391c07e93dcd1ff6aa4340d0e91ef`; supplement review `0ea9a493da0cf2f6dc245c7e45a0c4898359113b1bf4560f60b8a1cd33afb864`.

## Findings

- The 1.21.4 segment contract matches the accepted lifecycle and admission records: the client post-travel no-argument drain supplies `oldPosition()` to `position()`, while piston calls use that same no-argument phase after the move. The visited set suppresses repeated positions before shape tests; this version does not use the later fluid callback test in this method.
- The 1.21.5 contract correctly separates nested movement batches from drain-time residual insertion, preserves the exact movement-record predicate and no-physics/zero-piston exclusions, and keeps the single visited set across all segments until the pass completes. It describes shape and fluid checks as independent gates while preserving visited-before-tests behavior.
- The staged collector ordering, synchronous direct callback behavior, and piston immediate scan/removal sequence agree with the accepted lifecycle report. The callback method-name correction is respected: post-travel dispatch is in `LivingEntity.aiStep()`.
- The candidate delegates enumeration, clipping, tie order, epsilon, and integer-boundary rules to the separately accepted swept-AABB contract instead of substituting raw AABB overlap. Its .6 boundary is explicitly a different record model.
- The stated local-player/client gate and explicit exclusions do not broaden the source claims. No product implementation was inspected.

No source, product, tests, builds, or runtime were changed or run for this review.
