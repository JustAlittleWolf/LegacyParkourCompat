# Candidate: cross-dimension teleports move players spectating the entity

1.21.5, mojmap: `1.21.5/mojmap/net/minecraft/world/entity/Entity.java`, teleportCrossDimension 2681-2719.
1.21.8, mojmap: `1.21.8/mojmap/net/minecraft/world/entity/Entity.java`, teleportCrossDimension 2852-2885; teleportSpectators 2888-2894.
1.21.5, mojmap: `1.21.5/mojmap/net/minecraft/server/level/ServerPlayer.java`, teleport 976-1032.
1.21.8, mojmap: `1.21.8/mojmap/net/minecraft/server/level/ServerPlayer.java`, teleport 1007-1063; teleport 1007-1064.

Difference: - Exact precondition: a server-level entity successfully completes a cross-dimension `TeleportTransition` while one or more source-level `ServerPlayer` instances have `getCamera() ==` the entity being moved. In 1.21.8, those spectator players receive the same transition through `ServerPlayer#teleport`; 1.21.5 leaves them in their original dimension. ServerPlayer-initiated cross-dimension teleport has the corresponding direct helper call in 1.21.8. - Analysis: this is a direct player position/dimension writer triggered by a native dimension-transfer event. It does not change the target entity's ordinary movement or client correction formulas. The movement consequence is the newly added player relocation and camera reset for active spectators. The finding remains a candidate pending blind review and final audit scope disposition.

Movement meaning: - Analysis: this is a direct player position/dimension writer triggered by a native dimension-transfer event. It does not change the target entity's ordinary movement or client correction formulas. The movement consequence is the newly added player relocation and camera reset for active spectators. The finding remains a candidate pending blind review and final audit scope disposition.


## Current evidence and remaining work

Source decisions, implementation coverage and runtime parity are separate. Source paths are relative to `decompiled_minecraft/`; immutable Git evidence can be read with `git show <revision>:<path>`.

```json
{
  "implementation": {
    "status": "open",
    "changes": [],
    "hooks": [],
    "limits": [
      "No catalog Change implements the operation stated in this record. The current 104 annotated Changes/catalog were checked; unrelated hooks do not close this operation. Registration establishes static correspondence only; no runtime parity or renewed applicability acceptance."
    ]
  },
  "applicability": [],
  "sources": [
    {
      "version": "1.21.5",
      "mapping": "mojmap",
      "path": "1.21.5/mojmap/net/minecraft/world/entity/Entity.java",
      "member": "teleportCrossDimension",
      "lines": "2681-2719",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 fc177733e9cc4b2d5cf2562e5529d0f4e0b9c3690f081fbd58aa9119eb173dc4; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 3907657ade3e61bc8cffb4ca0a1bcba15f57986be4e613900947119364a206e5 *1.21.5/client_mappings.txt. Canonical source-manifest SHA-256 365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.5",
      "mapping": "mojmap",
      "path": "1.21.5/mojmap/net/minecraft/server/level/ServerPlayer.java",
      "member": "teleport",
      "lines": "976-1032",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 81ec90de6bf1521f45f1552c60af1ebb1a83fbcfa2eefb41b99e25b99d2f0d1b; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 3907657ade3e61bc8cffb4ca0a1bcba15f57986be4e613900947119364a206e5 *1.21.5/client_mappings.txt. Canonical source-manifest SHA-256 365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.8",
      "mapping": "mojmap",
      "path": "1.21.8/mojmap/net/minecraft/world/entity/Entity.java",
      "member": "teleportCrossDimension",
      "lines": "2852-2885",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 c403e6176d27b5bfd6aaa0dea3735ffa4fcf80dbae58766661dd84453b7e9704; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: dce035fedcc047ce0306c69892176b5eb9036bcc2845c87198163f12bb1d67ca *1.21.8/client_mappings.txt. Canonical source-manifest SHA-256 8ffb76cea647a2ba4fe58e000678f751bea2c40ea6358bae492e962ed1d9d008 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.8",
      "mapping": "mojmap",
      "path": "1.21.8/mojmap/net/minecraft/world/entity/Entity.java",
      "member": "teleportSpectators",
      "lines": "2888-2894",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 c403e6176d27b5bfd6aaa0dea3735ffa4fcf80dbae58766661dd84453b7e9704; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: dce035fedcc047ce0306c69892176b5eb9036bcc2845c87198163f12bb1d67ca *1.21.8/client_mappings.txt. Canonical source-manifest SHA-256 8ffb76cea647a2ba4fe58e000678f751bea2c40ea6358bae492e962ed1d9d008 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.8",
      "mapping": "mojmap",
      "path": "1.21.8/mojmap/net/minecraft/server/level/ServerPlayer.java",
      "member": "teleport",
      "lines": "1007-1063",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 1933118f0445fcb08dcdd0a5abd9e658d8669c210ea8ce9dfbf206d6b7193612; archived source-hash equivalence not established. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: dce035fedcc047ce0306c69892176b5eb9036bcc2845c87198163f12bb1d67ca *1.21.8/client_mappings.txt. Canonical source-manifest SHA-256 8ffb76cea647a2ba4fe58e000678f751bea2c40ea6358bae492e962ed1d9d008 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.8",
      "mapping": "mojmap",
      "path": "1.21.8/mojmap/net/minecraft/server/level/ServerPlayer.java",
      "member": "teleport",
      "lines": "1007-1064",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 1933118f0445fcb08dcdd0a5abd9e658d8669c210ea8ce9dfbf206d6b7193612; archived source-hash equivalence not established. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: dce035fedcc047ce0306c69892176b5eb9036bcc2845c87198163f12bb1d67ca *1.21.8/client_mappings.txt. Canonical source-manifest SHA-256 8ffb76cea647a2ba4fe58e000678f751bea2c40ea6358bae492e962ed1d9d008 matches archived publication; cited file hash is present in that manifest."
    }
  ],
  "sourceReviews": [],
  "openIssues": [],
  "runtime": "unverified"
}
```
