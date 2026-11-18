# C blind source report: local yaw precision, 1.13 → 1.13.1

## Exact source identity

- Release pair: Minecraft 1.13 and 1.13.1.
- Namespace: `ornithe-feather`.
- Mapping artifacts pinned by the exact preparation run `prep-1.13-1.13.1-feather-2026-10-09-r1`: `net.ornithemc:feather-gen2:1.13+build.2` (SHA-256 `a7a61604c7b365db1f4250c4c0079172c3beff37f4e16f7a53a13d77f3976897`) and `net.ornithemc:feather-gen2:1.13.1+build.2` (SHA-256 `6737db17e8cc724a018aad93d7fe5051a50e77136848d78d79b62310846fa65d`).
- The pinned raw client SHA-256 values are `d807a5ed9428b6c16b64888fd2b7fa76dcf5c55d06f96f7c0c47762c3fc3e26e` (1.13) and `f0b4cf6d1a87ae150f87a535651138ca7b82bdad8853f76e7ddbff1dd610ac87` (1.13.1). Release metadata SHA-256 values are `a4db43540601793fe6b4d5c4823f4c55899b84912e48a7a73c00e798775ea2ba` and `0263d250becfe899c746ee471112823c681a4262a18829192edd0839191c4e1a` respectively.
- Source manifest SHA-256: 1.13 `0509c2614b0bb0e616e2ebc6382f8eb5d031b5d86b834a4a6ed7fd96b6d68f1a`; 1.13.1 `21770b01ff3bf443db2e9e1b6df7db25d2fd6b7a72a86228259269c932d3abba`. The per-file hashes below were recomputed locally and match their respective manifests. Provenance JSON SHA-256: 1.13 `34652aeef6914b5cbff6619ae60a855aa676b2ae1f607eccdaa623ad09dc5e8d`; 1.13.1 `c28ed59f2c507b6133f7677742d316d38e9993eaf1fbb6c2054bd68d1085e904`.

## Yaw update and numeric behavior

In both releases, `net/minecraft/client/MouseHandler.java`, `turnPlayer()` (line 181), calls `minecraft.player.updateLocalPlayerCamera(j, k * n)` (line 211) when the mouse is grabbed and fullscreen; otherwise it clears accumulated deltas. The `MouseHandler.java` whole-file SHA-256 is identical in both releases: `b045dc9eaf5b5ab1d9f13dd795465a106f52571dda9e5c837f5c2bbbdfddf4ce`.

In both releases, `net/minecraft/entity/Entity.java`, `updateLocalPlayerCamera(double cursorDeltaX, double cursorDeltaY)` (line 318), computes `double e = cursorDeltaX * 0.15`, then writes `yaw = (float)(yaw + e)` and `lastYaw = (float)(lastYaw + e)`. Since `yaw` and `lastYaw` are floats while `e` is a double, each addition is performed as double and narrowed once to float. The method does not wrap or clamp yaw and has no finite-value guard on this yaw path. The current and previous yaw receive the same double delta independently. The corresponding extracted method body has SHA-256 `a08edd945453b3f76bc3de93b63fad3bcc1b4913603ba8f602f51fdadd37895a` in both releases.

Finite-input witness (method-level): with current and previous yaw initialized to `0.0F`, `cursorDeltaX = 1.0e40` is finite, and `e = 1.5e39` remains finite as a double. Narrowing either sum to float produces positive infinity. No finite guard in the described `MouseHandler.turnPlayer` → `updateLocalPlayerCamera` path rejects it. This establishes the float-overflow behavior of the source procedure; it is not a claim that a normal physical mouse event produces that magnitude.

## First ordinary dry ground/air stored-yaw consumer

`LocalClientPlayerEntity` extends `ClientPlayerEntity`, which extends `PlayerEntity` in both releases (`LocalClientPlayerEntity.java:89` / `87`, `ClientPlayerEntity.java:22`). `PlayerEntity.moveRelative` delegates to `super.moveRelative` in its ordinary non-flying branch (`PlayerEntity.java:1435–1458` in 1.13; `1442–1465` in 1.13.1). In inherited `LivingEntity.moveRelative`, the locally controlled path enters the non-fall-flying branch when dry (not in water or lava); it selects `getSpeed() * u` while grounded or `speedInAir` while airborne, then calls `updateVelocity(forwards, sideways, upwards, v)` (`LivingEntity.java:1459–1534` in 1.13; `1478–1553` in 1.13.1). This excludes the swimming/vehicle and elytra branches.

The first stored-yaw acceleration operation is `Entity.updateVelocity` (`Entity.java:1056` in 1.13; `1057` in 1.13.1). After its existing control-vector squared-magnitude dead zone (`f < 1.0E-4F`), it evaluates `MathHelper.sin(yaw * (float)(Math.PI / 180.0))` and `MathHelper.cos(...)` as floats and rotates the scaled controls into X/Z velocity. There is no yaw finite-value check at this consumer. Both `updateVelocity` bodies have matching extracted-body SHA-256 `ce5c24df63c34a398c66f03759c8cf9b403cc2d9f50b28baf204037d75e48f3f`.

`MathHelper.sin/cos` use the 65,536-entry table with an integer cast and `& 65535` (`MathHelper.java:24–29` in both releases). For the witness infinity, Java's float-to-int conversion saturates to `Integer.MAX_VALUE`; masking selects index 65535 for both lookups. Thus the path consumes the stored infinite yaw through this fixed table index rather than rejecting it. This describes source-level arithmetic only; no game/runtime was launched.

## Pair comparison

No yaw-only difference was found in the bounded operation path. `updateLocalPlayerCamera`, `Entity.updateVelocity`, `LivingEntity.moveRelative`, and `PlayerEntity.moveRelative` have matching extracted method-body SHA-256 values across the pair: respectively `a08edd945453b3f76bc3de93b63fad3bcc1b4913603ba8f602f51fdadd37895a`, `ce5c24df63c34a398c66f03759c8cf9b403cc2d9f50b28baf204037d75e48f3f`, `78de2dc443a97a6d6d08a3e430b0a5dbcb967ad73a5dddd14d9f7169cec56e82`, and `e2bec35ade0d77ec03898370e7466fc6f7504eb9629e2e9590c85804742093ae`. These hashes are SHA-256 of the extracted method text joined with LF, not whole-file hashes.

This report closes only the assigned local-turn numeric path and its first dry ground/air relative-acceleration consumer for this endpoint pair. It does not establish intermediate patch boundaries, whole-pair movement coverage, source acceptance, or runtime parity. Pitch-only behavior was intentionally left out.

## Whole-file source hashes cited above

| Source file | 1.13 SHA-256 | 1.13.1 SHA-256 |
|---|---|---|
| `net/minecraft/client/MouseHandler.java` | `b045dc9eaf5b5ab1d9f13dd795465a106f52571dda9e5c837f5c2bbbdfddf4ce` | `b045dc9eaf5b5ab1d9f13dd795465a106f52571dda9e5c837f5c2bbbdfddf4ce` |
| `net/minecraft/entity/Entity.java` | `09514488d9055449c47e2e4bcd23b5773203ca634b7ecba2323a99d0ae016642` | `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269` |
| `net/minecraft/entity/living/LivingEntity.java` | `c38e95c2c13e088b03d77fc2c6b98d4edd7e6ca5aef12b187c8e3f3ea782e864` | `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c` |
| `net/minecraft/entity/living/player/PlayerEntity.java` | `4a57ce65405436aeb211d7a04a9a9f6ae1a661d033fd3ba2424909bfb42b8acd` | `4c1b14b4727116071b1ba9e3eeae84b8077f3d767e10e49b6a68a2556da32272` |
| `net/minecraft/client/entity/living/player/ClientPlayerEntity.java` | `99b57acea68323125d859313ef5dd2c55d0703c08c5cce475c0227da0d12d1ee` | `99b57acea68323125d859313ef5dd2c55d0703c08c5cce475c0227da0d12d1ee` |
| `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `a188e1bc87831dd9da1ec1366a49acc4104c18e6979129097d02d5d3617e6a08` | `2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29b29d8d9bf42bf` |
| `net/minecraft/util/math/MathHelper.java` | `ad7615e7acf9d5ebe1d7cfc236a7b2dc7726c42a32bd46254ba29f9fc68c567f` | `2211b0cb3da7d8789a4a4df96509b95e6b1be8b222be2f530e7bd0695573bd69` |
