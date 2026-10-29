# Independent source-only audit: keyboard slow-input change

## Verdict

The cited finding is supported by the exact 1.14.4 and 1.15 Mojmap source trees. The `0.3` input scaling operation is materially changed by its predicate: 1.14.4 checks the freshly sampled sneak key OR the caller-provided visual sneak/crawl flag, gated by non-spectator status; 1.15 checks the caller-provided `isMovingSlowly()` value, which is crouching OR visually crawling. The multiplier, cast and impulse targets are otherwise the same.

The tick-order statement is correct at the source boundary. For precision, the code proves that `isMovingSlowly()` reads `input.shiftKeyDown` before this invocation of `KeyboardInput.tick` refreshes that field. It therefore consumes the state present on entry to `aiStep` (normally the preceding input sample); describing it as “the previous game tick” would be stronger than these call-order excerpts alone establish. `LivingEntity.tick` calls virtual `aiStep`, and `LocalPlayer.aiStep` is the only vanilla call site for this keyboard tick in each tree.

## Provenance and source integrity

The shared roots are the exact-version ready Mojmap trees under `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.14.4\mojmap` and `...\1.15\mojmap`.

| Release | Ready identity | Source manifest SHA-256 | Verified rows / bytes | Provenance identity |
|---|---|---|---|---|
| 1.14.4 | version `1.14.4`, mapping `mojmap` | `af98406f3d4ed31494fbdce1078d1f3df3f712501878f9a0e9ad5879bdf23c3b` | 3,250 / 17,080,207; zero malformed, missing or mismatched rows | version `1.14.4`, namespace `mojmap`, mapping option `feather,mojmap`; verification record reports identical source and input artifacts |
| 1.15 | version `1.15`, mapping `mojmap` | `251a3019ccc3fd067f66365f3545b69a983383a28b19a1038debf1791be29340` | 3,329 / 17,522,461; zero malformed, missing or mismatched rows | requested and exact version `1.15`, namespace `mojmap`, mapping option `mojmap`; ready marker's provenance digest matches the provenance file |

For both releases, the manifest file digest matches the ready marker, all manifest entries were parsed, every listed source file hash was recomputed, and the verified counts and aggregate byte sizes match the ready markers. The ready client digest also agrees with its provenance identity; the 1.14.4 digest appears in its artifact manifest. The referenced raw client jars are not present at their recorded artifact-cache paths now, so this audit did not independently rehash those jar files. The 1.14.4 ready marker does not embed a provenance-file digest; its provenance version/namespace/run fields and separate source verification record were checked directly.

Every source-file hash cited in the reviewed audit matches both the current file and its manifest entry:

| Source file | 1.14.4 SHA-256 | 1.15 SHA-256 |
|---|---|---|
| `net/minecraft/client/player/Input.java` | `8adaf34fa0bcef319d8b3dd678f53ddf68b0293a17e2c1620a6528fa0d9ad1ef` | `367c3a9b0b21d8f106a21fd2c73a3018685dbf07d9c8a9340e2d4c9d73359201` |
| `net/minecraft/client/player/KeyboardInput.java` | `33daa0833a95e09e728c1b8f509020dd13b70e38aba922e2b1e10ec5c9b7739a` | `746ea654cf4f46a5f4b94a237c4307652252a44807a488b606dc993e088396f7` |
| `net/minecraft/client/player/LocalPlayer.java` | `0795c1223198ce5acf5d2ed9e5db8435bbec4cd52b96f96b1ddf2865baaae85f` | `7227da5233e3c72ced008187b92a6a86842f84778eaa9f40fa1eb117286af823` |
| `net/minecraft/world/entity/Entity.java` | `31c6be42d165102d3e6dc4295fdfef6e8971fc3aea13f938a5b3a33b91d524a7` | `191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e` |
| `net/minecraft/world/entity/LivingEntity.java` | `428762178a876efd4069086e6b7d51f571ea0401f44e7eff0927b7d26d9ef681` | `139c3866ad2c6a1ad73496f22ff1a4b5259998442092ffbafb8664428e28bf79` |
| `net/minecraft/client/multiplayer/ClientPacketListener.java` | `6dddf37b79830d5413d91675e0bed8b4a3f0a179ac4535616dc57b510b778b45` | `d806d286af4b93d7c61c3c884f344a5870ce1871fb0a811506031adf78fafc30` |

## Behavioral trace

- **1.14.4:** `Input` stores `sneakKeyDown` and defines `tick(boolean, boolean)` (`Input.java:5-25`). `KeyboardInput.tick` reads movement and jump bindings, samples `options.keySneak.isDown()` into `sneakKeyDown`, and then applies `(float)(impulse * 0.3)` to both impulses if `!bl2 && (sneakKeyDown || bl)` (`KeyboardInput.java:5-27`). `LocalPlayer.aiStep` supplies `isVisuallySneaking() || isVisuallyCrawling()` for `bl` and `isSpectator()` for `bl2` (`LocalPlayer.java:618-634`). Thus a currently sampled sneak key independently triggers the slowdown for non-spectators, even when the visual-pose predicate is false. `isVisuallySneaking` itself is gated by not flying, not swimming and enterability of `Pose.SNEAKING`, then tests trying-to-sneak or inability to stand (`LocalPlayer.java:588-597`).
- **1.15:** `Input` stores `shiftKeyDown` and defines `tick(boolean)` (`Input.java:5-25`). `KeyboardInput.tick` samples `options.keyShift.isDown()` and scales both impulses under the passed boolean (`KeyboardInput.java:5-27`). `LocalPlayer.isShiftKeyDown` reads the stored `input.shiftKeyDown`; `isCrouching` is gated by not flying, not swimming and enterability of `Pose.CROUCHING`, then tests stored shift or (when not sleeping) inability to stand; `isMovingSlowly` is crouching OR visually crawling (`LocalPlayer.java:591-604`). There is no explicit spectator argument/check in this input predicate. The historical audit correctly avoids claiming that spectator status itself is an input gate in 1.15.
- In 1.15 `LocalPlayer.aiStep` evaluates `this.isMovingSlowly()` as the argument before entering `KeyboardInput.tick`, whose body later writes `shiftKeyDown` (`LocalPlayer.java:625-640`, `KeyboardInput.java:18-25`). Consequently, a key transition sampled in that call cannot affect that call's `0.3` gate through the shift-key component; it can affect a later evaluation. Crawl or blocked-pose conditions can independently make the predicate true.
- `Entity.isVisuallyCrawling` is swimming pose while not in water in both releases (1.14.4 `Entity.java:1840-1842`; 1.15 `Entity.java:1823-1825`). The login and respawn setup sites assign `new KeyboardInput(options)` in each version (1.14.4 `ClientPacketListener.java:359,1055`; 1.15 `:370,1084`). A repository-wide search of both Mojmap trees found `KeyboardInput` as the only `Input` subclass and those as the only `KeyboardInput` constructions; the one `input.tick` call in each is `LocalPlayer.aiStep` (unrelated `ChatScreen` input is a different type).
- Reachability through travel is present: `LivingEntity.tick` calls `aiStep` (1.14.4 `LivingEntity.java:1975,2042`; 1.15 `:2024,2103`); `LocalPlayer.serverAiStep` copies keyboard impulses into `xxa`/`zza` when it controls the camera (1.14.4 `LocalPlayer.java:600-605`; 1.15 `:607-612`); and `LivingEntity.aiStep` passes those fields to `travel` (1.14.4 `LivingEntity.java:2223`; 1.15 `:2287`). The unchanged `travel` branches use the horizontal vector in `moveRelative`. This supports the stated client-local-player reachability without asserting that the predicate changes downstream travel formulas.

## Boundary

The key-mapping poll/configuration/device handling is not part of this predicate finding. This is a closed audit of the cited keyboard slow-input operation and its direct impulse path, not a complete 1.14.4→1.15 movement-pair coverage verdict.
