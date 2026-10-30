# Bounded water sign witness attempt: 1.13.2 → 1.14.4

## Result

**No concrete ordinary-player witness is closed.** The gate divergence is arithmetically real for slow falling, but collision-free ordinary player movement does not provide a post-capture positive Y input. An upward collision/step path would need its own exact shape and displacement witness; that dependency remains open. Do not promote this to an ordinary reachable movement difference from the present evidence.

## Evidence identity

Claim inputs: `ordinary-gates/ordinary-gate-closure.md` (SHA-256 `2c70c46506dddef309ae52dbff41bf9c1bc7ecc519486f71c7c5734dc08e436c`) and `ordinary-gates-audit/audit.md` (SHA-256 `6c798986b64c8f53347b70c47c1f2b71733830e43cd1ecdf5213fe78f87dc73f`). Both identify the pair as Ornithe Feather Gen2 build 2. Canonical read-only roots were `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.13.2\ornithe-feather` and `...\1.14.4\ornithe-feather`.

| Version | Source file | Lines used | SHA-256 |
|---|---|---:|---|
| 1.13.2 | `net/minecraft/entity/living/LivingEntity.java` | 1478–1484, 1639–1654, 1926 | `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c` |
| 1.14.4 | `net/minecraft/entity/living/LivingEntity.java` | 1786–1793, 1904–1928, 2227 | `2cccf4331ce9e62013eeb8e96163e5e146e1291619eb998ce9867daa87c02b61` |
| 1.13.2 | `net/minecraft/entity/living/player/PlayerEntity.java` | 1442–1465 | `4ed22f6c5a3c55d67eed782070ac722201df4d624adbc90779f1fd29c2876633` |
| 1.14.4 | `net/minecraft/entity/living/player/PlayerEntity.java` | 1381–1405 | `2614deb3b50d11f6cdf54d9e308703bf49cb53b72646b05253857e9557d657df` |
| 1.13.2 | `net/minecraft/entity/Entity.java` | 953–959, 1072 | `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269` |
| 1.14.4 | `net/minecraft/entity/Entity.java` | 945–954, 1062–1076, 1814–1827 | `7315a496c195da767de9d4936d3adb6efc3c419dc0f0e95d6f32781b0da1ba55` |
| 1.13.2 | `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | 748–757 | `2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29b29d8d9bf42bf` |
| 1.14.4 | `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | 679–688 | `708af6a3880fb58b67bf4604a5509b351719a9c2a0c06a8ec261586435bf00ce` |

## Source trace and reachability

- At entry to old `LivingEntity.moveRelative`, `d` is `0.01` only when entry `velocityY <= 0` and slow falling is active (1478–1484). New caches that same entry sign as `bl`, then selects the same `d` (1786–1793). This base-call entry is **after** the player override's swimming correction on both sides (old `PlayerEntity.moveRelative` 1446–1451; new 1385–1391). Therefore that correction cannot itself be the post-capture sign crossing. Jumping/player AI can alter velocity before the base call, but any such state must still arrive at the base-call entry nonpositive to select slow fall.
- For `d = 0.01`, the threshold requires post-damping `y > 0`, `abs(y - 0.005) >= 0.003`, and `abs(y - 0.000625) < 0.003`. Their positive overlap is `0 < y <= 0.002`; exact `y = 0.000625` qualifies. At that exact value old sees a positive post-damping sign and subtracts `0.000625`, producing `0`; new's cached `bl == true` takes the snap and writes `-0.003`. The audit correctly notes that with default `d = 0.08`, both magnitude predicates cannot be true together because they compare the same value to `0.005` with mutually exclusive `< 0.003` and `>= 0.003` bounds.
- The base water branch applies `updateVelocity`, calls `move`, damps Y by `0.8F`, and then checks gravity. Old `Entity.updateVelocity` adds its second argument (`forwards`) to Y; old travel supplies `(sidewaysSpeed, verticalSpeed, forwardSpeed)` (LivingEntity 1926). New `updateVelocity` adds the transformed vector's Y component, and new travel supplies `verticalSpeed` as vector Y (2227; Entity 1062–1076). A read-only search of both exact pair trees found assignments to `verticalSpeed` only in `MobEntity` and `DolphinEntity`, not the player/living-player classes; the ordinary player input to this Y slot is therefore zero. With collision-free water movement, nonpositive base-entry Y plus zero Y input cannot become positive before damping. Player jump/swimming preparation occurs earlier and would change the base-call entry itself.
- A possible state mismatch does satisfy the *other* gates: entity water-state processing calls `updateSwimming` before `LivingEntity.tick` reaches local `mobTick` and travel (Entity 394, 947–950 old; 357, 942–945 new; LivingEntity 1690, 1752 and 1980, 2046). Local-player sprint handling can then cancel sprint while retaining the already-set swimming flag for the remainder of that tick: old local player 751–757 and new 682–688. Thus `isSwimming()` with `!isSprinting()` is not an impossible combination here. To enter the swimming correction, water must also satisfy its `isSwimming() && !isRiding()` gate; its upward-look branch requires jumping or fluid at the queried head block, or a nonpositive look Y. These conditions do not supply a positive vertical input after the base-call sign snapshot.
- The gravity block is skipped for `noGravity` and sprinting on both sides (old 1644; new 1912). Even where stale swimming state supplies `!sprinting`, collision-free motion still fails the required sign crossing. Both implementations also have a later horizontal-collision/climb-up branch that can write `0.3F` after the gravity calculation (old 1652–1653; new 1924–1927); any proposed witness must show that this does not override or mask its claimed result.

## Bounded disposition

Collision-free ordinary player path: **rejected as a witness** because the required base-call entry `y <= 0` and ordinary player Y input `0` leave no positive post-damping value. Collision-based routes remain **unresolved**, not impossible: an upward step/clipping result would require a concrete vanilla shape, exact `move` resolution, and proof that the resulting damped Y lies in `0 < y <= 0.002` without the later `0.3F` branch. This attempt did not inspect block-shape producers, enumerate collision trajectories, run code, or establish runtime parity.
