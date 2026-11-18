# Independent audit: Llama rider Y position, 1.16.1 → 1.16.2

## Verdict

**ACCEPT the bounded no-difference claim.** For an ordinary local player riding an adult default `Llama` or `TraderLlama`, the direct rider-position callback computes the same Y in both exact Mojmap source trees. The gate, dispatch, arithmetic expression and inherited methods checked below are unchanged. This verdict covers only 1.16.1→1.16.2; it establishes no earlier or later patch boundary.

The frozen input is `c-llama-1161-1162-position-intermediate/report.md`, SHA-256 `ddb7ca7ed4c95b058f5dd44e6405f9a5b1e564c219c60ff888c43c9652012c20` (verified against exact file bytes before review).

## Source identity and local verification

The canonical local trees are `decompiled_minecraft/1.16.1/mojmap/` and `decompiled_minecraft/1.16.2/mojmap/`. Each side has a distinct local `mojmap.ready.json`, `mojmap.provenance.json`, and `mojmap.sources.sha256`.

| Release | Exact run / mapping | Source-manifest SHA-256 | Ready metadata pins |
|---|---|---|---|
| 1.16.1 | Pair run `--versions=1.16.1,1.16.2 --mappings=mojmap`; Vineflower 1.12.0, Java 25.0.3+9-LTS | `76fdd121070c2012f48cbdf5cf0c78490116afc94117c30b0858eedcd6ab9754` | client `b4831e7b63b10588ff06ea86322108d916c536446f3aea3ae988b7fe3e1f66ef`; artifact-manifest pin `a113f9d59fa4582effb2ebf548c4388a54c9c5654fbf57fae7974dce896d1f98` |
| 1.16.2 | Same pair run/toolchain | `10d312ca29ee48e25f4727adafb9fcfbd741dcebe81b343d3dcffa071952a7a7` | client `19960f4e3c60f2723f361844d2b4020b8bf2160bd91a1e1efde8ce5aea4ff73d`; artifact-manifest pin `7b8551bdc108a1584039ac22e35b6b75c3584ed85aba24561c7d340f5cbeb108` |

Both local source-manifest byte hashes match their `ready.json` digests. The exact local metadata-file hashes are:

| Release | `mojmap.ready.json` SHA-256 | `mojmap.provenance.json` SHA-256 |
|---|---|---|
| 1.16.1 | `c19d707f7d6622733b5189599ff77aa7999cc11a3d900f29d150f9fab203ffcb` | `a6ff065188a400ec891980cdf2e6c0c238bf5e05168a2f69b29c6cecc2214b98` |
| 1.16.2 | `e5f69bab646b6143b0660a97fade59595ff2e924d36567b9cd285c8babd31214` | `a9b166406064e877751542fe7df27acc32be3843f6fdc2280dac32ce0b54207c` |

Each of the seven cited Java files was independently hashed and matches its entry in the corresponding source manifest:

| Relative source path | 1.16.1 SHA-256 | 1.16.2 SHA-256 |
|---|---|---|
| `net/minecraft/client/multiplayer/ClientLevel.java` | `3bae536422595514893b85bcde09b59459c292c737dd487d69db49d28913da7b` | `96c80a10623d41f136fa70391b3920cf7f3ef15547923f7d772b78d356f16d93` |
| `net/minecraft/client/player/LocalPlayer.java` | `ee20523adfdb82f3753fa43312af170f34c4f4aa78570631c61bc8c0820dfb8b` | `6011569e766bb1568609147be9aa14e9c08c51948e3d3a60fd066e848f6a8c2b` |
| `net/minecraft/world/entity/player/Player.java` | `a849960d47bf00a2cddfb6636c78c518532a11967ba08829f19c5ebced08681e` | `d2e26589bdb6a20dc914266db06aa48f50811efc792d6e63b3008c9199914960` |
| `net/minecraft/world/entity/Entity.java` | `27e3bb01b5d03c09161891e717c16477899768333082e81ebfaac4e525616576` | `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666` |
| `net/minecraft/world/entity/animal/horse/AbstractHorse.java` | `7f6c50efa62362bf5bf86a085c52292dc8b5274e27989870beb635be0aac51c2` | `c4e33cd5a8d52099fbcd2c90c5d0a55a0471ae768e682f3a45e53c4dd7925672` |
| `net/minecraft/world/entity/animal/horse/Llama.java` | `942fce37f13216906fe48d620076f6514fd93d4a3983eac8a8909170a9385c5c` | `18bf21b8d7559e4c9a3195126dc0b30b691e8e869fa1c214956a6395534e5f2d` |
| `net/minecraft/world/entity/animal/horse/TraderLlama.java` | `495d65da1c1903ba769793ee4dbccd6248ef96137c1709af6daa00c563c03dd4` | `2d5d0d9bbb694ec542d6d167f1e98c95a0c1991b66b6ef69c7881b326c54c8f3` |

The ready/provenance records preserve raw client, mapping, mapped-archive, and artifact-manifest digests, but the recorded campaign artifact-cache directory (`build/movement-campaign-2026-10-07/artifacts`) and version-specific directories under `.minecraft-decompile-cache/` are absent in this checkout. The local trees do not contain `artifacts.sha256`. Thus raw binaries and artifact-manifest bytes were not rehashed in this audit; their digests above are metadata pins. This does not prevent checking the present source bytes against the present, locally hashed source manifests.

## Independent trace and comparison

All references below are relative to each release's `mojmap/` source root.

1. `net/minecraft/client/multiplayer/ClientLevel.java:204–218` (both): `tickPassenger` stops removed/mismatched passengers; otherwise players pass the entity-ticking-chunk gate. If `inChunk`, it increments the passenger tick count and invokes virtual `rideTick()`. The gate and dispatch are the same.
2. `net/minecraft/client/player/LocalPlayer.java:861–864` (1.16.1) and `:836–839` (1.16.2): `LocalPlayer.rideTick()` delegates to `Player.rideTick()`; it adds no position write before or after that call in the cited body.
3. `net/minecraft/world/entity/player/Player.java:452–464` (both): `Player.rideTick()` stops riding only when `wantsToStopRiding() && isPassenger()`. Otherwise it calls `super.rideTick()` and records riding statistics. The guard and branch order are unchanged.
4. `net/minecraft/world/entity/Entity.java:1557–1562` (1.16.1) / `:1577–1582` (1.16.2): `Entity.rideTick()` zeroes passenger delta movement, ticks it, then, if it remains a passenger, invokes `getVehicle().positionRider(this)`.
5. `net/minecraft/world/entity/animal/horse/Llama.java:149–162` (1.16.1) / `:151–164` (1.16.2): `Llama.positionRider` gates on `this.hasPassenger(entity)` and directly calls `entity.setPos(...)`; it does not call `super.positionRider`. Its Y argument is exactly the left-associated expression:

   ```java
   this.getY() + this.getPassengersRidingOffset() + entity.getMyRidingOffset()
   ```

   `getPassengersRidingOffset()` returns `this.getBbHeight() * 0.67`. `getBbHeight()` is `float` (`Entity.java:2712–2714` / `:2720–2722`), while `0.67` is a `double` literal. Java therefore promotes the float height to double before multiplication; both subsequent additions and the target parameter are double. For a `Player`, `Player.getMyRidingOffset()` is the unchanged double `-0.35` (`Player.java:988–991` in both). The Y is therefore `mountY + (double(mountBbHeight) * 0.67) + (-0.35)`, with no float cast or intermediate float rounding.
6. `net/minecraft/world/entity/Entity.java:321–326` (1.16.1) / `:331–334` (1.16.2): the callback is virtual `Entity.setPos(double,double,double)`. Neither `Player` nor `LocalPlayer` overrides `setPos`; the inherited method writes raw position and rebuilds the bounding box. These methods retain their respective native body and callback behavior in the pair.
7. `net/minecraft/world/entity/animal/horse/AbstractHorse.java:905–923` / `:906–924`: `AbstractHorse.positionRider` can add a stand-animation adjustment, but the Llama override bypasses it because it never calls `super`. `TraderLlama.java:22–23` in both declares `TraderLlama extends Llama`; it has no `positionRider` or `getPassengersRidingOffset` override. It inherits the same Llama transform. The Player offset and callback are inherited unchanged as well.

The Llama gate, Y expression, offset methods, caller order, player dismount gate and inherited position callback are unchanged. No Y-position delta is supported for this pair. X/Z placement and yaw trigonometry are outside this Y-only verdict.