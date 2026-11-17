# Independent source audit: knockback modifier inputs r2

## Verdict

**ACCEPT** for the bounded source dependency claim, bound to `build/later-source-20261116/26.1.2--26.2/knockback_modifier_inputs_revision_r2/coverage-r2.md`, SHA-256 `B4DE880AD6BB57BFEFC33ADAFEE4923986E67E43EE9551DEA3D6BAC3F178A70E`.

The r2 correction resolves the prior client-side equipment-update overclaim. It says periodic equipment modifier collection occurs on server-side entities, while a positive LocalPlayer `ATTACK_KNOCKBACK` value through synchronized or other client-side state remains untraced. It does not claim ordinary client gameplay reaches the `Player.stabAttack` branch.

## Evidence integrity

- Canonical inputs are `decompiled_minecraft/26.1.2/unobfuscated/` and `decompiled_minecraft/26.2/unobfuscated/`.
- The actual provenance sidecars are `decompiled_minecraft/26.1.2/unobfuscated.provenance.json` (SHA-256 `8d727f880cc91f4a297e148c5b75976303d051457334bc689268fbdf631a012a`) and `decompiled_minecraft/26.2/unobfuscated.provenance.json` (SHA-256 `a328f5333a1b27aadde4422b23159a323ae219ccd26f11207c1a5461366f27d1`). Both declare their respective version IDs and `unobfuscated` namespace.
- Ready sidecars are `decompiled_minecraft/26.1.2/unobfuscated.ready.json` (SHA-256 `f24af0f4d38543c2bd9c19b50f347d4e89d6027050e4b21ae070734843946bb7`) and `decompiled_minecraft/26.2/unobfuscated.ready.json` (SHA-256 `f9406adb6bf7cb4c1ab0792a798ab2cb90e082ad4c2eee032c9f674d0ea8070f`). Both identify the exact release, `unobfuscated` mapping, and `ready` status. Their declared manifest digests match the actual sidecars at `decompiled_minecraft/26.1.2/unobfuscated.sources.sha256` and `decompiled_minecraft/26.2/unobfuscated.sources.sha256`.
- All nine Java paths in the report's hash table were recalculated against current bytes and matched their rows in the exact source manifests on both sides.

## Source checks

- `LivingEntity.tick` in both releases calls `detectEquipmentUpdates()` only inside `if (!this.level().isClientSide())` (26.1.2 `LivingEntity.java:2703–2728`; 26.2 `:2769–2794`). That helper delegates to `collectEquipmentChanges`; the latter applies modifiers from nonempty, unbroken equipped stacks. The modifier body itself has no client/server guard, but the periodic tick caller is server-gated. The report's revised distinction is accurate.
- The paired `LivingEntity.getKnockback` bodies read the effective `ATTACK_KNOCKBACK` attribute and divide it by two. Only the `ServerLevel` branch invokes `EnchantmentHelper.modifyKnockback`; the non-server branch returns the attribute contribution divided by two. `Player` does not override this method.
- `ATTACK_KNOCKBACK` has default `0.0` and range `0.0..5.0` on both endpoints; the player attribute builder extends `LivingEntity.createLivingAttributes()` and adds no second attack-knockback value. This establishes the unmodified base only, not every possible local effective value.
- `ItemStack.forEachModifier` forwards item attribute components and matching enchantment attribute modifiers in both versions. The direct knockback-enchantment effect helper accepts `ServerLevel`, and `getKnockback` calls it only in its server-level branch.
- The report's bounded caller observations are supported: `Player.stabAttack` gates its knockback block on `dealsKnockback`; `PiercingWeapon` passes its configured field and defaults the codec field to true when omitted; `KineticWeapon` computes `dealsKnockback` from a present condition and its condition test. Concrete packaged component values and LocalPlayer invocation are explicitly left open.

## Limits

Acceptance covers only the stated value/modifier dependency slice and the corrected server-only equipment-update caller. It does not establish LocalPlayer's effective attribute state or client reachability, packaged item/enchantment values, the separately analyzed `Player.stabAttack` source difference, or full-pair coverage. The 26.1.2→26.2 pair remains partial; no runtime or gameplay conclusion follows.

No backlog, wiki, product checkout, Git history, artifact payload, build, test, game runtime, or source generation was inspected or performed.
