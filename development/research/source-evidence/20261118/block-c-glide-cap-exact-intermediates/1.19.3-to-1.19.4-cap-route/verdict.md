# Independent source audit: active Elytra route, 1.19.3 → 1.19.4

## Verdict

**Accepted with one correction to the local travel-gate explanation.** The bounded active-glide, fall-distance cap, and climbable-feet conclusions are supported. There is no demonstrated local-player movement delta in this slice. Do not describe local-player eligibility as `Entity.isControlledByLocalInstance()` finding the local player as its controlling passenger: the local player has no controlling passenger here. The unchanged eligibility follows from the `LocalPlayer.isEffectiveAi()` override returning `true` in both releases.

## Immutable inputs

- Audited report: `c-glide-1193-1194-cap-route/report.md`
- Report SHA-256: `0D0C28041F1035C35E9CD8BAFD79EA6C3C9707EFBDAE82B6C6EF9424836FE830`
- Source pair: canonical `decompiled_minecraft/1.19.3/mojmap` and `decompiled_minecraft/1.19.4/mojmap`, read-only.
- Both provenance files identify exact version IDs, `mojmap`, and the same `1.19.2,1.19.3,1.19.4` exact run; both ready markers say `ready` and their source-manifest SHA-256 equals the actual manifest file hash.
- Manifest identities: 1.19.3 `6f1cc6d07ab3902a7ea25a3f8817504723be70d82e97f541cffda95ea2c1c843`; 1.19.4 `6286e325371e085dfee1ab7987e66d4b6d1a984a49b6ec8ea730045cd24cecb3`.
- Examined source hashes match those manifests: 1.19.3 `Entity.java` `8c35bdef3bf2de7b70ac76ad767ba2c194305e0dda2e03fdf6544983667adc32`, `LivingEntity.java` `2b8befdc406176e01672175451dd480b14da0b52bfe11a982d368909c05e73db`, `LocalPlayer.java` `64b670ee323d195b3928fb8ea629c26d2560e75a17379a12c376a7bc686d5479`; 1.19.4 respectively `3667fee610cbc5f58012e3a8fb8d6c4849f649fb7fe5300595158112d8b4b58b`, `c8d91af61f87aaa1666de79696d7cd9d4a8212d05f873bdaf28d7bb2926bf165`, `8e7da18f42d09fbb994f522c2b0e65fcb2bb83cabb21024360299d44d9674c58`.

## Bounded findings and correction

1. **Local-player travel gate.** In 1.19.3, `LivingEntity.travel` gates on `isEffectiveAi() || isControlledByLocalInstance()` (`LivingEntity.java:2052-2053`). `LocalPlayer.isEffectiveAi()` returns `true` (`LocalPlayer.java:493-495`), so the first term admits the local player. In 1.19.4, travel gates only on `isControlledByLocalInstance()` (`LivingEntity.java:1983-1984`); `Entity.isControlledByLocalInstance()` falls back to `this.isEffectiveAi()` when no controlling passenger is a `Player` (`Entity.java:2795-2800`). `LivingEntity.getControllingPassenger()` returns null (`Entity.java:2718-2721`), and `LocalPlayer.isEffectiveAi()` still returns true (`LocalPlayer.java:485-488`), so the new gate also admits the local player. The report's statement that the local player's controlling passenger is the player and is checked with `isLocalPlayer()` is incorrect. The gate expression changed, and may affect other actors, but the bounded local-player route remains admitted on both sides. This audit does not classify the broader actor behavior.

2. **Ordered active-glide route.** For the stated dry, non-fluid path, `travel` checks water then lava before `isFallFlying()` (`LivingEntity.java:2061-2117` in 1.19.3; `1992-2048` in 1.19.4). The glide branch invokes `checkSlowFallDistance()` first (`2117-2119`; `2048-2051`), calculates velocity, writes it with `setDeltaMovement` (`2142`; `2073`), then reaches its first `move(MoverType.SELF, ...)` (`2143`; `2074`). An already-active flag therefore selects glide before the ordinary friction branch; climbable handling (`handleOnClimbable`) appears only in that ordinary helper (`2201-2204`; `2152-2155`). Ladder/vine feet do not redirect the branch before that first move. This statement is about branch/order only; collision work inside `move` and subsequent tick effects are outside the bounded finding.

3. **Fall-distance operation.** `Entity.checkSlowFallDistance()` changes only the access expression between releases: 1.19.3 reads `this.deltaMovement.y()`; 1.19.4 reads `this.getDeltaMovement().y()` (`Entity.java:2147-2151`; `2167-2171`). Both use strict `> -0.5` and `fallDistance > 1.0F`, then assign exactly `1.0F`; equality at either threshold does not enter. The public method is inherited by `Player`/`LocalPlayer`; the inspected pair has no player override. The call remains before glide-velocity calculation and the first move. This is a mechanical field-to-accessor change for the inherited player implementation, with equivalent effects here; it is not evidence of a new cap rule. Later fall-distance accumulation is out of bounds.

4. **Fall-flight update preconditions.** `updateFallFlying()` bodies are text-identical in the examined sources (`LivingEntity.java:2637-2662` and `2588-2613`). They require the shared bit set, airborne, not passenger, no Levitation, and enabled chest Elytra to retain local `$$0=true`; otherwise the local boolean becomes false. The final flag write is server-only. `aiStep` calls this before travel on both sides (`1.19.3:2597-2599`; `1.19.4:2543-2550`, with ridden routing between update and travel). The report's ordinary unmounted local-player bounds correctly take the non-ridden route.

## Scope limits

This is an independent audit of the report's bounded source claim, not full-pair coverage or runtime parity. It does not settle start eligibility, final fall accumulation, fluid precedence beyond confirming it precedes glide, collision responses, ridden/player-as-passenger routes, other entities, or the 1.19.3→1.19.4 patch boundary's intermediate releases.

## Exact source references

All paths are relative to the repository root under `decompiled_minecraft/<version>/mojmap/`:

- `net/minecraft/world/entity/LivingEntity.java`: 1.19.3 lines 2052-2185, 2201-2208, 2597-2599, 2637-2662; 1.19.4 lines 1983-2116, 2152-2160, 2543-2551, 2588-2613.
- `net/minecraft/world/entity/Entity.java`: 1.19.3 lines 2147-2151, 2671-2673, 2742-2745; 1.19.4 lines 2167-2171, 2718-2721, 2795-2801.
- `net/minecraft/client/player/LocalPlayer.java`: 1.19.3 lines 493-495; 1.19.4 lines 485-488.