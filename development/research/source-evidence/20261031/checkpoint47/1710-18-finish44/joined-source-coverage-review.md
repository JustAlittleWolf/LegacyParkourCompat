# Joined 1.7.10–1.8 ordinary player movement source audit

## Source identity and comparison assumptions

Read-only exact source pair: 1.7.10 Ornithe Feather root `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.7.10\ornithe-feather`, manifest SHA-256 `11bada441ebe07661402d1c40e1c490b78b279ecaa084f9a4dc3ae2dd54caf6f`; 1.8 root `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.8\ornithe-feather`, manifest SHA-256 `52c8312c34a1a491d4a200c84e90f5d8d95c7e5caa9571f864d8e70f9737db05`. Use the actual embedded 1.8 Feather mergedv2 hash `3c020d1588b4aab403f5df077ca91b6e3776da2823d3b55202f4616f0d5ae5fe`; the earlier pool42 ordinary-tick report has a documented one-character transcription typo (see `errata.md`).

The comparison is over equal raw logical inputs and corresponding old-era vanilla block states. Input-device sampling differences and protocol translation/profile negotiation are not silently treated as equal inputs. No runtime execution is claimed.

## Joined ordinary tick and response path

The ordinary local-player path is traced through `Minecraft` → loaded-area `World.tickEntity` → `LocalClientPlayerEntity.tick` → `LivingEntity.tick/mobTick` → player pre-travel input/ability decisions → `LivingEntity` jump/travel → `PlayerEntity.moveRelative` → `Entity.move` → collision query and settled player position/flags. The paired class-role movement from old `InputClientPlayerEntity` into the 1.8 local-player class is accounted for. The ordinary tick and source identity inventory is in `.native-source/20261030/1710-18-pool42/ordinary_tick_reconciliation/ordinary-tick-and-core-role-inventory.md`; input and local-player state-reader coverage is in `.native-source/20261030/1710-18-pool42/input_localplayer/coverage.md`.

Movement-bearing `Entity.move` block collection dependencies close through the paired world scan and provider dispatch. The direct-provider inventory has 44 declaration classes per side, 43 common. An independent body/dependency comparison now covers all 43 common classes: `provider-formulas/a.md` covers the first 20 rows; `missing-lever-lilypad-liquid.md` closes the three omitted classes; `provider-formulas/b.md` covers 11 rows; `provider-formulas/c.md` covers the last nine. The membership/replacement closure records the sole old-only direct declaration (`CarpetBlock`) and sole 1.8-only declaration (`BannerBlock`), with Banner modern-only. The base query collector and full provider hashes are verified in the reports. Moving-block box identity is closed for vanilla providers in `moving-block-alias-closure.md`.

Direct player responses and readers are joined across local client packet application, sleep response, player correction, movement callbacks, contact/liquid effects, support/suffocation, and the local-player entity-list query. The corresponding records are `input-flight-independent-review.md`, `player-correction-review/review.md`, `entity-list-standing/entity-list-standing-audit.md`, and `block-support/block-support-audit.md`, together with the paired tick, callbacks/effects and response inventories in pool42. The independent player-correction review confirms common absolute writes after Y-coordinate normalization; relative correction flags have no old packet representation and remain protocol/profile applicability-open rather than a paired native movement delta.

Out-of-scope routes were explicitly closed at their consumer boundary: input-device plumbing is represented by equal logical input assumptions; rideable-mob physics and other non-player pushes are excluded; server piston and world/block event production is excluded; client rendering, health/food and damage are excluded. 1.8-only spectator, banner, barrier, slime and world-border branches are modern features without an old-state counterpart. Client-local direct player writes are kept in scope; server-only dismount placement was paired and found block-only/equivalent.

## Independently accepted movement findings

The final candidate set is in `canonical-findings/1.7.10--1.8/`; independent reviewer results and exact hashes are in the source-only reports. The accepted source differences are:

1. Sneak-held prior input suppresses the sprint double-tap window (`sneak-held-sprint-double-tap.md`).
2. Vertical flight input changes from fixed double `0.15` to float `flySpeed * 3F` (`flying-vertical-input-speed.md`).
3. Sprinting doubles airborne flight acceleration (`sprinting-flight-air-acceleration.md`).
4. Carpet's ordinary-player collision plane becomes a 1/16-height box; the concrete falling witness is in `carpet-collision-height.md`. This corrects the older pool42 blanket-rejection claim; immutable erratum retained.
5. Cake collision top rises by 1/16 (`cake-collision-height.md`).
6. Ordinary living-player step height increases from `0.5F` to `0.6F` (`living-player-step-height.md`).
7. The old airborne-sneak step-offset gate is removed (`airborne-sneak-step-eligibility.md`); its final reviewed witness holds step height at `0.5F` and uses feet Y=1.08 / requested dy=-0.10.
8. The 1.8 step solver adds a horizontal-sweep clearance candidate (`step-solver-swept-clearance-candidate.md`); the final witness specifies z center 0.5.
9. Sleep response places the old player's collision box minY about 0.05 blocks higher after normalizing source position conventions (`sleep-response-placement.md`).
10. The old signed-byte correction Y can wrap at high bed Y and select the low-height fallback (`sleep-response-high-bed-coordinate-wrap.md`).

Independent acceptance evidence: input findings review SHA-256 `1CD6B37D8EEA10B27C98DBFF84927FA650B2CCAD1C473772317E2AE28780EE23`; step review `F496083F76DF0B33B4821EA5C00101D690DBFA1597881B8DF3F1BD90DECF2E80`; Carpet/Cake reviews `9D00ADE8D5D806307480AC296C9FF4EF20FC1FA5BAFF8560B710AE25350B7769` / `743A74EE404CB316FFB9AFA721D3A443D07098577A2251F0BEB092B835D105A5`; sleep response review `08C6DF117AB3B98221F8873E6753719A06A013C3A62F57828DAFE8B3DD444B49`.

## Full-audit decision and boundary

Within the assigned ordinary logical-client player movement and direct player-response scope, there is no unpaired old-era provider class/member or unresolved block-only/provider branch. Every common direct provider class has an exact paired body/dependency pass. No accepted state-reader, callback, or entity-list audit establishes an additional movement candidate beyond the ten above. The old-only Carpet provider and the documented 1.8-only modern branches are accounted for.

The only remaining protocol applicability question is a newer relative correction packet reaching an old-profile route; source trees alone do not establish a supported translated connection/profile path. That is not a missing paired source body. This source audit establishes candidate acceptance and source coverage only. It does not establish implementation acceptance, deterministic runtime parity, or a game trajectory.
