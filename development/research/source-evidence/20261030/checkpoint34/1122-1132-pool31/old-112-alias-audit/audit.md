# Old 1.12 alias audit

## Scope and binding

This independent read-only audit is limited to the alias report and source hashes in `.native-source/20261030/1122-1132-pool31/old-112-alias-closure/`, compared against the canonical read-only trees at `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\{1.12,1.12.1,1.12.2}\ornithe-feather`.

- `report.md` SHA-256: `0010c45f3d9d0fab2756772150f8d85525a8f3a67ddab04ca95bd19d1fa5b22b`
- `source-hashes.md` SHA-256: `a448875c7a64c5975b518fdb230cb2e736afcfdfbafd76d2fc18172aa38a80f4`

Both digests match the assigned artifacts. The source-file hashes in `source-hashes.md` all match my direct SHA-256 calculations for the five named source files in all three releases. The 1.12 and 1.12.1 provenance records each include source maps with matching hashes for all five files. For 1.12.2, `ornithe-feather.ready.json` identifies version `1.12.2`, namespace `ornithe-feather`, and Feather Gen2 build 2 merged jar plus tiny mapping; its recorded manifest digest matches `ornithe-feather.sources.sha256`, and the manifest rows for all five files match their direct file hashes. The provenance metadata file hashes also match the assigned source-hashes artifact.

The embedded 1.12 and 1.12.1 provenance inputs identify the respective Feather Gen2 build 2 merged jars and Minecraft client objects (`909823f9c467f9934687f136bc95a667a0d19d7f` / `59e84cf6acc3912e1705ab05c722485afb3ec33343eef4756d8c16edfe6411ca`, and `83385d346cf7f97d1e447b888750fb88e9928b93` / `19331fb29bf8b8d5ed764677c94cca9d66481e10104f660eb555894a4300cf22`). Their provenance-file hashes are `27515e5f4c71e7db2f035a81716507128d77466c75380ad1ef7d140c06401467` and `0ff5246e8d29c04554cc9ec93ae9b4abef539652325b6c0ac822851d7186a9c8`. For 1.12.2, the ready manifest digest is `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`; the provenance file hash is `747425a26da363d1abc8a2cecd83a74504aeb1f58be93aa53f48c690e35318ce`, and its mapping artifacts name the matching Feather Gen2 build 2 merged jar and tiny mapping.

## Alias result

I independently extracted and SHA-256 hashed the complete brace-delimited method bodies as UTF-8 in each tree. The following are exact aliases across 1.12, 1.12.1, and 1.12.2:

| Method | Body SHA-256 in each release |
|---|---|
| `LivingEntity.jumpInWater()` | `45ada6f40c31397692fead02e195b20d9e5c737c9ab27caa6d01a48042e4cfa0` |
| `LivingEntity.moveRelative(float,float,float)` | `82b08ec68591b107e60c32b128359b6275de4531df3c1dd8deb8b8759d36d209` |
| `LivingEntity.mobTick()` | `34b8d985ba8a6df3b3b11059246d03eeaaa7f955a35f75b497419c42fe129832` |
| `Entity.checkWaterCollisions()` | `0ebe4f13e1f3f49a4f29072918ef82aa85a656c7610be8077306145a944f4669` |
| `Box.grown(double,double,double)` | `6b331bdb2466d365c311a5c62485b06a883a9e44d1c10be40a3baa819b772dd1` |
| `World.applyLiquidDrag(Box,Material,Entity)` | `55239dba546004396895e3d5b5667a4fc051318bd2feb23d8ec6c5a36c8394b3` |
| `LiquidBlock.getFlow(WorldView,BlockPos,BlockState)` | `41acbbf61dcfbc4f0a8cfd33f54963dbbad67d92db8ba0d6ab7ce3d298594eb7` |
| `LiquidBlock.applyMaterialDrag(World,BlockPos,Entity,Vec3d)` | `5fd971ddd1196df352e27e9953a673ae4e135786764165db05323e5ef25d68c3` |

`World.java` has a different full-file digest in each release, but `applyLiquidDrag` itself has the same complete-body digest. The other four source files have equal full-file digests across the three releases. These results support the report's alias applicability verdict for the named operations.

## Correction to source-hashes.md

The `LivingEntity.mobTick()` entry in the assigned `source-hashes.md` has a one-character hash error: it records `34b8d985ba8a6df3b3b11059246d03eeaa7f955a35f75b497419c42fe129832` (two `a` characters before `7f`), while the independently extracted body hashes to `34b8d985ba8a6df3b3b11059246d03eeaaa7f955a35f75b497419c42fe129832` (three `a` characters before `7f`). This mismatch does not change the equality finding: all three actual method bodies match each other. I found no source-hash correction needed for the five full source files or the other listed method bodies.

## Boundary

This concludes alias applicability for the specifically named old-release operations. It does not establish pair freeze or complete source coverage.
