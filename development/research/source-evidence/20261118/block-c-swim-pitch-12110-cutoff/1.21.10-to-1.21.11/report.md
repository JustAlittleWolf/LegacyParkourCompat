# Source boundary candidate: swim pitch vertical writer (1.21.10 → 1.21.11)

## Source identity and integrity

Both exact Mojmap source sets are in the canonical read-only root `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\<version>\mojmap`. Each has `status: ready` and matching version/mapping in its ready and provenance manifests. The ready markers list Vineflower, Java 25, 6,386 / 6,622 source files, and the exact versions 1.21.10 / 1.21.11. The provenance records one exact run for 1.21.8, 1.21.10 and 1.21.11 with Mojmap, Vineflower 1.12.0 and Java 25.0.3+9.

- 1.21.10 ready manifest SHA-256s verified: source manifest `4be26049350c1b314a0b198022cb1e7ab1e745e104047de7a5c7d099a9d7b7e1`; artifact manifest `c8210b15012dcc4e109c2f73015b4117abec1f8ab04fdd375dbc11419e9e246c`; movement diagnostics `5642b893edbb01bc1dd57386e013c61572c20f75fd25542877bace1f1f457eb0`.
- 1.21.11 ready manifest SHA-256s verified: source manifest `0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555`; artifact manifest `c8210b15012dcc4e109c2f73015b4117abec1f8ab04fdd375dbc11419e9e246c`; movement diagnostics `a97183dfdbeb5000aac0c66aaed9bb85aa2f2655594e5e4dece254a7c46a13aa`.
- Every relied-on source file hash below matches its line in that version's source manifest. The default `.minecraft-decompile-cache` has no 1.21.10 or 1.21.11 cache directory; the verified deliverable is the ready canonical source tree and manifests, not a claim that the raw download cache is present now.

| File | 1.21.10 SHA-256 | 1.21.11 SHA-256 |
|---|---|---|
| `net/minecraft/world/entity/player/Player.java` | `af857617b66a5776e63830771360b96f75e21d47d20db08f164a2dbeee801d82` | `8e97167350a91741d0aa10d3b0d92a33150ed6dccdc94cd5b37d9c7ca22bcc81` |
| `net/minecraft/world/entity/Entity.java` | `8361dbb86fe6c975d21f69d008377b6f191669be751150c842517e9d0346fa18` | `32314478c6036fa9f3f3cc409c61c622eefc5a282d60e1011a1a33cc29cf18a3` |
| `net/minecraft/world/entity/LivingEntity.java` | `b8b49d60769203f7bd5afe4a1bffcdcdbec30be28960324cdc43a2df85a6eb66` | `19ed2d565858c401c69a06750b054a633a20ea864cab3a753b5c767f0bdd60e8` |
| `net/minecraft/util/Mth.java` | `d2a84fa876d78eb9693b2625ff5dc2eb8568ea33a974b4368effb03b4390e7f1` | `1b547c6b896c9b149d4bc03e3ed106ece485d7ac481a792ae30f600d0239726c` |

## Exact player path and gates

- **1.21.10**, Mojmap `net/minecraft/world/entity/player/Player.java`, `travel(Vec3)` lines 1295–1317: passenger calls `super.travel` and skips the player swimming prelude. Otherwise, if `isSwimming()`, it reads `double lookY = this.getLookAngle().y`; sets `factor` to `0.085` when `lookY < -0.2`, otherwise `0.06`; and writes `deltaMovement.add(0.0, (lookY - deltaY) * factor, 0.0)` iff `lookY <= 0.0 || jumping || fluid at BlockPos.containing(x, y + 1.0 - 0.1, z) is nonempty`. Then flying handling or ordinary `super.travel` runs.
- **1.21.11**, same member lines 1360–1382: identical passenger, swimming, threshold, head-fluid, write, and ordering logic.
- `Player.isSwimming()` also matches across the pair (1.21.10 `Player.java` lines 1595–1597; 1.21.11 lines 1670–1672): it excludes flying and spectator players, then reads the entity swimming flag. `Entity.getLookAngle()` is unchanged: 1.21.10 `Entity.java` lines 2429–2430; 1.21.11 lines 2448–2449. It calls `calculateViewVector(getXRot(), getYRot())`.
- `Entity.calculateViewVector(float,float)` is unchanged: 1.21.10 lines 1806–1813; 1.21.11 lines 1828–1835. It computes float radians `pitch * (float)(Math.PI / 180.0)`, then float `Mth.sin(pitchRadians)`, and returns Y as its negation. The changed Mth signature means Java widens this already-computed float radians to double in 1.21.11.
- The player swim Y adjustment is therefore before the inherited travel call. A fluid-travelling `LivingEntity.travel(Vec3)` follows (`Player.super.travel`): it selects `travelInFluid`, fall-flying or air. In water, 1.21.10 calls `move(MoverType.SELF, getDeltaMovement())` before applying Y damping (`* 0.8F`) and fluid-fall adjustment; 1.21.11 extracts this into `travelInWater` but preserves that move-before-damping order. `Entity.move` remains the inherited position/collision writer. Thus, for a horizontal travel input and unobstructed tick, the pre-corrected Y value reaches the inherited move as its Y input before post-move damping.

## Mth numeric procedure change

- **1.21.10**, Mojmap `net/minecraft/util/Mth.java` lines 32–34, 48–50: `SIN` is `Util.make(new float[65536], ...)`; each entry is `(float)Math.sin(i * Math.PI * 2.0 / 65536.0)`. `sin(float x)` returns `SIN[(int)(x * 10430.378F) & 65535]`. The multiply is float; Java's float-to-int cast truncates toward zero, then int mask selects the table slot.
- **1.21.11**, same file lines 31–37, 50–52: the table is still `Util.make(new float[65536], ...)`, but entries are `(float)Math.sin(i / 10430.378350470453)`. The scale is a double constant; `sin(double x)` indexes `SIN[(int)((long)(x * 10430.378350470453) & 65535L)]`. Product and cast-to-long are double/long, truncation remains toward zero, then a 64-bit mask is cast to int. Thus both the index arithmetic and table initialization formula change. There is one `sin` overload per compared version (float before, double after), not an added coexistence path.

## Concrete legal witness

Use a nonpassenger, nonflying, nonspectator player whose active `isSwimming()` flag is true, pitch `51.0260009765625F`, `jumping == false`, empty fluid at the checked head position, and initial `deltaMovement.y == 0.0`. This is a reachable player pitch within the ordinary `[-90°, 90°]` look range. Positive pitch makes `lookY <= 0`, so the update gate is satisfied without jumping or head fluid. The float radian value entering `Mth.sin` is `0.8905717134475708F` on both sides.

- 1.21.10: float product and int truncation select index `9289`; table value is `0.7774314880371094F`; `lookY` is `-0.7774314880371094` after widening the returned float to double.
- 1.21.11: double product and long truncation select index `9288`; table value is `0.7773711681365967F`; `lookY` is `-0.7773711681365967`.
- Both values satisfy `< -0.2`, so the same `0.085` factor is used. With initial Y velocity zero, the source expression adds respectively `-0.0660816764831543` and `-0.06607654929161072` to Y velocity. The distinct addition precedes inherited travel and can change the tick's vertical displacement. This is an exact source-path consequence; no full trajectory or runtime result is claimed.

## Candidate disposition

The player `travel` gate and write are unchanged across this pair. The candidate operation change is the `Mth.sin` lookup reached from `Entity.calculateViewVector`, which changes the swim pre-travel Y correction for the witness above. The observed endpoint interval is **1.21.10 → 1.21.11**; this comparison alone does not establish the exact introducing patch/release within the interval. This is a bounded source candidate, not a full-pair movement coverage or runtime-parity claim.

