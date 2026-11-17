# Independent raw-resource audit r2

Audit scope: independently verify the 1.14.4 raw-client input and the two resource-membership claims in `wall_resource_membership/extraction-record.md`. This verdict is bound to extraction-record SHA-256 `f26f84c7221f667cd84ff4e048d06b0fd3c3aec52779f3fbd0a8f4e2afe64fea`. No canonical source or artifact was modified; no network, download, or decompilation was used.

## Raw client identity

The local `.minecraft-decompile-cache/1.14.4/client.jar` SHA-256 is `b3b2a798e2d67b566008fe4a03767ae2c7ff3f8c7ba6751e7b71fc7299672d0a`. This exact value agrees across the cache `client.jar.pin.json`, `decompiled_minecraft/1.14.4/ornithe-feather.ready.json` (`clientSha256`), the `1.14.4/client.jar` line in `ornithe-feather.artifacts.sha256`, and `development/research/source-evidence/20261103/checkpoint03/1.14.4-resource-manifest.json` (`clientSha256`). The local artifact-manifest SHA-256 is `308cc33ef6dffc047432ade6affc88eccc9de94a736ee9a98571fd92915c2970`, matching the ready marker's artifact-manifest pin.

The ready marker says `ready`, version `1.14.4`, mapping `ornithe-feather`. The local version metadata identifies `1.14.4`, has SHA-256 `615f466a39d2c19ae9b6e2401aa7bd07dbda337f976bcce383326b8d6ba4e532`, and declares client SHA-1 `8c325a0c5bd674dd747d6ebaa4c791fd363ad8a9` and size `25191691`; independently computed local values match both SHA-1 and size.

## ZIP resource verification

I opened the pinned raw client JAR read-only and hashed the decompressed bytes of each exact ZIP entry. The two expected entries are present, and the only entries in the checked leaves/walls/TNT tag-name scope are these two:

- `data/minecraft/tags/blocks/leaves.json` — SHA-256 `c09bb9af30197720b232b9c84079039d4f344bfc2970fa0d02a1b43bc091b991`, matching the checkpoint resource manifest and extraction payload. It contains six values, all leaf blocks; `minecraft:tnt` is absent.
- `data/minecraft/tags/blocks/walls.json` — SHA-256 `f20174f51f66d27681fd9d469a77c86cae82012c0d9125e7d246c426b772391c`, matching the checkpoint resource manifest and extraction payload. It contains fourteen wall blocks; `minecraft:tnt` is absent.

No `data/minecraft/tags/blocks/tnt.json` entry or other leaves/walls/TNT tag entry was present. The extracted payload JSON files' hashes match the same exact raw ZIP-entry bytes. The resource membership claim is therefore independently confirmed: neither tag includes TNT.

## Verdict

**Accepted.** The extraction record is correctly pinned to the local official raw 1.14.4 client artifact, both exact resource-entry hashes match the checkpoint manifest and payloads, and TNT is absent from both tag value lists. The earlier audit's Feather-mapped-client-artifact availability verdict is not altered; this r2 addresses the raw-client provenance and resource claim only.
