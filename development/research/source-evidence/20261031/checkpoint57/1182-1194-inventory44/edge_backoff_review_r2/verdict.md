# Edge-backoff finding review r2

**Verdict: ACCEPT — the corrected source-level finding is supported by the exact 1.18.2 and 1.19 Mojmap sources.** Scope is this pair only; the intervening introduction boundary remains unknown.

The finding draft hash matches the assigned SHA-256. The full `Player.maybeBackOffFromEdge` methods match the cited ranges. In 1.18.2 the gate is, in order, non-flying, `SELF`/`PLAYER`, `isStayingOnGroundSurface()`, then `isAboveGround()`. Its X, Z, then diagonal probes use `level.noCollision` on the bounding box lowered by `maxUpStep`, trim in `0.05` increments, and preserve the Y component. In 1.19 the added `$$0.y <= 0.0` gate appears after the non-flying check and before the mover-type check; the remaining gate and trimming-loop order/math are unchanged. `Entity.move` invokes this hook before `collide` in both versions (1.18.2 line 562; 1.19 line 566).

A reachable local-player witness exists in both versions: `LocalPlayer.isEffectiveAi()` is true, its `serverAiStep` copies held jump input to `jumping`, and `isShiftKeyDown()` reads held shift input. `LivingEntity.aiStep` accepts a grounded jump without a shift-key exclusion, `jumpFromGround` sets positive Y movement without clearing the previously true `onGround`, and the ensuing travel path calls `move(MoverType.SELF, ...)`. With flight off and the player just supported at a full-block ledge, select a nonzero horizontal request toward the edge large enough that the lowered translated box misses the support. `CollisionGetter.noCollision` returns true when its block and entity collision checks find no obstruction (and no border intersection applies); the 1.18.2 loop then trims the horizontal request, while the 1.19 positive-Y gate skips backoff. This is a conditional source-level vector difference, not a runtime trajectory claim.

## Hashes

- Finding draft: `0B87ABCD5F4F9A0D3E8D5A2DCE8D4712AEE6FE127A3D1E9338111168E7845082` (matches).
- 1.18.2 `mojmap.sources.sha256`: `AEA0CB9C6FC8F7A46F0EB82B0388AD58A4659F513BE6C0A2BE06C0DF0C1EB07A` (matches supplied manifest hash).
- 1.19 `mojmap.sources.sha256`: `78BF3F9D5B4B5437240DD0707FA7A9FF56E6D1E267BD8EDC13ADC8313B535E26` (matches supplied manifest hash).
- 1.18.2 Mojmap `Player.java`: `BF639C1962FF90D69E4569B2B18F6FCF57AC46EF80B19686F0FBC1687FCA744A`.
- 1.18.2 Mojmap `Entity.java`: `2228FDACA5793171CBD94038306D571A6ADA78CA96F5734EFB4CADA5B744C10A`.
- 1.18.2 Mojmap `LivingEntity.java`: `DB4168D531CAF18F22E3FEFD073365E776DA4075CE01452BB9F7671D9B458782`.
- 1.18.2 Mojmap `LocalPlayer.java`: `99C2D18BCD23243AFB8F95C5BAFB21FB0BE7EA04AACBB14FCF7BE7CED2C9C095`.
- 1.18.2 Mojmap `CollisionGetter.java`: `ED62E6B800EC057C1269D3CC4E2FB7C0CC2037370223FAC99F41371E000C6B5C`.
- 1.19 Mojmap `Player.java`: `4B5D3D71AC1C4B64EFDEB1AD86A4B6F84AFFFE210E272850E620339430942DE1`.
- 1.19 Mojmap `Entity.java`: `3A8CE3090D29AAD1BBFECA31DE300D34B42A7E902F0E78B13EFD5653CD588766`.
- 1.19 Mojmap `LivingEntity.java`: `00C591DD2CAC49EE5CCAD8705F7DD879AD5D29F0360B98742166294989B3531C`.
- 1.19 Mojmap `LocalPlayer.java`: `C5F346728F551D278E88B457E54E539885E0D8E549ED7394887D090F28224805`.
- 1.19 Mojmap `CollisionGetter.java`: `ED62E6B800EC057C1269D3CC4E2FB7C0CC2037370223FAC99F41371E000C6B5C`.
