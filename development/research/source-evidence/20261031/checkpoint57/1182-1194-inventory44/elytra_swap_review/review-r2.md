# Independent review r2: Elytra chest equipment swap movement route

**Verdict: ACCEPT** for the revised draft SHA-256 `E80B6BF73B5EBB74043C60F4C3ABCD2612B904B057D66752BC4B8F6649BF8366` and unchanged source ledger SHA-256 `4421951A0BBB3021B6A8EDD1A94ABD64078EBAB482B3E1B8EE59CDD79BC34630`.

All ten source hashes still match the exact local Mojmap files. The revised draft corrected the 1.19.4 `tryToStartFallFlying` range to 1570–1580, cites the separate `startFallFlying` setter at 1582–1584, includes spectator/cooldown gates from `MultiPlayerGameMode.useItem`, and binds `LocalPlayer.aiStep` to the post-check `super.aiStep` calls at 1.19.3:814 and 1.19.4:795.

The source route is supported: 1.19.3 Elytra use fails on an occupied chest slot; 1.19.4 `Equipable.swapWithEquipmentSlot` replaces a differing, non-binding-cursed equipped stack with a copy of the held Elytra and returns the prior chest item to the hand. The helper performs equipment/hand mutations on the client path before the server-only statistic gate. On a later eligible local jump tick, `Player.tryToStartFallFlying` sees the newly equipped enabled Elytra, sets shared flag 7 locally, and `super.aiStep` proceeds into travel in the same tick. The conditional no-overwrite and no-server-production caveat appropriately bounds the claim.

No builds, tests, launches, or library execution were used.
