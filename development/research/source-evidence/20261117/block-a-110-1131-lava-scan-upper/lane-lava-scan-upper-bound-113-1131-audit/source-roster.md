# Frozen source roster — 1.13 → 1.13.1 lava scan audit

Roster frozen before reading the source bodies or the assigned comparison artifact. Scope: local-player lava occupancy scan boundaries and predicates, local travel/jump call gates, and client current-chunk/missing-chunk dispatch. This is a bounded operation roster, not full-pair coverage.

## Canonical publication

- Pair: Minecraft 1.13 → 1.13.1
- Namespace: Ornithe Feather (`ornithe-feather`)
- Preparation run: `prep-1.13-1.13.1-feather-2026-10-09-r1`
- Mapping coordinates: `net.ornithemc:feather-gen2:1.13+build.2`; `net.ornithemc:feather-gen2:1.13.1+build.2`
- Version-root ready manifests: `decompiled_minecraft/1.13/ornithe-feather.ready.json` SHA-256 `82a4a8f6a03fbd99a7b8577b1ae5980d95af07c7ded0da284106d16eb1e01a99`; `decompiled_minecraft/1.13.1/ornithe-feather.ready.json` SHA-256 `f098b3a08b37276cc1a465a8a122df8de76d68786c854b92bac1ca51a79670de`
- Provenance manifests: 1.13 SHA-256 `34652aeef6914b5cbff6619ae60a855aa676b2ae1f607eccdaa623ad09dc5e8d`; 1.13.1 SHA-256 `c28ed59f2c507b6133f7677742d316d38e9993eaf1fbb6c2054bd68d1085e904`
- Source manifests: 1.13 SHA-256 `0509c2614b0bb0e616e2ebc6382f8eb5d031b5d86b834a4a6ed7fd96b6d68f1a`; 1.13.1 SHA-256 `21770b01ff3bf443db2e9e1b6df7db25d2fd6b7a72a86228259269c932d3abba`
- Exact source file SHA-256 values below were checked against each root's source manifest and recomputed from the corresponding file. Every listed hash matched.

## Paired source files

| Relative path | 1.13 SHA-256 | 1.13.1 SHA-256 |
|---|---|---|
| `net/minecraft/client/entity/living/player/ClientPlayerEntity.java` | `99b57acea68323125d859313ef5dd2c55d0703c08c5cce475c0227da0d12d1ee` | `99b57acea68323125d859313ef5dd2c55d0703c08c5cce475c0227da0d12d1ee` |
| `net/minecraft/client/world/ClientWorld.java` | `07e09cc5636ff7e12f2ca35aef528fca6305e6017d499a65926617cef82f64dc` | `bae1d6d653260e42d735b9652e5c89711f1bd78eacba7993e2460f2f2528640a` |
| `net/minecraft/client/world/chunk/ClientChunkCache.java` | `7e17147b171b739a4b6078397242faa97dcb8c47dbb73c81aa918ab42352f66e` | `d416574e48cf414145b2c8cb8c1b7ef9e39d44aaf876972d724316fd01957f88` |
| `net/minecraft/entity/Entity.java` | `09514488d9055449c47e2e4bcd23b5773203ca634b7ecba2323a99d0ae016642` | `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269` |
| `net/minecraft/entity/living/player/PlayerEntity.java` | `4a57ce65405436aeb211d7a04a9a9f6ae1a661d033fd3ba2424909bfb42b8acd` | `4c1b14b4727116071b1ba9e3eeae84b8077f3d767e10e49b6a68a2556da32272` |
| `net/minecraft/world/World.java` | `6e3d94e6cdcfbb15472c4ee981a1c12500eaf7cd5d5dc20d75bcde91174c2eb5` | `119ac6f61b93cf352b2026934e9f95877f55dc2f582b45d75c2bdae3652532b6` |
| `net/minecraft/world/chunk/WorldChunk.java` | `85d6203e3c3b1ebbb134b23b79fde4fd8f7a1950e9c065e3fd5ade030344029c` | `b1ee0253097c441ee75f05c187c086c3b37453dd74b276762ee58274c304d97b` |
| `net/minecraft/block/state/BlockState.java` | `0496fda381628e90b3b3ff8ae376a5bae24cbd662366f34d70991252fab2fe70` | `0496fda381628e90b3b3ff8ae376a5bae24cbd662366f34d70991252fab2fe70` |
| `net/minecraft/block/state/BlockStateImpl.java` | `20d8533df7e6675d835f8a1ed651db97c9a453671eac2ecc39e388875abbedda` | `20d8533df7e6675d835f8a1ed651db97c9a453671eac2ecc39e388875abbedda` |
| `net/minecraft/fluid/Fluid.java` | `0c0232eefb54dd073f251bd0d1f97948bc25f6713ea2866af955d82c7ae1ba96` | `55afdc25f330663746c253e29a0e272666c595f17cacbc78785b0a85b00f094e` |
| `net/minecraft/fluid/LavaFluid.java` | `1db521d50ec5b5fa744b9a5151bd32e5282638355ecedb51c31e4e09f7fb2924` | `1db521d50ec5b5fa744b9a5151bd32e5282638355ecedb51c31e4e09f7fb2924` |
| `net/minecraft/fluid/state/FluidState.java` | `05e2df9377a3bc1e91645ce4282bcaa4edebe93d7ef1907407961baaecff23a6` | `05e2df9377a3bc1e91645ce4282bcaa4edebe93d7ef1907407961baaecff23a6` |
| `net/minecraft/fluid/state/FluidStateImpl.java` | `cffddf7de4779c0d6e7508b827db506ea3b0cfe1a010fe24c2a8e042c2398b71` | `cffddf7de4779c0d6e7508b827db506ea3b0cfe1a010fe24c2a8e042c2398b71` |
