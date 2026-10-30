# Independent source-only audit: camera rounding closure

## Verdict

The closure is **verified** on the scoped claims. I found no operand-type, float-product, binary64 recurrence, reachable-input, or same-tick call-order error. It correctly limits its result to a difference in the vertical value supplied to same-tick movement; source evidence does not establish a different final position or trajectory.

## Claim checks

1. **Operand types and order — verified.** In 1.13.2, PlayerAbilities.flySpeed is double, initialized with 0.05F, while getFlySpeed() returns float; the camera branch evaluates getFlySpeed() * 3.0F as float before the double subtraction/addition. The sneak assignment precedes the jump assignment, so the second binary64 operation consumes the rounded first result (LocalClientPlayerEntity.java:797–806; PlayerAbilities.java:11,44–50). In 1.14.4 the local counter is int; the paired inputs decrement then increment it to zero, and the guard skips the Vec3d update (LocalClientPlayerEntity.java:728–742). The single-input product is float before widening as a vector component.

2. **Exact default-speed witness — verified independently.** 0.05F is binary32 0x3D4CCCCD; multiplying by 3.0F gives p = 0.15000000596046448f, bits 0x3E19999A. Float-to-double widening gives 0.1500000059604644775390625. Reproducing IEEE-754 binary32 products and binary64 operations gives:
   - sneak from zero, then flight damping: -0.09000000357627869 (0xBFB70A3D80000000);
   - neutral tick, then damping: v = -0.05400000214576721 (0xBFABA5E366666666);
   - old subtract then add: -0.2040000081062317 (0xBFCA1CAC1999999A), then -0.05400000214576722 (0xBFABA5E366666668);
   - residual: -1.3877787807814457e-17 (0xBC70000000000000), two binary64 ULPs at this magnitude.

   New paired-input j == 0 skips the update, leaving v unchanged at the camera branch.

3. **Source-reachable sequence — verified with the stated conditions.** GameMode.SPECTATOR sets canFly and flying (GameMode.java:39–43); local-player mobTick enforces flight for spectators (LocalClientPlayerEntity.java:760–765 old; :691–696 new). Camera gating is identity with the active camera (:648–650 old; :614–616 new). The keyboard sources continue to read sneak/jump booleans while spectator (KeyboardInput.java:13–50 old; :13–26 new). Spectator sets noClip and clears onGround in each version (PlayerEntity.java:177–180 old; :208–212 new), and the post-super.mobTick ground-flight clearing is explicitly disabled for spectator (LocalClientPlayerEntity.java:837–840 old; :773–776 new). An initial zero-Y state is source-plausible: old entity velocity fields are Java-default zero (Entity.java:115–117); new velocity is initialized to Vec3d.ZERO (Entity.java:123, getter :2646–2648). This supports the described initial state and sequence; it does not imply that arbitrary mode transitions reset velocity.

4. **Same-tick movement handoff — verified.** LivingEntity invokes mobTick before its travel phase (:1752, :1920–1926 old; :2046, :2221–2227 new). The old player override saves the updated Y, calls base movement, then damps the saved value (PlayerEntity.java:1442–1465); the base method performs move using current velocity (LivingEntity.java:1478–1575). In 1.14.4 the analogous override calls base movement and then restores Y as i * 0.6 (PlayerEntity.java:1380–1405); the base method moves with its current velocity (LivingEntity.java:1786–1846). Thus the paired-input residual reaches the move operation in the old path, while the new camera branch leaves the incoming Y unmodified. This is movement-input evidence only.

## Corrections and limits

No correction needed. The closure's qualification that source does not prove a changed final position/collision/trajectory is accurate. Its default-flight envelope is conditional on the stated speed and flight path, not a global bound on entity velocity.

## Source identity

All eight paired source-file hashes listed by the permitted closure were independently recomputed and matched. The additional input/entity evidence hashes are below. Line numbers refer to the stated exact Ornithe Feather Gen2 build 2 source roots.

| Version | Source file | SHA-256 |
|---|---|---|
| 1.13.2 | net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java | 2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29b29d8d9bf42bf |
| 1.13.2 | net/minecraft/entity/living/player/PlayerAbilities.java | 03c506732bb599c2a6d5f6c7d6f13175b9d27a0abfd9058811cf443f51af012d |
| 1.13.2 | net/minecraft/entity/living/player/PlayerEntity.java | 4ed22f6c5a3c55d67eed782070ac722201df4d624adbc90779f1fd29c2876633 |
| 1.13.2 | net/minecraft/entity/living/LivingEntity.java | bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c |
| 1.13.2 | net/minecraft/entity/Entity.java | 1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269 |
| 1.13.2 | net/minecraft/client/entity/living/player/KeyboardInput.java | 7be11425906be051c83e275f359816546e4677b16d212156380e8d2e9258654a |
| 1.14.4 | net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java | 708af6a3880fb58b67bf4604a5509b351719a9c2a0c06a8ec261586435bf00ce |
| 1.14.4 | net/minecraft/entity/living/player/PlayerAbilities.java | 706f0011836698a9be661cda922d42c7143fdb385b7086ed79143ef3d68874ee |
| 1.14.4 | net/minecraft/entity/living/player/PlayerEntity.java | 2614deb3b50d11f6cdf54d9e308703bf49cb53b72646b05253857e9557d657df |
| 1.14.4 | net/minecraft/entity/living/LivingEntity.java | 2cccf4331ce9e62013eeb8e96163e5e146e1291619eb998ce9867daa87c02b61 |
| 1.14.4 | net/minecraft/entity/Entity.java | 7315a496c195da767de9d4936d3adb6efc3c419dc0f0e95d6f32781b0da1ba55 |
| 1.14.4 | net/minecraft/client/entity/living/player/KeyboardInput.java | 5932453a9e48e7a798ae1be1cd3a4bf660b6b3be43bb7e5dc22686b3c4a82526 |
| Both | net/minecraft/world/GameMode.java | f245fb32c78232d3e936edfd517c76e727591c93001049f4e5e94ce86ed48971 |

Permitted claim input: .native-source/20261030/1132-1144-remaining34/camera-rounding/camera-rounding-closure.md, SHA-256 5ca5ab255a3ba0604eaab1e5c35ed6a38229c8707b835e2ca0f3e6fe0b887b6c. No other reports, product sources, wiki, backlog, or history were consulted.
