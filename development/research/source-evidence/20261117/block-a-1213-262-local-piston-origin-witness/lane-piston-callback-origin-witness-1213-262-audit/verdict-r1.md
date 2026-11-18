# Independent r1 rebind — piston callback origin witness

## Artifact bindings

| Role | Absolute path | SHA-256 |
|---|---|---|
| Prior immutable audit and source roster | C:\Users\Wolfi\.codex\visualizations\2026\11\17\01a1eb7b-ac27-7712-9259-3041950bc340\lane-piston-callback-origin-witness-1213-262-audit\audit.md | 4d1b0987ebec80a7332cc2713b8f2cba499a7cf234bd4b55c8d5aa47d8698a22 |
| Corrected r1 author witness | C:\Users\Wolfi\.codex\visualizations\2026\11\17\01a1eb7b-ac27-7712-9259-3041950bc340\lane-piston-callback-origin-source-1213-262\local-piston-origin-witness.corrected-r1.md | 601701427025e61e26d9dc500ef3eec00674db9ef6bcc0e9dce2389e2595fcca |

Both bindings were recomputed before this review. The r1 source roster is the one frozen in audit.md; this verdict does not alter it. Relevant current source SHAs were rechecked against that roster:

| Relative Java source | 1.21.3 Mojmap SHA-256 | 26.2 unobfuscated SHA-256 |
|---|---|---|
| net/minecraft/world/entity/Entity.java | a93719c302a0381a972af75ea360465e2e3551708dd07c34d4d40b7e5173c2b9 | 7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5 |
| net/minecraft/world/level/block/piston/PistonMovingBlockEntity.java | 426442149f89a28fc5973a52b8156aec7c18d76faf8b62eecae9f120b2fdba16 | 706bab1a13ba99bbd334aad853b99f72df58fce5b6b67d30d41f902b63dd8e06 |
| net/minecraft/world/level/block/WebBlock.java | ece9f8840481748d2d1542990b442cc9a97d670255d693b85f528fa618f48414 | 8afb61bea4d6fc8502971d0729c6a2f87f4f55d618ae6ed3251b95141f524af5 |
| net/minecraft/world/entity/player/Player.java | a803203e92aa4729d5f5c9b16085b6a43ce51d9907d309eb96736e9c7c1340de | 8decc71b9c780664578ddb14591db2a2f207c72c05b676edded6f8e964576531 |

## Verdict: corrected geometry accepted; callback-origin-only scope is narrower than the combined effect

The r1 coordinates correct the prior off-by-one: the west-facing extended piston base is at `(1,-2,0)` and its moving-head block entity is at destination `(0,-2,0)`, direction WEST, extending, progress zero. The long west cap at destination x `[0,0.25]` is translated east by one at progress zero, so its first `0.5`-progress WEST sweep is x `[0.5,1]`, y `[-2,-1]`, z `[0,1]`. At the corrected player's position x/z `0.5`, width `0.6`, its box x/z `[0.2,0.8]` overlaps that swept cap; y also overlaps. Query, normal push-reaction, and positive computed-motion conditions are met: `0.8 - 0.5 = 0.3`, then `+0.01` yields raw request `(-0.31,0,0)`. The moving-head destination/base arrangement is physically consistent with the progress translation. This accepts the conditional piston selection and cobweb reachability, not runtime observation or solid piston emulation.

### Velocity qualification

From rest in loaded clear air, the source recurrence is `vNext = (v - 0.08) * 0.98`; it approaches but does not equal its `-3.92` limit in finite ticks. Its discrete values are enough for the stated geometry: starting with `v0=0`, after 64 gravity/drag updates `v64=-2.844142104669577`; after 65, `v65=-2.8656592625761856`. A fall starting high enough can therefore enter the stated tick at foot y `1.05` with current velocity `v65`; the ordinary move ends at y `-1.8156592625761856`, with standing-box top `-0.01565926257618555`, strictly below web cell y `[0,1]`. This is a finite recurrence witness, not an inference that a continuous terminal-speed bound alone attains exactly `-2.85`. The source supports arbitrary prior fall height within the loaded world column assumed by the witness; no displacement cap appears in Entity.move.

### Multiplier state through the concrete sequence

Let `W = (0.25, 0.05F, 0.25)` and `0 = Vec3.ZERO`. The ordinary fall callback dispatches the cobweb on both releases before the piston block-entity pass. Since the LocalPlayer is not flying, `makeStuckInBlock` writes `W` and resets fall distance. Thus immediately before the piston `Entity.move`, both releases hold `W`.

| Sequence point | 1.21.3 Mojmap | 26.2 unobfuscated |
|---|---|---|
| Immediately before piston Entity.move | W, from the ordinary fall's WebBlock callback | W, from the ordinary fall's WebBlock callback |
| During piston Entity.move for raw WEST request of `0.31` | Multiplies x request by `0.25`: actual unobstructed x displacement `-0.0775`; then clears stuck multiplier to 0 and zeroes delta movement | Explicitly skips stuck-multiplier scaling for `MoverType.PISTON`: unobstructed x displacement `-0.31`; then clears stuck multiplier to 0 and zeroes delta movement |
| Immediately after Entity.move and before piston block callback | 0 | 0 |
| After piston block callback | `applyEffectsFromBlocks()` uses old tick position to post-piston position; the web cell is on that span, so `makeStuckInBlock` writes 0 → W | Explicit overload uses immediate pre-piston to post-piston span; this horizontal span has target box top below 0 and skips the web, so multiplier remains 0 |

The old callback therefore writes after an actual reset; it is not merely observing or repeating over a retained W. Its post-callback value is numerically the same W that the ordinary fall wrote earlier, but the intervening piston move cleared that field. In this concrete sequence old finishes with W and target finishes with 0. The callback-origin difference is source- and geometry-backed as a contributor to that combined post-callback state. However, the releases also produce different x endpoints because their piston-move multiplier gates differ (`0.4225` versus `0.19` from starting x `0.5`), and their block-traversal implementations differ. Do not characterize the result as an isolated origin-only effect at an identical cross-version endpoint. State the actual combined sequence: old-origin sweep restores W after the move; target short sweep leaves the move-cleared 0.

## Bounded source references

- Tick order and old-position snapshot: the frozen source roster in audit.md, Minecraft/ClientLevel rows and refs; 1.21.3 `ClientLevel.tickEntities` invokes entity tick before block entities, while 26.2 `Minecraft.tick` calls those passes in that order.
- Ordinary fall writer: the same frozen roster, LivingEntity/Player/WebBlock/Entity rows and refs; LocalPlayer reaches its gated travel/effects call before piston ticking, WebBlock calls `makeStuckInBlock`, and Player applies it when not flying.
- Old piston path: 1.21.3 `PistonMovingBlockEntity.java:110–186` and `Entity.java:619–652, 736–767, 1034–1080` in the frozen roster. Its no-argument piston callback supplies oldPosition to current position.
- New piston path: 26.2 `PistonMovingBlockEntity.java:120–198` and `Entity.java:711–755, 903–947, 1279–1404` in the frozen roster. The pre-piston sample, movement-record removal, PISTON multiplier skip/clear, and explicit short callback are all in these bodies.
- Shape/motion: 1.21.3 and 26.2 `PistonHeadBlock.java`, `PistonMath.java`, and `PistonMovingBlockEntity.java` rows in the frozen roster. At corrected head destination x=0, the translated west cap sweep overlaps the player's box and produces the positive request described above.

The prior acceptance of `oldPosition() != prePistonPosition` as a reachable conditional callback-input difference stands. The corrected r1 closes the piston-coordinate defect and proves the reset → old callback rewrite sequence. The source does not establish runtime traces, an intermediate-version introduction boundary, or a standalone origin-only delta at a common endpoint.