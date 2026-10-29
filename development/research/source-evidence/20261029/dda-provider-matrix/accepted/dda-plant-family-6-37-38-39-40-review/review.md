# Independent review: plant-family provider closure

Verdict: **ACCEPT** for the bounded registry/state alias, metadata, movement-collision, and separate ray/outline-provider claims for 1.8.9 IDs 6, 37, 38, 39, and 40 compared with 26.2. This review does not close DDA traversal, callback order, first-hit parity, or runtime applicability.

## Immutable inputs

- Finding: `dda-plant-family-6-37-38-39-40/FINDING.md`, SHA-256 `239C2C5ACC830DA6E5C0A0DAE0C3096D114E00164A7B2681434031FFB84B0A85`.
- Source hash JSON: `dda-plant-family-6-37-38-39-40/source-hashes.json`, SHA-256 `5C938FC276F0ACF89533ECB5CFED0B9893D8B51A16C5FFA6A526B2304BD55B68`.
- Exact source identities: 1.8.9 `ornithe-feather`, ready source manifest SHA-256 `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004`; 26.2 `unobfuscated`, ready source manifest SHA-256 `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894`. Both manifest file hashes match their ready markers.

## Checks

- Recomputed all 16 listed Java-file SHA-256 values from the canonical source directories. Every actual digest matches both the report manifest row and its canonical `.sources.sha256` row (0 mismatches).
- Confirmed old numeric registrations: ID 6 is `sapling`; 37/38 are `yellow_flower`/`red_flower`; 39/40 are `brown_mushroom`/`red_mushroom`. The source's metadata decoding supports six sapling species plus stage; yellow flower maps to dandelion; red flower metadata 0–8 maps to the nine stated variants, with metadata 3 `houstonia`; invalid group metadata falls back to index 0. Current registrations provide the six legacy sapling keys among newer species, and current flowers/mushrooms include the stated aliases, with old `houstonia` corresponding to `azure_bluet`.
- Confirmed all five old providers inherit `PlantBlock.getCollisionShape`, which returns null; old `Block.addCollisions` adds a movement box only for a non-null result. The mapped modern registrations use `noCollision`; `BlockBehaviour` returns an empty collision shape when that flag is false.
- Confirmed ray/outline bounds are distinct from the old movement provider: old `PlantBlock`, `SaplingBlock`, and `MushroomPlantBlock` initialize nonempty mutable bounds; old base `updateShape` is a no-op; modern sapling, flower, and mushroom shapes remain nonempty, and flower shape applies the block state's position offset. The report correctly keeps these facts separate from traversal claims.

No material contradiction found in the bounded claims. No product code, tests, builds, or runtime were inspected or changed.
