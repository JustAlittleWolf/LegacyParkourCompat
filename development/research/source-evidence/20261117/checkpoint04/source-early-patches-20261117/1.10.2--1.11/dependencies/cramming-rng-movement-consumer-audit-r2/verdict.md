# Independent narrow source audit: cramming RNG consumer correction r2

**Verdict: ACCEPT — correction to the omitted consumer disposition only.**

Bound correction: `build/source-early-patches-20261117/1.10.2--1.11/dependencies/cramming-rng-movement-consumer-r2.md`  
SHA-256: `7BED67CBACC5B089972FF982B9CB20341D258B6BDD2D92EBAC14BC395D607589`

The correction binds original report `dependencies/cramming-rng-movement-consumer.md` SHA-256 `FD76D4CA3A694EB62814B046B1B027B23EA133E20F98B34CB83595674A85E227` and its prior independent REQUEST CHANGES verdict SHA-256 `9F5C8B3D35CEE398D84E4744AA5DE8CADB64039B9F2EC49387CF35B29FDA7E4A`. All four additional source hashes in r2 match the exact paired source files.

## Verified correction

The base `Entity.pushAwayFrom()` source in both releases reads `this.random.nextFloat()` and writes one velocity component; the release-specific method bodies and SHA-256 identities are correctly bound. `LocalClientPlayerEntity` overrides `pushAwayFrom()` at both endpoints. The overrides contain no `this.random` read: they gate on `noClip` and `canSurvive`, then write fixed `-0.1F` or `0.1F` horizontal components for selected directions.

The local-player tick invokes its own four `pushAwayFrom()` calls at 1.10.2 lines 680–683 and 1.11 lines 692–695. Java virtual dispatch therefore selects the local-player override, not the base random velocity implementation. A complete search of the paired source trees finds the other base-method call sites only in `ItemEntity` and `ExperienceOrbEntity`, which are non-player entity paths. The report's correction accurately closes this possible consumer without broadening into non-player physics.

The cited `Entity.java` hashes are 1.10.2 `05da145effa19a6ef7934cc276e89226373b67c12f4ce89a8ce2183f29039f77` and 1.11 `3209feaacd1e798b521a193eeb081d92c96881a70a18254ef24a58e911a4fd97`. The cited `LocalClientPlayerEntity.java` hashes are 1.10.2 `a9637065f21ad67464eb5c204c74ebf228c3bb0da8a96ddf4ae73c0490fed443` and 1.11 `65c2747bd8c70def6be7f41f624d4c9493342b39ae7bed7967f9ff63608f59ed`.

## Boundary

Accept only this corrected consumer disposition. The previous consumer audit is not repeated; its other bounded conclusions are reused from the r1 report and verdict. This does not assert general RNG parity, reopen the accepted travel correction, freeze the pair, or claim implementation/runtime parity. No product/wiki inputs or runtime evidence were used.
