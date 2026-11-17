# 1.15.1 → 1.15.2 candidate resource/dependency closure

Status: **bounded NO_PACKAGED_RESOURCE dependency** for the two accepted movement-delta call paths only. This does not close pair-wide resource/provider coverage, does not establish a patch onset, and does not freeze the pair.

## Bound source evidence

This note is based on the exact Mojmap pair and accepted source report `build/source-mid-patches-20261117/1.15.1--1.15.2/ordinary_player/source-report-r2.md` (SHA-256 `322E867BEA50AF506E4438F4809D3C893B5E2897B656F02C0855D41A43274D2B`) with its citation-correction audit `ordinary_player/audit-r2/verdict-r2.md` (SHA-256 `710103010D2473E0F9C9D3ECBD0051222F6CAD00D1382A90E91AC5B74104F0E3`). The accepted static applicability packets are the Elytra ladder report (`elytra_ladder_applicability/report.md`, SHA-256 `594626744739A9DE195E85BA205230EABC8119714779BCDEB2C56C11580631F3`) and hidden-effect restoration report (`effect_restoration_applicability/report.md`, SHA-256 `EE06CC078BCD7901B9F5B12740F94A401E226A87C4933A836F623C893052FC39`). This bounded note makes no change to those claims.

The exact source roots, mappings, manifests and provenance are in `build/source-inputs-115-116-20261117/README.md`. Both source maps are Mojmap. Endpoint source-manifest SHA-256 values are 1.15.1 `c8fdce2f9432b72fc13c19a48f9ab3be203cac41a617139e81bac3552245912d` and 1.15.2 `cb7fd93f8730d8b2813857744f8572060535c7de44fcee86222acd1ceb6f43e7`; provenance SHA-256 values are `6a7af1f5d65d7af165c2ab9a90c1fae20169b38e70c8e92dbc5f59d94efe9461` and `d4802d35ee2927a44d753871f184c3255c060eb94457a5c65a8bc121a087954e`.

Every hash in the table below was re-computed from the exact source file and compared with the corresponding manifest entry; all 22 disk/manifest comparisons matched.

| Exact Mojmap source | 1.15.1 SHA-256 | 1.15.2 SHA-256 | Bounded caller role |
|---|---|---|---|
| `net/minecraft/client/player/LocalPlayer.java` | `7227da5233e3c72ced008187b92a6a86842f84778eaa9f40fa1eb117286af823` | `3a9019bd7b860e251c23fd8d0cd70b7f5b38566d34470c4e29b1014ef689ccbd` | Jump-triggered Elytra branch and movement input. |
| `net/minecraft/world/entity/player/Player.java` | `6985e11ad9be553e9a2f25e5decc20a9da5c3b59b68f4e83a5b9f448cd2e48a0` | `1ba2724c22163862b8f7fdfdea5a04a66e4db26a119d7e5360ba024724a34793` | `onLadder` caller route, `tryToStartFallFlying`, `getSpeed`. |
| `net/minecraft/world/entity/LivingEntity.java` | `139c3866ad2c6a1ad73496f22ff1a4b5259998442092ffbafb8664428e28bf79` | `46d243bb7e51f7b54404aa1d7d6e6b827682d0f5925d02847c4193306c4d5e54` | `onLadder`, `tickEffects`, `onEffectUpdated`, attribute consumers. |
| `net/minecraft/world/item/ElytraItem.java` | `701e25f13e9ba8db74aca774f404a5281b2a58f4f7dc72e3aeabac76bbf28b43` | `701e25f13e9ba8db74aca774f404a5281b2a58f4f7dc72e3aeabac76bbf28b43` | `isFlyEnabled` item-damage predicate. |
| `net/minecraft/world/item/ItemStack.java` | `5118b958c7e3627e7c1f9fe24f316d7a01580c0d2c6a54c58f19e9b3661b51ae` | `5118b958c7e3627e7c1f9fe24f316d7a01580c0d2c6a54c58f19e9b3661b51ae` | Elytra item identity/damage and item NBT. |
| `net/minecraft/world/effect/MobEffectInstance.java` | `80cc2a91b77d4687a9b36bcb38e3107e3b2b8f859c69893e4eec0e98b4941b5e` | `4999d12cd468e402506f067dbd8694b1c8125dd9131e4f8a2fcc1848f616f180` | Hidden-effect stacking, duration tick, restoration. |
| `net/minecraft/world/effect/MobEffect.java` | `f218a040ff9f77272395245c4ffecc3f2266335600b79be0a5b2823c46ad7dd0` | `f218a040ff9f77272395245c4ffecc3f2266335600b79be0a5b2823c46ad7dd0` | Attribute-modifier application. |
| `net/minecraft/world/effect/MobEffects.java` | `472b25ede6bed3ad3ed1090db3310cea9708b637f111981bcf083f492c030412` | `472b25ede6bed3ad3ed1090db3310cea9708b637f111981bcf083f492c030412` | Built-in Speed/Slowness effect and modifier declarations. |
| `net/minecraft/world/entity/ai/attributes/AttributeInstance.java` | `60df48ab672764ac55e6ed398be73b8b0a1fbe9be5c133a705cfd0b000faad9e` | `60df48ab672764ac55e6ed398be73b8b0a1fbe9be5c133a705cfd0b000faad9e` | Movement attribute storage/modifier operations. |
| `net/minecraft/world/entity/monster/SharedMonsterAttributes.java` | `f76db9a5159192af7548be4b521581038fcbc68879ba41d5441da91f543737ab` | `f76db9a5159192af7548be4b521581038fcbc68879ba41d5441da91f543737ab` | Built-in `MOVEMENT_SPEED` attribute. |
| `net/minecraft/client/multiplayer/ClientPacketListener.java` | `d806d286af4b93d7c61c3c884f344a5870ce1871fb0a811506031adf78fafc30` | `d806d286af4b93d7c61c3c884f344a5870ce1871fb0a811506031adf78fafc30` | Client effect/attribute packet application. |

## Candidate callpaths and external-data check

**Elytra ladder gate.** `LocalPlayer.aiStep()` reads the jump edge, checks `LivingEntity.onLadder()`, then follows the chest-slot item test and `Player.tryToStartFallFlying()` to `ElytraItem.isFlyEnabled()`. `onLadder()` reads the current foot `BlockState`, compares the built-in block identity with `Blocks.LADDER`, `Blocks.VINE` and `Blocks.SCAFFOLDING`, and for `TrapDoorBlock` checks its `OPEN`/`FACING` properties and the lower block identity `Blocks.LADDER`. The Elytra gate reads `Items.ELYTRA` and `ItemStack` damage; it performs no item/block/fluid tag lookup and loads no datapack or packaged tag membership. `ElytraItem` also registers a `ResourceLocation("broken")` item-model property in its constructor; this is a rendering predicate and is not read by the movement caller above, so it is outside this movement-resource dependency.

**Effect restoration.** `MobEffectInstance.update()/tick()` stores and restores `MobEffect` instances and their numeric duration/amplifier fields. `LivingEntity.tickEffects()` calls `onEffectUpdated(instance, true)` when restoration occurs; that method removes and reapplies the effect's attribute modifiers from the built-in `MobEffect` object. `MobEffects.MOVEMENT_SPEED` and `MOVEMENT_SLOWDOWN` are statically registered in Java with hard-coded `SharedMonsterAttributes.MOVEMENT_SPEED` modifiers. On the client, `ClientPacketListener.handleUpdateMobEffect()` resolves the numeric effect through the built-in `MobEffect` registry and applies a packet-created `MobEffectInstance`; `handleUpdateAttributes()` applies packet snapshots to `AttributeInstance`. `Player.getSpeed()` reads the movement attribute value. These paths consult source-defined registries, instance NBT/packet state, and in-memory attribute values; they do not read external JSON, tags, or packaged block/item/effect data files.

## Bounded disposition

No packaged resource payload, tag membership, or archive entry is an input to either accepted candidate movement delta. No resource extraction is required to close these two candidate-specific dependencies. This is not a claim that the entire 1.15.1→1.15.2 player movement graph has no resource dependencies: the accepted pair source report still leaves its wider resource/provider inventory, other movement operations and full-pair closure separate and PARTIAL. No runtime or map-occurrence claim is made here.
