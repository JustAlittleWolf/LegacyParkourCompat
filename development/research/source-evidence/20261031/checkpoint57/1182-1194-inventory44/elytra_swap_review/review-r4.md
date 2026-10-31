# Independent review r4: combined Equipable movement finding

**Verdict: ACCEPT** for final draft SHA-256 `D45BECAA496D1A854DEE0C647053DE51BFD60A8395CE62EEAE7CF505702C0BA3` and source-ledger SHA-256 `2E7109F0F4B58C3D919A48507C3DAA8DAE7840F1FE1FB8B1432BB92AE28E2931`.

All 23 source-ledger entries match the exact local Mojmap files. Both routes are supported by the cited source:

- Elytra use in 1.19.3 fails when the chest slot is occupied. In 1.19.4, `Equipable.swapWithEquipmentSlot` replaces a different, non-binding-cursed chest item with a copy of the held Elytra and returns the displaced item to the hand. The client `MultiPlayerGameMode.useItem` path invokes the item use outside spectator/cooldown cases. On a later eligible local airborne jump, the equipped enabled Elytra causes `Player.tryToStartFallFlying` to set shared flag 7 before LocalPlayer calls `super.aiStep`, so same-tick travel takes the fall-flying branch.
- ArmorItem use has the same empty-slot failure versus 1.19.4 shared swap behavior. With held boots enchanted with Soul Speed and prior boots without Soul Speed or Binding Curse, new client use equips the held boots while old client use fails. `LivingEntity.getBlockSpeedFactor` directly reads equipped Soul Speed and the support tag; vanilla providers put Soul Sand/Soul Soil in that tag on both versions. Without Soul Speed, the base factor on Soul Sand is `0.4F`; with Soul Speed it is `1.0F`. `Entity.move` applies that factor to stored horizontal delta movement after the current move, so the differing stored velocity changes subsequent horizontal movement.

The revised draft now states the needed prior-boot enchantment condition, cites the base fallback, and distinguishes post-move velocity scaling from same-call displacement. Its conditional caveat about synchronized equipment/flag overwrites and non-inference of server production is appropriate.

No build, tests, launches, or library execution were used.
