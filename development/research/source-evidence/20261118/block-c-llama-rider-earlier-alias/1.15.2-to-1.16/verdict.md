# Independent audit: Llama passenger Y placement

## Bound input and verdict

Audited `c-llama-1152-116-position-intermediate/report.md`, SHA-256 `065FE47F70B109AE939116D4F8271D23FA32AFCD16A048C65CA205A52861FEFE`.

**Behavioral result: confirmed for the bounded operation.** The reported no-difference conclusion is supported: an ordinary adult Llama and its `TraderLlama` subclass place a player at the same Y in 1.15.2 and 1.16, with the same gate and direct `setPos` call. The report's evidence-status paragraph needs correction: it missed provenance and readiness files adjacent to each mapping child. Those files provide locally verifiable exact-source provenance. The original client binaries and related cached artifacts are absent, so their bytes cannot now be rehashed; that is a separate revalidation limit, not absence of local source provenance.

## Canonical source and provenance audit

The literal canonical source roots are `decompiled_minecraft/1.15.2/mojmap/` and `decompiled_minecraft/1.16/mojmap/`. Each is a real directory under its exact version child. The mapping children contain `com/` and `net/`; the associated records are siblings of `mojmap/` under the version child. `mojmap.provenance.json`, `mojmap.ready.json`, `artifacts.sha256`, `mojmap.sources.sha256`, `mojmap.success.log`, and `movement-diagnostics.txt` are present for both versions. There is no `.decompile-provenance.json` in either version parent or mapping child; the actual adjacent record name is `mojmap.provenance.json`.

| Exact root | Provenance SHA-256 | Ready SHA-256 | Source manifest SHA-256 | Artifact manifest SHA-256 | Local source verification |
|---|---|---|---|---|---|
| `decompiled_minecraft/1.15.2/mojmap/` | `d4802d35ee2927a44d753871f184c3255c060eb94457a5c65a8bc121a087954e` | `64a0e40b784d525ce50937c63e465fc4f564efb1f7af17f2bac3c77a36907be0` | `cb7fd93f8730d8b2813857744f8572060535c7de44fcee86222acd1ceb6f43e7` | `208ab867640097a0c188e452de4876934deb217d75ac726d358cfa6730260406` | All 3,332 entries match; 17,543,871 bytes; ready counts/bytes and manifest hashes match. |
| `decompiled_minecraft/1.16/mojmap/` | `371d50521a183f4a3f42fac2d18021f228e0a4e6b2e8a9838c2805391709f2a9` | `5b6cb24f95e83b856c53f646d6477f2e46dae2276737dee56dd07c5bd745e4a2` | `e3c23e94ac985c25dec0c768aed17b443ba4823662cf1e152235c99756ae5d4a` | `b482d7aa44422dfafebad3a0b2a886d47d1f01135f99055aea5973faa110e8b2` | All 3,572 entries match; 19,220,152 bytes; ready counts/bytes and manifest hashes match. |

Both provenance records identify the exact version ID and `mojmap` namespace. The 1.15.2 ready/artifact records pin client SHA-256 `4a73008a73f3824b7c711750a5a37556df8614f193c0a531e292dad159a73a7c`; the 1.16 records pin `00740ef4475d4cd0a1d7064f89602f759c45ff9afe29da9b0ee0b9037ebb2a71` and include the exact metadata/client URLs. Their recorded cache paths no longer contain the original `client.jar`, mapped client or mapping file. Tool versions and run details are recorded; these older adjacent records do not expose a full decompiler classpath-content digest. Consequently the local source files and their recorded version/mapping lineage are verifiable, while a fresh binary-input/tool-classpath rehash is not.

The report's ten listed operation/dependency source hashes agree with the adjacent `mojmap.sources.sha256` entries and were included in the all-entry verification above. Key operation identities: 1.15.2 `Llama.java` `BBE98226C5AFADDCE8CA7458C721FBEFFF592CC75E02BC261D6B440CA6877F19`; 1.16 `Llama.java` `942FCE37F13216906FE48D620076F6514FD93D4A3983EAC8A8909170A9385C5C`; `TraderLlama.java` in both versions `495D65DA1C1903BA769793EE4DBCCD6248EF96137C1709AF6DAA00C563C03DD4`.

## Independent source trace

- `Entity.rideTick` invokes `this.getVehicle().positionRider(this)` for a passenger: 1.15.2 `Entity.java:1544-1549`; 1.16 `Entity.java:1557-1562`. `LocalPlayer` extends `AbstractClientPlayer`, which extends `Player`, in both trees (`LocalPlayer.java:78`, `AbstractClientPlayer.java:21`), so the player offset override applies.
- The 1.15.2 Llama override is `Llama.java:149-156`: under `hasPassenger(entity)`, its sole direct placement is `entity.setPos(..., this.getY() + this.getRideHeight() + entity.getRidingHeight(), ...)`. Its offset at `:158-161` is `getBbHeight() * 0.67`.
- The 1.16 override is `Llama.java:150-157`: the same gate and direct placement, with Y `this.getY() + this.getPassengersRidingOffset() + entity.getMyRidingOffset()`. Its offset at `:159-162` is the same `getBbHeight() * 0.67`.
- Player returns `-0.35` as a `double` from `getRidingHeight()` (`1.15.2 Player.java:985-988`) and `getMyRidingOffset()` (`1.16 Player.java:988-991`). The ordinary adult Llama and TraderLlama type dimensions are `0.9F, 1.87F` in both `EntityType.java` (`1.15.2:208, 285-287`; `1.16:275-277, 420-422`). `getBbHeight()` returns `float` (`Entity.java:2662-2663` / `2712-2713`). Thus for the default adult type, the Y operation remains left-to-right double addition after the float height is promoted: `vehicleY + ((double) 1.87F * 0.67D) + (-0.35D)`. The computed ride offset is `1.252900003194809`; net offset from vehicle Y is `0.902900003194809`.
- `TraderLlama extends Llama` at `TraderLlama.java:22` in both trees and declares no `positionRider` override. `Llama.positionRider` does not call `super`. It therefore replaces `AbstractHorse.positionRider`, rather than following its superclass placement/stand-animation path (`AbstractHorse.java:900-916` in 1.15.2; `:906-922` in 1.16). For this bounded ordinary call there is one Llama `setPos` and no second superclass passenger placement.

The named source coverage confirms this operation and its call path; it does not establish full-pair tick/inventory coverage or runtime parity.
