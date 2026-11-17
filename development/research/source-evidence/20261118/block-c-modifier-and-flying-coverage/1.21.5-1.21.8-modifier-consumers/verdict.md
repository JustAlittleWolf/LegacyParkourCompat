# Independent audit verdict — 1.21.5–1.21.8 modifier consumers

Status: COMPLETED for the assigned finite Java source slices. The claim of no changed Java operation in scope is independently supported. Packaged built-in resource definitions remain a separate, unverified applicability gate.

## Immutable input identity

Audited report: `c-queue-1215-1218-modifier-consumers/report-r2.md` (UTF-8 source bytes as provided).
SHA-256: `c7f7e356e14be0eac09c73553ed4a0076671172e42f07ffd742942f30e76c66a` — verified.

Canonical evidence: `decompiled_minecraft/1.21.5/mojmap/` and `decompiled_minecraft/1.21.8/mojmap/`. Both ready markers identify the exact release and Mojmap, and their source-manifest digests match the report: 1.21.5 `365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9`; 1.21.8 `8ffb76cea647a2ba4fe58e000678f751bea2c40ea6358bae492e962ed1d9d008`.

## Independent checks and verdicts

- **Effect update and expiry:** `MobEffectInstance.java` and `MobEffect.java` are byte-identical across the pair. In `LivingEntity.java`, compared the paired `tickEffects`, `addEffect`, `removeEffect`, `onEffectUpdated`, and `onEffectsRemoved` paths including surrounding calls. Expiry removal and update callbacks retain the same ordering and attribute application/removal behavior. The enclosing file changes do not alter those paths.
- **Client application:** The report states the incoming effect packet route was checked. This audit supports the lifecycle entry points above; the assigned report's packet comparison remains its reported evidence and I did not broaden this verdict beyond those source paths.
- **Held/worn/enchantment modifiers:** `EntityEquipment.java` and `EnchantmentHelper.java` are byte-identical. `LivingEntity` equipment collection/detection, slot updates, and modifier transitions were traced at their paired locations; observed full-file differences do not change slot order or the relevant modifier calls. No movement-relevant difference found.
- **Modifier aggregation:** `AttributeInstance.java` differs in persistence representation (`save/load` versus `pack/apply`). The operation-specific modifier maps, modifier insertion/removal, dirty/cache behavior and computed-value evaluation remain unchanged; the changed packed persistence additions do not modify the ordinary aggregation procedure. `AttributeMap` persistence changes likewise do not change lookup or ordinary modifier operations, as reported and checked against the paired class.
- **Player movement speed/default/clamp:** `Player` registers movement speed with `0.1F` on both sides; ability walking-speed base reset and `getSpeed()` cast from `getAttributeValue(MOVEMENT_SPEED)` are unchanged. `DefaultAttributes` movement-speed player entry is unchanged; observed diff adds a Happy Ghast default. `RangedAttribute.java` is byte-identical and still maps NaN to minimum and clamps with `Mth.clamp`.

No changed Java source operation was confirmed in the bounded lifecycle, equipment/enchantment aggregation, or ordinary player movement-speed default/clamp paths. No finding is warranted from these slices.

## Boundary

These decompiled Java trees do not establish packaged data resource completeness. Built-in item/effect modifier definitions and relevant tags remain unverified as a resource applicability gate; this does not undermine the bounded Java-source verdict. No product code, wiki, Git history, backlog, tests, launch, generation, or runtime behavior was consulted or exercised.
