# B12 provenance supplement: 1.21.8

Status: COMPLETED — missing 1.21.8 provenance verification resolved. Original report preserved unchanged.

## Verified pins

- `decompiled_minecraft/1.21.8/mojmap.ready.json` SHA-256: `2e3bf302503eb127061cc605797b390f5f4a4b508f09350a59d78ced857ba7d7`. It declares status `ready`, version `1.21.8`, mapping `mojmap`, source manifest `mojmap.sources.sha256`, source manifest SHA-256 `8ffb76cea647a2ba4fe58e000678f751bea2c40ea6358bae492e962ed1d9d008`, and artifact manifest `artifacts.sha256` with SHA-256 `c8210b15012dcc4e109c2f73015b4117abec1f8ab04fdd375dbc11419e9e246c`.
- `decompiled_minecraft/1.21.8/mojmap.sources.sha256` actual SHA-256 is `8ffb76cea647a2ba4fe58e000678f751bea2c40ea6358bae492e962ed1d9d008`, matching both the assignment and readiness record.
- `decompiled_minecraft/1.21.8/artifacts.sha256` actual SHA-256 is `c8210b15012dcc4e109c2f73015b4117abec1f8ab04fdd375dbc11419e9e246c`, matching the readiness record. Its `1.21.8/client_mappings.txt` entry is SHA-256 `dce035fedcc047ce0306c69892176b5eb9036bcc2845c87198163f12bb1d67ca`, matching the assigned official-mapping identity.

## Original report source bodies rechecked

Each listed local source file hash matches its entry in the verified `mojmap.sources.sha256` manifest:

- `net/minecraft/world/entity/Entity.java`: `c403e6176d27b5bfd6aaa0dea3735ffa4fcf80dbae58766661dd84453b7e9704`
- `net/minecraft/world/entity/player/Player.java`: `8dc5514fe44311f39268692f5188840b9f65cb71143228fa8b84242585ea7987`
- `net/minecraft/world/level/CollisionGetter.java`: `03752693709491670a3c6b1d91114d46d92d6dfb1f48d3ecea3725c08b22ac8c`
- `net/minecraft/world/level/BlockCollisions.java`: `e60f4d3cf7537a5e3b8b3ad099a2a7ec167f972460fa01045fd2b2141b6ddaa0`

The corresponding 1.21.9 body hashes in the original report were verified against its local `.decompile-provenance.json` source hashes. Thus the bounded source comparison’s exact-source provenance gate is closed for both sides.

## Residuals

No provenance issue remains for the assigned slice. The original report’s exclusions and open wider endpoint/pair-coverage gate remain unchanged.
