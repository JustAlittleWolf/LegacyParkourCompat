# Independent source audit: 1.13.2 → 1.14 first stable release

## Bound input and verdict

Audited source review: `.native-source/20261028/frontier-c/1132_1140_first-boundary/source-review.md`, SHA-256 `c7eea36b2e2af9d38b96b571c0f5e3762832c5ade859be774ac87918272b3ce1`. The `selected-source-hashes.csv` SHA-256 is `c1292b411dca8c67b882d18c12651b99e48f7e22544458fa96128a704ae24382`; the `changed-source-paths.csv` SHA-256 is `01c4fd2142a0154698e0f2a9441346192df38fd51ab9181655e2b1e92d444016`.

The exact source identities and all 21 selected source hash tuples verify. Independent rehashing of the full source maps found 2,711/2,711 1.13.2 files and 3,152/3,152 1.14 files with no hash mismatch or unmanifested Java file. Recomputed path deltas match the table exactly: 616 additions, 175 removals, 1,862 changed shared paths and 674 unchanged shared paths. The input report is accurate about source-visible methods; its movement candidates remain conditional. This is not a complete pair audit or freeze.

## Identity verification

Canonical read-only trees: `decompiled_minecraft/1.13.2/ornithe-feather` and `decompiled_minecraft/1.14/ornithe-feather`. Neither mapping directory contains an embedded provenance manifest. The exact release-parent markers and manifests exist on both sides and match the report:

| Release | Provenance | Ready marker | Source manifest | Artifact manifest |
| --- | --- | --- | --- | --- |
| 1.13.2 | `e25950b7855fc4f2aee1a662f0be6b0af9f28788d9e27293075cfc5a3aa5d383` | `1d1c644c1deb05c02d11dc575ad834f4f72489124701d9b2b90357f43dc37b38` | `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211` | `fdcacd9150f98ea70acafc5cab754027ab1a09828ff0eaa8e542dd7890d9ce1e` |
| 1.14 | `bfb011801a64e9afaa4aca43e8a8ed40bf2a02f136ffc02e65b32e79315764fa` | `e0ecb319a63d5d664b87ad7c00638e1cdb9dc302ee2c05bbc05a88ce96d1e60b` | `68bc39e38bc501e7c9af315de4678263e1a64606b0b9780099187c7984ccbc98` | `2fdf423e2fbe0d20ec961cc42cd69b3107caeed88edf3e097ee588d29868f5df` |

The release parents identify Feather Gen2 build 2 for both endpoints. Ready markers bind the reported version IDs and client artifact hashes. I did not rehash the cached metadata, client JARs or Feather artifact archives themselves; the source identity is supported by the parent provenance, ready markers and complete source manifests. The 1.13.2 release directory is named `1.13.2`; the 1.14 directory is the exact first 1.14 release, not a directory named `1.14.0`.

## Retraced movement claims

### Input slowdown gate — supported as a conditional source difference

In 1.13.2, `KeyboardInput.tick()` polls the current keys, sets `sneaking`, and multiplies the two movement axes by `0.3` only if that freshly polled sneak key is down. In 1.14, `LocalClientPlayerEntity.mobTick()` computes `bl4 = m_63723874() || m_99544176()` before the keyboard poll and passes it with `isSpectator()` into `KeyboardInput.tick(bl4, spectator)`. The new keyboard gate applies `0.3` when the spectator argument is false and either the newly polled sneak key or `bl4` is true.

`m_63723874()` requires non-flying state and a fitting sneaking pose, then returns prior input sneaking or failure of the standing-pose fit. `m_99544176()` is swimming pose while no longer in water. The reported input-gate difference and ordering are supported. The source establishes conditional gates, not a runtime occurrence or trajectory result. `bl4` is sampled before `input.tick`, so its input-sneak term is the pre-poll stored value; the report's forced-pose and just-left-water gates are correctly tied to that earlier phase.

### Travel arithmetic — source differences supported, numeric effect not established

The 1.13.2 local path calls `Entity.updateVelocity(float,float,float,float)`. It computes float squared-input magnitude, uses the `<1.0E-4F` early return, takes a float square root, and normalizes only magnitudes below one by clamping the divisor to one before yaw rotation and float velocity additions. The 1.14 path supplies a `Vec3d`; `Entity.m_51093791` measures double squared magnitude, returns below `1.0E-7`, conditionally normalizes magnitudes greater than one, scales the vector, rotates with float sine/cosine and returns a double vector for addition. The representation, threshold and arithmetic forms differ as reported. No numeric witness demonstrating a different result for ordinary local input was established here.

The grounded acceleration expressions also differ in spelling and constants: 1.13.2 computes `getSpeed() * (0.16277137F / (t*t*t))` with `t = slipperiness * 0.91F`; 1.14 uses `getSpeed() * (0.21600002F / (slipperiness*slipperiness*slipperiness))`. Both branches then move and apply horizontal velocity friction. In 1.13.2, slipperiness is sampled once to compute acceleration and sampled a second time before movement for the later friction multiplication. In 1.14, one pre-move sample is used to derive both acceleration and friction. The source expressions are different, but the review provides no numeric witness or proof that normal values diverge. Treat this as a method-level arithmetic comparison, not an accepted output divergence.

### Climbing boost — added source gate supported, applicability open

In the ordinary non-water/non-lava travel path, 1.13.2 writes vertical velocity `0.2` after movement only if `collidingHorizontally && isClimbing()`. In 1.14 the post-move gate is `(collidingHorizontally || jumping) && isClimbing()`. Both call the era-existing climb predicate for the ladder, vine and open trapdoor-over-matching-ladder cases; 1.14 additionally recognizes scaffolding, which is not an era-existing block for this comparison.

`LivingEntity.mobTick()` calls `serverTickAi()` before the jump and travel phases. The 1.13.2 and 1.14 local-player `serverTickAi()` implementations copy current input jumping into `this.jumping` when the player is the camera. This supports the stated input-to-travel call path. The new predicate reads `new BlockPos(this)` while the old reads `floor(getShape().minY)`; this audit did not close their equivalence for every unusual box/state. It also did not establish collision/provider reachability of the jump-without-horizontal-collision case. Keep the added OR gate as a conditional source candidate with those dependencies open.

### Respawn yaw — direct state write supported, tick applicability open

The 1.13.2 `handlePlayerRespawn` delegates replacement to `Minecraft.respawnPlayer`; the replacement's `Entity.resetPos()` clears velocity and pitch but does not write yaw. In 1.14 the handler performs replacement inline, calls `resetPos()`, and then assigns yaw `-180.0F`. The source-level direct client player state difference is supported. Whether a following position correction or other packet overwrites the yaw before the next movement tick was not checked; no movement outcome is claimed.

### Equal-packet response methods checked

For `handlePlayerMove`, both versions resolve relative coordinates/angles against the current local player, clear velocity components on absolute position axes, update position/angles, acknowledge the teleport and send a position response. 1.14 additionally updates previous-position interpolation fields and writes the retained velocity vector once; this audit does not classify those history fields as a movement delta, but their readers are not exhaustively closed here. `handleEntityVelocity` still divides the three integer components by `8000.0` and calls `lerpVelocity`; explosion knockback still adds the three received floats to current velocity after explosion finalization. I retraced the selected ability, effect and attribute receiver bodies and confirmed their selected operations are unchanged. These are equal-input receiver dispositions; packet producers, block/provider consequences and effective data remain external.

## Open coverage and disposition

The ordinary tick/travel differences above have supported source gates but no trajectory proof. The input slowdown and climb conditions need their applicable state/collision dependencies for a complete movement claim. Travel-helper coefficients and float/double normalization lack a numeric divergence witness. Respawn yaw has unresolved packet-to-tick order. Changed position-history fields have not had all client consumers retraced. The report itself states that the full `Entity.move` collision rewrite, changed block/fluid providers, registrations, resources/tags and other changed local-player dependencies remain outside this bounded review. Therefore no full-pair closure or exact implementation-readiness claim follows.

No product files, Git history, wiki material, tests, builds, runtime, game/server processes, or source generation were inspected or run. Canonical source trees were read-only.
