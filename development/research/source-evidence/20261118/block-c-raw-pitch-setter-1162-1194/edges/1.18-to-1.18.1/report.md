# Direct pitch setter and active Elytra consumer: 1.18 → 1.18.1

## Result

No source difference is present in the bounded operation. The full `Entity.java`, `LivingEntity.java`, `Player.java`, and `MouseHandler.java` byte hashes match between 1.18 and 1.18.1. In both releases, `Entity.turn` calculates pitch from the vertical double delta by casting it to `float` and multiplying by `0.15F`; it first writes the un-clamped result through `setXRot`, then clamps the current pitch to `[-90.0F, 90.0F]`. For a locally controlled player with `isFallFlying()` true, a subsequent `Player.travel` invocation reaches the Elytra branch in `LivingEntity.travel`, which reads that pitch and uses it in the movement calculation. This establishes the direct data path, not tick scheduling or input plumbing.

## Exact source evidence

All references below are relative to `decompiled_minecraft/<version>/mojmap/`.

- `net/minecraft/world/entity/Entity.java`, `Entity.turn(double,double)`, lines 383–395: `$$2 = (float)$$1 * 0.15F`; first `setXRot(getXRot() + $$2)` at 386; yaw write at 387; final current-pitch write `setXRot(Mth.clamp(getXRot(), -90.0F, 90.0F))` at 388; then `xRotO += $$2` and its separate clamp at 389–391. The identical sequence is present in both releases.
- The same file's `getXRot` / `setXRot`, lines 3010–3020: the setter rejects non-finite float inputs via `Float.isFinite`; otherwise it writes `xRot` directly. It does not itself clamp finite pitch.
- `net/minecraft/client/MouseHandler.java`, line 244: the ordinary caller invokes `minecraft.player.turn($$7, $$8 * $$13)`. Lines 226–232 derive the deltas from accumulated mouse movement and a scale. This confirms an ordinary caller only; it is not evidence about when movement is scheduled.
- `net/minecraft/world/entity/player/Player.java`, lines 1409–1439: both travel branches delegate to `super.travel` (1428 or 1435).
- `net/minecraft/world/entity/LivingEntity.java`, lines 2005–2006 and 2070–2099: for effective AI or a locally controlled instance, the `isFallFlying()` branch reads `getXRot()` into radians at 2077, then uses cosine/sine at 2081–2095 to update velocity before `move` at 2098–2099. The pitch-dependent `getLookAngle()` also uses current `getXRot()` (`Entity.java:1813–1815`).

As a concrete finite case, at initial pitch `0.0F`, a pitch delta of `1000.0` produces `$$2 = 150.0F`. The first setter accepts `150.0F`; the subsequent clamp writes `90.0F`. The same arithmetic and order apply in 1.18 and 1.18.1. Finite `double` alone does not guarantee finite intermediate `float`: if the cast/product overflows, `setXRot` rejects that intermediate value and leaves the prior pitch, also identically in both releases.

## Source identity and integrity

The ready files identify exact releases `1.18` and `1.18.1`, mapping `mojmap`, Vineflower, Java 25, and 4,203 source files each. Their recorded output paths refer to the original preparation staging tree; I validated the current local `decompiled_minecraft/<version>/mojmap` trees against each adjacent source manifest instead of relying on those paths. Every manifest row matched: 4,203/4,203 files and the exact recorded source-byte totals for each release.

| Identity | 1.18 | 1.18.1 |
|---|---|---|
| `mojmap.ready.json` byte SHA-256 | `4537edf0e88c88631215beb1c14f1d67f5a5d6d021c982c0af549b7b27856e5f` | `7a6f3d9d86776e5a95e4722fc2c36f41165c34177f2993978dfaab410641fc81` |
| `mojmap.provenance.json` byte SHA-256 (also matches ready `provenanceSha256`) | `e642f075b25d1b6e74bad05edacd740421c70f5d8e0c2a38767523cf864124c5` | `4b181e586201aa2695835e31155c96d8ae3d05b00c9bbb83e99eaa5b2b9db139` |
| `mojmap.sources.sha256` byte SHA-256 (matches ready `sourceManifestSha256`) | `a43c61223ddedbdd8ede4d2daf6a750a58cbba324b30c025429ee1030cfc0d78` | `52aa98450b5cfa55ac2bd2054f108e16df7fa939a3f061991b6a9adb434d8d2d` |
| `artifacts.sha256` byte SHA-256 (matches ready `artifactManifestSha256`) | `7ca2b4da88c215e52924d277b257c4631f9664f5def2fb2b7ef25479ec330234` | `b8237a01cdebe7d784caae113886f42565677ce6897ea960a49223cfec4c00ec` |
| Input client JAR SHA-256 (from provenance) | `381e090ef22584526113bcccf26ae38a310a22094d14c2d0dec72b71fdf65aaa` | `737c28a56c8980fcd8574465c61538ebde8f511bdd035c69d0cbe27953f892aa` |
| Ready source count / bytes | `4203 / 20,526,111` | `4203 / 20,530,654` |

Relied source byte hashes; each hash is both the source-manifest row and the independently computed local file SHA-256, and each source file is byte-identical across the pair:

| Relative source | SHA-256 |
|---|---|
| `net/minecraft/world/entity/Entity.java` | `69417675ae8e0a5bd88b7372baea1e73b1f5217154f39446c8ad5c92731437f1` |
| `net/minecraft/world/entity/LivingEntity.java` | `68fc9b15eb31690377afe92acfc783c86945fedec5739d28faacd96da4f9e0e7` |
| `net/minecraft/world/entity/player/Player.java` | `1fb9dfb9f700323d77f4196dbc3618d01a65e56438b9cce3ee38fa0b258bd7e2` |
| `net/minecraft/client/MouseHandler.java` | `7ab775d1565a883cbfb37dbef44da9412ce3f071475cb23e8f1247785634f819` |

## Bounded conclusion

This 1.18 → 1.18.1 comparison establishes no change to the direct local `Entity.turn` pitch calculation/setter/clamp operation or its consumption by active Elytra movement. It does not establish intermediate patch boundaries or tick/input scheduling behavior.