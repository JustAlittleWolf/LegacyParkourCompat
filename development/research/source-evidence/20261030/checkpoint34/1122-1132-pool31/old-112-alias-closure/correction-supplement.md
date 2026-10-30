# Correction supplement: `LivingEntity.mobTick()` body digest

This supplement binds the following immutable artifacts:

- `report.md` SHA-256: `0010c45f3d9d0fab2756772150f8d85525a8f3a67ddab04ca95bd19d1fa5b22b`
- `source-hashes.md` SHA-256: `a448875c7a64c5975b518fdb230cb2e736afcfdfbafd76d2fc18172aa38a80f4`
- Independent audit SHA-256: `4e5b723d2288642a884257877db135a5445b3ce357174de24954a68dba6aa60f`

The `LivingEntity.mobTick()` method-body SHA-256 printed in the report and hash list is incorrect. The correct digest is:

`34b8d985ba8a6df3b3b11059246d03eeaaa7f955a35f75b497419c42fe129832`

The typo is in the digest only. Direct extraction and comparison found that all three actual method bodies (1.12, 1.12.1, and 1.12.2) match. This correction does not change the alias verdict or any other finding.
