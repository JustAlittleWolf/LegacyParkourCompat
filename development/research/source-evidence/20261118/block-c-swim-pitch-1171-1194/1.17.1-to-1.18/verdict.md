# Independent verdict: swim pitch vertical writer, 1.17.1 → 1.18

## Verdict

**Accepted: the source report is accurate for the bounded Player-level swim-pitch vertical-writer slice.** The exact paired sources show no difference in the `Player.travel` gate, pitch-derived vertical term, conditional delta-velocity write, or the inherited ordinary water-travel `move(SELF, getDeltaMovement())` consumer. This verdict does not claim full-pair coverage, an exact patch introduction point, runtime parity, or fluid/collision closure.

Reviewed report: `C:\Users\Wolfi\.codex\visualizations\2026\11\17\01a1eb7b-b9ff-7a20-8686-0295dde20f26\c-swim-pitch-vertical-1171-118-source\report.md`
Report SHA-256: `50ba29ac29e6f36467fbb1c22b565d85375dc5300ecbc692b558ea66544e52a1`.

## Frozen independent trace

The exact comparison is Mojmap 1.17.1 → Mojmap 1.18 (the initial 1.18.0 release). In both versions `Player.isSwimming()` is `!abilities.flying && !isSpectator() && super.isSwimming()` (`Player.java:1802–1804`). `Player.travel` enters the direct writer when `isSwimming() && !isPassenger()` (`Player.java:1409–1435`). It executes before `super.travel`:

1. Read `g = getLookAngle().y` into a `double`.
2. Evaluate `h = g < -0.2 ? 0.085 : 0.06` as a `double`, with a strict less-than threshold.
3. Write only if `g <= 0.0 || jumping || !level.getBlockState(new BlockPos(getX(), getY() + 1.0 - 0.1, getZ())).getFluidState().isEmpty()`.
4. On that gate, read current delta movement and set `old.add(0.0, (g - old.y) * h, 0.0)`.

The exact block-coordinate expression and evaluation order, the `BlockPos` read, fluid-empty predicate, and operation sequence match. The expression has no pitch clamp or interpolation: `Entity.getLookAngle()` passes raw `getXRot()` and `getYRot()` to `calculateViewVector` in both releases. The rotation getters return floats. The view-vector method first computes float radians (`pitch * (float)(Math.PI / 180.0)` and `-yaw * (float)(Math.PI / 180.0)`), then computes float trig values and returns Y as unary `-Mth.sin(pitchRadians)`. The `Vec3(double,double,double)` constructor widens that float Y to double; subsequent comparisons and `(g - old.y) * h` use double arithmetic.

`Mth.sin(float)` in both versions uses `SIN[(int)(angle * 10430.378F) & 65535]`; the 65,536-entry table is initialized from `(float)Math.sin(index * Math.PI * 2.0 / 65536.0)`. There is no interpolation. The table initialization and lookup are unchanged.

Because the active swimming predicate excludes ability-flying players, this active swimmer reaches `Player.travel`'s ordinary `else { super.travel(...) }` path, not its flying branch. `LivingEntity.travel` has the same leading AI/local-control gate and water branch (`isInWater() && isAffectedByFluids() && !canStandOnFluid(...)`); in that branch it calls `moveRelative(...)`, then `move(MoverType.SELF, getDeltaMovement())`, then adjusts velocity. The water branch and actual move call are identical at 1.17.1 `LivingEntity.java:2003–2040` and 1.18 `LivingEntity.java:2005–2042`. This trace stops at that move call; it does not assess later fluid adjustment or collision response.

## Exact source identity and integrity

Canonical read-only source root: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\`.
No source generation was performed. Ready markers are status `ready`, exact version IDs `1.17.1` and `1.18`, mapping `mojmap`; provenance records identify Mojmap and Vineflower 1.12.0 / Java 25.0.3. The exact ready/provenance/source-manifest byte hashes below were recomputed. Each relied Java file's current SHA-256 was also matched to its entry in the corresponding source manifest.

| Release / mapping | Ready marker SHA-256 | Provenance SHA-256 | Source-manifest SHA-256 | Client SHA-256 |
|---|---|---|---|---|
| `1.17.1` / Mojmap | `c7180dce94d7b57b53a964445b0c788c71f23ba3ba40eea593c11cc939185e9b` | `de197f77473955635e8c5730e113f227af2b8af9306b74e8a54a765eafc5a333` | `93270d229acfb751bf56daf1e7be26ce3dcff26b29e94aa157de621405a3463b` | `a49b4a56c5bbe15c9ed9fe53efa9a591f265a1f5ba7d6aa9739a58ea7a92b79d` |
| `1.18` / Mojmap | `4537edf0e88c88631215beb1c14f1d67f5a5d6d021c982c0af549b7b27856e5f` | `e642f075b25d1b6e74bad05edacd740421c70f5d8e0c2a38767523cf864124c5` | `a43c61223ddedbdd8ede4d2daf6a750a58cbba324b30c025429ee1030cfc0d78` | `381e090ef22584526113bcccf26ae38a310a22094d14c2d0dec72b71fdf65aaa` |

### Relied Java source SHA-256

| Relative source file | 1.17.1 | 1.18 |
|---|---|---|
| `net/minecraft/world/entity/player/Player.java` | `724bb298499dabe489dffd5ce7ec81c9773619a8d2c70044911eae4c9e8ff481` | `1fb9dfb9f700323d77f4196dbc3618d01a65e56438b9cce3ee38fa0b258bd7e2` |
| `net/minecraft/world/entity/Entity.java` | `ab28e1fba924771ec048140dfd293ee5a46a7dfe81f71a1a0b1aecc1927232de` | `69417675ae8e0a5bd88b7372baea1e73b1f5217154f39446c8ad5c92731437f1` |
| `net/minecraft/world/entity/LivingEntity.java` | `33fd081aadb2b6fdc9ebf487db6da5b38c54f4b8676572790ee2203690d15e6f` | `68fc9b15eb31690377afe92acfc783c86945fedec5739d28faacd96da4f9e0e7` |
| `net/minecraft/util/Mth.java` | `24515c4549e01e985017227dccf7159166a675b232be9135d5f896022a9cb113` | `10bd2dcc2b7084d0f9be24a3e54322510f1730e67fb84d35ec460c6953b3397f` |
| `net/minecraft/world/phys/Vec3.java` | `eb52a20224767daeb5ddc500552c673df2daf9cd6868d7e74356411e94ef402e` | `3695439a814bc61cf986d4f6b7164aa930176d196287a3eb2013b9f2e72acf2c` |

## Review boundary

I independently traced the claim against the exact canonical Mojmap files and checked the ready/provenance/manifests and relied byte hashes before opening the report. The report's no-difference conclusion and stated scope are supported. No implementation, Git operation, build, test, launch, network lookup, or source generation was performed.

— Codex, independent source auditor; 2026-11-18
