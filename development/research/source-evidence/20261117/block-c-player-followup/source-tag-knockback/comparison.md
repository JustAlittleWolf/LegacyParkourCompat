# 1.20.1 to 1.20.2: damage-type knockback tag gate

## Evidence identity

- Exact pair: Minecraft 1.20.1 → 1.20.2, Mojmap on both sides.
- Canonical sources: `decompiled_minecraft/1.20.1/mojmap` and `decompiled_minecraft/1.20.2/mojmap`.
- Exact-run provenance: both `mojmap.provenance.json` files identify `mappingOption: mojmap`, batch `1.20.1, 1.20.2, 1.20.4, 1.20.6`, and the same decompiler toolchain (Gradle 9.7.1, Vineflower 1.12.0, TinyRemapper 0.14.1, Mapping IO 0.9.1). Ready manifests identify exact version IDs and source-manifest SHA256 values: 1.20.1 `858b56764113e591c60d09bc63e4d90f9c1d67a705645f0c793e8b28f2378465`; 1.20.2 `e439ff7e1d3f1d31e01be1edd141f94d917a9dc7fa0de5f69904544cbbe62887`.
- Relevant file SHA256: 1.20.1 `LivingEntity.java` `DECF8CD70D194098AD51D0C82E5A0087E0687881E0A2DC66456F2BA6EA76676F`; 1.20.2 `LivingEntity.java` `5C481DA1FFC8684C4B30171C92E61FAD751E6A3A708D5BC6486AC9F96FF69828`; 1.20.2 `DamageTypeTagsProvider.java` `B7FACD5DCC93AE6B0819B91433F5769D0F71073E80BA80D87985B2F2C6D25032`.

## Paired source evidence

1.20.1, Mojmap: `net/minecraft/world/entity/LivingEntity.java`, `hurt(DamageSource, float)`, line 1158: after the successful-damage branch and a non-null source entity, knockback is invoked only when `!source.is(DamageTypeTags.IS_EXPLOSION)`. The default `DamageTypeTagsProvider` defines `IS_EXPLOSION` at line 58 with `FIREWORKS`, `EXPLOSION`, `PLAYER_EXPLOSION`, and `BAD_RESPAWN_POINT`. Its `DamageTypeTags.java` has no `NO_KNOCKBACK` key.

1.20.2, Mojmap: the corresponding `LivingEntity.java` method, line 1183, gates the same knockback calculation with `!source.is(DamageTypeTags.NO_KNOCKBACK)`. `DamageTypeTags.java` adds the `NO_KNOCKBACK` key at line 33. The built-in `DamageTypeTagsProvider.java`, line 72, puts `EXPLOSION`, `PLAYER_EXPLOSION`, and `BAD_RESPAWN_POINT` in it, but not `FIREWORKS`. The `IS_EXPLOSION` default still contains all four types at line 58.

Thus, for a successful hurt with a non-null causing entity and the built-in tag defaults, a `FIREWORKS` damage type reaches `knockback(0.4F, dx, dz)` in 1.20.2, while the old `IS_EXPLOSION` gate suppressed that call in 1.20.1. The other three listed types remain suppressed. This is a source-level change in whether the direct player response is invoked; it does not establish whether a given attack deals damage or supplies a causing entity.

## Isolated player response and authority

The inherited `LivingEntity.knockback(double, double, double)` implementation is unchanged (1.20.1 line 1419; 1.20.2 line 1445). For an identical call and state, both versions multiply force by `1.0 - KNOCKBACK_RESISTANCE`; unless force is `<= 0.0`, set `hasImpulse`, normalize `(dx, 0, dz)` and scale by force, then set velocity to `(oldX / 2 - knockX, onGround ? min(0.4, oldY / 2 + force) : oldY, oldZ / 2 - knockZ)`. The `0.4F` call argument is widened to the method's `double` parameter in both versions. Operation order, types, and direct player movement math match.

For the client-held local player, `ClientPacketListener.handleSetEntityMotion` is also unchanged in behavior (1.20.1 line 501; 1.20.2 line 481): look up the packet entity ID, then call `lerpMotion(xa / 8000.0, ya / 8000.0, za / 8000.0)` when present. `Entity.lerpMotion` simply calls `setDeltaMovement` in both versions (1.20.1 line 2039; 1.20.2 line 2073). There is no local-player-specific condition in this path. Given equivalent received velocity components, the client response and server authority over that received velocity are unchanged. The new source-tag gate can alter the server-side knockback outcome before a velocity update is produced; tracing combat/damage eligibility or packet emission is outside this slice.

## Tag-resource gate and disposition

No exact `data/**/tags/damage_type` or `data/minecraft/tags/damage_type` JSON resource is present in either canonical decompiled tree. The 1.20.2 provider establishes the built-in generated defaults, but the actual loaded runtime tag contents can be affected by datapacks/reloads and are not established here. Keep that resource/runtime membership gate open.

Disposition: retain as the independently bounded direct player knockback-gate difference, with the default-data precondition stated above. Do not infer a universal FIREWORKS result where runtime tag data is unknown. Server combat/damage eligibility and exact loaded datapack contents were not audited.

## Coverage and limits

Compared only the paired `LivingEntity.hurt` knockback gate and inherited direct knockback implementation, built-in damage-tag keys/default provider entries, and client received-motion handler plus `Entity.lerpMotion`. No other damage paths, server packet production, combat eligibility, datapack load/override behavior, later versions, implementation, tests, or runtime behavior were examined. No independent coverage audit was performed.

Terminal status: COMPLETED. This is a bounded source comparison, not a pair freeze or runtime-parity verdict.