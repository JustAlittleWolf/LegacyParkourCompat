# Independent source audit: 1.16.3 → 1.16.4 swim-pitch vertical adjustment

## Frozen audit scope and provenance

Scope: ordinary active local-player execution of `Player.travel(Vec3)` when swimming and not a passenger; its look-Y calculation, sine lookup numeric behavior, vertical coefficient and guards/order; then the actual inherited `LivingEntity.travel` water path and `Entity.move(MoverType.SELF, ...)`. No wiki, implementation, Git, or other-lane material was consulted. This is an exact-source comparison, not runtime verification.

Canonical source root: `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/<version>/mojmap/` (read-only). The hidden `.decompile-provenance.json` inputs were checked against the local pinned `client.jar`, `client_mappings.txt`, and `version.json` files and their `.pin.json` SHA-256 values.

| Exact release | Hidden provenance manifest SHA-256 | Local client SHA-256 | Local Mojmap SHA-256 | Local version metadata SHA-256 |
|---|---|---|---|---|
| 1.16.3 | `513271804EAD6757345D305717E7E0BFC1641434C35A3B254F287BD8FD281498` | `DA85197BABD989A7DE318774CF5D97BCEF95E89A70C9FA9A4C8180463E9589FA` | `C41C58FAAF3A3B651D8883B9B1DF355CB6AD59C4D6296B2E1FFC3DFE2BF9BE3E` | `193C5226095CE9E8A317F1E35DDBE4D976F176F6DF7AC649ECFA669E17160EAD` |
| 1.16.4 | `1E04555C915527A398A2C7150679FB24D056F54BD680261E3C1DD55DA821147C` | `D6740464EE2BDF37D285C5D368D2E16497492427FB5783E2DB3482BBF0079ED0` | `93F188A0038FEC8C2402D78E7ACCA983680978D6A9237C1FAB80F22CADDB00FE` | `667FFF4CA7FD0F56AB78B757224515E8E64375868E8D1CE6040091D7B63A0CB5` |

Both manifests identify Mojmap/decompile pipeline `lpc-decompile-v1` and the same fixed options, with Java `Eclipse Adoptium:25.0.3+9-LTS:Windows 11:amd64`; the corresponding local files' computed SHA-256 values equal both manifest inputs and `.pin.json` pins. The exact manifest input tuples are:

- 1.16.3: client URL object `1321521b2caf934f7fc9665aab7e059a7b2bfcdf` → `da85197babd989a7de318774cf5d97bcef95e89a70c9fa9a4c8180463e9589fa`; mapping object `faac5028fbca3859db970cc4ca041aeec55f6d9d` → `c41c58faaf3a3b651d8883b9b1df355cb6ad59c4d6296b2e1ffc3dfe2bf9be3e`; version metadata package `6485dd131ef68c968041a9f6fd73094b027e42e1` → `193c5226095ce9e8a317f1e35ddbe4d976f176f6df7ac649ecfa669e17160ead`.
- 1.16.4: client URL object `1952d94a0784e7abda230aae6a1e8fc0522dba99` → `d6740464ee2bdf37d285c5d368d2e16497492427fb5783e2db3482bbf0079ed0`; mapping object `0837de813d1a6b67e23da3c520a84e872c8d2f0e` → `93f188a0038fec8c2402d78e7acca983680978d6a9237c1fab80f22caddb00fe`; version metadata package `596ad61fda7612d9edf8881cf81869276bdb7f82` → `667fff4ca7fd0f56ab78b757224515e8e64375868e8d1ce6040091d7b63a0cb5`.

## Pinned source files and trace (recorded before paired-report access)

Manifest source hashes were independently checked against the four canonical source files on each side. The source bytes are identical across this pair:

| Source path (both releases) | SHA-256 |
|---|---|
| `net/minecraft/world/entity/player/Player.java` | `d2e26589bdb6a20dc914266db06aa48f50811efc792d6e63b3008c9199914960` |
| `net/minecraft/world/entity/Entity.java` | `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666` |
| `net/minecraft/world/entity/LivingEntity.java` | `b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88` |
| `net/minecraft/util/Mth.java` | `0e4424ffa12923314c309ae13e012768271dfcace4b5a84727f74e8170afe290` |

Independent trace in both 1.16.3 and 1.16.4 Mojmap:

1. `Player.travel(Vec3)` (`Player.java:1387–1417`) first saves position, then applies the swim-pitch vertical adjustment only under `isSwimming() && !isPassenger()`. `Player.isSwimming()` (`Player.java:1782–1784`) additionally requires `!abilities.flying`, `!isSpectator()`, and `super.isSwimming()`. It obtains `double g = getLookAngle().y`; chooses `double h = g < -0.2 ? 0.085 : 0.06`; and only adds `(g - currentDeltaY) * h` to Y when `g <= 0.0`, `jumping`, or the fluid at `BlockPos(getX(), getY() + 1.0 - 0.1, getZ())` is nonempty. The short-circuit order means nonpositive look-Y bypasses later checks and jumping bypasses the block/fluid sample. Horizontal delta is preserved by adding `(0.0, ..., 0.0)`. The coefficient selection precedes the conditional, and both precede the flying branch and `super.travel(vec3)`. Because swimming excludes flying, an eligible swimmer follows the ordinary branch to `super.travel(vec3)`; a flying non-passenger never enters this swim adjustment and separately saves/overwrites its vertical velocity around the superclass call.
2. `Entity.getLookAngle()` (`Entity.java:1700-1701`) calls `calculateViewVector(xRot, yRot)`. In `calculateViewVector` (`Entity.java:1243-1251`), `h = xRot * (float)(PI / 180.0)` and `i = -yRot * (float)(PI / 180.0)` are floats. It calculates `j = Mth.cos(i)`, `k = Mth.sin(i)`, `l = Mth.cos(h)`, and `m = Mth.sin(h)`, all floats, and constructs `Vec3(k * l, -m, j * l)`. The unary-negated float Y is widened to double by the `Vec3(double,double,double)` arguments; `g` then receives that double. There is no double-precision trigonometric replacement in this chain.
3. `Mth` initializes one `float[65536]` sine table using `(float)Math.sin(ix * PI * 2.0 / 65536.0)`. `sin(float f)` returns `SIN[(int)(f * 10430.378F) & 65535]`; `cos(float f)` returns `SIN[(int)(f * 10430.378F + 16384.0F) & 65535]`. Multiplication by the float constants, truncating float-to-int cast, and 16-bit mask are preserved. The float table result remains float until vector construction.
4. `LivingEntity.travel(Vec3)` (`LivingEntity.java:1914+`) proceeds when `isEffectiveAi() || isControlledByLocalInstance()`. For the active local player whose fluid-state gates select water—`isInWater() && isAffectedByFluids() && !canStandOnFluid(fluidState.getType())`—it calls `moveRelative(g, vec3)` and then the actual inherited `move(MoverType.SELF, getDeltaMovement())` at `LivingEntity.java:1947`. It next scales velocity by `(f, 0.8F, f)`, applies fluid-falling adjustment, and can set Y to `0.3F` after the horizontal-collision/free-space check. The same class calls `move(MoverType.SELF, ...)` in lava (`1962`), fall-flying (`2008`), and ordinary travel (`2065`). `Player.travel` dispatches through this superclass after the pre-travel pitch correction.
5. `Entity.move(MoverType, Vec3)` (`Entity.java:485`) is the inherited target reached by these branches. The normal physics route applies edge adjustment, collision resolution, bounding-box/location update, collision flags and fall response; the `noPhysics` route instead moves the box/location directly. In either release the called method and its implementation are byte-identical by the pinned `Entity.java` hash.

Pre-report assessment: **no source difference found for the assigned operation or this traced inherited route** between 1.16.3 and 1.16.4. The operation, gates, constants, statement order, float/table casts/indexing, widening and `move` call are byte-identical in the corresponding source files. This is bounded to the named active-player swim-pitch operation and its `LivingEntity.travel`/`Entity.move` route; it does not claim whole-pair coverage.

## Paired report reconciliation

The source-side report was intentionally not read until this local source identity, hashes, and trace were frozen. The frozen pre-report verdict revision SHA-256 was `9203260AB8005F262D5FB9259E16C16D81F609857BEE57D29E8E206D57A6E774`; its hash pins this independent trace as it stood before report access. The paired source report SHA-256 is `C263F870AFE10FC2A394351A7FDC2CAD52D153E94F7866AEF87489244566E1`. Its bounded no-difference conclusion matches this independent trace. Its added eligibility and inherited-call details were then checked directly in the canonical sources (`Player.java:1782–1784`; `LivingEntity.java:1947, 1962, 2008, 2065`) and match both releases.

## Signed verdict

Independent exact-source reviewer: `/root/c_swim_pitch_vertical_1163_1164_audit`. Scope: 1.16.3→1.16.4 Mojmap ordinary active-player swimming pitch-to-Y adjustment, swimming eligibility, exact current look-Y float/table/index/widening path, coefficient and short-circuit gates/order, then `LivingEntity.travel` and actual inherited `Entity.move` paths. The paired source report at SHA-256 `C263F870AFE10FC2A394351A7FDC2CAD52D153E94F7866AEF87489244566E1` agrees with the frozen independent result. Verdict: **confirmed no source difference in this bounded operation and dependency trace; not full-pair coverage**.
