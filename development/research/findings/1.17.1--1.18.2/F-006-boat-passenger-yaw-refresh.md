# F-006: Boat passenger-list refresh preserves a player's existing yaw in 1.18.2

1.17.1, mojmap: `1.17.1/mojmap/net/minecraft/client/player/LocalPlayer.java`, `heal`, lines 154–169.
1.18.2, mojmap: `1.18.2/mojmap/net/minecraft/client/player/LocalPlayer.java`, `startRiding`, lines 153–163.

Difference: 1.17.1 LocalPlayer#startRiding, lines 154-169, writes yRotO, yRot, and yHeadRot to the boat yaw after super.startRiding succeeds for a Boat. LocalPlayer.java 1.17.1 ClientPacketListener#handleSetEntityPassengersPacket, lines 825-843, records prior passenger state, ejects current passengers, then reconstructs the listed passengers by calling startRiding. ClientPacketListener.java 1.18.2 LocalPlayer#startRiding, lines 153-163, has no yaw writes. LocalPlayer.java 1.18.2 ClientPacketListener#handleSetEntityPassengersPacket, lines 826-850, performs the boat yaw writes only when the local player is listed and was not already an indirect passenger before the handler ejects/rebuilds the passenger list. ClientPacketListener.java The paired Entity#hasIndirectPassenger / getIndirectPassengersStream methods establish that the prior-passenger test includes direct passengers. Entity#ejectPassengers runs before the handler calls startRiding, so a listed local player can successfully remount the same boat during a refresh. Entity.java hashes are 1.17.1  and 1.18.2 . Entity#moveRelative uses getYRot() to compute the later movement input vector in both versions (1.17.1 lines 1176-1191; 1.18.2 lines 1151-1166), connecting the player yaw transition to movement direction.

Movement meaning: For an inbound passenger-list packet naming a Boat that already contains the local player in its passenger tree and whose replacement passenger list still includes that player, 1.17.1 records the prior state, ejects the passenger list, and successfully remounts the local player. 1.17.1's successful LocalPlayer#startRiding resets all three yaw fields to the boat yaw. 1.18.2 records the same prior state and rebuilds the list, but its prior-passenger guard skips the yaw reset.


## Current evidence and remaining work

Source decisions, implementation coverage and runtime parity are separate. Source paths are relative to `decompiled_minecraft/`; immutable Git evidence can be read with `git show <revision>:<path>`.

```json
{
  "implementation": {
    "status": "partial",
    "changes": [
      "src/main/java/me/wolfii/legacyparkourcompat/change/v1_17_1/BoatPassengerYawRefresh.java"
    ],
    "hooks": [
      "BoatPassengerYawRefreshHook"
    ],
    "limits": [
      "BoatPassengerYawRefreshHook sets player yRot=boat yRot only for exact V1_17_1 with indirect passenger. Original yaw history/transition scope is broader than this write and exact-profile guard limits resolver coverage."
    ]
  },
  "applicability": [
    "Endpoint interval (1.17.1, 1.18.2]; exact cutover remains the original evidence’s separate applicability gate."
  ],
  "sources": [
    {
      "version": "1.17.1",
      "mapping": "mojmap",
      "path": "1.17.1/mojmap/net/minecraft/client/player/LocalPlayer.java",
      "member": "heal",
      "lines": [
        [
          154,
          169
        ]
      ],
      "status": "verified-content",
      "sha256": "c9a91cb6cb57806bc8d22e5bfe2d97daaf21d5d2a48f34a6e2c53164c61c5812",
      "detail": "Canonical source read. Matches digest in archived original."
    },
    {
      "version": "1.18.2",
      "mapping": "mojmap",
      "path": "1.18.2/mojmap/net/minecraft/client/player/LocalPlayer.java",
      "member": "startRiding",
      "lines": [
        [
          153,
          163
        ]
      ],
      "status": "verified-content",
      "sha256": "99c2d18bcd23243afb8f95c5bafb21fb0be7ea04aacbb14fcf7be7ced2c9c095",
      "detail": "Canonical source read. Matches digest in archived original."
    }
  ],
  "sourceReviews": [
    {
      "verdict": "accepted, bounded",
      "assertion": "Passenger-list refresh changes local-player yaw fields when already riding the listed Boat.",
      "assertion_preserved": true,
      "detail": "Historical exact snapshot decision preserved; player yaw only; no vehicle motion or final trajectory",
      "snapshot": "064fc24e5e3d8b4dab4f12c2000dcb13d37514ca:workflows/source-campaign-2026-10-07/1.17.1--1.18.2/findings/F-006-boat-passenger-yaw-refresh.md",
      "snapshot_sha256": "3994115e3789a8280aa61228b2b092a007e57985cde72492f19731a8f150db95",
      "identity": "4a5eae32b5819418dbd59eb4a763ee24ea1f2c52:development/research/reconciliation/review-decisions/90e97d96803bd8ab98f1d68181af01e1eaf00a48f2bb58ff13634c8757778c9d.md"
    },
    {
      "verdict": "ACCEPT, bounded",
      "assertion": " A's successful `LocalPlayer.startRiding` path refreshes yaw history from the boat. The client passenger packet handler reads the prior indirect-passenger relationship, ejects/rebuilds passenger state, and invokes riding; B's refresh is limited to a newly listed passenger (`!bl`). The resulting player yaw state is consumed by later relative movement. The server passenger packet is an explicit external state input; no boat or non-player physics is included. Acceptance is limited to this player yaw-state path.\n",
      "assertion_preserved": true,
      "detail": "Exact original snapshot verdict; current compact wording does not renew acceptance. Later identity-only revision, where present, remains separate.",
      "snapshot": "e531086eecc778432b40b2b9ef39499b8a7f0dba:workflows/source-campaign-2026-10-07/1.17.1--1.18.2/findings/F-006-boat-passenger-yaw-refresh.md",
      "snapshot_sha256": "3994115e3789a8280aa61228b2b092a007e57985cde72492f19731a8f150db95",
      "identity": "4a5eae32b5819418dbd59eb4a763ee24ea1f2c52:development/research/archive/legacy-workflows/source-boundary-reviews/2026-10-08-mc1171-1182-snapshot-review.md"
    }
  ],
  "openIssues": [],
  "runtime": "unverified"
}
```
