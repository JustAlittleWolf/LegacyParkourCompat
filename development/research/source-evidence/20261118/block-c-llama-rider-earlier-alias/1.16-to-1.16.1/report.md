# C — Llama passenger Y positioning, 1.16 → 1.16.1

## Scope and result

Bounded source slice: the ordinary adult, standing `LocalPlayer` as the sole passenger of a default adult vanilla `Llama` or `TraderLlama`; its normal riding tick and the mount's passenger-position callback. The exact 1.16 and 1.16.1 Mojmap source files for this slice are byte-identical. **No change found** for the direct player Y transform, callback, ancestry, entity dimensions, or offsets in this pair. This is a bounded no-difference result, not full-pair movement coverage or an independent audit.

No wiki inventory, mod implementation, Git history, generated source, test, or game/runtime evidence was consulted. The published exact-version sources were consumed read-only.

## Exact source claims

All paths below are relative to `decompiled_minecraft/<version>/mojmap/`; the same paths and cited line ranges were checked on both versions.

- `net/minecraft/client/player/LocalPlayer.java:78,862-872`: `LocalPlayer extends AbstractClientPlayer` and its `rideTick()` delegates to `super.rideTick()` before its local boat-input handling.
- `net/minecraft/client/player/AbstractClientPlayer.java:20`: `AbstractClientPlayer extends Player`. It does not override the riding-position callback or offset.
- `net/minecraft/world/entity/player/Player.java:111,452-465,989-991` and `net/minecraft/world/entity/LivingEntity.java:2575-2577`: `Player extends LivingEntity`; its riding branch calls `LivingEntity.rideTick()`, which calls `Entity.rideTick()`. `Player.getMyRidingOffset()` returns the `double` literal `-0.35`.
- `net/minecraft/world/entity/Entity.java:1557-1562,1565-1585`: `Entity.rideTick()` zeroes rider velocity, ticks the rider, then if it remains a passenger calls `this.getVehicle().positionRider(this)`. The inherited default positioning method has a passenger gate, sums mount Y + mount offset + rider offset, then calls the move function. Default entity riding offset is `0.0`; the mount offset default is height times `0.75` (overridden by Llama).
- `net/minecraft/world/entity/animal/horse/TraderLlama.java:22-27,34-42`: `TraderLlama extends Llama`, calls the Llama constructor, and adds no positioning or riding-offset override. Thus normal dispatch uses Llama's overrides for both Llama types.
- `net/minecraft/world/entity/animal/horse/Llama.java:65-67,150-162`: Llama's constructor delegates to its superclass. Its overriding `positionRider(Entity)` has the gate `this.hasPassenger(entity)` and directly calls `entity.setPos(...)`; its Y argument is exactly `this.getY() + this.getPassengersRidingOffset() + entity.getMyRidingOffset()`. Its `getPassengersRidingOffset()` returns `this.getBbHeight() * 0.67`. The Y sum is left-associative Java `double` arithmetic: `getBbHeight()` returns `float`, which is widened for multiplication by the unsuffixed `double` literal `0.67`; the returned mount offset and player `-0.35` are then added in source order to mount Y. The X/Z expressions are outside this Y finding.
- `net/minecraft/world/entity/EntityType.java:275-276,420-421`: both `LLAMA` and `TRADER_LLAMA` are registered at `.sized(0.9F, 1.87F)`.
- `net/minecraft/world/entity/LivingEntity.java:483-485,2955-2957` and `EntityType.java:474-476`: for the bounded non-baby adult state the default scale is `1.0F`; standing player dimensions are registered as `0.6F × 1.8F`. Player height is not used in the Llama Y formula; the rider contribution is the constant `-0.35`.
- `net/minecraft/world/entity/animal/horse/AbstractHorse.java:903-923` has a more general horse positioning method, including a stand-animation term. Llama overrides it, so that parent term is not part of either Llama's or TraderLlama's direct passenger Y write.

With the registered adult mount height `1.87F`, the source expression computes the mount contribution as binary32 `1.87F` widened to binary64, then multiplied by binary64 `0.67` (`1.252900003194809`); it adds binary64 `-0.35` (`0.902900003194809` total Y delta) and then adds mount `getY()`. This numeric evaluation preserves the source's operation order and literal types; it is not a claim about later movement/teleport paths.

## Pair comparison

Direct SHA-256 comparison of these full source files returned equality for 1.16 and 1.16.1:

| Relative source path | SHA-256 |
| --- | --- |
| `net/minecraft/world/entity/animal/horse/Llama.java` | `942fce37f13216906fe48d620076f6514fd93d4a3983eac8a8909170a9385c5c` |
| `net/minecraft/world/entity/animal/horse/TraderLlama.java` | `495d65da1c1903ba769793ee4dbccd6248ef96137c1709af6daa00c563c03dd4` |
| `net/minecraft/world/entity/animal/horse/AbstractHorse.java` | `7f6c50efa62362bf5bf86a085c52292dc8b5274e27989870beb635be0aac51c2` |
| `net/minecraft/world/entity/animal/horse/AbstractChestedHorse.java` | `7ea766ba27894300f605eff2f47df10507d2e8311d27d0ca32ea7df5905b9268` |
| `net/minecraft/world/entity/player/Player.java` | `a849960d47bf00a2cddfb6636c78c518532a11967ba08829f19c5ebced08681e` |
| `net/minecraft/world/entity/Entity.java` | `27e3bb01b5d03c09161891e717c16477899768333082e81ebfaac4e525616576` |
| `net/minecraft/world/entity/EntityType.java` | `e274f16ec3546723abb45a888332c4501d747a8020cf2d397d7b6d96f2a6dd65` |
| `net/minecraft/client/player/LocalPlayer.java` | `ee20523adfdb82f3753fa43312af170f34c4f4aa78570631c61bc8c0820dfb8b` |
| `net/minecraft/client/player/AbstractClientPlayer.java` | `89dde15acaa210ecdfd6bb429a8fc969acdd189feb03c0ea20faea13865ea64f` |
| `net/minecraft/world/entity/LivingEntity.java` | `b6ea2b9b037b79fc3ea6e1f6bcd2c4c1d89d4a96315968a8f247641f6c335f26` |

## Canonical source identity and integrity

Exact release IDs are `1.16` and `1.16.1`; both published trees use the `mojmap` namespace, Mojang official mappings, and Vineflower output. Identity below is read from each tree's `mojmap.ready.json`, `mojmap.provenance.json`, `artifacts.sha256`, and `mojmap.sources.sha256`. Full file digests are SHA-256.

| Identity | 1.16 | 1.16.1 |
| --- | --- | --- |
| Client artifact SHA-256 | `00740ef4475d4cd0a1d7064f89602f759c45ff9afe29da9b0ee0b9037ebb2a71` | `b4831e7b63b10588ff06ea86322108d916c536446f3aea3ae988b7fe3e1f66ef` |
| Official client mappings SHA-256 | `53d46abe149aadd367be8789207c53a7cee46ba8382e5bfd60247eb22984119d` | `6cc374f819bca44d940f8912044b07bdac3e240a02e37117ae1d4ad63c21409b` |
| Source inventory file SHA-256 (`mojmap.sources.sha256`) | `e3c23e94ac985c25dec0c768aed17b443ba4823662cf1e152235c99756ae5d4a` | `76fdd121070c2012f48cbdf5cf0c78490116afc94117c30b0858eedcd6ab9754` |
| Source file count / bytes from ready marker | `3572 / 19,220,152` | `3572 / 19,220,344` |
| Artifact manifest SHA-256 (`artifacts.sha256`) | `b482d7aa44422dfafebad3a0b2a886d47d1f01135f99055aea5973faa110e8b2` | `a113f9d59fa4582effb2ebf548c4388a54c9c5654fbf57fae7974dce896d1f98` |
| Provenance JSON SHA-256 (`mojmap.provenance.json`) | `371d50521a183f4a3f42fac2d18021f228e0a4e6b2e8a9838c2805391709f2a9` | `a6ff065188a400ec891980cdf2e6c0c238bf5e05168a2f69b29c6cecc2214b98` |
| Ready marker SHA-256 (`mojmap.ready.json`) | `5b6cb24f95e83b856c53f646d6477f2e46dae2276737dee56dd07c5bd745e4a2` | `c19d707f7d6622733b5189599ff77aa7999cc11a3d900f29d150f9fab203ffcb` |
| Version metadata SHA-256 | `32ea7a259475ba6f2dc03f979bb2429115ba4137727f776dfd22cc55b1483297` | `9688ec094a807c45513733388f7c8426d80a58cee96ed86be5380cb6a45f4a73` |

The version manifest SHA-256 recorded for each side is `845dfb7f8b28ce06bf0752b597ef2d4d65df973e4b59d8d6ff429ad2d3adde04`. The manifests identify the exact version metadata IDs `1.16` and `1.16.1`; the source publication directory and ready marker match those IDs. Each source inventory contains the Llama, TraderLlama, Entity, Player, LocalPlayer, AbstractClientPlayer, EntityType, LivingEntity and AbstractHorse paths cited above. No source generation or repair was done during this review.

## Bounds and handoff

This closes only the named Llama passenger-position slice for this exact adjacent pair, with an unchanged result. It does not claim that all riding mounts, all player transforms, all tick operations, or all source dependencies in the 1.16 → 1.16.1 pair were reviewed. It excludes multiple/custom passengers, baby entities, pose changes, dismount placement, teleport/chunk relocation, non-player riders, vehicle physics, and subsequent collision/movement responses. No implementation candidate or code change follows from this no-difference comparison.