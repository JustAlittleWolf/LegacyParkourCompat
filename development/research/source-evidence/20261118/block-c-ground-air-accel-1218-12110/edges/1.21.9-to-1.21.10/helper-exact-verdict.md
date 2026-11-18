# Exact helper verdict: 1.21.9 → 1.21.10

**Verdict: no difference in the assigned helper slice.** `Vec2.java`, `Vec3.java`, and `Mth.java` are byte-identical across the two exact Mojmap source sets. The bounded helper audit yields no finding. This does not establish that the full ground/air acceleration caller, its branch gates, or tick-level player movement is unchanged; caller logic was outside this helper-only assignment.

## Exact source identities

- **1.21.9 / Mojmap:** canonical nested source path `decompiled_minecraft/1.21.9/mojmap/`. The nested `.decompile-provenance.json` SHA-256 is `37d3049478585a43ffcace14fc8f2ee814b35ceedd8c9bad8b60adaed9b72c4f`. Its 6,386 source rows and 88 cached-input rows are present; the embedded source hashes for all three audited files match their actual bytes. The source-preparation identity pins the client SHA-256 to `40c0a32c46601f71f6a61dae9f126202ded5cc453cfb8da6e42ae73f2a3f5c1b` and the Mojmap artifact SHA-256 to `f223e9775abb66b6712c505cb1a73b48d615efebaf43a9f81dd58df9a9b1c7b3`.
- **1.21.10 / Mojmap:** ready marker identifies version `1.21.10`, mapping `mojmap`, 6,386 files, and 28,999,290 source bytes. The actual `mojmap.sources.sha256` SHA-256 is `4be26049350c1b314a0b198022cb1e7ab1e745e104047de7a5c7d099a9d7b7e1`, matching the ready marker; actual file count and byte total match. `mojmap.ready.json` SHA-256 is `2374d901074b72cd69f0229381d6a11edea7983613c538a2e2e18d9fc73dbeb0`; `mojmap.provenance.json` SHA-256 is `9a1db4c050c3caa28a1313f89dae9da3b28feaa5f8afba42cab798b47d7ad77c`. The artifact manifest SHA-256 is `c8210b15012dcc4e109c2f73015b4117abec1f8ab04fdd375dbc11419e9e246c`; its Mojmap row identifies `1.21.10/client_mojmap.jar` as `0885181c5e4c2f21dd2f95591dd095fa0176807edbe239ef77218e861f0751da` and `1.21.10/client_mappings.txt` as `2a6f53f4c1fd048e8fa956e3a3fbbb0afc02d5bbc23e52a16b328aed61b9bf39`. The ready marker's client SHA-256 is `e65ca028bc58da12bf8413066c90ecd4d48c94fb351d9bbd22ecdff5c87f9482`.

## Compared helpers

Every source reference below applies to both listed releases and the complete source-file SHA-256 is identical on each side.

| Mojmap source member | 1.21.9–1.21.10 SHA-256 | Verified behavior |
| --- | --- | --- |
| `net/minecraft/world/phys/Vec2.java`, `scale(float)` lines 28–30; `normalized()` lines 48–51 | `b637ef6899de5d8892e19273b114ffaf0397761e509170a56d52dd068f54bffe` | `scale` multiplies both float components by the float scalar. `normalized` sums float component squares, calls `Mth.sqrt(float)`, returns `ZERO` when the result is `< 1.0E-4F`, otherwise divides each component by that float length. |
| `net/minecraft/world/phys/Vec3.java`, `horizontal()` lines 171–173; `horizontalDistance()` lines 187–189; `horizontalDistanceSqr()` lines 191–193 | `f1601867b42544c1461fd7f315b5ec7eb108fc23f2b755f923a609f5466cd154` | `horizontal` returns `(x, 0.0, z)`. Horizontal distance is `Math.sqrt(x*x + z*z)` in double arithmetic; the squared variant returns `x*x + z*z`. These helpers do not cap or clamp the magnitude. |
| `net/minecraft/world/phys/Vec3.java`, `yRot(float)` lines 233–240 | `f1601867b42544c1461fd7f315b5ec7eb108fc23f2b755f923a609f5466cd154` | Reads float cosine and sine from `Mth`; computes `x′ = x*cos + z*sin`, preserves `y`, and computes `z′ = z*cos - x*sin` in double expressions. |
| `net/minecraft/util/Mth.java`, sine table lines 32–35; `sin(float)` lines 48–50; `cos(float)` lines 52–54; `sqrt(float)` lines 56–58 | `d2a84fa876d78eb9693b2625ff5dc2eb8568ea33a974b4368effb03b4390e7f1` | Initializes 65,536 float entries with `(float)Math.sin(i * Math.PI * 2.0 / 65536.0)`. `sin` indexes `(int)(radians * 10430.378F) & 65535`; `cos` adds `16384.0F` before the same cast and mask. Float square root is `(float)Math.sqrt(value)`. |
| `net/minecraft/util/Mth.java`, `clamp(float)` lines 106–108 and `clamp(double)` lines 110–112 | `d2a84fa876d78eb9693b2625ff5dc2eb8568ea33a974b4368effb03b4390e7f1` | Float and double lower-bound checks and upper-bound `Math.min` expressions are identical. There is no horizontal-magnitude cap in the audited `Vec3` methods; any cap assembled by a caller is outside this slice. |

## Applicability boundary

The verified result is that these exact shared math helpers preserve their historical operations between 1.21.9 and 1.21.10 when used by horizontal input normalization/scaling, horizontal magnitude reads, or yaw rotation. The comparison does not trace which helpers the local-player ground or air branch invokes, nor inspect caller-side caps, gates, acceleration constants, or vertical/sprinting/collision behavior. No tick-level parity conclusion follows from this helper result.


## Phase-two match to the frozen author report

The author report's supplied SHA-256 was verified before reading: `ceba78d5565e0fd2cfc5145a23c862ac88489f663890d95c449e669674dd36a3`. Its relied `Vec2.java`, `Vec3.java`, and `Mth.java` rows match actual bytes on both sides, and the three file hashes in its inventory match the source manifests/provenance and our independently computed hashes recorded above.

The movement path described in that report relies on these helper operations, each byte-identical in 1.21.9 and 1.21.10:

- `Vec2.scale(float)` (`Vec2.java:28–30`) multiplies both float components by the scalar. `Vec2.normalized()` (`48–51`) sums float squares, takes `Mth.sqrt(float)`, returns `ZERO` below `1.0E-4F`, otherwise divides the components by the float length.
- `Entity.getInputVector` uses `Vec3.lengthSqr()`, `normalize()`, and `scale(double)` (`Vec3.java:86–89, 155–157, 183–185`). `lengthSqr()` is the full double sum `x*x + y*y + z*z`; `normalize()` computes `Math.sqrt(x*x + y*y + z*z)` in double and returns zero below `1.0E-5F`, otherwise divides all three components; `scale()` delegates to componentwise multiplication by the double scalar. The caller's strict `lengthSqr < 1.0E-7` zero gate and `lengthSqr > 1.0` normalize gate are present in `Entity.getInputVector` (1.21.9 `Entity.java:1590–1600`; 1.21.10 `1595–1605`).
- That caller rotates with direct `Mth.sin` and `Mth.cos` float lookups and constructs `(x*cos - z*sin, y, z*cos + x*sin)`. This direct formula is the acceleration path's yaw transform; it does not call `Vec3.yRot()`.
- `Mth`'s 65,536-entry sine LUT and `sin`/`cos` index arithmetic (`Mth.java:32–35, 48–54`) are unchanged. The float square root used by `Vec2.normalized()` is unchanged (`Mth.java:56–58`).

**Applicability correction:** The earlier helper snapshot correctly found identical definitions but over-associated `Vec3.horizontalDistance()`, `horizontalDistanceSqr()`, and `yRot()` with the reported acceleration path. Those methods are byte-identical too, but the path in the frozen report does not use them. Its unit-length cap uses `Vec3.lengthSqr()` over all three components plus the caller's normalize gate; yaw rotation is performed directly in `Entity.getInputVector`. This correction narrows applicability and leaves the no-helper-difference verdict unchanged. It does not audit any caller beyond the helper rows and the explicitly cited acceleration transform.

**Frozen-report identity caveat:** The frozen author report's prose gives the 1.21.10 provenance SHA-256 as `9a1db4c050c3caa28a1313f89dae9da3b28fea5f8afba42cab798b47d7ad77c`. The actual local `mojmap.provenance.json` SHA-256 is `9a1db4c050c3caa28a1313f89dae9da3b28feaa5f8afba42cab798b47d7ad77c` (also recorded correctly in this verdict's earlier identity section). This is a hash transcription mismatch in the frozen report; the author report itself was not modified.