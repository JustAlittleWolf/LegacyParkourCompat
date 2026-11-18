# Independent source audit: 1.9.2 -> 1.9.3

## Verdict identity

- Frozen claim audited: `c-early-192-193-sprint-flight-input/report.md`, SHA-256 `664c031d60bc371f9bc857f3a73fa2f639af8171c112ea0cd1a91e9440438085`.
- Pair: Minecraft 1.9.2 to 1.9.3; canonical local source sets `decompiled_minecraft/{1.9.2,1.9.3}/ornithe-feather`.
- Mapping/source identity: Ornithe Feather Gen2 mergedv2 build.2. Provenance pipeline on both sides is `lpc-decompile-v1;threads=1;generic=true;ascii=true;synthetic=remove;indent=4;inner-warnings=false;runtime=false;prefix=net/minecraft,com/mojang;remap=ignoreConflicts,renameInvalidLocals,rebuildSourceFilenames,fixPackageAccess`; runtime is Eclipse Adoptium 25.0.3+9-LTS on Windows 11 amd64.
- Provenance manifest SHA-256: 1.9.2 `9928b467deb35c7e7f891b4fee1627a68feecdf233c2076909fd4834b0380510`; 1.9.3 `43325cdd53ca34b88ee9af8464782b323a948b98c8a556c4761f1e5737b84c3a`.
- Manifest-pinned mapping artifact SHA-256: 1.9.2 `e2b98ececbff8fa0bd0c89283bbb32b7f9f9dcd4b89fa3f137666fa3ac0b6859`; 1.9.3 `fc5defa1546cf87888a11783e2b5d9b48b47c6c022f48de4e55add978ab35da3`. Feather metadata SHA-256: 1.9.2 `5bad5e67d0870fec69bbab8fcae8d0525a274668bb472eb09d1552359d08edd1`; 1.9.3 `353c13519171cbba1a96872aebcb8b38248d5d5963b4c4613c23337adf028985`.
- Integrity check: I recomputed all manifest-listed source SHA-256 hashes on both sides; each tree has 1,819 listed Java sources, with zero missing files and zero mismatches. Each source used below also matches its manifest entry.

## Operation verdicts

### Local sprint timer and sprint gates — no difference

`LocalClientPlayerEntity.java`, `mobTick()` (1.9.2 lines 601–757; 1.9.3 lines 604–760) and `setSprinting(boolean)` (403–406; 406–409) have text-identical method bodies. The relevant state has no explicit initializer on either side. Per tick, `sprintTimer` increments first; positive `doubleTapSprintTime` decrements next. The old jumping, sneaking and forward-threshold values are sampled before `input.tick()`. The double-tap arm gate, `7`-tick value, food-level `> 6.0F || abilities.canFly` eligibility, item-use and blindness gates, held sprint-key path, and sprint-stop conditions are unchanged. Both requested sprint states call the superclass setter and then zero `sprintTimer`. Search of this class finds no additional `sprintTimer` read/write that changes this lifecycle. Verdict: the frozen no-difference claim is independently supported for the ordinary local timer tick/arm/terminal slice; it is not a pair-wide coverage verdict.

### Ability-flight fall-distance reset — no difference

`PlayerEntity.java`, `moveRelative(float,float)` (1.9.2 lines 1366–1384; 1.9.3 lines 1372–1390), is text-identical. The reset is gated by `abilities.flying && !isRiding()`; that branch temporarily changes air speed, delegates to `super.moveRelative`, restores vertical velocity as the snapshot multiplied by `0.6`, restores `speedInAir`, writes `fallDistance = 0.0F`, and clears flag 7, in that order. Coordinate-delta movement stats follow the branch. The ordinary branch does not perform this reset. The branch does not directly test `canFly`, spectator state, or the old fall distance. `LivingEntity.moveRelative(float,float)` is also text-identical across the full reported ranges (1293–1460; 1302–1469), and its reachable travel sequence applies the same input scaling, calls `flyingTick()`, then `moveRelative(...)` on both releases. Verdict: no change to the reset predicate, write timing, or adjacent movement work in this bounded operation.

### Sneak-flight input scaling — no difference

`LocalClientPlayerEntity.java`, the flying input block in `mobTick()` (1.9.2 lines 712–722; 1.9.3 lines 715–725), is text-identical. It runs only under `abilities.flying && isCamera()`. With sneaking, each float movement axis is divided by the double literal `0.3` and cast back to `float`; vertical velocity is then reduced by `getFlySpeed() * 3.0F`. Jumping is processed after that and adds the same float term. The `Input.tick()` bodies (line 13) and full `KeyboardInput.java` bodies are identical; keyboard sneak scaling multiplies each float axis by double literal `0.3` and casts to `float`. The same reachable `LivingEntity.travel` sequence calls `flyingTick()` before `moveRelative(sidewaysSpeed, forwardSpeed)` in both versions. Verdict: no change to the flight gate, axis scaling, cast, or vertical operation order.

## Relevant source hashes

| Relative source path | 1.9.2 SHA-256 | 1.9.3 SHA-256 |
| --- | --- | --- |
| `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `de9ad48bcfb2705ef7a9a63e9401c1742aead183e1e9c2d13f8fe40cca0139eb` | `8aaf711948b7602c2e6c015a37e36ed06073d39727d999d80480b4910b704f5d` |
| `net/minecraft/client/entity/living/player/Input.java` | `206dea2ff5977599596c907c54f36300c4e72d07d6778d82e8850f57bb0fb009` | same |
| `net/minecraft/client/entity/living/player/KeyboardInput.java` | `7be11425906be051c83e275f359816546e4677b16d212156380e8d2e9258654a` | same |
| `net/minecraft/entity/living/player/PlayerEntity.java` | `1fc5107b9b7f5711105d521a28829f2466fe9f297e832f4d209e6ec422111aac` | `ef96c8a00443f724eadad05a6a4dcc8a8a2e23cd4c3390b9e0f1f78c9ff4d1af` |
| `net/minecraft/entity/living/LivingEntity.java` | `d8824f670037a88753c428c27213989fc5173b1d7c638f498f73395c145b6fd9` | `bbb7703f18fd5da05c4e4a43a77ea644b388e63c01d34166d308ea52054be4e5` |

## Audit limit

This independently reviews only the three assigned operation slices against the frozen report and pinned local sources. It does not establish complete 1.9.2–1.9.3 movement-pair coverage, implementation applicability, or runtime parity.