# Independent source audit: early climb floor

TERMINAL: COMPLETED

## Bound input

- Comparison: `early-climb-floor-source/comparison.md`
- Comparison SHA-256: `799505e4201947ae05cd9d970939f17c92a428b08227ad31667dba18dc527ee1`
- Source lane: canonical `decompiled_minecraft/<version>/ornithe-feather/` only; no implementation, wiki, Git history, or backlog consulted.

## Verdict

Accepted. The bounded claim is supported: in every exact 1.8 through 1.8.9 source inspected, `LivingEntity.isClimbing()` floors x, `getShape().minY`, and z, reads the block at that position, and recognizes only ladder or vine, subject to the player spectator exclusion. In 1.9 the same sampled position and spectator rule remain, while the predicate adds `TrapdoorBlock` when `canClimbTrapdoor` succeeds: OPEN is true and the block below is a ladder with equal FACING. The named open-trapdoor-over-ladder witness therefore returns false for each inspected 1.8 release and true in 1.9 for a non-spectator player.

The 1.8–1.8.8 per-file provenance entries match the actual `LivingEntity.java` hashes. The 1.8.9 canonical source manifest and verification record identify the exact release/mapping, list 1,612 sources, and report zero source-file differences; its `LivingEntity.java` hash matches the manifest. The 1.9 per-file provenance entry also matches. The final comparison's recorded per-release hashes independently match the source files checked here.

The cited 1.8 and 1.8.9 registrations confirm ladder ID 65 and wooden trapdoor ID 96 as distinct block classes. The 1.9 registration and named block references are consistent. The player movement call sites invoke the predicate before movement for climb velocity/fall-distance handling, and after movement for the horizontal-collision upward response.

Qualification: the 1.9 predicate checks `block instanceof TrapdoorBlock`, not equality with the wooden trapdoor singleton. The same predicate therefore also applies to the registered iron trapdoor, provided its state and lower ladder meet the helper's conditions. This broadens applicability; it does not invalidate the comparison's ordinary wooden-trapdoor witness or the 1.8-to-1.9 introduction claim.

## Hashes and evidence anchors

- Immutable comparison SHA-256: `799505e4201947ae05cd9d970939f17c92a428b08227ad31667dba18dc527ee1`
- 1.8 `LivingEntity.java` SHA-256: `645624913e265cad50293f659812a35c3c4303d1e8bdafdcca4eaf2e8ed5c69d`
- 1.8.1 `LivingEntity.java` SHA-256: `fbfb0f0821a2d6b9d1d665387b2c5423fc0c1d8f5f36f7b5e1258b26d90c4c4f`
- 1.8.2–1.8.9 `LivingEntity.java` SHA-256: `082831c6578e3a70fa6cea5b90bc3eefc26678259b66334470de22b90b5b0e4e`
- 1.9 `LivingEntity.java` SHA-256: `5b5936656aacc64a4d75db7b370985f9456ac96076331da047bd246f76a705fb`
- 1.8.9 canonical source-manifest SHA-256: `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004`

## Limits

This is a static, operation-bounded source audit, not full-pair movement coverage or runtime parity evidence. It covers the climbability predicate, the named registration/state references, and its cited player movement call sites. It does not audit collision shapes, vehicle or other-entity physics, or unrelated tick operations. No code, tests, game launch, or source generation was performed.
