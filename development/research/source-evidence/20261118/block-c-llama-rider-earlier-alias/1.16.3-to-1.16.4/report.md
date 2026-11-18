# C — Llama rider Y placement, 1.16.3 → 1.16.4

## Scoped conclusion

No change to the client-visible Y placement of an ordinary adult player riding an ordinary Llama or Trader Llama is present between these exact releases. Both versions run the same Llama override and inherited player offset. For default adult dimensions, the passenger Y delta at vehicle Y `y` is evaluated left-to-right as `y + (1.87F * 0.67) + (-0.35)`. At `y = 0`, this is approximately `0.902900003194809`; actual placement uses the vehicle's double Y and the same two ordered double additions.

This covers the rider-position callback and its direct height/offset dependencies only. It does not establish complete 1.16.3–1.16.4 movement-pair coverage or runtime parity.

## Exact source path and gates

In both releases, `LocalPlayer.rideTick()` delegates to `Player.rideTick()`. Unless `Player.wantsToStopRiding()` is true while the player is a passenger (which stops riding and skips the superclass call), `Player.rideTick()` calls `Entity.rideTick()`. That method zeroes the rider's delta movement, ticks the rider, then calls `getVehicle().positionRider(this)` only if the rider remains a passenger. `LocalPlayer` adds no riding-position override after `super.rideTick()`.

`Llama.positionRider(Entity)` has the `hasPassenger(entity)` guard and then calls `entity.setPos(...)`. Its Y argument is `this.getY() + this.getPassengersRidingOffset() + entity.getMyRidingOffset()`. The X/Z arguments use `0.3F` and body yaw, but are outside this Y-only result. The Llama offset override is exactly `this.getBbHeight() * 0.67`; `getBbHeight()` returns float, so multiplication by the unsuffixed `0.67` occurs in double precision and returns double. `Player.getMyRidingOffset()` returns the unsuffixed double literal `-0.35`; the additions therefore use double arithmetic in source order. Entity's default rider offset is `0.0D`, but it is not used for a `Player`.

Both `EntityType.LLAMA` and `EntityType.TRADER_LLAMA` register dimensions with `.sized(0.9F, 1.87F)`. The ordinary adult default uses height `1.87F`. `TraderLlama extends Llama` and does not override `positionRider` or `getPassengersRidingOffset`, so it inherits the same path. Llama's color/variant field is not read by the height override. This result is bounded to one ordinary player passenger on an adult, default-size Llama/Trader Llama; other passenger classes supply their own riding offset, babies/changed dimensions are outside this numeric case, and vehicle movement is excluded.

## Source references (Mojmap)

Paths below are relative to `decompiled_minecraft/<release>/mojmap/`; line ranges are inclusive.

- Both releases, `net/minecraft/client/player/LocalPlayer.java:837–845`: local-player riding callback delegates to `Player.rideTick()`.
- Both, `net/minecraft/world/entity/player/Player.java:452–465`: dismount gate and superclass path; `:989–991`: player offset `-0.35`.
- Both, `net/minecraft/world/entity/Entity.java:1577–1605`: ride-tick callback, passenger guard, generic callback and default offsets.
- Both, `net/minecraft/world/entity/animal/horse/Llama.java:152–164`: passenger gate, `setPos` Y expression and `0.67` height multiplier.
- Both, `net/minecraft/world/entity/animal/horse/TraderLlama.java:23–39`: subclass relationship and no placement override in the class.
- Both, `net/minecraft/world/entity/EntityType.java:277–279` and `:425–427`: ordinary and trader Llama dimensions (`0.9F`, `1.87F`).

The six referenced source files have matching contents and the same declared/actual SHA-256 on both sides:

| Source | SHA-256 |
| --- | --- |
| `net/minecraft/client/player/LocalPlayer.java` | `6011569e766bb1568609147be9aa14e9c08c51948e3d3a60fd066e848f6a8c2b` |
| `net/minecraft/world/entity/player/Player.java` | `d2e26589bdb6a20dc914266db06aa48f50811efc792d6e63b3008c9199914960` |
| `net/minecraft/world/entity/Entity.java` | `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666` |
| `net/minecraft/world/entity/animal/horse/Llama.java` | `18bf21b8d7559e4c9a3195126dc0b30b691e8e869fa1c214956a6395534e5f2d` |
| `net/minecraft/world/entity/animal/horse/TraderLlama.java` | `2d5d0d9bbb694ec542d6d167f1e98c95a0c1991b66b6ef69c7881b326c54c8f3` |
| `net/minecraft/world/entity/EntityType.java` | `8b4c9ea61b2417dafc8bdc52a18aacb556e3ec9ce2a94fe69b307b9db2c009cd` |

## Exact-source readiness and identities

Canonical exact directories `decompiled_minecraft/1.16.3/mojmap/` and `decompiled_minecraft/1.16.4/mojmap/` both exist. Their `.decompile-provenance.json` manifests declare exact Minecraft client and official Mojang client-mapping inputs, the same decompiler pipeline and Java 25/Windows 11 runtime. Every listed source hash above was recomputed from the corresponding file on each release and matched its manifest.

| Release | Mojang client SHA-256 | Mojang client mapping (`client.txt`) SHA-256 | Provenance manifest SHA-256 |
| --- | --- | --- | --- |
| 1.16.3 | `da85197babd989a7de318774cf5d97bcef95e89a70c9fa9a4c8180463e9589fa` | `c41c58faaf3a3b651d8883b9b1df355cb6ad59c4d6296b2e1ffc3dfe2bf9be3e` | `513271804ead6757345d305717e7e0bfc1641434c35a3b254f287bd8fd281498` |
| 1.16.4 | `d6740464ee2bdf37d285c5d368d2e16497492427fb5783e2db3482bbf0079ed0` | `93f188a0038fec8c2402d78e7acca983680978d6a9237c1fab80f22caddb00fe` | `1e04555c915527a398a2c7150679fb24d056f54bd680261e3c1dd55da821147c` |

Readiness: exact adjacent releases and Mojmap trees are available and internally hash-consistent for this bounded path; no alias substitution or generation was used.

