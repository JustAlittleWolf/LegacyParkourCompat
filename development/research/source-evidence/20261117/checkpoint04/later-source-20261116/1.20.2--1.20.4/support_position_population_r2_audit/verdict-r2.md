# Independent audit — support-position population r2

## Verdict

**ACCEPT** for the bounded report at `build/later-source-20261116/1.20.2--1.20.4/support_position_population_revision_r2/coverage-r2.md`, SHA-256 `8243571697889DDC4275AF3B14A3EBB3FDD3C872C5A1E1BC0D7EEC0FD2CB9DF6`.

## Audit basis

- The prior r1 audit at `support_position_population_audit/verdict.md`, SHA-256 `3B8DE955FA6D576AA3D5132D90245AACA0B3A4D4D6527B6B1680247203A29E1E`, verified the 1.20.2 and 1.20.4 Mojmap identities and manifest bindings, all eight then-listed Java hash rows, and the bounded support-position writer/query claims. Its sole requested correction was the comparator implementation used for equal-distance support candidates.
- The r2 report preserves the r1 content and adds the requested comparator evidence and source row. `Vec3i.java` at both endpoints hashes to `36db6e4b3001c2ea4e5c218ba601d47b76a91e60455f9088b1db4893cef623c3`; both `mojmap.sources.sha256` manifests contain that exact path/hash entry.
- The bound source lines 54–59 compare Y first, then Z, then X, by coordinate subtraction. `BlockPos` extends `Vec3i` at line 30 in both endpoints and has no `compareTo` override. In the unchanged `CollisionGetter.findSupportingBlock` body, a candidate replaces the current result when `current.compareTo(candidate) < 0`; therefore a greater comparator value wins an equal-distance tie, as the report now states.
- The r2 diff is limited to correcting the tie explanation and adding the `Vec3i.java` evidence row. The source identities, other eight file rows, and previously audited query/writer operations are unchanged.

## Limits

Acceptance is limited to the report’s support-position producer and query slice for the two exact endpoints. Block-provider/state population, world distribution, pair-wide source closure, and runtime parity remain open.
