# Independent source audit — body-eye-fluid query closure, revision r2

## Verdict

**ACCEPT.** The r2 report corrects the 26.2 client-hash qualification, stale caller locators, and 1.21.11 provenance-root attribution. Its operation-level source claims, exact source hashes, and stated scope limits are supported by the assigned trees and output records. Acceptance is bound to the dossier SHA-256 below.

## Immutable dossier identity

`comparison-r2.md` SHA-256: `cca922f095105c49a0e3752f46a410c436bbebc4c0bbe2c264b649e695f48be6` (matches the assigned revision).

## Independent checks rebound to r2

- Recomputed every listed relevant source SHA-256 and matched it against the corresponding release `*.sources.sha256` entry. The 26.1 / 26.1.1 / 26.1.2 Entity, EntityFluidInteraction, Player, LocalPlayer, LivingEntity and AbstractBoat files are byte-identical. The 26.2 EntityFluidInteraction source is byte-identical to those releases.
- Rechecked the literal 1.21.11 provenance and ready marker: provenance records the exact batch run, staging `--output-root`, tool versions, and staging cache path; the ready marker records canonical `outputPath`. The staging cache path is absent, so the input metadata `.pin.json` cannot be reread.
- Rechecked the 26.2 ready marker/provenance distinction: marker states `versionMetadataId=26.2` and has no `clientSha256`; provenance contains neither metadata nor client hash. The artifact inventory contains `40896ee9f1e2bec3c934daac7e93d41e9e3d9c2f8ae0ca366d52ffbfd1afa290`, and r2 accurately says this does not bind it as provenance input.
- The corrected references are accurate: 26.x `Player.getDestroySpeed` is at `Player.java:607–609`; 1.21.11 `LocalPlayer.updateIsUnderwater` spans `1143–1159`; `LivingEntity` eye-water blocks are `417–432` for 26.1–26.1.2 and `436–450` for 26.2.
- The fluid producer/query/cache ordering, exact-top boundary distinction, ordinary-player consumer paths, old boat eye-box gate, and new vehicle-adjusted passenger-box gate match the source bodies. These support a source-level predicate change, not a measured trajectory result.

## Full source SHA-256 inventory

Each listed hash was recomputed from the canonical source tree and matched its release manifest. The grouped row for 26.1–26.1.2 applies to each of those three releases individually. `EntityFluidInteraction.java` is absent from 1.21.11.

| Release / mapping | Source file | SHA-256 |
| --- | --- | --- |
| 1.21.11 / Mojmap | `net/minecraft/world/entity/Entity.java` | `32314478c6036fa9f3f3cc409c61c622eefc5a282d60e1011a1a33cc29cf18a3` |
| 1.21.11 / Mojmap | `net/minecraft/world/entity/player/Player.java` | `8e97167350a91741d0aa10d3b0d92a33150ed6dccdc94cd5b37d9c7ca22bcc81` |
| 1.21.11 / Mojmap | `net/minecraft/client/player/LocalPlayer.java` | `948e94f8e874b2f72e9a689e6e3ba1933f5728ec381d64f3bb5e2388718bd477` |
| 1.21.11 / Mojmap | `net/minecraft/world/entity/LivingEntity.java` | `19ed2d565858c401c69a06750b054a633a20ea864cab3a753b5c767f0bdd60e8` |
| 1.21.11 / Mojmap | `net/minecraft/world/entity/vehicle/boat/AbstractBoat.java` | `e7d22b0d7a7c204eea153d1dcac7f74b4e4738fe33bdd613aff9ebcc924aa3cf` |
| 26.1–26.1.2 / unobfuscated | `net/minecraft/world/entity/Entity.java` | `8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf` |
| 26.1–26.1.2 / unobfuscated | `net/minecraft/world/entity/EntityFluidInteraction.java` | `5264ff4f1fddebc3fa9d63ad2edbe2eaf617392ff946a867a6817a78b478ee62` |
| 26.1–26.1.2 / unobfuscated | `net/minecraft/world/entity/player/Player.java` | `44cf28e0c64e78d39fd13368e9991381dbebab67029070cb9ddc43f09d45d14d` |
| 26.1–26.1.2 / unobfuscated | `net/minecraft/client/player/LocalPlayer.java` | `433fd995ad317af0f6ef0e50c1e8e3483cb8f00e0e327d4edf27a4dd99666ebe` |
| 26.1–26.1.2 / unobfuscated | `net/minecraft/world/entity/LivingEntity.java` | `c3b64de8dbaba8ad8a7ccd4f33255346d8206e66f12ca91260971bf5e5ac93bd` |
| 26.1–26.1.2 / unobfuscated | `net/minecraft/world/entity/vehicle/boat/AbstractBoat.java` | `8d681d6ac6e93a648640fd39cde921cc8ed9d4aec03a49a9e9e43b28cb7be840` |
| 26.2 / unobfuscated | `net/minecraft/world/entity/Entity.java` | `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5` |
| 26.2 / unobfuscated | `net/minecraft/world/entity/EntityFluidInteraction.java` | `5264ff4f1fddebc3fa9d63ad2edbe2eaf617392ff946a867a6817a78b478ee62` |
| 26.2 / unobfuscated | `net/minecraft/world/entity/player/Player.java` | `8decc71b9c780664578ddb14591db2a2f207c72c05b676edded6f8e964576531` |
| 26.2 / unobfuscated | `net/minecraft/client/player/LocalPlayer.java` | `8d089aa09217e3607b38590f7c1623385562800943ac6dfd3d17804e041da6d6` |
| 26.2 / unobfuscated | `net/minecraft/world/entity/LivingEntity.java` | `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a` |
| 26.2 / unobfuscated | `net/minecraft/world/entity/vehicle/boat/AbstractBoat.java` | `9e69c4d86df7da415b84b1206f7539b95dcd89af050bb6ebff33a572b2c4064e` |

## Scope limits

This accepts only the assigned eye-fluid producer/query/cache, ordinary-player caller, vehicle/passenger gate, and output-identity claims. It does not establish pair-wide movement coverage, runtime parity, a measured trajectory, or an exact introduction release between 1.21.11 and 26.1. Original cache pin sidecars remain unavailable at the recorded paths, and the 26.2 input client binding remains open as r2 states. No product implementation, wiki, backlog, build, test, or runtime material was inspected.
