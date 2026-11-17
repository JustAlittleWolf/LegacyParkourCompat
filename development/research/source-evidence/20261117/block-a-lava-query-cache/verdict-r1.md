# Provenance rebind — independent review

**Verdict:** accept the corrected provenance companion, limited to the 1.14.1–1.14.3 Yarn roots. It corrects the identity caveat; it does not revise the accepted lava-query/cache behavior, boundary, player witness, or continuity findings.

## Bound inputs

- Corrected companion: `lane-lava-query-cache/comparison.corrected-provenance.md`, SHA-256 `be6e887d31cdd72996144e85992555b0cebc772ead2e4f5d82bcf05609690604`.
- Fixed prior source audit: `lane-lava-cache-audit/verdict.md`, SHA-256 `8a3ed143f3aae478fb202f2243ee37676b82d4ffdac30f8f6b422d0911402fb8`.
- Frozen source-roster fingerprint in that audit: `380c5d10153eac59e14df18f7ae560d5f20171c6385acf7620aaf2a467bf62bc`.

The companion identifies only those three Yarn releases as corrected identity records and says the prior comparison/finding are unchanged. Its exact source-file pins for Entity, FluidBlock, and LivingEntity in each release match the prior audit's hash table. The 1.14.3 PlayerEntity, ClientPlayerEntity, and BlockPos pins also match the prior table. The frozen roots identify Yarn 1.14.1+build.10, 1.14.2+build.7, and 1.14.3+build.13; their recorded hidden-provenance digests match the companion's table. The earlier audit had already verified that the relevant local source hashes match the embedded maps and that those maps pin the mapping/source inputs.

Accordingly, the companion's attribution to the hidden `.decompile-provenance.json` records is supported by the frozen identity evidence. The continuous reset → moved-box scan → lava-tag callback → cached getter and jump-before-travel operation remain supported by the previously accepted exact source claim bytes. This rebind makes no new claim about other releases or the local source tree.

Static review only. No primary source, product files, Git state, runtime, tests, builds, or network were accessed or changed.