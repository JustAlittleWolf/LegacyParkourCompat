# 1.16.3 → 1.16.4 residual changed-path classification

Date: 2026-10-29. Source lane: Mojmap. This is a source-only residual changed-path classification, not full-pair freeze or runtime parity.

## Exact release and source identity

Canonical read-only source roots:

- 1.16.3 release parent: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.16.3\`; its only child is `mojmap\`.
- 1.16.3 mapping root: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.16.3\mojmap\`; `.decompile-provenance.json` SHA-256 `513271804ead6757345d305717e7e0bfc1641434c35a3b254f287bd8fd281498`; 3,516 source entries.
- 1.16.4 release parent: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.16.4\`; its only child is `mojmap\`.
- 1.16.4 mapping root: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.16.4\mojmap\`; `.decompile-provenance.json` SHA-256 `1e04555c915527a398a2c7150679fb24d056f54bd680261e3c1dd55da821147c`; 3,523 source entries.

The 1.16.3 manifest names release metadata `.../6485dd131ef68c968041a9f6fd73094b027e42e1/1.16.3.json` (SHA-256 `193c5226095ce9e8a317f1e35ddbe4d976f176f6df7ac649ecfa669e17160ead`), client JAR hash `da85197babd989a7de318774cf5d97bcef95e89a70c9fa9a4c8180463e9589fa`, and official Mojang client mapping input hash `c41c58faaf3a3b651d8883b9b1df355cb6ad59c4d6296b2e1ffc3dfe2bf9be3e`. The 1.16.4 manifest names metadata `.../596ad61fda7612d9edf8881cf81869276bdb7f82/1.16.4.json` (SHA-256 `667fff4ca7fd0f56ab78b757224515e8e64375868e8d1ce6040091d7b63a0cb5`), client JAR hash `d6740464ee2bdf37d285c5d368d2e16497492427fb5783e2db3482bbf0079ed0`, and official Mojang client mapping input hash `93f188a0038fec8c2402d78e7acca983680978d6a9237c1fab80f22caddb00fe`. Both manifests identify Mojmap inputs and Vineflower 1.12.0. The release metadata/JAR/mapping hashes above are provenance input pins; this bounded task did not retrieve or rehash the upstream payloads.

I reconciled the existing changed-path ledger against both canonical manifest `sources` maps: 71 expected delta paths = 64 changed shared paths + 7 additions + 0 removals; ledger missing paths 0, extra paths 0, source-manifest tuple errors 0. I independently recomputed SHA-256 for each ledger-listed source path that exists on its side: 0 mismatches. The complete residual table with both source hashes and disposition is `residual-changed-paths.tsv`.

## Classification and movement-state trace

The prior pair partitions remain the evidence for ordinary local-player tick/options, direct client response, movement modifiers, Java movement tags/providers, and block callbacks. I did not reopen those analyses. Their changed paths are explicitly identified as previously closed in the table. The collision-shape aggregation lane remains separately owned.

The only changed Java path that writes a living entity's movement state is `world/entity/boss/enderdragon/EnderDragon.java`: `knockBack` changes the horizontal distance denominator from `f*f + g*g` to `Math.max(f*f + g*g, 0.1)` before `entity.push(...)`. The call site in `EnderDragon.java:287–295` is guarded by `!this.level.isClientSide` and `this.hurtTime == 0`; the changed `knockBack` method is at `:391–399`. Thus it is a server-only movement producer that can affect a player entity on the server; under the project boundary it is a causal server input note, not client historical movement. `Entity.push(double,double,double)` is unchanged. The per-path hashes are in the TSV.

`world/entity/projectile/FireworkRocketEntity.java` changes only the unattached rocket's own horizontal multiplier when it has horizontal collision. The separate attached-to-fall-flying-entity branch (`FireworkRocketEntity.java:101–129`) directly writes the attached living entity's velocity, but is unchanged; the elytra item route constructs the rocket attached to its player target. The changed branch is at `:130–139`. The changed branch is therefore non-player projectile self-motion, with no changed local-player movement writer.

`server/network/ServerGamePacketListenerImpl.java`, `server/level/ServerPlayer.java`, `server/MinecraftServer.java`, `server/dedicated/DedicatedServer.java`, `server/dedicated/DedicatedServerProperties.java`, and the added `server/network/TextFilter*.java` paths implement server text-filter setup and chat/book processing. The changed hunks in `ServerGamePacketListenerImpl` are constructor filter join, filtering helpers, and edit-book/chat processing; `handlePlayerInput` and `handleMovePlayer` have no changed hunks. `ServerPlayer` gains a text-filter field/getter and no movement write. These are server-side text/input services, not historical client movement deltas.

`server/level/ServerLevel.java` adds a watchdog statistics formatter that reads entity/player/block-entity counts; it does not mutate movement state. `ServerWatchdog.java` consumes diagnostics. `WorldGenRegion.java` changes structure-start lookup inside server world generation. Other server-side changes are authentication/keypair setup, server properties, or chat commands. Client login/encryption/connection changes prepare the same network login path; they do not modify the reviewed client movement-response methods or player-state writers.

Client-side residual changes outside previously closed paths are social UI/state, chat/name filtering, menu and Realms code, rendering/HUD presentation, release metadata, and resource-pack compatibility UI. In particular, `Gui.java`'s changed air-bar arithmetic only reads air supply for display. New social options/keys and tutorial toasts do not alter movement key defaults, auto-jump, LocalPlayer input, or tick/travel order. No residual client-local player movement writer/consumer difference was identified.

## Residual dependencies and limits

- Collision-shape aggregation/provider closure remains with its separately assigned lane.
- Effective built-in/data-pack/server-supplied tag membership is not in these Java-only decompilations; the Java tag/provider and client tag receiver code is unchanged, but concrete payload contents remain an external movement input.
- The pair-wide audit retains external callback and packet-producer gates outside the changed-path-only inventory. This residual classification does not claim every unchanged callback or every server-produced packet/state route is closed.
- No runtime parity or exact intermediate patch-introduction boundary is established by this endpoint comparison.

## Prior report identity correction

`1163-1164-block-callbacks/closure.md` contains a mistyped 1.16.4 provenance digest (`...c1dd99d0744d629f`). The canonical mapping-root manifest currently hashes to `1e04555c915527a398a2c7150679fb24d056f54bd680261e3c1dd55da821147c`, as independently stated by its audit and the pair identity ledger. The callback audit already flagged this transcription discrepancy. This residual report does not edit that prior report or re-open its callback finding.
