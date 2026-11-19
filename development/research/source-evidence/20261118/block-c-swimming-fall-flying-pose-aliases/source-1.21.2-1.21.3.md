# 1.21.2 / 1.21.3 fall-flying and swimming overlap proof

Status: COMPLETED for this additive bounded source proof. No game/runtime, tests, build, implementation or primary research writes.

## Exact source authentication

1.21.2 Mojmap has no outer `mojmap.ready.json`, `.provenance.json` or `.sources.sha256`. The canonical source tree contains `decompiled_minecraft/1.21.2/mojmap/.decompile-provenance.json` (SHA-256 `00f399407dc447737ea83e3e46562418f8c712f66e3f51cb85d46afa2ecac575`). Its pinned inputs identify the exact 1.21.2 version metadata URL (`.../1.21.2.json`, content SHA-256 `793e3fd0451354a26aaa00253dcb6600add11f9d2a90f7bdef75ddf5b38c2b45`) and client object URL (`.../c7ac2d0d86f4ca416cab9064ff8a281852ad0c7b/client.jar`, content SHA-256 `613091da0f62426ae8dcf9afb30eb639485f493b81cda3a2b96b1fb8a6b9976c`). The nested provenance records per-source SHA-256 values.

1.21.3 has outer Mojmap ready/provenance/manifest sidecars. `mojmap.provenance.json` identifies `versionId: 1.21.3`, `namespace: mojmap`; `mojmap.ready.json` identifies the ready source manifest. Computed manifest SHA-256 `d673bb5464853e3a2a92ed9b1ffe1789d4e62c097bb884f67c336fcbb3b178ce` matches the ready marker. For each source below, I independently hashed both canonical files; each 1.21.2 hash matches its nested provenance entry, each 1.21.3 hash matches its source manifest entry, and the paired files are byte-identical:

| Source | SHA-256, both exact versions |
|---|---|
| `net/minecraft/world/entity/player/Player.java` | `a803203e92aa4729d5f5c9b16085b6a43ce51d9907d309eb96736e9c7c1340de` |
| `net/minecraft/world/entity/Entity.java` | `a93719c302a0381a972af75ea360465e2e3551708dd07c34d4d40b7e5173c2b9` |
| `net/minecraft/world/entity/LivingEntity.java` | `087390495f4fdfd14b9e12230e7aea4fdc50913bb4d1d91119e72892881cfc52` |
| `net/minecraft/client/player/LocalPlayer.java` | `fbd40f1f47adfa66dda9b15188e5dce82af3e8e8d7c3dd0543e602a354ad3fe0` |
| `net/minecraft/server/network/ServerGamePacketListenerImpl.java` | `e4ad1ac2fa3236f659220c44dd4d5f63d9ef2ab5791bdabbfa2d229b14452a38` |

This establishes digest-equivalent source aliases for the audited operation and relevant caller/writer gates in 1.21.2 and 1.21.3.

## State-to-selector path (same exact bodies in both releases)

- `Entity.baseTick` (1.21.2 lines 449–451) updates fluid state, eye-fluid state, then calls `updateSwimming`. `Entity.updateSwimming` (1285–1293) sets the shared swimming flag from sprint plus underwater water and non-passenger; if already swimming, it uses sprint plus in-water and non-passenger.
- `Player.updateSwimming` (1476–1482) suppresses swimming only for `abilities.flying`. `Player.isSwimming` (1815–1817) filters ability flight and spectator, but does not filter fall-flying. Thus dry-started fall-flying and later swimming can both be true for an eligible local player.
- `LivingEntity.updateFallFlying` (2814–2834) clears shared flag 7 only on the logical server and only when `canGlide()` fails. `canGlide` (2836–2848) requires airborne, non-passenger, no levitation and a usable glide item; it has no water exclusion. It therefore retains the server-authoritative flag through water entry while those existing eligibility conditions continue to hold.
- `Player.tryToStartFallFlying` (1525–1532) rejects a *new start* while in water. This is not a continuation gate: a glide started while dry is not cleared by this check. The local player sends the start command only when this method returns true (LocalPlayer lines 735–737).
- `Player.tick` calls `super.tick()` (280), then `updatePlayerPose()` (326); `LocalPlayer.tick` calls `super.tick()` before local input processing (189–192). Therefore base-tick water/swimming state is visible to the player pose selector in that tick.
- `Player.updatePlayerPose` (431–459) first requires fit for `Pose.SWIMMING`; it selects `FALL_FLYING` before sleeping and swimming; then it accepts the desired pose for a spectator/passenger or when that pose fits, otherwise tries crouching and then swimming. Both `FALL_FLYING` and `SWIMMING` are 0.6F × 0.6F and 0.4F eye height (dimensions table lines 139–144). Under the assigned non-sleeping, non-spectator, non-passenger, non-ability-flight case and admitted fit, the selector writes `FALL_FLYING` for the overlap.

## Disqualifying writer assessment

No water-triggered local-tick writer clearing fall-flying is present in the byte-identical `Entity`, `LivingEntity`, `Player` and `LocalPlayer` bodies for 1.21.2/.3. The server validator also has no water condition in `canGlide()`.

There is one conditional server packet writer to account for: `ServerGamePacketListenerImpl` handles `START_FALL_FLYING` by calling `tryToStartFallFlying()`, then `stopFallFlying()` if that start fails (1.21.3 lines 1445–1448; the file is byte-identical in 1.21.2). `tryToStartFallFlying` rejects while wet and also rejects if already fall-flying, so a failed or duplicate start command can clear the flag. The normal local path only sends this command when its local `tryToStartFallFlying()` returns true; the code contains no water-entry command and no ordinary water-state transition invokes this failed-start writer. This is a potential packet-path caveat, not a disqualifier for the stated local dry-start → water-entry state path. No runtime network timing was tested.

## Scope limits

The evidence proves exact 1.21.2/.3 source and selector admission, including the matching state updater, local tick caller and writers. It supports the same conditional overlap path as 1.21.4. Fit must pass in the relevant world; no specific collision-free route or runtime trajectory is claimed. The sources do not establish client/server packet timing. Sleep, spectator, passenger, ability flight, server movement parity and generalized glide equations are outside this proof.