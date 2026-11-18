# Source coverage report — 1.10.2 to 1.11, Feather

Terminal: **COMPLETED**

## Exact source identity

Compared exact releases `1.10.2` and `1.11`, both mapped as `ornithe-feather`, from the canonical read-only source roots `decompiled_minecraft/1.10.2/ornithe-feather/` and `decompiled_minecraft/1.11/ornithe-feather/`. Readiness and provenance identities were inspected locally; no source generation was run.

| Evidence | 1.10.2 SHA-256 | 1.11 SHA-256 |
|---|---|---|
| `ornithe-feather.ready.json` | `4c6d236e1d06ffc365452c48e73dfa2bf2414bbf4ea67a24a7010c5583906a2d` | `3c4102230dba0f9d2d07f752bbd2a19d2717375f6e31fb618c0450b7fd82bd45` |
| `ornithe-feather.provenance.json` | `13b208320bd6316d4b20eb0733f226cae151cae0ffd55ef3331a97223114adf6` | `6f775c806fd1401a4d1756e9ef88da8d73185fe1d13598235f85c424405f4c40` |
| `ornithe-feather.sources.sha256` | `91b0f478acb7b6f13463c35b268a30d2806f583402631a56f54ce2eb70d1ec71` | `8b3e5da58494f1dba77410960753b0bf646425a1ada491d8242cc71e6c7a59ca` |

The readiness markers identify 1.10.2 Feather build.2 and 1.11 Feather build.2, and the provenance markers report successful exact-version decompilation. The 1.10.2 tree is a published ready tree without an embedded provenance file inside the source root; its sibling provenance record is the local provenance evidence. Both source-manifest digests agree with their ready markers.

SHA-256 of the examined source files:

| Relative path under mapping root | 1.10.2 | 1.11 |
|---|---|---|
| `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `a9637065f21ad67464eb5c204c74ebf228c3bb0da8a96ddf4ae73c0490fed443` | `65c2747bd8c70def6be7f41f624d4c9493342b39ae7bed7967f9ff63608f59ed` |
| `net/minecraft/client/entity/living/player/Input.java` | `9e704cfe7fdc55c4eab78670e60cf392ba6817adbd5e7451d3a86f11da8bf50e` | `9e704cfe7fdc55c4eab78670e60cf392ba6817adbd5e7451d3a86f11da8bf50e` |
| `net/minecraft/entity/living/player/PlayerEntity.java` | `a055b84b98d98e828e177cad9bba47a1334ee9bf3ecd4331792979106235a302` | `a0d787681de5caecda03fea1536dac0c07e6ca5cfbfe3b5fbb700cbe77c86fb6` |
| `net/minecraft/entity/living/LivingEntity.java` | `d40dd476b6b68c6ce45b4202823475deb546ecda2284da330ff6724b33815e82` | `1f2e31fa6a905ccc2a51a0338f2aaf448f2b0569f28644e8205d864e570d9b2b` |
| `net/minecraft/entity/living/effect/StatusEffect.java` | `cd56502d70b9c742dcffc61fbac6337f268838e35f268ffead4610ff964bea89` | `b7953929609d29d36fb4714013870f9d3b6362b71682f3c06b597536de3eb2db` |
| `net/minecraft/entity/living/effect/StatusEffectInstance.java` | `652e49d0949f2626ec050901705bffbf59cdeafea9ecd7f384939b25d88016f2` | `d3cd002f44ded4cd4f6045af6849d73470d56d05c59e62dee4dd99c2fc47f102` |
| `net/minecraft/entity/living/attribute/ModifiableEntityAttributeInstance.java` | `4eec5f0347baba347a84e4c66702321fb34157329f95e70e8ce541aeb4bf6b7a` | `4eec5f0347baba347a84e4c66702321fb34157329f95e70e8ce541aeb4bf6b7a` |
| `net/minecraft/entity/living/attribute/AbstractEntityAttributeContainer.java` | `b1576cdc11ee7715080bfdd205601bf40f884af9efa8f5dc0eae07233c43786f` | `62d1b62a04daeb72335871435def3a7b1bcd30d7169ef68d9d24605d37895940` |
| `net/minecraft/client/network/handler/ClientPlayNetworkHandler.java` | `af3efba0a1d5a0824c29c4af4ac7f8e4484d1787a82208e4d72bcc65f957e656` | `cef5d643eca0b32119571eff5201705cc8c99125f8266f763172202d89e3a40b` |
| `net/minecraft/entity/Entity.java` | `05da145effa19a6ef7934cc276e89226373b67c12f4ce89a8ce2183f29039f77` | `3209feaacd1e798b521a193eeb081d92c96881a70a18254ef24a58e911a4fd97` |

## Ordered coverage and result

1. **Client input to local-player pre-travel state and native play gate — no finding.** `Input.java` is byte-identical. `LocalClientPlayerEntity.tick()` (1.10.2 lines 152–177; 1.11 lines 159–184) checks the player-position chunk before `super.tick()`. Its local-player path is client-only by class/call placement. `mobTick()` (1.10.2 lines 620–784; 1.11 lines 632–796) preserves the same relevant order: snapshot old jump/sneak/forward input; tick input; item-use scaling; autojump; sprint-start/stop eligibility; flight toggle; camera flight vertical input; mount-jump update; then `super.mobTick()`. Under the supplied ordinary-input state, these gates and writes match. The ordinary loaded-chunk eligibility check also matches. Network sends after the local tick were not evaluated as a movement-response operation.

2. **Movement-speed supplier, modifiers and consumer order — no finding.** `PlayerEntity.mobTick()` (1.10.2 lines 413–493; 1.11 lines 417–497) calls `super.mobTick()`, sets the base from abilities only when `!world.isClient`, derives `speedInAir` and sprint bonus, then calls `setSpeed((float) movementSpeedAttribute.get())`. `LivingEntity.setSprinting()` removes the known sprint modifier before conditionally adding it (1.10.2 around lines 1208–1216; 1.11 around 1258–1266). Equipment replacement still removes old slot modifiers before adding new slot modifiers in `LivingEntity` (1.10.2 lines 434–447 and 1541–1563; 1.11 lines 442–455 and 1595–1617). The attribute evaluator is byte-identical: base, operation 0 additions, operation 1 multipliers against the operation-0 value, operation 2 multipliers, then clamp. No equipment or effect modifier ordering/math change was found in this slice.

3. **Effect lifecycle and received movement gates — no finding.** `LivingEntity.addStatusEffect`, `removeStatusEffect`, `onStatusEffectApplied`, `onStatusEffectUpgraded`, and `onStatusEffectRemoved` match in the compared bodies (1.10.2 lines 606–669; 1.11 lines 620–683). Attribute modifiers are changed only on non-client worlds; the client still updates effect state/`effectsChanged`. The client status-effect receive and remove handlers preserve the same packet-thread, entity/effect validity, instance construction, apply/remove order (1.10.2 handler lines 1188–1200, 1266–1272; 1.11 lines 1219–1231, 1300–1306). The speed/slowness definitions in `StatusEffect` retain UUIDs, magnitudes and operation 2; received effect amplifiers continue through the same multiplier constructor. Jump boost remains read by the same local jump path (`LivingEntity.jump`, 1.10.2 around lines 1305–1310; 1.11 around 1359–1364). `PlayerEntity.jump()` still delegates to `super.jump()`. Server-synchronized attributes remain the client speed/slowness value source; the network attribute handler sets base then clears and adds packet modifiers in the same order (1.10.2 lines 1683–1711; 1.11 lines 1722–1750).

4. **Default ordinary input/pose and matched modifier slices — no finding.** `Input.java` is byte-identical. `PlayerEntity.updatePlayerPose()` retains the same normal/sneak/fall-flying sizing and collision gate (1.10.2 lines 296–319; 1.11 lines 299–322), and `PlayerEntity.getEyeHeight()` retains the same ordinary/sneak/flying/sleeping precedence (1.10.2 lines 1759–1770; 1.11 lines 1752–1763). For the ordinary and sneak transitions in this slice, width remains 0.6 and the observed `Entity.setSize()` shrink-only branch is not entered.

## Coverage limits / excluded observations

- This is only the four assigned compatible slices, not full tick-graph or pair coverage. Travel arithmetic, collision/contact and shapes, push responses, fluids, packet movement responses, and downstream callers outside the stated dependencies remain unexamined.
- `Entity.setSize()` differs for a width shrink: in 1.11 it rebuilds the box centered on current X/Z; in 1.10.2 it retains the old minimum X/Z. The examined `PlayerEntity.updatePlayerPose()` can request width `0.2F` while sleeping, so the changed branch is relevant to a sleeping-pose transition. That is outside this report's default ordinary-pose slice, and bed-entry eligibility is excluded by project scope. No finding draft was created for it. A separate bounded sleep/wake box-response investigation would be needed before classifying any movement consequence.
- The local-player elytra-start-fall-flying check has a nullable stack guard in 1.10.2 and none in 1.11. That branch emits a movement-action packet, is outside the assigned direct-packet scope, and differs alongside the release's `null` versus empty-stack representation. It is not reported as a movement finding here.

No independent difference draft was produced because none of the in-scope operations yielded an actual difference. No product source, wiki lane, Git history, tests, game/runtime, or generated source was accessed.
