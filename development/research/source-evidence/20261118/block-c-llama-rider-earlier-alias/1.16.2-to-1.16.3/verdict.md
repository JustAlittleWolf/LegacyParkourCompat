# Independent source audit: Llama passenger Y placement

## Verdict

**Claim accepted:** for the bounded ordinary adult default `Llama` and `TraderLlama` local player-rider case, there is no 1.16.2→1.16.3 difference in the passenger Y value or its tick-time dispatch. The frozen report's no-difference conclusion is supported by exact paired source. Its provenance limitation is inaccurate: the 1.16.3 tree has a hidden `.decompile-provenance.json` whose declared hashes match the five reviewed source files. Adjacent 1.16.3 ready/source manifests are absent, which is a separate narrower gap. No source-generation or runtime checks were performed.

## Frozen input identity

- Report: `C:/Users/Wolfi/.codex/visualizations/2026/11/17/01a1eb7b-b9ff-7a20-8686-0295dde20f26/c-llama-1162-1163-position-intermediate/report.md`
- Actual report SHA-256 (PowerShell `Get-FileHash -Algorithm SHA256`): `72694c8c684eb1ee7ee52616cd782d037c775737ab4ca61bd698933749fed657` (5,069 bytes).

## Source publication checks

- 1.16.2 Mojmap adjacent `mojmap.ready.json` names `versionId=1.16.2`, `mapping=mojmap`, source manifest `mojmap.sources.sha256`, and client SHA-256 `19960f4e3c60f2723f361844d2b4020b8bf2160bd91a1e1efde8ce5aea4ff73d`. The ready marker's declared source-manifest SHA-256 matches the adjacent manifest. Adjacent `mojmap.provenance.json` records the exact 1.16.1/1.16.2 Mojmap run and ready marker. No `.decompile-provenance.json` is present for 1.16.2.
- 1.16.3 has no adjacent ready marker, artifact manifest, or source hash manifest. Its `decompiled_minecraft/1.16.3/mojmap/.decompile-provenance.json` is present (actual SHA-256 `513271804ead6757345d305717e7e0bfc1641434c35a3b254f287bd8fd281498`). It records the exact-version 1.16.3 client, official client mappings and version metadata inputs with pinned SHA-256 values, the Mojmap decompile pipeline, tool/runtime identity, and a per-source SHA-256 map. Every reviewed 1.16.3 source file's actual digest equals the hidden manifest's declared digest. Thus the reviewed canonical source payload is hash-verified despite missing adjacent metadata.
- The named original client artifact was not found in the local `.minecraft-decompile-cache`. That does not undo the file-to-manifest verification above; provenance records the original input hash, but the unavailable raw artifact was not independently rehashed in this audit.

## Independent operation trace

The same five reviewed files have equal actual SHA-256 values in both trees, and those values match each side's applicable source manifest:

| Mojmap source | SHA-256 in both versions |
|---|---|
| `net/minecraft/world/entity/animal/horse/Llama.java` | `18bf21b8d7559e4c9a3195126dc0b30b691e8e869fa1c214956a6395534e5f2d` |
| `net/minecraft/world/entity/animal/horse/TraderLlama.java` | `2d5d0d9bbb694ec542d6d167f1e98c95a0c1991b66b6ef69c7881b326c54c8f3` |
| `net/minecraft/world/entity/Entity.java` | `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666` |
| `net/minecraft/world/entity/player/Player.java` | `d2e26589bdb6a20dc914266db06aa48f50811efc792d6e63b3008c9199914960` |
| `net/minecraft/world/entity/EntityType.java` | `8b4c9ea61b2417dafc8bdc52a18aacb556e3ec9ce2a94fe69b307b9db2c009cd` |

At `Llama.positionRider(Entity)` (lines 152–159), `hasPassenger(entity)` gates the direct `entity.setPos(...)` call. Its Y argument is `this.getY() + this.getPassengersRidingOffset() + entity.getMyRidingOffset()` (line 157), left-associative with all addition operands `double`. `Llama.getPassengersRidingOffset()` returns `this.getBbHeight() * 0.67` (162–164): the height is `float`, promoted to `double` for multiplication by the `double` literal. For the registered adult types, `EntityType.LLAMA` and `TRADER_LLAMA` both use `.sized(0.9F, 1.87F)` (277–279, 425–427); the adult offset therefore uses `1.87F` promoted to `double`. `Player.getMyRidingOffset()` returns `-0.35` (`double`, Player lines 989–991). The Y result is mount Y plus those two terms in source order.

For tick-time dispatch, `Entity.rideTick()` ticks the entity and, under `isPassenger()`, calls `this.getVehicle().positionRider(this)` (Entity lines 1577–1583). Virtual dispatch selects `Llama.positionRider` for a Llama or inherited TraderLlama. TraderLlama extends Llama and has no rider-position or rider-offset override. The generic `Entity.positionRider` callback helper (1585–1594) is not this route: Llama overrides the method and directly calls `setPos`. All of these gates and paths are byte-identical across the two exact source folders.

## Scope and disposition

This verdict covers only the default adult Llama/TraderLlama mount's player passenger Y placement, its inheritance and tick dispatch, adult registered dimensions, and player riding offset. It does not claim X/Z/yaw equivalence, whole-pair movement coverage, intermediate patch boundaries, or runtime parity. The frozen report's source identity paragraph should not be used to call the 1.16.3 reviewed source unknown: the hidden manifest and actual source hashes provide the checked identity. Adjacent 1.16.3 metadata and the raw client artifact remain absent locally.