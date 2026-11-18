# Independent source audit: 1.13 → 1.13.1 Llama rider Y placement

## Verdict

**Accept the bounded no-difference conclusion for an ordinary adult/default Llama’s direct rider Y placement, numeric expression, and Llama passenger-membership gate. Correct the report’s local-player call-path explanation.** This verifies only the stated operation and source evidence; it does not establish an intermediate patch boundary or runtime parity.

## Frozen input

- Report: `C:/Users/Wolfi/.codex/visualizations/2026/11/17/01a1eb7b-b9ff-7a20-8686-0295dde20f26/c-llama-113-1131-position-intermediate/report.md`
- SHA-256: `a0e1e2107189c2e56699a91002e6c1a61b52271ee16fe2381b608a221a1c7790` (matches the assigned frozen revision).

## Source identity and integrity

Both canonical local source trees are under `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/<version>/ornithe-feather/`. Their surviving provenance records declare the paired run `prep-1.13-1.13.1-feather-2026-10-09-r1`, namespace `ornithe-feather`, Feather build.2 mappings, Java `25.0.3+9-LTS`, and Vineflower `1.12.0`.

| Version | Declared exact Feather mapping | Provenance SHA-256 | Source-manifest SHA-256 | Artifact-manifest SHA-256 |
|---|---|---|---|---|
| 1.13 | `net.ornithemc:feather-gen2:1.13+build.2` | `34652aeef6914b5cbff6619ae60a855aa676b2ae1f607eccdaa623ad09dc5e8d` | `0509c2614b0bb0e616e2ebc6382f8eb5d031b5d86b834a4a6ed7fd96b6d68f1a` | `a2c17075b601d9b83703f7ee188b04c6d9f68cac08d97b1a4f838fe8cd2e2c24` |
| 1.13.1 | `net.ornithemc:feather-gen2:1.13.1+build.2` | `c28ed59f2c507b6133f7677742d316d38e9993eaf1fbb6c2054bd68d1085e904` | `21770b01ff3bf443db2e9e1b6df7db25d2fd6b7a72a86228259269c932d3abba` | `471177ba7bcff332e93ab5bf0127d5c689c3d34fe4f826976207ccfa3bf7b51c` |

The local source manifests contain 2,702 and 2,710 entries, respectively; every listed file was present and matched its manifest SHA-256. The source files cited below also match the hashes recorded in the frozen report. Provenance and ready records remain present and agree on the source-manifest and provenance digests. The source trees themselves are present.

The provenance records also retain pins for each raw client JAR, Feather-mapped client JAR, mapping JAR, and Tiny mapping. Their recorded artifact paths under `build/movement-campaign-2026-10-07/artifacts/` are absent in this checkout, so those input byte streams could not be independently re-hashed. The surviving artifact manifests and provenance substantiate the declared pins, but do not replace the missing bytes. The provenance-declared old `build/.../ready/...` source roots are absent; the canonical `decompiled_minecraft` sources and their manifests are present and verified. This is a missing-artifact limitation, not missing source metadata.

## Bounded source findings

All references below are relative to `decompiled_minecraft/<version>/ornithe-feather/`.

- `net/minecraft/entity/living/mob/passive/animal/LlamaEntity.java:62-65`: the ordinary constructor sets size to `0.9F, 1.87F` in both versions. This supports the report’s default adult-height premise for this constructor path.
- `LlamaEntity.java:146-152` in both versions: `updateRiderPositon(Entity rider)` has one gate, `if (this.hasPassenger(rider))`, then computes `f` and `g` from `bodyYaw` and calls `rider.setPosition(...)`. Its Y argument is exactly `this.y + this.getMountHeight() + rider.getRideHeight()`. Java evaluates this left-associatively as `(this.y + this.getMountHeight()) + rider.getRideHeight()`; all three operands are `double`. The local `h = 0.3F` is unused. Search of each complete Llama source finds no other `setPosition`/`setPos` writer.
- `LlamaEntity.java:156-158` in both versions: `getMountHeight()` returns `this.height * 0.67`; the multiplier is a `double` literal. The constructor’s `1.87F` is stored as the height input, subject to the ordinary `setSize` implementation.
- `net/minecraft/entity/Entity.java:1574-1584` (1.13) and `1575-1585` (1.13.1): `rideTick()` captures the mount, zeros the rider’s three velocities, ticks the rider, then calls the captured mount’s virtual `updateRiderPositon(this)` if the rider remains mounted. The Llama override therefore dispatches for a Llama mount. Its own `hasPassenger` test prevents a stale callback from writing position when the rider is no longer in that Llama’s current passenger list. `Entity.java:2360-2368` (1.13) and `2362-2370` (1.13.1) confirm membership is equality against the current passenger list.
- `net/minecraft/world/World.java:1143-1150` (1.13) and `1154-1161` (1.13.1): the world invokes `entity.rideTick()` when `entity.isRiding()`; the ordinary `entity.tick()` path is used in the `else` branch.
- `net/minecraft/entity/living/player/PlayerEntity.java:425-443` (1.13) and `426-444` (1.13.1): `PlayerEntity.rideTick()` reaches `super.rideTick()` in its ordinary client-side riding branch. `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java:841-848` (1.13) and `845-852` (1.13.1): `LocalClientPlayerEntity.rideTick()` calls `super.rideTick()` before boat-only handling. These overrides add no Llama rider Y placement.
- `PlayerEntity.tick()` and `LocalClientPlayerEntity.tick()` do call their superclass tick methods, but this is not the mounted-player route: the `World` branch above selects `rideTick()` for a riding entity. Thus the report’s statement that the local riding path is inherited through those `tick()` calls is inaccurate. The correct riding override chain is `World` → `LocalClientPlayerEntity.rideTick()` → `PlayerEntity.rideTick()` → `LivingEntity.rideTick()` → `Entity.rideTick()`.
- `net/minecraft/entity/living/mob/passive/animal/ChestedHorseEntity.java` is byte-identical by SHA-256 between the two trees (`7ce0c878108614f60a8f59017251b75a19581ad4f2e344fd5f8eb8c0b8bf6be8`). Llama’s override supplies the applicable mount height. `net/minecraft/entity/EntityType.java` registers `LLAMA` and `LLAMA_SPIT` on both sides. Neither compared source namespace contains a `TraderLlama` class or `TRADER_LLAMA`/`trader_llama` symbol; no Trader Llama behavior is inferred.

The checked source hashes for key operation and dispatch files are:

| Source | 1.13 SHA-256 | 1.13.1 SHA-256 |
|---|---|---|
| `LlamaEntity.java` | `464c8e8056e5ece43f496f022aaf1474a2c3a95ac49817812ef914495a639015` | `5be437bcd5600c4dbcd7ef5e5224ac31baa69efbd69b11152b0bc04081922089` |
| `Entity.java` | `09514488d9055449c47e2e4bcd23b5773203ca634b7ecba2323a99d0ae016642` | `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269` |
| `World.java` | `6e3d94e6cdcfbb15472c4ee981a1c12500eaf7cd5d5dc20d75bcde91174c2eb5` | `119ac6f61b93cf352b2026934e9f95877f55dc2f582b45d75c2bdae3652532b6` |
| `PlayerEntity.java` | `4a57ce65405436aeb211d7a04a9a9f6ae1a661d033fd3ba2424909bfb42b8acd` | `4c1b14b4727116071b1ba9e3eeae84b8077f3d767e10e49b6a68a2556da32272` |
| `LocalClientPlayerEntity.java` | `a188e1bc87831dd9da1ec1366a49acc4104c18e6979129097d02d5d3617e6a08` | `2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29b29d8d9bf42bf` |

## Disposition

No source difference was found for the scoped direct rider Y formula, numeric order/types, Llama membership gate, or local-player rider-Y callback behavior across 1.13 and 1.13.1. Retain the no-finding result with the call-path correction above. Child/variant resizing, other mount operations, absent Trader Llama classes, intermediate-release boundaries, and runtime behavior are outside this verdict.

