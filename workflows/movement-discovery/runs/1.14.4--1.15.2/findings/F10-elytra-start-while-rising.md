# F10: 1.15.2 can start Elytra flight while the player is rising

- Older version A: 1.14.4
- Newer version B: 1.15.2
- Mechanic / coverage slice IDs: stages 1–3 and 7; local Elytra input and server acceptance
- Classification: changed flight-start gate
- Confidence: source-confirmed
- Applicability: jump-key rising edge while airborne, rising, not flying, not on a ladder or passenger, outside water, and wearing a usable Elytra
- First changed release: unknown within (1.14.4, 1.15.2]
- Runtime validation: not performed

## Paired evidence

- A `LocalPlayer.aiStep` sends `START_FALL_FLYING` only when the player is airborne and `getDeltaMovement().y < 0.0`, in addition to the jump-edge, not-already-flying, not-creative-flying and Elytra checks (line 707). A `LocalPlayer.java` SHA-256: `0795C1223198CE5ACF5D2ED9E5DB8435BBEC4CD52B96F96B1DDF2865BAAAE85F`.
- A `ServerGamePacketListenerImpl` independently accepts the command only when the player is airborne, `getDeltaMovement().y < 0.0`, not already fall-flying and not in water, with a usable Elytra equipped (lines 1122–1126). A `ServerGamePacketListenerImpl.java` SHA-256: `912F528C03DA34FA703646B6383C2B49AC3493EFD7BA78CB8FF27AA7F693B67D`.
- B `LocalPlayer.aiStep` keeps the jump-edge and usable-Elytra gates, plus blocks activation while the player is already flying, a passenger or on a ladder (lines 715–718). It then calls `Player.tryToStartFallFlying` locally before sending the command. B `LocalPlayer.java` SHA-256: `3A9019BD7B860E251C23FD8D0CD70B7F5B38566D34470C4E29B1014EF689CCBD`.
- B `Player.tryToStartFallFlying` checks airborne, not already fall-flying and not in water, then checks the chest Elytra and sets the shared fall-flying flag. It has no negative-Y-velocity condition (lines 1578–1584). B `Player.java` SHA-256: `1BA2724C22163862B8F7FDFDEA5A04A66E4DB26A119D7E5360BA024724A34793`. B `ServerGamePacketListenerImpl` accepts the command through this same helper (lines 1137–1139; SHA-256 `3D46E455509891A3E0EF50472610D565255F5991987E971BA00AF2140D25982C`).
- `ElytraItem.isFlyEnabled` uses the same `damageValue < maxDamage - 1` check in both versions. A/B file hashes are `BE4DD30957DF32B209EBA321DFE6F5F9E7B398B2FDD4E5BEFB9739770FDF7DEA` and `701E25F13E9BA8DB74ACA774F404A5281B2A58F4F7DC72E3AEABAC76BBF28B43`.

## Source-level difference

When all listed gates hold while vertical velocity is nonnegative, A neither sends the command locally nor accepts it on the server. B starts fall-flying locally and the server helper accepts it because neither checks vertical direction. The local player therefore enters the fall-flying travel branch earlier in the jump arc.

## Reachability and boundary

Jump-key rising edge -> local Elytra gates -> B's immediate shared-flag update and `START_FALL_FLYING` command -> server Elytra gates -> living fall-flying travel. The finding isolates removal of the negative-Y gate; it does not claim a change to the fall-flying travel formula itself.

## Consequence and uncertainty

The activation timing difference follows from both client and server source. The first release with this gate change is unknown within the endpoint interval. No gameplay test was performed.
