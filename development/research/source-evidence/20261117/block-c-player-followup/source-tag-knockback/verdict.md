# Independent source audit: 1.20.1–1.20.2 damage-type knockback tag

## Terminal verdict

**COMPLETED — ACCEPTED, with the source/runtime tag-data limitation retained.** The bounded claim in `source-tag-knockback-scope/comparison.md` is supported by the exact Mojmap source pair for the built-in generated tag defaults. For a successful `LivingEntity.hurt` call with a non-null causing entity, the default provider membership changes whether the direct `knockback(0.4F, ...)` call is gated: `FIREWORKS` is in 1.20.1 `IS_EXPLOSION` but omitted from 1.20.2 `NO_KNOCKBACK`; the other three default entries overlap. This is a server-authored damage response, not a locally authored client damage/knockback path. The client motion packet handler and `Entity.lerpMotion` response are equivalent in both versions.

This verdict does **not** establish loaded datapack tag membership, damage eligibility, causing-entity production, or a universal runtime `FIREWORKS` outcome. No tests, runtime, product code, wiki material, or Git history were read.

## Immutable input and source identity

- Scope/comparison input: `source-tag-knockback-scope/comparison.md`, SHA-256 `341d8b45c7de619a2d83aaab5413321fd3577abfad7266f29e05679626bfecf8`.
- Pair: Minecraft 1.20.1 → 1.20.2, Mojmap on both sides; canonical roots `decompiled_minecraft/1.20.1/mojmap` and `decompiled_minecraft/1.20.2/mojmap`.
- Provenance JSON SHA-256: 1.20.1 `5aeb1df09671480aeddb921c919e92098e33d0a0bce47e22f82a865cb32400aa`; 1.20.2 `1dad9f725f6cb25b6568700fa0294f83b66ea9633b0d62ed354d7bd8b45d4f05`.
- Ready JSON SHA-256: 1.20.1 `f8dc81374dc42521ee9d17a04ea0dc77f1c5384548cfc01c9621b3d84b670fed`; 1.20.2 `5ff3322f287c250ee914c7ca3840d053bffc3c2c5f35e91b59314d948292483f`.
- Provenance identifies `mappingOption: mojmap`, exact run batch `1.20.1, 1.20.2, 1.20.4, 1.20.6`, and Gradle 9.7.1 / Vineflower 1.12.0 / TinyRemapper 0.14.1 / Mapping IO 0.9.1. Ready manifests identify exact version IDs and source-manifest SHA-256: 1.20.1 `858b56764113e591c60d09bc63e4d90f9c1d67a705645f0c793e8b28f2378465`; 1.20.2 `e439ff7e1d3f1d31e01be1edd141f94d917a9dc7fa0de5f69904544cbbe62887`. The checked source-file hashes match their manifest entries.

## Findings and bounds

1. **Tag declaration and generated defaults.** In 1.20.1, `DamageTypeTags.java:19` declares `IS_EXPLOSION`; `DamageTypeTagsProvider.java:58` adds `FIREWORKS`, `EXPLOSION`, `PLAYER_EXPLOSION`, and `BAD_RESPAWN_POINT`. There is no `NO_KNOCKBACK` key or source reference in the 1.20.1 tree. In 1.20.2, `DamageTypeTags.java:33` adds `NO_KNOCKBACK`; provider line 58 still gives `IS_EXPLOSION` all four types, while line 72 puts only `EXPLOSION`, `PLAYER_EXPLOSION`, and `BAD_RESPAWN_POINT` in `NO_KNOCKBACK`.

2. **Own engine read closure.** A source-wide search of the two canonical Mojmap trees finds the sole 1.20.2 runtime Java read of `DamageTypeTags.NO_KNOCKBACK` at `LivingEntity.java:1183`; the other matches are its key declaration and its generated-default provider entry. The 1.20.1 tree has no `NO_KNOCKBACK` matches. This closes the tag's Java source read inventory for this pair, not external datapack inputs or runtime resource resolution.

3. **Changed gate and direct response.** `LivingEntity.hurt` is at 1.20.1 line 1065 and 1.20.2 line 1090. Both return `false` when `level().isClientSide` before the successful-damage branch. The changed gate is 1.20.1 line 1158 (`!source.is(IS_EXPLOSION)`) versus 1.20.2 line 1183 (`!source.is(NO_KNOCKBACK)`); each guards the same `knockback(0.4F, dx, dz)` at lines 1166 and 1191 respectively, after success and non-null causing entity. For the built-in defaults, fireworks reaches this call in 1.20.2 and is suppressed in 1.20.1; the other three listed types remain suppressed. `LivingEntity.knockback` math/order is the same in both trees (lines 1419 and 1445): apply knockback resistance, return for nonpositive force, set `hasImpulse`, normalize horizontal direction and scale, then write the same x/y/z velocity expression.

4. **Client versus server author and packet response.** `LocalPlayer.hurt` returns `false` in both versions (1.20.1 line 150; 1.20.2 line 149). `ServerPlayer.hurt` reaches `super.hurt` after its own gates (1.20.1 line 685; 1.20.2 line 684). Thus this gate is not a client-local damage author; the changed direct velocity originates on the server-side LivingEntity path. `ServerEntity` observes `hasImpulse`, compares current motion to its cached motion, and broadcasts `ClientboundSetEntityMotionPacket` (1.20.1 lines 162–168; 1.20.2 lines 165–171); it also has the same `hurtMarked` `broadcastAndSend` packet branch (lines 199–201 / 202–204). On receipt, `ClientPacketListener.handleSetEntityMotion` looks up the entity and calls `lerpMotion(packet components / 8000.0)` (1.20.1 lines 501–505; 1.20.2 lines 481–485). `Entity.lerpMotion` delegates to `setDeltaMovement` in both versions (1.20.1 line 2039; 1.20.2 line 2073). No local-player special case exists in this response path. This confirms equivalent client handling for an equivalent received velocity packet; it does not audit all packet production or prove the packet is emitted for every possible state.

5. **Negative resource closure and limitation.** Recursive checks found no `data/**/tags/damage_type` or `data/minecraft/tags/damage_type` JSON under either canonical decompiled Mojmap tree. The `DamageTypeTagsProvider` establishes built-in generated defaults, not the actual post-reload tag contents. Datapacks/resource reload can change membership, so the runtime `NO_KNOCKBACK` membership gate remains open and the FIREWORKS outcome must stay qualified by default-source data.

## Exact SHA-256 evidence

| Version | Relative source file | SHA-256 |
| --- | --- | --- |
| 1.20.1 | `net/minecraft/world/entity/LivingEntity.java` | `decf8cd70d194098ad51d0c82e5a0087e0687881e0a2dc66456f2ba6ea76676f` |
| 1.20.2 | `net/minecraft/world/entity/LivingEntity.java` | `5c481da1ffc8684c4b30171c92e61fad751e6a3a708d5bc6486ac9f96ff69828` |
| 1.20.1 | `net/minecraft/tags/DamageTypeTags.java` | `8a2083fbfbad3bcf1a4392d8b676f4c3dc7ba6ffc427f3d9c867d65f49ebba06` |
| 1.20.2 | `net/minecraft/tags/DamageTypeTags.java` | `8f80dee1a5e4e154dafd107a28c719ff4948440fe2c8177aece51e2988c846e` |
| 1.20.1 | `net/minecraft/data/tags/DamageTypeTagsProvider.java` | `d1f375cfce32a339d362b1e5979d2813301bde28c5b64267acd629a7bf8b6644` |
| 1.20.2 | `net/minecraft/data/tags/DamageTypeTagsProvider.java` | `b7facd5dcc93ae6b0819b91433f5769d0f71073e80ba80d87985b2f2c6d25032` |
| 1.20.1 | `net/minecraft/client/player/LocalPlayer.java` | `69a2d043d2c0595bd364d625a5445e4934d9e07fef020cd79fafb42f59952ac2` |
| 1.20.2 | `net/minecraft/client/player/LocalPlayer.java` | `bb5cbfb03656a1866bb77c00431618befe792081223b35db4bd121ecbb151fd5` |
| 1.20.1 | `net/minecraft/server/level/ServerPlayer.java` | `4168d5d45d043f5edc0d9cafeeff2540cf544bae92b53f1b018fd1ee8887dd61` |
| 1.20.2 | `net/minecraft/server/level/ServerPlayer.java` | `6420fe291b4fa2f8cd6ad22b4393595422961de46c82fda4395d7b3f712bf2ed` |
| 1.20.1 | `net/minecraft/server/level/ServerEntity.java` | `77f892e4cd7f2eb60c86abec40425eb53110f5d0f111916858774af352e7349c` |
| 1.20.2 | `net/minecraft/server/level/ServerEntity.java` | `0bbbe8a12dedda7b61bbfb79086386a0198e5e6f0da0bdb248ebaf2ee78be872` |
| 1.20.1 | `net/minecraft/client/multiplayer/ClientPacketListener.java` | `8b21ccd4106abb0698f6872250e665ae3f0fc942c8953b9ddd45976660ed624b` |
| 1.20.2 | `net/minecraft/client/multiplayer/ClientPacketListener.java` | `0208f6942035adaf2e787546851ca296f0e7ec2179d3610f694911eda5283264` |
| 1.20.1 | `net/minecraft/world/entity/Entity.java` | `94b9c3656715de2d61fa9a02ccef164261e50eb13d6090a6f07e58fe9c5b0759` |
| 1.20.2 | `net/minecraft/world/entity/Entity.java` | `d7ee49aaea5e862b92508e767562cabc8f10515605e8fb67565c8c8d5c23b01b` |

This is a bounded independent audit of the immutable comparison and its listed tag/authority claims; it is not complete pair coverage, an implementation review, or runtime-parity evidence.