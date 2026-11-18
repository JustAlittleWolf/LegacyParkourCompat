# Independent audit: Llama passenger placement, 1.16.5 → 1.17

## Input identity

Audited frozen report: `c-llama-1165-117-position-intermediate/report.md`  
SHA-256: `9dd0124a95de04e2aee93626da378a984d776b05b415651a2af9e77f3afb2729`

This verdict is bound to that exact report revision. It is an independent bounded source audit of ordinary adult default Llama/TraderLlama placement of one direct local-player passenger; it does not audit vehicle steering/physics or claim full-pair discovery coverage.

## Source identity and provenance

Exact Mojmap sources were read from the primary checkout, `decompiled_minecraft/1.16.5/mojmap/` and `decompiled_minecraft/1.17/mojmap/`. Source files were not generated or modified during this audit.

- **1.16.5:** `mojmap.provenance.json` identifies `versionId=1.16.5`, namespace/mapping option `mojmap`, and the exact decompile run. `mojmap.ready.json` reports ready, 3,523 files, client SHA-256 `00b5ebbc33e95ea88c1ab80601599c9827e9aa861d93ccc2cfcbbfd996e86263`, and source-manifest SHA-256 `9499f2611d0e6dde37cb635f1a28a591416382d6bc188b37d5f99e2b2a20823b`; the actual manifest hashes to that value. Its artifact manifest also matches the ready marker and includes the 1.16.5 Mojmap client artifact (`client-mojmap.jar`, SHA-256 `7c1a9f30983fa3c777d00116bdf3f3cef97c1c1aaf8294027052f5337fcde940`). Identities of all five audited class files match the source manifest.
- **1.17:** `.decompile-provenance.json` identifies the exact 1.17 Mojang client input and official `client.txt` mappings by SHA-256, records Vineflower 1.12.0 and Tiny Remapper 0.14.1 artifact hashes, and embeds per-source hashes. Identities of all five audited classes match the embedded source map.

The original old cache is not present as an audit prerequisite. Its absence does not leave source identity open: exact release and Mojmap identities are tied to ready/provenance records, and the cited on-disk sources match their intact source manifests. For reproducibility, provenance-record hashes are 1.16.5 `mojmap.provenance.json` `b71df61eb775134119881915198f87c4337bae409e1f0de8d8d6c007e062902d`, 1.16.5 `mojmap.ready.json` `e9dd4397c6ade5f68d27f50baf285086dc367b56d2119b03a116cf3cf3b23fee`, 1.16.5 `artifacts.sha256` `f9b9812d6995012cefcc1201a63855e5931e8551b508753be6c46d442d8b370c`, and 1.17 `.decompile-provenance.json` `afc9dc30cd8f6406d8ea306e64cc0b3601cc8d7c7bcd314f737573d830a79357`.

## Independent finding

**Accepted bounded no-difference verdict.** In both exact sources, `Llama.positionRider(Entity)` is guarded by `this.hasPassenger(entity)` and computes the yaw terms in the same order and types before one `entity.setPos(...)` call. Its Y argument is the left-associative `this.getY() + this.getPassengersRidingOffset() + entity.getMyRidingOffset()`. The Llama offset is `this.getBbHeight() * 0.67` (double arithmetic); the player offset is the double literal `-0.35`. For the scoped adult default Llama and ordinary player, the Y result is therefore `llamaY + (llama bounding-box height * 0.67) + (-0.35)`. There is no player pose/crouch branch in the player override and no maturity/TraderLlama branch in the Llama placement method.

References, both Mojmap:

- 1.16.5 `net/minecraft/world/entity/animal/horse/Llama.java`, `positionRider` lines 151–159 and `getPassengersRidingOffset` lines 161–164; `net/minecraft/world/entity/player/Player.java`, `getMyRidingOffset` lines 989–991.
- 1.17 same Llama members at lines 153–161 and 163–166; Player member at lines 1005–1007.
- Both `TraderLlama.java` declare `TraderLlama extends Llama` and do not override either placement or passenger-offset method.
- 1.16.5 `net/minecraft/world/entity/Entity.java`, `rideTick` lines 1577–1583 calls `this.getVehicle().positionRider(this)` after the passenger test; 1.17 lines 1687–1693 does the same. This is virtual dispatch to the Llama override. `LocalPlayer.rideTick` in both versions calls `super.rideTick()` and only has a subsequent `Boat` input branch, so it does not replace or modify this Llama placement callback.
- `AbstractHorse.positionRider` adds its stand-animation Y adjustment after `super.positionRider`, but Llama's override does not call `super`; that adjustment is not on this path in either release.

The source identity and bounded behavior claim in the frozen report are confirmed. The scope and no-difference conclusion are accepted; this does not establish other mount/player callbacks or complete the pair inventory.

### Compared source-file SHA-256 identities

These exact file hashes were independently computed from the cited source paths and matched the corresponding 1.16.5 manifest / 1.17 embedded source map:

| Class | 1.16.5 | 1.17 |
|---|---|---|
| `Llama.java` | `18bf21b8d7559e4c9a3195126dc0b30b691e8e869fa1c214956a6395534e5f2d` | `0b41ea3feac6b413976171f45fca83fad0eb9e24f06de5f3ece40d20bfb04d72` |
| `TraderLlama.java` | `2d5d0d9bbb694ec542d6d167f1e98c95a0c1991b66b6ef69c7881b326c54c8f3` | `7902fcba73b9b9a8c3c941f047b47b9fa05c3f7a2bd6ddda4a9ba3dd1876ee0e` |
| `AbstractHorse.java` | `c4e33cd5a8d52099fbcd2c90c5d0a55a0471ae768e682f3a45e53c4dd7925672` | `19fda974a022c9521309661ebea103049d9d1ab96d29afebb04b89bd9e7405c0` |
| `Entity.java` | `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666` | `81231ad8ac1b9a7a9f7ee186352c7e73c0c5211a3034c3a35c04badc6914c8ef` |
| `Player.java` | `d2e26589bdb6a20dc914266db06aa48f50811efc792d6e63b3008c9199914960` | `9ad4c439814caf5f51cf6da9a86c5d22906b0a28b5b606bdeb1b770617d90516` |
