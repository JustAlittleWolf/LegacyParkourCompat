# Independent source audit: ordinary relative-movement yaw trig

**Terminal status: COMPLETED — ACCEPTED within the bounded evidence below.**

## Bound and identity

Reviewed the immutable source comparison `relative-movement-late-trig-source/comparison.md`, SHA-256 `ae6ad5078165aae9d6e8d8368bf697df864bf70212b69839a2c9aec57c565f13`. Independently checked the exact canonical decompiled sources for the seven named older endpoints in `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/`, their local outer provenance manifests, source paths, relevant method bodies, and stated source hashes. No intermediate releases or patches were inspected. The accepted 1.21.10→1.21.11 finding was not re-reviewed; those exact sources were used only as contrast for the older-side witness calculation.

## Verdict

The bounded claim is supported: at each named endpoint below, ordinary relative movement converts yaw to radians using float arithmetic and passes a `float` to the float `sin`/`cos` lookup methods. Those methods multiply by `10430.378F`, cast the product to `int` (truncation toward zero), then mask with `65535`; cosine adds `16384.0F` before that cast. The consumer stores trig results as `float`, while the rotated `Vec3`/`Vec3d` components are `double`. The 1.14.4 helper has different names and mapping family, but the inspected calculation and type behavior match this operation.

| Exact release / mapping | Relative-input consumer and line range | Trig lookup and line range | Provenance SHA-256 | Entity SHA-256 | Trig source SHA-256 |
|---|---|---|---|---|---|
| 1.14.4 / ornithe-feather | `entity/Entity.java`, `m_51093791`, 1067–1076 | `util/math/MathHelper.java`, `sin(float)` / `cos(float)`, 24–30 | `7167b135e0222fcf58ff5f36a36fbb604d5068f63a59165c8c4d18ce7d9b21c9` | `7315a496c195da767de9d4936d3adb6efc3c419dc0f0e95d6f32781b0da1ba55` | `e39d5dfc69c17a9032be086d7d4d14cc63525962a0ff83615b1befdcca599843` |
| 1.15.2 / mojmap | `world/entity/Entity.java`, `getInputVector`, 1068–1077 | `util/Mth.java`, `sin(float)` / `cos(float)`, 25–30 | `d4802d35ee2927a44d753871f184c3255c060eb94457a5c65a8bc121a087954e` | `191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e` | `a842059e498147d368126c896f0036916a90bdd125a09b09f9fe6658f2aaf0a4` |
| 1.16.5 / mojmap | `world/entity/Entity.java`, `getInputVector`, 1080–1089 | `util/Mth.java`, `sin(float)` / `cos(float)`, 25–30 | `b71df61eb775134119881915198f87c4337bae409e1f0de8d8d6c007e062902d` | `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666` | `0e4424ffa12923314c309ae13e012768271dfcace4b5a84727f74e8170afe290` |
| 1.17.1 / mojmap | `world/entity/Entity.java`, `getInputVector`, 1181–1190 | `util/Mth.java`, `sin(float)` / `cos(float)`, 43–48 | `de197f77473955635e8c5730e113f227af2b8af9306b74e8a54a765eafc5a333` | `ab28e1fba924771ec048140dfd293ee5a46a7dfe81f71a1a0b1aecc1927232de` | `24515c4549e01e985017227dccf7159166a675b232be9135d5f896022a9cb113` |
| 1.18.2 / mojmap | `world/entity/Entity.java`, `getInputVector`, 1156–1165 | `util/Mth.java`, `sin(float)` / `cos(float)`, 43–48 | `ce14d5dfc2d51cffa7b1512878d908e37c930d414147be24e69439a7f1061ca9` | `2228fdaca5793171cbd94038306d571a6ada78ca96f5734efb4cada5b744c10a` | `32747c5b09fc184baae356e39a0088fd66c9f08f69e98b67c19e1de6f1bb2e` |
| 1.19.4 / mojmap | `world/entity/Entity.java`, `getInputVector`, 1186–1195 | `util/Mth.java`, `sin(float)` / `cos(float)`, 42–47 | `0efcfc6075852711cff5d62c3177258cba273c206c8a2d2642bdf3487c676b34` | `3667fee610cbc5f58012e3a8fb8d6c4849f649fb7fe5300595158112d8b4b58b` | `201d17ea024637036761c2703e407d4ab1dc7651a1f4b00a857c09803754c797` |
| 1.20.6 / mojmap | `world/entity/Entity.java`, `getInputVector`, 1316–1325 | `util/Mth.java`, `sin(float)` / `cos(float)`, 48–53 | `ee53e67e7ab51069a0eaae5a0ce13d49a9f972bdc13e3c31e041afa77f4d26e8` | `71cd6b9f6c002684154dce11d3745e8714d82f13c8e1b3aa56743930131c18f3` | `f1b2d62f18b4b20ffad5843f40d34d8b5f9c40eee3e106dfe275859a24cfb18d` |

Manifest `versionId` values matched the exact named release, and namespaces matched the table. Each listed source-file hash matched the bytes read. The 1.14.4 provenance manifest records the `ornithe-feather` namespace; its helper name `m_51093791` and `MathHelper` are retained as mapped, rather than treated as name-equivalent to later Mojmap methods.

## Finite witness sanity check

The ordinary forward input `(0.0, 0.0, -1.0)` with speed `1.0F` and yaw `-168.75F` is finite and unit length. The consumer expression produces float radians `-2.9452431F` (exact float value `-2.9452431201934814`). On the older float route, sine's scaled float is `-30719.998046875`; cast-to-int yields `-30719`, masked index `34817`. Cosine's float expression yields `-14335.998046875`; cast-to-int yields `-14335`, masked index `51201`. On the double-overload source contrast, the widened float radians times `10430.378350470453` gives `-30720.000077738136`; cast-to-long yields `-30720`, masked sine index `34816`; adding `16384.0` gives `-14336.000077738136`, cast-to-long `-14336`, masked cosine index `51200`. The sign of both raw indices is negative before masking. For forward input, the output x component is `sin` and z is `-cos`; the adjacent lookup indices sample distinct table entries. This validates the arithmetic witness only; it does not reopen or re-accept the accepted transition claim.

## Limits

This verdict covers only the seven exact older endpoints named above, plus the witness arithmetic and limited source contrast stated above. It makes no claim about uninspected releases, exact patch introduction, continuity between endpoints, or runtime parity. No code, tests, source generation, or runtime action was performed.
