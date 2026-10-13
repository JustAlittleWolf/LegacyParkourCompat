# Candidate: Nether portal callback uses a broader entity-inside shape

1.21.5, mojmap: `1.21.5/mojmap/net/minecraft/world/entity/Entity.java`, class declaration / field initialization 107-110; setAsInsidePortal 2210-2221; handlePortal 2223-2244; handlePortal 2223-2245.
1.21.8, mojmap: `1.21.8/mojmap/net/minecraft/world/entity/Entity.java`, class declaration / field initialization 107-110; handlePortal 2391-2413.
1.21.5, mojmap: `1.21.5/mojmap/net/minecraft/world/level/block/NetherPortalBlock.java`, getShape 61-64; getPortalTransitionTime 114-125.
1.21.5, mojmap: `1.21.5/mojmap/net/minecraft/world/phys/shapes/Shapes.java`, block 49-51.
1.21.8, mojmap: `1.21.8/mojmap/net/minecraft/world/phys/shapes/Shapes.java`, block 49-51.
1.21.5, mojmap: `1.21.5/mojmap/net/minecraft/world/entity/PortalProcessor.java`, processPortalTeleportation 21-43.
1.21.8, mojmap: `1.21.8/mojmap/net/minecraft/world/entity/PortalProcessor.java`, processPortalTeleportation 21-43.
1.21.8, mojmap: `1.21.8/mojmap/net/minecraft/world/level/block/NetherPortalBlock.java`, getPortalTransitionTime 114-125.

Difference: - Exact precondition: on a tick when the player is eligible for portal use and not on portal cooldown, its movement callback scan reaches a NetherPortal block and the movement path intersects the block volume but misses the axis-specific thin portal shape. 1.21.5 then does not call the portal callback for that block; 1.21.8's inherited `Shapes.block()` sentinel calls it. To produce a transition, the 1.21.8-only callback condition must recur enough ticks for `PortalProcessor` to reach the configured Nether portal delay; one isolated callback only starts or refreshes its state. - Analysis: the default full-block sentinel intentionally bypasses shape intersection in `Entity#checkInsideBlocks`, while the removed override constrained the 1.21.5 callback to the portal plane. Sustained direct Player contacts can therefore start and advance portal processing in 1.21.8 when 1.21.5 would not call the callback.

Movement meaning: - Reachable Player callback: `NetherPortalBlock#entityInside` is identical 1.21.5/1.21.8 and calls `Entity#setAsInsidePortal` when `canUsePortal(false)` succeeds (1.21.5 lines 107-110; 1.21.8 lines 102-105). The unchanged `Entity#setAsInsidePortal` stores or updates `PortalProcessor` unless the entity is on portal cooldown (1.21.5 lines 2210-2221; 1.21.8 lines 2378-2389). `Entity#handlePortal` later processes it and applies the returned `TeleportTransition` (1.21.5 lines 2223-2244; 1.21.8 lines 2391-2413). These methods are on the direct player path; portals and player dimension transitions predate both releases. - Analysis: the default full-block sentinel intentionally bypasses shape intersection in `Entity#checkInsideBlocks`, while the removed override constrained the 1.21.5 callback to the portal plane. Sustained direct Player contacts can therefore start and advance portal processing in 1.21.8 when 1.21.5 would not call the callback.


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
      "path": "1.21.5/mojmap/net/minecraft/world/level/block/NetherPortalBlock.java",
      "member": "getShape",
      "lines": "61-64",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 1cb1b0a796f491deb2317d8a2706442741a627582ca86e3cc7adb6345f975d1b; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 3907657ade3e61bc8cffb4ca0a1bcba15f57986be4e613900947119364a206e5 *1.21.5/client_mappings.txt. Canonical source-manifest SHA-256 365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.5",
      "mapping": "mojmap",
      "path": "1.21.5/mojmap/net/minecraft/world/phys/shapes/Shapes.java",
      "member": "block",
      "lines": "49-51",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 03cd390636f0253b881ddd36c70e61cca2956b02281d45df26c8038ebbdbf63b; archived source-hash equivalence not established. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 3907657ade3e61bc8cffb4ca0a1bcba15f57986be4e613900947119364a206e5 *1.21.5/client_mappings.txt. Canonical source-manifest SHA-256 365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.5",
      "mapping": "mojmap",
      "path": "1.21.5/mojmap/net/minecraft/world/entity/Entity.java",
      "member": "class declaration / field initialization",
      "lines": "107-110",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 fc177733e9cc4b2d5cf2562e5529d0f4e0b9c3690f081fbd58aa9119eb173dc4; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 3907657ade3e61bc8cffb4ca0a1bcba15f57986be4e613900947119364a206e5 *1.21.5/client_mappings.txt. Canonical source-manifest SHA-256 365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.8",
      "mapping": "mojmap",
      "path": "1.21.8/mojmap/net/minecraft/world/entity/Entity.java",
      "member": "class declaration / field initialization",
      "lines": "107-110",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 c403e6176d27b5bfd6aaa0dea3735ffa4fcf80dbae58766661dd84453b7e9704; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: dce035fedcc047ce0306c69892176b5eb9036bcc2845c87198163f12bb1d67ca *1.21.8/client_mappings.txt. Canonical source-manifest SHA-256 8ffb76cea647a2ba4fe58e000678f751bea2c40ea6358bae492e962ed1d9d008 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.5",
      "mapping": "mojmap",
      "path": "1.21.5/mojmap/net/minecraft/world/entity/Entity.java",
      "member": "setAsInsidePortal",
      "lines": "2210-2221",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 fc177733e9cc4b2d5cf2562e5529d0f4e0b9c3690f081fbd58aa9119eb173dc4; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 3907657ade3e61bc8cffb4ca0a1bcba15f57986be4e613900947119364a206e5 *1.21.5/client_mappings.txt. Canonical source-manifest SHA-256 365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.5",
      "mapping": "mojmap",
      "path": "1.21.5/mojmap/net/minecraft/world/entity/Entity.java",
      "member": "handlePortal",
      "lines": "2223-2244",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 fc177733e9cc4b2d5cf2562e5529d0f4e0b9c3690f081fbd58aa9119eb173dc4; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 3907657ade3e61bc8cffb4ca0a1bcba15f57986be4e613900947119364a206e5 *1.21.5/client_mappings.txt. Canonical source-manifest SHA-256 365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.8",
      "mapping": "mojmap",
      "path": "1.21.8/mojmap/net/minecraft/world/phys/shapes/Shapes.java",
      "member": "block",
      "lines": "49-51",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 03cd390636f0253b881ddd36c70e61cca2956b02281d45df26c8038ebbdbf63b; archived source-hash equivalence not established. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: dce035fedcc047ce0306c69892176b5eb9036bcc2845c87198163f12bb1d67ca *1.21.8/client_mappings.txt. Canonical source-manifest SHA-256 8ffb76cea647a2ba4fe58e000678f751bea2c40ea6358bae492e962ed1d9d008 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.5",
      "mapping": "mojmap",
      "path": "1.21.5/mojmap/net/minecraft/world/entity/PortalProcessor.java",
      "member": "processPortalTeleportation",
      "lines": "21-43",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 88fdf8295eb812b7a9512086a91d654157c7473220822ef83f7938308c841ab2; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 3907657ade3e61bc8cffb4ca0a1bcba15f57986be4e613900947119364a206e5 *1.21.5/client_mappings.txt. Canonical source-manifest SHA-256 365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.8",
      "mapping": "mojmap",
      "path": "1.21.8/mojmap/net/minecraft/world/entity/PortalProcessor.java",
      "member": "processPortalTeleportation",
      "lines": "21-43",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 88fdf8295eb812b7a9512086a91d654157c7473220822ef83f7938308c841ab2; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: dce035fedcc047ce0306c69892176b5eb9036bcc2845c87198163f12bb1d67ca *1.21.8/client_mappings.txt. Canonical source-manifest SHA-256 8ffb76cea647a2ba4fe58e000678f751bea2c40ea6358bae492e962ed1d9d008 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.5",
      "mapping": "mojmap",
      "path": "1.21.5/mojmap/net/minecraft/world/level/block/NetherPortalBlock.java",
      "member": "getPortalTransitionTime",
      "lines": "114-125",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 1cb1b0a796f491deb2317d8a2706442741a627582ca86e3cc7adb6345f975d1b; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 3907657ade3e61bc8cffb4ca0a1bcba15f57986be4e613900947119364a206e5 *1.21.5/client_mappings.txt. Canonical source-manifest SHA-256 365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.8",
      "mapping": "mojmap",
      "path": "1.21.8/mojmap/net/minecraft/world/level/block/NetherPortalBlock.java",
      "member": "getPortalTransitionTime",
      "lines": "114-125",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 883beec01685148971b2b284be2267b5a6a1f258c49e2da4dd28fb4155d49967; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: dce035fedcc047ce0306c69892176b5eb9036bcc2845c87198163f12bb1d67ca *1.21.8/client_mappings.txt. Canonical source-manifest SHA-256 8ffb76cea647a2ba4fe58e000678f751bea2c40ea6358bae492e962ed1d9d008 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.5",
      "mapping": "mojmap",
      "path": "1.21.5/mojmap/net/minecraft/world/entity/Entity.java",
      "member": "handlePortal",
      "lines": "2223-2245",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 fc177733e9cc4b2d5cf2562e5529d0f4e0b9c3690f081fbd58aa9119eb173dc4; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 3907657ade3e61bc8cffb4ca0a1bcba15f57986be4e613900947119364a206e5 *1.21.5/client_mappings.txt. Canonical source-manifest SHA-256 365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.8",
      "mapping": "mojmap",
      "path": "1.21.8/mojmap/net/minecraft/world/entity/Entity.java",
      "member": "handlePortal",
      "lines": "2391-2413",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 c403e6176d27b5bfd6aaa0dea3735ffa4fcf80dbae58766661dd84453b7e9704; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: dce035fedcc047ce0306c69892176b5eb9036bcc2845c87198163f12bb1d67ca *1.21.8/client_mappings.txt. Canonical source-manifest SHA-256 8ffb76cea647a2ba4fe58e000678f751bea2c40ea6358bae492e962ed1d9d008 matches archived publication; cited file hash is present in that manifest."
    }
  ],
  "sourceReviews": [],
  "openIssues": [],
  "runtime": "unverified"
}
```
