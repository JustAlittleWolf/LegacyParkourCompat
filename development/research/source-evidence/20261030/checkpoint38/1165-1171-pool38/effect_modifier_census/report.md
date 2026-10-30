# 1.16.5 → 1.17.1 player modifier/effect/equipment census

## Scope and method

Bounded source-only review of Mojmap decompiled sources for remaining player movement-affecting effect, attribute-modifier, equipment, and direct-response readers, excluding the already assigned/closed Jump Boost addition, sprint/jump and fluid lanes. Examined exact paths below only. No product implementation, Git history, wiki material, or backlog was consulted. No build, test, launch, or decompilation was run.

**Source roots supplied by the coordinator:**

- `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.16.5\mojmap`
- `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.17.1\mojmap`

Both trees lack `.decompile-provenance.json`. The release/mapping directory names are the supplied identities; hashes below identify the exact locally read Java files but do not independently authenticate their publication inputs.

**Blindness incident:** one malformed search command had an unset PowerShell path variable. After its targeted lookups failed, `rg` searched the repository working directory and printed mixed product/research paths in its output. I stopped immediately upon seeing those paths, did not inspect or use any product implementation contents, Git history, wiki, or backlog, and did not incorporate any mixed-search result. All source findings below use only fully qualified paths under the two supplied `D:\...\decompiled_minecraft\<version>\mojmap` roots. The coordinator was notified.

## Source identities

SHA-256 is over each exact file's bytes. The shared speed/slowness effect definitions and attribute evaluator are byte-identical across the pair.

| Relative source path | 1.16.5 SHA-256 | 1.17.1 SHA-256 |
|---|---|---|
| `net/minecraft/world/entity/LivingEntity.java` | `B5D8A1A3C80F85D5D545B5A777E9E2A915DC002A7E31B5F5AD12BF7E285D7A88` | `33FD081AADB2B6FDC9EBF487DB6DA5B38C54F4B8676572790EE2203690D15E6F` |
| `net/minecraft/world/entity/Entity.java` | `F9A9A073FE3105A0AA53D0F21EC72E59084E8D21A14C1CD3BE75703865EE2666` | `AB28E1FBA924771EC048140DFD293EE5A46A7DFE81F71A1A0B1AECC1927232DE` |
| `net/minecraft/world/entity/player/Player.java` | `D2E26589BDB6A20DC914266DB06AA48F50811EFC792D6E63B3008C9199914960` | `724BB298499DABE489DFFD5CE7EC81C9773619A8D2C70044911EAE4C9E8FF481` |
| `net/minecraft/client/player/LocalPlayer.java` | `6011569E766BB1568609147BE9AA14E9C08C51948E3D3A60FD066E848F6A8C2B` | `C9A91CB6CB57806BC8D22E5BFE2D97DAAF21D5D2A48F34A6E2C53164C61C5812` |
| `net/minecraft/world/effect/MobEffects.java` | `64B3B592A48EAC1016C3A307B85C3E201689662DEA68C80B6D642CFFA35FD289` | same |
| `net/minecraft/world/effect/MobEffect.java` | `665E2365C273A8004D327020615B76A0F5FAA839BAB20F96A99F412C7330E812` | same |
| `net/minecraft/world/entity/ai/attributes/AttributeInstance.java` | `6CC7410E181197A56040682AAD1525AC1A110140BF7E99AD598C886F892345A5` | same |
| `net/minecraft/world/item/ItemStack.java` | `2BB228453C39F9792DC1496802E46220738DBF32855442696BAAA0288F791156` | `BA25D48580A3C586233956558905467DC183502EFC59334090B65D46F598DA59` |
| `net/minecraft/world/level/block/PowderSnowBlock.java` | absent | `4069B3EF70E2D0CE146BBFF79BE54B2BE99B7C7B32818C1EB45F11FD66F99AA3` |

`ItemStack.java` and other sources were read only for targeted member checks and are not part of the file-identity set above.

## Bounded no-difference results for eligible pre-existing movement paths

- **Speed/slowness effect attribute modifiers and attribute arithmetic:** `MobEffects` registers speed and slowness with the same UUIDs, amounts (`0.2F`, `-0.15F`) and `MULTIPLY_TOTAL` operation at 1.16.5 lines 12–22 and 1.17.1 lines 12–22. Those whole files have equal hashes. `MobEffect#getAttributeModifierValue(int, AttributeModifier)` remains `amount * (amplifier + 1)` at lines 166–168 in both; `AttributeInstance#calculateValue()` retains the same operation order (addition, multiply-base, multiply-total) at lines 130–147 and has an equal whole-file hash.
- **Soul Speed equipment modifier:** 1.16.5 `LivingEntity#tryAddSoulSpeed` lines 447–465 and `onChangedBlock` lines 467–478; 1.17.1 lines 480–498 and 526–537. The gates and expression match: movement block is non-air, Soul Speed level is positive, `onSoulSpeedBlock()` is true, movement-speed attribute exists, then the transient additive amount is `0.03F * (1.0F + level * 0.35F)`. The FEET stack durability roll remains server-side-independent source arithmetic with chance `< 0.04F`. The block-speed override is likewise unchanged (`LivingEntity` 1.16.5 lines 425–432; 1.17.1 lines 458–465).
- **Slow Falling and Levitation:** inherited `LivingEntity#travel(Vec3)` retains the same Slow Falling predicate and gravity override (1.16.5 lines 1914–1921; 1.17.1 lines 2003–2010), and the same Levitation vertical correction `(0.05 * (amplifier + 1) - vecY) * 0.2` plus fall-distance reset (1.16.5 lines 2028–2030; 1.17.1 lines 2117–2119). These are movement readers, and their checked equations/gates show no pair delta.
- **Elytra item/effect eligibility:** `Player#tryToStartFallFlying` retains the same `!onGround && !isFallFlying() && !isInWater() && !hasEffect(LEVITATION)` gate, CHEST slot read, enabled Elytra check, and start-flag write (1.16.5 lines 1520–1534; 1.17.1 lines 1542–1556). The only predicate spelling change is `itemStack.getItem() == Items.ELYTRA` to `itemStack.is(Items.ELYTRA)`; in 1.17.1 `ItemStack#is(Item)` returns `this.getItem() == item` (lines 192–194). LocalPlayer's jumping request path keeps the same jumping, ability-flight, passenger, climbable and enabled-Elytra gates (1.16.5 lines 725–730; 1.17.1 lines 747–751). Thus no substantive gate change was found.
- **Player movement-speed attribute consumer:** `Player#getSpeed()` remains `(float)this.getAttributeValue(Attributes.MOVEMENT_SPEED)` (1.16.5 lines 1433–1434; 1.17.1 lines 1455–1456); `Player#aiStep` refreshes its cached speed from the same attribute (1.16.5 line 506; 1.17.1 lines 512–513). This establishes the consumer relevant to the new 1.17 modifier below.

The previously assigned Jump Boost and fluid/sprint-related operations are not reassessed here; see coordinator's bounded prior-closure inventory. This census does not claim full-pair movement coverage.

## Newly added 1.17.1 powder-snow paths and scope disposition

1.17.1 adds two directly movement-affecting powder-snow paths that read equipment/frozen state. They have no 1.16.5 counterpart because `net/minecraft/world/level/block/PowderSnowBlock.java` is absent and 1.16.5 `LivingEntity.java` has no frost attribute modifier methods or powder-snow movement predicate.

1. **Leather-boots walkability and upward response.** In `net/minecraft/world/level/block/PowderSnowBlock.java`, `getCollisionShape` returns a full collision shape only for a falling block or when `canEntityWalkOnPowderSnow(entity)` is true and the collision context is above the block and not descending (1.17.1 lines 87–103). `canEntityWalkOnPowderSnow` accepts the entity tag path, otherwise a `LivingEntity` whose FEET stack is leather boots (lines 111–117). In `net/minecraft/world/entity/LivingEntity.java#handleRelativeFrictionAndCalculateMovement(Vec3,float)` the regular land movement routine writes `Y=0.2` when `(horizontalCollision || jumping)` and either climbable or the feet state is powder snow and the same walkability helper accepts the entity (lines 2155–2165). `LivingEntity#aiStep` reaches `travel` at lines 2541–2547; the ordinary travel branch calls the helper at lines 2111–2116. This is a concrete gear-gated movement response, but powder snow is a 1.17-only block/feature, so it is outside old-profile historical-map emulation scope; no compatibility finding is proposed.
2. **Freeze-progress movement-speed modifier.** `LivingEntity#aiStep` updates frozen ticks server-side when in powder snow and `canFreeze`, then calls `removeFrost()` and `tryAddFrost()` after travel on both logical sides (1.17.1 lines 2548–2563; client movement travel precedes the modifier recomputation). `tryAddFrost` requires a non-air `getBlockStateOn()` and `getTicksFrozen()>0`; it adds the transient additive movement-speed modifier `-0.05F * getPercentFrozen()` (lines 500–524). `getPercentFrozen()` is `(float)min(ticksFrozen, ticksRequiredToFreeze)/ticksRequiredToFreeze` and the default required count is 140 (`Entity.java` lines 2061–2079). For players, `Player#aiStep` calls `super.aiStep()` and refreshes speed afterward (lines 504–513); `Player#getSpeed()` consumes the attribute directly (lines 1454–1457). Player freeze eligibility also checks armor slots against the `FREEZE_IMMUNE_WEARABLES` tag (`LivingEntity.java` lines 3319–3329). These operations affect later player movement through the movement-speed attribute, but their producer is the new powder-snow/freezing feature. They are outside historical old-profile scope; no compatibility finding is proposed. Freeze damage at `LivingEntity.java` lines 2561–2564 is excluded as damage calculation.

## Explicit boundaries

- This is a bounded modifier/effect/equipment census, not a complete tick, collision, callback, resource/tag, or neighboring-block inventory; it does not close the full 1.16.5→1.17.1 pair.
- Jump Boost, sprint/jump, and fluid pair claims are deferred to the coordinator's existing audits rather than duplicated.
- Powder snow and freeze progression/modifiers are identified for disposition but remain modern-only features for this pair's historical-map compatibility policy.
- Rendering-only effect readers, movement animation/bobbing, AI movement of non-player mobs, equipment damage, and damage/health/food effects are excluded.
- No runtime trajectory claim or exact intermediate patch boundary is made.

## Contamination note

The malformed search exposure is disclosed above for immutable evidence provenance. No implementation content was intentionally examined, and the reported claims were established from the designated canonical decompiled source roots alone.
