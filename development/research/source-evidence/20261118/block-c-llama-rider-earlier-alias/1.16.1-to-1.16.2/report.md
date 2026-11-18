# Llama passenger Y-position — 1.16.1 → 1.16.2

## Result

**No difference found** for an ordinary local player riding a Llama or TraderLlama. The adjacent Mojmap sources preserve the same Y arithmetic, guards, dispatch, and position callback. This only bounds behavior across 1.16.1→1.16.2; no earlier or later patch boundary was checked.

## Reachable client path

| Release | Source | Lines |
|---|---|---:|
| 1.16.1 | net/minecraft/client/multiplayer/ClientLevel.java | 204–218 |
| 1.16.2 | net/minecraft/client/multiplayer/ClientLevel.java | 204–218 |
| 1.16.1 | net/minecraft/client/player/LocalPlayer.java | 862–863 |
| 1.16.2 | net/minecraft/client/player/LocalPlayer.java | 837–838 |
| Both | net/minecraft/world/entity/player/Player.java | 452–464 |
| 1.16.1 | net/minecraft/world/entity/Entity.java | 1557–1562 |
| 1.16.2 | net/minecraft/world/entity/Entity.java | 1577–1582 |

ClientLevel.tickPassenger rejects removed passengers or vehicle mismatches, then ticks a passenger if it is a Player or its chunk is entity-ticking; if in-chunk it calls rideTick(). LocalPlayer.rideTick delegates to Player.rideTick. Player stops riding only when wantsToStopRiding() && isPassenger(); otherwise it delegates to Entity. Entity.rideTick zeroes delta movement, ticks, then calls getVehicle().positionRider(this) if still a passenger.

## Transform, types, and dispatch

| Release | Source | Lines |
|---|---|---:|
| 1.16.1 | net/minecraft/world/entity/animal/horse/Llama.java | 149–162 |
| 1.16.2 | net/minecraft/world/entity/animal/horse/Llama.java | 151–164 |
| Both | net/minecraft/world/entity/player/Player.java | 988–991 |
| 1.16.1 | net/minecraft/world/entity/Entity.java | 321–326, 2712–2714 |
| 1.16.2 | net/minecraft/world/entity/Entity.java | 331–336, 2720–2722 |
| 1.16.1 | net/minecraft/world/entity/animal/horse/AbstractHorse.java | 905–923 |
| 1.16.2 | net/minecraft/world/entity/animal/horse/AbstractHorse.java | 906–924 |
| Both | net/minecraft/world/entity/animal/horse/TraderLlama.java | class line 22; no passenger-position/offset override |

In both releases Llama.positionRider gates on this.hasPassenger(entity), then directly calls entity.setPos(...). It does not call super.positionRider(entity). The Y argument is left-associative:

~~~java
this.getY() + this.getPassengersRidingOffset() + entity.getMyRidingOffset()
~~~

The llama override returns this.getBbHeight() * 0.67. getBbHeight() is float; 0.67 is double, so the multiplication is double after promotion. Player.getMyRidingOffset() returns -0.35 as double. Thus the actual Y is mountY + double(mountBoundingBoxHeight) * 0.67 + (-0.35).

The callback is the virtual Entity.setPos(double, double, double): Player and LocalPlayer do not override setPos in either compared tree. Entity.setPos updates raw position and rebuilds the bounding box. TraderLlama extends Llama and has no override of this positioning or either riding-offset method, so it uses the same code.

AbstractHorse.positionRider calls its superclass and can apply a standAnimO > 0.0F adjustment, but Llama overrides positionRider without a super call, bypassing that path. Llama’s passenger gate and Player’s dismount gate are unchanged. Y is independent of the yaw trig values; X/Z and yaw behavior are outside scope.

## Pair comparison

The Llama rider transform, mount offset, Player rider offset, superclass gates, and position callback are unchanged. No Y-position delta is supported for this adjacent pair.

## Exact source and mapping evidence

Adjacent mojmap.provenance.json files identify one paired exact run: --versions=1.16.1,1.16.2 --mappings=mojmap; Vineflower 1.12.0, Java 25.0.3+9-LTS, Tiny Remapper 0.14.1, Mapping IO 0.9.1, ASM 9.10.1, Gradle 9.7.1, created 2026-10-07. Adjacent mojmap.ready.json records exact version/mapping identity and source/artifact manifest digests. Each recorded source-manifest and artifact-manifest digest matches the adjacent manifest bytes.

Current files match their recorded per-file SHA-256 entries in mojmap.sources.sha256:

| Source relative to mojmap/ | 1.16.1 | 1.16.2 |
|---|---|---|
| net/minecraft/world/entity/Entity.java | 27e3bb01b5d03c09161891e717c16477899768333082e81ebfaac4e525616576 | f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666 |
| net/minecraft/world/entity/animal/horse/AbstractHorse.java | 7f6c50efa62362bf5bf86a085c52292dc8b5274e27989870beb635be0aac51c2 | c4e33cd5a8d52099fbcd2c90c5d0a55a0471ae768e682f3a45e53c4dd7925672 |
| net/minecraft/world/entity/animal/horse/Llama.java | 942fce37f13216906fe48d620076f6514fd93d4a3983eac8a8909170a9385c5c | 18bf21b8d7559e4c9a3195126dc0b30b691e8e869fa1c214956a6395534e5f2d |
| net/minecraft/world/entity/animal/horse/TraderLlama.java | 495d65da1c1903ba769793ee4dbccd6248ef96137c1709af6daa00c563c03dd4 | 2d5d0d9bbb694ec542d6d167f1e98c95a0c1991b66b6ef69c7881b326c54c8f3 |

Player.java was also checked against both manifests; its offset method is at lines 988–991 on both sides.

Pinned input identities in the adjacent artifact manifests: client JAR SHA-256 b4831e7b63b10588ff06ea86322108d916c536446f3aea3ae988b7fe3e1f66ef (1.16.1), 19960f4e3c60f2723f361844d2b4020b8bf2160bd91a1e1efde8ce5aea4ff73d (1.16.2); official client mapping-file SHA-256 6cc374f819bca44d940f8912044b07bdac3e240a02e37117ae1d4ad63c21409b and c95a7046ab43371cc476b8b51978c6f2155c62f4cc5756b05c8ef81e0f11691f; mapped client-mojmap.jar SHA-256 f92d3ad192db28f84522436cc12bb92498ef3b8090f88e906d56d1fb8744490d and 559df3579e366e11884d733f7a06530ee3f174b1eadeb8051f8492f6103b6d90. Source-manifest SHA-256 values: 76fdd121070c2012f48cbdf5cf0c78490116afc94117c30b0858eedcd6ab9754 and 10d312ca29ee48e25f4727adafb9fcfbd741dcebe81b343d3dcffa071952a7a7. Artifact-manifest SHA-256 values: a113f9d59fa4582effb2ebf548c4388a54c9c5654fbf57fae7974dce896d1f98 and 7b8551bdc108a1584039ac22e35b6b75c3584ed85aba24561c7d340f5cbeb108.

The exact client/mapping/mapped-JAR binaries are absent at the recorded campaign cache path in this checkout. Their identities above are retained manifest pins, not rehashed local binaries. The examined decompiled sources are present and match their per-file SHA-256 entries.


Client-side and rider source hashes were checked against their manifests too:

| Source | 1.16.1 SHA-256 | 1.16.2 SHA-256 |
|---|---|---|
| net/minecraft/client/multiplayer/ClientLevel.java | 3bae536422595514893b85bcde09b59459c292c737dd487d69db49d28913da7b | 96c80a10623d41f136fa70391b3920cf7f3ef15547923f7d772b78d356f16d93 |
| net/minecraft/client/player/LocalPlayer.java | ee20523adfdb82f3753fa43312af170f34c4f4aa78570631c61bc8c0820dfb8b | 6011569e766bb1568609147be9aa14e9c08c51948e3d3a60fd066e848f6a8c2b |
| net/minecraft/world/entity/player/Player.java | a849960d47bf00a2cddfb6636c78c518532a11967ba08829f19c5ebced08681e | d2e26589bdb6a20dc914266db06aa48f50811efc792d6e63b3008c9199914960 |

