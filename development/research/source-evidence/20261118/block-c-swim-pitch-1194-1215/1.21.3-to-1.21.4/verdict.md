# Independent source audit: 1.21.3 to 1.21.4 swim pitch Y writer

## Verdict

**Accepted as a bounded no-difference finding.** I independently compared the complete `Player.travel(Vec3)` override and the pitch-to-look-vector and inherited water-travel dependencies. The pre-`super.travel` swim-pitch Y writer is behaviorally identical across this pair. This verdict does not assert full-pair coverage, implementation correctness, or runtime parity.

The audited author report is bound by SHA-256 `715ebe272bc2f5502c162700352289d42ecf3f691ff59bf99c6b40d785ea7351`; I checked that hash before opening it. Its principal no-difference claim and source identities agree with this independent audit.

## Source identity and integrity

Both canonical inputs are exact `1.21.3` / `1.21.4` Mojmap trees. Their `mojmap.ready.json` markers say `ready`, give the exact release IDs and Mojmap namespace, and declare source counts and byte totals matching the trees (1.21.3: 5,655 files / 26,544,115 bytes; 1.21.4: 5,744 / 26,784,732). Provenance records identify the same preparation batch (`1.21.1,1.21.3,1.21.4,1.21.5`), Mojmap, Vineflower 1.12.0, Java 25.0.3+9, Gradle 9.7.1, Tiny Remapper 0.14.1, exact release metadata and client SHA-256 pins.

Recomputed hashes agree with each ready marker for `mojmap.sources.sha256`, `artifacts.sha256`, and `movement-diagnostics.txt`:

| Release | Source manifest | Artifact manifest | Diagnostics |
|---|---|---|---|
| 1.21.3 | `d673bb5464853e3a2a92ed9b1ffe1789d4e62c097bb884f67c336fcbb3b178ce` | `0587668e5c70bb06dacf3496a4442f66dc9cdcab23f2350b844f14259cc40dcf` | `065c0644d14a4ba2001713ba9a72470b414f01ab327aec772924709f3118f08f` |
| 1.21.4 | `f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0` | `1a0929ca8c88cfe7874f323918dfa3b044964007d3ff0d3943317bdb35caf841` | `fb5d4cfc7238ea634c47ad148e22539fec30a1b33bb5d3353ff8c34a3e44a421` |

Exact SHA-256 identities of the readiness and provenance records, recomputed from the canonical files:

| Release | Ready marker | Provenance | Client SHA-256 in ready marker |
|---|---|---|---|
| 1.21.3 | `72385aa99d249a56d742b9c07823c1155c74c39dda1fbc6eacc12c980cd12e02` | `24093437c0cd41d807ab0829068ef97828b65bbb95b38dfdc4f706883a8483d7` | `25190c556bc56d11070638ccab3b3c6efd76bb9684c92a2680a4de01c3c7138b` |
| 1.21.4 | `1c6617b7acf8bb357f77c6c6c276881c73d882bda1f93f6dd217bb54eb8aa4be` | `ab0439864c89f4e0dab09daae7b57ab973368eeb0320626822e06f5a9bce9fb9` | `c17c450c6e72cc51297daa57ce38f800aa01cf022b743daa21a0512d326d894e` |

Recomputed SHA-256 values for relied-on Java files match their per-release source-manifest entries:

| Source | 1.21.3 | 1.21.4 |
|---|---|---|
| `net/minecraft/world/entity/player/Player.java` | `a803203e92aa4729d5f5c9b16085b6a43ce51d9907d309eb96736e9c7c1340de` | `c45f41b9784ce50a88a498e84a13edcef0a23e175233d726ed7f90940f19ebc1` |
| `net/minecraft/world/entity/Entity.java` | `a93719c302a0381a972af75ea360465e2e3551708dd07c34d4d40b7e5173c2b9` | `05f18ef2ec0413fc010230407c812a11553eb5123b68d21b5d7b2c0c175698ad` |
| `net/minecraft/util/Mth.java` | `98dfb57e239348cf71bf00695b84299232bd5e0cfe206ee8c23a69b72f1099da` | `98dfb57e239348cf71bf00695b84299232bd5e0cfe206ee8c23a69b72f1099da` |
| `net/minecraft/core/BlockPos.java` | `aa1017d0cd97ddf59c45efa21e3c8970284fb237d6335e7e34f5627ed37a69b3` | `a01661d6880dab86f2adb8c8bc203484f63bc0e10eaa14e36525e7123a2d4d15` |
| `net/minecraft/world/entity/LivingEntity.java` | `087390495f4fdfd14b9e12230e7aea4fdc50913bb4d1d91119e72892881cfc52` | `e62ce650af0a5ac97e4d0a2ba7cad4ba68a8d098525609f8b5b6976a8ab0ae30` |

The `Player.travel(Vec3)` bodies are intact at 1.21.3 lines 1444–1468 and 1.21.4 lines 1447–1471. Evidence came from the canonical read-only trees at `decompiled_minecraft/<version>/mojmap/`.

## Independently checked behavior

- Both overrides branch first on `isPassenger()`. A passenger calls `super.travel(input)` directly and skips the swim-pitch block. Otherwise `isSwimming()` gates it.
- On the swimming path, both evaluate `double lookY = getLookAngle().y`, then `double factor = lookY < -0.2 ? 0.085 : 0.06`. The update predicate is the left-to-right short-circuit expression `lookY <= 0.0 || jumping || !level().getFluidState(BlockPos.containing(x, y + 1.0 - 0.1, z)).isEmpty()`. The fluid lookup runs only if the first two terms are false. The sample uses current position and preserves the double `+ 1.0 - 0.1` expression order; `BlockPos.containing` floors each coordinate through `Mth.floor`.
- If the predicate succeeds, both fetch current movement and set it to `movement.add(0.0, (lookY - movement.y) * factor, 0.0)`. This is a double-valued additive Y adjustment; X/Z are unchanged. The strict `-0.2` cutoff, inclusive `<= 0.0` comparison, constants, operand order and write placement match.
- `Entity.getLookAngle()` passes current `getXRot()` and `getYRot()` directly to `calculateViewVector`. The no-argument pitch getter returns backing `xRot`. The setter in both releases rejects non-finite values and stores `Math.clamp(value % 360.0F, -90.0F, 90.0F)`. Interpolated view getters are not used by this call.
- `calculateViewVector` matches: pitch and negated yaw are multiplied by `(float)(Math.PI / 180.0)` into floats; calls are `Mth.cos(yaw)`, `Mth.sin(yaw)`, `Mth.cos(pitch)`, `Mth.sin(pitch)` in that order; result is `(sinYaw * cosPitch, -sinPitch, cosYaw * cosPitch)`.
- `Mth`'s trig lookup source is byte-identical in both releases. `SIN` is `float[65536]`; entries 0 through 65535 are initialized from `(float)Math.sin(i * Math.PI * 2.0 / 65536.0)`. `sin(float)` indexes `SIN[(int)(angle * 10430.378F) & 65535]`; `cos(float)` indexes `SIN[(int)(angle * 10430.378F + 16384.0F) & 65535]`. Products and offset use float arithmetic, then cast truncates to `int`, then the 16-bit mask applies.
- After the local block, both Player overrides call `super.travel(input)` for non-flying players. Flying players save the already adjusted Y, call `super.travel(input)`, then restore Y to saved-Y times `0.6`; this order is unchanged. Inherited `LivingEntity.travel` in both releases gates on local control, samples fluid at `blockPosition()`, and calls `travelInFluid` when the player is in water or lava, affected by fluids, and cannot stand on that fluid. A swimming player in water meeting those gates therefore proceeds through ordinary water movement after the Player writer.

## Acceptance boundary

Accepted claim: no difference in the pre-super swim-pitch Y writer, its pitch-to-`lookY` computation, or immediate inherited water-travel dispatch between exact 1.21.3 and 1.21.4 Mojmap sources. Other Player tick paths and full-pair inventory remain outside this bounded audit.
