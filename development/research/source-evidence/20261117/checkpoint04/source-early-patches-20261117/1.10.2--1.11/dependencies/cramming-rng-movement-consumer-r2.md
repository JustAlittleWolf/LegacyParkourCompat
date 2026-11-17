# Cramming RNG movement-consumer inventory correction r2

This narrow correction is attached to the immutable 1.10.2→1.11 report `dependencies/cramming-rng-movement-consumer.md` (SHA-256 `FD76D4CA3A694EB62814B046B1B027B23EA133E20F98B34CB83595674A85E227`) and its independent verdict `dependencies/cramming-rng-movement-consumer-audit/verdict.md` (SHA-256 `9F5C8B3D35CEE398D84E4744AA5DE8CADB64039B9F2EC49387CF35B29FDA7E4A`, REQUEST CHANGES). It corrects only the omitted `Entity.pushAwayFrom()` consumer disposition; all other audited conclusions and exclusions remain bound to r1.

## Corrected consumer disposition

The base `Entity.pushAwayFrom(double,double,double)` samples `this.random.nextFloat()` and writes velocity in both releases (1.10.2 `Entity.java:1736–1783`; 1.11 `:1786–1838`). However, `LocalClientPlayerEntity` overrides this method at 1.10.2 `LocalClientPlayerEntity.java:360–410` and 1.11 `:370–420`. Each override checks `noClip` and `canSurvive` and, if it selects a direction, writes the corresponding horizontal velocity component as fixed `±0.1F`; it does not read entity RNG. The local player's four tick calls at 1.10.2 `LocalClientPlayerEntity.java:680–683` and 1.11 `:692–695` dispatch to this override. The 1.10.2 and 1.11 override bodies are identical for this behavior.

The only other source call sites of the base method in the paired source trees are `ItemEntity` and `ExperienceOrbEntity` (`ItemEntity.java:88` / `:92`; `ExperienceOrbEntity.java:86` / `:85` respectively). Those are non-player entities. Thus the base random velocity write is not an eligible local-player movement consumer, and the existing bounded no-eligible-local-player-consumer disposition stands after this inventory correction.

## Exact additional source bindings

| Release | Source file | SHA-256 |
|---|---|---|
| 1.10.2 | `net/minecraft/entity/Entity.java` | `05da145effa19a6ef7934cc276e89226373b67c12f4ce89a8ce2183f29039f77` |
| 1.11 | `net/minecraft/entity/Entity.java` | `3209feaacd1e798b521a193eeb081d92c96881a70a18254ef24a58e911a4fd97` |
| 1.10.2 | `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `a9637065f21ad67464eb5c204c74ebf228c3bb0da8a96ddf4ae73c0490fed443` |
| 1.11 | `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `65c2747bd8c70def6be7f41f624d4c9493342b39ae7bed7967f9ff63608f59ed` |

This addendum does not repeat the prior source audit, assert general RNG parity, reopen the accepted travel correction, freeze the pair, or claim implementation/runtime parity.
