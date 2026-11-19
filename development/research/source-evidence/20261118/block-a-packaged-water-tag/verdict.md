# Packaged water tag audit verdict

**Terminal verdict: ACCEPT** — scoped only to exact packaged `data/minecraft/tags/fluid/water.json` membership in 1.21.11 and 26.1.2.

The immutable author evidence at `f82908571fe8e0ce8dedac9bb9300c2c03614a99:evidence.md` hashes to SHA-256 `b586be6d872e04bc9bdfa07e9b7584cb2902d5642417631ba2c770eb0ae790c3`.

I independently checked the recovered metadata and client archives against the canonical `artifacts.sha256` sidecars under `decompiled_minecraft/1.21.11` and `decompiled_minecraft/26.1.2`. Metadata SHA-1s matched the supplied official pins, metadata SHA-256s matched the sidecar entries, client SHA-1s matched both the official metadata `downloads.client.sha1` fields and supplied pins, and whole-client SHA-256s matched the sidecar entries.

| Release | Client SHA-256 | ZIP entries | Exact tag entry | Payload SHA-256 | Ordered values | `replace` |
| --- | --- | ---: | --- | --- | --- | --- |
| 1.21.11 | `1473c9489ac50fda3c435049a76a70d61a10b8610db27f5ba9d8756b686cd3bd` | 28,163 | present once; 74 bytes, compressed 54 | `dcfa69a748d03dbf788d8f7b0e5eb6c8de6355a527fcaf74b9210fe4be2a3004` | `minecraft:water`, `minecraft:flowing_water` | absent |
| 26.1.2 | `b1b3158572666445eff01e82fad8c7de2e4953db6d354f311730d77a8359d0b0` | 30,675 | present once; 74 bytes, compressed 54 | `dcfa69a748d03dbf788d8f7b0e5eb6c8de6355a527fcaf74b9210fe4be2a3004` | `minecraft:water`, `minecraft:flowing_water` | absent |

Both exact packaged tag payloads are identical. This verdict establishes only the listed vanilla packaged-tag membership in these two client artifacts; it makes no claim about datapacks, tag resolution/flow behavior, other releases, or movement parity.