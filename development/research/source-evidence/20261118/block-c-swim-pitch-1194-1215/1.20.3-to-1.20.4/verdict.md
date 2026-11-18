# Independent source audit: 1.20.3 → 1.20.4 swim-pitch Y writer

## Verdict

**ACCEPTED — no source difference in the assigned operation.** The author report's bounded conclusion and its described control flow match the exact paired Mojmap source. The full `Player.java`, `Entity.java`, `LivingEntity.java`, and `Mth.java` bytes are identical across the pair; client-player dispatch and the sampled block-position dependency are also identical. This accepts the no-difference result for this operation only. It does not establish an intermediate patch boundary or runtime parity.

## Immutable inputs and source identity

The author input was opened only after independently freezing the paired source. Its SHA-256 matches the assignment: `c-swim-pitch-vertical-1203-1204-source/report.md` = `b2e481fa99996d2c7c560f0c64fca460d297b491f443e6e9ed5be9b1547926bc`.

- 1.20.3 source root: `decompiled_minecraft/1.20.3/mojmap`. Its hidden `.decompile-provenance.json` SHA-256 is `c3d605e55b287e800a86af8567a75edcb009d1a9a1744cfcb4e6f47667dcee0f`; its embedded source map and exact input pins were checked. The cached `client.jar`, `client_mappings.txt`, and `version.json` each match their adjacent pin (`bef219…c655`, `ad03c8…1e5f`, and `05e727…8fc2`).
- 1.20.4 source root: `decompiled_minecraft/1.20.4/mojmap`. The root `mojmap.ready.json` identifies version `1.20.4`, mapping `mojmap`, and 5,048 source files; its SHA-256 is `6225b97979420cc743c8b7156a0ce512ef27d0dbf48bfffbc40d3e1c8cb044d1`. Its declared source-manifest SHA-256 (`fd3c8668483e5ff208c847474f6cbd3952a909c4aa602ed98b24e3d11a9602a1`) and artifact-manifest SHA-256 (`ee3efc771d264c0bc49d5472abb9763a646f8d221841d0a61201ecd991cb3948`) match the current manifests. `mojmap.provenance.json` SHA-256 is `10914d9b7b3fc29de066ca24f7d905e4d4f0a451a6c81729a2d1981addfc1385`; it records the exact 1.20.4 run and `mojmap` namespace. The artifact manifest pins the 1.20.4 client (`9221ab…b838`) and mappings (`ad03c8…1e5f`).

For each row below, current bytes matched the applicable 1.20.3 hidden provenance entry and 1.20.4 source-manifest entry; paired hashes are equal:

| Exact source | SHA-256 on both sides |
| --- | --- |
| `net/minecraft/client/player/LocalPlayer.java` | `bb5cbfb03656a1866bb77c00431618befe792081223b35db4bd121ecbb151fd5` |
| `net/minecraft/client/player/AbstractClientPlayer.java` | `759f8f03b89dab1f6b65c73e1e25a68ae6a6083cb49b5d2719136edda7edf696` |
| `net/minecraft/world/entity/player/Player.java` | `218da60bc4f8c9279f56eb87e2cf2c0af79562fbe3d818a408429c656efb95dd` |
| `net/minecraft/world/entity/LivingEntity.java` | `f7bc53db24c1798f19f9bd6f6356c86d5e560e9c8e8aac60decaf15cc785e07d` |
| `net/minecraft/world/entity/Entity.java` | `07383522bff169938136638ef8c3244ca511b56ca4266913524f99f9821331b9` |
| `net/minecraft/util/Mth.java` | `f211066a32f5fd2ff3605d0db66f79d7811d7c90ea205caf6b5ce957c0652075` |
| `net/minecraft/core/BlockPos.java` | `a91e96745a49cdb2ec18be2ac7986071d25ed2de2a04c8f779ca41cfcccc29fc` |

## Independent trace

- `Player.travel(Vec3)`, `Player.java:1451–1473`: before either superclass branch, swimming and nonpassenger players read `getLookAngle().y`. The gain is `0.085` when that double is below `-0.2`, else `0.06`. The OR terms run left to right: `lookY <= 0.0`, then `jumping`, then the negated fluid-empty check at `BlockPos.containing(x, y + 1.0 - 0.1, z)`. Thus the above-player sample is reached only for positive look-Y while not jumping, and passes when that cell contains fluid. A passing gate reads current velocity and writes `current.add(0.0, (lookY - current.y) * gain, 0.0)` before `super.travel`.
- `Entity.getLookAngle()`, `Entity.java:2005–2006`, uses current `getXRot()` and `getYRot()` directly. `calculateViewVector`, `Entity.java:1459–1466`, computes the pitch radians with float multiplication and returns Y as `-Mth.sin(pitchRadians)`; yaw contributes only X/Z. The resulting float Y is widened to the `Vec3` double component.
- `Mth.java:27,31–35,47–53`: the sine table is `float[65536]`, initialized slot by slot from `(float)Math.sin(index * Math.PI * 2.0 / 65536.0)`. `sin` multiplies by float scale `10430.378F`, casts to `int` (Java truncation toward zero), then masks with `65535`; `cos` uses the same path with `16384.0F` added before the cast. These angle, table-width, initialization, index, and widening operations are identical because the full Entity and Mth files have equal hashes.
- `BlockPos.containing`, `BlockPos.java:81–83`, preserves argument order and the source expression `getY() + 1.0 - 0.1`; `Mth.floor(double)`, `Mth.java:64–67`, casts then corrects downward when the original value is below the cast, including negative coordinates. Both dependency files are byte-identical across the pair.
- For an active local player, `LivingEntity.aiStep` dispatches virtual `travel` (`LivingEntity.java:2601–2604`). After the pre-super writer, `Player.travel` calls `super.travel`. In its local-control water branch, `LivingEntity.travel` performs `moveRelative` then `move(MoverType.SELF, getDeltaMovement())` (`LivingEntity.java:2035–2068`). The inherited Entity movement therefore consumes the already-shaped velocity; it is not replaced by the pitch writer. This call order is identical in both releases.

No mismatch was found in the report's claims about the assigned writer, its branch short-circuit, authoritative angles, trigonometric lookup, sample position conversion, or inherited movement. No additional difference finding is warranted.

## Signed audit identity

Independent auditor: Codex subagent `/root/c_swim_pitch_vertical_1203_1204_audit`  
Verdict binds the exact report SHA-256 above and the source/manifests as hashed above.  
Scope: ordinary active local, nonpassenger player; the pre-super swim-pitch Y correction and its angle, sample, and inherited-move dependencies.