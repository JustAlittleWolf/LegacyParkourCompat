# F09: 1.15.2 blocks Riptide use at one remaining durability

1.14.4, mojmap: `1.14.4/mojmap/net/minecraft/world/item/TridentItem.java`, `use`, lines 117–129.
1.15.2, mojmap: `1.15.2/mojmap/net/minecraft/world/item/TridentItem.java`, `use`, lines 111–122.

Difference: With exactly one durability remaining, 1.14.4 permits starting a charged Riptide use; releasing it follows the shared impulse path, even though the server-side durability cost can break the Trident. 1.15.2 rejects starting use at that durability value, so the player cannot receive this impulse through that use. This is a player movement difference caused by item-use availability.

Movement meaning: Player item use -> `TridentItem.use` durability and environment checks -> charged release -> `player.push` and grounded vertical move.


## Current evidence and remaining work

Source decisions, implementation coverage and runtime parity are separate. Source paths are relative to `decompiled_minecraft/`; immutable Git evidence can be read with `git show <revision>:<path>`.

```json
{
  "implementation": {
    "status": "open",
    "changes": [],
    "hooks": [],
    "limits": [
      "Current annotated Change/interface/catalog inventory contains no operation implementing: F09: 1.15.2 blocks Riptide use at one remaining durability. Existing unrelated later operations do not establish coverage."
    ]
  },
  "applicability": [
    "Endpoint interval (1.14.4, 1.15.2]; exact cutover remains the original evidence’s separate applicability gate."
  ],
  "sources": [
    {
      "version": "1.14.4",
      "mapping": "mojmap",
      "path": "1.14.4/mojmap/net/minecraft/world/item/TridentItem.java",
      "member": "use",
      "lines": [
        [
          117,
          129
        ]
      ],
      "status": "verified-content",
      "sha256": "c1d13346913255983356bf64a8b63502ea736d37f04055da18d266b1ba024476",
      "detail": "Canonical source read. Matches digest in archived original."
    },
    {
      "version": "1.15.2",
      "mapping": "mojmap",
      "path": "1.15.2/mojmap/net/minecraft/world/item/TridentItem.java",
      "member": "use",
      "lines": [
        [
          111,
          122
        ]
      ],
      "status": "verified-content",
      "sha256": "9dfe4ec0e5159359285f1b7c50379795fac78f0a404eced1b1ed561b321531fb",
      "detail": "Canonical source read. Matches digest in archived original."
    }
  ],
  "sourceReviews": [],
  "openIssues": [],
  "runtime": "unverified"
}
```
