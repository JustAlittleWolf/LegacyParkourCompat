# Llama rider placement: 1.13.1 → 1.13.2

## Result and scope

No source difference was found for the bounded operation: an ordinary adult `LlamaEntity` placing its sole direct `PlayerEntity` passenger during the client ride tick. The checked callback, dimensions, mount-height override, player ride-height override, and ride-tick dispatch are the same in both releases. This is a no-difference slice, not complete 1.13.1–1.13.2 movement-pair coverage; no permanent difference finding is warranted from this slice.

The result is limited to player placement by a llama. It makes no claim about llama/non-player movement, vehicle physics generally, or a `TraderLlamaEntity` class (none is present in these mapped source trees).

## Exact behavior in both releases

`net/minecraft/entity/living/mob/passive/animal/LlamaEntity.java:62–65,146–158` (`1.13.1` and `1.13.2`): the constructor sets size to `(0.9F, 1.87F)`. `updateRiderPositon(Entity rider)` first checks `this.hasPassenger(rider)`; when true it computes `f = cos(bodyYaw * (float)(PI / 180.0))`, `g = sin(bodyYaw * (float)(PI / 180.0))`, and an unused local `h = 0.3F`, then calls:

```java
rider.setPosition(
    this.x + 0.3F * g,
    this.y + this.getMountHeight() + rider.getRideHeight(),
    this.z - 0.3F * f
);
```

Thus the lateral terms are `+ 0.3F * sin(...)` for X and `- 0.3F * cos(...)` for Z, in that order. The local `h` does not feed either expression. Llama `getMountHeight()` returns `this.height * 0.67`; `height` is float, while `0.67` is an unsuffixed double literal, so the multiplication is performed as double after promoting the float height. The expression then adds the player ride height in double arithmetic. For this ordinary adult llama and player, the source path is `llama.y + (double)1.87F * 0.67D + (-0.35D)`; the source does not store a precomputed offset.

`net/minecraft/entity/living/player/PlayerEntity.java:979–982` returns `-0.35` from `getRideHeight()` in both releases. The local-player inheritance chain is `LocalClientPlayerEntity extends ClientPlayerEntity extends PlayerEntity extends LivingEntity extends Entity`; no intermediate class overrides that method. The Llama override does not call its superclass positioning method, so the above Llama-specific expression is used.

The client callback path is also unchanged: `World.tickEntity` (`World.java:1139–1160`) dispatches `entity.rideTick()` when the entity is riding; `LocalClientPlayerEntity.rideTick()` (`LocalClientPlayerEntity.java:844–852`) delegates to `super.rideTick()`, and `PlayerEntity.rideTick()` (`PlayerEntity.java:425–445`) delegates to `Entity.rideTick()`. `Entity.rideTick()` (`Entity.java:1575–1587`) calls `getMount().updateRiderPositon(this)` if the player remains riding. Dynamic dispatch reaches `LlamaEntity.updateRiderPositon`, whose `hasPassenger(rider)` guard requires this player to be a direct passenger. The same direct-passenger guard is inside the Llama override itself.

## Exact sources and identities

Both sides are canonical local `ornithe-feather` decompilations, read-only. Mapping namespace is Ornithe Feather; the mapping artifacts are the release-specific `net.ornithemc:feather-gen2` build 2 artifacts. The relevant source-file hashes below were checked against each tree's source manifest; the Llama, Entity, LocalClientPlayer, ClientPlayer, and LivingEntity files are byte-identical across the pair. `PlayerEntity.java` differs elsewhere, but its cited rider-height and ride-tick slices match exactly.

| Release | Mapping artifact identity | Client SHA-256 | Source manifest SHA-256 | Key source SHA-256 |
|---|---|---|---|---|
| 1.13.1 | `feather-gen2:1.13.1+build.2`; merged V2 `6737db17e8cc724a018aad93d7fe5051a50e77136848d78d79b62310846fa65d`; Tiny `236423be826fb19d08edb3bf7cad9100eab72c5ddbbed8337afcf6ae304c4d14` | `f0b4cf6d1a87ae150f87a535651138ca7b82bdad8853f76e7ddbff1dd610ac87` | `21770b01ff3bf443db2e9e1b6df7db25d2fd6b7a72a86228259269c932d3abba` | `LlamaEntity.java` `5be437bcd5600c4dbcd7ef5e5224ac31baa69efbd69b11152b0bc04081922089`; `Entity.java` `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269` |
| 1.13.2 | `feather-gen2:1.13.2+build.2`; merged V2 `317384d4faccc2939c3b14252993d31745eb42aad59f2f0ea78893cbe4e7283b`; Tiny `b3fd787448aed2c6e115d965d9edbb437ce47794ba63c7883e9014fd41262f71` | `3410887ba652f25792c7675bfaf9140e73b60e93cfbf113a803f8a98cb05c0f9` | `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211` | `LlamaEntity.java` `5be437bcd5600c4dbcd7ef5e5224ac31baa69efbd69b11152b0bc04081922089`; `Entity.java` `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269` |

The player source hashes are `4c1b14b4727116071b1ba9e3eeae84b8077f3d767e10e49b6a68a2556da32272` (1.13.1) and `4ed22f6c5a3c55d67eed782070ac722201df4d624adbc90779f1fd29c2876633` (1.13.2); in both, the cited `getRideHeight()` body is exactly `return -0.35;`. `World.java` hashes are `119ac6f61b93cf352b2026934e9f95877f55dc2f582b45d75c2bdae3652532b6` and `0023de30da608db73c6c6188334d2bb19039d86fe4d6f5e4ad79d732a5d2cd1a`; the cited dispatch slice matches. `LocalClientPlayerEntity.java` is `2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29b29d8d9bf42bf` on both sides.

The recorded tool versions are Java `25.0.3+9-LTS`, Gradle `9.7.1`, Vineflower `1.12.0`, ASM `9.10.1`, Mapping IO `0.9.1`, Gson `2.14.0`, and TinyRemapper `0.14.1`. The available provenance records identify tool versions but do not provide tool-binary SHA-256 values. For 1.13.2, the ready marker and verification record show 2,711 source files verified with zero source-file differences; verification notes one regenerated intermediate mapped-jar hash difference, while the canonical source tree and raw source inputs match. No sources were generated or modified for this investigation.
