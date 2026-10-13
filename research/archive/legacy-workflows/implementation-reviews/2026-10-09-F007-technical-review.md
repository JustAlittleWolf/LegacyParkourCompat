# F-007 position-packet threshold — independent technical review

**Review date:** 2026-10-09
**Decision:** ACCEPT — no functional findings
**Reviewer:** independent technical reviewer `/root/f007_technical_review_resume`

## Reviewed identities and scope

- Candidate code commit: `60010459540809c16f0ec67a849ef57b159943d0`.
- Frozen task tree: `32a4c3d484ba05a76b85f648d48678cf075db7f4`.
- Catalog integration reviewed through `ee7ce616ae00aea11b335c80ece87f847d3cb3b5`; newer local `main` `309b330d546da8e0e25fa86158e993454e58f53f` was excluded.
- Inputs: accepted F-007 finding, boundary memo, independent boundary ACCEPT, and portable identity binding recorded in `workflows/fix-implementation/reconciliations/F007-position-packet-threshold-2026-10-09.md`.

## Review result

The implementation restores the accepted old squared threshold `9.0E-4` while retaining the modern target's `Mth.lengthSquared` arithmetic, strict `>` comparison, and surrounding packet logic. The hook modifies the return from `Mth.square(D)D` in `LocalPlayer.sendPosition()V`; the current source confirms this is the threshold call `Mth.square(2.0E-4)`. Reminder, controlled-camera, passenger, rotation, packet selection, and baseline-reset logic remain unchanged. `MovementRuntime.find` preserves the native threshold when no historical behavior resolves.

The hook interface, `V1_18` change, and catalog registration are present. F012's V1_18 registrations use separate mechanic keys. F005 and F3 changes occupy separate movement/entity paths; no semantic overlap with `LocalPlayer.sendPosition()` was found.

`ChangeResolver` selects the nearest registered change at or later than the selected version. The F-007 `V1_18` change therefore resolves for profiles through that group; `V1_18_2` and later have no F-007 registration, and `CURRENT` resolves no historical changes. The implementation adds no resolver change or permission/security surface.

The reviewed current source hashes match the implementation record: `LocalPlayer.java` SHA-256 `8d089aa09217e3607b38590f7c1623385562800943ac6dfd3d17804e041da6d6` and `Mth.java` SHA-256 `30455c684f2401c79290847822bca82e77162c4a2bcf8618e85cac9898a2dc17`.

## Limits

Runtime packet timing/parity, client/server behavior, and injection application by a build were not verified. No build, tests, runtime, decompilation, or network research was performed. The source pair remains partial; releases earlier than 1.17.1 are unsampled even though closest-later resolution applies the `V1_18` change to older selected profiles.
