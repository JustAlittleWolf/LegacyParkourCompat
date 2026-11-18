# Powder-snow additive frost speed math — blind source audit

## Scope and verdict

This audit compares the ordinary `LivingEntity.tryAddFrost()` consumer, movement-speed attribute evaluation, Speed/Slowness attribute templates, and `Player.getSpeed()` in the exact canonical client sources for 1.20.5, 1.21, 1.21.2, 1.21.4, and 26.2. The first four use official Mojmap; 26.2 is published unobfuscated source. No wiki, product implementation, Git history, or online mapping lookup was used.

**Verdict:** Across these five inspected snapshots, the frost amount and its effective attribute operation/order are unchanged. The native 26.2 arithmetic matches historical 1.20.5 arithmetic for this bounded operation. The cross-snapshot identifier changes from UUID to namespaced ID beginning in 1.21; the identifier is not part of the numeric calculation. This does not establish every intermediate patch boundary.

## Exact source behavior

In every inspected `LivingEntity.java`, `tryAddFrost()` has the same gates: the legacy block state on is non-air; `getTicksFrozen() > 0`; and a movement-speed `AttributeInstance` exists. It computes a local float as `-0.05F * getPercentFrozen()` and adds it as a transient `ADD_VALUE` modifier identified as powder snow. The calculation does not read, snapshot, or scale from current movement speed. It derives an absolute additive amount from the frozen percentage.

In every inspected `Entity.java`, `getPercentFrozen()` returns `(float)Math.min(getTicksFrozen(), getTicksRequiredToFreeze()) / getTicksRequiredToFreeze()`. The inherited `getTicksRequiredToFreeze()` returns 140 in each snapshot; the inspected Player classes do not override it. Thus, for example, 70 frozen ticks yields exactly `0.5F`, and 140 yields `1.0F`; the frost amounts are respectively `-0.025F` and `-0.05F`. The consumer method itself only requires positive frozen ticks and a non-air block-state-on; this audit does not infer the separate producer's block/contact timing or any packet-arrival timing.

`AttributeInstance.calculateValue()` has the same sequence in all five snapshots:

1. Start with the base value and add each `ADD_VALUE` amount.
2. Save that post-add value as the base for all `ADD_MULTIPLIED_BASE` contributions; each contribution is added to the result from that saved base.
3. Sequentially multiply the result by `1.0 + amount` for each `ADD_MULTIPLIED_TOTAL` modifier.
4. Sanitize the result through the attribute's value object.

Player's normal movement-speed base is `.1F`. Speed I contributes `.2F` and Slowness I contributes `-.15F`, both as `ADD_MULTIPLIED_TOTAL`; each effect template scales its configured amount by `(amplifier + 1)`. Sprint contributes `.3F` as `ADD_MULTIPLIED_TOTAL`. These three modifiers have distinct identifiers and can coexist on the movement-speed attribute. `Player.getSpeed()` returns `(float)getAttributeValue(MOVEMENT_SPEED)` in each inspected snapshot, so the effective double attribute value is converted to float at this player movement-speed access point.

## Source-reachable numeric examples

For a normal player base of `.1F`, with Speed I, Slowness I, and sprint all concurrently active and no other movement-speed modifiers, the source operations are:

`B = (double).1F + (double)(-.05F * percentFrozen)`

`V = B * (1.0 + (double).2F) * (1.0 + (double)(-.15F)) * (1.0 + (double).3F)`

The total-operation modifiers are applied by the source's sequential loop. Their relative iteration order does not change the displayed final float in these examples. `Player.getSpeed()` then casts `V` to float.

| Frozen ticks | Frost amount (float, then widened) | Effective attribute value before Player cast (approx.) | `Player.getSpeed()` float |
|---:|---:|---:|---:|
| 0 (comparison) | no frost modifier | 0.13260000259131194 | 0.13260000944137573 |
| 70 / 140 | -0.02500000037252903 | 0.09945000194348395 | 0.0994499996304512 |
| 140 / 140 | -0.05000000074505806 | 0.06630000129565597 | 0.06630000472068787 |

These values follow from the same source arithmetic in all five snapshots. The frost term is added before the Speed, Slowness, and sprint total multipliers. Although the standard `.1F` base makes the full-freeze amount equal half the base, it is still a fixed additive amount determined by frozen percentage, not a reduction computed from the current effect-adjusted speed. Different movement-speed bases therefore need not have the same relative reduction.

## Source locations

Paths are relative to `decompiled_minecraft/<version>/<mapping>/`.

- 1.20.5 Mojmap: `net/minecraft/world/entity/LivingEntity.java:542-552` (`tryAddFrost`), `Entity.java:2269-2281` (`getPercentFrozen` and freeze requirement), `ai/attributes/AttributeInstance.java:140-160` (`calculateValue`), `effect/MobEffects.java:16-24` (Speed/Slowness templates), `effect/MobEffect.java:173-176` (amplifier scaling), `player/Player.java:224` and `1511-1513` (base and `getSpeed`).
- 1.21 Mojmap: `LivingEntity.java:499-509`, `Entity.java:2335-2347`, `ai/attributes/AttributeInstance.java:142-162`, `effect/MobEffects.java:15-26`, `effect/MobEffect.java:180-183`, `player/Player.java:225` and `1494-1496`.
- 1.21.2 Mojmap: `LivingEntity.java:514-524`, `Entity.java:2433-2445`, `ai/attributes/AttributeInstance.java:152-172`, `effect/MobEffects.java:15-26`, `effect/MobEffect.java:181-184`, `player/Player.java:229` and `1489-1491`.
- 1.21.4 Mojmap: `LivingEntity.java:514-524`, `Entity.java:2461-2473`, `ai/attributes/AttributeInstance.java:152-172`, `effect/MobEffects.java:15-26`, `effect/MobEffect.java:181-184`, `player/Player.java:232` and `1492-1494`.
- 26.2 unobfuscated: `net/minecraft/world/entity/LivingEntity.java:531-541`, `Entity.java:2827-2839`, `ai/attributes/AttributeInstance.java:148-168`, `effect/MobEffects.java:15-26`, `effect/MobEffect.java:206-209`, `player/Player.java:209` and `1446-1448`.

## Source identities and pins

All IDs below were checked against the selected canonical directories. Hashes use SHA-256. For 1.20.5, 1.21.4, and 26.2 the recorded per-file source hashes match their `*.sources.sha256` manifest. For 1.21 and 1.21.2 they match the `sources` hash map embedded in `.decompile-provenance.json`. The listed file hashes are the exact source inputs used above.

| Version / mapping | Exact source and input identity |
|---|---|
| 1.20.5 / Mojmap | Ready marker ID `1.20.5`; source manifest SHA-256 `162dac6c4f1d539a1c2cea0255b7938a5281c207ab460bdc265358016d732484`; artifact manifest SHA-256 `f2dbd7a2e6c3fb452b0c28c93c9815e43ca24c179815f3d4fca34e484d465bb6`; version metadata `1.20.5` SHA-256 `d4e0df98325bc46ee023141b21b1230ad6c9266745835867bf6ab3eb1051a9b7`; client SHA-256 `49d5949adbb1021a7eb00c1b949f9e39934b10249cbf86d25004774d1b3ff510`; official client mappings SHA-256 `9afdb3b7fcc4c6acbec3bc6ccb46192bfc6460e12bf9e268e29bfee46bc7fbac`. |
| 1.21 / Mojmap | Exact pinned version metadata ID `1.21`, SHA-256 `cdd1a44db7b5e8787723c1f6c7c7dc3a4638d96a31ddc84aeb27d3f30efa802a`; client SHA-256 `4b56bc9b44de7e88f9f9bb52e716c18240da34469fcbafa5ed2e75b04d60617e8`; official client mappings (`client.txt`) SHA-256 `127485d79248d4ae2955a7d061c3626d38853ecb054c4d008707732264b0aac1`; source/provenance file SHA-256 `90246f277aefd2350e42e17245faa51928a949c8db4427f3be04a732527deb7c`. The canonical source tree carries this provenance file rather than a separate ready marker. |
| 1.21.2 / Mojmap | Exact pinned version metadata ID `1.21.2`, SHA-256 `793e3fd0451354a26aaa00253dcb6600add11f9d2a90f7bdef75ddf5b38c2b45`; client SHA-256 `613091da0f62426ae8dcf9afb30eb639485f493b81cda3a2b96b1fb8a6b9976c`; official client mappings (`client.txt`) SHA-256 `e37560e4889168916a73dbc4922c563002eff70fbecfe1cf5c50a7b222f451b2`; source/provenance file SHA-256 `00f399407dc447737ea83e3e46562418f8c712f66e3f51cb85d46afa2ecac575`. The canonical source tree carries this provenance file rather than a separate ready marker. |
| 1.21.4 / Mojmap | Ready marker ID `1.21.4`; source manifest SHA-256 `f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0`; artifact manifest SHA-256 `1a0929ca8c88cfe7874f323918dfa3b044964007d3ff0d3943317bdb35caf841`; version metadata `1.21.4` SHA-256 `9fdb459ae435955e5c4c12301dcac0fc08ee8f89c9b1bf15bb6c7a26e6503304`; client SHA-256 `c17c450c6e72cc51297daa57ce38f800aa01cf022b743daa21a0512d326d894e`; official client mappings SHA-256 `48b502ccc5e855b49da8aa9c0c0d7c565bec54c9f5da3a3664528aff7b9fdf23`. |
| 26.2 / unobfuscated | Ready marker ID `26.2`; source manifest SHA-256 `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894`; artifact manifest SHA-256 `ba9dc53a41bca9744ec1c8ab2ce9363764a22333c39ef64d5105f4e3bf5c98ba`; version metadata `26.2` SHA-256 `4d6b3a5a27fed3c0faab2cb4b6dee7318be310bc3c627a78951519ffefaf1d0c`; published client SHA-256 `40896ee9f1e2bec3c934daac7e93d41e9e3d9c2f8ae0ca366d52ffbfd1afa290`. No mapping artifact applies to the unobfuscated source. |

### Per-file source SHA-256

Each row is `relative source path = SHA-256`; all six hashes on each version match that version's recorded source hash set.

**1.20.5 Mojmap**
- `net/minecraft/world/entity/Entity.java` = `71cd6b9f6c002684154dce11d3745e8714d82f13c8e1b3aa56743930131c18f3`
- `net/minecraft/world/entity/LivingEntity.java` = `c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2`
- `net/minecraft/world/entity/player/Player.java` = `785d93ccc94e1f912e545b2b0c355edeb352b44ee8e83a69364daec35266dbe2`
- `net/minecraft/world/entity/ai/attributes/AttributeInstance.java` = `5d060c1740336835c74138d3c5fe3ae5f3b7f6a1c2f16f6844289f18ef90782a`
- `net/minecraft/world/effect/MobEffects.java` = `9b24dee8a23a4b8730fdfa7ea9cbd560c9370599cc282fac1c0755f5894e7645`
- `net/minecraft/world/effect/MobEffect.java` = `e5ef74736e7a4b8cd5103fb5bbbe10b1aeb0be3531cf5600a39e08761c3746e2`

**1.21 Mojmap**
- `net/minecraft/world/entity/Entity.java` = `b81905c7879e2cc5c5063a41d865c4164ad919f156306705be791d1017b99850`
- `net/minecraft/world/entity/LivingEntity.java` = `324a3eee8496caab57cfaf5101ef576f1ae3c60c40e3857e96f35f3af9a3a0d8`
- `net/minecraft/world/entity/player/Player.java` = `ed32b88c3c7c8418b83db41823520f2b6b0b49a98610306ef26e9681dc925c71`
- `net/minecraft/world/entity/ai/attributes/AttributeInstance.java` = `16a79843c53aabf5ae34b3230596995c70932197223482ad998085d6031ffee0`
- `net/minecraft/world/effect/MobEffects.java` = `35d97c1741ec1379b05f41b8452229870c87a75633d312cd0b20d7fd901b4d08`
- `net/minecraft/world/effect/MobEffect.java` = `fe0902d25d6dc3871e00d7a4b292b0d465d55670a1286a78f289875858c73e07`

**1.21.2 Mojmap**
- `net/minecraft/world/entity/Entity.java` = `a93719c302a0381a972af75ea360465e2e3551708dd07c34d4d40b7e5173c2b9`
- `net/minecraft/world/entity/LivingEntity.java` = `087390495f4fdfd14b9e12230e7aea4fdc50913bb4d1d91119e72892881cfc52`
- `net/minecraft/world/entity/player/Player.java` = `a803203e92aa4729d5f5c9b16085b6a43ce51d9907d309eb96736e9c7c1340de`
- `net/minecraft/world/entity/ai/attributes/AttributeInstance.java` = `2a5ed44096b45b3b050a8df947cd44c788e6a35d51eb81d4c023f331f6f62870`
- `net/minecraft/world/effect/MobEffects.java` = `35d97c1741ec1379b05f41b8452229870c87a75633d312cd0b20d7fd901b4d08`
- `net/minecraft/world/effect/MobEffect.java` = `ae9af2ce81ba3e9bf9f28a216b6b9953dd684d19721bbc29085465957e952b5e`

**1.21.4 Mojmap**
- `net/minecraft/world/entity/Entity.java` = `05f18ef2ec0413fc010230407c812a11553eb5123b68d21b5d7b2c0c175698ad`
- `net/minecraft/world/entity/LivingEntity.java` = `e62ce650af0a5ac97e4d0a2ba7cad4ba68a8d098525609f8b5b6976a8ab0ae30`
- `net/minecraft/world/entity/player/Player.java` = `c45f41b9784ce50a88a498e84a13edcef0a23e175233d726ed7f90940f19ebc1`
- `net/minecraft/world/entity/ai/attributes/AttributeInstance.java` = `2a5ed44096b45b3b050a8df947cd44c788e6a35d51eb81d4c023f331f6f62870`
- `net/minecraft/world/effect/MobEffects.java` = `35d97c1741ec1379b05f41b8452229870c87a75633d312cd0b20d7fd901b4d08`
- `net/minecraft/world/effect/MobEffect.java` = `ae9af2ce81ba3e9bf9f28a216b6b9953dd684d19721bbc29085465957e952b5e`

**26.2 unobfuscated**
- `net/minecraft/world/entity/Entity.java` = `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5`
- `net/minecraft/world/entity/LivingEntity.java` = `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a`
- `net/minecraft/world/entity/player/Player.java` = `8decc71b9c780664578ddb14591db2a2f207c72c05b676edded6f8e964576531`
- `net/minecraft/world/entity/ai/attributes/AttributeInstance.java` = `c7cbe38bfdc1b4aafcf623bd1b604200cc6190641091369ee9bb9303e8f07ad5`
- `net/minecraft/world/effect/MobEffects.java` = `532df5c7cd47edd3d9d091c2eae0962c279224c8f5f206860e9df45bc3b0c684`
- `net/minecraft/world/effect/MobEffect.java` = `07f7c99dc3a6d00d684f5ef88d1861fcada7fe83f80c718bb172ac33056d5555`