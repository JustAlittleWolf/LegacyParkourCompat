# Independent source audit: Brewing Stand AutoJump fixture and 1.10 aliases

Verdict: **FAILED — the fixture outcome and 1.10/1.10.1 alias evidence are supported, but the immutable `source-check.md` is not accurate as written.** It needs two corrections: the 1.11 `BrewingStandBlock.java` is not byte-identical to 1.11.1/1.11.2, and the exact-touch movement collision is blocked first by the brewing-stand base plate, not the stem. These errors do not reverse the reported timer result for the supplied fixture.

## Immutable input and exact sources

Audited `domain-b-autojump/brewing-110-alias-source/source-check.md`, SHA-256 `d19910193938fb45a1029fb6b691b24f9ad4a482c7684715ca2ce849e92eb33b` (matches the assigned identity).

All six source roots are under `decompiled_minecraft/<exact-version>/ornithe-feather/`; the 1.10.2 and 1.11.x ready sidecars identify the exact `feather-gen2-<version>+build.2` mapping archive/Tiny pair, and each sidecar's source-manifest hash matches the corresponding `.sources.sha256` bytes. The 1.10 and 1.10.1 `.decompile-provenance.json` file hashes match the report, and every cited target file's embedded source hash matches its bytes.

## Verified control, then aliases

First comparing exact 1.10.2 against the supplied accepted 1.11 / 1.11.1 / 1.11.2 control:

- `LocalClientPlayerEntity.autoJump(float,float)` has body SHA-256 `5b124378adc6f3e0116153987d752d813a48d5c4916d07731993d10e537b4aa3` in all four sources. The callback snapshots X/Z, calls the superclass movement route once, then calls `autoJump` once with resolved X/Z displacement cast to float (`1.10.2:812–817`; `1.11.x:824–829`). The 1.11 signature change does not create a second player callback for the internal step retry.
- `PlayerEntity.initAttributes`, `PlayerEntity.getSpeed` and `LivingEntity.setSprinting` have the report's respective body hashes (`0342…a185`, `c818…c915`, `aa72…3f2c`) in all six exact sources. The fixture's sprinted forward speed therefore uses the same movement attribute path.
- Stand constants are equal: stem `(0.4375,0,0.4375)–(0.5625,0.875,0.5625)` and base plate `(0,0,0)–(1,0.125,1)`. The collision provider submits stem first, plate second (`BrewingStandBlock.java:36–37, 70–72` in 1.10.2 and corresponding 1.11.x lines). The single bottom slab is `(0,0,0)–(1,0.5,1)` when its half is not TOP (`SlabBlock.java`); cited slab files are byte-identical across all six versions.
- The 1.11.1/1.11.2 `World.getCollisions` block dispatch passes `forceShape=false`. `BrewingStandBlock.addCollisions` does not branch on that argument: it appends the same two boxes. Exact 1.11 predates that boolean and uses the same two-box order without it.

Then, for aliases, the eight cited 1.10.2 files—`LocalClientPlayerEntity`, `Entity`, `BrewingStandBlock`, `Block`, `SlabBlock`, `LivingEntity`, `StateDefinition` and `BlockProperties`—are byte-identical in 1.10, 1.10.1 and 1.10.2. Their embedded source records match. The three movement bodies above also match all six versions. This supports the 1.10 and 1.10.1 patch-line alias conclusion for this fixture.

## Fixture path and shape correction

The source supports the timer arithmetic in the report. With the supplied clipped actual displacement equal to zero, AutoJump takes its input/yaw fallback toward +X. At sprint speed about `0.13F`, `p=max(f*7.0F,1.0F/l)` is about `0.91`; width `q=0.6F` gives side-ray offset `q*0.5F` about `0.3`. From z about `0.8`, the side rays lie near z `0.5` and `1.1`; the first lies inside the stand stem's z interval. From x about `-0.3`, the probe reaches about x `0.61`, crossing the stem's x interval. The probe is lifted `0.51F`; the fixture's side ray is at y about `1.01`, inside the stem. Since stem is appended first, it is the first matching probe box and supplies `s=1.875`. At feet `0.5`, `u=(float)(s-minY)=1.375F`, so the exact `u<=1.2F` timer bound fails and no timer is armed.

For the ordinary full-floor control, the supplied post-step feet are `1.125`; the probe is above the plate and still reaches the stem. It yields `u=1.875-1.125=0.75F`, which is strictly above `0.5F` and no greater than `1.2F`, so the one-tick timer is armed.

However, the movement collision description in the immutable report is wrong. With the supplied player center x about `-0.3`, width `0.6F` gives the AABB maxX exactly `0`; its z span overlaps the plate, and its vertical span overlaps the plate. The brewing stand adds its plate collision box `(x,z=[0,1], y=[1,1.125])`. `Box.intersectX` clips a positive move when the player maxX is `<=` the obstacle minX, so the plate clips the move at x=0 before the stem's x-min of `0.4375` can constrain it. In the 0.6 step retry, player feet rise only from `0.5` to `1.1`; the plate top is `1.125`, leaving `0.025` vertical overlap, so that retry remains horizontally clipped by the plate. The downward Y clip at the bottom slab and the one outer player callback remain as described, and zero resolved X/Z still selects the same +X probe fallback. Correct the explanation to say that the plate blocks movement while the stem supplies the first AutoJump probe height.

## Exact source identity correction

The report's whole-file equality statement for the 1.11.x `BrewingStandBlock.java` is false. Actual SHA-256 values are:

- 1.11: `b2b56e8bb930874f22bb77e4676fda0250c2695a27e83c92ca796d3b52d6e00c`
- 1.11.1 and 1.11.2: `d890d11f9123d2f82f3358b732f7a93464a8e3117d8ae7183fb0a66527be5e18`

The source difference is observable at the collision-method declaration: 1.11 has no `forceShape` parameter, while 1.11.1 and 1.11.2 do. The two box additions and their order are equivalent for this fixture, but the files are not byte-identical. The whole-file hashes for `LocalClientPlayerEntity.java` and `SlabBlock.java` do match across the 1.11 anchor line.

Static source audit only; no simulation, build, tests, game launch or runtime-parity claim.
