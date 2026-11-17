# 1.20.4–1.20.6 item-use movement source comparison

## Result

**Bounded no-difference** for active item-use movement slowdown and local sprint-start eligibility across Mojmap 1.20.4, 1.20.5, and 1.20.6. No new independently resolved player-movement operation is established for this slice. This closes only the checked item-use paths and vanilla shield/bow examples; it is not a full-pair movement inventory.

## Exact source evidence

For each version, LocalPlayer.tick handles active-use slowdown after input.tick and tutorial input: when isUsingItem() && !isPassenger(), it multiplies leftImpulse and forwardImpulse by 0.2F and clears sprintTriggerTime. The exact block is at 1.20.4 LocalPlayer.java:666–670, 1.20.5 :669–673, and 1.20.6 :669–673.

The client canStartSprinting predicate has the same gate order in all three versions: not already sprinting; sufficient impulse; sufficient food; !isUsingItem(); no blindness; vehicle allowance; not fall-flying. See 1.20.4 LocalPlayer.java:1013–1020, and 1.20.5/1.20.6 :1020–1027. Its call occurs after the active-use slowdown block in the input tick path. Player.canSprint() returns true in each version and is the vehicle predicate, not the local player's item-use eligibility check.

The active-use state has a stack gate before either movement check can observe it. Client LocalPlayer.startUsingItem(hand) first reads that hand's stack, then requires !stack.isEmpty() && !isUsingItem() before calling the superclass and setting startedUsingItem = true and usingItemHand. LocalPlayer.isUsingItem() returns startedUsingItem. Server/base LivingEntity.startUsingItem uses the same nonempty-stack and not-already-using gates before storing the stack and its duration; it is not a movement slowdown implementation.

For default shield/bow use, the item class overrides are unchanged across the compared versions: ShieldItem.getUseAnimation(stack) returns UseAnim.BLOCK and BowItem.getUseAnimation(stack) returns UseAnim.BOW; each returns duration 72000. Items registers the bow with durability only; the 1.20.5 shield adds the banner-pattern component, not a use-animation component. The local movement slowdown and sprint-start predicate do not read UseAnim, stack item identity, or use duration, so an ordinary actively used shield and bow both take the same 0.2F input slowdown and fail the !isUsingItem() sprint-start gate. A passenger is explicitly exempted from input scaling by !isPassenger(), while the sprint-start predicate applies its vehicle allowance separately.

The relevant animation/stack gate in LivingEntity.isBlocking is also unchanged: first require isUsingItem() && !useItem.isEmpty(), then read useItem.getItem(), then require its getUseAnimation(useItem) == UseAnim.BLOCK, then require getUseDuration(useItem) - useItemRemaining >= 5. This identifies shield blocking; the method is damage/blocking state, not the movement slowdown or sprint-start gate. The 1.20.5 base Item edible fallback changes from legacy edible/fast-food checks to DataComponents.FOOD and eatDurationTicks(), but shield and bow override those methods, and that base fallback does not enter the two checked movement predicates.

## Source provenance

Canonical local sources only: decompiled_minecraft/<version>/mojmap/; each mojmap.ready.json reports status: ready, exact versionId, mapping mojmap, and a source-manifest SHA-256 matching the corresponding local mojmap.sources.sha256 file. The source files below are covered by those manifests. Mapping family is Mojang Mojmap. No wiki, product implementation, product Git history, generated candidate, gameplay, test, or remote mapping input was used. No separate JSON resource was needed; the default shield/bow registrations were checked in vanilla Items.java.

| Version | client SHA-256 in ready marker | mojmap.sources.sha256 SHA-256 |
| --- | --- | --- |
| 1.20.4 | 9221ab461a491bf9661cd8e773a5e662aaa43d600fa7970b8c12bbfb0431b838 | fd3c8668483e5ff208c847474f6cbd3952a909c4aa602ed98b24e3d11a9602a1 |
| 1.20.5 | 49d5949adbb1021a7eb00c1b949f9e39934b10249cbf86d25004774d1b3ff510 | 162dac6c4f1d539a1c2cea0255b7938a5281c207ab460bdc265358016d732484 |
| 1.20.6 | 02dfd345ac1ad55692d5dbc8486ac7e4fea72cd54ac494a79cd48963048e56b2 | 56aae10684471d7abb1c366bd5dd431ab976112a87e6cc5c687a68f1eff06311 |

The 1.20.5 ready marker also declares local provenance SHA-256 1d9b247fb839ad0e234054cba1668073245e60d86783eca40d44c2b7b23c6242; its manifest matches. The 1.20.4 and 1.20.6 provenance files are the local legacy provenance format and do not themselves carry source-manifest hashes; their ready markers provide the exact source-manifest identities above. Source-manifest file hashes and checked source hashes were re-read from the canonical tree for this comparison.

## Bounded coverage

Compared the three-version LocalPlayer active-use/start-sprint paths; base LivingEntity active-use start/state and isBlocking; Player.canSprint; ItemStack use-duration/animation forwarding; base Item use defaults; and default ShieldItem, BowItem, and Items definitions. The pairwise movement gates are semantically unchanged. The component-related change in base edible item defaults does not establish a shield/bow or generic active-use movement delta. No 1.21.11 configurable USE_EFFECTS, armor, damage calculation, effect/food production, or broader equipment path was examined.

## Terminal

**COMPLETED** — exact three-version bounded no-difference report. No code, tests, or launches.
