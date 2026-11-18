# 1.13 → 1.13.1 Llama rider Y placement — intermediate source result

## Exact local source identity

Both compared trees are the paired, exact-version decompilation run `prep-1.13-1.13.1-feather-2026-10-09-r1`, namespace `ornithe-feather`, with separate exact mappings `net.ornithemc:feather-gen2:1.13+build.2` and `net.ornithemc:feather-gen2:1.13.1+build.2`. Provenance declares source versions `1.13` and `1.13.1`, respectively; mapping option `feather`; Java `25.0.3+9-LTS`; Vineflower `1.12.0`.

| Version | Version metadata SHA-256 | Raw client SHA-256 | Feather-mapped client SHA-256 | Feather mapping JAR SHA-256 | Tiny mapping SHA-256 | Provenance SHA-256 | Source manifest SHA-256 |
|---|---|---|---|---|---|---|---|
| 1.13 | `a4db43540601793fe6b4d5c4823f4c55899b84912e48a7a73c00e798775ea2ba` | `d807a5ed9428b6c16b64888fd2b7fa76dcf5c55d06f96f7c0c47762c3fc3e26e` | `4b91c330aaf6179f7384f70f91ebcdf72af2713cc3788fea1482c3529905a761` | `a7a61604c7b365db1f4250c4c0079172c3beff37f4e16f7a53a13d77f3976897` | `7fa6b27aab7245c2d47ecbfe86ad9178272d7b1fd9ba69a482a41659387ffd84` | `34652aeef6914b5cbff6619ae60a855aa676b2ae1f607eccdaa623ad09dc5e8d` | `0509c2614b0bb0e616e2ebc6382f8eb5d031b5d86b834a4a6ed7fd96b6d68f1a` |
| 1.13.1 | `0263d250becfe899c746ee471112823c681a4262a18829192edd0839191c4e1a` | `f0b4cf6d1a87ae150f87a535651138ca7b82bdad8853f76e7ddbff1dd610ac87` | `5e4305c91a3a46a929322180624ab2cf73f33e11f7ffac86ce77c882a524fcbf` | `6737db17e8cc724a018aad93d7fe5051a50e77136848d78d79b62310846fa65d` | `236423be826fb19d08edb3bf7cad9100eab72c5ddbbed8337afcf6ae304c4d14` | `c28ed59f2c507b6133f7677742d316d38e9993eaf1fbb6c2054bd68d1085e904` | `21770b01ff3bf443db2e9e1b6df7db25d2fd6b7a72a86228259269c932d3abba` |

The ready markers agree with those provenance/source-manifest hashes and mark both trees `ready`. For every source below, the local SHA-256 was checked against its exact `ornithe-feather.sources.sha256` entry:

| Source relative to `decompiled_minecraft/<version>/ornithe-feather/` | 1.13 SHA-256 | 1.13.1 SHA-256 |
|---|---|---|
| `net/minecraft/entity/living/mob/passive/animal/LlamaEntity.java` | `464c8e8056e5ece43f496f022aaf1474a2c3a95ac49817812ef914495a639015` | `5be437bcd5600c4dbcd7ef5e5224ac31baa69efbd69b11152b0bc04081922089` |
| `net/minecraft/entity/Entity.java` | `09514488d9055449c47e2e4bcd23b5773203ca634b7ecba2323a99d0ae016642` | `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269` |
| `net/minecraft/entity/living/mob/passive/animal/ChestedHorseEntity.java` | `7ce0c878108614f60a8f59017251b75a19581ad4f2e344fd5f8eb8c0b8bf6be8` | `7ce0c878108614f60a8f59017251b75a19581ad4f2e344fd5f8eb8c0b8bf6be8` |
| `net/minecraft/entity/living/mob/passive/animal/HorseBaseEntity.java` | `a3531c9b0e2cc02eb7adcc1922aaaa53d9b581e9e4436c79d879351a638d6e75` | `7a1ba0175f44a41d454e3047ebda9ea030efe70546c4551da4945d82e7371a9a` |
| `net/minecraft/entity/living/player/PlayerEntity.java` | `4a57ce65405436aeb211d7a04a9a9f6ae1a661d033fd3ba2424909bfb42b8acd` | `4c1b14b4727116071b1ba9e3eeae84b8077f3d767e10e49b6a68a2556da32272` |
| `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `a188e1bc87831dd9da1ec1366a49acc4104c18e6979129097d02d5d3617e6a08` | `2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29b29d8d9bf42bf` |
| `net/minecraft/entity/EntityType.java` | `650da413c012bc9d462c14fd964cb6e5ccc4b9c858918a686ed428c7b87e32d4` | `06cd98bc9922f9d09e4f5aab8cc0b235885183fa6a5d4d38315063501de7d473` |

## Bounded comparison

- `LlamaEntity.java:62-65` constructs the ordinary Llama with `setSize(0.9F, 1.87F)` in both versions. `LlamaEntity.java:146-158` has the same rider callback and mount-height override in both.
- When `hasPassenger(rider)` is true, the callback evaluates `f = cos(bodyYaw * (float)(PI / 180.0))`, then `g = sin(bodyYaw * (float)(PI / 180.0))`, and calls `rider.setPosition(x + 0.3F * g, y + getMountHeight() + rider.getRideHeight(), z - 0.3F * f)`. The direct Y expression is left-associative and unchanged. Llama overrides `getMountHeight()` as `height * 0.67` (double literal); on the ordinary adult constructor default this uses `height == 1.87F`. The `h = 0.3F` local is unused; X/Z use literal `0.3F`, not `h`. This same order and numeric typing occurs in both releases.
- `Entity.java:1574-1585` is identical: `rideTick()` captures the current mount; if still riding a removed mount it stops riding, otherwise it zeros the rider's three velocities, ticks the rider, then dynamically calls the captured mount's `updateRiderPositon(this)` only if the rider remains mounted. With a Llama mount, virtual dispatch reaches `LlamaEntity.updateRiderPositon`; its own `hasPassenger` guard rejects a stale/non-member rider (including a rider that changed mounts during its tick). `Entity.java:2360-2368` implements that guard by equality against the current passenger list. The default `Entity.canAddPassenger` cap at `Entity.java:1673-1675` is also unchanged; forced riding bypasses admission checks, but not the later callback guards.
- A local player inherits the riding tick path through `PlayerEntity.tick()` calling `super.tick()` (`PlayerEntity.java:175-208`); `LocalClientPlayerEntity.tick()` calls `super.tick()` inside its loaded-chunk guard (`LocalClientPlayerEntity.java:181-190`). Neither class adds a different Llama Y placement. Their riding-related code is only context for the ordinary local-player path; it does not change the callback formula.
- `ChestedHorseEntity.java:56-59` subtracts `0.25` from its superclass mount height, but the Llama's own override supersedes that method. Neither it nor `HorseBaseEntity.java` adds an alternate player Y write for this callback. The inherited ride-control flag is not involved in this placement.
- `EntityType.java` registers `LLAMA` and `LLAMA_SPIT` on both sides; neither tree contains a `TraderLlama` class or `TRADER_LLAMA` entity type. No Trader Llama behavior is inferred or substituted.

## Result and bounds

No source difference found for the ordinary adult/default Llama's direct rider Y placement, formula order, numeric types, or callback and passenger-membership guards between exact 1.13 and 1.13.1. This does not establish an intermediate release boundary, child/variant resizing behavior, or behavior for absent Trader Llama classes. Vehicle/non-player physics and other mount/player movement operations are outside this assigned slice. No permanent movement finding is warranted from this pair and operation.

## Artifact recheck limitation

The local `ornithe-feather.artifacts.sha256` manifests and ready/provenance records agree on the client, mapped-client and Feather mapping artifact SHA-256 values tabulated above. The exact artifact paths recorded in provenance under `build/movement-campaign-2026-10-07/artifacts/` are absent in this checkout, so those raw/mapping artifact byte streams could not be independently re-hashed here. The source manifests themselves and every source file used above were present and their actual SHA-256 values were checked. Treat the declared input artifact pins as provenance-backed but not locally revalidated bytes for this intermediate result.
