# Independent blind audit: yaw precision, 1.14.2 to 1.14.3

## Verdict

**No difference is established in the audited yaw operations.** The ordinary yaw writes, current/previous yaw ordering, and the yaw-to-horizontal-velocity calculation reached by a dry player on ground or in air are the same in the two exact source sets. The release pair does not justify a yaw-specific movement change in this bounded slice. This verdict does not freeze the complete player tick or the full 1.14.2–1.14.3 pair.

## Exact source identity and integrity

Canonical inputs are `decompiled_minecraft/1.14.2/yarn/` and `decompiled_minecraft/1.14.3/yarn/`, each with its local `.decompile-provenance.json`. The listed Yarn builds and local source hashes below identify the nested pins; hashes were recomputed from the cited bytes and match the `sources` entries.

| Release | Yarn / intermediary pins from manifest | Yarn version metadata / Mojang version metadata SHA-256 | Client artifact SHA-256 | Provenance manifest SHA-256 |
|---|---|---|---|---|
| 1.14.2 | Yarn `1.14.2+build.7`, `3c91c928d10797ba688b1f7e61063fd4bc56a3ee0f66fa2609ef960ec08b239b`; intermediary `1.14.2-v2`, `f73883d007c64858ae0f26533fe47ec5d3a051cede6892c8d6531fc5122e459e` | Yarn metadata `a65478802722f65b4dfec15e8a732872f2836e1e9b7ae95d7086c601b07f1d81`; version metadata `237aa8d499b71bb17122cbee40b8748efcd5ac9d8362c68c89e99ab0703e66b8` | `9b740c9fd4955981125ebd3cd086baa8b4392963d043a3480b5b942a96637c25` | `786546f99684423cc2563be45c9c3334dfcc2c7db58ddb74ba39ce403bd0869a` |
| 1.14.3 | Yarn `1.14.3+build.13`, `ad9e77f907cfc6780fb348ad4b3a948ffc8cea3bfe50abcc792428645e49e06e`; intermediary `1.14.3-v2`, `f2e9990c00fa87ddc7158a95afb735a947ae26d1997b52916226e11c612542a9` | Yarn metadata `85ad2c9e3ff892f5a6d5b774b869ed6b6dbdd26de9a46d1a37b5c739e73c8766`; version metadata `ebf2df95d9bd83c6687f287d5a6f5f41f74bc1b39159fcbb74ff6d1d19d8464c` | `4063062677433988e012fba53e42e4c21adb6919ac7f061f19f3c6e75b334e07` | `81ea0021eb122d4e3340e20d47d6ecb7079365ba767a038d26a8264cddacc28a` |

| Cited source | 1.14.2 SHA-256 | 1.14.3 SHA-256 | Manifest match |
|---|---|---|---|
| `net/minecraft/entity/Entity.java` | `7c0e9cab0842a45e42fa9f27c9661de3fd0fd076c35ff989aa0477198eb38edc` | `9b204a5f9af6ca50c09db4b6185ff37c39980c1a8cd1b5fd004713ecffae5c14` | both |
| `net/minecraft/entity/LivingEntity.java` | `e8224e7df98ccf0986bb390f35e64293263d17b9ab1dbdfeb661c65d28b6ee78` | `e8aed9c63c0dfbe88e4b9e11c58881bc6e04b9cbaecbde5e3606e3afa406d72b` | both |
| `net/minecraft/entity/player/PlayerEntity.java` | `333bb39db18af4cef320eb73b7abe359d438fb2a6ca7d8798ee449da6c684398` | `e3f361ac4e369d01473f3d84044ee33f9685d182c4485e2e80b7f62756950963` | both |
| `net/minecraft/client/network/ClientPlayerEntity.java` | `90c96701849c9212ea7714f02e7bc9011d0a7d0aaf1f7b46464497ba3ca14947` | `28b6b4031e02afd4a2e6b4a80e5a7ca06f16dac42ca0f92cab25e2c2dc9e14ae` | both |
| `net/minecraft/util/math/MathHelper.java` | `21ed846ac48a5a2fbcdb2ac31fd5b74a461485effd881112c903c09d9297504a` | same | both |
| `net/minecraft/util/math/Vec3d.java` | `c56acd6b7b03e1a05d01687c490b5533c53c1b97ba637f7492a2d6d1744b55dc` | same | both |

## Compared behavior

- **Ordinary angular writes.** `Entity.setRotation(float,float)` is `yaw = f % 360.0F; pitch = g % 360.0F` in both releases (`Entity.java`, 1.14.2 lines 301–304; 1.14.3 lines 300–303). It does not write `prevYaw`. `setPositionAnglesAndUpdate` stores `yaw = g`, then `prevYaw = yaw`, computes the double `i = prevYaw - g`, adjusts `prevYaw` by ±360 at the same strict/inclusive thresholds, and finally calls `setRotation(g,h)` (`Entity.java`, 1.14.2 lines 1047–1070; 1.14.3 lines 1091–1114). Yaw types, casts, order and thresholds are identical.
- **Look deltas.** `changeLookDirection` computes `double g = d * 0.15`, separately casts `(yaw + g)` and `(prevYaw + g)` to float, and clips pitch but not yaw. Its order and expressions match (`Entity.java`, 1.14.2 lines 315–327; 1.14.3 lines 314–326).
- **Movement’s yaw read.** `updateVelocity` passes the current `this.yaw` to `movementInputToVelocity`; the previous yaw is not consulted. The helper has the same `< 1.0E-7` zero-input gate, `> 1.0` normalization gate, float angle conversion, float sine/cosine locals, and double-vector rotation (`Entity.java`, 1.14.2 lines 1011–1026; 1.14.3 lines 1055–1070).
- **Reachable dry player path.** When the player is not swimming, flying, fall-flying, in water or lava, `PlayerEntity.travel` calls `super.travel` (1.14.2 lines 1362–1390; 1.14.3 lines 1364–1392). `LivingEntity.travel`’s ordinary dry branch obtains slipperiness `v`, chooses `w = onGround ? v * 0.91F : 0.91F`, computes the ground/air input scale with `method_18802(v)`, calls `updateVelocity`, then applies the same velocity preprocessing and move order (1.14.2 lines 1823–1849; 1.14.3 lines 1833–1859). The scale is `onGround ? getMovementSpeed() * (0.21600002F / (v * v * v)) : field_6281` in both (1.14.2 lines 1944–1946; 1.14.3 lines 1954–1956). The ground/air scale and friction differ by state in both releases; neither changes the yaw transform.

## Finite reachable witness

For the direct setter, pass finite `g = 361.25F` and finite pitch. In both versions the method first leaves `yaw = prevYaw = 361.25F`, the double delta is zero, then `setRotation` makes current yaw `361.25F % 360.0F = 1.25F`; previous yaw remains `361.25F`. This witnesses the exact write order and confirms no pair-specific current/previous discrepancy.

For actual dry movement, use a player with yaw `0.0F`, forward input `arg = (0,0,1)`, zero initial velocity, no vehicle/climbing, and no water, lava or fall-flying. These are finite ordinary states. `arg.lengthSquared()` is exactly `1`, so it is not normalized; the identical table functions give `sin(0)=0` and `cos(0)=1`. The added horizontal velocity is `(0,0,f)`. In air, the default finite `field_6281` is `0.02F`; on ground, `f` is the finite movement-speed/slipperiness expression above. The later `w` friction is respectively `0.91F` or `v*0.91F` in both releases. Thus the actual current-yaw contribution is the same; only the existing ground/air scale changes it.

## Dependency and boundary distinctions

The angle-to-vector calculation depends on `MathHelper.sin/cos` and `Vec3d` length/normalization/scaling. Their whole-file hashes are identical between these canonical sources, and the cited method bodies are the same. `PlayerEntity.travel` establishes the reachable inherited path; the dry branch and ground/air scale are in `LivingEntity.travel` and `method_18802`.

The public `Entity.setYaw(float)` default is empty; `LivingEntity.setYaw(float)` writes `field_6283`, not the primary `yaw` used by `updateVelocity` (`LivingEntity.java`, 1.14.2 lines 2406–2409; 1.14.3 lines 2414–2417). It is therefore not evidence of a change in the primary current/previous yaw operation. The only paired direct current/previous yaw assignment found in `ClientPlayerEntity` is inside `if (arg instanceof BoatEntity)`: `prevYaw = arg.yaw` followed by `yaw = arg.yaw` (1.14.2 and 1.14.3 lines 146–150). That mounted-boat case is outside the stated no-vehicle dry ground/air path. Liquid/flying/fall-flying branches, collision/shape dependencies, and complete tick coverage are outside this bounded audit.

## Phase-two independent audit of the source report

Reviewed `C:\Users\Wolfi\.codex\visualizations\2026\11\17\01a1eb7b-b9ff-7a20-8686-0295dde20f26\c-yaw-precision-1142-1143-source\report.md`, whose SHA-256 was `7c1c3ecd474eef896947f022ba22771ca6ececca1e5898849fc389c0b69bedc3` both before and after this audit. The source report was not edited.

**Verdict: agree with the report’s no-difference result for the assigned slice.** It adds the ordinary local cursor route and a finite witness; these are consistent with the exact pinned sources and with the frozen verdict above.

- Recomputed SHA-256 for each source cited by the report (`Mouse.java`, `Entity.java`, `LivingEntity.java`, `MathHelper.java`, `PlayerEntity.java`, and `Block.java`) on both sides. Every actual digest matched its nested provenance `sources` entry. The exact Yarn builds, client and mapping pins, and provenance identities in the report agree with the manifests.
- `Mouse.updateMouse` has matching locked/focused gates and sensitivity, smoothing, cursor-reset, inversion, and player dispatch order at lines 184–220 in both releases; `Mouse.java` is byte-identical (SHA-256 `b3724c88b74c005d741259f89329b92b4ab3af7a3f44cd0bdf7ac0222733f243`). With smooth camera off and sensitivity `0.5F`, the multiplier is exactly `1.0`; a finite accumulated horizontal delta of `600` dispatches `j=600`. Starting current and previous yaw at zero, `changeLookDirection(600,0)` computes double yaw delta `90.0` and separately casts both updated yaw values to `90.0F`. No yaw clamp or wrap intervenes.
- For the report’s subsequent reachable dry travel witness, `forwardSpeed=1.0F` reaches `LivingEntity.travel` as `(0,0,1)`. At yaw `90.0F`, the identical float angle operation indexes sine `1.0F` and cosine approximately `1.22464685e-16F`; the helper adds approximately `(-f,0,1.22464685e-16*f)` in each release. With the stated default flat-block slipperiness and base player speed, ground `f` is approximately `0.1F`; airborne `f` is `0.02F`. The negligible finite Z residual and distinct ground/air acceleration scales are not a version difference.
- The report’s citations to default slipperiness (`Block.Settings`), player base movement speed, and per-tick air movement speed are source-supported in the cited files. The dependency separation is sound: mouse input updates current/previous yaw; movement reads current yaw; dry ground/air state supplies the scale and friction.

This independent acceptance is limited to the assigned operations and does not certify full-pair inventory, coverage freeze, or runtime parity.