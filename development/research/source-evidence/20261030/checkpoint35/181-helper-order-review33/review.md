# Static build tooling review: 1.8.1 enum-helper order candidate

## Summary

- Candidate: `2cd1c20e84b13633968971a13bf8747cb0962ce0`
- Base: `16f80e8cacf7f795ae0e0b79ee5c9f98aec6b28a`
- Scope: 3 files, 171 insertions and 195 deletions. Reviewed `buildSrc/README.md`, `EnumSwitchHelperRepair.java`, `MinecraftDecompileEngine.java`, the surrounding cache/provenance/publication path, and the retained 1.8.1 ordering diagnosis. No files in the candidate were changed.
- Decision: **Accept the static build-tooling design. No functional findings.** The root-class guard was checked against the exact mapped input and holds for this pinned archive.

## Finding

No functional findings.

## Six review angles

1. **Requirement:** The candidate keeps class paths and uncompressed payloads unchanged and adds the intended order-only stage for the exact audited input.
2. **Correctness and edge cases:** Stable Kahn ordering is deterministic, emits all classes only when the dependency graph is acyclic, checks referenced helper fields, and verifies path/payload equality plus the canonical digest. The root-class guard was verified against the pinned input (see below).
3. **Missing work:** No call-site migration is missing; `applyHelperOrder` replaces both special 1.8.1 remap routes. Build and tests were not run under the assignment's static-only boundary.
4. **Conventions and duplication:** No new dependency or duplicated mapping route was introduced. The two 1.8.1 namespace routes retain their separate remap setup and share the helper-order stage.
5. **Permissions and visibility:** No permission changes. The visible output remains under `<version>/ornithe-feather`; reuse of unverified legacy output still requires `--force`. The changed pipeline ID produces the expected provenance mismatch for prior generated trees until deliberate forced regeneration.
6. **Security:** No new concrete attack path found; inputs are local pinned artifacts and the operation parses/repackages the mapped archive.

## Other static checks

- The special path is gated by exact version `1.8.1` and mapping source `ornithe-feather`. Other mapping sources and versions keep their existing direct remap paths and cache names.
- The baseline remap retains the ordinary-content hash, original 59 diagnostic binding gate, mapping audit, and pinned raw client/Tiny/Feather artifact checks before ordering.
- The new mapped-jar name and `pipeline:181-enum-helper-order` input give the changed pipeline a new cache/provenance identity. A forced or missing-output generation reruns remapping; the existing output reuse path still compares manifest inputs, and the 1.8.1 legacy-tree guard still requires force.
- The reorder writes entries in the original class-entry slots, moves each selected entry's path/content/entry metadata together, and copies timestamps, comments, extras, compression method, and stored-entry size/CRC. Deflated streams and archive-level metadata are recreated rather than byte-preserved; this decompile-only intermediate is verified by path and uncompressed-content equality, not by container-byte equality.
- I reran the standard-library raw classfile-attribute inventory against `D:\Javastuff\LegacyParkourCompat\.minecraft-decompile-cache\1.8.1\client-ornithe-feather.jar`. Its SHA-256 is `d2977e05623d692e2d73dacbd0d6328b9226f47d80f824711a6d5fe81df09001`; it has 2,500 classes and contains no `InnerClasses` or `EnclosingMethod` attributes (or any other nested-class attributes). This is the archive covered by the candidate's ordinary mapped content digest `c7527d0279c8a24e70b5b4103c0606dc883ff840b5d5afb827edbe9bda4abaf7`, so the all-root check at `EnumSwitchHelperRepair.java:338-339` is valid for the pinned candidate input. The 15 nested consumers in the separate diagnostic supplement refer to the 1.8.2 comparator metadata; I do not attribute that metadata to the 1.8.1 ordinary archive.
- Strict Vineflower ERROR counting, failed-method marker rejection, required-source checks, provenance checks, and publication remain unchanged.

## Not verified / not run

No candidate build, test, archive transform, decompile, cache write, source publication, game launch, or runtime parity check was run. Consequently, zero Vineflower errors and generated source hashes remain unproved. The topological result on the exact archive and strict generated-source gates remain for the designated owner to verify.

**Next step:** the designated owner may proceed with the separate compile/source proof and authorized strict candidate-generation path; this static review does not establish zero Vineflower errors or movement parity.
