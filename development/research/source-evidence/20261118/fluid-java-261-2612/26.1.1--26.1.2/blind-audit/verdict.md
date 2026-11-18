# Final draft review verdict

## Identity gate

The independent frozen audit reviewed here is `frozen.md`, SHA-256 `f8c583b81c688442a121065b6bbcaa729ad9bbfb4c3d4450b8a03463d2d66c17`.

The authorized comparison identity was SHA-256 `d4e863c4ae0fac682f67f4f0a4e605486532a36b7cfcac69836a68e60de13a3f`. The comparison file present during this review hashed to `d4e863c4aef0ac682f67f4f0a4e605486532a36b7cfcac69836a68e60de13a3f`. These digests differ (`e0fa` versus `ef0a` after the common prefix `d4e863c4a`). The authorized comparison identity therefore did not match the file, so no substantive draft verdict is bound to it.

## Bounded clauses

The three clauses in the frozen audit remain independently supported only to its stated scope: water/lava contact admission and stored height; current sampling and push; and first ordinary non-riding water/lava movement drag and gravity gates. This review does not extend that frozen scope or accept additional claims from a digest-mismatched comparison.

## 26.1.2 origin limitation

The permitted canonical local ready/provenance metadata identifies version `26.1.2`, namespace `unobfuscated`, and the local decompilation/tool run. The ready marker records a client SHA-256, but the provenance file omits the official client URL, client hash, and version-metadata hash. These local records support the identity of the prepared source set as labeled; they do not independently authenticate its official Mojang artifact origin. Official artifact-origin verification remains open on this evidence.

## Limits

This is an artifact-identity failure, not a rejection of the comparison's movement claims. No content-level acceptance or disagreement verdict is issued for the comparison file until its bytes match the authorized SHA-256. No source pair, product code, implementation, or runtime behavior was re-audited here.