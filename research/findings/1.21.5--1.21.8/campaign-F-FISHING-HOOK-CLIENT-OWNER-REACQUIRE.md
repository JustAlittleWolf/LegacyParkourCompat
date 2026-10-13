# Candidate: client owner reacquisition can change a fishing-hook player pull

- **1.21.5**, mojmap: `Projectile.java`; lines 64-78.
- **1.21.8**, mojmap: `Projectile.java`; lines 62-68.

## Difference

1.21.8 can reacquire a client projectile owner by UUID, but vanilla respawn does not restore Player.fishing.

Rod input therefore cannot retrieve the old hook or produce event 31.

The proposed post-respawn player-pull difference is discarded.

## Movement meaning

Without the old Player.fishing reference, vanilla rod input cannot produce the old-hook retrieval event. The UUID owner lookup change therefore does not establish the proposed post-respawn player pull.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.21.5--1.21.8/findings/F-FISHING-HOOK-CLIENT-OWNER-REACQUIRE.md).
