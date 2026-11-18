# Independent source audit: Llama passenger Y placement

TERMINAL: **COMPLETED**

## Verdict

**Endpoint claim accepted, bounded to 1.11 and 1.17.** Both exact sources place a direct player passenger at vehicle Y plus the Llama override `height * 0.67` plus the player riding offset `-0.35`. The Llama writer checks that the rider is a passenger before writing its position. A local client player is a Player subclass and can ride through the ordinary riding path; the writer itself does not distinguish local from remote players. Thus this confirms the direct local-player case without asserting a local-only placement guard.

Both Llama dimensions are `0.9F x 1.87F` at adult default size (1.11 constructor; 1.17 entity type registration). In both formulas the height value is float and `0.67` is a double literal, so Java promotes the float height to double for multiplication. The source order is vehicle Y + computed Llama offset + player offset; the product remains double and has no intervening cast or rounding to float. For adult default height `1.87F`, the rider Y is `vehicleY + ((double)1.87F * 0.67D) - 0.35D`. Relative to generic `0.75D`, the Llama term is lower by approximately `0.1496` blocks. This is source arithmetic, not a simulated trajectory.

## Exact evidence and provenance

- **1.11, Ornithe Feather:** `net/minecraft/entity/living/mob/passive/animal/LlamaEntity.java` constructor sets size `(0.9F, 1.87F)` (lines 60–63); `updateRiderPositon` guards with `hasPassenger(rider)` and writes `this.y + this.getMountHeight() + rider.getRideHeight()` (144–150); `getMountHeight` is `this.height * 0.67` (153–156). `net/minecraft/entity/living/player/PlayerEntity.java` returns `-0.35` from `getRideHeight` (948–950). The canonical source SHA-256 for LlamaEntity is `1577cf77a5c1d0e47dde0e13327dc333fcd65aa86c7e9073f49b2c678f0257b1`, matching the local manifest. `LocalClientPlayerEntity` extends `ClientPlayerEntity`, which extends `PlayerEntity`; `LocalClientPlayerEntity.startRiding` delegates to `super.startRiding`.
- **1.17, Mojmap:** `net/minecraft/world/entity/EntityType.java` registers LLAMA sized `(0.9F, 1.87F)` (299–301). `net/minecraft/world/entity/animal/horse/Llama.java` overrides `positionRider`, guards `hasPassenger(entity)`, and writes `getY() + getPassengersRidingOffset() + entity.getMyRidingOffset()` (154–160); the override returns `getBbHeight() * 0.67` (163–166). `net/minecraft/world/entity/player/Player.java` returns `-0.35` from `getMyRidingOffset` (1005–1007). Generic `Entity` placement computes this ordered sum before passing it to the move function (1699–1703). `LocalPlayer` extends `AbstractClientPlayer` and overrides `isLocalPlayer()` true; the Llama guard is passenger membership rather than this local marker.

The 1.17 tree's **nested hidden** `decompiled_minecraft/1.17/mojmap/.decompile-provenance.json` was verified, rather than relying on a root-level marker. Its manifest records the exact source hashes for Llama (`0b41ea3f…bfb04d72`), Entity (`81231ada…6914c8ef`), Player (`9ad4c439…617d90516`), EntityType (`acea872f…563e88b52`) and LocalPlayer (`08dbb861…21c5f4d4`); each matches the local file bytes. Provenance file SHA-256: `afc9dc30cd8f6406d8ea306e64cc0b3601cc8d7c7bcd314f737573d830a79357`. The 1.11 Llama source manifest hash likewise matches. These are canonical local decompiler outputs; no online mappings or source regeneration were used.

## Applicability and remaining boundaries

The sampled intermediate evidence named by the bound report (1.12.2, 1.13.2, 1.14.4, 1.15.2, and 1.16.5) supports these as **candidate early aliases** for the same formula, but this audit did not independently retrace those source files or their provenance. The supplied report itself flags 1.17 root provenance as absent; that gap is resolved by the verified nested hidden provenance file above. Do not interpret this two-endpoint audit or sampled candidates as proof of every patch release, a precise cutover, or full rider-height coverage. No source between 1.11 and 1.17 was exhaustively inventoried here. Animal physics and non-player rider behavior remain outside scope.

## Immutable input binding

Audited report: `c-queue-111-1171-llama-y-alias/report.md`, SHA-256 `86f875f1ffcf6679aaa4c7767cc08fdb7281ce151e82b263605543e06bf1cef2`.
This verdict is limited to the exact endpoints and source operations above; it does not revise the bound report or accept its unverified intermediate claims.