# Independent 1.19 to 1.19.1 source audit: swim pitch vertical adjustment

## Pre-report source trace freeze

I independently traced the assigned own-player, swimming, pitch-derived vertical velocity adjustment from the canonical exact-release Mojmap source trees. This trace and the listed immutable evidence identities were frozen before opening the candidate source report.

### Canonical source publication identity

Both canonical trees are marked `status: ready`, with exact IDs `1.19` and `1.19.1`, mapping `mojmap`. Ready markers and provenance manifests agree on source-manifest, artifact-manifest, diagnostics and input client digests. The exact runs name `decompileMinecraft --versions=1.19 --mappings=mojmap` and `--versions=1.19.1 --mappings=mojmap`, respectively; both are Vineflower 1.12.0 output from Java 25.0.3+9-LTS. The 1.19 Mojmap source inventory is 4,430 files / 21,697,172 bytes; 1.19.1 is 4,480 files / 21,921,512 bytes. No source tree was generated or modified for this audit.

Canonical metadata byte identities (SHA-256):

| Version | Ready marker | Provenance manifest | Source-hash manifest | Artifact manifest | Diagnostics |
|---|---|---|---|---|---|
| 1.19 | `B00A68DB8600EC8CDC3917357AFF59EB0537BE6F382E621E5ED3EEB900898B88` | `833CF50D6EAB0F24475DCC11085C1414C85EEE0E708FF17C59BC8DCD2AE9C35A` | `78BF3F9D5B4B5437240DD0707FA7A9FF56E6D1E267BD8EDC13ADC8313B535E26` | `EEE4A52F029446E075FF10EF761CEA9A621B40DF6E66240B6323D1596CA60EBF` | `E666EB5D7BDBEA46599F85B5AE720077338255DF0B9D0CC5B63FE6F292FAA4C` |
| 1.19.1 | `C7B5373078EB777895A4F4B10D8C99EB289CB14FA1835004C1EB4563B6A8A690` | `DE0DE05299C59D3B05D96E744B27F5A5CEF377880AE4A7B402337AA0FFE00245` | `25A8E35111971BE21A85FEFD567ADA685098F45E35E431163AFDBE0FA66DFDE1` | `AAE6327967E07F5CDD92AC9BFA6D9C606E1BE9A96E24DD30ACAD07FD2425DF41` | `AFD25C98EFB5DBF3AE434F5C7BE063A176F28C2AE42D37EC60DB9979D5301163` |

The ready-marker, provenance, source-manifest, artifact-manifest and diagnostics file hashes above were measured from the canonical files. Their source-manifest hash fields agree. Each cited Java source file's SHA-256 also matches its canonical source-manifest entry:

| Version | Relative source file | Source-manifest / file SHA-256 |
|---|---|---|
| 1.19 | `net/minecraft/world/entity/player/Player.java` | `4B5D3D71AC1C4B64EFDEB1AD86A4B6F84AFFFE210E272850E620339430942DE1` |
| 1.19 | `net/minecraft/world/entity/LivingEntity.java` | `00C591DD2CAC49EE5CCAD8705F7DD879AD5D29F0360B98742166294989B3531C` |
| 1.19 | `net/minecraft/world/entity/Entity.java` | `3A8CE3090D29AAD1BBFECA31DE300D34B42A7E902F0E78B13EFD5653CD588766` |
| 1.19 | `net/minecraft/util/Mth.java` | `710E1A1F4E262B610406C707D8FAB245D518570DD88B96828FFA9AA5A6FCDA65` |
| 1.19.1 | `net/minecraft/world/entity/player/Player.java` | `155C5FCFBA322D968F3180383E7D283DDEB5EDEE4E04314906310D4E3CE0CCC1` |
| 1.19.1 | `net/minecraft/world/entity/LivingEntity.java` | `FEE2DF5155449098556A138D2530D06B7A35979E43C6AA0AE1FFFD6251853E77` |
| 1.19.1 | `net/minecraft/world/entity/Entity.java` | `759DE9CDED43BD882AFCF5B7023BCF804D92419ACB656B493F3B490C83EB18B6` |
| 1.19.1 | `net/minecraft/util/Mth.java` | `61727897223A78846A5F1FA270D57AA714C21B3C8D6F2B0E12FEA4A07E6C327F` |

### Technical trace and comparison

- `Player.travel(Vec3)` first snapshots position. In both releases the relevant gate is exactly `isSwimming() && !isPassenger()`. It then reads `double lookY = getLookAngle().y`; chooses `double rate = lookY < -0.2 ? 0.085 : 0.06`; and, only when `lookY <= 0.0 || jumping || !fluidAboveIsEmpty`, adds `(lookY - deltaMovement.y) * rate` to Y while adding zero to X/Z. The above-block fluid probe is at `(x, y + 1.0 - 0.1, z)` and is read only if the first two disjuncts are false. Java `||` short-circuit order is identical in both files. See 1.19 `Player.java:1463-1471` and 1.19.1 `Player.java:1468-1476`.
- `Entity.getLookAngle()` calls `calculateViewVector(getXRot(), getYRot())` directly in both releases (no interpolated/partial-tick pitch). `calculateViewVector` keeps radians and trig results as `float`: pitch radians are `xRot * (float)(PI / 180.0)`; yaw radians are `-yRot * (float)(PI / 180.0)`; it computes cosine(yaw), sine(yaw), cosine(pitch), sine(pitch) with `Mth`; and constructs `Vec3(sinYaw*cosPitch, -sinPitch, cosYaw*cosPitch)`. Thus the Y component originates as float `-sinPitch` and is widened into Vec3's double component before Player compares it with double literals. See `Entity.java:1339-1347,1841-1843` in 1.19 and `1334-1342,1841-1843` in 1.19.1.
- `Mth.sin(float)` indexes the float sine LUT with `(int)(angle * 10430.378F) & 65535`; `Mth.cos(float)` uses `(int)(angle * 10430.378F + 16384.0F) & 65535`. Both exact expressions, casts, masks and float constants match across releases (`Mth.java:43-49` in 1.19; `44-50` in 1.19.1). The LUT is generated from `Math.sin(index * PI * 2.0 / 65536.0)` and cast to float in both.
- After the adjustment, Player calls `super.travel(input)` in either the flying/nonpassenger branch or the else branch. In the applicable water branch, `LivingEntity.travel` performs `moveRelative(0.02F, input)` then `move(MoverType.SELF, getDeltaMovement())`, then applies water slowdown / vertical fluid adjustment. These steps and order match in both sources (`LivingEntity.java:2042-2082`). `Entity.move` receives SELF and the current full delta in both releases. The Player adjustment therefore modifies the velocity that the inherited movement consumes in the same tick; it is a pre-move velocity delta, not a direct position update.
- The Player swim-pitch block, raw pitch/look-vector math, Mth LUT indexing expressions, swim/vehicle gate, up-fluid probe and short-circuit ordering are identical between these two release bodies. No change is evidenced in this bounded operation. Source file hashes differ because the surrounding classes differ; the line-level operation and its dependencies were compared directly.

## Candidate report review

After freezing the source trace, I verified the candidate report SHA-256 as `AC1F0F43914841BD8BE9FEE86BB122CC07C5F4677EA1E12591971985EE515208` before reading it. I found its operation-level conclusion consistent with the independent source trace: it reports no difference and does not overstate full-pair coverage. Its detailed pitch route, float LUT expressions, gates, threshold constants, super-travel ordering and water-branch move dispatch are supported by the cited canonical source bodies. `LivingEntity.tick` reaches `Player.travel` in both releases; no server movement inference is needed for this bounded check. The source writer describes movement meaning as a source-level prediction, not as runtime verification, which is appropriate. No correction to the candidate technical conclusion is required.

**Independent verdict:** Accept the no-difference coverage claim for this specific operation; there is no 1.19-to-1.19.1 finding to publish for the active nonpassenger swimming pitch vertical adjustment. The verdict is bounded to these exact release/mapping inputs and the traced dependencies. It does not establish full-pair coverage, an intermediate cutover, or runtime parity.

Pre-report trace freeze SHA-256: `0E6EE116F34A79E52F8B0811D6025AA0E73AC000755EC196F847591C9A204A2B`.
