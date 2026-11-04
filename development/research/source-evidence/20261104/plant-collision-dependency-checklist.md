# PlantBlock collision dependency checklist

Bounded source-only follow-up for the PlantBlock gate left open in `residual-endpoint-provider-coverage.md`. Exact inputs are Ornithe Feather Gen2 build.2 1.12.2 (ready source manifest SHA `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`) and 1.13.2 (ready source manifest SHA `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`). No shape geometry or parity claims are made here.

## Dispatch dependency

In 1.12.2, `block/PlantBlock.java:65-74` defines both an outline shape and a direct empty `getCollisionShape`; descendants normally inherit the empty collision gate. `block/LilyPadBlock.java:23` additionally declares its own `addCollisions` callback, so it is an explicit exception to assuming the inherited getter is always the effective path; this callback belongs to the separate bespoke-callback lane.

In 1.13.2, `block/PlantBlock.java` no longer overrides `getCollisionShape` or `getShape`. The inherited `block/Block.java:393-394` path returns empty only when the particular registered block has `hasCollision=false`; the property defaults true (`Block.java:1901`) and `.noCollision()` changes it to false (`:1943-1944`). Therefore the old abstract base's unconditional empty getter cannot be generalized to every current subclass without checking its registration.

## Complete transitive class census

Transitive subclasses found by following class `extends` relationships in `net/minecraft/block`:

- 1.12.2: `BeetrootsBlock`, `CarrotsBlock`, `DeadBushBlock`, `DoublePlantBlock`, `FlowerBlock`, `LilyPadBlock`, `MushroomPlantBlock`, `NetherWartBlock`, `PlantBlock`, `PotatoesBlock`, `RedFlowerBlock`, `SaplingBlock`, `StemBlock`, `TallPlantBlock`, `WheatBlock`, `YellowFlowerBlock`.
- 1.13.2: `AttachedPlantStem`, `BeetrootsBlock`, `BushBlock`, `CarrotsBlock`, `DeadBushBlock`, `DoublePlantBlock`, `FlowerBlock`, `LilyPadBlock`, `MushroomPlantBlock`, `NetherWartBlock`, `PlantBlock`, `PotatoesBlock`, `SaplingBlock`, `SeaGrassBlock`, `SeaPickleBlock`, `StemBlock`, `TallFlowerBlock`, `TallPlantBlock`, `TallSeaGrassBlock`, `WheatBlock`.

## Ownership boundary

Exclude these active E1 classes from this checklist's provider-body review; retain their assigned source packets as cross-references:

- Shared-provider drafts A/B: `BeetrootsBlock`, `CarrotsBlock`, `DeadBushBlock`, `FlowerBlock`, `MushroomPlantBlock`, `NetherWartBlock`.
- Shared-provider draft C: `PotatoesBlock`, `SaplingBlock`, `StemBlock`, `TallPlantBlock`, `WheatBlock`.

`DoublePlantBlock` is already in the earlier E2 packet and should be cross-referenced there. `LilyPadBlock`'s old callback is owned by the bespoke `addCollisions` lane. The renamed/reorganized old `RedFlowerBlock`/`YellowFlowerBlock` families are covered through E1's `FlowerBlock` owner. No E1 body is duplicated here.

## Remaining registration dependencies

The following non-E1 descendants are the remaining PlantBlock-family registration branches to account for. This checklist records class inheritance and whether the 1.13.2 constructor enables the base collision path; it does not inspect or claim shape geometry.

| 1.13.2 class | Registration/property evidence | Gate status / dependency |
|---|---|---|
| `PlantBlock` | Abstract base (`block/PlantBlock.java:11`). | No direct override; every concrete registration must be checked. |
| `BushBlock` | Abstract intermediate base (`block/BushBlock.java:22`); `DoublePlantBlock` and `TallFlowerBlock` inherit through it. | No direct registration; defer to each concrete class's properties. |
| `AttachedPlantStem` | Both registrations in `block/Block.java:1189-1193` pass `.noCollision()`. | Base collision gate disabled for these entries; its facing-selected getter is outside this checklist's claims. |
| `DoublePlantBlock` | Tall grass and large fern registrations in `block/Block.java:1438-1441` pass `.noCollision()`. | Base gate disabled; detailed E2 provider packet remains the reference. |
| `LilyPadBlock` | Registration in `block/Block.java:1208` does not pass `.noCollision()`; default `hasCollision=true` applies. Getter declaration is `block/LilyPadBlock.java:30`; old callback declaration is `1.12.2/net/minecraft/block/LilyPadBlock.java:23`. | Active current base collision path; must be cross-checked with old direct callback owner. No geometry/parity conclusion here. |
| `SeaGrassBlock` | Registration in `block/Block.java:927` passes `.noCollision()`. | Base collision gate disabled. |
| `SeaPickleBlock` | Registration in `block/Block.java:1869` does not pass `.noCollision()`; default `hasCollision=true` applies. Getter declaration is `block/SeaPickleBlock.java:88`. | Active current base collision path. Same-named 1.12.2 class absent; no old counterpart is established here. |
| `TallFlowerBlock` | Four registrations in `block/Block.java:1433-1436` pass `.noCollision()`. | Base collision gate disabled. |
| `TallSeaGrassBlock` | Registration in `block/Block.java:931` passes `.noCollision()`. | Base collision gate disabled. |

## Checklist status

- The 1.12.2 and 1.13.2 transitive subclass lists are source-derived and complete for direct Java `extends` ancestry under the two exact `net/minecraft/block` roots.
- Active E1 classes are listed only as exclusions; no duplicate E1 behavior review is claimed.
- Remaining current registrations show why `PlantBlock` cannot be assigned one blanket collision gate: `LilyPadBlock` and `SeaPickleBlock` keep the base gate enabled; several newer plant descendants explicitly disable it.
- Open dependencies: the LilyPad direct callback comparison remains with its callback owner; the SeaPickle provider is 1.13.2-only in this pair; E1 classes remain with their owners; the abstract base still has no single concrete registration. This checklist does not establish any collision geometry, movement consequence, or generic provider closure.

