# 1.15.2 → 1.16 ordinary dry horizontal travel

## Assignment and verdict

Bounded source-only comparison: a local player, unmounted and nonsprinting, in the ordinary dry `LivingEntity.travel` path, restricted to horizontal input acceleration, movement-speed selection and horizontal drag/friction. The sampled block friction, yaw and `onGround` are dependency inputs. No independent behavior difference was found in this slice. Do not create a canonical difference finding from this comparison.

This endpoint result establishes only the comparison between exact releases 1.15.2 and 1.16; it does not establish an intermediate patch boundary or close the pair-wide tick inventory.

## Source identity and integrity

Both mappings are Mojmap. Ready markers are at the version roots (alongside `mojmap/`), not within the source directories:

- `decompiled_minecraft/1.15.2/mojmap.ready.json` SHA-256 `64A0E40B784D525CE50937C63E465FC4F564EFB1F7AF17F2BAC3C77A36907BE0` (assigned pin matched).
- `decompiled_minecraft/1.16/mojmap.ready.json` SHA-256 `5B6CB24F95E83B856C53F646D6477F2E46DAE2276737DEE56DD07C5BD745E4A2` (assigned pin matched).

For 1.15.2, the ready marker pins source manifest `mojmap.sources.sha256` at `CB7FD93F8730D8B2813857744F8572060535C7DE44FCEE86222ACD1CEB6F43E7`, client bytes at `4A73008A73F3824B7C711750A5A37556DF8614F193C0A531E292DAD159A73A7C`, and records version ID 1.15.2 / mapping `mojmap`. Its source publication has `mojmap.provenance.json` SHA-256 `D4802D35EE2927A44D753871F184C3255C060EB94457A5C65A8BC121A087954E`; that additional publication record identifies the exact run as version 1.15.2, Mojmap, Java 25.0.3, Gradle 9.7.1 and Vineflower 1.12.0, but does not itself contain source-file hashes. The ready marker's source-manifest pin supplies that link.

For 1.16, `mojmap.provenance.json` SHA-256 is `371D50521A183F4A3F42FAC2D18021F228E0A4E6B2E8A9838C2805391709F2A9`. It records requested/resolved release 1.16, Mojmap, client SHA-256 `00740EF4475D4CD0A1D7064F89602F759C45FF9AFE29DA9B0EE0B9037EBB2A71`, mapping artifact SHA-256 `53D46ABE149AADD367BE8789207C53A7CEE46BA8382E5BFD60247EB22984119D`, and the source publication inputs. Its ready marker pins `mojmap.sources.sha256` at `E3C23E94AC985C25DEC0C768AED17B443BA4823662CF1E152235C99756AE5D4A`.

Actual SHA-256 for each source file used matched its corresponding line in that version's source manifest:

| Source | 1.15.2 SHA-256 | 1.16 SHA-256 |
|---|---|---|
| `net/minecraft/client/player/LocalPlayer.java` | `3A9019BD7B860E251C23FD8D0CD70B7F5B38566D34470C4E29B1014EF689CCBD` | `EE20523ADF3DB82F3753FA43312AF170F34C4F4AA78570631C61BC8C0820DFB8B` |
| `net/minecraft/world/entity/Entity.java` | `191B3AD3E7348C9BAC1E703FFF896706D23A751BF15162AACF676F5F97C0A10E` | `27E3BB01B5D03C09161891E717C16477899768333082E81EBFAAC4E525616576` |
| `net/minecraft/world/entity/LivingEntity.java` | `46D243BB7E51F7B54404AA1D7D6E6B827682D0F5925D02847C4193306C4D5E54` | `B6EA2B9B037B79FC3EA6E1F6BCD2C4C1D89D4A96315968A8F247641F6C335F26` |
| `net/minecraft/world/entity/player/Player.java` | `1BA2724C22163862B8F7FDFDEA5A04A66E4DB26A119D7E5360BA024724A34793` | `A849960D47BF00A2CDDFFB6636C78C518532A11967BA08829F19C5EBCEDE08681E` |

All source references below are relative to `decompiled_minecraft/<version>/mojmap/`.

## Ordered comparison

1. **Local player dispatch and enclosing gates.** `client/player/LocalPlayer.java:851-869` (1.15.2) and `:888-906` (1.16) show the local movement method only using `getSpeed()` in its unrelated move-statistics/sound logic; neither file declares a `travel`, `getSpeed`, nor `moveRelative` override. `world/entity/player/Player.java:1448-1475` (1.15.2) and `:1380-1407` (1.16) dispatch ordinary non-flying player travel through `super.travel(vec3)`. Under the assigned dry, unmounted, nonsprinting player preconditions, the `LivingEntity.travel` control path is therefore the relevant acceleration and drag implementation. Player swim and flying branches are excluded.
2. **Ordinary dry branch and friction sampling.** `world/entity/LivingEntity.java:1885-1891` (1.15.2) samples the below-movement block friction `v`, selects `w = onGround ? v * 0.91F : 0.91F`, then calls `moveRelative(getFrictionInfluencedSpeed(v), vec3)`. In 1.16, `:2020-2025` selects the ordinary `else` after water, lava and fall-flying branches, samples `t`, selects `u = onGround ? t * 0.91F : 0.91F`, and passes the input and sampled coefficient to `handleRelativeFrictionAndCalculateMovement`. For the assigned ordinary dry path these formulas and float constants are equivalent; branch rearrangement is explained by the new preceding fluid gates, which do not admit the assigned dry case.
3. **Movement-speed selection.** `world/entity/LivingEntity.java:2006-2008` (1.15.2) and `:2104-2106` (1.16) both return `onGround ? getSpeed() * (0.21600002F / (friction * friction * friction)) : flyingSpeed`. This keeps the cubic float friction expression, multiplication/division order, ground selection and air speed selection unchanged. `Player.java:1493-1496` (1.15.2) obtains `SharedMonsterAttributes.MOVEMENT_SPEED`'s attribute value and casts it to `float`; `:1425-1428` (1.16) obtains `Attributes.MOVEMENT_SPEED` through `getAttributeValue` and casts the result to `float`. The attribute API/name changes across the release; the source comparison provides no evidence of changed movement-speed semantics for an identical attribute input. The air branch still selects the entity's `flyingSpeed` field in both releases.
4. **Relative acceleration arithmetic.** `world/entity/Entity.java:1063-1077` (1.15.2) and `:1073-1087` (1.16) are textually equivalent: calculate `vec3.lengthSqr()`, return `Vec3.ZERO` below `1.0E-7`, normalize only above squared length `1.0`, scale by the selected float speed, compute sine and cosine from yaw converted by `(float)(Math.PI / 180.0)`, then rotate horizontal components as `x*cos - z*sin` and `z*cos + x*sin`, retaining the scaled y component. `moveRelative` adds this vector to existing delta movement in both versions.
5. **Movement call and horizontal drag.** The 1.15.2 inline sequence calls `handleOnClimbable`, moves with current delta, then reads resulting delta and applies `x * w`, `z * w` in `LivingEntity.java:1889-1911`. The 1.16 helper `handleRelativeFrictionAndCalculateMovement` at `:2060-2069` contains the same relative acceleration, climb handling, move call and resulting-vector read; its caller at `:2024-2039` applies `x * u`, `z * u`. For the assigned non-climbing ordinary path, the helper extraction preserves the order and horizontal result. Ground drag remains sampled friction times `0.91F`; air drag remains `0.91F`. Vertical gravity/drag is outside this slice.

## Exclusions and remaining gate

This report excludes swimming, fluid branches, Elytra, abilities flight, sprint and sprint-sensitive speed modifiers, jumping, climbing/scaffolding, collisions/support/stepping, movement block/provider changes, direct block effects, camera and setters, vertical gravity/threshold behavior, and equipment/block-specific mechanics. It treats sampled friction, yaw and on-ground state as supplied dependencies and does not establish how they are produced. The pair-wide inventory, dependency closure outside this assigned slice and independent coverage audit remain open with the coordinator.
