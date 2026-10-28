# Independent review: End Portal Frame ID 120 provider closure

Verdict: **ACCEPT within the stated provider, alias, and lifecycle scope.**

Reviewed input:

- Closure: `.native-source/20261028/collision-next/dda-end-portal-frame-120-closure/report.md`, SHA-256 `864BC037DE25C25C12527FBB3E753AF4FF04FB99B81DC2F78A443B548807300D`.
- Source manifest: `.native-source/20261028/collision-next/dda-end-portal-frame-120-closure/sources.sha256`, SHA-256 `BA381DD7AD4C7C8C7462E1D90ACD4AD2E69436CB1A3D58679F5089D5189056E9`.

## Evidence verification

All 22 listed source, ready-marker, provenance, and canonical source-manifest files exist and match the closure's SHA-256 entries. The selected Java source hashes also match their entries in the canonical 1.8.9 `ornithe-feather.sources.sha256` and 26.2 `unobfuscated.sources.sha256` manifests. The ready markers identify exact versions `1.8.9` and `26.2` with the stated mappings; their embedded source-manifest digests match the corresponding canonical manifests.

## Claim review

- 1.8.9 `Block.java` registers ID 120 as `end_portal_frame`; `EndPortalFrameBlock` defines `FACING` and `EYE`, defaults to north/no-eye, and decodes facing with `metadata & 3` and eye with `(metadata & 4) != 0`. Thus bit 3 is ignored for all 16 raw metadata values. Old and current horizontal direction data order agrees for values 0–3. 26.2 registers `minecraft:end_portal_frame`, exposes `FACING` and `HAS_EYE`, and defaults to north/no-eye. The alias mapping is supported.
- The old block constructor initializes shared bounds to a unit cube. The old End Portal Frame provider writes the 13/16-high base bounds, delegates that box to `Block.addCollisions`, optionally writes and submits the eye cap, then resets to the base bounds. On normal completion the singleton therefore retains the base frame bounds. `Block.rayTrace` invokes the inherited empty `updateShape` and reads those mutable fields; this class does not refresh them on the ray path.
- The old eye cap has horizontal bounds 5/16–11/16. The 26.2 eye shape adds an 8/16-wide centered cap spanning 4/16–12/16, while both base frame shapes reach 13/16 height. The modern shape is state-selected and immutable; direct substitution does not reproduce either the old eye cap width or residual shared bounds.

## Scope and limitations

The review accepts the block ID/state alias, collision-provider geometry, and mutable-bounds lifecycle claims. The report's DDA implication remains conditional: an early ray read can observe the constructor or post-provider residual bounds and could affect continuation if it changes a hit/miss. This closure does not prove a concrete visitation counterexample, old/current ray traversal equivalence, runtime reachability, or overall DDA parity. It correctly labels End Portal Frame as an early reader rather than a newly demonstrated camera-path writer.

No product code, build, tests, or runtime execution was used.
