# Independent exact-source review: edge backoff

## Verdict

**ACCEPT**, with citation and reachability details to add. The finding's substantive version-difference and conditional movement claim are supported by the exact decompiled sources. No semantic correction is needed. This is only an independent finding review; it makes no pair-freeze or coverage claim.

## Artifact identity

Reviewed `.native-source/20261030/1182-1194-inventory44/world_collision/edge-backoff-finding.md`; SHA-256 verified as `6bdb7db57a8646e8b7d4ab2735a33734d3b8fb98826147c41dd441f14776a151`.

Source roots and manifest identities supplied for this review: 1.18.2 `aea0cb9c6fc8f7a46f0eb82b0388ad58a4659f513be6c0a2be06c0df0c1eb07a`; 1.19 `78bf3f9d5b4b5437240dd0707fa7a9ff56e6d1e267bd8edc13adc8313b535e26`.

## Exact-source checks

SHA-256 hashes of exact source files checked:

| Version | File | SHA-256 |
|---|---|---|
| 1.18.2 | `net/minecraft/world/entity/player/Player.java` | `BF639C1962FF90D69E4569B2B18F6FCF57AC46EF80B19686F0FBC1687FCA744A` |
| 1.18.2 | `net/minecraft/world/entity/Entity.java` | `2228FDACA5793171CBD94038306D571A6ADA78CA96F5734EFB4CADA5B744C10A` |
| 1.18.2 | `net/minecraft/world/entity/LivingEntity.java` | `DB4168D531CAF18F22E3FEFD073365E776DA4075CE01452BB9F7671D9B458782` |
| 1.18.2 | `net/minecraft/client/player/LocalPlayer.java` | `99C2D18BCD23243AFB8F95C5BAFB21FB0BE7EA04AACBB14FCF7BE7CED2C9C095` |
| 1.19 | `net/minecraft/world/entity/player/Player.java` | `4B5D3D71AC1C4B64EFDEB1AD86A4B6F84AFFFE210E272850E620339430942DE1` |
| 1.19 | `net/minecraft/world/entity/Entity.java` | `3A8CE3090D29AAD1BBFECA31DE300D34B42A7E902F0E78B13EFD5653CD588766` |
| 1.19 | `net/minecraft/world/entity/LivingEntity.java` | `00C591DD2CAC49EE5CCAD8705F7DD879AD5D29F0360B98742166294989B3531C` |
| 1.19 | `net/minecraft/client/player/LocalPlayer.java` | `C5F346728F551D278E88B457E54E539885E0D8E549ED7394887D090F28224805` |

- **Gate and loops:** 1.18.2 `Player.java:1032–1079` gates on not flying, mover `SELF`/`PLAYER`, `isStayingOnGroundSurface()`, and `isAboveGround()`. Its X, Z, then diagonal loops at `1038–1074` use `level.noCollision` and adjust each component toward zero in `0.05` steps, before returning the adjusted vector. 1.19 `Player.java:1060–1112` has the same gates and loops, with the additional `$$0.y <= 0.0` gate at `1062`.
- **Remaining predicates:** Both versions define `isStayingOnGroundSurface()` as `isShiftKeyDown()` (1.18.2 `Player.java:303–305`; 1.19 `Player.java:311–313`). Both `isAboveGround()` bodies are textually equivalent: `onGround || fallDistance < maxUpStep && !noCollision(box moved by fallDistance - maxUpStep)` (1.18.2 `Player.java:1082–1085`; 1.19 `Player.java:1114–1117`). Thus the other edge-backoff gates and trim tests remain shared.
- **Call order:** `Entity.move` invokes `maybeBackOffFromEdge` before `collide` in both versions (1.18.2 `Entity.java:562–563`; 1.19 `Entity.java:566–567`).
- **Reachable local-player case:** Yes. `LocalPlayer.serverAiStep` copies input jump/forward/side impulses into `jumping`, `xxa`, and `zza` when controlled (1.18.2 `LocalPlayer.java:605–615`; 1.19 `LocalPlayer.java:688–698`). `LivingEntity` processes a jump while `onGround` and sets positive Y jump velocity (1.18.2 `LivingEntity.java:2519–2535`, `1981–1988`; 1.19 `LivingEntity.java:2551–2567`, `2014–2021`). The normal horizontal movement path calls `move(SELF, getDeltaMovement())` (1.18.2 `LivingEntity.java:2161–2164`; 1.19 `LivingEntity.java:2194–2197`). `Player.isStayingOnGroundSurface()` is true while shift is held, so a grounded sneaking jump with horizontal input can reach 1.18.2's gate with positive Y. Near an edge, the relevant downward-shifted candidate bounding box can have no collision; if the X/Z loop's `noCollision` predicate is true, it trims before `Entity.collide`. This establishes source-level reachability, not a tested trajectory or a claim that every such setup trims.

## Correction to the finding

Update the 1.19 method citation from `Player.java:1060–1098` to `Player.java:1060–1112` (or cite gate/loops `1060–1109` and return `1111–1112` separately); the current endpoint omits part of the diagonal loop and the return. Consider adding the reachable local-player precondition above to make the conditional movement consequence concrete. The claim should remain conditional: trimming occurs only if one or more `noCollision` loop predicates hold.
