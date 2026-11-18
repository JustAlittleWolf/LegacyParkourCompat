# Independent exact-source audit freeze: 1.14 → 1.14.1 swim pitch vertical writer

## Freeze declaration

This audit froze the canonical exact-version Yarn sources, the hidden decompile provenance manifests, relevant cached-input pins and every cited source byte before opening the assigned author report. The source-only trace below was completed independently from those sources. No wiki, product implementation, backlog, Git history, network, generated output, build, test or runtime evidence was consulted.

## Canonical source identities

Canonical root: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\<version>\yarn\` (read-only).

| Release | Mapping identity | Hidden manifest SHA-256 | Source count | Yarn metadata/pin SHA-256 | Upstream Yarn / intermediary input SHA-256 | Client / version metadata SHA-256 |
|---|---|---|---:|---|---|---|
| 1.14 | Yarn `1.14+build.21`, intermediary 1.14 | `EA70A955A12F377CD3B9E8D5F346F4625B044D55DA726B6BC472405AE91F4341` | 3152 | metadata `303A1E22AD9D0E9244057AE04288F2BE1E2D608A306AAA4572D4245DB531873D`; pin `A7840F9F1BF0FC985B7BFF6831C685078F687E87F1AD82BAA4BAD6FBA10A0C73` | Yarn `0233edadf36a28099f6bb86e920c423b728ab5d75618da9046d92d287f12de64`; intermediary `45e354d58c74473865054faa62a229e30f46ba2019000affdb3fb748df3fde17` | client `93907cbf0655aa5f10d049efa4d745f4250eecc5d0d5d1268046eec3144f323a`; version metadata `3d05fc911c8559105767713365ea1c29bbbeccf57214f286c86a598ce51f22a6` |
| 1.14.1 | Yarn `1.14.1+build.10`, intermediary 1.14.1 | `308537C5ADEC3104C5CA243EB5010554F00978635ED74F0D7801C804DD4D4907` | 3155 | metadata `6D026A20D6165444F67DF52DDCC29F575F0267AB4B545C3BB427E5803C434873`; pin `118C793725D5E1EB4F64F7246C92FC16DF1AB1194F040C9850CF8443952F7515` | Yarn `f9462af25135abeb22cbdec829cc8618b1a8dd902f17259abfbe7399a222a630`; intermediary `486f4e72a87d697c5d9e155a49158bec28117faa73e3f1201c3f8a616b42569d` | client `7194d1326cf796f62ac55f3cb56f851c232895639ae27510f3207d5a73f91814`; version metadata `71c39840d012fcbe8fedb7be876bd008365b62ec7b1c9c5c7bd34d9a29c8ca22` |

For each row the on-disk `client.jar` and `version.json` matched the listed SHA-256 and its `.pin.json` URL/SHA record. The manifest input hashes also match those cache pins. Both manifests identify pipeline `lpc-decompile-v1;threads=1;generic=true;ascii=true;synthetic=remove;indent=4;inner-warnings=false;runtime=false;prefix=net/minecraft,com/mojang;remap=ignoreConflicts,renameInvalidLocals,rebuildSourceFilenames,fixPackageAccess` and runtime `Eclipse Adoptium:25.0.3+9-LTS:Windows 11:amd64`. The full manifest digests bind all remaining pinned inputs, tools, options, runtime identity and source hashes.

Cache pin sidecar SHA-256s:

- 1.14: `client.jar.pin.json` `82112A733C1EB219A120B11168C4A813A635FDA8E02DF9F9987F8284072997F0`; `version.json.pin.json` `3FA9A617193E2212A2FA2867D7759F5407306CFD5DC78DDAAAE163EFBD97A974`.
- 1.14.1: `client.jar.pin.json` `60755AEC505EBB7DBDD1DC4D8AF654A0DE4D4BD11FCEE4BE14BB33713D77C3A8`; `version.json.pin.json` `B9937A6C55F93AA63ABDCF66FFADF739AC2477CE8A560A67656FBEF74AE22AD6`.

## Frozen cited source bytes

Each file hash below was independently recomputed from the canonical source and matched its relative-path entry in that version's hidden `.decompile-provenance.json`.

| Relative source path | 1.14 SHA-256 | 1.14.1 SHA-256 |
|---|---|---|
| `net/minecraft/client/network/ClientPlayerEntity.java` | `92D0F97A9293F7BD57D49AF00B70F13D78FC8B2202E7ED67EDCFED917E0071BE` | `6AF7E75E80619AD77CE2C84692136B0A1D562A44D0E7910DCC3F6040B2DC9933` |
| `net/minecraft/entity/player/PlayerEntity.java` | `EBDA4AD25A265F8C85E88345EFCCE753B78E248A29A9B910C5CFD311A4A69319` | `EBDA4AD25A265F8C85E88345EFCCE753B78E248A29A9B910C5CFD311A4A69319` |
| `net/minecraft/entity/LivingEntity.java` | `8889E22779B9124998931BC97CE8B0D183D335C32A9BCBB089E49DE65882BAE6` | `331E2D7DFA936A6F7197910BA374C4A60B479C8565FF7C64A4D6A9E1E8B52DDA` |
| `net/minecraft/entity/Entity.java` | `F52483D5E1434114B81B1C353E89F246D1557BDA7A95400832F5650B89CC744A` | `CE429699F2D8F0A54F4BE516783A474C6C7808572950164508CEED0BAF68ED5B` |
| `net/minecraft/util/math/MathHelper.java` | `21ED846AC48A5A2FBCDB2AC31FD5B74A461485EFFD881112C903C09D9297504A` | `21ED846AC48A5A2FBCDB2AC31FD5B74A461485EFFD881112C903C09D9297504A` |

The independently byte-compared matching operation/call-graph slices are `Entity.java` 335–355, 889–901, 1180–1188, 1635–1637 and 1765–1767; `LivingEntity.java` 1763–1905 and 2197–2205; `ClientPlayerEntity.java` 471–474, 607–620 and 762–765. `PlayerEntity.java` and `MathHelper.java` are byte-identical across the pair.

## Frozen technical trace and bounded result

In both releases, `ClientPlayerEntity.tickMovement` updates local input (lines 607–620) and then calls its superclass movement tick (762); its `canMoveVoluntarily()` is `true` (471–474). `PlayerEntity.tickMovement` delegates to `LivingEntity.tickMovement`; that method calls `this.travel(new Vec3d(sidewaysSpeed, upwardSpeed, forwardSpeed))` at 2204. Dynamic dispatch reaches `PlayerEntity.travel`.

Before that movement path, `Entity.baseTick` reaches its fluid/swimming refresh (Entity lines 335–355, 889–901). A non-flying `PlayerEntity` reports swimming only when not a spectator and the base flag is set (PlayerEntity 1739–1741); ordinary swim refresh requires sprinting, water and no vehicle (Entity 895–900; `PlayerEntity.updateSwimming` clears the flag for flight at 1393–1398). Thus this bound is an active, locally controlled, nonpassenger swimmer, not all players touching water.

At the start of `PlayerEntity.travel` (1362–1372), `isSwimming() && !hasVehicle()` gates the writer. `g` is the `double` Y component of `getRotationVector()`. `Entity.getRotationVector()` supplies current pitch and yaw (1635–1637); its helper computes pitch radians as `float f = pitch * (float)(Math.PI / 180.0)` and builds vector Y as `-MathHelper.sin(f)` (Entity 1180–1188). In the helper, the 65,536-entry float table is initialized by `fs[ix] = (float)Math.sin(ix * Math.PI * 2.0 / 65536.0)`; `sin(float)` indexes `SINE_TABLE[(int)(f * 10430.378F) & 65535]` and `cos(float)` offsets the same truncated `int` index by `16384.0F` (MathHelper 11–29). The float-to-int conversion truncates toward zero, then `& 65535` wraps to the table range. The pitch-to-Y path consumes the sine result as float before it widens into `Vec3d`'s double component; yaw/cosine do not change this Y component.

The writer selects double `h = 0.085` only when `g < -0.2`, otherwise `0.06`. It runs only when `g <= 0.0 || jumping || !fluidStateAt(x, y + 1.0 - 0.1, z).isEmpty()`. When admitted it adds `(g - currentVelocityY) * h` to existing Y velocity with `Vec3d.add(0.0, ..., 0.0)`; it does not replace Y with `g`. Since pitch radians and LUT sine make looking upward produce positive `g`, upward pitch alone is suppressed unless jumping or fluid is in the queried block above; horizontal/downward `g <= 0` proceeds. The order is this addition first, then `super.travel(arg)`.

For an ordinary in-water, non-flying active swimmer, `LivingEntity.travel` passes its voluntary-movement gate, bypasses the dry/lava branch and takes the water branch (1763–1773, 1859–1905). It moves with the adjusted velocity, then multiplies Y by `0.8F`; active swimming is sprint-gated, so the later `!isSprinting()` gravity-adjust branch is skipped. This records the vertical writer's position in the tick without tracing collision internals.

**Independent pair verdict before author-report review:** no source difference for this bounded operation. The complete `PlayerEntity.java` writer/predicate file and `MathHelper.java` float table are byte-identical, and every compared caller, pitch-vector, fluid/swim-state and water-travel slice is byte-equal. No 1.14 → 1.14.1 change is supported for this bounded operation by the frozen exact sources. This does not assert parity for other swim behavior or intermediate releases.

Freeze completed before author report inspection. The author report has not yet been opened.
## Post-freeze author-report audit

The assigned author report was opened only after the above evidence and trace were frozen. Exact report identity verified: `c-swim-pitch-vertical-114-1141-source/report.md`, SHA-256 `7731175b1093bb293fc61962f71677a444cbc3df3ab0c886cdb3e7b76bad871e`.

**Audit verdict:** its no-difference conclusion for `PlayerEntity.travel(Vec3d)` is supported. The source report has one sign-direction error in its interpretation: it says upward-looking is `g <= 0` and downward-looking is `g > 0`. With Minecraft pitch in `getRotationVector(float,float)`, `pitchRadians = pitch * (float)(Math.PI / 180.0)` and Y = `-sin(pitchRadians)`: negative pitch (looking up) gives positive `g`; positive pitch (looking down) gives negative `g`. Therefore the writer's unconditional `g <= 0` case includes horizontal/downward looking, while upward-looking `g > 0` is gated on jumping or a nonempty fluid state at the sampled block. At pitch 0, the source's table lookup yields zero and the `g <= 0.0` predicate is true. This correction follows directly from frozen `Entity.java` 1180–1188 and `MathHelper.java` 11–29.

The report's cited position-flooring claim was also checked against exact source: `BlockPos(double,double,double)` delegates to `Vec3i(double,double,double)`, which calls `MathHelper.floor` on each coordinate. In both versions, `BlockPos.java` SHA-256 is `cb812b182cb0cb87eb1ab82307922d4bf4513837cd85f9c76dd1ed0ce509777a`, and `Vec3i.java` SHA-256 is `534d56ad4fd83ee0f2c2187afa2e34145de41944d1d1ad092e1a8bc5e9833c61`; both match each version's hidden manifest and are byte-identical across the pair.

The author report's exact source inventory and all checked claims otherwise align with the independent trace. The report does not spell out the float LUT implementation; the frozen independent trace above supplies that path. No source finding is accepted for this pair/operation. Discovery acceptance, pair-wide coverage, implementation, and runtime parity remain separate.
### Complete live client tick reachability check

To make the locally controlled tick path explicit, I also checked the exact caller chain. `ClientWorld.tickEntity` admits a `PlayerEntity` and calls `arg.tick()` while its active flag is set (`ClientWorld.java:140–160`). `ClientPlayerEntity.tick()` requires its block to be loaded and calls `super.tick()` (`ClientPlayerEntity.java:172–175`); `PlayerEntity.tick()` calls `super.tick()` (`PlayerEntity.java:191–218`); `LivingEntity.tick()` calls `this.tickMovement()` (`LivingEntity.java:1955–2024`). Dynamic dispatch therefore enters the local `ClientPlayerEntity.tickMovement()`, which processes input and calls `super.tickMovement()`, reaching `LivingEntity.tickMovement()` and its `this.travel(...)` call. All four caller slices are byte-equal across the pair. `ClientWorld.java` is SHA-256 `86b4b5451115fde93bc1f6546dd07c93a373b09232870c0721f524216556368f` on both sides and matches both manifests; the other three files and relevant source identities are already listed in the freeze.