# 1.21.10 → 1.21.11 local effects and movement attributes

## Finding

No source-confirmed player-locomotion difference was found in the assigned slice under equal active effect inputs and otherwise equal attributes. No positive finding is warranted.

## Evidence identity

Both exact-version sources are the canonical local Mojmap decompilations. Their ready markers report `status: ready`, version IDs `1.21.10` and `1.21.11`, Mojmap, Vineflower, Java runtime 25, and 6,386 / 6,622 source files. Both provenance records identify the same exact preparation run (`--versions=1.21.8,1.21.10,1.21.11 --mappings=mojmap`), pinned tools and artifact manifest SHA-256 `c8210b15012dcc4e109c2f73015b4117abec1f8ab04fdd375dbc11419e9e246c`. Ready marker source-manifest hashes are `4be26049350c1b314a0b198022cb1e7ab1e745e104047de7a5c7d099a9d7b7e1` and `0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555`. Each cited source file's SHA-256 matched its entry in that version's `mojmap.sources.sha256` manifest.

## Compared slices

- `net/minecraft/world/entity/LivingEntity.java`: `tickEffects`, effect add/update/remove paths (`addEffect`, `forceAddEffect`, `onEffectAdded`, `onEffectUpdated`, `onEffectsRemoved`), `getJumpPower`, `getJumpBoostPower`, `getDefaultGravity`, and relevant effect readers. The client still ticks active effect instances in `tickEffects`; server duration expiry still removes effects through `onEffectsRemoved`; modifier installation/update/removal remains server-gated as before. The movement readers use the same order and constants: jump power multiplies attribute strength, input scale, and block jump factor, then adds jump boost; jump boost remains `0.1F * (amplifier + 1.0F)`; default gravity reads `Attributes.GRAVITY`; effect predicates such as Dolphins Grace and Slow Falling remain at their same readers.
- `net/minecraft/world/effect/MobEffectInstance.java`: `tickServer`, `tickClient`, duration decrement, expiry downgrade to hidden effect, and amplifier-driven effect callbacks. These bodies preserve the same gates, call order, and arithmetic.
- `net/minecraft/world/effect/MobEffect.java`: attribute modifier creation/removal. Modifier amount remains template amount times `(amplifier + 1)` and add/remove still uses the same modifier ID and permanence behavior.
- `net/minecraft/world/entity/ai/attributes/AttributeInstance.java`: lazy cached-value calculation and operation order. It remains base value plus additive values, then base-scaled modifiers, then total-scaled modifiers, followed by the attribute's clamp. `AttributeMap` reads and dirty invalidation behavior are unchanged.
- `net/minecraft/world/effect/MobEffects.java` and `net/minecraft/world/entity/ai/attributes/Attributes.java`: the existing speed/slowness modifiers, jump-boost safe-fall modifier, and movement attribute registrations preserve identifiers' namespace/path, numeric values, operation enum, and registration order. Mojmap renames `ResourceLocation` to `Identifier`; that type rename does not change these existing identifiers or calculations.

## Nearby changes and boundaries

1.21.11 moves the `effectsDirty` invisibility/glowing-status refresh from the end of server `tickEffects` into `updateDataBeforeSync`. That affects synchronized visibility status, not movement attribute state or any assigned movement reader. `LivingEntity.getJumpBoostPower` still reads the same active effect instance directly. 1.21.11 also adds Breath of the Nautilus and an air-supply refill gate; neither changes locomotion or an existing movement attribute, so they are outside this finding. The player travel implementation changed elsewhere in this pair and is outside this assignment.

This is a bounded same-state source comparison, not a claim about packet arrival timing, server/client replication, or parity across the entire tick. Existing external effect inputs are explicitly held equal; effect application arriving through network packets was excluded by assignment.

## Terminal source hashes (SHA-256)

| Relative Mojmap source | 1.21.10 | 1.21.11 |
|---|---|---|
| `net/minecraft/world/entity/LivingEntity.java` | `b8b49d60769203f7bd5afe4a1bffcdcdbec30be28960324cdc43a2df85a6eb66` | `19ed2d565858c401c69a06750b054a633a20ea864cab3a753b5c767f0bdd60e8` |
| `net/minecraft/world/effect/MobEffectInstance.java` | `8b35c542a25090f7a45fa019a5263ea51cc1beb2d58605dd4ed65e805035bc36` | `d786ece1cbc91c67907c422a9369bbbb9949af35a76dcc44ee8d1767c22c6367` |
| `net/minecraft/world/effect/MobEffect.java` | `a59b16b493d0c1fe164dc09e28a00050f4bf47ad95df0ed67e205b46b10a0e66` | `1c4877780a246facd2e5ac392763c960580881d9ea90335d0841a5e982a12df7` |
| `net/minecraft/world/entity/ai/attributes/AttributeInstance.java` | `c2d4f89e7ae2abbe856e7e1aa5bff2ca613073cbda282c1975636e479ba8e346` | `4b89cf81cf75b16ed743b6bd0a683154c481247230b56dfa37c11ccfe17004c1` |
| `net/minecraft/world/entity/ai/attributes/AttributeMap.java` | `06088d7aeedd65b468b807c3a500da7a3a16d787917812a55dd7b81ad60cd079` | `c8dfebeefa367556ff706951cacea308c18da53fa28108c18f59789e6f1b60e2` |
| `net/minecraft/world/effect/MobEffects.java` | `59460ae5a2087122e88eb4e6f799466ed3ea313a04a9e9162a9abf76dea440ae` | `e27eef187ce134c3b64300fd7a4c7f691d553a172ec26ebac79797a030db7049` |
| `net/minecraft/world/entity/ai/attributes/Attributes.java` | `b941c0556d9ed3c3ce1ffd4c993c9902287a690230e1510696ff13f9efdf2020` | `62129028c2f7beb6d2f87bcd3345e63a7be8fc3950a7ecdbc6ee21f8fb89f843` |