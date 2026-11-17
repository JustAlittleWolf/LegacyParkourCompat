# Tiny collided movement position write (r4)

**Exact source predicate difference only; source-derived player witness refined after r2 audit.** Pair 1.21.1→1.21.3; the first inspected changed form is 1.21.2. This r4 binds only to the evidence below; no full-pair completion or runtime claim.

1.21.1 Mojmap: `decompiled_minecraft/1.21.1/mojmap/net/minecraft/world/entity/Entity.java`, `Entity.move(MoverType, Vec3)`, lines 598–630. 1.21.2/1.21.3 Mojmap: `decompiled_minecraft/1.21.2/mojmap/net/minecraft/world/entity/Entity.java` / 1.21.3 equivalent, lines 619–652; 1.21.2 Entity.java is byte-identical to 1.21.3.

Difference: After edge backoff and collision resolution, the 1.21.1 condition for both the fall-distance clip and the position/box write is only `collided.lengthSqr() > 1.0E-7`. Starting in 1.21.2 it is `collided.lengthSqr() > 1.0E-7 || requested.lengthSqr() - collided.lengthSqr() < 1.0E-7`. For an unchanged nonzero request under the threshold, the old gate is false and the new second term is true. The calculations stay `double`; comparisons are strict; there is no cast change. With a sub-threshold move, `fallDistance` zero, and identical requested/collided vectors, the old release skips `setPos`, while the new release executes it. `setPos` delegates to `setPosRaw`, whose exact coordinate inequality updates position and rebuilds the box for a representable nonzero displacement.

Ordinary player route: client `LocalPlayer.aiStep` reaches `Player.travel`/`LivingEntity.travel` for an on-foot, non-flying player. `LivingEntity.handleRelativeFrictionAndCalculateMovement` calls `moveRelative`, then `handleOnClimbable`, then `move(MoverType.SELF, getDeltaMovement())`. Holding shift suppresses downward ladder slide; `handleOnClimbable` resets fall distance and passes the resulting movement into that SELF call. Exact hashes and source paths are listed below.

## Bounded source-derived witness with tag-free trapdoor fallback and edge/support precondition

Choose a non-spectator, non-flying player with shift held, zero X/Y velocity, yaw zero, Swift Sneak III leggings, and forward/back inputs `--++--+--++`. Place the player at center `(0.5, 1.0, 0.5)`, so `blockPosition()` is `(0,1,0)`. Put an open `TrapDoorBlock` with `FACING=NORTH` at `(0,1,0)` and a `Blocks.LADDER` block with `FACING=NORTH` directly below at `(0,0,0)`. The player's feet block remains the trapdoor cell while center Z stays in block 0. `LivingEntity.onClimbable()` reads the in-block trapdoor, then its `trapdoorUsableAsLadder` fallback requires that it is open and the block below is a ladder with matching facing. This route does not depend on membership in `BlockTags.CLIMBABLE`.

At every movement call, require the player remain off-ground, shift-held, nonflying and `MoverType.SELF`. Ladder handling is reached by the open-trapdoor fallback, resets fall distance to 0 and suppresses negative Y motion while shift is held; the stated Y request remains zero. The source-order movement requests sum to approximately `-0.1621212211` Z, so center Z ends near `0.3378787789`, remains in block Z 0 and leaves `blockPosition()` at the open trapdoor. X stays fixed.

Use north-facing trapdoor and ladder shapes and support them with a solid backing column at z+1 spanning y=0 and y=1. At each endpoint, open north-trapdoor (`TrapDoorBlock.java` .1 lines 46, 71–86; .3 lines 50, 75–90) and north-ladder (`LadderBlock.java` .1 lines 30, 43–64; .3 lines 31, 44–65) collision shapes occupy z=13/16..1; `EntityType.PLAYER` dimensions are 0.6F wide (.1 line 808; .3 line 1058), so the player centered at z=0.5 has horizontal bounds z=0.2..0.8, outside those shapes. `BlockBehaviour.Properties` defaults `hasCollision=true` (.1 line 939; .3 line 946), and vanilla trapdoor registration uses ordinary properties (`Blocks.java` .1 lines 2411–2420; .3 lines 2104–2113). The backing wall starts at z=1, also outside the player's X/Z box. Keep the full vertical support probe and each horizontal swept player AABB free of every other block shape, entity collision and world-border intersection. The probe descends into ladder/support-block rows, but their collision shapes remain beyond z=0.8. Because the trapdoor is open with the same-facing ladder below, the `onClimbable()` predicate remains true through the entire sequence without a tag-resource assumption.

These are explicit gates, not an inference from `onGround == false`. `Player.maybeBackOffFromEdge` can run because shift makes `isStayingOnGroundSurface()` true and the move type is SELF. But `Player.isAboveGround(step)` evaluates false under the stated support-probe condition: `onGround` is false and `canFallAtLeast(0,0,step)` returns true because its complete downward probe has no collision. Thus `maybeBackOffFromEdge` returns the request unchanged before the support-step loop. The collision-free horizontal swept AABBs make `Entity.collide` preserve each request.

The input arithmetic preserves source float operations and order. Swift Sneak III gives the sneaking-speed value `0.3F + 3×0.15F = 0.75F`; the input factor is `0.98F`; off-ground nonsprinting speed is `0.02F`; after each move, air friction stores `requestZ × 0.91F`. At each tick start, source code zeros stored components with absolute value `<0.003`. Detailed values are in `source-check-r4.md`. Before tick 11, stored Z is `-0.014819883818181116`, above the clamp; the SELF request is `-0.00011988386064943138`, nonzero with square `1.4372140044212282E-8`. In the stated collision-free geometry, collided equals requested. The old collided-only gate is false; the new requested-minus-collided term is `0 < 1.0E-7`, so only 1.21.2+ writes position and rebuilds the box. This is one explicit source-level scenario, not runtime replay or a general claim that all tiny movement is affected.

## Tag/resource evidence and tag-free fallback

The exact 1.21.1 and 1.21.3 source publications include Java files but not packed resource payloads. Their artifact manifests pin `client.jar`, but those archives are absent at the provenance-recorded cache path `build/movement-campaign-2026-10-07/artifacts/<version>/client.jar`; the secondary `.minecraft-decompile-cache` has a client archive for 1.21.2 but none for .1 or .3, and the prepared source-resource packet contains only 26.2. The endpoint `data/minecraft/tags/block/climbable.json` payload therefore is not directly hashed. Both exact `VanillaBlockTagsProvider.addTags` methods nonetheless source-define default membership by adding `Blocks.LADDER` to `BlockTags.CLIMBABLE` (lines 738–749). `BlockTags.CLIMBABLE` uses the default namespace `minecraft`. This establishes the vanilla default generated tag definition, not a packaged JSON payload or the effect of custom data-pack overlays.

The r4 witness does not depend on that tag membership. `LivingEntity.onClimbable` (1.21.1 lines 1517–1538; 1.21.3 lines 1561–1582) reads the state at `blockPosition()` and accepts a `TrapDoorBlock` through `trapdoorUsableAsLadder` when the trapdoor is open and the block below is `Blocks.LADDER` with the same `FACING`. The player occupies the trapdoor cell; the ladder is directly below. This exact fallback verifies climbability through the block identity and matching property rather than `BlockTags.CLIMBABLE`.

Exact hash-bound geometry and support-query evidence used by the witness:

- `net/minecraft/world/level/block/TrapDoorBlock.java`: 1.21.1 `91DACBB5C9B33B5642266E3B9D3A402153237131A58554511EEB93699EFF3D6F`; 1.21.3 `F72063663C3791348F9A4496E56A94D900E52D356BF54AC595A5C85B113EE0C5`.
- `net/minecraft/world/level/block/state/BlockBehaviour.java`: 1.21.1 `35420EE4FCA120CCF1D1542A6B1746FCDD1E9DAFD125CF0B8193F9B8B406B292`; 1.21.3 `4D92417129EB6DEF084A67D249D2FF8C7A30A4767FA46E63A045667EA70D5133`.
- `net/minecraft/world/level/block/Blocks.java`: 1.21.1 `538AC50C164484CA4C2367A2A6C07CF2FAEDCAC0C4D5117D8817D36C2D1BDE6D`; 1.21.3 `28A66DA36CE98800226EC6D8E5FB4904FC7B137322A31F6D7EC822450374E62A`.
- `net/minecraft/world/level/block/LadderBlock.java`: 1.21.1 `AB51BD41E6FC33E605177F7131DE91EC7E383F80C11C279C72AE146E648D4167`; 1.21.3 `2A994DA48241C74ED3FC945CB073D7135B62F212E0AFA417A2797D9E6A1BEEB5`.
- `net/minecraft/world/entity/EntityType.java` (PLAYER dimensions): 1.21.1 `23AA8FF6462783B83A09F058381A0EBEB8AFB066898806535E850E1F81CA1D0E`; 1.21.3 `433A855503EFF559BF0D9C009080B4FF1DEC73C497A455A1D22D6812AB9E7656`.
- `net/minecraft/world/level/CollisionGetter.java`: 1.21.1 `F21DAA1ED0365CC16A2638373D702FBAEB4242AD12A80A41105CF43C711E6D95`; 1.21.3 `42301653B90EEA59CAF787A92699BD707D2454155B7AA19E4FF2DE57BEEF774F`.
- `net/minecraft/data/tags/VanillaBlockTagsProvider.java`: 1.21.1 `819D133E6EE8F82485B3D84FCFCFA48E4EE2824431379B93DE48C7DDDC7B32F0`; 1.21.3 `89D578B1092BA26541C71A0B19D80B1DEAAE06D982D5E6AB483615080E7B9D55`.
- `net/minecraft/tags/BlockTags.java`: 1.21.1 `FA4107A372643DDC1C7FC1AEC2A3527CC27565A6B217E3E5D1C383CFB9B51DE4`; 1.21.3 `DCB23A50F1191841E6239D5209D87CE153E691DE78AE838EFA325C7373CFAFC1`.

All listed hashes match the corresponding endpoint `mojmap.sources.sha256` ledgers. The previously cited `LivingEntity.java`, `Entity.java`, and `Player.java` hashes bind the predicate, movement response, and support probe methods.
## Source identities and hashes
1.21.1 ready marker SHA-256 `988B38AC59491F9430D5BD1968DB0FDAACB777A63FEF745CA3E20342237A0310`; Mojmap source ledger SHA-256 `900F956E00F6FC1300BB3D689EA49DF2B1A57BCAA54617344EF456D95D47CB48`; provenance SHA-256 `6F84A596EA944A1D40C79B53EAD9D2CDD2B3C726D710A977758D15B5578CE7A9`.

1.21.2 uses in-tree `mojmap/.decompile-provenance.json`, SHA-256 `00F399407DC447737EA83E3E46562418F8C712F66E3F51CB85D46AFA2ECAC575`; its exact Java source map matches the cited `Entity.java`, `Player.java` and `LivingEntity.java` bytes. There is no separate root ready marker/source ledger for this publication.

1.21.3 ready marker SHA-256 `72385AA99D249A56D742B9C07823C1155C74C39DDA1FBC6EACC12C980CD12E02`; Mojmap source ledger SHA-256 `D673BB5464853E3A2A92ED9B1FFE1789D4E62C097BB884F67C336FCBB3B178CE`; provenance SHA-256 `24093437C0CD41D807AB0829068EF97828B65BBB95B38DFDC4F706883A8483D7`.

Cited source file SHA-256 (verified against each endpoint ledger or 1.21.2 provenance map):

- `net/minecraft/world/entity/Entity.java`: 1.21.1 `B81905C7879E2CC5C5063A41D865C4164AD919F156306705BE791D1017B99850`; 1.21.2/1.21.3 `A93719C302A0381A972AF75EA360465E2E3551708DD07C34D4D40B7E5173C2B9`.
- `net/minecraft/world/entity/player/Player.java`: 1.21.1 `ED32B88C3C7C8418B83DB41823520F2B6B0B49A98610306EF26E9681DC925C71`; 1.21.2/1.21.3 `A803203E92AA4729D5F5C9B16085B6A43CE51D9907D309EB96736E9C7C1340DE`.
- `net/minecraft/world/entity/LivingEntity.java`: 1.21.1 `324A3EEE8496CAAB57CFAF5101EF576F1AE3C60C40E3857E96F35F3AF9A3A0D8`; 1.21.2/1.21.3 `087390495F4FDFD14B9E12230E7AEA4FDC50913BB4D1D91119E72892881CFC52`.
- `net/minecraft/client/player/LocalPlayer.java`: 1.21.1 `C555E68AC3C63AB9B4F9A9E31933E263B96350A2BC599D11A0DE5928BC24B583`; 1.21.2/1.21.3 `FBD40F1F47ADFA66DDA9B15188E5DCE82AF3E8E8D7C3DD0543E602A354AD3FE0`.
- `net/minecraft/client/player/KeyboardInput.java`: 1.21.1 `A8064906872955A3520398AB5B2A326552D424F41887D1294AA6A038E2623FF0`; 1.21.2/1.21.3 `2A0994E8A6A2FDF9E76480227216DB4A4252734FCA8CA0AEDD25DDE77BE75BA6`.
- `net/minecraft/world/entity/ai/attributes/Attributes.java`: 1.21.1 `A9A19F556BB77FC218B5B2F4831EB6B285F4398C24FA2A3519009CA8DAD2DB7C`; 1.21.2/1.21.3 `14BCC5821D0F67F41FDC3E512556181823CFF5C43F84FC1007E51D25F413A3EA`.
- `net/minecraft/world/item/enchantment/Enchantments.java`: 1.21.1 `7848A2CD677AA0700434F85DE37D8A0C47597741D2D1F26C8DC270F73D65B266`; 1.21.2/1.21.3 `FBE415832202D8AD96A472EFF2E7F7F401EE6C397311790E04493A9169E31BDA`.

## Remaining coverage

This is a bounded `Entity.move` difference and one explicit source-derived player witness only. Full pair/tick inventory, remaining movement and collision callbacks, external writers, resource-pack overrides beyond the cited default tag definition, and independent full-pair coverage remain open. No runtime replay or general support-boundary result is claimed.









