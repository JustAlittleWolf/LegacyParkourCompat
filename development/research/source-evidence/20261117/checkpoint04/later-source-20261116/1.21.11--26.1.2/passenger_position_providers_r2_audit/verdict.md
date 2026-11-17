# Independent source audit: passenger-position provider crosswalk r2

## Verdict

**ACCEPT** the corrected bounded report `build/later-source-20261116/1.21.11--26.1.2/passenger_position_providers_revision_r2/coverage-r2.md`, SHA-256 `10d114fdf042e6abc1eea41eede0988f4f74ce553775ff3cf5361cffd0bdd6a3`.

The r2 correction fixes the r1 Happy Ghast census error without expanding the gameplay claim. The existing provider comparisons, Camel/Goat age-scale findings, and partial limits remain as independently checked in the r1 audit. This accepts only the bounded provider inventory; ordinary Player reachability for the conditional baby-animal cases, full pair coverage and runtime parity remain open.

## R2 correction check

The r2 diff from the audited r1 report changes only the age-scale census paragraph and adds one `HappyGhast.java` row to the citation table. Current report bytes hash to the assigned SHA above.

The exact source identities use the available version-root sidecars: 1.21.11 `decompiled_minecraft/1.21.11/mojmap.provenance.json` SHA-256 `99c9fb741eb5e4ac8fb5780e2158da324d7c8b6afc179d494e5986f78fe29f72`, and 26.1.2 `decompiled_minecraft/26.1.2/unobfuscated.provenance.json` SHA-256 `8d727f880cc91f4a297e148c5b75976303d051457334bc689268fbdf631a012a`. The ready sidecars and manifests are `mojmap.ready.json` / `mojmap.sources.sha256` and `unobfuscated.ready.json` / `unobfuscated.sources.sha256`; their hashes match the report, and their version, namespace and `ready` fields are correct. No full-tree rehash or artifact inspection was performed.

The newly added source-file hashes match both current bytes and the exact manifest rows:

- 1.21.11 `net/minecraft/world/entity/animal/happyghast/HappyGhast.java`: `0c2a0b3a97d8c7bc2de50924565c55367247f5cd7d9b59ff4fde3692e25610ea`.
- 26.1.2 `net/minecraft/world/entity/animal/happyghast/HappyGhast.java`: `798c1eb723e4e9640dd342c645829460b98029360d88a1467dc8170cfa54606e`.

Both `EntityType.java` files declare `HAPPY_GHAST`. Both `HappyGhast.java` files return `0.2375F` for baby and `1.0F` otherwise from `getAgeScale()`, have no passenger-position override, and implement `canAddPassenger` as passenger count `< 4`. `LivingEntity.getPassengerRidingPosition` in both releases multiplies the attachment input by `getScale() * getAgeScale()`, so the corrected report accurately includes Happy Ghast as an unchanged common input/provider route. A recursive declaration search found the age-scale overrides in this slice: the shared `LivingEntity` base plus Armadillo, Turtle, Dolphin, Camel and Happy Ghast in both sources, with Goat added in 26.1.2; the census now distinguishes the unchanged Happy Ghast input from the changed Camel/Goat inputs.

The corrected report does not claim that an ordinary Player mounts a Happy Ghast, Camel or Goat in the relevant conditions. It preserves forced/synchronized attachment and other source dependencies as open gates.

No backlog/product/canonical finding, build, test, runtime, source-generation or Git operation was performed.
