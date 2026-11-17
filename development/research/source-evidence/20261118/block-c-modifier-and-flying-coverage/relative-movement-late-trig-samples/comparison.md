# Ordinary relative-movement yaw trig: bounded source comparison

## Terminal status

**COMPLETED** for the exact sources listed below. This closes only the inspected endpoint evidence: 1.14.4, 1.15.2, 1.16.5, 1.17.1, 1.18.2, 1.19.4, 1.20.6, 1.21.10, 1.21.11, 26.1.2 and 26.2. It does not claim continuity through uninspected intermediate patches or releases. The earlier endpoints provide source contrast for the already accepted 1.21.10→1.21.11 trig change; that accepted claim is not re-reviewed here.

## Evidence bounds and provenance

All source paths below are under `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/`. Provenance manifests locally identify exact `versionId` and namespace. They are retained as read-only evidence; their SHA-256 values are included below. `1.14.4` uses `ornithe-feather` (the manifest records the dual Feather/Mojmap generation); `1.15.2` through `1.21.11` use `mojmap`; `26.1.2` and `26.2` use `unobfuscated`. Entity/Mth source SHA-256s are included per endpoint. No online mapping lookups, product source, Git history, wiki inventory or backlog were consulted. The 1.14.4 source names differ (`updateVelocity` / obfuscated helper and `MathHelper`) but the operation is the same relative-input yaw rotation; do not infer mapping-name identity from this.

| Exact version (namespace) | Entity source and inspected member | Mth equivalent and inspected member | SHA-256 of local provenance manifest |
|---|---|---|---|
| 1.14.4 (`ornithe-feather`) | `1.14.4/ornithe-feather/net/minecraft/entity/Entity.java`, `updateVelocity` / `m_51093791`, 1062–1076 | `.../util/math/MathHelper.java`, `sin(float)` / `cos(float)`, 24–30 | `7167b135e0222fcf58ff5f36a36fbb604d5068f63a59165c8c4d18ce7d9b21c9` |
| 1.15.2 (`mojmap`) | `1.15.2/mojmap/net/minecraft/world/entity/Entity.java`, `getInputVector`, 1063–1078 | `.../util/Mth.java`, `sin(float)` / `cos(float)`, 25–30 | `d4802d35ee2927a44d753871f184c3255c060eb94457a5c65a8bc121a087954e` |
| 1.16.5 (`mojmap`) | `1.16.5/mojmap/net/minecraft/world/entity/Entity.java`, `getInputVector`, 1075–1090 | `.../util/Mth.java`, `sin(float)` / `cos(float)`, 25–30 | `b71df61eb775134119881915198f87c4337bae409e1f0de8d8d6c007e062902d` |
| 1.17.1 (`mojmap`) | `1.17.1/mojmap/net/minecraft/world/entity/Entity.java`, `getInputVector`, 1176–1191 | `.../util/Mth.java`, `sin(float)` / `cos(float)`, 43–48 | `de197f77473955635e8c5730e113f227af2b8af9306b74e8a54a765eafc5a333` |
| 1.18.2 (`mojmap`) | `1.18.2/mojmap/net/minecraft/world/entity/Entity.java`, `getInputVector`, 1151–1166 | `.../util/Mth.java`, `sin(float)` / `cos(float)`, 43–48 | `ce14d5dfc2d51cffa7b1512878d908e37c930d414147be24e69439a7f1061ca9` |
| 1.19.4 (`mojmap`) | `1.19.4/mojmap/net/minecraft/world/entity/Entity.java`, `getInputVector`, 1181–1196 | `.../util/Mth.java`, `sin(float)` / `cos(float)`, 42–47 | `0efcfc6075852711cff5d62c3177258cba273c206c8a2d2642bdf3487c676b34` |
| 1.20.6 (`mojmap`) | `1.20.6/mojmap/net/minecraft/world/entity/Entity.java`, `getInputVector`, 1311–1326 | `.../util/Mth.java`, `sin(float)` / `cos(float)`, 48–53 | `ee53e67e7ab51069a0eaae5a0ce13d49a9f972bdc13e3c31e041afa77f4d26e8` |
| 1.21.10 (`mojmap`) | `1.21.10/mojmap/net/minecraft/world/entity/Entity.java`, `getInputVector`, 1590–1605 | `.../util/Mth.java`, `sin(float)` / `cos(float)`, 48–53 | `9a1db4c050c3caa28a1313f89dae9da3b28feaa5f8afba42cab798b47d7ad77c` |
| 1.21.11 (`mojmap`) | `1.21.11/mojmap/net/minecraft/world/entity/Entity.java`, `getInputVector`, 1608–1623 | `.../util/Mth.java`, `sin(double)` / `cos(double)`, 50–55 | `99c9fb741eb5e4ac8fb5780e2158da324d7c8b6afc179d494e5986f78fe29f72` |
| 26.1.2 (`unobfuscated`) | `26.1.2/unobfuscated/net/minecraft/world/entity/Entity.java`, `getInputVector`, 1659–1674 | `.../util/Mth.java`, `sin(double)` / `cos(double)`, 49–54 | `8d727f880cc91f4a297e148c5b75976303d051457334bc689268fbdf631a012a` |
| 26.2 (`unobfuscated`) | `26.2/unobfuscated/net/minecraft/world/entity/Entity.java`, `getInputVector`, 1746–1761 | `.../util/Mth.java`, `sin(double)` / `cos(double)`, 50–55 | `a328f5333a1b27aadde4422b23159a323ae219ccd26f11207c1a5461366f27d1` |

## Comparison

Across the listed endpoints, Entity's ordinary relative movement consumer first checks input squared length `< 1.0E-7`, normalizes only when length squared is `> 1.0`, scales the selected input by speed, obtains sine and cosine of yaw converted by multiplication with `(float)(Math.PI / 180.0)`, then forms `(x*cos - z*sin, y, z*cos + x*sin)`. The planar keyboard vector remains a `Vec3`/`Vec3d` input to this consumer. Its consumer operation order, normalization threshold, scaling point, and output rotation arrangement match at these endpoints. This is not the gliding look-vector path.

The trig lookup implementation differs at the 1.21.10 / 1.21.11 endpoint boundary: 1.21.10 and all earlier listed endpoints call float `Mth.sin(float)` / `cos(float)`, where the scaled float is cast to `int` before masking to 65535 (for example 1.20.6 Mth.java:48–53). 1.21.11 and both 26.x endpoints call double overloads: the angle expression in Entity is still computed as `float` because both operands are float, then widened to double at the call; Mth multiplies by the double scale `10430.378350470453`, casts to `long`, then masks (`1.21.11/Mth.java:50–55`; same structure in 26.1.2 and 26.2). Thus both the scale multiplication precision and integer-conversion type differ. The movement vector products happen after the table results are returned and retain double component arithmetic.

## Exact ordinary finite witness

Take yaw `-168.75F` degrees, speed `1.0F`, and the ordinary planar forward input `(0.0, 0.0, -1.0)` (already unit length). Entity's float conversion produces `-2.9452431F` radians.

* The 1.21.10 float path multiplies that float by `10430.378F`; truncation toward zero gives sine index `34817`. Its cosine expression adds `16384.0F` before truncation and gives index `51201`.
* The 1.21.11 double path widens the same float radians, multiplies by `10430.378350470453`, and casts to `long`; truncation toward zero gives sine index `34816`. Adding `16384.0` in double gives cosine index `51200`.

The indices differ by one for both lookups, so the returned sine and cosine table samples differ; with this forward input, the rotated x component is `sin` and z component is `-cos`. This is an ordinary finite player movement input and shows a concrete movement-vector difference at the yaw-rotation consumer.

The narrow stable applicability gate is: on the ordinary planar relative-input path, after `float radians = yaw * (float)(Math.PI / 180.0)`, the selected float and double Mth paths yield different masked sine or cosine lookup indices. This gate preserves the actual casts and scale arithmetic; a changed overload alone does not imply a differing result for every yaw. The witness above satisfies it. Endpoint evidence establishes the older float route at all listed endpoints through 1.21.10, and the double route at 1.21.11 and both 26.x endpoints. It does not prove the exact introducing patch before 1.21.11 or continuity across uninspected releases.
## Source file hashes

The compared `Entity.java` and trig implementation source files were hashed locally with SHA-256. Hashes are retained in this table to bind line references to the inspected payloads.

| Version | Entity.java SHA-256 | trig source SHA-256 |
|---|---|---|
| 1.14.4 | `7315a496c195da767de9d4936d3adb6efc3c419dc0f0e95d6f32781b0da1ba55` | `e39d5dfc69c17a9032be086d7d4d14cc63525962a0ff83615b1befdcca599843` |
| 1.15.2 | `191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e` | `a842059e498147d368126c896f0036916a90bdd125a09b09f9fe6658f2aaf0a4` |
| 1.16.5 | `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666` | `0e4424ffa12923314c309ae13e012768271dfcace4b5a84727f74e8170afe290` |
| 1.17.1 | `ab28e1fba924771ec048140dfd293ee5a46a7dfe81f71a1a0b1aecc1927232de` | `24515c4549e01e985017227dccf7159166a675b232be9135d5f896022a9cb113` |
| 1.18.2 | `2228fdaca5793171cbd94038306d571a6ada78ca96f5734efb4cada5b744c10a` | `32747c5b09fc184baae356e39a0088fd66c9f08f69d9e98b67c19e1de6f1bb2e` |
| 1.19.4 | `3667fee610cbc5f58012e3a8fb8d6c4849f649fb7fe5300595158112d8b4b58b` | `201d17ea024637036761c2703e407d4ab1dc7651a1f4b00a857c09803754c797` |
| 1.20.6 | `71cd6b9f6c002684154dce11d3745e8714d82f13c8e1b3aa56743930131c18f3` | `f1b2d62f18b4b20ffad5843f40d34d8b5f9c40eee3e106dfe275859a24cfb18d` |
| 1.21.10 | `8361dbb86fe6c975d21f69d008377b6f191669be751150c842517e9d0346fa18` | `d2a84fa876d78eb9693b2625ff5dc2eb8568ea33a974b4368effb03b4390e7f1` |
| 1.21.11 | `32314478c6036fa9f3f3cc409c61c622eefc5a282d60e1011a1a33cc29cf18a3` | `1b547c6b896c9b149d4bc03e3ed106ece485d7ac481a792ae30f600d0239726c` |
| 26.1.2 | `8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf` | `19d00e9745368c34692d0dfd7eddec07797790ac7f6e0bc579208b7a4b682d33` |
| 26.2 | `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5` | `30455c684f2401c79290847822bca82e77162c4a2bcf8618e85cac9898a2dc17` |