# Correction: cosine index and Z component

This sidecar corrects the cosine-index and Z-difference claim in `report.md`; the original report remains byte-for-byte unchanged.

For its exact finite witness (`d = 6.774901758530177`, initial current and previous yaw `+0.0F`, `e = 0`, followed by `moveRelative(0.1F, new Vec3(0, 0, 1))`), the two yaw values produce sine lookup indices 184 and 185, but **both cosine lookups use index 16569**. The Z component is therefore the same in both versions: approximately `0.0999842718241255`. The X component changes as stated in the original report, from approximately `-0.001763986449897087` to `-0.0017735723670359305`. The witness changes X only; it does not change Z.

This correction is bound to the independently signed audit at `c-turn-precision-yaw-1165-117-audit/exact-verdict.md`, whose verified SHA-256 is `2bf8217436772233d8e9efd0753af108dd4cb86083abca2bc31cb0a523ef2605`.

Unchanged original report SHA-256: `8b862816c21c161606ed6c3b62172b7a969b69586e9f04f893ac515703c821c1`.
