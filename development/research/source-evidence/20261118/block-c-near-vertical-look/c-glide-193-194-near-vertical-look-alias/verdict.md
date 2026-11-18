# Independent source audit verdict

**Verdict: ACCEPT, bounded no-difference.** The frozen claim is verified for the specified already-active local Elytra `LivingEntity.moveRelative` path. No 1.9.3→1.9.4 change appears in the paired source files for this operation. This verdict does not establish full pair coverage, a patch boundary, runtime parity, or any behavior outside the stated path.

## Frozen claim and source identity

Audited report: `c-glide-193-194-near-vertical-look-alias/report.md`, SHA-256 `9BBD41E786C522E60B7C01152B7732AC90F185F681F70487A281B165CD738870`.

Both sides use Ornithe Feather. In 1.9.3, `decompiled_minecraft/1.9.3/ornithe-feather/.decompile-provenance.json` has SHA-256 `43325CDD53CA34B88EE9AF8464782B323A948B98C8A556C4761F1E5737B84C3A`; its pinned inputs identify release metadata `1.9.3`, client artifact `b6985b0d…/client.jar` SHA-256 `ab8fdde789c5b4614ffc98249e04fdcce7dfce320b55215bfe5418608af6a63b`, and Feather `1.9.3+build.2` merged mapping artifact SHA-256 `fc5defa1546cf87888a11783e2b5d9b48b47c6c022f48de4e55add978ab35da3`.

For 1.9.4, `decompiled_minecraft/1.9.4/ornithe-feather.ready.json` SHA-256 is `3D0A810D9F3A93233D880CA07FFC806EA232230D33197197AD50BA69181171C6`; `ornithe-feather.provenance.json` SHA-256 is `5C913715BED763369CEB5FD171CAB3E63C43298023078C3683F96C7EA8FED3CD`. They declare exact release `1.9.4`, Feather mapping option/build 2 artifacts, and client artifact SHA-256 `23e90103a1ca2ac71100004c6d5846de09f85695f579843ef8da41571e60c908`. The declared source-manifest digest `C7B508FE01634887B65919DCD3A900C311A21D9510A1F1AB248D5C17C528AB19` matches the actual `ornithe-feather.sources.sha256` bytes; the artifact manifest similarly matches its declared digest `9527DCA544694DAA3B4A7741BE1A5B4802D8B6665C8A152A408A37A27A1B4D77`.

The actual 1.9.3 and 1.9.4 source bytes are equal for each examined file, and their hashes match the corresponding 1.9.3 provenance entries and 1.9.4 source-manifest entries:

| Relative source | SHA-256, both releases |
| --- | --- |
| `net/minecraft/entity/living/LivingEntity.java` | `bbb7703f18fd5da05c4e4a43a77ea644b388e63c01d34166d308ea52054be4e5` |
| `net/minecraft/entity/Entity.java` | `bcd7fa2206bf8d7271102f6da7fe2771f96dbe22ec79dab9f2101a3e29c17ef0` |
| `net/minecraft/util/math/MathHelper.java` | `43b2a4e3931733dbc3538024b7182b024d874a5f22a269a6199c6b4ca8c59151` |

## Independently checked behavior

- `LivingEntity.moveRelative` at lines 1302–1341 has the local/logical-update, fluid and active-flight gates at 1303–1306. It obtains `getLookVector`, computes radians `i` and horizontal denominator `j` at 1311–1314, and uses the same dive-pull guard (`velocityY < 0.0 && j > 0.0`) at 1319. The upward-look branch is gated by `i < 0.0F` at 1326, then divides look X/Z by `j` at 1329–1330 without a `j > 0.0` guard. The separate horizontal alignment guard is at 1333–1336. Damping is X/Y/Z at 1338–1340, followed by `move(...)` at **1341**.
- For a locally controlled player with already-active Elytra state and dry movement, this is the reachable branch. `Entity.updateLocalPlayerCamera` clamps pitch to `[-90.0F, 90.0F]` at `Entity.java:286–293`. A finite negative pitch near, but not exactly at, the vertical endpoint gives `i < 0`; with ordinary finite yaw and a nonzero near-vertical horizontal look vector, the computed `j` is positive. This confirms the report's requested bounded case; no zero, NaN, malformed or crafted-input claim is made.
- `LivingEntity.getLookVector()` delegates to `getRotationVector(1.0F)` at `LivingEntity.java:1817–1819`, which supplies current pitch and head yaw at `1822–1825`. `Entity.getRotationVector(float,float)` computes the yaw/pitch LUT components and returns `(g * h, i, f * h)` at `Entity.java:1124–1129`.
- `MathHelper` declares a 65,536-float sine table at line 8; `sin` uses `(int)(x * 10430.378F) & 65535` at 15–17 and `cos` uses `(int)(x * 10430.378F + 16384.0F) & 65535` at 19–21. Its table initialization is at 423–424. The explicit float arithmetic, truncating cast, offset ordering, and mask agree on both sides.

## Reference correction and bounds

The frozen report's statement that the final `move` call is at `LivingEntity.java:1340` is off by one: the checked sources place Z damping at 1340 and `move(...)` at 1341. Its bounded behavior conclusion remains supported. The report's “1333–1335” pointer describes the horizontal-alignment guard and assignments but excludes the closing brace at 1336.

This audit compares the named 1.9.3 and 1.9.4 local decompilations only. It verifies neither complete player-tick coverage nor intermediate releases, server behavior, flight activation, collision outcomes, or runtime trajectories.
