# Independent review: 1.7.10 enum-helper ordering

## Summary

Reviewed commit `2bcf62557a194f8057ce0b9d03d5550fc0627df7` against base `6901662573f57e54dbec9b0e933f9e2dc8e28f00`. Scope: 3 files, 83 additions and 7 deletions (`buildSrc/README.md`, `EnumSwitchHelperRepair.java`, `MinecraftDecompileEngine.java`). No confirmed or likely functional findings. Recommendation: **ACCEPT**. Review report SHA-256 is recorded by the coordinator after this file is finalized.

The implementation pins 1.7.10 release metadata, raw client, mapping artifact and Tiny content, then verifies a canonical digest of the ordinary mapped archive plus the audited flat-archive graph counts before ordering. Cache intermediates and pipeline identity are distinct from 1.8 and 1.8.1. Reordering is shared with prior repairs, and the helper writes only class-entry slots: it rejects duplicate paths, checks archive/class identities and nesting metadata, requires a complete acyclic graph, then verifies identical paths and uncompressed entry contents plus the canonical digest. The 1.7.10 output is held at the existing version/mapping output path, while unverified legacy source there is refused unless forced through the normal staged publication path.

Cached inputs were read-only checked: version metadata `265c7e…7cba6`, raw client `a4fc22…80cce`, Feather merged artifact `1a2278…491a7`, Tiny `bee4c2…59485`; these match the new constants. The checked ordinary mapped JAR container hash is `d37373…90fd`, consistent with the diagnosis; its canonical content pin is `36a733…43243`. The supplied diagnosis records 1,803 classes, 14 helper roots, 14 dependencies, 21 direct GETSTATIC sites, five reversed dependencies and the nine Vineflower failure bindings. I did not independently recompute the canonical digest or replay those bindings.

## Six review angles

1. **Requirement:** Satisfied by the code paths and safeguards described above. Explicit 1.7.10 branch is gated on `ornithe-feather`; ordinary remaps outside that exact release/mapping retain their existing route.
2. **Correctness and edge cases:** No functional issue found. Both Tiny namespace routes stage and validate the ordinary mapped archive before ordering; unsupported headers still fail. Stable Kahn ordering uses original class positions as tie-breaker, and cycles/incomplete class identities fail closed.
3. **Missing work:** No missing call site or strict gate found. After ordering, the unchanged flow still runs Vineflower error checking, failed-method-output rejection, required movement-source checks, provenance generation, and staged publication. Legacy-source reuse and force behavior are preserved.
4. **Conventions and duplication:** Reuses the existing ordering implementation; adds only version-specific pins/validation/routing. Version-specific cache filenames and provenance pipeline identities are separate. README documents the exact version route and limits.
5. **Permissions and visibility:** No permissions or public API changes. Output reuse is intentionally tightened for unverified legacy 1.7.10 Feather sources, consistent with the requested force/regeneration path. Existing 1.8/1.8.1 conditions remain in place.
6. **Security:** No new externally reachable input or trust boundary. Cached artifacts and remap output fail on pin/content mismatch; no security issue found.

## Not verified / not reviewed

- The requested strict 1.7.10 decompile and product build remain for the integration owner; neither was run here.
- Runtime movement parity is outside this tooling review.
- I relied on the supplied static diagnosis for the nine failure-to-helper bindings and did not independently recreate its classfile/mapping audit.

Static checks only: inspected the requested diffs and cached input hashes; `git diff --check` reported CRLF-related whitespace diagnostics on new engine lines. No build, test, download, decompile, game launch, or cache/source mutation was performed.
