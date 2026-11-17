# Audit addendum: report digest correction

**Technical disposition: ACCEPT, with corrected report binding.** This addendum supersedes only the report-hash field in the prior verdict; it does not claim a new full source audit.

## Exact artifact identities

- Current `wall_simple_utility_face_projection/report.md` SHA-256: `7161b0f0e0ba180cee371574c77aaac747012fa3cbcdba9cd2e597b87afa2142`.
- Prior `wall_simple_utility_face_projection_audit/verdict.md` SHA-256: `6031dd47c4b7c96743ce6813537069fd837da90e82b11e4197ff97f46a1292c4`.
- Current endpoint source-manifest SHA-256 values remain 1.13.2 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211` and 1.14.4 `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdbb5ea3c9f75bc`, matching the ready markers and the identities quoted in the prior verdict.

## Reconciliation

The prior verdict's report digest was transcribed as `7161b0f0e0ba180cee371574c77aaac747012fa3bcbdba9cd2e597b87afa2142`; that value does not hash the current report. Comparing the current report's scope and result rows with the prior verdict shows the same eight-key claim (cactus, snow, cake, farmland, grass path, daylight detector, enchanting table, structure void), the same old horizontal `FaceShape` versus target collision-face projection operation, the same bounded no-delta conclusion, and the same endpoint source-manifest identities. The visible discrepancy is the transposed digest characters in the prior verdict's report-hash field. The corrected binding is the current report SHA-256 above.

The current checkout does not retain a separate snapshot of the report bytes that the prior reviewer read. Therefore, the surviving artifacts establish matching claim scope and source identities, but cannot independently prove byte-for-byte identity with the exact payload previously reviewed. The acceptance is retained for the matching technical claims; this addendum records that limitation and binds the acceptance to the exact current report bytes stated above.