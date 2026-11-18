# 1.12.2 → 1.13 player swimming pitch adjustment

## Exact evidence identity

Compared exact releases **1.12.2** and **1.13** using Ornithe Feather (`feather-gen2-1.12.2+build.2` and `feather-gen2-1.13+build.2`). The canonical read-only sources used are under `decompiled_minecraft/<version>/ornithe-feather/`.

- 1.12.2 ready marker: `decompiled_minecraft/1.12.2/ornithe-feather.ready.json`, status `ready`, source manifest SHA-256 `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`. Actual manifest hash matches. Provenance: `ornithe-feather.provenance.json`, SHA-256 `747425a26da363d1abc8a2cecd83a74504aeb1f58be93aa53f48c690e35318ce`.
- 1.13 ready marker: `decompiled_minecraft/1.13/ornithe-feather.ready.json`, status `ready`, source manifest SHA-256 `0509c2614b0bb0e616e2ebc6382f8eb5d031b5d86b834a4a6ed7fd96b6d68f1a`. Actual manifest hash matches. Provenance: `ornithe-feather.provenance.json`, SHA-256 `34652aeef6914b5cbff6619ae60a855aa676b2ae1f607eccdaa623ad09dc5e8d`.
- Both ready records name prior campaign `outputPath`/`sourceRoot` locations that are absent in this checkout. The current canonical trees contain the relied source bytes: each relied Java file's SHA-256 matches its exact entry in the corresponding source manifest. The ready records' manifest hashes match those manifests.

Relied source byte hashes (SHA-256):

| Release | File | SHA-256 |
| --- | --- | --- |
| 1.12.2 | `net/minecraft/entity/living/player/PlayerEntity.java` | `e4e0fdbe07a7d0a0ae4a70cbb6739a287c9d045a4a12b409895c220b5d91fe1e` |
| 1.12.2 | `net/minecraft/entity/living/LivingEntity.java` | `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6` |
| 1.12.2 | `net/minecraft/entity/Entity.java` | `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a` |
| 1.13 | `net/minecraft/entity/living/player/PlayerEntity.java` | `4a57ce65405436aeb211d7a04a9f6ae1a661d033fd3ba2424909bfb42b8acd` |
| 1.13 | `net/minecraft/entity/living/LivingEntity.java` | `c38e95c2c13e088b03d77fc2c6b98d4edd7e6ca5aef12b187c8e3f3ea782e864` |
| 1.13 | `net/minecraft/entity/Entity.java` | `09514488d9055449c47e2e4bcd23b5773203ca634b7ecba2323a99d0ae016642` |
| 1.13 | `net/minecraft/client/entity/living/player/ClientPlayerEntity.java` | `99b57acea68323125d859313ef5dd2c55d0703c08c5cce475c0227da0d12d1ee` |
| 1.13 | `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `a188e1bc87831dd9da1ec1366a49acc4104c18e6979129097d02d5d3617e6a08` |

## Difference

**1.12.2**, `PlayerEntity.moveRelative(float,float,float)`, lines 1386–1404: the override records the initial position. If abilities allow flying and the player is not riding, it temporarily changes `speedInAir`, delegates to `super.moveRelative`, restores vertical velocity to the saved value times `0.6`, restores air speed, clears fall distance and clears the fall-flying flag. Otherwise it delegates directly. There is no swimming/pitch adjustment before delegation.

**1.13**, the same override, lines 1435–1462: before the flying branch or `super.moveRelative`, it adds this block:

```java
if (this.isSwimming() && !this.isRiding()) {
    double g = this.getLookVector().y;
    double h = g < -0.2 ? 0.085 : 0.06;
    if (g <= 0.0 || this.jumping || !this.world.getBlockState(new BlockPos(this.x, this.y + 1.0 - 0.1, this.z)).getFluidState().isEmpty()) {
        this.velocityY = this.velocityY + (g - this.velocityY) * h;
    }
}
```

`g` and `h` are `double`; the comparisons and constants are double-valued, with no cast. `Entity.getLookVector()` (both versions, `Entity.java:1690–1692`) uses the current `pitch` and `yaw`. In 1.13 its rotation-vector vertical component is `-sin(pitch * (float)(PI / 180.0))` (`Entity.java:1250–1258`), so `g` is the current look vector's vertical component. The adjustment moves `velocityY` toward that component by factor `h`.

The outer gate is player swimming state and not riding. `PlayerEntity.isSwimming()` at 1.13 lines 1814–1816 further requires not flying and not spectator. The inherited `Entity.updateSwimming()` (lines 949–956) updates the state from sprinting plus water/submerged-water and not riding; `PlayerEntity.updateSwimming()` (lines 1464–1471) clears it for flying and otherwise delegates under its block-space condition. These establish a reachable ordinary player swimming state without relying on fluid-contact internals. A concrete local witness is the 1.13 `LocalClientPlayerEntity` (`LocalClientPlayerEntity.java:87`), which extends `ClientPlayerEntity` (`ClientPlayerEntity.java:22`), itself a `PlayerEntity`; the local tick calls `super.tick()` (`LocalClientPlayerEntity.java:166–168`). A local player can sprint while submerged, remain unmounted, and enter this state. That tick reaches the inherited living travel call and dynamically dispatches to the `PlayerEntity` override.

Inside the new block, the velocity change is applied when the look vector is level/upward (`g <= 0`), jump is active, or the block at `(x, y + 0.9, z)` has nonempty fluid. Thus a downward look (`g > 0`) alone does not trigger it when not jumping and that fluid query is empty. The upward-looking and jumping cases do not require that query to be nonempty.

The generic `LivingEntity` travel/tick path invokes `this.moveRelative(this.sidewaysSpeed, this.verticalSpeed, this.forwardSpeed)` (1.12.2 lines 1842–1847; 1.13 lines 1901–1907), dispatching to the player override. The 1.13 player adjustment runs before `super.moveRelative`; inherited water movement then performs input acceleration and `move` using the adjusted velocity before its drag/gravity work. The inherited 1.12.2 water path has no earlier pitch-directed vertical change. The override's `upwards` parameter is not used by the new term; `jumping` is the separate input/state gate.

**Movement meaning:** in a reachable 1.13 swimming tick, looking upward/level (or jumping) can alter vertical velocity toward the look direction before that tick's inherited movement. Looking downward requires either jump or the specified fluid state above the player. No trajectory magnitude beyond this source-level effect is asserted.

## Scope

This closes only the assigned direct pitch/look-directed vertical swimming adjustment slice for exact pair 1.12.2 → 1.13. It does not establish the patch introduction point within the interval, complete pair coverage, independent acceptance, runtime parity, or any conclusion outside these exact methods/dependencies. No A contact/fluid physics or B collision internals were investigated.