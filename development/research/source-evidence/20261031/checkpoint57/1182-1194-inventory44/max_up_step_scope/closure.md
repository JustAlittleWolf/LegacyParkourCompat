# 1.19.3 → 1.19.4 `maxUpStep()` member closure

## Exact inputs and integrity

The read-only source trees are `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.19.3\mojmap` and `...\1.19.4\mojmap` (Mojmap). Both `mojmap.ready.json` markers say `ready`, and each provenance record names the corresponding exact version and Mojmap namespace. The markers' source-manifest digests match the computed SHA-256 values below. The artifact-manifest and movement-diagnostics digests also match each ready marker, and each source-manifest line count matches `sourceFileCount`.

| Version | Source manifest SHA-256 | Files in manifest |
| --- | --- | ---: |
| 1.19.3 | `6f1cc6d07ab3902a7ea25a3f8817504723be70d82e97f541cffda95ea2c1c843` | 4,618 |
| 1.19.4 | `6286e325371e085dfee1ab7987e66d4b6d1a984a49b6ec8ea730045cd24cecb3` | 4,740 |

Every source file referenced below was checked against its own version's source manifest. SHA-256 values are listed at the end.

## Source closure

In 1.19.3, `Entity` exposes `maxUpStep` as a public float field, and `LivingEntity` initializes it to `0.6F`; there is no `maxUpStep()` override. `Entity.collide` checks that field and uses it for the step-up candidates, comparing horizontal-distance squares before applying the vertical correction. (1.19.3 `Entity.java:180, 799–819`; `LivingEntity.java:236–247`.)

In 1.19.4, `Entity` makes the backing field private and adds `maxUpStep()`/`setMaxUpStep(float)` accessors; `LivingEntity` initializes the value to `0.6F` through the setter and overrides the getter. That override returns `Math.max(super.maxUpStep(), 1.0F)` only when `this.getControllingPassenger() instanceof Player`. The collision solver keeps the same eligibility gate (`onGround` or downward collision, plus horizontal collision), candidate ordering, horizontal-distance comparisons and vertical correction, but reads `maxUpStep()` at each former field access. (1.19.4 `Entity.java:182, 801–822, 3167–3173`; `LivingEntity.java:231–243, 3341–3345`.)

`Entity.move` calls `maybeBackOffFromEdge` before `collide` in both versions. The `Player` override has the same gates in both: not flying, requested Y displacement at most zero, mover type `SELF` or `PLAYER`, staying on a ground surface, and above ground. Its per-axis and diagonal probes continue reducing horizontal displacement in `0.05` increments. In 1.19.3 those probes and `isAboveGround` read the field; in 1.19.4 they call `maxUpStep()`. (1.19.3 `Entity.java:549–567`, `Player.java:1050–1107`; 1.19.4 `Entity.java:549–569`, `Player.java:1041–1099`.)

`LocalPlayer` extends `AbstractClientPlayer`, which extends `Player`; `Player` extends `LivingEntity`. `LocalPlayer.isLocalPlayer()` returns true, while the base `Player.isLocalPlayer()` returns false. Neither `Player` nor `LocalPlayer` overrides `getControllingPassenger`; both inherit `Entity`'s implementation, which returns `null`. So the `LivingEntity.maxUpStep()` check on the LocalPlayer itself is false and its step remains the constructor value `0.6F`. Its ordinary inherited living-entity travel uses `MoverType.SELF`; the Player edge-backoff gate accepts both `SELF` and `PLAYER`, and either call on the LocalPlayer sees that same `0.6F` value. (1.19.4 `Player.java:117, 1320–1322`; `AbstractClientPlayer.java:26`; `LocalPlayer.java:86, 344–346`; `Entity.java:2718–2724`; `LivingEntity.java:2155`.)

A LocalPlayer can nevertheless be the controlling passenger *of a mount*: 1.19.4 `AbstractHorse.getControllingPassenger()` returns its first passenger when saddled if that passenger is a `LivingEntity`. The class chain is `AbstractHorse → Animal → AgeableMob → PathfinderMob → Mob → LivingEntity`; `LocalPlayer` is itself a `Player` and `LivingEntity`. In that case the test is true on the mount, and the mount's dynamic step value can be at least `1.0F`. That is mounted/non-player movement, which is excluded here; it does not change the passenger LocalPlayer's own step value.

## Disposition

There is no direct on-foot LocalPlayer collision or edge-backoff consequence under ordinary `SELF` movement, nor under the `PLAYER` mover type accepted by the same Player gate: the LocalPlayer's own controlling-passenger query stays null, so the effective step remains `0.6F`. The new dynamic step rule affects a living mover controlled by a Player. Vehicle and non-player movement, including any indirect mounted-player position effect, remains outside this closure's scope. This is a bounded member/callsite closure, not a full-pair freeze or a runtime-parity claim.

## SHA-256 of cited source files

All paths are relative to the corresponding `<version>/mojmap/` root.

| Version | Source file | SHA-256 |
| --- | --- | --- |
| 1.19.3 | `net/minecraft/world/entity/Entity.java` | `8c35bdef3bf2de7b70ac76ad767ba2c194305e0dda2e03fdf6544983667adc32` |
| 1.19.3 | `net/minecraft/world/entity/LivingEntity.java` | `2b8befdc406176e01672175451dd480b14da0b52bfe11a982d368909c05e73db` |
| 1.19.3 | `net/minecraft/world/entity/player/Player.java` | `02e64e197aec3f4f2a8abc9c5ce4545121ac5bcbbc71592ce478601a0db17203` |
| 1.19.3 | `net/minecraft/client/player/LocalPlayer.java` | `64b670ee323d195b3928fb8ea629c26d2560e75a17379a12c376a7bc686d5479` |
| 1.19.4 | `net/minecraft/world/entity/Entity.java` | `3667fee610cbc5f58012e3a8fb8d6c4849f649fb7fe5300595158112d8b4b58b` |
| 1.19.4 | `net/minecraft/world/entity/LivingEntity.java` | `c8d91af61f87aaa1666de79696d7cd9d4a8212d05f873bdaf28d7bb2926bf165` |
| 1.19.4 | `net/minecraft/world/entity/player/Player.java` | `5e4436afccb361156f8184e7a5cfd91d5b12dcfe8f5937b4ac23dd3edda737a2` |
| 1.19.4 | `net/minecraft/client/player/LocalPlayer.java` | `8e7da18f42d09fbb994f522c2b0e65fcb2bb83cabb21024360299d44d9674c58` |
| 1.19.4 | `net/minecraft/client/player/AbstractClientPlayer.java` | `393b88eb4f41a84528f584311bb7aefc02adbf474c2744f3555acc0663c85dbf` |
| 1.19.4 | `net/minecraft/world/entity/animal/horse/AbstractHorse.java` | `538e06f092b59532e36cbc6724002cad43bcba5d1ae61289225807d339d6cc8e` |
| 1.19.4 | `net/minecraft/world/entity/animal/Animal.java` | `db6b57ead1be5608fd9d8305bfec1baad27dd28292abf3658545dc9c90d2a5d7` |
| 1.19.4 | `net/minecraft/world/entity/AgeableMob.java` | `9843760e048d7ebd310b23087e8d05e3ba674213cc45d80402c8b3a1ab9a3eda` |
| 1.19.4 | `net/minecraft/world/entity/PathfinderMob.java` | `350d947f666231dd1a6f880dd802789b8d4983e0a61e89aea507ed587a800720` |
| 1.19.4 | `net/minecraft/world/entity/Mob.java` | `2ad2a5d2abfe0fc90c239f0a28ec2a8480a0d6142494869be289969751eccde2` |
