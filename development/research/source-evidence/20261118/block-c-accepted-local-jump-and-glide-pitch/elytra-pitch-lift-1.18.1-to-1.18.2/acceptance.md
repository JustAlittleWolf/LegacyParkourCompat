# Independent acceptance of the Elytra glide correction

## Bound evidence

- Original source report: `report.md`, SHA-256 `4e5094d335e3f6082cba82ca2a7d9987d48662390a97f4368699b8a6cf56b3ea`.
- Independent scoped audit: `../c-glide-pitch-cos-1181-1182-audit/verdict.md`, SHA-256 `615a282617c3aea6258e1f013eddfd5090d1e0106183c7dfbd01ea404f9bab5d`.
- Author correction: `correction.md`, SHA-256 `bb7872c10512c1b4ab34d57ff6a51ece783a44cc6ba351ce22bc6981fc7a4db0`.

All four SHA-256 literals in the correction are exactly 64 lowercase hexadecimal characters. The original report, verdict, correction and both cited `LivingEntity.java` SHA-256 identities were independently rechecked; the current source bytes match the cited hashes.

## Verdict

**ACCEPT the source finding as corrected by the bound correction.** The correction closes the audit's exact qualifications without changing either immutable input:

1. It removes the unsupported dependency from the changed cosine factor to the separate negative-pitch `Mth.sin` lift term.
2. It clarifies that the look-vector length is approximately `0.999999982885729`, while the factor's denominator cap still selects `1.0`.
3. It applies the source's `0.98F` multiplier and replaces the absolute Y witness values with the source-ordered results: approximately `-0.1396500048208237` and `-0.1396500042601097`, with delta `5.607140030061686E-10` blocks.

The corrected scope remains the exact 1.18.1→1.18.2 Mojmap source difference in active dry ordinary-player Elytra travel and its path through `move(MoverType.SELF, ...)`. This is source acceptance only; it does not claim runtime parity.

## Signature

Independent reviewer: `/root/c_glide_pitch_cos_1181_1182_audit`  
Signed scope: acceptance of the correction against the exact original report and signed audit hashes above.  
Disposition: **accepted with correction**.
