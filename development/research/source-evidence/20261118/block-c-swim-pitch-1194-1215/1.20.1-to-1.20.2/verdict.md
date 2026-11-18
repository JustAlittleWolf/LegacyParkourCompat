# Independent source freeze — ordinary swim-pitch direct Y adjustment

## Assignment and source isolation

- Scope: Minecraft 1.20.1 → 1.20.2, Mojmap, `Player.travel`'s own ordinary swimming pitch-derived direct vertical velocity adjustment; follow its inherited `Entity` pitch/look-angle calculation and the ordinary water-travel route to the actual inherited move call.
- This source freeze was completed before opening `c-swim-pitch-vertical-1201-1202-source/report.md` or reading implementation, wiki, backlog, or Git evidence.
- Host: Windows PowerShell 7.6.9; canonical sources are read-only under `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft`.
- Read instructions: root `AGENTS.md` supplied in assignment, `README.md`, `development/workflows/orchestration/WORKFLOW.md`, `development/workflows/discovery/WORKFLOW.md`, `development/workflows/discovery/source/WORKFLOW.md`, `buildSrc/README.md`.

## Canonical source-set identities

The ready marker says `status=ready`, exact `versionId`, and `mapping=mojmap`. Its declared source-manifest digest matches the digest of the corresponding manifest bytes. Both provenance files identify the same exact decompiler batch (1.20.1, 1.20.2, 1.20.4, 1.20.6), Vineflower 1.12.0, Gradle 9.7.1 and Java 25.0.3+9-LTS.

| Release | Ready marker SHA-256 | Provenance SHA-256 | Source manifest SHA-256 | Mojang client SHA-256 |
| --- | --- | --- | --- | --- |
| 1.20.1 Mojmap | `f8dc81374dc42521ee9d17a04ea0dc77f1c5384548cfc01c9621b3d84b670fed` | `5aeb1df09671480aeddb921c919e92098e33d0a0bce47e22f82a865cb32400aa` | `858b56764113e591c60d09bc63e4d90f9c1d67a705645f0c793e8b28f2378465` | `56b71336d2b4fdffd197f56595b0da93e32a946f78f382a299b8f4b92758bb0f` |
| 1.20.2 Mojmap | `5ff3322f287c250ee914c7ca3840d053bffc3c2c5f35e91b59314d948292483f` | `1dad9f725f6cb25b6568700fa0294f83b66ea9633b0d62ed354d7bd8b45d4f05` | `e439ff7e1d3f1d31e01be1edd141f94d917a9dc7fa0de5f69904544cbbe62887` | `fa1a19be56a506426308abbc1cad85f299a7fc6dae4335559351ba0246713fda` |

## Frozen operation and dependencies

### Direct writer: `Player.travel(Vec3)`

- 1.20.1: `net/minecraft/world/entity/player/Player.java`, lines 1446–1473.
- 1.20.2: same relative file, lines 1450–1477.
- Both bodies have the same operation sequence and expressions. The local adjustment is entered under `isSwimming() && !isPassenger()`. It samples `double lookY = getLookAngle().y`, chooses `double factor = lookY < -0.2 ? 0.085 : 0.06`, then writes only when `lookY <= 0.0 || jumping || the block at (x, y + 1.0 - 0.1, z) has nonempty fluid state`. The write reads current velocity and applies `setDeltaMovement(current.add(0.0, (lookY - current.y) * factor, 0.0))`. The Y operation is double precision; the `1.0 - 0.1` sample coordinates are evaluated in that order. This is before `super.travel`; in the ordinary non-flying player branch that call is unconditional `super.travel(input)`.

### Authoritative pitch and look-angle math: `Entity`

- 1.20.1: `net/minecraft/world/entity/Entity.java`, rotation fields at 159–162, `calculateViewVector` at 1448–1455, `getLookAngle` at 1970–1972, `getXRot`/`setXRot` at 3269–3282.
- 1.20.2: same relative file, rotation fields at 161–164, `calculateViewVector` at 1458–1465, `getLookAngle` at 2004–2006, `getXRot`/`setXRot` at 3299–3312.
- Both use a private float `xRot`; `getLookAngle()` calls `calculateViewVector(getXRot(), getYRot())`. That computes float radians with `pitch * (float)(Math.PI / 180.0)`, then returns Y as `-Mth.sin(pitchRadians)` in a `Vec3` (widened to double). No interpolation/normalization enters this call.
- `Mth.java`: both releases declare `SIN_SCALE = 10430.378F`, allocate `float[65536]`, fill indices 0–65535 as `(float)Math.sin(index * Math.PI * 2.0 / 65536.0)`, and implement `sin(float)` as `SIN[(int)(radians * 10430.378F) & 65535]`; `cos(float)` offsets the scaled float by `16384.0F` before the same int cast and mask. These bodies are identical.

### Inherited route through actual move

- 1.20.1: `LivingEntity.travel(Vec3)` begins at line 2004; ordinary water branch calls `move(MoverType.SELF, getDeltaMovement())` at 2036; underwater branch also does so after its own velocity math at 2094. `Player.travel` reaches this through `super.travel` at 1469 in its non-flying branch.
- 1.20.2: `LivingEntity.travel(Vec3)` begins at line 2038; ordinary water branch calls the same inherited move at 2070; underwater branch does so after its own velocity math at 2128. `Player.travel` reaches this through `super.travel` at 1473 in its non-flying branch.
- `Entity.move(MoverType, Vec3)` receives the delta, applies its existing movement/collision processing, and updates position with `setPos(getX()+collided.x, getY()+collided.y, getZ()+collided.z)` (1.20.1 lines 593–625; 1.20.2 lines 595–627). The implementation is the same for this route in both sources.
- Applicability witness for the traced path: locally controlled, non-passenger player, not flying, swimming, in water, affected by fluids and not standing on the fluid. The direct adjustment precedes the superclass water travel; subsequent water movement may further change velocity before the inherited SELF move. This audit is limited to the direct `Player.travel` pitch-to-Y writer and its route, not other fluid-mechanics operations.

### Exact source bytes checked against the declared manifests

| Release | Relative path | SHA-256 |
| --- | --- | --- |
| 1.20.1 | `net/minecraft/world/entity/player/Player.java` | `873d82c6f5471d06812929e471d970fd973bfbe21638e4e348a6fcde45637dac` |
| 1.20.1 | `net/minecraft/world/entity/LivingEntity.java` | `decf8cd70d194098ad51d0c82e5a0087e0687881e0a2dc66456f2ba6ea76676f` |
| 1.20.1 | `net/minecraft/world/entity/Entity.java` | `94b9c3656715de2d61fa9a02ccef164261e50eb13d6090a6f07e58fe9c5b0759` |
| 1.20.1 | `net/minecraft/util/Mth.java` | `201d17ea024637036761c2703e407d4ab1dc7651a1f4b00a857c09803754c797` |
| 1.20.2 | `net/minecraft/world/entity/player/Player.java` | `25f263692fd6b2a737aa813315022a0caf4e18c3661ba3bd706c6297f909f25c` |
| 1.20.2 | `net/minecraft/world/entity/LivingEntity.java` | `5c481da1ffc8684c4b30171c92e61fad751e6a3a708d5bc6486ac9f96ff69828` |
| 1.20.2 | `net/minecraft/world/entity/Entity.java` | `d7ee49aaea5e862b92508e767562cabc8f10515605e8fb67565c8c8d5c23b01b` |
| 1.20.2 | `net/minecraft/util/Mth.java` | `8a16560d3dd84853a2caaa658d6a106ea2e7f4e04d5cd6ed4d8f76839cda9dce` |

Each listed file digest matched its exact path entry in the release's `mojmap.sources.sha256` manifest.

## Frozen independent source verdict

**No source difference in the assigned operation.** The direct player swim-pitch adjustment, exact double/float boundaries, gates, order of state sampling, authoritative pitch access, LUT index/width/mask, and inherited movement route are unchanged in this pair. The 1.20.1→1.20.2 line shifts reflect surrounding source changes; they do not change the audited operation. No source finding is warranted for this bounded operation.

Freeze record created before reading the assigned draft report. Hash this file and the draft report identity only after the freeze record is complete.

## Independent review of the frozen report

- Assigned draft identity was verified before opening: `c-swim-pitch-vertical-1201-1202-source/report.md`, SHA-256 `e1fa9633a4d79b2fe635a262e35f0e63cfa65de920e7ec9600409b4c1174f788`.
- The source freeze above was complete before report inspection. Its pre-report SHA-256 is `5efba94161d7d91cfb635b05d6b5a299ea32c70195bb039e97f2968c5e345625`.
- **Finding verdict: ACCEPT the bounded no-source-difference conclusion.** Independent exact-slice comparison confirmed `Player.travel` (1.20.1 lines 1446–1473 vs 1.20.2 lines 1450–1477), `Entity.calculateViewVector` (1448–1455 vs 1458–1465), `Entity.getLookAngle` (1970–1972 vs 2004–2006), `Mth` table plus `sin`/`cos` implementation (lines 20–48 in both), `LivingEntity` ordinary water travel route (2012–2047 vs 2046–2081), and `Entity.move` core (593–625 vs 595–627) compare textually equal after aligning the line windows. The same full source hashes in the table above are manifest-verified.
- **Report correction required: one statement is false.** It says the *full* `Player.java` and `Mth.java` files are byte-identical. The canonical full-file SHA-256s differ: `Player.java` is `873d82c6f5471d06812929e471d970fd973bfbe21638e4e348a6fcde45637dac` (1.20.1) versus `25f263692fd6b2a737aa813315022a0caf4e18c3661ba3bd706c6297f909f25c` (1.20.2); `Mth.java` is `201d17ea024637036761c2703e407d4ab1dc7651a1f4b00a857c09803754c797` versus `8a16560d3dd84853a2caaa658d6a106ea2e7f4e04d5cd6ed4d8f76839cda9dce`. These are not hashing errors: each digest matches its exact source-manifest path entry. The report's more precise statements that the compared operation/method blocks are unchanged are correct.
- Suggested correction: say that the compared `Player.travel` block and `Mth` LUT/`sin`/`cos` block are textually identical, while the full files differ elsewhere. No source finding is warranted for the bounded operation.
- Report readiness subclaims checked: both ready markers identify their exact Mojmap release and `status=ready`, both report 22 movement-diagnostic reviews, and the source, artifact and diagnostics manifest digests in the report match their canonical bytes.

## Terminal audit status

**ACCEPT WITH DOCUMENTED WORDING CORRECTION.** The audited movement behavior is a no-difference result for this bounded direct writer and route. The draft report should correct its two full-file byte-identity assertions before that report is treated as accurate evidence. This audit does not assert full-pair coverage, intermediate patch boundary, implementation acceptance, or runtime parity.
