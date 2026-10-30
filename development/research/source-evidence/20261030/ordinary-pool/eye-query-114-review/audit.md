# Independent eye-query source audit

Reviewed input: `.native-source/20261030/eye-query-114-closure/source-review.md`, SHA-256 `7784336b9268bb4a156ebee41fd0394affc9c66711f5650d86d3f900cd2e6db4d`.

## Verdict

The contextual-fluid-height change is supported at the first stable boundary, 1.13.2 → 1.14. In 1.13.2, `Entity.isSubmergedIn` samples the floored eye position and compares `d < blockY + (state.getHeight() + 0.11111111F)`; in 1.14 it calls contextual `getHeight(world, pos)`. `BaseFluid.getHeight` returns `1.0F` when the same fluid type is above, otherwise the state level divided by `9.0F`. This is a reachable point-query difference independent of the loaded-check example. The water-refresh caller uses the query result before swimming-state update.

The report's loaded-chunk witness is invalid as written. At `x=15.25`, the sampled block x is 15 and both it and the player's x coordinate belong to chunk 0. The `checkLoaded` branch checks only the sampled x/z chunk before `getFluidState`; absence of adjacent chunk 1 cannot make that branch reject. Do not use this witness or claim that it demonstrates a local-player movement difference. The added loaded-check operation is present in 1.14's `isSubmergedIn(tag, true)` path, but this audit has no valid ordinary-player witness showing it changes the result. The unguarded public query passes `false`.

## Provenance and alias boundary

Verified canonical source hashes match the report for 1.13.2 `Entity.java` (`1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269`) and `FlowingFluid.java` (`2398d109ddc5785cb1bc8872b8c157319137df8957faf8a03581a8f68afb1253`), and 1.14 `Entity.java` (`f52483d5e1434114b81b1c353e89f246d1557bda7a95400832f5650b89cc744a`) and `BaseFluid.java` (`c596bd4fa6aaf4f176c4be18d9e3125ff8b3e47846cf5a99253cccc9ba7bf5b5`). Exact mappings are Ornithe Feather Gen2 build 2 at 1.13.2 and Yarn `1.14+build.21` at 1.14.

The Yarn 1.14, 1.14.1, 1.14.2, and 1.14.3 source directories do not need separate `ready.json` markers to establish the relevant inputs: each embeds `.decompile-provenance.json`, and the audited `Entity.java`, `BaseFluid.java`, and `PlayerEntity.java` hashes match its `sources` entries. Provenance records the exact Yarn mapping artifact and client input. Provenance-file hashes: 1.14 `ea70a955a12f377cd3b9e8d5f346f4625b044d55da726b6bc472405ae91f4341`; 1.14.1 `308537c5adec3104c5ca243eb5010554f00978635ed74f0d7801c804dd4d4907`; 1.14.2 `786546f99684423cc2563be45c9c3334dfcc2c7db58ddb74ba39ce403bd0869a`; 1.14.3 `81ea0021eb122d4e3340e20d47d6ecb7079365ba767a038d26a8264cddacc28a`. The inspected 1.14 patch sources retain the same contextual-height operation; 1.14.3's `arg.method_20785()` is the Yarn alias for the state-level fallback, not a different rule. 1.14.4 uses the separate Ornithe Feather alias family; its ready/source manifests are available and the report's cited source hashes match.

The source difference is established between the adjacent stable releases 1.13.2 and 1.14.0. It does not justify inventing a missing stable 1.13.3–1.13.9 interval or treating releases after the bounded 26.2 comparison as an open gate for this review. This audit closes only the eye-query operation and its listed patch aliases; it is not full-pair movement coverage.

No product sources/history, wiki, backlog, builds, tests, runtime, decompilation, or source writes were used. No reviewed artifact was modified.
