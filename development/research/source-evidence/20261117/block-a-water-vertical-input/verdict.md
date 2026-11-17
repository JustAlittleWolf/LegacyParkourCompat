# Independent source verdict: vertical water input

Pair: 1.12.2 → 1.13. Scope is the ordinary unmounted local player’s held jump in shallow/deep water and sneak-driven water descent. This does not audit water-height sampling, eye/body tests, gravity, other fluids, or mounted input.

## Claim verdicts

### Held jump and water depth — ACCEPT

The immutable draft `lane-water-vertical-input/1.12.2--1.13/held-jump-water-depth.md` was read and SHA-256 rechecked as `9d7fcf2199b3eb58225756c44cf3365b0e3f0b5b1a53d12b0867d494722cc88b`.

In 1.12.2, `LivingEntity.mobTick` routes every `jumping && isInWater()` case to `jumpInWater()`, which adds `0.04F` to the double `velocityY`; it does not inspect water depth, on-ground state, or the ground-jump cooldown on this branch (`LivingEntity.java:1827–1847`, `1413–1415`). The ordinary ground jump instead assigns `velocityY = getJumpStrength()` and stores `jumpingCooldown = 10` when on ground and the cooldown is zero (`1394–1410`, `1833–1838`).

The first changed release in this bounded pair is 1.13. Its exact nested gate is:

```java
if (!(waterHeight > 0.0) || onGround && !(waterHeight > 0.4)) {
    ...
    if ((onGround || waterHeight > 0.0 && waterHeight <= 0.4) && jumpingCooldown == 0) {
        jump();
        jumpingCooldown = 10;
    }
} else {
    jumpInWater();
}
```

For water cases this means: grounded with `0 < waterHeight <= 0.4` takes the ordinary jump only when the cooldown is zero; if that cooldown is nonzero, this branch applies no jump impulse that tick. Airborne shallow water and water above `0.4` take the liquid branch and add `0.04F`. A grounded deep-water case also takes the liquid branch. The height comparisons are strict `> 0.0`, strict `> 0.4` in the outer exclusion, and `<= 0.4` in the jump predicate; preserving the parentheses matters. The default jump-strength getter returns `0.42F`; `jump()` stores it in double `velocityY` (then applies the separate jump-boost addition if present). The cooldown is an `int`, decremented before the jump branch, set to `10` after the jump, and reset to zero when `jumping` is false (`LivingEntity.java:1428–1453`, `1833–1836`, `1884–1901`).

The keyboard path is authoritative for this local-player case: `KeyboardInput.tick` reads the jump key into `Input.jumping` (`1.13/ornithe-feather/.../KeyboardInput.java:44`); `LocalClientPlayerEntity.mobTick` refreshes input before calling `super.mobTick`, and `LivingEntity.mobTick` calls `serverTickAi` before the jump block (`LocalClientPlayerEntity.java:694–699`, end call at `833`; `LivingEntity.java:1877–1885`). The jump block is before the `travel` profiler section (`1900–1905`). `0.04F` is a binary32 literal widened for arithmetic with double `velocityY`; the ordinary `0.42F` getter result is likewise widened on assignment.

The same relevant gate, liquid impulse, jump store, and cooldown store were checked in 1.13.1, 1.13.2, 1.14 Feather, 1.14.4 Mojmap, and 1.15.2 Mojmap. In 1.14.4/1.15.2 the corresponding names are `waterHeight`, `noJumpDelay`, `jumpFromGround`, and `jumpInLiquid`; the branch order remains the same. Thus 1.13 is the first version in the reviewed pair, with no later difference among the assigned endpoints.

### Sneak adds downward water velocity — ACCEPT

The immutable draft `lane-water-vertical-input/1.12.2--1.13/sneak-downward-water-input.md` was read and SHA-256 rechecked as `0d86ce1dc9b9aa092848aabd0133d5ce203ecfd15b6387e7c7b8fc85c07bc509`.

The checked 1.12.2 local-player `mobTick` refreshes input and then calls `super.mobTick`; it has no water-plus-sneak vertical operation (`LocalClientPlayerEntity.java:694–699`, `771–817`). Its `LivingEntity` has no `knockDownwards` method. In 1.13, after refreshing the key state, the local-player method checks the literal gate `isInWater() && input.sneaking`, invokes `knockDownwards()`, then calls `super.mobTick()` (`LocalClientPlayerEntity.java:694–699`, `780–783`, `833–838`). `LivingEntity.knockDownwards()` executes `velocityY -= 0.04F` (`1447–1449`). It therefore adds a downward binary32 `0.04F` amount, widened during arithmetic with the double vertical velocity, for any water-contact state satisfying that gate; it does not require swimming or a jump. This runs before the superclass jump/travel work.

The input comes from `KeyboardInput.tick`, which reads the sneak key into `Input.sneaking` (`1.13/ornithe-feather/.../KeyboardInput.java:44–45`). The water-and-sneak operation remains before superclass travel in 1.13.1, 1.13.2, and 1.14 Feather. In 1.14.4 and 1.15.2 Mojmap the names become `input.sneakKeyDown` and `goDownInWater`; the same `isInWater()` gate remains in `LocalPlayer.aiStep`, and `LivingEntity.goDownInWater()` adds `(0.0, -0.04F, 0.0)` before `super.aiStep()` reaches travel. The widened component is still a binary32 `-0.04F` passed to double-valued vector math. No later difference was found among the assigned endpoints.

## Frozen evidence identity and source recheck

Before requesting the drafts, the bounded source roster was frozen by exact version/mapping root. Each `*.sources.sha256` file’s SHA-256 below matches its ready marker’s `sourceManifestSha256`. After reading the drafts, each cited core source file was rehashed and its digest matched the corresponding entry in that manifest.

| Exact source | Mapping | Ready marker SHA-256 | Source manifest SHA-256 |
|---|---|---|---|
| 1.12.2 | Ornithe Feather | `b0aeec721e5c0af02b33d9e217cb8272889fa139ded1ecc2a31e42b930138f7a` | `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da` |
| 1.13 | Ornithe Feather | `82a4a8f6a03fbd99a7b8577b1ae5980d95af07c7ded0da284106d16eb1e01a99` | `0509c2614b0bb0e616e2ebc6382f8eb5d031b5d86b834a4a6ed7fd96b6d68f1a` |
| 1.13.1 | Ornithe Feather | `f098b3a08b37276cc1a465a8a122df8de76d68786c854b92bac1ca51a79670de` | `21770b01ff3bf443db2e9e1b6df7db25d2fd6b7a72a86228259269c932d3abba` |
| 1.13.2 | Ornithe Feather | `1d1c644c1deb05c02d11dc575ad834f4f72489124701d9b2b90357f43dc37b38` | `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211` |
| 1.14 | Ornithe Feather | `e0ecb319a63d5d664b87ad7c00638e1cdb9dc302ee2c05bbc05a88ce96d1e60b` | `68bc39e38bc501e7c9af315de4678263e1a64606b0b9780099187c7984ccbc98` |
| 1.14.4 | Mojmap | `ec7b6a6d9ba72f8a19908527977c970a8e2b3007b0e0b4323a725fb5943d3043` | `af98406f3d4ed31494fbdce1078d1f3df3f712501878f9a0e9ad5879bdf23c3b` |
| 1.15.2 | Mojmap | `64a0e40b784d525ce50937c63e465fc4f564efb1f7af17f2bac3c77a36907be0` | `cb7fd93f8730d8b2813857744f8572060535c7de44fcee86222acd1ceb6f43e7` |

The frozen core roster was the local-player class, `LivingEntity`, `Input`, and `KeyboardInput` at each assigned endpoint (Feather names through 1.14; Mojmap names for 1.14.4 and 1.15.2). Exact claim-to-source line references are above and in the immutable drafts. No companion comparison draft was supplied or reviewed.
