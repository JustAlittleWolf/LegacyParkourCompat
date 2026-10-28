# Independent audit: water contact transform and local-player push gate

**Verdict: ACCEPT for the bounded contact-transform and local-player water-current gate correction.** The supplement is bound to the pinned raw JARs and Tiny maps, and the requested bytecode and source-manifest claims were independently checked. This adds the effective `PlayerEntity` dispatch result to the accepted raw audit; it does not broaden that audit's movement or runtime scope.

## Immutable inputs

- Supplement: `.native-source/20261028/180181-water-raw-bytecode/contact-semantics-supplement.md`, SHA-256 `3467a47679c3c10af2f3c9a63548b1a36e5c9d55ccd1695810f69f20012923c1` (matches its sidecar).
- Parent raw report: `.native-source/20261028/180181-water-raw-bytecode/REPORT.md`, SHA-256 `3c43839c0e9e91efe6a4ec60074e17c78aff0bed43bf7f234724704cbd2d5eca` (matches `REPORT.sha256`).
- Read-only source/cache root: `D:\Javastuff\LegacyParkourCompat`.

## Rebound exact IDs

`Get-FileHash -Algorithm SHA256` on the raw client JARs and cached Tiny payloads matches the supplement and parent report:

| Release | Raw classes | Client JAR SHA-256 | Feather Gen2 Tiny input | Tiny SHA-256 |
|---|---|---|---|---|
| 1.8 | `Box=brt`, `Entity=wv`, `World=aqu`, `PlayerEntity=ahd`, `PlayerAbilities=aha` | `ccccd0a92f76fbb24279691daad9b67f85d80471a37e59e0da76d7754982dfe8` | `feather-gen2-1.8+build.2.tiny` | `0db2fa38dc74a0981164e53fc73152291cea5153e705b5d06d001ede01b682e8` |
| 1.8.1 | `Box=brr`, `Entity=wx`, `World=aqr`, `PlayerEntity=aha`, `PlayerAbilities=agx` | `66a749ad95adcb929439155c0341309e89e437444867e108dcb36fd16493b0bd` | `feather-gen2-1.8.1+build.2.tiny` | `f26e3fea2c580cf9eb2d5c47cd32a071f72177ba5e2c208c39196e669c8a383d` |

The Tiny entries bind `Entity.checkWaterCollisions()Z` to `W` in both releases; `Entity.hasLiquidCollision()Z` to `aK` / `aL`; `World.applyLiquidDrag(Box,Material,Entity)Z` to `aqu.a(Lbrt;Lbof;Lwv;)Z` / `aqr.a(Lbrr;Lbod;Lwx;)Z`; and the PlayerAbilities field `b:Z` to `flying` (`aha` in 1.8, `agx` in 1.8.1). The PlayerEntity classes are `ahd` and `aha`, respectively.

## Reproduced bytecode and arithmetic

Read-only `javap -p -c -s` on each pinned `client.jar` confirms:

- `brt.b(DDD)` and `brr.b(DDD)` compute each minimum as `min - delta` and each maximum as `max + delta`. `brt.d(DDD)` and `brr.d(DDD)` compute each minimum as `min + delta` and each maximum as `max - delta`.
- `wv.W()Z` and `wx.W()Z` call the box method `b(0.0, -0.4000000059604645, 0.0)`, then `d(0.001, 0.001, 0.001)`, then the water `World.applyLiquidDrag` overload. Thus the first call contracts Y by 0.4 because the delta is negative; the second call contracts all axes by 0.001. Together the query bounds are `minX/Z + 0.001`, `maxX/Z - 0.001`, `minY + 0.4010000059604645`, and `maxY - 0.4010000059604645`. This is symmetric min/max narrowing, not a downward translation. Total vertical height reduction is `0.802000011920929`.
- In the raw `World.applyLiquidDrag` methods, after accumulated flow length is found positive, the bytecode uses `invokevirtual wv.aK:()Z` / `invokevirtual wx.aL:()Z` before normalizing flow and adding the current to velocity.
- `ahd.aK()Z` and `aha.aL()Z`, the PlayerEntity overrides, read the mapped `PlayerAbilities.flying` boolean and return its negation. The raw PlayerEntity class hierarchies extend entity subclasses; virtual invocation therefore selects this override for player receivers. Water-current velocity push is enabled for a non-flying player and suppressed while that player's flying ability is true. Entity's base `true` implementation alone is not the effective PlayerEntity result.

## 1.8.9 manifest binding

The primary checkout's `decompiled_minecraft/1.8.9/ornithe-feather.sources.sha256` hashes to `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004`, matching the accepted source-boundary audit. Its `PlayerEntity.java` entry is `e66cb294fc93118148a444bbafdf4dd57cbf66a23d69b1e8892cefccc690ab88`; hashing the actual file yields the same value. At lines 1617–1619 that source returns `!this.abilities.flying` from `hasLiquidCollision()`. The 1.8.9 provenance file hashes to `ba066fc8f1c7ceb9819d2106338d9e9f2c2b96eb5a8329eb40e171b1f9889185` and records the Feather mapping inputs. This confirms the supplement's specific manifest, source-file hash, and override claim.

## Conclusion

The corrected contact-box arithmetic and effective local-player flight suppression are supported for raw 1.8 and 1.8.1, and the override's behavior is independently confirmed in the provenance-bound 1.8.9 source. The supplement's bounded correction is accepted. It does not establish uninspected releases, other fluid paths, or runtime parity.

No source/cache files were changed. No network, source generation, class execution, build, test, game, or runtime was used.
