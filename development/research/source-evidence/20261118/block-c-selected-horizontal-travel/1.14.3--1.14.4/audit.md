# Independent source audit: 1.14.3 → 1.14.4 horizontal travel

## Boundary and verdict

**Verdict: no source-confirmed difference in the assigned slice.** I independently traced the ordinary dry, unmounted, local, nonsprinting ground/air horizontal path: local input values reaching `LivingEntity.travel`, directional input conversion and acceleration, the player movement-speed value chosen for ground acceleration, and horizontal drag after movement. I treated block friction, yaw, and `onGround` as supplied. No change was found in the numeric operations, constants, branch ordering, or values selected for this slice.

The ordinary standing keyboard input path is unattenuated; the keyboard method also contains the same 0.3F sneak attenuation expression when its boolean gate is active. The pose-derived boolean passed into that method differs in names and source expressions between releases, so special crouch/crawl pose conditions are outside this ordinary-standing verdict. Item-use attenuation is included because it feeds the same local movement fields; the 0.2F scale and unmounted gate match. Sprint-specific transitions/timers and special travel branches are outside the verdict. The post-travel player update of the air-speed field is described below only to establish the stable nonsprinting value and ordering.

## Exact source identity and integrity

- **1.14.3, Yarn:** `decompiled_minecraft/1.14.3/yarn/`. `.decompile-provenance.json` SHA-256: `81EA0021EB122D4E3340E20D47D6ECB7079365BA767A038D26A8264CDDACC28A`. The recorded inputs pin the 1.14.3 client (`4063062677433988E012FBA53E42E4C21ADB6919AC7F061F19F3C6E75B334E07`), Yarn `1.14.3+build.13` (`AD9E77F907CFC6780FB348AD4B3A948FFC8CEA3BFE50ABCC792428645E49E06E`), and Intermediary `1.14.3` (`F2E9990C00FA87DDC7158A95AFB735A947AE26D1997B52916226E11C612542A9`).
- **1.14.4, Mojmap:** `decompiled_minecraft/1.14.4/mojmap/`. `mojmap.ready.json` SHA-256: `EC7B6A6D9BA72F8A19908527977C970A8E2B3007B0E0B4323A725FB5943D3043`; `mojmap.provenance.json` SHA-256: `9D1AD405E1E787D2FCA5ED1897E6797B1F3CD79527DA50D713232F46E2F30D97`; `mojmap.sources.sha256` SHA-256: `AF98406F3D4ED31494FBDCE1078D1F3DF3F712501878F9A0E9AD5879BDF23C3B`. Ready marker and manifest both report 3,250 source records. The ready marker identifies version `1.14.4`, Mojmap, and client SHA-256 `B3B2A798E2D67B566008FE4A03767AE2C7FF3F8C7BA6751E7B71FC7299672D0A`.

The following movement source files were individually checked against their pinned source entries; every computed SHA-256 matched:

| 1.14.3 Yarn relative source | SHA-256 |
|---|---|
| `net/minecraft/client/input/KeyboardInput.java` | `ADE8C1B46B4B25888D326A963F645DD4FE82F53EF0797C2E499FE37E9BB47AA0` |
| `net/minecraft/client/input/Input.java` | `0A14D641CEA95ECD6A836A2AECF0A7627DBF236460160A263A85B574A0216E4E` |
| `net/minecraft/client/network/ClientPlayerEntity.java` | `28B6B4031E02AFD4A2E6B4A80E5A7CA06F16DAC42CA0F92CAB25E2C2DC9E14AE` |
| `net/minecraft/entity/Entity.java` | `9B204A5F9AF6CA50C09DB4B6185FF37C39980C1A8CD1B5FD004713ECFFAE5C14` |
| `net/minecraft/entity/LivingEntity.java` | `E8AED9C63C0DFBE88E4B9E11C58881BC6E04B9CBAECBDE5E3606E3AFA406D72B` |
| `net/minecraft/entity/player/PlayerEntity.java` | `E3F361AC4E369D01473F3D84044EE33F9685D182C4485E2E80B7F62756950963` |

| 1.14.4 Mojmap relative source | SHA-256 |
|---|---|
| `net/minecraft/client/player/KeyboardInput.java` | `33DAA0833A95E09E728C1B8F509020DD13B70E38ABA922E2B1E10EC5C9B7739A` |
| `net/minecraft/client/player/Input.java` | `8ADAF34FA0BCEF319D8B3DD678F53DDF68B0293A17E2C1620A6528FA0D9AD1EF` |
| `net/minecraft/client/player/LocalPlayer.java` | `0795C1223198CE5ACF5D2ED9E5DB8435BBEC4CD52B96F96B1DDF2865BAAAE85F` |
| `net/minecraft/world/entity/Entity.java` | `31C6BE42D165102D3E6DC4295FDFEF6E8971FC3AEA13F938A5B3A33B91D524A7` |
| `net/minecraft/world/entity/LivingEntity.java` | `428762178A876EFD4069086E6B7D51F571EA0401F44E7EFF0927B7D26D9EF681` |
| `net/minecraft/world/entity/player/Player.java` | `E9CE5EB18C5581FFE2B610B273FFD85786587A00BA853E5DBA0AC96593622B12` |

## Compared operations

### Input construction and consumption

- `KeyboardInput.tick`: 1.14.3 Yarn `net/minecraft/client/input/KeyboardInput.java:13–26`; 1.14.4 Mojmap `net/minecraft/client/player/KeyboardInput.java:13–26`. Both derive each axis as `0.0F` for equal key states, otherwise forward/back or left/right as `+1`/`-1`; both set jumping/sneak state and multiply both impulses by `(float)(impulse * 0.3)` when `!bl2 && (sneak || bl)`. No arithmetic or gate difference.
- The vector wrapper is unchanged in structure: `Input.getMovementInput():Vec2f` at 1.14.3 `Input.java:18–20` and `Input.getMoveVector():Vec2` at 1.14.4 `Input.java:18–20` simply return the two stored float fields, sideways/forward or left/forward.
- Local player copies those two floats into the inherited travel fields: 1.14.3 `ClientPlayerEntity.tickNewAi():588–599` copies `input.movementSideways` and `.movementForward` to `sidewaysSpeed` and `forwardSpeed`; 1.14.4 `LocalPlayer.serverAiStep():599–611` copies `input.leftImpulse` and `.forwardImpulse` to `xxa` and `zza`. Both local-control gates guard the copy. Both local tick methods pass pose/spectator booleans to `Input.tick`: 1.14.3 `ClientPlayerEntity.tickMovement():614–619` and 1.14.4 `LocalPlayer.aiStep():625–630`. The keyboard tick body applies the same conditional 0.3F scaling, but pose-boolean production is not adjudicated for special poses; ordinary dry standing input does not activate that gate. Item-use attenuation multiplies both input fields by `0.2F` under `isUsingItem() && !hasVehicle()` in 1.14.3 `ClientPlayerEntity.tickMovement():620–624` and by the equivalent `isUsingItem() && !isPassenger()` in 1.14.4 `LocalPlayer.aiStep():631–635`.
- Before travel, both releases multiply the two horizontal inputs by `0.98F`: 1.14.3 `LivingEntity.tickMovement():2214–2220`; 1.14.4 `LivingEntity.aiStep():2217–2223`. They pass the strafe field as vector x and the forward field as vector z. The vertical component is outside this slice.

### Acceleration and movement-speed selection

- Ordinary nonfluid, nonfall-flying travel enters the same branch after the fluid and Elytra gates: 1.14.3 `LivingEntity.travel():1788–1790,1833–1839`; 1.14.4 `LivingEntity.travel():1791–1793,1836–1842`. For the player dry path, both read the block immediately below the bounding-box minimum Y, obtain its friction/slipperiness, and choose `w = onGround ? blockCoefficient * 0.91F : 0.91F` before applying movement.
- Acceleration uses the same selection formula: `onGround ? movementSpeed * (0.21600002F / (blockCoefficient * blockCoefficient * blockCoefficient)) : flyingSpeed`. This is 1.14.3 `LivingEntity.method_18802():1954–1956`, with `getMovementSpeed()`; 1.14.4 `LivingEntity.getFrictionInfluencedSpeed():1957–1959`, with `getSpeed()`. Both player overrides return the movement-speed attribute value as a float: 1.14.3 `PlayerEntity.getMovementSpeed():1407–1410`, 1.14.4 `Player.getSpeed():1423–1426`. Both register the player movement-speed attribute at `0.1F`: 1.14.3 `PlayerEntity.initAttributes():171–178`; 1.14.4 `Player.registerAttributes():188–195`.
- In stable nonsprinting travel, air acceleration field is `0.02F`: 1.14.3 `LivingEntity.field_6281` is initialized at `LivingEntity.java:143`; 1.14.4 `LivingEntity.flyingSpeed` at `LivingEntity.java:143`. Player refreshes it after `super` movement to `0.02F` and adds the same sprint-only `0.005999999865889549` when sprinting: 1.14.3 `PlayerEntity.tickMovement():493–504`; 1.14.4 `Player.aiStep():508–521`. This preserves the stable nonsprint value and ordering; sprint transitions remain outside this assignment.
- Input-to-velocity conversion is the same expression and sequence: 1.14.3 `Entity.updateVelocity()` / `movementInputToVelocity():1055–1069`; 1.14.4 `Entity.moveRelative()` / `getInputVector():1080–1094`. Both add the resulting vector to current velocity; use squared length, return zero below `1.0E-7`, normalize only above length-squared `1.0`, scale/multiply by the float acceleration, then rotate x/z using sine and cosine of `yaw * (float)(PI / 180.0)`. The operation order and float/double boundaries agree.

### Horizontal drag / friction

- Both branches move with velocity after acceleration, then multiply post-move horizontal x/z by the preselected `w`; the vertical component independently uses `0.98F` after gravity. 1.14.3 `LivingEntity.travel():1834–1839,1859`; 1.14.4 `LivingEntity.travel():1837–1842,1862`. With block coefficient and `onGround` supplied, horizontal drag is exactly the same for ground and air. The coefficient accessor name differs (`getSlipperiness()` vs `getFriction()`), but accessor output is explicitly supplied and outside this comparison.

## Coverage and limits

Compared the full ordinary travel branch and its direct input, rotation, speed, and horizontal drag dependencies listed above. The watery/lava, Elytra, climbing, effects/gravity/vertical correction, collision resolution/stepping/support, block-coefficient production, sprint-specific behavior, jump logic, and nonlocal/nonplayer paths were not accepted as part of this audit. The source evidence establishes no difference within the bounded operations; it does not establish runtime parity or exact patch introduction history.



## Phase 2 independent audit of author report

Author report identity read: uthor/report.md, 7,585 bytes, SHA-256 4311D24605DBC70D2B4839D540847859C47D0A529A4571FFE3C68FE3AAB6B837.

**Verdict: QUALIFIED ACCEPT.** The bounded no-delta conclusion and compared travel/acceleration/speed/drag and yaw-conversion claims agree with the independently checked pinned source. Additional direct source checks confirm the 1.14.3 PlayerEntity.travel and 1.14.4 Player.travel ordinary unmounted branches delegate to LivingEntity.travel; the Vec3d/Vec3 normalization guards and component divisions, scalar multiply/scale wrappers, length-squared operations, and MathHelper/Mth sine/cosine table-index expressions match. The additional source-file hashes listed by the report match their pinned entries.

Qualification: the report first cites the 1.14.4 horizontal post-move write as LivingEntity.java:1860; in the pinned source it is line 1862. The report later gives the correct line 1862. Correct the earlier line reference; the discrepancy does not change the source-supported conclusion. The report treats the input vector as supplied, so its bounded claims do not cover local keyboard-to-travel-field preparation; the frozen blind-prefix audit covers that adjacent path separately and found no ordinary-standing or item-use input-scaling delta. This is a coverage boundary, not a conflicting claim.
