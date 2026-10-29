# 1.21.1 → 1.21.2 walk-distance consumer closure

## Scope and verdict

This report closes the source-visible `walkDist` / `walkDistO` update-and-consumer path only. It does not establish full-pair coverage, runtime parity, or an implementation candidate.

The counter update moved from generic `Entity.move` movement-emission bookkeeping in 1.21.1 to `LocalPlayer.move` in 1.21.2. The new local-player value is consumed by client rendering code. The only old non-rendering consumer found is the `Player.attack` sweep-attack gate; that combat path is outside historical client movement scope and is rewritten in 1.21.2 to use `getKnownMovement()` rather than `walkDist`. The complete Java-source searches found no `walkDist` consumer in the local movement predicates or `updateAutoJump`. Therefore this counter change does not establish an in-scope movement predicate or local-player movement delta.

The accumulation conditions and arithmetic do change: 1.21.1 gates the generic counter update on movement-emission policy and passenger state, and measures the resolved horizontal movement vector; 1.21.2 increments from actual local-player position deltas after `super.move`, with no movement-emission/passenger check in that override. This can change the counter values used for rendering. Rendering and the old attack/sweep behavior are outside this closure's movement scope.

## Source identity

Canonical source root: `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/` (read-only).

- **1.21.1 Mojmap:** release-parent `mojmap.provenance.json` SHA-256 `6f84a596ea944a1d40c79b53ead9d2cdd2b3c726d710a977758d15b5578ce7a9`; `mojmap.ready.json` SHA-256 `988b38ac59491f9430d5bd1968db0fdaacb777a63fef745ca3e20342237a0310`; `mojmap.sources.sha256` SHA-256 `900f956e00f6fc1300bb3d689ea49df2b1a57bcaa54617344ef456d95d47cb48`. The ready marker binds version `1.21.1`, mapping `mojmap`, status `ready`, 5,363 source files, and that source-manifest digest. Selected source hashes below all match the parent manifest.
- **1.21.2 Mojmap:** no release-parent ready/provenance/source-manifest sidecar was present. Mapping-root `.decompile-provenance.json` SHA-256 `00f399407dc447737ea83e3e46562418f8c712f66e3f51cb85d46afa2ecac575` contains `inputs` and `sources` maps; every selected source hash below matches its `sources` entry. Its inputs pin `https://piston-meta.mojang.com/v1/packages/c14db17c663edf3d7a698488263a21b1df6d02af/1.21.2.json` to `793e3fd0451354a26aaa00253dcb6600add11f9d2a90f7bdef75ddf5b38c2b45`, the client JAR to `613091da0f62426ae8dcf9afb30eb639485f493b81cda3a2b96b1fb8a6b9976c`, and Mojmap `client.txt` to `e37560e4889168916a73dbc4922c563002eff70fbecfe1cf5c50a7b222f451b2`. These raw input artifacts were not independently rehashed in this task.

Selected path SHA-256 values, recalculated from the canonical files and checked against the stated map for each endpoint:

| Source path | 1.21.1 | 1.21.2 |
|---|---|---|
| `net/minecraft/client/player/LocalPlayer.java` | `c555e68ac3c63ab9b4f9a9e31933e263b96350a2bc599d11a0de5928bc24b583` | `fbd40f1f47adfa66dda9b15188e5dce82af3e8e8d7c3dd0543e602a354ad3fe0` |
| `net/minecraft/world/entity/Entity.java` | `b81905c7879e2cc5c5063a41d865c4164ad919f156306705be791d1017b99850` | `a93719c302a0381a972af75ea360465e2e3551708dd07c34d4d40b7e5173c2b9` |
| `net/minecraft/world/entity/player/Player.java` | `ed32b88c3c7c8418b83db41823520f2b6b0b49a98610306ef26e9681dc925c71` | `a803203e92aa4729d5f5c9b16085b6a43ce51d9907d309eb96736e9c7c1340de` |
| `net/minecraft/client/player/AbstractClientPlayer.java` | `759f8f03b89dab1f6b65c73e1e25a68ae6a6083cb49b5d2719136edda7edf696` | `fa15cd69238b46b410bc709c8ae0a629d5f3f817a6bc454e70a1d60bf7aa785c` |
| `net/minecraft/client/renderer/GameRenderer.java` | `b98778bb63033e31de5dfddc55dc849b3c401c3922a8b33bf2ee8d0f4508d16f` | `2697b5737bf4d8156f9c53d650447266418d268919e7c38339f650de80b1c77f` |
| `net/minecraft/client/renderer/entity/player/PlayerRenderer.java` | `8f35248f8e39bf3f8c35dd0d6bd19e7eff74d62956cde707b15f6f037e58d2f1` | `ce391f5ccc8d55a322d611638c9206229fe287397829b07b2726ad3591cb09cc` |
| `net/minecraft/client/renderer/entity/layers/CapeLayer.java` | `085d4af1bd271a279a289a5a48a55dbe2fb3a22e5b5638c78b81fe623daea87d` | `cac7be7f63dcca687a77a8fb9cd08c40587ab9acbf608e87275d3fed16325d56` |
| `net/minecraft/util/Mth.java` | `aa40272f0f980438ca7e8c874ceedc664feb77958b162d7aa3ed01f233bd2deb` | `98dfb57e239348cf71bf00695b84299232bd5e0cfe206ee8c23a69b72f1099da` |

## Data flow and exact gates

### 1.21.1

`Entity.walkDist` and `walkDistO` are declared on `Entity` (`Entity.java:182–183`). `Entity.tick` snapshots `walkDistO = walkDist` (`Entity.java:430`). In `Entity.move`, after collision resolution, the walk counter increments only inside the `getMovementEmission().emitsAnything() && !isPassenger()` gate (`Entity.java:666–679`). `Player.getMovementEmission` returns `NONE` while flying or while on ground and discrete/sneaking; otherwise it returns `ALL` (`Player.java:1717`). The increment adds `(float) resolvedMovement.horizontalDistance() * 0.6F`.

`LocalPlayer.move` calls `super.move` and then `updateAutoJump` with endpoint X/Z displacement (`LocalPlayer.java:881–885`). The local auto-jump path does not read `walkDist` (`LocalPlayer.java:892 onward`; whole-tree reference search confirms no such use).

### 1.21.2

The two fields now belong to `AbstractClientPlayer` (`AbstractClientPlayer.java:25–26`), whose tick snapshots `walkDistO` before `super.tick` (`AbstractClientPlayer.java:54–58`). Generic `Entity.applyMovementEmissionAndPlaySound` still updates `moveDist` and `flyDist`, but not `walkDist` (`Entity.java:703–711`). `LocalPlayer.move` computes post-move X/Z endpoint differences as floats, invokes `updateAutoJump`, then adds `Mth.length(dx, dz) * 0.6F` to `walkDist` (`LocalPlayer.java:873–880`; `Mth.length(float,float)` is `Mth.java:662–663`). This update is not guarded by `getMovementEmission()` or `isPassenger()` in this override.

### Consumers

A whole-tree search for `walkDist` / `walkDistO` found these consumers/writers:

- 1.21.1: generic `Entity.move` writer; `Entity.tick` snapshot; `GameRenderer.bobView` rendering (`GameRenderer.java:798–799`); `CapeLayer` rendering (`CapeLayer.java:51`); and `Player.attack`'s sweep-attack gate (`Player.java:1173–1177`).
- 1.21.2: `LocalPlayer.move` writer; `AbstractClientPlayer.tick` snapshot; `GameRenderer.bobView` rendering (`GameRenderer.java:338–339`); and `PlayerRenderer` rendering (`PlayerRenderer.java:235`). No 1.21.2 `Player` method reads the counter.

The old `Player.attack` sweep gate checks `walkDist - walkDistO < getSpeed()` under fully-charged, grounded, sword, non-sprinting-knockback conditions. In 1.21.2, the corresponding combat gate uses `getKnownMovement().horizontalDistanceSqr() < Mth.square(getSpeed() * 2.5)` and a sword item tag (`Player.java:1170 onward`), so it no longer consumes the changed local counter. This is an attack/sweep behavior change, not a player-movement predicate; damage, combat and non-player knockback are outside this closure's scope.

## Bounded conclusion and remaining limits

The counter's update phase, source vector and admission gate changed. Source consumers show rendering uses and an old combat/sweep check. The 1.21.2 auto-jump and ordinary player movement predicates do not read the counter; the new attack path no longer reads it either. I found no in-scope movement operation driven by the `walkDist` delta. The rendered bob/animation inputs may differ, but rendering is excluded.

This closes only the `walkDist` / `walkDistO` source path and its directly visible consumers. It does not close the 1.21.1 → 1.21.2 movement pair or establish runtime behavior. No tests, builds, game/server launch, or canonical-source writes were performed.
