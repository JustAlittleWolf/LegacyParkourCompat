# Blind source audit: powder frost movement slice

Bounded independent freeze of the three assigned claims only. This is not a full-pair movement inventory or full-pair freeze. Scope: ordinary local post-travel frost modifier refresh and order; modifier key, operation and gates; next player speed-cache read and frozen-tick synchronized metadata boundary. Excluded: block-state production, damage, visuals, runtime/arrival, and all other movement coverage.

## Exact source identity

All versions are exact decompiled Mojmap source trees. For 1.20.5, 1.21.4 and 1.21.5, source manifests/ready markers identify the version and `mojmap`; relevant source file SHA-256 values below were recomputed and matched to the published manifest. For 1.21 and 1.21.2, embedded provenance identifies the exact client inputs and Mojmap `client.txt`; relevant raw source SHA-256 values below matched the embedded per-file hashes.

| Version | Exact client SHA-256 | Mojang client mapping SHA-256 | Source manifest / provenance SHA-256 | `LivingEntity.java` SHA-256 | `Entity.java` SHA-256 | `Player.java` SHA-256 |
|---|---|---|---|---|---|---|
| 1.20.5 | `49d5949adbb1021a7eb00c1b949f9e39934b10249cbf86d25004774d1b3ff510` | `9afdb3b7fcc4c6acbec3bc6ccb46192bfc6460e12bf9e268e29bfee46bc7fbac` | `162dac6c4f1d539a1c2cea0255b7938a5281c207ab460bdc265358016d732484` (source manifest) | `c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2` | `71cd6b9f6c002684154dce11d3745e8714d82f13c8e1b3aa56743930131c18f3` | `785d93ccc94e1f912e545b2b0c355edeb352b44ee8e83a69364daec35266dbe2` |
| 1.21 | `4b56bc9b44de7e88f9fbb52e716c18240da34469fcbafa5ed2e75b04d60617e8` | `127485d79248d4ae2955a7d061c3626d38853ecb054c4d008707732264b0aac1` | `90246f277aefd2350e42e17245faa51928a949c8db4427f3be04a732527deb7c` (embedded provenance) | `324a3eee8496caab57cfaf5101ef576f1ae3c60c40e3857e96f35f3af9a3a0d8` | `b81905c7879e2cc5c5063a41d865c4164ad919f156306705be791d1017b99850` | `c555e68ac3c63ab9b4f9a9e31933e263b96350a2bc599d11a0de5928bc24b583` |
| 1.21.2 | `613091da0f62426ae8dcf9afb30eb639485f493b81cda3a2b96b1fb8a6b9976c` | `e37560e4889168916a73dbc4922c563002eff70fbecfe1cf5c50a7b222f451b2` | `00f399407dc447737ea83e3e46562418f8c712f66e3f51cb85d46afa2ecac575` (embedded provenance) | `087390495f4fdfd14b9e12230e7aea4fdc50913bb4d1d91119e72892881cfc52` | `a93719c302a0381a972af75ea360465e2e3551708dd07c34d4d40b7e5173c2b9` | `fbd40f1f47adfa66dda9b15188e5dce82af3e8e8d7c3dd0543e602a354ad3fe0` |
| 1.21.4 | `c17c450c6e72cc51297daa57ce38f800aa01cf022b743daa21a0512d326d894e` | `48b502ccc5e855b49da8aa9c0c0d7c565bec54c9f5da3a3664528aff7b9fdf23` | `f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0` (source manifest) | `e62ce650af0a5ac97e4d0a2ba7cad4ba68a8d098525609f8b5b6976a8ab0ae30` | `05f18ef2ec0413fc010230407c812a11553eb5123b68d21b5d7b2c0c175698ad` | `145686ebdc7f0d12a64070309073665eb8695b7e09911a723e86113a77d04611` |
| 1.21.5 | `522672ad20b460c02c2e39b6c5035ef6a849af28eb11ab2ac7eb293d395a8c11` | `3907657ade3e61bc8cffb4ca0a1bcba15f57986be4e613900947119364a206e5` | `365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9` (source manifest) | `a8aed863d4fdc515c751dd2878a8bb9c13179228cb8dbb50edf1d19cd5404271` | `fc177733e9cc4b2d5cf2562e5529d0f4e0b9c3690f081fbd58aa9119eb173dc4` | `f1fcfed4a938732361e7ad951f93e9b73b02320ee56f0b219e2b3c7acdbfa2ef` |

Source paths in every row are under `decompiled_minecraft/<version>/mojmap/net/minecraft/`. For 1.20.5 the ready marker says `versionId=1.20.5`, `mapping=mojmap`; for 1.21.4 and 1.21.5 those same fields appear in their ready markers and provenance. The embedded provenance for 1.21 and 1.21.2 lists the exact client JAR and official client mapping file inputs shown above.

## Frozen findings

### 1. Ordinary local post-travel refresh is removed in 1.21.5

`LivingEntity.aiStep` in 1.20.5 and 1.21 runs `travel(...)`, then the `freezing` section, then `removeFrost()` followed by `tryAddFrost()` without a client-side gate around those two calls. Server-only frozen-tick progression is separately gated. Anchors: 1.20.5 `LivingEntity.java:2683-2698`; 1.21 `:2678-2693`.

In 1.21.2 and 1.21.4, the same remove-then-recompute calls remain unconditional and after travel; block effects and animation now run between travel and the `freezing` section. Anchors: 1.21.2 `LivingEntity.java:2771-2791`; 1.21.4 `:2782-2802`.

In 1.21.5, travel and client block/animation work precede a `if (this.level() instanceof ServerLevel)` section, and both `removeFrost()` and `tryAddFrost()` are inside that server-only section. Anchors: `LivingEntity.java:2766-2785`. The local client therefore no longer performs this frost modifier removal/recomputation in its ordinary player tick path. This finding concerns that local operation; it makes no claim about separate attribute packet behavior.

### 2. Modifier key changes; additive float procedure and gates remain

1.20.5 declares powder-snow modifier key UUID `1eaf83ff-7207-4596-b37a-d7a07b3ec4ce`; `tryAddFrost` creates a transient `AttributeModifier` named `Powder snow slow`, amount `-0.05F * getPercentFrozen()`, operation `ADD_VALUE`. Anchors: `LivingEntity.java:133, 533-555`.

From 1.21 through 1.21.5 the key is `ResourceLocation.withDefaultNamespace("powder_snow")`; modifier creation uses that key, the same `-0.05F * getPercentFrozen()` float expression, `ADD_VALUE`, and transient application. Anchors: 1.21 `LivingEntity.java:138, 490-512`; 1.21.2/1.21.4 `:143/142, 505-527`; 1.21.5 `:141, 481-503`.

Across all five sources, removal first gets `Attributes.MOVEMENT_SPEED`, checks the instance for null, checks for the modifier key, then removes it. Re-add first requires `!getBlockStateOnLegacy().isAir()`, then `getTicksFrozen() > 0`, then a non-null movement-speed attribute instance; it computes the float amount above and adds a transient modifier. The block predicate is any non-air legacy block state, not a powder-snow-only check. The UUID-to-resource-location key/API transition occurs by 1.21; operation, amount calculation and re-add gates match in the examined versions.

### 3. Next local speed read and synchronized input boundary

`Entity.DATA_TICKS_FROZEN` is an `EntityDataAccessor<Integer>` defined with `SynchedEntityData` and defaulted to zero; `getTicksFrozen()` reads it from `entityData`. It is synchronized entity metadata at this source-defined boundary in all five versions. Anchors: `Entity.java:216, 260, 2261-2266` (1.20.5); `:218, 261, 2327-2332` (1.21); `:225, 269, 2425-2430` (1.21.2/1.21.4); `:232, 278, 2425-2430` (1.21.5).

`Player.aiStep` calls `super.aiStep()` before refreshing the cached movement speed with `setSpeed((float)getAttributeValue(Attributes.MOVEMENT_SPEED))`; `LivingEntity.getSpeed()` returns the cached `speed` field. For a local player whose `super.aiStep()` just completed travel and frost refresh, the post-travel modifier feeds that speed refresh and is available to the next travel read. Anchors: `Player.java:533` and `LivingEntity.java:2322` (1.20.5); `Player.java:535`, `LivingEntity.java:2305` (1.21); `Player.java:556`, `LivingEntity.java:2404` (1.21.2); `Player.java:559`, `LivingEntity.java:2415` (1.21.4); `Player.java:569`, `LivingEntity.java:2446` (1.21.5).

The frozen-tick integer being synchronized does not itself establish synchronization of this transient attribute modifier. In 1.21.5, the client-side helper call is absent even though `getTicksFrozen()` remains a synchronized entity-data read. No further network synchronization path is assessed here.

## Audit limits

This freezes only the three assigned source claims across the five named exact versions. It does not establish intermediate patch introduction boundaries, complete movement coverage, runtime parity, or any implementation status. No product source, Git history, wiki material, backlog, network source, builds, tests or launches were consulted.
