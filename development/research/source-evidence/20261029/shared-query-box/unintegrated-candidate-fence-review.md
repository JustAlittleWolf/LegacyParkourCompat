
# Follow-up review: native fence for partial-sweep scan

## Verdict

**ACCEPT for the focused native-fence change at candidate commit `62136c0cc78f0cc3c8b5cd2e163bbce08a4f4fa9`.** Under the candidate resolver's `emulates >= selected` rule, `@V1_21` is the closest provider for the scan hook when 1.21.0, 1.21.1, or any earlier profile is selected (there is no earlier provider for this hook in the candidate). Its `usesHistoricalScan()` returns `false`, so the call site can retain native scanning for that range. For 1.21.2/1.21.3, the closer `@V1_21_2` provider wins and inherits the hook's historical capability default of `true`.

## Bound inputs and hashes

- Candidate `.native-candidates/partial-sweep-traversal-1212-20261029`: exact `HEAD` `62136c0cc78f0cc3c8b5cd2e163bbce08a4f4fa9` (verified).
- Native fence `EntityInsideBlocksNativeFence.java`: SHA-256 `5785f27d863c54947d85a29611b55a46c9e622fe4654c08ed0e525e349ce0c58`.
- Updated hook `EntityInsideBlocksScanHook.java`: SHA-256 `fd5ab625a8cbd2cd565487e32f7ef9fb9cb6fbc40026bc6237d388df6661ad3a`.
- Historical provider `EntityInsideBlocksScan.java`: SHA-256 `0bb3ba0a8a8b4f1a2e83cd1271ff49a4d08db0637aa7a918460cb5155402c93e`; comparison against prior candidate commit `0d28943fe9f07a54eb5eef1fb59c1eb1bf35d5cc` confirmed this file is unchanged.
- Accepted source report `.native-source/20261029/partial-sweep-1212-alias-closure/report.md`: SHA-256 `554fca52d724e8133ec090f8d1b28f3daea443c926682df21b62e133d0816ab4` (recomputed).
- Independent source audit `.native-source/20261029/partial-sweep-1212-alias-audit/audit.md`: SHA-256 `601e1ec94848d2651ceecb8249f08fe3b138c476e2103c5a7fc5410ca72aa34e` (recomputed).

## Review notes

- `ChangeResolver` filters to providers whose `emulates` version is newer than or equal to the selected profile, then chooses the smallest qualifying version ordinal. Given the candidate's two providers (`V1_21` native fence and `V1_21_2` historical provider), the fence is selected for 1.21.0/1.21.1 and earlier; `V1_21_2` is selected for 1.21.2/1.21.3. `CURRENT` resolves no changes, as before.
- `EntityInsideBlocksScanHook.usesHistoricalScan()` defaults to `true`; the native fence overrides it to `false`. Both traversal methods now have defaults that throw `UnsupportedOperationException`, so a call made without first respecting the capability flag fails loudly instead of silently using an incomplete/native provider.
- The follow-up diff adds the fence and changes only the hook defaults. The historical `@V1_21_2` provider source is byte-for-byte unchanged from the prior candidate.

## Scope limits

This verifies the candidate resolver/fence contract only. Product call-site wiring that must consult `usesHistoricalScan()` is a later integration phase and was not inspected. No product files, builds, tests, or runtime were used.
