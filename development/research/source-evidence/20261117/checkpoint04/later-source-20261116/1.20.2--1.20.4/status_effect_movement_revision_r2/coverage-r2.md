# Movement status-effect paths (bounded source coverage)

Pair: Minecraft 1.20.2 Mojmap → 1.20.4 Mojmap. Scope is status-effect definitions and direct player movement consumers for Speed, Slowness, Jump Boost, Slow Falling, and Levitation, plus effect tick/application on the client. This is a bounded no-difference coverage report, not a movement finding or pair freeze.

## Exact source identity

Older source root: `decompiled_minecraft/1.20.2/mojmap`; newer source root: `decompiled_minecraft/1.20.4/mojmap`; declared mapping namespace is Mojmap for both. Each endpoint has root sidecars `mojmap.ready.json`, `mojmap.provenance.json`, `mojmap.sources.sha256`, and `artifacts.sha256`. Both ready markers identify the exact endpoint and namespace and bind the source manifest digest; both provenance sidecars identify the corresponding release/namespace.

| Endpoint | Ready marker SHA-256 | Provenance SHA-256 | Source manifest SHA-256 | Artifact manifest digest declared by ready |
| --- | --- | --- | --- | --- |
| 1.20.2 | `5FF3322F287C250EE914C7CA3840D053BFFC3C2C5F35E91B59314D948292483F` | `1DAD9F725F6CB25B6568700FA0294F83B66EA9633B0D62ED354D7BD8B45D4F05` | `E439FF7E1D3F1D31E01BE1EDD141F94D917A9DC7FA0DE5F69904544CBBE62887` | `EE67DA9733953B30AE4077C587184E59772B745D780ABD27A7B023780B459F54` |
| 1.20.4 | `6225B97979420CC743C8B7156A0CE512EF27D0DBF48BFFFBC40D3E1C8CB044D1` | `10914D9B7B3FC29DE066CA24F7D905E4D4F0A451A6C81729A2D1981ADDFC1385` | `FD3C8668483E5FF208C847474F6CBD3952A909C4AA602ED98B24E3D11A9602A1` | `EE3EFC771D264C0BC49D5472ABB9763A646F8D221841D0A61201ECD991CB3948` |

The source manifest entries for every cited Java file below match its local SHA-256 at each endpoint. `MobEffects.java`, `MobEffect.java`, `MobEffectInstance.java`, and `ClientboundUpdateMobEffectPacket.java` are byte-identical across the pair. `LivingEntity.java`, `Player.java`, and `ClientPacketListener.java` differ as whole files; the listed movement-effect methods were compared individually and match exactly.

## Examined operations

- **Speed and Slowness definitions:** `net/minecraft/world/effect/MobEffects.java:10-19` registers `speed` with a `MOVEMENT_SPEED` `MULTIPLY_TOTAL` modifier of `+0.2F`, and `slowness` with `-0.15F`, using their respective UUIDs. The complete file is identical across endpoints. `MobEffect.addAttributeModifier` stores the attribute/template, and `addAttributeModifiers` replaces the matching modifier ID before calling the template `create(amplifier)` (`MobEffect.java:81-109`). The amount formula is implemented directly in the nested `MobEffectAttributeModifierTemplate.create(int)` member, which constructs `AttributeModifier` with `this.amount * (amplifier + 1)` and the declared operation (`MobEffect.java:140-142`). This file is byte-identical across endpoints; its SHA-256 is `FE3D18C51352A1E84CF96BCF6386FD19B4FA6BF6D7BD667B813699FAB57EFAE5` on each side.
- **Player speed consumption:** `Player.aiStep` calls `super.aiStep()` and then `setSpeed((float)getAttributeValue(MOVEMENT_SPEED))` (`Player.java:505-524` in 1.20.2; `506-525` in 1.20.4). `LivingEntity.getSpeed` returns that stored speed; `getFrictionInfluencedSpeed` and water/air-relative travel consume it. The two `Player.aiStep` method bodies match exactly. The method-level comparison establishes no difference here; it does not re-audit general attribute aggregation.
- **Jump Boost:** `LivingEntity.getJumpPower` adds `getJumpBoostPower`; the latter returns `0.1F * (amplifier + 1.0F)` while `JUMP` is present, otherwise `0.0F` (`LivingEntity.java:2003-2009` in 1.20.2; `2000-2006` in 1.20.4). `jumpFromGround` assigns this result to vertical delta movement. The consumer bodies match exactly. This records only the effect contribution, not a new review of jump impulse/callers.
- **Slow Falling:** in `LivingEntity.travel`, gravity starts at `0.08`; if vertical velocity is nonpositive and Slow Falling is present, gravity becomes `0.01` before fluid or ordinary movement branches (`LivingEntity.java:2038-2044` in 1.20.2; `2035-2041` in 1.20.4). This method body matches exactly. `aiStep` also resets fall distance when Slow Falling or Levitation is present (`LivingEntity.java:2594-2596` in 1.20.2; `2596-2598` in 1.20.4); that gate matches. Fall-damage calculation is outside this scope.
- **Levitation:** in ordinary non-fluid travel, the vertical component is adjusted by `(0.05 * (amplifier + 1) - currentY) * 0.2` instead of the gravity branch (`LivingEntity.java:2148-2158` in 1.20.2; `2145-2155` in 1.20.4). This consumer method body matches exactly. The separate `Player.tryToStartFallFlying` Levitation veto is not assessed here.
- **Effect lifecycle and client delivery:** `MobEffectInstance.tick` invokes the effect tick callback when applicable, decrements duration, promotes a hidden effect at expiry, then reports whether duration remains (`MobEffectInstance.java:177-199`); the entire source file is identical. `LivingEntity.tickEffects`, `onEffectAdded`, `onEffectUpdated`, and `onEffectRemoved` bodies match exactly. Server effect hooks install/remove effect attribute modifiers; update/removal refresh dirty attributes, while effect state is sent to passenger players. On the client, `ClientPacketListener.handleUpdateMobEffect` constructs an instance from packet effect/duration/amplifier/flags/factor data and calls `forceAddEffect`; `handleRemoveMobEffect` removes the active instance without client attribute-modifier installation. These handlers match exactly. `handleUpdateAttributes` separately applies each received attribute snapshot's base value and transient modifiers to the local entity; its method body also matches exactly. This identifies the two client state paths, not server update cadence or runtime behavior.

## Disposition and limits

No difference was found in these paired status-effect declarations, tick/application hooks, or examined player movement consumers. No finding is warranted for this slice. The source comparison does not prove which effect values are reachable in a given world, server/client timing under play, or movement parity. Potion/effect production paths and durations, other effects, fluid movement including Dolphin's Grace, attributes beyond these effect-specific routes, Elytra eligibility, attack/damage behavior, and the rest of either release pair remain unexamined by this report.

## Source-file identity table

SHA-256 values are exact source-file bytes under the endpoint roots above.

| Relative source file | 1.20.2 SHA-256 | 1.20.4 SHA-256 |
| --- | --- | --- |
| `net/minecraft/world/effect/MobEffects.java` | `E8974D2E12C774AAB348A65D0ADE44FD3A8522270C403CB5DCC2C6DB3C25FBAE` | `E8974D2E12C774AAB348A65D0ADE44FD3A8522270C403CB5DCC2C6DB3C25FBAE` |
| `net/minecraft/world/effect/MobEffect.java` | `FE3D18C51352A1E84CF96BCF6386FD19B4FA6BF6D7BD667B813699FAB57EFAE5` | `FE3D18C51352A1E84CF96BCF6386FD19B4FA6BF6D7BD667B813699FAB57EFAE5` |
| `net/minecraft/world/effect/MobEffectInstance.java` | `E1AAC04C3F5714C5C47F84375F1D930204BD177218C05D8C0B590D70381AA249` | `E1AAC04C3F5714C5C47F84375F1D930204BD177218C05D8C0B590D70381AA249` |
| `net/minecraft/world/entity/LivingEntity.java` | `5C481DA1FFC8684C4B30171C92E61FAD751E6A3A708D5BC6486AC9F96FF69828` | `F7BC53DB24C1798F19F9BD6F6356C86D5E560E9C8E8AAC60DECAF15CC785E07D` |
| `net/minecraft/world/entity/player/Player.java` | `25F263692FD6B2A737AA813315022A0CAF4E18C3661BA3BD706C6297F909F25C` | `218DA60BC4F8C9279F56EB87E2CF2C0AF79562FBE3D818A408429C656EFB95DD` |
| `net/minecraft/client/multiplayer/ClientPacketListener.java` | `0208F6942035ADAF2E787546851CA296F0E7EC2179D3610F694911EDA5283264` | `FF9C8222614551075B03454EE78712B0D39F0F51845E74BFF76A81486F09B42F` |
| `net/minecraft/network/protocol/game/ClientboundUpdateMobEffectPacket.java` | `88A2692D0A0FC83753EE1B6BA558030C7836D12D25B4752457FB86AC353A3F3A` | `88A2692D0A0FC83753EE1B6BA558030C7836D12D25B4752457FB86AC353A3F3A` |