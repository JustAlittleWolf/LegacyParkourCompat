# Independent verdict: 1.10.2→1.11 Elytra near-vertical look alias

## Verdict

**Agree with the bounded report: no source-confirmed delta in the compared Elytra steering math.** For the ordinary precondition reviewed here—a locally controlled player, dry, already fall-flying in the default chest Elytra configuration, finite state and a legal near-negative-vertical pitch—the exact sine-table lookup keeps the horizontal look magnitude `j` strictly positive at the vertical endpoint and in the pitch values aliased to that lookup slot. The unguarded negative-pitch divisions are therefore finite for this path. This verdict does not cover malformed state, activation, later movement resolution, or full-pair coverage.

The pre-move order agrees in both versions: update `fallDistance` under its velocity gate; read look and calculate `i`, `j`, `k`, and `l`; apply the vertical lift; apply the downward-pitch adjustment only under `velocityY < 0 && j > 0`; apply the separate negative-pitch correction (`o`, vertical velocity, then X and Z each subtracting a look component times `o` divided by `j`); apply the guarded horizontal steering; damp X, Y, Z; then make the first move call. Both bodies use the same statements, casts, and operand order through damping. The first move call is the boundary: 1.10.2 invokes `move(dx, dy, dz)` while 1.11 invokes `move(MoverType.SELF, dx, dy, dz)`. This audit draws no conclusion about the broader move implementations.

## Independent source trace

- **1.10.2, Ornithe Feather:** `decompiled_minecraft/1.10.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java:1332-1371`. The local-control and dry medium gates enclose `isFallFlying()` (1333-1336). The separate negative-pitch branch has no `j` guard (1356-1361), but runs before guarded steering and damping (1363-1371).
- **1.11, Ornithe Feather:** `decompiled_minecraft/1.11/ornithe-feather/net/minecraft/entity/living/LivingEntity.java:1386-1425`. Same gates and same calculations/order (1387-1424); only the subsequent move invocation differs at 1425.
- In both versions, `getLookVector()` calls `getRotationVector(1.0F)`; that exact tick delta selects current `pitch` and `headYaw` (`LivingEntity.java:1853-1865` and `1914-1926`). `PlayerEntity.serverTickAi` assigns `headYaw = yaw` (`PlayerEntity.java:409`, `:413`). `Entity.getRotationVector(float,float)` computes trig values as floats and constructs `Vec3d` from float products (`Entity.java:1148-1154`, `:1170-1176`).
- `MathHelper.sin/cos` use the same 65536-entry table and index formulas in both versions (`MathHelper.java:15-20`). At pitch `-90.0F`, the cosine lookup for the rotation vector indexes slot 32768: the float angle/index calculation reaches `(int)(x * 10430.378F + 16384.0F) & 65535 == 32768`. The entry is initialized with `(float)Math.sin(i * Math.PI * 2.0 / 65536.0)` (`:424`, `:428`), so slot 32768 is the small nonzero float from `sin(π)`, not zero. Multiplying that nonzero `h` by the yaw sine/cosine pair yields a nonzero X/Z pair for an ordinary finite legal yaw; squaring and summing the promoted double components makes `j > 0.0`. Pitch is clamped to `[-90, 90]` in both camera paths (`Entity.java:291-297`, `:300-306`), so the endpoint is ordinarily reachable. At `i < 0`, the separate pitch correction executes, with its division by this positive `j`.
- The movement precondition uses the existing fall-flying flag (flag 7 via `LivingEntity.isFallFlying()`, `:2088-2090` and `:2143-2145`). Equipment/activation behavior is not part of this audit.

## Exact inputs and integrity

Frozen input report: `C:/Users/Wolfi/.codex/visualizations/2026/11/17/01a1eb7b-b9ff-7a20-8686-0295dde20f26/c-glide-1102-111-near-vertical-look-alias/report.md`; SHA-256 at audit: `8d28ad8a0d2037250462b350dfd3455284fd97b07e4fd52d0e1c4a433270cfe5`.

The canonical local trees are `decompiled_minecraft/1.10.2/ornithe-feather/` and `decompiled_minecraft/1.11/ornithe-feather/`. Their ready markers identify exact version IDs 1.10.2 and 1.11, namespace `ornithe-feather`, Feather Gen2 build.2 artifacts and Vineflower 1.12.0. The markers' client SHA-256 values are respectively `7cdf7fcdc1c92584a233bf3c42bd7f0df1bdad3007d306831fe50410692be1e9` and `f0d11987ea743f55a2c03bf77216e6dc9baea7201cc7bf498ceb18043e984634`; the 1.11 provenance records Java 25.0.3+9-LTS (the 1.10.2 provenance records Java 25).

For each version, the source-manifest SHA-256 recomputed locally equals the ready marker: 1.10.2 `91b0f478acb7b6f13463c35b268a30d2806f583402631a56f54ce2eb70d1ec71`; 1.11 `8b3e5da58494f1dba77410960753b0bf646425a1ada491d8242cc71e6c7a59ca`. The 1.11 provenance JSON SHA-256 is `6f775c806fd1401a4d1756e9ef88da8d73185fe1d13598235f85c424405f4c40`, matching its ready marker. For every Java file cited in this audit, local SHA-256 matched that version's source-manifest entry:

| Version | `LivingEntity.java` | `Entity.java` | `MathHelper.java` | `PlayerEntity.java` |
|---|---|---|---|---|
| 1.10.2 | `d40dd476b6b68c6ce45b4202823475deb546ecda2284da330ff6724b33815e82` | `05da145effa19a6ef7934cc276e89226373b67c12f4ce89a8ce2183f29039f77` | `43b2a4e3931733dbc3538024b7182b024d874a5f22a269a6199c6b4ca8c59151` | `a055b84b98d98e828e177cad9bba47a1334ee9bf3ecd4331792979106235a302` |
| 1.11 | `1f2e31fa6a905ccc2a51a0338f2aaf448f2b0569f28644e8205d864e570d9b2b` | `3209feaacd1e798b521a193eeb081d92c96881a70a18254ef24a58e911a4fd97` | `97aa2499681fdd8cee6564f777310ee396bc447fef78c2273a990e9202d9641b` | `a0d787681de5caecda03fea1536dac0c07e6ca5cfbfe3b5fbb700cbe77c86fb6` |

## Scope and limits

This independent review covers only the ordinary finite near-vertical lookup and the Elytra steering expression order up to the first move invocation for exact 1.10.2 and 1.11 source. It is not a full-pair inventory, source-acceptance decision, implementation handoff or runtime-parity claim. No other report, wiki inventory, product implementation or Git history was consulted; no source was generated or modified, and no build, test or runtime launch was performed.
