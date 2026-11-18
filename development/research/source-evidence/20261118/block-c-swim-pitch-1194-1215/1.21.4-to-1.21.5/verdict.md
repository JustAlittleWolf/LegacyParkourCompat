# Independent source audit: 1.21.4 to 1.21.5 swim-pitch Y adjustment

## Verdict

**ACCEPT — no source difference in the assigned operation.** The immutable source claim/report reviewed is `c-swim-pitch-vertical-1214-1215-source/report.md`, SHA-256 `d7fa78d6e0667f40b51d0a8dc5e94f75c2b8149dc0202b09796831034aa18273`. This audit covers only the nonpassenger `Player.travel` pitch-driven Y adjustment, its raw pitch/look-vector dependencies and float sine-table math, plus the adjacent inherited `LivingEntity.travel` route needed to characterize an ordinary locally controlled water case. It does not freeze complete pair coverage or establish runtime parity.

## Independent trace

In both exact Mojmap source trees, `Player.travel` first delegates directly to `super.travel` if `isPassenger()`; otherwise, if `isSwimming()`, it samples `getLookAngle().y` as `double`, selects `0.085` when that value is `< -0.2` and `0.06` otherwise, then writes Y only when the value is `<= 0.0`, `jumping` is true, or the fluid sample at `BlockPos.containing(getX(), getY() + 1.0 - 0.1, getZ())` is nonempty. The write is `getDeltaMovement().add(0.0, (lookY - currentY) * coefficient, 0.0)` followed by `setDeltaMovement`. This executes before the parent's travel. The operation, branch order, constants, double math, sample position and write are the same in both releases.

The dependency trace is also unchanged. `Entity.getLookAngle()` calls `calculateViewVector(getXRot(), getYRot())`; the live pitch getter returns the stored `float xRot`. The setter rejects non-finite input and stores `Math.clamp(input % 360.0F, -90.0F, 90.0F)`. `calculateViewVector` computes pitch radians as `float pitch * (float)(Math.PI / 180.0)`, does the equivalent float operation for negative yaw, calls `Mth.cos`/`Mth.sin`, and returns Y as `-sin(pitchRadians)` in a `Vec3`. In both releases, `Mth.SIN` is a 65,536-element `float[]`, initialized in index order by casting `Math.sin(i * Math.PI * 2.0 / 65536.0)` to `float`; `sin(float)` indexes `(int)(value * 10430.378F) & 65535`, and `cos(float)` indexes `(int)(value * 10430.378F + 16384.0F) & 65535`. The `Mth.java` files are byte-identical. No changed raw-angle, float cast, lookup initialization or truncation/mask behavior explains a delta.

The adjacent inherited route has one real difference: 1.21.4 wraps `LivingEntity.travel`'s fluid/fall-flying/air dispatch in `isControlledByLocalInstance()`; 1.21.5 removes that wrapper. Under an ordinary locally controlled player case, that old gate passes. For a nonpassenger, non-flying player swimming in water, the same Y adjustment runs first and the same parent fluid branch follows when the entity is in water or lava, affected by fluids, and cannot stand on that fluid. This parent gate change is adjacent route context, not a change to the owned direct pitch adjustment. The report correctly keeps the claims separate.

## Source and readiness identity checked

All paths below are relative to `decompiled_minecraft/<version>/mojmap/`. Source references independently checked:

- 1.21.4: `Player.java:1448-1469`; `Entity.java:1640-1647, 2220-2222, 3509-3518`; `Mth.java:32-36, 48-54`; `LivingEntity.java:2189-2199`.
- 1.21.5: `Player.java:1426-1447`; `Entity.java:1669-1676, 2188-2190, 3509-3518`; `Mth.java:32-36, 48-54`; `LivingEntity.java:2213-2222`.

Both local `mojmap.ready.json` markers say `ready`, identify the exact version and `mojmap`, and their `sourceManifestSha256` values match the adjacent source manifests. Marker source counts are 5,744 (1.21.4) and 5,921 (1.21.5). The provenance manifests identify Vineflower 1.12.0, Java 25.0.3+9-LTS, Gradle 9.7.1, Mapping IO 0.9.1, Tiny Remapper 0.14.1, ASM 9.10.1 and Gson 2.14.0; the batch command names exact versions `1.21.1,1.21.3,1.21.4,1.21.5`, Mojmap and 4G heap. No generation was run for this audit.

| Version | Client SHA-256 | Source manifest SHA-256 | Player.java | Entity.java | LivingEntity.java | Mth.java |
|---|---|---|---|---|---|---|
| 1.21.4 | `c17c450c6e72cc51297daa57ce38f800aa01cf022b743daa21a0512d326d894e` | `f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0` | `c45f41b9784ce50a88a498e84a13edcef0a23e175233d726ed7f90940f19ebc1` | `05f18ef2ec0413fc010230407c812a11553eb5123b68d21b5d7b2c0c175698ad` | `e62ce650af0a5ac97e4d0a2ba7cad4ba68a8d098525609f8b5b6976a8ab0ae30` | `98dfb57e239348cf71bf00695b84299232bd5e0cfe206ee8c23a69b72f1099da` |
| 1.21.5 | `522672ad20b460c02c2e39b6c5035ef6a849af28eb11ab2ac7eb293d395a8c11` | `365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9` | `8fc187f33999db9dfc49251e95e92a17645a50adab16ca0f93f4948209f62036` | `fc177733e9cc4b2d5cf2562e5529d0f4e0b9c3690f081fbd58aa9119eb173dc4` | `a8aed863d4fdc515c751dd2878a8bb9c13179228cb8dbb50edf1d19cd5404271` | `98dfb57e239348cf71bf00695b84299232bd5e0cfe206ee8c23a69b72f1099da` |

Each cited source-file digest was recomputed and matched its corresponding entry in the exact source manifest. The source manifest digests also matched their readiness markers; the two `Mth.java` content digests match each other.

## Signed audit identity

Independent verdict: **accepted, no direct-operation delta**. Bound to the unchanged report SHA-256 above and the exact ready source manifests and cited file SHA-256 values in this record. Scope does not include adjacent parent-dispatch implementation as an owned finding, other Player travel operations, full-pair inventory closure, or gameplay verification.