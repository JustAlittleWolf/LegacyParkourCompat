# Independent source verdict: Lily Pad collision terminal

## Verdict

**Accepted as a bounded, hash-bound source difference at onset 1.9.0.** The vanilla Lily Pad remains collision-applicable to a normal player in both 1.8.9 and 1.9.x. Its collision box changes from a full-cell, 1/64-high plate in 1.8.9 to a centered 7/8-by-7/8 plate, 3/32 high, in 1.9.0, 1.9.1, 1.9.2, 1.9.3 and 1.9.4. For the stated one-pad, straight-down player move, this gives a concrete 5/64 terminal-foot-position difference. This does not close provider-wide collision inventory, tick parity, ordinary trajectory reachability, or runtime behavior.

The historical `inputs/historical-source-run.md` WORLD-17 disposition calls the shapes matching and the provider `compared-no-difference`. That is superseded for Lily Pad by `fixed-inherited-provider-lists/report.md`, which records the geometry difference, and by `lilypad-terminal-clip/report.md`, which establishes the terminal player clipping consequence. I found no separate older WORLD-17 findings file in the assigned `189-194-finish57` tree to hash-bind as a distinct artifact.

## Exact-source and mapping identity

Readonly source root: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\<version>\ornithe-feather`. All six trees identify Feather Gen 2 build 2 for the corresponding exact release. Their current provenance-record SHA-256 values are:

| Release | Mapping artifact | Provenance-record SHA-256 |
|---|---|---|
| 1.8.9 | `feather-gen2-1.8.9+build.2-mergedv2.jar` | `ba066fc8f1c7ceb9819d2106338d9e9f2c2b96eb5a8329eb40e171b1f9889185` |
| 1.9.0 | `feather-gen2-1.9+build.2-mergedv2.jar` | `8daacb7af8f210e716ee4b34c43b8a5d6f6b4b19831d2fd725cbe16ecf330a1a` |
| 1.9.1 | `feather-gen2-1.9.1+build.2-mergedv2.jar` | `e9fb0da6511597e2de953a143f9570b82243e5b9504603686e557f06271da64a` |
| 1.9.2 | `feather-gen2-1.9.2+build.2-mergedv2.jar` | `9928b467deb35c7e7f891b4fee1627a68feecdf233c2076909fd4834b0380510` |
| 1.9.3 | `feather-gen2-1.9.3+build.2-mergedv2.jar` | `43325cdd53ca34b88ee9af8464782b323a948b98c8a556c4761f1e5737b84c3a` |
| 1.9.4 | `feather-gen2-1.9.4+build.2-mergedv2.jar` | `5c913715bed763369ceb5fd171cab3e63c43298023078c3683f96c7ea8fed3cd` |

I recomputed the source-file hashes below from the current decompiled trees; they match the terminal report for its endpoint and 1.9.0 anchors. For intermediate releases, the direct Lily Pad provider body is stable across 1.9.0–1.9.4. The original derived mapped JARs for the intermediate source-map snapshots are not available, so this verdict binds the current source snapshots and their provenance records, not regenerated binaries.

## Provider, registration and player path

- In 1.8.9, `LilyPadBlock` constructor lines 15–19 set bounds with `f = 0.5F`, `g = 0.015625F`, yielding local `[0,0,0]–[1,1/64,1]`. `addCollisions` lines 23–26 calls the base provider exactly when `entity == null || !(entity instanceof BoatEntity)`. `getCollisionShape` translates those bounds by block position.
- In every 1.9.x source, `LilyPadBlock.SHAPE` is `new Box(0.0625, 0.0, 0.0625, 0.9375, 0.09375, 0.9375)`, i.e. local `[1/16,0,1/16]–[15/16,3/32,15/16]`. `addCollisions` adds that shape under `if (!(entity instanceof BoatEntity))`. `getShape` returns the same shape. A PlayerEntity is not a BoatEntity and is not excluded on either side. The 1.9.x boat condition is type-only; it does not exclude player entities.
- `Blocks.LILY_PAD` is registered from the `waterlily` registry key in the examined releases. The witness uses that unchanged vanilla block state at `(0,64,0)`, source water at `(0,63,0)`, and air in neighboring swept cells. The query intersects the pad in X/Z and visits its Y cell on both collision scans. There are no other queried collision shapes/entities and the player is inside a loaded chunk and world border.
- The paired normal player size is set to `(0.6F,1.8F)` (`PlayerEntity.resetPos` 1.8.9 line 417, 1.9.0 line 387, and corresponding intermediate/final methods). At feet position `(0.5,65.0,0.5)`, its box is X/Z `[0.2,0.8]`, minY `65.0`, height `1.8`. The chosen footprint overlaps both Lily Pad shapes.
- Both `Entity.move(0,-1,0)` paths enter collision handling with `noClip=false`; the swept shape is passed to `World.getCollisions`, its returned boxes are applied through `Box.intersectY`, and the resulting shape is moved by the clipped Y value. With X and Z requests zero, no horizontal clipping causes the later step branch to run. These call and clipping sequences are textually equivalent for the stated input in A and B. `Box.intersectY` has the same negative-limit gate and computes `obstacle.maxY - player.minY` when X/Z overlap.

## Recomputed witness

For the player's initial bottom at world Y `65` and a single pad at block Y `64`, negative `dy=-1` is clipped to `obstacle.maxY - 65`:

| Release | World-space pad top | Clipped `dy` | Resulting bottom |
|---|---:|---:|---:|
| 1.8.9 | `64 + 1/64` | `-63/64` | `64 + 1/64` |
| 1.9.0–1.9.4 | `64 + 3/32` | `-29/32` | `64 + 3/32` |

The difference is `5/64` (`0.078125`) for this exact equal-input local-player move. This is a source-derived conditional call witness, not a runtime recording or assertion that an ordinary vanilla tick produces this exact input.

## Hash-bound source anchors

`D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\<version>\ornithe-feather\net\minecraft\...`:

| File/member | Version(s) | SHA-256 |
|---|---|---|
| `block/LilyPadBlock.java` | 1.8.9 | `c5d5c92a927e5d86feb5fbf22102e7200037314cdba6c1207abbe52445580f35` |
| `block/LilyPadBlock.java` | 1.9, 1.9.1, 1.9.2 | `4e266ccc4f6f1530abc4f3fdb04acff50e171288b0735e5171d8ce34857af429` |
| `block/LilyPadBlock.java` | 1.9.3, 1.9.4 | `91004173b4287d244c671ffe565ca0935d0c04983d598027f37f26d9c111136d` |
| `entity/Entity.java` | 1.8.9 | `d4c10932cb5bb1067a5a58be4e1bb1b42bdde3b6fc893d88178cc07435a1696b` |
| `entity/Entity.java` | 1.9, 1.9.1, 1.9.2 | `74d3ac34d7e6355c8fdcdd777dcd95ba9ef4626718c8772c1b58d0e28419e42a` |
| `entity/Entity.java` | 1.9.3, 1.9.4 | `bcd7fa2206bf8d7271102f6da7fe2771f96dbe22ec79dab9f2101a3e29c17ef0` |
| `util/math/Box.java` | 1.8.9 | `8fc7bb57b1132afc7ae9539c30ff9c2585f0e36c2b0e59c60946cb1e3cee9eea` |
| `util/math/Box.java` | 1.9, 1.9.1, 1.9.2 | `cb0f30c72b7925fa0c0b133dffd051cf8260f4321f7c4a093c64ef5a5f500e62` |
| `util/math/Box.java` | 1.9.3, 1.9.4 | `055d57e1e555cc378fd1d7ea14d57036190ae335238175d284b969f439abe198` |
| `world/World.java` | 1.8.9 | `3c04f5b874fbb6692039164c882a3c47fe28c7eca864e91c9ac57798153888ee` |
| `world/World.java` | 1.9 | `d1e23db2e20c86eb37b964a0df6838f1422ade7b0c2fff9c869ff8345979aa98` |
| `world/World.java` | 1.9.1, 1.9.2 | `cdda70c97f0e4d6f6c454c8f6d3c0d47f0e0ae612e75062d8fe93ddd3963e6b0` |
| `world/World.java` | 1.9.3, 1.9.4 | `2fe063e0ec224eed9fc7d01b8f788c32035eed5fee5a9d98ea5e82296236e05a` |

The direct `addCollisions`, `getCollisionShape` / `getShape`, `Entity.move`, `World.getCollisions`, and `Box.intersectY` anchors were checked in intact files. The 1.9.0 report confirms the same source-map provenance and movement route at the onset release.
