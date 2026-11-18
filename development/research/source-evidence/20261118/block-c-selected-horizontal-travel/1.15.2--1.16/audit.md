# Independent source audit: 1.15.2 → 1.16 dry horizontal travel

## Bounded scope and verdict

Compared the exact Mojmap decompilations for an ordinary local player in dry, unmounted, nonsprinting ground/air travel, from the movement vector consumed by `travel` through relative horizontal acceleration and horizontal friction/drag. The sampled block friction coefficient, yaw, and `onGround` are dependency inputs, as assigned. Water/lava, fall-flying, abilities flight, sprint/equipment changes, climbing, collision/support/stepping, block effects, and vertical gravity or thresholds are outside this slice.

**Verdict:** no horizontal travel-arithmetic difference was established in this bounded path. In 1.16 the dry movement sequence is extracted into `handleRelativeFrictionAndCalculateMovement`; its ordinary non-climbing horizontal work preserves the 1.15.2 order. `Player.getSpeed()` reaches the movement-speed attribute through a renamed/convenience accessor in 1.16, with the float cast still at the same point. This source-level accessor change alone does not establish a different value for an ordinary initialized player.

## Source identity and byte integrity

The supplied readiness and provenance identities were verified directly:

- `decompiled_minecraft/1.15.2/mojmap.ready.json`: SHA-256 `64A0E40B784D525CE50937C63E465FC4F564EFB1F7AF17F2BAC3C77A36907BE0`
- `decompiled_minecraft/1.16/mojmap.ready.json`: SHA-256 `5B6CB24F95E83B856C53F646D6477F2E46DAE2276737DEE56DD07C5BD745E4A2`
- `decompiled_minecraft/1.15.2/mojmap.provenance.json`: SHA-256 `D4802D35EE2927A44D753871F184C3255C060EB94457A5C65A8BC121A087954E`

SHA-256 of the actual compared source files:

| Release | Relative source | SHA-256 |
|---|---|---|
| 1.15.2 | `net/minecraft/client/player/LocalPlayer.java` | `3A9019BD7B860E251C23FD8D0CD70B7F5B38566D34470C4E29B1014EF689CCBD` |
| 1.15.2 | `net/minecraft/client/player/AbstractClientPlayer.java` | `DB13C55B0BA08CB2A40CF2DA7A3954DE24E1E1F55A197F125CC1AA6ED109BE0B` |
| 1.15.2 | `net/minecraft/world/entity/player/Player.java` | `1BA2724C22163862B8F7FDFDEA5A04A66E4DB26A119D7E5360BA024724A34793` |
| 1.15.2 | `net/minecraft/world/entity/LivingEntity.java` | `46D243BB7E51F7B54404AA1D7D6E6B827682D0F5925D02847C4193306C4D5E54` |
| 1.15.2 | `net/minecraft/world/entity/Entity.java` | `191B3AD3E7348C9BAC1E703FFF896706D23A751BF15162AACF676F5F97C0A10E` |
| 1.16 | `net/minecraft/client/player/LocalPlayer.java` | `EE20523ADFDB82F3753FA43312AF170F34C4F4AA78570631C61BC8C0820DFB8B` |
| 1.16 | `net/minecraft/client/player/AbstractClientPlayer.java` | `89DDE15ACAA210ECDFD6BB429A8FC969ACDD189FEB03C0EA20FAEA13865EA64F` |
| 1.16 | `net/minecraft/world/entity/player/Player.java` | `A849960D47BF00A2CDDFB6636C78C518532A11967BA08829F19C5EBCED08681E` |
| 1.16 | `net/minecraft/world/entity/LivingEntity.java` | `B6EA2B9B037B79FC3EA6E1F6BCD2C4C1D89D4A96315968A8F247641F6C335F26` |
| 1.16 | `net/minecraft/world/entity/Entity.java` | `27E3BB01B5D03C09161891E717C16477899768333082E81EBFAAC4E525616576` |

Mapping family: Mojmap on both sides. The declarations show `LocalPlayer extends AbstractClientPlayer` and `AbstractClientPlayer extends Player` in both releases; neither `LocalPlayer` file declares `travel`.

## Paired source trace

- **Vector consumption and dispatch:** 1.15.2 `LivingEntity.java:2283–2287`; 1.16 `LivingEntity.java:2453–2457`. Both multiply `xxa` and `zza` by `0.98F`, call `updateFallFlying`, then invoke virtual `this.travel(new Vec3(this.xxa, this.yya, this.zza))` in the same order. For a local player, both `Player.travel` overrides delegate to `super.travel(vec3)` on the non-flight route (1.15.2 `Player.java:1448–1478`; 1.16 `Player.java:1380–1410`). The superclass travel gate is `isEffectiveAi() || isControlledByLocalInstance()` in both (`LivingEntity.java:1831–1832` and `1912–1913`).
- **Dry branch and friction input:** 1.15.2 `LivingEntity.java:1831–1895`; 1.16 `LivingEntity.java:1912–2024`. For a nonswimming, non-lava, non-fall-flying ordinary player, each samples `getBlockPosBelowThatAffectsMyMovement()` and obtains the block's `getFriction()` float, then sets the horizontal multiplier to `onGround ? friction * 0.91F : 0.91F` (1.15.2 lines 1886–1889; 1.16 lines 2021–2024). The assigned coefficient and `onGround` value are treated as inputs here.
- **Relative acceleration:** 1.15.2 calls `moveRelative(getFrictionInfluencedSpeed(v), vec3)` directly (`LivingEntity.java:1889`); 1.16 calls the same expression as the first operation in `handleRelativeFrictionAndCalculateMovement` (`LivingEntity.java:2060–2063`). Both `getFrictionInfluencedSpeed` bodies are textually the same: `onGround ? getSpeed() * (0.21600002F / (f * f * f)) : flyingSpeed` (1.15.2 `LivingEntity.java:2006–2008`; 1.16 `LivingEntity.java:2104–2106`). Thus the ground/air selector, float constants, cubic friction expression and operation grouping are preserved.
- **Consumed vector rotation/normalization:** `Entity.moveRelative` and `getInputVector` have the same body in both versions (1.15.2 `Entity.java:1063–1078`; 1.16 `Entity.java:1073–1088`): compute `lengthSqr`; return zero below `1.0E-7`; normalize only above length-squared 1; scale by the float acceleration; compute float sine/cosine from yaw in degrees; form the same rotated x/z components; add that vector to current delta movement. No ordering, type, cast, or threshold change was found.
- **Post-move horizontal friction:** In 1.15.2, after `moveRelative`, the branch applies climb handling, moves, and captures `vec38` (`LivingEntity.java:1889–1895`); later it writes `vec38.x * w` and `vec38.z * w` (line 1911). In 1.16 the extracted helper performs the corresponding operations and returns `vec32` (`LivingEntity.java:2060–2069`); the caller writes `vec38.x * u` and `vec38.z * u` (line 2039). With climbing/collision behavior outside scope, these are the same horizontal post-move components and the same multiplier, with the same order. No horizontal drag/friction difference was found.
- **Player speed dispatch:** 1.15.2 `Player.java:1493–1496` returns `(float)this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getValue()`. 1.16 `Player.java:1425–1428` returns `(float)this.getAttributeValue(Attributes.MOVEMENT_SPEED)`. The source access route changes, while the selected movement attribute and double-to-float cast remain explicit. 1.16 `LivingEntity.getAttributeValue` delegates to `getAttributes().getValue(attribute)` (`LivingEntity.java:1740–1746`). This audit does not assess a missing/uninitialized movement attribute fallback; that is not the ordinary initialized local-player path.

## Limits

This is a single exact endpoint comparison for the assigned horizontal travel slice, not proof of the patch introduction boundary, complete movement-pair coverage, server behavior, collision parity, or runtime trajectory parity. The effect of the changed attribute-access route under absent/malformed attribute state is unexamined. No gameplay, tests, builds, source generation, product implementation, or network sources were used.

## Stage 2 independent audit verdict

**QUALIFIED ACCEPT** — The author report's bounded no-horizontal-difference conclusion is accepted. The 1.16 helper extraction preserves the compared horizontal operations. The movement-speed accessor path is applicable to a vanilla Player: 1.15.2 `Player.registerAttributes` registers `SharedMonsterAttributes.MOVEMENT_SPEED` and sets its base to `0.1F` (`Player.java:190–195`); 1.16 `Player.createAttributes` supplies `Attributes.MOVEMENT_SPEED` with base `0.1F` (`Player.java:187–193`). 1.16 `LivingEntity.getAttributeValue` delegates to `AttributeMap.getValue` (`LivingEntity.java:1740–1746`); that method reads the existing `AttributeInstance.getValue()` when present, otherwise the registered supplier value (`AttributeMap.java:62–65`), and the supplier returns its attribute instance's value (`AttributeSupplier.java:27–29`). The getter source route therefore preserves the selected double attribute value for the ordinary registered player input, and both `Player.getSpeed` bodies cast that value to `float`. No additional attribute-registration dependency remains open for this vanilla-player slice.

**Integrity correction required for the report’s hash table:** Its 1.16 `LocalPlayer.java` hash cell is 65 hex characters (`EE20523ADF3DB82F3753FA43312AF170F34C4F4AA78570631C61BC8C0820DFB8B`), while the verified file SHA-256 is `EE20523ADFDB82F3753FA43312AF170F34C4F4AA78570631C61BC8C0820DFB8B`. Its 1.16 `Player.java` hash cell is 66 hex characters (`A849960D47BF00A2CDDFFB6636C78C518532A11967BA08829F19C5EBCEDE08681E`), while the verified file SHA-256 is `A849960D47BF00A2CDDFB6636C78C518532A11967BA08829F19C5EBCED08681E`. The other six source hash cells match the compared source files. This is a report-table transcription defect; the staged source content and bounded behavior conclusion were independently checked. The 1.16 `AttributeMap.java` SHA-256 is `53F0B1709E503095EF30069F2D265AC0F972B35A49AB75B46E6CE2B15D126B75`; `AttributeSupplier.java` SHA-256 is `F325BC87C0998B03AA5CAE948180A79154119485D3E1923F8BC0C8EE7324D8AE`.

Reviewed author artifact: `lab/1.15.2--1.16/author/report.md`, 7,343 bytes, SHA-256 `A8C95C1D1BC34D33563DD2F1BFF0FE14E128C690B451C4C9497DA6045B07AA76`.