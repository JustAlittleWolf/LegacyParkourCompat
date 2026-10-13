# F13: 1.15.2 End Gateways target a mounted player's root vehicle

1.14.4, mojmap: `1.14.4/mojmap/net/minecraft/world/level/block/entity/TheEndGatewayBlockEntity.java`, `tick`, lines 74–94.
1.15.2, mojmap: `1.15.2/mojmap/net/minecraft/world/level/block/entity/TheEndGatewayBlockEntity.java`, `tick`, lines 76–96.

Difference: When the queried first entity is a player riding another entity, 1.14.4 targets that player for the gateway teleport. 1.15.2 targets the player's root vehicle. 1.15.2's common entity teleport path then updates the root/passenger chunk and teleport state and repositions its direct passengers, so the mounted player is carried through the passenger chain as part of the root vehicle teleport. This is a changed player position route; it does not establish any difference in independent vehicle movement.

Movement meaning: End Gateway server tick -> first intersecting entity -> (1.15.2 only) root-vehicle selection -> ticketed teleport -> `Entity.teleportTo` / `ServerPlayer.teleportTo` -> passenger position propagation.


## Current evidence and remaining work

Source decisions, implementation coverage and runtime parity are separate. Source paths are relative to `decompiled_minecraft/`; immutable Git evidence can be read with `git show <revision>:<path>`.

```json
{
  "implementation": {
    "status": "open",
    "changes": [],
    "hooks": [],
    "limits": [
      "Current annotated Change/interface/catalog inventory contains no operation implementing: F13: 1.15.2 End Gateways target a mounted player's root vehicle. Existing unrelated later operations do not establish coverage."
    ]
  },
  "applicability": [
    "Endpoint interval (1.14.4, 1.15.2]; exact cutover remains the original evidence’s separate applicability gate."
  ],
  "sources": [
    {
      "version": "1.14.4",
      "mapping": "mojmap",
      "path": "1.14.4/mojmap/net/minecraft/world/level/block/entity/TheEndGatewayBlockEntity.java",
      "member": "tick",
      "lines": [
        [
          74,
          94
        ]
      ],
      "status": "verified-content",
      "sha256": "ec4279c2b216f60f36adf382f404689c23f836c6fb9ce1919a34a7d3b832a3af",
      "detail": "Canonical source read. Matches digest in archived original."
    },
    {
      "version": "1.15.2",
      "mapping": "mojmap",
      "path": "1.15.2/mojmap/net/minecraft/world/level/block/entity/TheEndGatewayBlockEntity.java",
      "member": "tick",
      "lines": [
        [
          76,
          96
        ]
      ],
      "status": "verified-content",
      "sha256": "4be661e53f4ee7e06c2754cef1d4f5837766f776b0ac51af4adb3ac855668d10",
      "detail": "Canonical source read. Matches digest in archived original."
    }
  ],
  "sourceReviews": [],
  "openIssues": [],
  "runtime": "unverified"
}
```
