# Attribute modifier iteration candidate closure

The 1.20.4 → 1.20.5 `AttributeInstance` storage refactor changes the iteration order for some same-operation modifier sets, but this audit did not establish an ordinary `LocalPlayer` movement delta from vanilla modifiers.

Exact sources: Mojmap, 1.20.4 and 1.20.5; 1.20.6 is later context. Ready identities and source-manifest hashes were checked against the ready markers before reading the classes. The 1.20.5 and 1.20.6 `AttributeInstance.java` files are byte-identical.

In 1.20.4, `AttributeInstance.java` stores each operation in a `HashSet` (lines 21, 50–52) and applies `MULTIPLY_TOTAL` modifiers sequentially to the running value (130–147). In 1.20.5, it stores each operation in an `Object2ObjectOpenHashMap` keyed by UUID (lines 21, 50–53) and iterates `values()` while applying `ADD_MULTIPLIED_TOTAL` sequentially (140–161). These sources establish that ordering can change; the arithmetic is still sequential multiplication.

The vanilla player movement-speed cases checked here are the base `0.1F` value (`Player.createAttributes`, .4 lines 204–210; .5 lines 221–227), sprint modifier `0.3F` (`LivingEntity`, .4 lines 127–129, 1951–1958; .5 lines 134–136, 2008–2015), and Speed / Slowness modifiers `0.2F` / `-0.15F` (`MobEffects`, .4 lines 10–19; .5 lines 16–25). Effect amplifiers scale the template amount by amplifier + 1 (`MobEffect`, .4 lines 123–142; .5 lines 173–176). For the three modifiers together, UUID hash placement gives a possible set order of Slowness → sprint → Speed and map order of sprint → Slowness → Speed. No same-operation equipment movement modifier was found in the paired vanilla player paths.

Player movement consumes the movement-speed value as a float: `Player.aiStep` casts it before `setSpeed`, and `Player.getSpeed` returns that cast (.4 `Player.java` lines 523–524, 1488–1490; .5 lines 532–533, 1510–1512). The stored `LivingEntity.speed` is a float (.4 lines 2255–2260; .5 lines 2322–2327), and `LocalPlayer` travel uses `getSpeed`. Arithmetic evaluation of the vanilla sprint + Speed + Slowness factors at the player’s default `0.1F` base, using the paired operation order and amplifier values 0–255, produced the same movement float in every case after sanitization. The attribute range is `[0.0, 1024.0]` on both sides (`Attributes.java`, .4 lines 38–40; .5 lines 60–62; `RangedAttribute.sanitizeValue`, lines 27–29), so extreme negative totals clamp to the same lower bound before the movement read. The only other direct LocalPlayer read found is `AbstractClientPlayer`'s field-of-view modifier, which is outside movement scope.

Hashes (SHA-256):

- Ready source manifests: .4 `fd3c8668483e5ff208c847474f6cbd3952a909c4aa602ed98b24e3d11a9602a1`; .5 `162dac6c4f1d539a1c2cea0255b7938a5281c207ab460bdc265358016d732484`; .6 `56aae10684471d7abb1c366bd5dd431ab976112a87e6cc5c687a68f1eff06311`.
- `AttributeInstance.java`: .4 `F78CD38B3FE74F98950ECC0FE4AE420F99299DC5BB43360E4E6B3C76B3509672`; .5/.6 `5D060C1740336835C74138D3C5FE3AE5F3B7F6A1C2F16F6844289F18EF90782A`.
- `AttributeModifier.java`: .4 `F6A16D77B7620E72756BC079BA3AADE4FC3DA6A3763C1AF8FE21320F89551100`; .5 `5ACF5EC4D5A3361AF6DA18EC7C26DDBFDCE6FA8DECE778BE7C20D345CA846C4C`.
- `MobEffects.java`: .4 `E8974D2E12C774AAB348A65D0ADE44FD3A8522270C403CB5DCC2C6DB3C25FBAE`; .5 `9B24DEE8A23A4B8730FDFA7EA9CBD560C9370599CC282FAC1C0755F5894E7645`.
- `LivingEntity.java`: .4 `F7BC53DB24C1798F19F9BD6F6356C86D5E560E9C8E8AAC60DECAF15CC785E07D`; .5 `C66EC8DC3B1856E490E5834A46185589030D9FBC411E3E2CE64C73203CD753B2`.
- `Player.java`: .4 `218DA60BC4F8C9279F56EB87E2CF2C0AF79562FBE3D818A408429C656EFB95DD`; .5 `785D93CCC94E1F912E545B2C0B355EDEB352B44EE8E83A69364DAEC35266DBE2`.
- `MobEffect.java`: .4 `FE3D18C51352A1E84CF96BCF6386FD19B4FA6BF6D7BD667B813699FAB57EFAE5`; .5 `E5EF74736E7A4B8CD5103FB5BBBE10B1AEB0BE3531CF5600A39E08761C3746E2`.
- `Attributes.java`: .4 `287DCD477CCCA2AEBD24AD28DB7B0E7A1E9DC9BBEB272D330CD15504B8669FF7`; .5 `7E30D87C7A58B14D0052D2F9F7319D997B49AE7D025579CD762D28E845B2E82E`.
- `RangedAttribute.java`: .4/.5 `7885AA1E82E9B1E89427CE81A5ED8F0E1CFAD796E12D577EAD81A52FD9791C3C`.

Limits: this closes the concrete vanilla player movement-speed iterator candidate, not arbitrary extension-supplied attributes or all attribute consumers. It is not a full-pair coverage audit and makes no pair-freeze claim. No source was changed; no build, test, launch, or decompilation was run.
