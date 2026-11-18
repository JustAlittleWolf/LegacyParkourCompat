# Independent source audit — Llama passenger Y positioning, 1.16 → 1.16.1

## Verdict

**ACCEPT the bounded no-difference finding, with one required correction to its arithmetic explanation.** The report SHA-256 matches the frozen identity supplied for this audit. The exact Mojmap source files examined on both releases are equal, and the player riding call chain reaches the same Llama override for either registered mount type. This is not a full-pair movement audit.

Frozen input: `C:/Users/Wolfi/.codex/visualizations/2026/11/17/01a1eb7b-b9ff-7a20-8686-0295dde20f26/c-llama-116-1161-position-intermediate/report.md`

- SHA-256: `bdbf4d2851d6207d929c4622de2ea17d9bbcc74de5b723363225087fa7bb1849` (recomputed; matches assigned identity).
- Exact releases/mappings: `1.16/mojmap` and `1.16.1/mojmap`; both ready markers identify the stated releases and Vineflower output. The report's source-manifest, artifact-manifest, ready-marker, provenance, client-artifact, and official mapping hashes match the canonical files/metadata. The ten cited Java files below each match the corresponding source-manifest entry on both releases.

## Independent trace and exact source evidence

All paths are relative to `decompiled_minecraft/<release>/mojmap/`; ranges refer to both releases (the paired files have identical SHA-256 and line content).

- `net/minecraft/client/multiplayer/ClientLevel.java:204-216`: a passenger player is ticked with `entity2.rideTick()` through the ordinary client passenger tick.
- `net/minecraft/client/player/LocalPlayer.java:862-872`: `LocalPlayer.rideTick()` delegates to `super.rideTick()` before its boat-input handling.
- `net/minecraft/world/entity/player/Player.java:452-465`: when it does not stop riding, `Player.rideTick()` delegates to `LivingEntity`; `Player.java:989-991` returns the `double` literal `-0.35` from `getMyRidingOffset()`.
- `net/minecraft/world/entity/LivingEntity.java:2575-2577` delegates to `Entity.rideTick()`.
- `net/minecraft/world/entity/Entity.java:1557-1562`: after zeroing rider velocity and ticking the rider, the still-passenger branch calls `this.getVehicle().positionRider(this)` exactly once. The static receiver type does not change virtual dispatch on the vehicle.
- `net/minecraft/world/entity/animal/horse/Llama.java:150-162`: Llama overrides that callback. Under `hasPassenger(entity)`, it calls `entity.setPos(...)`; the Y argument is `this.getY() + this.getPassengersRidingOffset() + entity.getMyRidingOffset()`. The same class returns `this.getBbHeight() * 0.67` for the mount offset.
- `net/minecraft/world/entity/animal/horse/TraderLlama.java:22-27,34-42`: TraderLlama extends Llama, delegates construction to Llama, and does not override `positionRider` or `getPassengersRidingOffset`; the inherited Llama callback therefore applies to both registered types.
- `net/minecraft/world/entity/EntityType.java:275-276,420-421`: `LLAMA` and `TRADER_LLAMA` both register dimensions `.sized(0.9F, 1.87F)`. For the bounded adult state, `LivingEntity.java:483-485` returns scale `1.0F` when not a baby, and `Entity.java:2712-2719` obtains the mount bounding-box height from its type dimensions. `Player.java:1987-1989` gives standing player dimensions from `STANDING_DIMENSIONS`; the Y formula does not use player height.
- `net/minecraft/world/entity/animal/horse/AbstractHorse.java:905-923`: the parent horse positioning implementation includes a stand-animation term, but Llama overrides the method, so that parent callback is not invoked for either Llama type.
- `net/minecraft/world/entity/Entity.java:1565-1585`: inherited default positioning and riding offsets are not the callback/offset used for this Llama path.

### Arithmetic correction

The report's source formula is transcribed correctly, but its numeric-explanation sentence reverses the addition order by saying the `-0.35` offset is added before mount `getY()`. Java evaluates the source expression left-to-right. For an adult default Llama height:

```text
((mountY + ((double) 1.87F * 0.67D)) + -0.35D)
```

`1.87F` is binary32 first and widens to binary64 for multiplication by the unsuffixed `double` literal `0.67`; the mount's returned offset is binary64. The player offset is binary64 `-0.35`. The mount Y is added first, then the offset, exactly as the source groups it. At `mountY == 0.0`, the resulting Y is `0.902900003194809`; for arbitrary mount Y, do not regroup this into `mountY + (mountOffset - 0.35)`, since rounding can differ. This corrects the explanation without changing the no-difference conclusion.

For the bounded path the only direct position write in the mount callback is `entity.setPos`, where `entity` is the rider. The callback does not write Llama position or move another actor. This does not cover teleport/chunk-relocation handling (`Entity.java:2281-2283`), multiple/custom passengers, baby mounts, pose changes, dismounting, or subsequent collision/movement.

## SHA-256 evidence

Each listed pair is byte-identical and each digest matches its release's `mojmap.sources.sha256` entry. Hashes are lower-case SHA-256.

| Relative source path | 1.16 SHA-256 | 1.16.1 SHA-256 | Equal |
| --- | --- | --- | --- |
| `net/minecraft/world/entity/animal/horse/Llama.java` | `942fce37f13216906fe48d620076f6514fd93d4a3983eac8a8909170a9385c5c` | `942fce37f13216906fe48d620076f6514fd93d4a3983eac8a8909170a9385c5c` | yes |
| `net/minecraft/world/entity/animal/horse/TraderLlama.java` | `495d65da1c1903ba769793ee4dbccd6248ef96137c1709af6daa00c563c03dd4` | `495d65da1c1903ba769793ee4dbccd6248ef96137c1709af6daa00c563c03dd4` | yes |
| `net/minecraft/world/entity/animal/horse/AbstractHorse.java` | `7f6c50efa62362bf5bf86a085c52292dc8b5274e27989870beb635be0aac51c2` | `7f6c50efa62362bf5bf86a085c52292dc8b5274e27989870beb635be0aac51c2` | yes |
| `net/minecraft/world/entity/animal/horse/AbstractChestedHorse.java` | `7ea766ba27894300f605eff2f47df10507d2e8311d27d0ca32ea7df5905b9268` | `7ea766ba27894300f605eff2f47df10507d2e8311d27d0ca32ea7df5905b9268` | yes |
| `net/minecraft/world/entity/player/Player.java` | `a849960d47bf00a2cddfb6636c78c518532a11967ba08829f19c5ebced08681e` | `a849960d47bf00a2cddfb6636c78c518532a11967ba08829f19c5ebced08681e` | yes |
| `net/minecraft/world/entity/Entity.java` | `27e3bb01b5d03c09161891e717c16477899768333082e81ebfaac4e525616576` | `27e3bb01b5d03c09161891e717c16477899768333082e81ebfaac4e525616576` | yes |
| `net/minecraft/world/entity/EntityType.java` | `e274f16ec3546723abb45a888332c4501d747a8020cf2d397d7b6d96f2a6dd65` | `e274f16ec3546723abb45a888332c4501d747a8020cf2d397d7b6d96f2a6dd65` | yes |
| `net/minecraft/client/player/LocalPlayer.java` | `ee20523adfdb82f3753fa43312af170f34c4f4aa78570631c61bc8c0820dfb8b` | `ee20523adfdb82f3753fa43312af170f34c4f4aa78570631c61bc8c0820dfb8b` | yes |
| `net/minecraft/client/player/AbstractClientPlayer.java` | `89dde15acaa210ecdfd6bb429a8fc969acdd189feb03c0ea20faea13865ea64f` | `89dde15acaa210ecdfd6bb429a8fc969acdd189feb03c0ea20faea13865ea64f` | yes |
| `net/minecraft/world/entity/LivingEntity.java` | `b6ea2b9b037b79fc3ea6e1f6bcd2c4c1d89d4a96315968a8f247641f6c335f26` | `b6ea2b9b037b79fc3ea6e1f6bcd2c4c1d89d4a96315968a8f247641f6c335f26` | yes |

Source identities additionally verified from canonical metadata: `1.16/mojmap.ready.json` SHA-256 `5b6cb24f95e83b856c53f646d6477f2e46dae2276737dee56dd07c5bd745e4a2`; `1.16.1/mojmap.ready.json` SHA-256 `c19d707f7d6622733b5189599ff77aa7999cc11a3d900f29d150f9fab203ffcb`. Source-manifest SHA-256 values are `e3c23e94ac985c25dec0c768aed17b443ba4823662cf1e152235c99756ae5d4a` and `76fdd121070c2012f48cbdf5cf0c78490116afc94117c30b0858eedcd6ab9754`, respectively.

No implementation, product history, wiki inventory, generation, runtime, launch or build evidence was consulted. Pair scope remains the named adult standing-player / sole-passenger / ordinary client riding path only.
