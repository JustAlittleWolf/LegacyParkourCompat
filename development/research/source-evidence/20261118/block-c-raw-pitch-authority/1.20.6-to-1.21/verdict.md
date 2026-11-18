# Frozen source audit: ordinary mouse pitch through `Entity.setXRot`

## Frozen scope

Pair: Minecraft 1.20.6 → 1.21.0, Mojmap only. This audit is bounded to the local ordinary mouse path: callback double-delta accumulation; `MouseHandler.handleAccumulatedMovement` gates; sensitivity/smoothing/invert-Y conversion; `LocalPlayer.turn` dispatch; `Entity.turn`'s first pitch write, yaw write, second clamped pitch write and old-pitch update; the finite-only `Entity.setXRot` implementation; and the live local `LivingEntity.travel` pitch consumer. The movement consumer is traced far enough to distinguish ordinary travel from the conditional fall-flying path. No other input, camera, movement operation, intermediate patch boundary, wiki, product implementation, or author history is in scope.

Frozen verdict: **no source difference found in this bounded operation.** No movement-change finding is warranted from this pair/slice. This does not close full-pair movement coverage or runtime parity.

## Exact source and provenance identity

Canonical read-only roots:

- 1.20.6 Mojmap: `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/1.20.6/mojmap/`
- 1.21.0 Mojmap: `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/1.21/mojmap/`

The 1.20.6 `mojmap.ready.json` declares version `1.20.6`, mapping `mojmap`, 5,329 source files, and the source/artifact manifest hashes below. Its `mojmap.provenance.json` records the exact 1.20.6 Mojmap batch run, mapping option, Java 25.0.3+9-LTS and Vineflower 1.12.0. The ready marker, provenance, source manifest, artifact manifest and movement diagnostics have these SHA-256 values:

- `1.20.6/mojmap.ready.json`: `1ec1dc773ea2a8a3ef48a9fc868893bd2f83d6b13b057d75b151b711788c3d41`
- `1.20.6/mojmap.provenance.json`: `ee53e67e7ab51069a0eaae5a0ce13d49a9f972bdc13e3c31e041afa77f4d26e8`
- `1.20.6/mojmap.sources.sha256`: `56aae10684471d7abb1c366bd5dd431ab976112a87e6cc5c687a68f1eff06311` (matches ready marker)
- `1.20.6/artifacts.sha256`: `e5882622bcf22b3e3c2c96c73843a1308e11e3096f4feefd945ea5808933ce31` (matches ready marker)
- `1.20.6/movement-diagnostics.txt`: `558c501d2956cfa2445e9a75512dcf6c8ff7fec453c7b2f439af171162cab498` (matches ready marker)

The 1.20.6 ready/artifact metadata records raw `client.jar` SHA-256 `02dfd345ac1ad55692d5dbc8486ac7e4fea72cd54ac494a79cd48963048e56b2`, Mojmap mappings SHA-256 `27f4ef3a9362e9874e2c33edac981b6f485ad97f92c4e35b497525b646aae745`, and the mapped client archive SHA-256 `2add08d295773a0615ca595877f5fd055fa454d7c68fac56d6a4894a1e055284`. The historical artifact cache referenced by that run is absent from the current checkout, so these are checked sidecar pins, not re-hashes of locally present 1.20.6 raw artifacts. Source files were independently re-hashed against the ready-linked source manifest.

For 1.21.0, the hidden mapping-root `.decompile-provenance.json` has SHA-256 `90246f277aefd2350e42e17245faa51928a949c8db4427f3be04a732527deb7c`. Its embedded source SHA-256 entries match each source file listed below. Its local input-pin files and provenance inputs agree with re-hashed cache files: `client.jar` `4b56bc9b44de7e88f9fbb52e716c18240da34469fcbafa5ed2e75b04d60617e8`; `client_mappings.txt` `127485d79248d4ae2955a7d061c3626d38853ecb054c4d008707732264b0aac1`; `version.json` `cdd1a44db7b5e8787723c1f6c7c7dc3a4638d96a31ddc84aeb27d3f30efa802a`. This input verification is separate from source-file hash verification.

### Cited source hashes

Each listed source hash was recomputed from the file bytes and matched the 1.20.6 source manifest or the 1.21.0 embedded provenance entry.

| Mojmap source file | 1.20.6 SHA-256 | 1.21.0 SHA-256 |
| --- | --- | --- |
| `net/minecraft/client/MouseHandler.java` | `ed1248f1d62a927907e6e10fae87a0d4ab0a99d91e44a149aeba40d05fbda1e1` | `824c52842483eb2899bd5a76c39701c3a616f249dcb64283da556dff4bcd50f7` |
| `net/minecraft/client/Options.java` | `c020b31bd2650a0c6575de544831111323f7edb20ae431fc06faf4cb4c6fd8cc` | `77ebfa588da0edb355037ea40ab90ea57b19659abfbafcff84f621f5211f250e` |
| `net/minecraft/client/player/LocalPlayer.java` | `6b429dfa6e0681251ec985dda1627f808652a7bbe5b70dc85c8fa0fe0ed46ffa` | `c555e68ac3c63ab9b4f9a9e31933e263b96350a2bc599d11a0de5928bc24b583` |
| `net/minecraft/util/SmoothDouble.java` | `31fcb07fde988b10d5f9c4f252e7bb1bb9f6d337ac31e2846a0bc52468f5ef3f` | `31fcb07fde988b10d5f9c4f252e7bb1bb9f6d337ac31e2846a0bc52468f5ef3f` |
| `net/minecraft/world/entity/Entity.java` | `71cd6b9f6c002684154dce11d3745e8714d82f13c8e1b3aa56743930131c18f3` | `b81905c7879e2cc5c5063a41d865c4164ad919f156306705be791d1017b99850` |
| `net/minecraft/world/entity/LivingEntity.java` | `c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2` | `324a3eee8496caab57cfaf5101ef576f1ae3c60c40e3857e96f35f3af9a3a0d8` |

## Trace and comparison

1. `MouseHandler.onMove` accumulates cursor differences into `double accumulatedDX/DY` only while the window is active, then updates the cursor positions. `handleAccumulatedMovement` calls `turnPlayer` only for an active window with a grabbed mouse and non-null local player, then resets both accumulated deltas. The entire `onMove` body (17 nonblank normalized lines) and `handleAccumulatedMovement` body (29 lines) compare equal in the pair.
2. `Options.sensitivity` is `OptionInstance<Double>` in both versions. `turnPlayer` computes `s = sensitivity.get() * 0.6F + 0.2F`, `s3 = s*s*s`, `s8 = s3*8.0`; all are `double`. Smooth camera feeds accumulated deltas times `s8` and elapsed time times `s8` to `SmoothDouble`. First-person scoping feeds accumulated deltas times `s3`; the ordinary branch feeds them times `s8`. The smooth method's three state fields and calculations are `double`, and its source digest is identical across releases. Invert-Y changes a `double` multiplier from `1` to `-1` before `player.turn(dx, dy * multiplier)`. `turnPlayer` compares equal across the pair (33 normalized nonblank lines).
3. `Entity.turn(double yawDelta, double pitchDelta)` first casts each input to `float` and multiplies it by `0.15F`, so the pitch delta is rounded to `float` before addition. It calls `setXRot(getXRot() + pitchDelta)` first (the raw, finite-checked write), writes yaw second, then calls `setXRot(Mth.clamp(getXRot(), -90.0F, 90.0F))` as a second write. Only after that it adds the delta to `xRotO` and clamps `xRotO`; a mounted passenger notification follows. The complete method body compares equal (13 normalized nonblank lines).
4. `setXRot(float)` in both releases only rejects non-finite input (logging/pausing in IDE) and otherwise assigns `this.xRot`; it does not clamp. The setter bodies compare equal (7 normalized nonblank lines). Thus the ordering of the initial setter and outer clamp is unchanged.
5. The next local movement call chain is `LocalPlayer.tick` → `super.tick`; `LivingEntity.aiStep` constructs input and invokes `travel` for the local-controlled entity. In `LivingEntity.travel`, pitch is read for movement in the `isFallFlying()` branch: `getLookAngle()` uses current `getXRot()`, then the branch also converts `getXRot()` to radians and applies cosine/sine terms to velocity before `move`. The complete fall-flying block (62 normalized lines) compares equal. The ordinary water/lava branch does not directly read pitch; ordinary ground steering is yaw-relative. The reachable direct pitch-to-velocity consumer therefore remains conditional on fall-flying, with no 1.20.6→1.21 change in its math or gates.

## Audit boundary

This is a source-only no-difference result for one bounded operation. It does not assert that all mouse behavior, all player movement, other input/turn paths, or the full 1.20.6–1.21 interval are unchanged. No author lookup was performed or authorized. No product source, backlog, Git metadata, lab output from other assignments, tests, build, generated source or runtime was accessed.