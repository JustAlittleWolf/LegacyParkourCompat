# Independent item-use-to-movement source audit: 1.8 → 1.8.1

## Verdict

Confirmed, source-only, bounded movement difference. The 1.8 client can begin local item use while ordinary survival mining is active. The 1.8.1 `Minecraft.doUse()` guard suppresses the whole use operation while the interaction manager reports active mining. For an eligible bow use, the 1.8 local state transition reaches unchanged local-player movement consumers: both movement inputs are multiplied by `0.2F`, double-tap sprint timing is reset, and both sprint-start paths require `!hasItemInUse()`. The doUse guard remains in 1.8.2, so this boundary is present in 1.8 and absent by 1.8.1, with no reversion at 1.8.2.

## Evidence identity

Read-only exact source roots: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.8\ornithe-feather`, `...\1.8.1\ornithe-feather`, and `...\1.8.2\ornithe-feather`. The supplied core report SHA-256 was `fcb0360dc6ea6d14e28a8777008f81ae7ee8df21b225370c5af1387ec1a43824`; supplied collision report SHA-256 was `8d79cfe4cbfe9a5c514f61a4d821a744f0e6d8b4ee2505451cdc90150b1eef00`; supplied pair audit SHA-256 was `888fa72731726657c9f52eb5792f07183e52468c591cd6ca520b7b9e0e9bfbe1`. All three matched the assigned values.

Independently recomputed SHA-256 values for relied-on source files:

| File | 1.8 | 1.8.1 | 1.8.2 |
| --- | --- | --- | --- |
| `net/minecraft/client/Minecraft.java` | `3073fca8df5f2c4ef782e521fdf0fc8ed23a74a31d8e589230a6ecc08e9b3ba2` | `95433e8760da950b233ffdfb1ede1ebd277d3f13879346819a270a64b6dcec4b` | `d8cc6ab491ea69c91419a42bb2ed6433456d6d8e041a2ff8e4acc0ffe4c6f15f` |
| `net/minecraft/client/ClientPlayerInteractionManager.java` | `0c50b09174fdf1d7b55d13ffef98100e87473a355c469160933e60635ef767d9` | `46ce41e7f5037b83d0e6081de78c24658902c3dac1c6e40e189f3f22d7be1bfa` | `a0975c998aab7f28118ad28094d7f52aa234f24ef042b341128f5892ac1d8461` |
| `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `42a92a74e5c64992572760196a2052273d96bc061cd9d1120045ba03532f1e2c` | same | `1762b116e6b06d682b7daaa0fc8cce39b0ff455b3db8cf79dab03ac74f6c4053` |
| `net/minecraft/item/BowItem.java` | `5acdd8b8e1895ea2d44f45c9cbee916a65d19d50c7fe9871f3531730add02fad` | same | `606cbecf543557f36b36c53ea876da29c7f451bbfd97d8f388abd298bd3f836` |



## Trace and order

- **Input event order:** In 1.8 `Minecraft.tick()`, the unfocused-screen-free branch drains `attackKey.consumeClick()` and calls `doAttack()` before draining `useKey.consumeClick()` and calling `doUse()` (`Minecraft.java:1549–1555`). The corresponding order remains in 1.8.1 (`:1554–1560`) and 1.8.2 (`:1589–1595`).
- **Ordinary mining starts:** `doAttack()` handles a non-air block target by calling `ClientPlayerInteractionManager.startMiningBlock` (`1.8 Minecraft.java:1151–1154`). For a non-creative survival player with stone targeted, ordinary non-instant mining takes the `getMiningSpeed(...) < 1.0F` branch and sets `isMiningBlock = true` (`ClientPlayerInteractionManager.java:153–178`). Stone inherits `Block.use`, whose base implementation returns `false` (`Block.java:531–532`).
- **Eligible item use in 1.8:** The following use click invokes `doUse()`. With stone still targeted and a bow selected, `useBlock()` returns false: stone's block-use hook is false, the bow is not a `BlockItem`, and `BowItem` inherits `Item.useOn`, which returns false (`ClientPlayerInteractionManager.java:280–318`; `Item.java:78–80`). Thus `doUse` retains its `var1 == true` fallthrough and calls `interactionManager.useItem` (`Minecraft.java:1203–1207`). `useItem` invokes `itemInHand.startUsing(world, player)` regardless of whether the stack later changes (`ClientPlayerInteractionManager.java:321–339`). With an arrow available (or in creative), `BowItem.startUsing` calls `player.setItemInUse(...)` (`BowItem.java:85–89`). This setter stores `itemInUse` on the client; only the additional status-flag sync is server-only (`PlayerEntity.java:1485–1493`). `hasItemInUse()` is the direct `itemInUse != null` predicate (`PlayerEntity.java:156–158`).
- **Gate introduced in 1.8.1:** 1.8's entire `doUse()` body begins immediately with `useKeyCooldown = 4` (`Minecraft.java:1166–1209`). In 1.8.1, the entire body, including cooldown and item-use path, is inside `if (!interactionManager.isMiningBlock())` (`Minecraft.java:1169–1214`). Since the preceding attack click already set that flag in the witness, the use click has no local bow-state transition. The 1.8.2 method keeps the same enclosing `if (!this.interactionManager.isMiningBlock())` guard (`Minecraft.java:1189–1235`).
- **Movement consumer:** The 1.8 and 1.8.1 `LocalClientPlayerEntity.java` files are byte-identical (same SHA-256 above). In `mobTick`, `input.tick()` is followed by multiplication of `movementSideways` and `movementForward` by `0.2F` and clearing `doubleTapSprintTime` when `hasItemInUse() && !isRiding()` (`LocalClientPlayerEntity.java:543–552`). Both double-tap sprint initiation and held-sprint initiation separately require `!hasItemInUse()` (`:559–580`). Therefore the input scaling applies for a non-riding player; sprint starts are blocked while item-use state is active. The item-use gate changes local movement inputs/state only for 1.8 in this sequence. In 1.8.1, the unchanged consumer sees no state started by that blocked use action.

## Bounded reachable witness

A non-creative survival player is focused in-world, targets an ordinary stone block, has the attack and use click events queued for one `Minecraft.tick()`, and has a bow plus at least one arrow selected. The attack click is handled first and starts non-instant mining (`isMiningBlock = true`). The subsequent use click reaches the same block target. In 1.8, stone does not consume block use and the bow's `useOn` returns false, so `doUse()` calls `useItem`; bow `startUsing` locally sets item-in-use state. In 1.8.1, the mining guard skips `doUse()` at its entry, so that transition does not occur. On the local movement tick that consumes this state, 1.8 scales both input axes and blocks sprint starts under the source gates; 1.8.1 does not get those effects from this attempted use.

This is a source-reachability argument, not a runtime recording or trajectory measurement. It establishes the item-use-to-input/sprint state difference without relying on the separate step-descent candidate or collision-provider applicability gate.

## Scope

Only the specified source roots, three named reports, and the source classes above were used. No implementation, product/history/wiki/backlog review, test, build, gameplay launch, or source regeneration was performed. Output is this audit only.
