# Candidate: vehicle entity interpolation gate changes client player passenger positioning

1.21.5, mojmap: `1.21.5/mojmap/net/minecraft/world/entity/Entity.java`, positionRider 2019-2023.
1.21.8, mojmap: `1.21.8/mojmap/net/minecraft/world/entity/Entity.java`, positionRider 2187-2191.
1.21.5, mojmap: `1.21.5/mojmap/net/minecraft/client/multiplayer/ClientPacketListener.java`, handleEntityPositionSync 567-592.

Difference: - Exact precondition: the client receives an entity-position sync for a non-local-authoritative entity that has the local player as an indirect passenger. For an interpolating entity 1.21.5 applies the passenger-position update and 1.21.8 skips it; for a non-interpolating entity 1.21.8 applies it and 1.21.5 skips it. The report does not audit vehicle movement or physics; it records only the changed player-position call. Candidate remains pending blind review and scope disposition.

Movement meaning: - Analysis: this changes when client-side passenger positioning is applied for the local player. The report does not audit vehicle movement or physics; it records only the changed player-position call. Candidate remains pending blind review and scope disposition.


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
      "path": "1.21.5/mojmap/net/minecraft/client/multiplayer/ClientPacketListener.java",
      "member": "handleEntityPositionSync",
      "lines": "567-592",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 284f34159a1baf30b6b540751ad2fa62344c179d5ed6a7bbcb5c888665779617; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 3907657ade3e61bc8cffb4ca0a1bcba15f57986be4e613900947119364a206e5 *1.21.5/client_mappings.txt. Canonical source-manifest SHA-256 365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.5",
      "mapping": "mojmap",
      "path": "1.21.5/mojmap/net/minecraft/world/entity/Entity.java",
      "member": "positionRider",
      "lines": "2019-2023",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 fc177733e9cc4b2d5cf2562e5529d0f4e0b9c3690f081fbd58aa9119eb173dc4; archived source-hash equivalence not established. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 3907657ade3e61bc8cffb4ca0a1bcba15f57986be4e613900947119364a206e5 *1.21.5/client_mappings.txt. Canonical source-manifest SHA-256 365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.8",
      "mapping": "mojmap",
      "path": "1.21.8/mojmap/net/minecraft/world/entity/Entity.java",
      "member": "positionRider",
      "lines": "2187-2191",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 c403e6176d27b5bfd6aaa0dea3735ffa4fcf80dbae58766661dd84453b7e9704; archived source-hash equivalence not established. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: dce035fedcc047ce0306c69892176b5eb9036bcc2845c87198163f12bb1d67ca *1.21.8/client_mappings.txt. Canonical source-manifest SHA-256 8ffb76cea647a2ba4fe58e000678f751bea2c40ea6358bae492e962ed1d9d008 matches archived publication; cited file hash is present in that manifest."
    }
  ],
  "sourceReviews": [],
  "openIssues": [],
  "runtime": "unverified"
}
```
