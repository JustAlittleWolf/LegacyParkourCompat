# Independent audit — 1.16 → 1.16.1 position-correction producer r2

## Verdict

**ACCEPT** the narrow immutable correction in `server-position-correction-producers-r2.md`, SHA-256 `3BD3D02104458118E2B0701BE34C0EAA7B4ACBB038F4C42769282B658BD45E72`. This verdict addresses the REQUEST CHANGES findings in `audit/verdict.md`, SHA-256 `999E413D02B1DF45720D3FBC8172F4909F21ADA2A32CD2B3E834F13195E9F6D5`, and reuses its unchanged source-route checks.

## Changed claim checks

The revised `ServerGamePacketListenerImpl.handleMovePlayer` summary includes the outer movement-speed-check gate: `!player.isChangingDimension()` and `(!RULE_DISABLE_ELYTRA_MOVEMENT_CHECK || !player.isFallFlying())`. The checked source at `ServerGamePacketListenerImpl.java:823-830` then sets `s = isFallFlying ? 300.0F : 100.0F` and applies the correction condition `q - p > s * r && !isSingleplayerOwner()`. This accurately places the threshold behind both outer conditions and preserves the vanilla server-validation boundary.

The revised `ServerPlayer.teleportTo(ServerLevel, ...)` summary also distinguishes its routes: when the destination is the current level, it directly invokes `connection.teleport`; when crossing levels, the player is removed/transferred/moved and assigned the new level before the same packet writer is invoked. This matches the paired source at `ServerPlayer.java:1369-1402`.

The endpoint identities and source-hash bindings are unchanged from r1 and were verified in the prior audit. Both correction-producing classes remain byte-identical at the two endpoints. No additional source comparison or full-tree rehash was performed.

## Scope

Acceptance is limited to the corrected outer movement-speed gates, threshold formula, and same-level/cross-level route distinction. It preserves the prior bounded producer-route review. It does not claim historical server movement, pair-wide source freeze, product applicability, or runtime parity.
